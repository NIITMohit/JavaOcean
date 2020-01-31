package ocean.test.condition.underwriting;

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
 * OCEAN_UnderWriting_TC_21To23_25To33 class automates Ocean Pricing module Test
 * Condition 21 to 23 and 25 to 33, which holds 11 Test Case; Test Condition
 * Description : Validate premium calculation. Account :
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_21To23_25To33 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate remittance posting.
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = " Validate remittance posting")
	public void validateRemittancePostingForDealerAsPrimaryAccount(String[] inputData) throws Exception {

		//// create remittance with documents count as 1

		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
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

			assertEquals(premium, finalValue);

			//// post remittance and verify

		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

}
