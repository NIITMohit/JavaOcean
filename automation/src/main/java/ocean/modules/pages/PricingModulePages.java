package ocean.modules.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
		waitForSomeTime(3);
		click("clickOnCheckbox", 0);
		rightClick("clickOnCheckbox");

	}

	/**
	 * This function is use to clone Price Sheet
	 * 
	 * 
	 */
	public void selectCloneClassfication(String priceSheetName) throws Exception {
		selectClassificationList();
		click("clickOnClonePriceSheet");
		click("typeSubMasterPriceSheetNameForCloning");
		type("typeSubMasterPriceSheetNameForCloning", priceSheetName);
		click("clickOnSaveclonePriceSheet");
	}

	/**
	 * This function is use or validate to clone Price Sheet
	 * 
	 * 
	 */
	public void validateClonePriceSheet(String priceSheetName) throws Exception {
		click("typeForFilterClassificationList");
		type("typeForFilterClassificationList", priceSheetName);
		click("listOfClassificationList");
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
	 * @return
	 * 
	 */
	public boolean validateDeleteClassificationPriceSheet(String locator) {
		String test = getTextOfElement("listOfClassificationList");
		if (test.length() > 1)
			return false;
		else
			return true;
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
	 * This function is use to delete the download file
	 * 
	 * 
	 */
	public void deleteExportFile(String deletepath) throws Exception {
		try {
			// creates a file instance
			File file = new File(deletepath);
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
	public void selectPricesheetForTurbo(String turboPriceSheet) throws Exception {
		click("listOfClassificationList");
		type("typeForFilterClassificationList", turboPriceSheet);
		selectClassificationList();
	}

	/**
	 * This function is use to select the price for diesel option
	 * 
	 * 
	 */
	public void selectPricesheetForDiesel(String dieselPriceSheet) throws Exception {
		click("listOfClassificationList");
		type("typeForFilterClassificationList", dieselPriceSheet);
		selectClassificationList();
	}

	/**
	 * This function is used to get the value of formType Combobox value
	 */
	public String uiFormTypeValue(String code) {
		type("pricingCode", code);
		click("pricinglistRow");
		waitForSomeTime(5);
		click("formTypeCombo");
		WebElement ele = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list = ele.findElements(By.className("ListBoxItem"));
		List<WebElement> list1 = ele.findElements(By.xpath("//*[@ClassName='ListBoxItem']//*[@ClassName='TextBlock']"));
		String formValue = null;
		for (int i = 0; i < list.size(); i++) {

			for (int j = 0; j < list1.size(); j++) {
				String comboText = list.get(0).getAttribute("HasKeyboardFocus");
				System.out.println(comboText);
				if (comboText.equalsIgnoreCase("True")) {
					formValue = list1.get(0).getText();
					System.out.println(formValue);
					return formValue;
				}
			}
		}
		return formValue;
	}

	/**
	 * This function is used to verify formType Combobox values
	 */
	public boolean uiFormTypeComboValue(String code) {
		boolean matchFlag = false;

		type("pricingCode", code);
		click("pricinglistRow");
		waitForSomeTime(5);
		click("formTypeCombo");
		WebElement ele = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list = ele.findElements(By.xpath("//*[@ClassName='ListBoxItem']//*[@ClassName='TextBlock']"));
		for (WebElement webElement : list) {
			String comboText = webElement.getText();
			System.out.println(comboText);
			if (comboText.equalsIgnoreCase("Selling Account State") || comboText.equalsIgnoreCase("Customer State")) {
				matchFlag = true;
			} else {
				matchFlag = false;
				break;
			}
		}
		// click("pricingCode");
		// click("pricingCodeRemoveFilter");
		waitForSomeTime(5);
		return matchFlag;
	}

	/**
	 * This function is used to change the value of formType Combobox value
	 */
	public String uiChangeFormTypeValue(String code, String dbvalue) {
		// type("pricingCode",code);
		// click("pricinglistRow");
		// waitForSomeTime(5);
		String formType = dbvalue;
		String newFormType = null;
		if (formType.equalsIgnoreCase("Selling Account State")) {
			newFormType = "Customer Account State";
		} else {
			newFormType = "Selling Account State";
		}
		// click("formTypeCombo");
		WebElement ele = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list = ele.findElements(By.xpath("//*[@ClassName='ListBoxItem']//*[@ClassName='TextBlock']"));
		for (WebElement webElement : list) {
			String comboText = webElement.getText();
			if (comboText.equalsIgnoreCase(newFormType)) {
				webElement.click();
			}
		}
		// click("formTypeCombo");
		click("priceListTabSaveBtn");
		waitForSomeTime(5);
		// click("pricingCodeRemoveFilter");
		return formType;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void searchContractGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		click("clickClear");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = ((String) mapElement.getValue()).trim();
			switch (searchParamater) {
			case "CERT":
				type("typeContractId", valueToInput);
				break;
			case "CUSTOMER_FIRST":
				type("typeCustomerFirstName", valueToInput);
				break;
			case "CUSTOMER_LAST":
				type("typeCustomerLastName", valueToInput);
				break;
			case "VIN":
				type("typeVIN", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("searchContractButton");
	}

	/**
	 * This function is used to navigate to NewCancellationTab
	 * 
	 * 
	 */
	public void clickCancelButtonAndNavigateToNewCancellationTab() throws Exception {
		click("clickCancelButton");
	}

	/**
	 * This function is use to verify the view contract
	 * 
	 * 
	 */
	public String verifyCompareContract(Set<String> windowHandles, String OceanPage) throws Exception {
		waitForSomeTime(5);
		//// get All data for search results
		String viewContract = null;
		switchToNewWindow();
		viewContract = getValue("viewContractFormType");
		click("closePS");
		return viewContract;

	}

	/*
	 * function process the detail in compliance page to find latest version row.
	 * 
	 * @throws Exception
	 */
	public void complainceDataSearch(String priceSheetGroup, String state) throws Exception {
		String versionNumber = "";

		goToComplianceTab();
		goToContractBuilderTab();

		clearTextBox("clickOnLibraryLocation");
		type("compliancegroup", priceSheetGroup);
		type("compliancestate", state);
		// type("compliance_product_name",productName);
		click("clickComplianceSearch");
		versionNumber = getPriceSheetGroupVersion(priceSheetGroup, state);
		System.out.println("versionNumber1===" + versionNumber);
		waitForSomeTime(20);
		type("stateFilterRowCompliance", state);
		if (getTextOfElement("dataRowCompliance").length() < 1) {
			clearTextBox("stateFilterRowCompliance");
			type("compliancestate", "Multi-State");
			click("clickComplianceSearch");
			waitForSomeTime(5);
			versionNumber = getPriceSheetGroupVersion(priceSheetGroup, "Multi-State");
			System.out.println("versionNumber2===" + versionNumber);
		}
		waitForSomeTime(15);
		takeScreenshot();
		type("groupNameFilterRowCompliance", priceSheetGroup);
		type("versionFilterRowCompliance", versionNumber);
		waitForSomeTime(15);
		click("dataRowCompliance");
	}

	/**
	 * function process the detail in compliance page to find latest version row.
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> complainceRuleTreeProcessForAllRule(String or1, String cancelFeeTreeItems) {
		HashMap<String, String> getRuleData = new HashMap<String, String>();
		click(or1);

		List<WebElement> treeItems = listOfElements(cancelFeeTreeItems);
		for (int i = 0; i < treeItems.size(); i++) {
			String itemValue = treeItems.get(i).getAttribute("Name");
			clickViaXpath("//*[@Name='" + itemValue
					+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/preceding-sibling::*[@AutomationId='Expander']");

			List<WebElement> selectedTreeItemsList = listOfElementsByXpath("//*[@Name='" + itemValue
					+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/following-sibling::*//*[@ClassName='RadioButton']");

			for (WebElement we : selectedTreeItemsList) {
				if (we.isSelected()) {
					String ruleName = we.getText();
					System.out.println("ruleName===" + ruleName);
					getRuleData.put(itemValue, ruleName);
					System.out.println("ruleMap===" + getRuleData);
//				clickViaXpath("//*[@Name='" + itemValue
//						+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/preceding-sibling::*[@AutomationId='Expander']");
					break;
				}
//			else {
//				clickViaXpath("//*[@Name='" + itemValue
//						+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/preceding-sibling::*[@AutomationId='Expander']");
//				break;
//			}
			}
			clickViaXpath("//*[@Name='" + itemValue
					+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/preceding-sibling::*[@AutomationId='Expander']");

		}
		click(or1);
		return getRuleData;
	}

	/**
	 * This function is used to navigate to fill details on new cancellation tab and
	 * click calculate, if cancelMiles, cancelDate, dateReceived are blank random
	 * values will be saved
	 * 
	 * @param initiatedBy
	 * @param cancelReason
	 * @param cancelMiles,  if blank then it will fetch miles from cancellation
	 *                      screen
	 * @param cancelDate,   if blank current date with be taken in account
	 * @param dateReceived, if blank current date with be taken in account
	 * 
	 * 
	 */
	public void enterValuesOnNewCancellationTabAndClickCalculate(String initiatedBy, String cancelReason,
			String cancelMiles, String cancelDate, String dateReceived) throws Exception {
		type("selectInitiatedBy", initiatedBy);
		type("selectCancelReason", cancelReason);
		if (cancelMiles.length() < 1) {
			String miles = getSalesMiles();
			int milee = 0;
			milee = Integer.parseInt(miles) + 2214;
			cancelMiles = Integer.toString(milee);
		}
		type("enterCancelMiles", cancelMiles);
		if (cancelDate.length() < 1) {
			Format sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			// Add 7 days to current date
			// cal.add(Calendar.DAY_OF_MONTH, 7);
			// Date after adding the days to the current date
			cancelDate = sdf.format(cal.getTime());
		}
		String str = cancelDate;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(str);
		clipboard.setContents(strSel, null);
		click("enterCancelDate");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		if (dateReceived.length() < 1) {
			Format sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			// Add 7 days to current date
			// cal.add(Calendar.DAY_OF_MONTH, 7);
			// Date after adding the days to the current date
			dateReceived = sdf.format(cal.getTime());
		}
		type("enterDateReceived", dateReceived);
		click("clickCalculate");
	}

	/**
	 * This function is used to return rule info view ruleName & ruleGroup summary
	 * data in a hashmap
	 */
	public HashMap<String, String> getRuleInfoRule() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		int count = 1;
		try {
			for (int i = 0; i < 25; i++) {

				WebElement cell_RuleName = findElementByXpath(
						"//*[@AutomationId='Ocean_CancellationModule_NewCancellationRulesViews_RulesView_RulesInfoDataGridControl']//*[@AutomationId='Row_"
								+ i + "']//*[@ClassName='DataCell'][2]");
				String ruleName = cell_RuleName.getText();
				WebElement cell_RuleGroup = findElementByXpath(
						"//*[@AutomationId='Ocean_CancellationModule_NewCancellationRulesViews_RulesView_RulesInfoDataGridControl']//*[@AutomationId='Row_"
								+ i + "']//*[@ClassName='DataCell'][1]");
				String ruleGroup = cell_RuleGroup.getText();
				summaryData.put(ruleGroup, ruleName);
				System.out.println("summaryData====" + summaryData);
				if (count == 5) {
					click("ruleInfoViewScroll");
					click("ruleInfoViewScroll");
					click("ruleInfoViewScroll");
					count = 2;
				}
				count++;
			}
		} catch (Exception e) {
			return summaryData;
		}
		return summaryData;
	}

	/**
	 * This function is used to validate formType value on contract cancellation
	 * Mail Service Tab
	 */
	public String uiContractValidtion(String cert) throws Exception {

		goToCancellationTab();
		goToMailServiceTab();
		// create search data in hash map
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("CERT", cert);
		// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		// navigate to new canceltab
		clickCancelButtonAndNavigateToNewCancellationTab();
		waitForSomeTime(10);
		String uiCancellationValue = getTextOfElement("cancellationFormType");
		return uiCancellationValue;
	}

	/**
	 * This function is used to validate formType value on contract view page
	 */
	@SuppressWarnings("unused")
	public String uiSearchValidtion(String cert) throws Exception {
		goToSearchTab();
		waitForSomeTime(5);
		click("clickContractTab");
		waitForSomeTime(5);
		click("clickClearOnSearch");
		type("searchTypeContract", cert);
		click("clickSearchInSearchPage");
		waitForSomeTime(5);
		click("firstRowChkBox");
		String OceanPage = windowsDriver.getWindowHandle();
		waitForSomeTime(5);
		click("clickCompare");
		waitForSomeTime(10);
		switchToNewWindow();
		String viewContract = getValue("viewContractFormType");
		click("closePS");
		return viewContract;
	}

	/**
	 * This function is used to validate formType value on contract view page
	 */
	public String uiContractWebSearchValidtion(String cert) {

		goToSearchTab();
		// Click Webcontract
		click("WebContractsTab");
		waitForSomeTime(10);
		click("clickClearBtnOnWebContractSearch");
		type("CertWebContract", cert);
		click("listclickWebContractSearch");
		waitForSomeTime(10);
		click("listcheckWebContract", 0);
		click("CompareWebContract");
		waitForSomeTime(15);
		switchToNewWindow();
		String viewWebContractFormType = getTextOfElement("viewWebContractFormType");

		return viewWebContractFormType;

	}

	/**
	 * This function is use to edit classification list for turbo or Diesel used for
	 * checked and unchecked the checkbox
	 * 
	 * 
	 */
	public void editClassificationListForTurboAndDiesel(String turboDieselOption, String value) throws Exception {
		click("clickForEditTurboDiesel");
		switch (turboDieselOption.toLowerCase()) {
		case "turbo":
			click("clickCheckBoxOfTurbo");
			break;
		case "diesel":
			click("clickCheckBoxOfDiesel");
			break;
		}
		click("clickSaveForTurboDiesel");
		for (int i = 0; i < 2; i++) {
			try {
				click("clickOK");
			} catch (Exception e) {
				// // do nothing
			}
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
			type("typeNameOfNewPS", randomString(8));
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
	 * This common function is used delete a price sheet based on search paramater
	 * 
	 * @param priceSheet on which priceSheet needs to be searched
	 */
	public void clonePricesheet(String newPriceSheetCode) {
		try {
			type("typeProgramCode", "SNE");
			click("getPriceSheetCode");
			rightClick("getPriceSheetCode");
			click("clickClonePriceSheet");
			type("typeNameOfNewClonePS", "Automation Price Sheet");
			type("typeCodeOfNewClonePS", newPriceSheetCode);
			// CLick ok
			click("clickSavePS");
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
		try {
			priceSheet = getValue("getPriceSheetCode");
		} catch (Exception e) {
			// TODO: handle exception
		}
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
		click("scrollContractsListDown");
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

	/**
	 * This function is used to modified pricesheet using AND/OR opera under Pricing
	 * module
	 */

	public void modifiedPriceSheetForClassItem() throws Exception {
		type("clickOnANDORTextBox", "AND");
		type("clickOnFieldTextBox", "MAKE");
		type("clickOnOperatorTextBox", "=");
		type("clickOnFieldValueTextbox", "Chevrolet");
		/*
		 * click("clickOnPlusIcon"); type("clickOnANDORTextBox", "OR");
		 * type("clickOnFieldTextBox", "MAKE"); type("clickOnOperatorTextBox", ">");
		 * type("clickOnFieldValueTextbox", "0");
		 */
		click("clickOnSaveExistingClassItem");
		goToClassficationList();
		type("typeMakeName", "Chevrolet");
	}

	/**
	 * This function is used to deleted class item from pricesheet using AND/OR
	 * operator under Pricing module
	 */
	public void deletedClassItemInPriceSheet() throws Exception {
		click("clickOnEditToDeleteClassItem", 3);
		click("clickOnClearButtonToDeleteClassItem");
		click("clickOnSaveButtonForDeleteClassItem");
	}

	/**
	 * This function is used to select ineligible eliiblity crietria
	 * 
	 */
	public void selectInelibleForUnderWritingClass() throws Exception {
		for (int i = 0; i < 3; i++)
			try {
				click("cancelHistoySwipeRight");
			} catch (Exception e) {
				break;
			}
		type("typeIneligibleForUnderwritingClass", "Ineligible");
		click("clickSwipeLeft");
		click("clickSwipeLeft");
		click("editClassificationList");
	}

	/**
	 * This function is used to added class item in price sheet
	 * 
	 */
	public void addedClassItemForUnderwritingClass() throws Exception {
		type("clickNewFieldValueName", randomString(6));
		click("clickAddForNewFieldValue");
		//// Function not working
		//// code will be added when it will work
	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> enterMandatoryValuesOnContract(HashMap<String, String> premiumData)
			throws Exception {
		contractScrollToTop();
		HashMap<String, String> ss = new HashMap<String, String>();
		click("clearContractData");
		//// type unique contract number
		try {
			type("typeContractNumber", randomString(10));
		} catch (Exception e) {
			click("scrollContractsListUp");
			type("typeContractNumber", randomString(10));
		}
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		//// enter purchase date of contract, -10 days from today's date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(1);
		type("purchaseDateForNewContract", dtf.format(localDate).toString());
		//// Enter Primary Account Details
		type("primaryAccountType", premiumData.get("PrimaryAccount"));
		type("primaryAccountId", premiumData.get("DEALERID"));
		click("primaryAccountSearchButton");
		//// Enter Secondary Account Details
		type("secondaryAccountType", premiumData.get("SecondaryAccount"));
		type("secondaryAccountId", premiumData.get("SecondaryAccountId"));
		click("secondaryAccountSearchButton");
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));
		String contractSummary = "0";
		try {
			contractSummary = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (contractSummary.toLowerCase().equals("0"))
			click("vinNumberOverride");
		type("vinNumberMake", premiumData.get("MAKE"));
		type("vinNumberModel", premiumData.get("MODEL"));
		type("vinNumberYear", premiumData.get("YEAR"));
		type("vinNumberMileage", premiumData.get("MILEAGE"));
		type("vinNumberPrice", premiumData.get("VEHICLEPRICE"));
		//// Enter customer information
		type("customerFNAME", "Automation");
		type("customerLNAME", "Testing");
		click("scrollContractsListDown");
		type("customerADD", "Baker Street");
		type("customerZip", "12345");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
		type("selectPricesheet", premiumData.get("PRICESHEETID"));
		waitForSomeTime(5);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		//// Handling for Class
		if (premiumData.get("CLASS") != null) {
			type("getClass", premiumData.get("CLASS"));
		} else {
			String classs = getAttributeValue("getClass", "Value.Value");
			ss.put("CLASS", classs);
		}
		waitForSomeTime(5);
		//// Term for Price sheet handling
		specialclickComboBox("selectPricesheetTerm");
		HashSet<String> termValues = new HashSet<String>();
		termValues.addAll(specialGetAllValuesSaveInSet("getTermValues"));
		if (premiumData.get("TERM") != null) {
			if (termValues.contains(premiumData.get("TERM")))
				type("selectPricesheetTerm", premiumData.get("TERM"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : termValues) {
				try {
					if (string.length() > 0) {
						type("selectPricesheetTerm", string);
						ss.put("TERM", string);
						break;
					} else {
						throw new Exception("no data found");
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");

		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (termValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				try {
					if (string.length() > 0) {
						type("selectPricesheetCoverage", string);
						ss.put("COVERAGE", string);
						break;
					} else {
						throw new Exception("no data found");
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		waitForSomeTime(5);
		System.out.println("compleetd");
		return ss;
	}

	/**
	 * This function is used to get the value of class from UI
	 * 
	 * 
	 */
	public String getClassValueFromUI() throws Exception {
		return getAttributeValue("getClassNameValueFromUI", "Name");
	}

	/**
	 * This function is used to click display button to navigate to account details
	 * screen
	 * 
	 * 
	 */
	public void selectPriceSheetUnderClassficationList(String PriceSheetName) throws Exception {
		type("typeForFilterClassificationList", PriceSheetName);
		click("clickOnCheckbox", 0);
		rightClick("clickOnCheckbox");

	}

	/**
	 * This function is used to get the class value from pricing after modification
	 * the class item
	 * 
	 * @return
	 * 
	 */
	public String getClassValueAfterModification() throws Exception {
		return getAttributeValue("getClassValueAfterModification", "Name");
	}

	/**
	 * This function is used to filer the make model of vehicle
	 * 
	 * @return
	 * 
	 */
	public void clickCorrectMakeModel(String ModelName) throws Exception {
		type("typeMakeName", ModelName);
		click("clickOnEditDisplayButton");
	}

	/**
	 * This function is used to edit Ineligble vehicle
	 * 
	 * @return
	 * 
	 */
	public void editIneligibleVehicle() throws Exception {
		click("clickOnEditToDeleteClassItem", 0);
		click("editIneligibleVehicle", 1);
		type("editIneligibleVehicle", "Contains");
		click("clickOnSaveButtonForDeleteClassItem");

	}

	/**
	 * This function is used to get the class value of ineligible vehicle the class
	 * item
	 * 
	 * @return
	 * 
	 */
	public String getClassOfIneligibleVehicle() throws Exception {
		return getAttributeValue("getClassOfIneligibleVehicle", "Name");
	}

}
