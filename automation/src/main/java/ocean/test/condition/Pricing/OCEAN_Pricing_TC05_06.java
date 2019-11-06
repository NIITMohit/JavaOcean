package ocean.test.condition.Pricing;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;
import ocean.common.DataProviderClass;

/**
 * OCEAN_Pricing_TC05_06 class automates Ocean Pricing module Test Condition 05
 * and 06, which holds 1 Test Case each; Test Condition 05 Description :
 * Validate premium calculation for multiple contracts from same master price
 * sheet. Test Condition 06 Description : Validate premium calculation for
 * multiple contracts from same sub master price sheet.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Pricing_TC05_06 extends CommonFunctions {

	/**
	 * This function automates test case 01 and 02 for test condition 05 and 06
	 * respectively; Test Case description : Validate premium calculation for
	 * multiple contracts from same master and sub-master price sheet.
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchContract", 
			dataProviderClass = DataProviderClass.class)
	public void premiumCalculation() throws Exception {
		//// Upload a price sheet
		// Click Pricing Tab
		click("clickPricingTab");
		// Click Price Sheet List Tab
		click("clickPricingSheetListTab");

	}
}
