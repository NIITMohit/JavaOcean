
package ocean.com.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
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

		//// Path of ocean application
		String path = "C:\\Users\\mohit.goel\\AppData\\Local\\OceanDev\\Ocean.exe";

	
		//// Code to open application and wait till application is stable before
		//// attaching win app session to ocean
		try {
			/*
			Runtime runtime = Runtime.getRuntime();
			try {
				System.out.println(path);
				System.out.println("Ocean Application starting");
				runtime.exec(path);
				System.out.println(path);
				System.out.println("Ocean Application Started Waiting to application to boot");
				Thread.sleep(500000);
			} catch (Exception e) {
				// do nothing
			}
			*/

			//// Read xal, is uses to read object repository and save in local variable
			readXML(currentDir + "\\Repository\\OR.xml");

			//// Setting desired capabilities to help appium understand which platform and
			//// application need to attach session
			DesiredCapabilities appCapabilities = new DesiredCapabilities();
			String platform = System.getProperty("os.name");
			System.out.println(platform);
			appCapabilities.setCapability("platformName", platform);
			String device = InetAddress.getLocalHost().getHostName();
			System.out.println(device);
			appCapabilities.setCapability("deviceName", device);
			System.out.println(path);
			appCapabilities.setCapability("app", path);

			//// win app driver sesison attached successfully
			windriver = new WindowsDriver(new URL("http://127.0.0.1:4723/wd/hub"), appCapabilities);

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
		extent.flush();
	}

	/**
	 * create report and set all parameters like title etc
	 */
	public void createReport() {
		mapTest = new HashMap<String, ExtentTest>();
		nodeTest = new HashMap<String, ExtentTest>();
		statusSheet = new HashMap<Integer, String>();
		htmlReporter = new ExtentHtmlReporter(reportPath + "\\" + "Report" + ".html");
		htmlReporter.config().setDocumentTitle("Ocean Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.config();
		extent.attachReporter(htmlReporter);
	}

	/**
	 * create parent of report, in ocean application parent be Cancellation, Search,
	 * Etc Execute each time before test is executed
	 */
	@BeforeTest
	public void createParent(ITestContext context) {
		String testName = context.getCurrentXmlTest().getName();
		ExtentTest test1 = extent.createTest(testName);
		mapTest.put(testName, test1);
	}

	/**
	 * executed each time before method, to declare an array , where all screenshots
	 * associated with a test case would be saved
	 */
	@BeforeMethod
	public void screenshot() {
		screenShota = new ArrayList<String>();
	}

	/**
	 * Tear Down method used to append test results to parent node
	 */
	@AfterMethod
	public void tearDown(ITestResult result, ITestContext context) throws IOException {
		try {
			String Statuss = "";
			String abc = this.getClass().getName();
			abc = abc.substring(this.getClass().getName().lastIndexOf('.') + 1, this.getClass().getName().length());
			//// Create node with name similar to test case and attached with test parent
			ExtentTest node1 = mapTest.get(context.getCurrentXmlTest().getName())
					.createNode(abc + "." + result.getName());
			//// in case of failure
			if (result.getStatus() == ITestResult.FAILURE) {
				Statuss = "fail";
				takeScreenshot();
				node1.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());

				//// in case of skip
			} else if (result.getStatus() == ITestResult.SKIP) {
				Statuss = "skip";
				takeScreenshot();
				node1.log(Status.SKIP, "TEST CASE FAILED IS " + result.getName());

				//// in case of pass
			} else {
				Statuss = "pass";
				takeScreenshot();
				node1.log(Status.PASS, "TEST CASE FAILED IS " + result.getName());

			}

			//// Append all screenshot with the node
			for (String path : screenShota) {
				node1.addScreenCaptureFromPath(path);
			}
			int size = statusSheet.size() + 1;
			statusSheet.put(size, Statuss);
			extent.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Common function to take screenshot
	 */
	public void takeScreenshot() {
		try {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			File scrFile = ((TakesScreenshot) windriver).getScreenshotAs(OutputType.FILE);
			String abc = ssPath + "\\" + timeStamp + ".png";
			FileUtils.copyFile(scrFile, new File(abc));
			screenShota.add(abc);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Update excel post execution of all test data
	 */
	@AfterClass
	public void updateExcel(ITestResult result, ITestContext context) {
		try {

			String fileaname = currentDir + "Repository\\Cancellation - Copy.xlsx";
			FileInputStream inputStream = new FileInputStream(fileaname);
			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);
			for (Integer key : statusSheet.keySet()) {
				String value = statusSheet.get(key);
				System.out.println("Key = " + key + ", Value = " + value);

				int rows = key;
				int columnCount = 2;
				Row row = sheet.getRow(rows);

				Cell cell = row.createCell(columnCount);

				cell.setCellValue((String) value);

			}

			inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(fileaname);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates child node of parent test
	 */
	public void createChild(ITestContext context) {
		String testName = context.getCurrentXmlTest().getName();
		String className = this.getClass().getName();
		ExtentTest node1 = mapTest.get(testName).createNode(className);
		nodeTest.put(className, node1);
	}

	/**
	 * Creates the report folder.
	 */
	public void createReportFolder() {
		String dir = currentDir + "\\Repository\\Reports";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String folder = Long.toString(timestamp.getTime());
		reportPath = dir + "\\" + folder;
		File file = new File(reportPath);
		if (!file.exists()) {
			file.mkdir();
			createscreenshotfolder();
		}
	}

	/**
	 * Creates the report screenshot folder inside report folder under
	 * createReportFolder
	 */
	public void createscreenshotfolder() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String folder = Long.toString(timestamp.getTime());
		ssPath = reportPath + "\\" + folder;
		File file = new File(ssPath);
		if (!file.exists()) {
			file.mkdir();
		}
	}

}
