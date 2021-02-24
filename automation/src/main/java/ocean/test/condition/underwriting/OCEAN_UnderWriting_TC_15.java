package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_Cancel_TC_15 class automates Ocean Underwriting module Test Condition
 * 15, which holds 12 Test Cases; Test Condition Description : For contract data
 * enquiry, validate the following for contract/ contract adjustment: 1. Ability
 * to search account on the basis of type, role ID and name only. 2. Display
 * account search and allow user to apply filters. 3. Auto populate required
 * details for selected account from OCEAN/Account search screen.
 * 
 * @author Atul Awasthi
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_UnderWriting_TC_15 extends UnderwritingModulePages {

	/**
	 * Under contract data enquiry, validate user ability to search account on the
	 * basis of type, role ID and required fields are auto populated for selected
	 * account from Ocean for contract
	 */
	@Test(priority = 1, groups = "fullSuite", description = "validate user ability to search account on the basis of type, role ID and required fields are auto populated for selected account from Ocean for contract")
	public void verifySearchButtonStatusAndAddressDetailInContractDataEntry() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
			if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			HashMap<String, String> data = processForAccountSearch(remittName, fileName);
			boolean flag = verifySearchEnableDisable(data);
			if (flag) {
				assertEquals(verifyAddressDetailsInContractDataEntry(data), true);
			} else {
				new SkipException("Search button not enable");
			}
			waitForSomeTime(5);
			contractExpander();	
		} else {
			new SkipException("no value exist in db.");
		}
	}

	/**
	 * validate Ocean display account search and allow user to apply filters and
	 * required fields are auto populated for selected account from account search
	 * screen for contract
	 */
	@Test(priority = 2, groups = "fullSuite", description = "validate Ocean display account search and allow user to apply filters and required fields are auto populated for selected account from account search screen for contract")
	public void verifyDealerAndLenderContactInformationInAccounts() throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			goToUnderWritingTab();
			goToRemittanceList();
			refreshRemittance();
			HashMap<String, String> data = processForAccountSearch(remittName, fileName);
			assertEquals(verifyUIAddressDetailsOfDealerOnAccoountsPopUp(data)
					&& verifyUIAddressDetailsOfLenderOnAccoountsPopUp(data), true);
			waitForSomeTime(5);
			contractExpander();
		} else {
			new SkipException("no value exist in db.");
		}

	}

	/**
	 * validate Ability to search account on the basis of type, role ID and name
	 * only and required fields are auto populated for selected account from Ocean
	 * for contract adjustment
	 */
	@Test(priority = 3, groups = "fullSuite", description = "validate Ability to search account on the basis of type, role ID and name only and required fields are auto populated for selected account from Ocean for contract adjustment")
	public void verifySearchButtonStatusAndAddressDetailInContractDataEntryForContractAdjustment() throws Exception {
		String ContractIDForAdjustment = cancellation_getContractIdBasedOnStatus("Active");
		if (ContractIDForAdjustment.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(ContractIDForAdjustment);
			HashMap<String, String> data = processForAccountSearchCotractAdjustment();
			boolean flag = verifySearchEnableDisable(data);
			if (flag) {
				assertEquals(verifyAddressDetailsInContractDataEntry(data), true);
			} else {
				new SkipException("Search button not enable");
			}
			waitForSomeTime(5);
			contractExpander();
		} else {
			new SkipException("no value exist in db for processed status");
		}

	}

	/**
	 * validate Display account search and allow user to apply filters and required
	 * fields are auto populated for selected account from account search screen for
	 * contract adjustment
	 */
	@Test(priority = 4, groups = "fullSuite", description = "validate Display account search and allow user to apply filters and required fields are auto populated for selected account from account search screen for contract adjustment")
	public void verifyDealerAndLenderContactInformationInAccountsForContractAdjustment() throws Exception {
		String ContractIDForAdjustment = cancellation_getContractIdBasedOnStatus("Active");
		if (ContractIDForAdjustment.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(ContractIDForAdjustment);
			HashMap<String, String> data = processForAccountSearchCotractAdjustment();
			assertEquals(verifyUIAddressDetailsOfDealerOnAccoountsPopUp(data)
					&& verifyUIAddressDetailsOfLenderOnAccoountsPopUp(data), true);
			waitForSomeTime(5);
			contractExpander();
	    	} 
		else {
			new SkipException("no value exist in db for processed status");
	     	}
		
	}
}
