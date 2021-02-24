
package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * Ocean_UnderWriting_TC_17 class automates Ocean Underwriting module Test
 * Condition Ocean_UnderWriting_TC_17, which holds 8 Test Case; Test Condition
 * Description : Validate the creation of remittance in ocean Validate secondary
 * account for contract/contract adjustment
 * 
 * * least priority test case skiping in regression
 * 
 * @author Mohit Goel
 */

public class OCEAN_UnderWriting_TC_17 extends UnderwritingModulePages {
	// This function validate Agent is present or not. if Agent Display in secondary
	// combo box then it fails
	@Test(priority = 5, groups = "fullSuite", description = "Validate Agent Not Displayed In Secondary Account TypeID")
	public void ValidateAgentNotDisplayedInSecondaryAccount() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			assertEquals(validateSecondaryAccountforDealerLender(), true);
		} else
			throw new SkipException("no such data found");
	}

	// This function validate Agent is present or not.
	/// if Agent Display in secondary combo box then it fails
	@Test(priority = 5, groups = "fullSuite", description = "Validate Agent Not Displayed In Secondary Account TypeID for contract Adjustment")
	public void ValidateAgentNotDisplayedInSecondaryAccountForContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Processed");
		if (contractId.length() > 1) {
			processForAccountSearchForContractAdjustment(contractId);
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			assertEquals(validateSecondaryAccountforDealerLender(), true);
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Secondary Account(For Dealer) from OnHold to
	 * UnderWritten And verify Secondary Account Detail
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate Secondary Account For Dealer On Contract Posting")
	public void ValidateSecondaryAccountDealer() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getOnHoldContractwithRemittance();
		if (contractFromRemittance.size() > 1) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			///// Prepare Data
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("SecondaryAccount", "Dealer");
			premiumData.put("SecondaryAccountId", premiumData.get("DEALERID"));
			premiumData.put("PrimaryAccount", "Lender");
			premiumData.put("DEALERID", "");
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
				//// enter all mandatory values only on new business form screen
				System.out.print(premiumData);
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
			}
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Secondary Account(For Lender) from OnHold to
	 * UnderWritten And verify Secondary Account Detail
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate Secondary Account For Lender On Contract Posting")
	public void ValidateSecondaryAccountLender() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getOnHoldContractwithRemittance();
		if (contractFromRemittance.size() > 1) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			///// Prepare Data
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculationLender(premiumData);
			premiumData.putAll(sss);
			HashMap<String, String> sss1 = getDealerFromLender(premiumData);
			premiumData.putAll(sss1);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
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
			}
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Secondary Account(For Dealer) for Contract
	 * Adjustment
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate secondary account as dealer on contract adjustment")
	public void ValidateSecondaryAccountDealerOnContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Active");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(contractId);
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			// String pp = (sss.get("PRICESHEETID"));
			// String PSHEETID = getPriceshhetId(pp);
			premiumData.putAll(sss);
			// premiumData.put("PRICESHEETID",PSHEETID);
			premiumData.put("SecondaryAccount", "Dealer");
			premiumData.put("SecondaryAccountId", premiumData.get("DEALERID"));
			premiumData.put("PrimaryAccount", "Lender");
			premiumData.put("DEALERID", "");
			System.out.println("pyui--->" + premiumData);
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
				assertEquals(primaryIdForAdjustContract(contractId), premiumData.get("SecondaryAccountId"));
			}
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Change Secondary Account(For Lender) for Contract
	 * Adjustment
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate secondary account for lender on contract adjustment ")
	public void ValidateSecondaryAccountLenderOnContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Active");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(contractId);
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			// HashMap<String, String> sss =
			// setAllDataForPremiumCalculationLender(premiumData);
			premiumData.putAll(sss);
			// HashMap<String, String> sss1 = getDealerFromLender(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "6262");
			System.out.print("Final Data----->" + premiumData);
			// premiumData.put("DEALERID", sss1.get("DEALERID"));
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
				//// enter all mandatory values only on new business form screen

				premiumData.putAll(changeDealerorLender(premiumData));

				premiumData.putAll(changeDealerorLenderVin(premiumData));

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
				try {
					click("scrollContractsListUp");
				} catch (Exception e) {
					/// do nothing
				}

				waitForSomeTime(10);
				click("clickAdjust");
				click("AdjustCategoryUpgrade");
				click("clickNextBtnOnContractAdjustment");
				waitForSomeTime(5);
				click("FinishBtnOnContractAdjustment");
				click("clickOK");
				waitForSomeTime(40);
				removeErrorMessages();
				for (int i = 0; i < 8; i++) {
					try {
						click("contractExpander");
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				waitForSomeTime(10);
				processForAccountSearchForContractAdjustment(contractId);
				String secondaryId = getValue("secondaryAccountId");
				assertEquals(secondaryId, premiumData.get("SecondaryAccountId"));
			} else
				throw new SkipException("no such data found");
		}
	}
}
