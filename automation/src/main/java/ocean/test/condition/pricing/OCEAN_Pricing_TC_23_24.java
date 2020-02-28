package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

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
	@Test(priority = 5, groups = "regression", description = "Validate classification list for cloning.")
	public void validateClassificationListForCloneExport() throws Exception {
		goToPricingTab();
		goToClassficationList();
		selectCloneClassfication("ClonePriceSheet");
		validateClonePriceSheet("ClonePriceSheet");
		String validationMessage = getValueofClonePriceSheet();
		assertTrue(validationMessage.equalsIgnoreCase("ClonePriceSheet"));
	}

	/**
	 * Validate classification list for cloning/exporting and delete.this function
	 * cover the condition of this test case like 2. Export ClassificationList
	 */
	@Test(priority = 5, groups = "regression", description = "Validate classification list for exporting.")
	public void validateClassificationListForExport() throws Exception {
		goToPricingTab();
		goToClassficationList();
		exportClassificationPriceSheet();
		String desktopPath = System.getProperty("user.home") + "\\Desktop";
		String fileName = getTextOfElement("getExportClassificationListName");
		boolean deleted = isFileDownloaded(desktopPath, fileName);
		if (deleted == true) {
			String deletepath = desktopPath + "\\" + fileName;
			deleteExportFile(deletepath);
			boolean deleted1 = isFileDownloaded(desktopPath, fileName);
			assertEquals(deleted1, false);
		} else {
			throw new Exception("File not deleted");
		}
	}

	/**
	 * Validate classification list for cloning/exporting and delete.this function
	 * cover the condition of this test case like 3. Delete ClassificationList
	 */
	@Test(priority = 5, groups = "regression", description = "Validate classification list for delete.")
	public void validateClassificationListForDelete() throws Exception {
		goToPricingTab();
		goToClassficationList();
		deleteClassificationPriceSheet("ClonePriceSheet");
		validateDeleteClassificationPriceSheet("listOfClassificationList");
	}

	/**
	 * Validate usage of classification list for turbo/ diesel option.this function
	 * cover the condition of this test case like 1. Validate usage of
	 * classification list when it is increased by one on a vehicle for turbo
	 * option.
	 */
	@Test(priority = 5, groups = "regression", description = "Validate usage of classification list when it is increased by one on a vehicle for turbo option.")
	public void validateClassificationListForTurbo() throws Exception {
		goToPricingTab();
		goToClassficationList();
		HashMap<String, String> actualTurboValue = getClassificationInfomation("", "0", "0");
		selectPricesheetForTurbo(actualTurboValue.get("CLASS_GROUP_NAME"));
		editClassificationListForTurboAndDiesel("Turbo", "Y");
		checkEnableDisable("clickCheckBoxOfTurbo");
		HashMap<String, String> expectedTurboValue = getClassificationInfomation(
				actualTurboValue.get("CLASS_GROUP_NAME"), "1", "0");
		editClassificationListForTurboAndDiesel("Turbo", "N");
		assertNotEquals(actualTurboValue, expectedTurboValue);
	}

	/**
	 * Validate usage of classification list for turbo/ diesel option.this function
	 * cover the condition of this test case like 1. Validate usage of
	 * classification list when it is increased by one on a vehicle for turbo
	 * option.
	 */
	@Test(priority = 5, groups = "regression", description = "Validate usage of classification list when it is increased by one on a vehicle for turbo option.")
	public void validateClassificationListForDiesel() throws Exception {
		goToPricingTab();
		goToClassficationList();
		selectPricesheetForDiesel("ACL");
		HashMap<String, String> actualDieselValue = getClassficationListForDieselBeforeAndAfterEditing("ACL", 0);
		editClassificationListForTurboAndDiesel("Diesel", "Y");
		checkEnableDisable("clickCheckBoxOfDiesel");
		HashMap<String, String> expectedDieselValue = getClassficationListForDieselBeforeAndAfterEditing("ACL", 1);
		editClassificationListForTurboAndDiesel("Diesel", "N");
		assertNotEquals(actualDieselValue, expectedDieselValue);
	}
}
