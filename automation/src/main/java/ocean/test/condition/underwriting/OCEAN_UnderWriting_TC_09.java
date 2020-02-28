package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_09 class automates Ocean Underwriting module Test
 * Condition 09 which holds 5 Test Case; Test Condition Description :Validate
 * user ability to perform following on remittances from remittance list: 1.
 * Lock and Unlock 2. Save remittances 3. Load remittances 4. View thumb nails
 * of uploaded PDF. 5. Apply filters with fields like source, type, sub type
 * etc.
 * 
 * This test condition however would be covered indirectly with all other test
 * conditions. Included this condition to have correct mapping of test
 * conditions
 * 
 * @author Mohit Goel
 */
public class OCEAN_UnderWriting_TC_09 extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 09 Test Case
	 * description : Validate user ability to perform following on remittances from
	 * remittance list: 1. Lock and Unlock
	 * 
	 */
	// @Test(priority = 6, groups = "regression", description = "Validate user
	// ability to perform following on remittances from remittance list: 1. Lock and
	// Unlock.")
	public void lockAndUnlockRemittance() throws Exception {
		try {
			goToUnderWritingTab();
			goToRemittanceList();
			lockTopRemittance();
			unlockTopRemittance();
			lockTopRemittance();
			assertEquals(true, true);
		} catch (Exception e) {
			assertEquals(false, true);
		}

	}

	/**
	 * This function automates all test cases for test condition 09 Test Case
	 * description : Validate user ability to perform following on remittances from
	 * remittance list: 2. Save remittances
	 * 
	 */
	// @Test(priority = 6, groups = "regression", description = "Validate user
	// ability to perform following on remittances from remittance list: 2. Save
	// remittances")
	public void saveRemittane() throws Exception {
		String oldValue = getValue("remitName");
		goToUnderWritingTab();
		goToRemittanceList();
		click("remittanceSaveButton");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// TODO: handle exception
		}
		click("clickOK");
		String newValue = getValue("remitName");
		assertEquals(oldValue, newValue);
	}

	/**
	 * This function automates all test cases for test condition 09 Test Case
	 * description : Validate user ability to perform following on remittances from
	 * remittance list: 3. Load remittances *
	 */
	// @Test(priority = 6, groups = "regression", description = "Validate user
	// ability to perform following on remittances from remittance list: 3. Load
	// remittances")
	public void loadRemittane() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		String oldName = getValue("remitNumber");
		click("loadRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			// TODO: handle exception
		}
		contractExpander();
		String remittanceId = getValue("remittanceRemittanceIdOnContract");
		assertEquals(remittanceId, oldName);
	}

	/**
	 * This function automates all test cases for test condition 09 Test Case
	 * description : Validate user ability to perform following on remittances from
	 * remittance list: 4. View thumb nails of uploaded PDF.
	 * 
	 */
	// @Test(priority = 6, groups = "regression", description = "Validate user
	// ability to perform following on remittances from remittance list: 3. Load
	// remittances")
	public void thumbNailOfUploadedPDF() throws Exception {
		goToUnderWritingTab();
		goToRemittanceList();
		click("expandRemittance");
		try {
			click("lockContractYesButton");
		} catch (Exception e) {
			//// do nothing
		}
		assertEquals(thumbNailOfUploadedPDFOnTopRemittance(), true);
	}

	/**
	 * This function automates all test cases for test condition 09 Test Case
	 * description : Validate user ability to perform following on remittances from
	 * remittance list: 5. Apply filters with fields like source, type, sub type
	 * etc.
	 * 
	 */
	@Test(priority = 6, groups = "regression", description = "Validate user ability to perform following on remittances from remittance list: 3. Apply Filters")
	public void applyFiltersOnAllFields() throws Exception {
		HashMap<String, String> filtersData = getRemitDataForFilters();
		boolean flag = false;
		goToUnderWritingTab();
		goToRemittanceList();
		for (Map.Entry<String, String> entry : filtersData.entrySet()) {
			try {
				flag = false;
				flag = applyFiltersOnInputFieldAndVerifyData(entry.getKey(), entry.getValue());
				if (flag == false)
					break;
			} catch (Exception e) {
				continue;
			}
		}
		assertEquals(flag, true);
	}

}
