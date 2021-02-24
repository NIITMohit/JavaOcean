package ocean.modules.pages;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;

import ocean.common.ObjectRepo;
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
	public String getDisplayButton(int roleRowNumber) throws Exception {
		return getValue("listOfDisplayButton", roleRowNumber);
	}

	/**
	 * This function is used to click display button to navigate to account details
	 * screen
	 * 
	 * 
	 */
	public void clickDisplayButton(int roleRowNumber) throws Exception {
		click("listOfDisplayButton", roleRowNumber);
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
		String physicalAddress = "";
		physicalAddress = getValue("AccountDetails_AddressInfo_PhysicalAddress");
		physicalAddress = physicalAddress.replaceAll("\r\n", "");
		physicalAddress = physicalAddress.replaceAll(",", "");
		physicalAddress = physicalAddress.replaceAll(" ", "");
		physicalAddress = physicalAddress.replaceAll("N/A", "");
		return physicalAddress;
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
		takeScreenshot();
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));
		if (!checkIsSelected("vinNumberOverride"))
			click("vinNumberOverride");
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
		type("customerEmail", "niit@gmail.com");
		type("customerPhone", "9999888888");
		click("scrollContractsListDown");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
		System.out.println("premiumData.get(PRICESHEETID): 3 " + premiumData.get("PRICESHEETID"));
		type("selectPricesheet", premiumData.get("PRICESHEETID"));
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
				if (premiumData.get("MissTerm") == null) {
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
				} else {
					if (!premiumData.get("MissTerm").contains(string)) {
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
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");
		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (coverageValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				if (premiumData.get("MissCoverage") == null) {
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
				} else {
					if (!premiumData.get("MissCoverage").contains(string)) {
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
			}
		}
		waitForSomeTime(5);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		premiumData.putAll(ss);
		HashMap<String, String> ssss = getExceptionPremium(premiumData);
		ss.putAll(ssss);

		return ss;
	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 * @return
	 * 
	 */
	public boolean enterMandatoryValuesOnContractWhenMileIsGreater(HashMap<String, String> premiumData)
			throws Exception {
		contractScrollToTop();
		HashMap<String, String> ss = new HashMap<String, String>();
		boolean coverageInfoIsBlank = false;
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
		takeScreenshot();
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));
		if (!checkIsSelected("vinNumberOverride"))
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
		type("customerEmail", "niit@gmail.com");
		type("customerPhone", "9999888888");
		click("scrollContractsListDown");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
		System.out.println("premiumData.get(PRICESHEETID): 3 " + premiumData.get("PRICESHEETID"));
		type("selectPricesheet", premiumData.get("PRICESHEETID"));
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
				if (premiumData.get("MissTerm") == null) {
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
				} else {
					if (!premiumData.get("MissTerm").contains(string)) {
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
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");
		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (coverageValues.size() == 0)
				coverageInfoIsBlank = true;
		}
		waitForSomeTime(5);
		return coverageInfoIsBlank;
	}

	/**
	 * This function is used to getBillingAddressOnAccountDetailScreen
	 * 
	 * @return
	 * 
	 */
	public String getBillingAddressOnAccountDetailScreen() throws Exception {
		String physicalAddress = getValue("AccountDetails_AddressInfo_BillingAddress");
		physicalAddress = physicalAddress.replaceAll("\r\n", "");
		physicalAddress = physicalAddress.replaceAll(",", "");
		physicalAddress = physicalAddress.replaceAll(" ", "");
		physicalAddress = physicalAddress.replaceAll("N/A", "");
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
		// searchData.put("Date_Signed",
		// getValue("AccountDetails_AccountInfo_DateAssigned").trim());
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
			// dbData.put("Date_Signed", convertDate(dbData.get("Date_Signed"), 0));
			// String date = uiData.get("Trans_Date");
			// int index = date.indexOf(" ");
			// if (index > 0)
			// uiData.put("Trans_Date", date.substring(0, index));
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
		System.out.println(psCancel);
		System.out.println(dbpsCancel);
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
		System.out.println(psUnderwriting);
		String psCancel = getPricesheetCancellationWarningAccountDetailScreen();
		System.out.println(psCancel);
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
	public HashMap<String, String> getAccountAssignPriceSheetOnRoleType(String priceSheet) throws Exception {
		HashMap<String, String> assignData = new HashMap<String, String>();
		assignData.put("InternalName", getValue("PriceSheetInternalName"));
		assignData.put("RoleTypeId", getValue("getRoleTypeId"));
		assignData.put("PriceSheetCode", priceSheet);
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
		click("listOfSelectbuttonPriceSheet");
	}

	/**
	 * This function is used to type the pricesheet Code and select the pricesheet
	 * for assign
	 * 
	 * 
	 */
	public void typePriceSheetCodeMaster(String priceSheet, String roleType) {
		type("clickToSelectProgramCodeOfPRiceSheet", priceSheet);
		waitForSomeTime(3);
		/*
		 * String abc = randomString(10); type("PriceSheetInternalName",abc);
		 */
		if (roleType.toLowerCase().equals("agent"))
			typeKeys("typePriceSheetInFilter", "Master");
		click("listOfSelectbuttonPriceSheet");
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
			type("select$ToFillAmountForAdministration", commissionsData.get("AdminBucketAmt"));
			click("AdministrationAddButton");
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
			type("select$ToFillAmountForReserve", commissionsData.get("ReserveBucketAmt"));
			click("reserveAddButton");
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
		if (exceptionData.get("Delete").toLowerCase().equals("y")) {
			rightClick("rightClickToDeleteExceptions");
			click("clickOnDeleteForException");
			click("clickOK");
		}
		if (exceptionData.get("Edit").toLowerCase().equals("y")) {
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
			type("select$ToFillAmountForInsurance", commissionsData.get("InsuranceBucketAmt"));
			click("InsuranceAddButton");
		}
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
		try {
			String priceSheetError = getAttributeValue("priceSheetExits", "Name");
			if (priceSheetError.equals("Pricesheet aleady exits")) {
				click("clickOK");
				waitForSomeTime(2);
				click("clickOK");
				waitForSomeTime(2);
				click("savePriceSheet");
				waitForSomeTime(2);
				click("clickOK");
				click("clickOK");
			}
		} catch (Exception e) {
			waitForSomeTime(2);
			click("clickOK");
			waitForSomeTime(2);
			click("savePriceSheet");
			waitForSomeTime(2);
			click("clickOK");
			click("clickOK");

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
		if (commissionsData.get("NCBAdministration").length() > 1) {
			String[] bucket = commissionsData.get("NCBAdministration").split(",");
			for (String string : bucket) {
				type("selectNCBBucket", string);
				type("selectNCBFee", commissionsData.get("NCBFee"));
				type("selectWaitingPeriod", commissionsData.get("WaitingPeriod"));
				click("NCBAddButton");
			}
		}
		if (commissionsData.get("NCBRetail").length() > 1 && commissionsData.get("NCBRetailMax").length() > 1) {
			type("selectNCBRetail", commissionsData.get("NCBRetail"));
			type("selectRetailMax", commissionsData.get("NCBRetailMax"));
			click("NCBAddButton");
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
			if (commessionsData.get("BucketPayee").length() > 1) {
				type("typePayeeNameInTextBox2", commessionsData.get("BucketPayee"));
				waitForSomeTime(2);
				WebElement ele = windowsDriver.findElement(By.className("Popup"));
				List<WebElement> lis = ele.findElements(By.className("ListBoxItem"));
				lis.get(0).click();
			}
			type("select$ToFillAmountForCommissions", commessionsData.get("CommissionsAmount"));
			type("selectCancelMethod", commessionsData.get("cancelMethod"));
			click("clickCommissionAddButton");
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
			//// Select Payee as Agent
			if (pricesheetDataMap.get("Payee") != null && pricesheetDataMap.get("Payee").length() > 1) {
				type("typePayeeNameInTextBox", pricesheetDataMap.get("Payee"));
				waitForSomeTime(2);
				WebElement ele = windowsDriver.findElement(By.className("Popup"));
				List<WebElement> lis = ele.findElements(By.className("ListBoxItem"));
				lis.get(0).click();
			}
			//// Seelct payee as Lender
			if (pricesheetDataMap.get("Lender") != null && pricesheetDataMap.get("Lender").length() > 1) {
				type("typeLenderNameInTextBox", pricesheetDataMap.get("Lender"));
				waitForSomeTime(2);
				WebElement ele = windowsDriver.findElement(By.className("Popup"));
				List<WebElement> lis = ele.findElements(By.className("ListBoxItem"));
				lis.get(0).click();
			}
		}
		click("listOfSelectbutton");
	}

	/**
	 * This function is used to assign the priceSheet to add the exceptions
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public String selectPriceSheetToAssigntToAddExceptions(HashMap<String, String> excelSheetData) {
		// pricesheetName
		String random = randomString(14);
		type("pricesheetName", random);
		//// type pricesheet exception date
		if (excelSheetData.get("MainEffectiveDate").length() > 1)
			type("selectEffectiveDate2", excelSheetData.get("MainEffectiveDate"));
		click("assignPriceSheet");
		try {
			click("clickOK");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (excelSheetData.get("EffectiveDate").length() > 1)
			type("selectEffectiveDate", excelSheetData.get("EffectiveDate"));
		if (excelSheetData.get("ExceptionType").toLowerCase().equals("selectedplans")) {
			type("selectExceptionType", excelSheetData.get("ExceptionType"));
		}
		if (excelSheetData.get("ExceptionType").toLowerCase().equals("selectedplans")) {
			type("selectClasstype", excelSheetData.get("Class"));
			type("selectCoverageType", excelSheetData.get("Coverage"));
			type("selectTermType", excelSheetData.get("Term"));
		}

		return random;
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
	 */
	public void clickForSetUpNewPricing() {
		click("clickOnSetupNewPricing");
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

		/*
		 * //// Code to check pricesheet existance
		 * clickComboBox("selectPricesheetComboBoxd"); waitForSomeTime(2);
		 * List<WebElement> listItem = listOfElements("uwPSTextBlock"); if
		 * (listItem.size() < 1) return flag; for (WebElement www : listItem) { String
		 * PSTextforEff = www.getText(); if
		 * (PSTextforEff.toLowerCase().equals(premiumData.get("PRICESHEETINTERNALCODE").
		 * toLowerCase())) return "Hurray Data Exists"; else return "No Data Exists"; }
		 */
	}

	/**
	 * This function is used to click For SetUpLWPricing
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */

	public void clickForSetUpLWPricing() {
		click("clickOnSetUpLWPricing");
	}

	/**
	 * This function is used to enter PS Values for Acconts LWSetupPricing
	 */

	public void enterValuesForLWSetup(String inputArray) {
		// enter Price sheet value for LW setup
		type("selectAccountsLWSetupPS", inputArray);
		// clic on Search button
		click("clickOnLWSetupSearchButton");

	}

	/**
	 * This function is used to create new LWSetupPricing Values in Accounts
	 * 
	 * @throws Exception
	 */

	@SuppressWarnings("unused")
	public void fillDataForLimitedWarranty(String lWPlanCode, String deductibleValue) throws Exception {
		click("selectAircondition");
		click("selectDriveAxle");
		click("selectSealGaskets");
		click("selectTransmission");
		click("selectDeductible");
		clickOnDropDownItem(deductibleValue);
		HashMap<String, String> dbMap = account_getDBValueDefinedForLWSetUp(lWPlanCode);
		type("selectTermMonths", dbMap.get("term_months"));
		type("selectTermMiles", dbMap.get("term_mileage"));
		type("selectAgeFrom", dbMap.get("age_from"));
		type("selectAgeTo", dbMap.get("age_to"));
		type("selectMileageFrom", dbMap.get("mileage_from"));
		type("selectMileageTo", dbMap.get("mileage_to"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(2);
		type("effectiveDateLWSetup", dtf.format(localDate).toString());
		click("selectAggregate");
		clickOnDropDownItem("1500");
		waitForSomeTime(5);
		click("selectBucket");
		clickOnDropDownItem("ADMIN_AUL_1_DX");
		type("selectCost", "15");
		click("clickAddBucketButton");
		waitForSomeTime(5);
		click("selectBucket");
		clickOnDropDownItem("COMM_1_DX");
		String agentId = account_getActiveAgent();
		type("selectAgentForCommission", agentId);
		Robot r = new Robot();
		waitForSomeTime(10);
		r.keyPress(KeyEvent.VK_DOWN);
		r.keyRelease(KeyEvent.VK_DOWN);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		type("selectCost", "10");
		click("clickAddBucketButton");
		waitForSomeTime(5);
		click("selectBucket");
		clickOnDropDownItem("RES_BASE_DX");
		type("selectCost", "100");
		click("clickAddBucketButton");
		waitForSomeTime(5);
	}

	/**
	 * This function is used to select the top or First account for the agent,
	 * lender and dealer and also select the lender on the basis of classification
	 * list
	 * 
	 */
	public void selectTopAccountForRoleType(HashMap<String, String> selectRoleType) {
		if (selectRoleType.get("Role_Type").equals("Lender")) {
			selectClassificationListForLender(selectRoleType.get("ClassificationList"));
		} else {
			// clickDisplayButton(0);
			click("listOfDisplayButton", 1);
		}
	}

	/**
	 * This function is used to select Lender Classification list type
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * 
	 */
	public void selectClassificationListForLender(String classificationType) {
		List<WebElement> listOfElement = listOfElements("selectLenderClassificationList");
		for (int i = 0; i < listOfElement.size(); i++) {
			switch (classificationType) {
			case "A-list":
				if (listOfElement.get(i).getText().equals(classificationType)) {
					// click("swipeLeft");
					click("listOfDisplayButton", i);
				}
				break;
			case "B-list":
				if (listOfElement.get(i).getText().equals(classificationType)) {
					// click("swipeLeft");
					click("listOfDisplayButton", i);
				}
				break;
			}
			i++;
		}

	}

	/**
	 * This function is used to apply the filters on role type drop down and match
	 * the priceSheet on the basis of filters option
	 * 
	 * @param <WebElement>
	 * 
	 * @return
	 * @throws InterruptedException
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void selectRoleTypeValueAndMatch(String roleType) throws InterruptedException {

		switch (roleType.toLowerCase()) {
		case "agent":

			type("typePriceSheetInFilter", "Master");
			Thread.sleep(3000);
			List<WebElement> listOfElement = windowsDriver
					.findElements(ObjectRepo.fetchOR("checkPriceSheetForRoleType"));
			if (listOfElement.size() != 0) {
				Assert.assertTrue((listOfElement.get(1).getText().equals("Master")), "Incorrect Price Sheet found");
				System.out.println("pricehseet match");
			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Agent");
			Thread.sleep(3000);
			listOfElement = windowsDriver.findElements(ObjectRepo.fetchOR("checkPriceSheetForRoleType"));
			if (listOfElement.size() != 0) {
				Assert.assertTrue((listOfElement.get(1).getText().equals("Agent")), "Incorrect Price Sheet found");
				System.out.println("pricehseet match");
			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Lender");
			Thread.sleep(3000);
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
				System.out.println("Incorrect PriceSheet found.");
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "SubMaster");
			Thread.sleep(3000);
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
				System.out.println("Incorrect PriceSheet found.");

			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");
			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Dealer");
			Thread.sleep(3000);
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
				System.out.println("Incorrect PriceSheet found.");

			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");
			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Master");

			break;

		case "lender":

			type("typePriceSheetInFilter", "Master");
			listOfElement = windowsDriver.findElements(ObjectRepo.fetchOR("checkPriceSheetForRoleType"));
			if (listOfElement.size() != 0) {
				Assert.assertTrue((listOfElement.get(1).getText().equals("Master")), "Incorrect Price Sheet found");
				System.out.println("pricehseet match");
			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Lender");
			listOfElement = windowsDriver.findElements(ObjectRepo.fetchOR("checkPriceSheetForRoleType"));
			if (listOfElement.size() != 0) {
				Assert.assertTrue((listOfElement.get(1).getText().equals("Lender")), "Incorrect Price Sheet found");
				System.out.println("pricehseet match");
			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Agent");
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "SubMaster");
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Dealer");
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(10);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Master");

			break;

		case "dealer":

			type("typePriceSheetInFilter", "Lender");
			listOfElement = windowsDriver.findElements(ObjectRepo.fetchOR("checkPriceSheetForRoleType"));
			if (listOfElement.size() != 0) {
				Assert.assertTrue((listOfElement.get(1).getText().equals("Lender")), "Incorrect Price Sheet found");
				System.out.println("pricehseet match");
			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Agent");
			listOfElement = windowsDriver.findElements(ObjectRepo.fetchOR("checkPriceSheetForRoleType"));
			if (listOfElement.size() != 0) {
				Assert.assertTrue((listOfElement.get(1).getText().equals("Agent")), "Incorrect Price Sheet found");
				System.out.println("pricehseet match");
			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Master");
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
				System.out.println("fff");
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "SubMaster");
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Dealer");
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("checkPriceSheetForSingleRoleType"));
			}

			catch (NoSuchElementException e) {
				System.out.println("PriceSheet is not available");

			}
			waitForSomeTime(5);
			click("priceSheetInFilterClearBtn");
			type("typePriceSheetInFilter", "Agent");

			break;
		}

	}

	/**
	 * This function is get AUL premim
	 * 
	 */
	public String premium() throws Exception {
		click("clickPremiumCalculate");
		// boolean freezPane = checkTopPaneFreez();
		// if(freezPane= true) {
		return getAttributeValue("getPremium", "Name");
		// }
	}

	public boolean checkTopPaneFreez() {
		String contractNum = getAttributeValue("typeContractNumber", "Text");
		String contractStatus = getAttributeValue("getCotractStatusOnUnderwritingPage", "Name");
		if (contractNum != "" && contractStatus != "") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is use to create LW Pricing for a RoleId
	 * 
	 * 
	 */
	public String prepareLWSetup(String lWPlanCode, String deductibleValue) throws Exception {
		String roleId = account_getRoleIdToSetupLWPricing(lWPlanCode);
		if (roleId.length() > 0) {
			// create data to fill required values in search window
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			// Navigate to Accounts tab
			goToAccountsTab();
			// Navigate to Accounts Search tab
			goToAccountsSearchTab();
			// Put excel data to hashmap
			uiSearchData.put("role_id", roleId);
			uiSearchData.put("role_type", "Dealer");
			uiSearchData.put("status", "Active");
			// run code for search
			searchContractGivenInputParamaters(uiSearchData);
			// To click display button to navigate to account details screen
			// clickDisplayButton(0);
			click("clickDisplayButton");
			// setup a LW price sheet
			waitForSomeTime(20);
			clickForSetUpLWPricing();
			waitForSomeTime(20);
			// enter mandatory values for LW Setup
			enterValuesForLWSetup(lWPlanCode);
			// click on Yes button to create new one PS
			click("clickOnLWSetupYesButton");
			// create new data for Limited Warranty PlanCode
			fillDataForLimitedWarranty(lWPlanCode, deductibleValue);
			// click on new button to get error for validation
			click("clickOnLWSetupNewButton");
			waitForSomeTime(20);
			// Validate Correct LW setup Message
			String actualMsg = getAttributeValue("clickNewButtonForLWSetup", "Name");
			String expectedMsg = "Pricesheet saved";
			takeScreenshot();
			// click on OK button
			click("clickOnLWSetupOKButton");
			if (actualMsg.equals(expectedMsg))
				logger.info("LW Setup is correctly done for Role Id:" + roleId);
			assertEquals(actualMsg, expectedMsg);
		} else {
			new SkipException("no Account found to set LW Pricing");
		}
		return roleId;
	}

	public String editLWSetup(String lWPlanCode) throws Exception {
		String roleId = getDealerDetailsForRecentLWSetup(lWPlanCode);
		if (roleId.length() > 0) {
			// create data to fill required values in search window
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			// Navigate to Accounts tab
			goToAccountsTab();
			// Navigate to Accounts Search tab
			goToAccountsSearchTab();
			// Put excel data to hashmap
			uiSearchData.put("role_id", roleId);
			uiSearchData.put("role_type", "Dealer");
			uiSearchData.put("status", "Active");
			// run code for search
			searchContractGivenInputParamaters(uiSearchData);
			// To click display button to navigate to account details screen
			clickDisplayButton(0);
			// setup a LW price sheet
			waitForSomeTime(20);
			clickForSetUpLWPricing();
			waitForSomeTime(20);
			// enter mandatory values for LW Setup
			enterValuesForLWSetup(lWPlanCode);

			// Add a new bucket
			click("selectBucket");
			clickOnDropDownItem("ADMIN_AUL_2_DX");
			type("selectCost", "10");
			click("clickAddBucketButton");
			waitForSomeTime(5);
			// click on new button to get error for validation
			click("clickOnLWSetupUpdateButton");
			waitForSomeTime(20);
			// Validate Correct LW setup Message
			String actualMsg = getAttributeValue("clickUpdateButtonForLWSetup", "Name");
			String expectedMsg = "Pricesheet update successfull";
			takeScreenshot();
			// click on OK button
			click("clickOnLWSetupOKButton");
			if (actualMsg.equals(expectedMsg))
				logger.info("LW Setup is correctly edited for Role Id:" + roleId);
			assertEquals(actualMsg, expectedMsg);
		} else {
			new SkipException("no Account found to edit LW Pricing");
		}
		return roleId;
	}

	public HashMap<String, String> setDataAsTestData(String checkValue, HashMap<String, String> premiumData)
			throws Exception {

		if (checkValue.equals("BeforeMileage")) {
			String mileage = premiumData.get("MILEAGE_TO");
			int beforeMiles = Integer.parseInt(mileage) - 100;
			premiumData.put("MILEAGE", String.valueOf(beforeMiles));
			logger.info("Defined maximum mileage of vehicle in LW setup : " + mileage
					+ " Edited mileage to make vehicle eligible : " + beforeMiles);
		} else if (checkValue.equals("AfterMileage")) {
			String mileage = premiumData.get("MILEAGE_TO");
			int afterMiles = Integer.parseInt(mileage) + 100;
			premiumData.put("MILEAGE", String.valueOf(afterMiles));
			logger.info("Defined maximum mileage of vehicle in LW setup : " + mileage
					+ " Edited mileage to make vehicle ineligible : " + afterMiles);
		} else if (checkValue.equals("Age")) {
			String age = premiumData.get("YEAR");
			int afterAge = Integer.parseInt(age) - 5;
			premiumData.put("YEAR", String.valueOf(afterAge));
			logger.info("Defined maximum age of vehicle in LW setup : " + age
					+ " Edited year to make vehicle ineligible : " + afterAge);
		}
		return premiumData;
	}

	/**
	 * This function is used to get value of cancellation method
	 * 
	 * @return
	 * 
	 */
	public String getCancellationRefundMethodValue() throws Exception {
		return getAttributeValue("getValueOfCancellationRefundMethod", "Name");
	}

	/**
	 * This function is clear the contract data and close the selected remittance
	 * 
	 * 
	 */
	public void collapseRemList() throws Exception {
		try {
			//// scroll up and clear data
			clearPreFilledData();
			contractScrollToTop();
			click("contractExpander");
			refreshRemittance();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public boolean verifyWestlakeWesternCodeInUI(String roleId, String name) {
		boolean result = false;
		waitForSomeTime(10);
		try {
			for (int i = 0; i < 3; i++) {
				click("propertyInfoScrollBar");
			}
		} catch (Exception e) {

		}
		takeScreenshot();
		boolean Western = false;
		boolean Westlake = false;

		if (name.equalsIgnoreCase("Westlake Partner Code")) {
			Westlake = checkIsDisplayed("propertyInfoWestlakeCol");
		} else {
			Western = checkIsDisplayed("propertyInfoWesternCol");
		}
		if (Westlake == true || Western == true) {

			result = true;
			logger.info(
					"Westlake Partner Code OR Western Funding Code Are Present In Ocean for Role ID--->>>" + roleId);
		}

		return result;
	}

	/**
	 * This function is used to select the top or First account for the agent,
	 * lender and dealer and also select the lender on the basis of classification
	 * list
	 * 
	 */
	public void selectAccountForRoleType() {
		click("listOfDisplayButton", 1);
	}

}
