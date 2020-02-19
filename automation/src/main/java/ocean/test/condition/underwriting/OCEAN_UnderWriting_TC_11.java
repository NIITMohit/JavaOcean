package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map.Entry;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * OCEAN_Cancel_TC_11 class automates Ocean Underwriting module Test Condition
 * 11, which holds 10 Test Cases; Test Condition Description : Validate that
 * user is able to perform following on a remittance under remittance enquiry:
 * 1. View/Delete uploaded PDF. 2. Upload new PDFs. 3. Assign document type to
 * uploaded document. 4. Apply filter on document type. 5. Save changes to
 * remittances. 6. View contract before/after remittance posting. 7. Block
 * remittance posting till all till all contract are processed by OCEAN
 * (including not issued, but put on hold/returned) 8. Select and open contract
 * in OCEAN.
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_UnderWriting_TC_11 extends UnderwritingModulePages {

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 1. View/Delete uploaded PDF.
	 */
	@Test(priority = 1, groups = "regression", description = "Validate that user is able to perform on a remittance under remittance enquiry: 1.View/Delete uploaded PDF.")
	public void viewAndDeletePdf() throws Exception {
		goToUnderWritingTab();
		viewAndDeletePDF();
		boolean flag = true;
		try {
			click("closePDF");
			flag = false;
		} catch (Exception e) {
			System.out.println("PDF is not close");

		}
		assertEquals(flag, true);
	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 2. Upload new PDFs.
	 */
	@Test(priority = 2, groups = "regression", description = "Validate that user is able to perform on a remittance under remittance enquiry: 2.Upload new PDFs.")
	public void uplocadNewPDF() throws Exception {
		//// Defect lock in Defect tracker.Defect id 8
		goToUnderWritingTab();
		goToRemittanceList();
		uploadPdfByDragAndDrop();
		boolean flag = false;
		try {
			click("clickOnPDF");
			flag = true;
		} catch (Exception e) {
			System.out.println("Drag and drop function is working");
		}
		assertEquals(flag, true);
	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 3. Assign document type to uploaded document.
	 */
	@Test(priority = 3, groups = "regression", description = "Validate that user is able to perform on a remittance under remittance enquiry: 3.Assign document type to uploaded document.")
	public void assignDocumentToUploadPDF() throws Exception {
		//// Defect lock in Defect tracker.Defect id 8
		goToUnderWritingTab();
		goToRemittanceList();
		click("expandRemittance");
		assignDocumentsStatus(1);
		click("saveRemittance");
		click("expandRemittance");
		boolean flag = false;
		try {
			click("clickOnDocumentTYpeForFilter");
			flag = true;
		} catch (Exception e) {
			System.out.println("Apply filter is clicked");
		}
		assertEquals(flag, true);

	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 4. Apply filter on document type.
	 */
	@Test(priority = 4, groups = "regression", description = "Validate that user is able to perform on a remittance under remittance enquiry: 4.Apply filter on document type.")
	public void applyFilterOnDocumentType() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		applyFilterOnDocument();
		waitForSomeTime(2);
		click("expandRemittance");
		boolean flag = true;
		try {
			click("clickOnDocumentTYpeForFilter");
			flag = false;
		} catch (Exception e) {
			System.out.println("Apply filter is not clicked");
			assertEquals(flag, true);
		}

	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 5. Save changes to remittances.
	 */
	@Test(priority = 5, groups = "regression", description = "Validate that user is able to perform on a remittance under remittance enquiry: 5.Save changes to remittances.")
	public void saveChangesOnRemittanceForNewUploadedPdf() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		//// Defect lock in Defect tracker.Defect id 8
		click("saveRemittance");
		click("expandRemittance");

		boolean flag = true;
		try {
			click("clickOnDocumentTYpeForFilter");
			flag = false;
		} catch (Exception e) {
			System.out.println("Apply filter is clicked");
		}
		assertEquals(flag, true);

	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 6. View contract before/after remittance posting.
	 */
	@Test(priority = 7, groups = "regression", dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = "Validate that user is able to perform on a remittance under remittance enquiry: 6.View contract before/after remittance posting.")
	public void viewContractBeforeAfterRemittancePosting(String[] inputData) throws Exception {
		//// create remittance with documents count as 1
		goToUnderWritingTab();
		landToCreateRemittanceDetailsPage();
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
		HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
		premiumData.putAll(sss);
		premiumData.put("PrimaryAccount", "Dealer");
		premiumData.put("SecondaryAccount", "Lender");
		premiumData.put("SecondaryAccountId", "24");
		if (sss.size() > 1) {
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
			//// Get AUL Premium
			String premium = premium();

			String finalValue = calculateMyPremium(premiumData);

			assertEquals(premium, finalValue);

			//// post remittance and verify

		} else {
			new SkipException("no actual value exist for combination feeded in excel as test data");
		}
	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 7. Block remittance posting till all till all contract are processed by
	 * OCEAN (including not issued, but put on hold/returned)
	 */
	@Test(priority = 6, groups = "regression", dataProvider = "fetchDataForTC01_02_03_04", dataProviderClass = UnderwritingDataProvider.class, description = "Validate that user is able to perform on a remittance under remittance enquiry: 7.Block remittance posting")
	public void validateBlockRemittancePosting(String[] inputArray) throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceMandatoryValues("1");
		if (remittanceName.length() > 0) {
			//// search remittance
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(2);
			///// Update check status
			addCheck("1111", "111");
			//// Refresh remittance
			refreshRemittance();
			//// Search remittance and file name and issue contract
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			if (remitt.size() > 0) {
				for (Entry<Integer, HashMap<String, String>> letterEntry : remitt.entrySet()) {
					HashMap<String, String> value = letterEntry.getValue();
					String remitName = value.get("RemittanceName");
					String fileName = value.get("FILE_NAME");
					//// visit underwriting tab
					waitForSomeTime(10);
					goToUnderWritingTab();
					//// Search a contract with pending state, remittance name and contract name is
					//// fetched from database
					searchContractwithPendingState(remitName, fileName);
					//// lock contract on user name and open enter values in contract window
					lockAndViewContract();
				}
			} else {
				new Exception("not able to crate remittancce");
			}

		} else {
			new Exception("not able to crate remittancce");
		}
		blockRemittancePosting();
		///// Defect no 12 is locked for pending contract
	}

	/**
	 * Validate that user is able to perform following on a remittance under
	 * remittance enquiry: this function cover all the condition of this test cases
	 * like 8. Select and open contract in OCEAN.
	 */
	@Test(priority = 8, groups = "regression", description = "Validate that user is able to perform on a remittance under remittance enquiry: 8.Select and open contract in OCEAN")
	public void selectAndOpenContractInOCEAN() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		click("expandRemittance");
		openContractByPDFInNewWindow();
		click("saveRemittance");
		boolean flag = true;
		try {
			click("closePDF");
			flag = false;
		} catch (Exception e) {
			System.out.println("PDF is close");
			assertEquals(flag, true);
		}

	}

}
