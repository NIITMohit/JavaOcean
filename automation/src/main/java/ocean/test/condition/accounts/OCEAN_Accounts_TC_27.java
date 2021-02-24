package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC27 class automates Ocean Accounts module Test Condition 27,
 * which holds 1 Test Case; Test Condition Description : Validate that OCEAN
 * displays correct message for user, when mandatory fields for Limited warranty
 * (LW) setup are not filled.
 * 
 * 
 * @author Nainsi Jain
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Accounts_TC_27 extends AccountsModulePages {

	/**
	 * This function automates all test cases for test condition 27; Test Case
	 * description : Validate correct message when mandatory fields for LW setup are
	 * not filled.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC27", dataProviderClass = AccountsDataProvider.class, description = "Validate correct message when mandatory fields for LW setup are not filled.")
	public void validateCorrectErrorForLWSetup(String[] inputArray) throws Exception {
		// create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		// Navigate to Accounts tab
		goToAccountsTab();
		// Navigate to Accounts Search tab
		goToAccountsSearchTab();
		// Put excel data to hashmap
		uiSearchData.put("role_type", inputArray[0]);
		uiSearchData.put("status", inputArray[1]);
		// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		// To click display button to navigate to account details screen
		clickDisplayButton(0);
		// setup a LW price sheet
		waitForSomeTime(20);
		clickForSetUpLWPricing();
		waitForSomeTime(20);
		// enter mandatory values for LW Setup
		enterValuesForLWSetup(inputArray[2]);
		// click on Yes button to create new one PS
		click("clickOnLWSetupYesButton");
		// click on new button to get error for validation
		click("clickOnLWSetupNewButton");
		// Validate error message
		String actualMsg = getAttributeValue("clickclickPleasefillalldetails", "Name");
		// Take screenshot to validate error message
		takeScreenshot();
		String errorMsg = "Please enter all the required fields";
		// click on OK button
		click("clickOnLWSetupOKButton");
		assertEquals(actualMsg, errorMsg);
	}
}
