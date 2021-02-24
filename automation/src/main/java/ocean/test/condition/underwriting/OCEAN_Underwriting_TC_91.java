package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_TC91 class automates Ocean Underwriting module Test
 * Condition 91, which holds 9 Test Case; Test Condition Description : Validate
 * Dealer Statement - Update Balances and Move Balances Forward Functionality
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Underwriting_TC_91 extends UnderwritingModulePages {
	/**
	 * This function automates 3 test case- TFS ID 17143 Test Case 5, TFS ID 17143
	 * Test Case 6, TFS ID 17143 Test Case 12 for test condition 91; Test Case
	 * description : Validate Dealer Statement - Update Balances and Move Balances
	 * Forward Functionality.
	 * 
	 */

	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_91", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Dealer Statement - Update Balances and Move Balances Forward Functionality")
	public void ValidateAccountStatementsReportGenerated(String[] inputArray) throws Exception {
		String gvnPP = inputArray[0].replace(".0", "");
		String currentPostPeriod = getCurrentPostPeriod();
		// input the parameters given
		fillInputValuesForPostPeriod(gvnPP);
		click("clickUpdateBalancesButton");

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
			waitForSomeTime(15);
			String successMsgUpdateBalance = getValue("successMsgUpdateBalance");
			assertEquals(successMsgUpdateBalance, "Updated Successfully!");
			click("clickOK");
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
