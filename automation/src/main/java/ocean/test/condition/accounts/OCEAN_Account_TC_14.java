package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC_14 class automates Ocean Accounts module Test Condition 14
 * which holds 4 Test Case; Test Condition Description : Validate for a
 * lender/dealer account that assigned price sheet is available for contract
 * with sale date equal to or after their effective date only.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Account_TC_14 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 14 ; Test Case
	 * description : Validate only Dealer Level Price Sheet is visible while
	 * creating contract if Sale Date >= Dealer Level price sheet association.
	 * 
	 */
	@Test(priority = 3, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, description = "Validate Dealer Level Price Sheet is visible while creating contract if Sale Date > Dealer Level price sheet association")
	public void TestCase45_SaleDateGreaterThanDealerPriceSheetDate() throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
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

	@Test(priority = 4, groups = "fullSuite", description = "Validate Dealer Level Price Sheet is not visible while creating contract if Sale Date < Dealer Level price sheet association")
	public void TestCase46_SaleDateLessThanDealerPriceSheetDate() throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
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

	@Test(priority = 5, groups = "fullSuite", description = "Validate Dealer Level Price Sheet is visible while creating contract if Sale Date = Dealer Level price sheet association")
	public void TestCase45_SaleDateIsEqualDealerPriceSheetDate() throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("DEALERID", dataForValidation.get("DEALERID"));
		dataForValidation.put("PrimaryAccount", "Dealer");
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
