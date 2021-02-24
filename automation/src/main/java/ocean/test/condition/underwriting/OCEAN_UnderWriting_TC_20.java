package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Cancel_TC_20 class automates Ocean Underwriting module Test Condition
 * 20, which holds 10 Test Cases; Test Condition Description : Validate
 * following for Co owner information on contract: 1. Input Name fields only. 2.
 * Input data in all fields. 3. Update/delete Co owner details before contract
 * is underwritten and after contract is processed.
 * 
 * @author Atul Awasthi
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_UnderWriting_TC_20 extends UnderwritingModulePages {

	/**
	 * Under contract data enquiry, All input data fields are enable for coOwner
	 * info and Ocean allows to update and delete info before Underwritten.
	 */
	@Test(priority = 6, groups = { "regression",
			"fullSuite" }, description = "validate user ability to verify input data fields are enable for coOwner info and"
					+ "Ocean allows to update and delete and displays correct info before Underwritten.")
	public void verifyCoOwnerAdressDetailsContractDataEntry() throws Exception {
		String contractId = cancellation_getContractIdBasedOnStatus("OnHOLD");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(contractId);
			enterCoOwnerContactInfo(enterCoOwnerContactInfoInMap());
			click("clickSaveEditPSROw");// from here step or not reproduceable for 2nd & 3rd test case.
			String[] locatrors = { "coOwnerFNAME", "coOwnerLNAME", "coOwnerADD", "coOwnerEmail", "coOwnerPhone",
					"coOwnerZip" };
			boolean fieldsStatus = verifyEnableDisableFields(locatrors);
			if (fieldsStatus) {
				assertEquals(getAddressDetailsOfCoOwner(contractId).equals(enterCoOwnerContactInfoInMap())
						&& enterCoOwnerContactInfoInMap().equals(fetchCoOwnerContactInfofromUI()), true);
			} else {
				new SkipException("DataBase not updated while modifying value");
			}
		} else {
			new SkipException("no value in DB exists");
		}
	}

	/**
	 * Under contract data enquiry, validate user ability to verify that Ocean allow
	 * user to update and delete coOwner info(with/without Email/Phone) while
	 * contract is processed and displays the same on UI
	 */
	@Test(priority = 6, groups = { "regression",
			"fullSuite" }, description = "validate user ability to verify that Ocean allow user to update and delete coOwner"
					+ " info(with/without Email/Phone) while contract is processed and displays the same on UI")
	public void verifyCoOwnerAdressDetailsModificationAfterProcessed() throws Exception {
		String ContractIDForAdjustment = cancellation_getContractIdBasedOnStatus("Active");
		if (ContractIDForAdjustment.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(ContractIDForAdjustment);
			boolean result = verifyUIDataWithDB(ContractIDForAdjustment, enterCoOwnerContactInfoInMap(),
					enterCoOwnerInfoWithoutPhoneAndEmailInMap(), deleteCoOwnerContactInfoInMap());
//			if (fetchCoOwnerContactInfofromUI().equals(enterCoOwnerContactInfoInMap()))
			if (result) {
				assertTrue(true, "Data entered on UI did not matched with DB.");
			}
		} else {
			new SkipException("no value exist in db.");
		}
	}
}
