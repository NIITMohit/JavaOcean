package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Underwriting_TC10 class automates Ocean Underwriting module Test
 * Condition 10, which hold 1 Test Case; Test Condition Description : Validate
 * all data fields for correct remittance information under remittance list.
 * 
 * @author Shalu Chauhan
 * 
 * @reviewer : Poonam Kalra
 */

public class OCEAN_UnderWriting_TC_10 extends UnderwritingModulePages {
	/**
	 * This function automate test case for test condition 10; Test Case description
	 * : All the data fields should be display correct information related to
	 * remittances: 1. Post Period, 2. Remittance #, 3. Remittance Name, 4. Core, 5.
	 * LWA, 6. Under 7. Holds, 8. Source, 9. Type, 10. Sub Type, 11. Created By, 12.
	 * Locked By, 13. Created Date, 14. Comment
	 */
	@Test(priority = 1, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate all data fields for correct remittance information under remittance list.")
	public void validateAllDataFieldsRemittanceInfoUnderRemittanceList() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		String remittanceName = getRemittanceName();
		HashMap<String, String> actualData = getAllDataUnderRemittanceList();
		for (Map.Entry<String, String> entry : actualData.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			if (val == null || val.contains("-select-"))
				actualData.put(key, "");
		}
		for (Map.Entry<String, String> entry : actualData.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			if (val == null || val.toLowerCase().contains("null"))
				actualData.put(key, "");
		}
		String createDate = actualData.get("CreateByDate").toString();
		int space = createDate.indexOf(" ");
		String dateCreate = createDate.substring(0, space);
		String sDate1 = dateCreate;
		String value = ConverDatetFormatUnderWriting(sDate1);
		actualData.put("CreateByDate", value);
		HashMap<String, String> expectedDbRemitData = getAllDataFieldsUnderRemittanceList(remittanceName);
		String lokedby = expectedDbRemitData.get("Locked_by").toString();
		expectedDbRemitData.remove("Locked_by", lokedby);
		String createDate1 = expectedDbRemitData.get("CreateByDate").toString();
		int space1 = createDate1.indexOf(" ");
		String dateCreate1 = createDate1.substring(0, space1);
		String sDate2 = dateCreate1;
		String value1 = ConverDatetFormat(sDate2);
		expectedDbRemitData.put("CreateByDate", value1);
		System.out.println(actualData);
		System.out.println(expectedDbRemitData);
		// expectedDbRemitData.put("HoldCount", actualData.get("HoldCount"));
		// expectedDbRemitData.put("RemitType", actualData.get("UVremitType"));
		assertEquals(actualData, expectedDbRemitData);
	}

}
