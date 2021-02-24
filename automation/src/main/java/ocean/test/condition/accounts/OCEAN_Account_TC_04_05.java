package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_TC_04_05_06 class automates Ocean Cancel module Test Condition
 * 04 and 05 and 06, which holds 18 Test Case; Test Condition Description
 * :Validate all information related with Accounts on Account details screen,
 * actually fetched from sugar
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Account_TC_04_05 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate all information related with Accounts on Account
	 * details screen,actually fetched from sugar
	 * 
	 */
	@Test(priority = 3, groups = { "regression", "smoke", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_04_05", dataProviderClass = AccountsDataProvider.class, description = "Validate all information related with Accounts on Account details screen,actually fetched from sugar")
	public void validateAccountDetailsFromSugar(String[] inputArray) throws Exception {
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
		//// click display button to load account details page
		//// get role id of first searched data
		String roleId = getRoleId(0);
		String roleType = getRoleType(0);
		String roleStatus = getRoleStatus(0);
		clickDisplayButton(0);
		waitForSomeTime(25);
		//// verify accountInfo Details
		boolean accountInfo = verifyAccountInfoOnAccountDetailScreen(roleId, roleType, roleStatus);
		//// verify addressInfo Details
		boolean addressInfo = verifyAddressInfoOnAccountDetailScreen(roleId, roleType, roleStatus);
		if (accountInfo == addressInfo == true)
			assertEquals(addressInfo, true);
		else
			assertEquals(false, true);
	}

}
