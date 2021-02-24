package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Cancel_TC_55 class automates Ocean Underwriting module Test Condition
 * 55, which holds 2 Test Case; Test Condition Description : Validate OCEAN
 * ability to search and display check details by: 1. User ID 2. Date range
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_UnderWriting_TC_55 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 55 Case description
	 * : Validate OCEAN ability to search and display check details by 1. User ID 2.
	 * Date range
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_55", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Account search on the basis of search parameter given.")
	public void businessProcessorSeacrh(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		//// Navigate to mail service tab
		goToUnderWritingTab();
		goToBusinessProcessorTab();
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hash map from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = businessProcessor_Search(businessProcessor_Search_appendSearchData(inputArray));

		} else {
			uiSearchData = businessProcessor_Search_convertDataRemoveStar(inputArray);
		}

		if (uiSearchData.get("From_Date") != null && uiSearchData.get("From_Date") != "") {
			String fromDate = convertDate(uiSearchData.get("From_Date"), 0);
			uiSearchData.replace("From_Date", fromDate);
		} else {
			clearTextBox("typeFromDate");
		}
		if (uiSearchData.get("To_Date") != null && uiSearchData.get("To_Date") != "") {
			String toDate = convertDate(uiSearchData.get("To_Date"), 0);
			uiSearchData.replace("To_Date", toDate);
		} else {
			clearTextBox("typeToDate");
		}

		if (getAttributeValue("getCreadtedByValueForDB", "Name") != null) {
			uiSearchData.replace("CreatedBy",
					getAttributeValue("getCreadtedByValueForDB", "Name") + "," + uiSearchData.get("CreatedBy"));
		}
		//// run code for search
		searchBusinessProcessorGivenInputParamaters(uiSearchData);

		//// Compare DB data and match data from database
		HashMap<String, String> dataBaseMap = new HashMap<String, String>();
		String remittanceNo = getFirstSearchResultData();
		dataBaseMap.put("remittance_number", remittanceNo);
		dataBaseMap.put("From_Date", getAttributeValue("getFromDateValue", "Name"));
		dataBaseMap.put("To_Date", getAttributeValue("getToDateValue", "Name"));
		dataBaseMap.put("Created_By", getAttributeValue("getCreadtedByValueForDB", "Name"));

		HashMap<String, String> DBData = busineerProcessor_getbusineerProcessorSearchData(dataBaseMap);
		if (!DBData.isEmpty()) {
			String createDate = convertDate(DBData.get("created_Date"), 0);
			DBData.replace("created_Date", createDate);
		}
		System.out.println("db data: " + DBData);

		Thread.sleep(1000);
		//// save data from UI searched result
		HashMap<String, String> searchData = returnBusinessProcessorSearchResultGridData();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		searchData.replace("created_Date", dateFormat.format(dateFormat.parse(searchData.get("created_Date"))));
		System.out.println("UI data: " + searchData);
		System.out.println("UI data: " + searchData);
		//// verify both data, must match
		assertEquals(DBData.equals(searchData), true, "Data Not matched.");
		logger.info("Data matched successfully. ");
	}

}
