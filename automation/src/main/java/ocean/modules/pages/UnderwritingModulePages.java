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
		//// expand remittance to get contracts
		click("expandRemittance");
		//// type filename
		type("typeContract", fileName);
		//// click view contract
	}

	public void searchRemittance(String remittName) throws Exception {
		//// Type RemittanceName
		type("typeToSearchRemittance", remittName);
		click("expandRemittance");
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
		click("contractExpander");
		waitForSomeTime(2);
		click("contractExpander");
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
	 * This function is used to assign Documents status to newly created remittance
	 * 
	 */
	public void assignDocumentsStatus(int documentsasContract) throws Exception {
		Actions action = new Actions(windowsDriver);
		action.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
		List<WebElement> documentStatus = listOfElements("documentsStatus");
		int count = 1;
		for (WebElement webElement : documentStatus) {
			if (count <= documentsasContract)
				webElement.click();
			count++;
		}
		click("saveRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// do nothing
		}
		click("clickOK");
		System.out.println("jhje");
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
		click("remittanceExpander");
		waitForSomeTime(2);
		click("remittanceExpander");
		//// click view to open folder explorer
		click("viewInToolbar");
		//// click Folder explorer to upload files
		click("folderExplorer");
		waitForSomeTime(4);
	}

	/**
	 * This function is used to drag and drop necessary remittance files
	 * 
	 */
	public void dragAndDropFiles() throws Exception {
		//// drag and drop files
		dragAndDrop("filesSourcePath", "filesDestinationPath");
		System.out.println("dd");
	}

	/**
	 * This function is used to enter mandatory values in create remittance
	 * 
	 */
	public String enterRemittanceMandatoryValues() throws Exception {
		//// Type Remittance Name
		String remittanceName = "";
		try {
			remittanceName = randomString(20);
			type("remittanceName", remittanceName);
			//// ENter core count
			type("remittanceCoreCount", "2");
			//// select remit type
			typeKeys("remittanceTypeCombobox", "Standard");
			typeKeys("remittanceRemitTypeComboBox", "Paper Remit");
			typeKeys("remittanceSubTypeComboBox", "Dealer AGs");
			//// type comments
			type("remittanceComments", "Testing 123");
			//// click save
			click("clickSave");
			//// close file explorer
			click("closeFolderExplorer");
			//// close remitance
			click("remittanceExpander");
		} catch (Exception e) {
			remittanceName = "";
		}
		return remittanceName;
	}
}
