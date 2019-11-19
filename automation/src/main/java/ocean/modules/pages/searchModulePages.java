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
				String[] values = valueToInput.split(",");
				for (String value : values) {
					//// click status checkbox
					//// click desired status
				}
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
	}
}
