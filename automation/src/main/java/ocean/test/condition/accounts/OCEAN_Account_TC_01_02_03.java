package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Cancel_TC_01_02_03 class automates Ocean Cancel module Test Condition
 * 01 and 02 and 03, which holds 9 Test Case; Test Condition Description :
 * Validate Account search on the basis of search parameter given.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Account_TC_01_02_03 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate Account search on the basis of search parameter
	 * given and verify its values
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC01_02_03", dataProviderClass = AccountsDataProvider.class, description = "Validate Account search on the basis of search parameter given.")
	public void TC_04_AccountSeacrhByAddress(String[] inputArray) throws Exception {
		if(inputArray[8].equals("1")) {
			
		}
		else{
			//// dp
		}
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		//// Navigate to mail service tab
		goToAccountsTab();
		goToAccountsSearchTab();
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hash map from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = accounts_getDataSetforSearch(account_appendSearchData(inputArray));
		} else {
			uiSearchData = account_convertDataRemoveStar(inputArray);
		}
		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		///// get db count to have actual iterations
		int dbCount = Integer.parseInt(account_getSearchDataCountOnAccountsScreen(uiSearchData).get("count"));
		int iterationsCount = 10;
		if (dbCount < 11)
			iterationsCount = dbCount;
		boolean firstCutDataFlag = false;
		boolean secondCutDataFlag = false;
		for (int i = 0; i < iterationsCount; i++) {
			String roleId = getRoleId(i);
			// account_getAccountMouduleSearchData
			HashMap<String, String> myDBData = account_getAccountMouduleSearchData(roleId);
			HashMap<String, String> gridData = returnSearchResultGridData(i);
			if (myDBData.equals(gridData))
				firstCutDataFlag = true;
			else {
				firstCutDataFlag = false;
				break;
			}
		}
		if (dbCount > 10) {
			//// click sort on role id
			String roleId1 = getRoleId(0);
			click("clickSortRoleId");
			String roleId2 = getRoleId(0);
			if (roleId1.equals(roleId2))
				click("clickSortRoleId");
			//// navigate to top
			scrollUp();
			//// get top role id
			String roleId = getRoleId(0);
			HashMap<String, String> myDBData = account_getAccountMouduleSearchData(roleId);
			HashMap<String, String> gridData = returnSearchResultGridData(0);
			//// compare final cut
			if (myDBData.equals(gridData))
				secondCutDataFlag = true;
			else
				secondCutDataFlag = false;
		}
		if (firstCutDataFlag == secondCutDataFlag && firstCutDataFlag == true)
			assertEquals(true, true);
		else
			assertEquals(true, false);

	}
}
