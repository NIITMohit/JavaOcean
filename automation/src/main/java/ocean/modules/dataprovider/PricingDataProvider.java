package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;

/**
 * Data provider class, common class consisting all data provider consumed in
 * test classes
 * 
 * @author Mohit Goel
 */
public class PricingDataProvider extends CommonFunctions {

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_02")
	public static Object[][] fetchDataForTC01_02() {
		return dataProvider("Cancellation", "TC_01_02");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC16_17")
	public static Object[][] fetchDataForTC16_17() {
		return dataProvider("Pricing", "TC16_17");
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
		return dataProvider("Cancellation", "TC_06");
	}

	/**
	 * Data provider for fetchDataForTC06 used in tc 06 module
	 */
	@DataProvider(name = "fetchPriceSheet")
	public static Object[][] fetchPriceSheet() {
		return dataProvider("Cancellation", "PriceSheet");
	}
}
