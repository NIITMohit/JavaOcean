package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_T_252 class automates Ocean Underwriting module Test
 * Condition 49, which holds 1 Test Cases; Test Condition Description :Validate
 * that OCEAN user is able to authorize and post related remittance , if
 * contract is loaded via on hold contract enquiry.
 * 
 * @author Atul Awasthi
 */
public class OCEAN_UnderWriting_TC_49 extends UnderwritingModulePages {
	/**
	 * Validate that Ocean user is able to authorize loaded contract via on hold
	 * contract enquiry and is able to post that related remittance
	 */
	@Test(priority = 6, groups = "fullSuite", description = "Validate that Ocean user is able to authorize loaded contract via on hold contract enquiry and is able to post that related remittance")
	public void verifyPostRemittanceViaOnHoladEnquiry() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("OnHOLD");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToHoldContactTab();
			waitForSomeTime(5);
			type("certFilterRowInOnholdContract", contractId);
			waitForSomeTime(5);
			click("loadOnHoldContractInOnholdContract");
			waitForSomeTime(5);
			try {
				click("clickOK");
			} catch (Exception e) {
				// Do nothing
			}
			try {
				click("duplicateVINError");
			} catch (Exception e) {
				// Do nothing
			}
			contractExpander();
			try {
				click("duplicateVINError");
			} catch (Exception e) {
				// Do nothing
			}
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				// TODO: handle exception
			}
			click("clickPremiumCalculate");
			try {
				click("scrollContractsListUp");
			} catch (Exception e) {
				// TODO: handle exception
			}
			click("clickUnderW");
			waitForSomeTime(5);
			click("clickOnRemittanceSummary");
			waitForSomeTime(10);
			click("clickOnPostRemittance");
			try {
				click("clickOnYesButton");
			} catch (Exception e) {
				// TODO: handle exception
			}
			waitForSomeTime(5);
			try {
				click("clickOK");
			} catch (Exception e) {
				// Do nothing
			}
			click("contractExpander");
			goToFindContractTab();
			waitForSomeTime(10);
			type("contractNoInFindContract", contractId);
			click("searchFindContractBtn");
			waitForSomeTime(15);
			String status = getAttributeValue("statusInFindContract", "Value.Value");
			assertTrue(status.equals("Active"), "Contarct not posted successfully!");
		} else {
			new SkipException("no value in DB exists");
		}
	}
}
