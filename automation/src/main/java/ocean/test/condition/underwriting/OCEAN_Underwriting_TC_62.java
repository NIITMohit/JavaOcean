package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 15055 : , which holds 5 Test Case; Test
 * Condition Description : Verify correct message display while remittance
 * creation, if user does not provide check details.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_62 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9135 Case description : Add
	 * Reason for Rewrite in On Hold and Underwriting Adjustment screen drop down
	 * 
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Verify correct message display while remittance creation, if user does not provide check details.")
	public void deleteCheck() throws Exception {
		copyFilesWorkingRemittance();
		String[] inputArray = new String[] { "random", "1", "1", "Paper", "Standard", "Paper Remit", "", "Automation",
				"1121", "12345" };
		//// go to underwriting tab
		goToUnderWritingTab();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String remit = enterRemittanceValuesNoSave(inputArray);
		deleteCheckDetailsAndVerify("1121");
		click("clickSaveRemittance");
		String errorMessage = getValue("addCheckError");
		if (errorMessage.equalsIgnoreCase(
				"You are creating a Remittance with no Check Information entered, do you want to proceed?")) {
			click("clickOnYesButton");
			for (int i = 0; i < 2; i++) {
				try {
					click("closeFolderExplorer");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			for (int i = 0; i < 2; i++) {
				try {
					click("remittanceExpander");
					break;
				} catch (Exception e) {
					continue;
				}
			}

			//// db cheek
			HashMap<String, String> dbValues = getRemitCreationdata(remit);
			if (dbValues.size() > 0) {
				assertEquals(true, true);
			} else
				assertEquals(false, true);
		} else {
			assertEquals(false, true);
		}
	}

}
