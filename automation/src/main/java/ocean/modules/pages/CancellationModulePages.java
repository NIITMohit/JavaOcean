package ocean.modules.pages;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
		summaryData.put("Contract", getValue("checkContractOnCancellationHistory"));
		summaryData.put("Status", getValue("checkStatusOnCancellationHistory"));
		summaryData.put("Process_Date", getValue("checkProcessDateOnCancellationHistory"));
		summaryData.put("REFUND_PERCENTAGE", getValue("checkRefundOnCancellationHistory"));
		click("scrollPageDownForCancelHistory");
		click("checkContractOnCancellationHistory");
		click("cancelHistoySwipeRight");
		summaryData.put("Net_Refund", getValue("checkNetRefundOnCancellationHistory"));
		summaryData.put("Cancel_Miles", getValue("checkCancelMilesOnCancellationHistory"));
		summaryData.put("CANCEL_DATE", getValue("checkCancelDateOnCancellationHistory"));
		click("cancelHistoySwipeRight");
		summaryData.put("INITIATED_BY", getValue("checkInitiatedByOnCancellationHistory"));
		summaryData.put("Cancel_Reason", getValue("checkCancelReasonOnCancellationHistory"));
		return summaryData;
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
		summaryData.put("Sale_Date", getValue("contractSummarySaleDate"));
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
		summaryData.put("Sale_Date", getValue("contractDetailsSaleDate"));
		summaryData.put("Processed_Date", getValue("contractDetailsProcessedDate"));
		summaryData.put("Check", getValue("contractDetailsCheque"));
		summaryData.put("Pricesheet", getValue("contractDetailsPricesheet"));
		summaryData.put("Status", getValue("contractDetailsStatus"));
		summaryData.put("Premium", getValue("contractDetailsPremium"));
		summaryData.put("Dealer_Paid", getValue("contractDetailsDealerPaid"));
		summaryData.put("DB/CR", getValue("contractDetailsDBCR"));
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
		summaryData.put("Status", getValue("primaryAccountDetailsStatus"));
		summaryData.put("Address", getValue("primaryAccountAddress"));
		summaryData.put("City", getValue("primaryAccountCity"));
		summaryData.put("State", getValue("primaryAccountState"));
		summaryData.put("Zip", getValue("primaryAccountZip"));
		summaryData.put("Phone", getValue("primaryAccountPhone"));
		summaryData.put("Fax", getValue("primaryAccountFax"));
		summaryData.put("Email", getValue("primaryAccountEmail"));
		summaryData.put("Reference_Dealer", getValue("primaryAccountReferenceDealer"));
		summaryData.put("Reference_Dealer_Id", getValue("primaryAccountReferenceDealerId"));
		summaryData.put("Statement_Balance", getValue("primaryAccountStatementBalance"));
		summaryData.put("Change_Off_History", getValue("primaryAccountChangeOffHistory"));
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
		summaryData.put("SecondaryAcct_Id", getValue("secondaryAccountDetailsSecondaryAccountId"));
		summaryData.put("Status", getValue("secondaryAccountDetailsStatus"));
		summaryData.put("Address", getValue("secondaryAccountDetailsAddress"));
		summaryData.put("City", getValue("secondaryAccountDetailsCity"));
		summaryData.put("State", getValue("secondaryAccountDetailsState"));
		summaryData.put("Zip", getValue("secondaryAccountDetailsZip"));
		summaryData.put("Phone", getValue("secondaryAccountDetailsPhone"));
		summaryData.put("Fax", getValue("secondaryAccountDetailsFax"));
		summaryData.put("Email", getValue("secondaryAccountDetailsEmail"));
		summaryData.put("Statement_Balance", getValue("secondaryAccountDetailsStatementBalance"));
		summaryData.put("Change_Off_History", getValue("secondaryAccountDetailsChangeOffHistory"));
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
		summaryData.put("Zip", getValue("customerDetailsZip"));
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
		String contractSummary = getAtttibuteValue("toggleContractSummaryOnNewCancellationO", "Toggle.ToggleState");
		if (contractSummary.toLowerCase().equals("1"))
			click("toggleContractSummaryOnNewCancellation");
		String contractDetails = getAtttibuteValue("toggleContractDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (contractDetails.toLowerCase().equals("1"))
			click("toggleContractDetailsOnNewCancellation");
		String primaryAccountDetails = getAtttibuteValue("togglePrimaryAccountDetailsOnNewCancellationO",
				"Toggle.ToggleState");
		if (primaryAccountDetails.toLowerCase().equals("1"))
			click("togglePrimaryAccountDetailsOnNewCancellation");
		String secondaryAccountDetails = getAtttibuteValue("toggleSecondaryAccountDetailsOnNewCancellationO",
				"Toggle.ToggleState");
		if (secondaryAccountDetails.toLowerCase().equals("1"))
			click("toggleSecondaryAccountDetailsOnNewCancellation");
		String customerDetails = getAtttibuteValue("toggleCustomerDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (customerDetails.toLowerCase().equals("1"))
			click("toggleCustomerDetailsOnNewCancellation");
		String vehicleDetails = getAtttibuteValue("toggleVehicleDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (vehicleDetails.toLowerCase().equals("1"))
			click("toggleVehicleDetailsOnNewCancellation");
		String agentDetails = getAtttibuteValue("toggleAgentDetailsOnNewCancellationO", "Toggle.ToggleState");
		if (agentDetails.toLowerCase().equals("1"))
			click("toggleAgentDetailsOnNewCancellation");
		String dealerInfoView = getAtttibuteValue("toggleDealerInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (dealerInfoView.toLowerCase().equals("1"))
			click("toggleDealerInfoViewOnNewCancellation");
		String certInfoView = getAtttibuteValue("toggleCertInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (certInfoView.toLowerCase().equals("1"))
			click("toggleCertInfoViewOnNewCancellation");
		String breakDownInfoView = getAtttibuteValue("toggleBreakDownInfoViewOnNewCancellationO", "Toggle.ToggleState");
		if (breakDownInfoView.toLowerCase().equals("1"))
			click("toggleBreakDownInfoViewOnNewCancellation");
		String ruleInfoView = getAtttibuteValue("toggleRuleInfoViewOnNewCancellationO", "Toggle.ToggleState");
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
		type("enterCancelDate", cancelDate);
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
		return getValue("messageForCancelDateLessSaleDate");
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
			break;
		case "quote":
			click("clickQuote");
			break;
		case "hold":
			click("clickHold");
			break;
		case "denied":
			click("clickDenied");
			break;
		case "cancellation confirmation":
			click("clickCancellationConfirmation");
			break;
		case "delete":
			click("clickDelete");
			break;
		default:
			click("clickAuthorize");
		}
	}

	/**
	 * This function is used to check for cancellation task status
	 * 
	 */
	public boolean checkCancellationTaskStatus(String status) throws Exception {
		boolean flag = false;
		String actualStatus = "";
		switch (status.toLowerCase()) {
		case "authorize":
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "quote":
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "hold":
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "denied":
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "cancellation confirmation":
			click("checkAuthorize");
			actualStatus = getValue("getCancellationStatus");
			if (actualStatus.toLowerCase().equals(status.toLowerCase()))
				flag = true;
			break;
		case "delete":
			click("clickDcheckAuthorizeelete");
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
		searchData.put("Contract_Number", getValue("listOfContractNumbers", i));
		//// save price sheet name
		searchData.put("PriceSheet_Name", getValue("getPriceSheetNameFromSearchData", i));
		//// save program code
		searchData.put("PROGRAM_CODE", getValue("getProgramCodeFromSearchData", i));
		//// save primary account
		searchData.put("Primary_Account", getValue("getPrimaryAccountFromSearchData", i));
		//// swipe right to get all fields
		click("swipeRight");
		//// save primary account id
		searchData.put("ROLE_IDENTIFIER", getValue("getPrimaryAccountIdFromSearchData", i));
		//// save customer name
		searchData.put("customer_name", getValue("getCustomerNameFromSearchData", i));
		//// save contract status
		searchData.put("contractStatus", getValue("getStatusFromSearchData", i));
		//// save comments
		String comments = getValue("getCommentsFromSearchData", i);
		if (comments.equals("N/A"))
			comments = null;
		searchData.put("COMMENTS", comments);
		click("swipeLeft");
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
		click("swipeRight");
		String myStatus = getValue("statusOfContract");
		click("swipeLeft");
		if (status.toLowerCase().equalsIgnoreCase("processed")) {
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
				//// do nothing
			}
		} while (checkEnableDisableBasedOnBoundingRectangle("scrollContractsListDown").toLowerCase().equals("true"));
		return contractCount.size();
	}

	public void scrollUp() {
		do {
			try {
				click("scrollContractsListUp");
			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (checkEnableDisableBasedOnBoundingRectangle("scrollContractsListUp").toLowerCase().equals("true"));
	}
}
