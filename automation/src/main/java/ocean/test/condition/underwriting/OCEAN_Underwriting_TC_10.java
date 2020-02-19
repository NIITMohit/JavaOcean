package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_TC10 class automates Ocean Underwriting module Test
 * Condition 10, which hold 1 Test Case; Test Condition Description : Validate
 * all data fields for correct remittance information under remittance list.
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Underwriting_TC_10 extends UnderwritingModulePages {

	/**
	 * This function automate test case for test condition 10; Test Case description
	 * : All the data fields should be display correct information related to
	 * remittances: 1. Post Period, 2. Remittance #, 3. Remittance Name, 4. Core, 5.
	 * LWA, 6. Under 7. Holds, 8. Source, 9. Type, 10. Sub Type, 11. Created By, 12.
	 * Locked By, 13. Created Date, 14. Comment
	 */
	@Test(priority = 1, groups = "regression", description = "Validate all data fields for correct remittance information under remittance list.")
	public void validateAllDataFieldsRemittanceInfoUnderRemittanceList() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		String remittanceName = getRemittanceName();
		HashMap<String, String> actualData = getAllDataUnderRemittanceList();
		HashMap<String, String> expectedDbRemitData = getAllDataFieldsUnderRemittanceList(remittanceName);
		expectedDbRemitData.put("HoldCount", actualData.get("HoldCount"));
		expectedDbRemitData.put("RemitType", actualData.get("UVremitType"));
		assertEquals(actualData, expectedDbRemitData);
	}

}
