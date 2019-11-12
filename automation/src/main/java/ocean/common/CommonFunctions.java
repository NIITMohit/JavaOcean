package ocean.common;

import ocean.com.main.Suite;
import ocean.object.condition.underwriting.underwritingObjectMainScreen;

/**
 * This class contains common functions, which is used all over scripts, to save
 * manual efforts and LOC
 * 
 * @author Mohit Goel
 */
public class CommonFunctions extends Suite {

	/**
	 * This common function is used to search a contract
	 * 
	 * @param contract id on which contract needs to be searched
	 */
	public void searchContract(String contractId) {
		click("clickCancellationTab");
		click("clickMailservice");
		click("clickClear");
		type("typeContractId", contractId);
		click("searchContractButton");
	}

	/**
	 * This common function is used delete a price sheet based on search paramater
	 * 
	 * @param priceSheet on which priceSheet needs to be searched
	 */
	public void deletePriceSheet(String priceSheet) {
		try {
			// Type newProgramCode
			type("typeProgramCode", priceSheet);
			// Verify Program code = newProgramCode exists in search results, if exists
			// delete the same
			click("getPriceSheetCode");
			// right click on price sheet to delete
			rightClick("getPriceSheetCode");
			// Click Delete to delete price sheet
			click("clickDeleteToDeletePS");
			// Click Ok, to confirm delete ,
			click("clickOK");
			click("clickOK");
		} catch (Exception e) {
			//// DO nothing
		}

	}

	/**
	 * This common function is used to go to underwriting tab
	 * 
	 * @return
	 * 
	 */
	public underwritingObjectMainScreen goToUnderWritingTab() {
		try {
			click("clickUnderWritingTab");
		} catch (Exception e) {
			//// DO nothing
		}
		return new underwritingObjectMainScreen();
	}

	/**
	 * This common function is used to go to pricing tab
	 * 
	 */
	public void goToPricingTab() {
		try {
			click("clickPricingTab");
		} catch (Exception e) {
			//// DO nothing
		}

	}

	/**
	 * This common function is used to generate random string of length n
	 * 
	 */
	public String randomString(int n) {
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();

	}
}
