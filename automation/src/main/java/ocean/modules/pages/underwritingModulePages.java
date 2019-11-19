package ocean.modules.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ocean.common.CommonFunctions;

/**
 * This is object class which contains all pages of underwriting modules
 * 
 * @author Mohit Goel
 */
public class underwritingModulePages extends CommonFunctions {

	/**
	 * This function is used to land to search contract with pending state
	 * 
	 * @return
	 * 
	 */
	public void searchContractwithPendingState(String remittName, String fileName) throws Exception {
		//// Type RemittanceName
		type("typeToSearchRemittance", remittName);
		//// expand remittance to get contracts
		click("expandRemittance");
		//// type filename
		type("typeContract", fileName);
		//// click view contract
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
		waitForSomeTime(2);
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
