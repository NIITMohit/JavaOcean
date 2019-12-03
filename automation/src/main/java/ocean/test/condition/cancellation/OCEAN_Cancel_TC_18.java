package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_18 class automates Ocean Cancel module Test Condition 18 ,
 * which holds 3 Test Case; Test Condition Description : Validate user ability
 * to assign different payee type and input related payee information.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_18 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Authorize to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validation that OCEAN auto assign payee as per compliance rules on a contract after calculation.")
	public void validateAuthorizeContractForAnyPriceSheet(String pricesheet) throws Exception {
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("processed", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId.length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Repossession", "", "", "");
			click("clickOK");
			//// Authorize cancellation request
			selectCancellationTaskStatus("Authorize");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Authorize");
			assertEquals(cancelStatusActual, true);
		} else {
			new SkipException("no contract exist in db");
		}
	}
}
