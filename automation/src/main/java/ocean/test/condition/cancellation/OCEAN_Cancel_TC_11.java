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
public class OCEAN_Cancel_TC_11 extends CancellationModulePages {

	@SuppressWarnings({ "unused" })
	@Test(priority = 1, groups = { "regression", "","smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC11", dataProviderClass = CancellationDataProvider.class, description = "To validate availability of applicable cancellation rules under rule info view from applicable Compliance from only.")
	public void verifyApplicableCancellationRulesFromComplianceByProductName(String[] inputData) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdBasedOnProductCode(inputData[0], inputData[3]);
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("role_identifier"), "",
				inputData[3]);
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
			// ruleMap.putAll(complainceRuleTreeProcessForAllRule("claimsDeductionInTree",
			// "claimsDeductionInTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);

			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
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
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			/// Fetch Data form RuleInfoView On New Cancellation Page
			HashMap<String, String> ruleInfoViewValue = getRuleInfoRule();
			click("listtoggleStateRuleInfoView");
			System.out.print(ruleInfoViewValue);
			takeScreenshot();
			// Move to Contract Builder tab for compliance rule pick
			// complainceDataSearch(priceSheetGroup, state);
			// HashMap<String, String> ruleMap =
			// complainceDefaultRuleTree(ruleInfoViewValue);
			System.out.print(ruleMap);
			System.out.print(ruleInfoViewValue);
			if (ruleMap.containsKey("STDNCB")) {
				ruleMap.remove("STDNCB");
			}
			if (ruleMap.containsKey("STDPRORATE")) {
				ruleMap.remove("STDPRORATE");
			}
			matchFlag = verifyCancellationeRuleWithRuleInfoView(ruleInfoViewValue,ruleMap );
			if (matchFlag = true) {
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			} else {
			   matchFlag = false;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			}
			assertEquals(matchFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
		goToCancellationTab();
		goToMailServiceTab();
	}

	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC11", dataProviderClass = CancellationDataProvider.class, description = "To validate availability of applicable cancellation rules under rule info view from applicable Compliance from only.")
	public void verifyApplicableCancellationRulesFromComplianceForDifferentState(String[] inputData) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdBasedOnProductCode(inputData[0], inputData[3]);
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("role_identifier"), "",
				inputData[3]);
		if (contractList.get("CERT").length() > 0) {
			String contractNunmber = contractList.get("CERT");
			String PrimaryName = contractList.get("NAME");
			String priceSheetGroup = compliance_PricesheetIdbasedOnCert(contractNunmber);
			String productName = dBsearch.get("productName");
			String state = "Multi-State";
			// Move to Contract Builder tab for compliance rule pick
			complainceDataSearch(priceSheetGroup, state);
			takeScreenshot();
			/// Fetch Data for compliance rule verfication
			HashMap<String, String> ruleMap = new HashMap<String, String>();
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			// ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelReasonsInTree",
			// "cancelReasonsItems"));
			// ruleMap.putAll(complainceRuleTreeProcessForAllRule("claimsDeductionInTree",
			// "claimsDeductionInTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);

			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
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
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			/// Fetch Data form RuleInfoView On New Cancellation Page
			HashMap<String, String> ruleInfoViewValue = getRuleInfoRule();
			System.out.print(ruleInfoViewValue);
			click("listtoggleStateRuleInfoView");
			takeScreenshot();

			if (ruleMap.containsKey("STDNCB")) {
				ruleMap.remove("STDNCB");
			}
			if (ruleMap.containsKey("STDPRORATE")) {
				ruleMap.remove("STDPRORATE");
			}
			System.out.print(ruleMap);
			if (ruleMap.containsValue(ruleInfoViewValue)) {
				matchFlag = true;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			} else {
				matchFlag = false;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			}
			assertEquals(dbFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}
}
