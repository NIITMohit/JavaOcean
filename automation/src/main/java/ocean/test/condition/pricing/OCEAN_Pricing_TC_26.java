package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.PricingModulePages;

/**
 * PBI--17919 OCEAN_Pricing_TC_26 class automates Ocean Pricing module Test
 * Condition 26 which holds 7 Test Cases; Test Condition 26 Description : Adding
 * a 'Form Type' PSH Property - Account State vs Customer State - UI and CLR
 * Updates
 * 
 * @author Poonam kalra
 */
public class OCEAN_Pricing_TC_26 extends PricingModulePages {

	@SuppressWarnings("unused")
	@Test(priority = 5, groups = "smoke", dataProvider = "fetchDataForTC_26", dataProviderClass = PricingDataProvider.class, description = "Validate that the Form Type field exists and that following two entries available in the drop-down list:Selling Account State ,Customer state..")
	public void validateFormTypeField(String[] inputData) throws Exception {
		boolean matchflag = false;
		String priceSheetCode = inputData[0];
		goToPricingTab();
		waitForSomeTime(3);
		visitPriceSheetListTab();
		HashMap<String, String> dbFormTypeValue = fetchFormTypeValueDB(priceSheetCode);
		System.out.print(dbFormTypeValue.get("VALUE"));
		boolean formTypeUI = uiFormTypeComboValue(priceSheetCode);
		assertEquals(formTypeUI, true);
	}

	@SuppressWarnings("unused")
	@Test(priority = 5, groups = "regression", dataProvider = "fetchDataForTC_26", dataProviderClass = PricingDataProvider.class, description = "Validate that the user is able to update the form type property as customer state from default value as selling account state")
	public void validateFormTypePropertyChangable(String[] inputData) throws Exception {
		String priceSheetCode = inputData[0];
		goToPricingTab();
		waitForSomeTime(10);

		visitPriceSheetListTab();
		String formTypeUI = uiFormTypeValue(priceSheetCode);
		HashMap<String, String> dbFormTypeValue = fetchFormTypeValueDB(priceSheetCode);
		System.out.print(dbFormTypeValue.get("VALUE"));
		String dbvalue = dbFormTypeValue.get("VALUE");
		String formTypeUI1 = uiChangeFormTypeValue(priceSheetCode, dbvalue);
		HashMap<String, String> dbFormTypeValue1 = fetchFormTypeValueDB(priceSheetCode);
		assertEquals(dbFormTypeValue1.get("VALUE"), formTypeUI1);
	}

	@Test(priority = 5, groups = "regression", dataProvider = "fetchDataForTC_26", dataProviderClass = PricingDataProvider.class, description = "Validate that the Form Type field exists in 1)Under contract summary 2) Contract detail screen under search module 3) Contract detail screen under search module/ web contract")
	public void validateFormTypePropertyDisplayed(String[] inputData) throws Exception {
		String progCode = inputData[0];
		HashMap<String, String> dBsearch1 = fetchActiveContractValueDB(progCode, "WebContract");
		HashMap<String, String> dBsearch = fetchActiveContractValueDB(progCode, "Contract");
		String dbFormTypeValue = dBsearch.get("VALUE");
		String dbFormTypeValue1 = dBsearch1.get("VALUE");
		/// Under contract summary
		String contractFormVerification = uiContractValidtion(dBsearch.get("CERT"));
		assertEquals(dbFormTypeValue, contractFormVerification);
		// Contract detail screen under search module
		String searchFormVerification = uiSearchValidtion(dBsearch.get("CERT"));
		assertEquals(dbFormTypeValue, searchFormVerification);
		// Contract detail screen under search module/ web contract")
		String searchWebFormVerification = uiContractWebSearchValidtion(dBsearch1.get("CERT"));
		assertEquals(dbFormTypeValue1, searchWebFormVerification);

	}

