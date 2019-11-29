package ocean.modules.pages;

import ocean.modules.database.AccountsDataBase;

/**
 * This is object class which contains all pages of search modules
 * 
 * @author Mohit Goel
 */
public class AccountsModulePages extends AccountsDataBase {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
	}
}
