package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * 
 * OCEAN_Underwriting_TC_35_36 class automates Ocean Underwriting module Test
 * Condition 55, which holds 4 Test Case; Test Condition description : Validate
 * for checks tab that check information is correctly displayed for check added
 * via : 1. Remittance creation 2. Business processor
 * 
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_UnderWriting_TC_35_36 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases for test condition 35 Case description
	 * : Validate for checks tab that check information is correctly displayed for
	 * check added via Remittance creation
	 */
	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC01_02_03_04", dataProviderClass = UnderwritingDataProvider.class, description = "Validate for checks tab that check information is correctly displayed for check added via Remittance creation.")
	public void checkValidationForRemittanceCreation(String[] inputArray) throws Exception {
		copyFilesWorkingRemittance();
		//// go to underwriting tab
		goToUnderWritingTab();
		// goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// verify drag and drop status for pdf
		getPDFStatus();
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceValues(inputArray);
		if (inputArray[4] == "" || inputArray[5] == "" || inputArray[0] == "" || inputArray[1] == ""
				|| inputArray[2] == "") {
			if (remittanceName.equals("")) {
				assertEquals(true, true);
			} else {
				assertEquals(true, false);
			}
		}
		HashMap<String, String> dbValues = getRemitCreationdata(remittanceName);
		goToChecksTab();
		addCheckOnCheckTabs();
		HashMap<String, String> searchData = returnCheckDetailsFromChecksTab();
		//// verify both data, must match
		assertEquals(dbValues.equals(searchData), true, "Data not matched");

	}

	/**
	 * This function automates all test cases for test condition 36 Case description
	 * :Validate for checks tab that check information is correctly displayed for
	 * check added via Business processor
	 */
	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC_57", dataProviderClass = UnderwritingDataProvider.class, description = "Validate for checks tab that check information is correctly displayed for check added via Business processor.")
	public void checkValidationForBusinessProcessor(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("Role_Type", inputArray[0]);
		uiSearchData.put("Status", inputArray[1]);
		uiSearchData.put("RoleId", inputArray[2]);
		uiSearchData.put("CheckNumber", inputArray[3]);
		uiSearchData.put("CheckAmount", inputArray[4]);
		//// Navigate to mail service tab
		goToUnderWritingTab();
		goToBusinessProcessorTab();
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		//// get remittance name and file name
		String remittNumber = "";
		String remittName = "";
		String fileName = "";
		if (contractFromRemittance.size() > 0) {
			remittNumber = contractFromRemittance.get(1).get("RemittanceNumber");
			remittName = contractFromRemittance.get(1).get("RemittanceName");
			fileName = contractFromRemittance.get(1).get("FILE_NAME");
		}
		enterCheckDetailsOnRemittance(uiSearchData);
		type("typeRemittance", remittNumber);
		addAccountDetails(uiSearchData);
		click("clickOnAddButtonOnBusinessProcessor");

		//// db query to be added
		HashMap<String, String> dbData = getCheckDetailsFromDB(remittNumber);
		goToRemittanceList();
		for (int i = 0; i < 10; i++) {
			try {
				goToRemittanceList();
			} catch (Exception e) {
				waitForSomeTime(25);
			}
		}
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		searchContractwithPendingState(remittName, fileName);
		/// load remittance
		click("loadRemittance");
		//// lock contract on user name and open enter values in contract window
		lockAndViewContract();
		goToChecksTab();
		addCheckOnCheckTabs();
		removeCheckFromBusinessProcessorScreen();
		HashMap<String, String> searchData = returnCheckDetailsFromChecksTab();
		//// verify both data, must match
		assertEquals(dbData.equals(searchData), true, "Data not matched");

	}

}
