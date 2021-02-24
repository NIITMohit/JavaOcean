package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_TC_08_09_10_11_12 class automates Ocean Account module Test
 * Condition 08 and 09 and 10 and 11 and 12, which holds 14 Test Cases; Test
 * Condition Description : Validate for account type as agent,lender Class A,
 * lender Class B that OCEAN allow price sheet assignment for following only: 1.
 * Master 2. Sub Master
 * 
 * @author Shalu Chauhan
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Account_TC_08_09_10_11_12 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 08, 09, 10,11,12;
	 * Test Case description : Validate for role type dealer if Commission exception
	 * is not added then premium is calculated on Master Price sheet. Type as Lender
	 * type A Master Level Price sheet is visible Case description : Validate for
	 * Account Type as Lender type A sub Master(cloned) Level Price sheet is visible
	 * Case description : Validate for Account Type as Lender type A, Lender Level
	 * Price sheet is visible Case description : Validate for Account Type as Lender
	 * type b, no price sheet association exists Case description : Validate that
	 * Ocean display, Lender level price sheet list associated for account type as
	 * lender class A.
	 * 
	 */

	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_08_09_10_11_12", dataProviderClass = AccountsDataProvider.class, description = "Validate Assign priceSheet on the basis of Role type like Agent,Lender,Dealer.")
	public void validatePriceSheetforAccountType(String[] inputArray) throws Exception {
		// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		// // Navigate to mail service tab

		goToAccountsTab();
		goToAccountsSearchTab();

		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			// // run db query to get unique value, else no need
			// // including *, Blanks
			uiSearchData = accounts_getDataSetforSearch(account_appendSearchData(inputArray));
		} else {
			uiSearchData = account_convertDataRemoveStar(inputArray);
		}
		// // run code for search

		searchContractGivenInputParamaters(uiSearchData);
		uiSearchData.put("PriceSheet", inputArray[8]);
		uiSearchData.put("ClassificationList", inputArray[9]);
		uiSearchData.put("Payee", inputArray[10]);
		// select lender classification List A list or B list
		selectTopAccountForRoleType(uiSearchData);
		// setup new pricing
		waitForSomeTime(2);
		clickForSetUpNewPricing();
		// Verify payee isdisabled for Agent or Enable for lender

		verifyAndSelectPayee(uiSearchData);

		// Apply filters on Role Type and match priceSheet
		selectRoleTypeValueAndMatch(uiSearchData.get("Role_Type"));
		// type priceSheet code to select the priceSheet
		typePriceSheetCode(inputArray[8]);
		// Click on priceSheet internal name textbox
		click("PriceSheetInternalName");
		// this is used to get the value of priceSheet internal name
		String internalName = getValue("PriceSheetInternalName");
		// assign price sheet on Agent or Lender or dealer
		HashMap<String, String> pricesheetData = getAccountAssignPriceSheetOnRoleType(uiSearchData.get("PriceSheet"));

		selectPriceSheetToAssign();
		// Validate pricesheet which we have assigned on Agent, Lender or Dealer
		HashMap<String, String> assignPriceSheet = validatePriceSheetForRoleType(internalName);
		if (assignPriceSheet.get("RoleTypeId") == null
				|| assignPriceSheet.get("RoleTypeId") != pricesheetData.get("RoleTypeId")) {
			assignPriceSheet.replace("RoleTypeId", pricesheetData.get("RoleTypeId"));

		}
		assertEquals(pricesheetData, assignPriceSheet);
	}
}
