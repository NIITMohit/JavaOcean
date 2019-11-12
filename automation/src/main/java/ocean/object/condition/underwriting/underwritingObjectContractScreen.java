package ocean.object.condition.underwriting;

import ocean.common.CommonFunctions;

/**
 * This is object class for underwriting Module for contract screen
 * 
 * @author Mohit Goel
 */
public class underwritingObjectContractScreen extends CommonFunctions{
	/**
	 * This function is used to land to search contract with pending state
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
}
