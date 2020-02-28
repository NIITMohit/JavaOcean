package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

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
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "1", "Paper", "Standard", "Paper Remit", "Dealer Suspense", "Auto", "",
				"" };
		String remittanceName = enterRemittanceValues(inputArray);
		deleteMyRemittance();
		String deleted = deleteRemittanceStatus(remittanceName);
		String reittanceupdatedName = "";
		try {
			reittanceupdatedName = getValue("remitName");
		} catch (Exception e) {
			System.out.println(reittanceupdatedName);
			// TODO: handle exception
		}
		if (!reittanceupdatedName.toLowerCase().equals(remittanceName.toLowerCase()))
			assertEquals("1", deleted);
		else
			assertEquals("1", "0");

	}

}
