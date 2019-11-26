package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationDataProvider;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_08 class automates Ocean Cancel module Test Condition 08,
 * which holds 2 Test Case; Test Condition Description : Validate contract
 * summary information section for different contract statuses under
 * cancellation request.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_08 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 08; Test Case
	 * description : Validate contract summary section all fields as read only
	 * before cancellation of a contract.
	 * 
	 */
	@Test(priority = 2, groups = "regression", description = "Validate contract summary section all fields as read only before cancellation of a contract")
	public void validateContractSummarySectionBeforeCancellationOfContract() throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> dataForTC08 = cancellation_getDetailsForTC08("cancelled");
		if (dataForTC08.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC08.get("Contract_Number"));
			dataForTC08.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Contract Summary");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> contractSummary = getContractSummary();
			//// validate data as as per db?
			assertEquals(dataForTC08, contractSummary);
		} else {
			new SkipException("no value exist in db for status = cancelled");
		}
	}

}
