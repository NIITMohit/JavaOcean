package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_TC_89 class automates new PBI 18045, which holds Test
 * Cases; Test Condition Description : Vaidate sepchk Expander
 * 
 * @author Shalu Chauhan
 */

public class OCEAN_Underwriting_TC_89 extends UnderwritingModulePages {

	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Validate the creation of remittance in ocean
	 * 
	 */
	public String createRemittance(String[] inputData) throws Exception {
		copyFilesWorkingRemittance();
		String[] inputArray = new String[] { inputData[0], inputData[1], inputData[2], inputData[3], inputData[4],
				inputData[5], inputData[6], inputData[7], inputData[8], inputData[9], inputData[10], inputData[11] };
		//// go to underwriting tab
		goToUnderWritingTab();
		goToRemittanceList();
		//// navigate to create remittance tab
		landToCreateRemittanceDetailsPage();
		//// drag and drop files
		click("clickRemittanceReset");
		dragAndDropFiles();
		//// verify drag and drop status for pdf
		String remittanceName = enterRemittanceValues(inputArray);
		if (inputArray[4] == "" || inputArray[5] == "" || inputArray[0] == "" || inputArray[1] == ""
				|| inputArray[2] == "") {
			if (remittanceName.equals("")) {
				assertEquals(true, true);
			} else {
				assertEquals(true, false);
			}
		} else {
			HashMap<String, String> dbValues = getRemitCreationdata(remittanceName);
			HashMap<String, String> excelValues = excelValue(inputArray, remittanceName);
			dbValues.put("Paper", excelValues.get("Paper"));
			String aa = dbValues.get("checkamount");
			if (aa.length() > 1) {
				String s = aa.substring(0, aa.indexOf("."));
				dbValues.put("checkamount", s);
			} else
				dbValues.put("checkamount", aa);
			for (Map.Entry<String, String> entry : dbValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					dbValues.put(key, "");
			}
			for (Map.Entry<String, String> entry : excelValues.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (val == null || val.contains("-select-"))
					excelValues.put(key, "");
			}
			if (excelValues.equals(dbValues)) {
				assertEquals(true, true);
			} else {
				assertEquals(false, true);
			}
		}

		return remittanceName;
	}

