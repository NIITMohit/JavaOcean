package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_19 class automates Ocean Cancel module Test Condition 16 ,
 * which holds 5 Test Case; Test Condition Description : Validate user ability
 * to assign different payee type and input related payee information.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_19 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Authorize to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user	 to assign status as Authorize to cancellation request.")
	public void validateAuthorizeContractForAnyPriceSheet(String[] priscesheet) throws Exception {
		String pricesheet = priscesheet[0];
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("active", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId != null) {
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
			removeErrorMessages();
			//// Authorize cancellation request
			selectCancellationTaskStatus("Authorize");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Authorize", contractId);
			assertEquals(cancelStatusActual, true);
		} else {
			throw new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as On hold to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as On hold to cancellation request.")
	public void validateOnHoldContractForAnyPriceSheet(String[] priscesheet) throws Exception {
		String pricesheet = priscesheet[0];
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("active", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId != null) {
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
			removeErrorMessages();
			//// Authorize cancellation request
			selectCancellationTaskStatus("hold");
			cancelDenialCancelNewPopUp();
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("hold", contractId);
			assertEquals(cancelStatusActual, true);
		} else {
			throw new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Quote to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as Quote to cancellation request.")
	public void validateQuoteContractForAnyPriceSheet(String[] priscesheet) throws Exception {
		HashMap<String, String> contractList = new HashMap<String, String>();
		String pricesheet = priscesheet[0];
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("active", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId != null) {
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
			removeErrorMessages();
			//// Authorize cancellation request
			selectCancellationTaskStatus("Quote");
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Quote", contractId);
			assertEquals(cancelStatusActual, true);
		} else {
			throw new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Denied to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user to assign status as Denied to cancellation request.")
	public void validateDeniedContractForAnyPriceSheet(String[] priscesheet) throws Exception {
		String pricesheet = priscesheet[0];
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("active", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId != null) {
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
			removeErrorMessages();
			//// Authorize cancellation request
			selectCancellationTaskStatus("Denied");
			cancelDenialCancelNewPopUp();
			///// validation of successful authorization
			boolean cancelStatusActual = checkCancellationTaskStatus("Denied", contractId);
			assertEquals(cancelStatusActual, true);
		} else {
			throw new SkipException("no contract exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 19; Test Case
	 * description : Validate that OCEAN allow user to assign status as Delete to
	 * cancellation request.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke",
			"fullSuite" }, dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow user	 to assign status as Delete to cancellation request.")
	public void validateDeleteContractForAnyPriceSheet(String[] priscesheet) throws Exception {
		String pricesheet = priscesheet[0];
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("active", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId != null) {
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
			removeErrorMessages();
			//// Authorize cancellation request
			selectCancellationTaskStatus("Delete");
			click("cancelYes");
			///// validation of successful authorization
			goToMailServiceTab();
			boolean cancelStatusActual = checkCancellationTaskStatus("Delete", contractId);
			assertEquals(cancelStatusActual, true);
		} else {
			throw new SkipException("no contract exist in db");
		}
	}
}
