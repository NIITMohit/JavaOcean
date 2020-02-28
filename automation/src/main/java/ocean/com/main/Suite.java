package ocean.com.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.windows.WindowsDriver;
import ocean.common.ReadData;

/**
 * Represents class execution, Main entry class of execution which extends
 * ReadData Class
 * 
 * @author Mohit Goel
 */
public class Suite extends ReadData {

	/**
	 * Executed as just before Test cases suite setup class to open ocean
	 * application, read OR (Object repository), create reports, attach win APP
	 * driver session to ocean application.
	 *
	 * @param context the new up
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@BeforeSuite
	public void setup(ITestContext context) {
		String desktopPath = System.getProperty("user.home") + "\\Desktop";
		//// Path of ocean application assuming it is installed in user app data
		String oceanApplicationPath = System.getProperty("user.home") + "\\AppData\\Local\\OceanDev\\Ocean.exe";
		// String oceanApplicationPath = "c:\\Windows\\System32\\notepad.exe";
		//// Code to open application and wait till application is stable before
		//// attaching win app session to ocean
		try {
			// Runtime runtime = Runtime.getRuntime();
			/*
			 * try { System.out.println(oceanApplicationPath);
			 * System.out.println("Ocean Application starting");
			 * runtime.exec(oceanApplicationPath); System.out.println(oceanApplicationPath);
			 * System.out.println("Ocean Application Started Waiting to application to boot"
			 * ); Thread.sleep(500000); } catch (Exception e) { // do nothing }
			 */
			//// Read xml, is uses to read object repository and save in local variable
			readXML(currentDir + "\\Repository\\OR.xml");
			//// Setting desired capabilities to help appium understand which platform and
			//// application need to attach session
			DesiredCapabilities appCapabilities = new DesiredCapabilities();
			// Operating system on which execution is being done
			appCapabilities.setCapability("platformName", System.getProperty("os.name"));
			// device on which execution is being done
			appCapabilities.setCapability("deviceName", InetAddress.getLocalHost().getHostName());
			// Ocean Application Path
			appCapabilities.setCapability("app", oceanApplicationPath);
			//// win app driver session attached successfully
			windowsDriver = new WindowsDriver(new URL("http://127.0.0.1:4723/wd/hub"), appCapabilities);
			//// create report folder in running directory
			createReportFolder();
			//// Create reports
			createReport();
		} catch (Exception e) {
			//// Skip execution in case of any error or exceptions
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	/**
	 * Executed after whole suite is executed
	 */
	@AfterSuite
	public void tearDown1(ITestContext context) {
		//// Flush extent report
		extentReport.flush();
	}

	/**
	 * create report and set all parameters like title etc
	 */
	public void createReport() {
		// Test suite hash map
		mapTest = new HashMap<String, ExtentTest>();
		// Test case hash map
		nodeTest = new HashMap<String, ExtentTest>();
		// Status hash map
		statusSheet = new HashMap<Integer, String>();
		// Extent reports reporter
		htmlReporter = new ExtentHtmlReporter(reportPath + "\\" + "Report" + ".html");
		// Title for report
		htmlReporter.config().setDocumentTitle("Ocean Automation Report");
		// Theme for report
		htmlReporter.config().setTheme(Theme.DARK);
		extentReport = new ExtentReports();
		extentReport.config();
		// Attach reporter to operate extent reports
		extentReport.attachReporter(htmlReporter);
	}

	/**
	 * create parent of report, in ocean application parent be Cancellation, Search,
	 * Etc Execute each time before test is executed
	 */
	@BeforeTest
	public void createParent(ITestContext context) {
		// get test case name
		String testName = context.getCurrentXmlTest().getName();
		ExtentTest parentTest = extentReport.createTest(testName);
		mapTest.put(testName, parentTest);
	}

	/**
	 * executed each time before method, to declare an array , where all screenshots
	 * associated with a test case would be saved
	 */
	@BeforeMethod
	public void screenshot() {
		//// Take screenshots and append test execution data
		// declare screenshot array to capture all screenshots in a test case
		screenShots = new ArrayList<String>();
	}

	/**
	 * executed each time before method, to declare an array , where all screenshots
	 * associated with a test case would be saved
	 */
	@BeforeClass
	public void resultsStatus() {
		//// capture all result status and append
		executionResult = new HashMap<String, List<HashMap<String, List<String>>>>();
	}

	/**
	 * Tear Down method used to append test results to parent node
	 */
	@AfterClass
	public void appendResults(ITestContext context) throws Exception {
		try {
			int i = 0;
			String Statuss = "";
			String testName = null;
			for (Entry<String, List<HashMap<String, List<String>>>> letterEntry : executionResult.entrySet()) {
				ExtentTest node1 = mapTest.get(context.getCurrentXmlTest().getName()).createNode(letterEntry.getKey());
				List<HashMap<String, List<String>>> tempList = letterEntry.getValue();
				for (HashMap<String, List<String>> thashMap : tempList) {
					for (Map.Entry<String, List<String>> nameEntry : thashMap.entrySet()) {
						String status = nameEntry.getKey();
						List<String> localss = nameEntry.getValue();
						if (status.toLowerCase().equals("fail")) {
							Statuss = "fail";
							// node1.log(Status.FAIL, "Test Data Failed is " + testName);
							for (String path : localss) {
								addWaterMarkOnImages("Test Data Failed is ", new File(path), new File(path));
								node1.addScreenCaptureFromPath(path);
							}
						}
						//// in case of skip
						else if (status.toLowerCase().equals("skip")) {
							Statuss = "skip";
							for (String path : localss) {
								addWaterMarkOnImages("Test Data skiped is ", new File(path), new File(path));
								node1.addScreenCaptureFromPath(path);
							}
							// node1.log(Status.SKIP, "Test Data skiped is " + testName);
						} //// in case of pass
						else {
							Statuss = "pass";
							ExtentTest node2 = node1.createNode(Integer.toString(i));
							node2.log(Status.PASS, "Test Data passed is " + testName);
							for (String path : localss) {
								addWaterMarkOnImages("Test Data passed is ", new File(path), new File(path));
								node2.addScreenCaptureFromPath(path);
							}
							i++;
							// node1.log(Status.PASS, "Test Data passed is " + testName);
						}
					}
				}
				extentReport.flush();
			}
			//// status sheet adding for report flush
			int size = statusSheet.size() + 1;
			statusSheet.put(size, Statuss);
			extentReport.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Tear Down method used to save test execution data
	 */
	@AfterMethod
	public void tearDown(ITestResult result, ITestContext context) throws IOException {
		try {
			String methodName = result.getName();
			String className = this.getClass().getName();
			Object paramw = (result.getParameters())[0];
			if (paramw instanceof String[]) {
				String[] strArray = (String[]) paramw;
				for (String string : strArray) {
					System.out.println(string);
				}
			}
			className = className.substring(this.getClass().getName().lastIndexOf('.') + 1,
					this.getClass().getName().length());
			takeScreenshot();
			HashMap<String, List<String>> tempMap = new HashMap<String, List<String>>();
			//// in case of failure
			if (result.getStatus() == ITestResult.FAILURE) {
				tempMap.put("fail", screenShots);
			}
			//// in case of skip
			else if (result.getStatus() == ITestResult.SKIP) {
				tempMap.put("skip", screenShots);
			} //// in case of pass
			else {
				tempMap.put("pass", screenShots);
			}
			List<HashMap<String, List<String>>> temp = new ArrayList<HashMap<String, List<String>>>();
			if (executionResult.get(className + "." + methodName) == null) {
				temp.add(tempMap);
				executionResult.put(className + "." + methodName, temp);
			} else {
				temp = executionResult.get(className + "." + methodName);
				temp.add(tempMap);
				executionResult.put(className + "." + methodName, temp);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Common function to take screenshot
	 */
	public void takeScreenshot() {
		try { //// runtime get time of execution
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			//// file path
			File scrFile = ((TakesScreenshot) windowsDriver).getScreenshotAs(OutputType.FILE);
			String abc = screenshotPath + "\\" + timeStamp + ".png";
			FileUtils.copyFile(scrFile, new File(abc), false);
			// addWaterMarkOnImages("TC01", new File(abc), new File(abc));
			//// attach file in path
			screenShots.add(abc);
		} catch (Exception e) { // TODO: handle exception
		}
	}

	/**
	 * Code to append water Mark on images
	 */
	public void addWaterMarkOnImages(String text, File sourceImageFile, File destImageFile) {
		try {
			BufferedImage sourceImage = ImageIO.read(sourceImageFile);
			Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
			AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
			g2d.setComposite(alphaChannel);
			g2d.setColor(Color.BLUE);
			g2d.setFont(new Font("Arial", Font.BOLD, 64));
			FontMetrics fontMetrics = g2d.getFontMetrics();
			Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

			// calculates the coordinate where the String is painted
			int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
			int centerY = sourceImage.getHeight() / 2;

			// paints the textual watermark
			g2d.drawString(text, centerX, centerY);
			ImageIO.write(sourceImage, "png", destImageFile);
			g2d.dispose();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Creates child node of parent test
	 */
	public void createChild(ITestContext context) {
		//// get test case name
		String testName = context.getCurrentXmlTest().getName();
		//// get class name
		String className = this.getClass().getName();
		//// create test case name node
		ExtentTest node1 = mapTest.get(testName).createNode(className);
		//// append in test case map
		nodeTest.put(className, node1);
	}

	/**
	 * Creates the report folder.
	 */
	public void createReportFolder() {
		//// report folder to be created in current runing directory
		String reportFolder = currentDir + "\\Repository\\Reports";
		//// generate time stamp for unique path and name
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String folder = Long.toString(timestamp.getTime());
		//// get report path
		reportPath = reportFolder + "\\" + folder;
		File file = new File(reportPath);
		//// check if directory exist
		if (!file.exists()) {
			file.mkdir();
			//// create screenshot folder separately in report folder
			createscreenshotfolder();
		}
	}

	/**
	 * Creates the report screenshot folder inside report folder under
	 * createReportFolder
	 */
	public void createscreenshotfolder() {
		//// screenshot folder to be created in current runing directory
		//// generate time stamp for unique path and name
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String screenshotFolder = Long.toString(timestamp.getTime());
		screenshotPath = reportPath + "\\" + screenshotFolder;
		File file = new File(screenshotPath);
		//// check if directory exist
		if (!file.exists()) {
			file.mkdir();
		}
	}

}
