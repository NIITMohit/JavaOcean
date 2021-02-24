package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;
import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * test classes
 * 
 * @author Mohit Goel
 */
public class PricingDataProvider extends CommonFunctions {
	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_05_06")
	public static Object[][] fetchDataForTC_05_06() {
		return dataProvider("Pricing", "TC_05_06");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_02")
	public static Object[][] fetchDataForTC01_02() {
		return dataProvider("Cancellation", "TC_01_02");
	}

	/**
	 * Data provider for fetchDataForTC26 used in tc 26 module
	 */
	@DataProvider(name = "fetchDataForTC_26")
	public static Object[][] fetchDataForTC_26() {
		return dataProvider("Pricing", "TC_26");
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
		return ReadData.getRoughData(new String[][] { { "UnderW" }, { "OnHold" }, { "Returned" }, { "Posted" },
				{ "Active" }, { "Cancelled - complicated" }, { "Void - reverse trans" }, { "Expired" }, { "NIS" } });

	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC08_09")
	public static Object[][] fetchDataForTC08() {
		return ReadData.getRoughData(new String[][] { { "UnderW" }, { "OnHold" }, { "Returned" }, { "Posted" },
				{ "Active" }, { "Cancelled - complicated" }, { "Void - reverse trans" }, { "Expired" }, { "NIS" } });

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
	 * Data provider for fetchDataForTC06 used in tc 24 module
	 */
	@DataProvider(name = "fetchDataForTC24")
	public static Object[][] fetchDataForTC24() {
		return dataProvider("Pricing", "TC_24");

	}

	/**
	 * Data provider for fetchDataForTC06 used in tc 24 module
	 */
	@DataProvider(name = "fetchDataForTC_25")
	public static Object[][] fetchDataForTC_25() {
		return dataProvider("Pricing", "TC_25");

	}
}
