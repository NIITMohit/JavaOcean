package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

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
public class OCEAN_Underwriting_TC_78 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases of PBi 10722 Case description :
	 * Validate ocean ability to accept numeric values only for Check#, DB/CR $ in
	 * ACR Case description : Validate ocean ability to accept only mandatory values
	 * in ACR Case description : Validate ocean ability to accept all field values
	 * in ACR
	 * 
	 */
	@Test(enabled = false, priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI_ACR_10722", dataProviderClass = UnderwritingDataProvider.class, description = "Validate ocean ability to accept all field values in ACR.")
	public void accountCreditAndRefundsScreen(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("AdjustmentType", inputArray[0]);
		uiSearchData.put("AdjustmentCategory", inputArray[1]);
		uiSearchData.put("Role_Type", inputArray[2]);
		uiSearchData.put("RoleId", inputArray[3]);
		uiSearchData.put("Status", inputArray[4]);
		uiSearchData.put("Post Period", inputArray[5]);
		uiSearchData.put("AgentRoleId", inputArray[6]);
		uiSearchData.put("ContractId", inputArray[7]);
		uiSearchData.put("CheckNumber", inputArray[8]);
		uiSearchData.put("CheckAmount", inputArray[9]);
		goToUnderWritingTab();
		goToAccountStatements();
		goToCreditAndRefunds();

		enterValuesForAccountAndCreditRefunds(uiSearchData);

		HashMap<String, String> roleIdDbMap = addAccountDetailsOnCreditAndRefunds(uiSearchData);
		String contractId = roleIdDbMap.get("ContractId");
		click("clickOnAddButtonACR");
		String validationErrormsg = getValue("validationMsgForACRScreen");
		assertEquals(validationErrormsg,
				"Please enter all required fields remittance number, account name, role identifier, check number, DB/CR, adjustment type and adjustment category");
		click("clickOK");
		addContractIdAndCheckDetails(uiSearchData, roleIdDbMap.get("ContractId"));
		uploadPdf();
		if (uiSearchData.get("AdjustmentCategory").equals("Agent Charge off")
				|| uiSearchData.get("AdjustmentCategory").equals("Agent Charge Off Reversal")) {
			click("clickOnAddButtonACR");
			String validateAgentRoleIdMsg = getValue("validationMsgForAgentRoleId");
			assertEquals(validateAgentRoleIdMsg, "Please enter Agent Role Identifier");
			click("clickOK");
			HashMap<String, String> agentRoleId = getAgentRoleId(uiSearchData);
			type("clickOnAgentRoleId", agentRoleId.get("AgentRoleId"));
		}
		addDetailsOnACR();
		waitForSomeTime(10);
		boolean addflag = false;
		HashMap<String, String> actualdata = getCreditAndRefundsAddedDetails();

		HashMap<String, String> expecteddata = getAddedDataOfACRScreenFromDB(contractId);

		if (actualdata.equals(expecteddata))
			addflag = true;
		String contractID = getValueOfDeleteRowOnACR();
		deleteRowFromACR(contractID);
		String updatedContractId = getValueOfDeleteRowOnACR();
		updateDataOnACRScreen();
		boolean updateflag = false;
		waitForSomeTime(10);
		HashMap<String, String> actualData = getCreditAndRefundsAddedDetails();

		HashMap<String, String> expectedData = getAddedDataOfACRScreenFromDB(updatedContractId);
		clearDataFromACRScreen();
		boolean flagClearScreen = validateClearScreen();
		if (actualData.equals(expectedData))
			if (addflag == flagClearScreen == updateflag == true)
				assertEquals(true, true);
			else
				assertEquals(true, false);
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI_ADP_10722", dataProviderClass = UnderwritingDataProvider.class, description = "Validate ocean ability to accept all field values in ACR.")
	public void checkAddtionalPaymentsScreen(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("AdjustmentType", inputArray[0]);
		uiSearchData.put("AdjustmentCategory", inputArray[1]);
		uiSearchData.put("Role_Type", inputArray[2]);
		uiSearchData.put("RoleId", inputArray[3]);
		uiSearchData.put("Status", inputArray[4]);
		uiSearchData.put("ContractId", inputArray[5]);
		uiSearchData.put("CheckNumber", inputArray[6]);
		uiSearchData.put("CheckAmount", inputArray[7]);
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			click("loadRemittance");
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
			waitForSomeTime(2);
			goToChecksTab();
			boolean noneditableFields = checkNonEditablePostPeriodAndRemittanceNum();
			enterValuesForAdditionalPayments(uiSearchData);
			HashMap<String, String> roleIdDbMap = addAccountDetailsOnCreditAndRefunds(uiSearchData);
			String contractId = roleIdDbMap.get("ContractId");
			addContractDetailsOnAdditionalPayments(uiSearchData, contractId);
			uploadPdf();
			addDetailsOnADP();
			waitForSomeTime(5);
			HashMap<String, String> actualData = getAddtinalPaymentsAddedDetails();
			boolean addflag = false;
			HashMap<String, String> expectData = getAddedDataOfAdditinalPaymentsFromDB(roleIdDbMap.get("ContractId"));
			if (actualData.equals(expectData))
				addflag = true;
			String contractid1 = getAttributeValue("getContractIdFromADPScreen", "Name").trim();
			updateDataOnADPScreen();
			boolean updateflag = false;
			waitForSomeTime(10);
			HashMap<String, String> actualdata = getAddtinalPaymentsAddedDetails();
			HashMap<String, String> expectdata = getAddedDataOfAdditinalPaymentsFromDB(contractid1);
			deleteRowFromADP();
			boolean flagDeleteData = validateDeleteDataFromADP();
			clearDataFromADPScreen();
			boolean flagClearScreen = validateADPClearScreen();
			if (actualdata.equals(expectdata))
				updateflag = true;
			if (addflag == flagClearScreen == updateflag == flagDeleteData == true)
				assertEquals(true, true);
			else {
				assertEquals(true, false);
			}
		}
	}

}
