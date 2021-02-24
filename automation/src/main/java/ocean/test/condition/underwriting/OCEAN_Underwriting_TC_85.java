package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_85 class automates new PBI 18045, which holds Test
 * Cases; Test Condition Description : Vaidate refund Expander
 * 
 * @author Shalu Chauhan
 */
public class OCEAN_Underwriting_TC_85 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases for test condition TC_85 Test Case
	 * description : Validate below column header should be displayed with correct
	 * data in refund expander of Account Adjustment Screen on Account Statements.
	 * Validate that refund should be added in the refund expander of Account
	 * Adjustment screen on Account statements , if user process refund of contract.
	 */
	@Test(priority = 1, groups = "smoke", dataProvider = "fetchDataForTC_85", dataProviderClass = UnderwritingDataProvider.class, description = "Validate below column header should be displayed with correct data in refund expander of Account Adjustment Screen on Account Statement")
	public void refundExpanderOnAccountStatements(String[] inputArray) throws Exception {
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
		HashMap<String, String> roleIdDbMap = addAccountDetailsOnCreditAndRefundsForRefundExpander(uiSearchData);
		String contractId = roleIdDbMap.get("ContractId");
		addContractIdAndCheckDetails(uiSearchData, roleIdDbMap.get("ContractId"));
		uploadPdf();
		HashMap<String, String> agentRoleId = getAgentRoleId(uiSearchData);
		type("clickOnAgentRoleId", agentRoleId.get("AgentRoleId"));
		addDetailsOnACR();
		HashMap<String, String> dbdata = getAddedRefundDataForContractFromDB(contractId, uiSearchData.get("Role_Type"));
		logger.info("DBData" + dbdata);
		goToStatementsTab();
		typeRoletypeAndRoleIdUnderStatementsTab(uiSearchData.get("Role_Type"), roleIdDbMap.get("RoleId"));
		click("clickOnCalendarIconOnStatementsTab");
		waitForSomeTime(10);
		// get month from trans date
		String transDateMonth = getMonthFromDate(dbdata.get("Trans_Date"));
		// click on desired month
		clickOnCalenderMonth(transDateMonth);
		clickOnViewButtonUnderStatementsTab();
		clickRefundsUnderStatementsTab();
		waitForSomeTime(10);
		HashMap<String, String> uiData = getRefundsExpanderDetailsFromUI(uiSearchData);
		assertEquals(uiData, dbdata);

	}

}
