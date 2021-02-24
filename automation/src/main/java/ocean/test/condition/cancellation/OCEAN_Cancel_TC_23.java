package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_23 class automates Ocean Cancel module Test Condition 23 ,
 * which holds 2 Test Case; Test Condition Description : Verify that OCEAN allow
 * user to input and save (by authorize/on hold/denied) Internal comments and
 * share them with OYSTER after authorization of cancellation request. .
 * 
 * @author Atul Awasthi
 */

public class OCEAN_Cancel_TC_23 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 23; Test Case
	 * description : Verify that OCEAN allow user to input and save (by authorize/on
	 * hold/denied) Internal comments .
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke1",
			"fullSuite" }, description = "Verify that OCEAN allow user to input and save (by authorize/on hold/denied) Internal comments.")
	public void validateAuthorizeContractForInputAndSaveInternalComments() throws Exception {
		String contractId = "";
		// get contract id based for processed contract only with current year
		contractId = cancellation_getContractIdBasedOnStatus("active");
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// Enter details for cancellation
			processForContractCancellation(contractId);
			// validation of internal comments match
			boolean commentStatus = verifyInternalCommentStatusOnCancellation("clickAuthorize", contractId);
			assertEquals(commentStatus, false);
		}
	}

	/**
	 * This function automates test case for test condition 23; Test Case
	 * description : Verify that OCEAN allow user to input and save (by authorize/on
	 * hold/denied) Internal comments .
	 * 
	 */
	@Test(priority = 2, groups = { "regression","smoke1",
			"fullSuite" }, description = "Verify that OCEAN allow user to input and save (by authorize/on hold/denied) Internal comments.")
	public void validateOnHoldContractForInputAndSaveInternalComments() throws Exception {
		String contractId = "";
		// get contract id based for processed contract only with current year
		contractId = cancellation_getContractIdBasedOnStatus("active");
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// Enter details for cancellation
			processForContractCancellation(contractId);
			// validation of internal comments match
			boolean commentStatus = verifyInternalCommentStatusOnCancellation("clickHold", contractId);
			assertEquals(commentStatus, true);
		}
	}

	/**
	 * This function automates test case for test condition 23; Test Case
	 * description : Verify that OCEAN allow user to input and save (by authorize/on
	 * hold/denied) Internal comments .
	 * 
	 */
	@Test(priority = 2, groups = { "regression","smoke1",
			"fullSuite" }, description = "Verify that OCEAN allow user to input and save (by authorize/on hold/denied) Internal comments.")
	public void validateDeniedContractForInputAndSaveInternalComments() throws Exception {
		String contractId = "";
		// get contract id based for processed contract only with current year
		contractId = cancellation_getContractIdBasedOnStatus("active");
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// Enter details for cancellation
			processForContractCancellation(contractId);
			// validation of internal comments match
			boolean commentStatus = verifyInternalCommentStatusOnCancellation("clickDenied", contractId);
			assertEquals(commentStatus, true);
		}
	}
}
