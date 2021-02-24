package ocean.modules.database;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * compliance Module
 * 
 * @author Mohit Goel
 */
public class ComplianceDataBase extends CommonFunctions {
	/**
	 * This gets SearchDataCountOnCancellationScreen
	 * 
	 */
	public HashMap<String, String> cancellation_getSearchDataCountOnCancellationScreen(
			HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "";
		String query1 = "select count(1) as count";
		String query2 = " from [dbo].[ALLSALES_DETAILS] where ";
		String myvalue = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String key = (String) mapElement.getKey();
			String value = (String) mapElement.getValue();
			if (value.equals("*")) {
				//// do noting
			} else if (value.length() < 1) {
				//// do nothing
			} else {

				myvalue = myvalue + key + " = '" + value + "' and ";
			}

		}
		query = query1 + query2 + myvalue;
		query = query.substring(0, query.lastIndexOf("and")) + ";";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;

	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status it will fetch contracts for current year only
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusAndPriceSheet(String status,
			String priceSheet) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = "select top 1 CERT,SALE_DATE from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' and PROGRAM_CODE = '"
				+ priceSheet + "' and sale_date like '%" + year + "%'order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search return refund percentage and cancel fees data
	 * 
	 */
	public HashMap<String, String> cancellation_getRefundPercentAndCancelFee(String contractId) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select CANCEL_FEE_AMOUNT,REFUND_PERCENTAGE from [dbo].[CANCELLATION_PARAMETERS]  where ALLSALES_DETAILS_ID in \r\n"
				+ "(select ID from [dbo].[ALLSALES_DETAILS]\r\n" + "where cert = '" + contractId + "')";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 08
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC08(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();

		String query = "select top 1 sales.CERT as Contract_Number, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.START_MILEAGE as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ "account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where statuss.name = '"
				+ status + "' order by sales.id desc;";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.START_MILEAGE as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ "account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where statuss.name = '"
				+ status + "' order by sales.id desc;";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> cancellation_getCancellationMouduleSearchData(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select sales.CERT as Contract_Number,price.INTERNAL_NAME as PriceSheet_Name,sales.PROGRAM_CODE, "
				+ " account.NAME as Primary_Account,account.ROLE_IDENTIFIER, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) AS customer_name,statuss.Name as contractStatus ,sales.COMMENTS "
				+ "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] account on account.id =  "
				+ "sales.PRIMARY_ACCOUNT_ID join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ " where sales.CERT = '" + contractId + "';";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This is used to search on Compliance Search
	 * 
	 */
	public HashMap<String, String> compliance_Search(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "";
		String query1 = "select top " + "1" + " ";
		String query2 = " from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
				+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID join [dbo].[COMPLIANCE_GROUP] c "
				+ " on a.[COMPLIANCE_GROUP_ID] =c.ID join [dbo].[STATE] d "
				+ " on b.document_state_id = d.ID  join PRICING_PRICESHEET pp on pp.code=c.name "
				+ " where  pp.filename is not null  and ";
		String myKey = "";
		String myvalue = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String key = (String) mapElement.getKey();
			String value = (String) mapElement.getValue();
			if (value.equals("*")) {
				String value1 = "";
				if (key.equals("GroupName")) {
					key = " c.Name as GroupName ";
					value1 = " c.Name ";
				}
				if (key.equals("State")) {
					key = " d.display_name as State";
					value1 = " d.display_name ";
				}

				if (key.equals("ProductName")) {
					key = " pp. external_name as ProductName";
					value1 = "  pp. external_name ";
				}

				if (key.equals("VersionNumber")) {
					key = " b.Document_Version as VersionNumber";
					value1 = "  b.Document_Version  ";
				}

				if (key.equals("FormNumber")) {
					key = " b.Document_Name as FormNumber ";
					value1 = " b.Document_Name ";
				}

				if (key.equals("FormUsage")) {
					key = " b.Usage as FormUsage";
					value1 = " b.Usage ";
				}
				if (key.equals("FormType")) {
					key = "b.Compliance_Form as Formtype";
					value1 = "b.Compliance_Form";
				}
				if (key.equals("ProgramName")) {
					key = "b.document_program_name as ProgramName";
					value1 = "b.document_program_name";
				}
				myKey = myKey + key + ",";
				myvalue = myvalue + value1 + " is not null and ";
			} else if (value.length() < 1) {
				//// do nothing
			} else {
				String value1 = "";
				if (key.equals("GroupName")) {
					key = " c.Name as GroupName";
					value1 = " c.Name ";
				}
				if (key.equals("State")) {
					key = " d.display_name as State ";
					value1 = " d.display_name  ";
				}
				if (key.equals("ProductName")) {
					key = " pp. external_name as ProductName ";
					value1 = " pp. external_name";
				}
				if (key.equals("VersionNumber")) {
					key = "  b.Document_Version as VersionNumber";
					value1 = "  b.Document_Version ";
				}

				if (key.equals("FormNumber")) {
					key = " b.Document_Name as FormNumber ";
					value1 = "  b.Document_Name ";
				}

				if (key.equals("FormUsage")) {
					key = " b.Usage as FormUsage";
					value1 = " b.Usage ";
				}
				if (key.equals("FormType")) {
					key = "b.Compliance_Form as Formtype";
					value1 = "b.Compliance_Form";
				}
				if (key.equals("ProgramName")) {
					key = "b.document_program_name as ProgramName";
					value1 = "b.document_program_name";
				}

				myKey = myKey + key + ",";
				if (key.equals(" c.Name as GroupName"))
					myvalue = myvalue + value1 + " = " + value + " and ";
				else if (key.equals(" d.display_name as State ")) {
					myvalue = myvalue + value1 + " like '%" + value + "%' and ";
				} else
					myvalue = myvalue + value1 + " = '" + value + "' and ";

			}
		}
		query = query1 + myKey + query2 + myvalue;
		query = query.substring(0, query.lastIndexOf("and")) + ";";
		query = query.substring(0, query.lastIndexOf(","))
				+ query.substring(query.lastIndexOf(",") + 1, query.length());
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets business processor search all sales details and return us latest
	 * contract id
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> compliance_getcomplianceSearchData(
			HashMap<String, String> searchParamaters) throws Exception {
		String query = "";
		if (searchParamaters.get("GroupName") != null) {
			query = "Select c.Name as GroupName, d.display_name as State,b.document_program_name as ProgramName,pp. external_name as ProductName,"
					+ " b.Compliance_Form as Formtype, b.Document_Version as VersionNumber,b.Document_Name as FormNumber,"
					+ " b.Usage as FormUsage, d.Name as StateApproved from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
					+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID "
					+ " join [dbo].[COMPLIANCE_GROUP] c on a.[COMPLIANCE_GROUP_ID] =c.ID "
					+ " join [dbo].[STATE] d on b.document_state_id = d.ID  "
					+ " join PRICING_PRICESHEET pp on pp.code=c.name " + " where  pp.filename is not null and c.Name ='"
					+ searchParamaters.get("GroupName") + "'";
		}

		if (searchParamaters.get("State") != null && searchParamaters.get("FormType") != null) {
			query = "Select c.Name as GroupName, d.display_name as State,b.document_program_name as ProgramName,pp. external_name as ProductName,"
					+ " b.Compliance_Form as Formtype, b.Document_Version as VersionNumber,b.Document_Name as FormNumber,"
					+ " b.Usage as FormUsage, d.Name as StateApproved from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
					+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID "
					+ " join [dbo].[COMPLIANCE_GROUP] c on a.[COMPLIANCE_GROUP_ID] =c.ID "
					+ " join [dbo].[STATE] d on b.document_state_id = d.ID  "
					+ " join PRICING_PRICESHEET pp on pp.code=c.name "
					+ " where  pp.filename is not null and d.display_name='" + searchParamaters.get("State")
					+ "' and b.Compliance_Form='" + searchParamaters.get("Formtype") + "' ";
		}

		if (searchParamaters.get("State") != null) {
			query = "Select c.Name as GroupName, d.display_name as State,b.document_program_name as ProgramName,pp. external_name as ProductName,"
					+ " b.Compliance_Form as Formtype, b.Document_Version as VersionNumber,b.Document_Name as FormNumber,"
					+ " b.Usage as FormUsage, d.Name as StateApproved from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
					+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID "
					+ " join [dbo].[COMPLIANCE_GROUP] c on a.[COMPLIANCE_GROUP_ID] =c.ID "
					+ " join [dbo].[STATE] d on b.document_state_id = d.ID  "
					+ " join PRICING_PRICESHEET pp on pp.code=c.name "
					+ " where  pp.filename is not null and d.display_name='" + searchParamaters.get("State") + "' ";
		}
		if (searchParamaters.get("ProgramName") != null) {
			query = "Select c.Name as GroupName, d.display_name as State,b.document_program_name as ProgramName,pp. external_name as ProductName,"
					+ " b.Compliance_Form as Formtype, b.Document_Version as VersionNumber,b.Document_Name as FormNumber,"
					+ " b.Usage as FormUsage, d.Name as StateApproved from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
					+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID "
					+ " join [dbo].[COMPLIANCE_GROUP] c on a.[COMPLIANCE_GROUP_ID] =c.ID "
					+ " join [dbo].[STATE] d on b.document_state_id = d.ID  "
					+ " join PRICING_PRICESHEET pp on pp.code=c.name "
					+ " where  pp.filename is not null and b.document_program_name='"
					+ searchParamaters.get("ProgramName") + "' ";
		}
		if (searchParamaters.get("ProgramName") != null && searchParamaters.get("VersionNumber") != null) {
			query = "Select c.Name as GroupName, d.display_name as State,b.document_program_name as ProgramName,pp. external_name as ProductName,"
					+ " b.Compliance_Form as Formtype, b.Document_Version as VersionNumber,b.Document_Name as FormNumber,"
					+ " b.Usage as FormUsage, d.Name as StateApproved from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
					+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID "
					+ " join [dbo].[COMPLIANCE_GROUP] c on a.[COMPLIANCE_GROUP_ID] =c.ID "
					+ " join [dbo].[STATE] d on b.document_state_id = d.ID  "
					+ " join PRICING_PRICESHEET pp on pp.code=c.name "
					+ " where  pp.filename is not null and b.document_program_name='"
					+ searchParamaters.get("ProgramName") + "' " + " and b.Document_Version='"
					+ searchParamaters.get("VersionNumber") + "' ";
		}
		if (searchParamaters.get("GroupName") != null && searchParamaters.get("State") != null
				&& searchParamaters.get("FormUsage") != null) {
			query = "Select c.Name as GroupName, d.display_name as State,b.document_program_name as ProgramName,pp. external_name as ProductName,"
					+ " b.Compliance_Form as Formtype, b.Document_Version as VersionNumber,b.Document_Name as FormNumber,"
					+ " b.Usage as FormUsage, d.Name as StateApproved from [dbo].[COMPLIANCE_DOCUMENT_GROUP] a join [dbo].[COMPLIANCE_DOCUMENT] b "
					+ " on a.[COMPLIANCE_DOCUMENT_ID] = b.ID "
					+ " join [dbo].[COMPLIANCE_GROUP] c on a.[COMPLIANCE_GROUP_ID] =c.ID "
					+ " join [dbo].[STATE] d on b.document_state_id = d.ID  "
					+ " join PRICING_PRICESHEET pp on pp.code=c.name "
					+ " where  pp.filename is not null and  c.Name ='" + searchParamaters.get("GroupName") + "' "
					+ " and d.display_name ='" + searchParamaters.get("State") + "' and b.Usage ='"
					+ searchParamaters.get("FormUsage") + "' ";
		}
		return getAllDataFromDatabase(query);
	}

	/*
	 * to get the active roleID based on year and role type.
	 */
	public HashMap<String, String> compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(
			HashMap<String, String> excelMap) throws Exception {
		String roleType = excelMap.get("Role_Type");
		String ruleSubType = excelMap.get("Rule_Sub_Type");
		System.out.println("ruleSubType===" + ruleSubType);
		String rule_List = excelMap.get("Rule_List");
		String query = "";
		HashMap<String, String> dbMap2 = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (!ruleSubType.contains("Claims") && !ruleSubType.equals("Claims") && !rule_List.equals("Total Premium")
				&& !rule_List.equals("Customer Paid") && !ruleSubType.equals("If Transferred")) {
			query = "SELECT distinct A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] join UW_CONTRACT_STATUS stat on stat.Id =  SD.contract_status_id "
					+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
					+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
					+ " where A.ACCOUNT_STATUS_ID = 1 and stat.id=5 and  SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and pricesheet_id is not null and CERT NOT IN (SELECT CERT FROM [dbo].[CLAIMS]) "
					+ " and R.ROLE_NAME = '" + roleType + "' and SD.SALE_DATE like '%" + year + "%'"
					+ " order by A.ROLE_IDENTIFIER asc  " + ";";
		}
//	     else if(ruleSubType.equals("If Transferred")) {
//	    	 query = "SELECT distinct A.ROLE_IDENTIFIER"
//	                   + " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
//	                   + " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
//	                   + " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
//	                   + " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID join UW_CONTRACT_STATUS stat on stat.Id =  SD.contract_status_id left join"
//	                   + " [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=SD.contract_substatus_id where stat.id=5 and sub.id=1"
//	                   + " and A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and pricesheet_id is not null and CERT NOT IN (SELECT CERT FROM [dbo].[CLAIMS]) "
//	                   + " and R.ROLE_NAME = '"+ roleType + "' and SD.SALE_DATE like '%" + year + "%'"
//	                   + " order by A.ROLE_IDENTIFIER asc  " + ";";
//
//	     } 
//	     else if(rule_List.equals("Total Premium")) {
//	    	 query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] where A.ACCOUNT_STATUS_ID = 1 and "
//	    	 		+ " SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and pricesheet_id is not null and A.ROLE_IDENTIFIER in (select distinct aa.ROLE_IDENTIFIER from RULE_CATEGORY_APPLIED_RULE a"
//	    	 		+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id join "
//	    	 		+ " CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id join dbo.Account aa on aa.id = "
//	    	 		+ " ac.Primary_account_id where ac.pricesheet_id is null and b.name like '%Total Premium%' and ac.secondary_account_id is null) order by A.ROLE_IDENTIFIER asc ;";
//
//	     }
//	     else if(rule_List.equals("Customer Paid")) {
//	    	 query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] where A.ACCOUNT_STATUS_ID = 1 and "
//		    	 		+ " SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and pricesheet_id is not null and A.ROLE_IDENTIFIER in (select distinct aa.ROLE_IDENTIFIER from RULE_CATEGORY_APPLIED_RULE a"
//		    	 		+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id join "
//		    	 		+ " CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id join dbo.Account aa on aa.id = "
//		    	 		+ " ac.Primary_account_id where ac.pricesheet_id is null and b.name like '%Customer Paid%' and ac.secondary_account_id is null) order by A.ROLE_IDENTIFIER asc ;";
//
//	     } 
		else {
			query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join UW_CONTRACT_STATUS stat on stat.Id =  SD.contract_status_id join [dbo].[CLAIMS] b on SD.cert = b.cert join [dbo].[ACCOUNT] A "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID INNER JOIN dbo.ACCOUNT_TYPE AS T ON T.ID = "
					+ " A.ACCOUNT_TYPE_ID where A.ACCOUNT_STATUS_ID = 1 and SD.CONTRACT_STATUS_ID =5 and stat.id=5 and R.ROLE_NAME = '"
					+ roleType + "' and SD.SALE_DATE like '%" + year + "%' "
					+ " and b.CLAIM_AMOUNT> 0 AND STATUS='Paid' and SD.PRICESHEET_ID is not null and sd.program_code <> 'olw' order by A.ROLE_IDENTIFIER asc;";
		}
		System.out.println("query====" + query);
		dbMap = getAllDataFromDatabase(query);
		System.out.println("dbMap====" + dbMap);
		for (int i = 1; i < dbMap.size(); i++) {
			Random generator = new Random();
			int number = generator.nextInt(dbMap.size() - 1) + 1;
			String roleid = dbMap.get(number).get("ROLE_IDENTIFIER");
			System.out.println("roleid====" + roleid);
			String query1 = "select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on"
					+ " rr.account_Cancellation_id = ac.id where a.role_identifier = '" + roleid + "'" + ";";
			System.out.println("query1====" + query1);
			dbMap1 = getTopRowDataFromDatabase(query1);
			System.out.println("dbMap1====" + dbMap1);
			if (dbMap1.size() == 0) {
				String Data = dbMap.get(number).get("ROLE_IDENTIFIER");
				String query2 = "";
				System.out.println("ruleSubType===" + ruleSubType);
				if (!ruleSubType.contains("Claims") && !ruleSubType.equals("Claims")
						&& !rule_List.equals("Total Premium") && !rule_List.equals("Customer Paid")) {
					query2 = "select sales.CERT as Contract_Number, account.role_identifier as roleId, sales.effective_date, sales.sale_date, sales.PLAN_MILES,sales.PLAN_MONTHS,sales.EXPIRATION_DATE,"
							+ " sales.EXPIRATION_MILEAGE,sales.Customer_Paid,sales.DEALER_PAID, account.name, (sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES]) as SaleMileage,"
							+ " ((sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES])+2214) as cancelMiles , (sales.DEALER_PAID - sales.DBCR_AMT )as Premium from [dbo].[ALLSALES_DETAILS] sales "
							+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where "
							+ " account.role_identifier ='" + Data
							+ "' and pricesheet_id is not null and sales.program_code <> 'olw' "
							+ " order by sales.sale_date desc ;";
				} else {
					query2 = "select sales.CERT as Contract_Number, account.role_identifier as roleId, sales.effective_date, sales.sale_date, sales.PLAN_MILES,sales.PLAN_MONTHS,sales.EXPIRATION_DATE,"
							+ " sales.EXPIRATION_MILEAGE,sales.Customer_Paid,sales.DEALER_PAID, account.name, (sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES]) as SaleMileage,"
							+ " ((sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES])+2214) as cancelMiles , (sales.DEALER_PAID - sales.DBCR_AMT )as Premium from [dbo].[ALLSALES_DETAILS] sales join [dbo].[CLAIMS] b on sales.cert = b.cert "
							+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where b.CLAIM_AMOUNT> 0 AND STATUS='Paid' and "
							+ " account.role_identifier ='" + Data
							+ "' and pricesheet_id is not null and sales.program_code <> 'olw' "
							+ " order by sales.sale_date desc ;";
				}

				System.out.println("query2==========" + query2);
				dbMap2 = getTopRowDataFromDatabase(query2);
				System.out.println("dbMap2====" + dbMap2);
				return dbMap2;
			}
		}
		return null;
	}

	/*
	 * to get the active roleID based on year and role type.
	 */
	public HashMap<String, String> compliance_getCertBasedOnActiveRoleIdBasedOnRoleType(String roleType)
			throws Exception {
		HashMap<String, String> dbMap2 = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = "SELECT distinct A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5 " + " and R.ROLE_NAME = '" + roleType
				+ "' and SD.SALE_DATE like '%" + year + "%'" + " order by A.ROLE_IDENTIFIER asc  " + ";";
		System.out.println("query====" + query);
		dbMap = getAllDataFromDatabase(query);
		System.out.println("dbMap====" + dbMap);
		for (int i = 1; i < dbMap.size(); i++) {
			Random generator = new Random();
			int number = generator.nextInt(dbMap.size());
			String roleid = dbMap.get(number).get("ROLE_IDENTIFIER");
			System.out.println("roleid====" + roleid);
			String query1 = "select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on"
					+ " rr.account_Cancellation_id = ac.id where a.role_identifier = '" + roleid + "'" + ";";
			System.out.println("query1====" + query1);
			dbMap1 = getTopRowDataFromDatabase(query1);
			System.out.println("dbMap1====" + dbMap1);
			if (dbMap1.size() == 0) {
				String Data = dbMap.get(number).get("ROLE_IDENTIFIER");
				String query2 = "select sales.CERT as Contract_Number, sales.sale_date, account.name from [dbo].[ALLSALES_DETAILS] sales "
						+ "join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where "
						+ "account.role_identifier ='" + Data
						+ "' and pricesheet_id is not null order by sales.sale_date desc ;";
				dbMap2 = getTopRowDataFromDatabase(query2);
				System.out.println("dbMap2====" + dbMap2);
				return dbMap2;
			}
		}
		return null;
	}

	/*
	 * to get the active roleID based on year and role type.
	 */
	public HashMap<String, String> compliance_getCertBasedOnActiveRoleIdBasedOnRoleType_2(
			HashMap<String, String> excelMap) throws Exception {
		String roleType = excelMap.get("Role_Type");
		String ruleSubType = excelMap.get("Rule_Sub_Type");
		String query = "";
		HashMap<String, String> dbMap2 = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (!ruleSubType.contains("Claims")) {
			query = "SELECT distinct A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
					+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
					+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
					+ " where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and CERT NOT IN (SELECT CERT FROM [dbo].[CLAIMS]) "
					+ " and R.ROLE_NAME = '" + roleType + "' and SD.SALE_DATE like '%" + year + "%'"
					+ " order by A.ROLE_IDENTIFIER asc  " + ";";
		} else {
			query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join [dbo].[CLAIMS] b on SD.cert = b.cert join [dbo].[ACCOUNT] A "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID INNER JOIN dbo.ACCOUNT_TYPE AS T ON T.ID = "
					+ " A.ACCOUNT_TYPE_ID where A.ACCOUNT_STATUS_ID = 1 and SD.CONTRACT_STATUS_ID =5 and R.ROLE_NAME = '"
					+ roleType + "' and SD.SALE_DATE like '%" + year + "%' "
					+ " and CLAIM_AMOUNT> 0 AND STATUS='Paid' and SD.PRICESHEET_ID is not null and sd.program_code <> 'olw' order by A.ROLE_IDENTIFIER asc;";
		}
		System.out.println("query====" + query);
		dbMap = getAllDataFromDatabase(query);
		System.out.println("dbMap====" + dbMap);
		for (int i = 1; i < dbMap.size(); i++) {
			Random generator = new Random();
			int number = generator.nextInt(dbMap.size() - 1) + 1;
			String roleid = dbMap.get(number).get("ROLE_IDENTIFIER");
			System.out.println("roleid====" + roleid);
			String query1 = "select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on"
					+ " rr.account_Cancellation_id = ac.id where a.role_identifier = '" + roleid + "'" + ";";
			System.out.println("query1====" + query1);
			dbMap1 = getTopRowDataFromDatabase(query1);
			System.out.println("dbMap1====" + dbMap1);
			if (dbMap1.size() == 0) {
				String Data = dbMap.get(number).get("ROLE_IDENTIFIER");
				String query2 = "select sales.CERT as Contract_Number, account.role_identifier as roleId, sales.effective_date, sales.sale_date, sales.PLAN_MILES,sales.PLAN_MONTHS,sales.EXPIRATION_DATE,sales.EXPIRATION_MILEAGE,sales.CUSTOMER_PAID,sales.DEALER_PAID, account.name from [dbo].[ALLSALES_DETAILS] sales "
						+ "join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where "
						+ "account.role_identifier ='" + Data
						+ "' and pricesheet_id is not null and sales.program_code <> 'olw' "
						+ " order by sales.sale_date desc ;";
				System.out.println("query2==========" + query2);
				dbMap2 = getTopRowDataFromDatabase(query2);
				System.out.println("dbMap2====" + dbMap2);
				return dbMap2;
			}
		}
		return null;
	}

	/*
	 * to get the State display name based on cert and primary account name.
	 */
	public String compliance_getStateDisplayNamebasedOnCertAndPAccount(String cert, String pAccount) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String state = "";
		String query = "select display_name from dbo.state where name in (select state from allsales_details asd "
				+ " join [dbo].[ACCOUNT] account on account.id =  asd.PRIMARY_ACCOUNT_ID where cert = '" + cert + "'"
				+ " and name='" + pAccount + "') ;";
		System.out.println("statequery====" + query);
		dbMap = getTopRowDataFromDatabase(query);
		state = dbMap.get("display_name");
		System.out.println("dbMap====" + dbMap);
		return state;
	}

	/*
	 * to get the pricesheet group name based on cert and primary account name.
	 */
	public String compliance_getPricesheetIdbasedOnCert(String cert) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String code = "";
		String query = "select group_name from COMPLIANCE_GROUP_PRICESHEET cgp where cgp.PRICING_PRICESHEET_ID in"
				+ "(select id from dbo.pricing_pricesheet where id in(select parent_pricesheet_id from dbo.pricing_pricesheet"
				+ " where id in(select parent_pricesheet_id from dbo.pricing_pricesheet where id in (select pricesheet_id "
				+ "from AllSales_Details  where cert = '" + cert + "'))));";
		System.out.println("pricesheetquery====" + query);
		dbMap = getTopRowDataFromDatabase(query);
		code = dbMap.get("group_name");
		System.out.println("dbMap====" + dbMap);
		return code;
	}

