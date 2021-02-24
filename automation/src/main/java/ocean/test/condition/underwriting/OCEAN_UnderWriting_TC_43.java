package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * Ocean_UnderWriting_TC_43 class automates Ocean Underwriting module Test
 * Condition Ocean_UnderWriting_TC_43, which holds 2 Test Case; Test Condition
 * Description : Validate remittance posting for correct update to contract
 * status in case of : 1. New Business 2. Contract adjustment.
 * 
 * @author Poonam Kalra
 */

public class OCEAN_UnderWriting_TC_43 extends UnderwritingModulePages {
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Validate remittance posting for correct update to contract status in case of new business when contract status is on hold")
	public void verifyRemittancePostingForOnHoldContract() throws Exception {
		copyFilesWorkingRemittance();
		/// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "", "Paper", "Standard", "Paper Remit", "", "Automation", "234", "1500",
				"Dealer", "998" };
		String remittanceName = enterRemittanceValuesWithDealerId(inputArray);
		if (remittanceName.length() > 0) {
			HashMap<String, String> uiValues = myData(remittanceName);
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			String[] inputData = { "SNE", "", "n", "n", "n", "n", "n", "ALLPLANS", "ALLPLANS", "", "", "", "", "", "",
					"", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
				searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
				lockAndViewContract();
				addCheckOnremmit();
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					//// do nothing
				}
				enterCustomerPaidAndDealerPaid("1000", "2000");
				premium();
				selectCheckAndScrollToTop();
				click("saveAllOnRemittance");
				waitForSomeTime(30);
				clickOnHoldWithReason(new String[] { "" });
				String status = getAttributeValue("getCotractStatusOnUnderwritingPage", "Value.Name");
				assertEquals(status.equalsIgnoreCase("OnHold"), true);
				waitForSomeTime(10);
				// onHoldRemittancePosting(dealerPaid);
				postRemittance();
				takeScreenshot();
				goToFindContractTab();
				// type("contractNoInFindContract",contractid.get("cert"));
				click("searchFindContractBtn");
				takeScreenshot();
				String postRemmittenceState = getAttributeValue("contractStausOnFindcontract", "Value.Name");
				assertEquals(postRemmittenceState.equalsIgnoreCase("OnHold"), true);
			}
		}
	}
}
