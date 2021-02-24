package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_PBI_15057 automates Ocean Underwriting module PBI 15057,
 * which holds 1 Test Case; Test Condition Description : Validate for following
 * columns with correct data value in exported excel for business processor
 * search result: 1. Check # 2. Check $ 3. Remittance Number 4. Remittance Name
 * 5. Core Count 6. LWA Count 7. Role ID 8. Account Name 9. Date Deposited 10.
 * Created By
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Underwriting_TC_63 extends UnderwritingModulePages {

	/**
	 * Validate for following columns export with correct data value in excel for
	 * business processor search result: 1. Check # 2. Check $ 3. Remittance Number
	 * 4. Remittance Name 5. Core Count 6. LWA Count 7. Role ID 8. Account Name 9.
	 * Date Deposited 10. Created By
	 * 
	 */
	@Test(priority = 1, groups = { "smoke", "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI15057", dataProviderClass = UnderwritingDataProvider.class, description = "Validate for following columns with correct data value in exported excel for business processor search result.")
	public void businessProcessorExportExcel(String[] inputArray) throws Exception {
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

		Thread.sleep(1000);

		// download export excel on desktop
		exportExcelOfBusinessProcessor();

		// get system date with format "MM-dd-yyyy HH"

		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH");
		Calendar calobj = Calendar.getInstance();
		String currentDate = df.format(calobj.getTime());
		String dateAsPerFile = currentDate.replaceAll(" ", "_");

		Thread.sleep(5000);
		String desktopPath = System.getProperty("user.home") + "\\Desktop";

		File source = new File(desktopPath);
		File[] desktopAllFiles = source.listFiles();
		String reqFileName = null;
		for (File f : desktopAllFiles) {
			if (f.getName().contains("BusinessProcessor_Checks_" + dateAsPerFile)) {
				reqFileName = f.getName();
				break;
			}

		}

		HashMap<Integer, HashMap<String, String>> exceldataMap = readExcelFromDesktop(reqFileName, "Checks");
		int totalExcelRowCount = exceldataMap.size();

		//// Compare DB data and match data from database
		HashMap<String, String> dataBaseMap = new HashMap<String, String>();
		dataBaseMap.put("From_Date", getAttributeValue("getFromDateValue", "Name"));
		dataBaseMap.put("To_Date", getAttributeValue("getToDateValue", "Name"));
		dataBaseMap.put("Created_By", getAttributeValue("getCreadtedByValueForDB", "Name"));

		// get DB data map and UI data map
		HashMap<Integer, HashMap<String, String>> DBDataMap = businessProcessor_getAllSearchGridData(dataBaseMap);
		HashMap<Integer, HashMap<String, String>> uiAllRowsDataMap = returnBusinessProcessorSearchResultForAllGridData(
				totalExcelRowCount);

		// get complete Ui data map with account id
		HashMap<Integer, HashMap<String, String>> uiAllRowsDataMapWithAccountId = getAccountIdFromMap(uiAllRowsDataMap,
				DBDataMap);
		System.out.println("uiAllRowsDataMapWithAccountId " + uiAllRowsDataMapWithAccountId);

		// get complete excel data map with check no and check amount
		HashMap<Integer, HashMap<String, String>> exceldataMapWithCheckAmountAndCheckNum = getCheckAmountAndCheckNumFromUiDataMap(
				uiAllRowsDataMapWithAccountId, exceldataMap);
		System.out.println("exceldataMapWithCheckAmountAndCheckNum " + exceldataMapWithCheckAmountAndCheckNum);

		// get complete Db data map with Core and LW count
		HashMap<Integer, HashMap<String, String>> dbDataMapwithCoreLwCount = geLWCountAndCoreCountInFromUiDataMap(
				uiAllRowsDataMapWithAccountId, DBDataMap);
		System.out.println("dbDataMapwithCoreLwCount " + dbDataMapwithCoreLwCount);

		// Get boolean value to compare the Db map, excel map and Ui map
		boolean flagForUIDataMatch = validateExpectedMapWithActualDataMap(exceldataMapWithCheckAmountAndCheckNum,
				uiAllRowsDataMapWithAccountId);
		boolean flagForDBDataMatch = validateExpectedMapWithActualDataMap(exceldataMapWithCheckAmountAndCheckNum,
				dbDataMapwithCoreLwCount);
		assertTrue(flagForUIDataMatch == flagForDBDataMatch, "Data not matched");

	}

	/**
	 * this method is used to compare the map with expectdata map and actual data
	 * map
	 */
	public boolean validateExpectedMapWithActualDataMap(HashMap<Integer, HashMap<String, String>> ExpectedDataMap,
			HashMap<Integer, HashMap<String, String>> ActualDataMap) {
		boolean flag = false;
		int count = 0;

		for (int i = 1; i <= ExpectedDataMap.size(); i++) {

			for (int j = 1; j <= ActualDataMap.size(); j++) {

				if (ExpectedDataMap.get(i).equals(ActualDataMap.get(j))) {
					count++;
					break;

				}

			}

			if (ExpectedDataMap.size() == count) {

				flag = true;
			}

		}

		return flag;

	}

	/**
	 * this method is used to get the Ui data map with the account id
	 */
	public HashMap<Integer, HashMap<String, String>> getAccountIdFromMap(
			HashMap<Integer, HashMap<String, String>> uiAllRowsDataMap,
			HashMap<Integer, HashMap<String, String>> DBDataMap) throws Exception {

		int count = 0;
		int keyCount = 0;
		for (int i = 1; i <= uiAllRowsDataMap.size(); i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try {
				uiAllRowsDataMap.get(i).replace("DateDeposited",
						dateFormat.format(dateFormat.parse(uiAllRowsDataMap.get(i).get("DateDeposited"))));
			} catch (ParseException e) {
			}
			for (int j = 1; j <= DBDataMap.size(); j++) {

				String createDate = null;
				try {
					dateFormat.parse(DBDataMap.get(j).get("DateDeposited"));
				} catch (Exception e) {
					createDate = convertDate(DBDataMap.get(j).get("DateDeposited"), 0);
					DBDataMap.get(j).replace("DateDeposited", createDate);
				}
				HashMap<String, String> DBDataMap1 = new HashMap<String, String>();
				DBDataMap1.putAll(DBDataMap.get(j));
				DBDataMap1.remove("AccountId");
				for (Entry<String, String> ent : DBDataMap1.entrySet()) {
					if (uiAllRowsDataMap.get(i).containsValue(ent.getValue())) {
						count++;
					}
					keyCount++;
				}
				if (count == keyCount) {
					uiAllRowsDataMap.get(i).put("AccountId", DBDataMap.get(j).get("AccountId"));
					keyCount = 0;
					count = 0;
					break;
				}

				keyCount = 0;
				count = 0;
			}
		}
		return uiAllRowsDataMap;
	}

	/**
	 * this method is used to get the excel data map with check no and check amount
	 */
	public HashMap<Integer, HashMap<String, String>> getCheckAmountAndCheckNumFromUiDataMap(
			HashMap<Integer, HashMap<String, String>> uiAllRowsDataMapWithAccountId,
			HashMap<Integer, HashMap<String, String>> exceldataMap) throws Exception {

		int count = 0;
		int keyCount = 0;
		for (int i = 1; i <= uiAllRowsDataMapWithAccountId.size(); i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			for (int j = 1; j <= exceldataMap.size(); j++) {
				String createDate = null;
				try {
					createDate = dateFormat.format(dateFormat.parse(exceldataMap.get(j).get("DateDeposited")));
					exceldataMap.get(j).replace("DateDeposited", createDate);
				} catch (Exception e) {
					System.out.println("date formatting issue");
				}
				for (Entry<String, String> ent : exceldataMap.get(j).entrySet()) {
					if (uiAllRowsDataMapWithAccountId.get(i).containsValue(ent.getValue())) {
						count++;
					}
					keyCount++;
				}
				if (count == keyCount) {
					exceldataMap.get(j).put("CheckNo", uiAllRowsDataMapWithAccountId.get(i).get("CheckNo"));
					exceldataMap.get(j).put("CheckAmount", uiAllRowsDataMapWithAccountId.get(i).get("CheckAmount"));
					keyCount = 0;
					count = 0;
					break;
				}

				keyCount = 0;
				count = 0;
			}
		}
		return exceldataMap;
	}

	/**
	 * this method is used to get the db data map with Core count and LW count
	 */
	public HashMap<Integer, HashMap<String, String>> geLWCountAndCoreCountInFromUiDataMap(
			HashMap<Integer, HashMap<String, String>> uiAllRowsDataMapWithAccountId,
			HashMap<Integer, HashMap<String, String>> DBDataMapWithCoreLWCount) throws Exception {
		int count = 0;
		int keyCount = 0;
		for (int i = 1; i <= uiAllRowsDataMapWithAccountId.size(); i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			for (int j = 1; j <= DBDataMapWithCoreLWCount.size(); j++) {
				String createDate = null;
				try {
					dateFormat.parse(DBDataMapWithCoreLWCount.get(j).get("DateDeposited"));
				} catch (Exception e) {
					createDate = convertDate(DBDataMapWithCoreLWCount.get(j).get("DateDeposited"), 0);
					DBDataMapWithCoreLWCount.get(j).replace("DateDeposited", createDate);
				}
				for (Entry<String, String> ent : DBDataMapWithCoreLWCount.get(j).entrySet()) {
					if (uiAllRowsDataMapWithAccountId.get(i).containsValue(ent.getValue())) {
						count++;
					}
					keyCount++;
				}
				if (count == keyCount) {
					DBDataMapWithCoreLWCount.get(j).put("CoreCount",
							uiAllRowsDataMapWithAccountId.get(i).get("CoreCount"));
					DBDataMapWithCoreLWCount.get(j).put("LWACount",
							uiAllRowsDataMapWithAccountId.get(i).get("LWACount"));
					keyCount = 0;
					count = 0;
					break;
				}
				keyCount = 0;
				count = 0;
			}
		}
		return DBDataMapWithCoreLWCount;
	}
}