package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.ComplianceModulePages;

/**
 * OCEAN_Compliance _TC10 class automates Ocean Cancel module Test Condition 34
 * and 35 , which holds 2 Test Case; Test Condition Description Validate user
 * ability to apply deduction of claims in refund calculation and authorize
 * cancellation request for final payment.
 * 
 * @author Poonam Kalra
 */

public class OCEAN_Compliance_TC_10 extends ComplianceModulePages {
	/**
	 * This function automates test case for test condition 34 and 35; Test Case
	 * description : Validate user ability to select deduct claims and recalculate
	 * premium refund correctly by OCEAN, if compliance form allows. And Validate
	 * user ability to authorize payment after deduction of claims in premium refund
	 * calculated by OCEAN.
	 */

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_25", dataProviderClass = CancellationDataProvider.class, description = "Validate user ability to select deduct claims and recalculate premium refund  correctly by OCEAN, if compliance form allows.")
	public void validateDeductClaimAndAuthorisedAfterDeductionOfClaims(String inputData[]) throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = compliance_getContractIdBasedOnStateAndProductCode("ARC", "Arizona");
		String primaryRoleName = dBsearch.get("name");
		// HashMap<String, String> contractList = new HashMap<String, String>();
		if (dBsearch.get("cert").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", dBsearch.get("cert"));
			//// Search Data based on contract Id
			searchContractGivenInputParamatersCancellation(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(dBsearch.get("Sale_Date"), 18), "");
			// click ok for cancellation completed successfully
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(30);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			takeScreenshot();
			String primaryRoleId = dBsearch.get("ROLE_IDENTIFIER");
			String pCode = dBsearch.get("PROGRAM_CODE");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			HashMap<String, String> ruleInfoViewValue = getRuleInfoRule();
			logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
			if (ruleInfoViewValue.containsKey("Claims"))
				;
			{
				overRideCancellationValuesAndClickCalculate("90", "0");
			}
			HashMap<String, String> newCancellationValue = getCancellationDetails();
			String payee = getAttributeValue("selectPayeeText", "Value.Text");
			String payyeName = getTextOfElement("selectPayeeName");
			System.out.println(payee);
			System.out.println(payyeName);
			if (payyeName.equalsIgnoreCase("")) {
				type("selectPayeeText", "Primary Account");
			}
			if (newCancellationValue.equals(calculatedCancellationValue)) {
				assertEquals(dbFlag, true);
			}
			click("clickAuthorize");
			waitForSomeTime(30);
			goToMailServiceTab();
			String myStatus = getValue("statusOfContract");
			System.out.print(myStatus);
			String uiStatus = "Cancelled";
			assertEquals(myStatus, uiStatus);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}
}
