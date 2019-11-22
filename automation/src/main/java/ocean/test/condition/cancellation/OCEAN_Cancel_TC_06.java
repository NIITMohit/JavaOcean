package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.common.DataProviderClass;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_06 class automates Ocean Cancel module Test Condition 06,
 * which holds 2 Test Case; Test Condition Description : Validate OCEAN ability
 * to assign and reassign cancel method on the basis of cancellation details.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_06 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 02; Test Case
	 * description : Validate creation and blocking of cancellation request on the
	 * basis of contract status.
	 * 
	 */
	@Test(priority = 1, groups = "regression", description = "Validate that OCEAN assign cancel method as Flat (F), if cancel date is within flat cancel period.")
	public void verifyFlatCancelRule() throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
		goToCancellationTab();
		goToMailServiceTab();
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("CERT", contractList.get("CERT"));
		//// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		clickCancelButtonAndNavigateToNewCancellationTab();
		enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "5000",
				convertDate(contractList.get("SALE_DATE"), 1), "11/22/2019");
	}

	// @Test(priority = 1, groups = "regression", description = "Validate that OCEAN
	// reassign cancel method on a contract, if cancel date is modified by user as
	// outside flat cancel period.")
	public void verifyOutsideFlatCancelRule() throws Exception {
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
		goToCancellationTab();
		goToMailServiceTab();
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("CERT", contractList.get("CERT"));
		//// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		clickCancelButtonAndNavigateToNewCancellationTab();
		enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "5000",
				contractList.get("SALE_DATE"), "11/22/2019");
	}

}
