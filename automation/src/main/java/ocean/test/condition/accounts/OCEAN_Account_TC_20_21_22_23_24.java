package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_TC_21_22_23_24 class automates Ocean Cancel module Test
 * Condition 21, 22 ,23 ,24, which holds 18 Test Case; Test Condition
 * Description :Validate correct premium calculation in pricer
 * 
 * @author Mohit Goel
 */
public class OCEAN_Account_TC_20_21_22_23_24 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 21, 22 ,23 ,24;
	 * Test Case description :Validate correct premium calculation in pricer
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC_04_05_06", dataProviderClass = AccountsDataProvider.class, description = "Validate correct premium calculation in pricer")
	public void validatePremiumFromPricer(String[] inputArray) throws Exception {
		
		
	}
}
