package ocean.common;

import ocean.com.main.Suite;

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
}
