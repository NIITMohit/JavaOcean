package ocean.test.condition.pricing;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.PricingDataProvider;
import ocean.modules.pages.PricingModulePages;

/**
 * 
 * OCEAN_Pricing_TC_25 class automates Ocean Pricing module Test Condition 25
 * which holds 4 Test Cases; Test Condition 25 Description : Validate correct
 * underwriting class for a vehicle in contract, if related class item is 1.
 * Modified 2 Added (New vehicle addition) 3. Deleted 4 Ineligble
 * 
 * @author Shalu Chauhan
 * 
 *         Comments : As discussed with @shalu, @vivek : Classification list is
 *         not high priority, shalu created scripts but not as per shalu not
 *         executed even once. Hence marking OOS from automation.
 * 
 * @reviewer : Shalu Chauhan
 */
public class OCEAN_Pricing_TC_25 extends PricingModulePages {
	/**
	 * Validate correct underwriting class for a vehicle in contract, if related
	 * class item is modified.
	 */

	@Test(priority = 5, groups = {"none"}, dataProvider = "fetchDataForTC_25", dataProviderClass = PricingDataProvider.class, description = "Validate correct underwrting class for a vehicle in contract, if related class item is modified.")
	public void validateUnderwritingClassForModifiedClassificationList(String[] inputData) throws Exception {
		HashMap<Integer, HashMap<String, String>> contractFromRemittance = pricing_underwriting_getPendingContractwithRemittance();
		//// get remittance name and file name
		/// iterate to multiple contracts with same price sheet
		if (contractFromRemittance.size() > 0) {
			String remittName = contractFromRemittance.get(1).get("RemittanceName");
			String fileName = contractFromRemittance.get(1).get("FILE_NAME");
			//// visit underwriting tab
			goToUnderWritingTab();
			goToRemittanceList();
			//// Search a contract with pending state, remittance name and contract name is
			//// fetched from database
			searchContractwithPendingState(remittName, fileName);
			//// lock contract on user name and open enter values in contract window
			lockAndViewContract();
		} else {
			new SkipException("no remittance found");
		}
		///// Prepare Data
		HashMap<String, String> premiumData = prepareData(inputData);
		//// run query to get final data
		HashMap<String, String> premiumCalculation = setAllDataForPremiumCalculation(premiumData);
		premiumData.putAll(premiumCalculation);
		premiumData.put("PrimaryAccount", "Dealer");
		premiumData.put("SecondaryAccount", "Lender");
		premiumData.put("SecondaryAccountId", "24");
		if (premiumCalculation.size() > 1) {
			//// enter all mandatory values only on new business form screen
			premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
			//// Select Surcharges options, deductibles
			try {
				click("scrollContractsListDown");
			} catch (Exception e) {
				/// do nothing
			}
		}
		String UWclass = getClassValueFromUI();
		goToPricingTab();
		goToClassficationList();
		selectPriceSheetUnderClassficationList("ACL");
		clickCorrectMakeModel("Chevrolet");
		goToEditClassificationListTab();
		modifiedPriceSheetForClassItem();
		String UWclass1 = getClassValueAfterModification();
		assertEquals(UWclass, UWclass1);
	}

	/**
	 * Validate, if related class item is Deleted for a vehicle then no underwriting
	 * class won’t be display for that vehicle.
	 */
	@Test(priority = 5, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate, if related class item is Deleted for a vehicle then no underwriting class won’t be display for that vehicle.")
	public void validateUnderwritingClassForDeletedClassItem() throws Exception {
		goToPricingTab();
		goToClassficationList();
		selectPriceSheetUnderClassficationList("SNE");
		clickCorrectMakeModel("Hyundai");
		String Class1 = getClassValueAfterModification();
		goToEditClassificationListTab();
		deletedClassItemInPriceSheet();
		String Class2 = getClassValueAfterModification();
		assertNotEquals(Class1, Class2);
	}

	/**
	 * Validate, if related class item is Ineligble for a vehicle then no
	 * underwriting class won’t be display for that vehicle.
	 */
	@Test(priority = 5, groups = { "regression", "extendSmoke",
			"fullSuite" }, description = "Validate, if related class item is Ineligble for a vehicle then no underwriting class won’t be display for that vehicle.")
	public void validateUnderWritingClassForIneligbleClassItem() throws Exception {
		goToPricingTab();
		goToClassficationList();
		selectPriceSheetUnderClassficationList("ACL");
		clickCorrectMakeModel("Alder");
		selectInelibleForUnderWritingClass();
		String Uwclass = getClassValueAfterModification();
		goToEditClassificationListTab();
		editIneligibleVehicle();
		String UwClassw1 = getClassOfIneligibleVehicle();
		assertNotEquals(Uwclass, UwClassw1);
	}

	/**
	 * Validate correct underwriting class for a vehicle in contract, if related
	 * class item is added (New vehicle addition) under related price sheet.
	 */
	// @Test(priority = 5, groups = "regression", description = "Validate, if
	// related class item is Ineligble for a vehicle then no underwriting class
	// won’t be display for that vehicle.")
	public void validateUnderWritingClassAddedClassItem() throws Exception {
		goToPricingTab();
		goToClassficationList();
		selectClassificationList();
		goToEditClassificationListTab();
		//// code will be added when it will work
	}
}