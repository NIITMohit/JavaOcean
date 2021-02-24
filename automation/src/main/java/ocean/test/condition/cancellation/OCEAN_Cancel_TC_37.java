package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationModulePages;

/**
 * Validate application of rules by their effective date at : 1. Account level
 * 2. Price sheet level
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Cancel_TC_37 extends CancellationModulePages {

	/**
	 * This function automates test case for test condition OCEAN_Cancel_T37; Test
	 * Case description : Validate application of rules by their effective date at
	 * Account level under account rule builder.
	 * 
	 */
	@Test(priority = 1, groups = "smoke1", description = "Validate application of rules by their effective date at Account level under account rule builder.")
	public void validateAccountLevelruleOnTheBasisOfEffectiveDate() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer", 1);
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndRoleId(dBsearch.get("roleId"),"");
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, Integer> cancelDays = calculateCancelDateTC14(dBsearch.get("EFFECTIVE_DATE"),
					contractList.get("SALE_DATE"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), cancelDays.get("afterflatCanceldays")), "");
			// click ok for cancellation completed successfully
			click("clickOk");
			// click ok for cancellation completed successfully
			click("clickOk");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(30);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			// go to standardCalculation
			click("standardCalculation");
			// click("clickOk");
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			click("standOKbtn");
			// compare standardcalculation and normal calcuted value
			if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
				logger.info("Verfiying through Rule Info View");
			} else {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
				logger.info("Verfiying  through Rule Info View");
			}
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
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
			logger.info("DBInfoViewValue====" + dbruleInfo);
			boolean compareResult = compareValues(ruleInfoViewValue, dbruleInfo);
			assertEquals(compareResult, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}

	}

}
