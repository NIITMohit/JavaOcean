package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 15059 : , which holds 5 Test Case; Test
 * Condition Description : Add Reason for Rewrite in On Hold and Underwriting
 * Adjustment screen drop down
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_66 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9135 Case description : Add
	 * Reason for Rewrite in On Hold and Underwriting Adjustment screen drop down
	 * 
	 */
	@Test(priority = 1, groups = "fullSuite", description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void DealerPaidAndUnderWrite() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		HashMap<String, String> contractid = contractIdRemittanceNameFromStatusAndSubStatus("OnHold", "nsf");
		if (contractid != null) {
			searchContractwithPendingState(contractid.get("remittanceName"), contractid.get("cert"));
			lockAndViewContract();
			contractScrollToBottom();
			//// update delaer paid
			type("dealerPaid", "2000");
			//// Get AUL Premium
			premium();
			contractScrollToTop();
			//// click on underwrite
			click("clickUnderW");
			waitForSomeTime(25);
			String status = getAttributeValue("contractStatus", "Name");
			click("contractExpander");
			assertEquals(status.equalsIgnoreCase("underw"), true);
		} else
			throw new SkipException("no contract founf");
	}

	@Test(priority = 1, groups = "fullSuite", description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void DealerPaidAndUnderWriteOhHoldScreen() throws Exception {
		goToUnderWritingTab();
		goToHoldContactTab();
		HashMap<String, String> contractid = contractIdPostedRemittanceNameFromStatusAndSubStatus("OnHold", "nsf");
		if (contractid != null) {
			searchAndLandToNewBusinessFromOnHoldContracts(contractid.get("cert"));
			contractScrollToBottom();
			//// update delaer paid
			type("dealerPaid", "2000");
			//// Get AUL Premium
			premium();
			contractScrollToTop();
			//// click on underwrite
			click("clickUnderW");
			waitForSomeTime(25);
			String status = getAttributeValue("contractStatus", "Name");
			click("contractExpander");
			assertEquals(status.equalsIgnoreCase("underw"), true);
		} else {
			throw new SkipException("no contract foinf");
		}
	}

	@Test(priority = 1, groups = "fullSuite", description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void onHoldNSFReturn() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		HashMap<String, String> contractid = contractIdRemittanceNameFromStatusAndSubStatus("OnHold", "nsf");
		if (contractid != null) {
			searchContractwithPendingState(contractid.get("remittanceName"), contractid.get("cert"));
			lockAndViewContract();
			clickReturnWithReason(new String[] { "other" });
			waitForSomeTime(25);
			String status = getAttributeValue("contractStatus", "Name");
			click("contractExpander");
			assertEquals(status.equalsIgnoreCase("return"), true);
		} else {
			throw new SkipException("no contract fund");
		}
	}

	@Test(priority = 1, groups = "smoke", description = "Validate  Active NSF contract for sub status as blank, if user select adjust category other than rewrite and NSF under contract adjustment.")
	public void additionalPaymentWithAdjust() throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		//// pick a contract with active-nsf and additional payment already exists
		HashMap<String, String> contractid = contractIdPostedRemittanceNameFromStatusAndSubStatusExtAdjustment("active",
				"nsf");
		if (contractid.size() > 0) {
			processForAccountSearchForContractAdjustment(contractid.get("cert"));
			try {
				click("scrollContractsListDown", 1);
			} catch (Exception e) {
			}
			//// update delaer paid
			type("dealerPaid", "953");
			//// Get AUL Premium
			premium();
			String[] inputData = { "upgrade" };
			///// adjust reason
			clickAdjustWithReason(inputData);
			waitForSomeTime(25);
			String status = getAttributeValue("contractStatus", "Name");
			click("contractExpander");
			//// verify adjustment
			assertEquals(status.equalsIgnoreCase("active"), true);
		} else
			throw new SkipException("no contract fund");

	}
}
