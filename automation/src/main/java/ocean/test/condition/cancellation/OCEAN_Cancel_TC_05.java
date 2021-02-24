package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_05 class automates Ocean Cancel module Test Condition 05,
 * which holds 2 Test Case; Test Condition Description : Validate user ability
 * to input and modify cancellation details.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Atul Awasthi
 */
public class OCEAN_Cancel_TC_05 extends CancellationModulePages {
	/**
	 * This function automates test case OCEAN_Cancel_T14 for test condition
	 * OCEAN_Cancel_TC05; Test Case description : Validate user ability to input and
	 * modify cancellation details.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, groups = { "regression", "smoke",
			"fullSuite" }, description = "Validate user ability to input and modify cancellation details.")
	public void VerifyCancellationDetailsUsingContractID() throws Exception {
		// Search Contract from db where status is processed
		String contractId = cancellation_getContractIdBasedOnStatus("Active");
		if (contractId != null) {
			HashMap<String, String> contractData = new HashMap<String, String>();
			contractData.put("CERT", contractId);
			goToCancellationTab();
			goToMailServiceTab();
			searchContractGivenInputParamaters(contractData);
			// This function is used to cancel a contract
			clickCancelButtonAndNavigateToNewCancellationTab();
			// This function is used to validate cancellation input fields
			boolean flag = validateInputFieldsValues("", "", "", "", "");
			if (!flag)
				assertEquals(flag, true);
			else {
				SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
				Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
				String datek = formatter.format(yesterday);
				enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Repossession", "9999999", datek, datek);
				removeErrorMessages();
				boolean newflag = validateInputFieldsValues("Dealer", "Repossession", "9999999", datek, datek);
				assertEquals(newflag, true);
			}
		}
	}
}
