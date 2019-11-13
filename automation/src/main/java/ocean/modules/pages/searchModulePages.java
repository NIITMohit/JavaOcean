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
		for (Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();

			switch (searchParamater) {
			case "Contract":
				type("", valueToInput);
				break;
			case "Last Name":
				type("", valueToInput);
				break;
			case "First Name":
				type("", valueToInput);
				break;
			case "VIN":
				type("", valueToInput);
				break;
			case "Status":
				type("", valueToInput);
				break;
			case "Post Period":
				type("", valueToInput);
				break;
			case "State":
				type("", valueToInput);
				break;
			case "City":
				type("", valueToInput);
				break;

			default:
				//// type contract id
				type("", valueToInput);
			}
		}

		///// click search button
	}

}
