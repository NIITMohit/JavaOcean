package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_TC83 class automates Ocean Underwriting module Test
 * Condition 83, which holds 5 Test Case; Test Condition Description : Validate
 * Dealer Statement - Print Statement UI Changes
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Underwriting_TC_83 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 83; Test Case
	 * description : Validate Dealer Statement - Print Statement UI Changes
	 * 
	 */
	@Test(priority = 1, groups = { "regression",
			"smoke" }, description = "Validate Dealer Statement - Print Statement UI Changes")
	public void ValidateDealerStatementUIChanges() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		//// go to Account Statement
		goToAccountStatements();
		takeScreenshot();
		String accountStatementButtonText = getValue("accountStatementButtonText");
		assertEquals(accountStatementButtonText, "Account Statements");
		//// go to Account Statement Window
		click("accountStatementButtonText");
		verifyUiChangesForDealerStatement();
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
