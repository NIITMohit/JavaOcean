package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_24 class automates Ocean Pricing module Test Condition
 * 24 which holds 11 Test Case; Test Condition Description : Validate premium
 * calculation for a contract/contract adjustment in a remittance, if it's price
 * sheet is changed
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_25 extends UnderwritingModulePages {

	/**
	 * This function automates test Condition 24; Test Case description :Validate
	 * premium calculation for a contract/contract adjustment in a remittance, if
	 * it's price sheet is changed from : 1. One product to another product.
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC24", dataProviderClass = UnderwritingDataProvider.class, description = "Validate premium calculation for a contract/contract adjustment in a remittance, if it's price sheet is changed from : 1. One product to another product.")
	public void TCase_01_changePriceSheet(String[] inputData) throws Exception {
		takeScreenshot();
		type("notepad", inputData[0] + inputData[1]);
	}

	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC24", dataProviderClass = UnderwritingDataProvider.class, description = "Validate premium calculation for a contract/contract adjustment in a remittance, if it's price sheet is changed from : 1. One product to another product.")
	public void TCase_02_changePriceSheet(String[] inputData) throws Exception {
		takeScreenshot();
		type("notepad", inputData[0] + inputData[1]);
	}

}
