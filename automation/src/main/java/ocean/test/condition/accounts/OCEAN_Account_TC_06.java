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
 * @author Shalu Chauhan
 * @reviewer : Poonam Kalra
 */

public class OCEAN_Account_TC_06 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate all information related with Accounts on Account
	 * details screen,actually fetched from sugar
	 * 
	 */
	@Test(priority = 3, groups = {
			"smoke" }, dataProvider = "fetchDataForTC_06", dataProviderClass = AccountsDataProvider.class, description = "Validate all information related with Accounts on Account details screen,actually fetched from sugar")

	public void validateAccountDetailsFromSugar(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("Account_Name", inputArray[0]);
		uiSearchData.put("Role_Id", inputArray[1]);
		uiSearchData.put("Role_Type", inputArray[2]);
		uiSearchData.put("Address", inputArray[3]);
		uiSearchData.put("State", inputArray[4]);
		uiSearchData.put("City", inputArray[5]);
		uiSearchData.put("Zip_Code", inputArray[6]);
		uiSearchData.put("Status", inputArray[7]);
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

		HashMap<String, String> priceSheetAssign = checkPriceSheetOnAccount(uiSearchData);
		uiSearchData.put("PriceSheetInternalName", priceSheetAssign.get("PriceSheetInternalName"));
		uiSearchData.put("PriceSheet", priceSheetAssign.get("PriceSheet"));
		if (uiSearchData.get("PriceSheetInternalName") != null || uiSearchData.get("PriceSheetInternalName") != " ") {
			//// run code for search
			searchContractGivenInputParamaters(uiSearchData);
			//// click display button to load account details page
			//// get role id of first searched data
			waitForSomeTime(4);
			String roleId = getRoleId(0);
			System.out.println(roleId);
			String roleType = getRoleType(0);
			System.out.println(roleType);
			String roleStatus = getRoleStatus(0);
			System.out.println(roleStatus);
			clickDisplayButton(0);
			///// get first price sheet associated with account, to edit pricesheet level
			///// Warning

			String priceSheetnternalName = returnFirstPriceSheetToAddPriceSheetExceptions();
			System.out.println(priceSheetnternalName);
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
				typePriceSheetWarning("pricing underwriting automation warning",
						"pricing cancellation automation warning");
			//// click save
			click("AccountDetails_SaveWarning");
			//// verify edited warnings again
			returnFirstPriceSheetToAddPriceSheetExceptions();
			boolean accountLevelWarninigAfterEdit = verifyAccountLevelWarningAfterEditOnAccountDetailScreen(
					"account underwriting automation warning", "account cancellation automation warning");
			boolean priceSheetLevelWarninigAfterEdit = true;
			if (priceSheetnternalName.length() > 0) {
				priceSheetLevelWarninigAfterEdit = verifyPriceSheetLevelWarningAfterEditOnAccountDetailScreen(
						"pricing underwriting automation warning", "pricing cancellation automation warning");
			}
			System.out.println("priceSheetLevelWarninigAfterEdit ===>>" + priceSheetLevelWarninigAfterEdit);
			System.out.println("priceSheetLevelWarninig ===>>" + priceSheetLevelWarninig);
			System.out.println("accountLevelWarninig====>>" + accountLevelWarninig);
			System.out.println("accountLevelWarninigAfterEdit====>>" + accountLevelWarninigAfterEdit);

			if (accountLevelWarninig == priceSheetLevelWarninig == priceSheetLevelWarninigAfterEdit == accountLevelWarninigAfterEdit)
				assertEquals(accountLevelWarninigAfterEdit, true);

			else

				assertEquals(false, true);

		} else {
			logger.info("Account doesn't contain price sheet.");
			// assertEquals(true, true);
		}

	}

}
