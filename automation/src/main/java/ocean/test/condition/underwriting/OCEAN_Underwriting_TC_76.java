package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * PBI 11645 class automates NEW PBI 11645 : , which holds 9 Test Case; Test
 * Condition Description : Validate OCEAN ability to search based on contract
 * status given
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_76 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases of NEW PBI 11645 Case description :
	 * Validate OCEAN ability to search based on contract status given
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForPBI11645", dataProviderClass = UnderwritingDataProvider.class, description = "Validate OCEAN ability to search based on contract status given")
	public void searchContractOnContractStatus(String[] inputArray) throws Exception {
		///// get contract number based on input contract status
		//// land to find contract status page
		goToUnderWritingTab();
		goToFindContractTab();
		HashMap<String, String> aa = cancellation_getContractIdBasedOnStatusSubStatus(inputArray);
		if (aa.get("CERT") != null) {
			type("contractNoInFindContract", aa.get("CERT"));
			click("searchFindContractBtn");
			waitForSomeTime(15);
			//// get value of searched contract
			String uiContractid = getValue("searchResultContractNumberonFindContracts");	
			String SubStatus = getAttributeValue("searchResultSubStatusonFindContracts", "Value.Value");
			if (SubStatus == null)
				SubStatus = "";
			assertEquals(aa.get("CERT").equals(uiContractid) && aa.get("contractSubStatus").equalsIgnoreCase(SubStatus),
					true);
		} else {
			throw new SkipException("no data found");
		}
	}
}
