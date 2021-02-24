package ocean.modules.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ocean.modules.database.SearchDataBase;

/**
 * This is object class which contains all pages of search modules
 * 
 * @author Mohit Goel
 */
public class SearchModulePages extends SearchDataBase {

	/**
	 * This function is used to get search count
	 * 
	 */
	public int getSearchesultsCount() {
		int count = 0;
		String countText = getAttributeValue("getSearchCount", "Name");
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(countText);
		while (m.find()) {
			String aa = m.group();
			count = Integer.parseInt(aa);
		}
		return count;
	}

	/**
	 * This function is used to get search WebContract count
	 * 
	 */
	public int getSearchCountWebContract() {
		int count = 0;
		String countText = getAttributeValue("getSearchCountWebContract", "Name");
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(countText);
		while (m.find()) {
			String aa = m.group();
			count = Integer.parseInt(aa);
		}
		return count;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * on Web contract given. It accepts a hashmap with input parameters
	 * 
	 */
	public void searchWebContractGivenInputParamatersWithCheckbox(HashMap<String, String> searchParamaters, String flag)
			throws Exception {
		click("ClearBtnOnWebContractSearch");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			if (!(valueToInput.isEmpty() || valueToInput.equals(""))) {
				switch (searchParamater) {

				case "CERT":
					type("CertWebContract", valueToInput);
					break;
				case "FIRST_NAME":
					type("firstNameWebContract", valueToInput);
					break;
				case "LAST_NAME":
					type("LastNameWebContract", valueToInput);
					break;
				case "VIN":
					type("VinWebContract", valueToInput);
					break;
				case "Status":
					String[] values = valueToInput.split(",");
					for (String value : values) {
						System.out.print(value);
						click("StatusWebContract");
						switch (value.toLowerCase()) {
						case "entered":
							click("clickEnteredStatusOnWebContract");
							break;
						case "processed":
							click("clickProcessedStatusOnWebContract");
							break;
						case "remitted":
							click("clickRemittedStatusOnWebContract");
							break;
						case "quote":
							click("clickQuoteStatusOnWebContract");
							break;
						}
					}
					break;
				case "State":
					type("StateWebContract", valueToInput);
					String city = searchParamaters.get("City");
					type("CityWebContract", city);
					break;
				case "DISPLAY_NAME":
					type("StateWebContract", valueToInput);
					// String city1 = searchParamaters.get("City");
					// type("CityWebContract", city1);
					break;
				case "City":
					if (searchParamaters.containsKey("DISPLAY_NAME") || searchParamaters.containsKey("State")) {
						String value2 = searchParamaters.get("DISPLAY_NAME");
						type("StateWebContract", value2);
						searchParamater.replace("DISPLAY_NAME", "");
					}
					type("CityWebContract", valueToInput);
					break;
				case "Phone":
					type("PhoneWebContract", valueToInput);
					break;
				case "Program_Code":
					type("ProgramCodeWebContract", valueToInput);
					break;
				case "Payee_ID":
					type("PrimaryPayeeIDWebContract", valueToInput);
					break;
				case "Primary_Seller_Name":
					type("PrimaryAccountNameWebContract", valueToInput);
					break;
				case "Primary_Seller_ID":
					type("PrimaryAccountIDWebContract", valueToInput);
					break;
				case "Primary_Seller_Type":
					type("PrimaryAccountTypeWebContract", valueToInput);
					break;
				case "From_Sale_Date":
					type("FromSaleDateWebContract", valueToInput);
					break;
				case "To_Sale_Date":
					type("ToSaleDateWebContract", valueToInput);
					break;
				case "Secondary_Seller_Name":
					type("SecondaryAccountNameWebContract", valueToInput);
					break;
				case "Secondary_Seller_ID":
					type("SecondaryAccountIDWebContract", valueToInput);
					break;
				case "Secondary_Seller_Type":
					type("SecondaryAccountTypeWebContract", valueToInput);
					break;
				case "Remit":
					type("Remit#WebContract", valueToInput);
					break;
				case "From_Remit_Date":
					type("FromCreatedDateWebContract", valueToInput);
					break;
				case "To_Remit_Date":
					type("ToRemitDateWebContract", valueToInput);
					break;
				case "From_Create_Date":
					type("FromCreateDateWebContract", valueToInput);
					break;
				case "To_Create_Date":
					type("ToCreateDateWebContract", valueToInput);
					break;
				case "PartnerName":
					type("IntegratedPartnerNameWebContract", valueToInput);
					break;
				case "PartnerID":
					type("IntegratedPartnerWSIDWebContract", valueToInput);
					break;
				default:
					//// do nothing
				}
			}

			if (flag.equalsIgnoreCase("true")) {
				String tState = getAttributeValue("stateCityCheckbox", "Toggle.ToggleState");
				if (tState.equalsIgnoreCase("0")) {
					click("stateCityCheckbox");
				}
			}
			if (flag.equalsIgnoreCase("") || flag.equalsIgnoreCase("false") || flag.isEmpty()) {
				String tState = getAttributeValue("stateCityCheckbox", "Toggle.ToggleState");
				if (tState.equalsIgnoreCase("1")) {
					click("stateCityCheckbox");
				}
			}
			///// click search button
		}
		click("listclickWebContractSearch");
	}

