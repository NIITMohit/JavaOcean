package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;
import ocean.modules.pages.pricingModulePages;

/**
 * OCEAN_Pricing_TC01 class automates Ocean Pricing module Test Condition 01,
 * which holds 1 Test Case; Test Condition Description : Validate price sheet
 * content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC01 extends pricingModulePages {

	/**
	 * This function automates test case 01 for test condition 01; Test Case
	 * description : Post import of Price sheet, Content should be matched from the
	 * given price sheet under Display Price sheet tab inside Price section.
	 * 
	 */
	@Test(priority = 1, groups = "sanity", description = "Post import of Price sheet, Content should be matched from the given price sheet under Display Price sheet tab inside Price section.")
	public void ImportPriceSheetAndVerify() throws Exception {
		//// Upload a price sheet
		String newProgramCode = "SNZ";
		// Click Pricing Tab
		goToPricingTab();
		// Click Price Sheet List Tab
		visitPriceSheetListTab();
		//// Check if price Sheet Exist, delete the same
		deletePriceSheet(newProgramCode);
		// Click Pricing Tab
		// Click Pricing Tab
		goToPricingTab();
		// Click Price Sheet List Tab
		visitPriceSheetListTab();
		//// Import A price  sheet
	    importPriceSheet(newProgramCode);
	    //// validate price sheet is successfully imported
	    String priceSheet = searchPriceSheet(newProgramCode);
		assertEquals(priceSheet, newProgramCode);
		//// DB validation go be added
	}
}
