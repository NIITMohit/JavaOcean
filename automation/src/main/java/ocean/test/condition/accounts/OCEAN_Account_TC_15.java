package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC_15 class automates Ocean Accounts module Test Condition 15
 * which holds 6 Test Case; Test Condition Description : Validate the
 * availability of a price sheet for related account on a contract, only if it's
 * sales date is on same day/after effective date from account pricing..
 * 
 * @author Mohit Goel
 */
public class OCEAN_Account_TC_15 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 15 ; Test Case
	 * description : Validate only Lender Level Price Sheet is visible while
	 * creating contract if Sale Date >= lender Level price sheet association.
	 * 
	 */
	@Test(priority = 3, groups = "regression", description = "Validate Lender Level Price Sheet is visible while creating contract if Sale Date > Dealer Level price sheet association")
	public void TestCase49_SaleDateGreaterThanLenderPriceSheetDate() throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "4");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Lender");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 1));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			//// go to Remittance list tab
			goToRemittanceList();
			//// enter remittance details to find contract on UW window
			searchContractwithPendingState(remittName, fileName);
			//// To lock and view contract post contract search
			lockAndViewContract();
			//// Validate Price sheet visibility by entering all mandatory
			// values on new business form screen
			String priceSheetContains = enterMandatoryValuesOnContractAndCheckForPriceSheet(dataForValidation);
			assertEquals(priceSheetContains, "Hurray Data Exists");
		} else
			throw new Exception("No remittance exists");

	}

	@Test(priority = 4, groups = "regression", description = "Validate Lender Level Price Sheet is not visible while creating contract if Sale Date < Lender Level price sheet association")
	public void TestCase50_SaleDateLessThanLenderPriceSheetDate() throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "4");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Lender");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), -1));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			//// go to Remittance list tab
			goToRemittanceList();
			//// enter remittance details to find contract on UW window
			searchContractwithPendingState(remittName, fileName);
			//// To lock and view contract post contract search
			lockAndViewContract();
			//// Validate Price sheet visibility by entering all mandatory
			// values on new business form screen
			String priceSheetContains = enterMandatoryValuesOnContractAndCheckForPriceSheet(dataForValidation);
			assertEquals(priceSheetContains, "No Data Exists");
		} else
			throw new Exception("No remittance exists");

	}

	@Test(priority = 5, groups = "regression", description = "Validate Lender Level Price Sheet is visible while creating contract if Sale Date = Lender Level price sheet association")
	public void TestCase49_SaleDateIsEqualLenderPriceSheetDate() throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "4");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Lender");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 0));
		//// Search a contract with pending state, remittance name and contract name is
		//// fetched from database
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			//// go to Remittance list tab
			goToRemittanceList();
			//// enter remittance details to find contract on UW window
			searchContractwithPendingState(remittName, fileName);
			//// To lock and view contract post contract search
			lockAndViewContract();
			//// Validate Price sheet visibility by entering all mandatory
			// values on new business form screen
			String priceSheetContains = enterMandatoryValuesOnContractAndCheckForPriceSheet(dataForValidation);
			assertEquals(priceSheetContains, "Hurray Data Exists");
		} else
			throw new Exception("No remittance exists");

	}
}
