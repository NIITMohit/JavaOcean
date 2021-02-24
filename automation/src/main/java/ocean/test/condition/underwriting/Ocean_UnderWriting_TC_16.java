
package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * Ocean_UnderWriting_TC_16 class automates Ocean Underwriting module Test
 * Condition Ocean_UnderWriting_TC_16, which holds 8 Test Case; Test Condition
 * Description : Validate the creation of remittance in ocean Validate primary
 * account for contract/contract adjustment
 * 
 * @author Mohit Goel
 */

public class Ocean_UnderWriting_TC_16 extends UnderwritingModulePages {
	// This function validate Agent is present or not. if Agent Display in primary
	// combo box then it fails
	//@Test(priority = 5, groups = "fullSuite", description = "Validate Agent Not	 Displayed In Primary Account TypeID")
	public void ValidateAgentNotDisplayedInPrimaryAccount() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			assertEquals(validatePrimaryAccountforDealerLender(), true);
			waitForSomeTime(5);
			contractExpander();
		} else
			throw new SkipException("no such data found");
	}

	// This function validate Agent is present or not.
	/// if Agent Display in primary combo box then it fails
	@Test(priority = 5, groups = "fullSuite", description = "Validate Agent Not	Displayed In Primary Account TypeID for contract Adjustment")
	public void ValidateAgentNotDisplayedInPrimaryAccountForContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Active");
		goToUnderWritingTab();
		goToFindContractTab();
		if (contractId.length() > 1) {
			processForAccountSearchForContractAdjustment(contractId);
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			assertEquals(validatePrimaryAccountforDealerLender(), true);
			waitForSomeTime(5);
			contractExpander();
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Primary Account(For Dealer) from OnHold to
	 * UnderWritten And verify Primary Account Detail
	 * 
	 */
	@Test(priority = 1, groups = "fullSuite", description = "Validate Primary	 Account For Dealer On Contract Posting")
	public void ValidatePrimaryAccountDealer() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getOnHoldContractwithRemittance();
		if (contractFromRemittance.size() >= 1) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			///// Prepare Data
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
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
				premium();
				click("saveAllOnRemittance");
				waitForSomeTime(10);
				click("clickUnderW");
				//// check for successful underwrtten
				assertEquals(checkForFileStatus(fileName), "1");
				contractExpander();
			}
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Primary Account(For Lender) from OnHold to
	 * UnderWritten And verify Primary Account Detail
	 * 
	 */
//	@Test(priority = 5, groups = "fullSuite", description = "Validate Primary Account For Lender On Contract Posting")
	public void ValidatePrimaryAccountLender() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getOnHoldContractwithRemittance();
		if (contractFromRemittance.size() > 1) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			///// Prepare Data
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculationLender(premiumData);
			premiumData.put("PRICESHEETID", sss.get("PRICESHEETID"));
			premiumData.put("DEALERID", sss.get("SecondaryAccountId"));
			premiumData.put("parentpricesheetcode", sss.get("parentpricesheetcode"));
			premiumData.put("PrimaryAccount", "Lender");
			premiumData.put("SecondaryAccount", "Dealer");
			premiumData.put("SecondaryAccountId",  sss.get("DealerId"));
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
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
				premium();
				click("clickUnderW");
				//// check for successful underwrtten
				assertEquals(checkForFileStatus(fileName), "2");
				contractExpander();
			}
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Primary Account(For Dealer) for Contract
	 * Adjustment
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate primary account as dealer on contract adjustment")
	public void ValidatePrimaryAccountDealerOnContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("active");
		goToUnderWritingTab();
		goToFindContractTab();
		if (contractId.length() > 1) {
			processForAccountSearchForContractAdjustment(contractId);
			takeScreenshot();
			String[] impData = { "SNE", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
				//// enter all mandatory values only on new business form screen
				waitForSomeTime(20);
				takeScreenshot();
				premiumData.putAll(changeDealerorLender(premiumData));
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
				premium();
				takeScreenshot();
				contractScrollToTop();
				click("clickAdjust");
				takeScreenshot();
				AdjustForm();
				// ZAGHX096454E20
				String before = premiumData.get("DEALERID");
				String after = primaryIdForAdjustContract(contractId);
				assertEquals(before, after);
			}
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Primary Account(For Lender) for Contract
	 * Adjustment
	 * 
	 */
	//@Test(priority = 5, groups = "fullSuite", description = "Validate primary account for lender on contract adjustment ")
	public void ValidatePrimaryAccountLenderOnContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Processed");
		if (contractId.length() > 1) {
			processForAccountSearchForContractAdjustment(contractId);
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculationLender(premiumData);
			premiumData.put("PRICESHEETID", sss.get("PRICESHEETID"));
			premiumData.put("DEALERID", sss.get("SecondaryAccountId"));
			premiumData.put("parentpricesheetcode", sss.get("parentpricesheetcode"));
			premiumData.put("PrimaryAccount", "Lender");
			premiumData.put("SecondaryAccount", "Dealer");
			premiumData.put("SecondaryAccountId",  sss.get("DealerId"));
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(changeDealerorLender(premiumData));
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
				premium();
				click("clickAdjust");
				AdjustForm();
				assertEquals(primaryIdForAdjustContract(contractId), premiumData.get("DEALERID"));
			} else
				throw new SkipException("no such data found");
		}
	}
}
