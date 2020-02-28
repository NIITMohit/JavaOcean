package ocean.modules.pages;

import static org.testng.Assert.assertFalse;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;

import ocean.common.ObjectRepo;
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
	 * This function is used to click display button to navigate to account details
	 * screen
	 * 
	 * 
	 */
	public void selectClassificationList() throws Exception {
		click("clickOnCheckbox", 0);
		rightClick("clickOnCheckbox");
	}

	/**
	 * This function is use to clone Price Sheet
	 * 
	 * 
	 */
	public void selectCloneClassfication(HashMap<String, String> priceSheetName) throws Exception {
		selectClassificationList();
		click("clickOnClonePriceSheet");
		click("typeSubMasterPriceSheetNameForCloning");
		type("typeSubMasterPriceSheetNameForCloning", priceSheetName.get("PriceSheetName"));
		click("clickOnSaveclonePriceSheet");
		try {
			click("clickOnSaveclonePriceSheet");
		} catch (Exception e) {
			// do nothing
		}

	}

	/**
	 * This function is use or validate to clone Price Sheet
	 * 
	 * 
	 */
	public void validateClonePriceSheet(HashMap<String, String> priceSheetName) throws Exception {
		click("typeForFilterClassificationList");
		type("typeForFilterClassificationList", priceSheetName.get("PriceSheetName"));
		click("listOfClassificationList");
	}

	/**
	 * This function is use to export or delete the price sheet
	 * 
	 * 
	 */
	public void exportClassificationPriceSheet() throws Exception {
		click("listOfClassificationList");
		selectClassificationList();
		click("clickForExportClassification");
		click("clickOK");

		// click("clickTocloseMSExcel");
	}

	/**
	 * This function is use to that priceSheet is delete or not
	 * 
	 */
	public void validateDeleteClassificationPriceSheet(String locator) {
		try {
			int count = windowsDriver.findElements(ObjectRepo.fetchOR(locator)).size();
			if (count != 0) {
				assertFalse(true, "Pricesheet is not visible");
			}
		} catch (NoSuchElementException e) {
			System.out.println("Price sheet is not visible");
		}
	}

	/**
	 * This function is use to delete the ClassificationList Pricesheet
	 * 
	 * 
	 */
	public void deleteClassificationPriceSheet(String priceSheet) throws Exception {
		type("typeForFilterClassificationList", priceSheet);
		selectClassificationList();
		click("clickForDeleteClassification");
		click("clickOnYesForDeleteClassification");
	}

	/**
	 * This function is use to edit classification list for turbo
	 * 
	 * 
	 */
	public void editClassificationListForTurbo(HashMap<String, String> turboOption) throws Exception {

		click("clickForEditTurboDiesel");
		if (turboOption.get("Turbo").toLowerCase().equals("y")) {
			click("clickCheckBoxOfTurbo");
			click("clickSaveForTurboDiesel");
		}
		for (int i = 0; i < 4; i++) {
			try {
				click("clickOK");
			} catch (Exception e) {
				break;
				// // do nothing
			}
		}
	}

	/**
	 * This function is use to edit classification list for Diesel
	 * 
	 * 
	 */
	public void editClassificationListForDiesel(HashMap<String, String> dieselOption) throws Exception {
		click("clickForEditTurboDiesel");
		if (dieselOption.get("Diesel").toLowerCase().equals("y")) {
			click("clickCheckBoxOfDiesel");
			click("clickSaveForTurboDiesel");
		}

		for (int i = 0; i < 2; i++) {
			try {
				click("clickOK");
			} catch (Exception e) {
				break;
				// // do nothing
			}
		}

	}

	/**
	 * This function is use to check that file is download or not
	 * 
	 * 
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) throws Exception {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();
		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag = true;
		}
		return flag;
	}

	/**
	 * This function is use to get value of clone price sheet
	 * 
	 * 
	 */
	public String getValueofClonePriceSheet() throws Exception {
		return getTextOfElement("listOfClassificationList");
	}

	/**
	 * This function is use to delete the download file
	 * 
	 * 
	 */
	public void deleteExportFile(String deletePath) throws Exception {
		try {
			// creates a file instance
			File file = new File(deletePath);
			// deletes the file when JVM terminates
			file.deleteOnExit();
			System.out.println("Done");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function is use to select the price for turbo option
	 * 
	 * 
	 */
	public void selectPricesheetForTurbo(HashMap<String, String> turboOption) throws Exception {
		click("listOfClassificationList");
		type("typeForFilterClassificationList", turboOption.get("ClassificationListForTurbo"));
		selectClassificationList();
	}

	/**
	 * This function is use to select the price for diesel option
	 * 
	 * 
	 */
	public void selectPricesheetForDiesel(HashMap<String, String> dieselOption) throws Exception {
		click("listOfClassificationList");
		type("typeForFilterClassificationList", dieselOption.get("ClassificationListForDiesel"));
		selectClassificationList();
	}

	public void uncheckedEditTurboCheckbox() throws Exception {
		selectClassificationList();
		click("clickForEditTurboDiesel");
		click("clickCheckBoxOfTurbo");
		click("clickSaveForTurboDiesel");
	}

	public void uncheckedEditDieselCheckbox() throws Exception {
		selectClassificationList();
		click("clickForEditTurboDiesel");
		click("clickCheckBoxOfDiesel");
		click("clickSaveForTurboDiesel");
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
	 * This function is used search a price sheet based on search paramater
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
	 * This function is used to edit selected product under Display PS window.
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
	 * This function is used to edit options values under Display Price Sheet Window
	 */
	public void editSelectedOptionsValues() {
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
	 * This function is used to return price sheet cost value in map after PS
	 * editing, to be verified from search result grid.
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
	 * This function is used to return options actual breakdown value in map after
	 * PS editing, to be verified from search result grid
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
	 * after PS editing, to be verified from search result grid.
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
	 * This function is used to apply filters on selected PS under display PS
	 * window.
	 */

	public void applyAllMandatoryFiltersUnderDisplayPriceSheet(String[] inputArray) throws Exception {
		if (inputArray[1].length() > 0)
			type("selectCoverage", inputArray[1]);
		if (inputArray[2].length() > 0)
			type("selectMilage_Band", inputArray[2]);
		if (inputArray[3].length() > 0)
			type("selectClass", inputArray[3]);
		if (inputArray[4].length() > 0)
			type("selectTerm", inputArray[4]);
	}

	/**
	 * This function is used to select pricesheet using search parameter as code
	 * under Pricing module
	 */

	public void selectPriceSheet(String inputArray) throws Exception {
		type("typeProgramCode", inputArray);
		waitForSomeTime(2);
		click("getPriceSheetCode");
		rightClick("getPriceSheetCode");
		click("clickDisplayAndEditPriceSheet");
	}

}
