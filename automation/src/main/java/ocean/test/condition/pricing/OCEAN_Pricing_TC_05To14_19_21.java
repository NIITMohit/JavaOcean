package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Pricing_TC_05To14_18 class automates Ocean Pricing module Test
 * Condition 05 to 14 and 18, which holds 11 Test Case; Test Condition
 * Description : Validate premium calculation. Account :
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Shalu Chauhan
 */
public class OCEAN_Pricing_TC_05To14_19_21 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate premium calculation.
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke", "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = "Validate premium calculation.")
	public void validatePremiumCalculationForDealerAsPrimaryAccount(String[] inputData) throws Exception {
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
		HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
		if (sss.size() > 1) {
			goToUnderWritingTab();
			lockAndViewContract();
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
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
			logger.info("premiumData : " + premiumData);
			//// Get AUL Premium
			String premium = premium();
			premium = premium.replaceAll(",", "");
			logger.info("premium : " + premium);
			String finalValue = calculateMyPremium(premiumData);
			logger.info("finalValue : " + finalValue);
			assertEquals(premium, finalValue);
		} else {
			throw new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	/**
	 * This function is executed before class, this will land till contract fill up
	 * form,
	 * 
	 */
	@BeforeClass(alwaysRun = true)
	public void prepareRemittance() throws Exception {
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
			//// lock contract on uF34va3wrser name and open enter values in contract windo
		} else {
			new SkipException("no remittance found");
		}
	}

	@AfterClass(alwaysRun = true)
	public void collapse() throws Exception {
		try {
			contractScrollToTop();
			click("typeContractNumber");
			click("contractExpander");
			refreshRemittance();
		} catch (Exception e) {
			// TODO: handle exception
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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
