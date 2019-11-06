package ocean.test.condition.Pricing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;

/**
 * OCEAN_Pricing_TC02 class automates Ocean Pricing module Test Condition 02,
 * which holds 1 Test Case ; Test Condition Description : Validate uploaded price
 * sheet for their unique program code.
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
		type("typeNameOfNewPS", "Automation Price Sheet");
		click("clickBrowse");
		//// Call send keys func
		String priceSheetPath = currentDir + "\\Repository\\PriceSheetAutomation.xlsx";
		//D:\Git\Automation\Automation\automation\Repository
		//D:\Git\Automation\Automation\automationRepository\PriceSheetAutomation.xlsx
		type("priceSheetUploadPath", priceSheetPath);
		click("clickOpenbutton");
		type("typeCodeOfNewPS", "SNE");
		click("clickOK");
		String validationMessage = getValue("validationOfDuplicatePriceSheet");
		takeScreenshot();
		click("priceSheetOK");
		click("clickCancelButton");
		assertEquals(validationMessage, "Price sheet with same Program name or code already exists. ");

	}
}
