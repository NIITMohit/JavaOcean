package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_06 class automates Ocean Underwriting module Test
 * Condition 06 which holds 2 Test Case; Test Condition Description : Validate
 * creation of remittance in OCEAN with editing/reset of details given earlier.
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_06 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Validate the creation of remittance in ocean
	 * 
	 */
	@Test(priority = 5, groups = "regression", description = "Validate creation of remittance in OCEAN with editing/reset of details given earlier.")
	public void editAndResetRemittanceValues() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		remittanceExpander();
		String[] inputArray = { "random", "1", "1", "Paper", "Standard", "Paper Remit", "Dealer Suspense", "Automation",
				"", "" };
		enterRemittanceValueswithoutSave(inputArray);
		String[] inputArray2 = getRemittanceValueswithoutSave();
		String[] inputArray3 = { "random1", "11", "11", "Web", "Lender", "No Remit", "Dealer AGs", "Automation 11", "",
				"" };
		enterRemittanceValueswithoutSave(inputArray3);
		String[] inputArray5 = getRemittanceValueswithoutSave();
		boolean editFlag = false;
		List<String> inputList1 = Arrays.asList(inputArray);
		List<String> inputList2 = Arrays.asList(inputArray2);
		List<String> inputList3 = Arrays.asList(inputArray3);
		List<String> inputList5 = Arrays.asList(inputArray5);
		if (inputList1.containsAll(inputList2) && inputList3.containsAll(inputList5))
			editFlag = true;
		boolean resetFlag = false;
		click("clickRemittanceReset");
		String[] inputArray4 = getRemittanceValueswithoutSave();
		for (String string : inputArray4) {
			if (string.length() > 1)
				break;
		}
		assertEquals(editFlag == resetFlag, true);
	}
}
