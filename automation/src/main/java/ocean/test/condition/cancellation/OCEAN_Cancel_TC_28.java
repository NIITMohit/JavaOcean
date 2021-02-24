package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * Validate ocean ability to add and apply account level rule for following
 * account type : 1. Lender Type A and Type B. 2. Dealer as independent /
 * franchise
 * 
 * @author Poonam.Kalra
 **/
public class OCEAN_Cancel_TC_28 extends CancellationModulePages {
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "primary account price sheet cancellation rules on a cancellation when defined by OCEAN user under account rule builder")
	public void verifyAccountLevelRuleForDealerIndependentType(String[] inputData) throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		// Navigate to Cancellation tab
		goToCancellationTab();
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithGivenParameterTC_28(
				"Independent", "Dealer");
		System.out.print(fetchDbValueBasedonRoleType);
		uiSearchData.put("PrimaryRoleID", fetchDbValueBasedonRoleType.get("PrimaryRoleID"));
		uiSearchData.put("AccID", fetchDbValueBasedonRoleType.get("AccID"));
		uiSearchData.put("PrimaryRoleName", fetchDbValueBasedonRoleType.get("AccName"));
		uiSearchData.put("PrimaryRoleType", fetchDbValueBasedonRoleType.get("ROLE_NAME"));
		HashMap<String, String> contractList = new HashMap<String, String>();
		String primaryRoleId = uiSearchData.get("PrimaryRoleID");
		String PrimaryRoleName = uiSearchData.get("PrimaryRoleName");
		contractList = cancellation_getContractIdTC_28(primaryRoleId, "Dealer");
		System.out.print(contractList);
		String priceSheetName = cancellation_getAccountRoleIdPriceSheet(contractList.get("PRICESHEET_ID"));
		String progCode = contractList.get("PROGRAM_CODE");

		// this function validate if account Level rules are defined for given role-id.
		// if no account level rules are defined then it will add account level rule .
		boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, priceSheetName);
		if (primaryResult == true) {
			SearchData = cancellation_AccountRuleBuilderDataTC29(inputData);
			addAccountLevelRulesToAccountId(SearchData);
			HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAccountLevelRules(primaryRoleId,
					progCode, PrimaryRoleName);
			if (dBValidation.size() > 0) {
				logger.info("Account Rule Added For Given Role Id---->" + uiSearchData.get("PrimaryRoleID"));
				dbFlag = true;
			} else {
				dbFlag = false;
				logger.info("No Account Rule Added For Given Role Id");
			}
			assertEquals(dbFlag, true);
		} else {
			logger.info("Account Rule Exists For Given Role Id---->" + primaryRoleId);
			dbFlag = true;
		}
		;

		/// Validate that OCEAN apply cancellation rules on a cancellation request.This
		/// Function search contract with given role_Id
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
			uiSearchData1.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData1);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 70), "");
			// click ok for cancellation completed successfully
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(20);
			takeScreenshot();
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			// get rule info view value in hashmap
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					progCode, PrimaryRoleName);
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
			assertEquals(dbFlag, false);
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "primary account price sheet cancellation rules on a cancellation when defined by OCEAN user under account rule builder")
	public void verifyAccountLevelRuleForLenderAListType(String[] inputData) throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithGivenParameterTC_28(
				"A-list", "Lender");
		System.out.print(fetchDbValueBasedonRoleType);
		// Navigate to Cancellation tab
		goToCancellationTab();
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		uiSearchData.put("PrimaryRoleID", fetchDbValueBasedonRoleType.get("PrimaryRoleID"));
		uiSearchData.put("AccID", fetchDbValueBasedonRoleType.get("AccID"));
		uiSearchData.put("PrimaryRoleName", fetchDbValueBasedonRoleType.get("AccName"));
		uiSearchData.put("PrimaryRoleType", fetchDbValueBasedonRoleType.get("ROLE_NAME"));
		HashMap<String, String> contractList = new HashMap<String, String>();
		String primaryRoleId = uiSearchData.get("PrimaryRoleID");
		String PrimaryRoleName = uiSearchData.get("PrimaryRoleName");
		contractList = cancellation_getContractIdTC_28(primaryRoleId, "Lender");
		if (contractList.get("CERT").length() > 0 || !(contractList.isEmpty())) {
			System.out.print(contractList);
			String priceSheetName = cancellation_getAccountRoleIdPriceSheet(contractList.get("PRICESHEET_ID"));
			String progCode = contractList.get("PROGRAM_CODE");
			// this function validate if account Level rules are defined for given role-id.
			// if no account level rules are defined then it will add account level rule .
			boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, priceSheetName);
			if (primaryResult == true) {
				SearchData = cancellation_AccountRuleBuilderDataTC29(inputData);
				addAccountLevelRulesToAccountId(SearchData);
				HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAccountLevelRules(primaryRoleId,
						progCode, PrimaryRoleName);
				if (dBValidation.size() > 0) {
					logger.info("Account Rule Added For Given Role Id---->" + uiSearchData.get("PrimaryRoleID"));
					dbFlag = true;
				} else {
					dbFlag = false;
					logger.info("No Account Rule Added For Given Role Id");
				}
				assertEquals(dbFlag, true);
			} else {
				logger.info("Account Rule Exists For Given Role Id---->" + primaryRoleId);
				dbFlag = true;
			}
			;
			/// Validate that OCEAN apply cancellation rules on a cancellation request.This
			/// Function search contract with given role_Id
			//// Navigate to Mail service tab
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
			uiSearchData1.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData1);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 70), "");
			// click ok for cancellation completed successfully
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(20);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			takeScreenshot();
			// get rule info view value in hashmap
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					progCode, PrimaryRoleName);
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
			assertEquals(dbFlag, false);
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "primary account price sheet cancellation rules on a cancellation when defined by OCEAN user under account rule builder")
	public void verifyAccountLevelRuleForDealerfranchiseType(String[] inputData) throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		// Navigate to Cancellation tab
		goToCancellationTab();
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithGivenParameterTC_28(
				"franchise", "Dealer");
		System.out.print(fetchDbValueBasedonRoleType);
		uiSearchData.put("PrimaryRoleID", fetchDbValueBasedonRoleType.get("PrimaryRoleID"));
		uiSearchData.put("AccID", fetchDbValueBasedonRoleType.get("AccID"));
		uiSearchData.put("PrimaryRoleName", fetchDbValueBasedonRoleType.get("AccName"));
		uiSearchData.put("PrimaryRoleType", fetchDbValueBasedonRoleType.get("ROLE_NAME"));
		HashMap<String, String> contractList = new HashMap<String, String>();
		String primaryRoleId = uiSearchData.get("PrimaryRoleID");
		String PrimaryRoleName = uiSearchData.get("PrimaryRoleName");
		contractList = cancellation_getContractIdTC_28(primaryRoleId, "Dealer");
		if (contractList.get("CERT").length() > 0) {
			System.out.print(contractList);
			String priceSheetName = cancellation_getAccountRoleIdPriceSheet(contractList.get("PRICESHEET_ID"));
			String progCode = contractList.get("PROGRAM_CODE");
			// this function validate if account Level rules are defined for given role-id.
			// if no account level rules are defined then it will add account level rule .
			boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, priceSheetName);
			if (primaryResult == true) {
				SearchData = cancellation_AccountRuleBuilderDataTC29(inputData);
				addAccountLevelRulesToAccountId(SearchData);
				HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAccountLevelRules(primaryRoleId,
						progCode, PrimaryRoleName);
				if (dBValidation.size() > 0) {
					logger.info("Account Rule Added For Given Role Id---->" + uiSearchData.get("PrimaryRoleID"));
					dbFlag = true;
				} else {
					dbFlag = false;
					logger.info("No Account Rule Added For Given Role Id");
				}
				assertEquals(dbFlag, true);
			} else {
				logger.info("Account Rule Exists For Given Role Id---->" + primaryRoleId);
				dbFlag = true;
			}
			;

			/// Validate that OCEAN apply cancellation rules on a cancellation request.This
			/// Function search contract with given role_Id
			//// Navigate to Mail service tab
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
			uiSearchData1.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData1);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 70), "");
			// click ok for cancellation completed successfully
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(20);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			// go to standardCalculation
			click("standardCalculation");
			click("clickOK");
			takeScreenshot();
			click("standOKbtn");
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
					progCode, PrimaryRoleName);
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
			assertEquals(dbFlag, false);
		}
	}

	@Test(priority = 1, groups = "smoke1", dataProvider = "fetchDataForTC_29", dataProviderClass = CancellationDataProvider.class, description = "primary account price sheet cancellation rules on a cancellation when defined by OCEAN user under account rule builder")
	public void verifyAccountLevelRuleForLenderBlist(String[] inputData) throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		HashMap<String, String> SearchData = new HashMap<String, String>();
		// Navigate to Cancellation tab
		goToCancellationTab();
		// Navigate to AccountsRuleBuilder service tab
		goToAccountsRuleBuilderTab();
		HashMap<String, String> fetchDbValueBasedonRoleType = cancellation_getAccountRoleIdWithGivenParameterTC_28(
				"B-list", "Lender");
		if (!(fetchDbValueBasedonRoleType == null)) {
			System.out.print(fetchDbValueBasedonRoleType);
			uiSearchData.put("PrimaryRoleID", fetchDbValueBasedonRoleType.get("PrimaryRoleID"));
			uiSearchData.put("AccID", fetchDbValueBasedonRoleType.get("AccID"));
			uiSearchData.put("PrimaryRoleName", fetchDbValueBasedonRoleType.get("AccName"));
			uiSearchData.put("PrimaryRoleType", fetchDbValueBasedonRoleType.get("ROLE_NAME"));
			HashMap<String, String> contractList = new HashMap<String, String>();
			String primaryRoleId = uiSearchData.get("PrimaryRoleID");
			String PrimaryRoleName = uiSearchData.get("PrimaryRoleName");
			contractList = cancellation_getContractIdTC_28(primaryRoleId, "Lender");
			System.out.print(contractList);
			if (contractList.get("CERT").length() > 0) {
				String priceSheetName = cancellation_getAccountRoleIdPriceSheet(contractList.get("PRICESHEET_ID"));
				String progCode = contractList.get("PROGRAM_CODE");
				// this function validate if account Level rules are defined for given role-id.
				// if no account level rules are defined then it will add account level rule .
				boolean primaryResult = validatePrimaryAccountLevelCancellationRules(uiSearchData, priceSheetName);
				if (primaryResult == true) {
					SearchData = cancellation_AccountRuleBuilderDataTC29(inputData);
					addAccountLevelRulesToAccountId(SearchData);
					HashMap<Integer, HashMap<String, String>> dBValidation = dBValidationOfAccountLevelRules(
							primaryRoleId, progCode, PrimaryRoleName);
					if (dBValidation.size() > 0) {
						logger.info("Account Rule Added For Given Role Id---->" + uiSearchData.get("PrimaryRoleID"));
						dbFlag = true;
					} else {
						dbFlag = false;
						logger.info("No Account Rule Added For Given Role Id");
					}
					assertEquals(dbFlag, true);
				} else {
					logger.info("Account Rule Exists For Given Role Id---->" + primaryRoleId);
					dbFlag = true;
				}
				;

				/// Validate that OCEAN apply cancellation rules on a cancellation request.This
				/// Function search contract with given role_Id
				//// Navigate to Mail service tab
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
				uiSearchData1.put("CERT", contractList.get("CERT"));
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData1);
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get("SALE_DATE"), 70), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(20);
				HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
				// go to standardCalculation
				click("standardCalculation");
				click("clickOK");
				HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
				takeScreenshot();
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
				HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,
						progCode, PrimaryRoleName);
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
				assertEquals(dbFlag, false);
			}
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, false);
		}
	}
}
