package ocean.test.condition.search;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.common.CommonFunctions;
import ocean.common.DataProviderClass;
import ocean.modules.pages.pricingModulePages;
import ocean.modules.pages.searchModulePages;

/**
 * OCEAN_Pricing_TC01 class automates Ocean Pricing module Test Condition 01,
 * which holds 1 Test Case; Test Condition Description : Validate price sheet
 * content after their import into OCEAN
 * 
 * @author Mohit Goel
 */
public class OCEAN_Search_TC_01 extends searchModulePages {

	@Test(priority = 1, groups = "regression", dataProvider = "searchContract", dataProviderClass = DataProviderClass.class)
	public void searchContractWithAnyInputField(String Contract, String Last_Name, String First_Name, String VIN)
			throws Exception {
		try {
			HashMap<String, String> searchParamater = new HashMap<String, String>();
			if (Contract.length() > 0)
				searchParamater.put("Contract", Contract);
			else if (Last_Name.length() > 0)
				searchParamater.put("Contract", Contract);
			else if (Last_Name.length() > 0)
				searchParamater.put("Contract", Contract);
			searchContractGivenInputParamaters(searchParamater);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
