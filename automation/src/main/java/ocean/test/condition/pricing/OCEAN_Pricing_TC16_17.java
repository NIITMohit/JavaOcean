package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.PricingModulePages;

public class OCEAN_Pricing_TC16_17 extends PricingModulePages {

	@Test(priority = 3, groups = "regression", dataProvider = "fetchDataForTC16_17", dataProviderClass = PricingDataProvider.class, description = "Valdiate premium calculation and recalculation for a master price sheet before editing and after editing in OCEAN.")
	public void editingPriceSheetAndVerifyPostSuccessfulUpdation(String[] inputArray) throws Exception {
		boolean valueFlag = false;
		boolean surchargesFlag = false;
		boolean optionsFlag = false;
		boolean deductiblesFlag = false;
		// go to pricing tab
		goToPricingTab();
		// click price sheet list tab
		visitPriceSheetListTab();
		selectPriceSheet(inputArray[0]);
		if (inputArray[5].toLowerCase().equals("y")) {
			// apply filters to edit price sheet
			applyAllMandatoryFiltersUnderDisplayPriceSheet(inputArray);
			HashMap<String, String> beforEditingPSValues = returnPriceSheetResultGridData();
			// edit selected price values
			editSelectedPriceValues();
			// go to pricing tab
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			applyAllMandatoryFiltersUnderDisplayPriceSheet(inputArray);
			HashMap<String, String> AfterEditingPSValues = returnPriceSheetResultGridData();
			// compare both values
			if (Float.parseFloat(beforEditingPSValues.get("COST")) + 10 == Float
					.parseFloat(AfterEditingPSValues.get("COST"))) {
				valueFlag = true;
			}
		} else
			valueFlag = true;
		if (inputArray[6].toLowerCase().equals("y")) {
			HashMap<String, String> beforEditingOptionsValues = returnOptionsActualBreakdownGridData();
			editSelectedOptionsValues();
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			HashMap<String, String> afterEditingOptionsValues = returnOptionsActualBreakdownGridData();
			if (Float.parseFloat(beforEditingOptionsValues.get("Actual_Breakdown")) + 100 == Float
					.parseFloat(afterEditingOptionsValues.get("Actual_Breakdown"))) {
				optionsFlag = true;
			}
		} else
			valueFlag = true;
		click("scrollDisplayPSPageDown", 6);
		if (inputArray[7].toLowerCase().equals("y")) {
			HashMap<String, String> beforEditingSurchargesValues = returnSurchargesActualBreakdownGridData();
			editSelectedSurchargesValues();
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			HashMap<String, String> afterrEditingSurchargesValues = returnSurchargesActualBreakdownGridData();
			if (Float.parseFloat(beforEditingSurchargesValues.get("Actual_Breakdown")) + 100 == Float
					.parseFloat(afterrEditingSurchargesValues.get("Actual_Breakdown"))) {
			}
			surchargesFlag = true;
		} else
			valueFlag = true;
		if (inputArray[8].toLowerCase().equals("y")) {
			HashMap<String, String> beforeEditingDeductibleValues = returnDeductiblesActualBreakdownGridData();
			editSelectedDeductibleValues();
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			selectPriceSheet(inputArray[0]);
			HashMap<String, String> afterEditingDeductiblevalues = returnDeductiblesActualBreakdownGridData();
			if (Float.parseFloat(beforeEditingDeductibleValues.get("Actual_Breakdown")) + 100 == Float
					.parseFloat(afterEditingDeductiblevalues.get("Actual_Breakdown"))) {
				deductiblesFlag = true;
			}
		} else
			valueFlag = true;
		assertEquals(valueFlag == surchargesFlag == optionsFlag == deductiblesFlag == true, false);
	}
}
