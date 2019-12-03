package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_04 class automates Ocean Cancel module Test Condition 04
 * which holds 5 Test Case; Test Condition Description : Validate cancellation
 * request details under cancellation history for different statuses Validate
 * searched data from DB.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_04 extends CancellationModulePages {
	/**
	 * This function automates all test cases for test condition 04; Test Case
	 * description : Validate cancellation request details under cancellation
	 * history for different statuses (authorized, quote, hold, etc.)
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC04", dataProviderClass = CancellationDataProvider.class, description = "Validate cancellation request details under cancellation history for different statuses.")
	public void verifycancelrequestOnCancelHistoryScreen(String status) throws Exception {
		try {
			// /// get contract id from db bases on status of contract
			HashMap<String, String> cancelDataForTC4 = cancellation_getContractHistoryBasedOnStatus(status);
			if (cancelDataForTC4.get("CUSTOMER_FIRST").length() > 0) {
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				uiSearchData.put("CUSTOMER_FIRST", cancelDataForTC4.get("CUSTOMER_FIRST"));
				cancelDataForTC4.remove("CUSTOMER_FIRST");
				cancelDataForTC4.put("Process_Date", convertDate(cancelDataForTC4.get("Process_Date"), 0));
				cancelDataForTC4.put("CANCEL_DATE", convertDate(cancelDataForTC4.get("CANCEL_DATE"), 0));
				// // Navigate to mail service tab
				goToCancellationTab();
				goToMailServiceTab();
				searchContractGivenInputParamaters(uiSearchData);
				// verifyContractAndStatus(contractId, status);
				clickSearchContractFromCancellationSearchResult(cancelDataForTC4);
				// click("checkContractOnCancellationHistory");
				HashMap<String, String> contractHistory = checkContractHistoryDetails();
				// // validate data as as per db?
				assertEquals(cancelDataForTC4, contractHistory);
			} else {
				new SkipException("no value exist in db for status");
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
