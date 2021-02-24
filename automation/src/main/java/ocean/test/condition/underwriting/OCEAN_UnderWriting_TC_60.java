package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_60 class automates Ocean Underwriting module Test
 * Condition 60, which holds 3 Test Cases; Validate that OCEAN allow user to
 * perform following for ACH: 1. Create ACH account 2. Update ACH account 3.
 * Delete ACH account.
 * 
 * @author Atul Awasthi
 */
public class OCEAN_UnderWriting_TC_60 extends UnderwritingModulePages {
	String roleId = "";

	/**
	 * Validate that OCEAN allow user to perform Create ACH account
	 */
	@Test(priority = 1, groups = "fullSuite", description = "Validate that OCEAN allow user to perform Create ACH account ")
	public void verifyCreateACHAccount() throws Exception {
		roleId = getRoleIdForContractDataEntry("Lender");
		if (roleId.length() > 0) {
			goToUnderWritingTab();
			click("clickACHEntryTab");
			if (verifyCreateACHEntry(roleId)) {
				assertTrue(true, "ACH Entry not Created");
			}
		} else
			throw new Exception("Data not exists in DB");
	}

	/**
	 * Validate that OCEAN allow user to perform Update ACH account
	 */
	@Test(priority = 2, groups = "regression", description = "Validate that OCEAN allow user to perform Update ACH account ")
	public void verifyUpdateACHAccount() throws Exception {
		goToUnderWritingTab();
		click("clickACHEntryTab");
		if (verifyUpdateACHEntry(roleId)) {
			assertTrue(true, "ACH Entry not updated");
		}
	}

	/**
	 * Validate that OCEAN allow user to perform DeleteACH account
	 * 
	 * @throws Exception
	 */
	@Test(priority = 3, groups = "fullSuite", description = "Validate that OCEAN allow user to perform Delete ACH account ")
	public void verifyDeleteACHAccount() throws Exception {
		goToUnderWritingTab();
		click("clickACHEntryTab");
		if (verifyDeleteACHEntry(roleId)) {
			assertTrue(true, "ACH Entry not deleted");
		}

	}
}
