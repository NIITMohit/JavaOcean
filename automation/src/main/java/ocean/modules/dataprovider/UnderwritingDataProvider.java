package ocean.modules.dataprovider;

import org.testng.annotations.DataProvider;

import ocean.common.CommonFunctions;

/**
 * Data provider class, common class consisting all data provider consumed in
 * Underwriting Module
 * 
 * @author Mohit Goel
 */
public class UnderwritingDataProvider extends CommonFunctions {

	/**
	 * Data provider for create remittance in underwriting Module
	 */
	@DataProvider(name = "fetchDataForTC24")
	public static Object[][] fetchDataForTC24() {
		return dataProvider("Underwriting", "TC24");
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
	@DataProvider(name = "fetchDataForTC_05_06")
	public static Object[][] fetchDataForTC_05_06() {
		return dataProvider("Pricing", "TC_05_06");
	}

}
