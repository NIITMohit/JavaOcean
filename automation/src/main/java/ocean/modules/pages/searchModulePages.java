package ocean.modules.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import com.nimbusds.openid.connect.sdk.ClaimsRequest.Entry;

import ocean.common.CommonFunctions;

/**
 * This is object class which contains all pages of search modules
 * 
 * @author Mohit Goel
 */
public class searchModulePages extends CommonFunctions {
	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> appendSearchData(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length - 1; i++) {
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
		for (int i = 0; i < inputArray.length - 1; i++) {
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
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hashmap with input parameters
	 * 
	 */
	public void searchContractGivenInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			switch (searchParamater) {
			case "Contract":
				type("", valueToInput);
				break;
			case "First_Name":
				type("", valueToInput);
				break;
			case "Last_Name":
				type("", valueToInput);
				break;
			case "VIN":
				type("", valueToInput);
				break;
			case "Status":
				type("", valueToInput);
				break;
			case "State":
				type("", valueToInput);
				break;
			case "City":
				type("", valueToInput);
				break;
			case "Phone":
				type("", valueToInput);
				break;
			case "Program_Code":
				type("", valueToInput);
				break;
			case "Primary_Payee_ID":
				type("", valueToInput);
				break;
			case "Primary_Seller_Name":
				type("", valueToInput);
				break;
			case "Primary_Seller_ID":
				type("", valueToInput);
				break;
			case "Primary_Seller_Type":
				type("", valueToInput);
				break;
			case "From_Sale_Date":
				type("", valueToInput);
				break;
			case "To_Sale_Date":
				type("", valueToInput);
				break;
			case "Secondary_Seller_Name":
				type("", valueToInput);
				break;
			case "Secondary_Seller_ID":
				type("", valueToInput);
				break;
			case "Secondary_Seller_Type":
				type("", valueToInput);
				break;
			case "From_Trans_Date":
				type("", valueToInput);
				break;
			case "To_Trans_Date":
				type("", valueToInput);
				break;
			case "Post_Period":
				type("", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("seatcrbtn");
	}

}
