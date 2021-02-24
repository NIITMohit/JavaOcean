package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * Ocean_UnderWriting_TC_41 class automates Ocean Underwriting module Test
 * Condition Ocean_UnderWriting_TC_41, which holds 1 Test Case; Test Condition
 * Description : Ocean shall not allow user to process ACH transaction when
 * remittance is not posted source as ACH, till remittance posting
 * 
 * @author Poonam Kalra
 */

public class OCEAN_UnderWriting_TC_41 extends UnderwritingModulePages {
	@SuppressWarnings("unused")
	@Test(priority = 5, groups ="fullSuite", description = "Validate all data fields for correct remittance information under remittance list including change of contract count on the basis of all available underwriting status assigned.")
	public void verifyBlockingProcessACHTransaction() throws Exception {
		boolean matchflag = false;
		copyFilesWorkingRemittance();
		// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		// fill all necessary fields in create remittance
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
				enterCustomerPaidAndDealerPaid("1000", "1500");
				premium();
				selectCheckAndScrollToTop();
				//// click under
				click("saveAllOnRemittance");
				waitForSomeTime(30);
				click("clickUnderW");
				waitForSomeTime(15);
				String contractStatus = null;
				try {
					contractStatus = getAttributeValue("getCotractStatusOnUnderwritingPage", "Value.Name");
					System.out.println(contractStatus);
				} catch (Exception e) {
				}
				if (contractStatus.equalsIgnoreCase("UnderW")) {
					takeScreenshot();
					goToRemittanceSummary();
					takeScreenshot();
					String flag = checkEnableDisable("ProcessACHTransaction");
					System.out.println(flag);
					if (flag.equalsIgnoreCase("false")) {
						matchflag = true;
						click("clickOnPostRemittance");
						click("clickOnYesButton");
						assertEquals(matchflag, true);
						waitForSomeTime(60);
						try {
							click("clickOK");
						} catch (Exception e) {
							waitForSomeTime(40);
							click("clickOK");
						}
					} else {
						assertEquals(matchflag, true);
					}
				}
			} else {
				throw new Exception("Remittace is not UnderWritten");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}
}
