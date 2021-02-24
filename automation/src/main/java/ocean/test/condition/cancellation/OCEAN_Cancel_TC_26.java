package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;
import java.util.HashMap;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_26 class automates Ocean Cancel module Test Condition 26 ,
 * which holds 1 Test Case; Test Condition Description :Validate that OCEAN
 * apply primary account price sheet cancellation rules on a cancellation, when
 * defined by OCEAN user under account rule builder
 * 
 * @author Poonam Kalra
 */
public class OCEAN_Cancel_TC_26 extends CancellationModulePages {
	/**
	 * This function automates test case for test condition OCEAN_Cancel_T95; Test
	 * Case description : Validate that OCEAN apply primary account price sheet
	 * cancellation rules on a cancellation when defined by OCEAN user under account
	 * rule builder
	 * 
	 */
	@Test(priority = 2, groups = "smoke1", dataProvider = "fetchDataForTC26", dataProviderClass = CancellationDataProvider.class, description = "primary account price sheet cancellation rules on a cancellation when defined by OCEAN user under account rule builder")
	public void verifyFlatCancelRuleIntiatedByDealer(String[] inputData) throws Exception {
		boolean dbFlag = false;
		HashMap<String, String> dBsearch  = cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[1],1);
	    String priceSheetId = dBsearch.get("priceSheetId");                                     
		String primaryRoleId = dBsearch.get("roleIdentifier");
		String secRoleId = dBsearch.get("secRoleId");
		String primaryRoleName= dBsearch.get("name");
		String effDate = dBsearch.get("effectiveDate");
		String[] effDate1 = effDate.split("/s");
		String pCode = null ;
		effDate = effDate1[0];
		
		if(priceSheetId.equalsIgnoreCase("null")|| priceSheetId == ""){
		HashMap<Integer,HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		if(proCode.size()>1) {
		pCode  = proCode.get(1).get("PROGRAM_CODE");	
	      }
		}
		else {
			HashMap<Integer,HashMap<String, String>> progCode =	cancellation_getProgCodeWithAccRule(primaryRoleId,secRoleId);
			if(progCode.size()>1) {
				pCode  = progCode.get(1).get("CODE");	
			      }
	    	}
		  
