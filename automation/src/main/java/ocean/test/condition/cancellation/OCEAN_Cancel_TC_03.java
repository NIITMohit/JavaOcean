package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.common.DataProviderClass;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_03 class automates Ocean Cancel module Test Condition 03,
 * which holds 2 Test Case; Test Condition Description : Validate creation and
 * blocking of cancellation request on the basis of contract status.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_03 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 02; Test Case
	 * description : Validate creation and blocking of cancellation request on the
	 * basis of contract status.
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchCancelStatusData", dataProviderClass = DataProviderClass.class, description = "Validate creation and blocking of cancellation request on the basis of contract status. ")
	public void verifyCancelButtonStatusBasesOnCancellationStatus(String status) throws Exception {
		///// get contract id from db bases on status of contract
		String contractId = getContractIdBasedOnStatus(status);
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("CERT", contractId);
		//// Navigate to mail service tab
		goToCancellationTab();
		goToMailServiceTab();
		//// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		//// verify status and contractId
		boolean myStatus = verifyContractAndStatus(contractId, status);
		//// verify search result data
		assertEquals(myStatus, true);
	}

}
