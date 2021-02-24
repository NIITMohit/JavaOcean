package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.SearchDataProvider;
import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Search_TC_13 class automates Ocean Search module Test Condition , which
 * holds 25 Test Cases; Test Condition Description : Validate Search sheet
 * content after their import into OCEAN
 * 
 * @author Poonam Kalra
 */
public class OCEAN_Search_TC_13_14_15 extends SearchModulePages {
	/**
	 * This function covers all search cases based on search parameters given from
	 * excel
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_13_14_15", dataProviderClass = SearchDataProvider.class, description = "Search test case,Contract should be Search from the given search sheet under Search Section.")
	public void searchWebContractWithAnyInputField(String[] inputArray) throws Exception {
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
		goToSearchTab();
		click("WebContractsTab");
		String flag = inputArray[23];
		System.out.println(uiSearchData);
		searchWebContractGivenInputParamatersWithCheckbox(uiSearchData, flag);
		waitForSomeTime(25);
		//// get count from DB
		uiSearchData.put("cFlag", flag);
		int dbCount = Integer.parseInt(searchCountFromDatabaseWebContract(uiSearchData).get("Rcount"));
		waitForSomeTime(15);
		///// get count from search screen
		int screenCount = getSearchCountWebContract();
		///// verify Screen Count and DB Count
		if (dbCount == screenCount) {
			dbFlag = true;
			int iterations = dbCount;
			// Limit Number of iteration
			if (dbCount >= 1) {
				iterations = 1;
				// Compare DB Map and UI Map for each iteration
				for (int i = 0; i < iterations; i++) {
					//// get data from Db for contract id
					HashMap<String, String> dbMap = searchDetailsFromSearchDataWebContract(getContractIdWeb(i));
					//// convert Vin to Masked VIN as per UI
					dbMap.put("VIN", "XXXXXXXXX"
							+ dbMap.get("VIN").substring(dbMap.get("VIN").length() - 8, dbMap.get("VIN").length()));
					System.out.println(dbMap);
					HashMap<String, String> uiMap = getSearchResultWebContract(i);
					System.out.print(uiMap);
					if (dbMap.equals(uiMap)) {
						matchFlag = true;
						break;
					} else {
						matchFlag = false;

					}
					assertEquals(matchFlag, true);
				}
			}
		} else {
			assertEquals(dbFlag, true);
		}
	}

	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_13_14_15", dataProviderClass = SearchDataProvider.class, description = "Verify By Cicking Cancel Button LoadContract Indicator disappear .")
	public void validateCancelBtn(String[] inputArray) throws Exception {

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
		goToSearchTab();
		click("WebContractsTab");
		String flag = inputArray[23];
		searchWebContractGivenInputParamatersWithCheckbox(uiSearchData, flag);
		uiSearchData.put("cFlag", flag);
		boolean result = verifyCancelBTn();
		System.out.println(result);
		assertEquals(result, true);
	}
}
