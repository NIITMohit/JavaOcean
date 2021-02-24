package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * This class OCEAN_Underwriting_TC85 automates PBI-17919 for Dealer Statement:
 * On Hold/Short Pay it contains 9 test case
 * 
 * @author Poonam.Kalra
 *
 */

public class OCEAN_Underwriting_TC_96 extends UnderwritingModulePages {

	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_96", dataProviderClass = UnderwritingDataProvider.class, description = "Validate following column headers are visible under Onhold/ShortPay:- 1) Contract (Contract #) "
					+ "2) Date (date the contract went on hold) ,3) Paid (amount dealer paid for contract) ,4) Premium (Cost of the contract)\r\n"
					+ "5) DB/CR (debit or credit applied to contract, if any),6) Reason ( Standard comments)")
	public void ValidateColumnHeaderUnderOnHold(String[] inputArray) throws Exception {
		boolean matchflag = false;
		boolean dbflag = false;
		//// go to underwriting tab
		goToUnderWritingTab();
		goToAccountStatements();
		String roleID = inputArray[0];
		String roleType = inputArray[1];
		String postPeriod = inputArray[2];
		HashMap<String, String> dbValue = fetchOnHoldGivenRoleIDPostPeriodDB(roleID, roleType, postPeriod);
		if (dbValue.size() > 0) {
			dbflag = true;
			HashMap<String, String> uIValue = verifyUiColumnHeaderOnHold(roleID, roleType, postPeriod);
			if (dbValue.containsKey(uIValue)) {
				assertEquals(matchflag, true);
			} else {
				assertEquals(matchflag, true);
			}
			assertEquals(dbflag, true);
		} else {
			System.out.println("No OnHold Contract Exists For Given Data");
		}
		assertEquals(dbflag, true);
	}

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_85", dataProviderClass = UnderwritingDataProvider.class, description = "Validate that the user is able to 1)expand and contract the On Hold/ Short Pay Pane."
					+ " 2)Next button and fields on new popup window ,3) ValidateOnHold Popup Screen")
	public void ValidateToggleScrollOnHoldsection(String[] inputArray) throws Exception {
		boolean matchflag = false;
		boolean dbflag = false;
		boolean nextBtnOnHoldPopup = false;
		//// go to underwriting tab
		goToUnderWritingTab();
		goToAccountStatements();
		String roleID = inputArray[0];
		String roleType = inputArray[1];
		String postPeriod = inputArray[2];
		HashMap<String, String> dbValue = fetchOnHoldGivenRoleIDPostPeriodDB(roleID, roleType, postPeriod);
		if (dbValue.size() > 0) {
			dbflag = true;
			matchflag = fillDataForOnHoldAccountStatement(roleID, roleType, postPeriod);
			assertEquals(matchflag, true);
			assertEquals(dbflag, true);
			boolean toggleScroll = false;
			toggleScroll = validateToggleScrollOnHold();
			int count = listOfElements("listviewBtnOnHoldGrid").size();
			if (count > 1) {
				nextBtnOnHoldPopup = validatenextBtnOnHoldPopup();
			}
			HashMap<String, String> newPopupUI = validateONholdPopupScreen();
			HashMap<String, String> dbNewPopUP = dbValueOnHoldPopupScreen(roleID, "ONHOLD", postPeriod);
			assertEquals(toggleScroll, true);
			assertEquals(nextBtnOnHoldPopup, true);
			assertEquals(newPopupUI, dbNewPopUP);

		} else {
			System.out.println("No OnHold Contract Exists For Given Data");
		}
		assertEquals(dbflag, true);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_85", dataProviderClass = UnderwritingDataProvider.class, description = "Validate that Ocean should display the contract under On Hold /Short Pay")
	public void ValidateOceanDisplayContractUnderOnHold() throws Exception {
		boolean matchflag = false;
		boolean dbflag = false;
		copyFilesWorkingRemittance();
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		String[] inputArray = { "random", "2", "", "Paper", "Standard", "Paper Remit", "", "Automation", "234", "1500",
				"Dealer", "998" };
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceValues(inputArray);
		// String remittanceName = "pOlJghzmjKk1GZQ68CCB";
		if (remittanceName.length() > 0) {
			// HashMap<String, String> uiValues = myData(remittanceName);
			refreshRemittance();
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(2);
			///// Update check status
			addCheck();
			//// Refresh remittance
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			String[] inputData = { "SNE", "", "n", "n", "n", "n", "n", "ALLPLANS", "ALLPLANS", "", "", "", "", "", "",
					"", "", "", "", "" };
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			String DealerID = premiumData.get("DEALERID");
			if (sss.size() > 1) {
				refreshRemittance();
				searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
				lockAndViewContract();
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					//// do nothing
				}
				premium();
				enterCustomerPaidAndDealerPaid("1234", "1500");
				selectCheckAndScrollToTop();
				//// click under
				click("saveAllOnRemittance");
				waitForSomeTime(10);
				clickOnHoldWithReason(new String[] { "" });
				String status = getAttributeValue("getCotractStatusOnUnderwritingPage", "Value.Name");
				assertEquals(status.equalsIgnoreCase("OnHold"), true);
				waitForSomeTime(10);
				// onHoldRemittancePosting(dealerPaid);
				postRemittance();
				takeScreenshot();
				goToUnderWritingTab();
				goToAccountStatements();
				String roleID = DealerID;
				String roleType = "Dealer";
				String postPeriod = inputArray[2];
				HashMap<String, String> dbValue = fetchOnHoldGivenRoleIDPostPeriodDB(roleID, roleType, postPeriod);
				if (dbValue.size() > 0) {
					dbflag = true;
					HashMap<String, String> uiValue = verifyUiColumnHeaderOnHold(roleID, roleType, postPeriod);
					if (dbValue.containsValue(uiValue)) {
						matchflag = true;
					}
					assertEquals(matchflag, true);
					assertEquals(dbflag, true);
				} else {
					System.out.println("No OnHold Contract Exists For Given Data");
				}
				assertEquals(dbflag, true);
			}
		}
	}

	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC_85", dataProviderClass = UnderwritingDataProvider.class, description = "Validate that Ocean should display the contract under On Hold /Short Pay")
	public void ValidateOceanDeleteContractUnderOnHold(String[] inputArray) throws Exception {
		boolean matchflag = false;
		boolean dbflag = false;
		boolean nextBtnOnHoldPopup = false;
		//// go to underwriting tab
		goToUnderWritingTab();
		goToAccountStatements();
		String roleID = inputArray[0];
		String roleType = inputArray[1];
		String postPeriod = inputArray[2];
		HashMap<String, String> dbValue = fetchOnHoldGivenRoleIDPostPeriodDB(roleID, roleType, postPeriod);
		if (dbValue.size() > 0) {
			dbflag = true;
			matchflag = fillDataForOnHoldAccountStatement(roleID, roleType, postPeriod);
			assertEquals(matchflag, true);
			assertEquals(dbflag, true);
			List<WebElement> onHoldSecton = listOfElements("listgetCertFromOnHoldGrid");
			String onHoldCert = onHoldSecton.get(1).getText();
			underWOnHoldContract(onHoldCert);
			HashMap<String, String> dbNewContract = dbValueOnHoldPopupScreen(roleID, roleType, postPeriod);
			if (dbNewContract.containsValue(onHoldCert)) {
				matchflag = false;
			} else {
				matchflag = true;
			}
			assertEquals(matchflag, true);
		} else {
			System.out.println("No OnHold Contract for Given Data");
		}
		assertEquals(dbflag, true);
	}
}
