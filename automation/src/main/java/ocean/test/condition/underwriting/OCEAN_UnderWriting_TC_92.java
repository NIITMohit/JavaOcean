package ocean.test.condition.underwriting;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

public class OCEAN_UnderWriting_TC_92 extends UnderwritingModulePages {

	@Test(priority = 1,   dataProvider = "fetchDataForTC92", dataProviderClass = UnderwritingDataProvider.class,  description = "Statement Tab - Detail Summary Screen - Order of Categories & Category Name Updates")
	public void checkOrderOfCategoriesOnAccountStatementScreen (String[] inputData) throws Exception {
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("Role_Type", inputData[0]);
		uiSearchData.put("RoleId", inputData[1]);
		uiSearchData.put("Month", inputData[2]);
		goToUnderWritingTab();
		goToAccountsDetailsTab();
		goToStatementsTab();
		typeRoletypeAndRoleIdUnderStatementsTab(uiSearchData.get("Role_Type"),uiSearchData.get("RoleId"));
		click("clickOnCalendarIconOnStatementsTab");;
		//click on desired month 
		clickOnCalenderMonth(uiSearchData.get("Month"));
		clickOnViewButtonUnderStatementsTab();
		waitForSomeTime(10);
		HashMap<Integer, HashMap<String, String>>  DbData = getOrderOfCategoriesFromDB();
		logger.info("DB DATA :" +DbData);
		boolean flag=false;
		HashMap<Integer, HashMap<String, String>> UiData=getOrderOfCategoriesFromUI();
		for (int i = 1; i <=UiData.size(); i++) {
			if(UiData.get(i).equals(DbData.get(i))) {
				flag=true;	
			}
		}
		assertTrue(flag, "Not able to locate Order Of categories");
	}

}
