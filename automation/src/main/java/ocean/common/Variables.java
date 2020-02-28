package ocean.common;

import java.util.HashMap;
import java.util.List;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.windows.WindowsDriver;

/**
 * The Class Variables. Contains all variables used in execution of automation
 * script
 * 
 * @author Mohit Goel
 */
public class Variables extends Database_Connectivity {

	/** Static variable OR - which holds object repo xml data */
	public static HashMap<String, String[]> oR = new HashMap<String, String[]>();

	/** Static variable htmlReporter - which helps in generating reports */
	public static ExtentHtmlReporter htmlReporter;

	/** Static variable extent - which helps in generating reports */
	public static ExtentReports extentReport;

	/** Static variable windriver - contains driver instance */
	@SuppressWarnings("rawtypes")
	public static WindowsDriver windowsDriver;

	/**
	 * Static variable screenShota - list of all screenshots associated with single
	 * test
	 */
	public static List<String> screenShots;
	/**
	 * Static variable executionResult - append all results for all test data in a
	 * single test case
	 */
	public static HashMap<String, List<HashMap<String, List<String>>>> executionResult;

	/** Static variable mapTest - list of all test parent node */
	public static HashMap<String, ExtentTest> mapTest;

	/** Static variable mapTest - list of all test parent node */
	public static HashMap<String, ExtentTest> resultData;

	/** Static variable nodeTest - list of all test child node */
	public static HashMap<String, ExtentTest> nodeTest;

	/** Static variable statusSheet */
	public static HashMap<Integer, String> statusSheet;

	/** Static variable reportPath - report location */
	public static String reportPath = "";

	/** Static variable ssPath - screenshot folder location */
	public static String screenshotPath = "";

	/** Static variable ssPath - 5 sec delay */
	public static Integer shortWait = 5;

	/** Static variable mediumWait - 10 sec delay */
	public static Integer mediumWait = 10;

	/** Static variable longWait - 15 sec delay */
	public static Integer longWait = 15;

	/** Static variable extraLongWait - 20 sec delay */
	public static Integer extraLongWait = 20;

	/** Static variable hugeDelay - 60 sec delay */
	public static Integer hugeDelay = 60;

	/** Static variable currentDir - current running directory of code */
	public static String currentDir = System.getProperty("user.dir");
}
