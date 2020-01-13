package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Pricing_TC_05To14_18 class automates Ocean Pricing module Test
 * Condition 05 to 14 and 18, which holds 11 Test Case; Test Condition
 * Description : Validate premium calculation.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC_05To14_18 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate premium calculation.
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = "Validate premium calculation.")
	public void validatePremiumCalculationForDealerAsPrimaryAccount(String[] inputData) throws Exception {
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
			//// Select Surcharges
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

		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	///// Prepare test data needed for test case
	///// validatePremiumCalculationForMasterAndSubMasterPriceSheet
	public HashMap<String, String> prepareData(String[] inputData) {
		HashMap<String, String> premiumData = new HashMap<String, String>();
		//// Fetch PRICESHEETCODE, if exist
		if (inputData[0].length() > 0)
			premiumData.put("PRICESHEETCODE", inputData[0]);
		//// Fetch ACTUALPRICESHEETCODE, if exist
		if (inputData[1].length() > 0)
			premiumData.put("DEALERID", inputData[1]);
		//// Fetch VIN, in case of Blank, use any hard code value
		if (inputData[9].length() > 0)
			premiumData.put("VIN", inputData[9]);
		else
			premiumData.put("VIN", "WBANE52010CK67774");
		//// Fetch MAKE, in case of Blank, use any hard code value
		if (inputData[10].length() > 0)
			premiumData.put("MAKE", inputData[10]);
		else
			premiumData.put("MAKE", "HONDA");
		//// Fetch MODEL, in case of Blank, use any hard code value
		if (inputData[11].length() > 0)
			premiumData.put("MODEL", inputData[11]);
		else
			premiumData.put("MODEL", "Accord");
		//// Fetch YEAR, in case of Blank, use any hard code value
		if (inputData[12].length() > 0)
			premiumData.put("YEAR", inputData[12]);
		else
			premiumData.put("YEAR", "2013");
		//// Fetch MILEAGE, in case of Blank, use any hard code value
		if (inputData[13].length() > 0)
			premiumData.put("MILEAGE", inputData[13]);
		else
			premiumData.put("MILEAGE", "1000");
		//// Fetch VEHICLEPRICE, in case of Blank, use any hard code value
		if (inputData[14].length() > 0)
			premiumData.put("VEHICLEPRICE", inputData[14]);
		else
			premiumData.put("VEHICLEPRICE", "1000");
		//// Fetch TERM, if exist
		if (inputData[15].length() > 0)
			premiumData.put("TERM", inputData[15]);
		//// Fetch COVERAGE, if exist
		if (inputData[16].length() > 0)
			premiumData.put("COVERAGE", inputData[16]);
		//// Fetch MIELAGEBAND, if exist
		if (inputData[17].length() > 0)
			premiumData.put("MIELAGEBAND", inputData[17]);
		//// Fetch CLASS, if exist
		if (inputData[18].length() > 0)
			premiumData.put("CLASS", inputData[18]);
		//// Fetch SERVICEDATE, if exist
		if (inputData[19].length() > 0)
			premiumData.put("SERVICEDATE", inputData[19]);
		//// Fetch SURCHARGES, in case of Blank, use N
		if (inputData[4].length() > 0)
			premiumData.put("SURCHARGES", inputData[4]);
		else
			premiumData.put("SURCHARGES", "N");
		//// Fetch DEDUCTIBLE, in case of Blank, use N
		if (inputData[6].length() > 0)
			premiumData.put("DEDUCTIBLE", inputData[6]);
		else
			premiumData.put("DEDUCTIBLE", "N");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[7].length() > 0)
			premiumData.put("AGENTPLANTYPE", inputData[7]);
		else
			premiumData.put("AGENTPLANTYPE", "ALLPLANS");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[8].length() > 0)
			premiumData.put("DEALERPLANTYPE", inputData[8]);
		else
			premiumData.put("DEALERPLANTYPE", "ALLPLANS");
		//// Fetch OPTIONS, in case of Blank, use N
		if (inputData[5].length() > 0)
			premiumData.put("OPTIONS", inputData[5]);
		else
			premiumData.put("OPTIONS", "N");
		//// Fetch DEALEREXCEPTION, in case of Blank, use N
		if (inputData[2].length() > 0)
			premiumData.put("DEALEREXCEPTION", inputData[2]);
		else
			premiumData.put("DEALEREXCEPTION", "N");
		//// Fetch AGENTEXCEPTION, in case of Blank, use N
		if (inputData[3].length() > 0)
			premiumData.put("AGENTEXCEPTION", inputData[3]);
		else
			premiumData.put("AGENTEXCEPTION", "N");

		return premiumData;
	}

	/**
	 * This function is executed before class, this will land till contract fill up
	 * form,
	 * 
	 */
	@BeforeClass
	public void prepareRemittance() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		//// get remittance name and file name
		/// iterate to multiple contracts with same price sheet
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
		} else {
			new SkipException("no remittance found");
		}

	}

	/**
	 * This function clear pre filled data *
	 */
	@AfterTest
	public void clearData() throws Exception {
		//// scroll up and clear data
		try {
			clearPreFilledData();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
