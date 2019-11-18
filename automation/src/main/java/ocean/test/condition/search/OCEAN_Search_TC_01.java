package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.common.CommonFunctions;
import ocean.common.DataProviderClass;
import ocean.modules.pages.pricingModulePages;
import ocean.modules.pages.searchModulePages;

/**
 * OCEAN_Pricing_TC01 class automates Ocean Pricing module Test Condition 01,
 * which holds 1 Test Case; Test Condition Description : Validate price sheet
 * content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Search_TC_01 extends searchModulePages {
	@Test(priority = 1, groups = "regression", dataProvider = "searchContract", dataProviderClass = DataProviderClass.class)
	public void searchContractWithAnyInputFielsd(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hashmap from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = getDataSetforSearch(appendSearchData(inputArray));
		} else {
			uiSearchData = convertDataRemoveStar(inputArray);
		}

		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);

	}
}
