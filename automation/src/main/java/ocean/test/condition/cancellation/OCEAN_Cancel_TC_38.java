package ocean.test.condition.cancellation;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.Test;

import ocean.modules.dataprovider.CancellationDataProvider;
import ocean.modules.pages.CancellationModulePages;

/**
 * OCEAN_Cancel_TC_38 class automates Ocean Cancel module Test Condition 38,
 * which holds 5 Test Case; Validate OCEAN for correct display of rule applied under rule info view, if rules 
 * 1. Not defined by account level.
 * 2. Defined at account level.
 * 
 * @author Surbhi Singhal
 */
public class OCEAN_Cancel_TC_38 extends CancellationModulePages {
	/**
		 * This function automates 5 test cases for test condition 38; Test Case
		 * description : Validate OCEAN for correct display of rule applied under rule info view, if rules 
		 * 1. Not defined by account level.
		 * 2. Defined at account level.
		 * 
		 */
	  
	@SuppressWarnings("rawtypes")
	@Test(priority = 1, groups ="smoke1", dataProvider = "fetchDataForTC_38", dataProviderClass = CancellationDataProvider.class, description ="Validation that OCEAN displays correct rule applied under rule info view") 
     public void validateAccountAndComplianceRule(String[] inputArray) throws Exception { 
		
		boolean accountRule = inputArray[0].equalsIgnoreCase("Yes")? true : false;
		boolean accountRuleActive = inputArray[1].equalsIgnoreCase("Active")? true : false;
		String mode = inputArray[2];
		HashMap<String, String> contractList = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbruleInfo = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> complianceRuleInfo  = new HashMap<String, String>();
		String roleId = "";
		String roleName = "";
		String contractNumber = "";
		String effectiveDate = "";
		boolean compareResult = false;
		
		if (accountRule) {
			// Process when Account Builder Rule is active for an account
			HashMap<Integer,HashMap<String, String>>accountRuleData = cancellation_SearchSpecificAccountRule("", "");
			for(int i =1 ; i< accountRuleData.size(); i++) { 
	    	  HashMap<String, String> getValue = accountRuleData.get(i);
	    	  for (Map.Entry mapElement : getValue.entrySet()) {
	    		  String roleIdName = (String) mapElement.getKey();
	    		  roleId = roleIdName.substring(0, roleIdName.indexOf("_"));
	    		  roleName = roleIdName.substring(roleIdName.indexOf("_")+1);
	    		  effectiveDate = (String) mapElement.getValue();
	    	  }
	    	  
	    	  if (accountRuleActive) {
	    		  contractList = cancellation_getContractIdBasedOnStatusRoleIdAccRulesClaims("Dealer",roleId,"","");  
	    	  } else {
	    		  String minSaleDate = convertDateCancellation(effectiveDate, -70);
	    		  contractList = cancellation_getContractIdBasedOnStatusRoleIdAccRulesClaims("Dealer",roleId,"",minSaleDate+"*"); 
	    	  }
	    	  
	    	  if(contractList.size() > 0) {
	    		  contractNumber = contractList.get("CERT");
	    		  if (accountRuleActive) {
	    			dbruleInfo = dBValidationOfAccountLevelRulesforInitatorDealer(roleId) ;
	    			logger.info("Account Builder Rules : " + dbruleInfo);
	  				if(mode.equalsIgnoreCase("Clear")) {
	  				// Process when Account Builder Rule is active but all the account Rules will be cleared for an account
	  					HashMap<String, String> getValue1 = new HashMap<String, String>();
	  					for(int j =1 ; j<= dbruleInfo.size(); j++) { 
	  						getValue1.putAll(dbruleInfo.get(j));			
	  					}
	  					System.out.println("New Account Rules====>"+getValue1);
	  					//// Navigate to Account Rules Builder tab	
	  					goToCancellationTab(); 
			 			goToAccountsRuleBuilderTab();
			 			//// Search and edit the rule for Account
			 			findAccountAndEditAccountRule(getValue1,roleName);
			 			dbruleInfo = dBValidationOfAccountLevelRulesforInitatorDealer(roleId) ;
		    			logger.info("Account Builder Rules after clearing all the Rules : " + dbruleInfo);
		    			if(dbruleInfo.size()==0) {
		    				complianceRuleInfo = getComplianceRules(contractNumber, roleName);
		    				logger.info("Compliance Rules : " + complianceRuleInfo);
		    			}
	  				} else if (mode.equalsIgnoreCase("Edit")) {
	  					HashMap<Integer, HashMap<String, String>> dbruleInfoNew = new HashMap<Integer, HashMap<String, String>>();
	  					for(int j =1 ; j< dbruleInfo.size(); j++) { 
	  						HashMap<String, String> getValue1 = dbruleInfo.get(j);
	  						for (Map.Entry mapElement1 : getValue1.entrySet()) {
	  							String getValue1Key = (String) mapElement1.getKey();
	  							if(getValue1Key.contains("Refund Percent") || getValue1Key.contains("Cancel Fee") || getValue1Key.contains("Payee") || getValue1Key.contains("Refund Based On")) {
	  								HashMap<String, String> editedRefundRule = findEditedValue(getValue1,"","");
	  				 				logger.info(getValue1Key + " dbruleInfo after editing the rules should be :"+ editedRefundRule);
	  				 				//// Navigate to Account Rules Builder tab
	  				 				goToCancellationTab(); 
	  				 				goToAccountsRuleBuilderTab();
	  				 				//// Search and edit the rule for Account
	  				 				findAccountAndEditAccountRule(editedRefundRule,roleName);
	  				 				dbruleInfoNew = dBValidationOfAccountLevelRulesforInitatorDealer(roleId) ;
	  			  					logger.info("Account Builder Rules after editing : " + dbruleInfoNew);
	  			  					dbruleInfo = dbruleInfoNew;
	  				 				break;
	  				 			}
	  				 		}
  							if(dbruleInfoNew.size()> 0)
	  						break;
	  					}
	  				} 		
	  			} else {
	  				if (contractList.size()>0) {
	  					contractNumber = contractList.get("CERT");
	  					roleName = contractList.get("name");
	  					complianceRuleInfo = getComplianceRules(contractNumber, roleName);
	  					logger.info("Compliance Rules : " + complianceRuleInfo);
	  				}
	  			}
	  		break;
	    	  } 
			}	 
	      } else {
			contractList = compliance_getCertBasedOnActiveRoleIdBasedOnRoleType("Dealer");
			if (contractList.size()>0) {
				contractNumber = contractList.get("CERT");
				roleName = contractList.get("name");
				complianceRuleInfo = getComplianceRules(contractNumber, roleName);
				logger.info("Compliance Rules : " + complianceRuleInfo);
			}
	      }
		if (contractList.size()>0 && contractList.get("CERT").length() > 0) { 
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
			int daysDiff = 0;
			if(accountRule){
				long daysDiffCal = calculateDaysBwTwoDates(contractList.get("SALE_DATE"), effectiveDate);
				daysDiff = (int) (daysDiffCal > 70 ? daysDiffCal + 1 : 70);
			} else {
				daysDiff = 7;
			}
		
			String cancelMiles = "";
			String initiatedBy = "Dealer";
			String cancelReason = "Customer Request";
			// Enter the cancellation parameters on New Cancellation screen
			enterValuesOnNewCancellationTabAndClickCalculate(initiatedBy,cancelReason, cancelMiles, convertDateCancellation(contractList.get("SALE_DATE"), daysDiff), "");
	  	
			// click ok for cancellation completed successfully
			click("okClick");
			waitForSomeTime(30);
			try{
				click("listtoggleStateRuleInfoView");
				if(!checkIsDisplayed("ruleInfoRefersh"))
				click("listtoggleStateRuleInfoView");
		       }
			catch(Exception e) {
				click("ruleInfoRefersh");
				}
			
			if (accountRule && accountRuleActive && !mode.equalsIgnoreCase("Clear")) {
				HashMap<Integer,String[]> ruleInfovalue =  getRuleInfoValueResult();
				logger.info("Rule Info View Data : " + ruleInfovalue);
				compareResult = compareRulesValues(dbruleInfo,ruleInfovalue);
				if(compareResult) {
					logger.info("Account Builder Rules are correctly applied for : Account Rule Applied = "+ inputArray[0] + ", Account Rule Status = " + inputArray[1] + ", Mode = "+ inputArray[2]);
				} else { 
					logger.info("Account Builder Rules are not correctly applied for : Account Rule Applied = "+ inputArray[0] + ", Account Rule Status = " + inputArray[1] + ", Mode = "+ inputArray[2]);
				}
				
			} else {
				HashMap<String, String> matchRuleMap = getComplainceRuleWithRuleInfoViewMap();
				logger.info("Rule Info View Data : " + matchRuleMap);
				compareResult = verifyComplainceRuleWithRuleInfoView(complianceRuleInfo, matchRuleMap);
				if(compareResult) {
					logger.info("Compliance Rules are correctly applied for : Account Rule Applied = "+ inputArray[0] + ", Account Rule Status = " + inputArray[1] + ", Mode = "+ inputArray[2]);
				} else { 
					logger.info("Compliance Rules are not correctly applied for : Account Rule Applied ="+ inputArray[0] + ", Account Rule Status = " + inputArray[1] + ", Mode = "+ inputArray[2]);
				}
			}
			assertEquals(compareResult,true);
   } else {
		throw new SkipException("no value exist in db.");
	}
	  
}
}