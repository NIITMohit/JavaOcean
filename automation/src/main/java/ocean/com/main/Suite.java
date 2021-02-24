package ocean.com.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
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
import ocean.common.Variables;

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
	@BeforeSuite(alwaysRun = true)
	public void setup(ITestContext context) throws Exception {
		DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date dateobj1 = new Date();
		Variables.startTime = df1.format(dateobj1);
		//// Path of ocean application assuming it is installed in user app data
		// String oceanApplicationPath =
		//// "C:\\\\Users\\\\mohit.goel\\\\AppData\\\\Local\\\\Apps\\\\2.0\\\\5D4G0G7Q.10N\\\\AZ46M5PM.Q8Y\\\\niit..tion_0000000000000000_0002.0000_8fd57f21f0c2b79e\\\\NIIT.WinChatClient.exe";
		// String oceanApplicationPath = System.getProperty("user.home") +
		//// "\\AppData\\Local\\OceanDev\\Ocean.exe";
		String oceanApplicationPath = "C:\\Users\\mohit.goel\\Desktop\\Debug 07Jan2021\\Ocean.exe";
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
			// getValue("listOfAddress", 1);
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
			for (int i = 0; i < 7; i++) {
				try {
					windowsDriver = new WindowsDriver(new URL("http://127.0.0.1:4723/wd/hub"), appCapabilities);
					windowsDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					break;
				} catch (Exception e) {
					if (i < 6)
						continue;
					else
						throw new Exception(e.toString());
				}
			}
			try {
				Variables.somke = context.getIncludedGroups();
				createReportFolder();
				createReport(context.getSuite().getName());
			} catch (Exception e1) {
				System.out.println(e1.toString());
				System.exit(1);
			}
		} catch (Exception e) {
			//// Skip execution in case of any error or exceptions
			System.out.println(e.toString());
			System.exit(1);
		}

	}

	/**
	 * Executed after whole suite is executed
	 * 
	 * @throws Exception
	 */
	@AfterSuite(alwaysRun = true)
	public void tearDown1(ITestContext context) throws Exception {
		//// Flush extent report
		// extentReport.flush();
		StringBuffer sb = readReport();
		String reportPathName = reportPath + "\\" + "Report" + ".html";
		//// generate report and close
		FileWriter writer = new FileWriter(reportPathName);
		BufferedWriter bw = new BufferedWriter(writer);
		try {
			DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date dateobj1 = new Date();
			String endTime = df1.format(dateobj1);
			String startTime = Variables.startTime;
			//// logic to get time taken
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date d1 = null;
			Date d2 = null;
			d1 = format.parse(startTime);
			d2 = format.parse(endTime);
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			String timetaken = diffDays + " Days " + diffHours + " Hours " + diffMinutes + " Minutes " + diffSeconds
					+ " Seconds";
			String toReplace = "End_Time";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), endTime);
			toReplace = "Total_Time";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), timetaken);
		} finally {
			bw.write(sb.toString());
			bw.close();
		}
		File file = new File(reportPathName);
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
	}

	/**
	 * create report and set all parameters like title etc
	 */
	public void createReport(String suite) throws Exception {
		//// custom HTML Report
		String reportPathName = reportPath + "\\" + "Report" + ".html";
		FileWriter writer = new FileWriter(reportPathName);
		BufferedWriter bw = new BufferedWriter(writer);
		StringBuffer sb = getHTMLData();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dateobj = new Date();
		try {
			//// generate report and close
			sb = sb.replace(sb.indexOf("Suite_Detail"), sb.indexOf("Suite_Detail") + "Suite_Detail".length(),
					suite + " Suite");
			String toReplace = "Execution_Date";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), df.format(dateobj));
			toReplace = "Start_Time";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), Variables.startTime);
		} finally {
			bw.write(sb.toString());
			bw.close();
		}
	}

	/**
	 * this function is to replace test and step with scenario and test case in
	 * final report
	 * 
	 * @throws Exception
	 */
	@AfterTest(alwaysRun = true)
	public void specialAUL(ITestContext context) throws Exception {
		String testName = context.getCurrentXmlTest().getName();
		String path = reportPath + "\\" + testName + "\\" + testName + ".html";
		StringBuffer sb = readReportPath(path);
		FileWriter writer = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(writer);
		try {
			String toReplace = "<div class='left panel-name'>Tests</div>";
			String replace = "<div class='left panel-name'>Scenario</div>";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);
			toReplace = "<div class='left panel-name'>Steps</div>";
			replace = "<div class='left panel-name'>Test Cases</div>";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);

			toReplace = "</span> test(s) passed</span>";
			replace = "</span> Scenario(s) passed</span>";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);

			toReplace = "</span> test(s) failed,";
			replace = "</span> Scenario(s) failed,";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);

			toReplace = "</span> step(s) passed</span>";
			replace = "</span> Test Cases(s) passed</span>";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);

			toReplace = "</span> step(s) failed,";
			replace = "</span> Test Cases(s) failed,";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);

			toReplace = "<div class='card-panel r'>					Tests					<div class='panel-lead'>";
			replace = "<div class='card-panel r'>					Scenario					<div class='panel-lead'>";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);
			//// Break string buffer in 2 parts and attach
			toReplace = "<div class='card-panel r'>					Steps					<div class='panel-lead'>";
			replace = "<div class='card-panel r'>					Test Cases					<div class='panel-lead'>";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(), replace);
			//// replace all screenshots
			String toReplaced = reportPath + "\\" + testName + "\\";
			do {
				sb = sb.replace(sb.indexOf(toReplaced), sb.indexOf(toReplaced) + toReplaced.length(), "");
			} while (sb.indexOf(toReplaced) != -1);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			bw.write(sb.toString());
			bw.close();
		}
	}

	/**
	 * create parent of report, in ocean application parent be Cancellation, Search,
	 * Etc Execute each time before test is executed
	 * 
	 * @throws Exception
	 */
	@BeforeTest(alwaysRun = true)
	public void createParent(ITestContext context) throws Exception {
		Variables.pass = 0;
		Variables.skip = 0;
		Variables.fail = 0;
		String testName = context.getCurrentXmlTest().getName();
		//// create folder for that module
		File file = new File(reportPath + "\\" + testName);
		//// check if directory exist
		file.mkdir();
		createscreenshotfolder(reportPath + "\\" + testName);
		//// with report create log file
		try {
			fh = new FileHandler(reportPath + "\\" + testName + "\\" + testName + ".log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.info("Logging Started");
		} catch (Exception e) {
			// TODO: handle exception
		}
		htmlReporter = new ExtentHtmlReporter(reportPath + "\\" + testName + "\\" + testName + ".html");
		// Title for report
		htmlReporter.config().setDocumentTitle(testName + " Automation Report");
		// Theme for report
		htmlReporter.config().setTheme(Theme.DARK);
		extentReport = new ExtentReports();
		extentReport.config();
		// Attach reporter to operate extent reports
		extentReport.attachReporter(htmlReporter);
		extentReport.flush();
		StringBuffer sb = readReport();
		String reportPathName = reportPath + "\\" + "Report" + ".html";
		//// generate report and close
		FileWriter writer = new FileWriter(reportPathName);
		BufferedWriter bw = new BufferedWriter(writer);
		Variables.auto++;
		try {
			String toReplace = "<tr id = \"" + testName + "\" style=\"display: none;\">";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(),
					"<tr id = \"" + testName + "\">");
			toReplace = testName + "_S.No";
			sb = sb.replace(sb.indexOf(toReplace), sb.indexOf(toReplace) + toReplace.length(),
					Integer.toString(Variables.auto));
		} finally {
			bw.write(sb.toString());
			bw.close();
		}
	}

	/**
	 * executed each time before method, to declare an array , where all screenshots
	 * associated with a test case would be saved
	 */
	@BeforeMethod(alwaysRun = true)
	public void screenshot(ITestResult result, ITestContext context) {
		screenShots = new ArrayList<String>();

	}

	/**
	 * executed each time before method, to declare an array , where all screenshots
	 * associated with a test case would be saved
	 */
	@BeforeClass(alwaysRun = true)
	public void resultsStatus(ITestContext context) {
		parentNode = extentReport
				.createTest(this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1));
		logger.info("========================"
				+ this.getClass().getName().substring(this.getClass().getName().lastIndexOf(".") + 1)
				+ "==========================");
	}

	/**
	 * Tear Down method used to save test execution data
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result, ITestContext context) throws IOException {
		try {
			StringBuffer sb = readReport();
			String testName = context.getCurrentXmlTest().getName();
			String replacementpass = "<td id = " + testName + "_Pass>" + Integer.toString(Variables.pass) + "</td>";
			String replacementfail = "<td id = " + testName + "_Fail>" + Integer.toString(Variables.fail) + "</td>";
			String replacementskip = "<td id = " + testName + "_Skip>" + Integer.toString(Variables.skip) + "</td>";
			String replacementTot = "<td id = " + testName + "_Total>"
					+ Integer.toString(Variables.skip + Variables.pass + Variables.fail) + "</td>";
			String reportPathName = reportPath + "\\" + "Report" + ".html";
			//// generate report and close
			FileWriter writer = new FileWriter(reportPathName);
			BufferedWriter bw = new BufferedWriter(writer);
			String methodName = result.getName();
			String inputParamaters = "";
			try {
				Object paramw = (result.getParameters())[0];
				if (paramw instanceof String[]) {
					String[] strArray = (String[]) paramw;
					for (String string : strArray) {
						if (string.trim().length() > 1)
							inputParamaters = string + "," + inputParamaters;
					}
				}
			} catch (Exception e) {
				inputParamaters = "No input Params Required from Excel";
			}
			String nodeName = methodName;
			if (!inputParamaters.equalsIgnoreCase("No input Params Required from Excel"))
				nodeName = methodName + "." + inputParamaters;
			ExtentTest node = parentNode.createNode(nodeName);
			takeScreenshot();
			//// in case of failure
			if (result.getStatus() == ITestResult.FAILURE) {
				Variables.fail++;
				node.log(Status.FAIL, "Test Data failed is ");
				for (String path : screenShots) {
					addWaterMarkOnImages("Test Data failed is ", new File(path), new File(path));
					node.addScreenCaptureFromPath(path);
				}
				for (int i = 0; i < 2; i++) {
					try {
						click("clickOK");
					} catch (Exception e) {
						break;
						// TODO: handle exception
					}
				}

				for (int i = 0; i < 2; i++) {
					try {
						click("closeextrawindow");
					} catch (Exception e) {
						break;
						// TODO: handle exception
					}
				}
			}
			//// in case of skip
			else if (result.getStatus() == ITestResult.SKIP) {
				Variables.skip++;
				node.log(Status.SKIP, "Test Data Skip is ");
				for (String path : screenShots) {
					addWaterMarkOnImages("Test Data skip is ", new File(path), new File(path));
					node.addScreenCaptureFromPath(path);
				}
			} //// in case of pass
			else {
				Variables.pass++;
				node.log(Status.PASS, "Test Data Passed is ");
				for (String path : screenShots) {
					addWaterMarkOnImages("Test Data passed is ", new File(path), new File(path));
					node.addScreenCaptureFromPath(path);
				}
			}
			try {
				click("clicksearchinBar");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("clickAccountsTab");
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				//// generate report and close
				String replacement = "<td id = " + testName + "_Total>"
						+ Integer.toString(Variables.pass + Variables.fail + Variables.skip) + "</td>";
				sb = sb.replace(sb.indexOf(replacementTot), sb.indexOf(replacementTot) + replacementTot.length(),
						replacement);
				replacement = "<td id = " + testName + "_Fail>" + Integer.toString(Variables.fail) + "</td>";
				sb = sb.replace(sb.indexOf(replacementfail), sb.indexOf(replacementfail) + replacementfail.length(),
						replacement);
				replacement = "<td id = " + testName + "_Pass>" + Integer.toString(Variables.pass) + "</td>";
				sb = sb.replace(sb.indexOf(replacementpass), sb.indexOf(replacementpass) + replacementpass.length(),
						replacement);
				replacement = "<td id = " + testName + "_Skip>" + Integer.toString(Variables.skip) + "</td>";
				sb = sb.replace(sb.indexOf(replacementskip), sb.indexOf(replacementskip) + replacementskip.length(),
						replacement);
			} finally {
				bw.write(sb.toString());
				bw.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			extentReport.flush();
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
		}
	}

	/**
	 * Creates the report screenshot folder inside report folder under
	 * createReportFolder
	 */
	public void createscreenshotfolder(String path) {
		//// screenshot folder to be created in current runing directory
		//// generate time stamp for unique path and name
		String screenshotFolder = "screenshots";
		screenshotPath = path + "\\" + screenshotFolder;
		File file = new File(screenshotPath);
		//// check if directory exist
		if (!file.exists()) {
			file.mkdir();
		}
	}
}
