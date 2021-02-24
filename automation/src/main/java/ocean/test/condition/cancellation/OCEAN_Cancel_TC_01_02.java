package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_01_02 class automates Ocean Cancel module Test Condition 01
 * and 02, which holds 6 Test Case; Test Condition Description : Validate
 * contract search for cancellation on the basis of search parameter given and
 * Validate searched data from DB.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_01_02 extends CancellationModulePages {
	/**
	 * This function automates all test cases for test condition 01; Test Case
	 * description : Validate contract search for cancellation on the basis of
	 * contract number, VIN, First Name, Last Name
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke", "smoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC01_02", dataProviderClass = CancellationDataProvider.class, description = "Validate contract search for cancellation on the basis of contract number, VIN, First Name, Last Name and verify result")

	public void searchForContractOnCancelScreen(String[] inputArray) throws Exception {
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
			uiSearchData = cancellation_Search(cancellation_Search_appendSearchData(inputArray));
		} else {
			uiSearchData = cancellation_Search_convertDataRemoveStar(inputArray);
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
			for (Map.Entry<String, String> entry : gridData.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null)
					gridData.put(key, "");
			}
			for (Map.Entry<String, String> entry : myDBData.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null)
					myDBData.put(key, "");
			}
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
		assertEquals(countFlag == dataFlag == true, true);
	}
}
