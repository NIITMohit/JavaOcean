package ocean.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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
				searchData.put("Primary_Payee_ID", inputArray[i]);
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
				searchData.put("From_Trans_Date", inputArray[i]);
				break;
			case 19:
				searchData.put("To_Trans_Date", inputArray[i]);
				break;
			case 20:
				searchData.put("Post_Period", inputArray[i]);
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
					searchData.put("Primary_Payee_ID", inputArray[i]);
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
					searchData.put("From_Trans_Date", inputArray[i]);
					break;
				case 19:
					searchData.put("To_Trans_Date", inputArray[i]);
					break;
				case 20:
					searchData.put("Post_Period", inputArray[i]);
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
	 * 
	 */
	public void goToAccountsSearchTab() {
		try {
			click("clickAccountsSearchTab");
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
			//// get only date
			date = date.substring(0, date.indexOf(" "));
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
	
///// Prepare test data needed for test case
	///// validatePremiumCalculationForMasterAndSubMasterPriceSheet
	public HashMap<String, String> prepareData(String[] inputData) {
		HashMap<String, String> premiumData = new HashMap<String, String>();
		//// Fetch PRICESHEETCODE, if exist
		if (inputData[0].length() > 0)
			premiumData.put("PRICESHEETCODE", inputData[0]);
		//// Fetch ACTUALPRICESHEETCODE, if exist
		if (inputData[1].length() > 0)
			premiumData.put("DEALERID", inputData[1]);
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
		//// Excel sheet file name
		String fileName = excelFileName + ".xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, sheetName);
		return arrayObject;
	}
}
