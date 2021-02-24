package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_24 class automates Ocean Pricing module Test Condition
 * 24 which holds 11 Test Case; Test Condition Description : Validate premium
 * calculation for a contract/contract adjustment in a remittance, if it's price
 * sheet is changed
 * 
 * @author Mohit Goel
 * 
 *         comments : in case of any update also update OCEAN_UnderWriting_TC_24
 * 
 * @reviewer : Shalu Chauhan
 */
public class OCEAN_Pricing_TC_15_18 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 15 and 18; Test Case description
	 * :Validate premium calculation for a contract/contract adjustment in a
	 * remittance, if it's price sheet is changed from : 1. One product to another
	 * product.
	 * 
	 */
	@Test(priority = 6, groups = { "regression", "extendSmoke", "smoke",
			"fullSuite" }, description = "Validate premium calculation for a contract/contract adjustment in a remittance, ifit's price sheet is changed from : 1. One product to another product.")
	public void changePriceSheet() throws Exception {
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
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
			String oldPriceSheet = "SNE";
			String newPriceSheet = "SNF";
			String[] impData = { oldPriceSheet, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
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
				//// Get AUL Premium
				String premium = premium();
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equalsIgnoreCase(finalValue)) {
					String[] impData2 = { newPriceSheet, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
							"", "", "" };
					HashMap<String, String> premiumData2 = prepareData(impData2);
					HashMap<String, String> sss2 = setAllDataForPremiumCalculation(premiumData2);
					premiumData2.putAll(sss2);
					premiumData2.put("PrimaryAccount", "Dealer");
					premiumData2.put("SecondaryAccount", "Lender");
					premiumData2.put("SecondaryAccountId", "24");
					premiumData2.putAll(changeDealerorLender(premiumData2));
					try {
						click("scrollContractsListDown");
					} catch (Exception e) {
						/// do nothing
					}
					if (premiumData2.get("SURCHARGES").toLowerCase().equals("y"))
						premiumData2.put("SURCHARGESAMOUNT", surcharges());
					if (premiumData2.get("OPTIONS").toLowerCase().equals("y"))
						premiumData2.put("OPTIONSAMOUNT", options());
					if (premiumData2.get("DEDUCTIBLE").toLowerCase().equals("y"))
						premiumData2.put("DEDUCTIBLEAMOUNT", deductibles());
					//// Get AUL Premium
					String premium2 = premium();
					String finalValue2 = calculateMyPremium(premiumData2);
					assertEquals(premium2, finalValue2);
				} else {
					new SkipException("value not matched");
				}

			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			new SkipException("no remittance");
		}
	}

	/**
	 * This function automates test Condition 15; Test Case description :Validate
	 * premium calculation for a contract/contract adjustment in a remittance, if
	 * it's price sheet is changed from : 1. One product to another product.
	 * 
	 */
	@Test(priority = 6, groups = { "regression",
			"smoke" }, description = "Validate premium calculation for a contract/contract adjustment in a remittance, if it's price sheet is changed from : 1. One product to another product.")
	public void deleteChangeLender() throws Exception {
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
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = primaryDealerSecndaryLender(premiumData);
			premiumData.putAll(sss);
			// HashMap<String, String> sss1 = getDealerFromLender(premiumData);
			// premiumData.putAll(sss1);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			//// Dealer Id
			// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			if (sss.size() > 1) {
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				// // Select Surcharges options, deductibles
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
				String premium = premium();
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equalsIgnoreCase(finalValue) || !premium.equalsIgnoreCase(finalValue)) {
					HashMap<String, String> premiumData2 = premiumData;
					premiumData2.put("SecondaryAccountId", "24");
					premiumData2.putAll(changeDealerorLender(premiumData2));
					try {
						click("scrollContractsListDown");
					} catch (Exception e) {
						/// do nothing
					}
					if (premiumData2.get("SURCHARGES").toLowerCase().equals("y"))
						premiumData2.put("SURCHARGESAMOUNT", surcharges());
					if (premiumData2.get("OPTIONS").toLowerCase().equals("y"))
						premiumData2.put("OPTIONSAMOUNT", options());
					if (premiumData2.get("DEDUCTIBLE").toLowerCase().equals("y"))
						premiumData2.put("DEDUCTIBLEAMOUNT", deductibles());
					//// Get AUL Premium
					String premium2 = premium();
					String finalValue2 = calculateMyPremium(premiumData2);
					assertEquals(premium2, finalValue2);
				} else {
					new SkipException("value not matched");
				}

			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			new SkipException("no remittance");
		}
	}

	/**
	 * This function automates test Condition 15; Test Case description :Validate
	 * premium calculation for a contract/contract adjustment in a remittance, if
	 * it's price sheet is changed from : 1. One product to another product.
	 * 
	 */
	// @Test(priority = 6, groups = {
	// "fullSuite" }, description = "Validate premium calculation for a
	// contract/contract adjustment in a remittance, if it's price sheet is changed
	// from : 1. One product to another product.")
	//// functioanlty implemented in delete lender
	public void changeLender() throws Exception {
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
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
			String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculationLenderSecondaryNotNull(premiumData);
			premiumData.putAll(sss);
			HashMap<String, String> sss1 = getDealerFromLender(premiumData);
			premiumData.putAll(sss1);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
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
				//// Get AUL Premium
				String premium = premium();
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equalsIgnoreCase(finalValue)) {
					HashMap<String, String> premiumData2 = premiumData;
					//// only change lender, dealer and pricesheetid
					HashMap<String, String> sss2 = setAllDataForPremiumCalculationLenderNotIn(premiumData2);
					premiumData2.putAll(sss2);
					HashMap<String, String> sss12 = getDealerFromLender(premiumData);
					premiumData2.putAll(sss12);
					premiumData2.putAll(enterMandatoryValuesOnContract(premiumData2));
					try {
						click("scrollContractsListDown");
					} catch (Exception e) {
						/// do nothing
					}
					if (premiumData2.get("SURCHARGES").toLowerCase().equals("y"))
						premiumData2.put("SURCHARGESAMOUNT", surcharges());
					if (premiumData2.get("OPTIONS").toLowerCase().equals("y"))
						premiumData2.put("OPTIONSAMOUNT", options());
					if (premiumData2.get("DEDUCTIBLE").toLowerCase().equals("y"))
						premiumData2.put("DEDUCTIBLEAMOUNT", deductibles());
					//// Get AUL Premium
					String premium2 = premium();
					String finalValue2 = calculateMyPremium(premiumData2);
					assertEquals(premium2, finalValue2);
				} else {
					new SkipException("value not matched");
				}

			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			new SkipException("no remittance");
		}
	}

	/**
	 * This function automates test Condition 15; Test Case description :Validate
	 * premium calculation for a contract/contract adjustment in a remittance, if
	 * it's price sheet is changed from : 1. One product to another product.
	 * 
	 */
	@Test(priority = 6, groups = { "regression",
			"smoke" }, description = "Validate premium calculation for a contract/contract adjustment in a remittance, ifit's price sheet is changed from : 1. One product to another product.")
	public void changeAgnet() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		//// get remittance name and file name
		/// iterate to multiple contracts with same price sheet
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			/* goToUnderWritingTab(); */
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
			String oldPriceSheet = "SNE";
			String newPriceSheet = "SNF";
			String[] impData = { oldPriceSheet, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
					"" };
			HashMap<String, String> premiumData = prepareData(impData);
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
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
				//// Get AUL Premium
				String premium = premium();
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equalsIgnoreCase(finalValue)) {
					String[] impData2 = { newPriceSheet, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
							"", "", "" };
					HashMap<String, String> premiumData2 = prepareData(impData2);
					HashMap<String, String> sss2 = setAllDataForPremiumCalculation(premiumData2);
					premiumData2.putAll(sss2);
					premiumData2.put("PrimaryAccount", "Dealer");
					premiumData2.put("SecondaryAccount", "Lender");
					premiumData2.put("SecondaryAccountId", "24");
					premiumData2.putAll(changeDealerorLender(premiumData2));
					try {
						click("scrollContractsListDown");
					} catch (Exception e) {
						/// do nothing
					}
					if (premiumData2.get("SURCHARGES").toLowerCase().equals("y"))
						premiumData2.put("SURCHARGESAMOUNT", surcharges());
					if (premiumData2.get("OPTIONS").toLowerCase().equals("y"))
						premiumData2.put("OPTIONSAMOUNT", options());
					if (premiumData2.get("DEDUCTIBLE").toLowerCase().equals("y"))
						premiumData2.put("DEDUCTIBLEAMOUNT", deductibles());
					//// Get AUL Premium
					String premium2 = premium();
					String finalValue2 = calculateMyPremium(premiumData2);
					assertEquals(premium2, finalValue2);
				} else {
					new SkipException("value not matched");
				}

			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			new SkipException("no remittance");
		}
	}

	/**
	 * This function clear pre filled data *
	 */
	@AfterMethod(alwaysRun = true)
	public void clearDataFromUnderwritingWindow() throws Exception {
		//// scroll up and clear data
		try {
			clearPreFilledData();
			click("contractExpander");
			refreshRemittance();
		} catch (Exception e) {
			System.out.println("dd");
		}
	}
}
