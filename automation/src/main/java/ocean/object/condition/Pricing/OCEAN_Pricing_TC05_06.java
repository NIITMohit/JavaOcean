package ocean.object.condition.Pricing;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.WebElement;
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
	@Test(priority = 1, groups = "regression")
	public void premiumCalculation() throws Exception {
		//// get remittance from database
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = getPendingContractwithRemittance();
		//// get remittance name and file name
		/// iterate to multiple contracts with same price sheet
		for (Map.Entry<Integer, HashMap<String, String>> maps : contractFromRemittance.entrySet()) {
			String remittName = maps.getValue().get("RemittanceNumber");
			String fileName = maps.getValue().get("FILE_NAME");
			//// click underwriting tab
			click("clickUnderWritingTab");
			//// Type RemittanceName
			type("typeToSearchRemittance", remittName);
			//// expand remittance to get contracts
			click("expandRemittance");
			//// type filename
			type("typeContract", fileName);
			//// click view contract
			click("viewContract");
			try {
				//// click yes to lock remittance
				click("lockContractYesButton");
			} catch (Exception e) {
				// do nothing
			}
			//// click contract expander
			click("contractExpander");
			//// type unique contract id

		}

	}
}
