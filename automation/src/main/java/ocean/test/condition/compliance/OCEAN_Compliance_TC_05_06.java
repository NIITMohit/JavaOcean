package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.ComplianceModulePages;

/**
 * OCEAN_Compliance_TC_05 and 06 class automates Ocean Compliance module Test
 * Condition 01 and 02, which holds 7 Test Cases; Validate display of applied
 * cancellation rules for cancellation request from compliance for 1) Different
 * products (program code) 2) Same product with different state 3) Same product
 * with different versions. Test Condition Description:Validate correct premium
 * and cancel fee calculation under cancellation , when compliance rules are
 * applied for: 1) Different products (program code) 2) Same product with
 * different state 3) Same product with different versions.
 * 
 * @author Shalu Chauhan
 */
public class OCEAN_Compliance_TC_05_06 extends ComplianceModulePages {
	/**
	 * This function automates all test cases for test condition 05 Case description
	 * :Validate display of applied cancellation rules for cancellation request from
	 * compliance for 1) Different products (program code) 2) Same product with
	 * different state 3) Same product with different versions.
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Validate display of applied cancellation rules for cancellation request from compliance. ")
	public void cancellationRuleForCompliance() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType("Dealer");
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNunmber = contractList.get("Contract_Number");
			String PrimaryName = contractList.get("name");
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNunmber);
			System.out.println("priceSheet==" + priceSheetGroup);
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			System.out.println("state==" + state);
			// for compliance rule pick
			complainceDataProcess(priceSheetGroup, priceSheetGroup, state);
			// process in compliance tab
			HashMap<String, String> ruleMap = new HashMap<String, String>();
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelReasonsInTree", "cancelReasonsItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("claimsDeductionInTree", "claimsDeductionInTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);
			// for process in cancellation tab
			cancellationPageProcess(contractNunmber);
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateFormatWithDaysVariance(contractList.get("sale_date"), 7), "");
			// click ok for cancellation completed successfully
			click("okClick");
			// get cancel method value applied as flat or outside flat in hashmap
			HashMap<String, String> matchRuleMap = getComplainceRuleWithRuleInfoViewMap();

			assertTrue(verifyComplainceRuleWithRuleInfoView(ruleMap, matchRuleMap), "Rules are not matched");
			logger.info("Rules are matched.");

		} else {
			new SkipException("no value exist in db.");
			assertEquals(dbFlag, true);
		}
	}

}
