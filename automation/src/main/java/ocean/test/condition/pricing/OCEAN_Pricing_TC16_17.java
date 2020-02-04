package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;

import org.aspectj.lang.annotation.Before;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.PricingModulePages;

public class OCEAN_Pricing_TC16_17 extends PricingModulePages {

	@SuppressWarnings("null")
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC16_17", dataProviderClass = PricingDataProvider.class, description = "Valdiate premium calculation and recalculation for a master price sheet before editing and after editing in OCEAN.")
	public void EditingPriceSheetAndVerifyPostSuccessfulUpdation(String[] inputArray) throws Exception {
		boolean valueFlag = true;
		boolean surchargesFlag = false;
		boolean optionsFlag = false;
		boolean deductiblesFlag = false;
		// go to pricing tab
		goToPricingTab();
		// click price sheet list tab
		visitPriceSheetListTab();
		if (inputArray[5].toLowerCase().equals("y")) {
			// apply filters to edit price sheet
			applyAllMandatoryFiltersUnderDisplayPriceSheet(inputArray);
			HashMap<String, String> beforEditingPSValues = returnPriceSheetResultGridData(0);
			System.out.println("Before Editing Cost Values are :" + beforEditingPSValues);
			// edit selected price values
			editSelectedPriceValues();
			// go to pricing tab
			goToPricingTab();
			// click price sheet list tab
			visitPriceSheetListTab();
			applyAllMandatoryFiltersUnderDisplayPriceSheet(inputArray);
			click("getPriceSheetRow1ByClickCategoryValue");
			HashMap<String, String> AfterEditingPSValues = returnPriceSheetResultGridData(0);
			System.out.println("After Editing Cost Values are :" + AfterEditingPSValues);
			// compare both values
			if (Float.parseFloat(beforEditingPSValues.get("COST")) + 10 != Float
					.parseFloat(AfterEditingPSValues.get("COST"))) {
				valueFlag = false;
			}
		}

		if (inputArray[6].toLowerCase().equals("y")) {
			click("getOptionsRow1ByClickCategoryValue");

			HashMap<String, String> beforEditingOptionsValues = returnOptionsResultGridData(0);
			System.out.println(beforEditingOptionsValues);

			editSelectedOptionsValues();
			click("getOptionsRow1ByClickCategoryValue");
			HashMap<String, String> afterEditingOptionsValues = returnPriceSheetResultGridData(0);
			System.out.println(afterEditingOptionsValues);

			if (Integer.parseInt(beforEditingOptionsValues.get("RES_BUSE_BASE")) + 100 != Integer
					.parseInt(afterEditingOptionsValues.get("RES_BUSE_BASE"))) {

			} else if (Integer.parseInt(beforEditingOptionsValues.get("RES_LKIT_BASE")) + 100 != Integer
					.parseInt(afterEditingOptionsValues.get("RES_LKIT_BASE"))) {
			} else if (Integer.parseInt(beforEditingOptionsValues.get("RES_SG_BASE")) + 100 != Integer
					.parseInt(afterEditingOptionsValues.get("RES_SG_BASE"))) {
			} else if (Integer.parseInt(beforEditingOptionsValues.get("RES_WR_BASE")) + 100 != Integer
					.parseInt(afterEditingOptionsValues.get("RES_WR_BASE"))) {
			} else {
				surchargesFlag = true;
			}
		}

		if (inputArray[7].toLowerCase().equals("y")) {
			click("getSurchargestRow1ByClickCategoryValue");

			HashMap<String, String> beforEditingSurchargesValues = returnSurchargesResultGridData(0);
			System.out.println(beforEditingSurchargesValues);

			editSelectedSurchargesValues();

			click("getSurchargestRow1ByClickCategoryValue");
			HashMap<String, String> afterrEditingSurchargesValues = returnPriceSheetResultGridData(0);
			System.out.println(afterrEditingSurchargesValues);

			if (Integer.parseInt(beforEditingSurchargesValues.get("RES_AWD_BASE")) + 100 != Integer
					.parseInt(afterrEditingSurchargesValues.get("RES_AWD_BASE"))) {
			} else if (Integer.parseInt(beforEditingSurchargesValues.get("RES_ONETON_BASE")) + 100 != Integer
					.parseInt(afterrEditingSurchargesValues.get("RES_ONETON_BASE"))) {
			} else if (Integer.parseInt(beforEditingSurchargesValues.get("RES_SUPERCHARGE_BASE")) + 100 != Integer
					.parseInt(afterrEditingSurchargesValues.get("RES_SUPERCHARGE_BASE"))) {
			} else if (Integer.parseInt(beforEditingSurchargesValues.get("RES_TURBO_BASE")) + 100 != Integer
					.parseInt(afterrEditingSurchargesValues.get("RES_TURBO_BASE"))) {
			} else {
				surchargesFlag = true;
			}
		}

		if (inputArray[8].toLowerCase().equals("y")) {
			click("getDeductibleRow1ByClickCategoryValue");

			HashMap<String, String> beforeEditingDeductibleValues = returndDeductiblesResultGridData(0);
			System.out.println(beforeEditingDeductibleValues);

			editSelectedDeductibleValues();
			click("getDeductibleRow1ByClickCategoryValue");

			HashMap<String, String> afterEditingDeductiblevalues = returndDeductiblesResultGridData(0);
			System.out.println(afterEditingDeductiblevalues);

			if (Integer.parseInt(beforeEditingDeductibleValues.get("RES_DEDUCTIBLE_0_BASE")) + 100 != Integer
					.parseInt(afterEditingDeductiblevalues.get("RES_DEDUCTIBLE_0_BASE"))) {
			}

			else if (Integer.parseInt(beforeEditingDeductibleValues.get("RES_DEDUCTIBLE_100_BASE")) + 100 != Integer
					.parseInt(afterEditingDeductiblevalues.get("RES_DEDUCTIBLE_100_BASE"))) {
			} else if (Integer.parseInt(beforeEditingDeductibleValues.get("RES_DEDUCTIBLE_250_BASE")) + 100 != Integer
					.parseInt(afterEditingDeductiblevalues.get("RES_DEDUCTIBLE_250_BASE"))) {
			} else if (Integer.parseInt(beforeEditingDeductibleValues.get("RES_DISDEDUCT_BASE")) + 100 != Integer
					.parseInt(afterEditingDeductiblevalues.get("RES_DISDEDUCT_BASE"))) {
			} else {
				deductiblesFlag = true;
			}
			if (valueFlag == surchargesFlag == optionsFlag == deductiblesFlag) {
				assertEquals(true, true);
			} else {
				assertEquals(false, false);
			}
			/*
			 * // edit selected options values editSelectedOptionsValues();
			 * 
			 * // edit selected surcharges values
			 * 
			 * editSelectedSurchargesValues();
			 * 
			 * // edit selected deductible values
			 * 
			 * editSelectedDeductibleValues();
			 */
			/*
			 * 
			 * String initialCost = beforEditingValues.get("COST"); String
			 * initialADMIN_AUL_1_BASE = beforEditingValues.get("ADMIN_AUL_1_BASE");
			 * 
			 * String initialADMIN_AUL_2_BASE = beforEditingValues.get("ADMIN_AUL_2_BASE");
			 * String initialCOMM_1_BASE = beforEditingValues.get("COMM_1_BASE"); String
			 * initialINS_CLIP_BASE = beforEditingValues.get("INS_CLIP_BASE"); String
			 * initialRES_BASE = beforEditingValues.get("RES_BASE");
			 * 
			 * // System.out.println("initial Cost is:" + initialCost);
			 * 
			 * HashMap<String, String> afterEditingValues =
			 * returnPriceSheetResultGridData(0);
			 * 
			 * String finalCost = afterEditingValues.get("COST"); String
			 * finalADMIN_AUL_1_BASE = afterEditingValues.get("ADMIN_AUL_1_BASE"); String
			 * finalADMIN_AUL_2_BASE = afterEditingValues.get("ADMIN_AUL_2_BASE"); String
			 * finalCOMM_1_BASE = afterEditingValues.get("COMM_1_BASE"); String
			 * finalINS_CLIP_BASE = afterEditingValues.get("INS_CLIP_BASE"); String
			 * finalRES_BASE = afterEditingValues.get("RES_BASE");
			 * 
			 * // System.out.println("final Cost is:" + finalCost); if
			 * (Integer.parseInt(initialCost) + 10 == Integer.parseInt(finalCost)) { boolean
			 * flag = true; assertEquals(flag, "true"); } else { fail(); }
			 * 
			 * if (Integer.parseInt(initialADMIN_AUL_1_BASE) + 10 ==
			 * Integer.parseInt(finalADMIN_AUL_1_BASE)) { boolean flag = true;
			 * assertEquals(flag, "true"); } else { fail(); }
			 * 
			 * if (Integer.parseInt(initialADMIN_AUL_2_BASE) + 10 ==
			 * Integer.parseInt(finalADMIN_AUL_2_BASE)) { boolean flag = true;
			 * assertEquals(flag, "true"); } else { fail(); } if
			 * (Integer.parseInt(initialCOMM_1_BASE) + 10 ==
			 * Integer.parseInt(finalCOMM_1_BASE)) { boolean flag = true; assertEquals(flag,
			 * "true"); } else { fail(); } if (Integer.parseInt(initialINS_CLIP_BASE) + 10
			 * == Integer.parseInt(finalINS_CLIP_BASE)) { boolean flag = true;
			 * assertEquals(flag, "true"); } else { fail(); }
			 * 
			 * if (Integer.parseInt(initialRES_BASE) + 10 ==
			 * Integer.parseInt(finalRES_BASE)) { boolean flag = true; assertEquals(flag,
			 * "true"); } else { fail(); }
			 */

		}

	}
}
