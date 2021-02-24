package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_T_248_251 class automates Ocean Underwriting module Test
 * Condition 48, which holds 4 Test Cases; Test Condition Description : Validate
 * OCEAN ability to display correct contract details under search result for on
 * hold contracts only and on the basis of search parameter given and user is
 * able to perform following on a ONHOLD contract from on hold contract enquiry:
 * 1. Load contract 2. Load check 3. Assign document type 4. Save changes
 * 
 * @author Atul Awasthi
 */

public class OCEAN_UnderWriting_TC_48 extends UnderwritingModulePages {
	/**
	 * Validate that OCEAN user is able to Load contract on a ONHOLD contract from
	 * on hold contract enquiry and save the changes.
	 */
	@Test(priority = 6, groups = "fullSuite", description = "Validate that OCEAN user is able to Load contract on a ONHOLD contract from on hold contract enquiry and save the changes")
	public void verifyContractLoadAndSaveChanges() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("OnHOLD");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToHoldContactTab();
			waitForSomeTime(5);
			type("certFilterRowInOnholdContract", contractId);
			waitForSomeTime(5);
			click("editSaveButtonInOnholdContract");
			waitForSomeTime(10);
			String underwritingComment = "Updated Comments";
			System.out.println("underwritingComment==" + underwritingComment);
			type("underwritingCommentBoxInOnholdContract", underwritingComment);
			click("editSaveButtonInOnholdContract");
			click("loadOnHoldContractInOnholdContract");
			waitForSomeTime(5);
			try {
				click("clickOK");
			} catch (Exception e) {
				// Do nothing
			}
			contractExpander();
			String fileName = getTextOfElement("contractFileNameInNewBusinessForm");
			String comments = getTextOfElement("commentBoxInNewBusinessForm");
			assertTrue(comments.contains(underwritingComment) && fileName.contains(contractId),
					"Contract not loaded and comments not updated");
		} else {
			new SkipException("no value in DB exists");
		}
	}

	/**
	 * Validate that OCEAN user is able to assign document type on a ONHOLD contract
	 * from on hold contract enquiry
	 */
	@Test(priority = 6, groups = "fullSuite", description = "Validate that OCEAN user is able to assign document type on a ONHOLD contract from on hold contract enquiry")
	public void verifyDocumentAssigntment() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractIds = searchOnHoldContractWithremittance();
		Random generator = new Random();
		int number = generator.nextInt(contractIds.size() - 1) + 1;
		String contractId = contractIds.get(number).get("Contract_Number");
		System.out.println("roleid====" + contractId);

		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToHoldContactTab();
			waitForSomeTime(5);
			type("certFilterRowInOnholdContract", contractId);
			waitForSomeTime(5);
			click("expandContractInOnHold");
			click("editSaveButtonInOnholdContract");
			click("firstDocumentDropdown");// or type, but due to OR missing, on Hold
			click("editSaveButtonInOnholdContract");
			click("expandcontractdetail");
			click("removeContractId");
			waitForSomeTime(10);
			type("certFilterRowInOnholdContract", contractId);
			click("expandcontractdetail");
			getTextOfElement("firstDocumentDropdown");
		} else {
			new SkipException("no value in DB exists");
		}
	}

	/**
	 * Validate that OCEAN user is able to Load check on a ONHOLD contract from on
	 * hold contract enquiry
	 */
	@Test(priority = 6, groups = "fullSuite", description = "Validate that OCEAN user is able to Load check on a ONHOLD contract from on hold contract enquiry")
	public void verifyLoadCheck() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractIds = searchOnHoldContractWithremittance();
		Random generator = new Random();
		int number = generator.nextInt(contractIds.size() - 1) + 1;
		String contractId = contractIds.get(number).get("Contract_Number");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToHoldContactTab();
			waitForSomeTime(5);
			type("certFilterRowInOnholdContract", contractId);
			waitForSomeTime(5);
			click("expandContractInOnHold");
			List<WebElement> fileNames1 = listOfElements("getFileNamesInOnHold");
			List<String> fileNamesInOnHold = new ArrayList<String>();
			for (WebElement name : fileNames1) {
				fileNamesInOnHold.add(name.getText());
			}
			System.out.println("fileNames1==" + fileNamesInOnHold);
			click("loadOnHoldContractInOnholdContract");
			waitForSomeTime(20);
			click("loadCheckInOnHold");
			waitForSomeTime(10);
			contractExpander();
			List<WebElement> fileNames2 = listOfElements("getFileNamesInChecksTab");
			List<String> fileNamesInCheckstab = new ArrayList<String>();
			for (WebElement name : fileNames2) {
				fileNamesInCheckstab.add(name.getText());
			}
			System.out.println("fileNames2==" + fileNamesInCheckstab);
			assertTrue(fileNamesInOnHold.containsAll(fileNamesInCheckstab), "Checks detail not matching");
		} else {
			new SkipException("no value in DB exists");
		}
	}
}
