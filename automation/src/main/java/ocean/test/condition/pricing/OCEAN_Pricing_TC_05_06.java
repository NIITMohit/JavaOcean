package ocean.test.condition.pricing;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.sun.xml.bind.v2.runtime.SchemaTypeTransducer;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Pricing_TC05_06 class automates Ocean Pricing module Test Condition 05
 * and 06, which holds 1 Test Case each; Test Condition 05 Description :
 * Validate premium calculation for multiple contracts from same master price
 * sheet. Test Condition 06 Description : Validate premium calculation for
 * multiple contracts from same sub master price sheet.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC_05_06 extends UnderwritingModulePages {

	/**
	 * This function automates test case 01 and 02 for test condition 05 and 06
	 * respectively; Test Case description : Validate premium calculation for
	 * multiple contracts from same master and sub-master price sheet.
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = "Validate premium calculation for multiple contracts from same master and sub-master price sheet")
	public void validatePremiumCalculationForMasterAndSubMasterPriceSheet(String[] inputData) throws Exception {
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
		if (premiumData.size() == 1) {
			HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
			//// get remittance name and file name
			/// iterate to multiple contracts with same price sheet
			for (Map.Entry<Integer, HashMap<String, String>> maps : contractFromRemittance.entrySet()) {
				String remittName = maps.getValue().get("RemittanceName");
				String fileName = maps.getValue().get("FILE_NAME");
				//// visit underwriting tab
				goToUnderWritingTab();
				//// Search a contract with pending state, remittance name and contract name is
				//// fetched from database
				searchContractwithPendingState(remittName, fileName);
				//// lock contract on user name and open enter values in contract window
				lockAndViewContract();
				//// enter all mandatory values only on new business form screen
				enterMandatoryValuesOnContract();
			}

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
			premiumData.put("ACTUALPRICESHEETCODE", inputData[1]);
		//// Fetch VIN, in case of Blank, use any hard code value
		if (inputData[8].length() > 0)
			premiumData.put("VIN", inputData[8]);
		else
			premiumData.put("VIN", "WBANE52010CK67774");
		//// Fetch MAKE, in case of Blank, use any hard code value
		if (inputData[9].length() > 0)
			premiumData.put("MAKE", inputData[9]);
		else
			premiumData.put("MAKE", "HONDA");
		//// Fetch MODEL, in case of Blank, use any hard code value
		if (inputData[10].length() > 0)
			premiumData.put("MODEL", inputData[10]);
		else
			premiumData.put("MODEL", "Accord");
		//// Fetch YEAR, in case of Blank, use any hard code value
		if (inputData[11].length() > 0)
			premiumData.put("YEAR", inputData[11]);
		else
			premiumData.put("YEAR", "2013");
		//// Fetch MILEAGE, in case of Blank, use any hard code value
		if (inputData[12].length() > 0)
			premiumData.put("MILEAGE", inputData[12]);
		else
			premiumData.put("MILEAGE", "1000");
		//// Fetch VEHICLEPRICE, in case of Blank, use any hard code value
		if (inputData[13].length() > 0)
			premiumData.put("VEHICLEPRICE", inputData[13]);
		else
			premiumData.put("VEHICLEPRICE", "1000");
		//// Fetch TERM, if exist
		if (inputData[14].length() > 0)
			premiumData.put("TERM", inputData[14]);
		//// Fetch COVERAGE, if exist
		if (inputData[15].length() > 0)
			premiumData.put("COVERAGE", inputData[15]);
		//// Fetch MIELAGEBAND, if exist
		if (inputData[16].length() > 0)
			premiumData.put("MIELAGEBAND", inputData[16]);
		//// Fetch CLASS, if exist
		if (inputData[17].length() > 0)
			premiumData.put("CLASS", inputData[17]);
		//// Fetch SERVICEDATE, if exist
		if (inputData[18].length() > 0)
			premiumData.put("SERVICEDATE", inputData[18]);
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
		//// Fetch PLAN_TYPE, in case of Blank, use All_Plans
		if (inputData[7].length() > 0)
			premiumData.put("PLAN_TYPE", inputData[7]);
		else
			premiumData.put("PLAN_TYPE", "All_Plans");
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

}
