package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.ComplianceDataProvider;
import ocean.modules.pages.ComplianceModulePages;

/**
 * OCEAN_Compliance_TC_12 class automates Ocean Cancel module Test Condition 12,
 * which holds 98 Test Case; Test Condition Description : Validate application
 * of rules from each rule category under cancellation by OCEAN for a programs
 * code: 1. With their default values. 2. Change by OCEAN user. 3. for different
 * state.
 * 
 * @author Atul Awasthi
 */
public class OCEAN_Compliance_TC_12 extends ComplianceModulePages {
	/**
	 * This function automates test case for test condition 12; Test Case
	 * description : Validate that Flat cancel is correctly applied on cancellation
	 * for a contract with default values from compliance form.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_1", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultFlatCancelPeriodRule_T37(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			int daysDiff = 0;
			int day = Integer.parseInt(excelMap.get("Days"));
			long daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"),
					contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			System.out.println("daydiff=====" + daysDiff);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				if (excelMap.get("Role_Type").equals("Lender")) {
					click("clickOK");
				}
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			HashMap<Integer, HashMap<String, String>> summaryData = getRuleInfoExpectedResult(ruleMap,
					contractList.get("EXPIRATION_MILEAGE"), daysDiff + 1, contractList.get("PLAN_MILES"),
					contractList.get("sale_date"), contractList.get("EXPIRATION_DATE"),
					contractList.get("CUSTOMER_PAID"), contractList.get("DELAER_PAID"));
			// get cancel method value applied as flat or outside flat in hashmap
			assertTrue(verifyComplainceRulesWithRuleinfoView(summaryData, excelMap), "rules not matching");
		} else {
			new SkipException("no value exist in db.");
		}
	}

	/**
	 * This function automates test case for test condition 12; Test Case
	 * description : Validate that Flat cancel period is correctly applied on
	 * cancellation for a contract, if values is changed by user in compliance form.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_1", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period "
			+ "is correctly applied on cancellation for a contract, if values is changed by user  in compliance form.")
	public void verifyUserAppliedFlatCancelPeriodRule_T70(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			HashMap<String, String> ruleMap = complainceUserAppliedRuleTreeProcess(excelMap);
			waitForSomeTime(5);
			click("clickComplianceClearAll");
			complainceDataProcess(priceSheetGroup, state);
			HashMap<String, String> changedRuleMap = complainceDefaultRuleTreeProcess(excelMap);
			assertTrue(ruleMap.equals(changedRuleMap), "Rules not matching");
		} else {
			new SkipException("no value exist in db.");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_1", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyRulesAfterStateChange(String[] inputArray) throws Exception {
		int daysDiff = 0;
		int day = 0;
		long daysDiffCal = 0;
		HashMap<String, String> ruleMap = null;
		HashMap<Integer, HashMap<String, String>> summaryData = null;
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			day = Integer.parseInt(excelMap.get("Days"));
			daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"), contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			System.out.println("daydiff=====" + daysDiff);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				if (excelMap.get("Role_Type").equals("Lender")) {
					click("clickOK");
				}
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			summaryData = getRuleInfoExpectedResult(ruleMap, contractList.get("EXPIRATION_MILEAGE"), daysDiff + 1,
					contractList.get("PLAN_MILES"), contractList.get("sale_date"), contractList.get("EXPIRATION_DATE"),
					contractList.get("CUSTOMER_PAID"), contractList.get("DELAER_PAID"));
			// get cancel method value applied as flat or outside flat in hashmap
			boolean result1 = verifyComplainceRulesWithRuleinfoView(summaryData, excelMap);
			contractList = compliance_getCertBasedOnExistingPricesheetID(excelMap, priceSheetGroup);
			String newState = getDifferentState(contractList.get("roleId"), state);
			System.out.println("newState===" + newState);
			complainceDataProcess(priceSheetGroup, newState);
			// process in complaince tab
			ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			contractNunmber = contractList.get("Contract_Number");
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				if (excelMap.get("Role_Type").equals("Lender")) {
					click("clickOK");
				}
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			click("listtoggleStateRuleInfoView");
			summaryData = getRuleInfoExpectedResult(ruleMap, contractList.get("EXPIRATION_MILEAGE"), daysDiff + 1,
					contractList.get("PLAN_MILES"), contractList.get("sale_date"), contractList.get("EXPIRATION_DATE"),
					contractList.get("CUSTOMER_PAID"), contractList.get("DELAER_PAID"));
			// get cancel method value applied as flat or outside flat in hashmap
			boolean result2 = verifyComplainceRulesWithRuleinfoView(summaryData, excelMap);
			assertEquals(result1 && result2, true);
		} else {
			new SkipException("no value exist in db.");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_2", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleClaimsDeduction(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			HashMap<String, String> ruleMap = deductClaimsDefaultRuleTreeProcess(excelMap);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				if (excelMap.get("Role_Type").equals("Lender")) {
					click("clickOK");
				}
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			HashMap<String, String> actualMap = getActualClaimsDuctionRule(ruleMap);
			HashMap<String, String> calculatedValue = new HashMap<String, String>();
			int daysDiff = 0;
			int day = Integer.parseInt(excelMap.get("Days"));
			long daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"),
					contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			System.out.println("daydiff=====" + daysDiff);
			waitForSomeTime(10);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			System.out.println("calculatedValue===" + calculatedValue);
			System.out.println("actualMap===" + actualMap);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");
		} else {
			new SkipException("no value exist in db.");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_2", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyClaimsDeductionAfterStateChange(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		HashMap<String, String> actualMap = new HashMap<String, String>();
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			ruleMap = deductClaimsDefaultRuleTreeProcess(excelMap);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				if (excelMap.get("Role_Type").equals("Lender")) {
					click("clickOK");
				}
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			actualMap = getActualClaimsDuctionRule(ruleMap);
			calculatedValue = new HashMap<String, String>();
			int daysDiff = 0;
			int day = Integer.parseInt(excelMap.get("Days"));
			long daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"),
					contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			System.out.println("daydiff=====" + daysDiff);
			waitForSomeTime(10);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			contractList = compliance_getCertBasedOnExistingPricesheetID(excelMap, priceSheetGroup);
			String newState = getDifferentState(contractList.get("roleId"), state);
			complainceDataProcess(priceSheetGroup, newState);
			// process in complaince tab
			ruleMap = deductClaimsDefaultRuleTreeProcess(excelMap);
			contractNunmber = contractList.get("Contract_Number");
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				if (excelMap.get("Role_Type").equals("Lender")) {
					click("clickOK");
				}
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			actualMap = getActualClaimsDuctionRule(ruleMap);
			calculatedValue = new HashMap<String, String>();
			waitForSomeTime(10);
			day = Integer.parseInt(excelMap.get("Days"));
			daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"), contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			waitForSomeTime(10);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			click("listtoggleStateRuleInfoView");
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");

		} else {
			new SkipException("no value exist in db.");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_4", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleRefundBasedon(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			HashMap<String, String> actualMap = getActualRefundBasedOnRule(ruleMap);
			HashMap<String, String> calculatedValue = new HashMap<String, String>();
			int daysDiff = 0;
			int day = Integer.parseInt(excelMap.get("Days"));
			long daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"),
					contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			waitForSomeTime(10);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");
		} else {
			new SkipException("no value exist in db.");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_4", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyUserAppliedRuleRefundBasedon(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			HashMap<String, String> ruleMap = complainceUserAppliedRuleTreeProcess(excelMap);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			HashMap<String, String> actualMap = getActualRefundBasedOnRule(ruleMap);
			HashMap<String, String> calculatedValue = new HashMap<String, String>();
			int daysDiff = 0;
			int day = Integer.parseInt(excelMap.get("Days"));
			long daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"),
					contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			waitForSomeTime(10);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");
		} else {
			new SkipException("no value exist in db.");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12_4", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleRefundBasedonChangeState(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, state);
			// process in complaince tab
			HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			HashMap<String, String> actualMap = getActualRefundBasedOnRule(ruleMap);
			HashMap<String, String> calculatedValue = new HashMap<String, String>();
			int daysDiff = 0;
			int day = Integer.parseInt(excelMap.get("Days"));
			long daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"),
					contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			System.out.println("daydiff=====" + daysDiff);
			waitForSomeTime(10);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			contractList = compliance_getCertBasedOnExistingPricesheetID(excelMap, priceSheetGroup);
			String newState = getDifferentState(contractList.get("roleId"), state);
			complainceDataProcess(priceSheetGroup, newState);
			// process in complaince tab
			ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			contractNunmber = contractList.get("Contract_Number");
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"),
					excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			actualMap = getActualRefundBasedOnRule(ruleMap);
			calculatedValue = new HashMap<String, String>();
			day = Integer.parseInt(excelMap.get("Days"));
			daysDiffCal = calculateDaysBwTwoDates(contractList.get("sale_date"), contractList.get("effective_date"));
			daysDiff = (int) (daysDiffCal > day ? daysDiffCal + 1 : day);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			calculatedValue = getClaimsDeductionRuleInfoExpectedResult(ruleMap, contractList, daysDiff);
			assertTrue(calculatedValue.equals(actualMap), "rules not matching");
		} else {
			new SkipException("no value exist in db.");
		}
	}

//	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
//			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleIfTransferred(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		System.out.println("excelMap==="+excelMap);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
	 		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			//for compliance rule pick
	    	complainceDataProcess(priceSheetGroup, state);
		    //process in complaince tab
			HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			waitForSomeTime(5);
			if(ruleMap.get("If TransFerred").equals("Non-Cancellable")) {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
    	} else {
			new SkipException("no value exist in db.");
		  }
	}
	
//	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
//			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyUserAppliedRuleIfTransferred(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		System.out.println("excelMap==="+excelMap);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
	 		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			//for compliance rule pick
	    	complainceDataProcess(priceSheetGroup, state);
		    //process in complaince tab
			HashMap<String, String> ruleMap = complainceUserAppliedRuleTreeProcess(excelMap);
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			waitForSomeTime(5);
			if(ruleMap.get("If TransFerred").equals("Non-Cancellable")) {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
    	} else {
			new SkipException("no value exist in db.");
		  }
	}

//	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
//			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleIfTransferredChangeState(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		System.out.println("excelMap==="+excelMap);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
	 		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			//for compliance rule pick
	    	complainceDataProcess(priceSheetGroup, state);
		    //process in complaince tab
			HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			waitForSomeTime(5);
			if(ruleMap.get("If TransFerred").equals("Non-Cancellable")) {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
	        contractList = compliance_getCertBasedOnExistingPricesheetID(excelMap, priceSheetGroup);
	        System.out.println("ContractList===="+contractList);
	        String newState = getDifferentState(contractList.get("roleId"), state);
			System.out.println("newState==="+newState);
			complainceDataProcess(priceSheetGroup, newState);
			//process in complaince tab
			ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			contractNunmber = contractList.get("Contract_Number");
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), excelMap.get("Cancel_Reason"), "",
					convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
			waitForSomeTime(5);
			if(ruleMap.get("If TransFerred").equals("Non-Cancellable")) {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else {
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
    	} else {
			new SkipException("no value exist in db.");
		  }
	}

//	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
//			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleCancelReason(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		String reason = "";
		System.out.println("excelMap==="+excelMap);
		HashMap<String, String> contractList = new HashMap<String, String>();
		List<WebElement> reasonList = new ArrayList<>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
	 		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			//for compliance rule pick
	    	complainceDataProcess(priceSheetGroup, state);
		    //process in complaince tab
			HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			waitForSomeTime(5);
			if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason")) {
				reasonList = listOfElements("cancelReasonComboboxInCompliance");
	            Random generator = new Random();
	            int number = generator.nextInt(reasonList.size()-1);
	            reason = reasonList.get(number).getText();
	            enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), reason, "",
				        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason Except Misrepresentation by Seller")){
				for(int i=0; i<reasonList.size()-1; i++) {
					if(reasonList.get(i).getText().equals("Misrepresentation by Seller")) {
						enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), "Misrepresentation by Seller", "",
						        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
					}
				}
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
			
    	} else {
			new SkipException("no value exist in db.");
		  }
	}
	
//	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
//			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyUserAppliedRuleCancelReason(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = compliance_RuleVerification(inputArray);
		String reason = "";
		System.out.println("excelMap==="+excelMap);
		HashMap<String, String> contractList = new HashMap<String, String>();
		List<WebElement> reasonList = new ArrayList<>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
	 		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			//for compliance rule pick
	    	complainceDataProcess(priceSheetGroup, state);
		    //process in complaince tab
			HashMap<String, String> ruleMap = complainceUserAppliedRuleTreeProcess(excelMap);
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			waitForSomeTime(5);
			if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason")) {
				reasonList = listOfElements("cancelReasonComboboxInCompliance");
	            Random generator = new Random();
	            int number = generator.nextInt(reasonList.size()-1);
	            reason = reasonList.get(number).getText();
	            enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), reason, "",
				        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
//				assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason Except Misrepresentation by Seller")){
				for(int i=0; i<reasonList.size()-1; i++) {
					if(reasonList.get(i).getText().equals("Misrepresentation by Seller")) {
						enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), "Misrepresentation by Seller", "",
						        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
					}
				}
//				assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
			
    	} else {
			new SkipException("no value exist in db.");
		  }
	}

//	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC12", dataProviderClass = ComplianceDataProvider.class, description = "Validate that Flat cancel period is"
//			+ "	correctly applied on cancellation for a contract with default values from compliance form.")
	public void verifyDefaultRuleCancelReasonChangeState(String[] inputArray) throws Exception {
			HashMap<String, String> excelMap = new HashMap<String, String>();
			excelMap = compliance_RuleVerification(inputArray);
			String reason = "";
			System.out.println("excelMap==="+excelMap);
			HashMap<String, String> contractList = new HashMap<String, String>();
			List<WebElement> reasonList = new ArrayList<>();
			contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(excelMap);
			if (contractList.get("Contract_Number").length() > 0) {
				String contractNunmber = contractList.get("Contract_Number");
				String PrimaryName = contractList.get("name");
		 		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
				String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
				//for compliance rule pick
		    	complainceDataProcess(priceSheetGroup, state);
			    //process in complaince tab
				HashMap<String, String> ruleMap = complainceDefaultRuleTreeProcess(excelMap);
				//for process in cancellation tab
			    cancellationPageProcess(contractNunmber);
				waitForSomeTime(5);
				if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason")) {
					reasonList = listOfElements("cancelReasonComboboxInCompliance");
		            Random generator = new Random();
		            int number = generator.nextInt(reasonList.size()-1);
		            reason = reasonList.get(number).getText();
		            enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), reason, "",
					        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
					//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
				} else if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason Except Misrepresentation by Seller")){
					for(int i=0; i<reasonList.size()-1; i++) {
						if(reasonList.get(i).getText().equals("Misrepresentation by Seller")) {
							enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), "Misrepresentation by Seller", "",
							        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
						}
					}
					//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
				}
	        contractList = compliance_getCertBasedOnExistingPricesheetID(excelMap, priceSheetGroup);
	        System.out.println("ContractList===="+contractList);
	        String newState = getDifferentState(contractList.get("roleId"), state);
			System.out.println("newState==="+newState);
			complainceDataProcess(priceSheetGroup, newState);
			//process in complaince tab
			ruleMap = complainceDefaultRuleTreeProcess(excelMap);
			contractNunmber = contractList.get("Contract_Number");
			//for process in cancellation tab
		    cancellationPageProcess(contractNunmber);
			waitForSomeTime(5);
			if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason")) {
				reasonList = listOfElements("cancelReasonComboboxInCompliance");
	            Random generator = new Random();
	            int number = generator.nextInt(reasonList.size()-1);
	            reason = reasonList.get(number).getText();
	            enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), reason, "",
				        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");
			} else if(ruleMap.get("Cancel Reason, Administrator").equals("Any Reason Except Misrepresentation by Seller")){
				for(int i=0; i<reasonList.size()-1; i++) {
					if(reasonList.get(i).getText().equals("Misrepresentation by Seller")) {
						enterValuesOnNewCancellationTabAndClickCalculate(excelMap.get("Initiated_By"), "Misrepresentation by Seller", "",
						        convertDate(contractList.get("sale_date"), Integer.parseInt(excelMap.get("Days"))), "");
					}
				}
				//assertTrue(calculatedValue.equals(actualMap), "rules not matching");				
			}
	}
	else {
			new SkipException("no value exist in db.");
		  }
	}

}
