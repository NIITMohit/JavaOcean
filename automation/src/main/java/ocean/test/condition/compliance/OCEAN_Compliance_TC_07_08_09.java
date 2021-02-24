package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Compliance_TC_07,08 and 09 class automates Ocean Compliance module Test
 * Condition 07,08 and 09, which holds 6 Test Cases; Validate correct rule
 * display (Rule info view) and premium and cancel fee calculation under
 * cancellation , when compliance rules are applied with no account level rules.
 * Test Condition Description:Validate correct rule display (Rule info view) and
 * premium and cancel fee calculation under cancellation , when compliance rules
 * are applied with account level rules. Test Condition Description:Validate
 * correct rule display (Rule info view) and premium and cancel fee calculation
 * under cancellation , when compliance rules are applied with account price
 * sheet level rules.
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Compliance_TC_07_08_09 extends CancellationModulePages {

	/**
	 * This function automates test case for test condition OCEAN_Compliance _T28
	 * Account should be in Active status under Accounts Module;
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC27", dataProviderClass = CancellationDataProvider.class, description = "Validate Ocean ability to apply priority over compliance cancellation rule on the account level when account is active.")
	public void verifyPriorityComplianceRuleOverAccountlevelCancellationWithActiveAcc(String[] inputData)
			throws Exception {
		boolean dbFlag = false;
		//// Search Role id with accountlevel Cancellation rule added from db where
		//// account status is active
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[0], 1);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndRoleId(dBsearch.get("roleId"),"");
		if (contractList.get("CERT").length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id"/"
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[0].length() > 0)
				initiatedBy = inputData[0];
			if (inputData[1].length() > 0)
				cancelReason = inputData[1];
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 62), "");
			takeScreenshot();
			// click ok for cancellation completed successfully
			click("okClick");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(30);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			// go to standardCalculation
			click("standardCalculation");
			click("okClick");
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			takeScreenshot();
			click("standOKbtn");
			// compare standardcalculation and normal calcuted value
			if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
				logger.info("Verfiying through Rule Info View");
			} else {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
				logger.info("Verfiying  through Rule Info View");
			}
			// get rule info view value in hashmap
			String primaryRoleId = (dBsearch.get("roleId"));
			String pCode = contractList.get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					pCode);
			System.out.print("db--->" + dbruleInfo);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<Integer, String[]> ruleInfovalue = getRuleInfoValueResult();

			logger.info("AccountLevelRulesFromDataBase=====>>" + dbruleInfo);
			boolean compareResult = compareRulesValues(dbruleInfo, ruleInfovalue);
			assertEquals(compareResult, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}

	}

	/**
	 * This function automates test case for test condition OCEAN_Compliance _T29
	 * Account should be in Active status under Accounts Module;
	 */
	@SuppressWarnings("unused")
	@Test(priority = 2, groups = "regression", description = "Validate Ocean ability to apply priority over compliance cancellation rule on the account level when account is not  active.")
	public void verifyPriorityComplianceRuleOverAccountlevelCancellationRuleWithInActiveAccount() throws Exception {
		boolean dbFlag = false;
		//// Search Role id with accountlevel Cancellation rule added from db where
		//// account status is inactive
		HashMap<String, String> dBrules = cancellation_getAccountRoleInactiveIdWithAccountRuleBuilder4("Dealer", 2);
		String roleId = dBrules.get("roleId");
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusInactiveRoleId(roleId);
		if (contractList.get("CERT").length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id"/"
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 62), "");
			takeScreenshot();
			// click ok for cancellation completed successfully
			click("okClick");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(30);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			System.out.print(calculatedCancellationValue);
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			takeScreenshot();
			click("standOKbtn");
			// compare standardcalculation and normal calcuted value
			if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
				logger.info("Verfiying through Rule Info View");
			} else {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
				logger.info("Verfiying  through Rule Info View");
			}
			// get rule info view value in hashmap
			String primaryRoleId = (roleId);
			String pCode = contractList.get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					pCode);
			System.out.print("db--->" + dbruleInfo);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<Integer, String[]> ruleInfovalue = getRuleInfoValueResult();

			logger.info("AccountLevelRulesFromDataBase=====>>" + dbruleInfo);
			boolean compareResult = compareRulesValues(dbruleInfo, ruleInfovalue);
			assertEquals(compareResult, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

	/**
	 * This function automates test case for test condition OCEAN_Compliance _T30
	 * and OCEAN_Compliance _T31 Validate that, Ocean display correct rules in Rule
	 * info view section under cancellation, when compliance rules are applied with
	 * account level rules. Validate that, Ocean shall allow user to calculate
	 * premium and cancel fee for a contract over compliance rules are applied with
	 * account level rules under account rule builder.
	 */
	@Test(priority = 3, groups = "regression", description = "Validate that, Ocean display correct rules in Rule info view section under cancellation, when compliance rules are applied with account level rules.")
	public void validateComplianceRuleWithAccountRule(String[] inputArray) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilderTC14("Dealer");
		System.out.print(dBsearch);
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("roleIdentifier"), "");
		System.out.print(contractList);
		if (contractList.get("CERT").length() > 0) { //// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, Integer> cancelDays = calculateCancelDateTC14(dBsearch.get("EFFECTIVE_DATE"),
					contractList.get("SALE_DATE"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), cancelDays.get("afterflatCanceldays")), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			System.out.print(calculatedCancellationValue);
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, String> contractDetails = getContractDetailsDB(cert);
			// get rule info view value in hashmap
			String primaryRoleId = dBsearch.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					pCode);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			takeScreenshot();
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			boolean result = compareDbWithUiRules(ruleInfoViewValue, dbruleInfo);
			if (result == true) {
				int daysDiff = cancelDays.get("daysdiffAfterFlat");
				calculatedValue = getRuleInfoExpectedResultTC14(dbruleInfo, contractDetails, daysDiff);
				logger.info("DBInfoViewValue====" + dbruleInfo);
				HashMap<String, String> calculatedCancellationValueWithoutComma = removeComma(
						calculatedCancellationValue);
				logger.info("calculatedValue====" + calculatedCancellationValueWithoutComma);
				System.out.print("NewValue" + calculatedCancellationValueWithoutComma);
				if (calculatedCancellationValueWithoutComma.equals(calculatedValue)) {
					matchFlag = true;
				}
				assertEquals(matchFlag, true);
			}
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}

	}

	/**
	 * This function automates test case for test condition OCEAN_Compliance _T32
	 * and OCEAN_Compliance _T33 Validate that, Ocean display correct rules in Rule
	 * info view section under cancellation, when compliance rules are applied with
	 * account level rules. Validate that, Ocean shall allow user to calculate
	 * standard Premium and cancel fee for a contract, w when account price sheet
	 * level rules are applied with compliance rules.
	 */

	@SuppressWarnings({ "rawtypes", "unused" })
	@Test(priority = 4, groups = "regression", dataProvider = "fetchDataForTC_34", dataProviderClass = CancellationDataProvider.class, description = "Validate that, Ocean display correct rules in Rule info view section under cancellation, when compliance rules are applied with account level rules.")
	public void validateComplianceRuleWithPriceSheetLevelRule(String[] inputArray) throws Exception {

		boolean dbFlag = false;
		boolean matchFlag = false;

		HashMap<Integer, HashMap<String, String>> accountRuleData = cancellation_SearchSpecificAccountRuleWithPriceSheetLevelRules(
				inputArray[0], inputArray[1]);

		HashMap<String, String> contractList = new HashMap<String, String>();
		String roleId = "";
		String effectiveDate = "";
		String priceSheet = "";

		for (int i = 1; i < accountRuleData.size(); i++) {

			HashMap<String, String> getValue = accountRuleData.get(i);
			for (Map.Entry mapElement : getValue.entrySet()) {
				String roleIdName = (String) mapElement.getKey();
				roleId = roleIdName.substring(0, roleIdName.indexOf("_"));
				effectiveDate = (String) mapElement.getValue();
				priceSheet = (String) mapElement.getValue();
			}
			if (inputArray[1].contains("Cancel Fee Within Flat Cancel Period")) {
				String minSaleDate = convertDateCancellation(effectiveDate, -6);
				contractList = cancellation_getContractIdBasedOnStatusRoleIdPriceSheetLevelRules(inputArray[4], roleId,
						inputArray[1], minSaleDate, priceSheet);
			} else {
				contractList = cancellation_getContractIdBasedOnStatusRoleIdPriceSheetLevelRules(inputArray[4], roleId,
						inputArray[1], "", priceSheet);
			}

			if (contractList.size() > 0)
				break;
		}

		if (contractList.get("CERT").length() > 0) {
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
				daysDiff = 5; // static
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
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			System.out.print(calculatedCancellationValue);
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, String> contractDetails = getContractDetailsDB(cert);
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfPriceSheetLevelRulesforRefundPCancelF(
					roleId, inputArray[0], inputArray[1], priceSheet);
			logger.info("dbruleInfo" + dbruleInfo);

			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			boolean result = compareDbWithUiRules(ruleInfoViewValue, dbruleInfo);
			HashMap<String, String> calculatedValue = getRuleInfoExpectedResultTC14(dbruleInfo, contractDetails,
					daysDiff);
			logger.info("DBInfoViewValue====" + dbruleInfo);
			HashMap<String, String> calculatedCancellationValueWithoutComma = removeComma(calculatedCancellationValue);
			logger.info("calculatedValue====" + calculatedCancellationValueWithoutComma);
			System.out.print("NewValue" + calculatedCancellationValueWithoutComma);
			if (calculatedCancellationValueWithoutComma.equals(calculatedValue)) {
				matchFlag = true;
			}
			assertEquals(matchFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

}
