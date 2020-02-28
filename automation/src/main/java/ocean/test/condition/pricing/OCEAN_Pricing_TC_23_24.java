package ocean.test.condition.pricing;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.PricingModulePages;

/**
 * OCEAN_Pricing_TC_23_24 class automates Ocean Pricing module Test Condition 23
 * abd 24 which holds 5 Test Cases; Test Condition 23 Description : Validate
 * usage of classification list for turbo/ diesel option. Test Condition 23
 * Description: Validate classification list for cloning/exporting and delete.
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Pricing_TC_23_24 extends PricingModulePages {
	/**
	 * Validate classification list for cloning/exporting and delete.this function
	 * cover the condition of this test case like 1. Cloning ClassificationList
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC24", dataProviderClass = PricingDataProvider.class, description = "Validate classification list for cloning.")
	public void validateClassificationListForCloneExport(String[] inputArray) throws Exception {
		goToPricingTab();
		goToClassficationList();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("PriceSheetName", inputArray[0]);
		selectCloneClassfication(map);
		validateClonePriceSheet(map);
		String validationMessage = getValueofClonePriceSheet();
		assertTrue(validationMessage.equalsIgnoreCase(map.get("PriceSheetName")), "Not matched");
	}

	/**
	 * Validate classification list for cloning/exporting and delete.this function
	 * cover the condition of this test case like 2. Export ClassificationList
	 */
	// @Test(priority = 2, groups = "regression", description = "Validate
	// classification list for exporting.")
	public void validateClassificationListForExport() throws Exception {
		goToPricingTab();
		goToClassficationList();
		exportClassificationPriceSheet();
		String downloadPath = "C:\\Users\\shalu.chauhan\\Desktop";
		String fileName = getTextOfElement("getExportClassificationListName");
		isFileDownloaded(downloadPath, fileName);
		String deletepath = "C:\\Users\\shalu.chauhan\\Desktop\\Absolute Lease - ACL_Master_Export.xlxs";
		deleteExportFile(deletepath);
	}

	/**
	 * Validate classification list for cloning/exporting and delete.this function
	 * cover the condition of this test case like 3. Delete ClassificationList
	 */
	// @Test(priority = 3, groups = "regression", dataProvider = "fetchDataForTC24",
	// dataProviderClass = PricingDataProvider.class, description = "Validate
	// classification list for delete.")
	public void validateClassificationListForDelete(String[] inputArray) throws Exception {
		goToPricingTab();
		goToClassficationList();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("PriceSheetName", inputArray[0]);
		deleteClassificationPriceSheet(map.get("PriceSheetName"));
		validateDeleteClassificationPriceSheet("listOfClassificationList");
	}

	/**
	 * Validate usage of classification list for turbo/ diesel option.this function
	 * cover the condition of this test case like 1. Validate usage of
	 * classification list when it is increased by one on a vehicle for turbo
	 * option.
	 */
	// @Test(priority = 4, groups = "regression", dataProvider = "fetchDataForTC24",
	// dataProviderClass = PricingDataProvider.class, description = "Validate usage
	// of classification list when it is increased by one on a vehicle for turbo
	// option.")
	public void validateClassificationListForTurbo(String[] inputArray) throws Exception {
		goToPricingTab();
		goToClassficationList();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Turbo", inputArray[2]);
		map.put("ClassificationListForTurbo", inputArray[1]);
		selectPricesheetForTurbo(map);
		HashMap<String, String> actualTurboValue = getClassficationListForTurboBeforAndAfterEditing(inputArray[1], 0);
		editClassificationListForTurbo(map);
		HashMap<String, String> expectedTurboValue = getClassficationListForTurboBeforAndAfterEditing(inputArray[1], 1);
		uncheckedEditTurboCheckbox();
		waitForSomeTime(3);
		assertNotEquals(actualTurboValue, expectedTurboValue);
	}

	/**
	 * Validate usage of classification list for turbo/ diesel option.this function
	 * cover the condition of this test case like 1. Validate usage of
	 * classification list when it is increased by one on a vehicle for turbo
	 * option.
	 */
	// @Test(priority = 5, groups = "regression", dataProvider = "fetchDataForTC24",
	// dataProviderClass = PricingDataProvider.class, description = "Validate usage
	// of classification list when it is increased by one on a vehicle for turbo
	// option.")
	public void validateClassificationListForDiesel(String[] inputArray) throws Exception {
		goToPricingTab();
		goToClassficationList();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("Diesel", inputArray[3]);
		map1.put("ClassificationListForDiesel", inputArray[4]);
		selectPricesheetForDiesel(map1);
		HashMap<String, String> actualDieselValue = getClassficationListForDieselBeforeAndAfterEditing(inputArray[4],
				0);
		editClassificationListForDiesel(map1);
		HashMap<String, String> expectedDieselValue = getClassficationListForDieselBeforeAndAfterEditing(inputArray[4],
				1);
		uncheckedEditDieselCheckbox();
		waitForSomeTime(3);
		assertNotEquals(actualDieselValue, expectedDieselValue);
	}
}
