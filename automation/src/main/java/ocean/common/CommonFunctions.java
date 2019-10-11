package ocean.common;

import ocean.com.main.Suite;

public class CommonFunctions extends Suite {

	public void searchContract(String contractId) {
		click("clickCancellationTab");
		click("clickMailservice");
		click("clickClear");
		type("typeContractId", contractId);
		click("searchContractButton");
		String stateofbutton = checkEnableDisable("searchContractButton");
	}
}
