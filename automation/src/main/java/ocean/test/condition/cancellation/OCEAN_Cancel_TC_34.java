package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_34 class automates Ocean Cancel module Test Condition 34,
 * which holds 44 Test Case; Test Condition Description :Validate application of
 * rules from each rule category by OCEAN, if each rule category is
 * defined/updated for a primary account.
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Cancel_TC_34 extends CancellationModulePages {
	/**
	 * This function automates 21 test cases for test condition 34; Test Case
	 * description : Validate application of rules from each rule category by OCEAN,
	 * if each rule category is defined for a primary account.
	 * 
	 */

	@SuppressWarnings("rawtypes")
	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_34", dataProviderClass = CancellationDataProvider.class, description = "Validation that Refund Percent/Cancel Fee/Refund Based On/Payee rules for primary account are correctly applied on cancellation for a contract")
	public void validateAccountRefundPercentRule(String[] inputArray) throws Exception {

		HashMap<Integer, HashMap<String, String>> accountRuleData = cancellation_SearchSpecificAccountRule(
				inputArray[0], inputArray[1]);

		HashMap<String, String> contractList = new HashMap<String, String>();
		String roleId = "";
		String effectiveDate = "";

		for (int i = 1; i < accountRuleData.size(); i++) {

			HashMap<String, String> getValue = accountRuleData.get(i);
			for (Map.Entry mapElement : getValue.entrySet()) {
				String roleIdName = (String) mapElement.getKey();
				roleId = roleIdName.substring(0, roleIdName.indexOf("_"));
				effectiveDate = (String) mapElement.getValue();
			}
			if (inputArray[1].contains("Cancel Fee Within Flat Cancel Period")) {
				String minSaleDate = convertDateCancellation(effectiveDate, -6);
				contractList = cancellation_getContractIdBasedOnStatusRoleIdAccRulesClaims(inputArray[4], roleId,
						inputArray[1], minSaleDate);
			} else {
				contractList = cancellation_getContractIdBasedOnStatusRoleIdAccRulesClaims(inputArray[4], roleId,
						inputArray[1], "");
			}

			/*
			 * if (contractList.size() > 0) break;
			 */
		}

		if (contractList.size() > 0 && contractList.get("CERT").length() > 0) {
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
			int daysDiff = 0;
			if (inputArray[1].contains("Within Flat Cancel Period")) {
				daysDiff = 5;
			} else {
				long daysDiffCal = calculateDaysBwTwoDates(contractList.get("SALE_DATE"), effectiveDate);
				daysDiff = (int) (daysDiffCal > 70 ? daysDiffCal + 1 : 70);
			}

			String cancelMiles = "";
			// Enter the cancellation parameters on New Cancellation screen
			enterValuesOnNewCancellationTabAndClickCalculate(inputArray[2], inputArray[3], cancelMiles,
					convertDateCancellation(contractList.get("SALE_DATE"), daysDiff), "");

			// click ok for cancellation completed successfully
			click("okClick");
			waitForSomeTime(30);

			HashMap<String, String> dbruleInfo = dBValidationOfAccountLevelRulesforRefundPCancelF(roleId, inputArray[0],
					inputArray[1]);
			logger.info("dbruleInfo" + dbruleInfo);

			HashMap<Integer, HashMap<String, String>> expectedResult = getRuleInfoExpectedResult(dbruleInfo,
					cancelMiles, daysDiff + 1, contractList.get("PLAN_MILES"), contractList.get("SALE_DATE"),
					contractList.get("EXPIRATION_DATE"), contractList.get("CUSTOMER_PAID"),
					contractList.get("DEALER_PAID"));
			logger.info("expectedResult" + expectedResult);

			try {
				click("listtoggleStateRuleInfoView");
				if (!checkIsDisplayed("ruleInfoRefersh"))
					click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValueWithResult = getRuleInfoValueWithResult();
			logger.info("ruleInfoViewValueWithResult" + ruleInfoViewValueWithResult);

			boolean compareResult = compareValuesforRefundPCancelF(ruleInfoViewValueWithResult, expectedResult);
			if (compareResult) {
				logger.info("Account Rule Builder for " + inputArray[0] + " rule is correctly applied for "
						+ inputArray[1] + " category");
			} else {
				logger.info("Account Rule Builder for " + inputArray[0] + " rule is not correctly applied for "
						+ inputArray[1] + " category");
			}
			assertEquals(compareResult, true);

		} else {
			throw new SkipException("no value exist in db for Given Account Rule");
		}
	}

	/**
	 * This function automates 21 test cases for test condition 34; Test Case
	 * description : Validate application of rules from each rule category by OCEAN,
	 * if each rule category is edited for a primary account.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Test(priority = 2, groups = "smoke1", dataProvider = "fetchDataForTC_34", dataProviderClass = CancellationDataProvider.class, description = "Validation that Refund Percent/Cancel Fee/Refund Based On/Payee rules for primary account are correctly edited on cancellation for a contract")

	public void validateAccountRefundPercentRuleEdit(String inputArray[]) throws Exception {

		HashMap<Integer, HashMap<String, String>> accountRuleData = cancellation_SearchSpecificAccountRule(
				inputArray[0], inputArray[1]);

		String roleId = "";
		String roleName = "";

		if (accountRuleData.size() > 0) {
			HashMap<String, String> getValue = accountRuleData.get(1);
			for (Map.Entry mapElement : getValue.entrySet()) {
				String roleIdName = (String) mapElement.getKey();
				roleId = roleIdName.substring(0, roleIdName.indexOf("_"));
				roleName = roleIdName.substring(roleIdName.indexOf("_") + 1);
			}

			HashMap<String, String> dbruleInfo = dBValidationOfAccountLevelRulesforRefundPCancelF(roleId, inputArray[0],
					inputArray[1]);
			logger.info("dbruleInfo before editing rules :" + inputArray[1] + " category: " + dbruleInfo);

			HashMap<String, String> editedRule = findEditedValue(dbruleInfo, inputArray[0], inputArray[1]);
			logger.info("dbruleInfo after editing the rules should be :" + editedRule);
			//// Navigate to Account Rules Builder tab
			goToCancellationTab();
			goToAccountsRuleBuilderTab();
			//// Search and edit the rule for Account
			findAccountAndEditAccountRule(editedRule, roleName);

			HashMap<String, String> editedDbruleInfo = dBValidationOfAccountLevelRulesforRefundPCancelF(roleId,
					inputArray[0], inputArray[1]);
			logger.info("Actual dbruleInfo after editing the rules " + editedDbruleInfo);

			boolean compareResult = compareValuesforHashMap(dbruleInfo, editedDbruleInfo);
			if (!compareResult) {
				logger.info("Account Rule Builder for " + inputArray[0] + " rule is correctly edited for "
						+ inputArray[1] + " category");
			} else {
				logger.info("Account Rule Builder for " + inputArray[0] + " rule is not correctly edited for "
						+ inputArray[1] + " category");
			}
			assertEquals(!compareResult, true);
		} else {
			throw new SkipException("no value exist in db for Given Account Rule");
		}

	}

}
