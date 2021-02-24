package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_TC86 class automates Ocean Underwriting module Test
 * Condition 86, which holds 24 Test Case; Test Condition Description : Validate
 * Dealer Statement - Credit, Debit, and Zero Balances Functionality
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Underwriting_TC_86 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 86; Test Case
	 * description : Validate Dealer Statement - Credit, Debit, and Zero Balances
	 * Functionality on the basis of Different parameters
	 * 
	 */

	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_86", dataProviderClass = UnderwritingDataProvider.class, description = "Validate Dealer Statement - Credit, Debit, and Zero Balances Functionality")
	public void ValidateAccountStatementsWindowData(String[] inputArray) throws Exception {
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
			String abc = getTextOfElement("countTextBoxValue");
			int countUI = Integer.parseInt(abc);
			assertEquals(countUI, dbCount);
			if (dbCount == countUI && dbCount > 0) {
				int iterationsCount = 10;
				if (dbCount < 11)
					iterationsCount = dbCount;

				boolean dataMatched = true;
				for (int i = 0; i < iterationsCount; i++) {
					HashMap<String, String> myDBData = dbData.get(i + 1);
					// get the UI grid data
					HashMap<String, String> gridData = returnAccountStatementGridData(i);
					if (functionPerform.contains("Zero"))
						myDBData.put("Prior2 EOM", gridData.get("Prior2 EOM"));
					if (myDBData.equals(gridData))
						dataMatched = true;
					else {
						dataMatched = false;
						break;
					}
				}
				assertEquals(dataMatched, true);
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
