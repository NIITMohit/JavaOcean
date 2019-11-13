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
	public void ImportPriceSheet() throws Exception {
		//// Upload a price sheet
		String newProgramCode = "SNZ";
		// Click Pricing Tab
		goToPricingTab();
		// Click Price Sheet List Tab
		visitPriceSheetListTab();
		//// Check if price Sheet Exist, delete the same
		deletePriceSheet(newProgramCode);
		// Click Pricing Tab
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");
		click("clickImportButton");
		// Type price sheet name
		type("typeNameOfNewPS", "Automation Price Sheet");
		// click browse to upload pricesheet
		click("clickBrowse");
		//// price sheet path, taken at run time, price sheet exist in Repository common
		//// folder
		String priceSheetPath = currentDir + "\\Repository\\PriceSheetAutomation.xlsx";
		// price sheet path
		type("priceSheetUploadPath", priceSheetPath);
		// click open
		click("clickOpenbutton");
		// type price sheet code
		type("typeCodeOfNewPS", "SNE");
		// CLick ok
		click("clickOK");
		//// Post successful upload verify newprice sheet is uploaded
		type("typeProgramCode", newProgramCode);
		// Verify Program code = newProgramCode exists in search results, if exists
		// delete the same
		String priceSheet = getValue("getPriceSheetCode");
		//// Verify pricesheet excel with database
		assertEquals(priceSheet, newProgramCode);
	}
}
