package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_TC87 class automates Ocean Underwriting module Test
 * Condition 87, which holds 26 Test Case; Test Condition Description : Validate
 * Dealer Statement - Print PDF Statements & Print Statement List
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Underwriting_TC_87 extends UnderwritingModulePages {
	/**
	 * This function automates 3 test cases- TFS ID 18016 Test Case 6, TFS ID 18016
	 * Test Case 7,TFS ID 18016 Test Case 9 for test condition 87; Test Case
	 * description : Validate Dealer Statement - Print PDF Statements & Print
	 * Statement List related functionality
	 * 
	 */

	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_87", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Dealer Statement - Credit, Debit, and Zero Balances Functionality")
	public void ValidateAccountStatementsUIRelated(String[] inputArray) throws Exception {
		String gvnPP = inputArray[0].replace(".0", "");
		String roleType = inputArray[1];
		String functionPerform = inputArray[2];
		String currentPostPeriod = getCurrentPostPeriod();
		// input the parameters given
		fillInputValues(gvnPP, roleType, functionPerform);
		int givenPostPeriod = Integer.parseInt(gvnPP);
		if (givenPostPeriod < 200001) {
			// To be checked when Post Period is less than Jan 2000
			String validationErrormsg = getValue("validationMsgForLesserPostPeriod");
			assertEquals(validationErrormsg,
					"Selected Post Period is old Month/Year (MMM yyyy). Please select Post Period greater than or equivalent to Jan 2000 Month/Year (MMM yyyy).");
			click("clickOK");
		} else if (givenPostPeriod > Integer.parseInt(currentPostPeriod)) {
			// To be checked when Post Period greater than current Post Period
			String validationErrormsg = getValue("validationMsgForGreaterPostPeriod");
			assertEquals(validationErrormsg,
					"Selected Post Period is future Month/Year (MMM yyyy). Please select Post Period less than or equivalent to current Month/Year (MMM yyyy).");
			click("clickOK");
		} else {
			///// get db count to have actual iterations
			HashMap<Integer, HashMap<String, String>> dbData = getAccountStatementGridData(gvnPP, roleType,
					functionPerform);
			int dbCount = dbData.size();
			if (dbCount > 0) {
				int iterationsCount = 5;
				if (dbCount < 6)
					iterationsCount = dbCount;

				for (int i = 0; i < iterationsCount; i++) {
					//// get data from Db for contract id
					click("listcheckAccStmtGridData", i);
				}
				takeScreenshot();
				int listViewCount = listOfElements("listViewAccStmt").size();
				assertEquals(iterationsCount == listViewCount, true);

				for (int i = 0; i < iterationsCount; i++) {
					//// get data from Db for contract id
					click("listcheckAccStmtGridData", i);
				}
				takeScreenshot();
				boolean listViewEmpty = false;
				try {
					int newListViewCount = listOfElements("listViewAccStmt").size();
					if (newListViewCount >= 1) {
						listViewEmpty = false;
					}
				} catch (Exception e) {
					listViewEmpty = true;
				}
				assertEquals(listViewEmpty, true);

				click("clickPrintPDFStmt");
				String validationErrormsg = getValue("validationMsgForRowSelection");
				assertEquals(validationErrormsg, "Please select Grid rows before Print PDF statement");
				click("clickOK");
			}

		}
	}

	/**
	 * This function automates 1 test case- TFS ID 18016 Test Case 10 for test
	 * condition 87; Test Case description : Validate that user is able to generate
	 * PDF report for manually selected accounts from the grid.
	 * 
	 */

	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_87", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Dealer Statement - Credit, Debit, and Zero Balances Functionality")
	public void ValidateAccountStatementsReportGenerated(String[] inputArray) throws Exception {
		String gvnPP = inputArray[0].replace(".0", "");
		String roleType = inputArray[1];
		String functionPerform = inputArray[2];
		String currentPostPeriod = getCurrentPostPeriod();
		// input the parameters given
		fillInputValues(gvnPP, roleType, functionPerform);
		int givenPostPeriod = Integer.parseInt(gvnPP);
		if (givenPostPeriod < 200001) {
			// To be checked when Post Period is less than Jan 2000
			String validationErrormsg = getValue("validationMsgForLesserPostPeriod");
			assertEquals(validationErrormsg,
					"Selected Post Period is old Month/Year (MMM yyyy). Please select Post Period greater than or equivalent to Jan 2000 Month/Year (MMM yyyy).");
			click("clickOK");
		} else if (givenPostPeriod > Integer.parseInt(currentPostPeriod)) {
			// To be checked when Post Period greater than current Post Period
			String validationErrormsg = getValue("validationMsgForGreaterPostPeriod");
			assertEquals(validationErrormsg,
					"Selected Post Period is future Month/Year (MMM yyyy). Please select Post Period less than or equivalent to current Month/Year (MMM yyyy).");
			click("clickOK");
		} else {
			///// get db count to have actual iterations
			HashMap<Integer, HashMap<String, String>> dbData = getAccountStatementGridData(gvnPP, roleType,
					functionPerform);
			int dbCount = dbData.size();
			if (dbCount > 0) {
				int iterationsCount = 2;
				if (dbCount < 3)
					iterationsCount = dbCount;

				for (int i = 0; i < iterationsCount; i++) {
					//// get data from Db for contract id
					click("listcheckAccStmtGridData", i);
				}
				String titleBeforeClick = getValue("accountStatementsWindowTitle");
				click("clickPrintPDFStmt");
				String reportWindowTitle = "com.aul.Ocean.Modules.Underwriting.ViewModels.ReportViewModel";
				assertEquals(reportWindowTitle.toLowerCase(), getValue("getSSRSReportTitle").toLowerCase());
				String titleAfterClick = getValue("getSSRSReportTitle");
				assertEquals(titleBeforeClick.equals(titleAfterClick), false);
				takeScreenshot();
				click("closeReportWindow");

			}

		}
	}

	/**
	 * This function is executed before every Test, this will land to Account
	 * Statement Window
	 * 
	 */
	@BeforeMethod(alwaysRun = true)
	public void landToAccStatementWindow() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		//// go to Account Statement
		goToAccountStatements();
		//// go to Account Statement Window
		click("accountStatementButtonText");
	}

	/**
	 * This function is executed after Test, to close Account Statement Window
	 * 
	 */
	@AfterMethod(alwaysRun = true)
	public void closeAccStatementWindow() throws Exception {
		try {
			click("closeAccStmtWindow");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
