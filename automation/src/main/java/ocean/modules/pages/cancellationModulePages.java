package ocean.modules.pages;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	 * This function is used to navigate to NewCancellationTab
	 * 
	 * 
	 */
	public void clickCancelButtonAndNavigateToNewCancellationTab() throws Exception {
		click("clickCancelButton");
	}

	/**
	 * This function is used to navigate to fill details on new cancellation tab and
	 * click calculate, if cancelMiles, cancelDate, dateReceived are blank random
	 * values will be saved
	 * 
	 * @param string5
	 * @param string4
	 * @param string3
	 * @param string2
	 * @param string
	 * 
	 * 
	 */
	public void enterValuesOnNewCancellationTabAndClickCalculate(String initiatedBy, String cancelReason,
			String cancelMiles, String cancelDate, String dateReceived) throws Exception {
		type("selectInitiatedBy", initiatedBy);
		type("selectCancelReason", cancelReason);
		if (cancelMiles.length() < 1) {
			String miles = getValue("getMiles");
			int milee = 0;
			milee = Integer.parseInt(miles) + 2214;
			cancelMiles = Integer.toString(milee);
		}
		type("enterCancelMiles", cancelMiles);
		if (cancelDate.length() < 1) {
			Format sdf = new SimpleDateFormat("MM/dd/yy");
			Calendar cal = Calendar.getInstance();
			// Add 7 days to current date
			cal.add(Calendar.DAY_OF_MONTH, 7);
			// Date after adding the days to the current date
			cancelDate = sdf.format(cal.getTime());
		}
		type("enterCancelDate", cancelDate);
		if (dateReceived.length() < 1) {
			Format sdf = new SimpleDateFormat("MM/dd/yy");
			Calendar cal = Calendar.getInstance();
			// Add 7 days to current date
			cal.add(Calendar.DAY_OF_MONTH, 7);
			// Date after adding the days to the current date
			dateReceived = sdf.format(cal.getTime());
		}
		type("enterDateReceived", dateReceived);
		click("clickCalculate");
		click("clickOK");
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
