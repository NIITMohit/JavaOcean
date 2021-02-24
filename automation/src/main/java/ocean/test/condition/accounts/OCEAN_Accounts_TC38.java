package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.AccountsDataProvider;
import ocean.modules.pages.AccountsModulePages;

/**
 * PBI_16174 Make sure Westlake, Western Finance codes are in Ocean IN UI and in
 * Database
 * 
 * @author Poonam Kalra
 * 
 * @reviewer : Poonam Kalra
 */
public class OCEAN_Accounts_TC38 extends AccountsModulePages {

	@Test(priority = 1, groups = { "regression", "extendSmoke",
	"fullSuite" }, dataProviderClass = AccountsDataProvider.class, description = "Make sure Westlake, Western Finance codes are in Ocean.")
public void vrifyWestlakePartnerCodeAndWesternFundingCodeInOcean() throws Exception {
boolean dbFlag = false;
HashMap<String, String> dbValidation = dBValue();
if (dbValidation.get("NAME").equalsIgnoreCase("Westlake Partner Code")
		|| dbValidation.get("NAME").equalsIgnoreCase("Western Funding Code")) {
	dbFlag = true;
	logger.info("Database Validation ====" + dbValidation);
}
assertEquals(dbFlag, true);

HashMap<String, String> uiSearchData = new HashMap<String, String>();
// put all the excel sheet data in Hash map
uiSearchData.put("Role_Type", dbValidation.get("ROLE_NAME"));
uiSearchData.put("role_id", dbValidation.get("ROLE_IDENTIFIER"));
// Navigate to Account tab
goToAccountsTab();
goToAccountsSearchTab();
// // run code for search
searchContractGivenInputParamaters(uiSearchData);
selectTopAccountOnTheBasisOfRoleType();
waitForSomeTime(25);
boolean matchFlag = verifyWestlakeWesternCodeInUI(dbValidation.get("ROLE_IDENTIFIER"),dbValidation.get("NAME"));
assertEquals(matchFlag, true);
}

}
