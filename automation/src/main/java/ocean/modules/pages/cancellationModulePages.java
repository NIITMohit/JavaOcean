package ocean.modules.pages;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * This is object class which contains all pages of cancellation modules
 * 
 * @author Mohit Goel
 */
public class cancellationModulePages extends CommonFunctions {
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
	
	
	/**
	 * This function is used to return contract details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getContractDetails() throws Exception {
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
	 * paramater given
	 * 
	 */
	public void selectCancellationTaskStatus(String status) throws Exception {
		switch (status.toLowerCase()) {
		case "authorize":
			click("clickAuthorize");
			break;
		default:
			click("clickAuthorize");
		}
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
