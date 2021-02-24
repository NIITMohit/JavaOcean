package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Accounts_TC_30 class automates Ocean Accounts Test_Condition_Ref _30
 * which holds 4 Test Case; Test Condition Description : Validate that Limited
 * warranty (LW) coverage is available for a vehicle, if all of the following
 * parameter's(Term,Age,Mileage) are met (as defined by user ):
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Accounts_TC_30 extends UnderwritingModulePages {
	/*
	 * Validate for a LW price sheet that only defined term month and miles is
	 * available for user selection while creating a contract
	 * 
	 */
	@Test(priority = 5, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_30", dataProviderClass = AccountsDataProvider.class, description = "Limited warranty (LW) pricesheet is available for a vehicle")
	public void ValidateLWCoverageForVehicle(String[] inputData) throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			HashMap<String, String> lWRoleId = getRoleIdWithLWProgCode(inputData[0]);
			String roleId = lWRoleId.get("ROLE_IDENTIFIER");
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with Pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			///// Prepare Data
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
			// for age calcution
			String ageFrom = myDBData.get("AGE_TO");
			String calcultedYEAR = getVehicleAge(ageFrom);
			premiumData.put("YEAR", calcultedYEAR);
			if (sss.size() > 1) {
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				System.out.println(premiumData);
				click("clickPremiumCalculate");
				click("clickOK");
				// premium();
				/// Validate for a LW price sheet that only defined term month ,miles and
				/// coverage is available for user selection while creating a contract.
				assertEquals(myDBData.get("Term"), premiumData.get("TERM"));
				assertEquals(myDBData.get("Coverage"), premiumData.get("COVERAGE"));
				assertEquals(myDBData.get("MILEAGE_TO"), premiumData.get("MILEAGE_TO"));

			}
		} else
			throw new SkipException("no such data found");
	}

	@AfterMethod(alwaysRun = true)
	public void cleanup() {
		try {
			contractExpander();
			goToRemittanceList();
			refreshRemittance();
			waitForSomeTime(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
