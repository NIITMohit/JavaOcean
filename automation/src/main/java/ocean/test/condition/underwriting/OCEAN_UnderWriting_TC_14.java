package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_05 class automates Ocean Underwriting module Test
 * Condition 14 which holds 9 Test Case; Test Condition Description : Validate
 * the following for contract/ contract adjustment under OCEAN: 1. Correct
 * display for status, file name and remittance number. 2. Ability to input,
 * search and clear contract number. (Not applicable for contract adjustment) 3.
 * Ability to input purchase date in correct format only. 4. Check for contract
 * number duplicity.
 * 
 * least priority test case skiping in regression
 * 
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_UnderWriting_TC_14 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description : Validate the following for contract/ contract adjustment under
	 * OCEAN: 1. Correct display for status, file name and remittance number.
	 * 
	 */
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = "fullSuite", description = "Validate the following for contract/ contract adjustmen under OCEAN:1 Correct display for status,	file name	and remittance number.")
	public void correctDisplayForStatusFileNameRemittanceNumber() throws Exception {
		boolean flag = false;
		goToUnderWritingTab();
		goToRemittanceList();
		String remitName = getValue("remitNumber");
		expandRemiitence();
		lockAndViewContract();
		String fileName = getValue("UWFileName");
		String remitNumber = getValue("getValueRemittanceNumberFromContractAdjustment");
		HashMap<Integer, HashMap<String, String>> dbData = getContractStatus(remitNumber);
		for (Entry<Integer, HashMap<String, String>> letterEntry : dbData.entrySet()) {
			String aa = letterEntry.getValue().get("file_name");
			if (aa.toLowerCase().equals(fileName.toLowerCase())) {
				flag = true;
				break;
			}
		}
		assertEquals(flag, true);
		contractExpander();
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description :Validate Correct display for status, file name and remittance
	 * number after contract adjustment under Ocean.
	 * 
	 */
	@Test(priority = 2, groups = "fullSuite", description = "Validate the following for contract/ contract adjustment under OCEAN: 1. Correct displa  for status, file name and remittance number.")
	public void correctDisplayForStatusFileNameRemittanceNumberForContractAdjustmentdjustment() throws Exception {
		boolean flag = false;
		//// navigate underwriting tab
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		String remitNumber = getValue("getValueRemittanceNumberFromContractAdjustment");
		String fileName = getValue("UWFileName");
		HashMap<Integer, HashMap<String, String>> dbData = getContractStatus(remitNumber);
		for (Entry<Integer, HashMap<String, String>> letterEntry : dbData.entrySet()) {
			String aa = letterEntry.getValue().get("file_name");
			if (aa.toLowerCase().equals(fileName.toLowerCase())) {
				flag = true;
				break;
			}
		}
		contractExpander();
		assertEquals(flag, true);
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description :Validate user ability to input purchase date in correct format
	 * only after contract adjustment under Ocean.
	 * 
	 */
	@Test(priority = 3, groups = "fullSuite", description = "Validate user	 ability to input purchase date in correct format only after contract adjustment under Ocean.")
	public void correctInputPurchaseDateForContractrAdjustment() throws Exception {
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(1);
		clearTextBox("purchaseDateForNewContract");
		type("purchaseDateForNewContract", dtf.format(localDate).toString());
	    String newPurchaseDate = dtf.format(localDate).toString();
		click("primaryAccountType");
		String purchaseDate = getValue("purchaseDateForNewContract");
		System.out.println(purchaseDate);
		System.out.println(newPurchaseDate);
		 DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
			Date date = (Date) parser.parse(purchaseDate);
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String newFormatDate = formatter.format(date);
		String	purchaseDate1  = newFormatDate.toString() ;
	    System.out.println(purchaseDate1);
		assertEquals(newPurchaseDate, purchaseDate1);
		contractExpander();
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description : Validate the following for contract/ contract adjustment under
	 * OCEAN: 2. Ability to input, search and clear contract number. (Not applicable
	 * for contract adjustment)
	 */
	@Test(priority = 4, groups = "fullSuite", description = "Ability to input,	search and clear contract number. (Not applicable for contract adjustment)")
	public void inputSearchClearContractNumber() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			searchContractwithPendingState(remittName, fileName);
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
			click("clearContractData");
			//// type unique contract number
			type("typeContractNumber", randomString(10));
			click("clickSearchButtonToSearchContract");
			int count = getTextOfElement("typeContractNumber").length();
			if (count > 1) {
				click("clearContractData");
				assertEquals(0, getTextOfElement("typeContractNumber").length());
			} else
				throw new Exception("Not able to enter contract details successfully");
		} else
			throw new Exception("no remittance found");
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description : Validate that contract number already exists by Ocean post
	 * remittance loading under remittance List.
	 * 
	 */
	@Test(priority = 5, groups = "fullSuite", description = "Validate that	 contract number already exists by Ocean post remittance loading underremittance List.\r\n")
	public void duplicateContract() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
		String fileNameUI = getValue("UWFileName");
		click("clearContractData");
		//// type unique contract number
		String contractNo = fileNameUI.substring(0, fileNameUI.lastIndexOf(".pdf"));
		type("typeContractNumber", contractNo);
		click("clickSearchButtonToSearchContract");
		waitForSomeTime(10);
		String error = getValue("dupcontractError");
		click("clickOK");
		boolean err = error.toLowerCase().contains(contractNo.toLowerCase());
		contractExpander();
		assertEquals(true, err);
		} else
			throw new Exception("no remittance found");
	     }

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description :Validate user ability to input purchase date in correct format
	 * for a contract under Ocean.
	 * 
	 */
	@Test(priority = 6, groups = "fullSuite", description = "Validate user	 ability to input purchase date in correct format for a contract under Ocean.\r\n")
	public void correctInputDate() throws Exception {
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(1);
		clearTextBox("purchaseDateForNewContract");
		type("purchaseDateForNewContract", dtf.format(localDate).toString());
	    String newPurchaseDate = dtf.format(localDate).toString();
		click("primaryAccountType");
		String purchaseDate = getValue("purchaseDateForNewContract");
		System.out.println(purchaseDate);
		System.out.println(newPurchaseDate);
		 DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
			Date date = (Date) parser.parse(purchaseDate);
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String newFormatDate = formatter.format(date);
		String	purchaseDate1  = newFormatDate.toString() ;
	    System.out.println(purchaseDate1);
		assertEquals(newPurchaseDate, purchaseDate1);
		contractExpander();
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description :Validate user ability to input purchase date in correct format
	 * for a contract under Ocean.
	 * 
	 */
	@Test(priority = 7, groups = "fullSuite", description = "Validate user ability to input purchase date in correct format for a contract under Ocean.\r\n")
	public void correctInputDateGreaterThenTodayDate() throws Exception {
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
			lockAndViewContract();
			click("typeContractNumber");
			takeScreenshot();
			type("typeContractNumber", randomString(10));
			click("clickSearchButtonToSearchContract");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate localDate = LocalDate.now().plusDays(2);
			type("purchaseDateForNewContract", dtf.format(localDate).toString());
			click("primaryAccountType");
			assertEquals("Please enter correct purchase date to save this contract".toLowerCase(),
					getValue("dateError").toLowerCase());
			takeScreenshot();
			removeErrorMessages();
			click("contractExpander");
			System.out.println("asf");
		} else {
			new SkipException("no remittance found");
		}
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description :Validate that purchase date is already entered by Ocean post
	 * remittance loading.
	 * 
	 */
	@Test(priority = 8, groups = "fullSuite", description = "Validate that	purchase dateis already	entered by 	Ocean post	remittance loading.")
	public void preFilledPurchaseDate() throws Exception {
		HashMap<String, String> contractFromRemittance = underWDocument();
		if (contractFromRemittance.size() > 0) {
			String remitName = contractFromRemittance.get("RemittanceName");
			String fileName = contractFromRemittance.get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			searchContractwithPendingState(remitName, fileName);
			lockAndViewContract();
			String fate = getValue("purchaseDateForNewContract");
			String regex = "^[0-1]?[0-9]-[0-3]?[0-9]-(?:[0-9]{4})$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(fate);
			assertEquals(true, matcher.matches());
		} else {
			throw new Exception("no contract found");
		}
		contractExpander();
	}

	/**
	 * This function automates all test cases for test condition 14; Test Case
	 * description :Validate user ability to input purchase date in correct format
	 * only after contract adjustment under Ocean.
	 * 
	 */
	 @Test(priority = 9, groups = "fullSuite", description = "Validate user	 ability to input purchase date in correct format only after contract djustment under Ocean.")
	public void modifyInputDateContractAdjustment() throws Exception {
		goToUnderWritingTab();
		//// go to find contract to search the contract
		goToFindContractTab();
		//// get the processed contract for contract adjustment
		String contract = cancellation_getContractIdBasedOnStatus("Active");
		//// enter mandatory fields in contract adjustment for account search
		processForAccountSearchForContractAdjustment(contract);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(1);
		clearTextBox("purchaseDateForNewContract");
		type("purchaseDateForNewContract", dtf.format(localDate).toString());
	    String newPurchaseDate = dtf.format(localDate).toString();
		click("primaryAccountType");
		String purchaseDate = getValue("purchaseDateForNewContract");
		System.out.println(purchaseDate);
		System.out.println(newPurchaseDate);
		 DateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
			Date date = (Date) parser.parse(purchaseDate);
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String newFormatDate = formatter.format(date);
		String	purchaseDate1  = newFormatDate.toString() ;
	    System.out.println(purchaseDate1);
		assertEquals(newPurchaseDate, purchaseDate1);
		contractExpander();
	}
}
