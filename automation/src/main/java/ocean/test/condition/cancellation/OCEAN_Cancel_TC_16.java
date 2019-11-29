package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationDataProvider;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_16 class automates Ocean Cancel module Test Condition 16 ,
 * which holds 2 Test Case; Test Condition Description : Validate user ability
 * to override refund calculation and authorize cancellation request for final
 * payment
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_16 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 16; Test Case
	 * description : Validation that user is able to edit override customer premium
	 * refund calculation by OCEAN via editing of following fields: 1. Cancel Method
	 * 2. Refund% 3. Gross Refund 4. Cancel Fee
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchPriceSheet", dataProviderClass = CancellationDataProvider.class, description = "Validation that user is able to edit override customer premium refund calculation by OCEAN  via editing of following fields: 1. Cancel Method 2. Refund%"
			+ "3. Gross Refund 4. Cancel Fee")
	public void validateOverRidePremiumRefundForAnyPriceSheet(String pricesheet) throws Exception {
		HashMap<String, String> contractList = new HashMap<String, String>();
		//// get contract id based for processed contract only with current year
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("processed", pricesheet);
		String contractId = contractList.get("CERT");
		if (contractId.length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Repossession", "", "", "");
			click("clickOK");
			click("clickOK");
			//// override cancellation values
			overRideCancellationValuesAndClickCalculate("10", "10");
			HashMap<String, String> myData = new HashMap<String, String>();
			myData.put("CANCEL_FEE_AMOUNT", "10");
			myData.put("REFUND_PERCENTAGE", "10");
			//// take screenshot of new values
			takeScreenshot();
			selectCancellationTaskStatus("Authorize");
			//// verify db for all data and check refund fee and cancel fee
			HashMap<String, String> dbData = cancellation_getRefundPercentAndCancelFee(contractId);
			assertEquals(dbData, myData);
		} else {
			new SkipException("no contract exist in db");
		}
	}
}
