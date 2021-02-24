package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_29 class automates Ocean Cancel module Test Condition 29 ,
 * which holds 4 Test Case; Test Condition Description :Validate ocean ability
 * to add and apply account level rule for account with 1. No contract (apply
 * not applicable here) 2. Single contract 3. Multiple contract in different
 * status. 4. Multiple product type.
 * 
 * @author Poonam Kalra
 **/
public class OCEAN_Cancel_TC_29 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition OCEAN_Cancel_T105; Test
	 * Case description : Validate that OCEAN ability to add and apply account level
	 * rule for account with No contract
	 * 
	 */

	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "Add Account Level Rule  For An Primary Role Id with no contract in OCEAN.")
	public void addAcountLevelRuleforPrimaryAccountWithNoContract(String inputArray[]) throws Exception {
		boolean dbflag = true;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		// Navigate to Cancellation tab
		goToCancellationTab();
		// try { click("clickOK");}
		// catch(Exception e) {}; //
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithNoContract("Dealer");
		String roleId = fetchDbValueBasedonRoleType.get("PrimaryRoleID");
		String roleName = fetchDbValueBasedonRoleType.get("PrimaryAccountName");
		String PriceSheetID = fetchDbValueBasedonRoleType.get("PRICESHEET_ID");
		String RoleType = fetchDbValueBasedonRoleType.get("RoleType");
		uiSearchData.put("PrimaryRoleID", roleId);
		uiSearchData.put("PrimaryRoleName", roleName);
		uiSearchData.put("PrimaryRoleType", RoleType);
		uiSearchData.put("PRICESHEET_ID", PriceSheetID);
		String PriceSheetName = cancellation_getAccountRoleIdPriceSheet(PriceSheetID);
		uiSearchData.put("PRICESHEET_Name", PriceSheetName);
		// this function validate if account Level rules are defined for given role-id.
		// if no account level rules are defined then it return true.
		boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, PriceSheetName);
		if (primaryResult == true) {
			SearchData = cancellation_AccountRuleBuilderDataTC29(inputArray);
			addAccountLevelRulesToAccountId(SearchData);
		} else {
			assertEquals(primaryResult, true);
		}
		HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAddAccountLevelRulesWithoutContract(
				uiSearchData.get("PrimaryRoleID"), PriceSheetID);
		if (dBValidation.size() > 0) {
			logger.info("Account Rule Added For Given Role Id---->>");
			dbflag = true;
		} else {
			dbflag = false;
			logger.info("No Account Rule Added For Given Role Id");
		}
		assertEquals(dbflag, true);
	}

	/**
	 * This function automates test case for test condition OCEAN_Cancel_T105; Test
	 * Case description : Validate that OCEAN ability to add and apply account level
	 * rule for account with Single contract
	 */

	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "Add Account Level Rule  For An Primary Role Id with Single Contract in OCEAN.")
	public void addAcountLevelRuleforPrimaryAccountWithSingleContract(String inputArray[]) throws Exception {
		boolean dbflag = true;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		// Navigate to Cancellation tab
		goToCancellationTab();
		// try { click("clickOK");}
		// catch(Exception e) {}; //
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithSingleContract("Dealer");
		String roleId = fetchDbValueBasedonRoleType.get("PrimaryRoleID");
		String roleName = fetchDbValueBasedonRoleType.get("PrimaryAccountName");
		String PriceSheetID = fetchDbValueBasedonRoleType.get("PRICESHEET_ID");
		String RoleType = fetchDbValueBasedonRoleType.get("RoleType");
		String PROGRAM_CODE = fetchDbValueBasedonRoleType.get("PROGRAM_CODE");
		uiSearchData.put("PrimaryRoleID", roleId);
		uiSearchData.put("PrimaryRoleName", roleName);
		uiSearchData.put("PrimaryRoleType", RoleType);
		uiSearchData.put("PRICESHEET_ID", PriceSheetID);
		String PriceSheetName = cancellation_getAccountRoleIdPriceSheet(PriceSheetID);
		uiSearchData.put("PRICESHEET_Name", PriceSheetName);
		// this function validate if account Level rules are defined for given role-id.
		// if no account level rules are defined then it return true.
		boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, PriceSheetName);
		if (primaryResult == true) {
			SearchData = cancellation_AccountRuleBuilderDataTC29(inputArray);
			addAccountLevelRulesToAccountId(SearchData);
		} else {
			assertEquals(primaryResult, true);
		}
		HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAccountLevelRules(
				uiSearchData.get("PrimaryRoleID"), PROGRAM_CODE, roleName);
		if (dBValidation.size() > 0) {
			logger.info("Account Rule Added For Given Role Id---->>");
			dbflag = true;
		} else {
			dbflag = false;
			logger.info("No Account Rule Added For Given Role Id");
		}
		assertEquals(dbflag, true);
	}

	/**
	 * Validate that OCEAN apply cancellation rules on a cancellation request, when
	 * cancellation rules are defined for an account with multiple contracts with
	 * status like Onhold, processed. and with contracts from different plan codes.
	 * TestCondition-107 and 108
	 */

	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "Add Account Level Rule For An Primary Role Id with multiple Contract Status and Plan Code.")
	public void addAcountLevelRuleforPriAccWithMultipleContractStatusAndProgCode(String inputArray[]) throws Exception {
		boolean dbflag = true;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		// Navigate to Cancellation tab
		goToCancellationTab();
		// try { click("clickOK");}
		// catch(Exception e) {}; //
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithMultipleStatusAndProgCode(
				"Dealer");
		String roleId = fetchDbValueBasedonRoleType.get("PrimaryRoleID");
		String roleName = fetchDbValueBasedonRoleType.get("PrimaryAccountName");
		String PriceSheetID = fetchDbValueBasedonRoleType.get("PRICESHEET_ID");
		String RoleType = fetchDbValueBasedonRoleType.get("RoleType");
		String PROGRAM_CODE = fetchDbValueBasedonRoleType.get("PROGRAM_CODE");
		uiSearchData.put("PrimaryRoleID", roleId);
		uiSearchData.put("PrimaryRoleName", roleName);
		uiSearchData.put("PrimaryRoleType", RoleType);
		uiSearchData.put("PRICESHEET_ID", PriceSheetID);
		String PriceSheetName = cancellation_getAccountRoleIdPriceSheet(PriceSheetID);
		uiSearchData.put("PRICESHEET_Name", PriceSheetName);
		// this function validate if account Level rules are defined for given role-id.
		// if no account level rules are defined then it return true.
		boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, PriceSheetName);
		if (primaryResult == true) {
			SearchData = cancellation_AccountRuleBuilderDataTC29(inputArray);
			addAccountLevelRulesToAccountId(SearchData);
		} else {
			assertEquals(primaryResult, true);
		}
		HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAccountLevelRules(
				uiSearchData.get("PrimaryRoleID"), PROGRAM_CODE, roleName);
		if (dBValidation.size() > 0) {
			logger.info("Account Rule Added For Given Role Id---->" + uiSearchData.get("PrimaryRoleID"));
			dbflag = true;
		} else {
			dbflag = false;
			logger.info("No Account Rule Added For Given Role Id");
		}
		assertEquals(dbflag, true);
	}
}
