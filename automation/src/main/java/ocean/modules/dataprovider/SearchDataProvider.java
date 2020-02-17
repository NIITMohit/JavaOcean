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
	@DataProvider(name = "fetchDataForTC01_10")
	public static Object[][] fetchDataForTC01_10() {
		return dataProvider("Search", "TC_01_10");
	}

}
