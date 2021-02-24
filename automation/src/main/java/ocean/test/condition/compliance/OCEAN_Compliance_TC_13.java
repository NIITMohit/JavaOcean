package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.ComplianceModulePages;

/**
 * Validate that OCEAN user is able to edit compliance form to price sheet via
 * edit price sheet group.
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Compliance_TC_13 extends ComplianceModulePages {
	/**
	 * This function automates one test case for test condition 13. Case description
	 * Validate that OCEAN user is able to edit compliance form to price sheet via
	 * edit price sheet group.
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Validate that Correct cancellation rules are applied , if compliance group is changed for a price sheet via edit price sheet group.")
	public void editPriceSheetGroupAndVerifyCancellationRuleForCompliance() throws Exception {
		boolean compareResult = false;
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType("Dealer");
		if (contractList.get("Contract_Number").length() > 0) {
			String contractNumber = contractList.get("Contract_Number");
			String priceSheetName = compliance_getPricesheetIdNamebasedOnCert(contractNumber);
			System.out.println("priceSheet Name==" + priceSheetName);
			String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNumber);
			System.out.println("priceSheet Group before edit==" + priceSheetGroup);
			// change priceSheet Group
			changePriceSheetGroup(priceSheetName, priceSheetGroup);
			String editedpriceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNumber);
			System.out.println(" Changed priceSheet Group==" + editedpriceSheetGroup);
			String PrimaryName = contractList.get("name");
			String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNumber, PrimaryName);
			// for compliance rule pick
			complainceDataProcess(editedpriceSheetGroup, editedpriceSheetGroup, state);
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
			cancellationPageProcess(contractNumber);
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateFormatWithDaysVariance(contractList.get("sale_date"), 65), "");
			// click ok for cancellation completed successfully
			click("okClick");
			// get cancel method value applied as flat or outside flat in hashmap
			HashMap<String, String> matchRuleMap = getComplainceRuleWithRuleInfoViewMap();

			compareResult = verifyComplainceRuleWithRuleInfoView(ruleMap, matchRuleMap);
			if (compareResult) {
				logger.info(
						"Correct cancellation rules are applied, when compliance group is changed for a price sheet via edit price sheet group.");
			} else {
				logger.info(
						"Correct cancellation rules are not applied, when compliance group is changed for a price sheet via edit price sheet group.");
			}
			assertEquals(compareResult, true);

		} else {
			throw new SkipException("no value exist in db.");
		}
	}

}
