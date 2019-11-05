package ocean.test.condition.Pricing;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;

/**
 * OCEAN_Pricing_TC01 class automates Ocean Pricing module Test Condition 01,
 * which holds 1; Test Case Test Condition Description : Validate price sheet
 * content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC01 extends CommonFunctions {

	/**
	 * This function automates test case 01 for test condition 01; Test Case
	 * description : Post import of Price sheet, Content should be matched from the
	 * given price sheet under Display Price sheet tab inside Price section.
	 * 
	 */
	@Test(priority = 1, groups = "sanity")
	public void ImportPriceSheet() throws Exception {
		//// Upload a price sheet
		// Click Pricing Tab
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");
		// Click Import
		click("clickImportButton");

	}
}
