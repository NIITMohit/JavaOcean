package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Search Module
 * 
 * @author Mohit Goel
 */
public class SearchDataProvider extends CommonFunctions {

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_12")
	public static Object[][] fetchDataForTC01_12() {
		return dataProvider("Search", "TC_01_12");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC_13_14_15")
	public static Object[][] fetchDataForTC_13_14_15() {
		return dataProvider("Search", "TC_13_14_15");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC_15")
	public static Object[][] fetchDataForTC_15() {
		return dataProvider("Search", "TC_15");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC_16_17_18")
	public static Object[][] fetchDataForTC_16_17_18() {
		return dataProvider("Search", "TC_16_17_18");
	}

	/**
	 * Data provider for fetchSearchData used in Transaction History Search module
	 */
	@DataProvider(name = "fetchDataForTC_25")
	public static Object[][] fetchDataForTC_25() {
		return dataProvider("Search", "TC_25_26_27_28");
	}

	/**
	 * Data provider for fetchSearchData used in Transaction History Search module
	 */
	@DataProvider(name = "fetchDataForTC19_24")
	public static Object[][] fetchDataForTC19_24() {
		return dataProvider("Search", "TC_19_24");
	}

}
