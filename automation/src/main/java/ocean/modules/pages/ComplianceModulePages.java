package ocean.modules.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ocean.common.ObjectRepo;
import ocean.modules.database.ComplianceDataBase;

/**
 * This is object class which contains all pages of compliance modules
 * 
 * @author Mohit Goel
 */
public class ComplianceModulePages extends ComplianceDataBase {
	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void searchContractGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		click("clickComplianceClearAll");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = ((String) mapElement.getValue()).trim();
			switch (searchParamater.toLowerCase()) {
			case "library_location":
				type("compliance_library_location", valueToInput);
				break;
			case "group":
				type("compliancegroup", valueToInput);
				break;
			case "product_name":
				type("compliance_product_name", valueToInput);
				break;
			case "form_number":
				type("complianceform_number", valueToInput);
				break;
			case "document_format":
				type("compliancedocument_format", valueToInput);
				break;
			case "insured_type":
				type("complianceinsured_type", valueToInput);
				break;
			case "state":
				type("compliancestate", valueToInput);
				break;
			case "form_type":
				type("complianceform_type", valueToInput);
				break;
			case "form_usage":
				type("complianceform_usage", valueToInput);
				break;
			case "obligor_type":
				type("complianceobligor_type", valueToInput);
				break;
			case "program_name":
				type("complianceprogram_name", valueToInput);
				break;
			case "version_number":
				type("complianceversion_number", valueToInput);
				break;
			case "state_approved_for_sale":
				type("compliancestate_approved_for_sale", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("clickComplianceSearch");
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> compliance_Search_appendSearchData(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			//// method pending as db data issue
			case 0:
				searchData.put("GroupName", inputArray[i]);
				break;
			case 1:
				searchData.put("State", inputArray[i]);
				break;
			case 2:
				searchData.put("ProgramName", inputArray[i]);
				break;
			case 3:
				searchData.put("ProductName", inputArray[i]);
				break;
			case 4:
				searchData.put("FormType", inputArray[i]);
				break;
			case 5:
				searchData.put("VersionNumber", inputArray[i]);
				break;
			case 6:
				searchData.put("FormNumber", inputArray[i]);
				break;
			case 7:
				searchData.put("FormUsage", inputArray[i]);
				break;
			case 8:
				searchData.put("StateApproved", inputArray[i]);
				break;
			case 9:
				searchData.put("DocumentFormat", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		return searchData;
	}

	/**
	 * This function is used to check contract history on data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> checkContractHistoryDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		click("cancelHistoySwipeLeft");
		summaryData.put("Contract", getValue("checkContractOnCancellationHistory"));
		summaryData.put("Status", getValue("checkStatusOnCancellationHistory"));
//		String processDate = getValue("checkProcessDateOnCancellationHistory");
//		processDate = processDate.length() > 0 ? dateFormat.format(dateFormat.parse(processDate)) : processDate;
//		summaryData.put("Process_Date", processDate);
		summaryData.put("REFUND_PERCENTAGE", getValue("checkRefundOnCancellationHistory"));
		// click("scrollPageDownForCancelTransHistory");
		click("checkContractOnCancellationHistory");
		click("cancelHistoySwipeRight");
		summaryData.put("Net_Refund", getValue("checkNetRefundOnCancellationHistory"));
		summaryData.put("Cancel_Miles", getValue("checkCancelMilesOnCancellationHistory"));
		String cancelDate = getValue("checkCancelDateOnCancellationHistory");
		cancelDate = cancelDate.length() > 0 ? dateFormat.format(dateFormat.parse(cancelDate)) : cancelDate;
		summaryData.put("CANCEL_DATE", cancelDate);
		click("cancelHistoySwipeRight");
		summaryData.put("INITIATED_BY", getValue("checkInitiatedByOnCancellationHistory"));
		summaryData.put("Cancel_Reason", getValue("checkCancelReasonOnCancellationHistory"));
		click("cancelHistoySwipeLeft");
		return summaryData;
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, and Blanks
	 * columns and values
	 * 
	 */
	public HashMap<String, String> compliance_Search_convertDataRemoveStar(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		//// Switch Case to Transform Data
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*") && inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("GroupName", inputArray[i]);
					break;
				case 1:
					searchData.put("State", inputArray[i]);
					break;
				case 2:
					searchData.put("ProgramName", inputArray[i]);
					break;
				case 3:
					searchData.put("ProductName", inputArray[i]);
					break;
				case 4:
					searchData.put("FormType", inputArray[i]);
					break;
				case 5:
					searchData.put("VersionNumber", inputArray[i]);
					break;
				case 6:
					searchData.put("FormNumber", inputArray[i]);
					break;
				case 7:
					searchData.put("FormUsage", inputArray[i]);
					break;
				case 8:
					searchData.put("StateApproved", inputArray[i]);
					break;
				case 9:
					searchData.put("DocumentFormat", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		return searchData;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void searchComplinaceGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		clearTextBox("clickOnLibraryLocation");
		click("clickOnClearAllComplianceButton");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = ((String) mapElement.getValue()).trim();
			switch (searchParamater) {
			case "GroupName":
				type("clickOnGroupType", valueToInput);
				break;
			case "State":
				type("clickOnState", valueToInput);
				break;
			case "ProgramName":
				type("clickOnProgramName", valueToInput);
				break;
			case "ProductName":
				type("clickOnProductName", valueToInput);
				break;
			case "VersionNumber":
				type("clickOnVersionNumber", valueToInput);
				break;
			case "FormNumber":
				type("clickOnFormNumber", valueToInput);
				break;
			case "FromUsage":
				type("clickOnFormUsage", valueToInput);
				break;
			case "StateApproved":
				type("clickOnStatesApproved", valueToInput);
				break;
			case "DocumentFormat":
				type("clickOnDocumentFormat", valueToInput);
				break;
			case "FormType":
				type("clickOnFormType", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		click("clickOnSearchComplianceButton");

	}

	/**
	 * This function is used to return compliance searched data in map, to be
	 * verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnComplianceSearchResultGridData() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("GroupName", getAttributeValue("getGroupNameFirstResult", "Name").trim());
		searchData.put("State", getAttributeValue("getStateFirstResult", "Name").trim());
		// searchData.put("effective_date",
		// getAttributeValue("getEffectiveDateFirstResult", "Name").trim());
		searchData.put("ProgramName", getAttributeValue("getProgramNameFirstResult", "Name").trim());
		searchData.put("ProductName", getAttributeValue("getProductNameFirstResult", "Name").trim());
		searchData.put("Formtype", getAttributeValue("getFormTypeFirstResult", "Name").trim());
		waitForSomeTime(2);
		click("clickOnswipeRight");
		searchData.put("VersionNumber", getAttributeValue("getVersionNumberFirstResult", "Name").trim());
		searchData.put("FormNumber", getAttributeValue("getFormNumberFirstResult", "Name").trim());
		searchData.put("FormUsage", getAttributeValue("getFormUsageFirstResult", "Name").trim());
		// searchData.put("comments", getAttributeValue("getCommentsFirstResult",
		// "Name").trim());
		searchData.put("StateApproved", getAttributeValue("getStatesApprovedForSaleFirstResult", "Name").trim());
		waitForSomeTime(2);
		click("clickOnswipeLeft");
		return searchData;
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
	 * This function is used to get sales miles
	 * 
	 */
	public String getSalesMiles() throws Exception {
		return getValue("getMiles");
	}

	/**
	 * This function is used to return cancellation calculated summary data in a
	 * hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getCancellationDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("REFUND_PERCENTAGE", getValue("cancellationViewRefundPercentageTextBox"));
		summaryData.put("GROSS_REFUND", getValue("cancellationViewGrossRefundTextBox"));
		summaryData.put("CANCEL_FEE_AMOUNT", getValue("cancellationViewCancelFeeTextBox"));
		summaryData.put("AULREFUND", getValue("cancellationViewAULRefundTextBox"));
		summaryData.put("DEALER_REFUND", getValue("cancellationViewDealerFincoPortionTextBox"));
		summaryData.put("CUSTOMER_REFUND", getValue("cancellationViewCustomerRefundTextBox"));

		return summaryData;
	}

	/**
	 * This function is used to return cancellation standard calculated summary data
	 * in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getStandardCancellationDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		String OceanPage = windowsDriver.getWindowHandle();
		String newWindow = "";
		Set<String> winHandles = windowsDriver.getWindowHandles();
		for (String singleWindowHandle : winHandles) {
			newWindow = singleWindowHandle;
			if (!OceanPage.equals(newWindow)) {
				windowsDriver.switchTo().window(newWindow);
			}
		}
		summaryData.put("REFUND_PERCENTAGE", getValue("standViewRefundPercentageTextBox"));
		summaryData.put("GROSS_REFUND", getValue("standViewGrossRefundTextBox"));
		summaryData.put("CANCEL_FEE_AMOUNT", getValue("standViewCancelFeeTextBox"));
		summaryData.put("AULREFUND", getValue("standViewAULRefundTextBox"));
		summaryData.put("DEALER_REFUND", getValue("standViewDealerFincoPortionTextBox"));
		summaryData.put("CUSTOMER_REFUND", getValue("standViewCustomerRefundTextBox"));

		return summaryData;
	}

	/**
	 * function process the detail in cancellation page on rule info view .
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> getComplainceRuleWithRuleInfoViewMap() throws Exception {
		waitForSomeTime(30);
		HashMap<String, String> matchRule = new HashMap<String, String>();
		HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
		// go to standardCalculation
		click("standardCalculation");
		click("okClick");
		HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
		click("standOKbtn");
		// compare standardcalculation and normal calcuted value
		if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
			logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
			logger.info("Verfiying through Rule Info View");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
			for (int i = 1; i < ruleInfoViewValue.size(); i++) {
				for (Entry<String, String> ent : ruleInfoViewValue.get(i).entrySet()) {
					matchRule.put(ent.getKey(), ent.getValue());
				}
			}
		} else {
			logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
			logger.info("Verfiying  through Rule Info View");
		}
		if (matchRule.containsKey("STDNCB")) {
			matchRule.remove("STDNCB");
		}
		if (matchRule.containsKey("STDPRORATE")) {
			matchRule.remove("STDPRORATE");
		}
		return matchRule;
	}

	/**
	 * This function is used to return rule info view summary data in a hashmap
	 * 
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getRuleInfoValue() throws Exception {
		HashMap<Integer, HashMap<String, String>> summaryData = new HashMap<Integer, HashMap<String, String>>();
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
				HashMap<String, String> subValue = new HashMap<String, String>();
				subValue.put(ruleGroup, ruleName);
				summaryData.put(i + 1, subValue);
				if (count == 5) {
					click("ruleInfoViewScroll");
					click("ruleInfoViewScroll");
					click("ruleInfoViewScroll");
					count = 2;
				}
				count++;
			}
		} catch (Exception e) {
			// return summaryData;
		}
		return summaryData;
	}

	/**
	 * function process the detail in cancellation page.
	 * 
	 * @throws Exception
	 */
	public boolean verifyComplainceRulesWithRuleinfoView(HashMap<String, String> ruleMap) throws Exception {
		boolean uiFlag = false;
		waitForSomeTime(30);
		HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
		// go to standardCalculation
		click("standardCalculation");
		try {
			click("FuturePopupClick");
		} catch (Exception e) {
			// do nothing
		}
		click("okClick");
		HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
		click("standOKbtn");
		// compare standardcalculation and normal calcuted value
		if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
			logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
			logger.info("Verfiying through Rule Info View");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
			HashMap<String, String> matchRule = null;
			for (int i = 1; i < ruleInfoViewValue.size(); i++) {
				for (Entry<String, String> ent : ruleInfoViewValue.get(i).entrySet()) {
					matchRule = new HashMap<String, String>();
					matchRule.put(ent.getKey(), ent.getValue());
					if (matchRule.equals(ruleMap)) {
						uiFlag = true;
						break;
					}
				}
			}
		} else {
			logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
			logger.info("Verfiying  through Rule Info View");
		}
		return uiFlag;
	}

	public boolean compareValues(HashMap<Integer, HashMap<String, String>> ruleInfoViewValue,
			HashMap<Integer, HashMap<String, String>> dbruleInfo) {
		boolean matchFlag = false;
		int size = dbruleInfo.size();
		for (int i = 1; i < size; i++) {

			Map<String, String> uiMap1 = dbruleInfo.get(i);
			if (!ruleInfoViewValue.containsValue(uiMap1)) {
				matchFlag = false;
				break;
			} else {
				matchFlag = true;
				continue;
			}
		}
		return matchFlag;
	}

	public HashMap<Integer, HashMap<String, String>> getRuleInfoValueWithResult() throws Exception {
		HashMap<Integer, HashMap<String, String>> summaryData = new HashMap<Integer, HashMap<String, String>>();
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
				WebElement cell_RuleResult = findElementByXpath(
						"//*[@AutomationId='Ocean_CancellationModule_NewCancellationRulesViews_RulesView_RulesInfoDataGridControl']//*[@AutomationId='Row_"
								+ i + "']//*[@ClassName='DataCell'][3]");
				String ruleResult = cell_RuleResult.getText();

				HashMap<String, String> subValue = new HashMap<String, String>();
				subValue.put(ruleGroup + " " + ruleName, ruleResult);
				summaryData.put(i + 1, subValue);
				if (count == 5) {
					click("ruleInfoViewScroll");
					click("ruleInfoViewScroll");
					click("ruleInfoViewScroll");
					count = 2;
				}
				count++;
			}
		} catch (Exception e) {
		}
		return summaryData;
	}

	/**
	 * This function is used to fetch all search data details for row i
	 * 
	 * 
	 */
	public List<String> getSearchResult(int i) throws Exception {
		List<String> uiList = new ArrayList<String>();
		uiList.add(getValue("listOfversion", i));
		return uiList;
	}

	public static HashMap<String, String> sortByValue(HashMap<String, String> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, String>> list = new LinkedList<Map.Entry<String, String>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, String> temp = new LinkedHashMap<String, String>();
		for (Map.Entry<String, String> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	/**
	 * getValue keyword , this function is used to get text/value of locator
	 *
	 * @param unique identifier to locate object
	 * @return the text/value of locator
	 */
	public List<String> getAllValuesSaveInList(String locator) {
		List<String> abc = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			try {
				waitForSomeTime(5);
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				@SuppressWarnings("unchecked")
				// Find list of web elements
				List<WebElement> listWebElement = windowsDriver.findElements(ObjectRepo.fetchOR(locator));
				for (WebElement webElement : listWebElement) {
					// get value and return the same
					abc.add(webElement.getAttribute("Name"));
				}
				logger.info("getAllValuesSaveInList on" + locator + " is successful");
				break;
			} catch (Exception e) {
				logger.info("getAllValuesSaveInList on" + locator + " is successful");
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
		return abc;
	}

	/**
	 * function process the detail in compliance page to find latest version row.
	 * 
	 * @throws Exception
	 */
	public void complainceDataProcess(String priceSheetGroup, String state) throws Exception {
		HashMap<String, String> dbMap = null;
		String versionNumber = "";
		goToComplianceTab();
		goToContractBuilderTab();
		waitForSomeTime(5);
		click("clickComplianceClearAll");
		clearTextBox("clickOnLibraryLocation");
		type("compliancegroup", priceSheetGroup);
		type("compliancestate", state);
		click("clickComplianceSearch");
		dbMap = getPriceSheetGroupVersionAndFormNumber(priceSheetGroup, state);
		versionNumber = dbMap.get("Version_Number");
		waitForSomeTime(2);
		type("stateFilterRowCompliance", state);
		if (getTextOfElement("dataRowCompliance").length() < 1) {
			clearTextBox("stateFilterRowCompliance");
			type("compliancestate", "Multi-State");
			click("clickComplianceSearch");
			waitForSomeTime(29);
			dbMap = getPriceSheetGroupVersionAndFormNumber(priceSheetGroup, "Multi-State");
			versionNumber = dbMap.get("Version_Number");
		}
		waitForSomeTime(2);
		type("groupNameFilterRowCompliance", priceSheetGroup);
		type("versionFilterRowCompliance", versionNumber);
		waitForSomeTime(2);
		click("dataRowCompliance");
	}

	/**
	 * function fetch the applied rule in compliance page.
	 */
	public HashMap<String, String> complainceDefaultRuleTreeProcess(WebElement we, String ruleGroup) {
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		String ruleUsed = we.getText();
		ruleMap = new HashMap<String, String>();
		ruleMap.put(ruleGroup, ruleUsed);
		return ruleMap;
	}

	/**
	 * function fetch the applied rule in compliance page.
	 */
	public HashMap<String, String> complainceDefaultRuleTreeProcess(HashMap<String, String> excelMap) {

		HashMap<String, String> ruleMap = new HashMap<String, String>();
		String ruleGroup = "";
		if (excelMap.get("Rule_Type").equals("CancelFee")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("cancelFeeInTreeCompliance");
			switch (ruleSubType) {
			case "Flat Cancel Period":
				doubleClick("FlatCancelPeriodExpenderCompliance");
				ruleGroup = getTextOfElement("FlatCancelPeriodExpenderCompliance");
				List<WebElement> flatCancelPeriods = listOfElements("lisofFlatCancelRuleCompliance");
				for (WebElement we : flatCancelPeriods) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("FlatCancelPeriodExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period":
				doubleClick("cancelFeeAfterFlatCancelPeriodExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriod = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriod) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period, Administrator":
				doubleClick("cancelFeeAfterFlatCancelPeriod_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriod_AdminExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriod_Admin = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriod_AdminRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriod_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriod_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period, Lender, Trade/Sale":
				doubleClick("cancelFeeAfterFlatCancelPeriod_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriod_LTSExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriod_LTS = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriod_LTSRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriod_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriod_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period if Claims":
				doubleClick("cancelFeeAfterFlatCancelPeriodIfClaimsExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodIfClaimsExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriodIfClaims = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodIfClaimsRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriodIfClaims) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodIfClaimsExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period if Claims, Administrator":
				doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriodIfClaims_Admin = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodIfClaims_AdminRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriodIfClaims_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale":
				doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriodIfClaims_LTS = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodIfClaims_LTSRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriodIfClaims_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Override":
				doubleClick("cancelFeeOverdueExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeOverdueExpenderCompliance");
				List<WebElement> cancelFeeOverdue = listOfElements("lisofCancelFeeOverdueRuleCompliance");
				for (WebElement we : cancelFeeOverdue) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeOverdueExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period":
				doubleClick("cancelFeeWithInFlatCancelPeriodExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithInFlatCancelPeriodExpenderCompliance");
				List<WebElement> cancelFeeWithInFlatCancelPeriod = listOfElements(
						"lisofcancelFeeWithInFlatCancelPeriodCompliance");
				for (WebElement we : cancelFeeWithInFlatCancelPeriod) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithInFlatCancelPeriodExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period, Administrator":
				doubleClick("cancelFeeWithInFlatCancelPeriod_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithInFlatCancelPeriod_AdminExpenderCompliance");
				List<WebElement> cancelFeeWithInFlatCancelPeriod_Admin = listOfElements(
						"lisofCancelFeeWithInFlatCancelPeriod_AdminRuleCompliance");
				for (WebElement we : cancelFeeWithInFlatCancelPeriod_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithInFlatCancelPeriod_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale":
				doubleClick("cancelFeeWithinFlatCancelPeriod_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriod_LTSExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriod_LTS = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriod_LTSRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriod_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriod_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period if Claims":
				doubleClick("cancelFeeWithinFlatCancelPeriodIfClaimsExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriodIfClaimsExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriodIfClaims = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriodIfClaimsRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriodIfClaims) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriodIfClaimsExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period if Claims, Administrator":
				doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriodIfClaims_Admin = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriodIfClaims_AdminRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriodIfClaims_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale":
				doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriodIfClaims_LTS = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriodIfClaims_LTSRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriodIfClaims_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Refund Percent")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("refundPercentInTreeCompliance");
			switch (ruleSubType) {
			case "Refund Percent":
				doubleClick("refundPercentExpenderCompliance");
				ruleGroup = getTextOfElement("refundPercentExpenderCompliance");
				List<WebElement> refundPercent = listOfElements("lisofrefundPercentCompliance");
				for (WebElement we : refundPercent) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("refundPercentExpenderCompliance");
						click("refundPercentInTreeCompliance");
					}
				}
				break;
			case "Refund Percent, Administrator":
				doubleClick("refundPercent_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("refundPercent_AdminExpenderCompliance");
				List<WebElement> refundPercent_Admin = listOfElements("lisofrefundPercent_AdminCompliance");
				for (WebElement we : refundPercent_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("refundPercent_AdminExpenderCompliance");
						click("refundPercentInTreeCompliance");
					}
				}
				break;
			case "Refund Percent, Lender, Trade/Sale":
				doubleClick("refundPercent_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("refundPercent_LTSExpenderCompliance");
				List<WebElement> refundPercent_LTS = listOfElements("lisofrefundPercent_LTSCompliance");
				for (WebElement we : refundPercent_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("refundPercent_LTSExpenderCompliance");
						click("refundPercentInTreeCompliance");
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Claims Deduction")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("claimsDeductionInTreeCompliance");
			switch (ruleSubType) {
			case "Claims":
				doubleClick("claimsExpenderCompliance");
				ruleGroup = getTextOfElement("claimsExpenderCompliance");
				List<WebElement> claimsExpender = listOfElements("lisofclaimsExpenderCompliance");
				for (WebElement we : claimsExpender) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claimsExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Administrator":
				doubleClick("claims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("claims_AdminExpenderCompliance");
				List<WebElement> claims_Admin = listOfElements("lisofclaims_AdminExpenderCompliance");
				for (WebElement we : claims_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_AdminExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Lender, Trade/Sale":
				doubleClick("claims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LTSExpenderCompliance");
				List<WebElement> claims_LTS = listOfElements("lisofclaims_LTSExpenderCompliance");
				for (WebElement we : claims_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LTSExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Lender, NonPayment":
				doubleClick("claims_LNPExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LNPExpenderCompliance");
				List<WebElement> claims_LNP = listOfElements("lisofclaims_LNPExpenderCompliance");
				for (WebElement we : claims_LNP) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LNPExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Lender, Unwind":
				doubleClick("claims_LUWExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LUWExpenderCompliance");
				List<WebElement> claims_LUW = listOfElements("lisofclaims_LUWExpenderCompliance");
				for (WebElement we : claims_LUW) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LUWExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Non-Compliance Rule")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("non_ComplianceRuleInTreeCompliance");
			switch (ruleSubType) {
			case "Refund Based On":
				doubleClick("refundBasedOnExpenderCompliance");
				ruleGroup = getTextOfElement("refundBasedOnExpenderCompliance");
				List<WebElement> refundBasedOn = listOfElements("lisofrefundBasedOnRuleCompliance");
				for (WebElement we : refundBasedOn) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("refundBasedOnExpenderCompliance");
						click("non_ComplianceRuleInTreeCompliance");
					}
				}
				break;
			case "If Transferred":
				doubleClick("ifTransferredExpenderCompliance");
				ruleGroup = getTextOfElement("ifTransferredExpenderCompliance");
				List<WebElement> ifTransferred = listOfElements("lisofIfTransferredExpenderCompliance");
				for (WebElement we : ifTransferred) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("ifTransferredExpenderCompliance");
						click("non_ComplianceRuleInTreeCompliance");
					}
				}
				break;
			case "Payee":
				doubleClick("payeeExpenderCompliance");
				ruleGroup = getTextOfElement("payeeExpenderCompliance");
				List<WebElement> payee = listOfElements("lisofPayeeRuleCompliance");
				for (WebElement we : payee) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("payeeExpenderCompliance");
						click("non_ComplianceRuleInTreeCompliance");
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Cancel Reason")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("cancelReasonInTreeCompliance");
			switch (ruleSubType) {
			case "Refund Based On":
				doubleClick("cancelReason_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelReason_AdminExpenderCompliance");
				List<WebElement> cancelReason_Admin = listOfElements("lisofcancelReason_AdminExpenderCompliance");
				for (WebElement we : cancelReason_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelReason_AdminExpenderCompliance");
						click("cancelReasonInTreeCompliance");
					}
				}
				break;
			}
		}
		return ruleMap;
	}

	public void complainceDataProcess(String groupName, String priceSheetGroup, String state) throws Exception {
		String versionNumber = "";
		goToComplianceTab();
		goToContractBuilderTab();
		clearTextBox("clickOnLibraryLocation");
		type("clickOnGroupType", priceSheetGroup);
		type("clickOnState", state);
		click("clickOnSearchComplianceButton");
		WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ObjectRepo.fetchOR("loadingIcon")));
		versionNumber = getPriceSheetGroupVersion(priceSheetGroup, state);
		waitForSomeTime(10);
		type("stateFilterRow", state);
		if (getTextOfElement("getDataRow").length() < 1) {
			clearTextBox("stateFilterRow");
			type("clickOnState", "Multi-State");
			click("clickOnSearchComplianceButton");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(ObjectRepo.fetchOR("loadingIcon")));
			waitForSomeTime(5);
			versionNumber = getPriceSheetGroupVersion(priceSheetGroup, "Multi-State");
		}
		waitForSomeTime(15);
		type("groupNameFilterRow", groupName);
		type("versionFilterRow", versionNumber);
		waitForSomeTime(5);
		click("getDataRow");
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
					getRuleData.put(itemValue, ruleName);
					clickViaXpath("//*[@Name='" + itemValue
							+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/preceding-sibling::*[@AutomationId='Expander']");
					break;
				} else {
					clickViaXpath("//*[@Name='" + itemValue
							+ "' and @AutomationId='Ocean_ComplianceModule_ComplianceRulesView_DispalyNameTextBlock']/preceding-sibling::*[@AutomationId='Expander']");
					break;
				}
			}

		}
		click(or1);
		return getRuleData;
	}

	/**
	 * function process the detail in cancellation page.
	 * 
	 * @throws Exception
	 */
	public void cancellationPageProcess(String contractNunmber) {
		goToCancellationTab();
		goToMailServiceTab();
		click("clickClear");
		type("typeContractId", contractNunmber);
		waitForSomeTime(5);
		click("searchContractButton");
		click("clickCancelButton");
	}

	/**
	 * function process the detail in cancellation page.
	 * 
	 * @throws Exception
	 */
	public boolean verifyComplainceRulesWithRuleinfoView(HashMap<Integer, HashMap<String, String>> summaryData,
			HashMap<String, String> excelMap) throws Exception {
		boolean uiFlag = false;
		waitForSomeTime(30);
		HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
		// go to standardCalculation
		click("standardCalculation");
		try {
			if (excelMap.get("Role_Type").equals("Lender")) {
				click("clickOK");
			}
		} catch (Exception e) {
			// do nothing
		}

		try {
			click("FuturePopupClick");
		} catch (Exception e) {
			// do nothing
		}
		click("okClick");
		HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
		click("standOKbtn");
		// compare standardcalculation and normal calcuted value
		if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
			logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
			logger.info("Verfiying through Rule Info View");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValueWithResult();
			logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
			HashMap<String, String> matchRule = null;
			for (int i = 1; i < ruleInfoViewValue.size(); i++) {
				for (Entry<String, String> ent : ruleInfoViewValue.get(i).entrySet()) {
					matchRule = new HashMap<String, String>();
					matchRule.put(ent.getKey(), ent.getValue());
					if (matchRule.equals(summaryData)) {
						uiFlag = true;
						break;
					}
				}
			}
			uiFlag = compareValuesforRefundPCancelF(ruleInfoViewValue, summaryData);
		} else {
			logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
			logger.info("Verfiying  through Rule Info View");
		}
		return uiFlag;
	}

	/*
	 * To compare value for hashMap
	 */
	public boolean compareValuesforRefundPCancelF(HashMap<Integer, HashMap<String, String>> ruleInfoViewValue,
			HashMap<Integer, HashMap<String, String>> dbruleInfo) {
		boolean matchFlag = false;
		HashMap<String, String> uiMap1 = flatMap(ruleInfoViewValue);
		HashMap<String, String> uiMap2 = flatMap(dbruleInfo);
		for (Map.Entry<String, String> entry : uiMap2.entrySet()) {
			if (uiMap1.containsKey(entry.getKey())) {
				String value = uiMap1.get(entry.getKey());
				if (value.contains(entry.getValue())) {
					matchFlag = true;
					continue;
				} else {
					matchFlag = false;
					break;
				}
			}
		}
		return matchFlag;
	}

	/*
	 * To extract values from nested HashMap
	 */
	public HashMap<String, String> flatMap(HashMap<Integer, HashMap<String, String>> complexMap) {
		HashMap<String, String> flatMap = new HashMap<String, String>();
		Iterator<HashMap<String, String>> iterator1 = complexMap.values().iterator();
		while (iterator1.hasNext()) {
			HashMap<String, String> element = iterator1.next();
			Iterator<Entry<String, String>> iterator2 = element.entrySet().iterator();
			while (iterator2.hasNext()) {
				Entry<String, String> element2 = iterator2.next();
				flatMap.put(element2.getKey(), element2.getValue());
			}
		}
		return flatMap;
	}

	public boolean verifyComplainceRuleWithRuleInfoView(HashMap<String, String> ruleMap,
			HashMap<String, String> matchRuleMap) {

		boolean matcherFlag = false;
		int count = 0;
		for (String ruleMapKey : ruleMap.keySet()) {
			for (String matchRuleMapKey : matchRuleMap.keySet()) {
				if (ruleMap.get(ruleMapKey).equals(matchRuleMap.get(matchRuleMapKey))) {
					matcherFlag = true;
					count++;
					break;
				}
			}
			if (count == matchRuleMap.size()) {
				break;
			}
		}

		return matcherFlag;
	}

	public HashMap<String, String> complainceUserAppliedRuleTreeProcess(WebElement we, String ruleGroup) {
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		try {
			waitForSomeTime(10);
			we.click();
		} catch (Exception e) {
			// Do Nothing
		}
		String ruleUsed = we.getText();
		click("saveruleBTNCompliance");
		waitForSomeTime(12);
		click("clickOK");
		ruleMap.put(ruleGroup, ruleUsed);
		return ruleMap;
	}

	/**
	 * function change the applied rule in compliance page.
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> complainceUserAppliedRuleTreeProcess(HashMap<String, String> excelMap) {
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		String ruleGroup = "";
		if (excelMap.get("Rule_Type").equals("CancelFee")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("cancelFeeInTreeCompliance");
			switch (ruleSubType) {
			case "Flat Cancel Period":
				doubleClick("FlatCancelPeriodExpenderCompliance");
				ruleGroup = getTextOfElement("FlatCancelPeriodExpenderCompliance");
				List<WebElement> flatCancelPeriods = listOfElements("lisofFlatCancelRuleCompliance");
				for (WebElement we : flatCancelPeriods) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						waitForSomeTime(5);
						doubleClick("FlatCancelPeriodExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period":
				doubleClick("cancelFeeAfterFlatCancelPeriodExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriod = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriod) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period, Administrator":
				doubleClick("cancelFeeAfterFlatCancelPeriod_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriod_AdminExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriod_Admin = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriod_AdminRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriod_Admin) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriod_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period, Lender, Trade/Sale":
				doubleClick("cancelFeeAfterFlatCancelPeriod_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriod_LTSExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriod_LTS = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriod_LTSRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriod_LTS) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriod_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period if Claims":
				doubleClick("cancelFeeAfterFlatCancelPeriodIfClaimsExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodIfClaimsExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriodIfClaims = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodIfClaimsRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriodIfClaims) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodIfClaimsExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period if Claims, Administrator":
				doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriodIfClaims_Admin = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodIfClaims_AdminRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriodIfClaims_Admin) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale":
				doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeAfterFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				List<WebElement> cancelFeeAfterFlatCancelPeriodIfClaims_LTS = listOfElements(
						"lisofCancelFeeAfterFlatCancelPeriodIfClaims_LTSRuleCompliance");
				for (WebElement we : cancelFeeAfterFlatCancelPeriodIfClaims_LTS) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeAfterFlatCancelPeriodIfClaims_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Override":
				doubleClick("cancelFeeOverdueExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeOverdueExpenderCompliance");
				List<WebElement> cancelFeeOverdue = listOfElements("lisofCancelFeeOverdueRuleCompliance");
				for (WebElement we : cancelFeeOverdue) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeOverdueExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period":
				doubleClick("cancelFeeWithInFlatCancelPeriodExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithInFlatCancelPeriodExpenderCompliance");
				List<WebElement> cancelFeeWithInFlatCancelPeriod = listOfElements(
						"lisofcancelFeeWithInFlatCancelPeriodCompliance");
				for (WebElement we : cancelFeeWithInFlatCancelPeriod) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithInFlatCancelPeriodExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period, Administrator":
				doubleClick("cancelFeeWithInFlatCancelPeriod_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithInFlatCancelPeriod_AdminExpenderCompliance");
				List<WebElement> cancelFeeWithInFlatCancelPeriod_Admin = listOfElements(
						"lisofCancelFeeWithInFlatCancelPeriod_AdminRuleCompliance");
				for (WebElement we : cancelFeeWithInFlatCancelPeriod_Admin) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithInFlatCancelPeriod_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale":
				doubleClick("cancelFeeWithinFlatCancelPeriod_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriod_LTSExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriod_LTS = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriod_LTSRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriod_LTS) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriod_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period if Claims":
				doubleClick("cancelFeeWithinFlatCancelPeriodIfClaimsExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriodIfClaimsExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriodIfClaims = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriodIfClaimsRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriodIfClaims) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriodIfClaimsExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period if Claims, Administrator":
				doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriodIfClaims_AdminExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriodIfClaims_Admin = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriodIfClaims_AdminRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriodIfClaims_Admin) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_AdminExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			case "Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale":
				doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("cancelFeeWithinFlatCancelPeriodIfClaims_LTSExpenderCompliance");
				List<WebElement> cancelFeeWithinFlatCancelPeriodIfClaims_LTS = listOfElements(
						"lisofCancelFeeWithinFlatCancelPeriodIfClaims_LTSRuleCompliance");
				for (WebElement we : cancelFeeWithinFlatCancelPeriodIfClaims_LTS) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						;
						doubleClick("cancelFeeWithinFlatCancelPeriodIfClaims_LTSExpenderCompliance");
						click("cancelFeeInTreeCompliance");
						break;
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Refund Percent")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("refundPercentInTreeCompliance");
			switch (ruleSubType) {
			case "Refund Percent":
				doubleClick("refundPercentExpenderCompliance");
				ruleGroup = getTextOfElement("refundPercentExpenderCompliance");
				List<WebElement> refundPercent = listOfElements("lisofrefundPercentCompliance");
				for (WebElement we : refundPercent) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("refundPercentExpenderCompliance");
						click("refundPercentInTreeCompliance");
						break;
					}
				}
				break;
			case "Refund Percent, Administrator":
				doubleClick("refundPercent_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("refundPercent_AdminExpenderCompliance");
				List<WebElement> refundPercent_Admin = listOfElements("lisofrefundPercent_AdminCompliance");
				for (WebElement we : refundPercent_Admin) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("refundPercent_AdminExpenderCompliance");
						click("refundPercentInTreeCompliance");
						break;
					}
				}
				break;
			case "Refund Percent, Lender, Trade/Sale":
				doubleClick("refundPercent_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("refundPercent_LTSExpenderCompliance");
				List<WebElement> refundPercent_LTS = listOfElements("lisofrefundPercent_LTSCompliance");
				for (WebElement we : refundPercent_LTS) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("refundPercent_LTSExpenderCompliance");
						click("refundPercentInTreeCompliance");
						break;
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Claims Deduction")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("claimsDeductionInTreeCompliance");
			switch (ruleSubType) {
			case "Claims":
				doubleClick("claimsExpenderCompliance");
				ruleGroup = getTextOfElement("claimsExpenderCompliance");
				List<WebElement> claimsExpender = listOfElements("lisofclaimsExpenderCompliance");
				for (WebElement we : claimsExpender) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("claimsExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
						break;
					}
				}
				break;
			case "Claims, Administrator":
				doubleClick("claims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("claims_AdminExpenderCompliance");
				List<WebElement> claims_Admin = listOfElements("lisofclaims_AdminExpenderCompliance");
				for (WebElement we : claims_Admin) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_AdminExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
						break;
					}
				}
				break;
			case "Claims, Lender, Trade/Sale":
				doubleClick("claims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LTSExpenderCompliance");
				List<WebElement> claims_LTS = listOfElements("lisofclaims_LTSExpenderCompliance");
				for (WebElement we : claims_LTS) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LTSExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
						break;
					}
				}
				break;
			case "Claims, Lender, NonPayment":
				doubleClick("claims_LNPExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LNPExpenderCompliance");
				List<WebElement> claims_LNP = listOfElements("lisofclaims_LNPExpenderCompliance");
				for (WebElement we : claims_LNP) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LNPExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
						break;
					}
				}
				break;
			case "Claims, Lender, Unwind":
				doubleClick("claims_LUWExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LUWExpenderCompliance");
				List<WebElement> claims_LUW = listOfElements("lisofclaims_LUWExpenderCompliance");
				for (WebElement we : claims_LUW) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LUWExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
						break;
					}
				}
				break;
			}
		} else if (excelMap.get("Rule_Type").equals("Non-Compliance Rule")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("non_ComplianceRuleInTreeCompliance");
			switch (ruleSubType) {
			case "Refund Based On":
				doubleClick("refundBasedOnExpenderCompliance");
				ruleGroup = getTextOfElement("refundBasedOnExpenderCompliance");
				List<WebElement> refundBasedOn = listOfElements("lisofrefundBasedOnRuleCompliance");
				for (WebElement we : refundBasedOn) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("refundBasedOnExpenderCompliance");
						click("non_ComplianceRuleInTreeCompliance");
						break;
					}
				}
				break;
			case "If Transferred":
				doubleClick("ifTransferredExpenderCompliance");
				ruleGroup = getTextOfElement("ifTransferredExpenderCompliance");
				List<WebElement> ifTransferred = listOfElements("lisofIfTransferredExpenderCompliance");
				for (WebElement we : ifTransferred) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("ifTransferredExpenderCompliance");
						click("non_ComplianceRuleInTreeCompliance");
						break;
					}
				}
				break;
			case "Payee":
				doubleClick("payeeExpenderCompliance");
				ruleGroup = getTextOfElement("payeeExpenderCompliance");
				List<WebElement> payee = listOfElements("lisofPayeeRuleCompliance");
				for (WebElement we : payee) {
					if (!we.isSelected()) {
						ruleMap = complainceUserAppliedRuleTreeProcess(we, ruleGroup);
						doubleClick("payeeExpenderCompliance");
						click("non_ComplianceRuleInTreeCompliance");
						break;
					}
				}
				break;
			}
		}
		return ruleMap;
	}

	public HashMap<String, String> compliance_RuleVerification(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("Role_Type", inputArray[i]);
					break;
				case 1:
					searchData.put("Initiated_By", inputArray[i]);
					break;
				case 2:
					searchData.put("Cancel_Reason", inputArray[i]);
					break;
				case 3:
					searchData.put("Days", inputArray[i]);
					break;
				case 4:
					searchData.put("Rule_Type", inputArray[i]);
					break;
				case 5:
					searchData.put("Rule_Sub_Type", inputArray[i]);
					break;
				case 6:
					searchData.put("Rule_List", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		return searchData;
	}

	@SuppressWarnings("rawtypes")
	public HashMap<Integer, HashMap<String, String>> getRuleInfoExpectedResult(HashMap<String, String> ruleMap,
			String cancelMiles, int daysDiff, String planMile, String saleDate, String expireDate, String custAmount,
			String dealerAmount) throws Exception {
		HashMap<Integer, HashMap<String, String>> summaryData = new HashMap<Integer, HashMap<String, String>>();

		String ruleGroup = "";
		String ruleUsed = "";
		String ruleResult1 = "";
		String ruleResult2 = "";
		String ruleResult3 = "";
		String ruleResult4 = "";

		try {
			for (Map.Entry mapElement : ruleMap.entrySet()) {
				ruleGroup = (String) mapElement.getKey();
				ruleUsed = (String) mapElement.getValue();
			}
		} catch (Exception e) {
			//
		}
		if (ruleGroup.contains("Refund Percent")) {
			long planDays = calculateDaysBwTwoDates(saleDate, expireDate);
			int milesDiff = /* calculateMilesDifference(cancelMiles) */2214;
			int planMiles = Integer.parseInt(planMile);
			float daysUtilization = ((float) daysDiff) / planDays;
			float milesUtilization = ((float) milesDiff) / planMiles;
			float refByDay = ((1 - daysUtilization) * 100);
			float refByMile = (1 - milesUtilization) * 100;
			DecimalFormat df = new DecimalFormat("0.00");
			String formatRefByDay = df.format(refByDay);
			String formatRefByMile = df.format(refByMile);
			logger.info("planDays= " + planDays + "milesDiff= " + milesDiff + "planMiles= " + planMiles
					+ "daysUtilization= " + daysUtilization + "milesUtilization= " + milesUtilization + "RefByDay= "
					+ refByDay + "RefByMile= " + refByMile);
			if (ruleUsed.contains("Standard Refund Percent")) {
				ruleResult1 = "Days diff=" + daysDiff + ", " + "Miles diff=" + milesDiff;
				ruleResult2 = "Days% used=" + (daysUtilization) + ", " + "Miles% used=" + (milesUtilization);
				ruleResult3 = "Ref % = "
						+ (refByDay > refByMile ? removeZeroes(formatRefByMile) : removeZeroes(formatRefByDay));
				ruleResult4 = "Plan days=" + planDays + ", Plan miles=" + planMiles;
			} else if (ruleUsed.contains("Refund Percent by Time")) {
				ruleResult1 = "Cancel method used = T";
				ruleResult2 = "Days diff=" + daysDiff;
				ruleResult3 = "Ref % = " + removeZeroes(formatRefByDay);
			} else if (ruleUsed.contains("Refund Percent by Mileage")) {
				ruleResult1 = "Cancel method used = M";
				ruleResult2 = "Miles diff=" + milesDiff;
				ruleResult3 = "Ref % = " + removeZeroes(formatRefByMile);
			}
		}

		for (int i = 1; i < 5; i++) {
			HashMap<String, String> subValue = new HashMap<String, String>();
			if (ruleGroup.contains("Refund Percent")) {
				String key1 = ruleGroup + " " + ruleUsed;
				String key3 = "STDPRORATE" + " " + "Standard Pro Rate";
				switch (i) {
				case 1:
					subValue.put(key1, ruleResult1);
					break;
				case 2:
					subValue.put(key1, ruleResult2);
					break;
				case 3:
					if (ruleUsed.contains("Standard Refund Percent"))
						subValue.put(key1, ruleResult4);
					else
						subValue.put(key3, ruleResult3);
					break;
				case 4:
					if (ruleUsed.contains("Standard Refund Percent"))
						subValue.put(key3, ruleResult3);
					break;
				}
				if (i < 4 || ruleUsed.contains("Standard Refund Percent"))
					summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period")
					|| ruleGroup.equals("Cancel Fee After Flat Cancel Period")) {
				String cancelFeeValue = "Fee = " + (ruleUsed.substring(0, ruleUsed.indexOf("Cancel Fee")).trim());
				subValue.put(ruleGroup + " " + ruleUsed, cancelFeeValue);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period, Administrator")
					|| ruleGroup.equals("Cancel Fee After Flat Cancel Period, Administrator")) {
				String cancelFeeValue = "Fee = " + (ruleUsed.substring(0, ruleUsed.indexOf("Cancel Fee")).trim());
				subValue.put(ruleGroup.substring(0, ruleGroup.indexOf(", Administrator")).trim() + " " + ruleUsed,
						cancelFeeValue);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale")
					|| ruleGroup.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")) {
				String cancelFeeValue = "Fee = " + (ruleUsed.substring(0, ruleUsed.indexOf("Cancel Fee")).trim());
				subValue.put(ruleGroup.substring(0, ruleGroup.indexOf(", Lender, Trade/Sale")).trim() + " " + ruleUsed,
						cancelFeeValue);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims")
					|| ruleGroup.equals("Cancel Fee After Flat Cancel Period if Claims")) {
				String cancelFeeValue = "Fee = " + (ruleUsed.substring(0, ruleUsed.indexOf("Cancel Fee")).trim());
				subValue.put(ruleGroup.substring(0, ruleGroup.indexOf(" if Claims")).trim() + " " + ruleUsed,
						cancelFeeValue);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims, Administrator")
					|| ruleGroup.equals("Cancel Fee After Flat Cancel Period if Claims, Administrator")) {
				String cancelFeeValue = "Fee = " + (ruleUsed.substring(0, ruleUsed.indexOf("Cancel Fee")).trim());
				subValue.put(
						ruleGroup.substring(0, ruleGroup.indexOf(" if Claims, Administrator")).trim() + " " + ruleUsed,
						cancelFeeValue);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale")
					|| ruleGroup.equals("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale")) {
				String cancelFeeValue = "Fee = " + (ruleUsed.substring(0, ruleUsed.indexOf("Cancel Fee")).trim());
				subValue.put(ruleGroup.substring(0, ruleGroup.indexOf(" if Claims, Lender, Trade/Sale")).trim() + " "
						+ ruleUsed, cancelFeeValue);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Flat Cancel Period")) {
				subValue.put(ruleGroup + " " + ruleUsed, ruleGroup + " " + ruleUsed);
				summaryData.put(i, subValue);
			} else if (ruleGroup.contains("Claims")) {
				String deductClaims = "Calims deducted = "
						+ (Integer.parseInt(getTextOfElement("premiumInContractSummryCompliance"))
								- Integer.parseInt(getTextOfElement("grossRefundInCompliance")));
				subValue.put(ruleGroup + " " + ruleUsed, deductClaims);
				summaryData.put(i, subValue);
			} else if (ruleGroup.equals("Payee")) {
				subValue.put(ruleGroup + " " + ruleUsed, "Payee");
				summaryData.put(i, subValue);
			} else {
				logger.info("Wrong Rule Selected");
			}
		}
		return summaryData;
	}

	public void changePriceSheetGroup(String priceSheet, String groupName) throws Exception {
		String changedGroup = "";
		goToComplianceTab();
		goToPriceSheetGroupTab();
		type("priceSheetFilter", priceSheet);
		type("groupNameFilter", groupName);
		rightClick("selectedPriceSheetRow");
		// click Edit Price Sheet group
		Robot r = new Robot();
		waitForSomeTime(10);
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		if (groupName.equalsIgnoreCase("SNE")) {
			changedGroup = "SNF";
		} else {
			changedGroup = "SNE";
		}
		// waitForSomeTime(15);
		type("updateGroupName", changedGroup);
		waitForSomeTime(5);
		click("updatePriceSheetGroup");
	}

	/**
	 * This query is used to get account Role Id with account Rule Builder and
	 * return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> compliance_getContractIdBasedOnStateAndProductCode2(String State, String Progcode)
			throws Exception {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String name = null;
		String STATE = null;
		String CONTRACT_STATUS_ID = null;
		String cert = null;
		String PROGRAM_CODE = null;

		HashMap<String, String> dbResult = new HashMap<String, String>();
		String query = " SELECT distinct  A.ROLE_IDENTIFIER,A.Name,SD.[STATE],SD.[CONTRACT_STATUS_ID] ,SD.cert,"
				+ "  SD.PROGRAM_CODE from [dbo].[ALLSALES_DETAILS] SD  "
				+ "	join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ "	where  SD.[STATE] = 'AZ' and SD.PROGRAM_CODE ='ARC' and  A.ROLE_IDENTIFIER in ("
				+ "	SELECT distinct  A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD "
				+ "	join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ "	INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ "	INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ "	where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5 "
				+ "  and SD.PROGRAM_CODE ='ARC' and SD.PRICESHEET_ID is not null and R.ROLE_NAME ='Dealer' "
				+ "	and A.ROLE_IDENTIFIER not in " + "	(select distinct a.role_identifier "
				+ "	from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id  "
				+ "	join CANCELLATION_CATEGORY_RULE rr on rr.account_Cancellation_id = ac.id )"
				+ "  and SD.[SALE_DATE] between '" + preYear + "' and '" + currYear
				+ "' and SD.[STATE] = 'AZ' ) and   SD.CONTRACT_STATUS_ID =5 ) " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("ROLE_IDENTIFIER");
				name = dbMap.get(number).get("Name");
				STATE = dbMap.get(number).get("STATE");
				CONTRACT_STATUS_ID = dbMap.get(number).get("CONTRACT_STATUS_ID");
				PROGRAM_CODE = dbMap.get(number).get("cert");
				dbResult.put("roleIdentifier", roleId);
				dbResult.put("AccName", name);
				dbResult.put("STATE", STATE);
				dbResult.put("CONTRACT_ID", CONTRACT_STATUS_ID);
				dbResult.put("PROGRAM_CODE", PROGRAM_CODE);

				return dbResult;
			}
		}
		return dbResult;
	}

	@SuppressWarnings("unused")
	public HashMap<String, String> compliance_getContractIdBasedOnStateAndProductCode(String State, String Progcode)
			throws Exception {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String name = null;
		String STATE = null;
		String cert = null;
		String PROGRAM_CODE = null;
		String Sale_Date = null;
		String CliamAmount = null;
		HashMap<String, String> dbResult = new HashMap<String, String>();
		String query = " SELECT distinct  A.ROLE_IDENTIFIER,A.Name,SD.[STATE],SD.[CONTRACT_STATUS_ID] ,SD.cert,"
				+ "  SD.PROGRAM_CODE , SD.SALE_DATE ,claim.CLAIM_AMOUNT from [dbo].[ALLSALES_DETAILS] SD  "
				+ "	join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ "  join [dbo].[ACCOUNT_ADDRESS] AA on AA.[ACCOUNT_ID] = A.[ID] "
				+ "  join [dbo].[CLAIMS] claim on claim.CERT = SD.CERT "
				+ "	where  AA.[STATE] = 'AZ' and SD.PROGRAM_CODE ='ARC' and  A.ROLE_IDENTIFIER in ( "
				+ "	SELECT distinct  A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD "
				+ "	join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ "	INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ "	INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ "  join [dbo].[ACCOUNT_ADDRESS] AA on AA.[ACCOUNT_ID] = A.[ID] "
				+ "	where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5 "
				+ "  and SD.PROGRAM_CODE ='ARC' and SD.PRICESHEET_ID is not null and R.ROLE_NAME ='Dealer' "
				+ "	and A.ROLE_IDENTIFIER not in " + "	(select distinct a.role_identifier "
				+ "	from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id  "
				+ "	join CANCELLATION_CATEGORY_RULE rr on rr.account_Cancellation_id = ac.id )"
				+ "  and SD.[SALE_DATE] between '" + preYear + "' and '" + currYear
				+ "' and AA.[STATE] = 'AZ' ) and   SD.CONTRACT_STATUS_ID =5  and claim.CLAIM_AMOUNT between 1 and 1000 "
				+ ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("ROLE_IDENTIFIER");
				name = dbMap.get(number).get("Name");
				STATE = dbMap.get(number).get("STATE");
				cert = dbMap.get(number).get("cert");
				PROGRAM_CODE = dbMap.get(number).get("cert");
				Sale_Date = dbMap.get(number).get("SALE_DATE");
				CliamAmount = dbMap.get(number).get("CLAIM_AMOUNT");
				System.out.println("roleid====" + roleId);
				dbResult.put("roleIdentifier", roleId);
				dbResult.put("AccName", name);
				dbResult.put("STATE", STATE);
				dbResult.put("cert", cert);
				dbResult.put("PROGRAM_CODE", PROGRAM_CODE);
				dbResult.put("Sale_Date", Sale_Date);
				dbResult.put("SCliamAmount", CliamAmount);
				System.out.println("dbResult====" + dbResult);
				return dbResult;

			}
		}
		return dbResult;
	}

	/**
	 * This function is used to navigate to NewCancellationTab
	 * 
	 * 
	 */
	public void clickCancelButtonAndNavigateToNewCancellationTab() throws Exception {
		click("clickCancelButton");
	}

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
	 * This function is used to return rule info view ruleName & ruleGroup summary
	 * data in a hashmap
	 * 
	 * 
	 */
	/**
	 * This function is used to overRideCancellationValues
	 * 
	 * @param refundPercentage, if blank, than random refund would be taken in
	 *                          account
	 * @param CancelFees        if blank, than random 2 digit cancel fee would be
	 *                          applied
	 * 
	 */
	public void overRideCancellationValuesAndClickCalculate(String refundPercentage, String CancelFees)
			throws Exception {
		// click("checkAuthorize");
		click("overRideRulesCheckBox");
		String myRefundPercentage = refundPercentage;
		if (refundPercentage.length() < 1) {
			String refundPercent = getValue("refundPercent");
			float refundPercents = 0;
			refundPercents = Float.valueOf(refundPercent).floatValue() - 6;
			int abc = (int) refundPercents;
			myRefundPercentage = Integer.toString(abc);
		}
		String mycancelFee = CancelFees;
		if (CancelFees.length() < 1) {
			Random rand = new Random();
			int rand_int1 = rand.nextInt(100);
			mycancelFee = Integer.toString(rand_int1);
		}
		type("refundPercent", myRefundPercentage);
		type("cancelFee", mycancelFee);
		click("clickCalculate");
		click("clickOK");
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void searchContractGivenInputParamatersCancellation(HashMap<String, String> searchParamaters)
			throws Exception {
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
	 * function fetch the applied rule in compliance page.
	 */
	public HashMap<String, String> deductClaimsDefaultRuleTreeProcess(HashMap<String, String> excelMap) {
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		String ruleGroup = "";
		if (excelMap.get("Rule_Type").equals("Claims Deduction")) {
			String ruleSubType = excelMap.get("Rule_Sub_Type");
			click("claimsDeductionInTreeCompliance");
			switch (ruleSubType) {
			case "Claims":
				doubleClick("claimsExpenderCompliance");
				ruleGroup = "Claims";
				List<WebElement> claimsExpender = listOfElements("lisofclaimsExpenderCompliance");
				for (WebElement we : claimsExpender) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claimsExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Administrator":
				doubleClick("claims_AdminExpenderCompliance");
				ruleGroup = getTextOfElement("claims_AdminExpenderCompliance");
				List<WebElement> claims_Admin = listOfElements("lisofclaims_AdminExpenderCompliance");
				for (WebElement we : claims_Admin) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_AdminExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Lender, Trade/Sale":
				doubleClick("claims_LTSExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LTSExpenderCompliance");
				List<WebElement> claims_LTS = listOfElements("lisofclaims_LTSExpenderCompliance");
				for (WebElement we : claims_LTS) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LTSExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Lender, NonPayment":
				doubleClick("claims_LNPExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LNPExpenderCompliance");
				List<WebElement> claims_LNP = listOfElements("lisofclaims_LNPExpenderCompliance");
				for (WebElement we : claims_LNP) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LNPExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			case "Claims, Lender, Unwind":
				doubleClick("claims_LUWExpenderCompliance");
				ruleGroup = getTextOfElement("claims_LUWExpenderCompliance");
				List<WebElement> claims_LUW = listOfElements("lisofclaims_LUWExpenderCompliance");
				for (WebElement we : claims_LUW) {
					if (we.isSelected()) {
						ruleMap = complainceDefaultRuleTreeProcess(we, ruleGroup);
						doubleClick("claims_LUWExpenderCompliance");
						click("claimsDeductionInTreeCompliance");
					}
				}
				break;
			}
		}
		return ruleMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public HashMap<String, String> getClaimsDeductionRuleInfoExpectedResult(HashMap<String, String> ruleMap,
			HashMap<String, String> contractList, int daysDiff) throws Exception {
		HashMap<String, String> subValue = new HashMap<String, String>();
		String saleDate = contractList.get("sale_date");
		String expireDate = contractList.get("EXPIRATION_DATE");
		String cancelMiles = contractList.get("cancelMiles");
		String planMile = contractList.get("PLAN_MILES");
		String cusPaid = contractList.get("Customer_Paid");
		String aulPremium = contractList.get("Premium");
		String saleMiles = contractList.get("SaleMileage");
		String ruleGroup = "";
		String ruleUsed = "";
		String ruleGroupComPage = "";
		String ruleUsedComPage = "";
		String ruleResult1 = "";
		String ruleResult2 = "";
		String ruleResult3 = "";
		String cancelFee = "";
		String refundPer = "";
		String grossRefund = "";
		String refundCalAmount = "";
		String flatCanceFee = null;
		String cancelFeeAfterFlat = null;
		HashMap<String, String> matchRule = new HashMap<String, String>();
		try {
			for (Map.Entry mapElement : ruleMap.entrySet()) {
				ruleGroupComPage = (String) mapElement.getKey();
				ruleUsedComPage = (String) mapElement.getValue();
			}
			if (ruleGroupComPage.contains("Claims") || ruleGroupComPage.equals("Refund Based On")) {
				if (ruleUsedComPage.equals("Deduct Claims") || ruleUsedComPage.equals("Total Premium")
						|| ruleUsedComPage.equals("Customer Paid")) {
					HashMap<Integer, HashMap<String, String>> ruleInfoView = getRuleInfoValue();
					for (Map.Entry mapElement : ruleInfoView.entrySet()) {
						matchRule = (HashMap<String, String>) mapElement.getValue();
						for (Map.Entry mapElement1 : matchRule.entrySet()) {
							ruleGroup = (String) mapElement1.getKey();
							ruleUsed = (String) mapElement1.getValue();
							if (ruleGroup.contains("Refund Percent")) {
								long planDays = calculateTermDays(saleDate, expireDate);
								int milesDiff = calcMilesDifference(cancelMiles, saleMiles);
								int planMiles = Integer.parseInt(planMile);
								float daysUtilization = ((float) daysDiff) / planDays;
								float milesUtilization = ((float) milesDiff) / planMiles;
								float RefByDay = ((1 - daysUtilization) * 100);
								float RefByMile = (1 - milesUtilization) * 100;
								DecimalFormat df = new DecimalFormat("0.00");

								String formatRefByDay = df.format(RefByDay);
								String formatRefByMile = df.format(RefByMile);
								if (ruleUsed.contains("Standard Refund Percent")) {
									ruleResult1 = "Days diff=" + daysDiff + ", " + "Miles diff=" + milesDiff;
									ruleResult2 = "Days% used=" + (daysUtilization) + ", " + "Miles% used="
											+ (milesUtilization);
									ruleResult3 = "Ref% used="
											+ (RefByDay > RefByMile ? formatRefByMile : formatRefByDay);
									refundPer = (RefByDay > RefByMile ? formatRefByMile : formatRefByDay);
								} else if (ruleUsed.contains("Refund Percent by Time")) {
									ruleResult1 = "Cancel method used = T";
									ruleResult2 = "Days diff=" + daysDiff;
									ruleResult3 = "Ref% used=" + formatRefByDay;
									refundPer = formatRefByDay;
								} else if (ruleUsed.contains("Refund Percent by Mileage")) {
									ruleResult1 = "Cancel method used = M";
									ruleResult2 = "Miles diff=" + milesDiff;
									ruleResult3 = "Ref% used=" + formatRefByMile;
									refundPer = formatRefByMile;
								} else if (ruleUsed.contains("100% Refund")) {
									refundPer = "100";
								}
							}
							if (ruleGroup.equals("Refund Based On")) {
								if (ruleUsed.contains("Customer Paid")) {
									refundCalAmount = cusPaid;

								} else if (ruleUsed.contains("Total Premium")) {
									refundCalAmount = aulPremium;
								}
							}
							if (ruleGroup.equals("Cancel Fee Within Flat Cancel Period")) {
								flatCanceFee = ruleUsed.toString();
								flatCanceFee = flatCanceFee.substring(1, 3);
							} else if (ruleGroup.equals("Cancel Fee After Flat Cancel Period")) {
								cancelFeeAfterFlat = ruleUsed.toString();
								cancelFeeAfterFlat = cancelFeeAfterFlat.substring(1, 3);
							}
						}
					}
					if (daysDiff > 60) {
						cancelFee = cancelFeeAfterFlat.trim();
						cancelFee = cancelFee + ".00";
					} else {
						cancelFee = flatCanceFee.trim();
						cancelFee = cancelFee + ".00";
					}
					grossRefund = calculateGrossRefund(refundCalAmount, refundPer, ruleMap);
					String aulRef = calculateAULRefund(grossRefund, cancelFee);
					String dealerfincoPortion = calculateDealerFincoPortion(cusPaid, aulPremium, refundPer, ruleMap);
					String custRefund = calculateCustomerRefund(dealerfincoPortion, aulRef);
					DecimalFormat df = new DecimalFormat("0.0");
					subValue.put("CUSTOMER_REFUND", String.valueOf(Math.round((Float.parseFloat(custRefund)))));

				} else {
					subValue.put("Claims", ruleUsedComPage);
				}
			}
		} catch (Exception e) {
			// Do Nothing
		}
		return subValue;
	}

	@SuppressWarnings("rawtypes")
	public String calculateGrossRefund(String refundCalAmount, String refundPer, HashMap<String, String> ruleMap)
			throws Exception {
		String ruleUsedComPage = "";
		for (Map.Entry mapElement : ruleMap.entrySet()) {
			ruleUsedComPage = (String) mapElement.getValue();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		float refundAmount = Float.parseFloat(refundCalAmount);
		float refundPer1 = Float.parseFloat(refundPer);
		double grossRef = (refundAmount * refundPer1) / 100;
		String grossRefund = df.format(grossRef);
		if (!ruleUsedComPage.equals("Total Premium") && !ruleUsedComPage.equals("Customer Paid")) {
			String claimsPaid = getTextOfElement("claimsPaidInCompliance");
			grossRefund = String.valueOf((Float.parseFloat(grossRefund) - Float.parseFloat(claimsPaid)));
			return grossRefund;
		} else
			return grossRefund;
	}

	public int calculateMilesDifference(String cancelMiles) throws Exception {
		int milesDifference = 2214;
		if (cancelMiles.length() > 0) {
			String saleMiles = getSalesMiles();
			milesDifference = Integer.parseInt(cancelMiles) - Integer.parseInt(saleMiles);
		}
		return milesDifference;
	}

	public int calcMilesDifference(String cancelMiles, String saleMiles) throws Exception {
		int milesDifference = 0;
		if (cancelMiles.length() > 0) {
			milesDifference = Integer.parseInt(cancelMiles) - Integer.parseInt(saleMiles);
		}
		return milesDifference;
	}

	@SuppressWarnings("rawtypes")
	public String calculateDealerFincoPortion(String cusPaid, String aulPremium, String refundPer,
			HashMap<String, String> ruleMap) throws Exception {
		String ruleUsedComPage = "";
		for (Map.Entry mapElement : ruleMap.entrySet()) {
			ruleUsedComPage = (String) mapElement.getValue();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		float customerPaid = Float.parseFloat(cusPaid);
		float aulPre = Float.parseFloat(aulPremium);
		float delearPaid = (customerPaid - aulPre);
		float refPer = Float.parseFloat(refundPer);
		double dealerfinco = (delearPaid * refPer) / 100;
		String delaerFincoPortion = df.format(dealerfinco);
		if (!ruleUsedComPage.equals("Total Premium") && !ruleUsedComPage.equals("Customer Paid")) {
			String claimsPaid = getTextOfElement("claimsPaidInCompliance");
			delaerFincoPortion = String.valueOf((Float.parseFloat(delaerFincoPortion) + Float.parseFloat(claimsPaid)));
			return delaerFincoPortion;
		} else
			return delaerFincoPortion;

	}

	public String calculateAULRefund(String grossRefund, String cancelFeeAfterFlat) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		float grossRefund1 = Float.parseFloat(grossRefund);
		float cancelFee1 = Float.parseFloat(cancelFeeAfterFlat);
		double aulRefend = grossRefund1 - cancelFee1;
		String aulRef = df.format(aulRefend);
		return aulRef;
	}

	public String calculateCustomerRefund(String dealerfincoPortion, String aulRef) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		float dealerfinco = Float.parseFloat(dealerfincoPortion);
		float aulRefund = Float.parseFloat(aulRef);
		double custRef = (dealerfinco + aulRefund);
		String customerRefund = df.format(custRef);
		return customerRefund;

	}

	@SuppressWarnings("rawtypes")
	public HashMap<String, String> getActualClaimsDuctionRule(HashMap<String, String> ruleMap) {
		HashMap<String, String> actualMap = new HashMap<String, String>();
		String ruleGroupComPage = "";
		String ruleUsedComPage = "";
		try {
			for (Map.Entry mapElement : ruleMap.entrySet()) {
				ruleGroupComPage = (String) mapElement.getKey();
				ruleUsedComPage = (String) mapElement.getValue();
			}
		} catch (Exception e) {
			// Do nothing
		}
		if (ruleGroupComPage.contains("Claims")) {
			if (ruleUsedComPage.equals("Deduct Claims")) {
				String text = getTextOfElement("customerRefundInCompliance");
				String cusRef = text.replaceAll(",", "");
				actualMap.put("CUSTOMER_REFUND", String.valueOf(Math.round((Float.parseFloat(cusRef)))));
			} else {
				actualMap.put("Claims", "Do not Deduct Claims");
			}
		}
		return actualMap;
	}

	public HashMap<String, String> getActualRefundBasedOnRule(HashMap<String, String> ruleMap) {
		HashMap<String, String> actualMap = new HashMap<String, String>();
		String text = getTextOfElement("customerRefundInCompliance");
		String cusRef = text.replaceAll(",", "");
		actualMap.put("CUSTOMER_REFUND", String.valueOf(Math.round((Float.parseFloat(cusRef)))));
		return actualMap;
	}

}
