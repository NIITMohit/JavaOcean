package ocean.modules.pages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ocean.modules.database.AccountsDataBase;

/**
 * This is object class which contains all pages of search modules
 * 
 * @author Mohit Goel
 */
public class AccountsModulePages extends AccountsDataBase {
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

	/**
	 * This function is used to assign priceSheet on data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getAccountAssignPriceSheetOnRoleType() throws Exception {
		HashMap<String, String> assignData = new HashMap<String, String>();

		assignData.put("InternalName", getValue("PriceSheetInternalName"));
		assignData.put("RoleTypeId", getValue("getRoleTypeId"));
		assignData.put("PriceSheetCode", getValue("typePriceCode"));
		return assignData;

	}

	/**
	 * This function is used to type the pricesheet Code and select the pricesheet
	 * for assign
	 * 
	 * 
	 */
	public void typePriceSheetCode(String priceSheet) {
		click("clickToSelectProgramCodeOfPRiceSheet");
		type("clickToSelectProgramCodeOfPRiceSheet", priceSheet);
		/*
		 * String abc = randomString(10); type("PriceSheetInternalName",abc);
		 */
		click("listOfSelectbutton", 0);
	}

	/**
	 * This function is used to type the pricesheet Code and select the pricesheet
	 * for assign
	 * 
	 * 
	 */
	public void typePriceSheetCodeMaster(String priceSheet, String roleType) {
		click("clickToSelectProgramCodeOfPRiceSheet");
		type("clickToSelectProgramCodeOfPRiceSheet", priceSheet);
		/*
		 * String abc = randomString(10); type("PriceSheetInternalName",abc);
		 */
		if (roleType.toLowerCase().equals("agent"))
			typeKeys("typePriceSheetInFilter", "Master");
		click("listOfSelectbutton", 0);
	}

	/**
	 * This function is used to add Administraion Exception on priceSheet
	 * 
	 * 
	 */
	public void addAdministraionExceptions(HashMap<String, String> commissionsData) {
		String[] bucket = commissionsData.get("AdminBucket").split(",");
		for (String string : bucket) {
			type("selectBucketForAdmistration", string);
			type("select$ToFillAmountForAdministration", commissionsData.get("$ForCommissions"));
			click("clickOnAddButton", 2);
		}
	}

	/**
	 * This function is used to add Reserve Exception on priceSheet
	 * 
	 * 
	 */
	public void addReserveException(HashMap<String, String> commissionsData) {
		String[] bucket = commissionsData.get("ReserveBucket").split(",");
		for (String string : bucket) {
			type("selectBucketForReserve", string);
			type("select$ToFillAmountForReserve", commissionsData.get("$ForCommissions"));
			click("clickOnAddButton", 3);
		}
	}

	/**
	 * This function is used to edit and delete the priceSheet Exceptions
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public void editAndDeletePriceSheetExceptions(HashMap<String, String> exceptionData) {
		type("clickToFilterInternalName", exceptionData.get("internalName"));
		click("clickOnEditButton", 0);
		try {
			click("scrollContractsListDown");
		} catch (Exception e) {
			// // do nothing
		}
		if (exceptionData.get("delete").toLowerCase().equals("y")) {
			rightClick("rightClickToDeleteExceptions");
			click("clickOnDeleteForException");
			click("clickOK");
		}
		if (exceptionData.get("edit").toLowerCase().equals("y")) {
			// // code to be added
			click("savePriceSheet");
		}
	}

	/**
	 * This function is used to add Insurance Exception on priceSheet
	 * 
	 * 
	 */
	public void addInsuranceExceptions(HashMap<String, String> commissionsData) {
		String[] bucket = commissionsData.get("InsuranceBucket").split(",");
		for (String string : bucket) {
			type("selectBucketForInsurance", string);
			type("select$ToFillAmountForInsurance", commissionsData.get("$ForCommissions"));
			click("clickOnAddButton", 4);
		}
	}

