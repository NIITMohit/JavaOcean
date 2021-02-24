package ocean.test.condition.underwriting;

import java.util.HashMap;
import java.util.Map.Entry;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_21To23_25To33 class automates Ocean Pricing module Test
 * Condition 21 to 23 and 25 to 33, which holds 11 Test Case; Test Condition
 * Description : Validate premium calculation. Account :
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_21To23_25To33 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate remittance posting .
	 * 
	 */
	@Test(priority = 1, groups = "ffsf", dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = " Validate remittance posting")
	public void validateRemittancePostingForDealerAsPrimaryAccount(String[] inputData) throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		dragAndDropFiles();
		String[] inputArray = new String[] { "random", "2", "0", "Paper", "Standard", "Paper Remit", "", "Automation",
				"234", "12344", "Dealer", "*" };
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceValues(inputArray);
		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			refreshRemittance();
			searchRemittance(remittanceName);
			///// Update check status
			addCheck();
			//// Refresh remittance
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			///// Prepare Data
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
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				//// Select Surcharges options, deductibles
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					/// do nothing
				}
				if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
					premiumData.put("SURCHARGESAMOUNT", surcharges());
				if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
					premiumData.put("OPTIONSAMOUNT", options());
				if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
					premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
				//// select check, underwrite contract, post remittance
				premium();
				enterCustomerPaidAndDealerPaid("12345", "12345");
				selectCheckAndScrollToTop();
				//// click under
				click("clickUnderW");
				//// click ok
				//// post remittance and verify
				postRemittance();
				//// code to be added once underwrite code issue is fixed
			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate remittance posting.
	 * 
	 */
	@Test(priority = 1, groups = { "fff", "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_23_24_25", dataProviderClass = UnderwritingDataProvider.class, description = " Validate remittance posting")
	public void validateRemittancePostingForLeaderAsPrimaryAccount(String[] inputData) throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		dragAndDropFiles();
		String[] inputArray = new String[] { "random", "2", "", "Paper", "Standard", "Paper Remit", "", "Automation",
				"234", "12344", "Dealer", "998" };
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceValues(inputArray);
		if (remittanceName.length() > 0) {
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			///// Update check status
			addCheck();
			//// Refresh remittance
			// refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			///// Prepare Data
			HashMap<String, String> premiumData = prepareDataForLenderAsPrimaryAccount(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			HashMap<String, String> dealerID = getDealerForLender(sss);
			for (Entry<String, String> entry1 : dealerID.entrySet()) {
				String DEALERID = entry1.getValue();
				premiumData.put("SecondaryAccountId", DEALERID);
			}
			if (sss.size() > 1) {
				searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
				lockAndViewContract();
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(enterMandatoryValuesOnContractForLender(premiumData));
				//// Select Surcharges options, deductibles
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					/// do nothing
				}
				if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
					premiumData.put("SURCHARGESAMOUNT", surcharges());
				if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
					premiumData.put("OPTIONSAMOUNT", options());
				if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
					premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
				//// select check, underwrite contract, post remittance
				premium();
				enterCustomerPaidAndDealerPaid("12345", "12345");
				selectCheckAndScrollToTop();
				//// click under
				click("clickUnderW");
				//// click ok
				//// post remittance and verify
				postRemittance();
			}
			//// code to be added once underwrite code issue is fixed
			else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}
}
