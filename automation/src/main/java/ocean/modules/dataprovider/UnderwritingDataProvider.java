package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;
import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Underwriting Module
 * 
 * @author Mohit Goel
 */
public class UnderwritingDataProvider extends CommonFunctions {

	/**
	 * Data provider for fetch search data used in PBI 18035 underwriting Module
	 */
	@DataProvider(name = "fetchDataForPBI_18035")
	public static Object[][] fetchDataForPBI_18035() {
		return dataProvider("Underwriting", "18035");
	}

	/**
	 * Data provider for fetch search data used in PBI 17919 underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_94")
	public static Object[][] fetchDataForTC_94() {
		return dataProvider("Underwriting", "TC_94");
	}

	/**
	 * Data provider for fetch search data used in PBI 18489 & 18488 underwriting
	 * Module
	 */
	@DataProvider(name = "fetchDataForTC_86")
	public static Object[][] fetchDataForTC_86() {
		return dataProvider("Underwriting", "TC_86");
	}

	/**
	 * Data provider for testcase 91 in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_91")
	public static Object[][] fetchDataForTC_91() {
		return dataProvider("Underwriting", "TC_91");
	}

	/**
	 * Data provider for testcase 91 in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_96")
	public static Object[][] fetchDataForTC_96() {
		return dataProvider("Underwriting", "TC_96");
	}

	/**
	 * Data provider for account statement screen in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_85")
	public static Object[][] fetchDataForTC_85() {
		return dataProvider("Underwriting", "TC_85");
	}

	/**
	 * Data provider for sep check in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC89")
	public static Object[][] fetchDataForTC89() {
		return dataProvider("Underwriting", "TC_89");
	}

	/**
	 * Data provider for new business grid in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC90")
	public static Object[][] fetchDataForTC90() {
		return dataProvider("Underwriting", "TC_90");
	}

	/**
	 * Data provider for testcase 87 in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_87")
	public static Object[][] fetchDataForTC_87() {
		return dataProvider("Underwriting", "TC_87");
	}

	@DataProvider(name = "fetchDataForTC84")
	public static Object[][] fetchDataForTC84() {
		return dataProvider("Underwriting", "TC84");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC24")
	public static Object[][] fetchDataForTC24() {
		return dataProvider("Underwriting", "TC24");
	}

	/**
	 * Data provider for ACR screen in underwriting Module
	 */
	@DataProvider(name = "fetchDataForPBI_ACR_10722")
	public static Object[][] fetchDataForPBI_ACR_10722() {
		return dataProvider("Underwriting1", "PBI_ACR_10722");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fedassdadtchDataForTC24")
	public static Object[][] fedassdadtchDataForTC24() {
		return dataProvider("Underwriting", "TC24");
	}

	/**
	 * Data provider for business processor in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_55")
	public static Object[][] fetchDataForTC_55() {
		return dataProvider("Underwriting", "TC_55");
	}

	/**
	 * Data provider for business processor in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_50")
	public static Object[][] fetchDataForTC_50() {
		return dataProvider("Underwriting", "TC_50");
	}

	/**
	 * Data provider for business processor to add check in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_57")
	public static Object[][] fetchDataForTC_57() {
		return dataProvider("Underwriting", "TC_57");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC_13")
	public static Object[][] fetchDataForTC_13() {
		return dataProvider("Search", "TC_13");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC_16_17_18")
	public static Object[][] fetchDataForTC_16_17_18() {
		return dataProvider("Search", "TC_16_17_18");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC01_02_03_04")
	public static Object[][] fetchDataForTC01_02_03_04() {
		return dataProvider("Underwriting", "TC01_02_03");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForE2E")
	public static Object[][] fetchDataForE2E() {
		return dataProvider("Underwriting", "E2E");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_05_06")
	public static Object[][] fetchDataForTC_05_06() {
		return dataProvider("Pricing", "TC_05_06");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForSmoke_E2E")
	public static Object[][] fetchDataForSmoke_E2E() {
		return dataProvider("Underwriting", "Smoke_E2E");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForPBI_ADP_10722")
	public static Object[][] fetchDataForPBI_ADP_10722() {
		return dataProvider("Underwriting1", "PBI_ADP_10722");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI11645")
	public static Object[][] fetchDataForPBI11645() {
		return dataProvider("Underwriting", "PBI11645");
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI9135")
	public static Object[][] fetchDataForPBI9135() {
		return ReadData.getRoughData(new String[][] { { "NSF" } });
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI9113")
	public static Object[][] fetchDataForPBI9113() {
		return ReadData.getRoughData(new String[][] { { "Cancelled In Error" } });
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI15059")
	public static Object[][] fetchDataForPBI15059() {
		return ReadData.getRoughData(new String[][] { { "upgrade" }, { "nsf" }, { "rewritten" } });
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI10608")
	public static Object[][] fetchDataForPBI10608() {
		return ReadData.getRoughData(new String[][] { { "Rewritten" } });
	}

	/**
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForPBI15057")
	public static Object[][] fetchDataForPBI15057() {
		return dataProvider("Underwriting1", "TC_551");
	}

	/**
	 * Data provider for testcase 42 in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC42")
	public static Object[][] fetchDataForTC42() {
		return dataProvider("Underwriting", "TC_42");
	}

	/**
	 * Data provider for fetch search data used in PBI 15865 underwriting Module
	 */
	@DataProvider(name = "fetchDataForPBI_15865")
	public static Object[][] fetchDataForPBI_15865() {
		return dataProvider("Underwriting", "PBI_15865");
	}

	/**
	 * Data provider for order of categories in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC92")
	public static Object[][] fetchDataForTC92() {
		return dataProvider("Underwriting", "TC_92");
	}
}
