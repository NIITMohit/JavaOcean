package ocean.modules.pages;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ocean.common.ObjectRepo;
import ocean.modules.database.UnderwritingDataBase;

/**
 * This is object class which contains all pages of underwriting modules
 * 
 * @author Mohit Goel
 */
public class UnderwritingModulePages extends UnderwritingDataBase {

	/**
	 * This function is used to land to search contract with pending state
	 * 
	 * @return
	 * 
	 */
	public void searchContractwithPendingState(String remittName, String fileName) throws Exception {
		//// Type RemittanceName
		searchRemittance(remittName);
		//// type filename
		type("typeContract", fileName);
		//// click view contract
	}

	/**
	 * This function is used to land to applyFiltersOnInputField
	 * 
	 * @return
	 * 
	 */
	public boolean applyFiltersOnInputFieldAndVerifyData(String filterName, String filterExpectedValue)
			throws Exception {
		try {
			click("scrollContractsListDown", 2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		boolean flag = false;
		switch (filterName) {
		case "Remittance_Name":
			try {
				click("swipeLeft", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchRemittance", filterExpectedValue);
			String old = getValue("remitName");
			String compare = filterExpectedValue;
			flag = old.toLowerCase().equals(compare.toLowerCase());
			type("typeToSearchRemittance", "");
			break;
		case "Post_Period":
			try {
				click("swipeLeft", 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchPostPeriod", filterExpectedValue);
			String old1 = getValue("remitPostPeriod");
			String compare1 = filterExpectedValue;
			flag = old1.toLowerCase().equals(compare1.toLowerCase());
			type("typeToSearchPostPeriod", "");
			break;
		case "Remittance_Number":
			try {
				click("swipeLeft", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchRemittanceNumber", filterExpectedValue);
			String old11 = getValue("remitNumber");
			String compare11 = filterExpectedValue;
			flag = old11.toLowerCase().equals(compare11.toLowerCase());
			type("typeToSearchRemittanceNumber", "");
			break;
		case "Source":
			try {
				click("swipeLeft", 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
			}
			typeKeys("typeToSearchSource", filterExpectedValue);
			String old111 = getValue("remitSource");
			String compare111 = filterExpectedValue;
			flag = old111.toLowerCase().equals(compare111.toLowerCase());
			click("typeToSearchSourceClear");
			break;
		case "Sub_Type":
			try {
				click("swipeRight", 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			typeKeys("typeToSearchSubType", filterExpectedValue);
			String old2 = getValue("remitSubType");
			String compare2 = filterExpectedValue;
			flag = old2.toLowerCase().equals(compare2.toLowerCase());
			click("typeToSearchSubTypeClear");
			break;
		case "Type":
			try {
				click("swipeLeft", 1);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			typeKeys("typeToSearchType", filterExpectedValue);
			String old21 = getValue("remitType");
			String compare21 = filterExpectedValue;
			flag = old21.toLowerCase().equals(compare21.toLowerCase());
			click("typeToSearchTypeClear");
			break;
		case "Core":
			try {
				click("swipeLeft", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchCoreCount", filterExpectedValue);
			String old211 = getValue("remitCore");
			String compare211 = filterExpectedValue;
			flag = old211.toLowerCase().equals(compare211.toLowerCase());
			type("typeToSearchCoreCount", "");
			break;
		case "LWA":
			try {
				click("swipeLeft", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchLWACount", filterExpectedValue);
			String old3 = getValue("remitLWA");
			String compare3 = filterExpectedValue;
			flag = old3.toLowerCase().equals(compare3.toLowerCase());
			type("typeToSearchLWACount", "");
			break;
		case "UnderW":
			try {
				click("swipeLeft", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchUnderW", filterExpectedValue);
			String old31 = getValue("remitUnderW");
			String compare31 = filterExpectedValue;
			flag = old31.toLowerCase().equals(compare31.toLowerCase());
			type("typeToSearchUnderW", "");
			break;
		case "Comment":
			try {
				click("swipeRight", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchComment", filterExpectedValue);
			String old311 = getValue("remitComment");
			String compare311 = filterExpectedValue;
			flag = old311.toLowerCase().equals(compare311.toLowerCase());
			type("typeToSearchComment", "");
			break;
		case "Created_By":
			try {
				click("swipeRight", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchCreatedBy", filterExpectedValue);
			String old3111 = getValue("remitCreatedBy");
			String compare3111 = filterExpectedValue;
			flag = old3111.toLowerCase().equals(compare3111.toLowerCase());
			type("typeToSearchCreatedBy", "");
			break;
		case "Locked_By":
			try {
				click("swipeRight", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchLockedBy", filterExpectedValue);
			String old4 = getValue("remitLockedBy");
			String compare4 = filterExpectedValue;
			flag = old4.toLowerCase().equals(compare4.toLowerCase());
			type("typeToSearchLockedBy", "");
			break;
		case "Created_Date":
			try {
				click("swipeRight", 1);

			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				click("scrollContractsListUp", 2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			type("typeToSearchCreatedDate", filterExpectedValue);
			String old41 = getValue("remitCreatedDate");
			String compare41 = filterExpectedValue;
			flag = old41.toLowerCase().equals(compare41.toLowerCase());
			type("typeToSearchCreatedDate", "");
			break;
		default:
			flag = false;
			break;
		}

		return flag;
	}

	/**
	 * This function is used to view PDF, View PDF in new window and delete PDF
	 * 
	 */
	public void viewPDFandDeletePDF() throws Exception {
		click("clickOnPDF");
		click("closePDF");
		waitForSomeTime(2);
		rightClick("clickOnPDF");
		click("clickOnDeletePdf");
		click("clickOK");
	}

	/**
	 * This function is used to apply filter on documents like contract, check
	 * ,remit,miscellaneous,web contract etc
	 * 
	 */
	public void applyFilterOnDocument() throws Exception {
		type("clickOnDocumentTYpeForFilter", "Contract");
		click("saveRemittance");

	}

	public void searchRemittance(String remittName) throws Exception {
		//// Type RemittanceName
		type("typeToSearchRemittance", remittName);
		//// expand remittance to get contracts
		click("expandRemittance");
	}

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

	/**
	 * This function is used to verify pdf status
	 * 
	 */
	public boolean thumbNailOfUploadedPDFOnTopRemittance() throws Exception {
		boolean flag = true;
		List<WebElement> we = listOfElements("remittancePDFLogo");
		if (we.size() < 1) {
			flag = false;
		}
		for (WebElement webElement : we) {
			String ss = webElement.getAttribute("AutomationId");
			if (!ss.toLowerCase().equals("ViewPDFButton")) {
				flag = false;
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
	 * This function is used to lock and view contract post contract search
	 * 
	 */
	public void lockAndViewContract() throws Exception {
		click("viewContract");
		try {
			//// click yes to lock remittance
			click("lockContractYesButton");

		} catch (Exception e) {
			// do nothing
		}
		//// click contract expander
		contractExpander();
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
		click("clickCheckCheckBox");
		for (int i = 0; i < 2; i++) {
			try {
				click("scrollContractsListUp");
				continue;
			} catch (Exception e) {
				continue;
			}

		}
	}

	/**
	 * This function is used to select check
	 * 
	 */
	public void postRemittance() throws Exception {
		click("remittanceSummary");
		click("remittancePost");
		contractExpander();
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
	 * This function is used to clear data on new business form
	 * 
	 */
	public void clearPreFilledData() throws Exception {
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
	public void assignDocumentsStatus(int documentsasContract) throws Exception {
		//// after remittance search it will come to documents
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
		//// click document drop down
		action.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).build().perform();
		///// select document type as contract for documentsasContract
		for (int i = 0; i < documentsasContract; i++) {
			action.sendKeys(Keys.ARROW_RIGHT).build().perform();
			action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_LEFT).build().perform();
		}
		//// select document type as check
		action.sendKeys(Keys.F4).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).sendKeys(Keys.TAB).build().perform();
		//// save remittance
		click("typeToSearchRemittance");
		click("saveRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// do nothing
		}
		click("clickOK");
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

	/**
	 * This function is used to get add check
	 * 
	 */
	public void addCheck(String checkNumber, String checkAmount) throws Exception {
		click("typeToSearchRemittance");
		click("loadRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			//// do nothing
		}
		contractExpander();
		goToCheckTab();
		type("checkTabCheckNumber", checkNumber);
		type("checkTabCheckAmount", checkAmount);
		click("saveAllOnRemittance");
		click("clickOK");
		contractExpander();
		// click("contractExpander");
	}

	/**
	 * This function is used to refresh remittance
	 * 
	 */
	public void refreshRemittance() throws Exception {
		waitForSomeTime(1);
		try {
			click("scrollContractsListUp", 1);
		} catch (Exception e) {
			//// do nothing
		}
		click("refreshRemittance");
		//// wait
		for (int i = 0; i < 10; i++) {
			try {
				click("waitBusyIndicator");
				waitForSomeTime(10);
				continue;
			} catch (Exception e) {
				waitForSomeTime(10);
				break;
			}
		}
	}

	/**
	 * This function is used to view contract by clicking on the view contract icon
	 * before remittance posting
	 * 
	 */
	public void viewContractBeforeContractPosting(String remittName) throws Exception {
		//// Type RemittanceName
		type("typeToSearchRemittance", remittName);
		//// expand remittance to get contracts
		click("expandRemittance");
		click("viewContract", 1);
		;
		contractExpander();
	}

	/**
	 * This function is used to get surcharges
	 * 
	 */
	public String calculateMyPremium(HashMap<String, String> premiumData) throws Exception {
		String finalValue = "";
		waitForSomeTime(2);
		try {
			String excelPremium = getPremiumCalculation(premiumData);
			String surcharge = "0.00";
			String options = "0.00";
			String deduc = "0.00";
			String expPre = "0.00";
			if (premiumData.get("SURCHARGES").toLowerCase().equals("y")
					&& !premiumData.get("SURCHARGESAMOUNT").equals("0"))
				surcharge = premiumData.get("SURCHARGESAMOUNT").substring(
						premiumData.get("SURCHARGESAMOUNT").indexOf("$") + 1,
						premiumData.get("SURCHARGESAMOUNT").length());

			if (premiumData.get("OPTIONS").toLowerCase().equals("y") && !premiumData.get("OPTIONSAMOUNT").equals("0"))
				options = premiumData.get("OPTIONSAMOUNT").substring(premiumData.get("OPTIONSAMOUNT").indexOf("$") + 1,
						premiumData.get("OPTIONSAMOUNT").length());

			if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y")
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
			throw new Exception("not able to paerse");
		}
		return finalValue;
	}

	/**
	 * This function is get AUL premim
	 * 
	 */
	public String premium() throws Exception {
		click("clickPremiumCalculate");
		return getAttributeValue("getPremium", "Name");
	}

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
	 * This function is used to bget all data fields for remittance information
	 * under remittance list
	 * 
	 */
	public HashMap<String, String> getAllDataUnderRemittanceList() {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("PostPeriod", getValue("getPostPeriodValue"));
		dataMap.put("RemittanceNumber", getValue("remitNumber"));
		dataMap.put("RemittanceName", getValue("remitName"));
		dataMap.put("UnderwritingCount", getValue("remitUWCount"));
		dataMap.put("HoldCount", getValue("remitHolds"));
		dataMap.put("Source_Type", getValue("remitSource"));
		dataMap.put("Subtype_Name", getValue("remitSubType"));
		dataMap.put("name", getValue("remitType"));
		dataMap.put("RemitType", getValue("UVremitType"));
		dataMap.put("corecount", getValue("remitCore"));
		dataMap.put("lwacount", getValue("remitLWA"));
		click("swipeRight");
		dataMap.put("comments", getValue("getCommentsOnRemittanceScreen"));
		dataMap.put("CreateByDate", getValue("getCreatedDate"));
		dataMap.put("Locked_by", getValue("getLockedByValue"));
		dataMap.put("CreateByUser", getValue("getCreatedByOnRemittanceScreen"));
		click("swipeLeft");
		return dataMap;

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
		HashMap<String, String> ss = new HashMap<String, String>();
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
		type("primaryAccountId", premiumData.get("DEALERID"));
		click("primaryAccountSearchButton");
		//// Enter Secondary Account Details
		type("secondaryAccountType", premiumData.get("SecondaryAccount"));
		type("secondaryAccountId", premiumData.get("SecondaryAccountId"));
		click("secondaryAccountSearchButton");
		//// Enter VIN Details
		type("vinNumber", premiumData.get("VIN"));
		String contractSummary = "0";
		try {
			contractSummary = getAttributeValue("vinNumberOverride", "Toggle.ToggleState");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (contractSummary.toLowerCase().equals("0"))
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
		//// navigate to price sheet and select price sheet
		waitForSomeTime(5);
		type("selectPricesheet", premiumData.get("PRICESHEETID"));
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
		dd.put("RemittanceName", getValue("remitName"));
		dd.put("corecount", getValue("remitCore"));
		dd.put("lwacount", getValue("remitLWA"));
		dd.put("Source_Type", getValue("remitSource"));
		dd.put("Subtype_Name", getValue("remitSubType"));
		dd.put("name", getValue("remitType"));
		dd.put("Paper", getValue("UVremitType"));
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
		rightClick("remitName");
		click("deleteremittance");
		click("lockContractYesButton");

	}

	/**
	 * This function is used to drag and drop necessary remittance files
	 * 
	 */
	public void dragAndDropFiles() throws Exception {
		//// drag and drop files
		dragAndDrop("filesSourcePath", "filesDestinationPath");
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
			click("clickSave");

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
			if (inputArray[8] != null && inputArray[9] != null) {
				type("addCheckOnRemittance", inputArray[8]);
				type("addCheckAmtOnRemittance", inputArray[9]);
				click("clickAddCheckAmtOnRemittance");
			}
			//// click save
			String clickState = getAttributeValue("clickSave", "IsEnabled");
			if (clickState.toLowerCase().equals("true"))
				click("clickSave");
			else
				return remittanceName;
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
	public void enterRemittanceValueswithoutSave(String[] inputArray) throws Exception {
		//// Type Remittance Name
		type("remittanceName", inputArray[0]);
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
}
