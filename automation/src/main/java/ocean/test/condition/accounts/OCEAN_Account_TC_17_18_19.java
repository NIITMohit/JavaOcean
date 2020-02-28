package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * OCEAN_Account_TC_17_18_19 class automates Ocean Account module Test Condition
 * 17,18,19, which holds 48 Test Cases; Test Condition Description : Validate
 * for a role type dealer that following components of base premium are
 * correctly calculated on a contract , if exception are not added/
 * added/modified/deleted for: 1. Commission 2. Reserve 3. Administration 4.
 * Insurance Note : Exception for cancel method as NCB/Offset shall be tested
 * under cancellation
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Account_TC_17_18_19 extends AccountsModulePages {

	/**
	 * This function automates all test cases for test condition 17,18,19; Test Case
	 * description : Validate for a role type dealer, agent, lender that following
	 * components of base premium are correctly calculated on a contract , if
	 * exception are not added/ added/modified/deleted for: 1. Commission 2. Reserve
	 * 3. Administration 4. Insurance Note : Exception for cancel method as
	 * NCB/Offset shall be tested under cancellation.
	 * 
	 */

	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForTC_17_18_19", dataProviderClass = AccountsDataProvider.class, description = "Validate that add,delete and edit the exceptions on the basis of RoleType like Agent,Lender, Dealer .")
	public void addDeleteAndEditExceptionOnTheBasisOfRoletype(String[] inputData) throws Exception {
		// // create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		// put all the excel sheet data in Hash map
		uiSearchData.put("Role_Type", inputData[0]);
		uiSearchData.put("Status", inputData[1]);
		// // Navigate to mail service tab
		goToAccountsTab();
		goToAccountsSearchTab();
		// // run code for search
		searchContractGivenInputParamaters(uiSearchData);
		// put all the excel sheet data in Hash map
		uiSearchData.put("PriceSheet", inputData[2]);
		uiSearchData.put("ExceptionType", inputData[3]);
		uiSearchData.put("Class ", inputData[4]);
		uiSearchData.put("Coverage", inputData[5]);
		uiSearchData.put("Term", inputData[6]);
		uiSearchData.put("Payee", inputData[7]);
		uiSearchData.put("Bucket", inputData[8]);
		uiSearchData.put("cancelMethod", inputData[9]);
		uiSearchData.put("$ForCommissions", inputData[10]);
		uiSearchData.put("AdminBucket", inputData[11]);
		uiSearchData.put("ReserveBucket", inputData[12]);
		uiSearchData.put("InsuranceBucket", inputData[13]);
		uiSearchData.put("EffectiveDate", inputData[14]);
		uiSearchData.put("NCBAdministration", inputData[15]);
		uiSearchData.put("NCBFee", inputData[16]);
		uiSearchData.put("WaitingPeriod", inputData[17]);
		uiSearchData.put("NCBRetail", inputData[18]);
		uiSearchData.put("Edit", inputData[19]);
		uiSearchData.put("Delete", inputData[20]);
		// // select first account for every role type like Agent, lender and
		// dealer
		selectTopAccountOnTheBasisOfRoleType();
		// waitForSomeTime(2);
		// // setup a new price sheet
		clickForSetUpNewPricing();
		// Verify payee is disabled for Agent or Enable for lender
		verifyAndSelectPayee(uiSearchData);
		// type priceSheet code to select the priceSheet
		typePriceSheetCodeMaster(inputData[2], inputData[0]);
		// assign price sheet to add the exception
		selectPriceSheetToAssigntToAddExceptions(uiSearchData);
		// add Commissions exception on priceSheet
		addCommissionsExceptions(uiSearchData);
		// add NCBAdministartions exception on priceSheet
		addNCBAdministartionsExceptions(uiSearchData);
		// add Administraion Exception on priceSheet
		addAdministraionExceptions(uiSearchData);
		// add Reserve Exception on priceSheet
		addReserveException(uiSearchData);
		// add Insurance Exception on priceSheet
		addInsuranceExceptions(uiSearchData);
		// click on save button to add the exceptions
		click("savePriceSheet");
		// go to account details to for edit and delete
		goToAccountsDetailsTab();
		// get the internal name priceSheet for validation
		uiSearchData.put("internalName", getInternalNameOfPriceSheet());
		// edit the exception on the basis of Commissions, NCBAdministration,
		// Administration,Reserve,Insurance
		// Delete the price sheet which we have added the exceptions on Agent or
		// Lender or dealer
		// Code to be added
		editAndDeletePriceSheetExceptions(uiSearchData);
		// Validate the exception on the basis of class,
		// coverage,term,internal,exception type for agent, lender and dealer
		HashMap<Integer, HashMap<String, String>> actualExceptionData = validateExceptionDataOnTheBasisOfInternalName(
				uiSearchData.get("internalName"));
		Map<String, String> map = new HashMap<>();
		String defaultValue = " ";
		boolean flag = true;
		for (Entry<Integer, HashMap<String, String>> entry : actualExceptionData.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			Integer map1 = entry.getKey();
			HashMap<String, String> value1 = entry.getValue();
			Map<String, String> itreatemap = replaceNullValues(value1, defaultValue);
			for (Entry<String, String> entry1 : value1.entrySet()) {
				String mapkey = entry1.getKey();
				String mapvalue = entry1.getValue();
				HashMap<String, String> expectedExceptionData = getExceptionsOnTheBasisOfPriceSheet();
				String comparisonData = expectedExceptionData.get(mapkey);
				if (mapvalue.equals(comparisonData)) {
					flag = true;
				} else {
					flag = false;
					break;
				}
			}
		}
		assertEquals(flag, true);
	}
}
