package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_PBI_16414 class holds 8 Test Case; Test Condition Description :
 * 
 * 
 * @author Atul Awasthi
 */

public class OCEAN_Cancel_TC_43 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition; Validate that OCEAN
	 * doesn't allow deletion of processed cancellation.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke1", "extendSmoke",
			"fullSuite" }, description = "Validate that OCEAN doesn't allow deletion of processed cancellation.")
	public void verifyProcessedCancellationDeletion() throws Exception {
		String contractId = "";
		boolean deleteButtonStatus = false;
		// get contract id based for processed contract only with current year
		contractId = getContractIDBasedOnContractStatus_T8("Cancelled", "Processed");
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			cancellationPageProcess(contractId);
			List<WebElement> statusListBeforeDelete = listOfElements("getAllStatusInAllCancelHistory");
			for (int i = 0; i < statusListBeforeDelete.size(); i++) {
				if (statusListBeforeDelete.get(i).getText().equals("Processed")) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			String ButtonState = getAttributeValue("clickDeleteOnNewCancellation", "IsEnabled");
			if (ButtonState.equals("False")) {
				deleteButtonStatus = true;
			}
			assertEquals(deleteButtonStatus, true);
		} else {
			new SkipException("no value exist in db.");
		}

	}

	/**
	 * This function automates test case for test condition;
	 * 
	 */
	@SuppressWarnings("null")
//	@Test(priority = 2, groups = { "regression","smoke1","extendSmoke",
//			"fullSuite" }, dataProvider = "fetchDataForPBI_16414", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN allow deletion of cancellation request "
//					+ "for a reinstated contract with following status:"
//					+ "1. Quote"
//					+ "2. ON Hold"
//					+ "3. Denied")
	public void verifyDeletionOfCancellationRequestForDifferentStatus(String[] inputArray) throws Exception {
		HashMap<String, String> excelMap = new HashMap<String, String>();
		excelMap = cancellation_getCOntractStatus(inputArray);
		String contractstatus = excelMap.get("Contract_Status");
		String contractId = "";
		boolean cancelHistoryFlag = false;
		boolean transactionHistoryFlag = false;
		// get contract id based for processed contract only with current year
		HashMap<String, String> contractDetail = getContractIDBasedOnContractStatus_T2("Cancelled", "Reinstated",
				contractstatus);
		contractId = contractDetail.get("ContractId");
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			cancellationPageProcess(contractId);
			List<WebElement> statusListBeforeDelete = listOfElements("getAllStatusInAllCancelHistory");
			int statusSizeBeforeDelete = statusListBeforeDelete.size();
			List<WebElement> transactionHistoryListBeforeDelete = listOfElements(
					"getAllStatusInAllCancelTransactionHistory");
			int rowSizeBeforeDeleteInTransactionHistory = transactionHistoryListBeforeDelete.size();
			for (int i = 0; i < statusListBeforeDelete.size(); i++) {
				if (statusListBeforeDelete.get(i).getText().equals(contractstatus)) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			waitForSomeTime(3);
			if (contractstatus.equals("OnHold")) {
				enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "",
						convertDate(contractDetail.get("sale_date"), 7), "");
				try {
					click("FuturePopupClick");
				} catch (Exception e) {
					// do nothing
				}
				// click ok for cancellation completed successfully
				try {
					click("okClick");
				} catch (Exception e) {
					// do nothing
				}
			}
			click("clickDeleteOnNewCancellation");
			click("clickOnYesButton");
			waitForSomeTime(2);
			goToMailServiceTab();
			waitForSomeTime(2);
			try {
				checkIsDisplayed("getAllStatusInAllCancelHistory");
				List<WebElement> statusListAfterDelete = listOfElements("getAllStatusInAllCancelHistory");
				int statusSizeAftereDelete = statusListAfterDelete.size();
				if (statusSizeBeforeDelete > statusSizeAftereDelete) {
					cancelHistoryFlag = true;
				}
			} catch (Exception e) {
				cancelHistoryFlag = true;
			}
			List<WebElement> transactionHistoryListAfterDelete = listOfElements(
					"getAllStatusInAllCancelTransactionHistory");
			int rowSizeAfterDeleteInTransactionHistory = transactionHistoryListAfterDelete.size();
			if (rowSizeBeforeDeleteInTransactionHistory == rowSizeAfterDeleteInTransactionHistory) {
				transactionHistoryFlag = true;
			}
			assertEquals(cancelHistoryFlag && transactionHistoryFlag, true);
		} else {
			new SkipException("no value exist in db.");
		}

	}

	/**
	 * This function automates test case for test condition;
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke1", "fullSuite",
			"extendSmoke" }, description = "Validate that OCEAN doesn't allow deletion of authorized cancellation for a reinstated contract.")
	public void verifyDeletionOfCancellationForReinstatedOnNextBusinessday() throws Exception {
		String contractId = "";
		HashMap<String, String> contractDetail = getContractIDBasedOnContractStatus_T2("Cancelled", "Reinstated",
				"Authorized");
		contractId = contractDetail.get("ContractId");
		boolean deleteButtonStatus = false;
		// get contract id based for processed contract only with current year
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			cancellationPageProcess(contractId);
			List<WebElement> statusListBeforeDelete = listOfElements("getAllStatusInAllCancelHistory");
			for (int i = 0; i < statusListBeforeDelete.size(); i++) {
				if (statusListBeforeDelete.get(i).getText().equals("Authorized")) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			waitForSomeTime(3);
			click("clickDeleteOnNewCancellation");
			click("clickOnYesButton");
			waitForSomeTime(3);
			if (checkIsDisplayed("getErrorMSgOnDelete")) {
				deleteButtonStatus = true;
			}
			click("clickOK");
			assertEquals(deleteButtonStatus, true);
		} else {
			new SkipException("no value exist in db.");
		}
	}

	/**
	 * This function automates test case for test condition;
	 * 
	 */
	@SuppressWarnings("unused")
