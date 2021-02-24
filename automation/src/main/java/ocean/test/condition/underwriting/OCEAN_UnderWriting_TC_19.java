package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Cancel_TC_19 class automates Ocean Underwriting module Test Condition
 * 19, which holds 10 Test Cases; Test Condition Description : Validate
 * following for owner information on contract: 1. Input Name fields only. 2.
 * Input data in all fields. 3. Update owner details after contract is
 * processed.
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_UnderWriting_TC_19 extends UnderwritingModulePages {
	@Test(priority = 1, groups = "fullSuite", description = "validate user ability to Update owner details before contract is underwritten.")
	public void verifyOwnerAdressDetailsContractDataEntryContract() throws Exception {

		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getOnHoldContractwithRemittance();
		boolean result;

		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with OnHold state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			lockAndViewContract();
			waitForSomeTime(5);
			enterOwnerContactInfo(enterOwnerContactInfoInMap());
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				// TODO: handle exception
			}
			click("clickPremiumCalculate");
			contractScrollToTop();
			click("clickUnderW");
			//// check for successful underwritten
			assertEquals(checkForFileStatus(fileName), "2");
			if (fetchOwnerContactInfofromUI().equals(enterOwnerContactInfoInMap())) {
				result = true;
			} else {
				result = false;
			}

			assertEquals(result, true);
		} else {
			new SkipException("no value in DB exists");
		}
	}

	@Test(priority = 2, groups = "fullSuite", description = "validate user ability to Update owner details after contract is processed.")
	public void verifyOwnerAdressDetailsContractDataEntryForProcessedContract() throws Exception {
		boolean result;
		String contractId = cancellation_getContractIdBasedOnStatus("Active");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(contractId);
			waitForSomeTime(10);
			takeScreenshot();
			result = verifyUIDataWithDBOwner(contractId, enterOwnerContactInfoInMap(),
					enterOwnerInfoWithoutPhoneAndEmailInMap());
			if (result) {
				assertTrue(true, "Data entered on UI did not matched with DB.");
			}
		} else {
			new SkipException("no value exist in db.");
		}
	}
}
