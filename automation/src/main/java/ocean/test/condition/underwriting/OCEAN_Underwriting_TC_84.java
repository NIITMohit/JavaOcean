package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Set;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_PBI_18035 class which holds 6 Test Case; Test Condition
 * Description : Validate for a paper contract that OCEAN populate ineligible
 * vehicle message on price sheet selection, if related VIN details are defined
 * as Class 0 for pricesheet's classification list. Validate for a web contract
 * that OCEAN populate ineligible vehicle message on underwriting screen , if
 * related VIN details are defined as Class 0 for pricesheet's classification
 * list. Validate premium recalculation ( ineligible vehicle check) for on hold
 * contract, if it's classification list edited for existing vehicle details on
 * a contract. Validate premium recalculation ( ineligible vehicle check) for
 * contract adjustment , if it's classification list edited for existing vehicle
 * details on a contract. Validate premium recalculation for an On hold
 * contract, if it's classification list is edited with new vehicle details for
 * a contract. Validate premium recalculation (eligible vehicle check) for
 * contract reinstatement , if it's classification list edited for vehicle
 * details
 * 
 * @author Atul Awasthi
 */
public class OCEAN_Underwriting_TC_84 extends UnderwritingModulePages {
	/**
	 * This function automates Test Case-1; Test Case description : Validate for a
	 * paper contract that OCEAN populate ineligible vehicle message on price sheet
	 * selection, if related VIN details are defined as Class 0 for pricesheet's
	 * classification list.
	 * 
	 */
	// @Test(priority = 1, groups = "regression", description = " Validate for a
	// paper contract that OCEAN populate ineligible vehicle message on price sheet
	// selection, if related VIN details are defined as"
	// + " Class 0 for pricesheet's classification list.")
	public void validateErrorMsgTC1() throws Exception {
		boolean matchFlag = false;
		copyFilesWorkingRemittance();
		goToUnderWritingTab();
		goToRemittanceList();
		// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		click("clickRemittanceReset");
		waitForSomeTime(15);
		dragAndDropFiles();
		// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "", "Paper", "Standard", "Paper Remit", "", "Automation", "234", "1500",
				"Dealer", "174" };
		String remittanceName = enterRemittanceValues(inputArray);
		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittanceNew(remittanceName);
			/// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			//// Update check status
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
			String ineligibleVin = "2201873";
			premiumData.put("VIN", ineligibleVin);
			if (sss.size() > 1) {
				type("typeContract", remitt.get(1).get("FILE_NAME"));
				addCheckOnremmit();
				enterMandatoryValuesOnContractPBI_18035(premiumData);
				if (checkIsDisplayed("ineligibleVehicleError"))
					matchFlag = true;
				assertEquals(matchFlag, true);
			} else {
				throw new Exception("Remittace is not UnderWritten");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

	/**
	 * This function automates Test Case-1; Test Case description : Validate for a
	 * web contract that OCEAN populate ineligible vehicle message on underwriting
	 * screen , if related VIN details are defined as Class 0 for pricesheet's
	 * classification list.
	 * 
	 */
//	@Test(priority = 1, groups = "regression", description = "Validate for a web contract that OCEAN populate ineligible vehicle message on underwriting screen ,"
	// + " if related VIN details are defined as Class 0 for pricesheet's
	// classification list.")
	public void validateErrorMsgTC2() throws Exception {
		boolean matchFlag = false;
		copyFilesWorkingRemittance();
		goToUnderWritingTab();
		goToRemittanceList();
		// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		click("clickRemittanceReset");
		waitForSomeTime(15);
		dragAndDropFiles();
		// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "", "Web", "Standard", "Web Remit", "", "Automation", "234", "1500",
				"Dealer", "174" };
		String remittanceName = enterRemittanceValues(inputArray);
		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittanceNew(remittanceName);
			/// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			waitForSomeTime(2);
			String OceanPage = windowsDriver.getWindowHandle();
			rightClick("loadRemittance");
			click("externalRemittance");
			Set<String> windowHandles = windowsDriver.getWindowHandles();
			String popup = "";
			for (String singleWindowHandle : windowHandles) {
				if (!OceanPage.equals(singleWindowHandle)) {
					popup = singleWindowHandle;
				}

			}
			windowsDriver.switchTo().window(popup);
			type("typeContractOnExtRemit", "SNEC9115172I20");
			waitForSomeTime(10);
			click("searchContractOnExtRemit");
			waitForSomeTime(5);
			click("checkBoxExtRemit");
			click("clickSaveExtRemit");
			click("clickOK");
			click("closePS");
			windowsDriver.switchTo().window(OceanPage);
			//// Update check status
			@SuppressWarnings("unused")
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			type("typeContract", "SNEC9115172I20");
			click("loadRemittance");
			click("viewContract");
			waitForSomeTime(5);
			removeErrorMessages();
			waitForSomeTime(5);
			for (String singleWindowHandle : windowHandles) {
				if (!OceanPage.equals(singleWindowHandle)) {
					popup = singleWindowHandle;
				}

			}
			windowsDriver.switchTo().window(popup);
			if (checkIsDisplayed("ineligibleVehicleError"))
				matchFlag = true;
			assertEquals(matchFlag, true);
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

	/**
	 * This function automates Test Case-1; Test Case description : Validate premium
	 * recalculation ( ineligible vehicle check) for on hold contract, if it's
	 * classification list edited for existing vehicle details on a contract.
	 * 
	 */
	// @Test(priority = 1, groups = "regression", dataProvider =
	// "fetchDataForPBI_18035", dataProviderClass = UnderwritingDataProvider.class,
	// description = " Validate premium recalculation ( ineligible vehicle check)
	// for on hold contract, "
	// + "if it's classification list edited for existing vehicle details on a
	// contract.")
	public void validateErrorMsgTC3_5(String[] inputDetail) throws Exception {
		HashMap<String, String> arrayData = null;
		arrayData = dataFor18035(inputDetail);
		copyFilesWorkingRemittance();
		goToUnderWritingTab();
		goToRemittanceList();
		// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		click("clickRemittanceReset");
		waitForSomeTime(15);
		dragAndDropFiles();
		// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "", "Paper", "Standard", "Paper Remit", "", "Automation", "234", "1500",
				"Dealer", "174" };
		String remittanceName = enterRemittanceValues(inputArray);
		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittanceNew(remittanceName);
			/// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			//// Update check status
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
			premiumData.put("VIN", "SAJBD4FX4LCY84327");
			premiumData.put("MAKE", "Jaguar");
			premiumData.put("MODEL", "XF");
			premiumData.put("YEAR", "2020");
			premiumData.put("CLASS", "1");
			if (sss.size() > 1) {
				type("typeContract", remitt.get(1).get("FILE_NAME"));
				addCheckOnremmit();
				premiumData.putAll(enterMandatoryValuesOnContract_18035(premiumData));
				enterCustomerPaidAndDealerPaid("1000", "1500");
				premium();
				selectCheckAndScrollToTop();
				click("saveAllOnRemittance");
				waitForSomeTime(5);
				inputValues(arrayData);
				// need to get the vin for eligible vehicle in this case.
				waitForSomeTime(5);
				click("vinNumberOverride");
				String eligibleVin = "1HGEJ8145WL012057";
				click("vinNumberOverride");
				type("vinNumber", eligibleVin);
				type("vinNumberMake", "Honda");
				type("vinNumberModel", "Accord");
				type("vinNumberYear", "2018");
				contractScrollToBottom();
				type("getClass", "1");
				click("clickPremiumCalculate");
				contractScrollToTop();
				//// click under
				click("saveAllOnRemittance");
				waitForSomeTime(5);
				click("clickUnderW");
				waitForSomeTime(5);
				String status = getTextOfElement("getCotractStatusOnUnderwritingPage");
				assertEquals(status, "UnderW");
			} else {
				throw new Exception("Remittace is not UnderWritten");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

	/**
	 * This function automates Test Case-1; Test Case description : Validate premium
	 * recalculation ( ineligible vehicle check) for contract adjustment , if it's
	 * classification list edited for existing vehicle details on a contract.
	 * 
	 */
	// @Test(priority = 1, groups = "regression", description = "Validate premium
	// recalculation ( ineligible vehicle check) for contract adjustment ,"
	// + " if it's classification list edited for existing vehicle details on a
	// contract.")
	public void validateErrorMsgTC4() throws Exception {
		boolean matchFlag = false;
		String ContractIDForAdjustment = cancellation_getContractIdBasedOnStatus("Active");
		;
		// Need to find that active contract which has vehicle class related to master
		// pricesheet as '0'.
		if (ContractIDForAdjustment.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			type("contractNoInFindContract", ContractIDForAdjustment);
			click("searchFindContractBtn");
			waitForSomeTime(10);
			String OceanPage = windowsDriver.getWindowHandle();
			for (int i = 0; i < 5; i++) {
				try {
					click("AdjustContractBtn");
					break;
				} catch (Exception w) {
					continue;
					//// do nothing
				}
			}
			try {
				click("clickOK");
			} catch (Exception w) {
				//// do nothing
			}
			waitForSomeTime(10);
			Set<String> windowHandles = windowsDriver.getWindowHandles();
			String popup = "";
			for (String singleWindowHandle : windowHandles) {
				System.out.println("singleWindowHandle====" + singleWindowHandle);
				if (!OceanPage.equals(singleWindowHandle)) {
					popup = singleWindowHandle;
				}

			}
			windowsDriver.switchTo().window(popup);
			if (checkIsDisplayed("ineligibleVehicleError"))
				matchFlag = true;
			assertEquals(matchFlag, true);
		}
	}

	/**
	 * This function automates Test Case-1; Test Case description : Validate premium
	 * recalculation (eligible vehicle check) for contract reinstatement , if it's
	 * classification list edited for vehicle details
	 * 
	 */
	// @Test(priority = 1, groups = "regression", description = " Validate premium
	// recalculation (eligible vehicle check) for contract reinstatement , "
	// + "if it's classification list edited for vehicle details")
	public void validateErrorMsgTC6() throws Exception {
		@SuppressWarnings("unused")
		String contractID = UW_getCancelContractBasedOnCancelreason("Cancelled", "ineligible vehicle");
		// *********************
		// vehicle class need to change here becoz need to verify on new business form.
		String newContractID = "SNEF7176033H20";
		goToUnderWritingTab();
		goToFindContractTab();
		type("contractNoInFindContract", newContractID);
		click("searchFindContractBtn");
		for (int i = 0; i < 5; i++) {
			try {
				click("ReinstatedBtn");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		removeErrorMessages();
		contractExpander();
		contractScrollToBottom();
		click("clickPremiumCalculate");
		contractScrollToTop();
		click("clickReinstateOnNewBusinessForm");// need to add this OR inside below Function.
		String[] inputArray = { "Correct Form", "Others" };
		clickReinstateWithReason(inputArray);
		click("contractExpander");
		waitForSomeTime(5);
		type("contractNoInFindContract", newContractID);
		click("searchFindContractBtn");
		waitForSomeTime(10);
		String status = getTextOfElement("statusInFindContract");
		assertEquals(status, "Active");
	}

	/**
	 * @throws Exception
	 */
	/**
	 * @throws Exception
	 */
	@Test(priority = 1, groups = { "regression", "smoke",
			"smoke" }, dataProvider = "fetchDataForTC84", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Error Message based on input excel provided")
	public void validateVINErrorMessage(String[] inputarray) throws Exception {
		String[] inputData = { inputarray[0], "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"" };
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
		HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
		if (sss.size() > 1) {
			goToUnderWritingTab();
			lockAndViewContract();
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			//// enter all mandatory values only on new business form screen
			contractScrollToTop();
			click("clearContractData");
			type("typeContractNumber", randomString(10));
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate localDate = LocalDate.now().minusDays(1);
			type("purchaseDateForNewContract", dtf.format(localDate).toString());
			//// Enter Primary Account Details
			type("primaryAccountType", premiumData.get("PrimaryAccount"));
			type("primaryAccountId", premiumData.get("DEALERID"));
			click("primaryAccountSearchButton");
			//// Enter Secondary Account Details
			type("secondaryAccountType", premiumData.get("SecondaryAccount"));
			type("secondaryAccountId", premiumData.get("SecondaryAccountId"));
			click("secondaryAccountSearchButton");
			takeScreenshot();
			//// enter vin
			String togglestate = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
			if (togglestate.equals("1"))
				click("vinNumberOverride");
			if (inputarray[1].length() > 1 && inputarray[1] != null) {
				typeKeys("vinNumber", inputarray[1]);
				removeErrorMessages();
			} else {
				type("vinNumber", premiumData.get("VIN"));
				click("vinNumberOverride");
				type("vinNumberMake", inputarray[2]);
				if (inputarray[3].length() > 1 && inputarray[3] != null)
					type("vinNumberModel", inputarray[3]);
				else {
					click("vinNumberModel");
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
					robot.keyPress(KeyEvent.VK_DOWN);
					robot.keyRelease(KeyEvent.VK_DOWN);
				}
				type("vinNumberYear", premiumData.get("YEAR"));
			}
			type("vinNumberMileage", premiumData.get("MILEAGE"));
			type("vinNumberPrice", premiumData.get("VEHICLEPRICE"));
			//// enter pricesheet
			String str = premiumData.get("PRICESHEETID");
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			StringSelection strSel = new StringSelection(str);
			clipboard.setContents(strSel, null);
			click("selectPricesheet");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			String test = getValue("ineligibleVechcle");
			//// rmeove error messags
			removeErrorMessages();
			if (test.equalsIgnoreCase("Ineligible Vehicle!") && inputarray[4].equalsIgnoreCase("InEligible"))
				assertEquals(true, true);
			else if (!test.equalsIgnoreCase("Ineligible Vehicle!") && inputarray[4].equalsIgnoreCase("Eligible"))
				assertEquals(true, true);
			else
				assertEquals(false, true);

		} else {
			throw new Exception("no matching dealer and lender found for pricesheet");
		}
	}

	/**
	 * This function is executed before class, this will land till contract fill up
	 * form,
	 * 
	 */
	@BeforeClass(alwaysRun = true)
	public void prepareRemittance() throws Exception {
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
			//// lock contract on uF34va3wrser name and open enter values in contract window

		} else {
			new SkipException("no remittance found");
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
