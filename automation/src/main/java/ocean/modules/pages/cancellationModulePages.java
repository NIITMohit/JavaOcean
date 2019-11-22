package ocean.modules.pages;

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
		if (stateofbutton.toLowerCase().equals("true") && newcontractId.toLowerCase().equals(contractId)
				&& myStatus.toLowerCase().equals(status)) {

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
