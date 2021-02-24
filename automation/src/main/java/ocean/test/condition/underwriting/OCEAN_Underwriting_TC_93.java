package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

public class OCEAN_Underwriting_TC_93 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate remittance posting.
	 * 
	 */
	@Test(priority = 1, groups = { "smoke", "fullSuite" }, description = " Validate remittance posting")
	public void validateDataOnReturnsRowAndExpander() throws Exception {
		// String remittanceName = createRemittance();
		// String premium = createContractWithReturnStatusandPost(remittanceName);
		HashMap<String, String> dbdataForExpander = getAddedReturnsContractDataFromDB("SCRETRN28002", "Lender");

		HashMap<String, String> dbdataForRow = getAddedReturnsRowDataFromDB(dbdataForExpander, "975.00");

		dbdataForExpander.remove("PrimAccountID");
		dbdataForExpander.remove("SecAccountID");
		System.out.println("dbdataForExpander: " + dbdataForExpander);
		System.out.println("dbdataForRow: " + dbdataForRow);

		//// go to Account Statement
		goToAccountStatements();
		typeRoletypeAndRoleIdUnderStatementsTab("Lender", "4633");
		waitForSomeTime(10);
//		click("clickOnCalendarIconOnStatementsTab");
//		waitForSomeTime(10);
//	
//		String postPeriod = dbdataForExpander.get("PostPeriod");
//		
//		// select Post Period
//		fillInputValuesForPostPeriod(postPeriod);

		// click on View Button
		clickOnViewButtonUnderStatementsTab();

		HashMap<String, String> uiDataForReturnsRow = getReturnsRowDetailsFromUI();
		System.out.println("uiDataForReturnsRow: " + uiDataForReturnsRow);
		// Validating correct data on Returns row
		assertEquals(uiDataForReturnsRow, dbdataForRow);

		clickReturnsUnderStatementsTab();
		waitForSomeTime(10);
		HashMap<String, String> uiDataForExpander = getReturnsExpanderDetailsFromUI("Lender");
		System.out.println("uiDataForExpander: " + uiDataForExpander);
		// Validating correct data on Returns Expander
		assertEquals(uiDataForExpander, dbdataForExpander);

	}

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate remittance posting.
	 * 
	 */
	@Test(priority = 2, groups = { "regression", "fullSuite" }, description = " Validate remittance posting")
	public void validateReturnRowAfterContractReinstate() throws Exception {
		// Process To Reinstate a Cancelled contract
		String contractId = "";
		processReinstateContract(contractId);

		// String remittanceName = createRemittance();
		// String premium = createContractWithReturnStatusandPost(remittanceName);
		HashMap<String, String> dbdataForExpander = getAddedReturnsContractDataFromDB("SCRETRN28002", "Lender");

		HashMap<String, String> dbdataForRow = getAddedReturnsRowDataFromDB(dbdataForExpander, "975.00");

		dbdataForExpander.remove("PrimAccountID");
		dbdataForExpander.remove("SecAccountID");
		System.out.println("dbdataForExpander: " + dbdataForExpander);
		System.out.println("dbdataForRow: " + dbdataForRow);

		//// go to Account Statement
		goToAccountStatements();
		typeRoletypeAndRoleIdUnderStatementsTab("Lender", "4633");
		waitForSomeTime(10);
//		click("clickOnCalendarIconOnStatementsTab");
//		waitForSomeTime(10);
//	
//		String postPeriod = dbdataForExpander.get("PostPeriod");
//		
//		// select Post Period
//		fillInputValuesForPostPeriod(postPeriod);

		// click on View Button
		clickOnViewButtonUnderStatementsTab();

		HashMap<String, String> uiDataForReturnsRow = getReturnsRowDetailsFromUI();
		System.out.println("uiDataForReturnsRow: " + uiDataForReturnsRow);
		// Validating correct data on Returns row
		assertEquals(uiDataForReturnsRow, dbdataForRow);

		clickReturnsUnderStatementsTab();
		waitForSomeTime(10);
		HashMap<String, String> uiDataForExpander = getReturnsExpanderDetailsFromUI("Lender");
		System.out.println("uiDataForExpander: " + uiDataForExpander);
		// Validating correct data on Returns Expander
		assertEquals(uiDataForExpander, dbdataForExpander);

	}
}
