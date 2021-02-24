package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_25 class automates Ocean Cancel module Test Condition 25 ,
 * which holds 5 Test Case; Test Condition Description :Validate for an account
 * that OCEAN doesn't assign primary / secondary account level cancellation
 * rules by default under account rule builder.
 * 
 * @author Poonam Kalra
 */
public class OCEAN_Cancel_TC_25 extends CancellationModulePages {

	/**
	 * This function automates test case for test condition 85 and 86; Test Case
	 * description : Validate that OCEAN doesn't assign primary/secondary account
	 * level cancellation rules by default under account rule builder
	 * 
	 */
	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_25", dataProviderClass = CancellationDataProvider.class, description = "Validate for an account that OCEAN doesn't assign primary account level cancellation rules by default under account rule builder")
	public void validateAcountLevelRulePrimary(String inputArray[]) throws Exception {
		HashMap<String, String> uiSearchData = null;
		uiSearchData = cancellation_Data(inputArray);
		//// Navigate to Cancellation tab
		goToCancellationTab();
		/// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData);
		assertEquals(primaryResult, true);
	}

	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_25", dataProviderClass = CancellationDataProvider.class, description = "Validate for an account that OCEAN doesn't assign primary account level cancellation rules by default under account rule builder")
	public void validateAcountLevelRuleSecondary(String inputArray[]) throws Exception {
		HashMap<String, String> uiSearchData = null;
		uiSearchData = cancellation_Data(inputArray);
		//// Navigate to Cancellation tab
		goToCancellationTab();
		//// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		boolean secondaryResult = validateSecondaryAccountLevelCancellationRules(uiSearchData);
		assertEquals(secondaryResult, true);
	}

	@Test(priority = 2, groups = "smoke1", dataProvider = "fetchDataForTC_25", dataProviderClass = CancellationDataProvider.class, description = "Add Account Level Rule  For An Primary Role Id in OCEAN.")
	public void addAcountLevelRuleforPrimaryAccount(String inputArray[]) throws Exception {
		boolean dbflag = false;
		HashMap<String, String> uiSearchData = null;
		uiSearchData = cancellation_Data(inputArray);
		HashMap<String, String> SearchData = null;
		// Navigate to Cancellation tab
		goToCancellationTab();
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdBasedOnRoleType(
				uiSearchData.get("PrimaryRoleType"), 1);
		uiSearchData.put("PrimaryRoleID", fetchDbValueBasedonRoleType.get("PrimaryRoleID"));
		uiSearchData.put("PrimaryRoleName", fetchDbValueBasedonRoleType.get("PrimaryAccountName"));
		uiSearchData.put("PrimaryRoleType", fetchDbValueBasedonRoleType.get("PrimaryRoleType"));
		// this function validate if account Level rules are defined for given role-id.
		// if no account level rules are defined then it return true.
		System.out.println(uiSearchData);
		
		boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData);
		if (primaryResult == true) {
			SearchData = cancellation_AccountRuleBuilder_Data(inputArray);
			addAccountLevelRulesToAccountId(SearchData);
		} else {
			assertEquals(primaryResult, true);
		}
		HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAddAccountLevelRules(
				uiSearchData.get("PrimaryRoleID"),uiSearchData.get("PrimaryRoleName"));
		if (dBValidation.size() > 0) {
			logger.info("Account Rule Added For Given Role Id---->>" + dBValidation);
			dbflag = true;
		} else {
			dbflag = false;
			logger.info("No Account Rule Added For Given Role Id");
		}
		assertEquals(dbflag, true);
	}

	@Test(priority = 1, groups = "fullSuite", dataProviderClass = CancellationDataProvider.class, description = "Validate OCEAN abality to cancel contract")
	public void validateCancelledContractForAnyContractID() throws Exception {
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnRolenameAndStatus("Lender", "Active");
		String contractId = contractList.get("CERT");
		if (contractId.length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason,"",
					convertDateCancellation(contractList.get("SALE_DATE"), 18), "");
			click("clickOK");
			click("clickOK");
			//// Authorize cancellation request
			selectCancellationTaskStatus("Authorize");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Authorize",contractId);
			assertEquals(cancelStatusActual, true);
			}

		else {
			new SkipException("no contract exist in db");
		}
	}
}
