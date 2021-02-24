package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 9135 class automates NEW PBI 9135_15059_15439_15663 : , which holds 5
 * Test Case; Test Condition Description : Add Reason for NSF in On Hold and
 * Underwriting Adjustment screen drop down
 * OCEAN_UnderWriting_PBI_9135_15059_15439_15663
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_65 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases of NEW PBI 9135 Case description : Add
	 * Reason for NSF in On Hold and Underwriting Adjustment screen drop down
	 * 
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI9135", dataProviderClass = UnderwritingDataProvider.class, description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void OnHoldNSFStatusForContractAdjustment(String[] onHoldReason) throws Exception {
		goToUnderWritingTab();
		goToFindContractTab();
		String contractid = cancellation_getContractIdBasedOnStatus("Active");
		if (contractid != null) {
			processForAccountSearchForContractAdjustment(contractid);
			try {
				click("scrollContractsListDown", 1);
			} catch (Exception e) {
			}
			surcharges();
			options();
			//// Get AUL Premium
			premium();
			//// click on hold
			clickAdjustWithReason(new String[] { onHoldReason[0] });
			waitForSomeTime(25);
			String status = getAttributeValue("contractStatus", "Name");
			//// validate status and sub status
			String subStatus = getAttributeValue("contractSubStatus", "Name");
			click("contractExpander");
			assertEquals(status.equalsIgnoreCase("active") && subStatus.equalsIgnoreCase("nsf"), true);
		} else {
			throw new SkipException("no matchng contract found");
		}
	}

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI9135", dataProviderClass = UnderwritingDataProvider.class, description = "Add Reason for NSF in On Hold and Underwriting Adjustment screen drop down ")
	public void OnHoldNSFStatusForNewBusinessForm(String[] onHoldReason) throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		//// get remittance name and file name
		/// iterate to multiple contracts with same price sheet
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			//// lock contract on uF34va3wrser name and open enter values in contract window
			lockAndViewContract();
			String oldPriceSheet = "SNE";
			String[] impData = { oldPriceSheet, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
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
				//// Get AUL Premium
				premium();
				selectCheckAndScrollToTop();
				//// click on hold
				clickOnHoldWithReason(new String[] { onHoldReason[0] });
				waitForSomeTime(25);
				//// validate status and sub status
				String status = getAttributeValue("contractStatus", "Name");
				String subStatus = getAttributeValue("contractSubStatus", "Name");
				click("contractExpander");
				assertEquals(status.equalsIgnoreCase("onhold") && subStatus.equalsIgnoreCase("nsf"), true);
			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			new SkipException("no remittance found");
		}

	}
}
