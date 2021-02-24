package ocean.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Enums;

import ocean.com.main.Suite;

/**
 * This class contains common functions, which is used all over scripts, to save
 * manual efforts and LOC
 * 
 * @author Mohit Goel
 */
public class CommonFunctions extends Suite {

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> cancellation_Search_appendSearchData(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("CERT", inputArray[i]);
				break;
			case 1:
				searchData.put("CUSTOMER_FIRST", inputArray[i]);
				break;
			case 2:
				searchData.put("CUSTOMER_LAST", inputArray[i]);
				break;
			case 3:
				searchData.put("VIN", inputArray[i]);
				break;
			case 4:
				searchData.put("Status", inputArray[i]);
				break;
			case 5:
				searchData.put("Sub_Status", inputArray[i]);
				break;
			case 6:
				searchData.put("State", inputArray[i]);
				break;
			case 7:
				searchData.put("City", inputArray[i]);
				break;
			case 8:
				searchData.put("Phone", inputArray[i]);
				break;
			case 9:
				searchData.put("Program_Code", inputArray[i]);
				break;
			case 10:
				searchData.put("Primary_Payee_ID", inputArray[i]);
				break;
			case 11:
				searchData.put("Primary_Seller_Name", inputArray[i]);
				break;
			case 12:
				searchData.put("Primary_Seller_ID", inputArray[i]);
				break;
			case 13:
				searchData.put("Primary_Seller_Type", inputArray[i]);
				break;
			case 14:
				searchData.put("From_Sale_Date", inputArray[i]);
				break;
			case 15:
				searchData.put("To_Sale_Date", inputArray[i]);
				break;
			case 16:
				searchData.put("Secondary_Seller_Name", inputArray[i]);
				break;
			case 17:
				searchData.put("Secondary_Seller_ID", inputArray[i]);
				break;
			case 18:
				searchData.put("Secondary_Seller_Type", inputArray[i]);
				break;
			case 19:
				searchData.put("From_Trans_Date", inputArray[i]);
				break;
			case 20:
				searchData.put("To_Trans_Date", inputArray[i]);
				break;
			case 21:
				searchData.put("From_Post_Period", inputArray[i]);
				break;
			case 22:
				searchData.put("To_Post_Period", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		return searchData;
	}

	/**
	 * This function is used to select check
	 * 
	 */
	public void contractScrollToTop() throws Exception {
		for (int i = 0; i < 1; i++) {
			try {
				click("scrollContractsListUp");
			} catch (Exception e) {
			}

		}
	}

	public void contractScrollToBottom() throws Exception {
		for (int i = 0; i < 1; i++) {
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
			}

		}
	}

	public void switchToNewWindow() {
		String OceanPage = windowsDriver.getWindowHandle();
		String newWindow = "";
		Set<String> winHandles = windowsDriver.getWindowHandles();
		for (String singleWindowHandle : winHandles) {
			newWindow = singleWindowHandle;
			if (!OceanPage.equals(newWindow)) {
				windowsDriver.switchTo().window(newWindow);
			}
		}
	}

	public String EnterVinDetails(String vinNumber) throws Exception {
		typeKeys("vinNumber", vinNumber);
		try {
			click("VINDuplicate");
		} catch (Exception e) {
			// TODO: handle exception
		}
		String messagsse = "";
		String model = "";
		try {
			waitForSomeTime(15);
			switchToNewWindow();
			messagsse = getValue("VINMESSAGE").toLowerCase();
			click("clickOK");
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			model = getValue("vinModel").replaceAll("[^a-zA-Z0-9]", "");
		}

		catch (Exception e) {
			// TODO: handle exception
		}

		if (messagsse.contains("invalid vin")) {
			return "Invalid Vin";
		}

		else if (messagsse.contains("verify trim level for eligibility")) {
			if (!Enums.getIfPresent(Variables.CarDetails.class, model).isPresent()) {
				throw new Exception("Error message populated");
			} else {
				return "Vin Error Message Validated";
			}
		}

		else {
			if (model.length() > 1 && !Enums.getIfPresent(Variables.CarDetails.class, model).isPresent()) {
				return "VIN Check OK";
			} else
				throw new Exception("VIN details not proper");
		}
	}

