package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_27 class automates Ocean Cancel module Test Condition 27 ,
 * which holds 2 Test Case; Test Condition Description :Validate that OCEAN
 * apply priority to account level cancellation rule over rules from compliance
 * form, if account is inactive/active but rules are effective from account rule
 * builder.
 * 
 * @author Poonam Kalra
 */
public class OCEAN_Cancel_TC_27 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition OCEAN_Cancel_T100
	 * Account should be in Active status under Accounts Module;
	 */
	@Test(priority = 1, groups ="smoke1", dataProvider = "fetchDataForTC27", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN apply priority to account level cancellation rule over rules from compliance form")
	public void verifyPriorityAccountlevelCancellationRuleOverComplianceRuleWithActiveAcc(String[] inputData)
			throws Exception {
		boolean dbFlag = false;
	//// Search Role id with accountlevel Cancellation rule added from db where
			//// account status is inactive
			HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[1], 1);
			HashMap<String, String> contractList = new HashMap<String, String>();
			String primaryRoleId = dBsearch.get("roleIdentifier");
		      String secRoleId = dBsearch.get("secRoleId");
		      String primaryRoleName= dBsearch.get("name");
			contractList = cancellation_getContractIdBasedOnRoleId(primaryRoleId,secRoleId);
			
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
				// go to standardCalculation
				click("standardCalculation");
				click("okClick");
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
				
				HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,primaryRoleName);
						
				System.out.print("db--->" + dbruleInfo);
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				HashMap<Integer, String[]> ruleInfovalue = getRuleInfoValueResult();

				logger.info("AccountLevelRulesFromDataBase=====>>" + dbruleInfo);
				boolean compareResult = compareRulesValues(dbruleInfo, ruleInfovalue);
				assertEquals(compareResult, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		}

		/**
		 * This function automates test case for test condition OCEAN_Cancel_99 Account
		 * should be in Inactive status under Accounts Module;
		 */
		@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC27", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN apply priority to account level cancellation rule over rules from compliance form when account is inactive")
		public void verifyPriorityAccountlevelCancellationRuleOverComplianceRuleWithInActiveAccount(String[] inputData)
				throws Exception {
			boolean dbFlag = false;
			//// Search Role id with accountlevel Cancellation rule added from db where
			//// account status is inactive
			HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[1], 2);
			HashMap<String, String> contractList = new HashMap<String, String>();
			String primaryRoleId = dBsearch.get("roleIdentifier");
		      String secRoleId = dBsearch.get("secRoleId");
		      String primaryRoleName= dBsearch.get("name");
			contractList = cancellation_getContractIdBasedOnRoleId(primaryRoleId,secRoleId);
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
				// go to standardCalculation
				click("standardCalculation");
				click("okClick");
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
				HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,primaryRoleName);
				System.out.print("db--->" + dbruleInfo);
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				HashMap<Integer, String[]> ruleInfovalue = getRuleInfoValueResult();

				logger.info("AccountLevelRulesFromDataBase=====>>" + dbruleInfo);
				boolean compareResult = compareRulesValues(dbruleInfo, ruleInfovalue);
				assertEquals(compareResult, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		}

	}
