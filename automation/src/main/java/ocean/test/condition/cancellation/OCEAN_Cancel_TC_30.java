package ocean.test.condition.cancellation;

import ocean.modules.pages.CancellationModulePages;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
/**
  Validate that OCEAN apply same cancellation rules on multiple cancellation request 
  with different plan codes, when cancellation rules are defined for an account.
  @author Poonam.Kalra
 **/
public class OCEAN_Cancel_TC_30 extends CancellationModulePages {
  
 @SuppressWarnings("unused")
@Test(priority = 1,groups ="smoke1",description ="Validate that OCEAN apply same cancellation rules on multiple cancellation request with different plan codes")
 public void verifyCancelRuleForDifferentPlanCode() throws Exception {
     boolean dbFlag = false;
     HashMap<String,String> dBsearch  = cancellation_getAccountRoleIdWithAccountRuleBuilder("Dealer",1);
     if(dBsearch.size()>0 ){
     String primaryRoleId = dBsearch.get("roleIdentifier");
     String secRoleId = dBsearch.get("secRoleId");
     String secRoleName = dBsearch.get("secName");
     String primaryRoleName= dBsearch.get("name");
     String effDate = dBsearch.get("effectiveDate");
     String[] effDate1 = effDate.split("/s");
     effDate = effDate1[0];
     HashMap<Integer,HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
     if(proCode.size()>1) {
     String Pcode  = proCode.get(1).get("PROGRAM_CODE");	
     String Pcode1 = proCode.get(2).get("PROGRAM_CODE");
	  HashMap<String, String> contractList = cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,Pcode,secRoleId);
	  HashMap<Integer,HashMap<String, String>>dbruleInfo =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
	  System.out.print(dbruleInfo);
	  if (contractList.get("CERT").length() > 0) {
		//// Navigate to Mail service tab
		goToCancellationTab();
		goToMailServiceTab();
		//// create search data in hash map
		HashMap<String, String> uiSearchData = new HashMap<String, String>();
		uiSearchData.put("CERT", contractList.get("CERT"));
		//// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData);
		takeScreenshot();
		//// navigate to new cancel tab
		clickCancelButtonAndNavigateToNewCancellationTab();
		//// enter valid values on new cancellation tab screen and click calculate
		String initiatedBy = "Dealer";
		String cancelReason = "Customer Request";
		enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
				 convertDateCancellation(contractList.get("SALE_DATE"), 75), "");
		// click ok for cancellation completed successfully
		click("clickOK");
		click("standardCalculation");
		click("clickOK");
		click("standOKbtn");
		//// get cancel method value applied as flat or outside flat in hashmap
		waitForSomeTime(10);
		takeScreenshot();
		String pCode = contractList.get("PROGRAM_CODE");
		try{
			click("listtoggleStateRuleInfoView");
	       }
		catch(Exception e) {click("ruleInfoRefersh");}
		HashMap<Integer,HashMap<String, String>> ruleInfoViewValue = getRuleInfoValue();
		logger.info("ruleInfoViewValue===="+ruleInfoViewValue);
		logger.info("DBInfoViewValue===="+dbruleInfo);
		boolean compareResult = compareValues(ruleInfoViewValue,dbruleInfo);
		  assertEquals(compareResult,true);
		   } 
	   else {
			new SkipException("no value exist in db for Given PriceSheet");
			 assertEquals(dbFlag,true);
		  }
	  HashMap<String, String> contractList1 = cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,Pcode1,secRoleId);
	  if (contractList1.get("CERT").length() > 0) {
		//// Navigate to Mail service tab
		goToMailServiceTab();
		waitForSomeTime(20);
		//// create search data in hash map
		HashMap<String, String> uiSearchData1 = new HashMap<String, String>();
		uiSearchData1.put("CERT", contractList1.get("CERT"));
		//// Search Data based on contract Id
		searchContractGivenInputParamaters(uiSearchData1);
		takeScreenshot();
		//// navigate to new cancel tab
		clickCancelButtonAndNavigateToNewCancellationTab();
		//// enter valid values on new cancellation tab screen and click calculate
		String initiatedBy = "Dealer";
		String cancelReason = "Customer Request";
		waitForSomeTime(10);
		enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
				 convertDateCancellation(contractList1.get("SALE_DATE"),70), "");
		// click ok for cancellation completed successfully
		click("clickOK");
		//// get cancel method value applied as flat or outside flat in hashmap
		waitForSomeTime(15);
		takeScreenshot();
		//HashMap<Integer,HashMap<String, String>>dbruleInfo =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
		try{
			click("listtoggleStateRuleInfoView");
	       }
		catch(Exception e) {click("ruleInfoRefersh");}
		takeScreenshot();
		HashMap<Integer,HashMap<String, String>> ruleInfoViewValue  = getRuleInfoValue();
		logger.info("ruleInfoViewValue1===="+ruleInfoViewValue);
		logger.info("DBInfoViewValue1===="+dbruleInfo);
		boolean compareResult = compareValues(ruleInfoViewValue,dbruleInfo);
		  assertEquals(compareResult,true);
		  } 
	  else {
		new SkipException("no value exist in db for Given PriceSheet");
		 assertEquals(dbFlag,true);
	     }	
	    }
     else {
			new SkipException("Only One ProgCode So Can't Test ");
			 assertEquals(dbFlag,true);
		   }	
      }
     else {
   	  logger.info("NO ACCOUNT RULES FOUND ++ PLS. CREATE ACCOUNT LEVEL RULES");
			 assertEquals(dbFlag, false);
     }
    }
 
    }

