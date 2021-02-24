package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.junit.AfterClass;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_08 class automates Ocean Underwriting module Test
 * Condition 08 which holds 2 Test Case; Test Condition Description :Validate
 * all data fields for correct remittance information under remittance list
 * including change of contract count on the basis of all available underwriting
 * status assigned.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_UnderWriting_TC_08 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 08 Test Case
	 * description : Validate all data fields for correct remittance information
	 * under remittance list including change of contract count on the basis of all
	 * available underwriting status assigned.
	 * 
	 */
	@Test(priority = 5, groups = { "regression", "smoke",
			"fullSuite" }, description = "Validate all data fields for correct remittance information under remittance list including change of contract count on the basis of all available underwriting status assigned.")
	public void verifyRemittanceStatus() throws Exception {
		copyFilesWorkingRemittance();
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		String[] inputArray = { "random", "2", "1", "Paper", "Standard", "Paper Remit", "", "Automation", "234", "1500",
				"Dealer", "998" };
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceValues(inputArray);
		// String remittanceName = "pOlJghzmjKk1GZQ68CCB";
		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(2);
			refreshRemittance();
			searchRemittance(remittanceName);
			///// Update check status
			addCheck();
			//// Refresh remittance
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
			// premiumData.put("DEALERID","998");
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
				premium();
				enterCustomerPaidAndDealerPaid("1234", "1500");
				selectCheckAndScrollToTop();
				click("clickUnderW");
				contractExpander();
				if (getValue("remitUnderW").equalsIgnoreCase("1")) {
					assertEquals(true, true);
				} else {
					assertEquals(false, true);
				}

			} else {
				throw new Exception("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

	@AfterClass
	public void refreshremit() {
		try {
			refreshRemittance();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
