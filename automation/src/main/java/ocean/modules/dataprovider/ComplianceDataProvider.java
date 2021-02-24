package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Compliance Module
 * 
 * @author Mohit Goel
 */
public class ComplianceDataProvider extends CommonFunctions {

	/**
	 * Data provider for fetchSearchData used in compliance module
	 */
	@DataProvider(name = "fetchDataForTC01_02")
	public static Object[][] fetchDataForTC01_02() {
		return dataProvider("Compliance", "TC_01_02");
	}
	
	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC12_1")
	public static Object[][] fetchDataForTC12_1() {
		return dataProvider("Compliance", "TC_12_1");
	}
	
	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC12_2")
	public static Object[][] fetchDataForTC12_2() {
		return dataProvider("Compliance", "TC_12_2");
	}
	
	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC12_4")
	public static Object[][] fetchDataForTC12_4() {
		return dataProvider("Compliance", "TC_12_4");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC05_06")
	public static Object[][] fetchDataForTC05_06() {
		return dataProvider("Compliance", "TC_05_06");
	}
	
}
