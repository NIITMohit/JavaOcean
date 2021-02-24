package ocean.test.condition.cancellation;

import ocean.modules.pages.CancellationModulePages;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * Validate cancellation rules applied on contracts, before and after updating
 * account rules from same account on same or different product type. .
 * 
 * @author Poonam.Kalra
 **/
public class OCEAN_Cancel_TC_31 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 31 it contains 3 test
	 * case ; Test Case description : Validate that OCEAN apply 1)Same cancellation
	 * rules on multiple cancellation request with same plan codes, when
	 * cancellation rules are defined for an account. 2)Re apply updated
	 * cancellation rules on multiple cancellation request with same plan codes,
	 * when cancellation rules are updated for an account 3)Re apply updated
	 * cancellation rules on cancellation request with different plan codes, when
	 * cancellation rules are updated for an account.
	 */

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "smoke1", description = "Validate that OCEAN apply same cancellation rules on multiple cancellation request with same plan codes, when cancellation rules are defined for an account")
	public void verifyAccountRuleForSamePlanCode() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer", 1);
		String priceSheetId = dBsearch.get("priceSheetId");                                     
		String primaryRoleId = dBsearch.get("roleIdentifier");
		String secRoleId = dBsearch.get("secRoleId");
		String secRoleName = dBsearch.get("secName");
		String primaryRoleName= dBsearch.get("name");
		String effDate = dBsearch.get("effectiveDate");
		String[] effDate1 = effDate.split("/s");
		String pCode = null ;
		effDate = effDate1[0];
		HashMap<Integer, HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		System.out.println(proCode);
		if (proCode.size() > 0) {
			String Pcode = proCode.get(1).get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> contractList = cancellation_getContractMultipleIdBasedOnRoleIdAndProgCode(
					primaryRoleId, Pcode);
			System.out.println(contractList);
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId,primaryRoleName);
			System.out.println(dbruleInfo);
			if (contractList.size() > 1) {
				//// Navigate to Mail service tab
				goToCancellationTab();
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				String CERT = contractList.get(1).get("CERT").toString();
				uiSearchData.put("CERT", CERT);
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				waitForSomeTime(10);
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get(1).get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				takeScreenshot();
				pCode = contractList.get(1).get("PROGRAM_CODE");
				// HashMap<Integer,HashMap<String, String>>dbruleInfo
				// =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}

				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("DBInfoViewValue====" + dbruleInfo);
				boolean compareResult = compareValues(ruleInfoViewValue, dbruleInfo);
				logger.info("First Contract Compare ---" + compareResult);
				assertEquals(compareResult, true);

				logger.info("For Second contract with Same DelearID and  PlaneCode---->");
				//// Navigate to Mail service tab
				goToMailServiceTab();
				waitForSomeTime(15);
				//// create search data in hash map
				String CERT1 = contractList.get(2).get("CERT").toString();
				HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
				uiSearchData1.put("CERT", CERT1);
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData1);
				takeScreenshot();
				;
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy1 = "Dealer";
				String cancelReason1 = "Customer Request";
				waitForSomeTime(10);
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy1, cancelReason1, "",
						convertDateCancellation(contractList.get(2).get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(15);
				// HashMap<Integer,HashMap<String, String>>dbruleInfo
				// =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				takeScreenshot();
				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue1 = getRuleInfoValue();
				logger.info("ruleInfoViewValue1====" + ruleInfoViewValue1);
				logger.info("DBInfoViewValue1====" + dbruleInfo);
				boolean compareResult1 = compareValues(ruleInfoViewValue, dbruleInfo);
				assertEquals(compareResult1, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		} else {
			new SkipException("Only One ProgCode So Can't Test ");
			assertEquals(dbFlag, true);
		}
	}

	@Test(priority = 1, groups = "smoke1", description = "Validate that OCEAN reapply updated cancellation rules on multiple cancellation request with same plan codes, when cancellation rules are updated for an account.")
	public void verifyUpdatedAccountRuleForSamePlanCode() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer", 1);
		String primaryRoleId = dBsearch.get("roleIdentifier");
		String secRoleId = dBsearch.get("secRoleId");
		dBsearch.get("secName");
		String primaryRoleName= dBsearch.get("name");
		String effDate = dBsearch.get("effectiveDate");
		String[] effDate1 = effDate.split("/s");
		effDate = effDate1[0];
		HashMap<Integer, HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		if (proCode.size() > 1) {
			String Pcode = proCode.get(1).get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> contractList = cancellation_getContractMultipleIdBasedOnRoleIdAndProgCode(
					primaryRoleId, Pcode);
			System.out.println(contractList);
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAccountLevelRules(primaryRoleId, Pcode,
					primaryRoleName);
			System.out.println(dbruleInfo);
			if (contractList.size() > 1) {
				goToAccountsRuleBuilderTab();
				HashMap<String, String> updatedAccountLevelRules = editedAcountRuleValue(dbruleInfo);
				logger.info("Updating Account Rule for" + primaryRoleId + " and " + Pcode + "---->"
						+ updatedAccountLevelRules);
				findAccountAndEditAccountRule(updatedAccountLevelRules, primaryRoleName);
				HashMap<String, String> editedDbruleInfo = dBValueOfAddAccountLevelRules(primaryRoleId, Pcode,
						primaryRoleName);
				boolean compareResult = compareValuesforEditedAccountRule(updatedAccountLevelRules, editedDbruleInfo);
				if (compareResult = true) {
					logger.info("Account Rule Builder for " + primaryRoleId + "with Programm Code " + Pcode
							+ " is correctly edited for " + updatedAccountLevelRules + " category");
				}
				assertEquals(compareResult, true);
				//// Navigate to Mail service tab
				goToCancellationTab();
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				String CERT = contractList.get(1).get("CERT").toString();
				uiSearchData.put("CERT", CERT);
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get(1).get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				takeScreenshot();
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("DBInfoViewValue====" + dbruleInfo);
				boolean compareResultCancellation = compareValuesNew(ruleInfoViewValue, editedDbruleInfo);
				logger.info("Rule info Applied Compare with Edited DbRules ---" + compareResultCancellation);
				assertEquals(compareResultCancellation, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		}
	}

	@Test(priority = 1, groups = "smoke1", description = "Validate that OCEAN reapply updated cancellation rules on multiple cancellation request with different plan codes,when cancellation rules are updated for an account.")
	public void verifyUpdatedAccountRuleForDifferentPlanCode() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer", 1);
		String primaryRoleId = dBsearch.get("roleIdentifier");
		String secRoleId = dBsearch.get("secRoleId");
		String primaryRoleName= dBsearch.get("name");
		String effDate = dBsearch.get("effectiveDate");
		String[] effDate1 = effDate.split("/s");
		effDate = effDate1[0];
		HashMap<Integer, HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		if (proCode.size() > 1) {
			String Pcode = proCode.get(1).get("PROGRAM_CODE");
			String Pcode1 = proCode.get(2).get("PROGRAM_CODE");
			HashMap<Integer, HashMap<String, String>> contractList = cancellation_getContractMultipleIdBasedOnRoleIdAndProgCode(
					primaryRoleId, Pcode);
			HashMap<Integer, HashMap<String, String>> contractList1 = cancellation_getContractMultipleIdBasedOnRoleIdAndProgCode(
					primaryRoleId, Pcode1);
			System.out.println(contractList);
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAddAccountLevelRules(primaryRoleId,
					primaryRoleName);
			System.out.println(dbruleInfo);
			if (contractList.size() > 1 && contractList1.size() > 1) {
				goToAccountsRuleBuilderTab();
				/// for First Plan code
				HashMap<String, String> updatedAccountLevelRules = editedAcountRuleValue(dbruleInfo);
				logger.info("Updating Account Rule for" + primaryRoleId + "---->" + updatedAccountLevelRules);
				findAccountAndEditAccountRuleRows(updatedAccountLevelRules, primaryRoleName, "");
				HashMap<String, String> editedDbruleInfo = dBValidationOfEditedAccountLevelRules(primaryRoleId,
						primaryRoleName);
				boolean compareResult = compareValuesforEditedAccountRule(updatedAccountLevelRules, editedDbruleInfo);
				if (compareResult = true) {
					logger.info("Account Rule Builder for " + primaryRoleId + " is correctly edited for "
							+ updatedAccountLevelRules);
				}
				assertEquals(compareResult, true);
				waitForSomeTime(10);
				//// Navigate to Mail service tab
				goToCancellationTab();
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				String CERT = contractList.get(1).get("CERT").toString();
				uiSearchData.put("CERT", CERT);
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get(1).get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				takeScreenshot();
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("DBInfoViewValue====" + editedDbruleInfo);
				boolean compareResultCancellation = compareValuesNew(ruleInfoViewValue, editedDbruleInfo);
				if (compareResultCancellation = true) {
					logger.info("Rule info Applied Compare with Edited DbRules For First Contract ---"
							+ compareResultCancellation);
				}
				assertEquals(compareResultCancellation, true);
				// verfing for second contract wih another plan code
				goToCancellationTab();
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
				String CERT1 = contractList1.get(1).get("CERT").toString();
				uiSearchData.put("CERT", CERT1);
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData1);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList1.get(1).get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				takeScreenshot();
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue1 = getRuleInfoValue();
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue1);
				logger.info("DBInfoViewValue====" + editedDbruleInfo);
				boolean compareResultCancellation1 = compareValuesNew(ruleInfoViewValue1, editedDbruleInfo);
				if (compareResultCancellation1 = true) {
					logger.info(
							"Rule info Applied Compare with Edited DbRules For Second Contract with different Plan Code ---"
									+ compareResultCancellation1);
				}
				assertEquals(compareResultCancellation1, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "smoke1", description = "Validate that OCEAN apply same cancellation rules on multiple cancellation request with same plan codes, when cancellation rules are defined for an account")
	public void verifyCancelRuleForSamePlanCode() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer", 1);
		String priceSheetId = dBsearch.get("priceSheetId");                                     
		String primaryRoleId = dBsearch.get("roleIdentifier");
		String secRoleId = dBsearch.get("secRoleId");
		String secRoleName = dBsearch.get("secName");
		String primaryRoleName= dBsearch.get("name");
		String effDate = dBsearch.get("effectiveDate");
		String[] effDate1 = effDate.split("/s");
		String pCode = null ;
		effDate = effDate1[0];
		HashMap<Integer, HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		if (proCode.size() > 1) {
			String Pcode = proCode.get(1).get("PROGRAM_CODE");
			HashMap<String, String> contractList = cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,Pcode,secRoleName);
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAddAccountLevelRules(primaryRoleId,
					primaryRoleName);
			if (contractList.get("CERT").length() > 0) {
				//// Navigate to Mail service tab
				goToCancellationTab();
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				uiSearchData.put("CERT", contractList.get("CERT"));
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				takeScreenshot();
				 pCode = contractList.get("PROGRAM_CODE");
				// HashMap<Integer,HashMap<String, String>>dbruleInfo
				// =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
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
			HashMap<String, String> contractList1 = cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,Pcode,secRoleName);
			if (contractList1.get("CERT").length() > 0) {
				//// Navigate to Mail service tab
				goToMailServiceTab();
				waitForSomeTime(15);
				//// create search data in hash map
				HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
				uiSearchData1.put("CERT", contractList1.get("CERT"));
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData1);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				waitForSomeTime(10);
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(15);
				takeScreenshot();
				// HashMap<Integer,HashMap<String, String>>dbruleInfo
				// =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				takeScreenshot();
				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
				logger.info("ruleInfoViewValue1====" + ruleInfoViewValue);
				logger.info("DBInfoViewValue1====" + dbruleInfo);
				boolean compareResult = compareValues(ruleInfoViewValue, dbruleInfo);
				assertEquals(compareResult, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		} else {
			new SkipException("Only One ProgCode So Can't Test ");
			assertEquals(dbFlag, true);
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "smoke1", description = "Validate that OCEAN reapply updated cancellation rules on multiple cancellation request with same plan codes, when cancellation rules are updated for an account.")
	public void verifyCancelRuleForDifferentPlanCode() throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer", 1);
		String priceSheetId = dBsearch.get("priceSheetId");                                     
		String primaryRoleId = dBsearch.get("roleIdentifier");
		String secRoleId = dBsearch.get("secRoleId");
		String secRoleName = dBsearch.get("secName");
		String primaryRoleName= dBsearch.get("name");
		String effDate = dBsearch.get("effectiveDate");
		String[] effDate1 = effDate.split("/s");
		String pCode = null ;
		effDate = effDate1[0];
		HashMap<Integer, HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		if (proCode.size() > 1) {
			String Pcode = proCode.get(1).get("PROGRAM_CODE");
			HashMap<String, String> contractList = cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,Pcode,secRoleName);
			HashMap<Integer, HashMap<String, String>> dbruleInfo = dBValidationOfAddAccountLevelRules(primaryRoleId,
					primaryRoleName);
			if (contractList.get("CERT").length() > 0) {
				//// Navigate to Mail service tab
				goToCancellationTab();
				goToMailServiceTab();
				//// create search data in hash map
				HashMap<String, String> uiSearchData = new HashMap<String, String>();
				uiSearchData.put("CERT", contractList.get("CERT"));
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				takeScreenshot();
				pCode = contractList.get("PROGRAM_CODE");
				// HashMap<Integer,HashMap<String, String>>dbruleInfo
				// =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
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
			HashMap<String, String> contractList1 = cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,Pcode,secRoleName);
			if (contractList1.get("CERT").length() > 0) {
				//// Navigate to Mail service tab
				goToMailServiceTab();
				waitForSomeTime(15);
				//// create search data in hash map
				HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
				uiSearchData1.put("CERT", contractList1.get("CERT"));
				//// Search Data based on contract Id
				searchContractGivenInputParamaters(uiSearchData1);
				takeScreenshot();
				//// navigate to new cancel tab
				clickCancelButtonAndNavigateToNewCancellationTab();
				//// enter valid values on new cancellation tab screen and click calculate
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				waitForSomeTime(10);
				enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(15);
				takeScreenshot();
				// HashMap<Integer,HashMap<String, String>>dbruleInfo
				// =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
				try {
					click("listtoggleStateRuleInfoView");
				} catch (Exception e) {
					click("ruleInfoRefersh");
				}
				takeScreenshot();
				HashMap<Integer, HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
				logger.info("ruleInfoViewValue1====" + ruleInfoViewValue);
				logger.info("DBInfoViewValue1====" + dbruleInfo);
				boolean compareResult = compareValues(ruleInfoViewValue, dbruleInfo);
				assertEquals(compareResult, true);
			} else {
				new SkipException("no value exist in db for Given PriceSheet");
				assertEquals(dbFlag, true);
			}
		} else {
			new SkipException("Only One ProgCode So Can't Test ");
			assertEquals(dbFlag, true);
		}
	}
}
