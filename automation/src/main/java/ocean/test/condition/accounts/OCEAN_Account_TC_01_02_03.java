package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Cancel_TC_01_02_03 class automates Ocean Cancel module Test Condition
 * 01 and 02 and 03, which holds 9 Test Case; Test Condition Description :
 * Validate Account search on the basis of search parameter given.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Account_TC_01_02_03 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate Account search on the basis of search parameter
	 * given and verify its values
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC01_02_03", dataProviderClass = AccountsDataProvider.class, description = "Validate Account search on the basis of search parameter given.")
	public void AccountSearch(String[] inputArray) throws Exception {
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
		HashMap<Integer, HashMap<String, String>> dbCO = account_getSearchDataCountOnAccountsScreen2(uiSearchData);
		int dbCount = dbCO.size();
		int iterationsCount = 1;
		if (dbCount < 11)
			iterationsCount = dbCount;
		boolean firstCutDataFlag = false;
		boolean fla = false;
		for (int i = 0; i < iterationsCount; i++) {
			String roleId = getRoleId(i);
			String role_Type = getValue("listOfRoleType", i).trim();
			//// save STATUS
			String Status = getValue("listOfStatus", i).trim();
			for (Entry<Integer, HashMap<String, String>> letterEntry : dbCO.entrySet()) {
				HashMap<String, String> sss = letterEntry.getValue();
				fla = sss.containsValue(roleId);
				if (fla == true)
					break;
			}
			if (fla == false)
				throw new Exception("Data not matched");
			// account_getAccountMouduleSearchData
			HashMap<String, String> myDBData = account_getAccountMouduleSearchData(roleId, role_Type, Status);
			HashMap<String, String> gridData = returnSearchResultGridData(i);
			gridData.put("Role_Type", role_Type);
			//// save Role_Id
			gridData.put("Role_Id", roleId);
			//// save STATUS
			gridData.put("STATUS", Status);
			if (myDBData.equals(gridData))
				firstCutDataFlag = true;
			else {
				firstCutDataFlag = false;
				break;
			}
		}
		assertEquals(firstCutDataFlag, true);
	}
}
