package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationDataProvider;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_08_09 class automates Ocean Cancel module Test Condition 08
 * and 09, which holds 8 Test Case; Test Condition Description : Validate
 * contract summary information section for different contract statuses under
 * cancellation request.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_08_09 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 08; Test Case
	 * description : Validate contract summary section all fields as read only for
	 * all status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate contract summary section all fields as read only before cancellation of a contract")
	public void validateContractSummarySectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> dataForTC08 = cancellation_getDetailsForTC08(status);
		if (dataForTC08.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC08.get("Contract_Number"));
			dataForTC08.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Contract Summary");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> contractSummary = getContractSummary();
			//// validate data as as per db?
			assertEquals(dataForTC08, contractSummary);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

	/**
	 * This function automates test case for test condition 09; Test Case
	 * description : Validate contract details section all fields as read only for
	 * all status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate contract details section all fields as read only before cancellation of a contract")
	public void validateContractDetailsSectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		if (dataForTC09.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC09.get("Contract_Number"));
			dataForTC09.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Contract Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> contractDetails = getContractDetails();
			//// validate data as as per db?
			assertEquals(dataForTC09, contractDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

	/**
	 * This function automates test case for test condition 09; Test Case
	 * description : Validate primary account details section all fields as read
	 * only for all status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate primary account details section all fields as read only before cancellation of a contract")
	public void validatePrimaryAccountDetailsSectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		if (dataForTC09.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC09.get("Contract_Number"));
			dataForTC09.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Primary Account Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> primaryAccountDetails = getPrimaryAccountDetails();
			//// validate data as as per db?
			assertEquals(dataForTC09, primaryAccountDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

	/**
	 * This function automates test case for test condition 09; Test Case
	 * description : Validate Secondary account details section all fields as read
	 * only for all status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate secondary account details section all fields as read only before cancellation of a contract")
	public void validateSecondaryAccountDetailsSectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		if (dataForTC09.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC09.get("Contract_Number"));
			dataForTC09.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Secondary Account Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> secondaryAccountDetails = getSecondaryAccountDetails();
			//// validate data as as per db?
			assertEquals(dataForTC09, secondaryAccountDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

	/**
	 * This function automates test case for test condition 09; Test Case
	 * description : Validate customer details section all fields as read only for
	 * all status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate customer details section all fields as read only before cancellation of a contract")
	public void validateCustomerDetailsSectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		if (dataForTC09.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC09.get("Contract_Number"));
			dataForTC09.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Customer Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> customerDetails = getCustomerDetails();
			//// validate data as as per db?
			assertEquals(dataForTC09, customerDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

	/**
	 * This function automates test case for test condition 09; Test Case
	 * description : Validate VIN details section all fields as read only for all
	 * status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate VIN details section all fields as read only before cancellation of a contract")
	public void validateVINDetailsSectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		if (dataForTC09.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC09.get("Contract_Number"));
			dataForTC09.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Vehicle Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> VINDetails = getVINDetails();
			//// validate data as as per db?
			assertEquals(dataForTC09, VINDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

	/**
	 * This function automates test case for test condition 09; Test Case
	 * description : Validate Agent details section all fields as read only for all
	 * status of contract
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC08", dataProviderClass = CancellationDataProvider.class, description = "Validate Agent details section all fields as read only before cancellation of a contract")
	public void validateAgentDetailsSectionForAllStatusOfContract(String status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		if (dataForTC09.get("Contract_Number").length() > 0) {
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dataForTC09.get("Contract_Number"));
			dataForTC09.remove("Contract_Number");
			//// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// click cancel and navigate to cancellation tab
			if (status.toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Agent Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> agentDetails = getAgentDetails();
			//// validate data as as per db?
			assertEquals(dataForTC09, agentDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

}
