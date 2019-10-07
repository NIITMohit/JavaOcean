package ocean.test.condition.Cancellation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.fail;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.com.main.Suite;
import ocean.common.DataProviderClass;

public class OCEAN_Cancel_TC02 extends Suite {

	@Test(dataProvider = "SearchContract", dataProviderClass = DataProviderClass.class)
	public void cancelContract(String contractId, String contractStatus) throws Exception {
		click("clickCancellationTab");
		click("mailservice");
		click("clearContract");
		type("typeContractId", contractId);
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		click("swiperight");
		String status = getValue("statusofcontract");
		takeScreenshot();
		click("swipeleft");
		takeScreenshot();
		if (!status.toLowerCase().equals("processed") && stateofbutton.toLowerCase().equals("false"))
			assertEquals(stateofbutton, "false");
		else if (status.toLowerCase().equals("processed") && stateofbutton.toLowerCase().equals("true")) {
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Repossession");
			String miles = getValue("getMiles");
			int milee = 0;
			milee = Integer.parseInt(miles) + 2214;
			miles = Integer.toString(milee);
			type("enterCancelMiles", miles);
			type("enterCancelDate", "05-08-2019");
			type("enterDateReceived", "08-08-2019");
			click("clickCalculate");
			click("clickOK");
			type("selectPayee", "AUL");
			click("clickAuthorize");
			String stateofbutton1 = checkEnableDisable("clickCancelButton");
			click("checkAuthorize");
			assertNotEquals(stateofbutton, stateofbutton1);
		} else {
			fail();
		}
	}

	@Test(dataProvider = "SearchContractonOverRide", dataProviderClass = DataProviderClass.class)
	public void cancelContractOverRideRules(String contractId, String Rules) {
		String cancelDate = "05-08-2019";
		String dateReceived = "08-08-2019";
		click("clickCancellationTab");
		click("mailservice");
		click("clearContract");
		type("typeCostomerFirstName", contractId);
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		if (stateofbutton.toLowerCase().equals("true")) {
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Repossession");
			//// Get Cancel Mile
			String miles = getValue("getMiles");
			int milee = 0;
			milee = Integer.parseInt(miles) + 2214;
			miles = Integer.toString(milee);
			type("enterCancelMiles", miles);
			type("enterCancelDate", cancelDate);
			type("enterDateReceived", dateReceived);
			click("clickCalculate");
			click("clickOK");
			if (Rules.toLowerCase().equals("positive")) {
				type("selectPayee", "BLANK");
				click("clickAuthorize");
				// click("yesBox");
				String info = getValue("inValidPayee");
				takeScreenshot();
				click("clickOK");
				click("overRideRules");
				type("selectPayee", "Default");
				String refundpercent = getValue("refundpercent");
				float refundpercents = 0;
				refundpercents = Float.valueOf(refundpercent).floatValue() - 6;
				int abc = (int) refundpercents;
				refundpercent = Integer.toString(abc);
				type("refundpercent", refundpercent);
				// type("cancelFee", "10");
				click("clickCalculate");
				click("clickOK");
				click("clickAuthorize");
				String stateofbutton1 = checkEnableDisable("clickCancelButton");
				assertNotEquals(stateofbutton, stateofbutton1);
			} else {
				click("overRideRules");
				String refundpercent = getValue("refundpercent");
				float refundpercents = 0;
				refundpercents = Float.valueOf(refundpercent).floatValue() - 6;
				int abc = (int) refundpercents;
				refundpercent = Integer.toString(abc);
				type("refundpercent", refundpercent);
				type("cancelFee", "2000");
				click("clickCalculate");
				String messageFromToast = getValue("textMessage");
				takeScreenshot();
				click("clickOK");
				assertEquals(messageFromToast, "Cancelation fee exceeds refund");
			}
		} else {
			fail();
		}
	}

	@Test(dataProvider = "cancelContractCalculateVerify", dataProviderClass = DataProviderClass.class)
	public void cancelContractCalculationVerification(String contractId, String cancdd) {
		Boolean flag1 = true;
		String cancelDate = "05-08-2019";
		String dateReceived = "08-08-2019";
		click("clickCancellationTab");
		click("mailservice");
		click("clearContract");
		type("typeContractId", contractId);
		click("searchContract");
		String stateofbutton = checkEnableDisable("clickCancelButton");
		if (stateofbutton.toLowerCase().equals("true")) {
			click("clickCancelButton");
			type("selectInitiatedBy", "Dealer");
			type("selectCancelReason", "Customer Request");
			//// Get Cancel Mile
			String miles = getValue("getMiles");
			int milee = 0;
			milee = Integer.parseInt(miles) + 2214;
			miles = Integer.toString(milee);
			type("enterCancelMiles", miles);
			type("enterCancelDate", cancelDate);
			type("enterDateReceived", dateReceived);
			click("clickCalculate");
			click("clickOK");
			if (cancdd.toLowerCase().equalsIgnoreCase("cancel")) {
				Boolean flag = false;
				click("overRideRules");
				String refundpercent = getValue("refundpercent");
				String cancelFee = getValue("cancelFee");
				float refundpercents = 0;
				refundpercents = Float.valueOf(refundpercent).floatValue() - 6;
				int abc = (int) refundpercents;
				refundpercent = Integer.toString(abc);
				type("refundpercent", refundpercent);
				type("cancelFee", "96");
				takeScreenshot();
				click("clickCalculate");
				click("clickOK");
				click("clickCalculate");
				click("clickOK");
				String refundpercentCompare1 = getValue("refundpercent");
				String cancelFee1 = getValue("cancelFee");
				if (refundpercent.toLowerCase().equals(refundpercentCompare1.toLowerCase())
						&& cancelFee.toLowerCase().equals(cancelFee1.toLowerCase())) {
					flag = true;
				}
				assertEquals(flag, flag1);
			}

			else {
				Boolean flag = false;
				click("overRideRules");
				type("cancelFee", "85");
				type("selectPayee", "AUL");
				takeScreenshot();
				click("clickCalculate");
				click("clickOK");
				assertEquals(flag, flag1);
			}

		} else {
			fail();
		}
		System.out.println("done");
	}

}
