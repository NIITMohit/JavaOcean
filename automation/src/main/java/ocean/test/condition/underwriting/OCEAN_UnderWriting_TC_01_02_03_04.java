package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

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
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// fill all necessary fields in create remittance
		String remittanceName = enterRemittanceValues(inputArray);
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
			HashMap<String, String> uiValues = myData(remittanceName);
			uiValues.put("comments", excelValues.get("comments"));
			uiValues.put("checknumber", excelValues.get("checknumber"));
			uiValues.put("checkamount", excelValues.get("checkamount"));
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
			for (Map.Entry<String, String> entry : uiValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					uiValues.put(key, "");
			}
			if (dbValues.equals(excelValues) && excelValues.equals(uiValues)) {
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
