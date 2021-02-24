package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map.Entry;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Account_TC_23_24_25 class automates Ocean Account module Test Condition
 * 23_24_25 which holds 7 Test Cases; Test Condition Description :
 * OCEAN_Account_TC_23:Validate for payee level price that price exception are
 * correctly applied on a contract, if they are for all plans/ selected plans.
 * OCEAN_Account_TC_24:Validate for lender level price that price exception are
 * correctly applied on a contract, if they are for all plans/ selected plans.
 * OCEAN_Account_TC_25:Validate that OCEAN display correct exception information
 * for a price sheet , if exceptions are added/updated/deleted
 * 
 * @author Shalu Chauhan
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Account_TC_23_24_33_34_35_36_37 extends UnderwritingModulePages {
	/**
	 * Validate for payee level price that price exception are correctly applied on
	 * a contract, if they are for all plans/ selected plans. Validate that OCEAN
	 * display correct exception information for a price sheet , if exceptions are
	 * added/updated/deleted.
	 */
	@Test(priority = 1, groups = { "regression", "smkgggoske",
			"fullSuite" }, dataProvider = "fetchDataForTC_05_06", dataProviderClass = PricingDataProvider.class, description = "Validate for payee/lender level price that price exception are correctly applied on a contract,if they are for all plans/ selected plans")

	public void checkPriceSheetWithExceptionOrWithoutExceptionForDealerOnContract(String[] inputData) throws Exception {
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
		HashMap<String, String> premiumCalculation = setAllDataForPremiumCalculation(premiumData);
		premiumData.putAll(premiumCalculation);
		premiumData.put("PrimaryAccount", "Dealer");
		premiumData.put("SecondaryAccount", "Lender");
		premiumData.put("SecondaryAccountId", "24");
		if (premiumCalculation.size() > 1) {
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
	 * Validate for lender level price that price exception are correctly applied on
	 * a contract, if they are for all plans/ selected plans.
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smskkkkoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_23_24_25", dataProviderClass = AccountsDataProvider.class, description = "Validate for payee/lender level price that price exception are correctly applied on a contract,if they are for all plans/ selected plans")
	public void checkPriceSheetWithExceptionOrWithoutExceptionForLenderOnContract(String[] inputData) throws Exception {
		///// Prepare Data
		HashMap<String, String> premiumData = prepareDataForLenderAsPrimaryAccount(inputData);
		//// run query to get final data
		HashMap<String, String> premiumCalculation = setAllDataForPremiumCalculationForLender(premiumData);
		premiumData.putAll(premiumCalculation);
		premiumData.put("PrimaryAccount", "Lender");
		premiumData.put("SecondaryAccount", "Dealer");
		HashMap<String, String> dealerID = getDealerForLender(premiumCalculation);
		for (Entry<String, String> entry1 : dealerID.entrySet()) {
			String DEALERID = entry1.getValue();
			premiumData.put("SecondaryAccountId", DEALERID);
			if (premiumCalculation.size() > 1) {
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(enterMandatoryValuesOnContractForLender(premiumData));
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
	}

	/**
	 * This function is executed before class, this will land till contract fill up
	 * form for dealer and lender as primary account ID
	 * 
	 */
	@BeforeClass(alwaysRun = true)
	public void prepareRemittanceForDealerAndLender() throws Exception {
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

	@AfterClass(alwaysRun = true)
	public void collapse() throws Exception {
		try {
			contractScrollToTop();
			click("typeContractNumber");
			click("contractExpander");
			refreshRemittance();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This function clear pre filled data *
	 */
	@AfterMethod(alwaysRun = true)
	public void clearDataFromUnderwritingWindow() throws Exception {
		//// scroll up and clear data
		try {
			clearPreFilledData();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
