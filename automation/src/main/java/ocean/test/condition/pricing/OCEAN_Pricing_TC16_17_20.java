package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.PricingModulePages;

/**
 * OCEAN_Pricing_TC_16_17 class automates Ocean Pricing module Test Condition 14
 * and 18, which holds 4 Test Case; Test Condition Description : Editing of
 * Pricing, options, surcharges, deductibles in mater price sheet
 * 
 * @author Nainsi Jain
 */
public class OCEAN_Pricing_TC16_17_20 extends PricingModulePages {

	@Test(priority = 3, groups = "regression", dataProvider = "fetchDataForTC16_17", dataProviderClass = PricingDataProvider.class, description = "Editing of Pricing, options, surcharges, deductibles in mater price sheet")
	public void editingandVerifyPriceSheet(String[] inputArray) throws Exception {
		//// value field
		boolean valueFlag = false;
		//// surcharge field
		boolean surchargesFlag = false;
		//// options field
		boolean optionsFlag = false;
		//// deductible field
		boolean deductiblesFlag = false;
		// go to pricing tab
		if (inputArray[5].toLowerCase().equals("y")) {
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			//// Search Price sheet code
			selectPriceSheet(inputArray[0]);
			try {
				waitForSomeTime(3);
				click("scrollContractsListUp", 6);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// apply filters to edit price sheet
			applyAllMandatoryFiltersUnderDisplayPriceSheet(inputArray);
			//// return before edit data
			HashMap<String, String> beforEditingPSValues = returnPriceSheetResultGridData();
			// edit selected price values
			editSelectedPriceValues();
			// go to pricing tab
			waitForSomeTime(10);
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			//// Search Price sheet code
			selectPriceSheet(inputArray[0]);
			// apply filters to edit price sheet
			applyAllMandatoryFiltersUnderDisplayPriceSheet(inputArray);
			//// return after edit data
			HashMap<String, String> AfterEditingPSValues = returnPriceSheetResultGridData();
			// compare both values
			if (Float.parseFloat(beforEditingPSValues.get("COST")) + 10 == Float
					.parseFloat(AfterEditingPSValues.get("COST"))) {
				valueFlag = true;
			}
		} else
			valueFlag = true;
		if (inputArray[6].toLowerCase().equals("y")) {
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			//// Search Price sheet code
			selectPriceSheet(inputArray[0]);
			try {
				waitForSomeTime(3);
				click("scrollContractsListUp", 6);
			} catch (Exception e) {
				// TODO: handle exception
			}
			HashMap<String, String> beforEditingOptionsValues = returnOptionsActualBreakdownGridData(true);
			waitForSomeTime(10);
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			HashMap<String, String> afterEditingOptionsValues = returnOptionsActualBreakdownGridData(false);
			if (Float.parseFloat(beforEditingOptionsValues.get("Actual_Breakdown")) + 100 == Float
					.parseFloat(afterEditingOptionsValues.get("Actual_Breakdown"))) {
				optionsFlag = true;
			}
		} else
			valueFlag = true;
		if (inputArray[7].toLowerCase().equals("y")) {
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			//// Search Price sheet code
			selectPriceSheet(inputArray[0]);
			try {
				waitForSomeTime(3);
				click("scrollDisplayPSPageDown", 6);
			} catch (Exception e) {
				// TODO: handle exception
			}
			HashMap<String, String> beforEditingSurchargesValues = returnSurchargesActualBreakdownGridData(true);
			waitForSomeTime(10);
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			HashMap<String, String> afterrEditingSurchargesValues = returnSurchargesActualBreakdownGridData(false);
			if (Float.parseFloat(beforEditingSurchargesValues.get("Actual_Breakdown")) + 100 == Float
					.parseFloat(afterrEditingSurchargesValues.get("Actual_Breakdown"))) {
				surchargesFlag = true;
			}
		} else
			valueFlag = true;

		if (inputArray[8].toLowerCase().equals("y")) {
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			//// Search Price sheet code
			selectPriceSheet(inputArray[0]);
			try {
				waitForSomeTime(3);
				click("scrollDisplayPSPageDown", 6);
			} catch (Exception e) {
				// TODO: handle exception
			}
			HashMap<String, String> beforeEditingDeductibleValues = returnDeductiblesActualBreakdownGridData(true);
			waitForSomeTime(10);
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			HashMap<String, String> afterEditingDeductiblevalues = returnDeductiblesActualBreakdownGridData(false);
			if (Float.parseFloat(beforeEditingDeductibleValues.get("Actual_Breakdown")) + 100 == Float
					.parseFloat(afterEditingDeductiblevalues.get("Actual_Breakdown"))) {
				deductiblesFlag = true;
			}
		} else
			valueFlag = true;
		assertEquals(valueFlag == surchargesFlag == optionsFlag == deductiblesFlag == true, false);
	}
}