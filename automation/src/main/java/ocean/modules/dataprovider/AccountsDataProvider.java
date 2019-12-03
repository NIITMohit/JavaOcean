package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Accounts Module
 * 
 * @author Mohit Goel
 */
public class AccountsDataProvider {
	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_02_03")
	public static Object[][] fetchDataForTC01_02_03() {
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Accounts.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "TC01_02_03");
		return arrayObject;
	}
}
