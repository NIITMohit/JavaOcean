package ocean.common;

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
	public HashMap<String, String> appendSearchData(String[] inputArray) {
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
	public HashMap<String, String> convertDataRemoveStar(String[] inputArray) {
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
	 * @return
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
}
