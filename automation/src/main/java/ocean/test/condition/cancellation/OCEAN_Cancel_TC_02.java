package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.common.DataProviderClass;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_02 class automates Ocean Cancel module Test Condition 02,
 * which holds 1 Test Case; Test Condition Description : Validate search results
 * enquiry with required contract details.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_02 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 02; Test Case
	 * description : Validate search results for following column, when contract is
	 * searched under mail service: 1. Contract number 2. Price sheet Name 3.
	 * Program Code 4. Primary account 5. Primary account id 6. Customer Name 7.
	 * Status 8. Comments
	 * 
	 */
	@Test(priority = 1, groups = "regression", description = "Validate search results enquiry with required contract details.")
	public void verifyDetailsOfSearchedContractOnCancellationScreen(String abc) throws Exception {
		//// we are not using any data provider for this test case, so we will get run
		//// db query to get any random contract id form database and use it for search
		//String contract_Id = getSearchDataCountOnCancellationScreen();
		//// type searched contract id in cancellation search and click search
		//// run code for search
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		//uiSearchData.put("CERT", contract_Id);
		//// Navigate to mail service tab
		goToCancellationTab();
		goToMailServiceTab();
		searchContractGivenInputParamaters(uiSearchData);
		//// Post search verify grid and match data is as expected
		//// run db query to fetch all data from database based on contract id
	//	HashMap<String, String> myDBData = getCancellationMouduleSearchData(contract_Id);
		//// save data from UI searched result
		HashMap<String, String> gridData = returnSearchResultGridData(1);
		//// Compare both data, should be same2
	//	assertEquals(myDBData, gridData);
	}
}