	@SuppressWarnings({ "unused", "unlikely-arg-type" })
	@Test(priority = 5, groups = "regression", description = "Validate that the compliance rule applied by seller state for a price sheet if form type property is selected as seller state by price sheet list.")
	public void validateComplianceRuleAppliedSellerState() throws Exception {
		boolean matchFlag = false;
		boolean dbFlag = false;
		HashMap<String, String> contractList = dBGetSellerStateDealerDetail();
		if (contractList.size() > 1) {
			String contractNunmber = contractList.get("CERT");
			String PrimaryName = contractList.get("NAME");
			String priceSheetGroup = compliance_PricesheetIdbasedOnCert(contractNunmber);
			String productName = contractList.get("productName");
			String state = compliance_StateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			// Move to Contract Builder tab for compliance rule pick
			complainceDataSearch(priceSheetGroup, state);
			takeScreenshot();

			HashMap<String, String> ruleMap = new HashMap<String, String>();
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);

			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			// get rule info view value in hashmap
			String primaryRoleId = contractList.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			/// Fetch Data form RuleInfoView On New Cancellation Page
			HashMap<String, String> ruleInfoViewValue = getRuleInfoRule();
			System.out.print(ruleInfoViewValue);
			takeScreenshot();
			System.out.print(ruleMap);
			System.out.print(ruleInfoViewValue);
			if (ruleMap.containsKey("STDNCB")) {
				ruleMap.remove("STDNCB");
			}
			if (ruleMap.containsKey("STDPRORATE")) {
				ruleMap.remove("STDPRORATE");
			}
			if (ruleMap.containsValue(ruleInfoViewValue)) {
				matchFlag = true;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			} else {
				matchFlag = false;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			}
			assertEquals(dbFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}

	}

	@SuppressWarnings({ "unlikely-arg-type", "unused" })
	@Test(priority = 5, groups = "regression", description = "Validate that the compliance rule applied by seller state for a price sheet if form type property is selected as seller state by price sheet list.")
	public void validateComplianceRuleAppliedCustomerState() throws Exception {
		boolean matchFlag = false;
		boolean dbFlag = false;
		HashMap<String, String> contractList = dBGetSellerStateDealerDetail();
		if (contractList.size() > 1) {
			String contractNunmber = contractList.get("CERT");
			String PrimaryName = contractList.get("NAME");
			String priceSheetGroup = compliance_PricesheetIdbasedOnCert(contractNunmber);
			String productName = contractList.get("productName");
			String state = compliance_CustomerStateDisplayNamebasedOnCertAndPAccount(contractNunmber);
			// Move to Contract Builder tab for compliance rule pick
			complainceDataSearch(priceSheetGroup, state);
			takeScreenshot();
			HashMap<String, String> ruleMap = new HashMap<String, String>();
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);

			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			// get rule info view value in hashmap
			String primaryRoleId = contractList.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			/// Fetch Data form RuleInfoView On New Cancellation Page
			HashMap<String, String> ruleInfoViewValue = getRuleInfoRule();
			System.out.print(ruleInfoViewValue);
			takeScreenshot();
			System.out.print(ruleMap);
			System.out.print(ruleInfoViewValue);
			if (ruleMap.containsKey("STDNCB")) {
				ruleMap.remove("STDNCB");
			}
			if (ruleMap.containsKey("STDPRORATE")) {
				ruleMap.remove("STDPRORATE");
			}
			if (ruleMap.containsValue(ruleInfoViewValue)) {
				matchFlag = true;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			} else {
				matchFlag = false;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			}
			assertEquals(dbFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}

	@SuppressWarnings({ "unlikely-arg-type", "unused" })
	@Test(priority = 5, groups = "regression", description = "Validate that the compliance rule applied by seller state for a price sheet if form type property is selected as seller state by price sheet list.")
	public void VerifyComplianceRulesAreChangedCancellation() throws Exception {
		boolean matchFlag = false;
		boolean dbFlag = false;
		HashMap<String, String> contractList = dBGetSellerStateDealerDetail();
		if (contractList.size() > 1) {
			String contractNunmber = contractList.get("CERT");
			String PrimaryName = contractList.get("NAME");
			String priceSheetGroup = compliance_PricesheetIdbasedOnCert(contractNunmber);
			String productName = contractList.get("productName");
			String state = compliance_StateDisplayNamebasedOnCertAndPAccount(contractNunmber, PrimaryName);
			String cState = compliance_CustomerStateDisplayNamebasedOnCertAndPAccount(contractNunmber);
			// Move to Contract Builder tab for compliance rule pick
			complainceDataSearch(priceSheetGroup, state);
			takeScreenshot();
			HashMap<String, String> ruleMap = new HashMap<String, String>();
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			ruleMap.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMap.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMap);
			complainceDataSearch(priceSheetGroup, cState);
			takeScreenshot();
			HashMap<String, String> ruleMapCustomer = new HashMap<String, String>();
			ruleMapCustomer.putAll(complainceRuleTreeProcessForAllRule("cancelFeeInTree", "cancelFeeTreeItems"));
			ruleMapCustomer.putAll(
					complainceRuleTreeProcessForAllRule("nonComplianceRuleInTree", "nonComplianceRuleInTreeItems"));
			ruleMapCustomer
					.putAll(complainceRuleTreeProcessForAllRule("refundPercentInTree", "refundPercentInTreeItems"));
			System.out.println(ruleMapCustomer);
			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter validvalues on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			takeScreenshot();
			String cert = uiSearchData.put("CERT", contractList.get("CERT"));
			// get rule info view value in hashmap
			String primaryRoleId = contractList.get("roleIdentifier");
			String pCode = contractList.get("PROGRAM_CODE");
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			/// Fetch Data form RuleInfoView On New Cancellation Page
			HashMap<String, String> ruleInfoViewValue = getRuleInfoRule();
			System.out.print(ruleInfoViewValue);
			takeScreenshot();
			System.out.print(ruleMap);
			System.out.print(ruleInfoViewValue);
			if (ruleMap.containsKey("STDNCB")) {
				ruleMap.remove("STDNCB");
			}
			if (ruleMap.containsKey("STDPRORATE")) {
				ruleMap.remove("STDPRORATE");
			}
			if (ruleMap.containsValue(ruleInfoViewValue)) {
				matchFlag = true;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);
			} else {
				matchFlag = false;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValue);
				logger.info("ruleMap====" + ruleMap);

			}
			assertEquals(matchFlag, true);

