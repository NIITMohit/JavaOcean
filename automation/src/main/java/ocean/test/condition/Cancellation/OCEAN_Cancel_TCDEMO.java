package ocean.test.condition.Cancellation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.fail;

import org.testng.annotations.Test;

import ocean.com.main.Suite;
import ocean.common.CommonFunctions;
import ocean.common.DataProviderClass;

public class OCEAN_Cancel_TCDEMO extends CommonFunctions {

	@Test(priority = 2, dataProvider = "clickSearchContract", dataProviderClass = DataProviderClass.class)
	public void cancelContract(String contractId, String contractStatus) throws Exception {
		click("clickCancellationTab");
		click("clickMailservice");
		click("clickClear");
		type("typeContractId", contractId);
		click("searchContractButton");
		String stateofbutton = checkEnableDisable("searchContractButton");
		click("swipeRight");
		String status = getValue("statusOfContract");
		takeScreenshot();
		click("swipeLeft");
		takeScreenshot();
		if (!status.toLowerCase().equals("processed") && stateofbutton.toLowerCase().equals("false"))
			assertEquals(stateofbutton, "false");
		else if (status.toLowerCase().equals("processed") && stateofbutton.toLowerCase().equals("true")) {
			click("searchContractButton");
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
			String stateofbutton1 = checkEnableDisable("searchContractButton");
			click("checkAuthorize");
			assertNotEquals(stateofbutton, stateofbutton1);
		} else {
			fail();
		}
	}

	@Test(priority = 1, dataProvider = "clickSearchContractonOverRide", dataProviderClass = DataProviderClass.class)
	public void cancelContractoverRideRulesCheckBox(String contractId, String Rules) throws InterruptedException {
		String cancelDate = "05-08-2019";
		String dateReceived = "08-08-2019";
		click("clickCancellationTab");
		click("clickMailservice");
		click("clickClear");
		type("typeCustomerFirstName", contractId);
		click("clickSearchContract");
		String stateofbutton = checkEnableDisable("searchContractButton");
		if (stateofbutton.toLowerCase().equals("true")) {
			click("searchContractButton");
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
				String info = getValue("inValidPayeeErrorMessage");
				takeScreenshot();
				click("clickOK");
				click("overRideRulesCheckBox");
				type("selectPayee", "Primary Account");
				String refundpercent = getValue("refundPercent");
				float refundpercents = 0;
				refundpercents = Float.valueOf(refundpercent).floatValue() - 6;
				int abc = (int) refundpercents;
				refundpercent = Integer.toString(abc);
				type("refundPercent", refundpercent);
				// type("cancelFee", "10");
				click("clickCalculate");
				click("clickOK");
				click("clickAuthorize");
				String stateofbutton1 = checkEnableDisable("searchContractButton");
				assertNotEquals(stateofbutton, stateofbutton1);
			} else {
				click("overRideRulesCheckBox");
				String refundPercent = getValue("refundPercent");
				float refundPercents = 0;
				refundPercents = Float.valueOf(refundPercent).floatValue() - 6;
				int abc = (int) refundPercents;
				refundPercent = Integer.toString(abc);
				type("refundPercent", refundPercent);
				type("cancelFee", "2000");
				click("clickCalculate");
				String messageFromToast = getValue("textMessageForFeeExceeds");
				takeScreenshot();
				click("clickOK");
				assertEquals(messageFromToast, "Cancelation fee exceeds refund");
			}
		} else {
			fail();
		}
	}

	@Test(priority = 3, dataProvider = "cancelContractCalculateVerify", dataProviderClass = DataProviderClass.class)
	public void cancelContractCalculationVerification(String contractId, String cancdd) {
		Boolean flag1 = true;
		String cancelDate = "05-08-2019";
		String dateReceived = "08-08-2019";
		click("clickCancellationTab");
		click("clickMailservice");
		click("clickClear");
		type("typeContractId", contractId);
		click("clickSearchContract");
		String stateofbutton = checkEnableDisable("searchContractButton");
		if (stateofbutton.toLowerCase().equals("true")) {
			click("searchContractButton");
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
				click("overRideRulesCheckBox");
				String refundPercent = getValue("refundPercent");
				String cancelFee = getValue("cancelFee");
				float refundPercents = 0;
				refundPercents = Float.valueOf(refundPercent).floatValue() - 6;
				int abc = (int) refundPercents;
				refundPercent = Integer.toString(abc);
				type("refundPercent", refundPercent);
				type("cancelFee", "96");
				takeScreenshot();
				click("clickCalculate");
				click("clickOK");
				click("clickCalculate");
				click("clickOK");
				String refundpercentCompare1 = getValue("refundPercent");
				String cancelFee1 = getValue("cancelFee");
				if (refundPercent.toLowerCase().equals(refundpercentCompare1.toLowerCase())
						&& cancelFee.toLowerCase().equals(cancelFee1.toLowerCase())) {
					flag = true;
				}
				assertEquals(flag, flag1);
			}

			else {
				Boolean flag = false;
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
