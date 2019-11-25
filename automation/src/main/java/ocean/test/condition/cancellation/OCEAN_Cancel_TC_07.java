package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.common.DataProviderClass;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_07 class automates Ocean Cancel module Test Condition 07,
 * which holds 4 Test Case; Test Condition Description : Validate date range
 * check for cancel date with contract coverage dates.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_07 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 02; Test Case
	 * description : Validate creation and blocking of cancellation request on the
	 * basis of contract status.
	 * 
	 */
	@Test(priority = 5, groups = "sanity", description = "Validate that Ocean block user to authorize a cancellation, when user provide cancel date before sale date.")
	public void validateFutureCancelDate() throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
		} else {
			new SkipException("no value exist in db for SNE");
		}
	}

}
