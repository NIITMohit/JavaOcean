package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_PBI_16856 class automates Ocean account module PBI 16856, which
 * holds 4 Test Case; Test Condition Description :Accounts - LW setup screen
 * incorrectly adding NUMERIC_VALUE in PRICESHEET_PRODUCT_TIER_TARGET table for
 * deductibles
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Accounts_TC40 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 40 which include
	 * 1,2 test case for this; Test Case description : Accounts - LW setup screen
	 * incorrectly adding NUMERIC_VALUE in PRICESHEET_PRODUCT_TIER_TARGET table for
	 * deductibles
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke", "smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC_40", dataProviderClass = AccountsDataProvider.class, description = "Validate PRICESHEET_PRODUCT_TIER_TARGET for $0 as numeric value for deductible, if user defined any value.")
	public void validateCorrectDeductibleValueInDB(String[] inputData) throws Exception {
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("lWPlanCode", inputData[0]);
		uiSearchData.put("deductValue", inputData[1]);
		String roleId = "";
		roleId = prepareLWSetup(uiSearchData.get("lWPlanCode"), uiSearchData.get("deductValue"));
		if (roleId.length() > 0) {
			HashMap<String, String> premiumData = new HashMap<String, String>();
			premiumData.put("PRICESHEETCODE", uiSearchData.get("lWPlanCode"));
			HashMap<String, String> dbData = setAllDataForPremiumCalculationLimitedPriceSheet(premiumData, roleId);
			if (dbData.size() > 1)
				assertEquals(dbData.get("DeductibleNumericValue"), "0.0");
		} else
			throw new SkipException("not such data found");
	}

}