	/**
	 * This function is used to assign the priceSheet to add the exceptions
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public void selectPriceSheetToAssigntToAddExceptions(HashMap<String, String> excelSheetData) {
		click("assignPriceSheet");
		click("clickOK");
		type("selectEffectiveDate", excelSheetData.get("EffectiveDate"));
		type("selectExceptionType", excelSheetData.get("ExceptionType"));
		if (excelSheetData.get("ExceptionType").toLowerCase().equals("SELECTEDPLANS")) {
			type("selectClasstype", excelSheetData.get("Class"));
			type("selectCoverageType", excelSheetData.get("Coverage"));
			type("selectTermType", excelSheetData.get("Term"));
		}
	}

	/**
	 * This function is used to get the exceptions on agent, lender, dealer
	 * 
	 * 
	 */
	public HashMap<String, String> getExceptionsOnTheBasisOfPriceSheet() throws Exception {
		HashMap<String, String> exceptionsData = new HashMap<String, String>();
		exceptionsData.put("InternalName", getValue("PriceSheetInternalName"));
		try {
			click("scrollContractsListDown");
		} catch (Exception e) {
			// / do noting
		}
		exceptionsData.put("ExceptionType", getValue("selectExceptionType"));
		exceptionsData.put("Class", getValue("selectClasstype"));
		exceptionsData.put("Coverage", getValue("selectCoverageType"));
		exceptionsData.put("Term", getValue("selectTermType"));
		return exceptionsData;
	}

	/**
	 * This function is used to get the internal name of priceSheet which is
	 * assigned on agent, lender, dealer
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public String getInternalNameOfPriceSheet() throws Exception {
		click("PriceSheetInternalName");
		return getValue("PriceSheetInternalName");
	}

	public Map<String, String> replaceNullValues(Map<String, String> map, String defaultValue) {

		// Replace the null value
		map = map.entrySet().stream().map(entry -> {
			if (entry.getValue() == null)
				entry.setValue(defaultValue);
			return entry;
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return map;
	}

	/**
	 * This function is used to add NCBAdministartionsExceptions on priceSheet
	 * 
	 * 
	 */
	public void addNCBAdministartionsExceptions(HashMap<String, String> commissionsData) {
		String[] bucket = commissionsData.get("AdminBucket").split(",");
		for (String string : bucket) {
			type("selectNCBBucket", string);
			type("selectNCBFee", commissionsData.get("NCBFee"));
			type("selectWaitingPeriod", commissionsData.get("WaitingPeriod"));
			type("selectNCBRetail", "ADMIN_NCB_FEE_RETAIL_AX");
			type("selectRetailMax", "30");
			click("clickOnAddButton", 1);
		}
	}

	/**
	 * This function is used to get firstPrice sheet internal name
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public void addCommissionsExceptions(HashMap<String, String> commessionsData) {
		String[] bucket = commessionsData.get("Bucket").split(",");
		for (String string : bucket) {
			type("selectBucketForCommissions", string);
			try {
				type("typePayeeNameInTextBox", commessionsData.get("Payee"));
				WebElement ele = windowsDriver.findElement(By.className("AutoCompleteBox"));
				List<WebElement> lis = ele.findElements(By.className("ListBoxItem"));
				lis.get(1).click();
			} catch (Exception e) {
				// do nothing
			}
			type("select$ToFillAmountForCommissions", commessionsData.get("$ForCommissions"));
			type("selectCancelMethod", commessionsData.get("cancelMethod"));
			click("clickOnAddButton", 0);
		}
	}

	/**
	 * This function is used to verify payee is disabled or enabled and select the
	 * payee
	 * 
	 */
	public void verifyAndSelectPayee(HashMap<String, String> pricesheetDataMap) throws Exception {
		if (pricesheetDataMap.get("Role_Type").toLowerCase().equals("lender")
				|| pricesheetDataMap.get("Role_Type").toLowerCase().equals("dealer")) {
			type("typePayeeNameInTextBox", pricesheetDataMap.get("Payee"));
			waitForSomeTime(2);
			WebElement ele = windowsDriver.findElement(By.className("AutoCompleteBox"));
			List<WebElement> lis = ele.findElements(By.className("ListBoxItem"));
			lis.get(1).click();
		}
		click("listOfSelectbutton");
	}

