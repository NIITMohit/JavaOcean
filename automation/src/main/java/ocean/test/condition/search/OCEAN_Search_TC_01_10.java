package ocean.test.condition.search;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.SearchDataProvider;
import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Search_TC_01_10 class automates Ocean Search module Test Condition
 * 01-10, which holds 10 Test Cases; Test Condition Description : Validate
 * Search sheet content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Search_TC_01_10 extends SearchModulePages {
	/**
	 * This function covers all search cases based on search parameters given from
	 * excel
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC01_10", dataProviderClass = SearchDataProvider.class, description = "Search test case,Contract should be Search from the given search sheet under Search Section.")
	public void searchContractWithAnyInputField(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = search_getDataSetforSearch(appendSearchData(inputArray));
		} else {
			uiSearchData = convertDataRemoveStar(inputArray);
		}

		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);

	}
}
