package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.common.DataProviderClass;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_01 class automates Ocean Cancel module Test Condition 01,
 * which holds 5 Test Case; Test Condition Description : Validate contract
 * search for cancellation on the basis of search parameter given.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_01 extends cancellationModulePages {
	/**
	 * This function automates all test cases for test condition 01; Test Case
	 * description : Validate contract search for cancellation on the basis of
	 * contract number, VIN, First Name, Last Name
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchCancelSearchData", dataProviderClass = DataProviderClass.class, description = "Validate contract search for cancellation on the basis of contract number, VIN, First Name, Last Name")
	public void searchForContractOnCancelScreen(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		//// Navigate to mail service tab
		goToCancellationTab();
		goToMailServiceTab();
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hash map from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = getDataSetforSearch(appendSearchData(inputArray));
		} else {
			uiSearchData = convertDataRemoveStar(inputArray);
		}

		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		//// get number of search result from ocean, actual search result from input
		//// parameters
		int oceanCount = getSearchResultCount();
		//// get number of search result from database, actual result from input
		//// parameters
		int dbCount = Integer.parseInt(getSearchDataCountOnCancellationScreen(uiSearchData).get("count"));
		//// get data count and verify
		assertEquals(oceanCount, dbCount);
	}
}
