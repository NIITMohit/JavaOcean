package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.SearchDataProvider;
import ocean.modules.pages.SearchModulePages;

/**
 * OCEAN_Search_TC_19_24 class automates Ocean Search module Test Condition
 * 19-24, which holds 6 Test Cases; Test Condition Description : Validate Search
 * sheet content after their import into OCEAN
 * 
 * @author Atul Awasthi
 * 
 * @reviewer : Poonam/ Atul
 */
public class OCEAN_Search_TC_19_24 extends SearchModulePages {
	/*
	 * This function covers all search cases based on search parameters given from
	 * excel
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke","smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC19_24", dataProviderClass = SearchDataProvider.class, description = "Search test case,Cancel Contract should be"
					+ " Search from the given search sheet under Search Section.")
	public void searchContractWithAnyInputField(String[] inputArray) throws Exception {
		goToSearchTab();
		click("clickCancellationTabInContract");
		boolean matchFlag = false;
//		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			// run db query to get unique value, else no need
			// get search data value in a hashmap from data provider, all values would be
			// appendSearchData saved in searchData hash map same as in excel, all values
			// including *, Blanks
			uiSearchData = cancel_Search(appendSearchDataInCancellationSearch(inputArray));
		} else {
			uiSearchData = convertDataRemoveStarInCancellationSearch(inputArray);
		}
//		// run code for search
		searchCancelContractForInputParamaters(uiSearchData);
		HashMap<Integer, HashMap<String, String>> dbCO = searchCancelCountFromDatabase(uiSearchData);
		List<String> dbCert = new ArrayList<String>();
		for (int j = 1; j <= dbCO.size(); j++) {
			for (Entry<String, String> ent : dbCO.get(j).entrySet()) {
				dbCert.add(ent.getValue());
			}
		}
		System.out.println("dbcert==" + dbCert.size());
		List<WebElement> certName = listOfElements("listOfContractInCancellationSearch");
		List<String> uiCert = new ArrayList<String>();
		for (WebElement Cert : certName) {
			uiCert.add(Cert.getText());
		}
		System.out.println("uiCert==" + uiCert.size());
		if (dbCert.equals(uiCert)) {
			int iterations = 0;
			if (dbCO.size() > 2) {
				iterations = 2;
			} else
				iterations = dbCO.size();
			System.out.println("dbCert===" + dbCert);
			int counter = 1;
			String val = "";
			HashMap<String, String> dbEntry = new HashMap<String, String>();
			List<String> certNames = new ArrayList<String>();
			for (int i = 0; i < iterations; i++) {
				// get data from Db for contract id
				if (dbCert.contains(getCancelContractId(i))) {
					HashMap<Integer, HashMap<String, String>> dbMap = searchCancelDetailsFromSearchData(
							getCancelContractId(i));
					for (Entry<Integer, HashMap<String, String>> contractEntry : dbMap.entrySet()) {
						if (!certNames.contains(getCancelContractId(i))) {
							certNames.add(getCancelContractId(i));
							System.out.println("certName==" + certNames);
							for (Entry<String, String> nameEntry : contractEntry.getValue().entrySet()) {
								String name = nameEntry.getKey();
								val = nameEntry.getValue();
								dbEntry.put(name, val);
								System.out.println("dbentry===" + dbEntry);
							}
						} else {
							counter++;
							int f = contractEntry.getKey();
							System.out.println("f===" + f);
							System.out.println("counter==" + counter);
							if (f == counter) {
								for (Entry<String, String> nameEntry : contractEntry.getValue().entrySet()) {
									String name = nameEntry.getKey();
									val = nameEntry.getValue();
									dbEntry.put(name, val);
									System.out.println("dbentry===" + dbEntry);
								}
							} else {
								counter--;
								continue;
							}
						}
						// convert date as per Ui format
						waitForSomeTime(5);
						if (getValue("listOfStatusInCancellationSearch", i).equalsIgnoreCase("quote"))
							dbEntry.put("Process_Date", "");
						else
							dbEntry.put("Process_Date", convertNewDateFormat(dbEntry.get("Process_Date")));
						DecimalFormat decimalFormat = new DecimalFormat("#.##");
						decimalFormat.setGroupingUsed(true);
						decimalFormat.setGroupingSize(3);
						String netRefund = dbEntry.get("Net_Refund");
						if (netRefund.length() > 0) {
							double f = Double.parseDouble(netRefund);
							dbEntry.put("Net_Refund", decimalFormat.format(f));
						}
						String customerRefund = dbEntry.get("Customer_Refund");
						if (customerRefund.length() > 0) {
							double f = Double.parseDouble(customerRefund);
							dbEntry.put("Customer_Refund", decimalFormat.format(f));
						}

						// get All data for search results
						HashMap<String, String> uiMap = getCancellationSearchResult(i);
						String date = uiMap.get("Process_Date");
						if (!getValue("listOfStatusInCancellationSearch", i).equalsIgnoreCase("quote")
								|| !getValue("listOfStatusInCancellationSearch", i).equalsIgnoreCase("authorized")) {
							int index = date.indexOf(" ");
							if (index > 0)
								uiMap.put("Process_Date", date.substring(0, index));
							if (!uiMap.get("Process_Date").equals("")) {
								uiMap.put("Process_Date", convertNewDateForCompare(date));
							} else
								uiMap.put("Process_Date", dbEntry.get("Process_Date"));
						}
						waitForSomeTime(5);
						System.out.println("dbEntry==" + dbEntry);
						System.out.println("uiMap====" + uiMap);
						// compare both data
						if (!dbEntry.equals(uiMap)) {
							matchFlag = false;
							break;
						} else {
							matchFlag = true;
							break;
						}

					}
					assertEquals(matchFlag, true);
				} else
					throw new Exception("error");
			}
		}
		// verifyClearCancellationSearch();
	}

	/**
	 * This function compare the selected two cancel search cases.
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate OCEAN ability to provide user with correct contract details as read only for selected "
					+ "account from contract search results via compare option and clear compared contracts on user's request.")
	public void verifyCompareContract() throws Exception {
		HashMap<String, String> firstNameMap = searchFirstNameOfCancelByCount();
		String firstName = firstNameMap.get("First_Name");
		goToSearchTab();
		waitForSomeTime(5);
		click("clickCancellationTabInContract");
		waitForSomeTime(10);
		click("clickClearInCancellationSearch");
		type("TypeFNameInCancellationSearch", firstName);
		click("clickSearchInCancellationSearch");
		List<WebElement> certName = listOfElements("listOfContractInCancellationSearch");
//		int screenCount = getSearchesultsCount();
		if (certName.size() >= 2) {
			waitForSomeTime(10);
			click("firstRowChkBox");
			click("secondRowChkBox");
			String OceanPage = windowsDriver.getWindowHandle();
			waitForSomeTime(5);
			click("clickCompareInCancellationSearch");
			waitForSomeTime(15);
			Set<String> windowHandles = windowsDriver.getWindowHandles();
			verifyCancelCompareContract(windowHandles, OceanPage);
			verifyClearCompared("clickClearCancellationsInCancellationSearch");
		} else {
			throw new Exception("Compare not possible due to less cert!");
		}
	}

}
