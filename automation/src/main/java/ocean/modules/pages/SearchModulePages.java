package ocean.modules.pages;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ocean.modules.database.SearchDataBase;

/**
 * This is object class which contains all pages of search modules
 * 
 * @author Mohit Goel
 */
public class SearchModulePages extends SearchDataBase {

	/**
	 * This function is used to get search count
	 * 
	 */
	public int getSearchesultsCount() {
		int count = 0;
		String countText = getAttributeValue("getSearchCount", "Name");
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(countText);
		while (m.find()) {
			String aa = m.group();
			count = Integer.parseInt(aa);
		}
		return count;
	}

	/**
	 * This function is used to get ccontractId based on row number
	 * 
	 */
	public String getContractId(int i) {
		return getValue("listOfContractId", i);
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
				type("searchTypeContract", valueToInput);
				break;
			case "CUSTOMER_FIRST":
				type("searchTypeFirstName", valueToInput);
				break;
			case "CUSTOMER_LAST":
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

	/**
	 * This function is used to fetch all search data details for row i
	 * 
	 * 
	 */
	public HashMap<String, String> getSearchResult(int i) throws Exception {
		HashMap<String, String> summaryData = new HashMap<String, String>();
		summaryData.put("Contract", getValue("listOfContractId", i));
		summaryData.put("Code", getValue("listOfCode", i));
		summaryData.put("First_Name", getValue("listOfFirstName", i));
		summaryData.put("Last_Name", getValue("listOfLastName", i));
		summaryData.put("Sale_Date", getValue("listOfSaleDate", i));
		summaryData.put("Trans_Date", getValue("listOfTransDate", i));
		summaryData.put("VIN", getValue("listOfVIN", i));
		summaryData.put("Status", getValue("listOfStatus", i));
		click("swipeRight");
		summaryData.put("Primary_Seller_Id", getValue("listOfPrimarySellerId", i));
		summaryData.put("Prmary_Seller_Name", getValue("listOfPrimarySellerName", i));
		summaryData.put("Secondary_Seller_Name", getValue("listOfSecondarySellerName", i));
		summaryData.put("Secondary_Seller_Id", getValue("listOfSecondarySellerId", i));
		summaryData.put("Primary_Seller_Type", getValue("listOfPrimarySellerType", i));
		summaryData.put("Secondary_Seller_Type", getValue("listOfSecondarySellerType", i));
		summaryData.put("Primary_Payee_Id", getValue("listOfPrimaryPayeeId", i));
		summaryData.put("State", getValue("listOfState", i));
		summaryData.put("Phone", getValue("listOfPhone", i));
		return summaryData;
	}
}