	/**
	 * This function is used to select the top or First account for the agent,
	 * lender and dealer
	 * 
	 * 
	 */
	public void selectTopAccountOnTheBasisOfRoleType() {
		click("listOfDisplayButton", 0);

	}

	/**
	 * This function is used to click For SetUpNewPricing
	 * 
	 * 
	 */
	public void clickForSetUpNewPricing() {
		click("clickOnSetupNewPricing");
	}

	/**
	 * This function is used to get firstPrice sheet internal name
	 * 
	 * 
	 */
	public String getPriceSheetName() {
		// To get Internal name of PS info
		click("InternalNameOfPSInfo");
		// this is used to get the value of priceSheet internal name
		return getValue("InternalNameOfPSInfo");
	}

	/**
	 * This function is used to assign For SetUpNewPricing
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public void selectPriceSheetToAssign() {
		click("assignPriceSheet");
		waitForSomeTime(2);
		click("clickOK");
		waitForSomeTime(2);
		click("savePriceSheet");
		waitForSomeTime(2);
		click("clickOK");
	}

	/**
	 * This function is used to return Account Details PS Info searched data in map,
	 * to be verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnAccountDetailsPSInfoResultGridData(int i) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		//// save Role_Id
		searchData.put("Internal_Name", getValue("listOfPSInfoInternalName", i).trim());
		//// save Account_Name
		searchData.put("External_Name", getValue("listOfPSInfoExternalName", i).trim());
		//// Address
		searchData.put("Code", getValue("listOfPSInfoCode", i).trim());
		//// save CITY
		searchData.put("Effective_Date", getValue("listOfPSInfoEffectiveDate", i).trim());
		//// save STATE
		searchData.put("Classification_List", getValue("listOfPSInfoClassificationList", i).trim());
		//// save ZIP_CODE
		searchData.put("Status", getValue("listOfPSInfoStatus", i).trim());
		//// save Account_Type
		searchData.put("Master", getValue("listOfPSInfoMaster", i).trim());
		//// Save Role_Type
		searchData.put("Created_Date", getValue("listOfPSInfoCreatedDate", i).trim());
		//// save STATUS
		searchData.put("Created_By", getValue("listOfPSInfoCreatedBy", i).trim());
		return searchData;

	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 * @return
	 * 
	 */
	public String enterMandatoryValuesOnContractAndCheckForPriceSheet(HashMap<String, String> premiumData)
			throws Exception {
		String flag = "pata nahi";
		contractScrollToTop();
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
		type("purchaseDateForNewContract", premiumData.get("SaleDate"));
		//// Enter Primary Account Details
		type("primaryAccountType", premiumData.get("PrimaryAccount"));
		type("primaryAccountId", premiumData.get("DEALERID"));
		click("primaryAccountSearchButton");
		click("scrollContractsListDown");
		type("selectPricesheet", premiumData.get("PRICESHEETINTERNALCODE"));
		String text = getAttributeValue("selectPricesheet", "Value.Value");
		if (text.contains(premiumData.get("PRICESHEETINTERNALNAME")))
			return "Hurray Data Exists";
		else
			return "No Data Exists";

		//// Code to check pricesheet existance
		/*
		 * clickComboBox("selectPricesheetComboBoxd"); waitForSomeTime(2);
		 * List<WebElement> listItem = listOfElements("uwPSTextBlock"); if
		 * (listItem.size() < 1) return flag; for (WebElement www : listItem) { String
		 * PSTextforEff = www.getText(); if
		 * (PSTextforEff.toLowerCase().equals(premiumData.get("PRICESHEETINTERNALCODE").
		 * toLowerCase())) return "Hurray Data Exists"; else return "No Data Exists"; }
		 */
	}
}
