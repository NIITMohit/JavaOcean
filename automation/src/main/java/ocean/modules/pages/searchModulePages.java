package ocean.modules.pages;

import java.util.HashMap;
import java.util.Map;

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
				type("searchTypeContract", valueToInput);
				break;
			case "First_Name":
				type("searchTypeFirstName", valueToInput);
				break;
			case "Last_Name":
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
					case "processed":
						click("clickProcessed");
					case "purged":
						click("clickPurged");
					case "reference":
						click("clickReference");
					case "cancelled":
						click("clickCancelled");
					case "nis":
						click("clickNIS");
					default:
						click("clickProcessed");
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
			case "Post_Period":
				type("selectPostPeriod", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
	}
}
