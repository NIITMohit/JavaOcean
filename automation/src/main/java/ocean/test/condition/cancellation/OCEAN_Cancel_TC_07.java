package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_07 class automates Ocean Cancel module Test Condition 07,
 * which holds 4 Test Case; Test Condition Description : Validate date range
 * check for cancel date with contract coverage dates.
 * 
 * @author Mohit Goel
 */
public class OCEAN_Cancel_TC_07 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition 06; Test Case
	 * description : Validate that Ocean block user to authorize a cancellation,
	 * when user provide cancel date before sale date.
	 * 
	 */
	@Test(priority = 5, groups = "sanity", description = "Validate that Ocean block user to authorize a cancellation, when user provide cancel date before sale date.")
	public void validateMessageWhenCancelDateIsBeforeSaleDate() throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
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
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "",
					convertDate(contractList.get("SALE_DATE"), -10), "");
			//// validate validation message for cancel date
			String message = getValidationForCancelDateLessSaleDate();
			takeScreenshot();
			click("clickOK");
			String actualMessage = "Error: Time or mileage less than zero";
			assertEquals(message.toLowerCase(), actualMessage.toLowerCase());
		} else {
			new SkipException("no value exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 06; Test Case
	 * description : Validate that Ocean block user to authorize a cancellation,
	 * when user provide cancel date greater than today's date.
	 * 
	 */
	@Test(priority = 5, groups = "sanity", description = "Validate that Ocean block user to authorize a cancellation, when user provide cancel date greater than today's date.")
	public void validateMessageWhenCancelDateIsFutureDate() throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
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
			//// get future date
			Format sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			// Add 7 days to current date
			cal.add(Calendar.DAY_OF_MONTH, 7);
			// Date after adding the days to the current date
			String cancelDate = sdf.format(cal.getTime());
			//// enter valid values on new cancellation tab screen and click calculate
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "", cancelDate, "");
			//// validate validation message for cancel date
			String message = getValidationForCancelFutureDate();
			takeScreenshot();
			click("clickCancelButton");
			String actualMessage = "Cancel date is future date, do you want to continue?.";
			assertEquals(message.toLowerCase(), actualMessage.toLowerCase());
		} else {
			new SkipException("no value exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 06; Test Case
	 * description :Validate that Ocean block user to authorize a cancellation, when
	 * user provide cancel miles less than sale mileage.
	 * 
	 */
	@Test(priority = 5, groups = "sanity", description = "Validate that Ocean block user to authorize a cancellation, when user provide cancel miles less than sale mileage. ")
	public void validateMessageWhenCancelMilesLessThanSalesMiles() throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
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
			//// get future date
			String miles = getSalesMiles();
			int milee = 0;
			milee = Integer.parseInt(miles) - 10;
			String cancelMiles = Integer.toString(milee);
			//// enter valid values on new cancellation tab screen and click calculate
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", cancelMiles, "", "");
			//// validate validation message for cancel date
			String message = getValidationForCancelDateLessSaleDate();
			takeScreenshot();
			click("clickOK");
			String actualMessage = "Error: Time or mileage less than zero";
			assertEquals(message.toLowerCase(), actualMessage.toLowerCase());
		} else {
			new SkipException("no value exist in db");
		}
	}

	/**
	 * This function automates test case for test condition 06; Test Case
	 * description : Validate that Ocean block user to authorize a cancellation,
	 * when user provide date received greater than today's date.
	 * 
	 */
	@Test(priority = 5, groups = "sanity", description = "Validate that Ocean block user to authorize a cancellation, when user provide date received greater than today's date.")
	public void validateMessageWhenReceivedDateIsFutureDate() throws Exception {
		//// Search Contract from db where status is processed and price sheet is SNE
		HashMap<String, String> contractList = new HashMap<String, String>();
		contractList = cancellation_getContractIdBasedOnStatusAndPriceSheet("processed", "SNE");
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
			//// get future date
			Format sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar cal = Calendar.getInstance();
			// Add 7 days to current date
			cal.add(Calendar.DAY_OF_MONTH, 7);
			// Date after adding the days to the current date
			String cancelDate = sdf.format(cal.getTime());
			//// enter valid values on new cancellation tab screen and click calculate
			enterValuesOnNewCancellationTabAndClickCalculate("Dealer", "Customer Request", "", "", cancelDate);
			//// validate validation message for cancel date
			String message = getValidationForReceivedFutureDate();
			takeScreenshot();
			click("clickCancelButton");
			String actualMessage = "Received date is future date, do you want to continue?.";
			assertEquals(message.toLowerCase(), actualMessage.toLowerCase());
		} else {
			new SkipException("no value exist in db");
		}
	}

}
