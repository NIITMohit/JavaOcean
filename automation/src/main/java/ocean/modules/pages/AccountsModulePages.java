package ocean.modules.pages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

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
	 * 
	 */
	public String getRoleId(int roleRowNumber) throws Exception {
		return getValue("listOfRoleId", roleRowNumber);
	}

	/**
	 * This function is used to get role type based on row number
	 * 
	 * 
	 */
	public String getRoleType(int roleRowNumber) throws Exception {
		return getValue("listOfRoleType", roleRowNumber);
	}

	/**
	 * This function is used to get role type based on row number
	 * 
	 * 
	 */
	public String getRoleStatus(int roleRowNumber) throws Exception {
		return getValue("listOfStatus", roleRowNumber);
	}

	/**
	 * This function is used to click display button to navigate to account details
	 * screen
	 * 
	 * 
	 */
	public String clickDisplayButton(int roleRowNumber) throws Exception {
		return getValue("listOfDisplayButton", roleRowNumber);
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
	 * This function is used to getphysicalAddressOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getphysicalAddressOnAccountDetailScreen() throws Exception {
		String physicalAddress = getValue("AccountDetails_AddressInfo_PhysicalAddress");
		physicalAddress.replace("\r\n", "");
		physicalAddress.replace(",", "");
		physicalAddress.replace(" ", "");
		return physicalAddress;
	}

	/**
	 * This function is used to getBillingAddressOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getBillingAddressOnAccountDetailScreen() throws Exception {
		String physicalAddress = getValue("AccountDetails_AddressInfo_BillingAddress");
		physicalAddress.replace("\r\n", "");
		physicalAddress.replace(",", "");
		physicalAddress.replace(" ", "");
		return physicalAddress;
	}

	/**
	 * This function is used to getAccountUnderwritingWarningAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getAccountUnderwritingWarningAccountDetailScreen() throws Exception {
		return getValue("AccountDetails_AccountLevelWarining");
	}

	/**
	 * This function is used to getAccountUnderwritingWarningAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getAccountCancellationWarningAccountDetailScreen() throws Exception {
		return getValue("AccountDetails_AccountCancellationWarining");
	}

	/**
	 * This function is used to getPricesheetUnderwritingWarningAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getPricesheetUnderwritingWarningAccountDetailScreen() throws Exception {
		return getValue("AccountDetails_PriceSheetUnderwritingWarining");
	}

	/**
	 * This function is used to getPricesheetCancellationWarningAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getPricesheetCancellationWarningAccountDetailScreen() throws Exception {
		return getValue("AccountDetails_PriceSheetCancellationWarining");
	}

	/**
	 * This function is used to return all account info related data only
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getAccountInfoOnAccountDetails() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		//// save Role_Id
		searchData.put("Account_Name", getValue("AccountDetails_AccountInfo_AccountName").trim());
		searchData.put("Account_Type", getValue("AccountDetails_AccountInfo_AccountType").trim());
		searchData.put("Role_Id", getValue("AccountDetails_AccountInfo_RoleId").trim());
		searchData.put("Role_Type", getValue("AccountDetails_AccountInfo_RoleType").trim());
		searchData.put("Reference_Id", getValue("AccountDetails_AccountInfo_ReferenceId").trim());
		searchData.put("Status", getValue("AccountDetails_AccountInfo_Status").trim());
		searchData.put("Date_Signed", getValue("AccountDetails_AccountInfo_DateAssigned").trim());
		return searchData;

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

	/**
	 * This function is used to verifyAccountInfoOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyAccountInfoOnAccountDetailScreen(String roleId, String roleType, String roleStatus)
			throws Exception {
		if (roleId.length() > 0) {
			//// validate Account Info Section
			HashMap<String, String> dbData = account_getAccountInfoOnAccountDetails(roleId, roleType, roleStatus);
			HashMap<String, String> uiData = getAccountInfoOnAccountDetails();
			//// manipulate data to match both hash maps
			dbData.put("Date_Signed", convertDate(dbData.get("Date_Signed"), 0));
			String date = uiData.get("Trans_Date");
			int index = date.indexOf(" ");
			if (index > 0)
				uiData.put("Trans_Date", date.substring(0, index));
			if (uiData.equals(dbData)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * This function is used to verifyaAddressInfoOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyAddressInfoOnAccountDetailScreen(String roleId, String roleType, String roleStatus)
			throws Exception {
		String physicalAddress = getphysicalAddressOnAccountDetailScreen();
		String dbPhysicalAddrress = account_getPhysicalAddressOnAccountDetails(roleId, roleType, roleStatus);

		String billingAddress = getBillingAddressOnAccountDetailScreen();
		String dbBillingAddrress = account_getBillingAddressOnAccountDetails(roleId, roleType, roleStatus);
		if (physicalAddress.equalsIgnoreCase(dbPhysicalAddrress) && billingAddress.equals(dbBillingAddrress))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to returnFirstPriceSheetToAddPriceSheetExceptions
	 * 
	 * @return
	 * 
	 */
	public String returnFirstPriceSheetToAddPriceSheetExceptions() throws Exception {
		click("AccountDetails_PriceSheetAccociated");
		return getValue("AccountDetails_PriceSheetAccociated");
	}

	/**
	 * This function is used to verifyAccountLevelWarningOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyAccountLevelWarningOnAccountDetailScreen(String roleId, String roleType, String roleStatus)
			throws Exception {

		String uiAccountWarninig = getAccountUnderwritingWarningAccountDetailScreen();
		String dbAccountWarninig = account_getAccountUnderwritingWarningAccountDetails(roleId, roleType, roleStatus);

		String uiAccountCanWarnng = getAccountCancellationWarningAccountDetailScreen();
		String dbAccountCanWarnng = account_getAccountCancellationWarningAccountDetails(roleId, roleType, roleStatus);
		if (uiAccountWarninig.equalsIgnoreCase(dbAccountWarninig) && uiAccountCanWarnng.equals(dbAccountCanWarnng))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to verifyAccountLevelWarningOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyAccountLevelWarningAfterEditOnAccountDetailScreen(String war1, String war2) throws Exception {
		String uiAccountWarninig = getAccountUnderwritingWarningAccountDetailScreen();
		String uiAccountCanWarnng = getAccountCancellationWarningAccountDetailScreen();
		if (uiAccountWarninig.equalsIgnoreCase(war1) && uiAccountCanWarnng.equals(war2))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to verifyAccountLevelWarningOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyPriceSheetLevelWarningOnAccountDetailScreen(String priceSheetInternalName) throws Exception {

		///// verify price sheet level warning
		String psUnderwriting = getPricesheetUnderwritingWarningAccountDetailScreen();
		String dbpsUnderwriting = account_getPriceSheetUnderwritingWarningAccountDetails(priceSheetInternalName);

		String psCancel = getPricesheetCancellationWarningAccountDetailScreen();
		String dbpsCancel = account_getPriceSheetCancellationWarningAccountDetails(priceSheetInternalName);

		if (psUnderwriting.equalsIgnoreCase(dbpsUnderwriting) && psCancel.equals(dbpsCancel))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to verifyAccountLevelWarningOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyPriceSheetLevelWarningAfterEditOnAccountDetailScreen(String war1, String war2)
			throws Exception {
		///// verify price sheet level warning
		String psUnderwriting = getPricesheetUnderwritingWarningAccountDetailScreen();

		String psCancel = getPricesheetCancellationWarningAccountDetailScreen();

		if (psUnderwriting.equalsIgnoreCase(war1) && psCancel.equals(war2))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to verifyAccountLevelWarningOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyCorpAndGroupDetailsAccountDetailScreen(String roleId, String roleType, String roleStatus)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> myData = null;
		myData = account_getCorpAndGroupOnAccountDetails(roleId, roleType, roleStatus);
		HashMap<String, String> uiData = new HashMap<String, String>();
		uiData.put("CorpID", getValue("AccountDetails_CorpGroupInfo_CorpId"));
		uiData.put("CorpName", getValue("AccountDetails_CorpGroupInfo_CorpName"));
		uiData.put("GroupID", getValue("AccountDetails_CorpGroupInfo_GroupId"));
		uiData.put("GroupName", getValue("AccountDetails_CorpGroupInfo_GroupName"));
		HashMap<String, String> dbData = new HashMap<String, String>();
		for (Entry<Integer, HashMap<String, String>> letterEntry : myData.entrySet()) {
			for (Map.Entry<String, String> nameEntry : letterEntry.getValue().entrySet()) {
				String name = nameEntry.getKey().replace(" ", "");
				String value = nameEntry.getValue();
				dbData.put(name, value);
			}
		}
		if (uiData.equals(dbData))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to verifyAccountLevelWarningOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public boolean verifyPropertyInfoOnAccountDetailScreen(String roleId, String roleType, String roleStatus)
			throws Exception {
		HashSet<String> propertyHeader = new HashSet<String>();
		propertyHeader.addAll(getAllValuesSaveInSet("AccountDetails_PropertyInfoHeader"));

		HashSet<String> propertyValue = new HashSet<String>();
		propertyValue.addAll(getAllValuesSaveInSet("AccountDetails_PropertyInfoName"));

		HashMap<Integer, HashMap<String, String>> myData = null;
		myData = account_getPropertyInfoOnAccountDetails(roleId, roleType, roleStatus);

		HashSet<String> dbpropertyHeader = new HashSet<String>();
		HashSet<String> dbpropertyValue = new HashSet<String>();
		for (Entry<Integer, HashMap<String, String>> letterEntry : myData.entrySet()) {
			for (Map.Entry<String, String> nameEntry : letterEntry.getValue().entrySet()) {
				String name = nameEntry.getKey().replace(" ", "");
				String value = nameEntry.getValue();
				dbpropertyHeader.add(name);
				dbpropertyValue.add(value);
			}
		}

		if (dbpropertyHeader.equals(propertyHeader) && dbpropertyValue.equals(propertyValue))
			return true;
		else
			return false;
	}

	/**
	 * This function is used to typeAccountWarning
	 * 
	 * @return
	 * 
	 */
	public void typeAccountWarning(String underwritingWarning, String cancellationWarning) throws Exception {
		if (underwritingWarning.length() > 0) {
			type("AccountDetails_AccountLevelWarining", underwritingWarning);
		}
		if (cancellationWarning.length() > 0) {
			type("AccountDetails_AccountCancellationWarining", cancellationWarning);
		}
	}

	/**
	 * This function is used to typePriceSheetWarning
	 * 
	 * @return
	 * 
	 */
	public void typePriceSheetWarning(String underwritingWarning, String cancellationWarning) throws Exception {
		if (underwritingWarning.length() > 0) {
			type("AccountDetails_PriceSheetUnderwritingWarining", underwritingWarning);
		}
		if (cancellationWarning.length() > 0) {
			type("AccountDetails_PriceSheetCancellationWarining", cancellationWarning);
		}
	}

}
