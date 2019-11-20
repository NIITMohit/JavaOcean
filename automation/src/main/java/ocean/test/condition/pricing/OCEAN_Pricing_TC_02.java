package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.modules.pages.pricingModulePages;

/**
 * OCEAN_Pricing_TC02 class automates Ocean Pricing module Test Condition 02,
 * which holds 1 Test Case ; Test Condition Description : Validate uploaded
 * price sheet for their unique program code.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC_02 extends pricingModulePages {

	/**
	 * This function automates test case 01 for test condition 02; Test Case
	 * description : Validate that uploaded price sheet should have their unique
	 * program code by Ocean.
	 * 
	 */
	@Test(priority = 1, groups = "sanity", description = "Validate that uploaded price sheet should have their unique program code by Ocean.")
	public void validateOnlyUniquePriceSheetToBeUploaded() throws Exception {
		//// Upload a price sheet
		// Click Pricing Tab and navigate to pricing tab
		goToPricingTab();
		visitPriceSheetListTab();
		// Click Import
		//// Import A price sheet
		importPriceSheet("SNE");
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
