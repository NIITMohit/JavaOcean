package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_05 class automates Ocean Underwriting module Test
 * Condition 05 which holds 1 Test Case; Test Condition Description : Validate
 * OCEAN ability to allow check delete by user before remittance is saved.
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_05 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Validate the creation of remittance in ocean
	 * 
	 */
	@Test(priority = 5, groups = "regression", description = "Validate OCEAN ability to allow check delete by user before remittance is saved. ")
	public void deleteCheckDetails() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		remittanceExpander();
		addCheckDetails("111", "123");
		try {
			deleteCheckDetailsAndVerify("111");
			assertEquals(true, true);
		} catch (Exception e) {
			throw new Exception("Fail");
		}
	}
}
