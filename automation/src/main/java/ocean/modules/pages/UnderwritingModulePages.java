package ocean.modules.pages;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import ocean.common.ObjectRepo;
import ocean.modules.database.UnderwritingDataBase;

/**
 * This is object class which contains all pages of underwriting modules
 * 
 * @author Mohit Goel
 */
public class UnderwritingModulePages extends UnderwritingDataBase {

	/**
	 * This function is used to land to applyFiltersOnInputField
	 * 
	 * @return
	 * 
	 */
	public boolean applyFiltersOnInputFieldAndVerifyData(String filterName, String filterExpectedValue)
			throws Exception {
		boolean flag = false;
		switch (filterName) {
		case "Remittance_Name":
			type("typeToSearchRemittance", filterExpectedValue);
			String old = getValue("remitName");
			String compare = filterExpectedValue;
			flag = old.toLowerCase().equals(compare.toLowerCase());
			type("typeToSearchRemittance", "");
			waitForSomeTime(5);
			break;
		case "Post_Period":
			type("typeToSearchPostPeriod", filterExpectedValue);
			String old1 = getValue("remitPostPeriod");
			String compare1 = filterExpectedValue;
			flag = old1.toLowerCase().equals(compare1.toLowerCase());
			type("typeToSearchPostPeriod", "");
			waitForSomeTime(5);
			break;
		case "Remittance_Number":
			type("typeToSearchRemittanceNumber", filterExpectedValue);
			String old11 = getValue("remitNumber");
			String compare11 = filterExpectedValue;
			flag = old11.toLowerCase().equals(compare11.toLowerCase());
			type("typeToSearchRemittanceNumber", "");
			waitForSomeTime(5);
			break;
		case "Source":
			typeKeys("typeToSearchSource", filterExpectedValue);
			String old111 = getValue("remitSource");
			String compare111 = filterExpectedValue;
			flag = old111.toLowerCase().equals(compare111.toLowerCase());
			click("typeToSearchSourceClear");
			waitForSomeTime(5);
			break;
		case "Sub_Type":
			typeKeys("typeToSearchSubType", filterExpectedValue);
			String old2 = getValue("remitSubType");
			String compare2 = filterExpectedValue;
			flag = old2.toLowerCase().equals(compare2.toLowerCase());
			click("typeToSearchSubTypeClear");
			waitForSomeTime(5);
			break;
		case "Type":
			typeKeys("typeToSearchType", filterExpectedValue);
			String old21 = getValue("remitType");
			String compare21 = filterExpectedValue;
			flag = old21.toLowerCase().equals(compare21.toLowerCase());
			click("typeToSearchTypeClear");
			waitForSomeTime(5);
			break;

		case "Core":
			type("typeToSearchCoreCount", filterExpectedValue);
			String old211 = getValue("remitCore");
			String compare211 = filterExpectedValue;
			flag = old211.toLowerCase().equals(compare211.toLowerCase());
			type("typeToSearchCoreCount", "");
			waitForSomeTime(5);
			break;
		case "LWA":
			type("typeToSearchLWACount", filterExpectedValue);
			String old3 = getValue("remitLWA");
			String compare3 = filterExpectedValue;
			flag = old3.toLowerCase().equals(compare3.toLowerCase());
			type("typeToSearchLWACount", "");
			waitForSomeTime(5);
			break;
		case "UnderW":
			type("typeToSearchUnderW", filterExpectedValue);
			String old31 = getValue("remitUnderW");
			String compare31 = filterExpectedValue;
			flag = old31.toLowerCase().equals(compare31.toLowerCase());
			type("typeToSearchUnderW", "");
			waitForSomeTime(5);
			break;
		case "Comment":
			type("typeToSearchComment", filterExpectedValue);
			String old311 = getValue("remitComment");
			String compare311 = filterExpectedValue;
			flag = old311.toLowerCase().equals(compare311.toLowerCase());
			type("typeToSearchComment", "");
			waitForSomeTime(5);
			break;
		case "Created_By":
			type("typeToSearchCreatedBy", filterExpectedValue);
			String old3111 = getValue("remitCreatedBy");
			String compare3111 = filterExpectedValue;
			flag = old3111.toLowerCase().equals(compare3111.toLowerCase());
			type("typeToSearchCreatedBy", "");
			waitForSomeTime(5);
			break;
		case "Locked_By":
			type("typeToSearchLockedBy", filterExpectedValue);
			String old4 = getValue("remitLockedBy");
			String compare4 = filterExpectedValue;
			flag = old4.toLowerCase().equals(compare4.toLowerCase());
			type("typeToSearchLockedBy", "");
			waitForSomeTime(5);
			break;
		case "Created_Date":
			type("typeToSearchCreatedDate", filterExpectedValue);
			String old41 = getValue("remitCreatedDate");
			String compare41 = filterExpectedValue;
			flag = old41.toLowerCase().equals(compare41.toLowerCase());
			type("typeToSearchCreatedDate", "");
			waitForSomeTime(5);
			break;
		default:
			flag = false;
			break;
		}

		return flag;
	}

	/*	*//**
			 * This function is used to view PDF, View PDF in new window and delete PDF
			 * 
			 *//*
				 * public void viewPDFandDeletePDF() throws Exception { click("clickOnPDF");
				 * click("closePDF"); waitForSomeTime(2); rightClick("clickOnPDF");
				 * click("clickOnDeletePdf"); click("clickOK"); }
				 */

