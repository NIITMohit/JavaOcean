package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Accounts Module
 * 
 * @author Mohit Goel
 */
public class AccountsDataProvider extends CommonFunctions {
	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC01_02_03")
	public static Object[][] fetchDataForTC01_02_03() {
		return dataProvider("Accounts", "TC01_02_03");
	}

	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC_04_05_06")
	public static Object[][] fetchDataForTC_04_05_06() {
		return dataProvider("Accounts", "TC_04_05_06");
	}

	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC013")
	public static Object[][] fetchDataForTC013() {
		return new String[][] { { "Dealer", "Active" }, { "Lender", "Active" }, { "Agent", "Active" } };
	}
}
