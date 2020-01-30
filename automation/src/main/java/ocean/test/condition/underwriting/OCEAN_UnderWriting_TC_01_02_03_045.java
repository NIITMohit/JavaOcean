package ocean.test.condition.underwriting;

import java.util.HashMap;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Cancel_TC_01_02_03 class automates Ocean Underwriting module Test
 * Condition 01 and 02 and 03 and 04, which holds 15 Test Case; Test Condition
 * Description : Validate the creation of remittance in ocean
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_01_02_03_045 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Validate the creation of remittance in ocean
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC01_02_03_04", dataProviderClass = UnderwritingDataProvider.class, description = "Validate the creation of remittance in ocean")
	public void postRemittance(String[] inputArray) throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceMandatoryValues();
		if (remittanceName.length() > 0) {
			//// search remittance
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(2);
			///// Update check status
			addCheck();
			//// Refresh remittance
			refreshRemittance();
			//// Search remittance and file name and issue contract
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			if (remitt.size() > 0) {
				for (Entry<Integer, HashMap<String, String>> letterEntry : remitt.entrySet()) {
					HashMap<String, String> value = letterEntry.getValue();
					String remitName = value.get("RemittanceName");
					String fileName = value.get("FILE_NAME");
					//// visit underwriting tab
					waitForSomeTime(10);
					goToUnderWritingTab();
					//// Search a contract with pending state, remittance name and contract name is
					//// fetched from database
					searchContractwithPendingState(remitName, fileName);
					//// lock contract on user name and open enter values in contract window
					lockAndViewContract();
				}
			} else {
				new Exception("not able to crate remittancce");
			}

		} else {
			new Exception("not able to crate remittancce");
		}
	}
}