//	@Test(priority = 2, groups = { "regression","smoke1", "fullSuite","extendSmoke" }, description = "Validate that OCEAN doesn't allow deletion of authorized cancellation for a reinstated contract.")
	public void verifyDeletionOfCancellationForReinstatedOnSameBusinessday() throws Exception {
		String contractId = "";
		HashMap<String, String> contractDetail = getContractIDBasedOnContractStatus_T1("Active", "Reinstated");
		contractId = contractDetail.get("ContractId");
		boolean deleteButtonStatus = false;
		// get contract id based for processed contract only with current year
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			click("clickClear");
			type("typeContractId", contractId);
			waitForSomeTime(5);
			click("searchContractButton");
			click("certnumInFirstGrid");
			List<WebElement> statusListBeforeDelete = listOfElements("getAllStatusInAllCancelHistory");
			for (int i = 0; i < statusListBeforeDelete.size(); i++) {
				if (statusListBeforeDelete.get(i).getText().equals("Quote")) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "",
					convertDate(contractDetail.get("sale_date"), 7), "");
			// click ok for cancellation completed successfully
			try {
				click("clickOK");
			} catch (Exception e) {
				// do nothing
			}
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			selectCancellationTaskStatus("authorize");
			waitForSomeTime(3);
			cancellationPageProcess(contractId);
			waitForSomeTime(3);
			List<WebElement> statusListBeforeDelete1 = listOfElements("getAllStatusInAllCancelHistory");
			for (int i = 0; i < statusListBeforeDelete1.size(); i++) {
				if (statusListBeforeDelete1.get(i).getText().equals("Authorized")) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			waitForSomeTime(2);
			click("clickDeleteOnNewCancellation");
			click("clickOnYesButton");
			waitForSomeTime(3);
			if (checkIsDisplayed("getErrorMSgOnDelete")) {
				deleteButtonStatus = true;
			}
			click("clickOK");
			assertEquals(deleteButtonStatus, true);
		} else {
			new SkipException("no value exist in db.");
		}

	}

	/**
	 * This function automates test case for test condition;
	 * 
	 */
	@Test(priority = 2, groups = { "extendSmoke", "regression", "smoke1",
			"fullSuite" }, description = "Validate that OCEAN allow deletion of authorized cancellation by same business day in OCEAN, "
					+ "if related contract is not reinstated.")
	public void verifyDeletionOfAuthorizedCancellationWithDBOnSameBusinessday() throws Exception {
		String contractId = "";
		HashMap<String, String> contractDetail = getContractIDBasedOnContractStatus_T5("active");
		contractId = contractDetail.get("ContractId");
		boolean dbTableFlag = false;
		boolean cancelHistoryFlag = false;
		boolean transactionHistoryFlag = false;
		boolean finalStatus = false;
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			cancellationPageProcess(contractId);
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "",
					convertDate(contractDetail.get("sale_date"), 7), "");
			try {
				click("FuturePopupClick");
			} catch (Exception e) {
				// do nothing
			}
			// click ok for cancellation completed successfully
			try {
				click("okClick");
			} catch (Exception e) {
				// do nothing
			}
			selectCancellationTaskStatus("authorize");
			click("certnumInFirstGrid");
			List<WebElement> statusListBeforeDelete = listOfElements("getAllStatusInAllCancelHistory");
			int statusSizeBeforeDelete = statusListBeforeDelete.size();
			List<WebElement> transactionHistoryListBeforeDelete = listOfElements(
					"getAllStatusInAllCancelTransactionHistory");
			int rowSizeBeforeDeleteInTransactionHistory = transactionHistoryListBeforeDelete.size();
			waitForSomeTime(2);
			for (int i = 0; i < statusListBeforeDelete.size(); i++) {
				if (statusListBeforeDelete.get(i).getText().equals("Authorized")) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			waitForSomeTime(2);
			click("clickDeleteOnNewCancellation");
			click("clickOnYesButton");
			waitForSomeTime(2);
			goToMailServiceTab();
			click("clickClear");
			type("typeContractId", contractId);
			waitForSomeTime(2);
			click("searchContractButton");
			waitForSomeTime(3);
			click("swipeRightInMailServiceTopGrid");
			String statusText = getTextOfElement("getStatusOfContractInTopGrid");
			waitForSomeTime(2);
			click("swipeLeftInMailServiceTopGrid");
			String ButtonState = getAttributeValue("clickCancelButton", "IsEnabled");
			try {
				checkIsDisplayed("getAllStatusInAllCancelHistory");
				List<WebElement> statusListAfterDelete = listOfElements("getAllStatusInAllCancelHistory");
				int statusSizeAftereDelete = statusListAfterDelete.size();
				if (statusSizeBeforeDelete > statusSizeAftereDelete) {
					cancelHistoryFlag = true;
				}
			} catch (Exception e) {
				cancelHistoryFlag = true;
			}
			try {
				checkIsDisplayed("getAllStatusInAllCancelTransactionHistory");

				List<WebElement> transactionHistoryListAfterDelete = listOfElements(
						"getAllStatusInAllCancelTransactionHistory");
				int rowSizeAfterDeleteInTransactionHistory = transactionHistoryListAfterDelete.size();
				if (rowSizeBeforeDeleteInTransactionHistory > rowSizeAfterDeleteInTransactionHistory) {
					transactionHistoryFlag = true;
				}
			} catch (Exception e) {
				transactionHistoryFlag = true;
			}
			String value1 = verificationFromCHECKSTable_T5(contractId).get("cert");
			String value2 = verificationFromALLCANCEL_TRANS_DOLLAR_DETAILTable_T5(contractId).get("cert");
			String value3 = verificationFromALLCANCEL_TRANS_DETAILS_T5(contractId).get("cert");
			String value4 = verificationFromCANCELLATION_PARAMETER_RESULTS_T5(contractId).get("cert");
			String value5 = verificationFromALLCANCEL_DOLLAR_DETAIL_T5(contractId).get("cert");
			String value6 = verificationFromCANCELLATION_CONTRACT_CATEGORY_T5(contractId).get("cert");
			String value7 = verificationFromCANCELLATION_FORMS_T5(contractId).get("cert");
			String value8 = verificationFromALLCANCEL_DETAILS_T5(contractId).get("cert");
			String value9 = verificationFromCANCELLATION_PARAMETERS_T5(contractId).get("cert");
			if (value1 == null && value2 == null && value3 == null && value4 == null && value5 == null && value6 == null
					&& value7 == null && value8 == null && value9 == null)
				dbTableFlag = true;
			if (ButtonState.equals("True") && statusText.equals("Active") && cancelHistoryFlag && transactionHistoryFlag
					&& dbTableFlag) {
				finalStatus = true;
			}

			assertEquals(finalStatus, true);
		} else {
			new SkipException("no value exist in db.");
		}

	}

	/**
	 * This function automates test case for test condition;
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "smoke1", "fullSuite",
			"extendSmoke" }, description = "Validate that OCEAN doesn't allow deletion of authorized cancellation  on next"
					+ " business day in OCEAN, if related contract is not reinstated.")
	public void verifyDeletionOfAuthorizedCancellationOnSNextBusinessday() throws Exception {
		String contractId = "";
		HashMap<String, String> contractDetail = getContractIDBasedOnContractStatus_T6("Cancelled", "Reinstated",
				"Authorized");
		contractId = contractDetail.get("ContractId");
		boolean deleteButtonStatus = false;
		// get contract id based for processed contract only with current year
		if (contractId.length() > 0) {
			// Navigate to Mail service tab
			cancellationPageProcess(contractId);
			List<WebElement> statusListBeforeDelete = listOfElements("getAllStatusInAllCancelHistory");
			for (int i = 0; i < statusListBeforeDelete.size(); i++) {
				if (statusListBeforeDelete.get(i).getText().equals("Authorized")) {
					++i;
					clickViaXpath(
							"(//*[@AutomationId='Ocean_CancellationModule_ExchangeMailServiceView_QuoteDataGridControl_SelectCancelParameterCommandButton'])["
									+ i + "]");
				}
			}
			waitForSomeTime(2);
			click("clickDeleteOnNewCancellation");
			click("clickOnYesButton");
			waitForSomeTime(3);
			if (checkIsDisplayed("getErrorMSgOnDelete")) {
				deleteButtonStatus = true;
			}
			click("clickOK");
			assertEquals(deleteButtonStatus, true);
		} else {
			new SkipException("no value exist in db.");
		}
	}

}
