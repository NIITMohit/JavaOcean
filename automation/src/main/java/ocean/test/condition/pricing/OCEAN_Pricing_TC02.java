package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;

/**
 * OCEAN_Pricing_TC02 class automates Ocean Pricing module Test Condition 02,
 * which holds 1 Test Case ; Test Condition Description : Validate uploaded
 * price sheet for their unique program code.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC02 extends CommonFunctions {

	/**
	 * This function automates test case 01 for test condition 02; Test Case
	 * description : Validate that uploaded price sheet should have their unique
	 * program code by Ocean.
	 * 
	 */
	@Test(priority = 1, groups = "sanity")
	public void uniquePriceSheetName() throws Exception {
		//// Upload a price sheet
		// Click Pricing Tab
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");
		// Click Import
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
		// check for validation message
		String validationMessage = getValue("validationOfDuplicatePriceSheet");
		// take screenshot of validation message
		takeScreenshot();
		// click ok to close validation
		click("priceSheetOK");
		// click cancel button to close dialogue box
		click("clickCancelButton");
		// verifying message actual, expected
		assertEquals(validationMessage, "Price sheet with same Program name or code already exists. ");

	}
}
