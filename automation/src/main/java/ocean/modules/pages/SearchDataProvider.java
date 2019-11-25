package ocean.modules.pages;

import org.testng.annotations.DataProvider;

import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * test classes
 * 
 * @author Mohit Goel
 */
public class SearchDataProvider {

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_10")
	public static Object[][] fetchDataForTC01_10() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Search.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "TC_01_10");
		return arrayObject;
	}

}
