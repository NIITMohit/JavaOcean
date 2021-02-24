package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_03 class automates Ocean Cancel module Test Condition 03,
 * which holds 2 Test Case; Test Condition Description : Validate creation and
 * blocking of cancellation request on the basis of contract status.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Atul Awasthi
 * 
 */
public class OCEAN_Cancel_TC_03 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 02; Test Case
	 * description : Validate creation and blocking of cancellation request on the
	 * basis of contract status.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC03", dataProviderClass = CancellationDataProvider.class, description = "Validate creation and blocking of cancellation request on the basis of contract status. ")
	public void verifyCancelButtonStatusBasesOnCancellationStatus(String[] status) throws Exception {
		///// get contract id from db bases on status of contract
		String contractId = cancellation_getContractIdBasedOnStatus(status[0]);
		if (contractId != null) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractId);
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// verify status and contractId
			boolean myStatus = verifyContractAndStatus(contractId, status[0]);
			//// verify search result data
			assertEquals(myStatus, true);
			if (status[0].equalsIgnoreCase("active")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// validate active status
				String stat = getValue("getContractStatusOnNewCancellation");
				assertEquals(stat.equalsIgnoreCase("active"), true);
			}
		} else {
			throw new SkipException("no value exist in db for status = " + status);
		}
	}

}
