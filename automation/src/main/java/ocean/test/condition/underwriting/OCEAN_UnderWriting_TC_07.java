package ocean.test.condition.underwriting;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_07 class automates Ocean Underwriting module Test
 * Condition 07 which holds 2 Test Case; Test Condition Description : Validate
 * OCEAN ability to delete remittance , if none of the contract is
 * created/adjusted under related remittance.
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_07 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 07 Test Case
	 * description : Validate OCEAN ability to delete remittance , if none of the
	 * contract is created/adjusted under related remittance.
	 * 
	 */
	@Test(priority = 5, groups = "regression", description = "Validate OCEAN ability to delete remittance , if none of the contract is created/adjusted under related remittance.")
	public void deleteRemittance() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		remittanceExpander();
	}

}
