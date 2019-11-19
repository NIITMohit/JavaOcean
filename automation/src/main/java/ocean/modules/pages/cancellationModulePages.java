package ocean.modules.pages;

import java.util.HashMap;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * This is object class which contains all pages of cancellation modules
 * 
 * @author Mohit Goel
 */
public class cancellationModulePages extends CommonFunctions {
	/**
	 * This function is used to navigate to PricingSheetListTab
	 * 
	 * 
	 */
	public void visitPriceSheetListTab() throws Exception {
		click("clickPricingSheetListTab");
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
			case "CERT":
				type("typeContractId", valueToInput);
				break;
			case "CUSTOMER_FIRST":
				type("typeCustomerFirstName", valueToInput);
				break;
			case "CUSTOMER_LAST":
				type("typeCustomerLastName", valueToInput);
				break;
			case "VIN":
				type("typeVIN", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
	}
}
