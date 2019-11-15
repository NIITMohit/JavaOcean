package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.common.CommonFunctions;
import ocean.common.DataProviderClass;
import ocean.modules.pages.pricingModulePages;
import ocean.modules.pages.searchModulePages;

/**
 * OCEAN_Pricing_TC01 class automates Ocean Pricing module Test Condition 01,
 * which holds 1 Test Case; Test Condition Description : Validate price sheet
 * content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Search_TC_01 extends searchModulePages {

	// @Test(priority = 1, groups = "regression", dataProvider = "searchContract",
	// dataProviderClass = DataProviderClass.class)
	public void searchContractWithAnyInputField(String iteration, String Contract, String First_Name, String VIN,
			String Status, String State, String City, String Phone, String Program_Code, String Primary_Payee_ID,
			String Primary_Seller_Name, String Primary_Seller_ID, String Primary_Seller_Type, String From_Sale_Date,
			String To_Sale_Date, String Secondary_Seller_Name, String Secondary_Seller_ID, String Secondary_Seller_Type,
			String From_Trans_Date, String To_Trans_Date, String Post_Period) throws Exception {
		System.out.println(iteration + Contract + First_Name + VIN + Status + State + City + Phone + Program_Code
				+ Primary_Payee_ID + Primary_Seller_Name + Primary_Seller_ID + Primary_Seller_Type + From_Sale_Date
				+ To_Sale_Date + Secondary_Seller_Name + Secondary_Seller_ID + Secondary_Seller_Type + From_Trans_Date
				+ To_Trans_Date + Post_Period);

	}

	@Test(priority = 1, groups = "regression", dataProvider = "searchContract", dataProviderClass = DataProviderClass.class)
	public void searchContractWithAnyInputFielsd(String[] inputArray) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		String iterations = inputArray[0];
		for (int i = 1; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("iteration", inputArray[i]);
				break;
			case 1:
				searchData.put("Contract", inputArray[i]);
				break;
			case 2:
				searchData.put("First_Name", inputArray[i]);
				break;
			case 3:
				searchData.put("Last_Name", inputArray[i]);
				break;
			case 4:
				searchData.put("VIN", inputArray[i]);
				break;
			case 5:
				searchData.put("Status", inputArray[i]);
				break;
			case 6:
				searchData.put("State", inputArray[i]);
				break;
			case 7:
				searchData.put("City", inputArray[i]);
				break;
			case 8:
				searchData.put("Phone", inputArray[i]);
				break;
			case 9:
				searchData.put("Program_Code", inputArray[i]);
				break;
			case 10:
				searchData.put("Primary_Payee_ID", inputArray[i]);
				break;
			case 11:
				searchData.put("Primary_Seller_Name", inputArray[i]);
				break;
			case 12:
				searchData.put("Primary_Seller_ID", inputArray[i]);
				break;
			case 13:
				searchData.put("Primary_Seller_Type", inputArray[i]);
				break;
			case 14:
				searchData.put("From_Sale_Date", inputArray[i]);
				break;
			case 15:
				searchData.put("To_Sale_Date", inputArray[i]);
				break;
			case 16:
				searchData.put("Secondary_Seller_Name", inputArray[i]);
				break;
			case 17:
				searchData.put("Secondary_Seller_ID", inputArray[i]);
				break;
			case 18:
				searchData.put("Secondary_Seller_Type", inputArray[i]);
				break;
			case 19:
				searchData.put("From_Trans_Date", inputArray[i]);
				break;
			case 20:
				searchData.put("To_Trans_Date", inputArray[i]);
				break;
			case 21:
				searchData.put("Post_Period", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		//// run query and perform search
		HashMap<Integer, HashMap<String, String>> dbData = getDataSetforSearch(searchData);
		if (dbData.size() != Integer.parseInt(iterations)) {

		}

		///// run code for search
		searchContractGivenInputParamaters(searchData);

	}
}
