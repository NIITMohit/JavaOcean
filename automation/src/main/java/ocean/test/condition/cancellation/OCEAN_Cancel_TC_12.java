package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * Validate the availability of applicable cancellation rules for cancellation
 * request by 1. Product name 2. Different states.
 * 
 * @author Poonam.Kalra
 *
 */
public class OCEAN_Cancel_TC_12 extends CancellationModulePages {

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression","smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC12", dataProviderClass = CancellationDataProvider.class, description = "To validate availability of applicable cancellation rules under rule info view from applicable Compliance from only.")
	public void verifyApplicableCancellationRulesFromCompliance(String[] inputData) throws Exception {

		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdBasedOnProductCode(inputData[0], inputData[3]);
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndRoleId(dBsearch.get("role_identifier"), "");
		if (contractList.get("CERT").length() > 0) {
			String contractNunmber = contractList.get("CERT");
			String PrimaryName = contractList.get("NAME");
			String priceSheetGroup = compliance_PricesheetIdbasedOnCert(contractNunmber);
			String productName = dBsearch.get("productName");
			String state = compliance_StateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// Move to Contract Builder tab for compliance rule pick
			complainceDataSearch(priceSheetGroup, state);
			takeScreenshot();
			/// Fetch Data for compliance rule verfication
			HashMap<String, String> ruleMap = new HashMap<String, String>();
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			// ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelReasonsInTree",
			// "cancelReasonsItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("claimsDeductionInTree", "claimsDeductionInTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 65), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			// get rule info view value in hashmap
			String primaryRoleId = dBsearch.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			// go to standardCalculation
			click("standardCalculation");
			click("okClick");
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			takeScreenshot();
			click("standOKbtn");
			HashMap<String, String> ruleMap1 = complainceDefaultRuleTree(calculatedStandardCancellationValue);
			if (ruleMap.equals(calculatedStandardCancellationValue)) {
				matchFlag = true;
				logger.info("calculatedStandardCancellationValue====" + calculatedStandardCancellationValue);
				logger.info("ruleMap====" + ruleMap);
			} else {
				logger.info("calculatedStandardCancellationValue====" + calculatedStandardCancellationValue);
				logger.info("ruleMap====" + ruleMap);
			}
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 2, groups = { "regression","smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC12", dataProviderClass = CancellationDataProvider.class, description = "Validate that standard calculation is same as calculate from New cancellation for a contract, when no rules is applicable from account rule builder..")
	public void verifyStandCalculationWithCalculateWithoutAccountLevelRule(String[] inputData) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdBasedOnProductCode(inputData[0], inputData[3]);
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("role_identifier"), "",
				inputData[3]);
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 65), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			// get rule info view value in hashmap
			String primaryRoleId = dBsearch.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			// go to standardCalculation
			click("standardCalculation");
			click("okClick");
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			takeScreenshot();
			click("standOKbtn");
			/// Fetch Data for compliance rule verfication
			if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
				matchFlag = true;
				logger.info("calculatedStandardCancellationValue====" + calculatedStandardCancellationValue);
				logger.info("calculatedCancellationValue ====" + calculatedCancellationValue);
			} else {
				logger.info("calculatedStandardCancellationValue====" + calculatedStandardCancellationValue);
				logger.info("calculatedCancellationValue ====" + calculatedCancellationValue);
			}
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

	/// This Function Validate that user is able to add comments under standard
	/// calculation for a contract under cancellation.

	@Test(priority = 2, groups = { "regression","smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC12", dataProviderClass = CancellationDataProvider.class, description = "Validate that user is able to add comments under standard calculation for a contract under cancellation.")
	public void verifyUserCanAddCommentsInStandardCalculationUnderCancellation(String[] inputData) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdBasedOnProductCode(inputData[0], inputData[3]);
		System.out.print(dBsearch);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("role_identifier"), "",
				inputData[3]);
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 65), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			takeScreenshot();
			click("standardCalculation");
			click("clickOK");
			click("standardCalculationAddBtn");
			String commentStandard = getTextOfElement("standardCalculationCommentText");
			takeScreenshot();
			if (commentStandard.length() > 3) {
				matchFlag = true;

			}
			click("clickOK");
			assertEquals(matchFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

	/*
	 * Validate following fields under standard calculation for correct information:
	 * 1. Payee ,2. Address,3. City,4. State,5 Zip.
	 */
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression","smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC12", dataProviderClass = CancellationDataProvider.class, description = "Validate that user is able to add comments under standard calculation for a contract under cancellation.")
	public void verifyStandardCalculationForCorrectInformation(String[] inputData) throws Exception {

		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdBasedOnProductCode(inputData[0], "");
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> getAddressVerify = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("role_identifier"), "", "");
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 84), "");
			waitForSomeTime(10);
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(3);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			// get rule info view value in hashmap
			String payee = calculatedCancellationValue.get("Payee");
			// go to standardCalculation
			click("standardCalculation");
			click("clickOK");
			// HashMap<String, String> calculatedStandardCancellationValue =
			// getStandardCancellationDetails();
			HashMap<String, String> calculatedStandardAddressValue = getStandardCalAddrresDetails();
			takeScreenshot();
			click("standOKbtn");
			HashMap<String, String> getPayeeAddressDetail = getPayeeDetail(payee);
			if (calculatedStandardAddressValue.equals(getPayeeAddressDetail)) {
				matchFlag = true;
			}
			assertEquals(matchFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

}