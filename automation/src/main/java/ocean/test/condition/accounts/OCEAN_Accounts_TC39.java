package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_PBI_17239 class automates Ocean account modulePBI 17239, which
 * holds 1 Test Case; Test Condition Description : Validate that OCEAN display
 * correct cancellation method under account details. Note: To be validated for
 * Role ID as Dealer and Lender both.
 * 
 * @author Shalu Chauhan
 */
public class OCEAN_Accounts_TC39 extends AccountsModulePages {

	/**
	 * This function automates all test cases for test condition 01, 02, 03; Test
	 * Case description : Validate Account search on the basis of search parameter
	 * given and verify its values
	 * 
	 */
	@SuppressWarnings({ "null", "unused" })
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_39", dataProviderClass = AccountsDataProvider.class, description = "Validate that OCEAN display correct cancellation method under account details")
	public void validateCancellationMethodOnAccountDetailsPage(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("roleId", inputArray[1]);
		uiSearchData.put("roleType", inputArray[2]);
		uiSearchData.put("roleStatus", inputArray[7]);
		//// Navigate to mail service tab
		goToAccountsTab();
		goToAccountsSearchTab();
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hash map from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks
			uiSearchData = accounts_getDataSetforSearch(account_appendSearchData(inputArray));
		} else {
			uiSearchData = account_convertDataRemoveStar(inputArray);
		}
		//// run code for search
		searchContractGivenInputParamaters(uiSearchData);
		selectAccountForRoleType();
		waitForSomeTime(2);
		try {
			String cancellationRefundValue = getCancellationRefundMethodValue();
			logger.info(cancellationRefundValue);
			String cancellationRefundMethodfromDB = account_getCancellationRefundMethod(uiSearchData);
			assertEquals(cancellationRefundValue, cancellationRefundMethodfromDB);
		} catch (Exception e) {
			logger.info("Cancellation Refund Value is not exist on Ui screen");
		}

	}

}
