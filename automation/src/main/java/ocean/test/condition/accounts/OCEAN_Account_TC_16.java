package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Accounts_TC_16 class automates Ocean Accounts module Test Condition 16
 * which holds 4 Test Case; Test Condition Description : Validate that exception
 * are applied on a contract/ contract adjustment only from their effective
 * date.
 * 
 * @author Shalu Chauhan
 * 
 * @reviewer : Poonam Kalra
 */

public class OCEAN_Account_TC_16 extends UnderwritingModulePages {
	/**
	 * This function automates the test case OCEAN_Accounts_T55: Test Case
	 * description : Validate If for a contract if exception exists, then exception
	 * are applied on premium calculation if sale date >= Exception effective date
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_16", dataProviderClass = AccountsDataProvider.class, description = "Validate If for a contract if exception exists, then exception	 are applied on premium calculation if sale date > Exception effective date.")
	public void TestCase55_SaleDateGreaterThanForContract(String[] inputData) throws Exception {
		prepareRemittance();
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		premiumData.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(premiumData);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
		dataForValidation.put("SecondaryAccount", "Lender");
		dataForValidation.put("SecondaryAccountId", "24");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 1));
		//// Search a contract with pending state, remittance name and contract name is
		premiumData.putAll(dataForValidation);
		//// fetched from database
		if (dataForValidation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
				premiumData.put("SURCHARGESAMOUNT", surcharges());
			if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
				premiumData.put("OPTIONSAMOUNT", options());
			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
				premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
			//// Get AUL Premium
			String premium = premium();
			String finalValue = calculateMyPremium(premiumData);
			assertEquals(premium, finalValue);
		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC_16", dataProviderClass = AccountsDataProvider.class, description = "Validate If for a contract if exception exists, then exception are applied on premium calculation if sale date = Exception effective date.")
	public void TestCase55_SaleDateEqualForContract(String[] inputData) throws Exception {
		prepareRemittance();
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		premiumData.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(premiumData);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
		dataForValidation.put("SecondaryAccount", "Lender");
		dataForValidation.put("SecondaryAccountId", "24");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 0));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		premiumData.putAll(dataForValidation);
		if (dataForValidation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
				premiumData.put("SURCHARGESAMOUNT", surcharges());
			if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
				premiumData.put("OPTIONSAMOUNT", options());
			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
				premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
			//// Get AUL Premium
			String premium = premium();
			String finalValue = calculateMyPremium(premiumData);
			assertEquals(premium, finalValue);
		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC_16", dataProviderClass = AccountsDataProvider.class, description = "Validate If for a contract if exception exists, then exception are not applied on premium calculation if sale date < Exception effective	date.")
	public void TestCase56_SaleDateLessThanForContract(String[] inputData) throws Exception {
		prepareRemittance();
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		premiumData.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForSaleDateLessThanExceptionDate(premiumData);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
		dataForValidation.put("SecondaryAccount", "Lender");
		dataForValidation.put("SecondaryAccountId", "24");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), -1));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		premiumData.putAll(dataForValidation);
		if (dataForValidation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
				premiumData.put("SURCHARGESAMOUNT", surcharges());
			if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
				premiumData.put("OPTIONSAMOUNT", options());
			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
				premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
			//// Get AUL Premium
			String premium = premium();
			String finalValue = calculateMyPremium(premiumData);
			assertEquals(premium, finalValue);
		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	/**
	 * This function automates the test case OCEAN_Accounts_T57: Test Case
	 * description : Validate If for a contract adjustment if exception exists, then
	 * exception are applied on premium calculation if sale date > Exception
	 * effective date
	 */
	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC_16", dataProviderClass = AccountsDataProvider.class, description = "Validate If for a contract adjustment if exception exists, then exception are applied on premium calculation if sale date >Exception effective date.")
	public void TestCase57_SaleDateGreaterThanForContractAdjustment(String[] inputData) throws Exception {
		//// navigate underwriting tab
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		premiumData.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(premiumData);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
		dataForValidation.put("SecondaryAccount", "Lender");
		dataForValidation.put("SecondaryAccountId", "24");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 1));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		premiumData.putAll(dataForValidation);
		if (dataForValidation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
				premiumData.put("SURCHARGESAMOUNT", surcharges());
			if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
				premiumData.put("OPTIONSAMOUNT", options());
			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
				premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
			//// Get AUL Premium
			String premium = premium();
			String finalValue = calculateMyPremium(premiumData);
			assertEquals(premium, finalValue);
		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	/**
	 * This function automates the test case OCEAN_Accounts_T57: Test Case
	 * description : Validate If for a contract adjustment if exception exists, then
	 * exception are applied on premium calculation if sale date=Exception effective
	 * date
	 */
	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC_16", dataProviderClass = AccountsDataProvider.class, description = "Validate If for a contract adjustment if exception exists, then exception are applied on premium calculation if sale date = Exception effective date")
	public void TestCase57_SaleDateEqualForContractAdjustment(String[] inputData) throws Exception {
		//// navigate underwriting tab
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		premiumData.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(premiumData);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
		dataForValidation.put("SecondaryAccount", "Lender");
		dataForValidation.put("SecondaryAccountId", "24");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 0));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		premiumData.putAll(dataForValidation);
		if (dataForValidation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
				premiumData.put("SURCHARGESAMOUNT", surcharges());
			if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
				premiumData.put("OPTIONSAMOUNT", options());
			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
				premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
			//// Get AUL Premium
			String premium = premium();
			String finalValue = calculateMyPremium(premiumData);
			assertEquals(premium, finalValue);
		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	/**
	 * This function automates the test case OCEAN_Accounts_T57: Test Case
	 * description : Validate If for a contract adjustment if exception exists, then
	 * exception are not applied on premium calculation if sale date < Exception
	 * effective date
	 */
	@Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC_16", dataProviderClass = AccountsDataProvider.class, description = "Validate If for a contract adjustment if exception exists, then exception are notapplied on 	premium calculation if 	sale date<	Exception effective date")
	public void TestCase58_SaleDateLessThanForContractAdjustment(String[] inputData) throws Exception {
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		premiumData.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForSaleDateLessThanExceptionDate(premiumData);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
		dataForValidation.put("SecondaryAccount", "Lender");
		dataForValidation.put("SecondaryAccountId", "24");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), -1));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		premiumData.putAll(dataForValidation);
		if (dataForValidation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
				premiumData.put("SURCHARGESAMOUNT", surcharges());
			if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
				premiumData.put("OPTIONSAMOUNT", options());
			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
				premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
			//// Get AUL Premium
			String premium = premium();
			String finalValue = calculateMyPremium(premiumData);
			assertEquals(premium, finalValue);
		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	/*
	 * This function is executed before class, this will land till contract fill up
	 * form,
	 * 
	 */
	public void prepareRemittance() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		//// get remittance name and file name
		/// iterate to multiple contracts with same price sheet
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
		} else {
			new SkipException("no remittance found");
		}
	}

	@AfterMethod(alwaysRun = true)
	public void cleanup() {
		try {
			clearPreFilledData();
			click("contractExpander");
			refreshRemittance();
		} catch (Exception e) {
			//// Do Nothing
		}
	}
}
