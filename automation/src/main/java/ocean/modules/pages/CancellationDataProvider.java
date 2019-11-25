package ocean.modules.pages;

import org.testng.annotations.DataProvider;

import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * test classes
 * 
 * @author Mohit Goel
 */
public class CancellationDataProvider {

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_02")
	public static Object[][] fetchDataForTC01_02() {
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "TC_01_02");
		return arrayObject;
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC03")
	public static Object[][] fetchDataForTC03() {
		return new String[][] { { "UnderW" }, { "OnHold" }, { "Return" }, { "Pending" }, { "Processed" },
				{ "Cancelled" }, { "Purged" }, { "Reference" }, { "NIS" } };
	}

	/**
	 * Data provider for fetchDataForTC06 used in tc 06 module
	 */
	@DataProvider(name = "fetchDataForTC06")
	public static Object[][] fetchDataForTC06() {
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "TC_06");
		return arrayObject;
	}
}