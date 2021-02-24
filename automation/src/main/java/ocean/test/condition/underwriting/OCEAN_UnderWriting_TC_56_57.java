package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_56_57 class automates Ocean Underwriting module Test
 * Condition 55, which holds 1 Test Case; Test Condition Description : Validate
 * Ocean ability to update check from business prcessor
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_UnderWriting_TC_56_57 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 57 Case description
	 * : Validate Ocean ability to update check from business prcessor
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_57", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Ocean ability to update check from business prcessor for dealer.")
	public void addCheckInBusinessProcessor(String[] inputArray) throws Exception {
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
		HashMap<String, String> searchData = returnCheckDetailsFromChecksTab();
		//// verify both data, must match
		assertEquals(dbData.equals(searchData), true, "Data not matched");
		cloneCheck();
		String checkError = getValue("validationMsgForCloneCheck");
		assertEquals(checkError, "Destination folder does not exists. Please verify and try again.");
		click("clickOK");
		assertFalse(checkReadOnlyFields("getRoleIdValueFromCheckScreen"), "RoleId is not read only");
		logger.info("RoleId is read only");
		assertFalse(checkReadOnlyFields("getAccountNameFromCheckScreen"), "AccountName is not read only");
		logger.info("AccountName is read only");
		click("contractExpander");
		goToBusinessProcessorTab();
		updateCheckOnBusinessProcessor();
		String validMsg = getValue("validateUpdateCheckMsg");
		assertEquals(validMsg,
				"The remittance for this check is already posted, are you sure you want to update the check?");
		click("clickOnYesButton");
		HashMap<String, String> dbData1 = getCheckDetailsFromDB(remittNumber);
		HashMap<String, String> uiData = returnUpdateCheckDeatilsFromBusinessProcessor();
		removeCheckFromBusinessProcessorScreen();
		assertEquals(dbData1, uiData);

	}
}
