package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_45 class automates Ocean Underwriting
 * Test_Condition_Ref _45 which holds 1 Test Case; Test Condition Description :
 * Validate OCEAN ability to allow check on hold contract on given condition.
 * 
 * @author Poonam Kalra
 */

public class OCEAN_UnderWriting_TC_45 extends UnderwritingModulePages {

	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, description = "Validate OCEAN ability to search for on hold contracts only on the basis of search parameter given.")
	public void onHoldContractsDetails() throws Exception {
		//// go to underwriting tab
		// goToUnderWritingTab();
		// go to holdContract tab
		goToHoldContactTab();
		// to click on Search DropdownList
		click("searchByCategoryDropDown");
		// select on DealerOOB from drop_down
		click("selectDealerOOBInDropDown");
		// get list of on hold_contract_number based on DealerOOB criteria
		Set<String> ContractNumber = getAllValuesSaveInSet("listOfContractNumber");
		/// go to findContract tab
		goToFindContractTab();
		boolean check = searchContractStatus(ContractNumber);
		assertEquals(check, true);
	}

}
