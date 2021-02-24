package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC_13 class automates Ocean Accounts module Test Condition 13
 * which holds 3 Test Case; Test Condition Description : Validate price sheet
 * name is unique, when auto assigned/input by OCEAN user at:1. Master level 2.
 * Sub Master Level 3. Account level
 * 
 * @author Nainsi Jain
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Account_TC_13 extends AccountsModulePages {
	@Test(priority = 3, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC013", dataProviderClass = AccountsDataProvider.class, description = "Validate a unique price sheet name is generated, when a price sheet is generated")
	public void validateUniqueNameOfPS(String[] inputArray) throws Exception {
		// // create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		// // Navigate to mail service tab
		goToAccountsTab();
		goToAccountsSearchTab();
		uiSearchData.put("Role_Type", inputArray[0]);
		uiSearchData.put("Status", inputArray[1]);
		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		// To click display button to navigate to account details screen
		selectTopAccountOnTheBasisOfRoleType();
		waitForSomeTime(2);
		clickForSetUpNewPricing();
		waitForSomeTime(2);
		try {
			click("scrollContractsListUp", 7);
		} catch (Exception e) {
		}
		if (uiSearchData.get("Role_Type").toLowerCase().equals("dealer"))
			uiSearchData.put("Payee", "100");
		// Verify payee is disabled for Agent or Enable for lender
		verifyAndSelectPayee(uiSearchData);
		typePriceSheetCodeMaster("", uiSearchData.get("Role_Type"));
		// Click on priceSheet internal name textbox
		String internalName = randomString(20);
		// Click on priceSheet internal name textbox
		type("pricesheetName", internalName);
		// To assign new PS with unique name to check duplicacy
		click("assignPriceSheet");
		click("clickOK");
		click("assignPriceSheet");
		String actualMsg = getAttributeValue("clickPricesheetaleadyexits", "Name");
		String errorMsg = "Pricesheet aleady exits";
		for (int i = 0; i < 2; i++) {
			try {
				click("clickOK");
			} catch (Exception e) {
				break;
				// TODO: handle exception
			}
		}
		assertEquals(actualMsg, errorMsg);
	}
}
