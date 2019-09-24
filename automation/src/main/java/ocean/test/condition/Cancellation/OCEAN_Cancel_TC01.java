package ocean.test.condition.Cancellation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.fail;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.com.main.Suite;
import ocean.common.DataProviderClass;
import ocean.test.condition.Cancellation.interfaces.iOCEAN_Cancel_TC01;

public class OCEAN_Cancel_TC01 extends Suite implements iOCEAN_Cancel_TC01 {

	@Override
	@Test(dataProvider = "SearchContract", dataProviderClass = DataProviderClass.class)
	public void cancelContract(String contractId, String contractStatus) throws Exception {
		click("clickCancellationTab");
		click("mailservice");
		type("typeContractId", contractId);
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		click("swiperight");
		String status = getValue("statusofcontract");
		click("swipeleft");
		if (!status.toLowerCase().equals("processed") && stateofbutton.toLowerCase().equals("false"))
			assertEquals(stateofbutton, "false");
		else if (status.toLowerCase().equals("processed") && stateofbutton.toLowerCase().equals("true")) {
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Repossession");
			String miles = getValue("getMiles");
			type("enterCancelMiles", miles);
			type("enterCancelDate", "08-08-2019");
			type("enterDateReceived", "08-08-2019");
			click("clickCalculate");
			click("clickOK");
			type("selectPayee", "AUL");
			click("clickAuthorize");
			String stateofbutton1 = checkEnableDisable("clickCancelButton");
			assertNotEquals(stateofbutton, stateofbutton1);
		} else {
			fail();
		}
		System.out.println("done");
	}

	@Test(dataProvider = "SearchContractonBR", dataProviderClass = DataProviderClass.class)
	public void cancelContractBusinessRules_NonCancel(String contractId, String message) {
		String cancelDate = "08-08-2019";
		String dateReceived = "08-08-2019";
		click("clickCancellationTab");
		click("mailservice");
		type("typeContractId", contractId);
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		if (stateofbutton.toLowerCase().equals("true")) {
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Repossession");
			//// Get Cancel Mile

			String miles = getValue("getMiles");
			int milee = 0;
			if (message.toLowerCase().equalsIgnoreCase("Error: Time or mileage less than zero case")) {
				milee = Integer.parseInt(miles) - 100;
				miles = Integer.toString(milee);
			} else if (message.toLowerCase().equalsIgnoreCase("Error: Time or mileage less than zero")) {
				milee = Integer.parseInt(miles) + 100;
				miles = Integer.toString(milee);
				cancelDate = "08-08-2017";
			}
			type("enterCancelMiles", miles);
			type("enterCancelDate", cancelDate);
			type("enterDateReceived", dateReceived);
			click("clickCalculate");
			String messageFromToast = getValue("textMessage");
			click("clickOK");
			boolean abc = message.toLowerCase().contains(messageFromToast.toLowerCase());
			assertEquals(abc, true);
		} else {
			fail();
		}
		System.out.println("done");

	}
	

	//@Test
	public void cancelContractName() throws Exception {
		click("clickCancellationTab");
		click("mailservice");
		type("typeContractId", "");
		type("typeCostomerFirstName", "JUUPNDXR");
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		click("clickCancelButton");
		type("selectInitiatedBy", "Dealer");
		type("selectCancelReason", "Repossession");
		String miles = getValue("getMiles");
		int milee = 0;
		milee = Integer.parseInt(miles) + 100;
		miles = Integer.toString(milee);
		type("enterCancelMiles", miles);
		type("enterCancelDate", "09-09-2019");
		type("enterDateReceived", "09-09-2019");
		click("clickCalculate");
		click("clickOK");
		type("selectPayee", "AUL");
		click("clickAuthorize");
		String stateofbutton1 = checkEnableDisable("clickCancelButton");
		assertNotEquals(stateofbutton, stateofbutton1);
		System.out.println("done");
	}

	@Test(dataProvider = "SearchContractonOverRide", dataProviderClass = DataProviderClass.class)
	public void cancelContractOverRideRules(String contractId, String Rules) {
		String cancelDate = "08-08-2019";
		String dateReceived = "08-08-2019";
		click("clickCancellationTab");
		click("mailservice");
		type("typeContractId", contractId);
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		if (stateofbutton.toLowerCase().equals("true")) {
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Repossession");
			//// Get Cancel Mile
			String miles = getValue("getMiles");
			int milee = 0;
			milee = Integer.parseInt(miles) + 100;
			miles = Integer.toString(milee);
			type("enterCancelMiles", miles);
			type("enterCancelDate", cancelDate);
			type("enterDateReceived", dateReceived);
			click("clickCalculate");
			click("clickOK");
			click("overRideRules");
			if (Rules.toLowerCase().equals("positive")) {
				type("refundpercent", "10");
				type("cancelFee", "10");
				click("clickCalculate");
				click("clickOK");
				click("clickAuthorize");
				String stateofbutton1 = checkEnableDisable("clickCancelButton");
				assertNotEquals(stateofbutton, stateofbutton1);
			} else {
				type("refundpercent", "10");
				type("cancelFee", "1000");
				click("clickCalculate");
				String messageFromToast = getValue("textMessage");
				click("clickOK");
				assertEquals(messageFromToast, "Cancelation fee exceeds refund");
			}

		} else {
			fail();
		}
		System.out.println("done");

	}

}
