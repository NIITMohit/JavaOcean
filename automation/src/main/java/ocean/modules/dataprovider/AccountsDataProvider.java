package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * test classes
 * 
 * @author Mohit Goel
 */
public class AccountsDataProvider {

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
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC08_09")
	public static Object[][] fetchDataForTC08() {
		return new String[][] { { "Cancelled" } };
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
	
	/**
	 * Data provider for fetchDataForTC06 used in tc 06 module
	 */
	@DataProvider(name = "fetchPriceSheet")
	public static Object[][] fetchPriceSheet() {
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "PriceSheet");
		return arrayObject;
	}
}
