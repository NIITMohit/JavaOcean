package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_PBI_17918 class which holds 4 Test Case; Test Condition
 * Description : Validate that the price sheet should remain same, when the
 * secondary account (Lender, B-List) detail is changed by new business process.
 * Validate that the price sheet should remain same, when the secondary account
 * (Lender, B-List) detail is changed with primary as dealer by Adjust contract.
 * Validate that the price sheet should remain same, when the secondary account
 * (Lender, B-List) is changed with primary as dealer by on hold contract.
 * Validate that price sheet name is updated as blank for a contract, if
 * secondary account (dealer) is changed with primary account as Lender class A.
 * 
 * @author Atul Awasthi
 */
public class OCEAN_Underwriting_TC_82 extends UnderwritingModulePages {
	/**
	 * This function automates Test Case-1; Test Case description : Validate that
	 * the price sheet should remain same, when the secondary account (Lender,
	 * B-List) detail is changed by new business process.
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke",
			"fullSuite" }, description = " Validate that the price sheet should remain same, "
					+ "when the secondary account (Lender, B-List) detail is changed by new business process.")
	public void validatePricesheetIdExistanceThruBusinessProcess() throws Exception {
		HashMap<String, String> contractFromRemittance = getUnderWContractForBListLender();
		String remittanceName = contractFromRemittance.get("RemittanceName");
		String cert = contractFromRemittance.get("CERT");
		String sId = contractFromRemittance.get("SECONDARY_ACCOUNT_ID");
		if (contractFromRemittance.size() > 0) {
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittanceName, cert);
			//// lock contract on uF34va3wrser name and open enter values in contract window
			lockAndViewContract();
			String priceSheetold = getTextOfElement("selectPricesheet");
			String newSecid = roleIdentfier(sId);
			type("secondaryAccountId", newSecid);
			click("secondaryAccountSearchButton");
			takeScreenshot();
			String priceSheet = getTextOfElement("selectPricesheet");
			assertEquals(priceSheet, priceSheetold);
		} else {
			throw new SkipException("no value exist in db for processed status");
		}
	}

	/**
	 * This function automates Test Case-2; Test Case description : Validate that
	 * the price sheet should remain same, when the secondary account (Lender,
	 * B-List) detail is changed with primary as dealer by Adjust contract.
	 * 
	 */
	@Test(priority = 1, groups = "smoke", description = "Validate that the price sheet should remain same, "
			+ "when the secondary account (Lender, B-List) detail is changed with primary as dealer by Adjust contract.")
	public void validatePricesheetIdExistanceThruContractAdjustment() throws Exception {
		HashMap<String, String> contractFromRemittance = cancellation_getContractIdBasedOnStatusBList("Active");
		String ContractIDForAdjustment = contractFromRemittance.get("CERT");
		String sId = contractFromRemittance.get("SECONDARY_ACCOUNT_ID");
		if (ContractIDForAdjustment.length() > 0) {
			goToUnderWritingTab();
			goToFindContractTab();
			processForAccountSearchForContractAdjustment(ContractIDForAdjustment);
			String priceSheetold = getTextOfElement("selectPricesheet");
			type("secondaryAccountId", roleIdentfier(sId));
			click("secondaryAccountSearchButton");
			takeScreenshot();
			String priceSheet = getTextOfElement("selectPricesheet");
			assertEquals(priceSheet, priceSheetold);
		} else {
			throw new SkipException("no value exist in db for processed status");
		}
	}

	/**
	 * This function automates Test Case-3; Test Case description : Validate that
	 * the price sheet should remain same, when the secondary account (Lender,
	 * B-List) is changed with primary as dealer by on hold contract.
	 * 
	 */
	@Test(priority = 1, groups = "smoke", dataProvider = "fetchDataForTC_05_06", dataProviderClass = UnderwritingDataProvider.class, description = "Validate that the price sheet should remain same, "
			+ "when the secondary account (Lender, B-List) is changed with primary as dealer by on hold contract.")
	public void validatePricesheetIdExistanceThruOnHold(String[] inputData) throws Exception {
		HashMap<String, String> contractFromRemittance = cancellation_getContractIdBasedOnStatusBList("OnHOLD");
		String contractId = contractFromRemittance.get("CERT");
		String sId = contractFromRemittance.get("SECONDARY_ACCOUNT_ID");
		if (contractId.length() > 0) {
			goToUnderWritingTab();
			goToHoldContactTab();
			searchAndLandToNewBusinessFromOnHoldContracts(contractId);
			String priceSheetold = getTextOfElement("selectPricesheet");
			type("secondaryAccountId", roleIdentfier(sId));
			click("secondaryAccountSearchButton");
			takeScreenshot();
			String priceSheet = getTextOfElement("selectPricesheet");
			assertEquals(priceSheet, priceSheetold);
		} else {
			throw new SkipException("no value exist in db for processed status");
		}
	}

	/**
	 * This function automates Test Case-4; Test Case description : Validate that
	 * price sheet name is updated as blank for a contract, if secondary account
	 * (dealer) is changed with primary account as Lender class A.
	 * 
	 */
	@Test(priority = 1, groups = "smoke", description = " Validate that price sheet name is updated as blank for a contract, "
			+ "if secondary account (dealer) is changed with primary account as Lender class A.")
	public void validatePricesheetIdExistanceSecondaryAccountDealer() throws Exception {
		HashMap<String, String> contractFromRemittance = getLenderPrimaryDealerSecondary();
		String remittanceName = contractFromRemittance.get("remittancename");
		String cert = contractFromRemittance.get("cert");
		String sId = getSecondryDealer(contractFromRemittance);
		if (contractFromRemittance.size() > 0 && sId.length() > 0) {
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittanceName, cert);
			//// lock contract on uF34va3wrser name and open enter values in contract window
			lockAndViewContract();
			type("secondaryAccountId", sId);
			click("secondaryAccountSearchButton");
			takeScreenshot();
			String priceSheet = getTextOfElement("selectPricesheet");
			String mileageband = getTextOfElement("getMielage");
			String classType = getTextOfElement("getClass");
			String term = getTextOfElement("selectPricesheetTerm");
			String coverage = getTextOfElement("selectPricesheetCoverage");
			assertEquals(priceSheet.equals("") && mileageband.equals("") && classType.equals("") && term.equals("")
					&& coverage.equals(""), true);
		} else {
			throw new SkipException("no value exist in db for processed status");
		}
	}
}
