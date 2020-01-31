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
		click("getPriceSheetRow1ByClickCategoryValue");
		waitForSomeTime(2);

		try {
			rightClick("getPriceSheetRow1ByClickCategoryValue");
		} catch (Exception e) {
			throw e;
		}
		click("selectEdit");
		// RES_BASE
		typeKeysWithDoubleClick("selectOperatorResBase", "+");
		// doubleClick("selectOperatorResBase");
		// type("selectOperatorResBase", "+");
		/*
		 * // ADMIN_AUL_1_BASE typeKeysWithDoubleClick("selectOperatorAdminAul1Base",
		 * "+"); // INS_CLIP_BASE typeKeysWithDoubleClick("selectOperatorInsClipBase",
		 * "-"); // ADMIN_AUL_2_BASE
		 * typeKeysWithDoubleClick("selectOperatorAdminAul2Base", "+"); // COMM_1_BASE
		 * typeKeysWithDoubleClick("selectOperatorComm1Base", "+"); // type Cost
		 * RES_BASE value
		 */
		doubleClick("selectCostResBase");
		type("selectCostResBase", "10");
		/*
		 * typeWithDoubleClick("selectCostResBase", "25"); // type Cost ADMIN_AUL_1_BASE
		 * value typeWithDoubleClick("selectCostAdminAul1Base", "50"); // type Cost
		 * ADMIN_AUL_2_BASE value typeWithDoubleClick("selectCostAdminAul2Base", "15");
		 * // type Cost INS_CLIP_BASE value typeWithDoubleClick("selectCostInsClipBase",
		 * "25"); // type Cost COMM_1_BASE value
		 * typeWithDoubleClick("selectCostComm1Base", "100");
		 */
		try {
			click("clickSaveEditPSROw");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to edit options values under Display Price Sheet
	 * Window
	 */
	public void editSelectedOptionsValues() {

		click("getOptionsRow1ByClickCategoryValue");
		rightClick("getOptionsRow1ByClickCategoryValue");
		click("selectEdit");
		doubleClick("selectOperatorResBase");
		type("selectOperatorResBase", "+");
		doubleClick("selectCostResBase");
		type("selectCostResBase", "100");
		click("clickSaveEditPSROw");

	}

	/**
	 * This common function is used to edit surcharges values under Display Price
	 * Sheet Window
	 */
	public void editSelectedSurchargesValues() {
		click("scrollDisplayPSPageDown");

		click("getSurchargesRow1ByClickCategoryValue");
		try {
			rightClick("getSurchargesRow1ByClickCategoryValue");
		} catch (Exception e) {
			throw e;
		}
		click("selectEdit");
		doubleClick("selectOperatorResBase");
		type("selectOperatorResBase", "+");
		doubleClick("selectCostResBase");
		type("selectCostResBase", "100");
		click("clickSaveEditPSROw");

	}

	/**
	 * This common function is used to edit deductible values under Display Price
	 * Sheet Window
	 */
	public void editSelectedDeductibleValues() {
		click("scrollDisplayPSPageDown");
		click("getDeductibleRow1ByClickCategoryValue");
		try {
			rightClick("getDeductibleRow1ByClickCategoryValue");
		} catch (Exception e) {
			throw e;
		}
		click("selectEdit");
		doubleClick("selectOperatorResBase");
		type("selectOperatorResBase", "+");
		doubleClick("selectCostResBase");
		type("selectCostResBase", "100");
		click("clickSaveEditPSROw");
	}

	/**
	 * This function is used to return price sheet data in map after PS editing, to
	 * be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnPriceSheetResultGridData(int i) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		/*
		 * //// save CATEGORY_VALUE searchData.put("CATEGORY_VALUE",
		 * getValue("listOfCategoryValue", i).trim()); //// save COVERAGE_TYPE
		 * searchData.put("COVERAGE_TYPE", getValue("listOfCoverageType", i).trim());
		 * //// save COVERAGE searchData.put("COVERAGE", getValue("listOfCoverage",
		 * i).trim()); //// save MILEAGE_BAND searchData.put("MILEAGE_BAND",
		 * getValue("listOfMileageBand", i).trim()); //// save MILEAGE_FROM
		 * searchData.put("MILEAGE_FROM", getValue("listOfMileageFrom", i).trim()); ////
		 * save MILEAGE_TO searchData.put("MILEAGE_TO", getValue("listOfMileageTo",
		 * i).trim()); //// save CLASS searchData.put("CLASS", getValue("listOfClass",
		 * i).trim()); //// Save TERM searchData.put("TERM", getValue("listOfTerm",
		 * i).trim()); //// save TERM_MONTHS searchData.put("TERM_MONTHS",
		 * getValue("listOfTermMonths", i).trim());
		 */
		//// save COST
		searchData.put("COST", getValue("listOfCost", i).trim());
		/*
		 * //// save ADMIN_AUL_1_BASE searchData.put("ADMIN_AUL_1_BASE",
		 * getValue("listOfAdminAul1Base", i).trim()); //// save ADMIN_AUL_2_BASE
		 * searchData.put("ADMIN_AUL_2_BASE", getValue("listOfAdminAul2Base",
		 * i).trim()); //// save COMM_1_BASE searchData.put("COMM_1_BASE",
		 * getValue("listOfComm1Base", i).trim()); //// save INS_CLIP_BASE
		 * searchData.put("INS_CLIP_BASE", getValue("listOfInsClipBase", i).trim());
		 * //// save RES_BASE searchData.put("RES_BASE", getValue("listOfResBase",
		 * i).trim());
		 */
		return searchData;

	}

	/**
	 * This function is used to return options data in map after optins editing, to
	 * be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnOptionsResultGridData(int i) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("RES_BUSE_BASE", getValue("listOfRES_BUSE_BASE", i).trim());
		searchData.put("RES_LKIT_BASE", getValue("listOfRES_LKIT_BASE", i).trim());
		searchData.put("RES_SG_BASE", getValue("listOfRES_SG_BASE", i).trim());
		searchData.put("RES_WR_BASE", getValue("listOfRES_WR_BASE", i).trim());
		return searchData;

	}

	/**
	 * This function is used to return options data in map after Surcharges editing,
	 * to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnSurchargesResultGridData(int i) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("RES_AWD_BASE", getValue("listOfRES_AWD_BASE", i).trim());
		searchData.put("RES_ONETON_BASE", getValue("listOfRES_ONETON_BASE", i).trim());
		searchData.put("RES_SUPERCHARGE_BASE", getValue("listOfRES_SUPERCHARGE_BASE", i).trim());
		searchData.put("RES_TURBO_BASE", getValue("listOfRES_TURBO_BASE", i).trim());
		return searchData;

	}

	/**
	 * This function is used to return options data in map after Surcharges editing,
	 * to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returndDeductiblesResultGridData(int i) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("RES_DEDUCTIBLE_0_BASE", getValue("listOfRES_DEDUCTIBLE_0_BASE", i).trim());
		searchData.put("RES_DEDUCTIBLE_100_BASE", getValue("listOfRES_DEDUCTIBLE_100_BASE", i).trim());
		searchData.put("RES_DEDUCTIBLE_250_BASE", getValue("listOfRERES_DEDUCTIBLE_250_BASE", i).trim());
		searchData.put("RES_DISDEDUCT_BASE", getValue("listOfRES_DISDEDUCT_BASE", i).trim());
		return searchData;

	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void applyAllMandatoryFiltersUnderDisplayPriceSheet(String[] inputArray) throws Exception {
		type("typeProgramCode", inputArray[0]);
		waitForSomeTime(2);
		click("getPriceSheetCode");
		waitForSomeTime(5);

		try {

			rightClick("getPriceSheetCode");
		} catch (Exception e) {
			throw e;
		}
		waitForSomeTime(2);

		try {
			click("clickDisplayAndEditPriceSheet");
		} catch (Exception e) {
			throw e;
		}

		type("selectCoverage", inputArray[1]);
		type("selectMilage_Band", inputArray[2]);
		type("selectClass", inputArray[3]);
		type("selectTerm", inputArray[4]);
		/*
		 * for (@SuppressWarnings("rawtypes") Map.Entry mapElement :
		 * searchParamaters.entrySet()) { String searchParamater = (String)
		 * mapElement.getKey(); String valueToInput = ((String)
		 * mapElement.getValue()).trim(); switch (searchParamater.toLowerCase()) { case
		 * "Code": type("typeProgramCode", valueToInput);
		 * 
		 * 
		 * // String priceSheetcode = getValue("getPriceSheetCode"); //
		 * System.out.println("Price sheet code : " + priceSheetcode);
		 * click("getPriceSheetCode"); rightClick("getPriceSheetCode");
		 * click("clickDisplayAndEditPriceSheet");
		 * 
		 * case "Coverage_Type": type("selectCoverage", valueToInput);
		 * 
		 * case "Mileage_Band": type("selectMilage_Band", valueToInput);
		 * 
		 * case "Class": type("selectClass", valueToInput);
		 * 
		 * case "Term": type("selectTerm", valueToInput); break; default: //// do
		 * nothing } try { //String categoryValue =
		 * getValue("getRow1ByClickCategoryValue");
		 * //System.out.println("category value is : "+ categoryValue);
		 * click("getPriceSheetRow1ByClickCategoryValue");
		 * rightClick("getPriceSheetRow1ByClickCategoryValue"); click("selectEdit"); }
		 * catch (Exception e) { throw e; } }
		 */
	}

}
