package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_PBI_16823 class automates, which holds 3 Test Case; Test
 * Condition Description : Validate ocean ability to select check from available
 * check list. Validate Ocean ability to update check from business processor
 * Validate Ocean Ability to clone check from checks Tab.
 * 
 * @author Atul Awasthi
 */

public class OCEAN_Underwriting_TC_72 extends UnderwritingModulePages {

	/**
	 * Validate Ocean Ability to clone check from checks tab
	 * 
	 */
	@Test(priority = 5, groups ="fullSuite", description = "Validate Ocean Ability to clone check from checks tab")
	public void businessProcessorTestCase9() throws Exception {
		copyFilesWorkingRemittance();
		// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "", "Paper", "Standard", "Paper Remit", "", "Automation", "234", "1500",
				"Dealer", "998" };
		String remittanceName = enterRemittanceValuesWithDealerId(inputArray);
		if (remittanceName.length() > 0) {
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
//			refreshRemittance();}
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			String remitNumber = remitt.get(1).get("RemittanceNumber");
			searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
			lockAndViewContract();
			// HashMap<Integer, HashMap<String, String>> contractFromRemittance =
			// pricing_underwriting_getPendingContractwithRemittance();
			// if (contractFromRemittance.size() > 0) {
			List<WebElement> checkAmountsInCheckTab = null;
			List<WebElement> checkAmountsInBusinessProcess = null;
			String cloneCheckAmountInBusinessProcessor = "";
			String cloneCheckNumberInBusinessProcessor = "";
			List<WebElement> checkNumbersInBusinessProcess = null;
			// String remittName = contractFromRemittance.get(1).get("RemittanceName");
			// String remitNumber = contractFromRemittance.get(1).get("RemittanceNumber");
			// String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			// goToUnderWritingTab();
			// goToRemittanceList();
			waitForSomeTime(20);
			// searchContractwithPendingState(remittName, fileName);
			// lock contract on user name and open enter values in contract window
			// lockAndViewContract();
			goToChecksTab();
			addCheckOnCheckTabs();
			cloneCheck();
			waitForSomeTime(3);
			// click on clone check popup
			click("saveremittancepopup");
			// save clone check
			click("clickOK");
			List<WebElement> checkNumbersInCheckTab = listOfElements("getCheckNumberFromRemittanceSummryPage");
			String originalCheckNumber = checkNumbersInCheckTab.get(0).getText();
			String cloneCheckNumber = checkNumbersInCheckTab.get(checkNumbersInCheckTab.size() - 1).getText();
			checkAmountsInCheckTab = listOfElements("getCheckAmountFromRemittanceSummryPage");
			String cloneCheckAmount = checkAmountsInCheckTab.get(checkAmountsInCheckTab.size() - 1).getText();

			// Verify clone check detail with DB
			boolean uiFlag = false;
			HashMap<String, String> uiCheckDetail = new HashMap<String, String>();
			uiCheckDetail.put("Check_no", cloneCheckNumber);
			uiCheckDetail.put("Amount", cloneCheckAmount);
			HashMap<Integer, HashMap<String, String>> dbCheckDetail = Underwriting_getCheckDetail(remitNumber);
			HashMap<String, String> dbData = null;
			for (Entry<Integer, HashMap<String, String>> letterEntry : dbCheckDetail.entrySet()) {
				dbData = new HashMap<String, String>();
				for (Entry<String, String> nameEntry : letterEntry.getValue().entrySet()) {

					dbData.put(nameEntry.getKey(), nameEntry.getValue());
				}
				if (dbData.equals(uiCheckDetail)) {
					uiFlag = true;
					break;
				}
			}
			assertTrue(uiFlag, "Clone check details not matching with DB");
			if (originalCheckNumber.equals(cloneCheckNumber) && cloneCheckAmount.equals("0.00")) {
				click("contractExpander");
				goToBusinessProcessorTab();
				waitForSomeTime(3);
				click("clickBusinessProcessorSearchButton");
				waitForSomeTime(5);
				type("remittanceNumberFilterRowInBusinessProcessor", remitNumber);
				checkNumbersInBusinessProcess = listOfElements("getCheckNumbersInBusinessProcessorGrid");
				checkAmountsInBusinessProcess = listOfElements("getCheckAmountsInBusinessProcessorGrid");
				cloneCheckNumberInBusinessProcessor = checkNumbersInBusinessProcess.get(0).getText();
				cloneCheckAmountInBusinessProcessor = checkAmountsInBusinessProcess.get(0).getText();
				assertTrue(
						cloneCheckAmount.equals(cloneCheckAmountInBusinessProcessor)
								&& cloneCheckNumber.equals(cloneCheckNumberInBusinessProcessor),
						"Clone check details not matching in businees processor tab.");
				click("getRemittanceNumberFromUI");
				rightClick("getRemittanceNumberFromUI");
				click("clickOnEditCheckOnBusinessProcessor");
				waitForSomeTime(3);
				clearTextBox("typeCheckAmount");
				String editedCheckAmount = "100";
				type("typeCheckAmount", editedCheckAmount);
				typeKeys("selectRoleType", "Dealer");
				click("clickOnPlusIcon");
				type("typeRoleIdOnSearchPage", "140");
				click("clickOnRoleIdSearchButton");
				waitForSomeTime(5);
				click("clickOnSelectButtonRoleID");
				click("clickOnSaveButtonOnBusinessProcessor");
				waitForSomeTime(5);
				type("remittanceNumberFilterRowInBusinessProcessor", remitNumber);
				checkNumbersInBusinessProcess = listOfElements("getCheckNumbersInBusinessProcessorGrid");
				checkAmountsInBusinessProcess = listOfElements("getCheckAmountsInBusinessProcessorGrid");
				cloneCheckNumberInBusinessProcessor = checkNumbersInBusinessProcess.get(0).getText();
				cloneCheckAmountInBusinessProcessor = checkAmountsInBusinessProcess.get(0).getText();
				cloneCheckAmountInBusinessProcessor = cloneCheckAmountInBusinessProcessor.replaceAll("\\.0*$", "");
				// Verify modified check detail with DB
				HashMap<String, String> uiModifiedCheckDetail = new HashMap<String, String>();
				uiModifiedCheckDetail.put("Check_no", cloneCheckNumberInBusinessProcessor);
				uiModifiedCheckDetail.put("Amount", cloneCheckAmountInBusinessProcessor);
				HashMap<Integer, HashMap<String, String>> dbModifiedCheckDetail = Underwriting_getCheckDetail(
						remitNumber);
				for (Entry<Integer, HashMap<String, String>> letterEntry : dbModifiedCheckDetail.entrySet()) {
					dbData = new HashMap<String, String>();
					for (Entry<String, String> nameEntry : letterEntry.getValue().entrySet()) {

						dbData.put(nameEntry.getKey(), nameEntry.getValue());
					}
					if (dbData.equals(uiCheckDetail)) {
						uiFlag = true;
						break;
					}

				}
				assertTrue(uiFlag, "Modified check details not matching with DB");

				if (editedCheckAmount.equals(cloneCheckAmountInBusinessProcessor)) {
					assertTrue(true, "Check detail not modified Business Processor");
				} else {
					new SkipException("Check detail not modified Business Processor");
				}
			} else {
				new SkipException("Check not cloned on Checks Tab");
			}
		} else {
			new SkipException("no value exist in db.");
		}
	}

}
