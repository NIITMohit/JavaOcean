package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_26 class automates Ocean Cancel module Test Condition 124,
 * which holds 1 Test Case; Test Condition Description : Validate the
 * availability of account level warning as enquiry only, while processing
 * cancellation request..
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam Kalra
 */

public class OCEAN_Account_TC_26 extends CancellationModulePages {

	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProviderClass = CancellationDataProvider.class, description = "When user process for cancel a contract request, account level warning should be available on cancellation screen as read only. ")
	public void ValidateAccountLevelWarningAsReadOnly() throws Exception {
		boolean matchFlag = false;
		boolean dBFlag = false;
		HashMap<String, String> dbValues = getRoleIdWithAccountCancellationWarning();
		System.out.println(dbValues);
		String warningTextDb = (dbValues.get("AccWarText"));
		goToCancellationTab();
		goToMailServiceTab();
		//// create search data in hash map
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("CERT", dbValues.get("CERT"));
		System.out.println(uiSearchData);
		//// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		//// navigate to new cancel tab
		clickCancelButtonAndNavigateToNewCancellationTab();
		//// enter valid values on new cancellation tab screen and click calculate
		String initiatedBy = "Dealer";
		String cancelReason = "Customer Request";
		enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
				convertDateCancellation(dbValues.get("SALE_DATE"), 18), "");
		// click ok for cancellation completed successfully
		click("okClick");
		//// get cancel method value applied as flat or outside flat in hashmap
		waitForSomeTime(10);
		String[] dBWarning = warningTextDb.split("\\W+");
		int dBlenth = dBWarning.length;

		String uIWarning = getTextOfElement("AccountWarningTextOnCancellationPage");
		String[] uIWarningText = uIWarning.split("\\W+");
		int uIlenth = dBWarning.length;
		logger.info("UIAccountLevelWrningText======>>" + uIWarning);
		logger.info("DbAccountLevelWrningText======>>" + warningTextDb);
		if (uIlenth == dBlenth) {
			dBFlag = Arrays.equals(dBWarning, uIWarningText);
		}
		assertEquals(dBFlag, true);
		String warningTextBoxAttribute = getAttributeValue("AccountWarningTextOnCancellationPage", "Value.IsReadOnly");
		if (warningTextBoxAttribute.equalsIgnoreCase("True")) {
			matchFlag = true;
		}
		assertEquals(matchFlag, true);
	}
}
