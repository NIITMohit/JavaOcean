package ocean.test.condition.undwewriting;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Cancel_TC_01_02_03 class automates Ocean Underwriting module Test
 * Condition 01 and 02 and 03 and 04, which holds 15 Test Case; Test Condition
 * Description : Validate the creation of remittance in ocean
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_01_02_03_04 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Validate the creation of remittance in ocean
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC01_02_03_04", dataProviderClass = UnderwritingDataProvider.class, description = "Validate the creation of remittance in ocean")
	public void createRemittance(String[] inputArray) throws Exception {
		goToUnderWritingTab();
		landToCreateRemittanceDetailsPage();
	}
}
