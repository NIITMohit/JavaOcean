package ocean.modules.pages;

import ocean.modules.database.ComplianceDataBase;

/**
 * This is object class which contains all pages of compliance modules
 * 
 * @author Mohit Goel
 */
public class ComplianceModulePages extends ComplianceDataBase {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
	}
}
