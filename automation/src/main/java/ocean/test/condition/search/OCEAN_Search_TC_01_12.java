package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.SearchDataProvider;
import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Search_TC_01_12 class automates Ocean Search module Test Condition
 * 01-10, which holds 12 Test Cases; Test Condition Description : Validate
 * Search sheet content after their import into OCEAN
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Poonam/ Atul
 */
public class OCEAN_Search_TC_01_12 extends SearchModulePages {
	/**
	 * This function covers all search cases based on search parameters given from
	 * excel
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke","smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC01_12", dataProviderClass = SearchDataProvider.class, description = "Search test case,Contract should be Search from the given search sheet under Search Section.")
	public void searchContractWithAnyInputField(String[] inputArray) throws Exception {
		goToSearchTab();
		click("clickContractTab");
		boolean dbFlag = false;
		boolean matchFlag = false;
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			// run db query to get unique value, else no need
			// get search data value in a hashmap from data provider, all values would be
			// appendSearchData saved in searchData hash map same as in excel, all values
			// including *, Blanks
			uiSearchData = search_Search(cancellation_Search_appendSearchData(inputArray));
		} else {
			uiSearchData = cancellation_Search_convertDataRemoveStar(inputArray);
		}
		// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		// get count from search screen
		waitForSomeTime(10);
		int screenCount = getSearchesultsCount();
		if (screenCount == 0) {
			screenCount = getSearchesultsCount();
		}
		//// get count from DB
		HashMap<String, String> dbData = searchCountFromDatabase(uiSearchData);
		int dbCount = dbData.size();
		if (screenCount == dbCount) {
			dbFlag = true;
			int iterations = dbCount;
			if (dbCount > 2) {
				iterations = 2;
			}
			for (int i = 0; i < iterations; i++) {
				// get data from Db for contract id
				if (dbData.containsValue(getContractId(i))) {
					HashMap<String, String> dbMap = searchDetailsFromSearchData(getContractId(i));
					// convert date as per Ui format
					dbMap.put("Sale_Date", convertNewDateFormat(dbMap.get("Sale_Date")));
					dbMap.put("Trans_Date", convertNewDateFormat(dbMap.get("Trans_Date")));
					System.out.println("VINnumber===" + dbMap.get("VIN"));
					dbMap.put("VIN", "XXXXXXXXX"
							+ dbMap.get("VIN").substring(dbMap.get("VIN").length() - 8, dbMap.get("VIN").length()));
					// get All data for search results
					HashMap<String, String> uiMap = getSearchResult(i);
					uiMap.put("Sale_Date", convertNewDateForCompare(uiMap.get("Sale_Date")));
					uiMap.put("Trans_Date", convertNewDateForCompare(uiMap.get("Trans_Date")));
					// compare both data
					if (!dbMap.equals(uiMap)) {
						matchFlag = false;
						break;
					} else {
						matchFlag = true;
						continue;
					}
				} else {
					matchFlag = false;
					break;
				}
			}
			assertEquals(matchFlag, true);
		} else {
			assertEquals(dbFlag, true);
		}
//		verifyClearContractSearch();
	}

	/**
	 * This function compare the selected two search cases.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate OCEAN ability to provide user with correct contract details as read only for selected "
					+ "account from contract search results via compare option and clear compared contracts on user's request.")
	public void verifyCompareContract() throws Exception {
		HashMap<String, String> firstNameMap = searchFirstNameByCount();
		String firstName = firstNameMap.get("First_Name");
		goToSearchTab();
		waitForSomeTime(5);
		click("clickContractTab");
		waitForSomeTime(15);
		type("searchTypeFirstName", firstName);
		click("clickSearchInSearchPage");
		int screenCount = getSearchesultsCount();
		if (screenCount >= 2) {
			waitForSomeTime(10);
			click("firstRowChkBox");
			click("secondRowChkBox");
			String OceanPage = windowsDriver.getWindowHandle();
			waitForSomeTime(5);
			click("clickCompare");
			waitForSomeTime(15);
			Set<String> windowHandles = windowsDriver.getWindowHandles();
			verifyCompareContract(windowHandles, OceanPage);
			verifyClearCompared("clickClearContracts");
		} else {
			throw new Exception("Compare not possible due to less cert!");
		}
	}

	/**
	 * This function verify the downloaded excel.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate OCEAN ability to allow user to download (export) search "
					+ "results in excel report on local machine.")
	public void verifyExportExcel() throws Exception {
		HashMap<String, String> firstNameMap = searchFirstNameByCount();
		String firstName = firstNameMap.get("First_Name");
		goToSearchTab();
		waitForSomeTime(5);
		click("clickContractTab");
		waitForSomeTime(15);
		type("searchTypeFirstName", firstName);
		click("clickSearchInSearchPage");
		waitForSomeTime(18);
		click("clickExportInternal");
		verifyExportFile();
		click("clickExportExternal");
		verifyExportFile();
	}

	/**
	 * This function verify the PDF is opened.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate that User is able to view related PDF on a contract"
					+ " under contract search results and clear search results")
	public void verifyPDFImage() throws Exception {
		HashMap<String, String> firstNameMap = searchFirstNameByCount();
		String firstName = firstNameMap.get("First_Name");
		goToSearchTab();
		waitForSomeTime(5);
		click("clickContractTab");
		waitForSomeTime(15);
		type("searchTypeFirstName", firstName);
		click("clickSearchInSearchPage");
		waitForSomeTime(18);
		verifyPDF();
	}
}