	public HashMap<String, String> excelValue(String[] inputArray, String remit) {
		HashMap<String, String> excel = new HashMap<String, String>();
		for (int i = 0; i < inputArray.length; i++) {
			switch (i) {
			case 0:
				excel.put("RemittanceName", remit);
				break;
			case 1:
				excel.put("corecount", inputArray[i]);
				break;
			case 2:
				excel.put("lwacount", inputArray[i]);
				break;
			case 3:
				excel.put("Source_Type", inputArray[i]);
				break;
			case 4:
				excel.put("name", inputArray[i]);
				break;
			case 5:
				excel.put("Paper", inputArray[i]);
				break;
			case 6:
				excel.put("Subtype_Name", inputArray[i]);
				break;
			case 7:
				excel.put("comments", inputArray[i]);
				break;
			case 8:
				excel.put("checknumber", inputArray[i]);
				break;
			case 9:
				excel.put("checkamount", inputArray[i]);
				break;
			default:
				break;
			}
		}
		return excel;
	}

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Validate remittance posting.
	 * 
	 */
	@SuppressWarnings("unused")
	@Test(priority = 1, groups = { "smoke",
			"fullSuite" }, dataProvider = "fetchDataForTC89", dataProviderClass = UnderwritingDataProvider.class, description = "Separate Check with DB/CR - Expander")
	public void addSepcheckExpanderOnAccountStatementScreen(String[] inputDataa) throws Exception {
		String remittanceName = createRemittance(inputDataa);
		String[] inputData = new String[] { inputDataa[12], inputDataa[13], inputDataa[14], inputDataa[15],
				inputDataa[16], inputDataa[17], inputDataa[18], inputDataa[19], inputDataa[20], inputDataa[21],
				inputDataa[22], inputDataa[23], inputDataa[24], inputDataa[25], inputDataa[26], inputDataa[27],
				inputDataa[28], inputDataa[29], inputDataa[30], inputDataa[31] };
		if (remittanceName.length() > 0) {
			refreshRemittance();
			waitForSomeTime(10);
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			//// Refresh remittance
			// refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			refreshRemittance();
			searchContractwithPendingState(remittanceName, remitt.get(1).get("FILE_NAME"));
			// lockAndViewContract();
			addCheck();
			///// Prepare Data
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
				click("loadRemittance");
				// type("typeContract", remitt.get(1).get("FILE_NAME"));
				// searchContractwithPendingState(remitt.get(1).get("RemittanceName"),
				// remitt.get(1).get("FILE_NAME"));
				lockAndViewContract();
				//// enter all mandatory values only on new business form screen
				premiumData.putAll(enterMandatoryValuesOnContract(premiumData));
				//// Select Surcharges options, deductibles
				try {
					click("scrollContractsListDown");
				} catch (Exception e) {
					/// do nothing
				}
				if (premiumData.get("SURCHARGES").toLowerCase().equals("y"))
					premiumData.put("SURCHARGESAMOUNT", surcharges());
				if (premiumData.get("OPTIONS").toLowerCase().equals("y"))
					premiumData.put("OPTIONSAMOUNT", options());
				if (premiumData.get("DEDUCTIBLE").toLowerCase().equals("y"))
					premiumData.put("DEDUCTIBLEAMOUNT", deductibles());
				//// select check, underwrite contract, post remittance
				String premium = premium().replaceAll(",", "");
				String aulPrice = getAttributeValue("getPremium", "Name");
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equals(finalValue)) {
					enterCustomerPaidAndDealerPaid("1000", inputDataa[9]);
					String premium1 = premium().replaceAll(",", "");
					String sepChkAmnt = getAttributeValue("getCreditDebitAmount", "Name");
					selectCheckAndScrollToTop();
					String contractNumber = getAttributeValue("typecontractnumber", "Text");
					//// click under
					click("clickUnderW");
					waitForSomeTime(20);
					//// click ok
					removeErrorMessages();
					//// create data to fill required values in search window
					HashMap<String, String> uiSearchData = new HashMap<String, String>();
					uiSearchData.put("AdjustmentType", inputDataa[32]);
					uiSearchData.put("AdjustmentCategory", inputDataa[33]);
					uiSearchData.put("Role_Type", inputDataa[10]);
					uiSearchData.put("RoleId", inputDataa[11]);
					uiSearchData.put("Status", inputDataa[35]);
					uiSearchData.put("ContractId", contractNumber);
					uiSearchData.put("CheckNumber", inputDataa[34]);
					uiSearchData.put("CheckAmount", "-" + sepChkAmnt);
					goToChecksTab();
					enterValuesForAdditionalPayments(uiSearchData);
					HashMap<String, String> roleIdDbMap = addAccountDetailsOnCreditAndRefunds(uiSearchData);
					addContractDetailsOnAdditionalPayments(uiSearchData, contractNumber);
					uploadPdf();
					addDetailsOnADP();
					HashMap<String, String> dbData = getSepChkAddedDataForContractFromDB(roleIdDbMap.get("ContractId"),
							inputDataa[10]);
					click("gotoNewBusinessTab");
					enterCustomerPaidAndDealerPaid("1000", aulPrice);
					click("contractExpander");
					//// post remittance and verify
					postRemittance();
					//// code to be added once underwrite code issue is fixed
					//// verify post status
					assertEquals(postedRemittanceStatus(remittanceName).toLowerCase(), "posted");
					goToStatementsTab();
					typeRoletypeAndRoleIdUnderStatementsTab(uiSearchData.get("Role_Type"), roleIdDbMap.get("RoleId"));
					click("clickOnCalendarIconOnStatementsTab");
					waitForSomeTime(10);
					// get month from trans date
					String transDateMonth = getMonthFromDate(dbData.get("Trans_Date"));
					// click on desired month
					clickOnCalenderMonth(transDateMonth);
					clickOnViewButtonUnderStatementsTab();
					clickSepChkUnderStatementsTab();
					waitForSomeTime(10);
					HashMap<String, String> uiData = getSepChkExpanderDetailsFromUI(uiSearchData);
					assertTrue(uiData.equals(dbData), "Data not Matched for sepcheck");

				} else {
					throw new Exception("Premium Not matched");
				}
			} else {
				new SkipException("no actual value exist for combination feeded in excel as test data");
			}
		} else {
			throw new Exception("Remittace creation failed");
		}
	}

}
