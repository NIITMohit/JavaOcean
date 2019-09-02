package ocean.test.condition.Cancellation;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.com.main.Suite;
import ocean.common.DataProviderClass;
import ocean.test.condition.Cancellation.interfaces.iOCEAN_Cancel_TC01;

public class OCEAN_Cancel_TC01 extends Suite implements iOCEAN_Cancel_TC01 {

	@Override
	@Test(dataProvider = "SearchContract", dataProviderClass = DataProviderClass.class)
	public void cancelContract(String contractId, String contractStatus) {

		/*
		 * if(contractStatus.equalsIgnoreCase("on hold")) throw new
		 * SkipException("Skipping this exception"); else
		 * type("notePad",contractId+contractStatus);
		 */

		//// Click on search Tab 
		click("clickPricingTab");
		click("clickSearchTab");

		click("clickContracts");
		click("clickClear");

		//// Type contract
		type("typeContractinSearch", contractId);

		click("clickSearch");

		//// Check Status
		Boolean status = compareValue("getContractStatus", contractStatus);

		if (status) {
			
			//// Go To Contract Tab 
			
			click("clickCancellationTab");

			///// Cancel Contract
			
			type("typeContractId", contractId);
			click("searchContract");
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Rewrite");

			//// Get Cancel Mile
			String miles = getValue("getMiles");
			if (miles.length() < 1) {
				miles = "150662";
			}

			type("enterCancelMiles", miles);
			type("enterCancelDate", "08-08-2019");
			type("enterDateReceived", "07-07-2019");
			click("clickCalculate");
			click("clickOK");
			click("clickOK");
			click("clickAuthorize");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) { // TODO Auto-generated catch block //// do nothing
			}
		}

		else {
			throw new SkipException("Skipping this exception");
		}

		System.out.println("done");

	}

}
