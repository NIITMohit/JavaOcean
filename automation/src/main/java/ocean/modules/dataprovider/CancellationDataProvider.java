package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Cancellation Module
 * 
 * @author Mohit Goel
 */
public class CancellationDataProvider extends CommonFunctions {

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

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC04")
	public static Object[][] fetchDataForTC04() {
		return new String[][] { { "OnHold" } };
	}
}