	/**
	 * This function is used to get ccontractId based on row number
	 * 
	 */
	public String getContractId(int i) {
		return getValue("listOfContractId", i);
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hashmap with input parameters
	 * 
	 */
	public void searchContractGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		waitForSomeTime(10);
		click("clickClearOnSearch");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			switch (searchParamater) {
			case "CERT":
				type("searchTypeContract", valueToInput);
				break;
			case "CUSTOMER_FIRST":
				type("searchTypeFirstName", valueToInput);
				break;
			case "CUSTOMER_LAST":
				type("searchTypeLastName", valueToInput);
				break;
			case "VIN":
				type("searchTypeVIN", valueToInput);
				break;
			case "Status":
				String[] values = valueToInput.split(",");
				for (String value : values) {
					click("clickStatus");
					switch (value.toLowerCase()) {
					case "underw":
						click("clickUnderW");
					case "onhold":
						click("clickOnHold");
					case "return":
						click("clickReturn");
					case "pending":
						click("clickPending");
					case "active":
						click("clickActive");
					case "purged":
						click("clickPurged");
					case "reference":
						click("clickReference");
					case "cancelled":
						click("clickCancelled");
					case "nis":
						click("clickNIS");
					default:
						// do nothing
					}
				}
				break;
			case "Sub_Status":
				String[] subValues = valueToInput.split(",");
				for (String value : subValues) {
					click("clickSubStatus");
					switch (value.toLowerCase()) {
					case "transferred":
						click("clickTransferred");
					case "rewritten":
						click("clickRewritten");
					case "reinstated":
						click("clickReinstated");
					case "nsf":
						click("clickNSF");
					default:
						// do nothing
					}
				}
				break;
			case "State":
				type("selectState", valueToInput);
				break;
			case "City":
				type("selectCity", valueToInput);
				break;
			case "Phone":
				type("typePhone", valueToInput);
				break;
			case "Program_Code":
				type("selectProgramCode", valueToInput);
				break;
			case "Primary_Payee_ID":
				type("typePrimaryPayeeID", valueToInput);
				break;
			case "Primary_Seller_Name":
				type("typePrimarySellerName", valueToInput);
				break;
			case "Primary_Seller_ID":
				type("typePrimarySellerId", valueToInput);
				break;
			case "Primary_Seller_Type":
				type("clickPrimarySellertype", valueToInput);
				break;
			case "From_Sale_Date":
				type("fromSaleDate", valueToInput);
				break;
			case "To_Sale_Date":
				type("toSaleDate", valueToInput);
				break;
			case "Secondary_Seller_Name":
				type("typeSecondarySellerName", valueToInput);
				break;
			case "Secondary_Seller_ID":
				type("typeSecondarySellerId", valueToInput);
				break;
			case "Secondary_Seller_Type":
				type("clickSecondarySellertype", valueToInput);
				break;
			case "From_Trans_Date":
				type("fromTransDate", valueToInput);
				break;
			case "To_Trans_Date":
				type("toTransDate", valueToInput);
				break;
			case "From_Post_Period":
				type("fromPostPeriod", valueToInput);
				break;
			case "To_Post_Period":
				type("toPostPeriod", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("clickSearchInSearchPage");
		waitForSomeTime(29);
		click("clickContractTab");
		waitForSomeTime(29);
	}

	/**
	 * This function is used to fetch all search data details for row i
	 * 
	 * 
	 */
	public HashMap<String, String> getSearchResult(int i) throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Contract", getValue("listOfContractId", i));
		summaryData.put("Code", getValue("listOfCode", i));
		summaryData.put("First_Name", getValue("listOfFirstName", i));
		summaryData.put("Last_Name", getValue("listOfLastName", i));// because of junk value in UI
		summaryData.put("Sale_Date", getValue("listOfSaleDate", i));
		summaryData.put("Trans_Date", getValue("listOfTransDate", i));
		summaryData.put("VIN", getValue("listOfVIN", i));
		summaryData.put("Status", getValue("listOfStatusInContract", i));
		summaryData.put("Post_Period", getValue("listOfPostPeriod", i));
		waitForSomeTime(5);
		click("swipeRightInContract");
		try {
			summaryData.put("SubStatus", getValue("listOfSubStatusInContract", i));
		} catch (Exception e) {
			summaryData.put("SubStatus", "");
		}
		summaryData.put("Primary_Seller_ID", getValue("listOfPrimarySellerId", i));
		summaryData.put("Primary_Seller_Name", getValue("listOfPrimarySellerName", i));
		summaryData.put("Secondary_Seller_Name", getValue("listOfSecondarySellerName", i));
		summaryData.put("Secondary_Seller_ID", getValue("listOfSecondarySellerId", i));
		summaryData.put("Primary_Seller_Type", getValue("listOfPrimarySellerType", i));
		summaryData.put("Secondary_Seller_Type", getValue("listOfSecondarySellerType", i));
//		summaryData.put("Primary_Payee_Id", getValue("listOfPrimaryPayeeId", i));
		summaryData.put("State", getValue("listOfStateInContract", i));
		summaryData.put("Phone", getValue("listOfPhoneInContract", i));// because of junk value in UI
		click("swipeLeftInContract");
		return summaryData;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * on Web contract given. It accepts a hashmap with input parameters
	 * 
	 */
	public void searchWebContractGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		click("clickClearBtnOnWebContractSearch");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			if (!(valueToInput.isEmpty() || valueToInput.equals(""))) {
				switch (searchParamater) {
				case "CERT":
					type("CertWebContract", valueToInput);
					break;
				case "FIRST_NAME":
					type("firstNameWebContract", valueToInput);
					break;
				case "LAST_NAME":
					type("LastNameWebContract", valueToInput);
					break;
				case "VIN":
					type("VinWebContract", valueToInput);
					break;
				case "Status":
					String[] values = valueToInput.split(",");
					for (String value : values) {
						System.out.print(value);
						click("StatusWebContract");
						switch (value.toLowerCase()) {
						case "entered":
							click("clickEnteredStatusOnWebContract");
							break;
						case "processed":
							click("clickProcessedStatusOnWebContract");
							break;
						case "remitted":
							click("clickRemittedStatusOnWebContract");
							break;
						case "quote":
							click("clickQuoteStatusOnWebContract");
							break;
						}
					}
					break;
				case "State":
					type("StateWebContract", valueToInput);
					break;
				case "DISPLAY_NAME":
					type("StateWebContract", valueToInput);
					break;
				case "City":
					type("CityWebContract", valueToInput);
					break;
				case "Phone":
					type("PhoneWebContract", valueToInput);
					break;
				case "Program_Code":
					type("ProgramCodeWebContract", valueToInput);
					break;
				case "Payee_ID":
					type("PrimaryPayeeIDWebContract", valueToInput);
					break;
				case "Primary_Seller_Name":
					type("PrimaryAccountNameWebContract", valueToInput);
					break;
				case "Primary_Seller_ID":
					type("PrimaryAccountIDWebContract", valueToInput);
					break;
				case "Primary_Seller_Type":
					type("PrimaryAccountTypeWebContract", valueToInput);
					break;
				case "From_Sale_Date":
					type("FromSaleDateWebContract", valueToInput);
					break;
				case "To_Sale_Date":
					type("ToSaleDateWebContract", valueToInput);
					break;
				case "Secondary_Seller_Name":
					type("SecondaryAccountNameWebContract", valueToInput);
					break;
				case "Secondary_Seller_ID":
					type("SecondaryAccountIDWebContract", valueToInput);
					break;
				case "Secondary_Seller_Type":
					type("SecondaryAccountTypeWebContract", valueToInput);
					break;
				case "Remit":
					type("Remit#WebContract", valueToInput);
					break;
				case "From_Remit_Date":
					type("FromCreatedDateWebContract", valueToInput);
					break;
				case "To_Remit_Date":
					type("ToRemitDateWebContract", valueToInput);
					break;
				case "From_Create_Date":
					type("FromCreateDateWebContract", valueToInput);
					break;
				case "To_Create_Date":
					type("ToCreateDateWebContract", valueToInput);
					break;
				case "PartnerName":
					type("IntegratedPartnerNameWebContract", valueToInput);
					break;
				case "PartnerID":
					type("IntegratedPartnerWSIDWebContract", valueToInput);
					break;
				default:
					//// do nothing
				}
			}

		}
		///// click search button
		click("listclickWebContractSearch");

	}

///// Verify cancel btn on Search WebContract

