package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 9135 : , which holds 5 Test Case; Test
 * Condition Description : Add Reason for Rewrite in On Hold and Underwriting
 * Adjustment screen drop down
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_73 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9135 Case description : Add
	 * Reason for Rewrite in On Hold and Underwriting Adjustment screen drop down
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForPBI10608", dataProviderClass = UnderwritingDataProvider.class, description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void OnHoldRewriteStatusForContractAdjustment(String[] onHoldReason) throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatus("Active");
		if (contractid != null) {
			processForAccountSearchForContractAdjustment(contractid);
			try {
				click("scrollContractsListDown", 1);
			} catch (Exception e) {
			}
			//// Get AUL Premium
			premium();
			//// click on hold
			clickAdjustWithReason(new String[] { onHoldReason[0] });
			waitForSomeTime(25);
			String status = getAttributeValue("contractStatus", "Name");
			//// validate status and sub status
			String subStatus = getAttributeValue("contractSubStatus", "Name");
			click("contractExpander");
			assertEquals(status.equalsIgnoreCase("active") && subStatus.equalsIgnoreCase("rewritten"), true);
		}
		else {
			throw new SkipException("no matching contract found");
		}

	}

}