	/*
	 * to get the version number of PriceSheet group
	 */
	public String getPriceSheetGroupVersion(String groupName, String state) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String versionNumber = "";
		String query = "select top 1 document_version as VersionNumber from COMPLIANCE_DOCUMENT c join COMPLIANCE_DOCUMENT_GROUP a "
				+ " on a.compliance_document_id = c.id join [dbo].[STATE] s on s.id = c.document_state_id join "
				+ " COMPLIANCE_GROUP b on b.id = a.compliance_group_id where b.name ='" + groupName + "' "
				+ " and Display_Name = '" + state + "' order by c.id desc;";
		System.out.println("query===" + query);
		dbMap = getTopRowDataFromDatabase(query);
		versionNumber = dbMap.get("VersionNumber");
		return versionNumber;
	}

	/*
	 * to get the version number of PriceSheet group
	 */
	public HashMap<String, String> getPriceSheetGroupVersionAndFormNumber(String groupName, String state)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 document_version as Version_Number, document_Name as Form_Number from COMPLIANCE_DOCUMENT c join COMPLIANCE_DOCUMENT_GROUP a "
				+ " on a.compliance_document_id = c.id join [dbo].[STATE] s on s.id = c.document_state_id join "
				+ " COMPLIANCE_GROUP b on b.id = a.compliance_group_id where b.name ='" + groupName + "' "
				+ " and Display_Name = '" + state + "' order by c.id desc;";
		System.out.println("query===" + query);
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/*
	 * to get the version number of PriceSheet group
	 */
	public String getDifferentState(String roleId, String displayName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String newState = "";
		String query = "select display_name from dbo.state where name in (select state from allsales_details asd join [dbo].[ACCOUNT] account on account.id =  asd.PRIMARY_ACCOUNT_ID "
				+ " where cert in(select sales.CERT as Contract_Number from [dbo].[ALLSALES_DETAILS] sales	join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where"
				+ " account.role_identifier ='" + roleId
				+ "' and pricesheet_id is not null and sales.program_code <> 'olw')) and display_name<>'" + displayName
				+ "';";
		System.out.println("querynewDisplay===" + query);
		dbMap = getTopRowDataFromDatabase(query);
		System.out.println("dbMap============" + dbMap);

		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			System.out.println("value===" + value);
			String key = entry.getKey();
			if (value.equals("NULL") || value.equals("") || value.equals(null)) {
				dbMap.replace(key, "Multi-State");
			}
		}
		newState = dbMap.get("display_name");
		System.out.println("news==" + newState);
		return newState;
	} /*
		 * to get the pricesheet name based on cert and primary account name.
		 */

	public String compliance_getPricesheetIdNamebasedOnCert(String cert) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String code = "";
		String query = "select external_name from dbo.pricing_pricesheet where id in(select parent_pricesheet_id from dbo.pricing_pricesheet"
				+ " where id in(select parent_pricesheet_id from dbo.pricing_pricesheet where id in (select pricesheet_id "
				+ "from AllSales_Details  where cert = '" + cert + "')));";
		System.out.println("pricesheetnamequery====" + query);
		dbMap = getTopRowDataFromDatabase(query);
		code = dbMap.get("external_name");
		System.out.println("dbMap====" + dbMap);
		return code;
	}

	/*
	 * to get the active roleID based on year and role type.
	 */
	public HashMap<String, String> compliance_getCertBasedOnExistingPricesheetID(HashMap<String, String> excelMap,
			String program_Code) throws Exception {
		String roleType = excelMap.get("Role_Type");
		String ruleSubType = excelMap.get("Rule_Sub_Type");
		String rule_List = excelMap.get("Rule_List");
		String query = "";
		HashMap<String, String> dbMap2 = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (!ruleSubType.contains("Claims") && !ruleSubType.equals("Claims") && !rule_List.equals("Total Premium")
				&& !rule_List.equals("Customer Paid")) {
			query = "SELECT distinct A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] join UW_CONTRACT_STATUS stat on stat.Id =  SD.contract_status_id "
					+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
					+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
					+ " where A.ACCOUNT_STATUS_ID = 1 and stat.id=5 and  SD.CONTRACT_STATUS_ID =5 and sd.program_code = '"
					+ program_Code
					+ "' and pricesheet_id is not null and CERT NOT IN (SELECT CERT FROM [dbo].[CLAIMS]) "
					+ " and R.ROLE_NAME = '" + roleType + "' and SD.SALE_DATE like '%" + year + "%'"
					+ " order by A.ROLE_IDENTIFIER asc  " + ";";
		} else {
			query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join UW_CONTRACT_STATUS stat on stat.Id =  SD.contract_status_id join [dbo].[CLAIMS] b on SD.cert = b.cert join [dbo].[ACCOUNT] A "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID INNER JOIN dbo.ACCOUNT_TYPE AS T ON T.ID = "
					+ " A.ACCOUNT_TYPE_ID where A.ACCOUNT_STATUS_ID = 1 and stat.id=5 and SD.CONTRACT_STATUS_ID =5 and R.ROLE_NAME = '"
					+ roleType + "' and SD.SALE_DATE like '%" + year + "%' "
					+ " and CLAIM_AMOUNT> 0 AND STATUS='Paid' and SD.PRICESHEET_ID is not null and sd.program_code = '"
					+ program_Code + "' order by A.ROLE_IDENTIFIER asc;";
		}
		System.out.println("query====" + query);
		dbMap = getAllDataFromDatabase(query);
		System.out.println("dbMap====" + dbMap);
		for (int i = 1; i < dbMap.size(); i++) {
			Random generator = new Random();
			int number = generator.nextInt(dbMap.size() - 1) + 1;
			String roleid = dbMap.get(number).get("ROLE_IDENTIFIER");
			System.out.println("roleid====" + roleid);
			String query1 = "select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on"
					+ " rr.account_Cancellation_id = ac.id where a.role_identifier = '" + roleid + "'" + ";";
			System.out.println("query1====" + query1);
			dbMap1 = getTopRowDataFromDatabase(query1);
			System.out.println("dbMap1====" + dbMap1);
			if (dbMap1.size() == 0) {
				String Data = dbMap.get(number).get("ROLE_IDENTIFIER");
				String query2 = "";
				if (!ruleSubType.contains("Claims") && !ruleSubType.equals("Claims")
						&& !rule_List.equals("Total Premium") && !rule_List.equals("Customer Paid")) {
					query2 = "select sales.CERT as Contract_Number, account.role_identifier as roleId, sales.effective_date, sales.sale_date, sales.PLAN_MILES,sales.PLAN_MONTHS,sales.EXPIRATION_DATE,"
							+ " sales.EXPIRATION_MILEAGE,sales.Customer_Paid,sales.DEALER_PAID, account.name, (sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES]) as SaleMileage,"
							+ " ((sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES])+2214) as cancelMiles , (sales.DEALER_PAID - sales.DBCR_AMT )as Premium from [dbo].[ALLSALES_DETAILS] sales "
							+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where "
							+ " account.role_identifier ='" + Data
							+ "' and pricesheet_id is not null and sales.program_code = '" + program_Code + "' "
							+ " order by sales.sale_date desc ;";
				} else {
					query2 = "select sales.CERT as Contract_Number, account.role_identifier as roleId, sales.effective_date, sales.sale_date, sales.PLAN_MILES,sales.PLAN_MONTHS,sales.EXPIRATION_DATE,"
							+ " sales.EXPIRATION_MILEAGE,sales.Customer_Paid,sales.DEALER_PAID, account.name, (sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES]) as SaleMileage,"
							+ " ((sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES])+2214) as cancelMiles , (sales.DEALER_PAID - sales.DBCR_AMT )as Premium from [dbo].[ALLSALES_DETAILS] sales join [dbo].[CLAIMS] b on sales.cert = b.cert"
							+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where CLAIM_AMOUNT> 0 AND STATUS='Paid' and"
							+ " account.role_identifier ='" + Data
							+ "' and pricesheet_id is not null and sales.program_code = '" + program_Code + "' "
							+ " order by sales.sale_date desc ;";
				}

				System.out.println("query2==========" + query2);
				dbMap2 = getTopRowDataFromDatabase(query2);
				System.out.println("dbMap2====" + dbMap2);
				return dbMap2;
			}
		}
		return null;
	}

	/*
	 * to get the active roleID based on year and role type.
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> compliance_getCertBasedOnActiveRoleIdBasedOnRoleType123(
			HashMap<String, String> excelMap) throws Exception {
		String roleType = excelMap.get("Role_Type");
		String ruleSubType = excelMap.get("Rule_Sub_Type");
		System.out.println("ruleSubType===" + ruleSubType);
		String rule_List = excelMap.get("Rule_List");
		String query = "";
		HashMap<String, String> dbMap2 = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (rule_List.equals("Total Premium")) {
			query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] where A.ACCOUNT_STATUS_ID = 1 and "
					+ " SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and pricesheet_id is not null and A.ROLE_IDENTIFIER in (select distinct aa.ROLE_IDENTIFIER from RULE_CATEGORY_APPLIED_RULE a"
					+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id join "
					+ " CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id join dbo.Account aa on aa.id = "
					+ " ac.Primary_account_id where ac.pricesheet_id is null and b.name like '%Total Premium%' and ac.secondary_account_id is null) order by A.ROLE_IDENTIFIER asc ;";

		} else if (rule_List.equals("Customer Paid")) {
			query = "SELECT distinct A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] where A.ACCOUNT_STATUS_ID = 1 and "
					+ " SD.CONTRACT_STATUS_ID =5 and sd.program_code <> 'olw' and pricesheet_id is not null and A.ROLE_IDENTIFIER in (select distinct aa.ROLE_IDENTIFIER from RULE_CATEGORY_APPLIED_RULE a"
					+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id join "
					+ " CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id join dbo.Account aa on aa.id = "
					+ " ac.Primary_account_id where ac.pricesheet_id is null and b.name like '%Customer Paid%' and ac.secondary_account_id is null) order by A.ROLE_IDENTIFIER asc ;";

		}
		System.out.println("query====" + query);
		dbMap = getAllDataFromDatabase(query);
		System.out.println("dbMap====" + dbMap);
		Random generator = new Random();
		int number = generator.nextInt(dbMap.size() - 1) + 1;
		String Data = dbMap.get(number).get("ROLE_IDENTIFIER");
		String query2 = "";
		System.out.println("ruleSubType===" + ruleSubType);
		query2 = "select sales.CERT as Contract_Number, account.role_identifier as roleId, sales.effective_date, sales.sale_date, sales.PLAN_MILES,sales.PLAN_MONTHS,sales.EXPIRATION_DATE,"
				+ " sales.EXPIRATION_MILEAGE,sales.CUSTOMER_PAID,sales.DEALER_PAID, account.name, (sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES]) as SaleMileage,"
				+ " ((sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES])+2214) as cancelMiles , (sales.DEALER_PAID - sales.DBCR_AMT )as Premium from [dbo].[ALLSALES_DETAILS] sales "
				+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID where "
				+ " account.role_identifier ='" + Data
				+ "' and pricesheet_id is not null and sales.program_code <> 'olw' "
				+ " order by sales.sale_date desc ;";

		System.out.println("query2==========" + query2);
		dbMap2 = getTopRowDataFromDatabase(query2);
		System.out.println("dbMap2====" + dbMap2);
		return dbMap2;

	}

}
