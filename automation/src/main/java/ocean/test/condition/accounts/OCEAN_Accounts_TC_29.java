package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Accounts_TC29 class automates Ocean Accounts module Test Condition 29,
 * which holds 3 Test Case; Test Condition Description : Validate that Limited
 * warranty (LW) coverage is not available for a vehicle, if any of the
 * following parameter is not met (as defined by user ): 1. Term Month 2. Term
 * Miles 3. Age from 4. Age to 5. Mileage From 6. Mileage to.
 * 
 * @author Surbhi Singhal
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Accounts_TC_29 extends AccountsModulePages {

	/**
	 * This function automates all test cases for test condition 29; Test Case
	 * description : Validate for a LW price sheet that vehicle will become
	 * eligible/ineligible in contract creation, on the basis of the selected
	 * term/age/mileage is not defined under LW price sheet.
	 * 
	 * @throws Exception
	 */

	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_29", dataProviderClass = AccountsDataProvider.class, description = "Validate for a LW price sheet that vehicle will become eligible/ineligible in contract creation, on the basis of the selected term/age/mileage is not defined under LW price sheet.")
	public void validateVehicleEligibilityForLW(String[] inputData) throws Exception {
		String checkValue = inputData[20];
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with Pending state, remittance name and contract name is
			//// fetched from database
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			searchContractwithPendingState(remittName, fileName);

			lockAndViewContract();

			/// Prepare Data
			HashMap<String, String> premiumData = prepareData(inputData);
			HashMap<String, String> sss = setAllDataForPremiumCalculationLimitedPriceSheet(premiumData, "");
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			// fetching PriceSheet Internal Name detail from database for Limited Price
			// Sheet
			HashMap<String, String> myDBData = getPricesheetDetails(premiumData.get("internalName"));
			premiumData.put("PRICESHEETID", myDBData.get("PRICESHEET_ID"));
			setDataAsTestData(checkValue, premiumData);
			if (sss.size() > 1) {
				if (checkValue.equals("BeforeMileage") || checkValue.equals("Age")) {
					//// enter all mandatory values only on new business form screen
					premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
					try {
						click("scrollContractsListDown");
					} catch (Exception e) {
						/// do nothing
					}
					logger.info("premiumData : " + premiumData);
					if (checkValue.equals("BeforeMileage")) {
						String finalValue = premiumData.get("Premium");
						logger.info("finalValue : " + finalValue);
						//// Get AUL Premium
						String premium = premium();
						premium = premium.replaceAll(",", "");
						logger.info("premium : " + premium);
						takeScreenshot();
						if (premium.equals(finalValue))
							logger.info(
									"Vehicle is eligible in contract creation, if Vehicle's Mileage is before Mileage from not defined under LW price sheet");
						assertEquals(premium, finalValue);
					} else {
						click("clickPremiumCalculate");
						boolean flag = checkIsDisplayed("NoBreakdownFoundMessageBox");
						if (flag) {
							takeScreenshot();
							click("NoBreakdownFoundMessageBox");
							logger.info(
									"Vehicle is ineligible in contract creation, if Vehicle's age is after Age to not defined under LW price sheet");
						}
						assertEquals(flag, true);
					}
				} else if (checkValue.equals("AfterMileage")) {
					boolean coverageInfoIsBlank = enterMandatoryValuesOnContractWhenMileIsGreater(premiumData);
					takeScreenshot();
					if (coverageInfoIsBlank)
						logger.info(
								"Vehicle is ineligible in contract creation, if Vehicle's Mileage is after Mileage to not defined under LW price sheet");
					assertEquals(coverageInfoIsBlank, true);
				}

			}
		} else
			throw new SkipException("no such data found");
	}

	@AfterClass(alwaysRun = true)
	public void collapse() throws Exception {
		try {
			//// scroll up and clear data
			clearPreFilledData();
			contractScrollToTop();
			click("typeContractNumber");
			click("contractExpander");
			refreshRemittance();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
