package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Set;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_46 class automates Ocean Underwriting
 * Test_Condition_Ref _46 which holds 1 Test Case; Test Condition Description :
 * ability to display correct remittance details under search result for on hold
 * contracts only and on the basis of search parameter given. .
 * 
 * @author Poonam Kalra
 */

public class OCEAN_UnderWriting_TC_46 extends UnderwritingModulePages {
	@Test(priority = 1, groups = "fullSuite", description = "Validate OCEAN ability to  display correct remittance details for on hold contracts only on the basis of search parameter given.")
	public void verifyRemittanceDetails() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		// go to holdContract tab
		goToHoldContactTab();
		// to click on Search DropdownList
		click("searchByCategoryDropDown");
		// select on DealerOOB from drop_down
		click("selectDealerOOBInDropDown");
		// get count of on hold_contract_number based on DealerOOB criteria
		Set<String> contractNumber = getAllValuesSaveInSet("listOfContract");
		int iterationsCount = contractNumber.size();
		boolean flag = false;
		if (iterationsCount > 5) {
			iterationsCount = 5;
		}
		// verify gridResult with database and return boolean value
		for (int i = 0; i < iterationsCount; i++) {
			String contract = getValue("listOfContract", i).trim();
			HashMap<String, String> myDBData = getRemittanceNumber(contract);
			HashMap<String, String> gridData = returnSearchResult(i);
			gridData.put("Contract_Number", contract);
			if (myDBData.equals(gridData))
				flag = true;
			else {
				flag = false;
				break;
			}
		}
		assertEquals(flag, true);
	}
}
