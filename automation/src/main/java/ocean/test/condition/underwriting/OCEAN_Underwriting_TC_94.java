package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * This class OCEAN_Underwriting_TC86 automates PBI-18489 for Dealer Statement:
 * Mileage Expender
 * 
 * @author Poonam.Kalra
 *
 */
public class OCEAN_Underwriting_TC_94 extends UnderwritingModulePages {
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_94", dataProviderClass = UnderwritingDataProvider.class, description = "Validate following fields on Mileage change row under account statement screen :"
					+ "   1) Premium 2) DB/CR 3) Dealer Paid 4) Balance 5) Contract # ")
	public void ValidateMileageChangeRowUnderAccountStatement(String[] inputArray) throws Exception {
		boolean matchflag = true;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf((c.get(Calendar.MONTH) + 1));
		int postPeriod = Integer.parseInt(year + month);
		String uIPostperiod = year + month;
		String AdjType = "mileage change";
		int adjID = 125;
		String roleType = inputArray[0];
		HashMap<String, String> mileageChangeValue = dbSearchAdjTypeDetChange(postPeriod, AdjType, roleType);
		if (mileageChangeValue.size() >= 1) {
			String DealerId = mileageChangeValue.get("Dealer");
			int roleId = Integer.parseInt(mileageChangeValue.get("Dealer"));
			boolean accountStateGrid = accountStatementSearch(uIPostperiod, roleType, DealerId);
			assertEquals(accountStateGrid, true);
			HashMap<String, String> uIValue = mileageChangeRowData(AdjType);
			HashMap<String, String> dbValue = dBmileageChangeData(postPeriod, adjID, roleId, roleType);
			assertEquals(uIValue, dbValue);

		} else {
			HashMap<String, String> roleIDAndContractMileageChange = dBSearchActiveRoleIDAndActiveContract(roleType);
			String cert = roleIDAndContractMileageChange.get("CERT");
			mileageChangeAdjustment(cert);

			premium();
			try {
				click("clickOK");
			} catch (Exception e) {
			}
			waitForSomeTime(10);
			click("clickAdjust");
			waitForSomeTime(5);
			if (getAttributeValue("AdjustCategoryMilage", "Toggle.ToggleState").equals("0")) {
				click("AdjustCategoryMilage");
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
			String DealerId = roleIDAndContractMileageChange.get("DealerID");
			;
			int roleId = Integer.parseInt(roleIDAndContractMileageChange.get("DealerID"));
			boolean accountStateGrid = accountStatementSearch(uIPostperiod, roleType, DealerId);
			assertEquals(accountStateGrid, true);
			HashMap<String, String> uIValue = mileageChangeRowData(AdjType);
			HashMap<String, String> dbValue = dBmileageChangeData(postPeriod, adjID, roleId, roleType);
			assertEquals(uIValue, dbValue);
		}

	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_85", dataProviderClass = UnderwritingDataProvider.class, description = "Validate following fields on Mileage change row under account statement screen :"
					+ "   1) Premium 2) DB/CR 3) Dealer Paid 4) Balance 5) Contract # ")
	public void ValidateChangeMileageInContractAdjustment(String[] inputArray) throws Exception {
		boolean matchflag = true;
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf((c.get(Calendar.MONTH) + 1));
		int postPeriod = Integer.parseInt(year + month);
		String uIPostperiod = year + month;
		String AdjType = "mileage change";
		int adjID = 125;
		String roleType = "Dealer";
		HashMap<String, String> mileageChangeValue = dBSearchRoleIdWithNoAdjCount(postPeriod, adjID, roleType);
		if (mileageChangeValue.size() >= 1) {
			mileageChangeAdjustment(mileageChangeValue.get("Contract"));
			premium();
			try {
				click("clickOK");
			} catch (Exception e) {
			}
// This function is used to lock and view contract post contract search
			waitForSomeTime(10);
			click("clickAdjust");
			waitForSomeTime(5);
			if (getAttributeValue("AdjustCategoryMilage", "Toggle.ToggleState").equals("0")) {
				click("AdjustCategoryMilage");
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
		String DealerId = mileageChangeValue.get("Primary_Account_ID");
		int roleId = Integer.parseInt(mileageChangeValue.get("Primary_Account_ID"));
		boolean accountStateGrid = accountStatementSearch(uIPostperiod, roleType, DealerId);
		assertEquals(accountStateGrid, true);
		HashMap<String, String> uIValue = mileageChangeRowData(AdjType);
		HashMap<String, String> dbValue = dBmileageChangeData(postPeriod, adjID, roleId, roleType);
		assertEquals(uIValue, dbValue);
	}
}
