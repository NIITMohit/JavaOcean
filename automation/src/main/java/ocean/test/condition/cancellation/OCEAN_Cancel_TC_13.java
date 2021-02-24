package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * Validate premium refund calculation for different account types with
 * applicable account level rules by OCEAN 1. Product name 2. Effective date
 * (Multi-versions check) 3. Issue states.
 * 
 * @author Poonam.Kalra
 *
 */
public class OCEAN_Cancel_TC_13 extends CancellationModulePages {

	@Test(priority = 1, groups = { "regression","smoke1",
			"fullSuite" }, dataProvider = "fetchDataForTC14", dataProviderClass = CancellationDataProvider.class, description = "Validate premium refund calculation for a contract is as per defined primary account rules only for a product in one state")
	public void verifyPremiumRefundAsPerPrimaryAccountRules(String[] inputData) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[0], "FI2");
		System.out.print(dBsearch);
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		String primaryRoleName = dBsearch.get("name");
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("roleIdentifier"), "",
				"FI2");
		System.out.print(contractList);
		if (contractList.get("CERT").length() > 0) { //// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, Integer> cancelDays = calculateCancelDateTC14(dBsearch.get("EFFECTIVE_DATE"),
					contractList.get("SALE_DATE"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0) {
				initiatedBy = inputData[1];
			}
			if (inputData[2].length() > 0) {
				cancelReason = inputData[2];
			}
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), cancelDays.get("afterflatCanceldays")), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			System.out.print(calculatedCancellationValue);
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, String> contractDetails = getContractDetailsDB(cert);
			// get rule info view value in hashmap
			String primaryRoleId = dBsearch.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId, pCode,
					primaryRoleName);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			takeScreenshot();
			HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
			boolean result = compareDbWithUiRules(ruleInfoViewValue, dbruleInfo);
			if (result == true) {
				int daysDiff = cancelDays.get("daysdiffAfterFlat");
				calculatedValue = getRuleInfoExpectedResultTC14(dbruleInfo, contractDetails, daysDiff);
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

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC14", dataProviderClass = CancellationDataProvider.class, description = "Validate premium refund calculation for a contract is as per defined primary account rules from their effective date only")
	public void verifyPremiumRefundBeforeEffectiveDate(String[] inputData) throws Exception {
		boolean dbFlag = false;
		boolean matchFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[0], "FI2");
		HashMap<String, String> calculatedValue = new HashMap<String, String>();
		HashMap<String, String> contractList = new HashMap<String, String>();
		String primaryRoleName = dBsearch.get("name");
		contractList = cancellation_getContractIdBasedOnEffectiveDateAndRoleId(dBsearch.get("roleIdentifier"),
				dBsearch.get("EFFECTIVE_DATE"), "FI2");
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hashmap
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			long dayDiff = (calculateTermDays(dBsearch.get("EFFECTIVE_DATE"), contractList.get("SALE_DATE"))) - 5;
			if (dayDiff <= -60) {
				dayDiff = 60;
			} else {
				dayDiff = (dayDiff * -1);
			}
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0) {
				initiatedBy = inputData[1];
			}
			if (inputData[2].length() > 0) {
				cancelReason = inputData[2];
			}
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), (int) dayDiff), "");
			// click ok for cancellation completedsuccessfully
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			takeScreenshot();
			// go to standardCalculation
			click("standardCalculation");
			click("clickOK");
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			takeScreenshot();
			click("standOKbtn");
			// compare standardcalculation and normal calcuted value
			if (calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
				logger.info("Verfiying through Rule Info View No Account Rule Are Applied");
			} else {
				logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
				logger.info("AccountRule are applied");
			}
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			HashMap<String, String> contractDetails = getContractDetailsDB(cert);
			// get rule info view value in hashMap
			String primaryRoleId = dBsearch.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId, pCode,
					primaryRoleName);
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			takeScreenshot();
			// HashMap<Integer,HashMap<String,String>>ruleInfoViewValue =
			// getRuleInfoValue();
			// boolean result = compareDbWithUiRules(ruleInfoViewValue,dbruleInfo);

			// if(result== true) {
			int daysDiff = (int) dayDiff;
			calculatedValue = getRuleInfoExpectedResultTC14(dbruleInfo, contractDetails, daysDiff);
			logger.info("DBInfoViewValue====" + dbruleInfo);
			HashMap<String, String> calculatedCancellationValueWithoutComma = removeComma(calculatedCancellationValue);
			logger.info("calculatedValue====" + calculatedCancellationValueWithoutComma);
			System.out.print("NewValue" + calculatedCancellationValueWithoutComma);
			if (calculatedCancellationValueWithoutComma.equals(calculatedValue)) {
				matchFlag = false;
			} else {
				matchFlag = true;
			}

			assertEquals(matchFlag, true);
		} // }
		else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}
}
