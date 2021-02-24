package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_24 class automates Ocean Cancel module Test Condition 24
 * which holds 4 Test Case; Test Condition Description : Validate input of check
 * comments in ocean and enquiry by different user.
 * 
 * @author Nainsi Jain
 */

public class OCEAN_Cancel_TC_24 extends CancellationModulePages {
	/**
	 * This function automates all test cases for test condition 24; Test Case
	 * description : Verify that OCEAN display correct check comments, if
	 * cancellation request is enquired by a different user.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_24", dataProviderClass = CancellationDataProvider.class, description = "Verify that OCEAN display correct check comments,for authorize/on hold/denied cancellation Request")
	public void validateInputCommentsForDifferentStatus(String[] imp) throws Exception {
		boolean dBFlag = false;
		String status = imp[0];
		System.out.println("Testcase for status : " + status);
		// get contract id from db bases on status of contract
		String contractId = cancellation_getContractIdBasedOnStatusToVerifyCheckComments(status);
		if (contractId != null) {
			System.out.println("contractId: " + contractId);
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			waitForSomeTime(10);
			uiSearchData.put("CERT", contractId);
			// Navigate to cancellation tab
			goToCancellationTab();
			// Navigate to mail service tab
			goToMailServiceTab();
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			// verify status and contractId
			boolean myStatus = verifyContractAndStatus(contractId, status);
			// verify search result data
			assertEquals(myStatus, true);

			// click on Select button
			click("clickOnCancellationSelectButton");
			// Get contract id at row i
			String contract_Id = getFirstContractId(0);
			// get data for contract id at row 0 for contract compare with db data
			HashMap<String, String> myDBData = cancellation_getCancellationMouduleSearchDataForCheckComments(
					contractId);
			String dBCheckComment = myDBData.get("CHECK_COMMENTS").toString();
			String[] dBComment = dBCheckComment.split("\\W+");
			waitForSomeTime(10);
			String gridData = returnCheckCommentsResultGridData();
			System.out.println(gridData);
			System.out.println(dBCheckComment);
			String[] gridDataCheckComments = gridData.split("\\W+");
			logger.info("dBCheckComment======>>" + dBCheckComment);
			logger.info("gridDataText======>>" + gridData);
			if (gridDataCheckComments.length == dBComment.length) {
				dBFlag = Arrays.equals(gridDataCheckComments, dBComment);
			}
			// verify both data, must match
			assertEquals(dBFlag, true);
		} else {
			throw new SkipException("no value exist in db for status = " + status);
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression", "smoke",
			"fullSuite" }, dataProviderClass = CancellationDataProvider.class, description = "Verify that contract status is updated as Cancelled after check details updation for an authorized cancellation request")
	public void validateContractStatusAfterCheckDetailsUpdation() throws Exception {
		DecimalFormat df = new DecimalFormat("0.00##");
		boolean dBFlag = false;
		String status = "Cancelled";
		System.out.println("Testcase for status : " + status);
		// get contract id from db bases on status of contract
		HashMap<String, String> dBSearch = cancellation_getContractIdBasedOnStatusToVerifyCheckDetails(status);
		String contractId = dBSearch.get("cert").toString();
		if (contractId != null) {
			System.out.println("contractId: " + dBSearch);
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			waitForSomeTime(10);

			uiSearchData.put("CERT", contractId);
			// Navigate to cancellation tab
			goToCancellationTab();
			// Navigate to mail service tab
			goToMailServiceTab();
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			waitForSomeTime(10);
			click("clickOnFirstRowContractNo");
			// click on Select button
			click("clickOnCancellationSelectButton");
			// Get contract id at row i
			String contract_Id = getFirstContractId(0);
			// get data for contract id at row 0 for contract compare with db data
			HashMap<String, String> myUIData = cancellation_getcheckDetail();
			myUIData.put("cert", contractId);
			waitForSomeTime(10);
			System.out.println(myUIData);
			// verify both data, must match
			if (myUIData.equals(dBSearch)) {
				dBFlag = true;
				System.out.println("Value Mathches");
			} else {
				System.out.println("Value not Mathched");
			}
			assertEquals(dBFlag, true);
		} else {
			throw new SkipException("no value exist in db for status = " + status);
		}

	}
}