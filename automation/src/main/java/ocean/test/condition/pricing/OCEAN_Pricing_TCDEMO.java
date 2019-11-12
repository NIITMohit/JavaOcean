package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;

public class OCEAN_Pricing_TCDEMO extends CommonFunctions {

	// @Test(priority = 1)
	public void clonePriceSheet() throws Exception {
		String newProgramCode = "SNETest";
		String actualProgramCode = "SNE";
		// Click Pricing Tab
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");
		//// Verify in case new Program code already exist
		try {
			// Type newProgramCode
			type("typeProgramCode", newProgramCode);
			// Verify Program code = newProgramCode exists in search results, if exists
			// delete the same
			String priceSheet = getValue("getPriceSheetCode");
			click("getPriceSheetCode");
			// right click on price sheet to delete
			rightClick("getPriceSheetCode");
			// Click Delete to delete price sheet
			click("clickDeleteToDeletePS");
			// Click Ok, to confirm delete ,
			click("clickOK");
			click("clickOK");
		} catch (Exception e) {
			//// DO nothing
		}

		//// Cloning a pricesheet
		// Type Actual Program code
		type("typeProgramCode", actualProgramCode);
		// Click actual program code
		click("getPriceSheetCode");
		// right click on price sheet to clone
		rightClick("getPriceSheetCode");
		// Click Clone as Sub-Master
		click("clickCloneToClonePS");
		// Enter Program Code and Name
		type("typeNewPSName", newProgramCode);
		type("typeNewPSCode", newProgramCode);
		// Click Save to Save Price Sheet
		click("clickSave");
		// Verify Price Sheet is successfully created
		// search for new price sheet
		type("typeProgramCode", newProgramCode);
		String priceSheet = getValue("getPriceSheetCode");
		assertEquals(priceSheet, newProgramCode);
	}

	@Test(priority = 1)
	public void editPriceSheet() throws Exception {
		String actualProgramCode = "SNE";
		click("clickCancellationTab");
		// Click Pricing Tab
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");
		// Type ProgramCode
		type("typeProgramCode", actualProgramCode);
		// Click actual program code
		click("getPriceSheetCode");
		// right click on price sheet to clone
		rightClick("getPriceSheetCode");
		// Click Display/Edit Price Sheet
		click("clickEditAndDeletePriceSheet");
		//// Apply Filters
		// Select COVERAGE
		type("selectCoverage", "Powertrain");
		// Select Class
		type("selectClass", "2");
		// Select Term
		type("selectTerm", "12/12");
		// Select Milage_Band
		type("selectMilage_Band", "0-80");
		// Get Premium Value
		String initialCost = getValue("getPremium");
		// Update Premium
		click("getPremium");
		// Right click to edit premium
		rightClick("getPremium");
		// Select edit
		click("selectEdit");
		// Select Replace
		click("selectReplace");
		// select operator
		typeKeys("selectOperator", "+");
		// typeKeys("selectOperator", "Enter");
		// Select Cost Field
		click("selectCost");
		// Type Cost
		type("typeCost", "10");
		// click save
		click("clickSaveEditPSROw");
		// Apply filters again to verify data
		click("clickCancellationTab");
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");
		// Type ProgramCode
		type("typeProgramCode", actualProgramCode);
		// Click actual program code
		click("getPriceSheetCode");
		// right click on price sheet to clone
		rightClick("getPriceSheetCode");
		// Click Display/Edit Price Sheet
		click("clickEditAndDeletePriceSheet");
		//// Apply Filters
		// Select COVERAGE		
		type("selectCoverage", "Powertrain");
		// Select Class
		type("selectClass", "2");
		// Select Term
		type("selectTerm", "12/12");
		// Select Milage_Band
		type("selectMilage_Band", "0-80");
		// Get new Premium Value
		String finalCost = getValue("getPremium");
		if (Integer.parseInt(finalCost) + 10 == Integer.parseInt(initialCost)) {
			boolean flag = true;
			assertEquals(flag, "true");
		} else {
			fail();
		}
	}

}
