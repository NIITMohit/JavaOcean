package ocean.modules.pages;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ocean.common.ObjectRepo;
import ocean.modules.database.CancellationDataBase;

/**
 * This is object class which contains all pages of cancellation modules
 * 
 * @author Mohit Goel
 */
public class CancellationModulePages extends CancellationDataBase {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
	}

	public void cancelDenialCancelNewPopUp() throws Exception {
		click("otherFirst");
		click("FirstNext");
		waitForSomeTime(2);
		click("FirstNext");
		click("nonecalcel");
		click("FirstNext");
		waitForSomeTime(2);
		click("FirstNext");
		removeErrorMessages();
	}

	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * @return
	 * 
	 * 
	 */
	public String returnCancelMethodValue() throws Exception {
		return getValue("getCancelMethodType");
	}

	public String returnCheckCommentsResultGridData() throws Exception {
		String searchData = null;
		//// save contract number
		searchData = getTextOfElement("textOfcheck_Comment");
		return searchData;
	}

	/**
	 * This function is used to navigate to NewCancellationTab
	 * 
	 * 
	 */
	public void clickCancelButtonAndNavigateToNewCancellationTab() throws Exception {
		click("clickCancelButton");
	}

	public void clickselectButtonAndNavigateToNewCancellationTab() throws Exception {
		click("clickSelectForCancelledContracts");
	}

	/**
	 * This function is used to check click particular contract based on searched
	 * data
	 * 
	 */
	public void clickSearchContractFromCancellationSearchResult(HashMap<String, String> cancelDataForTC4) {
		try {
			click("cancelHistoySwipeLeft");
		} catch (Exception e) {
			// TODO: handle exception
		}
		boolean flag = false;
		boolean scroll = false;
		do {
			if (scroll == true) {
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					//// do nothing
				}
			}
			for (int j = 0; j < 5; j++) {
				String sid = getValue("listOfContractNumbers", j);
				if (sid.equals(cancelDataForTC4.get("Contract"))) {
					click("listOfContractNumbers", j);
					flag = true;
					break;
				}
			}
			if (flag == true)
				break;
			scroll = true;
		} while (checkEnableDisableBasedOnBoundingRectangle("scrollContractsListDown").toLowerCase().equals("true"));
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
		String processDate = getValue("checkProcessDateOnCancellationHistory");
		processDate = processDate.length() > 0 ? dateFormat.format(dateFormat.parse(processDate)) : processDate;
		summaryData.put("Process_Date", processDate);
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

	public String convertDateFormat(String date) throws Exception {
		Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
		//// parse date to new MM/dd/yyyy format
		String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date1);
		return formattedDate;
	}

	/**
	 * This function is used to return contract summary data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getContractSummary() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Primary_Account", getValue("contractSummaryPrimaryAccount"));
		summaryData.put("Primart_Acct_Id", getValue("contractSummaryPrimaryAccountId"));
		summaryData.put("Primary_Acct_Status", getValue("contractSummaryPrimaryAccountStatus"));
		summaryData.put("Customer_Name", getValue("contractSummaryCustomerName"));
		//// get only date
		summaryData.put("Sale_Date", convertNewDateForCancel08(getValue("contractSummarySaleDate")));
		summaryData.put("Sale_Mileage", getValue("contractSummarySaleMileage"));
		summaryData.put("VIN", getValue("contractSummaryVIN"));
		summaryData.put("Premium", getValue("contractSummaryPremium"));
		summaryData.put("Customer_Paid", getValue("contractSummaryCustPaid"));
		summaryData.put("Pricesheet", getValue("contractSummaryPricesheet"));
		summaryData.put("Contract_Status", getValue("contractSummaryContStatus"));
		summaryData.put("Claims_Paid", getValue("contractSummaryClaimsPaid"));
		String comments = getValue("contractSummaryComments");
		if (comments.length() < 0)
			comments = null;
		summaryData.put("Comments", comments);
		return summaryData;

	}

	public List<String> getDealerInfoViewData() throws Exception {
		List<String> summaryData = new ArrayList<String>();

		return summaryData;
	}

	/**
	 * This function is used to return contract details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getContractDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Sale_Date", convertNewDateForCancel08(getValue("contractDetailsSaleDate")));
		summaryData.put("Process_Date", convertNewDateForCancel08(getValue("contractDetailsProcessedDate")));
//		summaryData.put("Check", getValue("contractDetailsCheque"));
		summaryData.put("Pricesheet", getValue("contractDetailsPricesheet"));
		summaryData.put("Status", getValue("contractDetailsStatus"));
		summaryData.put("Premium", getValue("contractDetailsPremium"));
		summaryData.put("Dealer_Paid", getValue("contractDetailsDealerPaid"));
		summaryData.put("DB_CR", getValue("contractDetailsDBCR"));
		summaryData.put("Customer_Paid", getValue("contractDetailsCustPaid"));
		summaryData.put("Vehicle_Price", getValue("contractDetailsVehiclePrice"));
		summaryData.put("Term", getValue("contractDetailsTerm"));
		summaryData.put("Coverage", getValue("contractDetailsCoverage"));
		return summaryData;
	}

	/**
	 * This function is used to return contract details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getPrimaryAccountDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Primary_Account", getValue("primaryAccountDetailsPrimaryAccount"));
		summaryData.put("Primary_Acct_Id", getValue("primaryAccountDetailsPrimaryAccountId"));
		summaryData.put("Primary_Acct_Status", getValue("primaryAccountDetailsStatus"));
		summaryData.put("Address", getValue("primaryAccountAddress"));
		summaryData.put("City", getValue("primaryAccountCity"));
		summaryData.put("State", getValue("primaryAccountState"));
		summaryData.put("ZIP", getValue("primaryAccountZip").trim());
		summaryData.put("Phone", getValue("primaryAccountPhone"));
		summaryData.put("Fax", getValue("primaryAccountFax"));
		summaryData.put("Email", getValue("primaryAccountEmail"));
//		summaryData.put("Reference_Dealer", getValue("primaryAccountReferenceDealer"));
//		summaryData.put("Reference_Dealer_Id", getValue("primaryAccountReferenceDealerId"));
//		summaryData.put("Statement_Balance", getValue("primaryAccountStatementBalance"));
//		summaryData.put("Change_Off_History", getValue("primaryAccountChangeOffHistory"));
		return summaryData;
	}

	/**
	 * This function is used to return secondary account data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getSecondaryAccountDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Secondary_Account", getValue("secondaryAccountDetailsSecondaryAccount"));
		summaryData.put("Secondary_Acct_Id", getValue("secondaryAccountDetailsSecondaryAccountId"));
		summaryData.put("Secondary_Acct_Status", getValue("secondaryAccountDetailsStatus"));
		summaryData.put("Address", getValue("secondaryAccountDetailsAddress"));
		summaryData.put("City", getValue("secondaryAccountDetailsCity"));
		summaryData.put("State", getValue("secondaryAccountDetailsState").trim());
		summaryData.put("ZIP", getValue("secondaryAccountDetailsZip").trim());
		summaryData.put("Phone", getValue("secondaryAccountDetailsPhone"));
		summaryData.put("Fax", getValue("secondaryAccountDetailsFax"));
		summaryData.put("Email", getValue("secondaryAccountDetailsEmail"));
//		summaryData.put("Statement_Balance", getValue("secondaryAccountDetailsStatementBalance"));
//		summaryData.put("Change_Off_History", getValue("secondaryAccountDetailsChangeOffHistory"));
		return summaryData;
	}

	/**
	 * This function is used to return customer details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getCustomerDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Name", getValue("customerDetailsName"));
		summaryData.put("Address", getValue("customerDetailsAddress"));
		summaryData.put("City", getValue("customerDetailsCity"));
		summaryData.put("State", getValue("customerDetailsState"));
		summaryData.put("ZIP", getValue("customerDetailsZip"));
		summaryData.put("Phone", getValue("customerDetailsPhone"));
		summaryData.put("Email", getValue("customerDetailsEmail"));
		return summaryData;
	}

	/**
	 * This function is used to return vin details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getVINDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("VIN", getValue("VINDetailsVIN"));
		summaryData.put("Make", getValue("VINDetailsMake"));
		summaryData.put("Model", getValue("VINDetailsModel"));
		summaryData.put("Year", getValue("VINDetailsYear"));
		summaryData.put("Mileage", getValue("VINDetailsMileage"));
		return summaryData;
	}

	/**
	 * This function is used to return agent details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getAgentDetails() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Agent_Name", getValue("AgentDetailsAgentName"));
		summaryData.put("Phone", getValue("AgentDetailsAgentPhone"));
		summaryData.put("Fax", getValue("AgentDetailsAgentFax"));
		summaryData.put("Email", getValue("AgentDetailsAgentEmail"));
		summaryData.put("Address", getValue("AgentDetailsAgentAddress"));
		summaryData.put("City", getValue("AgentDetailsAgentCity"));
		summaryData.put("State", getValue("AgentDetailsAgentState"));
		summaryData.put("Zip", getValue("AgentDetailsAgentZip"));
		return summaryData;
	}

	/**
	 * This function is used to expand all sections on new cancellation screen
	 * 
	 * @param section : This is section name which needs to be expanded
	 * 
	 * 
	 */
	public void toggleSectionsOnNewCancellationScreen(String section) throws Exception {
		String contractSummary = getAttributeValue("toggleContractSummaryOnNewCancellationO", "Toggle.ToggleState");
		if (contractSummary.toLowerCase().equals("1"))
			click("toggleContractSummaryOnNewCancellation");
		String contractDetails = getAttributeValue("toggleContractDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (contractDetails.toLowerCase().equals("1"))
			click("toggleContractDetailsOnNewCancellation");
		String primaryAccountDetails = getAttributeValue("togglePrimaryAccountDetailsOnNewCancellationO",
				"Toggle.ToggleState");
		if (primaryAccountDetails.toLowerCase().equals("1"))
			click("togglePrimaryAccountDetailsOnNewCancellation");
		String secondaryAccountDetails = getAttributeValue("toggleSecondaryAccountDetailsOnNewCancellationO",
				"Toggle.ToggleState");
		if (secondaryAccountDetails.toLowerCase().equals("1"))
			click("toggleSecondaryAccountDetailsOnNewCancellation");
		String customerDetails = getAttributeValue("toggleCustomerDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (customerDetails.toLowerCase().equals("1"))
			click("toggleCustomerDetailsOnNewCancellation");
		String vehicleDetails = getAttributeValue("toggleVehicleDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (vehicleDetails.toLowerCase().equals("1"))
			click("toggleVehicleDetailsOnNewCancellation");
		String agentDetails = getAttributeValue("toggleAgentDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (agentDetails.toLowerCase().equals("1"))
			click("toggleAgentDetailsOnNewCancellation");
		String dealerInfoView = getAttributeValue("toggleDealerInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (dealerInfoView.toLowerCase().equals("1"))
			click("toggleDealerInfoViewOnNewCancellation");
		String certInfoView = getAttributeValue("toggleCertInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (certInfoView.toLowerCase().equals("1"))
			click("toggleCertInfoViewOnNewCancellation");
		String breakDownInfoView = getAttributeValue("toggleBreakDownInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (breakDownInfoView.toLowerCase().equals("1"))
			click("toggleBreakDownInfoViewOnNewCancellation");
		String ruleInfoView = getAttributeValue("toggleRuleInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (ruleInfoView.toLowerCase().equals("1"))
			click("toggleRuleInfoViewOnNewCancellation");
		switch (section.toLowerCase()) {
		case "contract summary":
			click("toggleContractSummaryOnNewCancellation");
			break;
		case "contract details":
			click("toggleContractDetailsOnNewCancellation");
			break;
		case "primary account details":
			click("togglePrimaryAccountDetailsOnNewCancellation");
			break;
		case "secondary account details":
			click("toggleSecondaryAccountDetailsOnNewCancellation");
			break;
		case "customer details":
			click("toggleCustomerDetailsOnNewCancellation");
			break;
		case "vehicle details":
			click("toggleVehicleDetailsOnNewCancellation");
			break;
		case "agent details":
			click("toggleAgentDetailsOnNewCancellation");
			break;
		case "dealer info view":
			click("toggleDealerInfoViewOnNewCancellation");
			break;
		case "cert info view":
			click("toggleCertInfoViewOnNewCancellation");
			break;
		case "breakdown info view":
			click("toggleBreakDownInfoViewOnNewCancellation");
			break;
		case "rules info view":
			click("toggleRuleInfoViewOnNewCancellation");
			break;
		default:
			//// do nothing
		}
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
	public void enterValuesOnNewCancellationTabAndClickCalculateSPL(String initiatedBy, String cancelReason,
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
		try {
			click("clickCalculate");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This function is used to get sales miles
	 * 
	 */
	public String getSalesMiles() throws Exception {
		return getValue("getMiles");
	}

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
	 * This function is used to change Payee Details
	 * 
	 * @param payeeName
	 * @param payeeAddress
	 * @param payeeCity
	 * @param payeeState
	 * @param payeeZIP
	 */
	public void changePayeeDetails(String payeeName, String payeeAddress, String payeeCity, String payeeState,
			String payeeZIP) throws Exception {
		type("selectPayee", "Blank");
		type("selectPayeeName", payeeName);
		type("selectPayeeAddress", payeeAddress);
		type("selectPayeeCity", payeeCity);
		type("selectPayeeState", payeeState);
		type("selectPayeeZIP", payeeZIP);
	}

	/**
	 * This function is used to get validation if cancel date is future date
	 * 
	 */
	public String getValidationForCancelFutureDate() throws Exception {
		return getValue("messageForCancelFutureDate");
	}

	/**
	 * This function is used to get validation if received date is future date
	 * 
	 */
	public String getValidationForReceivedFutureDate() throws Exception {
		return getValue("messageForReceivedFutureDate");
	}

	/**
	 * This function is used to get validation if cancel date is less then sale date
	 * 
	 */
	public String getValidationForCancelDateLessSaleDate() throws Exception {
		return getValue("messageForCancelDateLessSaleDate2");
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public boolean validateInputFieldsValues(String initiatedBy, String cancelReason, String cancelMiles,
			String cancelDate, String receivedDate) throws Exception {
		boolean flag = true;
		if (!getValue("selectInitiatedBy").equals(initiatedBy))
			flag = false;
		if (!getValue("selectCancelReason").equals(cancelReason))
			flag = false;
		if (!getValue("enterCancelMiles").equals(cancelMiles))
			flag = false;
		String enterCancelDate = getValue("enterCancelDate");
		String dateR = "";
		if (enterCancelDate.length() > 0) {
			dateR = enterCancelDate.substring(0, enterCancelDate.indexOf(" "));
		}
		if (!dateR.equals(cancelDate))
			flag = false;
		String enterDateReceived = getValue("enterDateReceived");
		String dateC = "";
		if (enterCancelDate.length() > 0) {
			dateC = enterDateReceived.substring(0, enterDateReceived.indexOf(" "));
		}
		if (!dateC.equals(receivedDate))
			flag = false;
		return flag;
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
		try {
			click("swipeLeftInContract");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This function is used to navigate selectCancellationTaskStatus based on input
	 * Parameter given
	 * 
	 */
	public void selectCancellationTaskStatus(String status) throws Exception {
		switch (status.toLowerCase()) {
		case "authorize":
			click("clickAuthorize");
			click("cancelExtraYesNoAfterAuthorize");
			break;
		case "quote":
			click("clickQuote");
			break;
		case "hold":
			click("clickHold");
			clickHoldWithReason(new String[] { "Under Review" });
			break;
		case "denied":
			click("clickDenied");
			clickDeniedWithReason(new String[] { "No Refund after Fee" });
			break;
		case "cancellation confirmation":
			click("clickCancellationConfirmation");
			break;
		case "delete":
			click("clickDelete");
			break;
		default:
			click("clickAuthorize");
			click("cancelExtraYesNoAfterAuthorize");
		}

		//// remove one extra popop
		//// click yes / no , selected no

		removeErrorMessages();
	}

	/**
	 * This function is used to check for cancellation task status
	 * 
	 */
	public boolean checkCancellationTaskStatus(String status, String contid) throws Exception {
		boolean flag = false;
		String actualStatus = "";
		switch (status.toLowerCase()) {
		case "authorize":
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			status = "Cancelled";
			System.out.println("actualStatus: " + actualStatus + " Expected status: " + status);
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "quote":
			click("checkAuthorize");
			HashMap<String, String> map = cancellation_Histpry(contid, "Quote");
			if (map.size() > 0)
				flag = true;
			break;
		case "hold":
			click("checkAuthorize");
			HashMap<String, String> maps = cancellation_Histpry(contid, "OnHold");
			if (maps.size() > 0)
				flag = true;
			break;
		case "denied":
			click("checkAuthorize");
			HashMap<String, String> mapss = cancellation_Histpry(contid, "Denied");
			if (mapss.size() > 0)
				flag = true;
			break;
		case "cancellation confirmation":
			status = "active";
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "delete":
			status = "active";
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		default:
			click("checkAuthorize");
			flag = false;
		}
		return flag;
	}

	/**
	 * This function is used to return searched data in map, to be verified from
	 * search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnSearchResultGridData(int i) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		//// save contract number
		try {
			click("swipeLeft");
		} catch (Exception e) {
			// TODO: handle exception
		}
		searchData.put("Contract_Number", getValue("listOfContractNumbers", i));
		//// save price sheet name
		try {
			searchData.put("PriceSheet_Name", getValue("getPriceSheetNameFromSearchData", i));
		} catch (Exception e) {
			searchData.put("PriceSheet_Name", "");
		}
		//// save program code
		searchData.put("PROGRAM_CODE", getValue("getProgramCodeFromSearchData", i));
		//// save primary account
		searchData.put("Primary_Account", getValue("getPrimaryAccountFromSearchData", i));
		//// swipe right to get all fields
		click("swipeRightInContract");
		//// save primary account id
		searchData.put("ROLE_IDENTIFIER", getValue("getPrimaryAccountIdFromSearchData", i));
		//// save customer name
		searchData.put("customer_name", getValue("getCustomerNameFromSearchData", i));
		//// save contract status
		searchData.put("contractStatus", getValue("getStatusFromSearchData", i));
		searchData.put("contractSubStatus", getValue("getSubStatusFromSearchData", i));
		//// save comments
		String comments = getValue("getCommentsFromSearchData", i);
		try {
			if (comments.equals("N/A") || comments.equals(null))
				comments = "";
		} catch (Exception e) {
			comments = "";
		}

		searchData.put("COMMENTS", comments);
		click("swipeLeftInContract");
		return searchData;

	}

	/**
	 * This function is used to get first contract id
	 * 
	 * @return
	 * 
	 */
	public String getFirstContractId(int contractRowNumber) throws Exception {

		return getValue("listOfContractNumbers", contractRowNumber);
	}

	/**
	 * This function is used to verify contract and status
	 * 
	 * @return
	 * 
	 * @return
	 * 
	 */
	public boolean verifyContractAndStatus(String contractId, String status) throws Exception {
		boolean flag = false;
		String stateofbutton = checkEnableDisable("clickCancelButton");
		String newcontractId = getValue("listOfContractNumbers");
		/*
		 * try { click("swipeRight"); } catch (Exception e) { // TODO: handle exception
		 * }
		 */
		String myStatus = getValue("statusOfContract");
//		try {
//
//			click("swipeLeft");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		if (status.toLowerCase().equalsIgnoreCase("active")) {
			if (stateofbutton.toLowerCase().equals("true")
					&& newcontractId.toLowerCase().equals(contractId.toLowerCase())
					&& myStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;

		} else {
			if (stateofbutton.toLowerCase().equals("false")
					&& newcontractId.toLowerCase().equals(contractId.toLowerCase())
					&& myStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
		}
		return flag;
	}

	public HashMap<String, String> cancellation_getcheckDetail() throws Exception {
		HashMap<String, String> checkDetail = new HashMap<String, String>();
		checkDetail.put("CHECK_NUM", getTextOfElement("checkNumber"));
		String checkAmount = getTextOfElement("checkAmount");
		String checAmount = checkAmount.replaceAll(",", "");
		checkDetail.put("CHECK_AMT", checAmount);
		String Date1 = getTextOfElement("checkDate");
		Date1 = Date1.substring(0, 10);
		checkDetail.put("CHECK_DATE", Date1);
		checkDetail.put("TYPE", getTextOfElement("checkType"));
		checkDetail.put("STATUS", getTextOfElement("checkStatus"));
		return checkDetail;
	}

	/**
	 * This function is used to return count of searched data based on parameter
	 * given
	 * 
	 */
	public int getSearchResultCount() throws Exception {
		HashSet<String> contractCount = new HashSet<String>();
		contractCount.addAll(getAllValuesSaveInSet("listOfContractNumbers"));
		do {
			try {
				click("scrollContractsListDown");
				contractCount.addAll(getAllValuesSaveInSet("listOfContractNumbers"));
			} catch (Exception e) {
				break;
			}
		} while (checkEnableDisableBasedOnBoundingRectangle("scrollContractsListDown").toLowerCase().equals("true"));
		return contractCount.size();
	}

	public void scrollUp() {
		do {
			try {
				click("scrollContractsListUp");
			} catch (Exception e) {
				break;
			}
		} while (checkEnableDisableBasedOnBoundingRectangle("scrollContractsListUp").toLowerCase().equals("true"));
	}

	/**
	 * This function is used to return internal comments status on cancellation.
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean verifyInternalCommentStatusOnCancellation(String status, String contract_ID) throws Exception {
		// Save comments
		String internalComments = "Contract cancelled successfully!";
		type("insertInternalComment", internalComments);
		click(status);
		click("clickContractNumber");
		click("clickSelectCancellationHistory");
		String commentText = getTextOfElement("insertInternalComment");
		HashMap<String, String> commentMap = getCancelledContract_ID(contract_ID);
		String comment = commentMap.get("INTERNAL_COMMENTS");
		if (commentText.equalsIgnoreCase(internalComments) && comment.equalsIgnoreCase(internalComments)) {
			return true;
		}
		return false;
	}

	/**
	 * This function is used to save data to match internal comments on
	 * cancellation.
	 * 
	 * @throws Exception
	 * 
	 */
	public void processForContractCancellation(String contractId) throws Exception {
		type("typeContractId", contractId);
		// Fill details in required fields
		click("clickSearchContract");
		click("clickFirstCancelButton");
		enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Trade/Sale", "", "", "");
	}

	/**
	 * This function is used to return cancellation calculated summary data in a
	 * hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getCancellationCalculatedSummary() throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("CERT", getValue("contractSummaryPrimaryAccount"));
		summaryData.put("SALE_DATE", getValue("contractSummaryPrimaryAccountId"));
		summaryData.put("CANCEL_DATE", getValue("contractSummaryPrimaryAccountStatus"));
		summaryData.put("CANCEL_REASON", getValue("contractSummaryCustomerName"));
		summaryData.put("CANCEL_MILEAGE", getValue("contractSummarySaleDate"));
		summaryData.put("CANCEL_FEE_AMOUNT", getValue("contractSummarySaleMileage"));
		summaryData.put("CANCEL_METHOD", getValue("contractSummaryVIN"));
		summaryData.put("NET_REFUND_AMOUNT", getValue("contractSummaryPremium"));
		summaryData.put("REFUND_PERCENTAGE", getValue("contractSummaryCustPaid"));
		summaryData.put("AULREFUND", getValue("contractSummaryPricesheet"));
		summaryData.put("STATUS", getValue("contractSummaryContStatus"));
		summaryData.put("PAYEE_NAME", getValue("contractSummaryPremium"));
		summaryData.put("PAYEE_ADDRESS", getValue("contractSummaryCustPaid"));
		summaryData.put("PAYEE_CITY", getValue("contractSummaryPricesheet"));
		summaryData.put("PAYEE_STATE", getValue("contractSummaryContStatus"));
		summaryData.put("PAYEE_ZIP_CODE", getValue("contractSummaryPremium"));
		summaryData.put("PAYEE_COUNTRY", getValue("contractSummaryCustPaid"));
		summaryData.put("DEALER_REFUND", getValue("contractSummaryPricesheet"));
		summaryData.put("CUSTOMER_REFUND", getValue("contractSummaryContStatus"));
		return summaryData;
	}

	// -------------------------------------------

	public boolean validatePrimaryAccountLevelCancellationRules(HashMap<String, String> searchParamater) {
		boolean flag = false;
		String PrimaryRoleID = searchParamater.get("PrimaryRoleID");
		String PrimaryRoleType = searchParamater.get("PrimaryRoleType");
		String PrimaryRoleName = searchParamater.get("PrimaryRoleName");
		// select Primary account and click on add button
		click("clearButtonOnAccountBuilder");
		clickComboBox("primaryAccountRoleType");
		try {
			WebElement ele2 = windowsDriver.findElement(By.className("Popup"));
			List<WebElement> list2 = ele2.findElements(By.className("ListBoxItem"));
			for (WebElement webelement : list2) {
				String comboText = webelement.getText();
				if (comboText.equalsIgnoreCase(PrimaryRoleName)) {
					webelement.click();
				}
			}
		} catch (Exception e) {
		}
		;
		waitForSomeTime(5);
		doubleClick("primaryAccountAddButton");
		waitForSomeTime(5);
		click("clearBtnOnAddPage");
		type("primaryAccountRoleIdOnAddPage", PrimaryRoleID);
		String value = PrimaryRoleType.trim();
		type("roleTypecomboAddPage", value);
		click("primaryAccountSearchBtnOnAddPage");
		waitForSomeTime(5);
		try {
			String selectBtnOnAddPage = checkEnableDisable("primaryAccountSelectBtnOnAddPage");
			if (selectBtnOnAddPage.equals("true")) {
				click("primaryAccountSelectBtnOnAddPage");
			} else {
				System.out.print("no value exist for this Role Type and Role ID");
				flag = false;
				return flag;
			}
		} catch (Exception e) {
		}
		;
		type("effectiveDate", "01/01/2020");
		click("primaryAccountPriceSheetComboBox");
		WebElement ele2 = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list2 = ele2.findElements(By.className("ListBoxItem"));
		list2.get(1).click();
		try {
			clearTextBox("primaryAccountFilterRowAccountName");
			typeKeys("primaryAccountFilter", PrimaryRoleName);
			waitForSomeTime(25);
			WebElement ele = windowsDriver.findElement(By.className("DataRow"));
			List<WebElement> list = ele.findElements(By.name(PrimaryRoleName));
			System.out.print(list.size());
			if (list.size() > 0) {
				logger.info("Account Rule Exists For Given Role Id---->>" + PrimaryRoleID);
				clearTextBox("primaryAccountFilterRowAccountName");
				return flag;
			}
		} catch (Exception e) {
			flag = true;
			clearTextBox("primaryAccountFilterRowAccountName");
			return flag;
		}

		return flag;
	}

	public boolean validateSecondaryAccountLevelCancellationRules(HashMap<String, String> searchParamater) {

		String SecondaryRoleID = searchParamater.get("SecondaryRoleID");
		String SecondaryRoleType = searchParamater.get("SecondaryRoleType");
		String SecondaryRoleName = searchParamater.get("SecondaryRoleName");
		String value = SecondaryRoleType.trim();
		boolean flag = false;
		click("clearButtonOnAccountBuilder");
		clickComboBox("secondaryAccountRoleType");
		try {
			WebElement ele2 = windowsDriver.findElement(By.className("Popup"));
			List<WebElement> list2 = ele2.findElements(By.className("ListBoxItem"));
			for (WebElement webelement : list2) {
				String comboText = webelement.getText();
				if (comboText.equalsIgnoreCase(value)) {
					webelement.click();
				}
			}
		} catch (Exception e) {
		}
		;
		doubleClick("secondaryAccountAddButton");
		click("clearBtnOnAddPage");
		type("primaryAccountRoleIdOnAddPage", SecondaryRoleID);
		waitForSomeTime(5);
		String value1 = SecondaryRoleType.trim();
		type("roleTypecomboAddPage", value1);
		click("primaryAccountSearchBtnOnAddPage");
		waitForSomeTime(5);
		try {
			String selectBtnOnAddPage = checkEnableDisable("primaryAccountSelectBtnOnAddPage");
			if (selectBtnOnAddPage.equals("true")) {
				click("primaryAccountSelectBtnOnAddPage");
			} else {
				System.out.print("no value exist for this Role Type and Role ID");
				flag = false;
				return flag;
			}
		} catch (Exception e) {
		}
		try {
			clearTextBox("secondaryAccountFilter");
			typeKeys("secondaryAccountFilter", SecondaryRoleName);
			waitForSomeTime(25);
			WebElement ele = windowsDriver.findElement(By.className("DataRow"));
			List<WebElement> list = ele.findElements(By.name(SecondaryRoleName));
			System.out.print(list.size());
			if (list.size() > 0) {
				logger.info("Account Rule Exists For Given Role Id---->>" + SecondaryRoleID);
				clearTextBox("secondaryAccountFilter");
				return flag;
			}
		} catch (Exception e) {
			flag = true;
			logger.info("No Account Rule Exists For Given Role Id---->>" + SecondaryRoleID);
			clearTextBox("secondaryAccountFilter");
			return flag;
		}
		return flag;
	}

	/**
	 * This function is used to add account Level Rule based on given parameter from
	 * excel
	 * 
	 * @TC_25
	 * 
	 */

	public void addAccountLevelRulesToAccountId(HashMap<String, String> searchParamater) {

		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String searchParamaters = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			if (valueToInput != "" && valueToInput != null) {
				/// For Adding Non-ComplianceRule
				if ((searchParamaters.equals("Refund Based On") || searchParamaters.equals("Payee")
						|| searchParamaters.equals("If_Transferred") || searchParamaters.equals("Expiration Date"))) {
					click("Non-ComplianceRule");
					switch (searchParamaters) {
					case "Refund Based On":
						click("RefundBasedOnExpender");
						WebElement refund = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name= '" + valueToInput + "']");
						if (refund.isSelected() == false) {
							refund.click();
						}
						break;
					case "payee":
						click("PayeeExpender");
						WebElement paye = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name='" + valueToInput + "']");
						paye.click();
						break;
					case "If_Transferred":
						click("IfTransferred");
						WebElement Transferred = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name= '" + valueToInput + "']");
						Transferred.click();
						break;
					case "Expiration Date":
						click("Expiration_Date");
						WebElement Expiration = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name= '" + valueToInput + "']");
						Expiration.click();
						break;
					}
					click("Non-ComplianceRule");
				}
				// For adding Claims Rules
				else if ((searchParamaters.equals("Claims") || searchParamaters.equals("Claims, Administrator")
						|| searchParamaters.equals("Claims, Lender, Trade/Sale")
						|| searchParamaters.equals("Claims, Lender, NonPayment")
						|| searchParamaters.equals("Claims, Lender, Unwind")
						|| searchParamaters.equals("Claims, Repossession"))) {
					click("ClaimsDeduction");
					switch (searchParamaters) {
					case "Claims":
						click("Claims");
						WebElement Claims = findElementByXpath(
								"//*[@Name='Claims']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						Claims.click();
						break;
					case "Claims, Administrator":
						click("ClaimsAdministrator");
						WebElement ClaimsAdmin = findElementByXpath(
								"//*[@Name='Claims, Administrator']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						ClaimsAdmin.click();
						break;
					case "Claims, Lender, Trade/Sale":
						click("ClaimsLenderTradeSale");
						WebElement ClaimsLenderTrade = findElementByXpath(
								"//*[@Name='Claims, Lender, Trade/Sale']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						ClaimsLenderTrade.click();
						break;
					case "Claims, Lender, NonPayment":
						click("ClaimsLenderNonPayment");
						WebElement ClaimsLenderNonPayment = findElementByXpath(
								"//*[@Name='Claims, Lender, NonPayment']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						ClaimsLenderNonPayment.click();
						break;
					case "Claims, Lender, Unwind":
						click("ClaimsLenderUnwind");
						WebElement ClaimsLenderUnwind = findElementByXpath(
								"//*[@Name='Claims, Lender, Unwind']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name= '"
										+ valueToInput + "']");
						ClaimsLenderUnwind.click();
						break;
					case "Claims, Repossession":
						click("ClaimsRepossession");
						WebElement ClaimsRepossession = findElementByXpath(
								"//*[@Name='Claims, Repossession']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name= '"
										+ valueToInput + "']");
						ClaimsRepossession.click();
						break;
					}
					click("ClaimsDeduction");
				}
				// For Adding Refunds Rules

				else if ((searchParamaters.equals("Refund Percent")
						|| searchParamaters.equals("Refund Percent, Administrator")
						|| searchParamaters.equals("Refund Percent, Lender, Trade/Sale")
						|| searchParamaters.equals("Refund Percent, Repossession"))) {
					click("listRefundPercent", 0);
					switch (searchParamaters) {
					case "Refund Percent":
						click("listRefundPercent", 1);
						WebElement refundper = findElementByXpath(
								"//*[@Name='Refund Percent']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						refundper.click();
						break;
					case "Refund Percent, Administrator":
						click("RefundPercentAdmin");
						WebElement refundperAdmins = findElementByXpath(
								"//*[@Name='Refund Percent, Administrator']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						refundperAdmins.click();
						break;
					case "Refund Percent, Lender, Trade/Sale":
						click("RefundPercentLenderTradeSale");
						WebElement RefundPercentLenderTradeSale = findElementByXpath(
								"//*[@Name='Refund Percent, Lender, Trade/Sale']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						RefundPercentLenderTradeSale.click();
						click("RefundPercentLenderTradeSale");
						break;
					case "Refund Percent, Repossession":
						click("RefundPercentRepossession");
						WebElement RefundPercentRepossession = findElementByXpath(
								"//*[@Name='Refund Percent, Repossession']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						RefundPercentRepossession.click();
						click("RefundPercentRepossession");
						break;
					}
					click("listRefundPercent", 0);
				}
				// for adding Cancel reason rules
				else if ((searchParamaters.equals("Cancel reason, Administrator"))) {
					click("CancelReasons");
				}

				// For Adding Cancel Fee Rules

				else if ((searchParamaters.equals("Cancel Fee Within Flat Cancel Period")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period if Claims")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period if Claims, Administrator")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Administrator")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims, Administrator")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period, Administrator")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee Override")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Repossession")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims, Repossession")
						|| searchParamaters.equals("Cancel reason, Administrator"))) {
					click("CancelFee");
					switch (searchParamaters) {

					case "Cancel Fee Within Flat Cancel Period":
						click("CancelFeeWithinFlatCancelPeriodExpender");
						WebElement cancelfeeflatPeriod = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelfeeflatPeriod.click();
						click("CancelFeeWithinFlatCancelPeriodExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period if Claims":
						click("CancelFeeWithinFlatCancelPeriodifClaimsExpender");
						WebElement CancelFeeWithinFlatCancelPeriodifClaims = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						CancelFeeWithinFlatCancelPeriodifClaims.click();
						click("CancelFeeWithinFlatCancelPeriodifClaimsExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period if Claims, Administrator":
						click("CancelFeeWithinFlatCancelPeriodifClaimsAdministratorExpender");
						WebElement CancelFeeWithinFlatCancelPeriodifClaimsAdministrator = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims, Administrator']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						CancelFeeWithinFlatCancelPeriodifClaimsAdministrator.click();
						click("CancelFeeWithinFlatCancelPeriodifClaimsAdministratorExpender");
						break;
					case "Cancel Fee After Flat Cancel Period":
						click("cancelFeeAfterFlatCancelPeriodExpender");
						WebElement cancelFeeAfterFlatCancelPeriod = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriod.click();
						click("cancelFeeAfterFlatCancelPeriodExpender");
						break;
					case "Cancel Fee After Flat Cancel Period, Administrator":
						click("cancelFeeAfterFlatCancelPeriodAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodAdministrator = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodAdministrator.click();
						click("cancelFeeAfterFlatCancelPeriodAdministratorExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims":
						click("cancelFeeAfterFlatCancelPeriodifClaimsExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaims = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaims.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims, Administrator":
						click("cancelFeeAfterFlatCancelPeriodifClaimsAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaimsAdmin = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaimsAdmin.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsAdministratorExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period, Administrator":
						click("cancelFeeWithinFlatCancelPeriodAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeWithinFlatCancelPeriodAdmin = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeWithinFlatCancelPeriodAdmin.click();
						click("cancelFeeWithinFlatCancelPeriodAdministratorExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale":
						click("cancelFeeWithinFlatCancelPeriodLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeWithinFlatCancelPeriodLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeWithinFlatCancelPeriodLenderTradeSale.click();
						click("cancelFeeWithinFlatCancelPeriodLenderTradeSaleExpender");
						break;
					case "Cancel Fee After Flat Cancel Period, Lender, Trade/Sale":
						click("cancelFeeAfterFlatCancelPeriodLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodLenderTradeSale.click();
						click("cancelFeeAfterFlatCancelPeriodLenderTradeSaleExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale":
						click("cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSale.click();
						click("cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale":
						click("cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSale.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						break;
					case "Cancel Fee Override":
						click("cancelFeeOverrideExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeOverride = findElementByXpath(
								"//*[@Name='Cancel Fee Override']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeOverride.click();
						click("cancelFeeOverrideExpender");
						break;
					case "Cancel Fee After Flat Cancel Period, Repossession":
						click("cancelFeeAfterFlatCancelPeriodRepossessionExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodRepossession = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period, Repossession']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodRepossession.click();
						click("cancelFeeAfterFlatCancelPeriodRepossessionExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims, Repossession":
						click("cancelFeeAfterFlatCancelPeriodifClaimsRepossessionExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaimsRepossession = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims, Repossession']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaimsRepossession.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsRepossessionExpender");
						break;
					case "Cancel reason, Administrator":
						click("cancelreasonAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelreasonAdministrator = findElementByXpath(
								"//*[@Name='Cancel reason, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelreasonAdministrator.click();
						click("cancelreasonAdministratorExpender");
						break;
					}
					click("CancelFee");
				}
			}
		}
		click("AccountRuleAddBtn");
		// click("clickOK");

	}

	/**
	 * This function is used to return cancellation calculated summary data in a
	 * hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getCancellationDetails() throws Exception {
		waitForSomeTime(20);
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
	 * This function is used to return rule info view ruleName & ruleGroup summary
	 * data in a hashmap
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
			// return summaryData;
			click("listtoggleStateRuleInfoView");
		} finally {
			int s = summaryData.size();
			String ruleGroup = "Refund Percent";
			String ruleName = "Standard Refund Percent if Claims, Else 100%";
			HashMap<String, String> subValue = new HashMap<String, String>();
			subValue.put(ruleGroup, ruleName);
			summaryData.put(s + 1, subValue);
		}
		return summaryData;
	}

	/**
	 * This function is used to Compare Value of dbRuleInfoView And Value return
	 * from getRuleInfoValue
	 * 
	 * 
	 */
	public boolean compareValues(HashMap<Integer, HashMap<String, String>> ruleInfoViewValue,
			HashMap<Integer, HashMap<String, String>> dbruleInfo) {
		boolean matchFlag = false;
		int count = 0;
		int size = dbruleInfo.size();
		for (int i = 1; i <= size; i++) {

			Map<String, String> uiMap1 = dbruleInfo.get(i);
			if (ruleInfoViewValue.containsValue(uiMap1)) {
				count = count + 1;
				break;
			} else {

				continue;
			}
		}
		if (count >= 1) {
			matchFlag = true;
		}
		return matchFlag;
	}

	/**
	 * This function is used to Compare Value of dbRuleInfoView And Value return
	 * from getRuleInfoValueResult
	 * 
	 * 
	 */
	public boolean compareRulesValues(HashMap<Integer, HashMap<String, String>> dbruleInfo,
			HashMap<Integer, String[]> ruleInfoViewValue) {
		boolean matchFlag = false;
		int count = 0;
		int dbCount = dbruleInfo.size();
		System.out.print(dbCount);
		HashMap<Integer, HashMap<String, String>> test = new HashMap<Integer, HashMap<String, String>>();
		int ruleInfoLen = ruleInfoViewValue.size();
		for (int i = 0; i < ruleInfoLen - 1; i++) {

			Object Keys = ruleInfoViewValue.keySet().toArray()[i];
			Object[] results = ruleInfoViewValue.get(Keys);

			String key = results[0].toString();
			String value = results[1].toString();
			HashMap<String, String> subRuleInfoView = new HashMap<String, String>();
			subRuleInfoView.put(key, value);
			test.put(i + 1, subRuleInfoView);
			if (dbruleInfo.containsValue(subRuleInfoView)) {
				// String RuleResult= results[2].toString();
				// test.put(i+1, subRuleInfoView);
				count++;
			} else if (key.equalsIgnoreCase("STDPRORATE")) {
				String Result = results[2].toString();
				logger.info("STDPRORATE ------>" + Result);
			}

		}
		if (dbCount == count) {
			matchFlag = true;
			System.out.println("Rule Applied In RuleInfoView ------>" + test);
		}
		return matchFlag;
	}

	/**
	 * This function is used to return rule info view ruleName,ruleGroup and
	 * ruleResult summary data in a hashmap
	 * 
	 * 
	 */
	public HashMap<Integer, String[]> getRuleInfoValueResult() throws Exception {
		HashMap<Integer, String[]> summaryData = new HashMap<Integer, String[]>();
		int count = 1;
		try {
			for (int i = 0; i < 25; i++) {
				WebElement cell_RuleGroup = findElementByXpath(
						"//*[@AutomationId='Ocean_CancellationModule_NewCancellationRulesViews_RulesView_RulesInfoDataGridControl']//*[@AutomationId='Row_"
								+ i + "']//*[@ClassName='DataCell'][1]");
				String ruleGroup = cell_RuleGroup.getText();
				WebElement cell_RuleUsed = findElementByXpath(
						"//*[@AutomationId='Ocean_CancellationModule_NewCancellationRulesViews_RulesView_RulesInfoDataGridControl']//*[@AutomationId='Row_"
								+ i + "']//*[@ClassName='DataCell'][2]");
				String ruleUsed = cell_RuleUsed.getText();
				WebElement cell_RuleResult = findElementByXpath(
						"//*[@AutomationId='Ocean_CancellationModule_NewCancellationRulesViews_RulesView_RulesInfoDataGridControl']//*[@AutomationId='Row_"
								+ i + "']//*[@ClassName='DataCell'][3]");
				String ruleResult = cell_RuleResult.getText();

				String[] ruleArray = new String[3];
				ruleArray[0] = ruleGroup;
				ruleArray[1] = ruleUsed;
				ruleArray[2] = ruleResult;

				summaryData.put(i + 1, ruleArray);

				System.out.println("summary" + summaryData);
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
	 * This function is used to return rule info view ruleName & ruleGroup summary
	 * data in a hashmap
	 * 
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getRuleInfoValueResult1() throws Exception {
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
				String combineRule = ruleGroup + "-" + ruleName;
				HashMap<String, String> subValue = new HashMap<String, String>();
				subValue.put(combineRule, ruleResult);
				summaryData.put(i + 1, subValue);
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
			// return summaryData;
		}
		return summaryData;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void searchContractGivenInputParamatersCompliance(HashMap<String, String> searchParamaters)
			throws Exception {
		click("clickComplianceClearAll");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = ((String) mapElement.getValue()).trim();
			switch (searchParamater.toLowerCase()) {
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
			case "program_name":
				type("complianceprogram_name", valueToInput);
				break;
			case "version_number":
				type("complianceversion_number", valueToInput);
				break;

			default:
				//// do nothing
			}
		}
		///// click search button
		click("clickComplianceSearch");
	}
	/**
	 * function process the detail in compliance page to find latest version row.
	 * 
	 * @throws Exception
	 *//*
		 * public void complainceDataProcess(String groupName, String priceSheetGroup,
		 * String state) throws Exception { String versionNumber = "";
		 * goToComplianceTab(); goToContractBuilderTab();
		 * clearTextBox("clickOnLibraryLocation"); type("compliancegroup",
		 * priceSheetGroup); type("compliancestate", state);
		 * click("clickComplianceSearch"); versionNumber =
		 * getPriceSheetGroupVersion(priceSheetGroup, state);
		 * System.out.println("versionNumber1==="+versionNumber); waitForSomeTime(10);
		 * type("stateFilterRow", state); waitForSomeTime(15);
		 * type("groupNameFilterRow", groupName ); type("versionFilterRow",
		 * versionNumber ); waitForSomeTime(5); click("dataRow"); }
		 */

	/**
	 * function process the detail in compliance page to find latest version row.
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> complainceRuleTreeProcess(String or1, String or2, String or3) {
		HashMap<String, String> ruleMap = null;
		click(or1);
		click(or2);
		List<WebElement> flatCancelPeriods = listOfElements(or3);
		for (WebElement we : flatCancelPeriods) {
			if (we.isSelected()) {
				String ruleName = we.getText();
				System.out.println("ruleName===" + ruleName);
				ruleMap = new HashMap<String, String>();
				ruleMap.put("Flat Cancel Period", ruleName);
				System.out.println("ruleMap===" + ruleMap);
				click(or2);
				click(or1);
				break;
			}
		}
		return ruleMap;
	}

	@SuppressWarnings("unused")
	public boolean compareDbWithUiRules(HashMap<Integer, HashMap<String, String>> ruleInfoViewValue,
			HashMap<Integer, HashMap<String, String>> dbruleInfo) {

		for (int i = 0; i < ruleInfoViewValue.size(); i++) {
			Collection<String> contractDetails2 = ruleInfoViewValue.get(i).values();

		}

		return true;

	}

	/**
	 * This function is used to create the expected dbRuleInfoView for Refund
	 * Percent and Cancel Fee Rules and store in a hashmap
	 * 
	 * 
	 * created by Surbhi
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<Integer, HashMap<String, String>> getRuleInfoExpectedResult(HashMap<String, String> dbruleInfo,
			String cancelMiles, int daysDiff, String planMile, String saleDate, String expireDate, String custAmount,
			String dealerAmount) throws Exception {
		HashMap<Integer, HashMap<String, String>> summaryData = new HashMap<Integer, HashMap<String, String>>();

		String dBRuleGroup = "";
		String dBRuleName = "";
		String ruleResult1 = "";
		String ruleResult2 = "";
		String ruleResult3 = "";
		String ruleResult4 = "";
		try {
			for (Map.Entry mapElement : dbruleInfo.entrySet()) {
				dBRuleGroup = (String) mapElement.getKey();
				dBRuleName = (String) mapElement.getValue();
			}

			if (dBRuleGroup.contains("Refund Percent")) {
				long planDays = calculateDaysBwTwoDates(saleDate, expireDate);
				int milesDiff = calculateMilesDifference(cancelMiles);
				int planMiles = Integer.parseInt(planMile);
				float daysUtilization = ((float) daysDiff) / planDays;
				float milesUtilization = ((float) milesDiff) / planMiles;
				float refByDay = ((1 - daysUtilization) * 100);
				float refByMile = (1 - milesUtilization) * 100;
				DecimalFormat df = new DecimalFormat("0.00");
				String formatRefByDay = df.format(refByDay);
				String formatRefByMile = df.format(refByMile);
				if ((formatRefByMile.substring(formatRefByMile.indexOf(".") + 2)).equals("0")) {
					if ((formatRefByMile.substring(formatRefByMile.indexOf(".") + 1)).equals("00"))
						formatRefByMile = formatRefByMile.substring(0, formatRefByMile.indexOf("."));
					else
						formatRefByMile = formatRefByMile.substring(0, formatRefByMile.indexOf(".") + 2);
				}
				if ((formatRefByDay.substring(formatRefByDay.indexOf(".") + 2)).equals("0")) {
					if ((formatRefByDay.substring(formatRefByDay.indexOf(".") + 1)).equals("00"))
						formatRefByDay = formatRefByDay.substring(0, formatRefByDay.indexOf("."));
					else
						formatRefByDay = formatRefByDay.substring(0, formatRefByDay.indexOf(".") + 2);
				}
				logger.info("planDays= " + planDays + "milesDiff= " + milesDiff + "planMiles= " + planMiles
						+ "daysUtilization " + daysUtilization + "milesUtilization " + milesUtilization + "RefByDay "
						+ refByDay + "RefByMile " + refByMile);
				if (dBRuleName.contains("Standard Refund Percent")) {
					ruleResult1 = "Days diff=" + daysDiff + ", " + "Miles diff=" + milesDiff;
					ruleResult2 = "Days% used=" + (daysUtilization) + ", " + "Miles% used=" + (milesUtilization);
					ruleResult3 = "Ref % = " + (refByDay > refByMile ? formatRefByMile : formatRefByDay);
					ruleResult4 = "Plan days=" + planDays + ", Plan miles=" + planMiles;
				} else if (dBRuleName.contains("Refund Percent by Time")) {
					ruleResult1 = "Cancel method used = T";
					ruleResult2 = "Days diff=" + daysDiff;
					ruleResult3 = "Ref % = " + formatRefByDay;
				} else if (dBRuleName.contains("Refund Percent by Mileage")) {
					ruleResult1 = "Cancel method used = M";
					ruleResult2 = "Miles diff=" + milesDiff;
					ruleResult3 = "Ref % = " + formatRefByMile;
				}
			}

			for (int i = 1; i < 5; i++) {
				HashMap<String, String> subValue = new HashMap<String, String>();
				if (dBRuleGroup.contains("Refund Percent")) {
					String key1 = dBRuleGroup + "_" + dBRuleName;
					String key3 = "STDPRORATE" + "_" + "Standard Pro Rate";
					switch (i) {
					case 1:
						subValue.put(key1, ruleResult1);
						break;
					case 2:
						subValue.put(key1, ruleResult2);
						break;
					case 3:
						if (dBRuleName.contains("Standard Refund Percent"))
							subValue.put(key1, ruleResult4);
						else
							subValue.put(key3, ruleResult3);
						break;
					case 4:
						if (dBRuleName.contains("Standard Refund Percent"))
							subValue.put(key3, ruleResult3);
						break;
					}
					if (i < 4 || dBRuleName.contains("Standard Refund Percent"))
						summaryData.put(i, subValue);
				}

				if (dBRuleGroup.contains("Cancel Fee Within Flat Cancel Period")
						|| dBRuleGroup.contains("Cancel Fee After Flat Cancel Period")) {
					String cancelFeeValue = "Fee = "
							+ (dBRuleName.substring(0, dBRuleName.indexOf("Cancel Fee")).trim());
					subValue.put(dBRuleGroup + "_" + dBRuleName, cancelFeeValue);
					summaryData.put(i, subValue);
					break;
				}

				if (dBRuleGroup.contains("Payee")) {
					String payeeDetails = "";
					if (dBRuleName.contains("Standard Payee")) {
						payeeDetails = "Dealer address used for Payee";
					} else if (dBRuleName.contains("AUL Payee")) {
						payeeDetails = "AUL CLEARING used for Payee";
					} else if (dBRuleName.contains("Blank Payee")) {
						payeeDetails = "Blank Payee used";
					} else if (dBRuleName.contains("Customer Payee")) {
						payeeDetails = "Customer address used for Payee";
					} else if (dBRuleName.contains("Dealer Payee")) {
						payeeDetails = "Dealer address used for Payee";
					} else if (dBRuleName.contains("Lender Payee")) {
						payeeDetails = "Lender address used for Payee";
					}
					subValue.put(dBRuleGroup + "_" + dBRuleName, payeeDetails);
					summaryData.put(i, subValue);
					break;
				}

				if (dBRuleGroup.contains("Refund Based On")) {
					String premiumDetails = "";

					if (dBRuleName.contains("Total Premium")) {
						premiumDetails = "AUL premium basis (dlr pd) amount = $" + removeZeroes(dealerAmount);
					} else {
						premiumDetails = "Cust paid basis amount = $" + removeZeroes(custAmount);
					}
					subValue.put(dBRuleGroup + "_" + dBRuleName, premiumDetails);
					summaryData.put(i, subValue);
					break;
				}
			}

		} catch (Exception e) {
		}

		return summaryData;
	}

	/*
	 * To compare value for hashMap
	 */
	public boolean verifyCancellationeRuleWithRuleInfoView(HashMap<String, String> ruleMap,
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

	/**
	 * This function is used to return rule info view summary data in a hashmap
	 * 
	 * 
	 */
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
				subValue.put(ruleGroup + "_" + ruleName, ruleResult);
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
			System.out.println(e.getMessage());
		}
		return summaryData;
	}

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

	/**
	 * This function is used to return edited value of Refund Percent and Cancel fee
	 * 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, String> findEditedValue(HashMap<String, String> dbruleInfo, String ruleType,
			String ruleSubType) throws Exception {
		String dBRuleGroup = "";
		String dBRuleName = "";
		String mapKey = "";
		String mapValue = "";
		HashMap<String, String> editedValue = new HashMap<String, String>();

		for (Map.Entry mapElement : dbruleInfo.entrySet()) {
			dBRuleGroup = (String) mapElement.getKey();
			dBRuleName = (String) mapElement.getValue();
		}

		if (ruleType.equals("") && ruleSubType.equals(""))
			mapKey = dBRuleGroup;
		else
			mapKey = ruleSubType;

		if (dBRuleGroup.contains("Refund Percent")) {
			if (dBRuleName.contains("Standard Refund Percent")) {
				mapValue = "Refund Percent by Mileage";
			} else {
				mapValue = "Standard Refund Percent";
			}
			if (ruleSubType.contains("Lender") && dBRuleName.contains("Standard Refund Percent")) {
				mapValue = "100% Refund";
			}
		}

		if (dBRuleGroup.contains("Cancel Fee Within Flat Cancel Period")
				|| dBRuleGroup.contains("Cancel Fee After Flat Cancel Period")) {
			if (dBRuleName.contains("$25 Cancel Fee")) {
				mapValue = "$50 Cancel Fee";
			} else {
				mapValue = "$25 Cancel Fee";
			}
		}

		if (dBRuleGroup.contains("Payee")) {
			if (dBRuleName.contains("Dealer Payee"))
				mapValue = "Standard Payee";
			else
				mapValue = "Dealer Payee";
		}

		if (dBRuleGroup.contains("Refund Based On")) {
			if (dBRuleName.contains("Total Premium"))
				mapValue = "Customer Paid";
			else
				mapValue = "Total Premium";
		}

		editedValue.put(mapKey, mapValue);

		return editedValue;
	}

	public void findAccountAndEditAccountRule(HashMap<String, String> editedRefundRule, String accountName) {
		try {
			waitForSomeTime(5);
			// type("primaryAccountFilterNew", accountName);
			type("primaryAccountFilterRowAccountName", accountName);
			waitForSomeTime(5);
			click("firstRowAccoundBuilder");
			if (editedRefundRule.size() > 1) {
				for (Map.Entry<String, String> set1 : editedRefundRule.entrySet()) {
					HashMap<String, String> mapFromSet = new HashMap<String, String>();
					mapFromSet.put(set1.getKey(), set1.getValue());
					editAccountLevelRulesToAccountId(mapFromSet);
				}
			} else {
				editAccountLevelRulesToAccountId(editedRefundRule);
			}
			click("SaveRule");
			waitForSomeTime(5);
		} catch (Exception e) {
		}

	}

	public boolean compareValuesforEditedAccountRule(HashMap<String, String> editedRefundRule,
			HashMap<String, String> editedDbruleInfo) {
		boolean matchFlag = false;

		if (editedRefundRule.equals(editedDbruleInfo))
			matchFlag = true;

		return matchFlag;
	}

	/**
	 * This function is used to Compare Value of dbRuleInfoView And Value return
	 * from getRuleInfoValue
	 * 
	 * 
	 */
	public boolean compareValuesNew(HashMap<Integer, HashMap<String, String>> ruleInfoViewValue,
			HashMap<String, String> dbruleInfo) {
		boolean matchFlag = false;
		int count = 0;
		int size = dbruleInfo.size();
		for (int i = 1; i <= size; i++) {

			Map<String, String> uiMap1 = dbruleInfo;
			if (ruleInfoViewValue.containsValue(uiMap1)) {
				count = count + 1;
				break;
			} else {

				continue;
			}
		}
		if (count >= 1) {
			matchFlag = true;
		}
		return matchFlag;
	}

	public void findAccountAndEditAccountRuleRows(HashMap<String, String> editedRefundRule, String accountName,
			String ProCode) {
		try {
			waitForSomeTime(5);
			try {
				type("primaryAccountFilterNew", accountName);
			} catch (Exception e) {
				type("primaryAccountFilterRowAccountName", accountName);
			}
			waitForSomeTime(5);
			int count = listOfElements("RowCountAccoundBuilder").size();
			if (count > 1) {
				for (int i = 0; i < count; i++) {
					try {
						WebElement priceSheet = findElementByXpath(
								"//*[@AutomationId='Ocean_CancellationModule_AccountRulesBuilderView_AccountCancellationsDataGridControl']//*[@AutomationId='Row_"
										+ i
										+ "']//*[@AutomationId='Cell_PricesheetName']//*[@ClassName='TextBlockLite']");
						String priceSheetCode = priceSheet.getText();
						priceSheetCode = priceSheetCode.substring(0, 3);
						if (priceSheetCode.equalsIgnoreCase(ProCode)) {
							priceSheet.click();
						}
					} catch (Exception e) {
						WebElement priceSheet1 = findElementByXpath(
								"//*[@AutomationId='Ocean_CancellationModule_AccountRulesBuilderView_AccountCancellationsDataGridControl']//*[@AutomationId='Row_"
										+ i + "']//*[@AutomationId='Cell_PricesheetName']");
						priceSheet1.click();
					}
				}
			} else {
				click("firstRowAccoundBuilder");
			}
			if (editedRefundRule.size() > 1) {
				for (Map.Entry<String, String> set1 : editedRefundRule.entrySet()) {
					HashMap<String, String> mapFromSet = new HashMap<String, String>();
					mapFromSet.put(set1.getKey(), set1.getValue());
					editAccountLevelRulesToAccountId(mapFromSet);
				}
			} else {

				editAccountLevelRulesToAccountId(editedRefundRule);
			}
			click("SaveRule");
			waitForSomeTime(30);
		} catch (Exception e) {
		}

	}

	/**
	 * This function is used to return edited value of Account Level Rules
	 * 
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, String> editedAcountRuleValue(HashMap<Integer, HashMap<String, String>> dbruleInfo)
			throws Exception {
		String dBRuleGroup = "";
		String dBRuleName = "";
		String mapKey = "";
		String mapValue = "";
		HashMap<String, String> editedValue = new HashMap<String, String>();
		HashMap<String, String> matchRule = new HashMap<String, String>();
		for (Map.Entry mapElement : dbruleInfo.entrySet()) {
			matchRule = (HashMap<String, String>) mapElement.getValue();
			for (Map.Entry mapElement1 : matchRule.entrySet()) {
				dBRuleGroup = (String) mapElement1.getKey();
				dBRuleName = (String) mapElement1.getValue();

				if (dBRuleGroup.contains("Refund Percent") || dBRuleGroup.contains("Refund Percent")
						|| dBRuleGroup.contains("Refund Percent, Administrator")
						|| dBRuleGroup.contains("Refund Percent, Repossession")) {
					if (dBRuleName.contains("Standard Refund Percent")) {
						mapKey = dBRuleGroup;
						mapValue = "Refund Percent by Mileage";
					} else {
						mapKey = dBRuleGroup;
						mapValue = "Standard Refund Percent";
					}
					editedValue.put(mapKey, mapValue);
				}
				if (dBRuleGroup.contains("Refund Percent, Lender, Trade/Sale")) {
					mapKey = dBRuleGroup;
					mapValue = "100% Refund";

					editedValue.put(mapKey, mapValue);
				}

				if (dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period")
						|| dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims")
						|| dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims, Administrator")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period, Administrator")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period if Claims")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period if Claims, Administrator")
						|| dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period, Administrator")
						|| dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")
						|| dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale")
						|| dBRuleGroup.equals("Cancel Fee Override")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period, Repossession")
						|| dBRuleGroup.equals("Cancel Fee After Flat Cancel Period if Claims, Repossession")
						|| dBRuleGroup.equals("Cancel reason, Administrator")) {
					if (dBRuleName.contains("$25 Cancel Fee")) {
						mapKey = dBRuleGroup;
						mapValue = "$50 Cancel Fee";
					} else {
						mapKey = dBRuleGroup;
						mapValue = "$25 Cancel Fee";
					}
					editedValue.put(mapKey, mapValue);
				}

				if (dBRuleGroup.contains("Payee")) {
					if (dBRuleName.contains("Dealer Payee")) {
						mapKey = dBRuleGroup;
						mapValue = "Standard Payee";
					} else {
						mapValue = "Dealer Payee";
						mapKey = dBRuleGroup;
					}
					editedValue.put(mapKey, mapValue);
				}

				if (dBRuleGroup.contains("Refund Based On")) {
					if (dBRuleName.contains("Total Premium")) {
						mapValue = "Customer Paid";
						mapKey = dBRuleGroup;
					} else {
						mapValue = "Total Premium";
						mapKey = dBRuleGroup;
					}
					editedValue.put(mapKey, mapValue);
				}
				if (dBRuleGroup.contains("If Transferred")) {
					if (dBRuleName.contains("Cancellable")) {
						mapValue = "Non-Cancellable";
						mapKey = dBRuleGroup;
					} else {
						mapValue = "Cancellable";
						mapKey = dBRuleGroup;
					}
					editedValue.put(mapKey, mapValue);
				}
				if (dBRuleGroup.contains("Expiration Date")) {
					if (dBRuleName.contains("Standard New Car")) {
						mapValue = "Standard Used Car";
						mapKey = dBRuleGroup;
					} else {
						mapValue = "Standard New Car";
						mapKey = dBRuleGroup;
					}
					editedValue.put(mapKey, mapValue);
				}
				if ((dBRuleGroup.equals("Claims") || dBRuleGroup.equals("Claims, Administrator")
						|| dBRuleGroup.equals("Claims, Lender, Trade/Sale")
						|| dBRuleGroup.equals("Claims, Lender, NonPayment")
						|| dBRuleGroup.equals("Claims, Lender, Unwind")
						|| dBRuleGroup.equals("Claims, Repossession"))) {
					if (dBRuleName.contains("Deduct Claims")) {
						mapValue = "Do not Deduct Claims";
						mapKey = dBRuleGroup;
					} else {
						mapValue = "Deduct Claims";
						mapKey = dBRuleGroup;
					}
					editedValue.put(mapKey, mapValue);
				}
			}
		}
		return editedValue;
	}

	/**
	 * This function is used to edit account Level Rule based on given parameter
	 * 
	 */

	public void editAccountLevelRulesToAccountId(HashMap<String, String> searchParamater) {

		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String searchParamaters = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			if (searchParamaters.contains("_Total Premium") || searchParamaters.contains("_Customer Paid"))
				searchParamaters = "Refund Based On";
			if (valueToInput != "" && valueToInput != null) {
				/// For Adding Non-ComplianceRule
				if ((searchParamaters.equals("Refund Based On") || searchParamaters.equals("Payee")
						|| searchParamaters.equals("If_Transferred") || searchParamaters.equals("Expiration Date"))) {
					click("Non-ComplianceRule");
					switch (searchParamaters) {
					case "Refund Based On":
						click("RefundBasedOnExpender");
						WebElement refund = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name= '" + valueToInput + "']");
						if (refund.isSelected() == false || refund.isSelected() == true) {
							refund.click();
						}
						click("RefundBasedOnExpender");
						break;
					case "Payee":
						click("PayeeExpender");
						WebElement paye = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name='" + valueToInput + "']");
						paye.click();
						click("PayeeExpender");
						break;
					case "If_Transferred":
						click("IfTransferred");
						WebElement Transferred = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name= '" + valueToInput + "']");
						Transferred.click();
						click("IfTransferred");
						break;
					case "Expiration Date":
						click("Expiration_Date");
						WebElement Expiration = findElementByXpath(
								"//*[@ClassName='RadioButton'and @Name= '" + valueToInput + "']");
						Expiration.click();
						click("Expiration_Date");
						break;
					}
					click("Non-ComplianceRule");
				}
				// For adding Claims Rules
				else if ((searchParamaters.equals("Claims") || searchParamaters.equals("Claims, Administrator")
						|| searchParamaters.equals("Claims, Lender, Trade/Sale")
						|| searchParamaters.equals("Claims, Lender, NonPayment")
						|| searchParamaters.equals("Claims, Lender, Unwind")
						|| searchParamaters.equals("Claims, Repossession"))) {
					click("ClaimsDeduction");
					switch (searchParamaters) {
					case "Claims":
						click("Claims");
						WebElement Claims = findElementByXpath(
								"//*[@Name='Claims']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						Claims.click();
						break;
					case "Claims, Administrator":
						click("ClaimsAdministrator");
						WebElement ClaimsAdmin = findElementByXpath(
								"//*[@Name='Claims, Administrator']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						ClaimsAdmin.click();
						break;
					case "Claims, Lender, Trade/Sale":
						click("ClaimsLenderTradeSale");
						WebElement ClaimsLenderTrade = findElementByXpath(
								"//*[@Name='Claims, Lender, Trade/Sale']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						ClaimsLenderTrade.click();
						break;
					case "Claims, Lender, NonPayment":
						click("ClaimsLenderNonPayment");
						WebElement ClaimsLenderNonPayment = findElementByXpath(
								"//*[@Name='Claims, Lender, NonPayment']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						ClaimsLenderNonPayment.click();
						break;
					case "Claims, Lender, Unwind":
						click("ClaimsLenderUnwind");
						WebElement ClaimsLenderUnwind = findElementByXpath(
								"//*[@Name='Claims, Lender, Unwind']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name= '"
										+ valueToInput + "']");
						ClaimsLenderUnwind.click();
						break;
					case "Claims, Repossession":
						click("ClaimsRepossession");
						WebElement ClaimsRepossession = findElementByXpath(
								"//*[@Name='Claims, Repossession']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name= '"
										+ valueToInput + "']");
						ClaimsRepossession.click();
						break;
					}
					click("ClaimsDeduction");
				}
				// For Adding Refunds Rules

				else if ((searchParamaters.equals("Refund Percent")
						|| searchParamaters.equals("Refund Percent, Administrator")
						|| searchParamaters.equals("Refund Percent, Lender, Trade/Sale")
						|| searchParamaters.equals("Refund Percent, Repossession"))) {
					click("listRefundPercent", 0);
					switch (searchParamaters) {
					case "Refund Percent":
						click("listRefundPercent", 1);
						WebElement refundper = findElementByXpath(
								"//*[@Name='Refund Percent']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						refundper.click();
						click("listRefundPercent", 1);
						break;
					case "Refund Percent, Administrator":
						click("RefundPercentAdmin");
						WebElement refundperAdmins = findElementByXpath(
								"//*[@Name='Refund Percent, Administrator']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						refundperAdmins.click();
						click("RefundPercentAdmin");
						break;
					case "Refund Percent, Lender, Trade/Sale":
						click("RefundPercentLenderTradeSale");
						WebElement refundPercentLenderTradeSale = findElementByXpath(
								"//*[@Name='Refund Percent, Lender, Trade/Sale']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						refundPercentLenderTradeSale.click();
						click("RefundPercentLenderTradeSale");
						break;
					case "Refund Percent, Repossession":
						click("RefundPercentRepossession");
						WebElement RefundPercentRepossession = findElementByXpath(
								"//*[@Name='Refund Percent, Repossession']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						RefundPercentRepossession.click();
						click("RefundPercentRepossession");
						break;
					}
					click("listRefundPercent", 0);
				}
				// for adding Cancel reason rules
				else if ((searchParamaters.equals("Cancel reason, Administrator"))) {
					click("CancelReasons");
				}

				// For Adding Cancel Fee Rules

				else if ((searchParamaters.equals("Cancel Fee Within Flat Cancel Period")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period if Claims")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period if Claims, Administrator")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Administrator")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims, Administrator")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period, Administrator")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale")
						|| searchParamaters.equals("Cancel Fee Override")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period, Repossession")
						|| searchParamaters.equals("Cancel Fee After Flat Cancel Period if Claims, Repossession")
						|| searchParamaters.equals("Cancel reason, Administrator"))) {
					click("CancelFee");
					switch (searchParamaters) {

					case "Cancel Fee Within Flat Cancel Period":
						click("CancelFeeWithinFlatCancelPeriodExpender");
						WebElement cancelfeeflatPeriod = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelfeeflatPeriod.click();
						click("CancelFeeWithinFlatCancelPeriodExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period if Claims":
						click("CancelFeeWithinFlatCancelPeriodifClaimsExpender");
						WebElement CancelFeeWithinFlatCancelPeriodifClaims = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						CancelFeeWithinFlatCancelPeriodifClaims.click();
						click("CancelFeeWithinFlatCancelPeriodifClaimsExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period if Claims, Administrator":
						click("CancelFeeWithinFlatCancelPeriodifClaimsAdministratorExpender");
						WebElement CancelFeeWithinFlatCancelPeriodifClaimsAdministrator = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims, Administrator']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						CancelFeeWithinFlatCancelPeriodifClaimsAdministrator.click();
						click("CancelFeeWithinFlatCancelPeriodifClaimsAdministratorExpender");
						break;
					case "Cancel Fee After Flat Cancel Period":
						click("cancelFeeAfterFlatCancelPeriodExpender");
						WebElement cancelFeeAfterFlatCancelPeriod = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period']/following-sibling::*[@ClassName='TreeViewItem']//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriod.click();
						click("cancelFeeAfterFlatCancelPeriodExpender");
						break;
					case "Cancel Fee After Flat Cancel Period, Administrator":
						click("cancelFeeAfterFlatCancelPeriodAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodAdministrator = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodAdministrator.click();
						click("cancelFeeAfterFlatCancelPeriodAdministratorExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims":
						click("cancelFeeAfterFlatCancelPeriodifClaimsExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaims = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaims.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims, Administrator":
						click("cancelFeeAfterFlatCancelPeriodifClaimsAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaimsAdmin = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaimsAdmin.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsAdministratorExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period, Administrator":
						click("cancelFeeWithinFlatCancelPeriodAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeWithinFlatCancelPeriodAdmin = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeWithinFlatCancelPeriodAdmin.click();
						click("cancelFeeWithinFlatCancelPeriodAdministratorExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale":
						click("cancelFeeWithinFlatCancelPeriodLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeWithinFlatCancelPeriodLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeWithinFlatCancelPeriodLenderTradeSale.click();
						click("cancelFeeWithinFlatCancelPeriodLenderTradeSaleExpender");
						break;
					case "Cancel Fee After Flat Cancel Period, Lender, Trade/Sale":
						click("cancelFeeAfterFlatCancelPeriodLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodLenderTradeSale.click();
						click("cancelFeeAfterFlatCancelPeriodLenderTradeSaleExpender");
						break;
					case "Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale":
						click("cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSale.click();
						click("cancelFeeWithinFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale":
						click("cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSale = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSale.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsLenderTradeSaleExpender");
						break;
					case "Cancel Fee Override":
						click("cancelFeeOverrideExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeOverride = findElementByXpath(
								"//*[@Name='Cancel Fee Override']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeOverride.click();
						click("cancelFeeOverrideExpender");
						break;
					case "Cancel Fee After Flat Cancel Period, Repossession":
						click("cancelFeeAfterFlatCancelPeriodRepossessionExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodRepossession = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period, Repossession']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodRepossession.click();
						click("cancelFeeAfterFlatCancelPeriodRepossessionExpender");
						break;
					case "Cancel Fee After Flat Cancel Period if Claims, Repossession":
						click("cancelFeeAfterFlatCancelPeriodifClaimsRepossessionExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelFeeAfterFlatCancelPeriodifClaimsRepossession = findElementByXpath(
								"//*[@Name='Cancel Fee After Flat Cancel Period if Claims, Repossession']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelFeeAfterFlatCancelPeriodifClaimsRepossession.click();
						click("cancelFeeAfterFlatCancelPeriodifClaimsRepossessionExpender");
						break;
					case "Cancel reason, Administrator":
						click("cancelreasonAdministratorExpender");
						try {
							click("IncreaseRepeat", 1);
						} catch (Exception e) {
						}
						;
						WebElement cancelreasonAdministrator = findElementByXpath(
								"//*[@Name='Cancel reason, Administrator']/following-sibling::*//*[@ClassName='RadioButton'and @Name='"
										+ valueToInput + "']");
						cancelreasonAdministrator.click();
						click("cancelreasonAdministratorExpender");
						break;
					}
					click("CancelFee");
				}
			}
		}

	}

	public boolean compareValuesforHashMap(HashMap<String, String> editedRefundRule,
			HashMap<String, String> editedDbruleInfo) {
		boolean matchFlag = false;

		if (editedRefundRule.equals(editedDbruleInfo))
			matchFlag = true;

		return matchFlag;
	}

	public HashMap<String, Integer> calculateCancelDateTC14(String effectiveDate, String saleDate) throws Exception {
		HashMap<String, Integer> cancelDays = new HashMap<String, Integer>();
		int flatCanceldays = 0;
		int afterflatCanceldays = 0;
		long daysdifference;
		String dateCancel = "";
		LocalDate cancelDate;
		long daysdiffAfterFlat = 0;
		long daysdiffFlat = 0;
		LocalDate dateEffective = LocalDate.parse(effectiveDate);
		LocalDate dateSale = LocalDate.parse(saleDate);
		daysdifference = ChronoUnit.DAYS.between(dateEffective, dateSale);
		if (daysdifference > 0) {
			flatCanceldays = 25;
			afterflatCanceldays = 72;
			dateCancel = addDateCancellation(saleDate, afterflatCanceldays);
			cancelDate = LocalDate.parse(dateCancel);
			daysdiffFlat = ChronoUnit.DAYS.between(dateSale, cancelDate);
		} else {
			int i = (int) daysdifference;
			i = (i * -1);
			flatCanceldays = 25 + i;
			afterflatCanceldays = 72 + i;
			dateCancel = addDateCancellation(saleDate, afterflatCanceldays);
			cancelDate = LocalDate.parse(dateCancel);
			daysdiffAfterFlat = ChronoUnit.DAYS.between(dateSale, cancelDate);
		}

		cancelDays.put("flatCanceldays", flatCanceldays);
		cancelDays.put("afterflatCanceldays", afterflatCanceldays);
		cancelDays.put("daysdifference", (int) daysdifference);
		cancelDays.put("daysdiffAfterFlat", (int) daysdiffAfterFlat);
		cancelDays.put("daysdiffFlat", (int) daysdiffFlat);
		return cancelDays;

	}

	public boolean compareValuesforRefundP(HashMap<Integer, HashMap<String, String>> ruleInfoViewValue,
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

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public HashMap<String, String> getRuleInfoExpectedResultTC14(HashMap<Integer, HashMap<String, String>> dbruleInfo,
			HashMap<String, String> contractDetails, int daysDiff) throws Exception {
		HashMap<String, String> subValue = new HashMap<String, String>();
		String saleDate = contractDetails.get("Sale_Date");
		String expireDate = contractDetails.get("EXPIRATION_DATE");
		String cancelMiles = contractDetails.get("cancelMiles");
		String planMile = contractDetails.get("PLAN_MILES");
		String cusPaid = contractDetails.get("Customer_Paid");
		String aulPremium = contractDetails.get("Premium");
		String saleMiles = contractDetails.get("SaleMileage");
		String dBRuleGroup = "";
		String dBRuleName = "";
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
			for (Map.Entry mapElement : dbruleInfo.entrySet()) {
				matchRule = (HashMap<String, String>) mapElement.getValue();
				for (Map.Entry mapElement1 : matchRule.entrySet()) {
					dBRuleGroup = (String) mapElement1.getKey();
					dBRuleName = (String) mapElement1.getValue();

					if (dBRuleGroup.contains("Refund Percent")) {
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
						System.out.println("planDays=" + planDays + "milesDiff=" + milesDiff + "planMiles=" + planMiles
								+ "daysUtilization" + daysUtilization + "milesUtilization" + milesUtilization
								+ "RefByDay" + RefByDay + "RefByMile" + RefByMile);
						if (dBRuleName.contains("Standard Refund Percent")) {
							ruleResult1 = "Days diff=" + daysDiff + ", " + "Miles diff=" + milesDiff;
							ruleResult2 = "Days% used=" + (daysUtilization) + ", " + "Miles% used="
									+ (milesUtilization);
							ruleResult3 = "Ref% used=" + (RefByDay > RefByMile ? formatRefByMile : formatRefByDay);
							refundPer = (RefByDay > RefByMile ? formatRefByMile : formatRefByDay);
						} else if (dBRuleName.contains("Refund Percent by Time")) {
							ruleResult1 = "Cancel method used = T";
							ruleResult2 = "Days diff=" + daysDiff;
							ruleResult3 = "Ref% used=" + formatRefByDay;
							refundPer = formatRefByDay;
						} else if (dBRuleName.contains("Refund Percent by Mileage")) {
							ruleResult1 = "Cancel method used = M";
							ruleResult2 = "Miles diff=" + milesDiff;
							ruleResult3 = "Ref% used=" + formatRefByMile;
							refundPer = formatRefByMile;
						} else if (dBRuleName.contains("100% Refund")) {
							refundPer = "100";
						}
					}
					if (dBRuleGroup.equals("Refund Based On")) {
						if (dBRuleName.contains("Customer Paid")) {
							refundCalAmount = cusPaid;

						} else if (dBRuleName.contains("Total Premium")) {
							refundCalAmount = aulPremium;
						}
					}
					if (dBRuleGroup.equals("Cancel Fee Within Flat Cancel Period")) {
						flatCanceFee = dBRuleName.toString();
						flatCanceFee = flatCanceFee.substring(1, 3);
					} else if (dBRuleGroup.equals("Cancel Fee After Flat Cancel Period")) {
						cancelFeeAfterFlat = dBRuleName.toString();
						cancelFeeAfterFlat = cancelFeeAfterFlat.substring(1, 3);

					}
				}
			}
			if (daysDiff > 60) {
				cancelFee = cancelFeeAfterFlat;
				cancelFee = cancelFee + ".00";
			} else {
				cancelFee = flatCanceFee;
				cancelFee = cancelFee + ".00";
			}
			grossRefund = calculateGrossRefund(refundCalAmount, refundPer);
			String aulRef = calculateAULRefund(grossRefund, cancelFee);
			String dealerfincoPortion = calculateDealerFincoPortion(cusPaid, aulPremium, refundPer);
			String custRefund = calculateCustomerRefund(dealerfincoPortion, aulRef);

			subValue.put("REFUND_PERCENTAGE", refundPer);
			subValue.put("GROSS_REFUND", grossRefund);
			subValue.put("AULREFUND", aulRef);
			subValue.put("DEALER_REFUND", dealerfincoPortion);
			subValue.put("CUSTOMER_REFUND", custRefund);
			subValue.put("CANCEL_FEE_AMOUNT", cancelFee);
			System.out.print(subValue);
		} catch (Exception e) {
		}

		return subValue;
	}

	public String calculateGrossRefund(String refundCalAmount, String refundPer) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		float refundAmount = Float.parseFloat(refundCalAmount);
		float refundPer1 = Float.parseFloat(refundPer);
		double grossRef = (refundAmount * refundPer1) / 100;
		String grossRefund = df.format(grossRef);
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

	public String calculateDealerFincoPortion(String cusPaid, String aulPremium, String refundPer) throws Exception {
		DecimalFormat df = new DecimalFormat("0.00");
		float customerPaid = Float.parseFloat(cusPaid);
		float aulPre = Float.parseFloat(aulPremium);
		float delearPaid = (customerPaid - aulPre);
		float refPer = Float.parseFloat(refundPer);
		double dealerfinco = (delearPaid * refPer) / 100;
		String delaerFincoPortion = df.format(dealerfinco);
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

	public HashMap<String, String> removeComma(HashMap<String, String> calculatedCancellationValue) {
		HashMap<String, String> cancellationValueWithoutComma = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : calculatedCancellationValue.entrySet()) {
			String RuleGroup = entry.getKey();
			String RuleUsed = entry.getValue();
			if (RuleUsed.contains(",")) {
				RuleUsed = RuleUsed.replaceAll(",", "");
			}
			cancellationValueWithoutComma.put(RuleGroup, RuleUsed);
		}
		return cancellationValueWithoutComma;
	}

	/*
	 * to get Compliance Rules
	 */
	public HashMap<String, String> getComplianceRules(String contractNumber, String roleName) throws Exception {
		String priceSheetGroup = compliance_getPricesheetIdbasedOnCert(contractNumber);
		System.out.println("priceSheet==" + priceSheetGroup);
		String state = compliance_getStateDisplayNamebasedOnCertAndPAccount(contractNumber, roleName);
		System.out.println("state==" + state);
		// for compliance rule pick
		complainceDataProcess(priceSheetGroup, priceSheetGroup, state);
		// process in compliance tab
		HashMap<String, String> ruleMap = new HashMap<String, String>();
		ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
		ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelReasonsInTree", "cancelReasonsItems"));
		ruleMap.putAll(complainceRuleTreeProcessForAllRule("claimsDeductionInTree", "claimsDeductionInTreeItems"));
		ruleMap.putAll(complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
		ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
		System.out.println(ruleMap);
		return ruleMap;
	}

	/**
	 * function process the detail in cancellation page on rule info view .
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> getComplainceRuleWithRuleInfoViewMap() throws Exception {
		// waitForSomeTime(30);
		HashMap<String, String> matchRule = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
		for (int i = 1; i < ruleInfoViewValue.size(); i++) {
			for (Entry<String, String> ent : ruleInfoViewValue.get(i).entrySet()) {
				matchRule.put(ent.getKey(), ent.getValue());
			}
		}
		if (matchRule.containsKey("STDNCB")) {
			matchRule.remove("STDNCB");
		}
		if (matchRule.containsKey("STDPRORATE")) {
			matchRule.remove("STDPRORATE");
		}
		return matchRule;
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
		System.out.println("versionNumber1===" + versionNumber);
		waitForSomeTime(10);
		type("stateFilterRow", state);
		if (getTextOfElement("getDataRow").length() < 1) {
			clearTextBox("stateFilterRow");
			type("clickOnState", "Multi-State");
			click("clickOnSearchComplianceButton");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(ObjectRepo.fetchOR("loadingIcon")));
			waitForSomeTime(5);
			versionNumber = getPriceSheetGroupVersion(priceSheetGroup, "Multi-State");
			System.out.println("versionNumber2===" + versionNumber);
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
	 * This function is used to check AllCancel Transaction history on data in a
	 * hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getSearchResultForAllCancelTransHistoryData(int i) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String processDate = dateFormat.format(dateFormat.parse(getValue("listOfAllTransProcessDate", i)));
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Contract", getValue("listOfAllTransContractId", i));
		summaryData.put("cancelStatus", getValue("listOfAllTransStatus", i));
		summaryData.put("adjType", getValue("listOfAllTransAdjType", i));
		summaryData.put("processDate", processDate);
		summaryData.put("refundPercent", getValue("listOfAllTransRefundPercent", i));
		summaryData.put("netRefund", getValue("listOfAllTransRefundAmount", i));
		summaryData.put("cancelMiles", getValue("listOfAllTransCancelMile", i));
		click("swipeRightInAllCancelTransHistory");
		String cancelDate = dateFormat.format(dateFormat.parse(getValue("listOfAllTransCancelDate", i)));
		summaryData.put("cancelDate", cancelDate);
		summaryData.put("initiatedBy", getValue("listOfAllTransInitiatedBy", i));
		summaryData.put("cancelReason", getValue("listOfAllTransCancelReason", i));
		// click("scrollPageDownForCancelHistory");
		click("swipeLeftInAllCancelTransHistory");
		return summaryData;
	}

	/**
	 * This common function is used to go to clickOnHoldWithReason
	 * 
	 * @return
	 * 
	 */
	public void clickHoldWithReason(String[] onHoldReason) {
		WebElement we = null;
		for (String onHold : onHoldReason) {
			switch (onHold.toLowerCase()) {
			case "cancellation mileage":
				we = findElementByXpath("//*[@Name='Cancellation Mileage']");
				break;
			case "cancellation date":
				we = findElementByXpath("//*[@Name='Cancellation Date']");
				break;
			case "customer signature":
				we = findElementByXpath("//*[@Name='Customer Signature']");
				break;
			case "dealer signature":
				we = findElementByXpath("//*[@Name='Dealer Signature']");
				break;
			case "leinholder signature":
				we = findElementByXpath("//*[@Name='Leinholder Signature']");
				break;
			case "repossession paperwork":
				we = findElementByXpath("//*[@Name='Repossession Paperwork']");
				break;
			case "total loss paperwork":
				we = findElementByXpath("//*[@Name='Total Loss Paperwork']");
				break;
			case "paid in full letter":
				we = findElementByXpath("//*[@Name='Paid in Full Letter']");
				break;
			case "under review":
				we = findElementByXpath("//*[@Name='Under Review']");
				break;
			case "dealer status":
				we = findElementByXpath("//*[@Name='Dealer Status']");
				break;
			case "other":
				we = findElementByXpath("//*[@Name='Other']");
				break;
			default:
				we = findElementByXpath("//*[@Name='Other']");
				break;
			}
		}
		if (we.isSelected() == false)
			we.click();
		click("ClickOnHoldNxtBtn");
		waitForSomeTime(10);
		click("ClickOnHoldNxtBtn");
		waitForSomeTime(2);
		click("onHoldSendByNoneOnCancelPage");
		click("ClickOnHoldNxtBtn");
		click("ClickOnHoldNxtBtn");
		removeErrorMessages();
	}

	/**
	 * This common function is used to go to clickOnDeniedWithReason
	 * 
	 * @return
	 * 
	 */
	public void clickDeniedWithReason(String[] deniedReason) {
		WebElement we = null;
		for (String denied : deniedReason) {
			switch (denied.toLowerCase()) {
			case "expired by time":
				we = findElementByXpath("//*[@Name='Expired by Time']");
				break;
			case "expired by mileage":
				we = findElementByXpath("//*[@Name='Expired by Mileage']");
				break;
			case "no refund after fee":
				we = findElementByXpath("//*[@Name='No Refund after Fee']");
				break;
			case "no refund after claims":
				we = findElementByXpath("//*[@Name='No Refund after Claims']");
				break;
			case "not cancellable":
				we = findElementByXpath("//*[@Name='Not Cancellable']");
				break;
			case "other":
				we = findElementByXpath("//*[@Name='Other']");
				break;
			default:
				we = findElementByXpath("//*[@Name='Other']");
				break;
			}
		}
		if (we.isSelected() == false)
			we.click();
		click("ClickOnDeniedNxtBtn");
		waitForSomeTime(15);
		click("ClickOnDeniedNxtBtn");
		waitForSomeTime(2);
		click("deniedSendByNoneOnCancelPage");
		click("ClickOnDeniedNxtBtn");
		click("ClickOnDeniedNxtBtn");
		removeErrorMessages();
	}

	/**
	 * This function is used to process the contract search on Mail Service Tab
	 * 
	 * 
	 */
	public void processSearchContract(HashMap<String, String> uiSearchData, HashMap<String, String> contractData,
			String action) throws Exception {
		// Navigate to mail service tab
		goToCancellationTab();
		goToMailServiceTab();
		// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		clickSearchContractFromCancellationSearchResult(contractData);
		if (action.equals("Cancel"))
			cancelContract(contractData);

	}

	/**
	 * This function is used to cancel an active contract
	 * 
	 * @param contractData
	 * 
	 * 
	 */
	public void cancelContract(HashMap<String, String> contractData) throws Exception {
		clickCancelButtonAndNavigateToNewCancellationTab();
		// Enter the cancellation parameters on New Cancellation screen
		enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "NonPayment", "",
				convertDateCancellation(contractData.get("SALE_DATE"), 30), "");
		// click ok for cancellation completed successfully
		click("okClick");
		waitForSomeTime(30);
	}

	/**
	 * This function is used to perform Authorize/Hold/Denied Action
	 * 
	 * @param actionPerform
	 * 
	 * 
	 */
	public void performAction(String actionPerform) throws Exception {
		String buttonClick = "click" + actionPerform + "Button";
		click(buttonClick);
		if (actionPerform.equals("OnHold")) {
			// fill OnHold form with reason
			clickHoldWithReason(new String[] { "Under Review" });
		} else if (actionPerform.equals("Denied")) {
			// fill Denied form with reason
			clickDeniedWithReason(new String[] { "No Refund after Fee" });
		} else if (actionPerform.equals("Authorized")) {
			click("cancelExtraYesNoAfterAuthorize");
			removeErrorMessages();
		}
		waitForSomeTime(10);
	}

	/**
	 * This function is used to format data from database
	 * 
	 * 
	 */
	public HashMap<String, String> dataFormatAsUI(HashMap<String, String> dbDataContractHistoryData) throws Exception {
		if (dbDataContractHistoryData.get("Status").equalsIgnoreCase("Quote"))
			dbDataContractHistoryData.put("Process_Date", "");
		else
			dbDataContractHistoryData.put("Process_Date",
					convertDate(dbDataContractHistoryData.get("Process_Date"), 0));
		dbDataContractHistoryData.put("CANCEL_DATE", convertDate(dbDataContractHistoryData.get("CANCEL_DATE"), 0));
		float netRefund = Float.parseFloat(dbDataContractHistoryData.get("Net_Refund"));
		DecimalFormat df = new DecimalFormat("0.00");
		dbDataContractHistoryData.put("Net_Refund", df.format(netRefund));
		dbDataContractHistoryData.remove("Process_Date");
		return dbDataContractHistoryData;

	}

	/*
	 * function process the detail in compliance page to find latest version row.
	 * 
	 * @throws Exception
	 */
	public void complainceDataSearch(String priceSheetGroup, String state) throws Exception {
		String versionNumber = "";
		/*
		 * goToComplianceTab(); try { takeScreenshot(); click("clickOK"); }
		 * catch(Exception e) {
		 * 
		 * } goToContractBuilderTab();
		 */
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
	 * function fetch the applied rule in compliance page.
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> complainceDefaultRuleTree(HashMap<String, String> ruleInfoViewValue) {
		HashMap<String, String> ruleMap = null;
		for (Map.Entry<String, String> entry : ruleInfoViewValue.entrySet()) {
			String RuleGroup = entry.getKey();
			String RuleUsed = entry.getValue();
			if ((RuleGroup.equals("Refund Based On") || RuleGroup.equals("Payee") || RuleGroup.equals("If_Transferred")
					|| RuleGroup.equals("Expiration Date"))) {
				click("NonCompliancecompliance");
				switch (RuleGroup) {
				case "Refund Based On":
					click("RefundBasedOnExpender");
					List<WebElement> refund = listOfElementsByXpath(
							"//*[@Name='Refund Based On']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : refund) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
						click("RefundBasedOnExpender");
						break;
					}
				case "payee":
					click("PayeeExpender");
					List<WebElement> payee = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : payee) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
					}
					click("PayeeExpender");
					break;
				}
				click("NonCompliancecompliance");
			}
			// For fetching Claims Rules
			else if (RuleGroup.equals("Claims")) {
				click("ClaimsDeduction");
				switch (RuleGroup) {
				case "Claims":
					click("Claims");
					List<WebElement> claims = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : claims) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
					}
					click("Claims");
					break;
				}
				click("ClaimsDeduction");
			}

			// For Fetching Refunds Rules

			else if ((RuleGroup.equals("Refund Percent"))) {
				click("listRefundPercent", 0);
				switch (RuleGroup) {
				case "Refund Percent":
					List<WebElement> RefundPercent = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : RefundPercent) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
					}
					break;
				}
				click("listRefundPercent", 0);
			}
			// For Fetching Cancel Fee Rules

			else if ((RuleGroup.equals("Cancel Fee Within Flat Cancel Period")
					|| RuleGroup.equals("Cancel Fee Within Flat Cancel Period if Claims")
					|| RuleGroup.equals("Cancel Fee After Flat Cancel Period")
					|| RuleGroup.equals("Cancel Fee After Flat Cancel Period if Claims")
					|| RuleGroup.equals("Flat Cancel Period"))) {
				click("CancelFee");
				switch (RuleGroup) {
				case "Cancel Fee Within Flat Cancel Period":
					click("CancelFeeWithinFlatCancelPeriodExpender");
					List<WebElement> cancelFeeWithinFlat = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : cancelFeeWithinFlat) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
					}
					click("CancelFeeWithinFlatCancelPeriodExpender");
					break;
				case "Cancel Fee Within Flat Cancel Period if Claims":
					click("CancelFeeWithinFlatCancelPeriodifClaimsExpender");
					List<WebElement> cancelFeeWithinFlatifClaims = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : cancelFeeWithinFlatifClaims) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
					}
					click("CancelFeeWithinFlatCancelPeriodifClaimsExpender");
					break;
				case "Cancel Fee After Flat Cancel Period":
					click("cancelFeeAfterFlatCancelPeriodExpender");
					List<WebElement> cancelFeeAfterFlat = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : cancelFeeAfterFlat) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
							click("cancelFeeAfterFlatCancelPeriodExpender");
							break;
						}
					}
				case "Cancel Fee After Flat Cancel Period if Claims":
					click("cancelFeeAfterFlatCancelPeriodifClaimsExpender");
					List<WebElement> cancelFeeAfterFlatifClaims = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : cancelFeeAfterFlatifClaims) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
							click("cancelFeeAfterFlatCancelPeriodifClaimsExpender");
							break;
						}
					}
				case "Flat Cancel Period":
					doubleClick("FlatCancelPeriodExpenderCompliance");
					List<WebElement> cancelPeriod = listOfElementsByXpath(
							"//*[@Name='" + RuleGroup + "']/following-sibling::*//*[@ClassName='RadioButton']");
					for (WebElement we : cancelPeriod) {
						if (we.isSelected()) {
							String ruleName = we.getText();
							System.out.println("ruleName===" + ruleName);
							ruleMap = new HashMap<String, String>();
							ruleMap.put(RuleGroup, ruleName);
							System.out.println("ruleMap===" + ruleMap);
						}
					}
					click("FlatCancelPeriodExpenderCompliance");
					break;
				}
				click("CancelFee");
			}
		}
		return ruleMap;
	}

	/**
	 * This function is used to return rule info view ruleName & ruleGroup summary
	 * data in a hashmap
	 * 
	 * 
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
	 * This function is used to return cancellation standard calculated summary data
	 * in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getStandardCalAddrresDetails() throws Exception {
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
		summaryData.put("Payee", getValue("standViewPayeeTextBox"));
		summaryData.put("Address", getValue("standViewAdressTextBox"));
		summaryData.put("City", getValue("standViewCityTextBox"));
		summaryData.put("State", getValue("standViewStateTextBox"));
		summaryData.put("Zip", getValue("standViewZipTextBox"));
		return summaryData;
	}

	public HashMap<String, String> getPayeeDetail(String payee) throws Exception {
		HashMap<String, String> getPayeeAddDetail = new HashMap<String, String>();
		getPayeeAddDetail.put("Payee", getValue("cancellationViewPayeeName"));
		getPayeeAddDetail.put("Address", getValue("cancellationViewPayeeAddress"));
		getPayeeAddDetail.put("City", getValue("cancellationViewPayeeCity"));
		getPayeeAddDetail.put("State", getValue("cancellationViewPayeeState"));
		getPayeeAddDetail.put("Zip", getValue("cancellationViewPayeeZip"));
		return getPayeeAddDetail;
	}

	@SuppressWarnings("unused")
	public boolean validatePrimaryAccountLevelCancellationRulesWithPriceSheetName(
			HashMap<String, String> SearchParameter) throws Exception {

		boolean flag = false;
		String PrimaryRoleID = SearchParameter.get("PrimaryRoleID");
		String PrimaryRoleType = SearchParameter.get("PrimaryRoleType");
		String PrimaryRoleName = SearchParameter.get("PrimaryRoleName");
		String PriceSheetName = SearchParameter.get("PrimaryRoleName");
		// select Primary account and click on add button
		click("clearButtonOnAccountBuilder");

		try {
			clearTextBox("primaryAccountFilterRowAccountName");
			typeKeys("primaryAccountFilter", PrimaryRoleName);
			typeKeys("primaryAccountPriceSheetFilter", PriceSheetName);
			waitForSomeTime(25);
			WebElement ele = windowsDriver.findElement(By.className("DataRow"));
			List<WebElement> list = ele.findElements(By.name(PrimaryRoleName));
			System.out.print(list.size());
			if (list.size() > 0) {
				logger.info("Account Rule Exists For Given Role Id---->>" + PrimaryRoleID);

				return flag;
			}
		} catch (Exception e) {
			flag = true;
			return flag;
		}

		return flag;
	}

	public boolean validatePrimaryAccountLevelCancellationRules(HashMap<String, String> searchParamater,
			String PriceSheet) {
		boolean flag = false;
		String PrimaryRoleID = searchParamater.get("PrimaryRoleID");
		String PrimaryRoleType = searchParamater.get("PrimaryRoleType");
		String PrimaryRoleName = searchParamater.get("PrimaryRoleName");
		String PriceSheetName = PriceSheet;
		// select Primary account and click on add button
		click("clearButtonOnAccountBuilder");
		clickComboBox("primaryAccountRoleType");
		try {
			WebElement ele2 = windowsDriver.findElement(By.className("Window"));
			List<WebElement> list2 = ele2.findElements(By.className("ListBoxItem"));
			for (WebElement webelement : list2) {
				String comboText = webelement.getText();
				if (comboText.equalsIgnoreCase(PrimaryRoleName)) {
					webelement.click();
				}
			}
		} catch (Exception e) {
			List<WebElement> roletype = listOfElements("listprimaryAccountRoleTypeElement");
			for (WebElement we : roletype) {
				String comboText = we.getText();
				if (comboText.equalsIgnoreCase(PrimaryRoleName)) {
					we.click();
				}
			}
		}
		waitForSomeTime(5);
		click("primaryAccountAddButton");
		waitForSomeTime(5);
		click("clearBtnOnAddPage");
		type("primaryAccountRoleIdOnAddPage", PrimaryRoleID);
		String value = PrimaryRoleType.trim();
		type("roleTypecomboAddPage", value);
		click("primaryAccountSearchBtnOnAddPage");
		waitForSomeTime(40);
		try {
			String selectBtnOnAddPage = checkEnableDisable("primaryAccountSelectBtnOnAddPage");
			if (selectBtnOnAddPage.equals("true")) {
				click("primaryAccountSelectBtnOnAddPage");
			} else {
				System.out.print("no value exist for this Role Type and Role ID");
				flag = false;
				return flag;
			}
		} catch (Exception e) {
		}
		takeScreenshot();
		type("effectiveDate", "01/01/2020");
		click("primaryAccountPriceSheetComboBox");

		WebElement ele2 = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list2 = ele2
				.findElements(By.xpath("//*[@ClassName ='ListBoxItem']//*[@ClassName ='TextBlock']"));
		System.out.println(list2.size());
		System.out.println(list2);
		System.out.println("PriceSheetName--------->" + PriceSheetName);
		if (PriceSheetName == "") {
			// list2.get(1).click();
		} else {
			for (WebElement we : list2) {
				String comboText = we.getText();
				if (comboText.equalsIgnoreCase(PriceSheetName)) {
					we.click();
				}
			}
		}
		try {
			clearTextBox("primaryAccountFilterRowAccountName");
		} catch (Exception e2) {
		}
		try {
			typeKeys("primaryAccountFilter", PrimaryRoleName);
			waitForSomeTime(25);
			WebElement ele = windowsDriver.findElement(By.className("DataRow"));
			List<WebElement> list = ele.findElements(By.name(PrimaryRoleName));
			System.out.print(list.size());
			// takeScreenshot();
			if (list.size() > 0) {
				logger.info("Account Rule Exists For Given Role Id---->>" + PrimaryRoleID);
				// clearTextBox("primaryAccountFilterRowAccountName");
				return flag;
			}
		} catch (Exception e1) {
			// clearTextBox("primaryAccountFilterRowAccountName");
			flag = true;
			return flag;
		}
		return flag;
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

	public HashMap<String, String> cancellation_getCOntractStatus(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("Contract_Status", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		return searchData;
	}

}