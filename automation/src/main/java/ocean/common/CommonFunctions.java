package ocean.common;

import ocean.com.main.Suite;
import ocean.modules.pages.underwritingModulePages;

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
	 * This common function is used to go to underwriting tab
	 * 
	 * @return
	 * 
	 */
	public void goToUnderWritingTab() {
		try {
			click("clickUnderWritingTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to pricing tab
	 * 
	 */
	public void goToPricingTab() {
		try {
			click("clickPricingTab");
		} catch (Exception e) {
			throw e;
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
