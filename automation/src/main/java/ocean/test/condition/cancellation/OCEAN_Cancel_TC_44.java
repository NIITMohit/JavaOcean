package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * PBI 9213 class automates NEW PBI 9213 : , which holds 7 Test Case; Test
 * Condition Description : Cancelled - Ocean - TBC
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_44 extends CancellationModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9135 Case description :
	 * Cancelled - Ocean - TBC
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForPBI9213", dataProviderClass = CancellationDataProvider.class, description = "Cancelled - Ocean - TBC")
	public void CancellationHistoryForDifferentCancellationStatus(String[] cancelStatus) throws Exception {
		HashMap<String, String> cancelDataForTC4 = cancellation_getContractHistoryBasedOnStatusSingleRecord(
				cancelStatus[0]);
		if (cancelDataForTC4.size() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", cancelDataForTC4.get("Contract"));
			cancelDataForTC4.remove("CUSTOMER_FIRST");
			cancelDataForTC4.put("Process_Date", convertDate(cancelDataForTC4.get("Process_Date"), 0));
			if (cancelDataForTC4.get("Status").equalsIgnoreCase("quote"))
				cancelDataForTC4.remove("Process_Date");
			cancelDataForTC4.put("CANCEL_DATE", convertDate(cancelDataForTC4.get("CANCEL_DATE"), 0));
			cancelDataForTC4.put("Net_Refund",
					cancelDataForTC4.get("Net_Refund").substring(0, cancelDataForTC4.get("Net_Refund").indexOf(".")));
			goToCancellationTab();
			goToMailServiceTab();
			searchContractGivenInputParamaters(uiSearchData);
			waitForSomeTime(3);
			click("listOfContractNumbers", 0);
			waitForSomeTime(3);
			if (cancelDataForTC4.get("Status").equalsIgnoreCase("authorized")) {
				String stateofbutton = checkEnableDisable("clickAdjustButton");
				assertEquals(stateofbutton.equalsIgnoreCase("true"), true);
			}
			HashMap<String, String> contractHistory = checkContractHistoryDetails();
			if (contractHistory.get("Status").equalsIgnoreCase("quote"))
				contractHistory.remove("Process_Date");
			contractHistory.put("Net_Refund",
					contractHistory.get("Net_Refund").substring(0, contractHistory.get("Net_Refund").indexOf(".")));
			// // validate data as as per db?
			assertEquals(cancelDataForTC4, contractHistory);
		} else {
			throw new SkipException("no matching data found");
		}
	}
}
