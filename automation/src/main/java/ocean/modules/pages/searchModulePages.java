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
			case "iteration":
				//// do nothing
			case "Contract":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "First_Name":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Last_Name":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "VIN":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Status":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "State":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "City":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Phone":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Program_Code":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Primary_Payee_ID":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Primary_Seller_Name":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Primary_Seller_ID":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Primary_Seller_Type":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "From_Sale_Date":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "To_Sale_Date":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Secondary_Seller_Name":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Secondary_Seller_ID":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Secondary_Seller_Type":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "From_Trans_Date":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "To_Trans_Date":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			case "Post_Period":
				if (valueToInput.length() > 0) {
					type("", valueToInput);
				}
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("seatcrbtn");
	}

}
