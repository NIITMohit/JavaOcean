package ocean.test.condition.Pricing;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;

/**
 * OCEAN_Pricing_TC03 class automates Ocean Pricing module Test Condition 03,
 * which holds 4 Test Case; Test Condition Description : Validate various status
 * of price sheet (Master and sub master both) with issuance of contract for
 * them.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC03 extends CommonFunctions {

	/**
	 * This function automates test case 01 for test condition 03; Test Case
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
		

	}
}
