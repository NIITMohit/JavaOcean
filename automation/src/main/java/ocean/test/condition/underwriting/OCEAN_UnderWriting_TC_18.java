package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * Ocean_UnderWriting_TC_18 class automates Ocean Underwriting module Test
 * Condition Ocean_UnderWriting_TC_18, which holds 15 Test Case; Test Condition
 * Description : Validate the creation of remittance in ocean Validate primary
 * account for contract/contract adjustment
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam Kalra
 */

public class OCEAN_UnderWriting_TC_18 extends UnderwritingModulePages {
	// This function Input and validate VIN number availability.
	// on Contract Posting

	@Test(priority = 5, groups = "fullSuite", description = "Input and validate VIN number availability")
	public void ValidateVinNumber() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// //// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			assertEquals(validateVinNumberAvailability(), true);
		} else
			throw new SkipException("no such data found");
	}

	// This function Input and validate VIN number availability. // on
	// AdjustContract

	@Test(priority = 5, groups = "fullSuite", description = "Input and validate VIN number availability on AdjustContract")
	public void ValidateVinNumberAdjustContract() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Processed");
		if (contractId.length() > 1) {
			processForAccountSearchForContractAdjustment(contractId);
			assertEquals(validateVinNumberAvailability(), true);
		} else
			throw new SkipException("no such data found");
	}

	/*
	 * This Validate Ability To Override vehicle details before pricing calculation
	 * from OnHold to UnderWritten And verify Vehicle Detail after pricing
	 * calculation
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate Primary Account For Dealer On Contract Posting")
	public void ValidateVehicleDetailOnContractPosting() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("OnHold");
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getOnHoldContract(
				contractId);
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			// processForAccountSearchForContractUnderWritten(contractId);
			// goToUnderWritingTab();
			// goToRemittanceList();
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
				click("clickOK");
				try {
					click("clickOK");
				} catch (Exception e) {
				}
				waitForSomeTime(10);
				click("clickUnderW");
				try {
					click("clickOK");
				} catch (Exception e) {
				}
				click("scrollContractsListUp");
				//// check for successful underwritten
				HashMap<String, String> VINDetails = getVINDetails();
				HashMap<String, String> VINBeforeContractAdjust = new HashMap<String, String>();
				VINBeforeContractAdjust.put("VIN", premiumData.get("VIN"));
				VINBeforeContractAdjust.put("MAKE", premiumData.get("MAKE"));
				VINBeforeContractAdjust.put("MODEL", premiumData.get("MODEL"));
				VINBeforeContractAdjust.put("YEAR", premiumData.get("YEAR"));
				VINBeforeContractAdjust.put("MILEAGE", premiumData.get("MILEAGE"));
				VINBeforeContractAdjust.put("VEHICLEPRICE", premiumData.get("VEHICLEPRICE"));
				System.out.println(VINBeforeContractAdjust);
				assertEquals(VINBeforeContractAdjust, VINDetails);
				System.out.println(checkForFileStatus(fileName));
				assertEquals(checkForFileStatus(fileName), "1");
			}
		} else
			throw new SkipException("no such data found");

	}

	/*
	 * This Validate Ability To Override vehicle details before pricing calculation
	 * on Contract adjustment And verify Vehicle Detail after pricing calculation
	 * 
	 */

	@Test(priority = 5, groups = "fullSuite", description = "Validate To Override vehicle details before pricing calculation on contract adjustment")
	public void ValidateVehicleDetailOnContractAdjustment() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("Active");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(contractId);
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			System.out.println("String 1--->" + sss);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
				System.out.println("String---premium>" + premiumData);
				//// enter all mandatory values only on new business form screen

				// premiumData.putAll(changeDealerorLender(premiumData));

				premiumData.putAll(changeDealerorLenderVin(premiumData));

				//// Select Surcharges options, deductibles
				if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
					premiumData.put("SURCHARGESAMOUNT", surcharges());
				if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
					premiumData.put("OPTIONSAMOUNT", options());
				if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
					premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
				premium();
				click("clickOK");
				try {
					click("clickOK");
				} catch (Exception e) {
				}
				// This function is used to lock and view contract post contract search

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
				HashMap<String, String> VINDetails = getVINDetails();
				HashMap<String, String> VINBeforeContractAdjust = new HashMap<String, String>();
				VINBeforeContractAdjust.put("VIN", premiumData.get("VIN"));
				VINBeforeContractAdjust.put("MAKE", premiumData.get("MAKE"));
				VINBeforeContractAdjust.put("MODEL", premiumData.get("MODEL"));
				VINBeforeContractAdjust.put("YEAR", premiumData.get("YEAR"));
				VINBeforeContractAdjust.put("MILEAGE", premiumData.get("MILEAGE"));
				VINBeforeContractAdjust.put("VEHICLEPRICE", premiumData.get("VEHICLEPRICE"));
				System.out.println(VINBeforeContractAdjust);
				assertEquals(VINBeforeContractAdjust, VINDetails);
			}
		} else
			throw new SkipException("no such data found");
	}

}
