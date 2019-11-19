package ocean.modules.pages;

import java.util.HashMap;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * This is object class which contains all pages of pricing modules
 * 
 * @author Mohit Goel
 */
public class pricingModulePages extends CommonFunctions {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
	}
	
	

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
}
