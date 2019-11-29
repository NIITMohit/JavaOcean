package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationDataProvider;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_19 class automates Ocean Cancel module Test Condition 16 ,
 * which holds 5 Test Case; Test Condition Description : Validate user ability
 * to assign different payee type and input related payee information.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_19 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Authorize to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as Authorize to cancellation request.")
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

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as On hold to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as On hold to cancellation request.")
	public void validateOnHoldContractForAnyPriceSheet(String pricesheet) throws Exception {
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
			selectCancellationTaskStatus("hold");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("hold");
			assertEquals(cancelStatusActual, true);
		} else {
			new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Quote to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as Quote to cancellation request.")
	public void validateQuoteContractForAnyPriceSheet(String pricesheet) throws Exception {
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
			selectCancellationTaskStatus("Quote");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Quote");
			assertEquals(cancelStatusActual, true);
		} else {
			new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Denied to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as Denied to cancellation request.")
	public void validateDeniedContractForAnyPriceSheet(String pricesheet) throws Exception {
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
			selectCancellationTaskStatus("Denied");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Denied");
			assertEquals(cancelStatusActual, true);
		} else {
			new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Delete to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as Delete to cancellation request.")
	public void validateDeleteContractForAnyPriceSheet(String pricesheet) throws Exception {
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
			selectCancellationTaskStatus("Delete");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Delete");
			assertEquals(cancelStatusActual, true);
		} else {
			new SkipException("no contract exist in db");
		}
	}
}
