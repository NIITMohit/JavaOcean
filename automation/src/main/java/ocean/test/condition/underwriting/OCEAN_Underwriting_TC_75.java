package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 9275 : , which holds 1 Test Case; Test
 * Condition Description : Transferred - AllSales
 * 
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_75 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9113 Case description : NSF
	 * - AllSales
	 */
	@Test(priority = 1, groups = { "regression", "fullSuite" }, description = "Transferred - AllSales")
	public void TransferredAllSales() throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatus("Active");
		processForAccountSearchForTransfer(contractid);
		String fee = randomInteger(3);
		typeTransferFeeandTransfer(fee);
		waitForSomeTime(10);
		String status = getAttributeValue("contractStatus", "Name");
		//// validate status and sub status
		String subStatus = getAttributeValue("contractSubStatus", "Name");
		click("contractExpander");
		//// get db for comparison
		assertEquals(status.equalsIgnoreCase("active") && subStatus.equalsIgnoreCase("transferred")
				&& cancellation_getContractIdTrasferred(contractid, fee).equalsIgnoreCase("1"), true);
	}
}
