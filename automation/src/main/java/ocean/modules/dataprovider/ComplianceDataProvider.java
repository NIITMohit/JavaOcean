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
	 * Data provider for fetchSearchData used in search module
	 */
	@DataProvider(name = "fetchDataForTC01_02")
	public static Object[][] fetchDataForTC01_02() {
		return dataProvider("Cancellation", "TC_01_02");
	}

}
