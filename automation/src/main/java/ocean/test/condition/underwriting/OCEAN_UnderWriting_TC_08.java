package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

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
 */
public class OCEAN_UnderWriting_TC_08 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 08 Test Case
	 * description : Validate all data fields for correct remittance information
	 * under remittance list including change of contract count on the basis of all
	 * available underwriting status assigned.
	 * 
	 */
	@Test(priority = 5, groups = "regression", description = "Validate all data fields for correct remittance information under remittance list including change of contract count on the basis of all available underwriting status assigned.")
	public void verifyRemittanceStatus() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceMandatoryValues("2");
		if (remittanceName.length() > 0) {
			HashMap<String, String> uiValues = myData(remittanceName);
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(2);
			///// Update check status
			addCheck("1111", "12344");
			//// Refresh remittance
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			String[] inputData = { "SNE", "", "n", "n", "n", "n", "n", "ALLPLANS", "ALLPLANS", "", "", "", "", "", "",
					"", "", "", "" };
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
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					/// do nothing
				}
				premium();
				enterCustomerPaidAndDealerPaid("12344", "12344");
				selectCheckAndScrollToTop();
				//// click under
				click("clickUnderW");
				contractExpander();
				HashMap<String, String> uiValues2 = myData(remittanceName);
				if (Integer.parseInt(uiValues.get("corecount")) - 1 == Integer.parseInt(uiValues2.get("corecount")))
					assertEquals(true, true);
				else
					assertEquals(false, true);
			} else {
				throw new Exception("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

}
