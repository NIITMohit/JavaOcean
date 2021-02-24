package ocean.test.condition.cancellation;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_22 class automates Ocean Cancel module Test Condition 75 ,76
 * ,78 and 79, which holds 4 Test Case; Test Condition Description : Validate
 * contract search for cancellation on the basis of search parameter given and
 * Validate searched data from DB.
 * 
 * @author Poonam Kalra
 */
public class OCEAN_Cancel_TC_22 extends CancellationModulePages {
	/**
	 * This function automates all test cases OCEAN_Cancel_TC_22 for test condition
	 * 75; Test Case description : Validate contract search for cancellation on the
	 * basis of contract number and Role_Type Agent
	 * 
	 */
	@Test(priority = 1, groups = { "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC22", dataProviderClass = CancellationDataProvider.class, description = "Validate contract search for cancellation on the basis of contract number and Agent")
	public void validateAccountLevelWarningAgent(String[] inputData) throws Exception {
		goToCancellationTab();
		goToMailServiceTab();
		// Search Contract from db where status is processed
		HashMap<String, String> fetchDataForTC22 = get_ContractDetailsForTC22(inputData[0]);
		searchContractGivenInputParamaters(fetchDataForTC22);
		// This function is used to cancel a contract
		clickCancelButtonAndNavigateToNewCancellationTab();
		// This function is used to validate cancellation input fields
	}
}
