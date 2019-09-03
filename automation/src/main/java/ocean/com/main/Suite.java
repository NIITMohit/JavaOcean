package ocean.com.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.sql.Timestamp;
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
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.windows.WindowsDriver;
import ocean.common.ReadData;

public class Suite extends ReadData {

	@SuppressWarnings("rawtypes")
	@BeforeSuite
	public void setup(ITestContext context) {
		try {
			readXML(currentDir + "\\Repository\\OR.xml");
			DesiredCapabilities appCapabilities = new DesiredCapabilities();
			String platform = System.getProperty("os.name");
			System.out.println(platform);
			appCapabilities.setCapability("platformName", platform);
			String device  =  InetAddress.getLocalHost().getHostName();
			System.out.println(device);
			appCapabilities.setCapability("deviceName", device);
			//String path = System.getenv("LOCALAPPDATA") + "\\OceanDev\\Ocean.exe";
			String path = "C:\\Users\\mohit.goel\\AppData\\Local\\OceanDev\\Ocean.exe";
			System.out.println(path);
			appCapabilities.setCapability("app", path);
			// appCapabilities.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
			windriver = new WindowsDriver(new URL("http://127.0.0.1:4723/wd/hub"), appCapabilities);
			createReportFolder();
			createReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block;
			System.out.println(e.toString());
			System.exit(1);

		}
	}

	@AfterSuite
	public void tearDown(ITestContext context) {
		extent.flush();
	}

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

	@BeforeTest
	public void createParent(ITestContext context) {
		String testName = context.getCurrentXmlTest().getName();
		ExtentTest test1 = extent.createTest(testName);
		mapTest.put(testName, test1);
	}

	@AfterMethod
	public void tearDown(ITestResult result, ITestContext context) throws IOException {
		try {
			String Statuss = "";
			String instac = result.getInstanceName();
			String abc = this.getClass().getName();
			abc = abc.substring(this.getClass().getName().lastIndexOf('.') + 1, this.getClass().getName().length());
			ExtentTest node1 = mapTest.get(context.getCurrentXmlTest().getName())
					.createNode(abc + "." + result.getName());
			// Object[] inputArgs = result.getParameters();
			// String data = inputArgs.toString();
			if (result.getStatus() == ITestResult.FAILURE) {
				Statuss = "fail";
				File scrFile = ((TakesScreenshot) windriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(reportPath + "\\screen1.png"));

				node1.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
				node1.addScreenCaptureFromPath(reportPath + "\\screen1.png");

				// nodeTest.get(instac).log(Status.FAIL, "TEST CASE FAILED IS " +
				// result.getName());
				// nodeTest.get(instac).addScreenCaptureFromPath(reportPath + "\\screen1.png");
			} else if (result.getStatus() == ITestResult.SKIP) {

				Statuss = "skip";
				File scrFile = ((TakesScreenshot) windriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(reportPath + "\\screen2.png"));

				node1.log(Status.SKIP, "TEST CASE FAILED IS " + result.getName());
				node1.addScreenCaptureFromPath(reportPath + "\\screen2.png");

				// nodeTest.get(instac).addScreenCaptureFromPath(reportPath + "\\screen2.png");
				// nodeTest.get(instac).log(Status.SKIP, "TEST CASE Skiped IS " +
				// result.getName());

			} else {

				Statuss = "pass";
				File scrFile = ((TakesScreenshot) windriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(reportPath + "\\screen3.png"));

				node1.log(Status.PASS, "TEST CASE FAILED IS " + result.getName());
				node1.addScreenCaptureFromPath(reportPath + "\\screen3.png");

				// nodeTest.get(instac).addScreenCaptureFromPath(reportPath + "\\screen3.png");
				// nodeTest.get(instac).log(Status.PASS, "Test Case PASSED IS " +
				// result.getName());

			}
			int size = statusSheet.size() + 1;
			statusSheet.put(size, Statuss);
			extent.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@AfterClass
	public void updateExcel() {
		try {
			String fileaname = "D:\\Office\\Ocean Win App POC\\Ocean Automation\\automation\\Repository\\Cancellation - Copy.xlsx";
			FileInputStream inputStream = new FileInputStream(fileaname);
			Workbook workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheetAt(0);

			HashMap<Integer, String> abc = statusSheet;

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

	public void createChild(ITestContext context) {
		String testName = context.getCurrentXmlTest().getName();
		String className = this.getClass().getName();
		ExtentTest node1 = mapTest.get(testName).createNode(className);
		nodeTest.put(className, node1);
	}

	public void createReportFolder() {
		String dir = currentDir + "\\Repository\\Reports";
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String folder = Long.toString(timestamp.getTime());
		reportPath = dir + "\\" + folder;
		File file = new File(reportPath);
		if (!file.exists()) {
			file.mkdir();
		}
	}

}
