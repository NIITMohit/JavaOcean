package ocean.common;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.windows.WindowsDriver;

public class Variables {

	public static HashMap<String, String[]> oR = new HashMap<String, String[]>();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static AppiumDriverLocalService service;
	@SuppressWarnings("rawtypes")
	public static WindowsDriver windriver;
	// public static WiniumDriver windriver;
	public static List<String> screenShota ; 
	public static HashMap<String, ExtentTest> mapTest;
	public static HashMap<String, ExtentTest> nodeTest;
	public static HashMap<Integer, String> statusSheet;
	public static String oceanFile = "";
	public static String reportPath = "";
	public static String ssPath = "";
	public static Integer shortWait = 5;
	public static Integer mediumWait = 10;
	public static Integer longWait = 15;
	public static Integer extraLongWait = 20;
	public static Integer hugeDelay = 60;
	public static WebDriver driver;
	public static String currentDir = System.getProperty("user.dir");
}
