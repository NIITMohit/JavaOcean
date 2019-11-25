package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationDataProvider;
import ocean.modules.pages.cancellationModulePages;

/**
 * OCEAN_Cancel_TC_06 class automates Ocean Cancel module Test Condition 06,
 * which holds 2 Test Case; Test Condition Description : Validate OCEAN ability
 * to assign and reassign cancel method on the basis of cancellation details.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_06 extends cancellationModulePages {
	/**
	 * This function automates test case for test condition 02; Test Case
	 * description : Validate creation and blocking of cancellation request on the
	 * basis of contract status.
	 * 
	 */
	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC06", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN assign cancel method as Flat (F), if cancel date is within flat cancel period.")
	public void verifyFlatCancelRuleWithInCancelPeriod(String[] inputData) throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", inputData[0]);
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDate(contractList.get("SALE_DATE"), 1), "");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// get cancel method applied as flat or outside flat
			String cancelMethodType = returnCancelMethodValue();
			//// assert flat or not flat
			assertEquals(cancelMethodType.toLowerCase(), "f");
		} else {
			new SkipException("no value exist in db for SNE");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC06", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN reassign cancel method on a contract, if cancel date is modified by user as outside flat cancel period.")
	public void verifyOutsideFlatCancelRuleWithInCancelPeriod(String[] inputData) throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", inputData[0]);
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDate(contractList.get("SALE_DATE"), 1), "");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// get cancel method applied as flat or outside flat
			String cancelMethodType = returnCancelMethodValue();
			//// assert flat or not flat
			assertEquals(cancelMethodType.toLowerCase(), "t");
		} else {
			new SkipException("no value exist in db for SNE");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC06", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN reassign cancel method on a contract, if cancel date is modified by user as outside flat cancel period.")
	public void verifyBoundryValueFlatCancelPeriod(String[] inputData) throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", inputData[0]);
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDate(contractList.get("SALE_DATE"), 1), "");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// get cancel method applied as flat or outside flat
			String cancelMethodType = returnCancelMethodValue();
			//// assert flat or not flat
			assertEquals(cancelMethodType.toLowerCase(), "t");
		} else {
			new SkipException("no value exist in db for SNE");
		}
	}

	@Test(priority = 2, groups = "regression", dataProvider = "fetchDataForTC06", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN reassign cancel method on a contract, if cancel date is modified by user as outside flat cancel period.")
	public void verifyBoundryValueFlatOutSideCancelPeriod(String[] inputData) throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = getContractIdBasedOnStatusAndPriceSheet("processed", inputData[0]);
		if (contractList.get("CERT").length() > 0) {
			//// Navigate to Mail service tab
			goToCancellationTab();
			goToMailServiceTab();
			//// create search data in hash map
			HashMap<String, String> uiSearchData = new HashMap<String, String>();
			uiSearchData.put("CERT", contractList.get("CERT"));
			//// Search Data based on contract Id
			searchContractGivenInputParamaters(uiSearchData);
			//// navigate to new cancel tab
			clickCancelButtonAndNavigateToNewCancellationTab();
			//// enter valid values on new cancellation tab screen and click calculate
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					convertDate(contractList.get("SALE_DATE"), 1), "");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// click ok for cancellation completed successfully
			click("clickOk");
			//// get cancel method applied as flat or outside flat
			String cancelMethodType = returnCancelMethodValue();
			//// assert flat or not flat
			assertEquals(cancelMethodType.toLowerCase(), "t");
		} else {
			new SkipException("no value exist in db for SNE");
		}
	}

}
