package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import ocean.common.ObjectRepo;
import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * 
 * OCEAN_Underwriting_TC_42 class automates Ocean Underwriting module Test
 * Condition 42, which holds 2 Test Case; Test Condition description : Validate
 * correct remittance posting for a remittance , if its Total check amount is
 * adjusted via additional payments for excess and short pay at Remittance level
 * 
 * 
 * @author Surbhi Singhal
 */

public class OCEAN_UnderWriting_TC_42 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases for test condition 42 Case description
	 * : Validate correct remittance posting for a remittance , if its Total check
	 * amount is adjusted via additional payments for excess and short pay at
	 * Remittance level
	 */
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC42", dataProviderClass = UnderwritingDataProvider.class, description = "Validate correct remittance posting for a remittance ,if its Total check amount is adjusted via additional payments for excess and short pay at Remittance level")
	public void checkAddtionalPaymentsScreen(String[] inputArray) throws Exception {
		copyFilesWorkingRemittance();
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		String roleId = inputArray[3];
		if (roleId.contains("."))
			roleId = roleId.substring(0, roleId.indexOf("."));
		//// fill all necessary fields in create remittance
		String roleIdForCheck = "";
		if (roleId == null || roleId.trim().length() == 0)
			roleIdForCheck = "140";
		else
			roleIdForCheck = roleId;

		String[] data = { "random", "1", "0", "Paper", "Standard", "Paper Remit", "Dealer Suspense", "Automation",
				"1121", inputArray[5], "Dealer", roleIdForCheck };

		String remittanceName = enterRemittanceValues(data);
		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			///// Update check status
			addCheck();
			//// Refresh remittance
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			String[] inputData = { "SNE", roleId, "n", "n", "n", "n", "n", "ALLPLANS", "ALLPLANS", "", "", "", "", "",
					"", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
				refreshRemittance();
				searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
				lockAndViewContract();
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					//// do nothing
				}
				String aulPremium = premium();
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				uiSearchData = processAddPayments(inputArray);
				selectCheckAndScrollToTop();
				//// click underW button
				click("saveAllOnRemittance");
				waitForSomeTime(10);
				click("clickUnderWButton");
				waitForSomeTime(5);
				String contractStatus = getAttributeValue("getCotractStatusOnUnderwritingPage", "Name");
				goToChecksTab();
				enterValuesForAdditionalPayments(uiSearchData);
				HashMap<String, String> roleIdDbMap = addAccountDetailsOnCreditAndRefunds(uiSearchData);
				String contractId = premiumData.get("ContractNumber");
				addContractDetailsOnAdditionalPaymentsWithoutError(uiSearchData, contractId);
				uploadPdf();
				click("clickOnAddButtonOnADP");
				waitForSomeTime(10);
				goToNewBusinessFormUnderWritingTab();
				click("scrollContractsListDown");
				aulPremium = premium();
				aulPremium = aulPremium.replace("$", "");
				aulPremium = aulPremium.replace(",", "");
				enterCustomerPaidAndDealerPaid(aulPremium, aulPremium);
				contractScrollToTop();
				//// click underW button
				click("clickUnderWButton");
				waitForSomeTime(5);
				goToRemittanceSummary();
				validateAdditionalPayAmount(aulPremium, uiSearchData.get("CheckAmount"));
				if (contractStatus.equalsIgnoreCase("UnderW")) {
					click("clickOnPostRemittance");
					for (int i = 0; i < 4; i++) {
						try {
							click("clickOnYesButton");
						} catch (Exception e) {
							break;
							// // do nothing
						}
					}
					WebElement successMsg = windowsDriver.findElement(ObjectRepo.fetchOR("getTextOfSuccessMessage"));
					assertEquals(successMsg.getText().equals("Success"), true);
				} else {
					throw new Exception("Remittace is not UnderWritten");
				}
			} else {
				throw new Exception("Remittace creation failed");
			}
		}
	}
}