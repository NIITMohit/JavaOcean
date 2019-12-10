package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_TC_04_05_06 class automates Ocean Cancel module Test Condition
 * 04 and 05 and 06, which holds 18 Test Case; Test Condition Description
 * :Validate all information related with Accounts on Account details screen,
 * actually fetched from sugar
 * 
 * @author Mohit Goel
 */
public class OCEAN_Account_TC_04_05_06 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate all information related with Accounts on Account
	 * details screen,actually fetched from sugar
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_04_05_06", dataProviderClass = AccountsDataProvider.class, description = "Validate all information related with Accounts on Account details screen,actually fetched from sugar")
	public void validateAccountDetailsFromSugar(String[] inputArray) throws Exception {
		
	}
}
