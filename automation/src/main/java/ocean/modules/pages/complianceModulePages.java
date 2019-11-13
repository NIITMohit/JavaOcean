package ocean.modules.pages;

import ocean.common.CommonFunctions;

/**
 * This is object class which contains all pages of compliance modules
 * 
 * @author Mohit Goel
 */
public class complianceModulePages extends CommonFunctions {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
	}
}