	/**
	 * This function is used to clear data on new business form
	 * 
	 */
	public void clearPreFilledData() throws Exception {
		click("scrollContractsListUp");
		try {
			//// click yes to lock remittance
			click("scrollContractsListUp");

		} catch (Exception e) {
			// do nothing
		}
		click("clearContractData");
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, will remove *
	 * and Blanks columns and values
	 * 
	 */
	public HashMap<String, String> cancellation_Search_convertDataRemoveStar(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*") && inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("CERT", inputArray[i]);
					break;
				case 1:
					searchData.put("CUSTOMER_FIRST", inputArray[i]);
					break;
				case 2:
					searchData.put("CUSTOMER_LAST", inputArray[i]);
					break;
				case 3:
					searchData.put("VIN", inputArray[i]);
					break;
				case 4:
					searchData.put("Status", inputArray[i]);
					break;
				case 5:
					searchData.put("Sub_Status", inputArray[i]);
					break;
				case 6:
					searchData.put("State", inputArray[i]);
					break;
				case 7:
					searchData.put("City", inputArray[i]);
					break;
				case 8:
					searchData.put("Phone", inputArray[i]);
					break;
				case 9:
					searchData.put("Program_Code", inputArray[i]);
					break;
				case 10:
					searchData.put("Primary_Payee_ID", inputArray[i]);
					break;
				case 11:
					searchData.put("Primary_Seller_Name", inputArray[i]);
					break;
				case 12:
					searchData.put("Primary_Seller_ID", inputArray[i]);
					break;
				case 13:
					searchData.put("Primary_Seller_Type", inputArray[i]);
					break;
				case 14:
					searchData.put("From_Sale_Date", inputArray[i]);
					break;
				case 15:
					searchData.put("To_Sale_Date", inputArray[i]);
					break;
				case 16:
					searchData.put("Secondary_Seller_Name", inputArray[i]);
					break;
				case 17:
					searchData.put("Secondary_Seller_ID", inputArray[i]);
					break;
				case 18:
					searchData.put("Secondary_Seller_Type", inputArray[i]);
					break;
				case 19:
					searchData.put("From_Trans_Date", inputArray[i]);
					break;
				case 20:
					searchData.put("To_Trans_Date", inputArray[i]);
					break;
				case 21:
					searchData.put("From_Post_Period", inputArray[i]);
					break;
				case 22:
					searchData.put("To_Post_Period", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}

			}
		}
		return searchData;
	}

	/*
	 * This function get the Date converted from mm/dd/yy to dd/mm/yy
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertNewDateForCancel08(String date) throws Exception {
		String abc = "";
		try {
			//// get only date
			int index = date.indexOf(" ");
			if (index > 0)
				date = date.substring(0, index);
			//// format string to date
			SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = format1.parse(date);
			abc = format2.format(date1);
			return abc;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> account_appendSearchData(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("Account_Name", inputArray[i]);
				break;
			case 1:
				searchData.put("Role_Id", inputArray[i]);
				break;
			case 2:
				searchData.put("Role_Type", inputArray[i]);
				break;
			case 3:
				searchData.put("Address", inputArray[i]);
				break;
			case 4:
				searchData.put("State", inputArray[i]);
				break;
			case 5:
				searchData.put("City", inputArray[i]);
				break;
			case 6:
				searchData.put("Zip_Code", inputArray[i]);
				break;
			case 7:
				searchData.put("Status", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		return searchData;
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, will remove *
	 * and Blanks columns and values
	 * 
	 */
	public HashMap<String, String> account_convertDataRemoveStar(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*") && inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("Account_Name", inputArray[i]);
					break;
				case 1:
					searchData.put("Role_Id", inputArray[i]);
					break;
				case 2:
					searchData.put("Role_Type", inputArray[i]);
					break;
				case 3:
					searchData.put("Address", inputArray[i]);
					break;
				case 4:
					searchData.put("State", inputArray[i]);
					break;
				case 5:
					searchData.put("City", inputArray[i]);
					break;
				case 6:
					searchData.put("Zip_Code", inputArray[i]);
					break;
				case 7:
					searchData.put("Status", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		return searchData;
	}

	/**
	 * This common function is used to search a contract
	 * 
	 * @param contract id on which contract needs to be searched
	 */
	public void searchContract(String contractId) {
		click("clickCancellationTab");
		click("clickMailservice");
		click("clickClear");
		type("typeContractId", contractId);
		click("searchContractButton");
	}

	/**
	 * This common function is used to go to Edit Classification List tab
	 * 
	 * 
	 */
	public void goToEditClassificationListTab() {
		try {
			click("editClassificationList");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This function is used to reinstate the contract
	 * 
	 * 
	 */
	public void processReinstateContract(String contractId) throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		processForAccountSearchForContractReinstated(contractId);
		//// click Reinstate button
		String value = getValue("ClickReinstatedBtnForCancel");
		if (value.equalsIgnoreCase("reinstate")) {
			//// fill Reinstate form with reason
			clickReinstateWithReason(new String[] { "Cancelled In Error" });
			waitForSomeTime(10);
		}
		click("contractExpander");
	}

	/**
	 * This common function is used to go to Reinstate With Reason
	 * 
	 * @return
	 * 
	 */
	public void clickReinstateWithReason(String[] reinstateReason) {
		click("ClickReinstatedBtnForCancel");
		WebElement we = null;
		for (String reinstate : reinstateReason) {
			switch (reinstate.toLowerCase()) {
			case "cancelled in error":
				we = findElementByXpath(
						"//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Cancelled in Error']");
				break;
			case "funding received":
				we = findElementByXpath(
						"//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Funding Received']");
				break;
			case "missing info received":
				we = findElementByXpath(
						"//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Missing Info Received']");
				break;
			case "correct form":
				we = findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Correct Form']");
				break;
			case "other":
				we = findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Other']");
				break;
			default:
				we = findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Other']");
				break;
			}
		}
		if (we.isSelected() == false)
			we.click();
		click("ClickReinstatedNxtBtn");
		waitForSomeTime(15);
		click("ClickReinstatedNxtBtn");
		waitForSomeTime(2);
		click("onHoldSendByNone");
		click("ClickReinstatedNxtBtn");
		removeErrorMessages();
	}

	/**
	 * This common function is used to go to Statements tab
	 *
	 * @return
	 *
	 */
	public void goToStatementsTab() {
		try {
			click("gotoStatementsTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to underwriting tab
	 * 
	 * @return
	 * 
	 */
	public void goToUnderWritingTab() {
		try {
			click("clickUnderWritingTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to New Business Form underwriting tab
	 * 
	 * @return
	 * 
	 */
	public void goToNewBusinessFormUnderWritingTab() {
		try {
			click("clickNewBusinessUnderWritingTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to expand contract
	 * 
	 * @return
	 * 
	 */
	public void contractExpander() {
		String att11 = "";
		String att21 = "";
		try {
			att11 = getAttributeValue("checkTab", "IsOffscreen");
		} catch (Exception e) {
			// TODO: handle exception
		}
		click("contractExpander");
		try {
			att21 = getAttributeValue("checkTab", "IsOffscreen");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (att11.equals(att21)) {
			click("contractExpander");
		}
	}

	/**
	 * This function is used to refresh remittance
	 * 
	 */
	public void refreshRemittance() throws Exception {
		click("refreshRemittance");
		waitForSomeTime(10);
		for (int i = 0; i < 1; i++) {
			try {
				click("clickRemittanceListTab");
			} catch (Exception e) {
				waitForSomeTime(10);
			}
		}
	}

	/**
	 * This common function is used to exoand contract
	 * 
	 * @return
	 * 
	 */
	public void remittanceExpander() {
		String att1 = "";
		String att2 = "";
		try {
			att1 = getAttributeValue("remittanceName", "BoundingRectangle");
		} catch (Exception e) {
			// TODO: handle exception
		}
		click("remittanceExpander");
		att2 = getAttributeValue("remittanceName", "BoundingRectangle");
		if (att1.equals(att2)) {
			click("remittanceExpander");
		}
	}

	/**
	 * This common function is used to go to underwriting tab
	 * 
	 * @return
	 * 
	 */
	public void goToCheckTab() {
		try {
			click("checkTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to search tab
	 * 
	 * @return
	 * 
	 */
	public void goToSearchTab() {
		try {
			click("clicksearchinBar");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go goToRemittanceList
	 * 
	 * 
	 */
	public void goToRemittanceList() {
		try {
			click("clickRemittanceListTab");
		} catch (Exception e) {
			// throw e;
		}
		for (int i = 0; i < 1; i++) {
			try {
				click("clickRemittanceListTab");
			} catch (Exception e) {
				waitForSomeTime(10);
			}
		}
	}

	/**
	 * This common function is used to go to cancellation tab
	 * 
	 * 
	 */
	public void goToCancellationTab() {
		try {
			click("clickCancellationTab");
		} catch (Exception e) {
			// throw e;
		}
	}

	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
	}

	/**
	 * This common function is used to go to pricing tab
	 * 
	 */
	public void goToPricingTab() {
		try {
			click("clickPricingTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to mail service tab
	 * 
	 * 
	 */
	public void goToMailServiceTab() {
		try {
			click("clickMailservice");
		} catch (Exception e) {
			throw e;
		}
	}

	public void goToComplianceTab() {
		try {
			click("clickComplianceModule");
		} catch (Exception e) {
			throw e;
		}
	}

	public void goToContractBuilderTab() {
		try {
			click("clickContractBuilder");
		} catch (Exception e) {
			throw e;
		}
	}

	public void goToPriceSheetGroupTab() {
		try {
			click("clickPriceSheetGroup");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to mail service tab
	 * 
	 * 
	 */
	public void goToAccountsTab() {
		try {
			click("clickAccountsTab");

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to mail service tab
	 * 
	 * try { click("clickAccountsTab"); } catch (Exception e) { // TODO: handle
	 * exception }
	 */
	public void goToAccountsSearchTab() {
		try {
			click("clickAccountsSearchTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to mail service tab
	 * 
	 * 
	 */
	public void goToAccountsRuleBuilderTab() {
		try {
			click("clickAccountRuleBuilder");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common accepts Date and convert the same in mm/dd/yyyy format after
	 * adding input days
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertNewDateFormat(String date) throws Exception {
		String newDate = "";
		try {
			// get only date
			System.out.println("date===" + date);
			int index = date.indexOf(" ");
			if (index > 0)
				date = date.substring(0, index);
			// parse string to date
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			// parse date to new MM/dd/yyyy format
			newDate = new SimpleDateFormat("dd-MM-yyyy").format(date1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}
		return newDate;
	}

	/*
	 * This function get the Date converted from mm/dd/yy to dd/mm/yy
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertNewDateForCompare(String date) throws Exception {
		String abc = "";
		try {
			//// get only date
			System.out.println("dateee==" + date);
			int index = date.indexOf(" ");
			if (index > 0)
				date = date.substring(0, index);
			//// format string to date
			SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = format1.parse(date);
			abc = format2.format(date1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}

		return abc;
	}

	/**
	 * This common function is used to go to Account Details tab
	 * 
	 * 
	 */
	public void goToAccountsDetailsTab() {
		try {
			click("clickOnAccountDetails");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to generate random string of length n
	 * 
	 */
	public String randomString(int n) {
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();

	}

	/**
	 * This common accepts Date and convert the same in mm/dd/yyyy format after
	 * adding input days
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertDate(String date, int noOfDays) throws Exception {
		String newDate = "";
		try {
			if (date.length() > 0) {
				//// get only date
				// date = date.substring(0, date.indexOf(" "));
				System.out.println("date1===" + date);
//			date = date.substring(0, date.indexOf(" "));
				System.out.println("date2===" + date);
				//// parse string to date
				Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
				//// parse date to new MM/dd/yyyy format
				String abc = new SimpleDateFormat("MM/dd/yyyy").format(date1);
				//// use calendar to add days
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				// Setting the date to the given date
				c.setTime(sdf.parse(abc));
				// Add noOfDays days to current date
				c.add(Calendar.DAY_OF_MONTH, noOfDays);
				// Date after adding the days to the current date
				newDate = sdf.format(c.getTime());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}

		return newDate;

	}

	/**
	 * This common accepts Date and convert the same in mm/dd/yyyy format after
	 * adding input days CancellationTC_25&26
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertDateCancellation(String date, int noOfDays) throws Exception {
		String newDate = "";
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			//// parse date to new MM/dd/yyyy format
			String abc = new SimpleDateFormat("MM/dd/yyyy").format(date1);
			//// use calendar to add days
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			// Setting the date to the given date
			c.setTime(sdf.parse(abc));
			// Add noOfDays days to current date
			c.add(Calendar.DAY_OF_MONTH, noOfDays);
			// Date after adding the days to the current date
			newDate = sdf.format(c.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}

		return newDate;

	}

///// Prepare test data needed for test case
	///// validatePremiumCalculationForMasterAndSubMasterPriceSheet
	public HashMap<String, String> prepareData(String[] inputData) {
		HashMap<String, String> premiumData = new HashMap<String, String>();
		//// Fetch PRICESHEETCODE, if exist
		if (inputData[0].length() > 0)
			premiumData.put("PRICESHEETCODE", inputData[0]);
		else
			premiumData.put("PRICESHEETCODE", "");
		//// Fetch ACTUALPRICESHEETCODE, if exist
		if (inputData[1].length() > 0)
			premiumData.put("DEALERID", inputData[1]);
		//// Fetch VIN, in case of Blank, use any hard code value
		if (inputData[9].length() > 0)
			premiumData.put("VIN", inputData[9]);
		else
			premiumData.put("VIN", "3N1BC11E79L484011");
		//// Fetch MAKE, in case of Blank, use any hard code value
		if (inputData[10].length() > 0)
			premiumData.put("MAKE", inputData[10]);
		else
			premiumData.put("MAKE", "Nissan");
		//// Fetch MODEL, in case of Blank, use any hard code value
		if (inputData[11].length() > 0)
			premiumData.put("MODEL", inputData[11]);
		else
			premiumData.put("MODEL", "Versa");
		//// Fetch YEAR, in case of Blank, use any hard code value
		if (inputData[12].length() > 0)
			premiumData.put("YEAR", inputData[12]);
		else
			premiumData.put("YEAR", "2018");
		//// Fetch MILEAGE, in case of Blank, use any hard code value
		if (inputData[13].length() > 0)
			premiumData.put("MILEAGE", inputData[13]);
		else
			premiumData.put("MILEAGE", "1000");
		//// Fetch VEHICLEPRICE, in case of Blank, use any hard code value
		if (inputData[14].length() > 0)
			premiumData.put("VEHICLEPRICE", inputData[14]);
		else
			premiumData.put("VEHICLEPRICE", "1000");
		//// Fetch TERM, if exist
		if (inputData[15].length() > 0)
			premiumData.put("TERM", inputData[15]);
		//// Fetch COVERAGE, if exist
		if (inputData[16].length() > 0)
			premiumData.put("COVERAGE", inputData[16]);
		//// Fetch MIELAGEBAND, if exist
		if (inputData[17].length() > 0)
			premiumData.put("MIELAGEBAND", inputData[17]);
		//// Fetch CLASS, if exist
		if (inputData[18].length() > 0)
			premiumData.put("CLASS", inputData[18]);
		//// Fetch SERVICEDATE, if exist
		if (inputData[19].length() > 0)
			premiumData.put("SERVICEDATE", inputData[19]);
		//// Fetch SURCHARGES, in case of Blank, use N
		if (inputData[4].length() > 0)
			premiumData.put("SURCHARGES", inputData[4]);
		else
			premiumData.put("SURCHARGES", "N");
		//// Fetch DEDUCTIBLE, in case of Blank, use N
		if (inputData[6].length() > 0)
			premiumData.put("DEDUCTIBLE", inputData[6]);
		else
			premiumData.put("DEDUCTIBLE", "N");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[7].length() > 0)
			premiumData.put("AGENTPLANTYPE", inputData[7]);
		else
			premiumData.put("AGENTPLANTYPE", "ALLPLANS");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[8].length() > 0)
			premiumData.put("DEALERPLANTYPE", inputData[8]);
		else
			premiumData.put("DEALERPLANTYPE", "ALLPLANS");
		//// Fetch OPTIONS, in case of Blank, use N
		if (inputData[5].length() > 0)
			premiumData.put("OPTIONS", inputData[5]);
		else
			premiumData.put("OPTIONS", "N");
		//// Fetch DEALEREXCEPTION, in case of Blank, use N
		if (inputData[2].length() > 0)
			premiumData.put("DEALEREXCEPTION", inputData[2]);
		else
			premiumData.put("DEALEREXCEPTION", "N");
		//// Fetch AGENTEXCEPTION, in case of Blank, use N
		if (inputData[3].length() > 0)
			premiumData.put("AGENTEXCEPTION", inputData[3]);
		else
			premiumData.put("AGENTEXCEPTION", "N");

		return premiumData;
	}

	/**
	 * This common function is used to go to mail service tab
	 * 
	 * 
	 */
	public void goToRemittanceSummary() {
		try {
			click("clickOnRemittanceSummary");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * dataProvider function is used to read data provider excel
	 * 
	 * @param excelFileName : excel file name
	 * @param sheetName     : sheet name in respective excel file
	 */
	public static Object[][] dataProvider(String excelFileName, String sheetName) {
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		if (Arrays.stream(Variables.somke).anyMatch("smoke"::equalsIgnoreCase)) {
			dir = currentDir + "\\Repository\\Smoke\\";
		}
		if (Arrays.stream(Variables.somke).anyMatch("regression"::equalsIgnoreCase)) {
			dir = currentDir + "\\Repository\\Regression\\";
		}
		//// Excel sheet file name
		String fileName = excelFileName + ".xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, sheetName);
		return arrayObject;
	}

	/**
	 * dataProvider function is used to return top data from database
	 * 
	 * @param query : dbQuery
	 * @return HashMap<String, String>: Key value pair where key is actual column
	 *         name and value is column data
	 */
	public static HashMap<String, String> getTopRowDataFromDatabase(String query) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * dataProvider function is used to return top data from database
	 * 
	 * @param query : dbQuery
	 * @return HashMap<Integer, HashMap<String, String>>: Key value pair key where
	 *         is auto incremented and value is hashmap where key is actual column
	 *         name and value is column data
	 */
	public static HashMap<Integer, HashMap<String, String>> getAllDataFromDatabase(String query) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnAllData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * This common function is used to go to Classification Tab
	 * 
	 * 
	 */
	public void goToClassficationList() {
		try {
			click("clickOnClassificationTab");
		} catch (Exception e) {
			throw e;
		}
	}

	public void searchRemittance(String remittName) throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		// refreshRemittance();
		/*
		 * //// Type RemittanceName type("typeToSearchRemittance", remittName); ////
		 * expand remittance to get contracts
		 * 
		 * click("expandRemittance");
		 */
		type("typeToSearchRemittance", remittName);
		waitForSomeTime(5);
		// click("expandRemittance");
		List<WebElement> contractNumber = listOfElements("expandRemittance");
		for (WebElement webElement : contractNumber) {
			try {
				webElement.click();
				break;
			} catch (Exception e) {
				continue;
			}
		}
	}

	/**
	 * This function is used to lock and view contract post contract search
	 * 
	 */
	public void lockAndViewContract() throws Exception {
		click("viewContract");
		try {
			//// click yes to lock remittance
			click("lockContractYesButton");
		} catch (Exception e) {
			// do nothing
		}
		click("loadRemittance");
		removeErrorMessages();
		try {
			click("VINDuplicate");
		} catch (Exception e) {
			// TODO: handle exception
		}
		removeErrorMessages();
		//// click contract expander
		contractExpander();
	}

	/**
	 * This function is used to lock and view contract post contract search
	 * 
	 */
	public void AdjustForm() throws Exception {

		waitForSomeTime(15);
		click("AdjustCategoryUpgrade");
		click("clickNextBtnOnContractAdjustment");
		waitForSomeTime(15);
		click("FinishBtnOnContractAdjustment");
		waitForSomeTime(15);
		removeErrorMessages();
		for (int i = 0; i < 5; i++) {
			try {
				click("contractExpander");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		waitForSomeTime(3);
		System.out.println("togglestate===" + getAttributeValue("AdjustCategoryUpgrade", "Toggle.ToggleState"));
		if (getAttributeValue("AdjustCategoryUpgrade", "Toggle.ToggleState").equals("0")) {
			System.out.println("togglestate===" + getAttributeValue("AdjustCategoryUpgrade", "ToggleState"));
			click("AdjustCategoryUpgrade");
		}
		click("clickNextBtnOnContractAdjustment");
		click("FinishBtnOnContractAdjustment");
		click("clickOK");
		waitForSomeTime(30);
		removeErrorMessages();
		for (int i = 0; i < 9; i++) {
			try {
				click("contractExpander");
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * This function is used to lock and view contract post contract search
	 * 
	 */
	public void AdjustFormNew() throws Exception {
		waitForSomeTime(3);
		if (getAttributeValue("AdjustCategoryUpgrade", "Toggle.ToggleState").equals("0")) {
			click("AdjustCategoryUpgrade");
		}
		click("clickNextBtnOnContractAdjustment");
		click("FinishBtnOnContractAdjustment");
		click("clickOK");
		waitForSomeTime(3);
		try {
			click("contractExpander");
		} catch (Exception e) {
			// Do Nothing
		}

	}

	/*
	 * This function change the key name as aliasing for DB query
	 */
	public String convertKeys(String key) throws Exception {
		String myKey = "";
		switch (key) {
		case "CERT":
			myKey = "asd.cert";
			break;
		case "CUSTOMER_FIRST":
			myKey = "asd.CUSTOMER_FIRST";
			break;
		case "CUSTOMER_LAST":
			myKey = "asd.CUSTOMER_LAST";
			break;
		case "VIN":
			myKey = "asd.VIN";
			break;
		case "Status":
			myKey = "stat.NAME";
			break;
		case "State":
			myKey = "asd.STATE";
			break;
		case "City":
			myKey = "asd.CITY";
			break;
		case "Phone":
			myKey = "asd.PHONE";
			break;
		case "Program_Code":
			myKey = "asd.PROGRAM_CODE";
			break;
		case "Primary_Payee_ID":
			myKey = "account.ROLE_IDENTIFIER";
			break;
		case "Primary_Seller_Name":
			myKey = "account.NAME";
			break;
		case "Primary_Seller_ID":
			myKey = "account.ROLE_IDENTIFIER";
			break;
		case "Primary_Seller_Type":
			myKey = "accType.ROLE_NAME";
			break;
		case "From_Sale_Date":
			myKey = "asd.SALE_DATE";
			break;
		case "To_Sale_Date":
			myKey = "asd.SALE_DATE";
			break;
		case "Secondary_Seller_Name":
			myKey = "account2.Name";
			break;
		case "Secondary_Seller_ID":
			myKey = "account2.ROLE_IDENTIFIER";
			break;
		case "Secondary_Seller_Type":
			myKey = "accType3.ROLE_NAME";
			break;
		case "From_Trans_Date":
			myKey = "asd.TRANS_DATE";
			break;
		case "To_Trans_Date":
			myKey = "asd.TRANS_DATE";
			break;
		case "Sub_Status":
			myKey = "sub.name";
			break;
		case "From_Post_Period":
			myKey = "asd.Post_Period ";
			break;
		case "To_Post_Period":
			myKey = "asd.Post_Period ";
			break;
		default:
			//// do nothing
		}
		return myKey;
	}

	/**
	 * This function is used to land to search contract with pending state
	 * 
	 * @return
	 * 
	 */
	public void searchContractwithPendingState(String remittName, String fileName) throws Exception {
		//// Type RemittanceName
		searchRemittance(remittName);
		goToRemittanceList();
		//// type filename
		type("typeContract", fileName);
		//// click view contract
	}

	/**
	 * This function is used to land to search contract which are added via external
	 * contract screen
	 * 
	 * @return
	 * 
	 */
	public void searchContractAddedViaExternalContract(String remittName, String fileName) throws Exception {
		//// Type RemittanceName
		searchRemittance(remittName);
		//// type filename
		type("typeContract", fileName);
		//// click view contract
	}

	/**
	 * This common function is used to go to onHoldContract tab
	 * 
	 * @return
	 * 
	 */
	public void goToHoldContactTab() {
		try {
			click("clickonHoldContractsTab");
			waitForSomeTime(6);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to onToFindContract tab
	 * 
	 * @return
	 * 
	 */
	public void goToFindContractTab() {
		try {
			click("findcontracts_tab");
			click("clearonFindOCnt");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to onToFindContract tab
	 * 
	 * @return
	 * @throws IOException
	 * 
	 */
	public void copyFilesWorkingRemittance() throws IOException {
		File sourceFolder = new File("\\\\webtest\\QA_SHARE\\Ocean\\Working Remittance");
		File targetFolder = new File("\\\\webtest\\QA_SHARE\\Ocean\\Working Remittance\\Processed");
		String files[] = sourceFolder.list();
		for (String file : files) {
			File srcFile = new File(sourceFolder, file);
			File destFile = new File(targetFolder, file);
			if (srcFile.toString().contains(".pdf")) {
				try {
					FileUtils.copyFile(srcFile, destFile, true);
				} catch (Exception e) {
					continue;
					// TODO: handle exception
				}
			}
		}
	}

///// Prepare test data needed for test case for lender as primary account
	public HashMap<String, String> prepareDataForLenderAsPrimaryAccount(String[] inputData) {
		HashMap<String, String> premiumData = new HashMap<String, String>();
		//// Fetch PRICESHEETCODE, if exist
		if (inputData[0].length() > 0)
			premiumData.put("PRICESHEETCODE", inputData[0]);
		else
			premiumData.put("PRICESHEETCODE", "");
		//// Fetch ACTUALPRICESHEETCODE, if exist
		if (inputData[1].length() > 0)
			premiumData.put("LENDERID", inputData[1]);
		//// Fetch VIN, in case of Blank, use any hard code value
		if (inputData[9].length() > 0)
			premiumData.put("VIN", inputData[9]);
		else
			premiumData.put("VIN", "WBANE52010CK67774");
		//// Fetch MAKE, in case of Blank, use any hard code value
		if (inputData[10].length() > 0)
			premiumData.put("MAKE", inputData[10]);
		else
			premiumData.put("MAKE", "HONDA");
		//// Fetch MODEL, in case of Blank, use any hard code value
		if (inputData[11].length() > 0)
			premiumData.put("MODEL", inputData[11]);
		else
			premiumData.put("MODEL", "Accord");
		//// Fetch YEAR, in case of Blank, use any hard code value
		if (inputData[12].length() > 0)
			premiumData.put("YEAR", inputData[12]);
		else
			premiumData.put("YEAR", "2013");
		//// Fetch MILEAGE, in case of Blank, use any hard code value
		if (inputData[13].length() > 0)
			premiumData.put("MILEAGE", inputData[13]);
		else
			premiumData.put("MILEAGE", "1000");
		//// Fetch VEHICLEPRICE, in case of Blank, use any hard code value
		if (inputData[14].length() > 0)
			premiumData.put("VEHICLEPRICE", inputData[14]);
		else
			premiumData.put("VEHICLEPRICE", "1000");
		//// Fetch TERM, if exist
		if (inputData[15].length() > 0)
			premiumData.put("TERM", inputData[15]);
		//// Fetch COVERAGE, if exist
		if (inputData[16].length() > 0)
			premiumData.put("COVERAGE", inputData[16]);
		//// Fetch MIELAGEBAND, if exist
		if (inputData[17].length() > 0)
			premiumData.put("MIELAGEBAND", inputData[17]);
		//// Fetch CLASS, if exist
		if (inputData[18].length() > 0)
			premiumData.put("CLASS", inputData[18]);
		//// Fetch SERVICEDATE, if exist
		if (inputData[19].length() > 0)
			premiumData.put("SERVICEDATE", inputData[19]);
		//// Fetch SURCHARGES, in case of Blank, use N
		if (inputData[4].length() > 0)
			premiumData.put("SURCHARGES", inputData[4]);
		else
			premiumData.put("SURCHARGES", "N");
		//// Fetch DEDUCTIBLE, in case of Blank, use N
		if (inputData[6].length() > 0)
			premiumData.put("DEDUCTIBLE", inputData[6]);
		else
			premiumData.put("DEDUCTIBLE", "N");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[7].length() > 0)
			premiumData.put("LENDERPLANTYPE", inputData[7]);
		else
			premiumData.put("LENDERPLANTYPE", "ALLPLANS");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[8].length() > 0)
			premiumData.put("DEALERPLANTYPE", inputData[8]);
		else
			premiumData.put("DEALERPLANTYPE", "ALLPLANS");
		//// Fetch OPTIONS, in case of Blank, use N
		if (inputData[5].length() > 0)
			premiumData.put("OPTIONS", inputData[5]);
		else
			premiumData.put("OPTIONS", "N");
		//// Fetch DEALEREXCEPTION, in case of Blank, use N
		if (inputData[2].length() > 0)
			premiumData.put("DEALEREXCEPTION", inputData[2]);
		else
			premiumData.put("DEALEREXCEPTION", "N");
		//// Fetch AGENTEXCEPTION, in case of Blank, use N
		if (inputData[3].length() > 0)
			premiumData.put("LENDEREXCEPTION", inputData[3]);
		else
			premiumData.put("LENDEREXCEPTION", "N");

		return premiumData;
	}

	/**
	 * This function is used enter mandatory fields in contract adjustment for
	 * account search
	 *
	 */
	public void processForAccountSearchForContractAdjustment(String contractID) {
		type("contractNoInFindContract", contractID);
		click("searchFindContractBtn");
		for (int i = 0; i < 5; i++) {
			try {
				click("AdjustContractBtn");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		removeErrorMessages();
		try {
			click("VINDuplicate");
		} catch (Exception e) {
			// TODO: handle exception
		}
		removeErrorMessages();
		contractExpander();
	}

	public boolean validateVinNumberAvailability() {
		// Passing Static VIN number
		type("vinNumber", "1HGEJ8145WL012057");
		click("vinNumberSearch");
		click("vinSearchButton");
		waitForSomeTime(5);
		try {
			click("VINDuplicate");

		} catch (Exception e) {

		}
		try {
			click("clickOK");

		} catch (Exception e) {

		}
		String vinNumberText = getValue("vinNumberSearchAvailability");
		takeScreenshot();
		if (vinNumberText.isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * This function is used enter mandatory fields in contract underwritten from on
	 * hold for account search
	 *
	 */
	public void processForAccountSearchForContractUnderWritten(String contractID) {
		type("certFilterOnHoldContract", contractID);

		waitForSomeTime(20);
		for (int i = 0; i < 10; i++) {
			try {
				click("onHoldContractBtn");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		removeErrorMessages();
		contractExpander();
	}

	/**
	 * This function is used enter mandatory fields in contract adjustment for
	 * account search
	 *
	 */
	public void processForAccountSearchForTransfer(String contractID) {
		type("contractNoInFindContract", contractID);
		click("searchFindContractBtn");
		for (int i = 0; i < 5; i++) {
			try {
				click("clickTransferContract");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		removeErrorMessages();
		contractExpander();
	}

	/**
	 * This function is used enter mandatory fields in contract adjustment for
	 * account search
	 * 
	 * @throws Exception
	 *
	 */
	public void typeTransferFeeandTransfer(String Fees) throws Exception {
		try {
			click("scrollContractsListDown");
		} catch (Exception e) {
			/// do nothing
		}
		try {
			click("scrollContractsListDown");
		} catch (Exception e) {
			/// do nothing
		}
		//// enter check number, fees and date
		type("enterTransferFeeCheckNumber", randomString(3));
		type("enterTransferFeeCheckFee", Fees);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(1);
		type("enterTransferFeeCheckDate", dtf.format(localDate).toString());
		contractScrollToTop();
		click("transferContractOnTransfer");
		removeErrorMessages();
		fillTransferForm();
		removeErrorMessages();
	}

	public void fillTransferForm() {
		click("transferContractNext");
		click("onHoldSendByNone");
		click("transferContractNext");
		waitForSomeTime(10);
		click("transferContractNext");
	}

	/**
	 * This function is used enter mandatory fields in contract adjustment for
	 * account search
	 *
	 */
	public void processForAccountSearchForContractReinstated(String contractID) {
		type("contractNoInFindContract", contractID);
		click("searchFindContractBtn");
		for (int i = 0; i < 5; i++) {
			try {
				click("ReinstatedBtn");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		removeErrorMessages();
		contractExpander();
	}

	public void removeErrorMessages() {
		for (int i = 0; i < 4; i++) {
			try {
				click("clickOK");
			} catch (Exception w) {
				break;
				//// do nothing
			}
		}
	}

	/**
	 * This common function is used to go to Business Processor tab
	 * 
	 * 
	 */
	public void goToBusinessProcessorTab() {
		try {
			// windowsDriver.findElement(By.xpath("clickBusinessProcessor")).click();
			click("clickBusinessProcessor");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> businessProcessor_Search_appendSearchData(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("From_Date", inputArray[i]);
				break;
			case 1:
				searchData.put("To_Date", inputArray[i]);
				break;
			case 2:
				searchData.put("CreatedBy", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		return searchData;
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, and Blanks
	 * columns and values
	 * 
	 */
	public HashMap<String, String> businessProcessor_Search_convertDataRemoveStar(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*")) {
				switch (i) {
				case 0:
					try {
						if (inputArray[i].length() > 0) {
							Date fromdate1 = new SimpleDateFormat("MM/dd/yyyy").parse(inputArray[i]);
							String fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fromdate1);
							searchData.put("From_Date", fromdate);
						}
					} catch (Exception e) {
					}
					break;
				case 1:
					try {
						if (inputArray[i].length() > 0) {
							Date todate1 = new SimpleDateFormat("MM/dd/yyyy").parse(inputArray[i]);
							String todate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(todate1);
							searchData.put("To_Date", todate);
						}
					} catch (Exception e) {
					}
					break;
				case 2:
					searchData.put("CreatedBy", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}

			}
		}
		return searchData;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hash map with input parameters
	 * 
	 */
	public void searchBusinessProcessorGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = ((String) mapElement.getValue()).trim();
			switch (searchParamater) {
			case "From_Date":
				if (valueToInput != "" && valueToInput != null)
					inputTextBox("typeFromDate", valueToInput);
				break;

			case "To_Date":
				if (valueToInput != "" && valueToInput != null)
					inputTextBox("typeToDate", valueToInput);
				break;

			case "CreatedBy":

				String[] values = valueToInput.split(",");
				for (String value : values) {
					if (value != "" || value != null) {
						click("clickCreatedBy");
						waitForSomeTime(7);
						clickOnDropDownItem(value);
						waitForSomeTime(10);
						click("clickCreatedBy");
					}
				}
				break;
			}
		}
		click("scrollContractsListUp");
		///// click search button
		click("clickBusinessProcessorSearchButton");
	}

	/**
	 * This common function is used to go goToChecksTab
	 * 
	 * 
	 */
	public void goToChecksTab() {
		try {
			click("clickChecksTab");
		} catch (Exception e) {
			// throw e;
		}
	}

	/**
	 * This common function is used to genrate random integer
	 * 
	 */
	public String randomInteger(int n) {
		// chose a Character random from this String
		String NumericString = "0123456789";
		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (NumericString.length() * Math.random());
			// add Character one by one in end of sb
			sb.append(NumericString.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_Web_ContractSearch_appendSearchData(String[] inputArray)
			throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("s.CERT", inputArray[i]);
				break;
			case 1:
				searchData.put("s.FIRST_NAME", inputArray[i]);
				break;
			case 2:
				searchData.put("s.LAST_NAME", inputArray[i]);
				break;
			case 3:
				searchData.put("s.VIN", inputArray[i]);
				break;
			case 4:
				searchData.put("s.Status", inputArray[i]);
				break;
			case 5:
				searchData.put("State", inputArray[i]);
				break;
			case 6:
				searchData.put("City", inputArray[i]);
				break;
			case 7:
				searchData.put("s.Phone", inputArray[i]);
				break;
			case 8:
				searchData.put("s.Program_Code", inputArray[i]);
				break;
			case 9:
				searchData.put("Payee_ID", inputArray[i]);
				break;
			case 10:
				searchData.put("Primary_Seller_Name", inputArray[i]);
				break;
			case 11:
				searchData.put("Primary_Seller_ID", inputArray[i]);
				break;
			case 12:
				searchData.put("Primary_Seller_Type", inputArray[i]);
				break;
			case 13:
				searchData.put("From_Sale_Date", inputArray[i]);
				break;
			case 14:
				searchData.put("To_Sale_Date", inputArray[i]);
				break;
			case 15:
				searchData.put("Secondary_Seller_Name", inputArray[i]);
				break;
			case 16:
				searchData.put("Secondary_Seller_ID", inputArray[i]);
				break;
			case 17:
				searchData.put("Secondary_Seller_Type", inputArray[i]);
				break;
			case 18:
				searchData.put("From_Remit_Date", inputArray[i]);
				break;
			case 19:
				searchData.put("To_Remit_Date", inputArray[i]);
				break;
			case 20:
				searchData.put("From_Create_Date", inputArray[i]);
				break;
			case 21:
				searchData.put("To_Create_Date", inputArray[i]);
				break;
			case 22:
				searchData.put("s.Remit_no", inputArray[i]);
				break;
			case 23:
				searchData.put("CFlag", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}

		if (!searchData.get("From_Sale_Date").isEmpty() || !searchData.get("To_Sale_Date").isEmpty()) {

			HashMap<String, String> searchParameter = calculateSaleDate(searchData);
			return searchParameter;
		}

		if (!searchData.get("From_Create_Date").equals("") || !searchData.get("To_Create_Date").equals("")) {
			HashMap<String, String> searchParameter = calculateCreateDate(searchData);
			return searchParameter;
		}
		if (!searchData.get("From_Remit_Date").isEmpty() || !searchData.get("To_Remit_Date").isEmpty()) {

			HashMap<String, String> searchParameter = calculateRemitDate(searchData);
			return searchParameter;
		}
		return searchData;
	}

	public HashMap<String, String> calculateSaleDate(HashMap<String, String> searchData) throws Exception {

		HashMap<String, String> searchParameter = searchData;

		String value1 = searchParameter.get("From_Sale_Date");
		String value2 = searchParameter.get("To_Sale_Date");

		if (value1 == "*" && value2 == "") {
			searchParameter.put("PURCHASE_DATE", "*");
			searchParameter.put("From_Sale_Date", "");
		}
		if (value1 == "" && value2 == "*") {
			searchParameter.put("PURCHASE_DATE", "*");
			searchParameter.put("To_Sale_Date", "");
		}

		if (value1 == "*" && value2 == "*") {
			searchParameter.put("PURCHASE_DATE", "*");
			searchParameter.put("To_Sale_Date", "");
			searchParameter.put("From_Sale_Date", "");
		}

		if ((value1 != "*" && value1 != "") && (value2 == "" || value2 == null || value2 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date fSaleDate = sdf.parse(value1);
			Calendar c = Calendar.getInstance();
			c.setTime(fSaleDate); // Now use today date.
			c.add(Calendar.DATE, 1); // Adding 5 days
			String tSaleDate = sdf.format(c.getTime());
			searchParameter.put("To_Sale_Date", tSaleDate);

		}

		if ((value2 != "*" && value2 != "") && (value1 == "" || value1 == null || value1 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date tSaleDate = sdf.parse(value2);
			Calendar c = Calendar.getInstance();
			c.setTime(tSaleDate); // Now use today date.
			c.add(Calendar.DATE, -1); // Adding 5 days
			String fSaleDate = sdf.format(c.getTime());
			searchParameter.put("From_Sale_Date", fSaleDate);
		}

		return searchParameter;
	}

	public HashMap<String, String> calculateCreateDate(HashMap<String, String> searchData) throws Exception {

		HashMap<String, String> searchParameter = searchData;

		String value1 = searchParameter.get("From_Create_Date");
		String value2 = searchParameter.get("To_Create_Date");

		if (value1.equals("*") && value2.equals("")) {
			searchParameter.put("DATE_CREATED", "*");
			searchParameter.put("From_Create_Date", "");
		}
		if (value1.equals("") && value2.equals("*")) {
			searchParameter.put("DATE_CREATED", "*");
			searchParameter.put("To_Create_Date", "");
		}

		if (value1.equals("*") && value2.equals("*")) {
			searchParameter.put("DATE_CREATED", "*");
			searchParameter.put("To_Create_Date", "");
			searchParameter.put("From_Create_Date", "");
		}

		if ((value1 != "*" && value1 != "") && (value2 == "" || value2 == null || value2 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date fSaleDate = sdf.parse(value1);
			Calendar c = Calendar.getInstance();
			c.setTime(fSaleDate); // Now use today date.
			c.add(Calendar.DATE, 1); // Adding 5 days
			String tSaleDate = sdf.format(c.getTime());
			searchParameter.put("To_Create_Date", tSaleDate);

		}

		if ((value2 != "*" && value2 != "") && (value1 == "" || value1 == null || value1 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date tSaleDate = sdf.parse(value2);
			Calendar c = Calendar.getInstance();
			c.setTime(tSaleDate); // Now use today date.
			c.add(Calendar.DATE, -1); // Adding 5 days
			String fSaleDate = sdf.format(c.getTime());
			searchParameter.put("From_Create_Date", fSaleDate);
		}

		return searchParameter;
	}

	public HashMap<String, String> calculateRemitDate(HashMap<String, String> searchData) throws Exception {

		HashMap<String, String> searchParameter = searchData;

		String value1 = searchParameter.get("From_Remit_Date");
		String value2 = searchParameter.get("To_Remit_Date");

		if (value1.equals("*") && value2.equals("")) {
			searchParameter.put("Remit_Date", "*");
			searchParameter.put("From_Remit_Date", "");
		}
		if (value1.equals("") && value2.equals("*")) {
			searchParameter.put("Remit_Date", "*");
			searchParameter.put("To_Remit_Date", "");
		}

		if (value1.equals("*") && value2.equals("*")) {
			searchParameter.put("Remit_Date", "*");
			searchParameter.put("To_Remit_Date", "");
			searchParameter.put("From_Remit_Date", "");
		}

		if ((value1 != "*" && value1 != "") && (value2 == "" || value2 == null || value2 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date fRemitDate = sdf.parse(value1);
			Calendar c = Calendar.getInstance();
			c.setTime(fRemitDate); // Now use today date.
			c.add(Calendar.DATE, 1); // Adding 5 days
			String tRemitDate = sdf.format(c.getTime());
			searchParameter.put("To_Remit_Date", tRemitDate);

		}

		if ((value2 != "*" && value2 != "") && (value1 == "" || value1 == null || value1 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date tRemitDate = sdf.parse(value2);
			Calendar c = Calendar.getInstance();
			c.setTime(tRemitDate); // Now use today date.
			c.add(Calendar.DATE, -1); // Adding 5 days
			String fRemitDate = sdf.format(c.getTime());
			searchParameter.put("From_Remit_Date", fRemitDate);
		}

		return searchParameter;
	}

	/// This function convert date from format ---dd-MM-yyyy to yyyy-MM-dd
	public String ConverDtFormat(String value1) throws Exception {
		String Format_dt = value1;
		DateFormat parser = new SimpleDateFormat("dd-MM-yyyy");
		Date date = (Date) parser.parse(Format_dt);

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newFormatDate = formatter.format(date);
		return newFormatDate;
	}

	/// This function convert date from format --- yyyy-MM-dd to MM-dd-yyyy
	public String ConverDatetFormat(String value1) throws Exception {
		String Format_dt = value1;
		DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date) parser.parse(Format_dt);

		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String newFormatDate = formatter.format(date);
		return newFormatDate;

	}

	/// This function convert date from format --- yyyy-MM-dd to MM-dd-yyyy
	public String ConverDatetFormatUnderWriting(String value1) throws Exception {
		String Format_dt = value1;
		DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
		Date date = (Date) parser.parse(Format_dt);

		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String newFormatDate = formatter.format(date);
		return newFormatDate;

	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, will remove *
	 * and Blanks columns and values for WebContract
	 * 
	 * @throws Exception
	 * 
	 */

	public HashMap<String, String> cancellation_Web_Contract_Search_convertDataRemoveStar(String[] inputArray)
			throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*") && inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("CERT", inputArray[i]);
					break;
				case 1:
					searchData.put("FIRST_NAME", inputArray[i]);
					break;
				case 2:
					searchData.put("LAST_NAME", inputArray[i]);
					break;
				case 3:
					searchData.put("VIN", inputArray[i]);
					break;
				case 4:
					searchData.put("Status", inputArray[i]);
					break;
				case 5:
					searchData.put("State", inputArray[i]);
					break;
				case 6:
					searchData.put("City", inputArray[i]);
					break;
				case 7:
					searchData.put("Phone", inputArray[i]);
					break;
				case 8:
					searchData.put("Program_Code", inputArray[i]);
					break;
				case 9:
					searchData.put("Payee_ID", inputArray[i]);
					break;
				case 10:
					searchData.put("Primary_Seller_Name", inputArray[i]);
					break;
				case 11:
					searchData.put("Primary_Seller_ID", inputArray[i]);
					break;
				case 12:
					searchData.put("Primary_Seller_Type", inputArray[i]);
					break;
				case 13:
					searchData.put("From_Sale_Date", inputArray[i]);
					break;
				case 14:
					searchData.put("To_Sale_Date", inputArray[i]);
					break;
				case 15:
					searchData.put("Secondary_Seller_Name", inputArray[i]);
					break;
				case 16:
					searchData.put("Secondary_Seller_ID", inputArray[i]);
					break;
				case 17:
					searchData.put("Secondary_Seller_Type", inputArray[i]);
					break;
				case 18:
					searchData.put("From_Remit_Date", inputArray[i]);
					break;
				case 19:
					searchData.put("To_Remit_Date", inputArray[i]);
					break;
				case 20:
					searchData.put("From_Create_Date", inputArray[i]);
					break;
				case 21:
					searchData.put("To_Create_Date", inputArray[i]);
					break;
				case 22:
					searchData.put("Remit_no", inputArray[i]);
					break;
				case 23:
					searchData.put("CFlag", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		if (searchData.containsKey("From_Sale_Date") || searchData.containsKey("To_Sale_Date")) {
			HashMap<String, String> searchParameter = calculateSaleDate(searchData);
			return searchParameter;
		}
		if (searchData.containsKey("From_Create_Date") || searchData.containsKey("To_Create_Date")) {
			HashMap<String, String> searchParameter = calculateCreateDate(searchData);
			return searchParameter;
		}
		if (searchData.containsKey("From_Remit_Date") || searchData.containsKey("To_Remit_Date")) {
			HashMap<String, String> searchParameter = calculateRemitDate(searchData);
			return searchParameter;
		}
		return searchData;
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * for Transaction_HistorySearch
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_Transaction_HistorySearch_appendSearchData(String[] inputArray)
			throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();

		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("atd.CERT", inputArray[i]);
				break;
			case 1:
				searchData.put("atd.ADJTYPE", inputArray[i]);
				break;
			case 2:
				searchData.put("atd.ADJTYPE_DET", inputArray[i]);
				break;
			case 3:
				searchData.put("atd.ADJTYPE_CAT", inputArray[i]);
				break;
			case 4:
				searchData.put("accType.ROLE_NAME", inputArray[i]);
				break;
			case 5:
				searchData.put("a.Name", inputArray[i]);
				break;
			case 6:
				searchData.put("a.Role_Identifier", inputArray[i]);
				break;
			case 7:
				searchData.put("asd.Program_Code", inputArray[i]);
				break;
			case 8:
				searchData.put("atd.Batch_No", inputArray[i]);
				break;
			case 9:
				searchData.put("atd.Post_Period", inputArray[i]);
				break;
			case 10:
				searchData.put("atd.Check_No", inputArray[i]);
				break;
			case 11:
				searchData.put("atd.Check_Amount", inputArray[i]);
				break;
			case 12:
				searchData.put("atd.DBCR_AMT", inputArray[i]);
				break;
			case 13:
				searchData.put("atd.Dealer_Paid", inputArray[i]);
				break;
			case 14:
				searchData.put("AFDB.Agent1_ID", inputArray[i]);
				break;
			case 15:
				searchData.put("From_Trans_Date", inputArray[i]);
				break;
			case 16:
				searchData.put("To_Trans_Date", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}

		if (!(searchData.get("From_Trans_Date") == "" || searchData.get("From_Trans_Date") == null)
				|| !(searchData.get("To_Trans_Date") == "" || searchData.get("To_Trans_Date") == null)) {

			HashMap<String, String> searchParameter = calculateTransDate(searchData);
			return searchParameter;
		}

		return searchData;
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, will remove *
	 * and Blanks columns and values for Transaction_HistorySearch
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_Transaction_HistorySearch_convertDataRemoveStar(String[] inputArray)
			throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*") && inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("CERT", inputArray[i]);
					break;
				case 1:
					searchData.put("ADJTYPE", inputArray[i]);
					break;
				case 2:
					searchData.put("ADJTYPE_DET", inputArray[i]);
					break;
				case 3:
					searchData.put("ADJTYPE_CAT", inputArray[i]);
					break;
				case 4:
					searchData.put("ROLE_NAME", inputArray[i]);
					break;
				case 5:
					searchData.put("Name", inputArray[i]);
					break;
				case 6:
					searchData.put("Role_Identifier", inputArray[i]);
					break;
				case 7:
					searchData.put("Program_Code", inputArray[i]);
					break;
				case 8:
					searchData.put("Batch_No", inputArray[i]);
					break;
				case 9:
					searchData.put("Post_Period", inputArray[i]);
					break;
				case 10:
					searchData.put("Check_No", inputArray[i]);
					break;
				case 11:
					searchData.put("DBCR_AMT", inputArray[i]);
					break;
				case 12:
					searchData.put("Dealer_Paid", inputArray[i]);
					break;
				case 13:
					searchData.put("Agent1_ID", inputArray[i]);
					break;
				case 14:
					searchData.put("From_Trans_Date", inputArray[i]);
					break;
				case 15:
					searchData.put("To_Trans_Date", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		if (searchData.containsKey("From_Trans_Date") || searchData.containsKey("To_Trans_Date")) {
			HashMap<String, String> searchParameter = calculateTransDate(searchData);
			return searchParameter;
		}

		return searchData;
	}

	public HashMap<String, String> calculateTransDate(HashMap<String, String> searchData) throws Exception {

		HashMap<String, String> searchParameter = searchData;

		String value1 = searchParameter.get("From_Trans_Date");
		String value2 = searchParameter.get("To_Trans_Date");

		if (value1 == "*" && value2 == "") {
			searchParameter.put("TRANS_DATE", "*");
			searchParameter.put("From_Trans_Date", "");
		}
		if (value1 == "" && value2 == "*") {
			searchParameter.put("TRANS_DATE", "*");
			searchParameter.put("To_Trans_Date", "");
		}

		if (value1 == "*" && value2 == "*") {
			searchParameter.put("TRANS_DATE", "*");
			searchParameter.put("To_Trans_Date", "");
			searchParameter.put("From_Trans_Date", "");
		}

		if ((value1 != "*" && !value1.isEmpty()) && (value2 == "" || value2 == null || value2 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date fTransDate = sdf.parse(value1);
			Calendar c = Calendar.getInstance();
			c.setTime(fTransDate); // Now use today date.
			c.add(Calendar.DATE, 1); // Adding 5 days
			String tTransDate = sdf.format(c.getTime());
			searchParameter.put("To_Trans_Date", tTransDate);
		}
		if ((value2 != "*" && !value2.isEmpty()) && (value1 == "" || value1 == null || value1 == "*")) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date tTransDate = sdf.parse(value2);
			Calendar c = Calendar.getInstance();
			c.setTime(tTransDate); // Now use today date.
			c.add(Calendar.DATE, -1); // Adding 5 days
			String fTransDate = sdf.format(c.getTime());
			searchParameter.put("From_Trans_Date", fTransDate);
		}
		return searchParameter;
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> cancellation_Data(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data

			switch (i) {
			case 0:
				searchData.put("PrimaryRoleID", inputArray[i]);
				break;
			case 1:
				searchData.put("PrimaryRoleType", inputArray[i]);
				break;
			case 2:
				searchData.put("PrimaryRoleName", inputArray[i]);
				break;
			case 3:
				searchData.put("SecondaryRoleID", inputArray[i]);
				break;
			case 4:
				searchData.put("SecondaryRoleType", inputArray[i]);
				break;
			case 5:
				searchData.put("SecondaryRoleName", inputArray[i]);
				break;
			}

		}
		return searchData;
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> cancellation_AccountRuleBuilder_Data(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 6; i < inputArray.length; i++) {
			if (!inputArray[i].equals("") && inputArray[i].length() > 0) {
				//// Switch Case to Transform Data
				switch (i) {
				case 6:
					searchData.put("Refund Based On", inputArray[i]);
					break;
				case 7:
					searchData.put("Payee", inputArray[i]);
					break;
				case 8:
					searchData.put("If Transferred", inputArray[i]);
					break;
				case 9:
					searchData.put("Expiration Date", inputArray[i]);
					break;
				case 10:
					searchData.put("Claims", inputArray[i]);
					break;
				case 11:
					searchData.put("Claims, Administrator", inputArray[i]);
					break;
				case 12:
					searchData.put("Claims, Lender, Trade/Sale", inputArray[i]);
					break;
				case 13:
					searchData.put("Claims, Lender, NonPayment", inputArray[i]);
					break;
				case 14:
					searchData.put("Claims, Lender, Unwind", inputArray[i]);
					break;
				case 15:
					searchData.put("Claims, Repossession", inputArray[i]);
					break;
				case 16:
					searchData.put("Refund Percent", inputArray[i]);
					break;
				case 17:
					searchData.put("Refund Percent, Administrator", inputArray[i]);
					break;
				case 18:
					searchData.put("Refund Percent, Lender, Trade/Sale", inputArray[i]);
					break;
				case 19:
					searchData.put("Refund Percent, Repossession", inputArray[i]);
					break;
				case 20:
					searchData.put("Cancel Fee Within Flat Cancel Period", inputArray[i]);
					break;
				case 21:
					searchData.put("Cancel Fee Within Flat Cancel Period if Claims", inputArray[i]);
					break;
				case 22:
					searchData.put("Cancel Fee Within Flat Cancel Period if Claims, Administrator", inputArray[i]);
					break;
				case 23:
					searchData.put("Cancel Fee After Flat Cancel Period", inputArray[i]);
					break;
				case 24:
					searchData.put("Cancel Fee After Flat Cancel Period, Administrator", inputArray[i]);
					break;
				case 25:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims", inputArray[i]);
					break;
				case 26:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims, Administrator", inputArray[i]);
					break;
				case 27:
					searchData.put("Cancel Fee Within Flat Cancel Period, Administrator", inputArray[i]);
					break;
				case 28:
					searchData.put("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale", inputArray[i]);
					break;
				case 29:
					searchData.put("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale", inputArray[i]);
					break;
				case 30:
					searchData.put("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale", inputArray[i]);
					break;
				case 31:
					searchData.put("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale", inputArray[i]);
					break;
				case 32:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale", inputArray[i]);
					break;
				case 33:
					searchData.put("Cancel Fee Override", inputArray[i]);
					break;
				case 34:
					searchData.put("Cancel Fee After Flat Cancel Period, Repossession", inputArray[i]);
					break;
				case 35:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims, Repossession", inputArray[i]);
					break;
				case 36:
					searchData.put("Cancel reason, Administrator", inputArray[i]);
					break;

				}
			}
		}
		return searchData;
	}

	/**
	 * This common accepts sale Date and Expiration Date and calculates the number
	 * of Planned Policy days
	 * 
	 */
	public long calculateTermDays(String saleDate, String expireDate) throws Exception {
		long noOfDays = 0;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-12");
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2024-02-12");
			noOfDays = ChronoUnit.DAYS.between(date1.toInstant(), date2.toInstant());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}
		return noOfDays;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculation(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, -2);
		String date = dateFormat.format(c.getTime());
		//// get data for query
		String agentException = "";
		if (map.get("AGENTEXCEPTION").toLowerCase().equals("y")) {
			if (map.get("AGENTPLANTYPE").equalsIgnoreCase("selectedplans")) {
				agentException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
						+ "' and tt.CATEGORY_VALUE = '" + map.get("AGENTPLANTYPE").toUpperCase()
						+ "' and tt.coverage = '" + map.get("COVERAGE") + "' and tt.term = '" + map.get("TERM")
						+ "' and tt.class = '" + map.get("CLASS") + "'";
			} else {
				agentException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
						+ "' and tt.CATEGORY_VALUE = '" + map.get("AGENTPLANTYPE").toUpperCase() + "'";
				@SuppressWarnings("unused")
				///// this is to be used as STD-XCP is not a part of dealer and agent exception
				String agentExceptionFinal = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '"
						+ date + "' and tt.CATEGORY_VALUE = '" + map.get("AGENTPLANTYPE").toUpperCase()
						+ "' and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')";

			}
		} else
			agentException = "null";
		String dealerException = "";
		if (map.get("DEALEREXCEPTION").toLowerCase().equals("y")) {
			if (map.get("DEALERPLANTYPE").equalsIgnoreCase("selectedplans"))
				dealerException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
						+ "' and tt.CATEGORY_VALUE = '" + map.get("DEALERPLANTYPE").toUpperCase()
						+ "' and tt.coverage = '" + map.get("COVERAGE") + "' and tt.term = '" + map.get("TERM")
						+ "' and tt.class = '" + map.get("CLASS") + "'";
			else {
				dealerException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
						+ "' and tt.CATEGORY_VALUE = '" + map.get("DEALERPLANTYPE").toUpperCase() + "'";
				@SuppressWarnings("unused")
				///// this is to be used as STD-XCP is not a part of dealer and agent exception
				String dealerExceptionFinal = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '"
						+ date + "' and tt.CATEGORY_VALUE = '" + map.get("DEALERPLANTYPE").toUpperCase()
						+ "' and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')";
			}
		} else
			dealerException = "null";
		String progCode = "";
		try {
			progCode = map.get("PRICESHEETCODE");
			if (progCode == null) {
				progCode = "";
			}
		} catch (Exception e) {
		}
		String dealerId = "";
		try {
			dealerId = map.get("DEALERID");
			if (dealerId == null) {
				dealerId = "";
			}
		} catch (Exception e) {
		}
		String query = "select top 1 p.id as pricesheetId,a.role_identifier as dealerid, p.CODE as pcode "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + dealerId
				+ "%' and a.account_status_id = 1 and a.role_type_id = 1 order by 1 desc";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		/*
		 * ///// code to get if selected plan data exists to refrain to be selected
		 * while ///// filling new business form String splanquery =
		 * "select distinct coverage,class,term from PRICESHEET_PRODUCT_TIER where pricesheet_id in "
		 * + "(" + dbMap1.get("pricesheetId") +
		 * ", (select Parent_PriceSheet_id from dbo.pricing_pricesheet where id = " +
		 * dbMap1.get("pricesheetId") + ")) " + "and category_value = 'SELECTEDPLANS';";
		 * HashMap<Integer, HashMap<String, String>> dd =
		 * getAllDataFromDatabase(splanquery); if (dd.size() > 0) { for
		 * (Map.Entry<Integer, HashMap<String, String>> letterEntry : dd.entrySet()) {
		 * for (Map.Entry<String, String> nameEntry : letterEntry.getValue().entrySet())
		 * { String name = nameEntry.getKey(); String val = nameEntry.getValue(); if
		 * (name.equalsIgnoreCase("coverage")) { if (dbMap.get("MissCoverage") == null)
		 * dbMap.put("MissCoverage", val); else { String oldVal =
		 * dbMap.get("MissCoverage"); dbMap.put("MissCoverage", oldVal + "," + val); }
		 * 
		 * } else if (name.equalsIgnoreCase("term")) { if (dbMap.get("MissTerm") ==
		 * null) dbMap.put("MissTerm", val); else { String oldVal =
		 * dbMap.get("MissTerm"); dbMap.put("MissTerm", oldVal + "," + val); }
		 * 
		 * } else if (name.equalsIgnoreCase("class")) { if (dbMap.get("MissClass") ==
		 * null) dbMap.put("MissClass", val); else { String oldVal =
		 * dbMap.get("MissClass"); dbMap.put("MissClass", oldVal + "," + val); }
		 * 
		 * } } } }
		 */
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("DEALERID", dbMap1.get("dealerid"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		return dbMap;
	}

	/**
	 * This common accepts Date and convert the same in mm/dd/yyyy format after
	 * adding input days
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertNewDateFormatTC09(String date) throws Exception {
		try {
			// get only date
			int index = date.indexOf(" ");
			if (index > 0)
				date = date.substring(0, index);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}
		return date;
	}

	public HashMap<String, String> getExceptionPremium(HashMap<String, String> map) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, -2);
		String date = dateFormat.format(c.getTime());
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String finalQuery = "";
		if (map.get("AGENTPLANTYPE").toUpperCase().equalsIgnoreCase("ALLPLANS")
				&& map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase("ALLPLANS")
				&& (map.get("DEALEREXCEPTION").equalsIgnoreCase("y")
						|| map.get("AGENTEXCEPTION").equalsIgnoreCase("y"))) {
			float sumOfPremium = 0;
			if (map.get("DEALEREXCEPTION").equalsIgnoreCase("y")) {
				finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
						+ "from PRICESHEET_PRODUCT_TIER tt "
						+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
						+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
						+ "where t.pricesheet_id in (" + map.get("PRICESHEETID") + ") "
						+ "and tt.CATEGORY_VALUE = 'ALLPLANS' "
						+ "and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')" + " and tt.EFFECTIVE_DATE < '"
						+ date + "' and t.EFFECTIVE_DATE < '" + date + "'"
						+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
				HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
				if (data.size() > 0) {
					String dsate = data.get(1).get("PSDATE");
					for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
						String date2 = maps.getValue().get("PSDATE");
						if (dsate.equals(date2))
							sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
					}
				}
				//// also get data for selected plans
				finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
						+ "from PRICESHEET_PRODUCT_TIER tt "
						+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
						+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
						+ "where t.pricesheet_id in (" + map.get("PRICESHEETID") + ") "
						+ "and tt.CATEGORY_VALUE = 'SELECTEDPLANS' "
						+ "and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')" + " and tt.EFFECTIVE_DATE < '"
						+ date + "' and t.EFFECTIVE_DATE < '" + date + "' and (tt.TERM = '" + map.get("TERM")
						+ "'or tt.class = '" + map.get("CLASS") + "'  or tt.coverage ='" + map.get("COVERAGE")
						+ "' or tt.MILEAGE_FROM<'" + map.get("MILEAGE") + "'" + " or tt.MILEAGE_TO>'"
						+ map.get("MILEAGE") + "')" + "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
				HashMap<Integer, HashMap<String, String>> deata = getAllDataFromDatabase(finalQuery);
				if (deata.size() > 0) {
					String dsate1 = deata.get(1).get("PSDATE");
					for (Entry<Integer, HashMap<String, String>> maps : deata.entrySet()) {
						String date2 = maps.getValue().get("PSDATE");
						if (dsate1.equals(date2))
							sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
					}
				}
			}
			String dealerPricesheet = map.get("PRICESHEETID");
			if (map.get("AGENTEXCEPTION").equalsIgnoreCase("y")) {
				for (int i = 0; i < 10; i++) {
					String query = "(select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
							+ dealerPricesheet + ")";
					HashMap<String, String> dasta = getTopRowDataFromDatabase(query);
					String query1 = "(select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
							+ dasta.get("parent_pricesheet_id") + ")";
					HashMap<String, String> dasta1 = getTopRowDataFromDatabase(query1);
					if (dasta1.get("parent_pricesheet_id") != "") {
						dealerPricesheet = dasta.get("parent_pricesheet_id");
						finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
								+ "from PRICESHEET_PRODUCT_TIER tt "
								+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
								+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
								+ "where t.pricesheet_id in (" + dealerPricesheet + ") "
								+ "and tt.CATEGORY_VALUE = 'ALLPLANS' "
								+ "and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')"
								+ " and tt.EFFECTIVE_DATE < '" + date + "' and t.EFFECTIVE_DATE < '" + date + "'"
								+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
						HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
						if (data.size() > 0) {
							String dsate = data.get(1).get("PSDATE");
							for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
								String date2 = maps.getValue().get("PSDATE");
								if (dsate.equals(date2))
									sumOfPremium = sumOfPremium
											+ Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
							}
						}
						//// also get data for selected plans
						finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
								+ "from PRICESHEET_PRODUCT_TIER tt "
								+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
								+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
								+ "where t.pricesheet_id in (" + dealerPricesheet + ") "
								+ "and tt.CATEGORY_VALUE = 'SELECTEDPLANS' "
								+ "and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')"
								+ " and tt.EFFECTIVE_DATE < '" + date + "' and t.EFFECTIVE_DATE < '" + date
								+ "' and (tt.TERM = '" + map.get("TERM") + "'or tt.class = '" + map.get("CLASS")
								+ "'  or tt.coverage ='" + map.get("COVERAGE") + "' or tt.MILEAGE_FROM<'"
								+ map.get("MILEAGE") + "'" + " or tt.MILEAGE_TO>'" + map.get("MILEAGE") + "')"
								+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
						HashMap<Integer, HashMap<String, String>> deata = getAllDataFromDatabase(finalQuery);
						if (deata.size() > 0) {
							String dsate1 = data.get(1).get("PSDATE");
							for (Entry<Integer, HashMap<String, String>> maps : deata.entrySet()) {
								String date2 = maps.getValue().get("PSDATE");
								if (dsate1.equals(date2))
									sumOfPremium = sumOfPremium
											+ Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
							}
						}
					} else
						break;
				}
			}
			dbMap.put("ExceptionPremium", String.valueOf(sumOfPremium));
		}

		else if (map.get("AGENTPLANTYPE").toUpperCase().equalsIgnoreCase("SELECTEDPLANS")
				|| map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase("SELECTEDPLANS")) {
			String abc = "";
			if (map.get("DEALEREXCEPTION").equalsIgnoreCase("y")) {
				abc = abc + map.get("PRICESHEETID");
				if (map.get("AGENTEXCEPTION").equalsIgnoreCase("y"))
					abc = abc + ", ";
			}
			if (map.get("AGENTEXCEPTION").equalsIgnoreCase("y")) {
				abc = abc + "(select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
						+ map.get("PRICESHEETID") + ")";
			}
			finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
					+ "from PRICESHEET_PRODUCT_TIER tt "
					+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
					+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
					+ "where t.pricesheet_id in (" + abc + ") " + "and tt.CATEGORY_VALUE = 'ALLPLANS' "
					+ "and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID')" + " and tt.EFFECTIVE_DATE < '"
					+ date + "' and t.EFFECTIVE_DATE < '" + date + "'"
					+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
			HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
			float sumOfPremium = 0;
			if (data.size() > 0) {
				for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
					sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
				}
			}
			String abcd = "";
			if (map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase("SELECTEDPLANS")) {
				abcd = abcd + map.get("PRICESHEETID");
				if (map.get("AGENTPLANTYPE").toUpperCase().equalsIgnoreCase("SELECTEDPLANS"))
					abcd = abcd + ", ";
			}
			if (map.get("AGENTPLANTYPE").toUpperCase().equalsIgnoreCase("SELECTEDPLANS")) {
				abcd = abcd + "(select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
						+ map.get("PRICESHEETID") + ")";
			}
			finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
					+ "from PRICESHEET_PRODUCT_TIER tt "
					+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
					+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
					+ "where t.pricesheet_id in (" + abc + ") " + "and tt.CATEGORY_VALUE = 'SELECTEDPLANS' "
					+ "and xtype not like '%STD-XCP%' and DTYPE not in ('TYPE','ID') and tt.coverage = '"
					+ map.get("COVERAGE") + "' and tt.class = '" + map.get("CLASS") + "' and tt.term = '"
					+ map.get("TERM") + "' " + " and tt.EFFECTIVE_DATE < '" + date + "' and t.EFFECTIVE_DATE < '" + date
					+ "'" + "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
			HashMap<Integer, HashMap<String, String>> data2 = getAllDataFromDatabase(finalQuery);
			if (data2.size() > 0) {
				for (Entry<Integer, HashMap<String, String>> maps : data2.entrySet()) {
					sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
				}
			}
			dbMap.put("ExceptionPremium", String.valueOf(sumOfPremium));
		}
		return dbMap;
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * This common function is used to go To ContractBuilder module tab
	 * 
	 */
	public HashMap<String, String> appendSearchDataInCancellationSearch(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("Contract", inputArray[i]);
				break;
			case 1:
				searchData.put("First_Name", inputArray[i]);
				break;
			case 2:
				searchData.put("Last_Name", inputArray[i]);
				break;
			case 3:
				searchData.put("VIN", inputArray[i]);
				break;
			case 4:
				searchData.put("Role_ID", inputArray[i]);
				break;
			case 5:
				searchData.put("Role_Type", inputArray[i]);
				break;
			case 6:
				searchData.put("Payee", inputArray[i]);
				break;
			case 7:
				searchData.put("Code", inputArray[i]);
				break;
			case 8:
				searchData.put("From_Cancel_Date", inputArray[i]);
				break;
			case 9:
				searchData.put("To_Cancel_Date", inputArray[i]);
				break;
			case 10:
				searchData.put("From_Date_Processed", inputArray[i]);
				break;
			case 11:
				searchData.put("To_Date_Processed", inputArray[i]);
				break;
			case 12:
				searchData.put("Status", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		System.out.println("have star====" + searchData);
		return searchData;
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, will remove *
	 * and Blanks columns and values
	 * 
	 */
	public HashMap<String, String> convertDataRemoveStarInCancellationSearch(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (!inputArray[i].equals("*") && inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("Contract", inputArray[i]);
					break;
				case 1:
					searchData.put("First_Name", inputArray[i]);
					break;
				case 2:
					searchData.put("Last_Name", inputArray[i]);
					break;
				case 3:
					searchData.put("VIN", inputArray[i]);
					break;
				case 4:
					searchData.put("Role_ID", inputArray[i]);
					break;
				case 5:
					searchData.put("Role_Type", inputArray[i]);
					break;
				case 6:
					searchData.put("Payee", inputArray[i]);
					break;
				case 7:
					searchData.put("Code", inputArray[i]);
					break;
				case 8:
					searchData.put("From_Cancel_Date", inputArray[i]);
					break;
				case 9:
					searchData.put("To_Cancel_Date", inputArray[i]);
					break;
				case 10:
					searchData.put("From_Date_Processed", inputArray[i]);
					break;
				case 11:
					searchData.put("To_Date_Processed", inputArray[i]);
					break;
				case 12:
					searchData.put("Status", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}
			}
		}
		System.out.println("remove star===" + searchData);
		return searchData;
	}

	/*
	 * This function change the key name as aliasing for DB query
	 */
	public String convertKeysforCancelSearch(String key) throws Exception {
		String myKey = "";
		System.out.println("Parameter====" + key);
		switch (key) {
		case "Contract":
			myKey = "asd.cert";
			break;
		case "First_Name":
			myKey = "asd.CUSTOMER_FIRST";
			break;
		case "Last_Name":
			myKey = "asd.CUSTOMER_LAST";
			break;
		case "VIN":
			myKey = "asd.VIN";
			break;
		case "Role_ID":
			myKey = "account.Role_Identifier";
			break;
		case "Role_Type":
			myKey = "accType.Role_Name";
			break;
		case "Status":
			myKey = "cs.NAME";
			break;
		case "Payee":
			myKey = "cp.Payee_Name";
			break;
		case "Code":
			myKey = "asd.PROGRAM_CODE";
			break;
		case "From_Cancel_Date":
			myKey = "acd.CANCEL_DATE";
			break;
		case "To_Cancel_Date":
			myKey = "acd.CANCEL_DATE";
			break;
		case "From_Date_Processed":
			myKey = "acd.PROCESS_DATE";
			break;
		case "To_Date_Processed":
			myKey = "acd.PROCESS_DATE";
			break;
		default:
			//// do nothing
		}
		return myKey;
	}

	/**
	 * This common function is used to go to creditAndRefunds
	 * 
	 * @return
	 * 
	 */
	public void goToCreditAndRefunds() {
		try {
			click("clickOnCreditsAndRefunds");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This common function is used to go to AccountStatements
	 * 
	 * @return
	 * 
	 */
	public void goToAccountStatements() {
		try {
			click("clickOnAccountStatements");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * this function is used to find element by Xpath
	 */
	public WebElement findElementByXpath(String xpathExpression) {
		WebElement we = null;
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
				logger.info("click on " + xpathExpression + " is successful");
				break;
			} catch (Exception e) {
				logger.info("click on " + xpathExpression + " is failed");
				if (i < 3)
					continue;
				else
					throw e;
			}
		}

		return we;
	}

	/**
	 * This common accepts Date and convert the same in mm/dd/yyyy format after
	 * adding input days
	 * 
	 * @throws Exception
	 * 
	 */
	public String convertDateFormatWithDaysVariance(String date, int noOfDays) throws Exception {
		String newDate = "";
		try {
			//// parse string to date
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			//// parse date to new MM/dd/yyyy format
			String abc = new SimpleDateFormat("MM/dd/yyyy").format(date1);
			//// use calendar to add days
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			// Setting the date to the given date
			c.setTime(sdf.parse(abc));
			// Add noOfDays days to current date
			c.add(Calendar.DAY_OF_MONTH, noOfDays);
			// Date after adding the days to the current date
			newDate = sdf.format(c.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}

		return newDate;

	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatus(String status) throws Exception {
		String contract_id = "";
		String query = "select top 1 CERT from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("CERT");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatusandSubStatus(String status, String subStatus)
			throws Exception {
		String contract_id = "";
		String query = "select top 1 CERT from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id = sale.contract_substatus_id where sta.NAME = '"
				+ status + "' and sub.name = '" + subStatus + "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("CERT");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusSubStatus(String[] statuss) throws Exception {
		String status = statuss[0].trim().toLowerCase();
		String substatus = statuss[1].trim().toLowerCase();
		String query = "";
		if (status.length() > 1 && substatus.length() > 1)
			query = "select top 1 CERT,sss.Name as contractSubStatus,sta.name as contractstatus  from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
					+ "on sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sss on sss.id = sale.[CONTRACT_SUBSTATUS_ID] where sta.NAME = '"
					+ status + "' and sss.name = '" + substatus + "' order by 1 desc;";
		else
			query = "select top 1 CERT,sss.Name as contractSubStatus,sta.name as contractstatus  from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
					+ "on sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sss on sss.id = sale.[CONTRACT_SUBSTATUS_ID] where sta.NAME = '"
					+ status + "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This common method accepts two dates and calculates the number of days
	 * between them
	 * 
	 */
	public long calculateDaysBwTwoDates(String fromDate, String toDate) throws Exception {
		long noOfDays = 0;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
			noOfDays = ChronoUnit.DAYS.between(date1.toInstant(), date2.toInstant());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}
		return noOfDays;
	}

	/**
	 * This common accepts Date in yyyy/dd/mm format and add input days
	 * CancellationTC_14
	 * 
	 * @throws Exception
	 * 
	 */
	public String addDateCancellation(String date, int noOfDays) throws Exception {
		String newDate = "";
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			//// parse date to new MM/dd/yyyy format
			String abc = new SimpleDateFormat("yyyy-MM-dd").format(date1);
			//// use calendar to add days
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// Setting the date to the given date
			c.setTime(sdf.parse(abc));
			// Add noOfDays days to current date
			c.add(Calendar.DAY_OF_MONTH, noOfDays);
			// Date after adding the days to the current date
			newDate = sdf.format(c.getTime());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}

		return newDate;
	}

	/**
	 * This common method accepts a number in String format and removes zeroes after
	 * decimal point and returns a integer in string format
	 * 
	 * created by Surbhi
	 * 
	 */
	public String removeZeroes(String amount) throws Exception {
		try {
			if ((amount.substring(amount.indexOf(".") + 2)).equals("0")) {
				if ((amount.substring(amount.indexOf(".") + 1)).equals("00"))
					amount = amount.substring(0, amount.indexOf("."));
				else
					amount = amount.substring(0, amount.indexOf(".") + 2);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw e1;
		}
		return amount;
	}

	public int calculateMilesDifference(String cancelMiles) throws Exception {
		int milesDifference = 2214;
		if (cancelMiles.length() > 0) {
			String saleMiles = getSalesMiles();
			milesDifference = Integer.parseInt(cancelMiles) - Integer.parseInt(saleMiles);
		}
		return milesDifference;
	}

	/**
	 * This function is used to get sales miles
	 * 
	 */
	public String getSalesMiles() throws Exception {
		return getValue("getMiles");
	}

	/**
	 * dataProvider function is used to read excel from dektop or local directory
	 * 
	 * @param excelFileName : excel file name
	 * @param sheetName     : sheet name in respective excel file
	 */

	@SuppressWarnings({ "resource", "deprecation" })
	public static HashMap<Integer, HashMap<String, String>> readExcelFromDesktop(String file, String sheetName)
			throws IOException {

		HashMap<Integer, HashMap<String, String>> excelFileMap = new HashMap<Integer, HashMap<String, String>>();

		//// current running directory
		String currentDir = System.getProperty("user.home") + "\\Desktop\\";
		//// Excel sheet file name
		String fileName = currentDir + file;
		FileInputStream fis = new FileInputStream(fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int lastRow = sheet.getLastRowNum();
		int count = 0;
		// Looping over entire row
		for (int i = 1; i <= lastRow; i++) {

			Row row = sheet.getRow(i);
			String abc = "";
			Map<String, String> dataMap = new HashMap<String, String>();
			for (int j = 0; j <= row.getLastCellNum(); j++) {
				try {
					CellType cellType = row.getCell(j).getCellType();
					//// Switch case to convert excel data to excel
					switch (cellType.toString().toLowerCase()) {
					case "string":
						abc = row.getCell(j).getStringCellValue();
						break;
					case "blank":
						abc = row.getCell(j).getStringCellValue();
						break;
					case "numeric":
						row.getCell(j).setCellType(CellType.STRING);
						abc = row.getCell(j).getRichStringCellValue().getString();
						break;
					default:
						abc = row.getCell(j).getStringCellValue();
					}
				} catch (Exception e) {
					abc = "";
				}
				// 0th Cell as Key
				Cell keyCell = sheet.getRow(0).getCell(j);
				String value = abc.trim();
				String key = keyCell.getStringCellValue().trim();

				// Putting key & value in dataMap
				dataMap.put(key, value);
			}

			if (dataMap.containsKey("IsSelected")) {
				dataMap.remove("IsSelected");
			}
			if (dataMap.containsKey("UpdatedBy")) {
				dataMap.remove("UpdatedBy");
			}
			if (dataMap.containsKey("CreatedDate")) {
				dataMap.remove("CreatedDate");
			}
			count++;

			// Putting dataMap to excelFileMap
			excelFileMap.put(count, (HashMap<String, String>) dataMap);
		}

		// Returning excelFileMap
		return excelFileMap;
	}

	public List<WebElement> findElementsByXpath(String xpathExpression) {
		List<WebElement> we = null;
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				we = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathExpression)));
				logger.info("click on " + xpathExpression + " is successful");
				break;
			} catch (Exception e) {
				logger.info("click on " + xpathExpression + " is failed");
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
		return we;
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> cancellation_AccountRuleBuilderDataTC29(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			if (!inputArray[i].equals("") && inputArray[i].length() > 0) {
				//// Switch Case to Transform Data
				switch (i) {
				case 0:
					searchData.put("Refund Based On", inputArray[i]);
					break;
				case 1:
					searchData.put("Payee", inputArray[i]);
					break;
				case 2:
					searchData.put("If Transferred", inputArray[i]);
					break;
				case 3:
					searchData.put("Expiration Date", inputArray[i]);
					break;
				case 4:
					searchData.put("Claims", inputArray[i]);
					break;
				case 5:
					searchData.put("Claims, Administrator", inputArray[i]);
					break;
				case 6:
					searchData.put("Claims, Lender, Trade/Sale", inputArray[i]);
					break;
				case 7:
					searchData.put("Claims, Lender, NonPayment", inputArray[i]);
					break;
				case 8:
					searchData.put("Claims, Lender, Unwind", inputArray[i]);
					break;
				case 9:
					searchData.put("Claims, Repossession", inputArray[i]);
					break;
				case 10:
					searchData.put("Refund Percent", inputArray[i]);
					break;
				case 11:
					searchData.put("Refund Percent, Administrator", inputArray[i]);
					break;
				case 12:
					searchData.put("Refund Percent, Lender, Trade/Sale", inputArray[i]);
					break;
				case 13:
					searchData.put("Refund Percent, Repossession", inputArray[i]);
					break;
				case 14:
					searchData.put("Cancel Fee Within Flat Cancel Period", inputArray[i]);
					break;
				case 15:
					searchData.put("Cancel Fee Within Flat Cancel Period if Claims", inputArray[i]);
					break;
				case 16:
					searchData.put("Cancel Fee Within Flat Cancel Period if Claims, Administrator", inputArray[i]);
					break;
				case 17:
					searchData.put("Cancel Fee After Flat Cancel Period", inputArray[i]);
					break;
				case 18:
					searchData.put("Cancel Fee After Flat Cancel Period, Administrator", inputArray[i]);
					break;
				case 19:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims", inputArray[i]);
					break;
				case 20:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims, Administrator", inputArray[i]);
					break;
				case 21:
					searchData.put("Cancel Fee Within Flat Cancel Period, Administrator", inputArray[i]);
					break;
				case 22:
					searchData.put("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale", inputArray[i]);
					break;
				case 23:
					searchData.put("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale", inputArray[i]);
					break;
				case 24:
					searchData.put("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale", inputArray[i]);
					break;
				case 25:
					searchData.put("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale", inputArray[i]);
					break;
				case 26:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale", inputArray[i]);
					break;
				case 27:
					searchData.put("Cancel Fee Override", inputArray[i]);
					break;
				case 28:
					searchData.put("Cancel Fee After Flat Cancel Period, Repossession", inputArray[i]);
					break;
				case 29:
					searchData.put("Cancel Fee After Flat Cancel Period if Claims, Repossession", inputArray[i]);
					break;
				case 30:
					searchData.put("Cancel reason, Administrator", inputArray[i]);
					break;
				}
			}
		}
		return searchData;
	}

	public String getVehicleAge(String age) {
		int age1 = Integer.parseInt(age);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int vehicleYear = year - (age1 + 1);
		age = String.valueOf(vehicleYear);
		return age;

	}

}
