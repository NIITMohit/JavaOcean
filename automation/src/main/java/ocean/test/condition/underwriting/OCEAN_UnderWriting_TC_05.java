package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_05 class automates Ocean Underwriting module Test
 * Condition 05 which holds 1 Test Case; Test Condition Description : Validate
 * OCEAN ability to allow check delete by user before remittance is saved.
 * 
 * @author Mohit Goel
 * 
 * @reviewer : Mohit Goel
 */
public class OCEAN_UnderWriting_TC_05 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Validate the creation of remittance in ocean
	 * 
	 */
	@Test(priority = 5, groups = { "regression", "extendSmoke", "smoke1",
			"fullSuite" }, description = "Validate OCEAN ability to allow check delete by user before remittance is saved. ")
	public void deleteCheckDetails() throws Exception {
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		copyFilesWorkingRemittance();
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// verify drag and drop status for pdf
		String[] inputArray = new String[] { "random", "1", "1", "Paper", "Standard", "Paper Remit", "", "Automation",
				"111", "1312", "Dealer", "140" };
		String remittanceName = enterRemittanceValuesDelete(inputArray);
		//// navigate to create remittance tab
		if (inputArray[4] == "" || inputArray[5] == "" || inputArray[0] == "" || inputArray[1] == ""
				|| inputArray[2] == "") {
			if (remittanceName.equals("")) {
				assertEquals(true, true);
			} else {
				assertEquals(true, false);
			}
		} else {
			HashMap<String, String> dbValues = getRemitCreationdata(remittanceName);
			HashMap<String, String> excelValues = excelValue(inputArray, remittanceName);
			dbValues.put("Paper", excelValues.get("Paper"));
			String aa = dbValues.get("checkamount");
			if (aa.length() > 1) {
				String s = aa.substring(0, aa.indexOf("."));
				dbValues.put("checkamount", s);
			} else
				dbValues.put("checkamount", aa);
			for (Map.Entry<String, String> entry : dbValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					dbValues.put(key, "");
			}
			for (Map.Entry<String, String> entry : excelValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					excelValues.put(key, "");
			}
			if (excelValues.equals(dbValues)) {
				assertEquals(true, true);
			} else {
				assertEquals(false, true);
			}
		}
	}

	public HashMap<String, String> excelValue(String[] inputArray, String remit) {
		HashMap<String, String> excel = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			switch (i) {
			case 0:
				excel.put("RemittanceName", remit);
				break;
			case 1:
				excel.put("corecount", inputArray[i]);
				break;
			case 2:
				excel.put("lwacount", inputArray[i]);
				break;
			case 3:
				excel.put("Source_Type", inputArray[i]);
				break;
			case 4:
				excel.put("name", inputArray[i]);
				break;
			case 5:
				excel.put("Paper", inputArray[i]);
				break;
			case 6:
				excel.put("Subtype_Name", inputArray[i]);
				break;
			case 7:
				excel.put("comments", inputArray[i]);
				break;
			case 8:
				excel.put("checknumber", inputArray[i]);
				break;
			case 9:
				excel.put("checkamount", inputArray[i]);
				break;
			default:
				break;
			}
		}
		return excel;
	}
}
