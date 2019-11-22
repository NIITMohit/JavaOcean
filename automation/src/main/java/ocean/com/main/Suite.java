package ocean.com.main;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.windows.WindowsDriver;
import ocean.common.CommonFunctions;
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
	 */
	@SuppressWarnings("rawtypes")
	@BeforeSuite
	public void setup(ITestContext context) {
		//// Path of ocean application assuming it is installed in user app data
		String oceanApplicationPath = System.getProperty("user.home") + "\\AppData\\Local\\OceanQa\\Ocean.exe";
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
	public void tearDown(ITestContext context) {
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
		// declare screenshot array to capture all screenshots in a test case
		screenShots = new ArrayList<String>();
	}

	/**
	 * Tear Down method used to append test results to parent node
	 */
	@AfterMethod
	public void tearDown(ITestResult result, ITestContext context) throws IOException {
		try {
			//// Status of test execution
			String Statuss = "";
			//// Class name of running execution class
			String className = this.getClass().getName();
			className = className.substring(this.getClass().getName().lastIndexOf('.') + 1,
					this.getClass().getName().length());
			//// Create node with name similar to test case and attached with test parent
			ExtentTest node1 = mapTest.get(context.getCurrentXmlTest().getName())
					.createNode(className + "." + result.getName());
			//// Take screenshot of final screen
			takeScreenshot();
			//// in case of failure
			if (result.getStatus() == ITestResult.FAILURE) {
				Statuss = "fail";
				node1.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
			}
			//// in case of skip
			else if (result.getStatus() == ITestResult.SKIP) {
				Statuss = "skip";
				node1.log(Status.SKIP, "TEST CASE FAILED IS " + result.getName());
			} //// in case of pass
			else {
				Statuss = "pass";
				node1.log(Status.PASS, "TEST CASE FAILED IS " + result.getName());
			}
			//// Append all screenshot with the node
			for (String path : screenShots) {
				node1.addScreenCaptureFromPath(path);
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
	 * Common function to take screenshot
	 */
	public void takeScreenshot() {
		try {
			//// runtime get time of execution
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			//// file path
			File scrFile = ((TakesScreenshot) windowsDriver).getScreenshotAs(OutputType.FILE);
			String abc = screenshotPath + "\\" + timeStamp + ".png";
			FileUtils.copyFile(scrFile, new File(abc));
			//// attach file in path
			screenShots.add(abc);
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
