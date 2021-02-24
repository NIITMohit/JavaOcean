package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Compliance_TC_11class automates Ocean Compliance module Test Condition
 * which hold 1 test case: Test case description: Validate ocean ability to
 * apply priority over compliance cancellation rules for account with account
 * level rules only when account is active.
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Compliance_TC_11 extends CancellationModulePages {

	/**
	 * This function automates test case for test condition OCEAN_Compliance _T36
	 * Account should be in Active status under Accounts Module;
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC27", dataProviderClass = CancellationDataProvider.class, description = "Validate Ocean ability to apply priority over compliance cancellation rule when user apply standard calculation for premium when account is active.")
	public void verifyPriorityComplianceRuleOverAccountlevelCancellationOnActiveAcc(String[] inputData)
			throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		//// Search Role id with accountlevel Cancellation rule added from db where
		//// account status is active
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[0], 1);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndRoleId(dBsearch.get("roleId"),"");
		if (contractList.get("CERT").length() > 0) {
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id"/"
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[0].length() > 0)
				initiatedBy = inputData[0];
			if (inputData[1].length() > 0)
				cancelReason = inputData[1];
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 62), "");
			takeScreenshot();
			// click ok for cancellation completed successfully
			click("okClick");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(30);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			System.out.print(calculatedCancellationValue);
			takeScreenshot();
			// get rule info view value in hashmap
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, String> contractDetails = getContractDetailsDB(cert);
			// get rule info view value in hashmap
			String primaryRoleId = dBsearch.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					pCode);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			takeScreenshot();
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			boolean result = compareDbWithUiRules(ruleInfoViewValue, dbruleInfo);
			if (result == true) {
				int daysDiff = 62;
				HashMap<String, String> calculatedValue = getRuleInfoExpectedResultTC14(dbruleInfo, contractDetails,
						daysDiff);
				logger.info("DBInfoViewValue====" + dbruleInfo);
				HashMap<String, String> calculatedCancellationValueWithoutComma = removeComma(
						calculatedCancellationValue);
				logger.info("calculatedValue====" + calculatedCancellationValueWithoutComma);
				System.out.print("NewValue" + calculatedCancellationValueWithoutComma);
				if (calculatedCancellationValueWithoutComma.equals(calculatedValue)) {
					matchFlag = true;
				}
				assertEquals(matchFlag, true);
			}
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

}
