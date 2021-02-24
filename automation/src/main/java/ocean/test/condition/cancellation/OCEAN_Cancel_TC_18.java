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
	@Test(priority = 2, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validation that OCEAN auto assign payee as per compliance rules on a contract after calculation.")
	public void validateAuthorizeContractForAnyPriceSheet(String[] s) throws Exception {
		String pricesheet = s[0];
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("active", pricesheet);
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
			click("okClick");
			//// Change Payee Details
			changePayeeDetails("Jack Ryan", "43 Stephenville St", "Massena", "NY", "13662");
			//// take screenshot of new values
			takeScreenshot();
			//// Authorize cancellation request
			selectCancellationTaskStatus("Authorize");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Authorize",contractId);
			HashMap<String, String> myData = new HashMap<String, String>();
			myData.put("PAYEE_NAME", "Jack Ryan");
			myData.put("PAYEE_ADDRESS", "43 Stephenville St");
			myData.put("PAYEE_CITY", "Massena");
			myData.put("PAYEE_STATE", "NY");
			myData.put("PAYEE_ZIP_CODE", "13662");
			HashMap<String, String> dbData = cancellation_getPayeeDetails(contractId);
			if (cancelStatusActual)
				assertEquals(dbData, myData);
			else
				assertEquals(true, false);
		} else {
			new SkipException("no contract exist in db");
		}
	}
}
