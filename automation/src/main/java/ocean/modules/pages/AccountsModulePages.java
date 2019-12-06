package ocean.modules.pages;

import java.util.HashMap;
import java.util.Map;

import ocean.modules.database.AccountsDataBase;

/**
 * This is object class which contains all pages of search modules
 * 
 * @author Mohit Goel
 */
public class AccountsModulePages extends AccountsDataBase {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
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
			switch (searchParamater.toLowerCase()) {
			case "account_name":
				type("typeAccountName", valueToInput);
				break;
			case "role_id":
				type("typeRoleID", valueToInput);
				break;
			case "role_type":
				type("typeRoleType", valueToInput);
				break;
			case "address":
				type("typeAddress", valueToInput);
				break;
			case "status":
				type("typeStatus", valueToInput);
				break;
			case "zip_code":
				type("typeZip", valueToInput);
				break;
			case "state":
				type("typeState", valueToInput);
				break;
			case "city":
				type("typeCity", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("searchAccountButton");
	}

	/**
	 * This function is used to get role id based on row number
	 * 
	 * @return
	 * 
	 */
	public String getFirstRoleId(int roleRowNumber) throws Exception {
		return getValue("listOfRoleId", roleRowNumber);
	}

	/**
	 * This function is used to get role id based on row number
	 * 
	 */
	public void scrollUp() {
		do {
			try {
				click("scrollContractsListUp");
			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (checkEnableDisableBasedOnBoundingRectangle("scrollContractsListUp").toLowerCase().equals("true"));
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
		//// save Role_Id
		searchData.put("Role_Id", getValue("listOfRoleId", i).trim());
		//// save Account_Name
		searchData.put("Account_Name", getValue("listOfAccountName", i).trim());
		//// Address
		searchData.put("Address", getValue("listOfAddress", i).trim());
		//// save CITY
		searchData.put("CITY", getValue("listOfCity", i).trim());
		//// save STATE
		searchData.put("STATE", getValue("listOfState", i).trim());
		//// save ZIP_CODE
		searchData.put("ZIP_CODE", getValue("listOfZipCode", i).trim());
		//// save Account_Type
		searchData.put("Account_Type", getValue("listOfAccountType", i).trim());
		//// Save Role_Type
		searchData.put("Role_Type", getValue("listOfRoleType", i).trim());
		//// save STATUS
		searchData.put("STATUS", getValue("listOfStatus", i).trim());
		return searchData;

	}
}
