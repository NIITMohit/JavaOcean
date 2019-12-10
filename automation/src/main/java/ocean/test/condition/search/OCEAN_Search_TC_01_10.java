package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import javax.mail.Flags.Flag;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.SearchDataProvider;
import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Search_TC_01_10 class automates Ocean Search module Test Condition
 * 01-10, which holds 10 Test Cases; Test Condition Description : Validate
 * Search sheet content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Search_TC_01_10 extends SearchModulePages {
	/**
	 * This function covers all search cases based on search parameters given from
	 * excel
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC01_10", dataProviderClass = SearchDataProvider.class, description = "Search test case,Contract should be Search from the given search sheet under Search Section.")
	public void searchContractWithAnyInputField(String[] inputArray) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = search_Search(cancellation_Search_appendSearchData(inputArray));
		} else {
			uiSearchData = cancellation_Search_convertDataRemoveStar(inputArray);
		}

		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);

		///// get count from search screen
		int screenCount = getSearchesultsCount();

		//// get count from DB
		int dbCount = Integer.parseInt(searchCountFromDatabase(uiSearchData).get("count"));

		if (screenCount == dbCount) {
			dbFlag = true;
			int iterations = dbCount;

			if (dbCount > 5) {
				iterations = 5;
			}
			for (int i = 0; i < iterations; i++) {
				//// get data from Db for contract id
				HashMap<String, String> dbMap = searchDetailsFromSearchData(getContractId(i));
				//// convert date as per Ui format
				dbMap.put("Sale_Date", convertDate(dbMap.get("Sale_Date"), 0));
				dbMap.put("Trans_Date", convertDate(dbMap.get("Trans_Date"), 0));
				//// convert Vin to Masked VIN as per UI
				dbMap.put("VIN", "XXXXXXXXX"
						+ dbMap.get("VIN").substring(dbMap.get("VIN").length() - 8, dbMap.get("VIN").length()));
				//// get All data for search results
				HashMap<String, String> uiMap = getSearchResult(i);
				String date = uiMap.get("Trans_Date");
				int index = date.indexOf(" ");
				if (index > 0)
					uiMap.put("Trans_Date", date.substring(0, index));
				//// compare both data
				if (!dbMap.equals(uiMap)) {
					matchFlag = false;
					break;
				} else {
					matchFlag = true;
					continue;
				}
			}
			assertEquals(matchFlag, true);
		}

		else {
			assertEquals(dbFlag, true);
		}
	}
}
