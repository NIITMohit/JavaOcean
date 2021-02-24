package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;
import ocean.common.ReadData;

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
	 * Data provider for fetchDataForPBI15863 used in tc OCEAN_Cancel_TC_42
	 */
	@DataProvider(name = "fetchDataForPBI15863")
	public static Object[][] fetchDataForPBI15863() {
		return ReadData.getRoughData(new String[][] { { "authorize" }, { "hold" }, { "denied" } });

	}

	/**
	 * Data provider for fetchCancellationData used in cancellation TC-12 module
	 */
	@DataProvider(name = "fetchDataForTC11")
	public static Object[][] fetchDataForTC11() {
		return dataProvider("Cancellation", "TC_11");
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
	public static Object[][] fetchDataForTC08_09() {
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
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC_24")
	public static Object[][] fetchDataForTC_24() {
		return ReadData.getRoughData(new String[][] { { "UnderW" }, { "OnHold" }, { "Returned" }, { "Posted" },
				{ "Active" }, { "Cancelled - complicated" }, { "Void - reverse trans" }, { "Expired" }, { "NIS" } });
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC04")
	public static Object[][] fetchDataForTC04() {
		return ReadData.getRoughData(new String[][] { { "Authorized" }, { "Denied" }, { "OnHold" }, { "Processed" },
				{ "Quote" }, { "Reinstated" } });

	}

	@DataProvider(name = "fetchDataForTC22")
	public static Object[][] fetchDataForTC22() {
		return dataProvider("ContractID", "WarningText");
	}

	/**
	 * Data provider for fetchCancellationData used in cancellation TC-25 module
	 */
	@DataProvider(name = "fetchDataForTC_25")
	public static Object[][] fetchDataForTC_25() {
		return dataProvider("Cancellation", "TC_25");
	}

	/**
	 * Data provider for fetchCancellationData used in cancellation TC-26 module
	 */
	@DataProvider(name = "fetchDataForTC26")
	public static Object[][] fetchDataForTC26() {
		return dataProvider("Cancellation", "TC_26");
	}

	/**
	 * Data provider for fetchCancellationData used in cancellation TC-27 module
	 */
	@DataProvider(name = "fetchDataForTC27")
	public static Object[][] fetchDataForTC27() {
		return dataProvider("Cancellation", "TC_27");
	}

	/**
	 * Data provider for fetchCancellationData used in cancellation TC-14 module
	 */
	@DataProvider(name = "fetchDataForTC14")
	public static Object[][] fetchDataForTC14() {
		return dataProvider("Cancellation", "TC_14");
	}

	/**
	 * Data provider for fetchSearchData used in tc 34
	 */
	@DataProvider(name = "fetchDataForTC_34")
	public static Object[][] fetchDataForTC_34() {
		return dataProvider("Cancellation", "RuleType");
	}

	@DataProvider(name = "fetchDataForPBI9213")
	public static Object[][] fetchDataForPBI9213() {
		return ReadData.getRoughData(new String[][] { { "Quote" }, { "Reinstated" } });
	}

	/**
	 * Data provider for fetchSearchData used in tc 38
	 */
	@DataProvider(name = "fetchDataForTC_38")
	public static Object[][] fetchDataForTC_38() {
		return dataProvider("Cancellation", "TC_38");
	}

	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC_22")
	public static Object[][] fetchDataForTC_22() {
		return dataProvider("Cancellation", "TC_22");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI_16414")
	public static Object[][] fetchDataForPBI_16414() {
		return dataProvider("Cancellation", "PBI-16414");
	}
}
