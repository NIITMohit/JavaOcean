package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC_14 class automates Ocean Accounts module Test Condition 14
 * which holds 4 Test Case; Test Condition Description : Validate for a
 * lender/dealer account that assigned price sheet is available for contract
 * with sale date equal to or after their effective date only.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Accounts_TC_14 extends AccountsModulePages {

	@Test(priority = 3, groups = "regression", description = "Validate Dealer Level Price Sheet is visible while creating contract if Sale Date >= Dealer Level price sheet association")
	public void TestCase45_SaleDateGreaterThanDealerPriceSheetDate() throws Exception {
		HashMap<String, String> dataforquery = new HashMap<String, String>();
		dataforquery.put("AGENTEXCEPTION", "N");
		dataforquery.put("DEALEREXCEPTION", "N");
		dataforquery.put("PRICESHEETCODE", "");
		dataforquery.put("DEALERID", "");
		dataforquery.put("ROLETYPE", "1");
		//// Get Required Data from DB
		HashMap<String, String> dataForValidation = setAllDataForPriceSheetVisibility(dataforquery);
		dataForValidation.put("primaryAccountId", dataForValidation.get("DEALERID"));
		dataForValidation.put("primaryAccountType", "Dealer");
		///// modify sale date
		dataForValidation.put("SaleDate", convertDate(dataForValidation.get("PRICESHEETMAINEFFECTIVEDATE"), 10));
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			String priceSheetContains = enterMandatoryValuesOnContractAndCheckForPriceSheet(dataForValidation);
			assertEquals(priceSheetContains, "Hurray Data Exists");
		} else
			throw new Exception("No remittance exists");

	}
}
