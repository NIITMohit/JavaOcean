package ocean.test.condition.underwriting;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.UnderwritingDataProvider;
import ocean.modules.pages.UnderwritingModulePages;

/**
 * OCEAN_UnderWriting_PBI_9227 class automates new PBI 9227, which holds 12 Test
 * Case; Test Condition Description : Active - AllTrans
 * 
 * @author Mohit Goel
 */
public class OCEAN_Underwriting_TC_79_E2E extends UnderwritingModulePages {
	/**
	 * This function automates all test cases for test condition 01, 02, 03, 04;
	 * Test Case description : Active - AllTrans
	 * 
	 */
	public String createRemittance(String[] inputData) throws Exception {
		copyFilesWorkingRemittance();
		String[] inputArray = new String[] { inputData[20], inputData[21], inputData[22], inputData[23], inputData[24],
				inputData[25], inputData[26], inputData[27], inputData[28], inputData[29], inputData[30],
				inputData[31] };
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
	 * Case description : Active - AllTrans
	 * 
	 */
	@Test(priority = 1, groups = { "regression",
			"fullSuite" }, dataProvider = "fetchDataForE2E", dataProviderClass = UnderwritingDataProvider.class, description = "Active - AllTrans")
	public void validateE2ERemittancePostingWithPrimaryAsDealer(String[] inputData) throws Exception {
		if (inputData[28].length() < 1 || inputData[28] == null) {
			inputData[28] = "111";

		}
		if (inputData[29].length() < 1 || inputData[29] == null) {
			inputData[29] = "1132";

		}
		if (inputData[30].length() < 1 || inputData[30] == null) {
			inputData[30] = "Dealer";

		}
		if (inputData[31].length() < 1 || inputData[31] == null) {
			inputData[31] = "*";

		}
		String remittanceName = createRemittance(inputData);

		if (remittanceName.length() > 0) {
			refreshRemittance();
			searchRemittance(remittanceName);
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(1);
			refreshRemittance();
			searchRemittance(remittanceName);
			///// Update check status
			addCheck();
			//// Refresh remittance
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			///// Prepare Data
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
				refreshRemittance();
				searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
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
				String premium = premium();
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equals(finalValue)) {
					enterCustomerPaidAndDealerPaid(inputData[29], inputData[29]);
					selectCheckAndScrollToTop();
					//// click under
					click("clickUnderW");
					removeErrorMessages();
					///// validate contract for underwrite status in db
					String status = statusidforCOntract(remittanceName);
					assertEquals(status.equalsIgnoreCase("1"), true);
					click("contractExpander");
					//// post remittance and verify
					postRemittance();
					//// after post remittance verify active status for contract
					//// post remittance status
					status = statusidforCOntract(remittanceName);
					assertEquals(status.equalsIgnoreCase("5"), true);
					assertEquals(postedRemittanceStatus(remittanceName).toLowerCase(), "posted");
				} else
					throw new Exception("Premium Not matched");

			} else
				new SkipException("no actual value exist for combination feeded in excel as test data");

		} else
			throw new Exception("Remittace creation failed");

	}

	/**
	 * This function automates test Condition 05 to 14 and 18 respectively; Test
	 * Case description : Active - AllTrans
	 * 
	 */
	@Test(priority = 1, groups = "regression", dataProvider = "fetchDataForE2E", dataProviderClass = UnderwritingDataProvider.class, description = "Active - AllTrans")
	public void validateE2ERemittancePostingWithPrimaryAsDealerWithExternalRemittance(String[] inputData)
			throws Exception {
		if (inputData[28].length() < 1 || inputData[28] == null) {
			inputData[28] = "111";

		}
		if (inputData[29].length() < 1 || inputData[29] == null) {
			inputData[29] = "1132";

		}
		if (inputData[30].length() < 1 || inputData[30] == null) {
			inputData[30] = "Dealer";

		}
		if (inputData[31].length() < 1 || inputData[31] == null) {
			inputData[31] = "*";

		}
		String remittanceName = createRemittance(inputData);
		if (remittanceName.length() > 0) {
			refreshRemittance();
			try {
				searchRemittance(remittanceName);
			} catch (Exception e) {
				waitForSomeTime(30);
				searchRemittance(remittanceName);
			}
			//// Assign Status of documents and save remittance
			assignDocumentsStatus(0);
			///// code to add external remittance
			//// MohittestFinal
			addExternalRemittance();
			click("expandRemittance");
			addCheck();
			//// Refresh remittance
			refreshRemittance();
			HashMap<Integer, HashMap<String, String>> remitt = pendingContractsFromRemittanceName(remittanceName);
			///// Prepare Data
			HashMap<String, String> premiumData = prepareData(inputData);
			//// run query to get final data
			HashMap<String, String> sss = setAllDataForPremiumCalculation(premiumData);
			premiumData.putAll(sss);
			premiumData.put("PrimaryAccount", "Dealer");
			premiumData.put("SecondaryAccount", "Lender");
			premiumData.put("SecondaryAccountId", "24");
			if (sss.size() > 1) {
				try {
					searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
				} catch (Exception e) {
					waitForSomeTime(30);
					searchContractwithPendingState(remitt.get(1).get("RemittanceName"), remitt.get(1).get("FILE_NAME"));
				}

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
				String premium = premium();
				String finalValue = calculateMyPremium(premiumData);
				if (premium.equals(finalValue)) {
					enterCustomerPaidAndDealerPaid(inputData[29], inputData[29]);
					selectCheckAndScrollToTop();
					//// click under
					click("clickUnderW");
					waitForSomeTime(30);
					//// click ok
					try {
						click("clickOK");
					} catch (Exception e) {
						// TODO: handle exception
						try {
							waitForSomeTime(30);
							click("clickOK");
						} catch (Exception ex) {
							// TODO: handle exception
						}
					}
					///// validate contract for underwrite status in db
					String status = statusidforCOntract(remittanceName);
					assertEquals(status.equalsIgnoreCase("1"), true);
					click("contractExpander");
					//// post remittance and verify
					postRemittance();
					//// after post remittance verify active status for contract
					//// post remittance status
					status = statusidforCOntract(remittanceName);
					assertEquals(status.equalsIgnoreCase("5"), true);
					assertEquals(postedRemittanceStatus(remittanceName).toLowerCase(), "posted");
				} else
					throw new Exception("Premium Not matched");

			} else
				new SkipException("no actual value exist for combination feeded in excel as test data");

		} else
			throw new Exception("Remittace creation failed");

	}

}
