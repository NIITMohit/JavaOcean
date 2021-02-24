package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashMap;
import org.testng.annotations.Test;

import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Search_TC_16_17_18 class automates Ocean Search module Test Condition ,
 * which holds 25 Test Cases; Test Condition Description : Validate Search sheet
 * content after their import into OCEAN
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam/ Atul
 */
public class OCEAN_Search_TC_16_17_18 extends SearchModulePages {
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Search test case,Contract should be Search from the given search sheet under Search Section.")
	public void searchWebContractViewRelatedPDFWithInputField() throws Exception {
		@SuppressWarnings("unused")
		boolean dbFlag = false;
		boolean matchFlag = false;
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		String[] inputArray = { "*", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "" };
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = search_SearchWeb(cancellation_Web_ContractSearch_appendSearchData(inputArray));
		} else {
			uiSearchData = (cancellation_Web_Contract_Search_convertDataRemoveStar(inputArray));
		}
		// run code for search
		goToSearchTab();
		click("WebContractsTab");
		String flag = inputArray[23];
		System.out.println(uiSearchData);
		searchWebContractGivenInputParamatersWithCheckbox(uiSearchData, flag);
		waitForSomeTime(25);
		//// get count from DB
		uiSearchData.put("cFlag", flag);
		// get count from DB
		int dbCount = Integer.parseInt(searchCountFromDatabaseWebContract(uiSearchData).get("Rcount"));
		// get count from search screen
		int screenCount = getSearchCountWebContract();
		// get count from search screen
		if (screenCount == dbCount) {
			dbFlag = true;
			int iterations = dbCount;
			if (dbCount > 5) {
				iterations = 5;
			}
			boolean prsent = false;
			for (int i = 0; i < iterations; i++) {
				//// get data from Db for contract id
				click("listclickPDFBtnWebContract", i);
				prsent = checkIsDisplayed("fileDialogeBox");
				System.out.println(prsent);
				takeScreenshot();
				if (prsent == false) {
					matchFlag = false;
					break;
				} else {
					System.out.println(prsent);
					click("clickOK");
					matchFlag = true;
					continue;
				}
			}
			assertEquals(matchFlag, true);
		}
	}

	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Search test case,Contract should be Search from the given search sheet under Search Section.")
	public void compareWebContractWithAnyInputField() throws Exception {
		String[] inputArray = { "", "", "", "*", "*", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "false" };
		boolean dbFlag = false;
		boolean matchFlag = false;
//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;

		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks

			uiSearchData = search_SearchWeb(cancellation_Web_ContractSearch_appendSearchData(inputArray));
		} else {
			uiSearchData = (cancellation_Web_Contract_Search_convertDataRemoveStar(inputArray));
		}
		System.out.print(uiSearchData);
		// run code for search
		goToSearchTab();
		click("WebContractsTab");
		String flag = inputArray[23];
		// System.out.println(uiSearchData);
		searchWebContractGivenInputParamatersWithCheckbox(uiSearchData, flag);
		waitForSomeTime(25);
		//// get count from DB
		uiSearchData.put("cFlag", flag);
		// get count from DB
		int dbCount = Integer.parseInt(searchCountFromDatabaseWebContract(uiSearchData).get("Rcount"));
		// get count from search screen
		int screenCount = getSearchCountWebContract();
		if (screenCount == dbCount) {
			dbFlag = true;
			int iterations = dbCount;
			if (dbCount > 2) {
				iterations = 2;
			}
			for (int i = 0; i < iterations; i++) {
				//// get data from Db for contract id
				click("listcheckWebContract", i);
			}
			takeScreenshot();
			int compareListViweCount = listOfElements("listViewWebContract").size();
			if (compareListViweCount >= 1) {
				click("CompareWebContract");
				waitForSomeTime(15);

				// get data from Db for contract id
				HashMap<String, String> dbMap1 = new HashMap<String, String>();
				HashMap<String, String> dbMap2 = new HashMap<String, String>();
				dbMap1 = searchDetailsFromCompareWebContract(getContractIdWeb(0));
				if (iterations > 1)
					dbMap2 = searchDetailsFromCompareWebContract(getContractIdWeb(1));

				takeScreenshot();
				switchToNewWindow();
				takeScreenshot();

				// get data from Compare Contract for contract id
				HashMap<String, String> uiMap1 = getCompareSearchResultFirst(0);
				HashMap<String, String> uiMap2 = new HashMap<String, String>();
				if (iterations > 1)
					uiMap2 = getCompareSearchResultFirst(1);

				if (dbMap2.size() > 0) {
					if (dbMap1.equals(uiMap1) && dbMap2.equals(uiMap2))
						matchFlag = true;
				} else {
					if (dbMap1.equals(uiMap1))
						matchFlag = true;
				}
				assertEquals(matchFlag, true);
			}
		} else {
			assertEquals(dbFlag, true);
		}
		click("clickOnCloseButton");
	}

	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate compare contract grid, if user will click on clear button of web contract search screen instead of compare clear button.")
	public void clearSearchResultsAndValidateCompareContractGrid() throws Exception {
		String[] inputArray = { "", "", "", "*", "*", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "false" };
		boolean dbFlag = false;
		boolean matchFlag = false;
//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks

			uiSearchData = search_SearchWeb(cancellation_Web_ContractSearch_appendSearchData(inputArray));
		} else {
			uiSearchData = (cancellation_Web_Contract_Search_convertDataRemoveStar(inputArray));
		}
		// run code for search
		goToSearchTab();
		click("WebContractsTab");
		String flag = inputArray[23];
		// System.out.println(uiSearchData);
		searchWebContractGivenInputParamatersWithCheckbox(uiSearchData, flag);
		waitForSomeTime(25);
		//// get count from DB
		uiSearchData.put("cFlag", flag);
		// get count from DB
		int dbCount = Integer.parseInt(searchCountFromDatabaseWebContract(uiSearchData).get("Rcount"));
		// get count from search screen
		int screenCount = getSearchCountWebContract();
		System.out.println("screenCount: " + screenCount + "dbCount: " + dbCount);
		if (screenCount == dbCount) {
			dbFlag = true;
			int iterations = dbCount;
			if (dbCount > 2) {
				iterations = 2;
			}
			for (int i = 0; i < iterations; i++) {
				//// get data from Db for contract id
				click("listcheckWebContract", i);
			}
			takeScreenshot();
			int compareListViweCount = listOfElements("listViewWebContract").size();
			if (compareListViweCount == iterations) {
				click("clickClearBtnOnWebContractSearch");
				waitForSomeTime(15);
			}

			compareListViweCount = listOfElements("listViewWebContract").size();
			if (compareListViweCount == iterations) {
				matchFlag = true;
				assertEquals(matchFlag, true);
			} else {
				assertEquals(matchFlag, true);
			}
		} else {
			assertEquals(dbFlag, true);
		}
		click("SelectClearWebContract");
	}

	@Test(priority = 4, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate Ocean ability to Export datagrid in excel format.")
	public void validateExportFunctonality() throws Exception {
		@SuppressWarnings("unused")
		boolean dbFlag = false;
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		String[] inputArray = { "*", "", "", "", "", "", "", "", "", "", "", "", "*", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "" };
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = search_SearchWeb(cancellation_Web_ContractSearch_appendSearchData(inputArray));
		} else {
			uiSearchData = (cancellation_Web_Contract_Search_convertDataRemoveStar(inputArray));
		}
		// run code for search
		goToSearchTab();
		click("WebContractsTab");
		String flag = inputArray[23];
		System.out.println(uiSearchData);
		searchWebContractGivenInputParamatersWithCheckbox(uiSearchData, flag);
		waitForSomeTime(25);
		//// get count from DB
		uiSearchData.put("cFlag", flag);
		// get count from DB
		int dbCount = Integer.parseInt(searchCountFromDatabaseWebContract(uiSearchData).get("Rcount"));
		waitForSomeTime(10);
		/// get count from search screen
		int screenCount = getSearchCountWebContract();
		System.out.println("screenCount: " + screenCount + "dbCount: " + dbCount);
		if (screenCount == dbCount) {
			dbFlag = true;
		}
		// HashMap<String, String> uiMap = getSearchResultWebContract(0);
		click("exportBtnWebcontract");
		verifyExportFileWebContract(screenCount);
	}

}
