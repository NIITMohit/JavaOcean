package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC31_32 class automates Ocean Accounts module Test Condition
 * 31,32, which holds 3 Test Case; Test Condition Description : Validate that
 * Limited warranty (LW) premium is correctly calculated for a dealer on a
 * contract, when related price sheet is : 1. Added 2. Modified.
 * 
 * @author Surbhi Singhal
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Accounts_TC_31_32 extends AccountsModulePages {

	/**
	 * This function automates all test cases for test condition 31,32; Test Case
	 * description : Validate that, Ocean displayed correct LW premium calculation
	 * for a price sheet when it is added/edited on a contract.
	 * 
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_31_32", dataProviderClass = AccountsDataProvider.class, description = "Validate that, Ocean displayed correct LW premium calculation for a price sheet when it is added/edited on a contract.")
	public void validateCorrectErrorForLWSetup(String[] inputData) throws Exception {
		String actionPerform = inputData[20];
		String lWPlanCode = inputData[0];
		String roleId = "";
		String deductibleValue = "100";
		if (actionPerform.equals("Add"))
			roleId = prepareLWSetup(lWPlanCode, deductibleValue);
		else
			roleId = editLWSetup(lWPlanCode);
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			goToUnderWritingTab();
			if (actionPerform.equals("Add")) {
				goToRemittanceList();
				//// Search a contract with Pending state, remittance name and contract name is
				//// fetched from database
				String remittName = contractFromRemittance.get(1).get("RemittanceName");
				String fileName = contractFromRemittance.get(1).get("FILE_NAME");
				searchContractwithPendingState(remittName, fileName);
				lockAndViewContract();
			} else {
				contractExpander();
			}

			/// Prepare Data
			HashMap<String, String> premiumData = prepareData(inputData);
			HashMap<String, String> sss = setAllDataForPremiumCalculationLimitedPriceSheet(premiumData, roleId);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			// fetching PriceSheet Internal Name detail from database for Limited Price
			// Sheet
			HashMap<String, String> myDBData = getPricesheetDetails(premiumData.get("internalName"));
			premiumData.put("PRICESHEETID", myDBData.get("PRICESHEET_ID"));

			if (sss.size() > 1) {
				if (actionPerform.equals("Add")) {
					//// enter all mandatory values only on new business form screen
					premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
					try {
						click("scrollContractsListDown");
					} catch (Exception e) {
						/// do nothing
					}
				}
				logger.info("premiumData : " + premiumData);
				String finalValue = premiumData.get("Premium");
				logger.info("finalValue : " + finalValue);
				//// Get AUL Premium
				String premium = premium();
				premium = premium.replaceAll(",", "");
				logger.info("premium : " + premium);
				assertEquals(premium, finalValue);
				if (premium.equals(finalValue))
					logger.info("Ocean is displaying correct LW premium calculation for a price sheet when LWSetup "
							+ actionPerform + " is performed");
				if (actionPerform.equals("Edit"))
					collapseRemList();
			}
		} else
			throw new SkipException("no such data found");
	}
}
