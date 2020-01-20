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
	 * This common function is used to go to cancellation tab
	 * 
	 * @return
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
