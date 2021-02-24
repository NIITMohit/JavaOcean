package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

public class OCEAN_UnderWriting_TC_88 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases for test condition TC_88 Test Case
	 * description :
	 */
	@SuppressWarnings("unused")
	@Test
	public void cancellationGridOnAccountStatements() throws Exception {
		HashMap<String, String> cancelData = getDetailsOfCancelledContract("Authorized");
		goToUnderWritingTab();
		goToAccountsDetailsTab();
		goToStatementsTab();
		typeRoletypeAndRoleIdUnderStatementsTab(cancelData.get("Primary_Acct_Status"),
				cancelData.get("Primart_Acct_Id"));
		click("clickOnCalendarIconOnStatementsTab");
		;
		// click on desired month
		clickOnCalenderMonth(cancelData.get("CANCEL_DATE"));
		clickOnViewButtonUnderStatementsTab();
		waitForSomeTime(10);
		HashMap<String, String> dbData = new HashMap<String, String>();
		dbData.put("CUSTOMER_LAST", cancelData.get("CUSTOMER_LAST"));
		dbData.put("CONTRACT_NUMBER", cancelData.get("CONTRACT_NUMBER"));
		dbData.put("REFUND_PERCENTAGE", cancelData.get("REFUND_PERCENTAGE"));
		dbData.put("NET_REFUND", cancelData.get("NET_REFUND"));
		dbData.put("CANCEL_DATE", cancelData.get("CANCEL_DATE"));
		dbData.put("PROCESS_DATE", cancelData.get("PROCESS_DATE"));
		HashMap<String, String> uiData = getCancelledContractDetailsUnderCancellationGridFromUI();
		assertTrue(uiData.equals(dbData), "UI data is not equal to DB data");
		clickOnViewButtonUnderCancellationGrid();
		HashMap<String, String> dbdata1 = getNewCancellationDetailsforCanceledContract(
				cancelData.get("CONTRACT_NUMBER"));
		HashMap<String, String> uidata1 = getNewCancellationDetailsFromAccountstatementUI();
		assertTrue(uidata1.equals("dbdata1"), "UI data is not equal to DB data");

	}

}
