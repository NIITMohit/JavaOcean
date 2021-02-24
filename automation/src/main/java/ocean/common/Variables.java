package ocean.common;

import java.util.HashMap;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
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

	public static HashMap<String, Integer> pricingExecution;

	public static HashMap<String, Integer> accountExecution;
	/**
	 * logger object
	 */
	public static Logger logger = Logger.getLogger("ExecutionLog");

	/**
	 * FileHandler object
	 */
	public static FileHandler fh;

	/**
	 * Static variable screenShota - list of all screenshots associated with single
	 * test
	 */
	public static List<String> screenShots;

	/**
	 * Static variable startTime - start time of execution test
	 */
	public static String startTime;

	/** Static variable mapTest - list of all test parent node */
	public static HashMap<String, ExtentTest> mapTest;

	/** Static variable auto - numbering of test modules */
	public static int auto = 0;

	/** Static variable pass - pass count */
	public static int pass;

	/** Static variable fail - fail count */
	public static int fail;

	/** Static variable auto - skip count */
	public static int skip;

	/** Static variable to decide run mode */
	public static String[] runMode;

	/** Static variable statusSheet */
	public static ExtentTest parentNode;

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

	/** Static variable hugeDelay - 60 sec delay */
	public static String[] somke = null;

	/** Static variable hugeDelay - 60 sec delay */
	public static Integer autoIncrement = 1;
	public static ExtentTest node;
	/** Static variable currentDir - current running directory of code */
	public static String currentDir = System.getProperty("user.dir");

	public static enum CarDetails {
		F150
	}
}
