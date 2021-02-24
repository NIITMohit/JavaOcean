package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_PBI_15865 class automates new PBI 15865, which holds 6
 * Test Case; Test Condition Description : Validate that Comment is added when
 * Ford F-150 vehicles are decoded when contracts are Externally Added.
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Underwriting_TC_71 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01,03, 04; Test
	 * Case description : Verify for web/ACH/ACH Push source remittance that comment
	 * is added automatically for web contract with Ford F150 vehicle, when they are
	 * saved on Add external remittance screen.
	 * 
	 */

	public String createRemittance(String[] inputData) throws Exception {
		copyFilesWorkingRemittance();
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// verify drag and drop status for pdf
		String remittanceName = enterRemittanceValues(inputData);
		return remittanceName;
	}

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI_15865", dataProviderClass = UnderwritingDataProvider.class, description = "Verify for web/ACH/ACH Push source remittance that comment is added automatically for web contract with Ford F150 vehicle, when they are saved on Add external remittance screen")
	public void validateCommentForFord150Vehicle(String[] inputData) throws Exception {
		String remittanceName = createRemittance(inputData);
		// String remittanceName = "WebSur001";
		if (remittanceName.length() > 0) {
			refreshRemittance();
			try {
				searchRemittance(remittanceName);
			} catch (Exception e) {
				waitForSomeTime(30);
				searchRemittance(remittanceName);
			}
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(0);
			///// code to add external remittance
			waitForSomeTime(10);
			addCheck();
			addExternalRemittanceWithSelectedContractNumber("Ford F150");

			// Refresh remittance
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = getExternalWebContractsFromRemittanceName(
					remittanceName);
			try {
				searchContractAddedViaExternalContract(remitt.get(1).get("RemittanceName"),
						remitt.get(1).get("FILE_NAME"));
			} catch (Exception e) {
				waitForSomeTime(30);
				searchContractAddedViaExternalContract(remitt.get(1).get("RemittanceName"),
						remitt.get(1).get("FILE_NAME"));
			}
			lockAndViewContract();
			String message = getValidationForFordF150Vehicle();
			takeScreenshot();
			click("clickOK");
			String actualMessage = "This vehicle is a Ford F-150, verify trim level for eligibility.";
			assertEquals(message.toLowerCase(), actualMessage.toLowerCase());
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			String commentsNewBusForm = getTextOfElement("commentBoxInNewBusinessForm");
			System.out.println("commentsNewBusForm : " + commentsNewBusForm);
			takeScreenshot();
			assertEquals(commentsNewBusForm.contains("Verify Ford F-150"), true);
			goToRemittanceSummary();
			click("remSummarySwipeRight");
			String commentsRemSumm = getValue("getCommentsFromRemittanceSummryPage");
			System.out.println("commentsRemSumm : " + commentsRemSumm);
			takeScreenshot();
			assertEquals(commentsRemSumm.contains("Verify Ford F-150"), true);
			click("remSummarySwipeLeft");
		} else
			throw new Exception("Remittance creation failed");
	}

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForPBI_15865", dataProviderClass = UnderwritingDataProvider.class, description = "Verify for web/ACH/ACH Push source remittance that comment is not added automatically for web contract with Non Ford F150 vehicle, when they are saved on Add external remittance screen")
	public void validateCommentForNonFord150Vehicle(String[] inputData) throws Exception {
		String remittanceName = createRemittance(inputData);
		// String remittanceName = "WebSur001";
		if (remittanceName.length() > 0) {
			refreshRemittance();
			try {
				searchRemittance(remittanceName);
			} catch (Exception e) {
				waitForSomeTime(30);
				searchRemittance(remittanceName);
			}
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(0);
			///// code to add external remittance
			waitForSomeTime(10);
			addCheck();
			addExternalRemittanceWithSelectedContractNumber("Non-Ford F150");

			// Refresh remittance
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = getExternalWebContractsFromRemittanceName(
					remittanceName);
			try {
				searchContractAddedViaExternalContract(remitt.get(1).get("RemittanceName"),
						remitt.get(1).get("FILE_NAME"));
			} catch (Exception e) {
				waitForSomeTime(30);
				searchContractAddedViaExternalContract(remitt.get(1).get("RemittanceName"),
						remitt.get(1).get("FILE_NAME"));
			}
			lockAndViewContract();
//			String message = getValidationForFordF150Vehicle();
//			takeScreenshot();
//			click("clickOK");
//			String actualMessage = "This vehicle is a Ford F-150, verify trim level for eligibility.";
//			assertEquals(message.toLowerCase(), actualMessage.toLowerCase());
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
			String commentsNewBusForm = getTextOfElement("commentBoxInNewBusinessForm");
			System.out.println("commentsNewBusForm : " + commentsNewBusForm);
			takeScreenshot();
			assertEquals(!commentsNewBusForm.contains("Verify Ford F-150"), true);
			goToRemittanceSummary();
			click("remSummarySwipeRight");
			String commentsRemSumm = getValue("getCommentsFromRemittanceSummryPage");
			System.out.println("commentsRemSumm : " + commentsRemSumm);
			takeScreenshot();
			assertEquals(!commentsRemSumm.contains("Verify Ford F-150"), true);
			click("remSummarySwipeLeft");
		} else
			throw new Exception("Remittance creation failed");
	}
}
