package ocean.test.condition.accounts;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
 * 
 * @reviewer : Poonam Kalra
 */

public class OCEAN_Account_TC_17_18_19_25 extends AccountsModulePages {
	/**
	 * This function automates all test cases for test condition 17,18,19; Test Case
	 * description : Validate for a role type dealer, agent, lender that following
	 * components of base premium are correctly calculated on a contract , if
	 * exception are not added/ added/modified/deleted for: 1. Commission 2. Reserve
	 * 3. Administration 4. Insurance Note : Exception for cancel method as
	 * NCB/Offset shall be tested under cancellation. groups = { "regression", "",
	 * "fullSuite" }
	 * 
	 */
	@Test(priority = 1, groups = { "regression", "smoke", "extendSmoke",
			"fullSuite" }, dataProvider = "fetchDataForTC_17_18_19", dataProviderClass = AccountsDataProvider.class, description = "Validate that add,delete and edit the exceptions on the basis of RoleType like Agent,Lender, Dealer .")
	public void addDeleteAndEditExceptionOnTheBasisOfRoletype(String[] inputData) throws Exception {
		// // create data to fill required values in search window
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		// put all the excel sheet data in Hash map
		uiSearchData.put("Role_Type", inputData[0].trim());
		uiSearchData.put("role_id", inputData[3].trim());
		uiSearchData.put("Status", inputData[1].trim());
		// // Navigate to mail service tab
		goToAccountsTab();
		goToAccountsSearchTab();
		// // run code for search
		searchContractGivenInputParamaters(uiSearchData);
		if (!(uiSearchData.get("Role_Type").length() > 1))
			uiSearchData.put("Role_Type", getValue("listOfRoleType", 0));
		if (!(uiSearchData.get("role_id").length() > 1))
			uiSearchData.put("role_id", getValue("listOfRoleId", 0));
		if (!(uiSearchData.get("Status").length() > 1))
			uiSearchData.put("Status", getValue("listOfStatus", 0));
		uiSearchData.put("LenderAorB", "");
		//// save lender class a or b
		/// swipe left
		if (uiSearchData.get("Role_Type").toLowerCase().equals("lender")) {
			// click("swipeRight");
			//// capture value
			uiSearchData.put("LenderAorB", getValue("selectLenderClassificationListAorB"));
			/// swipe right
			// click("swipeLeft");
		}
		// put all the excel sheet data in Hash map
		uiSearchData.put("PriceSheet", inputData[2].trim());
		uiSearchData.put("ExceptionType", inputData[4].trim());
		uiSearchData.put("Class", inputData[5].trim());
		uiSearchData.put("Coverage", inputData[6].trim());
		uiSearchData.put("Term", inputData[7].trim());
		uiSearchData.put("Payee", inputData[8].trim());
		uiSearchData.put("Lender", inputData[9].trim());
		uiSearchData.put("EffectiveDate", inputData[11].trim());
		uiSearchData.put("MainEffectiveDate", inputData[10].trim());
		uiSearchData.put("Bucket", inputData[12].trim());
		uiSearchData.put("BucketPayee", inputData[13].trim());
		uiSearchData.put("cancelMethod", inputData[14].trim());
		uiSearchData.put("CommissionsAmount", inputData[15].trim());
		uiSearchData.put("NCBAdministration", inputData[16].trim());
		uiSearchData.put("NCBFee", inputData[17].trim());
		uiSearchData.put("WaitingPeriod", inputData[18].trim());
		uiSearchData.put("NCBRetail", inputData[19].trim());
		uiSearchData.put("NCBRetailMax", inputData[20].trim());
		uiSearchData.put("AdminBucket", inputData[21].trim());
		uiSearchData.put("AdminBucketAmt", inputData[22].trim());
		uiSearchData.put("ReserveBucket", inputData[23].trim());
		uiSearchData.put("ReserveBucketAmt", inputData[24].trim());
		uiSearchData.put("InsuranceBucket", inputData[25].trim());
		uiSearchData.put("InsuranceBucketAmt", inputData[26].trim());
		uiSearchData.put("Edit", inputData[27].trim());
		uiSearchData.put("Delete", inputData[28].trim());
		// // select first account for every role type like Agent, lender and
		// dealer
		selectTopAccountOnTheBasisOfRoleType();
		// // setup a new price sheet
		waitForSomeTime(2);
		clickForSetUpNewPricing();
		waitForSomeTime(2);
		try {
			click("scrollContractsListUp", 7);
		} catch (Exception e) {
		}
		// Verify payee is disabled for Agent or Enable for lender
		verifyAndSelectPayee(uiSearchData);
		// type priceSheet code to select the priceSheet
		typePriceSheetCodeMaster(inputData[2], inputData[0]);
		// assign price sheet to add the exception
		String pricesheet = selectPriceSheetToAssigntToAddExceptions(uiSearchData);
		// add Commissions exception on priceSheet
		if (uiSearchData.get("Bucket").length() > 1)
			addCommissionsExceptions(uiSearchData);
		// add NCBAdministartions exception on priceSheet
		if (uiSearchData.get("NCBAdministration").length() > 1)
			addNCBAdministartionsExceptions(uiSearchData);
		try {
			click("scrollContractsListDown", 7);
		} catch (Exception e) {
		}
		// add Administration Exception on priceSheet
		if (uiSearchData.get("AdminBucket").length() > 1)
			addAdministraionExceptions(uiSearchData);
		// add Reserve Exception on priceSheet
		if (uiSearchData.get("ReserveBucket").length() > 1)
			addReserveException(uiSearchData);
		// add Insurance Exception on priceSheet
		if (uiSearchData.get("InsuranceBucket").length() > 1)
			addInsuranceExceptions(uiSearchData);
		// click on save button to add the exceptions
		click("savePriceSheet");
		// go to account details to for edit and delete
		// get the internal name priceSheet for validation
		uiSearchData.put("internalName", pricesheet);
		try {
			click("clickOK");
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			goToAccountsDetailsTab();
		} catch (Exception e) {
			// TODO: handle exception
		}
		// edit the exception on the basis of Commissions, NCBAdministration,
		// Administration,Reserve,Insurance
		// Delete the price sheet which we have added the exceptions on Agent or
		// Lender or dealer
		// Code to be added
		if (uiSearchData.get("Delete").toLowerCase().equals("y") || uiSearchData.get("Edit").toLowerCase().equals("y"))
			editAndDeletePriceSheetExceptions(uiSearchData);
		// Validate the exception on the basis of class,
		// coverage,term,internal,exception type for agent, lender and dealer
		HashMap<Integer, HashMap<String, String>> actualExceptionData = validateExceptionDataOnTheBasisOfInternalName(
				uiSearchData.get("internalName"));
		if (uiSearchData.get("LenderAorB").toLowerCase().equals("b-list")) {
			if (actualExceptionData.size() > 1) {
				assertEquals(true, false);
			} else {
				assertEquals(false, false);
			}
		} else {
			HashMap<Integer, HashMap<String, Boolean>> result = new HashMap<Integer, HashMap<String, Boolean>>();
			int m = 1;
			for (Entry<Integer, HashMap<String, String>> entry : actualExceptionData.entrySet()) {
				Boolean flag = true;
				Boolean flag2 = false;
				Boolean flag3 = false;
				Boolean flag4 = false;
				Boolean flag5 = false;
				for (Entry<String, String> entry1 : entry.getValue().entrySet()) {
					String mapkey = entry1.getKey();
					String mapvalue = entry1.getValue();
					//// not to be part of smoke, run it in sanity
					/*
					 * //// not to be part of smoke HashMap<String, String> expectedExceptionData =
					 * getExceptionsOnTheBasisOfPriceSheet(); String comparisonData =
					 * expectedExceptionData.get(mapkey); if (mapvalue.equals(comparisonData)) {
					 * flag = true; } else { flag = false; break; }
					 */
					if (mapvalue != null && !mapvalue.toLowerCase().equals("0")) {
						if (mapkey.equalsIgnoreCase("name")) {
							for (int i = 0; i < inputData.length; i++) {
								if (inputData[i].contains(mapvalue)) {
									flag2 = true;
									break;
								}
							}
						} else if (mapkey.equals("EffectiveDate")) {
							String dbValue = mapvalue;
							String excelValue = uiSearchData.get(mapkey);
							// 2020-03-24 00:00:00.000
							dbValue = dbValue.substring(0, dbValue.indexOf(" "));
							Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dbValue);
							//// parse date to new MM/dd/yyyy format
							String abc = new SimpleDateFormat("MM-dd-yyyy").format(date1);
							//// use calendar to add days
							Calendar c = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
							// Setting the date to the given date
							c.setTime(sdf.parse(abc));
							// Date after adding the days to the current date
							String newDate = sdf.format(c.getTime());
							if (excelValue.equals(newDate))
								flag4 = true;

						} else if (mapkey.equals("MainEffectiveDate")) {
							String dbValue = mapvalue;
							String excelValue = uiSearchData.get(mapkey);
							dbValue = dbValue.substring(0, dbValue.indexOf(" "));
							Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dbValue);
							//// parse date to new MM/dd/yyyy format
							String abc = new SimpleDateFormat("MM-dd-yyyy").format(date1);
							//// use calendar to add days
							Calendar c = Calendar.getInstance();
							SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
							// Setting the date to the given date
							c.setTime(sdf.parse(abc));
							// Date after adding the days to the current date
							String newDate = sdf.format(c.getTime());
							if (excelValue.equals(newDate))
								flag5 = true;
						} else if (mapkey.contains("CommissionsAmount")) {
							for (int i = 0; i < inputData.length; i++) {
								if (mapvalue.contains(inputData[i])) {
									flag3 = true;
									break;
								}
							}
						} else {
							if (!mapvalue.contains(uiSearchData.get(mapkey))) {
								flag = false;
								break;
							}
						}
					}
				}
				HashMap<String, Boolean> interimResut = new HashMap<String, Boolean>();
				interimResut.put("flag", flag);
				interimResut.put("flag3", flag3);
				interimResut.put("flag2", flag2);
				interimResut.put("flag4", flag4);
				interimResut.put("flag5", flag5);
				result.put(m, interimResut);
				m++;
			}
			boolean resutflag = true;
			if (result.size() < 1) {
				resutflag = false;
				try {
					click("closePDF");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			for (Entry<Integer, HashMap<String, Boolean>> entry : result.entrySet()) {
				for (Entry<String, Boolean> entry1 : entry.getValue().entrySet()) {
					if (entry1.getValue() == false) {
						resutflag = false;
						break;
					}
				}
				if (resutflag == false)
					break;
			}
			assertEquals(resutflag, true);
		}
	}
}