	/**
	 * This function is used to lock remittance
	 * 
	 */
	public void lockTopRemittance() throws Exception {
		click("remittanceLockUnlockButton");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void searchRemittanceNew(String remittName) throws Exception {
		//// Type RemittanceName
		type("typeToSearchRemittance", remittName);
		//// expand remittance to get contracts

		int size = listOfElements("expandRemittance").size();
		List<WebElement> contractNumber = listOfElements("expandRemittance");
		for (int i = 1; i <= size; i++) {
			try {
				contractNumber.get(i).click();
				break;
			} catch (Exception e) {

			}
		}
		waitForSomeTime(15);
	}

	/**
	 * This function is used to enter values in create remittance
	 * 
	 */
	public String enterRemittanceValuesDelete(String[] inputArray) throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		try {
			click("clickDelete");
		} catch (Exception ex) {
			//// dp nothing
		}
		try {
			if (inputArray[0].toLowerCase().equals("random")) {
				remittanceName = randomString(20);
			} else {
				remittanceName = inputArray[0];
			}
			type("remittanceName", remittanceName);
			//// Enter core count
			type("remittanceCoreCount", inputArray[1]);
			type("remittanceLWACount", inputArray[2]);
			//// select remit type
			typeKeys("remittanceContractCombobox", inputArray[3]);
			typeKeys("remittanceTypeCombobox", inputArray[4]);
			typeKeys("remittanceRemitTypeComboBox", inputArray[5]);
			typeKeys("remittanceSubTypeComboBox", inputArray[6]);
			//// type comments
			type("remittanceComments", inputArray[7]);

			//// add check here
			type("addCheckOnRemittance", "121");
			type("addCheckAmtOnRemittance", "123");
			//// enter dealer details
			type("typeDealerinaddcheck", "Dealer");
			click("addDealeraddButton");
			type("addcheckroleid", "140");
			click("addchecksearchbutton");
			click("addchecksearchtoprow");
			//// add check details
			click("clickAddCheckAmtOnRemittance");
			//// delete check
			click("selecttopcheckdelete");
			click("clickDelete");
			/// enter check details
			type("addCheckOnRemittance", inputArray[8]);
			type("addCheckAmtOnRemittance", inputArray[9]);
			//// enter dealer details
			type("typeDealerinaddcheck", inputArray[10]);
			click("addDealeraddButton");
			type("addcheckroleid", inputArray[11]);
			click("addchecksearchbutton");
			click("addchecksearchtoprow");
			//// add check details
			click("clickAddCheckAmtOnRemittance");

			//// click save
			String clickState = getAttributeValue("clickSaveRemittance", "IsEnabled");
			if (clickState.toLowerCase().equals("true")) {
				click("clickSaveRemittance");
				if (!(inputArray[8].length() > 1 && inputArray[9].length() > 1))
					click("clickOnYesButton");
			} else
				return "";
			// click("remittanceExpander");
			//// close file explorer
			for (int i = 0; i < 2; i++) {
				try {
					click("closeFolderExplorer");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			for (int i = 0; i < 5; i++) {
				try {
					click("remittanceExpander");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			//// close remittance
		} catch (Exception e) {
			remittanceName = "";
		}
		return remittanceName;
	}

	/**
	 * This function is used to verify UI Changes as a part of PBI 6474
	 * 
	 */
	public void verifyUiChangesForDealerStatement() throws Exception {
		takeScreenshot();
		String newDealerIdText = getValue("getNewDealerIdText");
		assertEquals(newDealerIdText, "Role ID");
		String roleTypeText = getValue("getRoleTypeText");
		assertEquals(roleTypeText, "Role Type");
		assertEquals("Account Statements".toLowerCase(), getValue("accountStatementsWindowTitle").toLowerCase());

		int columnSize = getSearchResultCount("listOfColumnsAccountStatement");
		HashSet<String> columnValues = new HashSet<String>();
		for (int i = 1; i < columnSize; i++) {
			String columnValue = getValue("listOfColumnsAccountStatement", i);
			columnValues.add(columnValue);
		}

		HashSet<String> expColumnValues = new HashSet<String>();
		expColumnValues.add("Agent ID");
		expColumnValues.add("Status");
		expColumnValues.add("Role Type");
		expColumnValues.add("Role ID");
		expColumnValues.add("Account Name");
		expColumnValues.add("EOM Posted DBCR");
		expColumnValues.add("Prior1 EOM");
		expColumnValues.add("Prior2 EOM");
		expColumnValues.add("Posted DBCR");
		expColumnValues.add("Onhold DBCR");
		assertEquals(expColumnValues.equals(columnValues), true);
	}

	/**
	 * This function is used to verify pdf status
	 * 
	 */
	public boolean thumbNailOfUploadedPDFOnTopRemittance() throws Exception {
		boolean flag = true;
		waitForSomeTime(5);
		List<WebElement> we = listOfElements("remittancePDFLogo");
		if (we.size() < 1) {
			flag = false;
		}
		for (WebElement webElement : we) {
			String ss = webElement.getAttribute("AutomationId");
			if (!ss.toLowerCase().equals("ViewPDFButton")) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * This function is used to unlock remittance
	 * 
	 */
	public void unlockTopRemittance() throws Exception {
		click("remittanceLockUnlockButton");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This function is used to block remittance posting
	 * 
	 */
	public void blockRemittancePosting() throws Exception {
		goToRemittanceSummary();
		click("clickOnRemittanceSummary");
		click("clickOnPostRemittance");
		for (int i = 0; i < 4; i++) {
			try {
				click("clickOnYesButton");
			} catch (Exception e) {
				break;
				// // do nothing
			}
		}
		WebElement successMsg = windowsDriver.findElement(ObjectRepo.fetchOR("getTextOfSuccessMessage"));
		if (successMsg.getText().equals("Success")) {
			System.out.println("Post remittance successfully");
		} else {
			System.out.println("Block Remittance");

		}
	}

	/**
	 * This function is used to view contract click on link-view PDF in new window
	 * 
	 */
	public void openContractByPDFInNewWindow() throws Exception {
		waitForSomeTime(2);
		rightClick("clickOnPDF");
		waitForSomeTime(2);
		click("clickOnViewPdfInNewWindow");
		click("closePDF");
	}

	/**
	 * This function is used to select check
	 * 
	 */
	public void selectCheckAndScrollToTop() throws Exception {
		String togglestate = getAttributeValue("clickCheckCheckBox", "Toggle.ToggleState");
		if (togglestate.equals("0"))
			click("clickCheckCheckBox");
		contractScrollToTop();
	}

	/**
	 * This function is used to select check
	 * 
	 */
	public void postRemittance() throws Exception {
		click("loadRemittance");
		contractExpander();
		click("remittanceSummary");
		/*
		 * String dealeraid = getValue("totalDealerPaid"); goToCheckTab();
		 * type("checkTabCheckAmount", dealeraid); click("saveAllOnRemittance");
		 * click("clickOK"); click("remittanceSummary");
		 * click("clickOnSaveclonePriceSheet");
		 */
		click("clickOnPostRemittance");
		click("clickOnYesButton");
		waitForSomeTime(20);
		removeErrorMessages();
		try {
			type("typeInCommentBoxUW", "Testing started");
			click("commentBoxUWSaveButton");
		} catch (Exception e) {
			// Do nothing
		}
		click("contractExpander");
	}

	/**
	 * This function is used to select check
	 * 
	 */
	public void enterCustomerPaidAndDealerPaid(String custPaid, String dealerPaid) throws Exception {
		type("custPaid", custPaid);
		type("dealerPaid", dealerPaid);
	}

	/**
	 * This function is used to issue contract with random default values
	 * 
	 */
	public void issueContractOnSelectedRemittance(int contractCount) throws Exception {
		click("scrollContractsListUp");
		try {
			//// click yes to lock remittance
			click("scrollContractsListUp");

		} catch (Exception e) {
			// do nothing
		}
		click("clearContractData");
	}

	/**
	 * This function is used to assign Documents status to newly created remittance
	 * 
	 */
	public void assignDocumentsStatus_old(int documentsasContract) throws Exception {
		//// after remittance search it will come to documents
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
		//// click document drop down
		action.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).build().perform();
		///// select document type as contract for documentsasContract
		for (int i = 0; i < documentsasContract; i++) {
			action.sendKeys(Keys.F4).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).sendKeys(Keys.TAB).build()
					.perform();
			action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_LEFT).build().perform();
		}
		if (documentsasContract == 0) {
			action.sendKeys(Keys.F4).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER)
					.sendKeys(Keys.TAB).build().perform();
		} else
			//// select document type as check
			action.sendKeys(Keys.F4).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).sendKeys(Keys.TAB).build()
					.perform();
		//// save remittance
		try {
			click("typeToSearchRemittance");
		} catch (Exception e) {
			// TODO: handle exception
		}
		click("saveRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// do nothing
		}
		click("clickOK");
	}

	/**
	 * This function is used to assign Documents status to newly created remittance
	 * 
	 */
	public void assignDocumentsStatus(int documentsasContract) throws Exception {
		//// after remittance search it will come to document
		click("typeContract");
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_RIGHT).build().perform();
		// action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
		//// click document drop down
		// action.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).build().perform();
		///// select document type as contract for documentsasContract
		for (int i = 0; i < documentsasContract; i++) {
			action.sendKeys(Keys.F4).sendKeys("contract").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER)
					.sendKeys(Keys.ARROW_DOWN).build().perform();
		}
		action.sendKeys(Keys.F4).sendKeys("check").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).build().perform();
		//// save remittance
		click("typeToSearchRemittance");
		click("saveRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// do nothing
		}
		removeErrorMessages();
	}

	/**
	 * This common function is used to go to clickOnHoldWithReason
	 * 
	 * @return
	 * 
	 */
	public void clickOnHoldWithReason(String[] onHoldReason) {
		click("clickOnHold");
		removeErrorMessages();
		for (String onHold : onHoldReason) {
			switch (onHold.toLowerCase()) {
			case "nsf":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='NSF']").click();
				break;
			default:
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Setup Issue']").click();
				break;
			}
		}
		click("onHoldNextButtonNSF");
		waitForSomeTime(10);
		click("onHoldNextButtonNSF");
		waitForSomeTime(2);
		//// Click Send by all
		click("onHoldSendByNone");
		click("onHoldNextButtonNSF");
		waitForSomeTime(2);
		click("onHoldNextButtonNSF");
		removeErrorMessages();
	}

	/**
	 * This common function is used to go to clickOnHoldWithReason
	 * 
	 * @return
	 * 
	 */
	public void clickAdjustWithReason(String[] onHoldReason) {
		click("clickAdjust");
		for (String onHold : onHoldReason) {
			switch (onHold.toLowerCase()) {
			case "nsf":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='NSF']").click();
				break;
			case "rewritten":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Rewritten']").click();
				break;
			default:
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Upgrade']").click();
				break;
			}
		}
		click("clickNextBtnOnContractAdjustment");
		waitForSomeTime(25);
		click("clickNextBtnOnContractAdjustment");
		waitForSomeTime(5);
		removeErrorMessages();
	}

	/**
	 * This common function is used to go to clickOnHoldWithReason
	 * 
	 * @return
	 * 
	 */
	public void clickReinstateWithReason(String[] onHoldReason) {
		click("ClickReinstatedBtn");
		for (String onHold : onHoldReason) {
			switch (onHold.toLowerCase()) {
			case "cancelled in error":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Cancelled in Error']")
						.click();
				break;
			case "funding received":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Funding Received']")
						.click();
				break;
			case "missing info received":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Missing Info Received']")
						.click();
				break;
			case "correct form":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Correct Form']").click();
				break;
			case "other":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Other']").click();
				break;
			default:
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Other']").click();
				break;
			}
		}
		click("ClickReinstatedNxtBtn");
		waitForSomeTime(15);
		click("ClickReinstatedNxtBtn");
		waitForSomeTime(2);
		click("onHoldSendByNone");
		click("ClickReinstatedNxtBtn");
		removeErrorMessages();
	}

	/**
	 * This common function is used to go to clickOnHoldWithReason
	 * 
	 * @return
	 * 
	 */
	public void clickReturnWithReason(String[] onHoldReason) {
		click("clickRetrunBtn");
		for (String onHold : onHoldReason) {
			switch (onHold.toLowerCase()) {
			case "other":
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Other']").click();
				break;
			default:
				findElementByXpath("//*[@Name='com.aul.Ocean.DataModel.UW_CATEGORY']//*[@Name='Other']").click();
				break;
			}
		}
		click("clickRetrunNextBtn");
		waitForSomeTime(5);
		click("clickRetrunNextBtn");
		waitForSomeTime(15);
		click("clickRetrunNextBtn");
		waitForSomeTime(15);
		click("clickRetrunNextBtn");
		click("onHoldSendByNone");
		click("clickRetrunNextBtn");
		waitForSomeTime(5);
		click("clickRetrunNextBtn");
		waitForSomeTime(15);
		removeErrorMessages();
	}

	/**
	 * This function is used to get surcharges
	 * 
	 */
	public String surcharges() throws Exception {
		String surcharge = "0";
		try {
			surcharge = getAttributeValue("getSurcharges", "Name");
			click("getSurchargesCheckBox");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return surcharge;
	}

	public String getVehicleAge(String age) {
		int age1 = Integer.parseInt(age);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int vehicleYear = year - (age1 + 1);
		age = String.valueOf(vehicleYear);
		return age;

	}

	/**
	 * This function is used to get add check
	 * 
	 */
	public void addCheck() throws Exception {
		// click("typeToSearchRemittance");
		click("loadRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			//// do nothing
		}
		contractExpander();
		goToCheckTab();
		//// Click + button
		click("clickAddButtonToAddCheck");
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).build()
				.perform();
		click("clickAddButtonToAddCheckOk");
		click("saveAllOnRemittance");
		waitForSomeTime(15);
		click("clickOK");
		click("contractExpander");
		// click("contractExpander");
	}

	/**
	 * This function is used to get add check
	 * 
	 */
	public void addCheckOnremmit() throws Exception {
		// click("typeToSearchRemittance");
		click("loadRemittance");
		click("viewContract");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			//// do nothing
		}
		contractExpander();
		goToCheckTab();
		//// Click + button
		click("clickAddButtonToAddCheck");
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).build()
				.perform();
		click("clickAddButtonToAddCheckOk");
		click("saveAllOnRemittance");
		waitForSomeTime(10);
		click("clickOK");
		click("uWTabONcheckTab");

	}

	/**
	 * This function is used to get add check
	 * 
	 */
	public void addExternalRemittance() throws Exception {
		rightClick("remitName");
		click("externalRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
		}
		waitForSomeTime(25);
		click("checkBoxExtRemit");
		click("clickSaveExtRemit");
		waitForSomeTime(10);
		click("clickOK");
		waitForSomeTime(25);
	}

	/**
	 * This function is used to get add external web contract
	 * 
	 */
	public void addExternalRemittanceWithSelectedContractNumber(String vehicleType) throws Exception {
		String contractNo = searchContractOnBasisVehicleType(vehicleType);
		rightClick("remitName");
		click("externalRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
		}
		waitForSomeTime(25);
		switchToNewWindow();
		type("typeCertInExternalRem", contractNo);
		click("clickSearchInExternalRem");
		click("checkBoxCertExtRemit");
		click("clickSaveExtRemit");
		waitForSomeTime(10);
		click("clickOK");
		click("clickCloseAddExtRemitWindow");
		waitForSomeTime(25);
	}

	/**
	 * This function is used to get surcharges
	 * 
	 */
	public String calculateMyPremium(HashMap<String, String> premiumData) throws Exception {
		String finalValue = "";
		try {
			String excelPremium = getPremiumCalculation(premiumData);
			String surcharge = "0.00";
			String options = "0.00";
			String deduc = "0.00";
			String expPre = "0.00";
			if (excelPremium.length() == 0)
				excelPremium = "0.00";
			if (premiumData.get("SURCHARGES") != null && premiumData.get("SURCHARGESAMOUNT") != null
					&& premiumData.get("SURCHARGES").toLowerCase().equals("y")
					&& !premiumData.get("SURCHARGESAMOUNT").equals("0"))
				surcharge = premiumData.get("SURCHARGESAMOUNT").substring(
						premiumData.get("SURCHARGESAMOUNT").indexOf("$") + 1,
						premiumData.get("SURCHARGESAMOUNT").length());

			if (premiumData.get("OPTIONS") != null && premiumData.get("OPTIONSAMOUNT") != null
					&& premiumData.get("OPTIONS").toLowerCase().equals("y")
					&& !premiumData.get("OPTIONSAMOUNT").equals("0"))
				options = premiumData.get("OPTIONSAMOUNT").substring(premiumData.get("OPTIONSAMOUNT").indexOf("$") + 1,
						premiumData.get("OPTIONSAMOUNT").length());

			if (premiumData.get("DEDUCTIBLE") != null && premiumData.get("DEDUCTIBLEAMOUNT") != null
					&& premiumData.get("DEDUCTIBLE").toLowerCase().equals("y")
					&& !premiumData.get("DEDUCTIBLEAMOUNT").equals("0"))
				deduc = premiumData.get("DEDUCTIBLEAMOUNT").substring(
						premiumData.get("DEDUCTIBLEAMOUNT").lastIndexOf("$") + 1, // lastIndexOf("_BASE") + 6
						premiumData.get("DEDUCTIBLEAMOUNT").length());

			if (premiumData.get("ExceptionPremium") != null)
				expPre = premiumData.get("ExceptionPremium");

			Float finalPre = Float.parseFloat(options) + Float.parseFloat(deduc) + Float.parseFloat(surcharge)
					+ Float.parseFloat(excelPremium) + Float.parseFloat(expPre);

			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String numberAsString = decimalFormat.format(finalPre);
			String finallyd = "$" + numberAsString;
			finalValue = finallyd;
		} catch (Exception e) {
			throw new Exception("not able to parse");
		}
		return finalValue;
	}

	/**
	 * This function is used to lock and view contract post contract search
	 * 
	 */
	public void searchAndLandToNewBusinessFromOnHoldContracts(String contract) throws Exception {
		type("typecontractnumberonHold", contract);
		waitForSomeTime(3);
		for (int i = 0; i < 3; i++) {
			try {
				click("clickonholdcontractbtn");
				break;
			} catch (Exception w) {
				continue;
				//// do nothing
			}
		}
		removeErrorMessages();
		try {
			click("VINDuplicate");
		} catch (Exception e) {
			// TODO: handle exception
		}
		removeErrorMessages();
		contractExpander();
	}

	/**
	 * This function is get AUL premim
	 * 
	 */
	public String premium() throws Exception {
		click("clickPremiumCalculate");
		boolean freezPane = checkTopPaneFreez();
		if (freezPane = true) {
			return getAttributeValue("getPremium", "Name");
		}
		System.out.println(freezPane);
		assertEquals(freezPane, true);
		return null;
	}

	public boolean checkTopPaneFreez() {
		/*
		 * String contractNum = getAttributeValue("typeContractNumber", "Text"); String
		 * contractSta tus = getAttributeValue("getCotractStatusOnUnderwritingPage",
		 * "Name"); if (contractNum != "" && contractStatus != "") { return true; } else
		 * { return false; }
		 */
		String contractNum = getAttributeValue("typeContractNumber", "Value.Text");
		System.out.println("contractNum===" + contractNum);
		String contractStatus = getAttributeValue("getCotractStatusOnUnderwritingPage", "Value.Name");
		System.out.println("contractStatus===" + contractStatus);
		if (contractNum != "" && contractStatus != "") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is get AUL premim OnRecalculation
	 * 
	 */
	@SuppressWarnings("unused")

	public String premiumRecalculation() throws Exception {
		click("clickPremiumCalculate");
		boolean flag = checkIsDisplayed("missingPriceSheetInfoBox");
		if (flag = true) {
			click("clickOK");
			click("selectPricesheet");
			click("selectPricesheetCode");

		}
		boolean freezPane = checkTopPaneFreez();
		if (freezPane = true) {
			return getAttributeValue("getPremium", "Name");
		}

		System.out.println(freezPane);
		assertEquals(freezPane, true);
		return null;

	}
	// public boolean achPost() throws Exception {

	// boolean flag = checkIsDisplayed("missingPriceSheetInfoBox");
	// if (flag = true) {

	// }
	/**
	 * This function is get options
	 * 
	 */
	public String options() throws Exception {
		String surcharge = "0";
		try {
			surcharge = getAttributeValue("getOptions", "Name");
			click("getOptionsCheckBox");
		} catch (Exception e) {
			// TODO: handle exception
		}

		return surcharge;
	}

	//// This function validate SecondaryAccountforDealerLender and return boolean
	//// value
	public boolean validateSecondaryAccountforDealerLender() {
		// validatePrimaryAccountforDealerLender
		specialclickComboBox("secondaryTypeComboBox");
		List<WebElement> webElementList = listOfElements("secondaryTypeListValue");
		if (webElementList.size() > 0) {
			for (WebElement webElement : webElementList) {
				if ((webElement.getText().equalsIgnoreCase("Agent"))) {
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	/**
	 * This function is used to get deductibles
	 * 
	 */
	public String deductibles() throws Exception {
		String surcharge = "0";
		clickComboBox("selectDeductibleConboBox");
		surcharge = getValue("getDeductibles", 0);
		click("getDeductibles", 0);
		return surcharge;
	}

	/**
	 * This function is used to get the remittance name from Remittance screen
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 */
	public String getRemittanceName() throws Exception {
		click("remitName");
		return getValue("remitName");
	}

	/**
	 * This function is used to return searched data in map, to be verified from
	 * search result grid
	 *
	 * @return
	 *
	 */
	public HashMap<String, String> returnAccountStatementGridData(int i) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		//// save Agent ID
		searchData.put("Agent ID", getValue("listOfAgentIDOnAccStmt", i).trim());
		//// save Status
		searchData.put("Status", getValue("listOfStatusOnAccStmt", i).trim());
		//// save Role Type
		searchData.put("Role Type", getValue("listOfRoleTypeOnAccStmt", i).trim());
		//// save Role ID
		searchData.put("Role ID", getValue("listOfRoleIDOnAccStmt", i).trim());
		//// save Account Name
		searchData.put("Account Name", getValue("listOfAccNameOnAccStmt", i).trim());
		//// save EOM Posted DBCR
		searchData.put("EOM Posted DBCR", getValue("listOfEOMDBCROnAccStmt", i).trim());
		//// save Prior1 EOM
		searchData.put("Prior1 EOM", getValue("listOfPrior1OnAccStmt", i).trim());
		//// save Prior2 EOM
		searchData.put("Prior2 EOM", getValue("listOfPrior2OnAccStmt", i).trim());
		//// save Posted DBCR
		searchData.put("Posted DBCR", getValue("listOfPostedDBCROnAccStmt", i).trim());
		//// save Onhold DBCR
		searchData.put("Onhold DBCR", getValue("listOfOnholdOnAccStmt", i).trim());
		return searchData;
	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> enterMandatoryValuesOnContract(HashMap<String, String> premiumData)
			throws Exception {
		contractScrollToTop();
		click("clearContractData");
		String togglestate = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		if (togglestate.equals("1"))
			click("vinNumberOverride");
		HashMap<String, String> ss = new HashMap<String, String>();
		// click("clearContractData");
		//// type unique contract number
		String contractNumber = randomString(10);
		try {
			type("typeContractNumber", contractNumber);
		} catch (Exception e) {
			click("scrollContractsListUp");
			type("typeContractNumber", contractNumber);
		}
		ss.put("ContractNumber", contractNumber);
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		//// enter purchase date of contract, -10 days from today's date
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
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));

		click("vinNumberOverride");
		type("vinNumberMake", premiumData.get("MAKE"));
		type("vinNumberModel", premiumData.get("MODEL"));
		type("vinNumberYear", premiumData.get("YEAR"));

		type("vinNumberMileage", premiumData.get("MILEAGE"));
		type("vinNumberPrice", premiumData.get("VEHICLEPRICE"));
		//// Enter customer information
		type("customerFNAME", "Automation");
		type("customerLNAME", "Testing");
		click("scrollContractsListDown");
		type("customerADD", "Baker Street");
		type("customerZip", "12345");
		type("customerEmail", "niit@gmail.com");
		click("scrollContractsListDown");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
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
		waitForSomeTime(5);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		//// Handling for Class
		if (premiumData.get("CLASS") != null) {
			type("getClass", premiumData.get("CLASS"));
		} else {
			String classs = getAttributeValue("getClass", "Value.Value");
			ss.put("CLASS", classs);
		}
		waitForSomeTime(5);
		//// Term for Price sheet handling
		specialclickComboBox("selectPricesheetTerm");
		HashSet<String> termValues = new HashSet<String>();
		termValues.addAll(specialGetAllValuesSaveInSet("getTermValues"));
		if (premiumData.get("TERM") != null) {
			if (termValues.contains(premiumData.get("TERM")))
				type("selectPricesheetTerm", premiumData.get("TERM"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : termValues) {
				if (premiumData.get("MissTerm") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetTerm", string);
							ss.put("TERM", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissTerm").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetTerm", string);
								ss.put("TERM", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");
		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (coverageValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				if (premiumData.get("MissCoverage") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetCoverage", string);
							ss.put("COVERAGE", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissCoverage").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetCoverage", string);
								ss.put("COVERAGE", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		premiumData.putAll(ss);
		HashMap<String, String> ssss = getExceptionPremium(premiumData);
		ss.putAll(ssss);
		System.out.println("compleetd");
		return ss;
	}

	public HashMap<String, String> changeDealerorLender(HashMap<String, String> premiumData) throws Exception {
		contractScrollToTop();
		HashMap<String, String> ss = new HashMap<String, String>();
		type("primaryAccountType", premiumData.get("PrimaryAccount"));
		type("primaryAccountId", premiumData.get("DEALERID"));
		click("primaryAccountSearchButton");
		//// Enter Secondary Account Details
		type("secondaryAccountType", premiumData.get("SecondaryAccount"));
		type("secondaryAccountId", premiumData.get("SecondaryAccountId"));
		click("secondaryAccountSearchButton");
		takeScreenshot();
		click("scrollContractsListDown");
		waitForSomeTime(5);
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
		waitForSomeTime(5);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		//// Handling for Class
		if (premiumData.get("CLASS") != null) {
			type("getClass", premiumData.get("CLASS"));
		} else {
			String classs = getAttributeValue("getClass", "Value.Value");
			ss.put("CLASS", classs);
		}
		waitForSomeTime(5);
		//// Term for Price sheet handling
		specialclickComboBox("selectPricesheetTerm");
		HashSet<String> termValues = new HashSet<String>();
		termValues.addAll(specialGetAllValuesSaveInSet("getTermValues"));
		if (premiumData.get("TERM") != null) {
			if (termValues.contains(premiumData.get("TERM")))
				type("selectPricesheetTerm", premiumData.get("TERM"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : termValues) {
				if (premiumData.get("MissTerm") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetTerm", string);
							ss.put("TERM", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissTerm").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetTerm", string);
								ss.put("TERM", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");
		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (coverageValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				if (premiumData.get("MissCoverage") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetCoverage", string);
							ss.put("COVERAGE", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissCoverage").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetCoverage", string);
								ss.put("COVERAGE", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		premiumData.putAll(ss);
		HashMap<String, String> ssss = getExceptionPremium(premiumData);
		ss.putAll(ssss);
		System.out.println("compleetd");
		return ss;
	}

	/**
	 * This function is used to open all necessary windows required for remittance
	 * creation
	 * 
	 */
	public void landToCreateRemittanceDetailsPage() throws Exception {
		//// open remittance expander
		remittanceExpander();
		//// click view to open folder explorer
		click("viewInToolbar");
		//// click Folder explorer to upload files
		click("folderExplorer");
		waitForSomeTime(4);

	}

	/**
	 * This function is used to open all necessary windows required for remittance
	 * creation
	 * 
	 */
	public HashMap<String, String> myData(String name) throws Exception {
		HashMap<String, String> dd = new HashMap<String, String>();
		waitForSomeTime(5);
		dd.put("RemittanceName", getValue("remitName"));
		dd.put("corecount", getValue("remitCore"));
		// dd.put("uwcount", getValue("remitUnderW"));
		dd.put("lwacount", getValue("remitLWA"));
		dd.put("Source_Type", getValue("remitSource"));
		dd.put("Subtype_Name", getValue("remitSubType"));
		dd.put("name", getValue("remitType"));
		// dd.put("Paper", getValue("UVremitType"));
		return dd;
	}

	/**
	 * This function is used to delete remittance
	 * 
	 */
	public void deleteMyRemittance() throws Exception {

		click("remitName");
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT)
				.sendKeys(Keys.ARROW_RIGHT).build().perform();
		waitForSomeTime(2);
		rightClick("remitName");
		click("deleteremittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public HashMap<String, String> enterMandatoryValuesOnContract_18035(HashMap<String, String> premiumData)
			throws Exception {
		contractScrollToTop();
		String togglestate = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		if (togglestate.equals("1"))
			click("vinNumberOverride");
		click("clearContractData");
		HashMap<String, String> ss = new HashMap<String, String>();
		// type unique contract number
		try {
			type("typeContractNumber", randomString(10));
		} catch (Exception e) {
			click("scrollContractsListUp");
			type("typeContractNumber", randomString(10));
		}
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		//// enter purchase date of contract, -10 days from today's date
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
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));

		click("vinNumberOverride");
		type("vinNumberMake", premiumData.get("MAKE"));
		type("vinNumberModel", premiumData.get("MODEL"));
		type("vinNumberYear", premiumData.get("YEAR"));

		type("vinNumberMileage", premiumData.get("MILEAGE"));
		type("vinNumberPrice", premiumData.get("VEHICLEPRICE"));
		//// Enter customer information
		type("customerFNAME", "Automation");
		type("customerLNAME", "Testing");
		click("scrollContractsListDown");
		type("customerADD", "Baker Street");
		type("customerZip", "12345");
		type("customerEmail", "niit@gmail.com");
		type("customerPhone", "9999888888");
		click("scrollContractsListDown");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
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
		boolean matchFlag = false;
		if (checkIsDisplayed("ineligibleVehicleError"))
			matchFlag = true;
		assertEquals(matchFlag, true);

		click("clickOK");

		waitForSomeTime(5);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		//// Handling for Class
		if (premiumData.get("CLASS") != null) {
			type("getClass", premiumData.get("CLASS"));
		} else {
			String classs = getAttributeValue("getClass", "Value.Value");
			ss.put("CLASS", classs);
		}
		waitForSomeTime(5);
		//// Term for Price sheet handling
		specialclickComboBox("selectPricesheetTerm");
		HashSet<String> termValues = new HashSet<String>();
		termValues.addAll(specialGetAllValuesSaveInSet("getTermValues"));
		if (premiumData.get("TERM") != null) {
			if (termValues.contains(premiumData.get("TERM")))
				type("selectPricesheetTerm", premiumData.get("TERM"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : termValues) {
				if (premiumData.get("MissTerm") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetTerm", string);
							ss.put("TERM", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissTerm").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetTerm", string);
								ss.put("TERM", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");
		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (coverageValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				if (premiumData.get("MissCoverage") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetCoverage", string);
							ss.put("COVERAGE", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissCoverage").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetCoverage", string);
								ss.put("COVERAGE", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		premiumData.putAll(ss);
		HashMap<String, String> ssss = getExceptionPremium(premiumData);
		ss.putAll(ssss);
		System.out.println("compleetd");
		return ss;
	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 */
	public void enterMandatoryValuesOnContractPBI_18035(HashMap<String, String> premiumData) throws Exception {
		contractScrollToTop();
		String togglestate = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		if (togglestate.equals("1"))
			click("vinNumberOverride");
		click("clearContractData");
		// type unique contract number
		try {
			type("typeContractNumber", randomString(10));
		} catch (Exception e) {
			click("scrollContractsListUp");
			type("typeContractNumber", randomString(10));
		}
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		//// enter purchase date of contract, -10 days from today's date
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
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));

		click("vinNumberOverride");
		type("vinNumberMake", premiumData.get("MAKE"));
		type("vinNumberModel", premiumData.get("MODEL"));
		type("vinNumberYear", premiumData.get("YEAR"));

		type("vinNumberMileage", premiumData.get("MILEAGE"));
		type("vinNumberPrice", premiumData.get("VEHICLEPRICE"));
		//// Enter customer information
		type("customerFNAME", "Automation");
		type("customerLNAME", "Testing");
		contractScrollToBottom();
		type("customerADD", "Baker Street");
		type("customerZip", "12345");
		type("customerEmail", "niit@gmail.com");
		type("customerPhone", "9999888888");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
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
		waitForSomeTime(5);
	}

	/**
	 * This function is used to receive hashmap which have column and data mapping
	 * and return data and column mapping which have only valid data, will remove *
	 * and Blanks columns and values
	 * 
	 */
	public HashMap<String, String> dataFor18035(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			if (inputArray[i].length() > 0) {
				switch (i) {
				case 0:
					searchData.put("reason1", inputArray[i]);
					break;
				case 1:
					searchData.put("reason2", inputArray[i]);
					break;
				default:
					searchData.put("NoData", inputArray[i]);
					break;
				}

			}
		}
		return searchData;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hashmap with input parameters
	 * 
	 */
	public void inputValues(HashMap<String, String> inputArray) throws Exception {
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : inputArray.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			switch (searchParamater) {
			case "reason1":
				clickOnHoldWithReason(new String[] { valueToInput });
				break;
			case "reason2":
				clickOnHoldWithReason(new String[] { valueToInput });
				break;
			default:
				//// do nothing
			}
		}
	}

	/**
	 * This function is used to drag and drop necessary remittance files
	 * 
	 */
	public void dragAndDropFiles() throws Exception {
		for (int i = 0; i < 3; i++) {
			copyFilesWorkingRemittance();
			//// drag and drop files
			dragAndDrop("filesSourcePath", "filesDestinationPath");
			waitForSomeTime(3);
		}
	}

	/**
	 * This function is used to drag and drop necessary remittance files
	 * 
	 */
	public void getPDFStatus() throws Exception {
		//// drag and drop files
		List<WebElement> we = listOfElements("pdfStatus");
		for (WebElement webElement : we) {
			if (!webElement.getText().toLowerCase().contains(".pdf")) {
				throw new Exception("Contract Files Status didn't matched");
			}
		}
	}

	/**
	 * This function is used to enter mandatory values in create remittance
	 * 
	 */
	public String enterRemittanceMandatoryValues(String coreCount) throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		try {
			remittanceName = randomString(20);
			type("remittanceName", remittanceName);
			//// ENter core count
			type("remittanceCoreCount", coreCount);
			//// select remit type
			typeKeys("remittanceTypeCombobox", "Standard");
			typeKeys("remittanceRemitTypeComboBox", "Paper Remit");
			typeKeys("remittanceSubTypeComboBox", "Dealer AGs");
			//// type comments
			type("remittanceComments", "Testing 123");
			//// click save
			click("clickSaveRemittance");
			click("clickOnYesButton");
			// click("remittanceExpander");
			//// close file explorer
			for (int i = 0; i < 2; i++) {
				try {
					click("closeFolderExplorer");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			//// close remitance
			remittanceExpander();
		} catch (Exception e) {
			remittanceName = "";
		}
		return remittanceName;
	}

	/**
	 * This function is used to enter values in create remittance
	 * 
	 */
	public String enterRemittanceValues(String[] inputArray) throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		try {
			click("clickDelete");
		} catch (Exception ex) {
			//// dp nothing
		}
		try {
			if (inputArray[0].toLowerCase().equals("random")) {
				remittanceName = randomString(20);
			} else {
				remittanceName = inputArray[0];
			}
			type("remittanceName", remittanceName);
			//// Enter core count
			type("remittanceCoreCount", inputArray[1]);
			type("remittanceLWACount", inputArray[2]);
			//// select remit type
			typeKeys("remittanceContractCombobox", inputArray[3]);
			typeKeys("remittanceTypeCombobox", inputArray[4]);
			typeKeys("remittanceRemitTypeComboBox", inputArray[5]);
			typeKeys("remittanceSubTypeComboBox", inputArray[6]);
			//// type comments
			type("remittanceComments", inputArray[7]);
			//// add check here
			if (inputArray[8].length() > 0 && inputArray[9].length() > 0 && inputArray[10] != null
					&& inputArray[11] != null) {
				/// enter check details
				type("addCheckOnRemittance", inputArray[8]);
				type("addCheckAmtOnRemittance", inputArray[9]);
				//// enter dealer details
				type("typeDealerinaddcheck", inputArray[10]);
				click("addDealeraddButton");
				String roleid = inputArray[11];
				if (roleid.equals("*")) {
					roleid = getroleId(inputArray[10]);
				}
				type("addcheckroleid", roleid);
				click("addchecksearchbutton");
				click("addchecksearchtoprow");
				//// add check details
				click("clickAddCheckAmtOnRemittance");
			}
			//// click save
			String clickState = getAttributeValue("clickSaveRemittance", "IsEnabled");
			if (clickState.toLowerCase().equals("true")) {
				click("clickSaveRemittance");
				if (!(inputArray[8].length() > 0 && inputArray[9].length() > 0))
					click("clickOnYesButton");
			} else
				return "";
			// click("remittanceExpander");
			//// close file explorer
			for (int i = 0; i < 2; i++) {
				try {
					click("closeFolderExplorer");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			for (int i = 0; i < 5; i++) {
				try {
					click("remittanceExpander");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			//// close remittance
		} catch (Exception e) {
			remittanceName = "";
		}
		return remittanceName;
	}

	/**
	 * This function is used to enter values in create remittance
	 * 
	 */
	public String enterRemittanceValuesNoSave(String[] inputArray) throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		if (inputArray[0].toLowerCase().equals("random")) {
			remittanceName = randomString(20);
		} else {
			remittanceName = inputArray[0];
		}
		type("remittanceName", remittanceName);
		//// Enter core count
		type("remittanceCoreCount", inputArray[1]);
		type("remittanceLWACount", inputArray[2]);
		//// select remit type
		typeKeys("remittanceContractCombobox", inputArray[3]);
		typeKeys("remittanceTypeCombobox", inputArray[4]);
		typeKeys("remittanceRemitTypeComboBox", inputArray[5]);
		typeKeys("remittanceSubTypeComboBox", inputArray[6]);
		//// type comments
		type("remittanceComments", inputArray[7]);
		//// add check here
		///// first without echeck
		click("clickSaveRemittance");
		String errorMessage = getValue("addCheckError");
		if (errorMessage.equalsIgnoreCase(
				"You are creating a Remittance with no Check Information entered, do you want to proceed?")) {
			click("clickOnNoButton");
		} else {
			throw new Exception("No warning message shown to add check");
		}
		/// enter check details
		type("addCheckOnRemittance", inputArray[8]);
		type("addCheckAmtOnRemittance", inputArray[9]);
		//// enter dealer details
		type("typeDealerinaddcheck", "Dealer");
		click("addDealeraddButton");
		type("addcheckroleid", "140");
		click("addchecksearchbutton");
		click("addchecksearchtoprow");
		//// add check details
		click("clickAddCheckAmtOnRemittance");

		return remittanceName;
	}

	/**
	 * This function is used to enter values in create remittance
	 * 
	 */
	public void enterRemittanceValueswithoutSave(String[] inputArray) throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		if (inputArray[0].toLowerCase().equals("random")) {
			remittanceName = randomString(20);
		} else {
			remittanceName = inputArray[0];
		}
		type("remittanceName", remittanceName);
		//// Enter core count
		type("remittanceCoreCount", inputArray[1]);
		type("remittanceLWACount", inputArray[2]);
		//// select remit type
		typeKeys("remittanceContractCombobox", inputArray[3]);
		typeKeys("remittanceTypeCombobox", inputArray[4]);
		typeKeys("remittanceRemitTypeComboBox", inputArray[5]);
		typeKeys("remittanceSubTypeComboBox", inputArray[6]);
		//// type comments
		type("remittanceComments", inputArray[7]);
	}

	/**
	 * This function is used to enter check details
	 * 
	 */
	public void addCheckDetails(String checkNumber, String checkAmount) throws Exception {
		//// Type Remittance Name
		type("addCheckOnRemittance", checkNumber);
		type("addCheckAmtOnRemittance", checkAmount);
		click("clickAddCheckAmtOnRemittance");
	}

	/**
	 * This function is used to enter check details
	 * 
	 */
	public void deleteCheckDetailsAndVerify(String checkNumber) throws Exception {
		//// Type Remittance Name
		String chkNumber = getValue("clickAddedCheck");
		if (checkNumber.toLowerCase().equals(chkNumber.toLowerCase())) {
			click("clickAddedCheck");
			click("deleteCheck");
			String chkNumberAfterDelete = "";
			try {
				chkNumberAfterDelete = getValue("clickAddedCheck");
			} catch (Exception e) {
				System.out.println("dsf");
				// throw new Exception("Check didn't deleted");
			}
			if (chkNumberAfterDelete.length() > 1)
				throw new Exception("Check didn't deleted");
		} else {
			throw new Exception("Check details didn't matched");
		}
	}

	/**
	 * This function is used to enter values in create remittance
	 * 
	 */
	public String[] getRemittanceValueswithoutSave() throws Exception {
		String[] dasd = { getValue("remittanceName"), getValue("remittanceCoreCount"), getValue("remittanceLWACount"),
				getValue("remittanceComments") };
		return dasd;
	}

	/**
	 * This function is used to view PDF, View PDF in new window and delete PDF
	 * 
	 */
	public void viewAndDeletePDF() throws Exception {
		expandRemiitence();
		click("clickOnPDF");

		String popupValue = getAttributeValue("clickOnOkForUnhandleException", "Name");
		if (popupValue.equals("UnHandled Exception")) {
			click("clickOK");
		}

		click("clickOnSelectAllForPDF");
		click("clickOnZoomOut");
		click("clickOnZoomIn");
		click("closePDF");
		waitForSomeTime(5);
		rightClick("clickOnPDF");
		click("clickOnDeletePdf");
		click("clickOK");
		waitForSomeTime(5);
		click("expandRemittance");

	}

	/**
	 * This function is used to view contract by clicking on the view contract icon
	 * before remittance posting
	 * 
	 */
	public void viewContractBeforeContractPosting(String remittName) throws Exception {
		// // Type RemittanceName
		type("typeToSearchRemittance", remittName);
		// // expand remittance to get contracts
		click("expandRemittance");
		click("viewContract", 1);
		contractExpander();
	}

	/**
	 * This function is used to upload the pdf using draganddrop to add the file
	 * 
	 */
	public void uploadPdfByDragAndDrop() throws Exception {
		click("expandRemittance");
		// dragAndDropFiles();
		//// check if pdf is successfully uploaded
		waitForSomeTime(2);
		click("saveRemittance");
		click("expandRemittance");
	}

	/**
	 * This function is used to apply filter on documents like contract, check
	 * ,remit,miscellaneous,web contract etc
	 * 
	 */
	public boolean applyFilterOnDocument(String remittName) throws Exception {
		searchRemittance(remittName);
		expandRemiitence();
		boolean flag = false;
		type("clickOnDocumentTYpeForFilter", "Contract");
		try {
			if (getValue("getValuefromFilters").toLowerCase().equals("contract")) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			throw new Exception("Filter on contract is failed");
		}
		type("clickOnDocumentTYpeForFilter", "Check");
		try {
			if (getValue("getValuefromFilters").toLowerCase().equals("check")) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			throw new Exception("Filter on check is failed");
		}
		return flag;
	}

	/**
	 * This function is used to get all data fields for remittance information under
	 * remittance list
	 * 
	 */
	public HashMap<String, String> getAllDataUnderRemittanceList() {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("PostPeriod", getValue("getPostPeriodValue"));
		dataMap.put("RemittanceNumber", getValue("remitNumber"));
		dataMap.put("RemittanceName", getValue("remitName"));
		dataMap.put("UnderwritingCount", getValue("remitUWCount"));
		// dataMap.put("HoldCount", getValue("remitHolds"));
		dataMap.put("Source_Type", getValue("remitSource"));
		dataMap.put("Subtype_Name", getValue("remitSubType"));
		dataMap.put("name", getValue("remitType"));
		// dataMap.put("RemitType", getValue("UVremitType"));
		dataMap.put("corecount", getValue("remitCore"));
		dataMap.put("lwacount", getValue("remitLWA"));
		// click("swipeRight", 3);
		dataMap.put("comments", getValue("getCommentsOnRemittanceScreen"));
		dataMap.put("CreateByDate", getValue("getCreatedDate"));
		// dataMap.put("Locked_by", getValue("getLockedByValue"));
		// dataMap.put("CreateByUser", getValue("getCreatedByOnRemittanceScreen"));
		// click("swipeLeft", 3);
		return dataMap;
	}

	/**
	 * This function is used to get all data fields for remittance information under
	 * remittance list
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getContractDataFromRemittance() {
		HashMap<Integer, HashMap<String, String>> data = new HashMap<Integer, HashMap<String, String>>();
		List<WebElement> file = listOfElements("getFileName");
		List<WebElement> document = listOfElements("getDocumentType");
		List<WebElement> status = listOfElements("getDocumentStatus");
		int i = 0;
		for (WebElement webElement : file) {
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("file_name", webElement.getText());
			data.put(i, temp);
			i++;
		}
		i = 0;
		for (WebElement webElement : status) {
			HashMap<String, String> temp = data.get(i);
			String statuss = webElement.getText();
			if (statuss.toLowerCase().equals("n/a"))
				statuss = null;
			temp.put("status", statuss);
			data.put(i, temp);
			i++;
		}
		i = 0;
		for (WebElement webElement : document) {
			HashMap<String, String> temp = data.get(i);
			temp.put("documenttype", webElement.getText());
			data.put(i, temp);
			i++;
		}
		return data;
	}

	///// This function verify contract status by contract number and return boolean
	///// value
	public boolean searchContractStatus(Set<String> ContractNumber) {
		Iterator<String> itr = ContractNumber.iterator();
		// traversing over HashSet
		while (itr.hasNext()) {
			//// click on contract text
			click("contractsText");
			String contractName = itr.toString();
			type("contractsText", contractName);
			click("clickSearch");
			String Statustxt = getTextOfElement("statusText");
			if (!(Statustxt.equalsIgnoreCase("OnHold"))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form for lender
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> enterMandatoryValuesOnContractForLender(HashMap<String, String> premiumData)
			throws Exception {
		contractScrollToTop();
		String togglestate = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		if (togglestate.equals("1"))
			click("vinNumberOverride");

		HashMap<String, String> ss = new HashMap<String, String>();
		click("clearContractData");
		//// type unique contract number
		try {
			type("typeContractNumber", randomString(10));
		} catch (Exception e) {
			click("scrollContractsListUp");
			type("typeContractNumber", randomString(10));
		}
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		//// enter purchase date of contract, -10 days from today's date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(1);
		type("purchaseDateForNewContract", dtf.format(localDate).toString());
		//// Enter Primary Account Details
		type("primaryAccountType", premiumData.get("PrimaryAccount"));
		type("primaryAccountId", premiumData.get("LENDERID"));
		click("primaryAccountSearchButton");
		//// Enter Secondary Account Details
		type("secondaryAccountType", premiumData.get("SecondaryAccount"));
		type("secondaryAccountId", premiumData.get("SecondaryAccountId"));
		click("secondaryAccountSearchButton");
		//// Enter VIN Details
		String dataaa = EnterVinDetails(premiumData.get("VIN"));
		if (dataaa.equalsIgnoreCase("Invalid Vin")) {
			String contractSummary = "0";
			try {
				contractSummary = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
			} catch (Exception e) {
				takeScreenshot();
				// TODO: handle exception
			}
			if (contractSummary.toLowerCase().equals("0"))
				click("vinNumberOverride");
			type("vinNumberMake", premiumData.get("MAKE"));
			type("vinNumberModel", premiumData.get("MODEL"));
			type("vinNumberYear", premiumData.get("YEAR"));
		}
		type("vinNumberMileage", premiumData.get("MILEAGE"));
		type("vinNumberPrice", premiumData.get("VEHICLEPRICE"));
		//// Enter customer information
		type("customerFNAME", "Automation");
		type("customerLNAME", "Testing");
		click("scrollContractsListDown");
		type("customerADD", "Baker Street");
		type("customerZip", "12345");
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
		click("scrollContractsListDown");
		waitForSomeTime(5);
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
		waitForSomeTime(5);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		//// Handling for Class
		if (premiumData.get("CLASS") != null) {
			type("getClass", premiumData.get("CLASS"));
		} else {
			String classs = getAttributeValue("getClass", "Value.Value");
			ss.put("CLASS", classs);
		}
		waitForSomeTime(5);
		//// Term for Price sheet handling
		specialclickComboBox("selectPricesheetTerm");
		HashSet<String> termValues = new HashSet<String>();
		termValues.addAll(specialGetAllValuesSaveInSet("getTermValues"));
		if (premiumData.get("TERM") != null) {
			if (termValues.contains(premiumData.get("TERM")))
				type("selectPricesheetTerm", premiumData.get("TERM"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : termValues) {
				try {
					if (string.length() > 0) {
						type("selectPricesheetTerm", string);
						ss.put("TERM", string);
						break;
					} else {
						throw new Exception("no data found");
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");

		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (termValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				try {
					if (string.length() > 0) {
						type("selectPricesheetCoverage", string);
						ss.put("COVERAGE", string);
						break;
					} else {
						throw new Exception("no data found");
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		waitForSomeTime(5);
		premiumData.putAll(ss);
		HashMap<String, String> ssss = getExceptionPremium(premiumData);
		ss.putAll(ssss);
		System.out.println("compleetd");
		return ss;
	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 * @return
	 * 
	 */
	public String enterMandatoryValuesOnContractAndCheckForPriceSheetForLender(HashMap<String, String> premiumData)
			throws Exception {
		contractScrollToTop();
		String togglestate = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		if (togglestate.equals("1"))
			click("vinNumberOverride");

		click("clearContractData");
		//// type unique contract number
		try {
			type("typeContractNumber", randomString(10));
		} catch (Exception e) {
			click("scrollContractsListUp");
			type("typeContractNumber", randomString(10));
		}
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		type("purchaseDateForNewContract", premiumData.get("SaleDate"));
		//// Enter Primary Account Details
		type("primaryAccountType", premiumData.get("PrimaryAccount"));
		type("primaryAccountId", premiumData.get("LENDERID"));
		click("primaryAccountSearchButton");
		click("scrollContractsListDown");
		type("selectPricesheet", premiumData.get("PRICESHEETINTERNALCODE"));
		String text = getAttributeValue("selectPricesheet", "Value.Value");
		if (text.contains(premiumData.get("PRICESHEETINTERNALNAME")))
			return "Hurray Data Exists";
		else
			return "No Data Exists";
	}

	//// This function validate PrimaryAccountforDealerLender and return boolean
	//// value
	public boolean validatePrimaryAccountforDealerLender() {
		// validatePrimaryAccountforDealerLender
		specialclickComboBox("primaryTypeComboBox");
		List<WebElement> webElementList = listOfElements("primaryTypeListValue");
		if (webElementList.size() > 0) {
			for (WebElement webElement : webElementList) {
				if ((webElement.getText().equalsIgnoreCase("Agent"))) {
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	/**
	 * This function is used enter mandatory fields in account search
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> processForAccountSearch(String remittName, String fileName) throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		searchContractwithPendingState(remittName, fileName);
		// lock contract on user name and open enter values in contract window
		lockAndViewContract();
		click("clearContractData");
		// type unique contract number
		type("typeContractNumber", randomString(10));
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		// enter purchase date of contract, -10 days from today's date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(10);
		type("purchaseDateForNewContract", dtf.format(localDate).toString());
		// insert RoleType and RoleID
		data.put("Dealer", getRoleIdForContractDataEntry("Dealer"));
		data.put("Lender", getRoleIdForContractDataEntry("Lender"));
		type("primaryAccountType", "Dealer");
		type("primaryAccountId", data.get("Dealer"));
		type("secondaryAccountType", "Lender");
		type("secondaryAccountId", data.get("Lender"));
		click("primaryAccountSearchButton");
		click("secondaryAccountSearchButton");
		try {
			click("clickOK");
		} catch (Exception e) {
			// Do Nothing
		}
		return data;
	}

	/**
	 * This function is used enter mandatory fields in account search
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> processForAccountSearchCotractAdjustment() throws Exception {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("Dealer", getRoleIdForContractDataEntry("Dealer"));
		data.put("Lender", getRoleIdForContractDataEntry("Lender"));
		type("primaryAccountType", "Dealer");
		type("primaryAccountId", data.get("Dealer"));
		type("secondaryAccountType", "Lender");
		type("secondaryAccountId", data.get("Lender"));
		click("primaryAccountSearchButton");
		try {
			click("contractInfoPopUp");
			click("closeAccountPopUp");
		} catch (Exception e) {
			// do nothing
		}
		click("secondaryAccountSearchButton");
		try {
			click("contractInfoPopUp");
			click("closeAccountPopUp");
		} catch (Exception e) {
			// do nothing
		}
		return data;
	}

	/**
	 * This function is used enter mandatory fields in account search
	 * 
	 * @throws Exception
	 */
	public boolean verifySearchEnableDisable(HashMap<String, String> data) throws Exception {
		String primaryRoleIndetifier = getTextOfElement("primaryAccountId");
		String SecondaryaryRoleIndetifier = getTextOfElement("secondaryAccountId");
		if (primaryRoleIndetifier.equals(data.get("Dealer")) && SecondaryaryRoleIndetifier.equals(data.get("Lender"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This function is used to verify primary and secondary account address detail
	 * with DB.
	 * 
	 * @throws Exception
	 */
	public boolean verifyAddressDetailsInContractDataEntry(HashMap<String, String> data) throws Exception {
		// Verify UIAddress detail with DB
		String roleID = data.get("Dealer");
		HashMap<String, String> dbPrimaryAccountPhysicalAddress = getDBRemittancePhysicalAddressDetail("Dealer",
				roleID);
		HashMap<String, String> uiPrimaryAccountAddressDetail = new HashMap<String, String>();
		uiPrimaryAccountAddressDetail.put("name", getValue("primaryAccountName"));
		uiPrimaryAccountAddressDetail.put("STREET", getValue("primaryAccountAddressUW"));
		uiPrimaryAccountAddressDetail.put("CITY", getValue("primaryAccountCityUW"));
		uiPrimaryAccountAddressDetail.put("STATE", getValue("primaryAccountStateUW"));
		uiPrimaryAccountAddressDetail.put("ZIP_CODE", getValue("primaryAccountZipcodeUW").trim());
		HashMap<String, String> dbSecondaryAccountPhysicalAddress = getDBRemittancePhysicalAddressDetail("Lender",
				data.get("Lender"));
		HashMap<String, String> uiSecondaryAccountAddressDetail = new HashMap<String, String>();
		uiSecondaryAccountAddressDetail.put("name", getValue("secondaryAccountName"));
		uiSecondaryAccountAddressDetail.put("STREET", getValue("secondaryAccountAddress"));
		uiSecondaryAccountAddressDetail.put("CITY", getValue("secondaryAccountCity"));
		uiSecondaryAccountAddressDetail.put("STATE", getValue("secondaryAccountState").trim());
		uiSecondaryAccountAddressDetail.put("ZIP_CODE", getValue("secondaryAccountZipcode").trim());
		if (uiPrimaryAccountAddressDetail.equals(dbPrimaryAccountPhysicalAddress)
				&& uiSecondaryAccountAddressDetail.equals(dbSecondaryAccountPhysicalAddress)) {
			return true;
		} else
			return false;
	}

	/**
	 * This function is used verify primary account address detail on contract data
	 * entry and account pop up with DB.
	 * 
	 * @throws Exception
	 */
	public boolean verifyUIAddressDetailsOfDealerOnAccoountsPopUp(HashMap<String, String> data) throws Exception {
		// Verify UIAddress detail with DB
		click("primaryAccountSelectButton");
		String roleID = data.get("Dealer");
		type("roleIDOnAccountSelect", roleID);
		click("searchButtonOnAccountSelect");
		HashMap<String, String> uiPrimaryAccountAddressDetailOnAccountPopUp = new HashMap<String, String>();
		uiPrimaryAccountAddressDetailOnAccountPopUp.put("name", getValue("accountNameOnAccountPopUp"));
		uiPrimaryAccountAddressDetailOnAccountPopUp.put("STREET", getValue("addressOnAccountPopUp"));
		uiPrimaryAccountAddressDetailOnAccountPopUp.put("CITY", getValue("cityOnAccountPopUp"));
		uiPrimaryAccountAddressDetailOnAccountPopUp.put("STATE", getValue("stateOnAccountPopUp"));
		uiPrimaryAccountAddressDetailOnAccountPopUp.put("ZIP_CODE", getValue("zipCodeOnAccountPopUp").trim());
		HashMap<String, String> dbPrimaryAccountPhysicalAddress = getDBRemittancePhysicalAddressDetail("Dealer",
				roleID);
		System.out.println(uiPrimaryAccountAddressDetailOnAccountPopUp);
		System.out.println(dbPrimaryAccountPhysicalAddress);
		click("selectButtonOnAccountSelect");
		try {
			click("contractInfoPopUp");
			click("closeAccountPopUp");
		} catch (Exception e) {
			// do nothing
		}
		HashMap<String, String> uiPrimaryAccountAddressDetail = new HashMap<String, String>();
		uiPrimaryAccountAddressDetail.put("name", getValue("primaryAccountName"));
		waitForSomeTime(5);
		uiPrimaryAccountAddressDetail.put("STREET", getValue("primaryAccountAddressUW"));
		uiPrimaryAccountAddressDetail.put("CITY", getValue("primaryAccountCityUW"));
		waitForSomeTime(5);
		uiPrimaryAccountAddressDetail.put("STATE", getValue("primaryAccountStateUW"));
		uiPrimaryAccountAddressDetail.put("ZIP_CODE", getValue("primaryAccountZipcodeUW").trim());
		System.out.println(uiPrimaryAccountAddressDetail);
		if (uiPrimaryAccountAddressDetailOnAccountPopUp.equals(dbPrimaryAccountPhysicalAddress)
				&& uiPrimaryAccountAddressDetailOnAccountPopUp.equals(uiPrimaryAccountAddressDetail)) {
			return true;
		} else
			return false;
	}

	/**
	 * This function is used verify secondary account address detail on contract
	 * data entry and account pop up with DB.
	 * 
	 * @throws Exception
	 */
	public boolean verifyUIAddressDetailsOfLenderOnAccoountsPopUp(HashMap<String, String> data) throws Exception {
		// Verify UIAddress detail with DB
		waitForSomeTime(5);
		click("secondaryAccountSelectButton");
		type("roleIDOnAccountSelect", data.get("Lender"));
		click("searchButtonOnAccountSelect");
		HashMap<String, String> uiSecondaryAccountAddressDetailOnAccountPopUp = new HashMap<String, String>();
		uiSecondaryAccountAddressDetailOnAccountPopUp.put("name", getValue("accountNameOnAccountPopUp"));
		uiSecondaryAccountAddressDetailOnAccountPopUp.put("STREET", getValue("addressOnAccountPopUp"));
		uiSecondaryAccountAddressDetailOnAccountPopUp.put("CITY", getValue("cityOnAccountPopUp"));
		uiSecondaryAccountAddressDetailOnAccountPopUp.put("STATE", getValue("stateOnAccountPopUp"));
		uiSecondaryAccountAddressDetailOnAccountPopUp.put("ZIP_CODE", getValue("zipCodeOnAccountPopUp").trim());
		HashMap<String, String> dbSecondaryAccountPhysicalAddress = getDBRemittancePhysicalAddressDetail("Lender",
				data.get("Lender"));
		click("selectButtonOnAccountSelect");
		try {
			click("contractInfoPopUp");
			click("closeAccountPopUp");
		} catch (Exception e) {
			// do nothing
		}
		HashMap<String, String> uiSecondaryAccountAddressDetail = new HashMap<String, String>();
		uiSecondaryAccountAddressDetail.put("name", getValue("secondaryAccountName"));
		uiSecondaryAccountAddressDetail.put("STREET", getValue("secondaryAccountAddress"));
		uiSecondaryAccountAddressDetail.put("CITY", getValue("secondaryAccountCity"));
		uiSecondaryAccountAddressDetail.put("STATE", getValue("secondaryAccountState").trim());
		waitForSomeTime(5);
		uiSecondaryAccountAddressDetail.put("ZIP_CODE", getValue("secondaryAccountZipcode").trim());

		System.out.println(uiSecondaryAccountAddressDetail);
		System.out.println(dbSecondaryAccountPhysicalAddress);
		System.out.println(uiSecondaryAccountAddressDetailOnAccountPopUp);

		if (uiSecondaryAccountAddressDetailOnAccountPopUp.equals(dbSecondaryAccountPhysicalAddress)
				&& uiSecondaryAccountAddressDetailOnAccountPopUp.equals(uiSecondaryAccountAddressDetail)) {
			return true;
		} else
			return false;
	}

	/**
	 * This function is used to validate VIN Number Availability and return boolean
	 * value
	 * 
	 */
	public boolean validateVinNumberAvailability() {
		// Passing Static VIN number
		type("vinNumber", "1HGEJ8145WL012057");
		click("vinNumberSearch");
		click("vinSearchButton");
		waitForSomeTime(5);
		String vinNumberText = getValue("vinNumberSearchAvailability");
		takeScreenshot();
		if (vinNumberText.isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * This function is used to return vin details data in a hashmap
	 * 
	 * 
	 */
	public HashMap<String, String> getVINDetails() throws Exception {
		HashMap<String, String> VINData = new HashMap<String, String>();
		VINData.put("VIN", getTextOfElement("vinNumber"));
		VINData.put("MAKE", getTextOfElement("vinNumberMake"));
		VINData.put("MODEL", getTextOfElement("vinNumberModel"));
		VINData.put("YEAR", getTextOfElement("vinNumberYear"));
		VINData.put("MILEAGE", getTextOfElement("vinNumberMileage"));
		String price = getTextOfElement("vinNumberPrice");
		price = price.substring(0, price.indexOf('.'));
		VINData.put("VEHICLEPRICE", price);
		System.out.print(VINData);
		return VINData;
	}

	/**
	 * This function is used to return searched data in map, to be verified from
	 * search result grid for onhold Contract
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnSearchResult(int i) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		//// save Remittance_Number
		searchData.put("Remittance_Number", getValue("listOfRemittenceNumber", i).trim());
		/// return Search Result for single row
		return searchData;
	}

	/**
	 * This function is used to delete co owner info in Map
	 */
	public HashMap<String, String> deleteOwnerContactInfoInMap() {

		HashMap<String, String> coOwnerDeletedInfo = new HashMap<String, String>();
		coOwnerDeletedInfo.put("ownerFNAME", "");
		coOwnerDeletedInfo.put("ownerLNAME", "");
		coOwnerDeletedInfo.put("ownerADD", "");
		coOwnerDeletedInfo.put("ownerEmail", "");
		coOwnerDeletedInfo.put("ownerPhone", "");
		coOwnerDeletedInfo.put("ownerZip", "");
		return coOwnerDeletedInfo;
	}

	/**
	 * This function is used to enter owner info
	 * 
	 * @throws AWTException
	 */
	public void enterOwnerContactInfo(HashMap<String, String> enterOrDeleteInfo1) throws AWTException {
		/*
		 * try { click("scrollContractsListDown"); } catch (Exception e) { // TODO:
		 * handle exception }
		 */
		removeErrorMessages();
		type("ownerFNAME", enterOrDeleteInfo1.get("ownerFNAME"));
		type("ownerLNAME", enterOrDeleteInfo1.get("ownerLNAME"));
		type("ownerEmail", enterOrDeleteInfo1.get("ownerEmail"));
		click("ownerPhone");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_HOME);
		robot.keyRelease(KeyEvent.VK_HOME);
		type("ownerPhone", enterOrDeleteInfo1.get("ownerPhone"));
		type("ownerADD", enterOrDeleteInfo1.get("ownerADD"));
		type("ownerZip", enterOrDeleteInfo1.get("ownerZip"));
	}

	/**
	 * This function is used to enter owner info
	 */
	public void enterOwnerContactInfoWithoutEmail(HashMap<String, String> enterOrDeleteInfo1) {
		/*
		 * try { click("scrollContractsListDown"); } catch (Exception e) { // TODO:
		 * handle exception }
		 */
		removeErrorMessages();
		clearTextBox("ownerFNAME");
		clearTextBox("ownerLNAME");
		clearTextBox("ownerADD");
		clearTextBox("ownerEmail");
		clearTextBox("ownerPhone");
		clearTextBox("ownerZip");
		type("ownerFNAME", enterOrDeleteInfo1.get("ownerFNAME"));
		type("ownerLNAME", enterOrDeleteInfo1.get("ownerLNAME"));
		type("ownerADD", enterOrDeleteInfo1.get("ownerADD"));
		type("ownerPhone", enterOrDeleteInfo1.get("ownerPhone"));
		// type("ownerZip", enterOrDeleteInfo1.get("ownerZip"));
	}

	/**
	 * This function is used to store owner info in Map
	 */
	public HashMap<String, String> enterOwnerContactInfoInMap() {
		HashMap<String, String> ownerStoredInfo = new HashMap<String, String>();
		ownerStoredInfo.put("ownerFNAME", "First");
		ownerStoredInfo.put("ownerLNAME", "Owner");
		ownerStoredInfo.put("ownerADD", "NIIT");
		ownerStoredInfo.put("ownerEmail", "Abc123@gmail.com");
		ownerStoredInfo.put("ownerPhone", "9876543218");
		ownerStoredInfo.put("ownerZip", "98201");
		return ownerStoredInfo;
	}

	/**
	 * This function is used to store owner info without Email and Phone in Map
	 */
	public HashMap<String, String> enterOwnerInfoWithoutPhoneAndEmailInMap() {

		HashMap<String, String> ownerStoredInfo = new HashMap<String, String>();
		ownerStoredInfo.put("ownerFNAME", "First");
		ownerStoredInfo.put("ownerLNAME", "Owner");
		ownerStoredInfo.put("ownerADD", "NIIT");
		// ownerStoredInfo.put("ownerZip", "98201");
		ownerStoredInfo.put("ownerPhone", "");
		ownerStoredInfo.put("coOwnerEmail", "");
		waitForSomeTime(3);
		ownerStoredInfo.put("ownerZip", getValue("primaryAccountZipcode").trim());
		return ownerStoredInfo;
	}

	/**
	 * This function is used to fetch owner info from UI
	 */
	public HashMap<String, String> fetchOwnerContactInfofromUI() {

		HashMap<String, String> ownerUIInfo = new HashMap<String, String>();
		ownerUIInfo.put("ownerFNAME", getTextOfElement("ownerFNAME"));
		ownerUIInfo.put("ownerLNAME", getTextOfElement("ownerLNAME"));
		ownerUIInfo.put("ownerADD", getTextOfElement("ownerADD"));
		ownerUIInfo.put("ownerEmail", getTextOfElement("ownerEmail"));
		ownerUIInfo.put("ownerPhone", getTextOfElement("ownerPhone"));
		// ownerUIInfo.put("ownerZip", getTextOfElement("ownerZip"));
		return ownerUIInfo;
	}

	/**
	 * This function is used to owner info
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public boolean verifyUIDataWithDBOwner(String contractId, HashMap<String, String> mapFunction1,
			HashMap<String, String> mapFunction2) throws Exception {
		enterOwnerContactInfo(enterOwnerContactInfoInMap());
		saveFieldsAndReopenOwnerInfoPage(contractId);
		// very now with DB after entering data
		waitForSomeTime(5);
		fetchOwnerContactInfofromUI();
		waitForSomeTime(5);
		boolean resultOnEnter = false;
		System.out.println("DB Values-->" + getAddressDetailsOfOwner(contractId));
		System.out.println("New Values for UI-->" + fetchOwnerContactInfofromUI());
		System.out.println("OLD Values for UI-->" + mapFunction1);
		if (getAddressDetailsOfOwner(contractId).equals(mapFunction1)
				&& fetchOwnerContactInfofromUI().equals(mapFunction1)) {
			resultOnEnter = true;
		}
		type("ownerEmail", "");
		type("ownerPhone", "");

		saveFieldsAndReopenOwnerInfoPage(contractId);
		boolean resultWithoutEmailAndPhone = false;
		if (fetchOwnerContactInfofromUI().equals(mapFunction2)) {
			resultWithoutEmailAndPhone = true;
		}
		if (resultOnEnter = true) {
			return true;
		} else
			return false;
	}

	/**
	 * This function is used to store co owner info in Map
	 */
	public HashMap<String, String> enterCoOwnerContactInfoInMap() {
		HashMap<String, String> coOwnerStoredInfo = new HashMap<String, String>();
		waitForSomeTime(10);
		coOwnerStoredInfo.put("coOwnerFNAME", "Second");
		coOwnerStoredInfo.put("coOwnerLNAME", "Owner");
		coOwnerStoredInfo.put("coOwnerADD", "NIIT");
		coOwnerStoredInfo.put("coOwnerEmail", "Abc123@gmail.com");
		coOwnerStoredInfo.put("coOwnerPhone", "9876543210");
		coOwnerStoredInfo.put("coOwnerZip", getValue("primaryAccountZipcode").trim());
		return coOwnerStoredInfo;
	}

	/**
	 * This function is used to store co owner info without Email and Phone in Map
	 */
	public HashMap<String, String> enterCoOwnerInfoWithoutPhoneAndEmailInMap() {

		HashMap<String, String> coOwnerStoredInfo = new HashMap<String, String>();
		waitForSomeTime(10);
		coOwnerStoredInfo.put("coOwnerFNAME", "Second");
		coOwnerStoredInfo.put("coOwnerLNAME", "Owner");
		coOwnerStoredInfo.put("coOwnerADD", "NIIT");
		coOwnerStoredInfo.put("coOwnerEmail", "");
		coOwnerStoredInfo.put("coOwnerPhone", "");
//		waitForSomeTime(3);
		coOwnerStoredInfo.put("coOwnerZip", getValue("primaryAccountZipcode").trim());
		return coOwnerStoredInfo;
	}

	/**
	 * This function is used to delete co owner info in Map
	 */
	public HashMap<String, String> deleteCoOwnerContactInfoInMap() {

		HashMap<String, String> coOwnerDeletedInfo = new HashMap<String, String>();
		waitForSomeTime(10);
		coOwnerDeletedInfo.put("coOwnerFNAME", "");
		coOwnerDeletedInfo.put("coOwnerLNAME", "");
		coOwnerDeletedInfo.put("coOwnerADD", "");
		coOwnerDeletedInfo.put("coOwnerEmail", "");
		coOwnerDeletedInfo.put("coOwnerPhone", "");
		waitForSomeTime(2);
		coOwnerDeletedInfo.put("coOwnerZip", "");
		return coOwnerDeletedInfo;
	}

	/**
	 * This function is used to enter co owner info
	 * 
	 * @throws AWTException
	 */
	public void enterCoOwnerContactInfo(HashMap<String, String> enterOrDeleteInfo) throws AWTException {
		try {
			click("scrollContractsListDown");
		} catch (Exception e) {
			// TODO: handle exception
		}
		type("coOwnerFNAME", enterOrDeleteInfo.get("coOwnerFNAME"));
		type("coOwnerLNAME", enterOrDeleteInfo.get("coOwnerLNAME"));
		type("coOwnerADD", enterOrDeleteInfo.get("coOwnerADD"));
		type("coOwnerEmail", enterOrDeleteInfo.get("coOwnerEmail"));
		click("coOwnerPhone");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_HOME);
		robot.keyRelease(KeyEvent.VK_HOME);
		type("coOwnerPhone", enterOrDeleteInfo.get("coOwnerPhone"));
		waitForSomeTime(2);
		type("coOwnerZip", enterOrDeleteInfo.get("coOwnerZip"));
		if (enterOrDeleteInfo.get("coOwnerZip").equals("")) {
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_DELETE);
			click("clickOK");
		}
	}

	/**
	 * This function is used to fetch co owner info from UI
	 */
	public HashMap<String, String> fetchCoOwnerContactInfofromUI() {

		HashMap<String, String> coOwnerUIInfo = enterCoOwnerContactInfoInMap();
		waitForSomeTime(15);
		coOwnerUIInfo.put("coOwnerFNAME", getTextOfElement("coOwnerFNAME"));
		coOwnerUIInfo.put("coOwnerLNAME", getTextOfElement("coOwnerLNAME"));
		coOwnerUIInfo.put("coOwnerADD", getTextOfElement("coOwnerADD"));
		coOwnerUIInfo.put("coOwnerEmail", getTextOfElement("coOwnerEmail"));
		coOwnerUIInfo.put("coOwnerPhone", getTextOfElement("coOwnerPhone"));
		coOwnerUIInfo.put("coOwnerZip", getTextOfElement("coOwnerZip"));
		return coOwnerUIInfo;
	}

	/**
	 * This function is used to co owner info
	 * 
	 * @throws Exception
	 */
	public boolean verifyUIDataWithDB(String contractId, HashMap<String, String> mapFunction1,
			HashMap<String, String> mapFunction2, HashMap<String, String> mapFunction3) throws Exception {
		waitForSomeTime(5);
		enterCoOwnerContactInfo(enterCoOwnerContactInfoInMap());
		saveFieldsAndReopenCoOwnerInfoPage(contractId);
		// very now with DB after entering data
		boolean resultOnEnter = false;
		if (getAddressDetailsOfCoOwner(contractId).equals(mapFunction1)
				&& fetchCoOwnerContactInfofromUI().equals(mapFunction1)) {
			resultOnEnter = true;
		}
		System.out.println("resultOnEnter" + resultOnEnter);
		type("coOwnerEmail", "");
		type("coOwnerPhone", "");
		saveFieldsAndReopenCoOwnerInfoPage(contractId);
		boolean resultWithoutEmailAndPhone = false;
		if (fetchCoOwnerContactInfofromUI().equals(mapFunction2)) {
			resultWithoutEmailAndPhone = true;
		}
		System.out.println("resultWithoutEmailAndPhone" + resultWithoutEmailAndPhone);
		enterCoOwnerContactInfo(deleteCoOwnerContactInfoInMap());
		saveFieldsAndReopenCoOwnerInfoPage(contractId);
		// very now with DB after deleting data
		boolean resultOnDelete = false;
		if (getAddressDetailsOfCoOwner(contractId).equals(mapFunction3)
				&& fetchCoOwnerContactInfofromUI().equals(mapFunction3)) {
			resultOnDelete = true;
		}
		System.out.println("resultOnDelete" + resultOnDelete);
		if (resultOnEnter && resultOnDelete && resultWithoutEmailAndPhone) {
			return true;
		} else
			return false;
	}

	/**
	 * This function is used to verify co owner fields info
	 * 
	 * @throws Exception
	 */
	public void saveFieldsAndReopenCoOwnerInfoPage(String contractId) throws Exception {
		try {
			click("scrollContractsListUp");
		} catch (Exception e) {
			// TODO: handle exception
		}
		waitForSomeTime(5);
		click("clickAdjust");
		AdjustFormNew();
		// again select processed contract and go back to verify the UI.
		click("clickClear");
		processForAccountSearchForContractAdjustment(contractId);
	}

	/**
	 * This function is used to verify owner fields info
	 * 
	 * @throws Exception
	 */
	public void saveFieldsAndReopenOwnerInfoPage(String contractId) throws Exception {
		// enterOwnerContactInfo(enterOwnerContactInfoInMap());
		try {
			click("scrollContractsListUp");
		} catch (Exception e) {
			// TODO: handle exception
		}
		waitForSomeTime(5);
		click("clickAdjust");
		takeScreenshot();
		AdjustForm();
		// again select processed contract and go back to verify the UI.
		click("clickClear");
		processForAccountSearchForContractAdjustment(contractId);
	}

	/**
	 * This function is used to verify co owner fields info
	 * 
	 * @throws Exception
	 */
	public boolean verifyEnableDisableFields(String[] locators) {
		boolean fieldStatus = true;
		for (String locator : locators) {
			checkEnableDisable(locator);
			fieldStatus = false;
		}
		return fieldStatus;
	}

	/**
	 * This function is used to receive string array which is data from test
	 * provider and append data in hashmap with mapping with column and its value
	 * 
	 */
	public HashMap<String, String> businessProcessor_Search_appendSearchData(String[] inputArray) {
		HashMap<String, String> searchData = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			//// Switch Case to Transform Data
			switch (i) {
			case 0:
				searchData.put("From_Date", inputArray[i]);
				break;
			case 1:
				searchData.put("To_Date", inputArray[i]);
				break;
			case 2:
				searchData.put("CreatedBy", inputArray[i]);
				break;
			default:
				searchData.put("NoData", inputArray[i]);
				break;
			}
		}
		return searchData;
	}

	/**
	 * This function is used to get first created by
	 * 
	 * @return
	 * 
	 */
	public String getFirstSearchResultData() throws Exception {

		return getAttributeValue("getRemittanceNumberFromUI", "Name");
	}

	/**
	 * This function is used to return busineess searched data in map, to be
	 * verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnBusinessProcessorSearchResultGridData() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("check_no", getAttributeValue("getCheckNoFromUI", "Name").trim());
		searchData.put("amount", getAttributeValue("getCheckAmountFromUI", "Name").trim().replaceAll(",", ""));
		searchData.put("remittance_number", getAttributeValue("getRemittanceNumberFromUI", "Name").trim());
		searchData.put("created_Date", getAttributeValue("getCreadtedDateFromUI", "Name").trim());
		searchData.put("creator", getAttributeValue("getCreadtedByFromUI", "Name").trim());
		return searchData;
	}

	/**
	 * this function is used to enter the details of check and check amount
	 */
	public void enterCheckDetailsOnRemittance(HashMap<String, String> map) throws Exception {

		if (map.get("CheckNumber") == null) {
			type("typeCheckNumber", randomInteger(6));
		} else {
			type("typeCheckNumber", map.get("CheckNumber"));
		}
		if (map.get("CheckAmount") == null) {
			type("typeCheckAmount", randomInteger(5));
		} else {
			type("typeCheckAmount", map.get("CheckAmount"));
		}
	}

	/**
	 * this function is used to add the account details Also this function is used
	 * to select the role type on search screen
	 */
	public void addAccountDetails(HashMap<String, String> inputData) throws Exception {
		click("selectRoleType");
		waitForSomeTime(2);
		clickOnDropDownItem(inputData.get("Role_Type"));
		click("clickOnPlusIcon");
		waitForSomeTime(2);
		if (inputData.get("RoleId") == null) {
			HashMap<String, String> roleId = getroleId(inputData);
			type("typeRoleIdOnSearchPage", roleId.get("RoleId"));
		} else {
			type("typRoleIdOnACR", inputData.get("RoleId"));
		}
		click("clickOnRoleIdSearchButton");
		click("clickOnSelectButtonRoleID");

	}

	/**
	 * This function is used to Return the values of check details
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnCheckDetailsFromChecksTab() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("amount", getAttributeValue("getCheckAmountFromUI", "Name").replaceAll(",", ""));
		searchData.put("check_no", getAttributeValue("getCheckNumberValueFromCheckScreen", "Name").trim());
		searchData.put("roleid", getAttributeValue("getRoleIdValueFromCheckScreen", "Name").trim());
		searchData.put("accountname", getAttributeValue("getAccountNameFromCheckScreen", "Name").trim());
		return searchData;
	}

	/**
	 * This function is used to Return the values of check details
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getCheckAndRemittanceNumber() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("check_no", getAttributeValue("getCheckNoFromUI", "Name").trim());
		searchData.put("amount", getAttributeValue("getCheckAmountValueFromCheckScreen", "Name").trim());
		return searchData;

	}

	/**
	 * This function is used to Enter ACH info
	 */
	public void processACHInfo(String rId, String rNumber, String rcDigit, String caValue) {
		click("selectRoleTypeInACHEntry");
		click("clickLender");
		type("typeRoleIdInACHEntry", rId);
		click("searchACHEntry");
		waitForSomeTime(5);
		if (rNumber.length() > 0) {
			click("selectTransactionTypeInACHEntry");
			click("clickPrenoteDebit");
			type("typeRoutingNumberInACHEntry", rNumber);
			type("typeRoutingCheckDigitInACHEntry", rcDigit);
			type("typeCheckingAccountValueInACHEntry", caValue);
		}
	}

	/**
	 * This function is used to create ACH Info info
	 * 
	 * @throws Exception
	 */
	public Boolean verifyCreateACHEntry(String roleId) throws Exception {

		HashMap<String, String> map = createACHMap();
		processACHInfo(roleId, map.get("rNumber"), map.get("rcDigit"), map.get("caValue"));
		click("clickCreateUpdateInACHEntry");
		waitForSomeTime(5);
		if (getTextOfElement("getSuccessMsgInACHEntry").equals("Insert/Update successful!")) {
			waitForSomeTime(5);
			click("clickOK");
			type("typeRoleIdInACHEntry", "");
			type("typeRoleIdInACHEntry", roleId);
			click("searchACHEntry");
			waitForSomeTime(5);
			if (createACHMap().equals(getValuesInACHEntry())) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * This function is used to get ACH Info from UI
	 */
	public HashMap<String, String> getValuesInACHEntry() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rNumber", getTextOfElement("typeRoutingNumberInACHEntry"));
		map.put("rcDigit", getTextOfElement("typeRoutingCheckDigitInACHEntry"));
		map.put("caValue", getTextOfElement("typeCheckingAccountValueInACHEntry"));
		return map;
	}

	/**
	 * This function is used to Update ACH info
	 * 
	 * @throws Exception
	 */
	public boolean verifyUpdateACHEntry(String roleId) throws Exception {
		HashMap<String, String> map = updateACHMap();
		processACHInfo(roleId, map.get("rNumber"), map.get("rcDigit"), map.get("caValue"));
		click("clickCreateUpdateInACHEntry");
		waitForSomeTime(5);
		click("clickOnYesButton");
		waitForSomeTime(5);
		if (getTextOfElement("getSuccessMsgInACHEntry").equals("Insert/Update successful!")) {
			waitForSomeTime(5);
			click("clickOK");
			type("typeRoleIdInACHEntry", "");
			type("typeRoleIdInACHEntry", roleId);
			click("searchACHEntry");
			waitForSomeTime(5);
			if (updateACHMap().equals(getValuesInACHEntry())) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * This function is used to Delete ACH info
	 * 
	 * @throws Exception
	 */
	public boolean verifyDeleteACHEntry(String roleId) throws Exception {
		processACHInfo(roleId, "", "", "");
		click("ClickDeleteInACHEntry");
		waitForSomeTime(5);
		if (getTextOfElement("getSuccessMsgInACHEntry").equals("Delete successful!")) {
			click("clickOK");
			type("typeRoleIdInACHEntry", roleId);
			click("searchACHEntry");
			if (deleteACHMap().equals(getValuesInACHEntry())) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	/**
	 * This function is used to store create values in Map
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> createACHMap() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rNumber", "12345678");
		map.put("rcDigit", "1");
		map.put("caValue", "9876");
		return map;
	}

	/**
	 * This function is used to store update values in Map
	 * 
	 * @throws Exception
	 */
	public HashMap<String, String> updateACHMap() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rNumber", "87654321");
		map.put("rcDigit", "2");
		map.put("caValue", "6789");
		return map;
	}

	/**
	 * This function is used to store clear values in Map
	 */
	public HashMap<String, String> deleteACHMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rNumber", "");
		map.put("rcDigit", "");
		map.put("caValue", "");
		return map;
	}

	/**
	 * This function is enter the details for Account and credit refunds screen
	 * 
	 * @return
	 * 
	 */
	public void enterValuesForAccountAndCreditRefunds(HashMap<String, String> inputData) throws Exception {
		click("clickOnAdjustmentType");
		clickOnDropDownItem(inputData.get("AdjustmentType"));
		click("clickOnAdjustmentCategory");
		waitForSomeTime(5);
		clickOnDropDownItem(inputData.get("AdjustmentCategory"));

	}

	/**
	 * this function is used to add the agent details, post period, check details
	 */
	public void addContractIdAndCheckDetails(HashMap<String, String> inputData) throws Exception {
		if (inputData.get("Post Period") == null) {
			click("clickOnPostPeriod");
			findElementByXpath("(//*[@ClassName='Popup']//*[@ClassName='ListBoxItem'])[1]").click();
		} else {
			click("clickOnPostPeriod");
			clickOnDropDownItem(inputData.get("Post Period"));
		}
		//// enter contract deatails
		click("clickOnSearchContractPlusIcon");
		if (inputData.get("ContractId") == null) {
			HashMap<String, String> contractId = getContractIdWithRoleID(inputData.get("RoleId"),
					inputData.get("Role_Type"));
			type("typeContractID", contractId.get("ContractId"));
		} else {
			type("typeContractID", inputData.get("ContractId"));
		}
		click("clickOnContractSearchOnACR");
		click("getContractIDOnACR");
		click("clickOK");
		if (inputData.get("CheckNumber") == null) {
			type("typeCheckNumberInAccountsAndCredits", randomInteger(5));
		} else {
			type("typeCheckNumberInAccountsAndCredits", inputData.get("CheckNumber"));
		}

		if (inputData.get("CheckAmount") == null) {
			type("typeCheckAmountInAccountsAndCredits", randomInteger(4));
		} else {
			type("typeCheckNumberInAccountsAndCredits", inputData.get("CheckAmount"));
		}
		type("typeCommentsOnACR", randomString(15));
	}

	/**
	 * this function is used to upload the pdf file
	 */
	public void uploadPdf() throws Exception {
		click("clickOnUploadDocument");
		//// current running directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository" + "\\Upload file\\";
		Random random = new Random();
		int rand = 1;

		while (true) {
			rand = random.nextInt(7);
			if (rand != 0)
				break;
		}
		String fileName = "FILE_" + rand + ".pdf";
		// upload code should be added
		click("clickOnBrowseButton");
		click("clickOnUploadFileName");
		type("clickOnUploadFileName", dir + fileName);
		click("clickOnOpenButton");
		click("clickOnOKUploadDocumentButton");

	}

	/**
	 * this function is used to add the details on ACR screen
	 */
	public void addDetailsOnACR() throws Exception {
		click("clickOnAddButtonACR");
		click("clickOnYesButton");
	}

	/**
	 * This function is used to Return the data of ACR added details
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getCreditAndRefundsAddedDetails() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("PostPeriod", getAttributeValue("getPostPeriodOnACR", "Name").trim());
		searchData.put("ContractID", getAttributeValue("getContractIDOnACR", "Name").trim());
		searchData.put("RoleID", getAttributeValue("getRoleIDOnACR", "Name").trim());
		searchData.put("CheckNo", getAttributeValue("getCheckNumberOnACR", "Name").trim());
		searchData.put("CheckAmount", getAttributeValue("getCheckAmountOnACR", "Name").trim().replaceAll(",", ""));
		searchData.put("AdjustmentType", getAttributeValue("getAdjustmentTypeOnACR", "Name").trim());
		searchData.put("AdjustmentCategpry", getAttributeValue("getAdjustmentCategoryOnACR", "Name").trim());
		click("swipeRight");
		searchData.put("Trans_Date", getAttributeValue("getTransDateOnACR", "Name").trim());
		searchData.put("CreatedBy", getAttributeValue("getCreatedByOnACR", "Name").trim());
		return searchData;
	}

	/**
	 * this function is used to validate clear screen on ACR screen
	 */
	public boolean validateClearScreen() {
		boolean flag = false;
		String checkNum = getAttributeValue("getCheckNumValueFromACR", "Text");
		String checkAmount = getAttributeValue("getCheckAmountValueFromACR", "Text");
		if (checkNum == null && checkAmount == null) {
			flag = true;
			System.out.println("Screen has been Cleared.");
		}
		return flag;
	}

	/**
	 * this function is used to get contractId to delete the row form ACR Screen
	 */
	public String getValueOfDeleteRowOnACR() throws Exception {
		return getAttributeValue("getContractIdFromADPScreen", "Name");

	}

	/**
	 * this function is used to delete the row form ACR Screen
	 */
	public void deleteRowFromACR() throws Exception {
		rightClick("clickOnDeletedRowOnACRScreen");
		click("clickOnDeleteSelectedRow");
		click("clickOnDeleteConfirmationButton");
	}

	/**
	 * this function is used to validate clear screen on ACR screen
	 */
	public void validateDeleteDataFromACR(String contractId) {
		click("clickOnContractIdFilter");
		type("clickOnContractIdFilter", contractId);
		try {
			windowsDriver.findElement(ObjectRepo.fetchOR("getPostPeriodOnACR")).click();

		} catch (NoSuchElementException e) {
			String createdBy = getValue("getCreatedByvalue");
			type("clickOnCreatedByFilter", createdBy);
			try {
				windowsDriver.findElement(ObjectRepo.fetchOR("getPostPeriodOnACR")).click();
				;
			} catch (NoSuchElementException ex) {
				System.out.println("Row is deleted successfully.");
			}
		}
	}

	/**
	 * this function is used to update the data row form ACR Screen
	 */
	public void updateDataOnACRScreen() throws Exception {
		click("clickOnEditButtonOnACRScreen");
		type("typeCheckNumberInAccountsAndCredits", randomInteger(4));
		type("typeCheckAmountInAccountsAndCredits", randomInteger(4));
		click("clickOnUpdateButton");
	}

	/**
	 * This function is use to enter the required data on Additional Payment screen
	 * 
	 * @return
	 * 
	 */
	public void enterValuesForAdditionalPayments(HashMap<String, String> inputData) throws Exception {
		click("clickOnAdditionalPaymentsAdjustmentType");
		clickOnDropDownItem(inputData.get("AdjustmentType"));
		click("clickOnAdditionalPaymentsAdjustmentCategory");
		clickOnDropDownItem(inputData.get("AdjustmentCategory"));
	}

	public void addAccountandContractDetailsOnAdditionalPayments(HashMap<String, String> map) throws Exception {
		click("clickOnAdditionalPaymentsRoleType");
		clickOnDropDownItem(map.get("Role_Type"));
		click("clickOnPlusOnCreditandRefunds");
		waitForSomeTime(2);
		if (map.get("RoleId") == null) {
			HashMap<String, String> roleId = getroleId(map);
			type("typRoleIdOnACR", roleId.get("RoleId"));
		} else {
			type("typRoleIdOnACR", map.get("RoleId"));
		}
		click("clickOnRoleIdSearchButtonOnACR");
		click("clickOnSelectButtonRoleIDOnACR");
		click("clickOnContractIDSearchOnADP");
		if (map.get("ContractId") == null) {
			HashMap<String, String> contractId = getContractIdWithRoleID(map.get("RoleId"), map.get("Role_Type"));
			type("typeContractID", contractId.get("ContractId"));
		} else {
			type("typeContractID", map.get("ContractId"));
		}
		click("clickOnRoleIdSearchButtonOnACR");
		click("getContractIdFromADPScreen");
		click("clickOK");
		if (map.get("CheckNumber") == null)
			type("typeCheckNumberOnADPScreen", randomInteger(5));
		else {
			type("typeCheckNumberOnADPScreen", map.get("CheckNumber"));
		}
		if (map.get("CheckAmount") == null)
			type("typeDbCrAmountOnADPScreen", randomInteger(4));
		else {
			type("typeDbCrAmountOnADPScreen", map.get("CheckAmount"));
		}
		type("typeCommentsOnACR", randomString(15));
	}

	/**
	 * This function is used to Return the data of ACR added details
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getAddtinalPaymentsAddedDetails() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("RemittanceNumber", getAttributeValue("getRemittanceNoFromADPScreen", "Name").trim());
		searchData.put("PostPeriod", getAttributeValue("getPostPeriodFromADPScreen", "Name").trim());
		searchData.put("ContractID", getAttributeValue("getContractIdFromADPScreen", "Name").trim());
		searchData.put("RoleID", getAttributeValue("getRoleIdFromADPScreen", "Name").trim());
		searchData.put("CheckNo", getAttributeValue("getCheckNumberFromADPScreen", "Name").trim());
		searchData.put("CheckAmount",
				getAttributeValue("getCheckAmountFromADPScreen", "Name").trim().replaceAll(",", ""));
		searchData.put("AdjustmentType", getAttributeValue("getAdjustmentTypeFromADPScreen", "Name").trim());
		searchData.put("AdjustmentCategory", getAttributeValue("getAdjustmentCategoryFromADPScreen", "Name").trim());
		searchData.put("Comments", getAttributeValue("getCommentsFromADPScreen", "Name").trim());
		return searchData;
	}

	/**
	 * This function is used to enter the agent role id
	 * 
	 * @return
	 */
	public void addAgentRoleId(HashMap<String, String> inputData) throws Exception {
		if (inputData.get("AdjustmentCategory") == "Agent Charge off"
				|| inputData.get("AdjustmentCategory") == "Agent Charge Off Reversal") {
			if (inputData.get("AgentRoleId") == null) {
				HashMap<String, String> agentroleId = getAgentRoleId(inputData.get("AgentRoleId"));
				type("clickOnAgentRoleId", agentroleId.get("RoleId"));
			} else {
				type("clickOnAgentRoleId", inputData.get("AgentRoleId"));
			}
		}
	}

	/**
	 * this function is used to clear the data from the ACR screen
	 */
	public void clearDataFromACRScreen() throws Exception {
		click("clickOnEditButtonOnACRScreen");
		click("clickOnClearButtonACR");
	}

	/**
	 * this function is used to check that post period or remittance number is not
	 * editable
	 * 
	 * @return
	 */
	public boolean checkNonEditablePostPeriodAndRemittanceNum() throws Exception {
		boolean flag = true;
		try {
			WebElement editvalue = windowsDriver.findElement(ObjectRepo.fetchOR("checkRemittanceNumberOnADPScreen"));
			editvalue.sendKeys("abcde");
			String newValue = editvalue.getAttribute("Value");
			if (newValue.contentEquals("abcde"))
				flag = false;
			WebElement editvalue1 = windowsDriver.findElement(ObjectRepo.fetchOR("checkPostPeriodOnADPScreen"));
			editvalue1.sendKeys("12345");
			String newValue1 = editvalue1.getAttribute("Value");
			if (newValue1.contentEquals("12345"))
				flag = false;

		} catch (Exception e) {
			System.out.println("Field is non editable");
		}

		return flag;

	}

	/**
	 * this function is used to validate clear screen on ACR screen
	 */
	public boolean validateADPClearScreen() {
		boolean flag = true;
		try {
			windowsDriver.findElement(ObjectRepo.fetchOR("getContractIdFromADPScreen"));
			flag = false;
		} catch (NoSuchElementException e) {
			flag = true;
		}
		return flag;
	}

	/**
	 * this function is used to update the data row form ACR Screen
	 */
	public void updateDataOnADPScreen() throws Exception {
		click("clickOnEditButtonOnACRScreen");
		type("typeCheckNumberOnADPScreen", randomInteger(4));
		type("typeDbCrAmountOnADPScreen", randomInteger(4));
		click("clickOnUpdateButton");
	}

	/**
	 * this function is used to click on random number
	 *//*
		 * public void selectRandomValueFromDropDown()throws Exception{ // Locate the
		 * dropdown menu WebElement drpdown =
		 * windowsDriver.findElement(ObjectRepo.fetchOR("Popup")); // click the dropdown
		 * menu drpdown.click(); //Get the list of dropdown options List<WebElement>
		 * itemsInDropdown = drpdown.findElements(By.className("ListBoxItem")); // Get
		 * the size of dropdown list int size = itemsInDropdown.size(); // Generate the
		 * random number int randomNumber = ThreadLocalRandom.current().nextInt(0,
		 * size); // Clicking on random value itemsInDropdown.get(randomNumber).click();
		 * }
		 */

	// click("typeToSearchRemittance");

	/**
	 * This function is used to get add check
	 * 
	 */
	public void addCheckOnCheckTabs() throws Exception {
		//// Click + button
		click("clickAddButtonToAddCheck");
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).build()
				.perform();
		click("clickAddButtonToAddCheckOk");
		click("saveAllOnRemittance");
		click("clickOK");
	}

	/**
	 * This function is used to clone the check form checks screen
	 */
	public void cloneCheck() throws Exception {
		rightClick("getCheckAmountFromUI");
		click("clickOnCloneCheck");

	}

	/**
	 * this function is used to check that field is readonly
	 */
	public boolean checkReadOnlyFields(String locator) throws Exception {
		boolean flag = true;
		try {
			String readOnly = windowsDriver.findElement(ObjectRepo.fetchOR(locator))
					.getAttribute("IsKeyboardFocusable");
			if (readOnly == "false") {
				flag = false;
			}
		} catch (Exception e) {
			System.out.println("fields are not only for readonly");
		}
		return flag;

	}

	/**
	 * this function is used to update the check on business processor
	 */
	public void updateCheckOnBusinessProcessor() throws Exception {
		rightClick("getRemittanceNumberFromUI");
		click("clickOnEditCheckOnBusinessProcessor");
		type("typeCheckNumber", randomInteger(6));
		type("typeCheckAmount", randomInteger(5));
		click("clickOnSaveButtonOnBusinessProcessor");
	}

	/**
	 * This function is used to return busineess searched data in map, to be
	 * verified from search result grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> returnUpdateCheckDeatilsFromBusinessProcessor() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("check_no", getAttributeValue("getCheckNoFromUI", "Name").trim());
		searchData.put("amount", getAttributeValue("getCheckAmountFromUI", "Name").trim().replaceAll(",", ""));
		searchData.put("remittance_number", getAttributeValue("getRemittanceNumberFromUI", "Name").trim());
		return searchData;
	}

	/**
	 * this function is used to add the account details Also this function is used
	 * to select the role type on search screen
	 */
	public HashMap<String, String> addAccountDetailsOnCreditAndRefunds(HashMap<String, String> inputData)
			throws Exception {
		click("clickOnAccounttype");
		type("clickOnAccounttype", inputData.get("Role_Type"));
		// clickOnDropDownItem(inputData.get("Role_Type"));
		waitForSomeTime(3);
		click("clickOnSearchPlusIcon");
		HashMap<String, String> roleIdDbMap = new HashMap<>();
		waitForSomeTime(5);
		if (inputData.get("RoleId") == null || inputData.get("RoleId") == "") {
			roleIdDbMap = getContractIdOnTheBasisOfActiveRoleOId(inputData);
			waitForSomeTime(2);
			type("typeRoleIdOnACR", roleIdDbMap.get("RoleId"));
		} else {
			type("typeRoleIdOnACR", inputData.get("RoleId"));
		}

		click("clickOnRoleIdSearchButtonOnACR");
		WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ObjectRepo.fetchOR("loadingIcon")));
		waitForSomeTime(15);
		click("clickOnSelectButtonRoleIDOnACR");

		return roleIdDbMap;
	}

	/**
	 * this function is used to add the agent details, post period, check details
	 */
	public void addContractIdAndCheckDetails(HashMap<String, String> inputData, final String ContractId)
			throws Exception {
		click("clickOnPostPeriod");
		waitForSomeTime(3);
		clickOnDropDownItem(inputData.get("Post Period"));
		click("clickOnSearchContractPlusIcon");
		if (inputData.get("ContractId") == null || inputData.get("ContractId") == "") {
			type("typeContractID", ContractId);
		} else {
			type("typeContractID", inputData.get("ContractId"));
		}
		click("clickOnContractSearchOnACR");
		click("getContractIDOnACR");
		waitForSomeTime(2);
		click("clickOkOnACR");
		if (inputData.get("CheckNumber") == null || inputData.get("CheckNumber") == "") {
			type("typeCheckNumberInAccountsAndCredits", randomInteger(5));
		} else {
			type("typeCheckNumberInAccountsAndCredits", inputData.get("CheckNumber"));
		}

		if (inputData.get("CheckAmount") == null || inputData.get("CheckAmount") == "") {
			type("typeCheckAmountInAccountsAndCredits", randomInteger(4));
		} else {
			type("typeCheckNumberInAccountsAndCredits", inputData.get("CheckAmount"));
		}
		type("typeCommentsOnACR", randomString(15));
	}

	/**
	 * this function is used to delete the row form ACR Screen
	 */
	public void deleteRowFromACR(String contractId) throws Exception {
		rightClick("clickOnDeletedRowOnACRScreen");
		click("clickOnDeleteSelectedRow");
		waitForSomeTime(3);
		if (getAttributeValue("getAdjustmentTypeOnACR", "Name").trim().equals("EXTADJ")) {
			String[] validateDeletePopUpMsg = getAttributeValue("getDeletePopUpMsgForEXtAdj", "Name").split(". ");
			assertEquals(validateDeletePopUpMsg[0],
					"Cannot delete Remit Diffs that were created during the underwriting process");
			waitForSomeTime(2);
			click("clickOK");
		} else {
			click("clickOnDeleteConfirmationButton");
			waitForSomeTime(5);
			validateDeleteDataFromACR(contractId);
		}
	}

	/**
	 * This function is use to add account details on Addtional Payment screen
	 * 
	 * @return
	 * 
	 */
	public void addContractDetailsOnAdditionalPayments(HashMap<String, String> map, String contractId)
			throws Exception {
		click("clickOnSearchContractPlusIconOnADP");
		if (map.get("ContractId") == null || map.get("ContractId") == "") {
			type("typeContractID", contractId);
		} else {
			type("typeContractID", map.get("ContractId"));
		}
		click("clickOnContractSearchOnACR");
		click("getContractIDOnACR");
		waitForSomeTime(2);
		click("clickOkOnACR");
		if (map.get("CheckNumber") == null || map.get("CheckAmount") == "") {
			type("typeCheckNumberOnADPScreen", randomInteger(5));
		} else {
			type("typeCheckNumberOnADPScreen", map.get("CheckNumber"));
		}
		click("clickOnAddButtonOnADP");
		String validationErrormsg = getValue("validationMsgForACRScreen");
		assertEquals(validationErrormsg,
				"Please enter all required fields remittance number, account name, role identifier, check number, DB/CR, adjustment type and adjustment category");
		click("clickOK");
		if (map.get("CheckAmount") == null || map.get("CheckAmount") == "") {
			type("typeDbCrAmountOnADPScreen", randomInteger(4));
		} else {
			type("typeDbCrAmountOnADPScreen", map.get("CheckAmount"));
		}
		type("typeCommentsOnADPScreen", randomString(15));
	}

	/**
	 * this function is used to add the details on ACR screen
	 */
	public void addDetailsOnADP() throws Exception {
		click("clickOnAddButtonOnADP");
		click("clickOnYesButton");
	}

	/**
	 * this function is used to delete the row form ACR Screen
	 */
	public void deleteRowFromADP() throws Exception {
		rightClick("clickOnEditButtonForUpdateOnADPScreen");
		click("clickOnDeleteAddtionalPayment");
		waitForSomeTime(3);
		String validateDeletePopUpMsg = getAttributeValue("getDeletePopUpMsgOnADP", "Name");
		assertEquals(validateDeletePopUpMsg,
				"Deleting this record will delete the attached paperwork.Are you sure you want to delete ?");
		waitForSomeTime(2);
		click("clickOK");
	}

	/**
	 * this function is used to validate delete data screen on ADP screen
	 */
	public boolean validateDeleteDataFromADP() {
		boolean flag = true;
		try {
			windowsDriver.findElement(ObjectRepo.fetchOR("getContractIdFromADPScreen"));
			flag = false;
		} catch (NoSuchElementException e) {
			flag = true;
		}
		return flag;
	}

	/**
	 * this function is used to clear the data from the ADP screen
	 */
	public void clearDataFromADPScreen() throws Exception {
		click("clickOnClearButtonOnADP");
	}

	@SuppressWarnings("unused")
	public HashMap<String, String> changeDealerorLenderVin(HashMap<String, String> premiumData) throws Exception {
		contractScrollToTop();
		HashMap<String, String> ss = new HashMap<String, String>();
		type("primaryAccountType", premiumData.get("PrimaryAccount"));
		type("primaryAccountId", premiumData.get("DEALERID"));
		click("primaryAccountSearchButton");
		//// Enter Secondary Account Details
		type("secondaryAccountType", premiumData.get("SecondaryAccount"));
		type("secondaryAccountId", premiumData.get("SecondaryAccountId"));
		click("secondaryAccountSearchButton");
		//// Enter VIN Details
		String dataaa = EnterVinDetails(premiumData.get("VIN"));
		if (dataaa.equalsIgnoreCase("Invalid Vin")) {
			String contractSummary = "0";
			try {
				contractSummary = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
			} catch (Exception e) {
				takeScreenshot();
				// TODO: handle exception
			}
			if (contractSummary.toLowerCase().equals("0"))
				click("vinNumberOverride");
			type("vinNumberMake", premiumData.get("MAKE"));
			type("vinNumberModel", premiumData.get("MODEL"));
			type("vinNumberYear", premiumData.get("YEAR"));
		} else {
			String contractSummary = "0";
			type("vinNumberMake", premiumData.get("MAKE"));
			type("vinNumberModel", premiumData.get("MODEL"));
			type("vinNumberYear", premiumData.get("YEAR"));
		}
		//// Enter customer information
		takeScreenshot();
		try {
			click("scrollContractsListDown1");
		} catch (Exception e) {
			click("scrollContractsListDown");
		}
		waitForSomeTime(5);
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
		waitForSomeTime(10);
		//// Handling for MielageBand
		if (premiumData.get("MIELAGEBAND") != null) {
			type("getMielage", premiumData.get("MIELAGEBAND"));
		} else {
			String milegae = getAttributeValue("getMielage", "Value.Value");
			ss.put("MIELAGEBAND", milegae);
		}
		waitForSomeTime(5);
		//// Handling for Class
		if (premiumData.get("CLASS") != null) {
			type("getClass", premiumData.get("CLASS"));
		} else {
			String classs = getAttributeValue("getClass", "Value.Value");
			ss.put("CLASS", classs);
		}
		waitForSomeTime(5);
		//// Term for Price sheet handling
		specialclickComboBox("selectPricesheetTerm");
		HashSet<String> termValues = new HashSet<String>();
		termValues.addAll(specialGetAllValuesSaveInSet("getTermValues"));
		if (premiumData.get("TERM") != null) {
			if (termValues.contains(premiumData.get("TERM")))
				type("selectPricesheetTerm", premiumData.get("TERM"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : termValues) {
				if (premiumData.get("MissTerm") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetTerm", string);
							ss.put("TERM", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissTerm").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetTerm", string);
								ss.put("TERM", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		//// Coverage for Price sheet handling
		specialclickComboBox("selectPricesheetCoverage");
		HashSet<String> coverageValues = new HashSet<String>();
		coverageValues.addAll(specialGetAllValuesSaveInSet("getCoverageValues"));
		if (premiumData.get("COVERAGE") != null) {
			if (coverageValues.contains(premiumData.get("COVERAGE")))
				type("selectPricesheetCoverage", premiumData.get("COVERAGE"));
			else
				throw new Exception("no data found");
		} else {
			for (String string : coverageValues) {
				if (premiumData.get("MissCoverage") == null) {
					try {
						if (string.length() > 0) {
							type("selectPricesheetCoverage", string);
							ss.put("COVERAGE", string);
							break;
						} else {
							throw new Exception("no data found");
						}
					} catch (Exception e) {
						continue;
					}
				} else {
					if (!premiumData.get("MissCoverage").contains(string)) {
						try {
							if (string.length() > 0) {
								type("selectPricesheetCoverage", string);
								ss.put("COVERAGE", string);
								break;
							} else {
								throw new Exception("no data found");
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
		}
		waitForSomeTime(5);
		premiumData.putAll(ss);
		HashMap<String, String> ssss = getExceptionPremium(premiumData);
		ss.putAll(ssss);
		System.out.println("compleetd");
		return ss;
	}

	/**
	 * This function is use to check that file is download or not
	 * 
	 * 
	 */
	public boolean isFileDownloaded(String downloadPath, String fileName) throws Exception {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag = true;
		}

		return flag;
	}

	/**
	 * this function is used to export the excel of business processor
	 */
	public void exportExcelOfBusinessProcessor() throws Exception {
		click("clickOnExportExcel");
		click("clickOK");
	}

	/**
	 * this function is used to return the data of business processor from UI
	 */

	public HashMap<Integer, HashMap<String, String>> returnBusinessProcessorSearchResultForAllGridData(
			int totalRowCount) throws Exception {
		HashMap<Integer, HashMap<String, String>> searchDataForAllRow = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> searchData = null;
		int count = 0;
		for (int i = 0; i < totalRowCount; i++) {
			searchData = new HashMap<String, String>();
			searchData.put("CheckNo", getValue("getCheckNoFromUI", i).trim());
			searchData.put("CheckAmount", getValue("getCheckAmountFromUI", i).trim().replaceAll(",", ""));
			searchData.put("RemittanceNumber", getValue("getRemittanceNumberFromUI", i).trim());
			searchData.put("DateDeposited", getValue("getCreadtedDateFromUI", i).trim());
			searchData.put("Creator", getValue("getCreadtedByFromUI", i).trim());
			searchData.put("RoleIdentifier", getValue("getRoleIdFromBusinessProcessor", i).trim());
			searchData.put("AccountName", getValue("getAccountNameFromBusinessProcessor", i).trim());
			searchData.put("CoreCount", getValue("getCoreCountFromBusinessProcessor", i).trim());
			searchData.put("LWACount", getValue("getLWCountFromBusinessProcessor", i).trim());
			searchData.put("RemittanceName", getValue("getRemitanceNameFromBusinessProcessor", i).trim());
			count++;
			searchDataForAllRow.put(count, searchData);
		}
		return searchDataForAllRow;
	}

	/**
	 * this function is used to remove the check from Business processor screen
	 */
	public void removeCheckFromBusinessProcessorScreen() throws Exception {
		rightClick("getRemittanceNumberFromUI");
		click("deleteCheckFromBusinessProcessor");
		String deleteMsgForConfirmation = getValue("deleteCheckConfirmationMsg");
		assertEquals(deleteMsgForConfirmation, "Do you want to remove all checks with this remittance?");
		click("clickOK");

	}

	/**
	 * This function is get debt/credit amount
	 * 
	 */
	public String getCreditDebitAmount() throws Exception {
		String amount = getAttributeValue("getCreditDebitAmount", "Name");
		if (amount.contains("(")) {
			System.out.println("Less amount paid by dealer: Short Pay Case");
			amount = amount.replace("(", "");
			amount = amount.replace(")", "");
		} else {
			System.out.println("More amount paid by dealer: Excess Pay Case");
		}
		amount = amount.replace("$", "");
		return amount;
	}

	/**
	 * This function is get Debt/credit amount
	 * 
	 */
	public void validateAdditionalPayAmount(String aulPremium, String debitCreditAmount) throws Exception {
		String totalDealerAmount = getAttributeValue("getTotalDealerAmount", "Name").replace(",", "");
		String balanceAmount = getAttributeValue("getBalanceAmount", "Name").replace(",", "");
		String additionaAmount = getAttributeValue("getAdditionalPaymentAmount", "Name").replace(",", "");
		assertEquals(aulPremium, totalDealerAmount);
		assertEquals(debitCreditAmount, additionaAmount);
		assertEquals(balanceAmount, "0.00");
	}

	/**
	 * This function is use to add account details on Additional Payment screen
	 * 
	 * @return
	 * 
	 */
	public void addContractDetailsOnAdditionalPaymentsWithoutError(HashMap<String, String> map, String contractId)
			throws Exception {
		click("clickOnSearchContractPlusIconOnADP");
		if (map.get("ContractId") == null || map.get("ContractId").equals("")) {
			type("typeContractID", contractId);
		} else {
			type("typeContractID", map.get("ContractId"));
		}
		click("clickOnContractSearchOnACR");
		click("getContractIDOnACR");
		click("clickOkOnACR");
		if (map.get("CheckNumber") == null || map.get("CheckNumber").equals("")) {
			type("typeCheckNumberOnADPScreen", randomInteger(5));
		} else {
			type("typeCheckNumberOnADPScreen", map.get("CheckNumber"));
		}
		waitForSomeTime(5);
		if (map.get("CheckAmount") == null || map.get("CheckAmount").equals("")) {
			type("typeDbCrAmountOnADPScreenNew", randomInteger(4));
		} else {
			type("typeDbCrAmountOnADPScreenNew", map.get("CheckAmount"));
		}
		type("typeCommentsOnADPScreen", randomString(15));
	}

	/**
	 * This function is process data for Additional Payments
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> processAddPayments(String inputArray[]) throws Exception {
		String adjType = inputArray[0];
		String checkAmount = inputArray[5];
		String roleId = inputArray[3];
		if (checkAmount.contains("."))
			checkAmount = checkAmount.substring(0, checkAmount.indexOf("."));
		if (roleId.contains("."))
			roleId = roleId.substring(0, roleId.indexOf("."));

		enterCustomerPaidAndDealerPaid(checkAmount, checkAmount);
		String debitCreditAmount = getCreditDebitAmount();
		if (adjType.equals("REMITDIFF")) {
			debitCreditAmount = "-" + debitCreditAmount;
		}
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("AdjustmentType", adjType);
		uiSearchData.put("AdjustmentCategory", inputArray[1]);
		uiSearchData.put("Role_Type", inputArray[2]);
		uiSearchData.put("RoleId", roleId);
		uiSearchData.put("Status", inputArray[4]);
		uiSearchData.put("CheckNumber", "");
		uiSearchData.put("CheckAmount", debitCreditAmount);
		return uiSearchData;
	}

	/*
	 * This function is used to enter values in create remittance TC41&43
	 * 
	 */
	public String enterRemittanceValuesWithDealerId(String[] inputArray) throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		try {
			if (inputArray[0].toLowerCase().equals("random")) {
				remittanceName = randomString(20);
			} else {
				remittanceName = inputArray[0];
			}
			type("remittanceName", remittanceName);
			//// Enter core count
			type("remittanceCoreCount", inputArray[1]);
			type("remittanceLWACount", inputArray[2]);
			//// select remit type
			typeKeys("remittanceContractCombobox", inputArray[3]);
			typeKeys("remittanceTypeCombobox", inputArray[4]);
			typeKeys("remittanceRemitTypeComboBox", inputArray[5]);
			typeKeys("remittanceSubTypeComboBox", inputArray[6]);
			//// type comments
			type("remittanceComments", inputArray[7]);
			//// add check here
			if (inputArray[8].length() > 1 && inputArray[9].length() > 1 && inputArray[10] != null
					&& inputArray[11] != null) {
				/// enter check details
				typeKeys("typeDealerinaddcheck", inputArray[10]);
				waitForSomeTime(5);
				click("clickOnNoButton");
				click("addDealeraddButton");
				typeKeys("addcheckroleid", inputArray[11]);
				click("addchecksearchbutton");
				click("addchecksearchtoprow");
				Robot rt = new Robot();
				rt.keyPress(KeyEvent.VK_TAB);
				rt.keyRelease(KeyEvent.VK_TAB);
				waitForSomeTime(5);
				type("addCheckOnRemittance", inputArray[8]);
				type("addCheckAmtOnRemittance", inputArray[9]);

				//// add check details
				click("clickAddCheckAmtOnRemittance");

			}
			//// click save
			String clickState = getAttributeValue("clickSaveRemittance", "IsEnabled");
			if (clickState.toLowerCase().equals("true")) {
				click("clickSaveRemittance");
				waitForSomeTime(25);
				if (!(inputArray[8].length() > 1 && inputArray[9].length() > 1))
					click("clickOnYesButton");
			} else
				return "";
			//// close file explorer
			for (int i = 0; i < 2; i++) {
				try {
					click("closeFolderExplorer");
					waitForSomeTime(5);
					break;
				} catch (Exception e) {
					continue;
				}
			}
			for (int i = 0; i < 5; i++) {
				try {
					click("remittanceExpander");
					break;
				} catch (Exception e) {
					continue;
				}
			}
			//// close remittance
		} catch (Exception e) {
			remittanceName = "";
		}
		return remittanceName;

	}

	public HashMap<String, String> uiValue() {
		HashMap<String, String> searchData = new HashMap<String, String>();
		String firstName = getTextOfElement("firstNameOnFindContractGrid");
		String lastName = getTextOfElement("lastNameOnFindContractGrid");
		searchData.put("firstName", firstName);
		searchData.put("lastName", lastName);

		return searchData;
	}

	/**
	 * This function is used to get validation if vehicle is Ford F150 Vehicle
	 * 
	 */
	public String getValidationForFordF150Vehicle() throws Exception {
		return getValue("messageForFordF150Vehicle");
	}

	/**
	 * Expand Remittance
	 * 
	 */
	public void expandRemiitence() {

		List<WebElement> contractNumber = listOfElements("expandRemittance");
		for (WebElement webElement : contractNumber) {
			try {
				webElement.click();
				break;

			} catch (Exception e) {
				continue;
			}
		}

	}

	/**
	 * This function is used to provide input Values for Credit/Debit/Zero Balnace
	 * Functionality
	 * 
	 * @return
	 * 
	 */
	public void fillInputValues(String postPeriod, String roleType, String functionPerform) throws Exception {
		/// select given Post Period
		fillInputValuesForPostPeriod(postPeriod);
		// select role type
		typeKeys("selectRoleTypeComboBox", roleType.substring(0));

		// click Credits/Debits/Last 3 Zero Balance
		if (functionPerform.contains("Credit"))
			click("clickCreditsButton");
		else if (functionPerform.contains("Debit"))
			click("clickDebitsButton");
		else
			click("clickLast3ZeroBalButton");
		waitForSomeTime(10);
		takeScreenshot();
	}

	/**
	 * This function is used to select the given post period
	 * 
	 * @return
	 * 
	 */
	public void fillInputValuesForPostPeriod(String postPeriod) throws Exception {

		/// select given Post Period
		click("clickCalenderIcon");
		String calYear = getValue("getCalYear");
		String givenPostPeriodYear = postPeriod.substring(0, 4);
		String givenPostPeriodMonth = postPeriod.substring(4, 6);
		int gvnPPYearInt = Integer.parseInt(givenPostPeriodYear);
		int calYearInt = Integer.parseInt(calYear);
		int yearDiff = 0;
		if (gvnPPYearInt > calYearInt) {
			yearDiff = gvnPPYearInt - calYearInt;
			for (int i = 0; i < yearDiff; i++) {
				click("clickCalNextYearButton");
			}

		} else if (gvnPPYearInt < calYearInt) {
			yearDiff = calYearInt - gvnPPYearInt;
			for (int i = 0; i < yearDiff; i++) {
				click("clickCalPrevYearButton");
			}

		}
		int gvnPPMonthInt = Integer.parseInt(givenPostPeriodMonth);
		findElementByXpath(
				"(//*[@AutomationId='Ocean_Controls_MonthYearCalendar_MonthYearCalendar']//*[@ClassName='CalendarButton'])["
						+ gvnPPMonthInt + "]").click();
	}

	public String createRemittance() throws Exception {
		copyFilesWorkingRemittance();
		//// fill all necessary fields in create remittance
		String[] inputArray = { "random", "1", "", "Paper", "Standard", "Paper Remit", "Dealer Suspense", "Automation",
				"1121", "500", "Dealer", "140" };
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// verify drag and drop status for pdf
		String remittanceName = enterRemittanceValues(inputArray);
		if (inputArray[4] == "" || inputArray[5] == "" || inputArray[0] == "" || inputArray[1] == ""
				|| inputArray[2] == "") {
			if (remittanceName.equals("")) {
				assertEquals(true, true);
			} else {
				assertEquals(true, false);
			}
		} else {
			HashMap<String, String> dbValues = getRemitCreationdata(remittanceName);
			HashMap<String, String> excelValues = excelValue(inputArray, remittanceName);
			dbValues.put("Paper", excelValues.get("Paper"));
			String aa = dbValues.get("checkamount");
			if (aa.length() > 1) {
				String s = aa.substring(0, aa.indexOf("."));
				dbValues.put("checkamount", s);
			} else
				dbValues.put("checkamount", aa);
			for (Map.Entry<String, String> entry : dbValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					dbValues.put(key, "");
			}
			for (Map.Entry<String, String> entry : excelValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					excelValues.put(key, "");
			}
			if (excelValues.equals(dbValues)) {
				assertEquals(true, true);
			} else {
				assertEquals(false, true);
			}
		}

		return remittanceName;
	}

	public HashMap<String, String> excelValue(String[] inputArray, String remit) {
		HashMap<String, String> excel = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			switch (i) {
			case 0:
				excel.put("RemittanceName", remit);
				break;
			case 1:
				excel.put("corecount", inputArray[i]);
				break;
			case 2:
				excel.put("lwacount", inputArray[i]);
				break;
			case 3:
				excel.put("Source_Type", inputArray[i]);
				break;
			case 4:
				excel.put("name", inputArray[i]);
				break;
			case 5:
				excel.put("Paper", inputArray[i]);
				break;
			case 6:
				excel.put("Subtype_Name", inputArray[i]);
				break;
			case 7:
				excel.put("comments", inputArray[i]);
				break;
			case 8:
				excel.put("checknumber", inputArray[i]);
				break;
			case 9:
				excel.put("checkamount", inputArray[i]);
				break;
			default:
				break;
			}
		}
		return excel;
	}

	public String createContractWithReturnStatusandPost(String remittanceName) throws Exception {
		String[] inputData = { "SNE", "Y", "Y", "Y", "Y", "Y", "Y", "ALLPLANS", "ALLPLANS", "", "", "", "", "", "", "",
				"", "", "", "" };
		String premium = "";
		if (remittanceName.length() > 0) {
			refreshRemittance();
			waitForSomeTime(10);
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			//// Refresh remittance
			// refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			refreshRemittance();
			searchContractwithPendingState(remittanceName, remitt.get(1).get("FILE_NAME"));
			// lockAndViewContract();
			addCheck();
			///// Prepare Data
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
				click("loadRemittance");
				// type("typeContract", remitt.get(1).get("FILE_NAME"));
				// searchContractwithPendingState(remitt.get(1).get("RemittanceName"),
				// remitt.get(1).get("FILE_NAME"));
				lockAndViewContract();
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
				//// select check, underwrite contract, post remittance
				premium = premium().replaceAll(",", "");
				enterCustomerPaidAndDealerPaid("500", "500");
				selectCheckAndScrollToTop();
				//// click Return
				clickReturnWithReason(new String[] { "other" });
				waitForSomeTime(25);
				//// click ok
				removeErrorMessages();
				click("contractExpander");
				//// post remittance and verify
				postRemittance();
				//// code to be added once underwrite code issue is fixed
				//// verify post status
				assertEquals(postedRemittanceStatus(remittanceName).toLowerCase(), "posted");
			} else {
				throw new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
		return premium;
	}

	/**
	 * This function is used to Return the data of returns expander
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getReturnsExpanderDetailsFromUI(String roleType) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("ContractID", getAttributeValue("getContractIdFromAccountAdjustments", "Name").trim());
		searchData.put("PrimaryRoleName", getAttributeValue("getPrimaryRoleNameFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("DealerID", getAttributeValue("getDealerIdFromAccountAdjustments", "Name").trim());
		searchData.put("LenderID", getAttributeValue("getLenderIdFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("CheckNo", getAttributeValue("getCheckNoFromAccountAdjustments", "Name").trim());
		searchData.put("CheckAmount",
				getAttributeValue("getCheckAmountFromAccountAdjustments", "Name").trim().replaceAll(",", ""));
		searchData.put("RemitNumber", getAttributeValue("getRemitFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("DBCR", getAttributeValue("getDBCRFromAccountAdjustments", "Name").trim());
		searchData.put("DealerPaid", getAttributeValue("getDealerPaidFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("Trans_Date", getAttributeValue("getTransDateFromAccountAdjustments", "Name").trim());
		searchData.put("PostPeriod", getAttributeValue("getPostPeriodFromAccountAdjustments", "Name").trim());
		searchData.put("Admin", getAttributeValue("getAdminFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("UserName", getAttributeValue("getUserFromAccountAdjustments", "Name").trim());
		searchData.put("Comments", getAttributeValue("getCommentsFromAccountAdjustments", "Name").trim());
		return searchData;
	}

	/**
	 * This function is used to Return the data of returns row
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getReturnsRowDetailsFromUI() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("ContractCount", getValue("getContractCountFromReturnsRow").trim());
		searchData.put("Premium", getValue("getPremiumFromReturnsRow").trim());
		searchData.put("DBCR", getValue("getDBCRFromReturnsRow").trim());
		searchData.put("DealerPaid", getValue("getDealerPaidFromReturnsRow").trim());
		searchData.put("Balance", getValue("getBalanceFromReturnsRow").trim());
		return searchData;
	}

	/**
	 * This common function is used to click on Returns under statements tab
	 * 
	 * @return
	 * 
	 */
	public void clickReturnsUnderStatementsTab() {
		try {
			click("clickOnReturnsOnStatementsTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This function is used type role type and role id on Statements tab
	 * 
	 * @return
	 * 
	 */

	/**
	 * This common function is used to click on view button under statements tab
	 * 
	 * @return
	 * 
	 */
	public void clickOnViewButtonUnderStatementsTab() {
		try {
			click("clickOnViewButtonOnStatementsTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * this function is used to add the account details Also this function is used
	 * to select the role type on search screen
	 */
	public HashMap<String, String> addAccountDetailsOnCreditAndRefundsForRefundExpander(
			HashMap<String, String> inputData) throws Exception {
		click("clickOnAccounttype");
		type("clickOnAccounttype", inputData.get("Role_Type"));
		// clickOnDropDownItem(inputData.get("Role_Type"));
		waitForSomeTime(3);
		click("clickOnSearchPlusIcon");
		HashMap<String, String> roleIdDbMap = new HashMap<>();
		waitForSomeTime(5);
		if (inputData.get("RoleId") == null || inputData.get("RoleId") == "") {
			roleIdDbMap = getContractIdOnTheBasisOfActiveRoleOIdForRefundExpander(inputData);
			waitForSomeTime(2);
			type("typeRoleIdOnACR", roleIdDbMap.get("RoleId"));
		} else {
			type("typeRoleIdOnACR", inputData.get("RoleId"));
		}

		click("clickOnRoleIdSearchButtonOnACR");
		WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ObjectRepo.fetchOR("loadingIcon")));
		waitForSomeTime(15);
		click("clickOnSelectButtonRoleID");

		return roleIdDbMap;
	}

	/**
	 * This common function is used to click on Refunds under statements tab
	 * 
	 * @return
	 * 
	 */
	public void clickRefundsUnderStatementsTab() {
		try {
			click("clickOnRefundsOnStatementsTab");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This function is used to Return the data of refunds expander from Account
	 * Adjustments
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getRefundsExpanderDetailsFromUI(HashMap<String, String> map) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("ContractID", getAttributeValue("getContractIdFromAccountAdjustments", "Name").trim());
		if (map.get("Role_Type").equals("Dealer")) {
			searchData.put("PrimaryRoleName",
					getAttributeValue("getPrimaryRoleNameFromAccountAdjustments", "Name").trim());
			click("clickSwipeRightOnAccountAdjustments");
			searchData.put("RoleID", getAttributeValue("getDealerIdFromAccountAdjustments", "Name").trim());
		} else {
			searchData.put("PrimaryRoleName",
					getAttributeValue("getPrimaryRoleNameFromAccountAdjustments", "Name").trim());
			click("clickSwipeRightOnAccountAdjustments");
			searchData.put("RoleID", getAttributeValue("getLenderIdFromAccountAdjustments", "Name").trim());
		}
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("CheckNo", getAttributeValue("getCheckNoFromAccountAdjustments", "Name").trim());
		searchData.put("CheckAmount",
				getAttributeValue("getCheckAmountFromAccountAdjustments", "Name").trim().replaceAll(",", ""));
		click("clickSwipeRightOnAccountAdjustments");
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("DBCR", getAttributeValue("getDBCRFromAccountAdjustments", "Name").trim());
		searchData.put("DealerPaid", getAttributeValue("getDealerPaidFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("Trans_Date", getAttributeValue("getTransDateFromAccountAdjustments", "Name").trim());
		searchData.put("PostPeriod", getAttributeValue("getPostPeriodFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("Comments", getAttributeValue("getCommentsFromAccountAdjustments", "Name").trim());
		return searchData;
	}

	/**
	 * This function is used type role type and role id on Statements tab
	 * 
	 * @return
	 * 
	 */
	public void typeRoletypeAndRoleIdUnderStatementsTab(String roletype, String roleId) {
		try {
			type("selectDealerOrLenderOnStatementsTab", roletype);
			type("typeDealerOrLenderIdOnStatementsTab", roleId);
		} catch (Exception e) {
			throw e;
		}
	}

	public String getMonthFromDate(String inputDate) throws ParseException {

		//// parse string to date
		Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(inputDate);

		SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yy");
		String d = format2.format(date1);

		return d.replaceAll("\\d", "").replaceAll("-", "");
	}

	public void clickOnCalenderMonth(String inputMonth) {

		findElementByXpath(
				"//*[@AutomationId='Ocean_Controls_MonthYearCalendar_MonthYearCalendar']//*[@ClassName='CalendarButton']//*[@ClassName='TextBlock' and @Name='"
						+ inputMonth + "']").click();
	}

	/**
	 * This common function is used to click on Refunds under statements tab
	 * 
	 * @return
	 * 
	 */
	public void clickSepChkUnderStatementsTab() {
		try {
			click("clickOnSeprateChecksOnStatementsScreen");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This function is used to Return the data of sepcheck expander from Account
	 * Adjustments
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getSepChkExpanderDetailsFromUI(HashMap<String, String> map) throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("DetailsDescription",
				getAttributeValue("getDetailsDescriptionFromAccountAdjustments", "Name").trim());
		searchData.put("ContractID", getAttributeValue("getContractIdFromAccountAdjustments", "Name").trim());
		if (map.get("Role_Type").equals("Dealer")) {
			searchData.put("PrimaryRoleName",
					getAttributeValue("getPrimaryRoleNameFromAccountAdjustments", "Name").trim());
			click("clickSwipeRightOnAccountAdjustments");
			searchData.put("RoleID", getAttributeValue("getDealerIdFromAccountAdjustments", "Name").trim());
		} else {
			searchData.put("PrimaryRoleName",
					getAttributeValue("getPrimaryRoleNameFromAccountAdjustments", "Name").trim());
			click("clickSwipeRightOnAccountAdjustments");
			searchData.put("RoleID", getAttributeValue("getLenderIdFromAccountAdjustments", "Name").trim());
		}
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("CheckNo", getAttributeValue("getCheckNoFromAccountAdjustments", "Name").trim());
		searchData.put("CheckAmount",
				getAttributeValue("getCheckAmountFromAccountAdjustments", "Name").trim().replaceAll(",", ""));
		click("clickSwipeRightOnAccountAdjustments");
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("DBCR", getAttributeValue("getDBCRFromAccountAdjustments", "Name").trim());
		searchData.put("DealerPaid", getAttributeValue("getDealerPaidFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("Trans_Date", getAttributeValue("getTransDateFromAccountAdjustments", "Name").trim());
		searchData.put("PostPeriod", getAttributeValue("getPostPeriodFromAccountAdjustments", "Name").trim());
		click("clickSwipeRightOnAccountAdjustments");
		click("clickSwipeRightOnAccountAdjustments");
		searchData.put("Comments", getAttributeValue("getCommentsFromAccountAdjustments", "Name").trim());
		return searchData;
	}

	/**
	 * This function is used to Return the data of New Business grid from Account
	 * Adjustments
	 * 
	 * @return
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getNewBusinessGridDetailsFromUI() throws Exception {
		HashMap<Integer, HashMap<String, String>> searchDataForAllRow = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> searchData = new HashMap<String, String>();
		;
		int count = 0;
		List<WebElement> totalRowCount = listOfElementsByXpath("getAdjTYpeFromNewBusinessGridDetails");
		for (int i = 0; i < totalRowCount.size(); i++) {
			searchData.put("AdjType", getValue("getAdjTYpeFromNewBusinessGridDetails", i).trim());
			searchData.put("ContractID", getValue("getContractFromNewBusinessGridDetails", i).trim());
			searchData.put("RoleID", getValue("getDealeIdFromNewBusinessGridDetails", i).trim());
			searchData.put("CheckNo", getValue("getCheckNumFromNewBusinessGridDetails", i).trim());
			searchData.put("Total", getValue("getTotalFromNewBusinessGridDetails", i).trim());
			searchData.put("CheckAmount",
					getValue("getCheckAmountFromNewBusinessGridDetails", i).trim().replaceAll(",", ""));
			searchData.put("RemitNum", getValue("getRemitFromNewBusinessGridDetails", i).trim());
			searchData.put("DBCR", getValue("getDBCRAmtFromNewBusinessGridDetails", i).trim());
			searchData.put("DealerPaid", getValue("getDealerPaidFromNewBusinessGridDetails", i).trim());
			searchData.put("Trans_Date", getValue("getTransDateFromNewBusinessGridDetails", i).trim());
			searchData.put("PostPeriod", getValue("getPostPeriodFromNewBusinessGridDetails", i).trim());
			searchData.put("AdminCode", getValue("getAdminFromNewBusinessGridDetails", i).trim());
			// searchData.put("Agt1", getValue("getAgt1FromNewBusinessGridDetails",
			// i).trim());
			// searchData.put("AgtAmt", getValue("getAgtAmtFromNewBusinessGridDetails",
			// i).trim());
			searchData.put("CustomerName", getValue("getCustomerNameFromNewBusinessGridDetails", i).trim());
			searchData.put("UserId", getValue("getUserIDFromNewBusinessGridDetails", i).trim());
			searchData.put("Comments", getValue("getStdCommentsFromNewBusinessGridDetails", i).trim());
			count++;
			searchDataForAllRow.put(count, searchData);
		}
		return searchDataForAllRow;
	}

	/**
	 *
	 * / * This function fills all detail like -Role Id , Role Type and Post period
	 * and search for onhold contract
	 * 
	 */
	public boolean fillDataForOnHoldAccountStatement(String roleId, String roleType, String postPeriod) {
		boolean matchFlag = true;
		String year = postPeriod.substring(0, 3);
		String month = postPeriod.substring(4, 5);
		int intYear = Integer.parseInt(year);
		try {
			click("scrollContractsListUp");
		} catch (Exception e) {

		}
		click("selectDealerOrLenderOnStatementsTab");
		WebElement ele = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list = ele.findElements(By.className("ListBoxItem"));
		for (WebElement webElement : list) {
			String comboText = webElement.getText();
			if (comboText.equalsIgnoreCase(roleType)) {
				webElement.click();
			}
			type("typeDealerOrLenderIdOnStatementsTab", "roleId");
		}
		click("clickOnCalendarIconOnStatementsTab");
		int uIyear = Integer.parseInt(getValue("yearOnCalenderIconOnStatementsTab"));
		if (intYear > uIyear) {
			int i = (intYear - uIyear);
			for (int j = 0; j < i; j++) {
				click("nextOnCalenderIconOnStatementsTab");
			}
		}
		if (intYear < uIyear) {
			int i = (uIyear - intYear);
			for (int j = 0; j < i; j++) {
				click("priviousOnCalenderIconOnStatementsTab");
			}
		}
		List<WebElement> clcMonth = listOfElements("listMonthBtnCalendarIconOnStatementsTab");
		int month1 = Integer.parseInt(month);
		clcMonth.get(month1).click();
		click("clickOnViewButtonOnStatementsTab");
		waitForSomeTime(10);
		click("clickOnHoldOnStatementsTab");
		List<WebElement> onHoldSecton = listOfElements("listgetCertFromOnHoldGrid");
		int onHoldSectionSize = onHoldSecton.size();
		if (onHoldSectionSize >= 1) {
			matchFlag = true;
		} else {
			matchFlag = false;
		}

		return matchFlag;
	}

	/**
	 *
	 * / * This function validate toggle functionality OnHold/ShortPay section
	 * values
	 * 
	 */
	public boolean validateToggleScrollOnHold() {
		boolean matchflag = false;
		String toggleState = getAttributeValue("contractOnHoldToggle", "Toggle.ToggleState");
		if (toggleState.equalsIgnoreCase("true")) {
			click("contractOnHoldToggle");
			waitForSomeTime(2);
			String contractOnHoldGrid = getAttributeValue("contractOnHoldGrid", "IsOffscreen");
			if (contractOnHoldGrid.equalsIgnoreCase("False")) {
				matchflag = true;
			}
		} else {
			click("contractOnHoldToggle");
			waitForSomeTime(2);
			String contractOnHoldGrid = getAttributeValue("contractOnHoldGrid", "IsOffscreen");
			if (contractOnHoldGrid.equalsIgnoreCase("true")) {
				matchflag = true;
			}
		}
		return matchflag;
	}

	/**
	 *
	 * / * This function search on hold contract on the basis of Role Id , Role Type
	 * and Post period and return OnHold/ShortPay section values
	 * 
	 */
	public HashMap<String, String> verifyUiColumnHeaderOnHold(String roleId, String roleType, String postPeriod)
			throws ParseException {

		String year = postPeriod.substring(0, 3);
		String month = postPeriod.substring(4, 5);
		int intYear = Integer.parseInt(year);
		click("selectDealerOrLenderOnStatementsTab");
		WebElement ele = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list = ele.findElements(By.className("ListBoxItem"));
		for (WebElement webElement : list) {
			String comboText = webElement.getText();
			if (comboText.equalsIgnoreCase(roleType)) {
				webElement.click();
			}
			type("typeDealerOrLenderIdOnStatementsTab", "roleId");
		}
		click("clickOnCalendarIconOnStatementsTab");
		int uIyear = Integer.parseInt(getValue("yearOnCalenderIconOnStatementsTab"));
		if (intYear > uIyear) {
			int i = (intYear - uIyear);
			for (int j = 0; j < i; j++) {
				click("nextOnCalenderIconOnStatementsTab");
			}
		}
		if (intYear < uIyear) {
			int i = (uIyear - intYear);
			for (int j = 0; j < i; j++) {
				click("priviousOnCalenderIconOnStatementsTab");
			}
		}
		List<WebElement> clcMonth = listOfElements("listMonthBtnCalendarIconOnStatementsTab");
		int month1 = Integer.parseInt(month);
		clcMonth.get(month1).click();
		click("clickOnViewButtonOnStatementsTab");
		waitForSomeTime(10);
		click("clickOnHoldOnStatementsTab");
		HashMap<String, String> onHoldGridValue = new HashMap<String, String>();

		onHoldGridValue.put("CERT", getValue(("listgetCertFromOnHoldGrid"), 1));
		onHoldGridValue.put("TRANS_DATE", getValue(("listgetTransDateFromOnHoldGrid"), 1));
		onHoldGridValue.put("DEALER_PAID", getValue(("listgetDEALER_PAIDFromOnHoldGrid"), 1));
		onHoldGridValue.put("PREMIUM", getValue(("listgetPRIMIUMFromOnHoldGrid"), 1));
		onHoldGridValue.put("DBCR_AMT", getValue((""), 1));
		onHoldGridValue.put("STD_COMMENTS", getValue(("listgetSTDCommFromOnHoldGrid"), 1));

		for (Map.Entry<String, String> entry : onHoldGridValue.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();
			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				onHoldGridValue.replace(key, "***");
			}
		}
		String sDate1 = onHoldGridValue.get("TRANS_DATE");
		if (sDate1.equalsIgnoreCase("***")) {
		}
		DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
		Date date = (Date) parser.parse(sDate1);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String newFormatDate = formatter.format(date);
		sDate1 = newFormatDate.toString();
		onHoldGridValue.replace("TRANS_DATE", sDate1);

		return onHoldGridValue;
	}

	/**
	 *
	 * / * This function fetches OnHold Popup Scrren Values
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> validateONholdPopupScreen() {

		int count = listOfElements("listviewBtnOnHoldGrid").size();
		List<WebElement> eleView = listOfElements("listviewBtnOnHoldGrid");
		eleView.get(1).click();
		waitForSomeTime(5);
		switchToNewWindow();
		HashMap<String, String> onHoldWindowValue = new HashMap<String, String>();
		onHoldWindowValue.put("AdjType", getValue("adjTextOnHoldPopup"));
		String firstCert = getValue("certTextOnHoldPopup");
		onHoldWindowValue.put("CERT", firstCert);
		onHoldWindowValue.put("DEALER_PAID", getValue("dealerPaiTextOnHoldPopup"));
		onHoldWindowValue.put("DEALER_ID", getValue("dealerIdTextOnHoldPopup"));
		onHoldWindowValue.put("DBCR_AMT", getValue("dBCRTextOnHoldPopup"));
		onHoldWindowValue.put("CHECK_NO", getValue("chechNoTextOnHoldPopup"));
		onHoldWindowValue.put("CHECK_AMT", getValue("checkAmountTextOnHoldPopup"));
		onHoldWindowValue.put("POST_PERIOD", getValue("postperiodTextOnHoldPopup"));
		onHoldWindowValue.put("PREMIUM", getValue("totalTextOnHoldPopup"));
		onHoldWindowValue.put("STD_COMMENTS", getValue("StdCommentsTextOnHoldPopup"));
		click("closeextrawindow");
		return onHoldWindowValue;
	}

	/**
	 *
	 * / * This function validate next btn on ONHOLD-Pop-up screen
	 * 
	 */
	@SuppressWarnings("unused")
	public boolean validatenextBtnOnHoldPopup() {
		boolean matchFlag = false;
		int count = listOfElements("listviewBtnOnHoldGrid").size();
		List<WebElement> eleView = listOfElements("listviewBtnOnHoldGrid");
		eleView.get(1).click();
		waitForSomeTime(5);
		switchToNewWindow();
		String secondCert = getValue("certTextOnHoldPopup");
		click("nextBtnOnHoldPopup");
		waitForSomeTime(3);
		String firstCert = getValue("certTextOnHoldPopup");
		if (firstCert.equalsIgnoreCase(secondCert)) {
			logger.info("Incorrect Value On Hold PopUP Screen");
			click("closeextrawindow");
		} else {
			matchFlag = true;
			click("closeextrawindow");
		}
		return matchFlag;
	}

	/**
	 *
	 * / * This function underwrite the OnHold contract
	 * 
	 */
	public void underWOnHoldContract(String onHoldCert) throws Exception {
		goToUnderWritingTab();
		goToHoldContactTab();
		waitForSomeTime(15);
		type("certFilterRowInOnholdContract", onHoldCert);
		waitForSomeTime(5);
		click("onHoldContractBtn");
		removeErrorMessages();
		try {
			click("VINDuplicate");
		} catch (Exception e) {
		}
		removeErrorMessages();
		try {
			click("contractExpander");
		} catch (Exception e) {
		}
		try {
			click("scrollContractsListDown");
		} catch (Exception e) {
			//// do nothing
		}
		premium();
		selectCheckAndScrollToTop();
		//// click under
		waitForSomeTime(15);
		click("clickUnderW");
		waitForSomeTime(15);
	}

	/**
	 *
	 * / * This function fills all detail like -Role Id , Role Type and Post period
	 * and search for related account Statement
	 * 
	 */

	public boolean accountStatementSearch(String postPeriod, String DealerId, String roleType) {
		boolean matchflag = false;
		String year = postPeriod.substring(0, 3);
		String month = postPeriod.substring(4, 5);
		int intYear = Integer.parseInt(year);
		click("selectDealerOrLenderOnStatementsTab");
		WebElement ele = windowsDriver.findElement(By.className("Popup"));
		List<WebElement> list = ele.findElements(By.className("ListBoxItem"));
		for (WebElement webElement : list) {
			String comboText = webElement.getText();
			if (comboText.equalsIgnoreCase(roleType)) {
				webElement.click();
			}
			type("typeDealerOrLenderIdOnStatementsTab", "roleId");
		}
		click("clickOnCalendarIconOnStatementsTab");
		int uIyear = Integer.parseInt(getValue("yearOnCalenderIconOnStatementsTab"));
		if (intYear > uIyear) {
			int i = (intYear - uIyear);
			for (int j = 0; j < i; j++) {
				click("nextOnCalenderIconOnStatementsTab");
			}
		}
		if (intYear < uIyear) {
			int i = (uIyear - intYear);
			for (int j = 0; j < i; j++) {
				click("priviousOnCalenderIconOnStatementsTab");
			}
		}
		List<WebElement> clcMonth = listOfElements("listMonthBtnCalendarIconOnStatementsTab");
		int month1 = Integer.parseInt(month);
		clcMonth.get(month1).click();
		click("clickOnViewButtonOnStatementsTab");
		waitForSomeTime(10);
		int dealerStatementGridItem = listOfElements("listdealerStatementsDataGridItem").size();
		if (dealerStatementGridItem > 6) {
			matchflag = true;
		}

		return matchflag;
	}

	/**
	 *
	 * This function used to fetch data fields value from mieageChange Row
	 */
	public HashMap<String, String> mileageChangeRowData(String AdjType) {
		click("scrollContractsListDown");
		click("clicknOnTotalUWAdjustmentsStatementsTab");
		click("clicknOnMilieageChangeStatementsTab");
		HashMap<String, String> mileageChangeRow = new HashMap<String, String>();
		mileageChangeRow.put("ITEM_COUNT", getValue("MilieageChangeRowCount"));
		mileageChangeRow.put("TOTAL", getValue("MilieageChangeRowTotal"));
		mileageChangeRow.put("DBCR", getValue("MilieageChangeRowDBCR"));
		mileageChangeRow.put("DLR_PAID", getValue("MilieageChangeRowDealerPaid"));
		mileageChangeRow.put("BALANCE", getValue("MilieageChangeRowBalance"));
		for (Map.Entry<String, String> entry : mileageChangeRow.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				mileageChangeRow.replace(key, "***");
			}
		}
		return mileageChangeRow;
	}

	/**
	 *
	 * This function used to fetch data fields value from PlanChange Row
	 */
	public HashMap<String, String> planChangeRowData(String AdjType) {
		click("scrollContractsListDown");
		click("clicknOnTotalUWAdjustmentsStatementsTab");
		click("clicknOnPlanChangeStatementsTab");
		HashMap<String, String> planChangeRow = new HashMap<String, String>();
		planChangeRow.put("ITEM_COUNT", getValue("planChangeRowCount"));
		planChangeRow.put("TOTAL", getValue("planChangeRowTotal"));
		planChangeRow.put("DBCR", getValue("planChangeRowDBCR"));
		planChangeRow.put("DLR_PAID", getValue("planChangeRowDealerPaid"));
		planChangeRow.put("BALANCE", getValue("planChangeRowBalance"));
		for (Map.Entry<String, String> entry : planChangeRow.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				planChangeRow.replace(key, "***");
			}
		}
		return planChangeRow;
	}

	/**
	 *
	 * This function used to change mileage
	 */
	@SuppressWarnings("unused")
	public void mileageChangeAdjustment(String cert) {
		goToUnderWritingTab();
		goToFindContractTab();
		processForAccountSearchForContractAdjustment(cert);
		HashMap<String, String> VINData = new HashMap<String, String>();
		int MILEAGE = Integer.parseInt(getTextOfElement("vinNumberMileage"));
		MILEAGE = MILEAGE + 20000;
		String miles = String.valueOf(MILEAGE);
		type("vinNumberMileage", miles);
		click("scrollContractsListDown");
	}

	/**
	 * This function is used to Return the data of order of cateogries from Account
	 * Adjustments
	 * 
	 * @return
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getOrderOfCategoriesFromUI() throws Exception {
		HashMap<Integer, HashMap<String, String>> searchDataForAllRow = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> searchData = new HashMap<String, String>();
		;
		List<WebElement> totalRowCount = listOfElementsByXpath("getOrderOfCategoriesValueFromAccountStatement");
		for (int i = 1; i <= totalRowCount.size(); i++) {
			searchData.put("ACCOUNT_DESCR", getValue("getOrderOfCategoriesValueFromAccountStatement", i).trim());
			searchDataForAllRow.put(i, searchData);
		}
		return searchDataForAllRow;
	}

	/**
	 * This function is used to return the cancel contract information from Account
	 * Statement Screen under cancellation grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getCancelledContractDetailsUnderCancellationGridFromUI() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("CUSTOMER_LAST",
				getAttributeValue("getLastNameForCancelledContractUnderCancellationGrid", "Name").trim());
		searchData.put("CONTRACT_NUMBER",
				getAttributeValue("getContractForCancelledContractUnderCancellationGrid", "Name").trim());
		searchData.put("REFUND_PERCENTAGE",
				getAttributeValue("getRefundForCancelledContractUnderCancellationGrid", "Name").trim());
		searchData.put("NET_REFUND",
				getAttributeValue("getAULRefundForCancelledContractUnderCancellationGrid", "Name").trim());
		searchData.put("CANCEL_DATE",
				getAttributeValue("getCancelDateForCancelledContractUnderCancellationGrid", "Name").trim());
		searchData.put("PROCESS_DATE",
				getAttributeValue("getProcessDateForCancelledContractUnderCancellationGrid", "Name").trim());
		return searchData;
	}

	/**
	 * This common function is used to click on view button under cancellation grid
	 * 
	 * @return
	 * 
	 */
	public void clickOnViewButtonUnderCancellationGrid() {
		try {
			click("clickViewButtonUnderCancellationGrid");
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This function is used to return the cancel contract information from Account
	 * Statement Screen under cancellation grid
	 * 
	 * @return
	 * 
	 */
	public HashMap<String, String> getNewCancellationDetailsFromAccountstatementUI() throws Exception {
		HashMap<String, String> searchData = new HashMap<String, String>();
		searchData.put("CONTRACT_NUMBER",
				getAttributeValue("getContractNumFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("INITIATED_BY",
				getAttributeValue("getIniatedByFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CANCEL_REASON",
				getAttributeValue("getCancelReasonFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CANCEL_MILES",
				getAttributeValue("getCancelMilesFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CANCEL_DATE",
				getAttributeValue("getCancelDateFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CUSTOMER_PAID",
				getAttributeValue("getCustomerPaidFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("PROCESS_DATE",
				getAttributeValue("getProcessDateFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CANCEL_METHOD",
				getAttributeValue("getCancelMethodFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("REFUND_PERCENTAGE",
				getAttributeValue("getRefundPercentageFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("GROSS_REFUND",
				getAttributeValue("getGrossRefundFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CANCEL_FEE", getAttributeValue("getcancelFeeFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("NET_REFUND", getAttributeValue("getNetRefundFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("DEALER_FINCO",
				getAttributeValue("getDealerFincoFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CUSTOMER_REFUND",
				getAttributeValue("getCustomerRefundFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CANCEL_STATUS",
				getAttributeValue("getCancelStatusFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("PAYEE", getAttributeValue("getPayeeFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("ADDRESS", getAttributeValue("getAddressFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("PAYEE_ZIP", getAttributeValue("getPayeeZipFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("STATE", getAttributeValue("getStateFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("INTERNAL_COMMENTS",
				getAttributeValue("getInternalCommentsFromNewCancelUnderAccountStatment", "Text").trim());
		searchData.put("CHECK_COMMENTS",
				getAttributeValue("getCheckCommentsFromNewCancelUnderAccountStatment", "Text").trim());
		return searchData;
	}

}
