package ocean.modules.pages;

import java.util.HashMap;

import ocean.modules.database.PricingDataBase;

/**
 * This is object class which contains all pages of pricing modules
 * 
 * @author Mohit Goel
 */
public class PricingModulePages extends PricingDataBase {
	/**
	 * This function is used delete a price sheet based on search paramater
	 * 
	 * @param priceSheet on which priceSheet needs to be searched
	 */
	public void deletePriceSheet(String priceSheet) {
		try {
			String searchPriceSheet = searchPriceSheet(priceSheet);
			if (searchPriceSheet.toLowerCase().equals(priceSheet.toLowerCase())) {
				click("getPriceSheetCode");
				// right click on price sheet to delete
				rightClick("getPriceSheetCode");
				// Click Delete to delete price sheet
				click("clickDeleteToDeletePS");
				// Click Ok, to confirm delete ,
				click("clickOK");
				click("clickOK");
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * This function is used to return actual breakdown value in map after PS
	 * editing, to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnActualBreakdownGridData(int i) throws Exception {

		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("Actual_Breakdown", getValue("getActualBreakdown"));
		return searchData;

	}

	/**
	 * This common function is used delete a price sheet based on search paramater
	 * 
	 * @param priceSheet on which priceSheet needs to be searched
	 */
	public void importPriceSheet(String newPriceSheetCode) {
		try {
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
			type("typeCodeOfNewPS", newPriceSheetCode);
			// CLick ok
			click("clickOK");
			//// Post successful upload verify newprice sheet is uploaded
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * This common function is used search a price sheet based on search paramater
	 * 
	 * @param priceSheet on which priceSheet needs to be searched
	 */
	public String searchPriceSheet(String newPriceSheetCode) {
		String priceSheet = "";
		type("typeProgramCode", newPriceSheetCode);
		// Verify Program code = newProgramCode exists in search results,
		priceSheet = getValue("getPriceSheetCode");
		return priceSheet;
	}

	/**
	 * This common function is used to apply mandatory filter under Display Price
	 * Sheet Window
	 */
	/*
	 * public void applyAllMandatoryFiltersUnderDisplayPriceSheet() { try {
	 * type("selectCoverage", "Powertrain"); // Select Class type("selectClass",
	 * "2"); // Select Term type("selectTerm", "24/24"); // Select Milage_Band
	 * type("selectMilage_Band", "0-60");
	 * 
	 * click("getRow1ByClickCategoryValue"); rightClick("selectEdit"); } catch
	 * (Exception e) { throw e; }
	 * 
	 * }
	 */
	/**
	 * This common function is used to apply mandatory filter for price under
	 * Display Price Sheet Window
	 */
	public void editSelectedPriceValues() {
		click("listOfCost");
		rightClick("listOfCost");
		click("selectEdit");
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
		// RES_BASE
	}

	/**
	 * This common function is used to get actual breakdown post editing values for
	 * options, surcharges and deductibles
	 */
	public void afterEditGetActualBreakdownValue() {
		click("getRow1ByClickOnCategoryValue");
		rightClick("getRow1ByClickOnCategoryValue");
		click("selectEdit");

	}

	/**
	 * This common function is used to edit options values under Display Price Sheet
	 * Window
	 */
	public void editSelectedOptionsValues() {
		click("getOptionsRow1ByClickCategoryValue");
		rightClick("getOptionsRow1ByClickCategoryValue");
		// Click on Edit selected rows
		click("selectEdit");
		// select operator
		click("selectOptionsReplace");
		// typeKeys("selectOperator", "Enter");
		typeKeys("selectOptionsOperator", "+");
		// Select Cost Field
		click("selectOptionsCost");
		// Type Cost
		type("typeOptionsCost", "100");
		// click save
		click("clickSaveEditPSROw");
	}

	/**
	 * This common function is used to edit surcharges values under Display Price
	 * Sheet Window
	 */
	public void editSelectedSurchargesValues() {
		click("getSurchargesRow1ByClickCategoryValue");
		rightClick("getSurchargesRow1ByClickCategoryValue");
		// Click on Edit rows
		click("selectEdit");
		// select operator
		click("selectSurchargesReplace");
		// typeKeys("selectOperator", "Enter");
		typeKeys("selectSurchargesOperator", "+");
		// Select Cost Field
		click("selectSurchargesCost");
		// Type Cost
		type("typeSurchargesCost", "100");
		// click save
		click("clickSaveEditPSROw");
	}

	/**
	 * This common function is used to edit deductible values under Display Price
	 * Sheet Window
	 */
	public void editSelectedDeductibleValues() {
		click("getDeductibleRow1ByClickCategoryValue");
		rightClick("getDeductibleRow1ByClickCategoryValue");
		// Click on Edit rows
		click("selectEdit");
		// select operator
		click("selectDeductiblesReplace");
		// typeKeys("selectOperator", "Enter");
		typeKeys("selectDeductiblesOperator", "+");
		// Select Cost Field
		click("selectDeductiblesCost");
		// Type Cost
		type("typeDeductiblesCost", "100");
		// click save
		click("clickSaveEditPSROw");
	}

	/**
	 * This function is used to return price sheet data in map after PS editing, to
	 * be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnPriceSheetResultGridData() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		// save COST
		searchData.put("COST", getValue("listOfCost"));
		return searchData;

	}

	/**
	 * This function is used to return optins actual breakdown value in map after PS
	 * editing, to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnOptionsActualBreakdownGridData(boolean edit) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		click("getOptionsRow1ByClickCategoryValue");
		rightClick("getOptionsRow1ByClickCategoryValue");
		click("selectEdit");
		// save Actual Breakdown
		searchData.put("Actual_Breakdown", getValue("getActualBreakdown"));
		if (edit == true) {
			editSelectedOptionsValues();
		} else {
			click("clickOnCloseButton");
		}
		return searchData;
	}

	/**
	 * This function is used to return Surcharges actual breakdown value in map
	 * after PS editing, to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnSurchargesActualBreakdownGridData(boolean edit) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		click("getSurchargesRow1ByClickCategoryValue");
		rightClick("getSurchargesRow1ByClickCategoryValue");
		click("selectEdit");
		// save Actual Breakdown
		searchData.put("Actual_Breakdown", getValue("getActualBreakdown"));
		if (edit == true) {
			editSelectedSurchargesValues();
		} else {
			click("clickOnCloseButton");
		}
		return searchData;
	}

	/**
	 * This function is used to return Deductible actual breakdown value in map
	 * after PS editing, to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnDeductiblesActualBreakdownGridData(boolean edit) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		click("getDeductibleRow1ByClickCategoryValue");
		rightClick("getDeductibleRow1ByClickCategoryValue");
		click("selectEdit");
		// save Actual Breakdown
		searchData.put("Actual_Breakdown", getValue("getActualBreakdown"));
		if (edit == true) {
			editSelectedDeductibleValues();
		} else {
			click("clickOnCloseButton");
		}
		return searchData;

	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */

	public void applyAllMandatoryFiltersUnderDisplayPriceSheet(String[] inputArray) throws Exception {
		if (inputArray[1].length() > 1)
			type("selectCoverage", inputArray[1]);
		if (inputArray[2].length() > 1)
			type("selectMilage_Band", inputArray[2]);
		if (inputArray[3].length() > 1)
			type("selectClass", inputArray[3]);
		if (inputArray[4].length() > 1)
			type("selectTerm", inputArray[4]);
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */

	public void selectPriceSheet(String inputArray) throws Exception {
		type("typeProgramCode", inputArray);
		waitForSomeTime(2);
		click("getPriceSheetCode");
		rightClick("getPriceSheetCode");
		click("clickDisplayAndEditPriceSheet");
	}

}
