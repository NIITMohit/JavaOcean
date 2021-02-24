package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_42 class automates NEW PBI 15863 : , which holds 9 Test Case;
 * Test Condition Description : Verify following fields are displayed correctly
 * for selected contract only (from search results) under section named
 * AllCancel Transaction History: 1. Contract 2. Cancel Status 3. Adj. Type 4.
 * Process Date ( without timestamp) 5. Refund% 6. Net Refund 7. Cancel Miles 8.
 * Cancel Date 9. Initiated By 10. Cancel reason.
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Cancel_TC_42 extends CancellationModulePages {
	/**
	 * This function automates all test cases of NEW PBI 15863 Case description :
	 * AllCancel Transaction History
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke1", "fullSuite" }, description = "AllCancel Transaction History")
	public void AllCancelTransactionHistory() throws Exception {
		HashMap<String, String> contractData = cancellation_getContractIdforPBI15863();
		if (contractData.size() > 0) {
			String contractId = contractData.get("Contract");
			String allSalesID = contractData.get("id");
			HashMap<Integer, HashMap<String, String>> uiData = new HashMap<Integer, HashMap<String, String>>();
			// Navigate to mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			contractData.remove("ID");
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractData.get("Contract"));
			searchContractGivenInputParamaters(uiSearchData);
			clickSearchContractFromCancellationSearchResult(contractData);
			click("scrollPageDownForCancelTransHistory");
			HashMap<Integer, HashMap<String, String>> dbData = getAllCancelTransHistoryData(contractId, allSalesID);
			logger.info("Data from DB: " + dbData);
			int dbCount = dbData.size();
			for (int i = 0; i < dbCount; i++) {
				HashMap<String, String> uiMap = getSearchResultForAllCancelTransHistoryData(i);
				int j = i + 1;
				uiData.put(j, uiMap);
			}
			logger.info("Data from View: " + uiData);
			// validate data as as per database
			boolean compareResult = compareValues(dbData, uiData);
			if (compareResult) {
				logger.info("OCEAN is displaying correct AllCancel Transaction History Data");
			} else {
				logger.info("OCEAN is not displaying correct AllCancel Transaction History Data");
			}
			assertEquals(compareResult, true);

		} else {
			throw new SkipException("no such contract found");
		}
	}

	/**
	 * Verify that OCEAN update existing cancellation request only, if user update
	 * cancel status as Authorized/Hold/Denied. For Testcases: TFS ID 15863 Test
	 * case 12, TFS ID 15863 Test case 13, TFS ID 15863 Test case 14,TFS ID 15863
	 * Test case 19
	 */
	@Test(priority = 1, groups = { "regression", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForPBI15863", dataProviderClass = CancellationDataProvider.class, description = "Verify that OCEAN update existing cancellation request only, if user update cancel status as Authorized/Hold/Denied.")
	public void ValidateExistingCancellationRequestForCancelContractHistory(String[] imp) throws Exception {
		String actionPerform = imp[0];
		HashMap<String, String> contractData = new HashMap<String, String>();
		HashMap<String, String> contractData1 = new HashMap<String, String>();
		// Search for an active contract and Reinstated Contract
		contractData = cancellation_getContractIdforStatus("Active", "Reinstated");
		// If not found find an active contact, cancel it and reinstate
		if (contractData.size() == 0)
			contractData1 = cancellation_getContractIdforStatus("Active", "");

		if (contractData.size() > 0 || contractData1.size() > 0) {
			String contractId = "";
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			// Process when no active-reinstated contract is found
			if (contractData1.size() > 0) {
				contractData = contractData1;
				contractId = contractData.get("Contract");
				// create search data in hash map
				uiSearchData.put("CERT", contractId);
				// Search active contract and cancel the contract
				processSearchContract(uiSearchData, contractData, "Cancel");
				selectCancellationTaskStatus("authorize");
				// Process To Reinstate a Cancelled contract
				processReinstateContract(contractId);
				processForAccountSearchForContractReinstated(contractId);
			}

			// Again Search the reinstated contract
			contractId = contractData.get("Contract");
			uiSearchData.put("CERT", contractId);
			processSearchContract(uiSearchData, contractData, "");
			click("clickSelectCancellationHistory");
			HashMap<String, String> dbDataContractHistoryDataCount = cancellation_getContractCount(
					contractData.get("ID"));
			int oldDbCountdbDataContractHistory = Integer.parseInt(dbDataContractHistoryDataCount.get("count"));
			int dbDataAllCancelTransHistoryDataOldCount = 0;
			if (actionPerform.equals("authorize")) {
				dbDataAllCancelTransHistoryDataOldCount = getAllCancelTransHistoryData(contractId,
						contractData.get("ID")).size();
			}
			logger.info("oldDbCountdbDataContractHistory: " + oldDbCountdbDataContractHistory);
			// perform Authorize/Hold/Denied Action
			waitForSomeTime(15);
			selectCancellationTaskStatus(actionPerform);
			String actionPerformedStatus = "";
			switch (actionPerform.toLowerCase()) {
			case "authorize":
				actionPerformedStatus = "Authorized";
				break;
			case "hold":
				actionPerformedStatus = "OnHold";
				break;
			case "denied":
				actionPerformedStatus = "Denied";
				break;
			default:
				// do nothing
			}
			clickSearchContractFromCancellationSearchResult(contractData);
			click("checkContractOnCancellationHistory");

			// Validating data from Contract History DB
			HashMap<String, String> dbDataContractHistoryDataNew = cancellation_getContractHistoryData(
					contractData.get("ID"));
			dbDataContractHistoryDataNew = dataFormatAsUI(dbDataContractHistoryDataNew);

			// Validating data from Contract History Grid
			HashMap<String, String> contractHistoryViewNew = checkContractHistoryDetails();
			dbDataContractHistoryDataCount = cancellation_getContractCount(contractData.get("ID"));
			int newDbCountdbDataContractHistory = Integer.parseInt(dbDataContractHistoryDataCount.get("count"));

			boolean result1 = oldDbCountdbDataContractHistory == newDbCountdbDataContractHistory;
			boolean result2 = compareValuesforHashMap(dbDataContractHistoryDataNew, contractHistoryViewNew);
			boolean result3 = dbDataContractHistoryDataNew.get("Status").equalsIgnoreCase(actionPerformedStatus);

			if (!actionPerform.equals("authorize")) {
				if (result1 && result2 && result3)
					logger.info("OCEAN updates existing cancellation request only if user update cancel status as "
							+ actionPerform);
				else
					logger.info("OCEAN doesn't update existing cancellation request,if user update cancel status as "
							+ actionPerform);

				assertEquals(result1, true);
				assertEquals(result2, true);
				assertEquals(result3, true);
			} else {
				boolean result4 = false;
				boolean result5 = false;
				if (cancellation_getContractStatus(contractId).equalsIgnoreCase("Cancelled")
						&& getValue("listOfContractStatus").equalsIgnoreCase("Cancelled"))
					result4 = true;

				HashMap<Integer, HashMap<String, String>> dbDataAllCancelTransHistoryData = getAllCancelTransHistoryData(
						contractId, contractData.get("ID"));
				click("scrollPageDownForCancelTransHistory");
				HashMap<Integer, HashMap<String, String>> viewDataAllCancelTransHistoryData = new HashMap<Integer, HashMap<String, String>>();
				int dbCount = dbDataAllCancelTransHistoryData.size();
				for (int i = 0; i < dbCount; i++) {
					HashMap<String, String> uiMap = getSearchResultForAllCancelTransHistoryData(i);
					int j = i + 1;
					viewDataAllCancelTransHistoryData.put(j, uiMap);
				}
				int dbDataAllCancelTransHistoryDataCount = viewDataAllCancelTransHistoryData.size();
				if ((dbDataAllCancelTransHistoryDataCount == dbDataAllCancelTransHistoryDataOldCount + 1)
						&& compareValues(dbDataAllCancelTransHistoryData, viewDataAllCancelTransHistoryData)) {
					result5 = true;
				}
				assertEquals(result1, true);
				assertEquals(result2, true);
				assertEquals(result3, true);
				assertEquals(result4, true);
				assertEquals(result5, true);

				if (result1 && result2 && result3 && result4 && result5)
					logger.info(
							"OCEAN updates existing cancellation request only and displays a new record in All cancel trans history, if user update cancel status as "
									+ actionPerform);
				else
					logger.info("OCEAN doesn't update existing cancellation request,if user update cancel status as "
							+ actionPerform);
			}

		} else {
			throw new SkipException("no such contract found");
		}
	}

	/**
	 * Verify that OCEAN display a new record in All cancel trans history, when
	 * cancellation is authorized first time for a contract. For Testcase : TFS ID
	 * 15863 Test case 17
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke1",
			"fullSuite" }, description = "Verify that OCEAN display a new record in All cancel trans history,  when cancellation is authorized first time for a contract.")
	public void ValidateExistingCancellationRequestForAllCancelTransactionHistoryForActive() throws Exception {
		//// Search a cancelled contract on the basis of Status and sub Status
		HashMap<String, String> contractData = cancellation_getContractIdforStatus("Active", "");
		if (contractData.size() > 0) {
			String contractId = contractData.get("Contract");
			// Data before Contract Cancellation
			HashMap<String, String> dbDataContractHistoryDataOld = cancellation_getContractCount(
					contractData.get("ID"));
			int dbDataContractHistoryDataOldCount = Integer.parseInt(dbDataContractHistoryDataOld.get("count"));
			int dbDataAllCancelTransHistoryDataOldCount = getAllCancelTransHistoryData(contractId,
					contractData.get("ID")).size();

			// Search active contract and cancel the contract
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractId);
			processSearchContract(uiSearchData, contractData, "Cancel");
			selectCancellationTaskStatus("authorize");
			clickSearchContractFromCancellationSearchResult(contractData);

			boolean checkContractStatus = false;
			boolean checkContractHistoryData = false;
			boolean checkAllCancelTransHistoryData = false;

			// Data after Contract Cancellation from DB
			HashMap<String, String> dbDataContractHistoryData = cancellation_getContractHistoryData(
					contractData.get("ID"));
			dbDataContractHistoryData = dataFormatAsUI(dbDataContractHistoryData);
			HashMap<Integer, HashMap<String, String>> dbDataAllCancelTransHistoryData = getAllCancelTransHistoryData(
					contractId, contractData.get("ID"));

			// Data after Contract Cancellation from UI
			HashMap<String, String> contractHistoryView = checkContractHistoryDetails();
			HashMap<Integer, HashMap<String, String>> viewDataAllCancelTransHistoryData = new HashMap<Integer, HashMap<String, String>>();
			int dbCount = dbDataAllCancelTransHistoryData.size();

			click("scrollPageDownForCancelTransHistory");
			for (int i = 0; i < dbCount; i++) {
				HashMap<String, String> uiMap = getSearchResultForAllCancelTransHistoryData(i);
				int j = i + 1;
				viewDataAllCancelTransHistoryData.put(j, uiMap);
			}

			// Data count after Contract Cancellation
			HashMap<String, String> dbDataContractHistoryDataForCount = cancellation_getContractCount(
					contractData.get("ID"));
			int dbDataContractHistoryDataCount = Integer.parseInt(dbDataContractHistoryDataForCount.get("count"));
			int dbDataAllCancelTransHistoryDataCount = getAllCancelTransHistoryData(contractId, contractData.get("ID"))
					.size();

			clickSearchContractFromCancellationSearchResult(contractData);

			// Validating contract status
			if (cancellation_getContractStatus(contractId).equalsIgnoreCase("Cancelled")
					&& getValue("listOfContractStatus").equalsIgnoreCase("Cancelled")) {
				checkContractStatus = true;
			}
			logger.info("checkContractStatus: " + checkContractStatus);

			logger.info("dbDataContractHistoryDataCount: " + dbDataContractHistoryDataCount);
			logger.info("dbDataContractHistoryDataOldCount: " + dbDataContractHistoryDataOldCount);
			logger.info("dbDataContractHistoryData: " + dbDataContractHistoryData);
			logger.info("contractHistoryView: " + contractHistoryView);
			// Validating Contract History Data
			if ((dbDataContractHistoryDataCount == dbDataContractHistoryDataOldCount + 1)
					&& compareValuesforHashMap(dbDataContractHistoryData, contractHistoryView)
					&& dbDataContractHistoryData.get("Status").equalsIgnoreCase("Authorized")) {
				checkContractHistoryData = true;
			}
			logger.info("checkContractHistoryData: " + checkContractHistoryData);

			logger.info("dbDataAllCancelTransHistoryDataCount: " + dbDataAllCancelTransHistoryDataCount);
			logger.info("dbDataAllCancelTransHistoryDataOldCount: " + dbDataAllCancelTransHistoryDataOldCount);
			logger.info("dbDataAllCancelTransHistoryData: " + dbDataAllCancelTransHistoryData);
			logger.info("viewDataAllCancelTransHistoryData: " + viewDataAllCancelTransHistoryData);
			// Validating Contract All Cancel Trans History Data
			if ((dbDataAllCancelTransHistoryDataCount == dbDataAllCancelTransHistoryDataOldCount + 1)
					&& compareValues(dbDataAllCancelTransHistoryData, viewDataAllCancelTransHistoryData)) {
				checkAllCancelTransHistoryData = true;
			}
			logger.info("checkAllCancelTransHistoryData: " + checkAllCancelTransHistoryData);

			if (checkContractStatus && checkContractHistoryData && checkAllCancelTransHistoryData)
				logger.info(
						"OCEAN displays a new record in All cancel trans history,  when cancellation is authorized first time for a contract.");
			else
				logger.info(
						"OCEAN doesn't display a new record in All cancel trans history,  when cancellation is authorized first time for a contract.");

			assertEquals(checkContractStatus, true);
			assertEquals(checkContractHistoryData, true);
			assertEquals(checkAllCancelTransHistoryData, true);

		} else {
			throw new SkipException("no such contract found");
		}
	}

	/**
	 * Verify that OCEAN display a new record in All cancel trans history, when a
	 * cancelled contract is reinstated. For Testcases:TFS ID 15863 Test case 18,
	 * TFS ID 15863 Test case 20
	 */
	@Test(priority = 1, groups = { "regression", "smoke1",
			"fullSuite" }, description = "Verify that OCEAN display a new record in All cancel trans history,  when a cancelled contract is reinstated. ")
	public void ValidateExistingCancellationRequestForAllCancelTransactionHistoryForReInstated() throws Exception {
		HashMap<String, String> contractData = new HashMap<String, String>();
		HashMap<String, String> contractData1 = new HashMap<String, String>();
		// Search for a cancelled contract
		contractData = cancellation_getContractIdforStatus("Cancelled", "");
		// If not found, search for active contract and then cancel
		if (contractData.size() == 0)
			contractData1 = cancellation_getContractIdforStatus("Active", "");

		if (contractData.size() > 0 || contractData1.size() > 0) {
			String contractId = "";
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			// Process when no active-reinstated contract is found
			if (contractData1.size() > 0) {
				contractData = contractData1;
				contractId = contractData.get("Contract");
				// create search data in hash map
				uiSearchData.put("CERT", contractId);
				// Search active contract and cancel the contract
				processSearchContract(uiSearchData, contractData, "Cancel");
				selectCancellationTaskStatus("authorize");

			}

			// Data before the Contract is Reinstated
			HashMap<String, String> dbDataContractHistoryDataOld = cancellation_getContractCount(
					contractData.get("ID"));
			int dbDataContractHistoryDataOldCount = Integer.parseInt(dbDataContractHistoryDataOld.get("count"));
			int dbDataAllCancelTransHistoryDataOldCount = getAllCancelTransHistoryData(contractId,
					contractData.get("ID")).size();

			// Process To Reinstate a Cancelled contract
			contractId = contractData.get("Contract");
			processReinstateContract(contractId);
			processForAccountSearchForContractReinstated(contractId);

			// Again Search the reinstated contract
			uiSearchData.put("CERT", contractId);
			processSearchContract(uiSearchData, contractData, "");

			boolean checkContractStatus = false;
			boolean checkContractHistoryData = false;
			boolean checkAllCancelTransHistoryData = false;

			// Data after Contract Cancellation from DB
			HashMap<String, String> dbDataContractHistoryData = cancellation_getContractHistoryData(
					contractData.get("ID"));
			dbDataContractHistoryData = dataFormatAsUI(dbDataContractHistoryData);
			HashMap<Integer, HashMap<String, String>> dbDataAllCancelTransHistoryData = getAllCancelTransHistoryData(
					contractId, contractData.get("ID"));

			// Data after Contract Cancellation from UI
			HashMap<String, String> contractHistoryView = checkContractHistoryDetails();
			HashMap<Integer, HashMap<String, String>> viewDataAllCancelTransHistoryData = new HashMap<Integer, HashMap<String, String>>();
			int dbCount = dbDataAllCancelTransHistoryData.size();

			click("scrollPageDownForCancelTransHistory");
			for (int i = 0; i < dbCount; i++) {
				HashMap<String, String> uiMap = getSearchResultForAllCancelTransHistoryData(i);
				int j = i + 1;
				viewDataAllCancelTransHistoryData.put(j, uiMap);
			}

			// Data count after Contract Cancellation
			HashMap<String, String> dbDataContractHistoryDataForCount = cancellation_getContractCount(
					contractData.get("ID"));
			int dbDataContractHistoryDataCount = Integer.parseInt(dbDataContractHistoryDataForCount.get("count"));
			int dbDataAllCancelTransHistoryDataCount = getAllCancelTransHistoryData(contractId, contractData.get("ID"))
					.size();

			clickSearchContractFromCancellationSearchResult(contractData);

			// Validating contract status
			if (cancellation_getContractStatus(contractId).equalsIgnoreCase("Active")
					&& getValue("listOfContractStatus").equalsIgnoreCase("Active")) {
				checkContractStatus = true;
			}
			logger.info("checkContractStatus: " + checkContractStatus);

			logger.info("dbDataContractHistoryDataCount: " + dbDataContractHistoryDataCount);
			logger.info("dbDataContractHistoryDataOldCount: " + dbDataContractHistoryDataOldCount);
			logger.info("dbDataContractHistoryData: " + dbDataContractHistoryData);
			logger.info("contractHistoryView: " + contractHistoryView);
			// Validating Contract History Data
			if ((dbDataContractHistoryDataCount == dbDataContractHistoryDataOldCount)
					&& compareValuesforHashMap(dbDataContractHistoryData, contractHistoryView)
					&& dbDataContractHistoryData.get("Status").equalsIgnoreCase("Quote")) {
				checkContractHistoryData = true;
			}
			logger.info("checkContractHistoryData: " + checkContractHistoryData);

			logger.info("dbDataAllCancelTransHistoryDataCount: " + dbDataAllCancelTransHistoryDataCount);
			logger.info("dbDataAllCancelTransHistoryDataOldCount: " + dbDataAllCancelTransHistoryDataOldCount);
			logger.info("dbDataAllCancelTransHistoryData: " + dbDataAllCancelTransHistoryData);
			logger.info("viewDataAllCancelTransHistoryData: " + viewDataAllCancelTransHistoryData);
			// Validating Contract All Cancel Trans History Data
			if ((dbDataAllCancelTransHistoryDataCount == dbDataAllCancelTransHistoryDataOldCount + 1)
					&& compareValues(dbDataAllCancelTransHistoryData, viewDataAllCancelTransHistoryData)) {
				checkAllCancelTransHistoryData = true;
			}
			logger.info("checkAllCancelTransHistoryData: " + checkAllCancelTransHistoryData);

			if (checkContractStatus && checkContractHistoryData && checkAllCancelTransHistoryData)
				logger.info(
						"OCEAN displays a new record in All cancel trans history and update cancel status as Quote, when a cancelled contract is reinstated");
			else
				logger.info(
						"OCEAN doesn't display a new record in All cancel trans history and update cancel status as Quote, when a cancelled contract is reinstated");

			assertEquals(checkContractStatus, true);
			assertEquals(checkContractHistoryData, true);
			assertEquals(checkAllCancelTransHistoryData, true);

		} else {
			throw new SkipException("no such contract found");
		}
	}

	/**
	 * This function move to the top of Mail Service page*
	 */
	@AfterMethod(alwaysRun = true)
	public void moveToTopCancellationMailServiceWindow() throws Exception {
		//// scroll up and clear data
		try {
			click("scrollPageUpForCancelPageTop");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