	public boolean verifyCancelBTn() {
		String matchFlag = null;
		String uiFlag;
		boolean result = false;
		try {
			matchFlag = getAttributeValue("searchLoadBtnOnWebSearch", "IsOffscreen");
			System.out.println(matchFlag);
		} catch (Exception e) {
		}
		if (matchFlag.equalsIgnoreCase("false")) {
			click("cancelBtnOnWebSearch");
			waitForSomeTime(2);
			uiFlag = getAttributeValue("searchLoadBtnOnWebSearch", "IsOffscreen");
			System.out.println(uiFlag);
			if (uiFlag.equalsIgnoreCase("true")) {
				result = true;
			}
		} else {
			logger.info("Search Already Completed");
			result = true;
		}
		return result;
	}

	/**
	 * This function is used to fetch all search data details for row i on web
	 * contract
	 * 
	 */
	public HashMap<String, String> getSearchResultWebContract(int i) throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Contract", getValue("listOfContractIdWeb", i));
		summaryData.put("PROGRAM_CODE", getValue("listOfCodeWeb", i));
		summaryData.put("First_Name", getValue("listOfFirstNameWeb", i).trim());
		summaryData.put("Last_Name", getValue("listOfLastNameWeb", i));
		summaryData.put("Sale_Date", getValue("listOfSaleDateWeb", i));
		summaryData.put("Created_Date", getValue("listOfCreatedDateWeb", i));
		summaryData.put("VIN", getValue("listOfVINWeb", i));
		summaryData.put("Status", getValue("listOfStatusWeb", i));
		summaryData.put("Primary_Account_Name", getValue("listOfPrimarySellerNameWeb", i));
		summaryData.put("Primary_Account_Type", getValue("listOfPrimarySellerTypeWeb", i));
		click("listScrollRightWebContracts");
		summaryData.put("Primary_Account_City", getValue("listOfPrimarySellerCityWeb", i));
		summaryData.put("Primary_Account_State", getValue("listOfPrimarySellerStateWeb", i));
		summaryData.put("Secondary_Account_ID", getValue("listOfSecondarySellerIdWeb", i));
		summaryData.put("Secondary_Account_Name", getValue("listOfSecondarySellerNameWeb", i));
		summaryData.put("Secondary_Account_Type", getValue("listOfSecondarySellerTypeWeb", i));
		summaryData.put("Secondary_Account_City", getValue("listOfSecondarySellerCityWeb", i));
		summaryData.put("Secondary_Account_State", getValue("listOfSecondarySellerStateWeb", i));
		summaryData.put("Primary_Payee_ID", getValue("listOfPrimaryPayeeIdWeb", i));
		summaryData.put("Customer_City", getValue("listOfCityWeb", i));
		summaryData.put("Customer_State", getValue("listOfStateWeb", i));
		summaryData.put("Phone", getValue("listOfPhoneWeb", i));
		summaryData.put("REMIT_NO", getValue("listOfRemit#Web", i));
		summaryData.put("Remit_Date", getValue("listOfRemitDateWeb", i));
		// click("swipeLeft");
		click("listScrollLeftWebContracts");
		for (Map.Entry<String, String> entry : summaryData.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();

			if (key.equalsIgnoreCase("Created_Date") || key.equalsIgnoreCase("Sale_Date")
					|| key.equalsIgnoreCase("Remit_Date")) {
				if (value != ("NULL") && value != ("None") && value != ("") && value != (" ") && value.length() != 0) {

					String sDate1 = value;
					DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
					Date date = (Date) parser.parse(sDate1);
					DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					String newFormatDate = formatter.format(date);
					value = newFormatDate.toString();
					summaryData.replace(key, value);
				}
			}
			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				summaryData.replace(key, "***");
			}
		}
		return summaryData;
	}

	/**
	 * This function is use verify download Excel file.
	 * 
	 * 
	 */
	public void verifyExportFileWebContract(int screenCount) throws Exception {
		boolean countFlag = false;
		click("clickOK");
		Robot r = new Robot();
		waitForSomeTime(10);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_F4);
		r.keyRelease(KeyEvent.VK_F4);
		r.keyRelease(KeyEvent.VK_ALT);
		try {
			click("clickRadiobuttonOnExcel");
			click("clickOKOnExcelPopup");
		} catch (Exception exception) {
			// do nothing
		}
		waitForSomeTime(10);
		String desktopPath = System.getProperty("user.home") + "\\Desktop";
		File directory = new File(desktopPath);
		File[] files = directory.listFiles();
		long lastModifiedTime = Long.MIN_VALUE;
		File downloadedFile = null;
		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					downloadedFile = file;
					lastModifiedTime = file.lastModified();
				}
			}
		}

		String sheetName = downloadedFile.getName();
		sheetName = sheetName.replace(".xlsx", "");
		String fileName = downloadedFile.getName();
		fileName = desktopPath + "\\" + fileName;

		int rowsExcel = getDownloadedExcelRow(fileName, sheetName);
		if (rowsExcel == screenCount) {
			countFlag = true;
		}
		assertEquals(countFlag, true);
	}

	/**
	 * This function is used to fetch all search data details for row i on web
	 * contract
	 * 
	 */
	public HashMap<String, String> getCompareSearchResultFirst(int i) throws Exception {

		String PRICE = "";
		String AUL_PREMIUM = "";

		HashMap<String, String> compareData1 = new HashMap<String, String>();
		// compareData1.put("Contract", getTextOfElement("listOfContractIdWeb"));
		compareData1.put("PROGRAM_CODE", getValue("listWebContractInfoProgrmCode", i));
		// compareData1.put("MILEAGE", getValue("listWebContractInfoMileage", i));
		compareData1.put("STATUS", getValue("listWebContractInfoStatus", i));
		// compareData1.put("Sale_Date", getTextOfElement("listCoverageInfoCoverage"));
		compareData1.put("COVERAGE", getValue("listCoverageInfoCoverage", i));
		compareData1.put("TERM", getValue("listCoverageInfoTerm", i));
		compareData1.put("Primary_Seller_Type", getValue("listAccountInfoPriAccRole", i));
		compareData1.put("Primary_Seller_Name", getValue("listAccountInfoPriAccName", i));
		compareData1.put("Primary_Seller_ID", getValue("listAccountInfoPriAccRoleId", i));
		compareData1.put("Primary_Seller_Status", getValue("listAccountInfoPriAccStatus", i));
		compareData1.put("Secondary_Seller_ID", getValue("listAccountInfoSecAccRoleId", i));
		compareData1.put("Secondary_Seller_Status", getValue("listAccountInfoSecAccStatus", i));
		compareData1.put("First_Name", getValue("listCustomerInfoFirstName", i));
		compareData1.put("Last_Name", getValue("listCustomerInfoLastName", i));
		compareData1.put("CITY", getValue("listCustomerInfoCity", i));
		compareData1.put("STATE", getValue("listCustomerInfoState", i));
		compareData1.put("VIN", getValue("listVehicleInfoVIN", i));
		compareData1.put("CLASS", getValue("listVehicleInfoClass", i));
		compareData1.put("MILEAGE", getValue("listVehicleInfoMileage", i));
		compareData1.put("PRICE", getValue("listPricingInfoPrice", i));
		compareData1.put("AUL_PREMIUM", getValue("listPricingInfoAUL", i));
		compareData1.put("OPTION_TOTAL", getValue("listPricingInfoOptionTotal", i));
		compareData1.put("DEDUCTIBLE", getValue("listCoverageInfoDeductible", i));

		PRICE = compareData1.get("PRICE");
		PRICE = PRICE.replaceAll(",", "");
		AUL_PREMIUM = compareData1.get("AUL_PREMIUM");
		AUL_PREMIUM = AUL_PREMIUM.replaceAll(",", "");
		compareData1.put("PRICE", PRICE);
		compareData1.put("AUL_PREMIUM", AUL_PREMIUM);

		for (Map.Entry<String, String> entry : compareData1.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("")) {
				compareData1.replace(key, "***");

			}
		}

		verifyUiChanges(i);
		return compareData1;
	}

	/**
	 * This function is used to verify UI Changes as a part of PBI 17242
	 * 
	 */
	public void verifyUiChanges(int i) throws Exception {

		String pricesheet = getValue("listWebContractInfoPriceSheetLabel", i);
		assertEquals(pricesheet, "Price Sheet:");
		String saleDate = getValue("listWebContractInfoSaleDateLabel", i);
		assertEquals(saleDate, "Sale Date:");
		String firstName = getValue("listCustomerInfoFirstNameLabel", i);
		assertEquals(firstName, "First Name:");
		String lastName = getValue("listCustomerInfoLastNameLabel", i);
		assertEquals(lastName, "Last Name:");
		String vehiclePrice = getValue("listPricingInfoVehiclePriceLabel", i);
		assertEquals(vehiclePrice, "Vehicle Price:");
		String customerPaid = getValue("listPricingInfoCustomerPaidLabel", i);
		assertEquals(customerPaid, "Customer Paid:");
		String aulPrice = getValue("listPricingInfoAULPriceLabel", i);
		assertEquals(aulPrice, "AUL Price:");
		String lenderMarkup = getValue("listDefaultMarkupInfoLenderMarkup", i);
		assertEquals(lenderMarkup, "Lender Markup:");
	}

	/**
	 * This function is used to get ccontractId based on row number on web contract
	 *
	 */
	public String getContractIdWeb(int i) {
		return getValue("listOfContractIdWeb", i);
	}

	/*
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hashmap with input parameters in Transaction History
	 * search
	 * 
	 */
	public void searchContractGivenInputParamatersTransactionHistory(HashMap<String, String> searchParamaters)
			throws Exception {
		click("clearTransHist");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			switch (searchParamater) {
			case "CERT":
				type("certTransHist", valueToInput);
				break;
			case "Role Type":
				type("roleTypeTransHist", valueToInput);
				break;
			case "ADJTYPE":
				String value = valueToInput.trim();
				click("typeTransHist");
				WebElement ele = windowsDriver.findElement(By.className("Popup"));
				List<WebElement> list = ele.findElements(By.className("ListBoxItem"));
				for (WebElement webElement : list) {
					String comboText = webElement.getText();
					if (comboText.equalsIgnoreCase(value)) {
						webElement.click();
					}
				}
				String value1 = searchParamaters.get("ADJTYPE_DET");
				value1 = value1.trim();
				if (((value.equals("EXTADJ") || value.equals("REMITDIFF") || value.equals("UWADJ"))
						&& (value1 != "*" || value1 != "" || value1 != null))) {
					click("subTypeTransHist");
					WebElement ele1 = windowsDriver.findElement(By.className("Popup"));
					List<WebElement> list1 = ele1.findElements(By.className("ListBoxItem"));
					for (WebElement webelement : list1) {
						String comboText = webelement.getText();
						if (comboText.equalsIgnoreCase(value1)) {
							webelement.click();
						}
					}
				}
				String value2 = searchParamaters.get("ADJTYPE_CAT");

				if ((value2 != "*" || value2 != "" || value1 != null)) {
					if ((value.equals("EXTADJ") || value.equals("REMITDIFF"))) {
						click("categoryTransHist");
						WebElement ele2 = windowsDriver.findElement(By.className("Popup"));
						List<WebElement> list2 = ele2.findElements(By.className("ListBoxItem"));
						for (WebElement webelement : list2) {
							String comboText = webelement.getText();
							if (comboText.equalsIgnoreCase(value1)) {
								webelement.click();
							}
						}
					}
				}
				break;
			case "ROLE_NAME":
				type("roleTypeTransHist", valueToInput);
				break;
			case "Name":
				type("roleNameTransHist", valueToInput);
				break;
			case "Role_Identifier":
				type("roleIdTransHist", valueToInput);
				break;
			case "Batch_No":
				type("batchTransHist", valueToInput);
				break;
			case "Program_Code":
				type("programCodeTransHist", valueToInput);
				break;
			case "Post_Period":
				type("postPeriodTransHist", valueToInput);
				break;
			case "Check_No":
				type("checkNoTransHist", valueToInput);
				break;
			case "Check_AMT":
				String string_temp = new Double(valueToInput).toString();
				String string_form = string_temp.substring(0, string_temp.indexOf('.'));
				type("checkAmountTransHist", string_form);
				break;
			case "DBCR_AMT":
				String string_temp2 = new Double(valueToInput).toString();
				String string_form2 = string_temp2.substring(0, string_temp2.indexOf('.'));
				type("debitCreditTransHist", string_form2);
				break;
			case "Dealer_Paid":
				String string_temp1 = new Double(valueToInput).toString();
				String string_form1 = string_temp1.substring(0, string_temp1.indexOf('.'));
				type("dealerPaidTransHist", string_form1);
				break;
			case "Agent1_ID":
				type("agentIDTransHist", valueToInput);
				break;
			case "From_Trans_Date":
				type("fromTransactionTransHist", valueToInput);
				break;
			case "To_Trans_Date":
				type("toTransactionTransHist", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("searchTransHist");
	}

	public List<Map<String, String>> getSearchResultTransactionHistory(int i) throws Exception {

		List<Map<String, String>> rows1 = new ArrayList<Map<String, String>>();
		System.out.print("inside grid");
		Map<String, String> compareData = new HashMap<String, String>();
		compareData.put("CERT", getValue("listCertTransHist", i));
		compareData.put("ADJTYPE", getValue("listTypeTransHist", i));
		compareData.put("ADJTYPE_DET", getValue("listSubTypeTransHist", i));
		compareData.put("ADJTYPE_CAT", getValue("listCategoryTransHist", i));
		compareData.put("CHECK_NO", getValue("listCheckNumberTransHist", i));
		compareData.put("CHECK_AMT", getValue("listCheckAmountTransHist", i));
		compareData.put("BATCH_NO", getValue("listBatchTransHist", i));
		compareData.put("Debit_Credit", getValue("listDebit/CreditTransHist", i));
		compareData.put("POST_PERIOD", getValue("listPostPeriodTransHist", i));
		compareData.put("DEALER_PAID", getValue("listDealerPaidTransHist", i));
		compareData.put("STD_COMMENTS", getValue("listExtCommentTransHist", i));
		compareData.put("ADD_COMMENTS", getValue("listIntCommentTransHist", i));

		for (Entry<String, String> entry : compareData.entrySet()) {
			String value = entry.getValue();

			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("")) {
				compareData.replace(key, "***");

			}
		}

		rows1.add(compareData);
		return rows1;

	}

	/**
	 * This function is use to check that file is download or not
	 * 
	 * 
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) throws Exception {
		boolean flag = false;
		if ((fileName).contains("Internal") || (fileName).contains("External"))
			flag = true;
		return flag;
	}

	/**
	 * This function is use to delete the download file
	 * 
	 * 
	 */
	public void deleteExportFile(String deletepath) throws Exception {
		try {
			// creates a file instance
			File file = new File(deletepath);
			// deletes the file
			file.delete();
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This function is use to delete the download file
	 * 
	 * 
	 */
	public void verifyExportFile() throws Exception {
		click("clickOK");
		Robot r = new Robot();
		waitForSomeTime(10);
		r.keyPress(KeyEvent.VK_ALT);
		r.keyPress(KeyEvent.VK_F4);
		r.keyRelease(KeyEvent.VK_F4);
		r.keyRelease(KeyEvent.VK_ALT);
		try {
			click("clickRadiobuttonOnExcel");
			click("clickOKOnExcelPopup");
		} catch (Exception exception) {
			// do nothing
		}
		waitForSomeTime(10);
		String desktopPath = System.getProperty("user.home") + "\\Desktop";
		File directory = new File(desktopPath);
		File[] files = directory.listFiles();
		long lastModifiedTime = Long.MIN_VALUE;
		File downloadedFile = null;
		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					downloadedFile = file;
					lastModifiedTime = file.lastModified();
				}
			}
		}
		String fileName = downloadedFile.getName();
		boolean downloaded = isFileDownloaded(desktopPath, fileName);
		if (downloaded == true) {
			String deletepath = desktopPath + "\\" + fileName;
			deleteExportFile(deletepath);
			boolean deleted = false;
			File dirAfterDelete = new File(desktopPath);
			File[] filesAfterDelete = dirAfterDelete.listFiles();
			for (int i = 0; i < filesAfterDelete.length; i++) {
				if (filesAfterDelete[i].getName().equals(fileName)) {
					deleted = true;
				}
				assertEquals(deleted, false);
			}
		} else {
			throw new Exception("File not deleted");
		}
	}

	/**
	 * This function is use to verify the compare contract
	 * 
	 * 
	 */
	public void verifyCompareContract(Set<String> windowHandles, String OceanPage) throws Exception {
		for (int i = 0; i < 2; i++) {
			//// convert date as per Ui format
			waitForSomeTime(5);
			//// get All data for search results
			HashMap<String, String> uiMap = getSearchResult(i);
			String date = uiMap.get("Trans_Date");
			int index = date.indexOf(" ");
			if (index > 0)
				uiMap.put("Trans_Date", date.substring(0, index));
			HashMap<String, String> uiMapRowWise = null;
			uiMapRowWise = uiMap;
			waitForSomeTime(5);
			String popup = "";
			for (String singleWindowHandle : windowHandles) {
				popup = singleWindowHandle;
			}
			if (!OceanPage.equals(popup)) {
				windowsDriver.switchTo().window(popup);
			}
			HashMap<String, String> compareContractMap = new HashMap<String, String>();
			if (i == 0) {
				waitForSomeTime(15);
				compareContractMap.put("Contract", getTextOfElement("getCertOnCompareFirstRow"));
				compareContractMap.put("State", getTextOfElement("getStateOnCompareFirstRow"));
				compareContractMap.put("Phone", getTextOfElement("getPhoneCompareFirstRow"));
				compareContractMap.put("First_Name", getTextOfElement("getFNameOnCompareFirstRow"));
				compareContractMap.put("Last_Name", getTextOfElement("getLNameOnCompareFirstRow"));
				compareContractMap.put("Sale_Date", getTextOfElement("getSaleDateOnCompareFirstRow"));
				compareContractMap.put("Trans_Date", getTextOfElement("getTransDateOnCompareFirstRow"));
				compareContractMap.put("VIN", getTextOfElement("getVINOnCompareFirstRow"));
				compareContractMap.put("Sale_Date", convertNewDateForCompare(compareContractMap.get("Sale_Date")));
				compareContractMap.put("Trans_Date", convertNewDateForCompare(compareContractMap.get("Trans_Date")));
				if (uiMapRowWise.equals(compareContractMap)) {
					windowsDriver.switchTo().window(OceanPage);
					assertTrue(true, "Data did not matched.");
				}
			} else {
				compareContractMap.put("Contract", getTextOfElement("getCertOnCompareSecondRow"));
				compareContractMap.put("State", getTextOfElement("getStateOnCompareSecondRow"));
				compareContractMap.put("Phone", getTextOfElement("getPhoneCompareSecondRow"));
				compareContractMap.put("First_Name", getTextOfElement("getFNameOnCompareSecondRow"));
				compareContractMap.put("Last_Name", getTextOfElement("getLNameOnCompareSecondRow"));
				compareContractMap.put("Sale_Date", getTextOfElement("getSaleDateOnCompareSecondRow"));
				compareContractMap.put("Trans_Date", getTextOfElement("getTransDateOnCompareSecondRow"));
				compareContractMap.put("VIN", getTextOfElement("getVINOnCompareSecondRow"));
				compareContractMap.put("Sale_Date", convertNewDateForCompare(compareContractMap.get("Sale_Date")));
				compareContractMap.put("Trans_Date", convertNewDateForCompare(compareContractMap.get("Trans_Date")));
				if (uiMapRowWise.equals(compareContractMap)) {
					windowsDriver.switchTo().window(OceanPage);
					assertTrue(true, "Data did not matched.");
				}
			}
		}
	}

	/**
	 * This function is use to verify the clear compare contract
	 * 
	 * 
	 */
	public void verifyClearCompared(String objectRepo) throws Exception {
		if (listOfElements("itemInListBox").size() > 1) {
			click(objectRepo);
			try {
				listOfElements("itemInListBox").size();
				assertFalse(false, "Data still available");
			} catch (Exception exception) {
				assertTrue(true, "Compare data is not deleted");
			}
		} else {
			assertFalse(false, "No data available");
		}
	}

	/**
	 * This function is use to verify the PDF for contract and clear button
	 * 
	 * 
	 */
	public void verifyPDF() throws Exception {
		try {
			click("clickOnPDFInContractSearch");
			click("closePDF");
			assertTrue(true, "PDF not opened successfuly!");
		} catch (Exception e) {
			// do nothing
		}
		click("clickClearOnSearch");
		waitForSomeTime(5);
	}

	/**
	 * This function is use to verify clear button in contract search
	 * 
	 * 
	 */
	public void verifyClearContractSearch() throws Exception {
		click("clickClearOnSearch");
		waitForSomeTime(5);
		try {
			if (getSearchesultsCount() == 0 && getTextOfElement("searchTypeContract").length() == 0
					&& getTextOfElement("searchTypeFirstName").length() == 0
					&& getTextOfElement("searchTypeLastName").length() == 0
					&& getTextOfElement("searchTypeVIN").length() == 0 && getTextOfElement("selectCity").length() == 0
					&& getTextOfElement("typePhone").length() == 0 && getTextOfElement("selectState").length() == 0
					&& getTextOfElement("selectProgramCode").length() == 0
					&& getTextOfElement("typePrimaryPayeeID").length() == 0
					&& getTextOfElement("typePrimarySellerName").length() == 0
					&& getTextOfElement("typePrimarySellerId").length() == 0
					&& getTextOfElement("clickPrimarySellertype").length() == 0
					&& getTextOfElement("fromSaleDate").length() == 0 && getTextOfElement("toSaleDate").length() == 0
					&& getTextOfElement("typeSecondarySellerName").length() == 0
					&& getTextOfElement("typeSecondarySellerId").length() == 0
					&& getTextOfElement("clickSecondarySellertype").length() == 0
					&& getTextOfElement("fromTransDate").length() == 0
					&& getTextOfElement("selectPostPeriod").length() == 0
					&& getTextOfElement("toTransDate").length() == 0) {
				assertTrue(true, "All data has not been cleared");
			}
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * This function is used to get ccontractId based on row number
	 * 
	 */
	public String getCancelContractId(int i) {
		return getValue("listOfContractInCancellationSearch", i);
	}

	/**
	 * This function is used to fetch all search cancellation data details for row i
	 * 
	 * 
	 */
	public HashMap<String, String> getCancellationSearchResult(int i) throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Contract", getValue("listOfContractInCancellationSearch", i));
		summaryData.put("First_Name", getValue("listOfFNameInCancellationSearch", i).trim());
		summaryData.put("Last_Name", getValue("listOfLNameInCancellationSearch", i).trim());
		summaryData.put("Status", getValue("listOfStatusInCancellationSearch", i));
		summaryData.put("Process_Date", getValue("listOfProcessDateInCancellationSearch", i));
		summaryData.put("Net_Refund", getValue("listOfNetRefundInCancellationSearch", i));
		summaryData.put("Customer_Refund", getValue("listOfCustomerRefundInCancellationSearch", i));
		System.out.println("summaryDataui=====" + summaryData);
		return summaryData;
	}

	/**
	 * This function is use to verify clear button in cancellation search
	 * 
	 * 
	 */
	public void verifyClearCancellationSearch() throws Exception {
		click("clickClearInCancellationSearch");
		waitForSomeTime(5);
		try {
			if (getTextOfElement("TypeContractInCancellationSearch").length() == 0
					&& getTextOfElement("TypeRoleIDInCancellationSearch").length() == 0
					&& getTextOfElement("TypeLNameInCancellationSearch").length() == 0
					&& getTextOfElement("TypeFNameInCancellationSearch").length() == 0
					&& getTextOfElement("TypePayeeInCancellationSearch").length() == 0
					&& getTextOfElement("TypeVINInCancellationSearch").length() == 0
					&& getTextOfElement("TypePCodeInCancellationSearch").length() == 0
					&& getTextOfElement("TypeFCancelDateInCancellationSearch").length() == 0
					&& getTextOfElement("TypeTCancelDateInCancellationSearch").length() == 0
					&& getTextOfElement("TypeFDateProcessedInCancellationSearch").length() == 0
					&& getTextOfElement("TypeTDateProcessedInCancellationSearch").length() == 0) {
				assertTrue(true, "All data has not been cleared");
				System.out.println("pass");
			}
		} catch (Exception e) {
			// do nothing
		}
	}

	/**
	 * This function is use to verify the compare contract
	 * 
	 * 
	 */
	public void verifyCancelCompareContract(Set<String> windowHandles, String OceanPage) throws Exception {
		for (int i = 0; i < 2; i++) {
			//// convert date as per Ui format
			waitForSomeTime(5);
			//// get All data for search results
			HashMap<String, String> uiMap = getCancellationSearchResult(i);
			String date = uiMap.get("Process_Date");
			int index = date.indexOf(" ");
			if (index > 0)
				uiMap.put("Process_Date", date.substring(0, index));
			System.out.println("uiMap====>" + uiMap);
			HashMap<String, String> uiMapRowWise = null;
			uiMapRowWise = uiMap;
			waitForSomeTime(5);
			String popup = "";
			for (String singleWindowHandle : windowHandles) {
				popup = singleWindowHandle;
			}
			if (!OceanPage.equals(popup)) {
				windowsDriver.switchTo().window(popup);
			}
			HashMap<String, String> compareContractMap = new HashMap<String, String>();
			if (i == 0) {
				waitForSomeTime(15);
				compareContractMap.put("Contract", getTextOfElement("getCertOnCancelCompareFirstRow"));
				compareContractMap.put("First_Name", getTextOfElement("getFNameOnCancelCompareFirstRow"));
				compareContractMap.put("Process_Date", getTextOfElement("getProcessDateOnCancelCompareFirstRow"));
				compareContractMap.put("Net_Refund", getTextOfElement("getNetRefundOnCancelCompareFirstRow"));
				compareContractMap.put("Customer_Refund", getTextOfElement("getCustomerRefundOnCancelCompareFirstRow"));
				compareContractMap.put("Process_Date",
						convertNewDateForCompare(compareContractMap.get("Process_Date")));
				System.out.println("compareContractMap1=====" + compareContractMap);
				if (uiMapRowWise.equals(compareContractMap)) {
					windowsDriver.switchTo().window(OceanPage);
					assertTrue(true, "Data did not matched.");
				}
			} else {
				click("swipeRightInContract");
				compareContractMap.put("Contract", getTextOfElement("getCertOnCancelCompareSecondRow"));
				compareContractMap.put("First_Name", getTextOfElement("getFNameOnCancelCompareSecondRow"));
				compareContractMap.put("Process_Date", getTextOfElement("getProcessDateOnCancelCompareSecondRow"));
				compareContractMap.put("Net_Refund", getTextOfElement("getNetRefundOnCancelCompareSecondRow"));
				compareContractMap.put("Customer_Refund",
						getTextOfElement("getCustomerRefundOnCancelCompareSecondRow"));
				compareContractMap.put("Process_Date",
						convertNewDateForCompare(compareContractMap.get("Process_Date")));
				System.out.println("compareContractMap2=====" + compareContractMap);
				if (uiMapRowWise.equals(compareContractMap)) {
					windowsDriver.switchTo().window(OceanPage);
					assertTrue(true, "Data did not matched.");
				}
			}
		}
	}

}
