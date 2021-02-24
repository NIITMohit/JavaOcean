package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.SearchDataProvider;
import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Transaction_History_Search_TC_25 class automates Ocean Transaction
 * History Search module Test Condition OCEAN_Search_TC25,TC26 ,TC27 and TC28
 * which holds 21 Test Cases; Test Condition Description : Validate Transacion
 * Histoy Search sheet content after their import into OCEAN
 * 
 * @author Poonam kalra
 * 
 * @reviewer : Poonam/ Atul
 */
public class OCEAN_Search_TC_25_26_27_28 extends SearchModulePages {

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression", "extendSmoke", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_25", dataProviderClass = SearchDataProvider.class, description = "Search Transaction History based on parameter given in transaction history search sheet under Search Section")
	public void searchTransactionHistoryWithAnyInputField(String inputArray[]) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = search_TransactionHistory(
					cancellation_Transaction_HistorySearch_appendSearchData(inputArray));
		} else {
			uiSearchData = cancellation_Transaction_HistorySearch_convertDataRemoveStar(inputArray);
		}
		goToSearchTab();
		click("transactionHistoryTab");
		waitForSomeTime(5);
		searchContractGivenInputParamatersTransactionHistory(uiSearchData);
		waitForSomeTime(10);
		// get count from DB
		int dbCount = Integer.parseInt(searchCountFromDatabaseTransactionHistory(uiSearchData).get("count"));
		int iterations = dbCount;
		System.out.println("dbcount--->" + dbCount);
		if (dbCount > 3) {
			iterations = 2;
		}
		waitForSomeTime(25);
		// int uiCount = listOfElements("listRowCount").size();
		//// get data from Db for contract id
		List<Map<String, String>> dbMap = searchFromDatabaseTransactionHistory(uiSearchData);
		System.out.println("Inside DB Loop--->" + dbMap);
		if (dbMap.size() > 0) {
			for (int i = 0; i < iterations; i++) {
				//// get All data for ui search results
				List<Map<String, String>> uiMap = getSearchResultTransactionHistory(i);
				Map<String, String> uiMap1 = uiMap.get(0);
				System.out.println(uiMap1);
				if (!dbMap.contains(uiMap1)) {
					matchFlag = false;
					break;
				} else {
					matchFlag = true;
					continue;
				}
			}
			assertEquals(matchFlag, true);
		}

	}

	@Test(priority = 2, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_25", dataProviderClass = SearchDataProvider.class, description = "Validate Clear button ,to clear all search result.")
	public void clearContractSearchResult(String inputArray[]) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = search_TransactionHistory(
					cancellation_Transaction_HistorySearch_appendSearchData(inputArray));
		} else {
			uiSearchData = cancellation_Transaction_HistorySearch_convertDataRemoveStar(inputArray);
		}
		// run code for search
		goToSearchTab();
		click("transactionHistoryTab");
		waitForSomeTime(5);
		searchContractGivenInputParamatersTransactionHistory(uiSearchData);
		waitForSomeTime(15);
		// get count from UI
		int uiCount = listOfElements("listRowCount").size();
		if (uiCount > 1) {
			dbFlag = true;
			click("clearTransHist");

			try {
				int uiCountClear = listOfElements("listRowCount").size();
				if (uiCountClear >= 1) {
					matchFlag = false;
				}
			} catch (Exception e) {
				matchFlag = true;
			}
			assertEquals(matchFlag, true);
		}
		assertEquals(dbFlag, true);
	}
}