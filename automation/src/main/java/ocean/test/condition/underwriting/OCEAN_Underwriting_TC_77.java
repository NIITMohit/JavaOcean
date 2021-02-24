package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 9168 : , which holds 2 Test Case; Test
 * Condition Description : NSF - AllSales
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_77 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9113 Case description : NSF
	 * - AllSales
	 * 
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Add Reason for NSF	in On Hold and Underwriting Adjustment screen drop down ")
	public void OnHoldNSFStatusForContractAdjustment() throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatus("Active");
		processForAccountSearchForContractAdjustment(contractid);
		try {
			click("scrollContractsListDown", 1);
		} catch (Exception e) {
		}
		surcharges();
		options();
		//// Get AUL Premium
		premium();
		//// click on hold
		clickAdjustWithReason(new String[] { "nsf" });
		waitForSomeTime(25);
		String status = getAttributeValue("contractStatus", "Name");
		//// validate status and sub status
		String subStatus = getAttributeValue("contractSubStatus", "Name");
		click("contractExpander");
		assertEquals(status.equalsIgnoreCase("active") && subStatus.equalsIgnoreCase("nsf"), true);
	}

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void RemoveOnHoldNSFStatusForContractAdjustment() throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatusandSubStatus("Active", "nsf");
		processForAccountSearchForContractAdjustment(contractid);
		try {
			click("scrollContractsListDown", 1);
		} catch (Exception e) {
		}
		surcharges();
		options();
		takeScreenshot();
		//// Get AUL Premium
		//// change dealer amount
		type("dealerPaid", "1000");
		premium();
		waitForSomeTime(5);
		String dealer = getValue("dealerPaid");
		takeScreenshot();
		assertEquals(dealer.contains("1,000"), true);
		contractScrollToTop();
		//// click on hold
		clickAdjustWithReason(new String[] { "rewritten" });
		waitForSomeTime(25);
		String status = getAttributeValue("contractStatus", "Name");
		//// validate status and sub status
		String subStatus = getAttributeValue("contractSubStatus", "Name");
		click("contractExpander");
		assertEquals(status.equalsIgnoreCase("active") && subStatus.equalsIgnoreCase("rewritten"), true);
	}
}
