package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_01_02_03 class automates Ocean Cancel module Test Condition
 * 01 and 02 and 03, which holds 9 Test Case; Test Condition Description :
 * Validate Account search on the basis of search parameter given.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_01_02_03 extends CancellationModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test Case
	 * description : Validate Account search on the basis of search parameter given and verify its values
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC01_02_03", dataProviderClass = AccountsDataProvider.class, description = "Validate Account search on the basis of search parameter given.")
	public void searchForAccountOnAccountSearchScreen(String[] inputArray) throws Exception {
		Boolean countFlag = false;
		Boolean dataFlag = false;
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
			uiSearchData = cancellation_getSearchDataCountOnCancellationScreen(appendSearchData(inputArray));
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
		int dbCount = Integer.parseInt(cancellation_getSearchDataCountOnCancellationScreen(uiSearchData).get("count"));
		//// get data count and verify
		if (oceanCount == dbCount)
			countFlag = true;
		//// scroll up to get row 1
		scrollUp();
		//// Compare DB data and match data from database

		//// verify data for a max of 4 or search result count which is less
		for (int i = 0; i < oceanCount; i++) {
			//// Get contract id at row i
			String contractId = getFirstContractId(i);
			//// get data for contract id at row i
			HashMap<String, String> myDBData = cancellation_getCancellationMouduleSearchData(contractId);
			//// get data from db for contract i
			//// save data from UI searched result
			HashMap<String, String> gridData = returnSearchResultGridData(i);
			//// verify both data, must match
			if (myDBData.equals(gridData))
				dataFlag = true;
			else {
				dataFlag = false;
				break;
			}

			if (i > 3) {
				break;
			}
		}
		if (countFlag == true && dataFlag == true)
			assertEquals(true, true);
		else
			assertEquals(false, true);
	}
}
