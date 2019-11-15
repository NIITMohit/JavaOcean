package ocean.test.condition.pricing;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import ocean.common.DataProviderClass;
import ocean.modules.pages.underwritingModulePages;

/**
 * OCEAN_Pricing_TC05_06 class automates Ocean Pricing module Test Condition 05
 * and 06, which holds 1 Test Case each; Test Condition 05 Description :
 * Validate premium calculation for multiple contracts from same master price
 * sheet. Test Condition 06 Description : Validate premium calculation for
 * multiple contracts from same sub master price sheet.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC_05_06 extends underwritingModulePages {

	/**
	 * This function automates test case 01 and 02 for test condition 05 and 06
	 * respectively; Test Case description : Validate premium calculation for
	 * multiple contracts from same master and sub-master price sheet.
	 * 
	 */
	@Test(priority = 1, groups = "regression", description = "Validate premium calculation for multiple contracts from same master and sub-master price sheet")
	public void validatePremiumCalculationForMasterAndSubMasterPriceSheet() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = getPendingContractwithRemittance();
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
	}

}
