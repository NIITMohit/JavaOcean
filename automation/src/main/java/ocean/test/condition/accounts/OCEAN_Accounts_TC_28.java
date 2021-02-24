package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC_28 class automates Ocean Accounts Test_Condition_Ref _28
 * which holds 1 Test Case; Test Condition Description :Validate that OCEAN
 * displays LW price sheet from pricing module only for user selection, when
 * working on LW setup for dealer.
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Accounts_TC_28 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 27; Test Case
	 * description : Validate correct message when mandatory fields for LW setup are
	 * not filled.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, description = "Validate correct message when mandatory fields for LW setup are not filled.")
	public void validateCorrectErrorForLWSetup() throws Exception {
		// create data to fill required values in search window
		boolean matchFlag = false;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		// Navigate to Accounts tab
		goToAccountsTab();
		// Navigate to Accounts Search tab
		goToAccountsSearchTab();
		// Put excel data to hashmap
		uiSearchData.put("role_type", "Dealer");
		uiSearchData.put("status", "Active");
		// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		// To click display button to navigate to account details screen
		clickDisplayButton(0);
		// setup a LW price sheet
		waitForSomeTime(20);
		clickForSetUpLWPricing();
		waitForSomeTime(20);
		HashMap<Integer, HashMap<String, String>> progcodeWithLW = getProgCodeWithLW();
		// enter mandatory values for LW Setup
		enterValuesForLWSetup(progcodeWithLW.get(1).get("pcode"));
		// click on Yes button to create new one PS
		click("clickOnLWSetupSearchButton");
		String errorMsg = "Please fill all details";
		// Validate error message
		String actualMsg = getAttributeValue("lwSetupErrorInfoMsg", "Name");
		System.out.print(actualMsg);
		// Take screenshot to validate error message
		takeScreenshot();

		// click on OK button
		click("clickOnLWSetupOKButton");
		assertEquals(actualMsg, errorMsg);
		enterValuesForLWSetup("OLW");
		// click on Yes button to create new one PS
		String actualMsg1 = null;
		try {
			actualMsg1 = getAttributeValue("lwSetupPriceSheetInfoMsg", "Name");
			if (actualMsg1.contains("No pricesheet found"))  {
				matchFlag = true;
				click("clickOnLWSetupOKButton");
			}
		} catch (Exception e) {
			matchFlag = true;
		}
		assertEquals(matchFlag, true);

	}

	@AfterClass(alwaysRun = true)
	public void collapse() throws Exception {
		removeErrorMessages();
	}
}