		  HashMap<String, String> contractList = new HashMap<String, String>();
		  contractList =  cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,pCode,secRoleId);
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
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			if (inputData[1].length() > 0)
				initiatedBy = inputData[1];
			if (inputData[2].length() > 0)
				cancelReason = inputData[2];
			 enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
					 convertDateCancellation(contractList.get("SALE_DATE"), 18), "");
			// click ok for cancellation completed successfully
			 click("clickOK");
			//// get cancel method value applied as flat or outside flat in hashmap
			 waitForSomeTime(30);
			HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
			//go to standardCalculation
		    click("standardCalculation");
		    click("clickOK");
			HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
			takeScreenshot();
			click("standOKbtn");
			//compare standardcalculation and normal calcuted value
		   if(calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
			   logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
			   logger.info("Verfiying through Rule Info View");}
			  else { 
				  logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
				  logger.info("Verfiying  through Rule Info View");
				 }
			//get rule info view value in hashmap
			HashMap<Integer,HashMap<String, String>>dbruleInfo =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
			try{
				click("listtoggleStateRuleInfoView");
		       }
			catch(Exception e) {click("ruleInfoRefersh");}
			HashMap<Integer,HashMap<String, String>> ruleInfoViewValue  = getRuleInfoValue();
			logger.info("ruleInfoViewValue===="+ruleInfoViewValue);
			logger.info("DBInfoViewValue===="+dbruleInfo);
			boolean compareResult = compareValues(ruleInfoViewValue,dbruleInfo);
			  assertEquals(compareResult,true);
			   } 
			else {
			new SkipException("no value exist in db for Given PriceSheet");
			 assertEquals(dbFlag,true);
		    }
		  
		    }
         

  @Test(priority = 1, groups = "fullSuite", dataProvider = "fetchDataForTC26", dataProviderClass = CancellationDataProvider.class, description = "Validate that OCEAN reassign cancel method on a contract, if cancel date is modified by user as outside flat cancel period.")
		 public void verifyOutsideFlatCancelRuleInitiatedByDelear(String[] inputData) throws Exception {
			boolean dbFlag = false;
			HashMap<String, String> dBsearch  =  cancellation_getAccountRoleIdWithAccountRuleBuilder(inputData[1],1);
			 String primaryRoleId = dBsearch.get("roleIdentifier");
			 String priceSheetId = dBsearch.get("priceSheetId"); 
		     String secRoleId = dBsearch.get("secRoleId");
		     String primaryRoleName= dBsearch.get("name");
		     String effDate = dBsearch.get("effectiveDate");
		     String pCode = null ;
			 String[] effDate1 = effDate.split("/s");
		      effDate = effDate1[0];
		      if(priceSheetId.equalsIgnoreCase("null")|| priceSheetId == ""){
		  		HashMap<Integer,HashMap<String, String>> proCode = cancellation_getProgCode(primaryRoleId,effDate,secRoleId);
		  		if(proCode.size()>1) {
		  		pCode  = proCode.get(1).get("PROGRAM_CODE");	
		  	      }
		  		}
		  		else {
		  			HashMap<Integer,HashMap<String, String>> progCode =	cancellation_getProgCodeWithAccRule(primaryRoleId,secRoleId);
		  			if(progCode.size()>1) {
		  				pCode  = progCode.get(1).get("CODE");	
		  			      }
		  	    	}
			    HashMap<String, String> contractList = new HashMap<String, String>();
			    contractList =  cancellation_getContractIdBasedOnRoleIdAndProgCode(primaryRoleId,pCode,secRoleId);;
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
				String initiatedBy = "Dealer";
				String cancelReason = "Customer Request";
				if (inputData[1].length() > 0)
					initiatedBy = inputData[1];
				if (inputData[2].length() > 0)
					cancelReason = inputData[2];
				 enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy, cancelReason, "",
						 convertDateCancellation(contractList.get("SALE_DATE"), 65), "");
				// click ok for cancellation completed successfully
				click("clickOK");
				//// get cancel method value applied as flat or outside flat in hashmap
				waitForSomeTime(30);
				HashMap<String, String> calculatedCancellationValue = getCancellationDetails();
				//go to standardCalculation
			    click("standardCalculation");
			    click("clickOK");
				HashMap<String, String> calculatedStandardCancellationValue = getStandardCancellationDetails();
				takeScreenshot();
				click("standOKbtn");
				//compare standardcalculation and normal calcuted value
			   if(calculatedCancellationValue.equals(calculatedStandardCancellationValue)) {
				   logger.info("Standard Cancellation Value And Calculated Cancellation Value Are Same ");
				   logger.info("Verfiying through Rule Info View");}
				else { 
					logger.info("Standard Cancellation Value And Calculated Cancellation Value Are different ");
					logger.info("Verfiying  through Rule Info View");
					 }
				//get rule info view value in hashmap
				
			   HashMap<Integer,HashMap<String, String>>dbruleInfo =dBValidationOfAddAccountLevelRules(primaryRoleId,primaryRoleName) ;
				try {
					click("listtoggleStateRuleInfoView");
			       }
				catch(Exception e) {click("ruleInfoRefersh");}
				HashMap<Integer,HashMap<String, String>> ruleInfoViewValue  = getRuleInfoValue();
				logger.info("ruleInfoViewValue===="+ruleInfoViewValue);
				logger.info("DBInfoViewValue===="+dbruleInfo);
				boolean compareResult = compareValues(ruleInfoViewValue,dbruleInfo);
				  assertEquals(compareResult,true);
				   } 
				else {
				new SkipException("no value exist in db for Given PriceSheet");
				 assertEquals(dbFlag,true);
			    }
              } 
		    }
		 
	      

