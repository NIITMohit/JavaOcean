package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 9113 : , which holds 12 Test Case; Test
 * Condition Description : Reinstated
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_74 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9113 Case description :
	 * Reinstated
	 * 
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI9113", dataProviderClass = UnderwritingDataProvider.class, description = "Reinstated")
	public void Reinstated(String[] reinstateReason) throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatus("Cancelled");
		processForAccountSearchForContractReinstated(contractid);
		//// click Reinstate button
		String value = getValue("ClickReinstatedBtn");
		if (value.equalsIgnoreCase("reinstate")) {
			//// fill Reinstate form with reason
			clickReinstateWithReason(new String[] { reinstateReason[0] });
			String status = getAttributeValue("contractStatus", "Name");
			//// validate status and sub status
			String subStatus = getAttributeValue("contractSubStatus", "Name");
			click("contractExpander");
			assertEquals(status.equalsIgnoreCase("cancelled") && subStatus.equalsIgnoreCase("reinstated"), true);
		} else {
			throw new Exception("reinstate button not available");
		}

	}

	/**
	 * This function automates all test cases of NEW PBI 9113 Case description :
	 * ReinstatedErrorForActiveContracts
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "fullSuite" }, description = "ReinstatedErrorForActiveContracts")
	public void ReinstatedErrorForActiveContracts() throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatus("Active");
		type("contractNoInFindContract", contractid);
		click("searchFindContractBtn");
		for (int i = 0; i < 5; i++) {
			try {
				click("ReinstatedBtn");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		String errorMessage = getValue("errorMessageValue");
		removeErrorMessages();
		boolean flag = errorMessage.contains(
				" is in Active status and cannot be reinstated. Only Cancelled or Returned contracts can be reinstated.");
		assertEquals(flag, true);

	}
}
