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
		String iterations = inputArray[0];
		//// get search data value in a hashmap
		HashMap<String, String> searchData = appendSearchData(inputArray);
		HashMap<Integer, HashMap<String, String>> dbData = getDataSetforSearch(iterations, searchData);
		if (dbData.size() != Integer.parseInt(iterations)) {
			///// append result with message : that iteration count is different with
			///// records searched from db, however running the same for all records
			///// searched in db
		}

		///// run code for search
		searchContractGivenInputParamaters(searchData);

	}
}
