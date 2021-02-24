package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_08_09 class automates Ocean Cancel module Test Condition 08
 * and 09, which holds 8 Test Case; Test Condition Description : Validate
 * contract summary information section for different contract statuses under
 * cancellation request.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Atul Awasthi
 */
public class OCEAN_Cancel_TC_08_09 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 08; Test Case
	 * description : Validate contract summary section all fields as read only for
	 * all status of contract
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate contract summary section all fields as read only before cancellation of a contract")
	public void validateContractSummarySectionForAllStatusOfContract(String[] status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> dataForTC08 = cancellation_getDetailsForTC08(status[0]);
		float claimsPaid = Float.parseFloat(dataForTC08.get("Claims_Paid"));
		DecimalFormat df = new DecimalFormat("0.00");
		dataForTC08.put("Claims_Paid", df.format(claimsPaid));
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
			if (status[0].toLowerCase().equals("cancelled")) {
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
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate contract details section all fields as read only before cancellation of a contract")
	public void validateContractDetailsSectionForAllStatusOfContract(String[] status) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status[0]);
		System.out.println("allTestData==" + allTestData);
		allTestData.put("Process_Date", convertNewDateFormatTC09(allTestData.get("Process_Date")));
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
			if (status[0].toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Contract Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> contractDetails = getContractDetails();
			System.out.println("allTestData==" + allTestData);
			System.out.println("contractDetails==" + contractDetails);
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
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate primary account details section all fields as read only before cancellation of a contract")
	public void validatePrimaryAccountDetailsSectionForAllStatusOfContract(String status[]) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09_02(status[0]);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		System.out.println("dataForTC09===" + dataForTC09);
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
			if (status[0].toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Primary Account Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> primaryAccountDetails = getPrimaryAccountDetails();
			System.out.println("dataForTC09===" + dataForTC09);
			System.out.println("primaryAccountDetails===" + primaryAccountDetails);
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
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate secondary account details section all fields as read only before cancellation of a contract")
	public void validateSecondaryAccountDetailsSectionForAllStatusOfContract(String status[]) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09_03(status[0]);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		System.out.println("dataForTC09===" + dataForTC09);
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
			if (status[0].toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Secondary Account Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> secondaryAccountDetails = getSecondaryAccountDetails();
			System.out.println("dataForTC09===" + dataForTC09);
			System.out.println("secondaryAccountDetails===" + secondaryAccountDetails);
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
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate customer details section all fields as read only before cancellation of a contract")
	public void validateCustomerDetailsSectionForAllStatusOfContract(String status[]) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09_04(status[0]);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		System.out.println("dataForTC09===" + dataForTC09);
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
			if (status[0].toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Customer Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> customerDetails = getCustomerDetails();
			System.out.println("dataForTC09===" + dataForTC09);
			System.out.println("secondaryAccountDetails===" + customerDetails);
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
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate VIN details section all fields as read only before cancellation of a contract")
	public void validateVINDetailsSectionForAllStatusOfContract(String status[]) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09_05(status[0]);
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
			if (status[0].toLowerCase().equals("cancelled")) {
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
	@Test(priority = 2, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC08_09", dataProviderClass = CancellationDataProvider.class, description = "Validate Agent details section all fields as read only before cancellation of a contract")
	public void validateAgentDetailsSectionForAllStatusOfContract(String status[]) throws Exception {
		///// get contract id from db bases on status of contract
		HashMap<String, String> allTestData = cancellation_getDetailsForTC09(status[0]);
		//// append all data used in test case 09
		HashMap<String, String> dataForTC09 = allTestData;
		System.out.println("dataForTC09===" + dataForTC09);
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
			if (status[0].toLowerCase().equals("cancelled")) {
				clickCancelButtonAndNavigateToNewCancellationTab();
				clickselectButtonAndNavigateToNewCancellationTab();
			} else
				//// click select in cancellation history and proceed to new cancellation tab
				clickCancelButtonAndNavigateToNewCancellationTab();
			//// Expand Section for contract Summary
			toggleSectionsOnNewCancellationScreen("Agent Details");
			//// function to append all contract summary data in a hashmap
			HashMap<String, String> agentDetails = getAgentDetails();
			System.out.println("dataForTC09===" + dataForTC09);
			System.out.println("secondaryAccountDetails===" + agentDetails);
			//// validate data as as per db?
			assertEquals(dataForTC09, agentDetails);
		} else {
			new SkipException("no value exist in db for status = " + status);
		}
	}

}