			String priceSheetCode = productName;
			goToPricingTab();
			waitForSomeTime(10);
			visitPriceSheetListTab();
			String formTypeUI = uiFormTypeValue(priceSheetCode);
			HashMap<String, String> dbFormTypeValue = fetchFormTypeValueDB(priceSheetCode);
			System.out.print(dbFormTypeValue.get("VALUE"));
			String dbvalue = dbFormTypeValue.get("VALUE");
			String formTypeUI1 = uiChangeFormTypeValue(priceSheetCode, dbvalue);
			HashMap<String, String> dbFormTypeValue1 = fetchFormTypeValueDB(priceSheetCode);
			assertEquals(dbFormTypeValue1, formTypeUI1);

			// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			// create search data in hash map
			searchContractGivenInputParamaters(uiSearchData);
			// navigate to new canceltab
			clickCancelButtonAndNavigateToNewCancellationTab();
			// enter validvalues on new cancellation tab screen and click calculate
			waitForSomeTime(10);
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
			// click ok for cancellation completed successfully //
			click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			waitForSomeTime(5);
			takeScreenshot();
			try {
				click("listtoggleStateRuleInfoView");
			} catch (Exception e) {
				click("ruleInfoRefersh");
			}
			// Fetch Data form RuleInfoView On New Cancellation Page
			HashMap<String, String> ruleInfoViewValueCustomer = getRuleInfoRule();
			System.out.print(ruleInfoViewValueCustomer);
			takeScreenshot();
			System.out.print(ruleMapCustomer);
			System.out.print(ruleInfoViewValueCustomer);
			if (ruleInfoViewValueCustomer.containsKey("STDNCB")) {
				ruleInfoViewValueCustomer.remove("STDNCB");
			}
			if (ruleInfoViewValueCustomer.containsKey("STDPRORATE")) {
				ruleMap.remove("STDPRORATE");
			}
			if (ruleInfoViewValueCustomer.containsValue(ruleInfoViewValueCustomer)) {
				matchFlag = true;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValueCustomer);
				logger.info("ruleMap====" + ruleMapCustomer);
			} else {
				matchFlag = false;
				logger.info("ruleInfoViewValue====" + ruleInfoViewValueCustomer);
				logger.info("ruleMap====" + ruleInfoViewValueCustomer);
			}
			assertEquals(matchFlag, true);
			assertEquals(dbFlag, true);
		} else {
			new SkipException("no value exist in db for Given PriceSheet");
			assertEquals(dbFlag, true);
		}
	}
}
