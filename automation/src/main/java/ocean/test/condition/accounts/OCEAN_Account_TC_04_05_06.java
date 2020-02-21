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
 */
public class OCEAN_Account_TC_04_05_06 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate all information related with Accounts on Account
	 * details screen,actually fetched from sugar
	 * 
	 */
	@Test(priority = 3, groups = "regression", dataProvider = "fetchDataForTC_04_05_06", dataProviderClass = AccountsDataProvider.class, description = "Validate all information related with Accounts on Account details screen,actually fetched from sugar")
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
		clickDisplayButton(0);
		//// get role id of first searched data
		String roleId = getRoleId(0);
		String roleType = getRoleType(0);
		String roleStatus = getRoleStatus(0);

		//// verify accountInfo Details
		boolean accountInfo = verifyAccountInfoOnAccountDetailScreen(roleId, roleType, roleStatus);

		//// verify addressInfo Details
		boolean addressInfo = verifyAddressInfoOnAccountDetailScreen(roleId, roleType, roleStatus);

		///// get first price sheet associated with account, to edit pricesheet level
		///// Warning
		String priceSheetnternalName = returnFirstPriceSheetToAddPriceSheetExceptions();

		//// verify accountLevelWarninig Details
		boolean accountLevelWarninig = verifyAccountLevelWarningOnAccountDetailScreen(roleId, roleType, roleStatus);
		boolean priceSheetLevelWarninig = true;
		//// verify priceSheetLevelWarninig Details
		if (priceSheetnternalName.length() > 0) {
			priceSheetLevelWarninig = verifyPriceSheetLevelWarningOnAccountDetailScreen(priceSheetnternalName);
		}

		//// Edit priceSheet : First Select top pricesheet and click edit warning to
		//// edit warnings
		click("AccountDetails_EditWarning");
		//// type typeAccountWarning
		typeAccountWarning("account underwriting automation warning", "account cancellation automation warning");
		//// type typePriceSheetWarning
		if (priceSheetnternalName.length() > 0)
			typePriceSheetWarning("pricing underwriting automation warning", "pricing cancellation automation warning");

		//// click save
		click("AccountDetails_SaveWarning");
		//// verify edited warnings again
		returnFirstPriceSheetToAddPriceSheetExceptions();
		boolean accountLevelWarninigAfterEdit = verifyAccountLevelWarningAfterEditOnAccountDetailScreen(
				"account underwriting automation warning", "account cancellation automation warning");
		boolean priceSheetLevelWarninigAfterEdit = true;
		if (priceSheetnternalName.length() > 0) {
			priceSheetLevelWarninigAfterEdit = verifyPriceSheetLevelWarningAfterEditOnAccountDetailScreen(
					"account underwriting automation warning", "account cancellation automation warning");
		}

		boolean corpDetails = verifyCorpAndGroupDetailsAccountDetailScreen(roleId, roleType, roleStatus);

		///// to be implemented once details are recieved
		boolean profitPrograms = false;

		///// find properties info
		boolean propertyInfo = verifyPropertyInfoOnAccountDetailScreen(roleId, roleType, roleStatus);

		if (accountInfo == propertyInfo == addressInfo == accountLevelWarninig == priceSheetLevelWarninigAfterEdit == priceSheetLevelWarninig == accountLevelWarninigAfterEdit == corpDetails == profitPrograms)
			assertEquals(accountLevelWarninigAfterEdit, true);
		else
			assertEquals(false, true);
	}

}
