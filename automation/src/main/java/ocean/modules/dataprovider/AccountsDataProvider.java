package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;
import ocean.common.ReadData;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Accounts Module
 * 
 * @author Mohit Goel
 */
public class AccountsDataProvider extends CommonFunctions {
	/**
	 * Data provider for fetchSearchData used in account module
	 * fetchDataForTC39
	 */
	@DataProvider(name = "fetchDataForTC01_02_03")
	public static Object[][] fetchDataForTC01_02_03() {
		return dataProvider("Accounts", "TC01_02_03");
	}
	
	@DataProvider(name = "fetchDataForTC39")
	public static Object[][] fetchDataForTC39() {
		return dataProvider("Accounts", "TC_39");
	}

	@DataProvider(name = "fetchDataForTC_40")
	public static Object[][] fetchDataForTC_40() {
		return dataProvider("Accounts", "TC_40");
	}

	/**
	 * Data provider for fetchData used in PBI16174
	 */

	@DataProvider(name = "fetchDataForPBI16174")
	public static Object[][] fetchDataForPBI16174() {
		return dataProvider("Accounts", "PBI16174");
	}

	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC_04_05")
	public static Object[][] fetchDataForTC_04_05() {
		return dataProvider("Accounts", "TC_04_05");
	}

	/**
	 * Data provider for check the priceSheet on underwriting
	 */
	@DataProvider(name = "fetchDataForTC_05_06")
	public static Object[][] fetchDataForTC_05_06() {
		return dataProvider("Pricing", "TC_05_06");
	}

	/**
	 * Data provider for fetchData used in Limited PriceSheet
	 */

	@DataProvider(name = "fetchDataForTC_30")
	public static Object[][] fetchDataForTC_30() {
		return dataProvider("Accounts", "TC_30");
	}

	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC013")
	public static Object[][] fetchDataForTC013() {
		return ReadData
				.getRoughData(new String[][] { { "Dealer", "Active" }, { "Lender", "Active" }, { "Agent", "Active" } });

	}

	/**
	 * Data provider for fetchData used on Account Search and add the exceptions On
	 * the excel data
	 */
	@DataProvider(name = "fetchDataForTC_17_18_19")
	public static Object[][] fetchDataForTC_17_18_19() {
		return dataProvider("Accounts", "TC_17_18_19");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC_23_24_25")
	public static Object[][] fetchDataForTC_23_24_25() {
		return dataProvider("Accounts", "TC_23_24_25");
	}

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC27")
	public static Object[][] fetchDataForTC27() {
		return dataProvider("Accounts", "TC27");
	}

	/**
	 * Data provider for fetchData used on Account Search and add the exceptions On
	 * the excel data
	 */
	@DataProvider(name = "fetchDataForTC_16")
	public static Object[][] fetchDataForTC_16() {
		return dataProvider("Accounts", "TC_16");
	}

	/**
	 * Data provider for fetchData used on Account Search and add the exceptions On
	 * the excel data
	 */
	@DataProvider(name = "fetchDataForTC_08_09_10_11_12")
	public static Object[][] fetchDataForTC_08_09_10_11_12() {
		return dataProvider("Accounts", "TC_08_09_10_11_12");
	}

	/**
	 * Data provider for fetchSearchData used in account module
	 */
	@DataProvider(name = "fetchDataForTC_06")
	public static Object[][] fetchDataForTC_06() {
		return dataProvider("Accounts", "TC_06");
	}

	/**
	 * Data provider for fetchData used in TC_31_32
	 */

	@DataProvider(name = "fetchDataForTC_31_32")
	public static Object[][] fetchDataForTC_31_32() {
		return dataProvider("Accounts", "TC_31_32");
	}

	/**
	 * Data provider for fetchData used in TC_29
	 */

	@DataProvider(name = "fetchDataForTC_29")
	public static Object[][] fetchDataForTC_29() {
		return dataProvider("Accounts", "TC_29");
	}

	/**
	 * Data provider for PBI 16415
	 */
	@DataProvider(name = "fetchDataForPBI16415")
	public static Object[][] fetchDataForPBI16415() {
		return ReadData.getRoughData(new String[][] { { "200" }, { "0" } });

	}
}
