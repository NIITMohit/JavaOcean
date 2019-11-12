package ocean.object.condition.underwriting;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import ocean.common.CommonFunctions;
import ocean.common.DataProviderClass;

/**
 * This is object class for underwriting Module for main screen
 * 
 * @author Mohit Goel
 */
public class underwritingObjectMainScreen extends CommonFunctions {

	/**
	 * This function is used to land to search contract with pending state
	 * 
	 * @return
	 * 
	 */
	public underwritingObjectContractScreen searchContractwithPendingState(String remittName, String fileName)
			throws Exception {
		//// Type RemittanceName
		type("typeToSearchRemittance", remittName);
		//// expand remittance to get contracts
		click("expandRemittance");
		//// type filename
		type("typeContract", fileName);
		//// click view contract

		return new underwritingObjectContractScreen();
	}

	/**
	 * This function is used to lock and view contract post contract search
	 * 
	 */
	public void lockAndViewContract() throws Exception {
		click("viewContract");
		try {
			//// click yes to lock remittance
			click("lockContractYesButton");
		} catch (Exception e) {
			// do nothing
		}
		//// click contract expander
		click("contractExpander");

	}

	/**
	 * This function is used to enter all mandatory values on new business contract
	 * form
	 * 
	 */
	public void enterMandatoryValuesOnContract() throws Exception {
		//// type unique contract number
		type("typeContractNumber", randomString(10));
		/// click search button to verify unique contract
		click("clickSearchButtonToSearchContract");
		//// enter purchase date of contract, -10 days from today's date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.now().minusDays(10);
		type("purchaseDateForNewContract", dtf.format(localDate).toString());

	}
}
