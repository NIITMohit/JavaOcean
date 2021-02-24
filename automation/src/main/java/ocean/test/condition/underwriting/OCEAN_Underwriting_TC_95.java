package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * This class OCEAN_Underwriting_TC86 automates PBI-18488 for Dealer Statement:
 * Plan Expender
 * 
 * @author Poonam.Kalra
 *
 */
public class OCEAN_Underwriting_TC_95 extends UnderwritingModulePages {
	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_94", dataProviderClass = UnderwritingDataProvider.class, description = "Validate following fields on plan change row under account statement screen :"
					+ "   1) Premium 2) DB/CR 3) Dealer Paid 4) Balance 5) Contract # ")
	public void ValidatePlanChangeRowUnderAccountStatement(String[] inputArray) throws Exception {
		boolean matchflag = true;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf((c.get(Calendar.MONTH) + 1));
		int postPeriod = Integer.parseInt(year + month);
		String uIPostperiod = year + month;
		String AdjType = "plan change";
		int adjID = 124;
		String roleType = inputArray[0];
		HashMap<String, String> planChangeValue = dbSearchAdjTypeDetChange(postPeriod, AdjType, roleType);
		if (planChangeValue.size() >= 1) {
			String DealerId = planChangeValue.get("Dealer");
			int roleId = Integer.parseInt(planChangeValue.get("Dealer"));
			boolean accountStateGrid = accountStatementSearch(uIPostperiod, roleType, DealerId);
			assertEquals(accountStateGrid, true);
			HashMap<String, String> uIValue = planChangeRowData(AdjType);
			HashMap<String, String> dbValue = dBmileageChangeData(postPeriod, adjID, roleId, roleType);
			assertEquals(uIValue, dbValue);

		} else {
			System.out.println("No Plan Change for this PostPeriod");
			assertEquals(matchflag, true);
		}
	}

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_86", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Ability To Change Primary Account On Contract Adjustment")

	public void ValidateAbilityToChangePrimaryAccountContractAdjustment(String[] inputArray) throws Exception {
		boolean matchflag = true;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf((c.get(Calendar.MONTH) + 1));
		int postPeriod = Integer.parseInt(year + month);
		String uIPostperiod = year + month;
		String AdjType = "plan change";
		int adjID = 124;
		String roleType = inputArray[0];
		HashMap<String, String> pChangeValue = dBSearchRoleIdWithNoAdjCount(postPeriod, adjID, roleType);
		String roleIdentifier = null;
		if (pChangeValue.size() >= 1) {
			if (roleType.equalsIgnoreCase("Lender")) {
				processForAccountSearchForContractAdjustment(pChangeValue.get("Contract"));

				String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
				HashMap<String, String> premiumData = prepareData(impData);
				HashMap<String, String> sss = setAllDataForPremiumCalculationLender(premiumData);
				premiumData.put("PRICESHEETID", sss.get("PRICESHEETID"));
				premiumData.put("DEALERID", sss.get("SecondaryAccountId"));
				premiumData.put("parentpricesheetcode", sss.get("parentpricesheetcode"));
				premiumData.put("PrimaryAccount", "Lender");
				premiumData.put("SecondaryAccount", "Dealer");
				premiumData.put("SecondaryAccountId", sss.get("DealerId"));
				roleIdentifier = sss.get("SecondaryAccountId");
				//// Dealer Id
				// dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
				if (sss.size() > 1) {
					//// enter all mandatory values only on new business form screen
					premiumData.putAll(changeDealerorLender(premiumData));
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
					premium();
					click("clickAdjust");
					waitForSomeTime(5);
					if (getAttributeValue("AdjustCategoryPlan", "Toggle.ToggleState").equals("0")) {
						click("AdjustCategoryPlan");
					}
					click("clickNextBtnOnContractAdjustment");
					click("FinishBtnOnContractAdjustment");
					click("clickOK");
					waitForSomeTime(5);
					try {
						click("contractExpander");
					} catch (Exception e) {
						// Do Nothing
					}
				}
			}

			else {

				processForAccountSearchForContractAdjustment(pChangeValue.get("Contract"));
				String[] impData = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
				HashMap<String, String> premiumData = prepareData(impData);
				HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
				premiumData.putAll(sss);
				premiumData.put("PrimaryAccount", "Dealer");
				premiumData.put("SecondaryAccount", "Lender");
				premiumData.put("SecondaryAccountId", "24");

				if (sss.size() > 1) {
					roleIdentifier = premiumData.get("DEALERID");
					//// enter all mandatory values only on new business form screen
					premiumData.putAll(changeDealerorLender(premiumData));
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
					premium();
					click("clickAdjust");
					waitForSomeTime(5);
					if (getAttributeValue("AdjustCategoryPlan", "Toggle.ToggleState").equals("0")) {
						click("AdjustCategoryPlan");
					}
					click("clickNextBtnOnContractAdjustment");
					click("FinishBtnOnContractAdjustment");
					click("clickOK");
					waitForSomeTime(5);
					try {
						click("contractExpander");
					} catch (Exception e) {
						// Do Nothing
					}
				}
			}
			String DealerId = roleIdentifier;
			int roleId = Integer.parseInt(roleIdentifier);
			boolean accountStateGrid = accountStatementSearch(uIPostperiod, roleType, DealerId);
			assertEquals(accountStateGrid, true);
			HashMap<String, String> uIValue = planChangeRowData(AdjType);
			HashMap<String, String> dbValue = dBmileageChangeData(postPeriod, adjID, roleId, roleType);
			assertEquals(uIValue, dbValue);
		} else {
			System.out.println("No Plan Change for this PostPeriod");
			assertEquals(matchflag, true);
		}
	}

}
