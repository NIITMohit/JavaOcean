package ocean.test.condition.compliance;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import ocean.common.ObjectRepo;
import ocean.modules.dataprovider.ComplianceDataProvider;
import ocean.modules.pages.ComplianceModulePages;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * OCEAN_Compliance_TC_01 and 02 class automates Ocean Compliance module Test
 * Condition 01 and 02, which holds 17 Test Cases; Test Condition Description :
 * Validate compliance search rules for cancellation on the basis of search
 * parameter given. Test Condition Description:Validate search results enquiry
 * under contract builder with required details.(including uploaded PDF enquiry)
 * 
 * @author Shalu Chauhan
 */
public class OCEAN_Compliance_TC_01_02 extends ComplianceModulePages {
	/**
	 * This function automates all test cases for test condition 01 Case description
	 * :Validate compliance search rules for cancellation on the basis of search
	 * parameter given.
	 */
	@Test(priority = 1, groups = { "smoke", "regression",
			"fullSuite" }, dataProvider = "fetchDataForTC01_02", dataProviderClass = ComplianceDataProvider.class, description = "Validate compliance search rules for cancellation on the basis of search parameter given.")
	public void complianceSeacrh(String[] inputArray) throws Exception {
		//// create data to fill required values in search window
		HashMap<String, String> uiSearchData = null;
		//// Navigate to mail service tab
		goToComplianceTab();
		goToContractBuilderTab();
		if (Arrays.stream(inputArray).anyMatch("*"::equals)) {
			//// run db query to get unique value, else no need
			//// get search data value in a hash map from data provider, all values would be
			//// appendSearchData saved in searchData hash map same as in excel, all values
			//// including *, Blanks

			uiSearchData = compliance_Search(compliance_Search_appendSearchData(inputArray));

		} else {
			uiSearchData = compliance_Search_convertDataRemoveStar(inputArray);
		}
		//// run code for search
		searchComplinaceGivenInputParamaters(uiSearchData);

		WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ObjectRepo.fetchOR("loadingIcon")));

		// Get data from DB on the basis of excel input data
		HashMap<Integer, HashMap<String, String>> DBData = compliance_getcomplianceSearchData(uiSearchData);

		//// save data from UI searched result
		HashMap<String, String> searchData = returnComplianceSearchResultGridData();
		System.out.println("UI data: " + searchData);

		int expectedMapIndex = 0;
		boolean flag = false;
		for (int i = 1; i <= DBData.size(); i++) {
			if ((DBData.get(i).get("GroupName").equals(searchData.get("GroupName")))
					&& (DBData.get(i).get("State").equals(searchData.get("State")))
					&& (DBData.get(i).get("VersionNumber").equals(searchData.get("VersionNumber")))) {
				expectedMapIndex = i;
				flag = true;
				break;
			}
		}
		assertTrue(flag, "search data is not available in DB data.");
		String[] FormNum = DBData.get(expectedMapIndex).get("FormNumber").split("-V");
		String FormNumber = FormNum[0];
		DBData.get(expectedMapIndex).replace("FormNumber", FormNumber);
		Thread.sleep(1000);
		//// verify both data, must be equal
		assertEquals(DBData.get(expectedMapIndex).equals(searchData), true, "Data Not matched.");
	}

}
