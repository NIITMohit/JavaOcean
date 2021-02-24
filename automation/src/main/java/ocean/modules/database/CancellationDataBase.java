package ocean.modules.database;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * cancellation Module
 * 
 * @author Mohit Goel
 */
public class CancellationDataBase extends CommonFunctions {
	/**
	 * This function gets all required details used in TC 09
	 * 
	 */

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number, sales.SALE_DATE as Sale_Date, sales.CREATE_DATE as Process_Date, sales.DBCR_AMT as DB_CR, "
				+ "  (sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, sales.Dealer_Paid as Dealer_Paid, price.INTERNAL_NAME "
				+ " as Pricesheet,statuss.NAME as Status, sales.coverage as Coverage, sales. vehicle_price as Vehicle_Price, sales.x_term As Term from [dbo].[ALLSALES_DETAILS] sales "
				+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "	left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID join  ACCOUNT_STATUS accS on accS.id =account.ACCOUNT_STATUS_ID join dbo.claims b "
				+ "	on sales.cert = b.cert join CANCELLATION_PARAMETERS cp on sales.id = cp.ALLSALES_DETAILS_ID where b.CLAIM_AMOUNT> 0 and b.STATUS='paid' and statuss.name = '"
				+ status + "' " + "	order by sales.id desc;";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09_old(String status) throws Exception {
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
	public HashMap<String, String> cancellation_getDetailsForTC09_02(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number, ff.Fax, ff.Phone, account.NAME as Primary_Account, adr.City, ccount.email_address as Email, adr.ZIP_CODE as ZIP, "
				+ " adr.State, adr.STREET as Address, account.ROLE_IDENTIFIER as Primary_Acct_Id, accS.Status as Primary_Acct_Status from [dbo].[ALLSALES_DETAILS] sales "
				+ " join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID join [dbo].[ACCOUNT_EMAIL] ccount on ccount.ACCOUNT_ID =  sales.PRIMARY_ACCOUNT_ID "
				+ " join  [dbo].[ACCOUNT_ADDRESS] adr on adr.ACCOUNT_ID =  sales.PRIMARY_ACCOUNT_ID join [dbo].[ACCOUNT_PHONE_FAX] ff on ff.ACCOUNT_ID =  sales.PRIMARY_ACCOUNT_ID "
				+ " join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID"
				+ " join  ACCOUNT_STATUS accS on accS.id =account.ACCOUNT_STATUS_ID join dbo.claims b on sales.cert = b.cert where b.CLAIM_AMOUNT> 0 and b.STATUS='paid' and "
				+ " statuss.name = '" + status + "' order by sales.id desc;";
		System.out.println("query==" + query);
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09_03(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number, ff.Fax, ff.Phone, account.NAME as Secondary_Account, adr.City, ccount.email_address as Email, adr.ZIP_CODE as ZIP, "
				+ " adr.State, adr.STREET as Address, account.ROLE_IDENTIFIER as Secondary_Acct_Id, accS.Status as Secondary_Acct_Status from [dbo].[ALLSALES_DETAILS] sales "
				+ " join [dbo].[ACCOUNT] account on account.id =  sales.SECONDARY_ACCOUNT_ID join [dbo].[ACCOUNT_EMAIL] ccount on ccount.ACCOUNT_ID =  sales.SECONDARY_ACCOUNT_ID "
				+ " join  [dbo].[ACCOUNT_ADDRESS] adr on adr.ACCOUNT_ID =  sales.SECONDARY_ACCOUNT_ID join [dbo].[ACCOUNT_PHONE_FAX] ff on ff.ACCOUNT_ID =  sales.SECONDARY_ACCOUNT_ID "
				+ " join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID"
				+ " join  ACCOUNT_STATUS accS on accS.id =account.ACCOUNT_STATUS_ID join dbo.claims b on sales.cert = b.cert where b.CLAIM_AMOUNT> 0 and b.STATUS='paid' and "
				+ " statuss.name = '" + status + "' order by sales.id desc;";
		System.out.println("query==" + query);
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09_04(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number,sales.Phone, sales.City, sales.cust_email as Email, sales.ZIP, CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "as Name, sales.State, sales.Address from [dbo].[ALLSALES_DETAILS] sales join dbo.claims b on sales.cert = b.cert join [dbo].[UW_CONTRACT_STATUS] statuss on  "
				+ "statuss.id = sales.CONTRACT_STATUS_ID where b.CLAIM_AMOUNT> 0 and b.STATUS='paid' and  statuss.name = '"
				+ status + "' order by sales.id desc;";
		System.out.println("query==" + query);
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09_05(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number, vd.AUTOMAKE as Make, vd.AUTOMODEL as Model, vd.AUTOYEAR as Year, sales.AUTOMILES as Mileage, sales.VIN from [dbo].[ALLSALES_VIN_DETAIL] vd join "
				+ "	dbo.ALLSALES_DETAILS sales on sales.cert = vd.cert join dbo.claims b on sales.cert = b.cert join [dbo].[UW_CONTRACT_STATUS] statuss on  "
				+ "statuss.id = sales.CONTRACT_STATUS_ID where b.CLAIM_AMOUNT> 0 and b.STATUS='paid' and  statuss.name = '"
				+ status + "' order by sales.id desc;";
		System.out.println("query==" + query);
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This gets DataSetforSearch
	 * 
	 */
	public HashMap<String, String> cancellation_Search(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "";
		String query1 = "select top " + "1" + " ";
		String query2 = " from [dbo].[ALLSALES_DETAILS] where ";
		String myKey = "";
		String myvalue = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String key = (String) mapElement.getKey();
			String value = (String) mapElement.getValue();
			if (value.equals("*")) {
				myKey = myKey + key + ",";
				myvalue = myvalue + key + " is not null and ";
			} else if (value.length() < 1) {
				//// do nothing
			} else {
				myKey = myKey + key + ",";
				myvalue = myvalue + key + " = '" + value + "' and ";
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
	 * status
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatus(String status) throws Exception {
		String contract_id = "";
		String query = "select top 1 sale.id, cert as Contract from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' order by 1 desc;";
		///// execute query
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		System.out.println("query: " + query);
		contract_id = dbMap.get("Contract");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractHistoryBasedOnStatus(String status) throws Exception {
		HashMap<String, String> dbMap;
		String allStatus = "('Authorized','Calculated','Check Assigned','Denied','OnHold','Posted','Processed','Quote','Reinstated')";
		String subQuery = "";
		if (status.equalsIgnoreCase("Authorized")) {
			subQuery = allStatus.replace("'" + status + "',", "");
		} else {
			subQuery = allStatus.replace(",'" + status + "'", "");
		}
		String query = "select top 1 asd.cert as Contract,asd.CUSTOMER_FIRST,"
				+ "cs.Name as Status,cp.CREATED_DATE as Process_Date,cp.REFUND_PERCENTAGE,cp.NET_REFUND_AMOUNT as Net_Refund "
				+ ", cp.CANCEL_MILEAGE as Cancel_Miles, cp.CANCEL_DATE, cib.NAME as INITIATED_BY,crt.NAME as Cancel_Reason "
				+ "from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id = cp.ALLSALES_DETAILS_ID join "
				+ "[dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID join [dbo].[CANCELLATION_INITIATED_BY] cib "
				+ "on cib.id = cp.INITIATED_BY_ID left join [dbo].[CANCELLATION_REASON_TYPE] crt "
				+ "on crt.id = cp.REASON_TYPE_ID " + "where cs.name = '" + status.toLowerCase()
				+ "' and cert not in (select asd1.cert from ALLSALES_DETAILS asd1 "
				+ "join CANCELLATION_PARAMETERS cp1 on asd1.id = cp1.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs1 on cs1.id = cp1.STATUS_ID where "
				+ "cs1.name in " + subQuery + ") order by asd.id desc";
		System.out.println("query: " + query);
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractHistoryBasedOnStatusSingleRecord(String status)
			throws Exception {
		HashMap<String, String> dbMap;
		String query = "select top 1 asd.cert as Contract,asd.CUSTOMER_FIRST,cs.Name as Status,cp.CREATED_DATE as "
				+ "Process_Date,cp.REFUND_PERCENTAGE,cp.NET_REFUND_AMOUNT as Net_Refund ,"
				+ "cp.CANCEL_MILEAGE as Cancel_Miles, cp.CANCEL_DATE, cib.NAME as INITIATED_BY,crt.NAME as "
				+ "Cancel_Reason from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id = "
				+ "cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID "
				+ "join [dbo].[CANCELLATION_INITIATED_BY] cib on cib.id = cp.INITIATED_BY_ID left "
				+ "join [dbo].[CANCELLATION_REASON_TYPE] crt on crt.id = cp.REASON_TYPE_ID " + "where cs.name = '"
				+ status + "' and asd.cert in( "
				+ "select asd.cert from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id =  "
				+ "cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID "
				+ "group by asd.cert having count(*) = 1) order by asd.id desc";
		dbMap = getTopRowDataFromDatabase(query);

		return dbMap;
	}

	public HashMap<String, String> cancellation_Histpry(String contractId, String Status) throws Exception {
		String query = "select cp.*,s.* from  [dbo].[CANCELLATION_PARAMETERS] cp join  [dbo].[ALLSALES_DETAILS] asd on asd.id = cp.ALLSALES_DETAILS_ID "
				+ "join CANCELLATION_STATUS s on s.id = cp.STATUS_ID " + "where CERT = '" + contractId
				+ "' and s.NAME = '" + Status + "';";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status it will fetch contracts for current year only
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusAndPriceSheet(String status,
			String priceSheet) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = "select top 1 CERT,SALE_DATE from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' and PROGRAM_CODE = '"
				+ priceSheet + "' and sale_date like '%" + year
				+ "%' and sale.id not in (select ALLSALES_DETAILS_ID  from [dbo].[CANCELLATION_PARAMETERS]) order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status and RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnRoleId(String roleId, String sRoleId)
			throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
		int preYear = currYear - 1;
		String secRoleID = "";
		if (!sRoleId.equalsIgnoreCase("")) {
			secRoleID = " and aa.ROLE_IDENTIFIER =" + sRoleId + " ";
		}
		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER = '" + roleId + "' '" + secRoleID + "'"
				+ "and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW'"
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00  and SD.SALE_DATE  between '"
				+ preYear + "' and '" + currYear + "' order by  SD.SALE_DATE asc;";
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
		String query = "select CANCEL_FEE_AMOUNT,REFUND_PERCENTAGE from [dbo].[CANCELLATION_PARAMETERS]  where ALLSALES_DETAILS_ID in "
				+ "(select ID from [dbo].[ALLSALES_DETAILS] " + "where cert = '" + contractId + "')";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This gets search return Payee Details
	 * 
	 */
	public HashMap<String, String> cancellation_getPayeeDetails(String contractId) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select PAYEE_NAME,PAYEE_ADDRESS,PAYEE_CITY,PAYEE_STATE,PAYEE_ZIP_CODE from [dbo].[CANCELLATION_PARAMETERS]  where ALLSALES_DETAILS_ID in "
				+ "(select ID from [dbo].[ALLSALES_DETAILS] " + "where cert = '" + contractId + "') order by ID desc";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 08
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC08(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select top 1 sales.CERT as Contract_Number, claim_amount as Claims_Paid, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.automiles as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ "account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on "
				+ "accS.id =account.ACCOUNT_STATUS_ID join dbo.claims b on sales.cert = b.cert  where b.CLAIM_AMOUNT> 0 and b.STATUS='paid' and statuss.name = '"
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
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) AS customer_name,statuss.Name as contractStatus, sss.Name as contractSubStatus ,sales.COMMENTS "
				+ "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] account on account.id =  "
				+ "sales.PRIMARY_ACCOUNT_ID join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sss on sss.id = sales.[CONTRACT_SUBSTATUS_ID] "
				+ " left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ " where sales.CERT = '" + contractId + "';";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status to verify check comments
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatusToVerifyCheckComments(String status) throws Exception {
		String contract_id = "";
		String query = "select top 1 a.cert from [dbo].[ALLCANCEL_DETAILS] a join [dbo].[CANCELLATION_PARAMETERS] b"
				+ " on a.CANCELLATION_PARAMETER_ID= b.id join [dbo].[CANCELLATION_STATUS] cs on cs.id = b.STATUS_ID "
				+ "  where len(b.CHECK_COMMENTS) >0 and b.CHECK_COMMENTS is not null and len(b.CHECK_COMMENTS) >0 and b.CHECK_COMMENTS is not null "
				+ " and cs.NAME = '" + status + "' ";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("cert");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status to verify check comments
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusToVerifyCheckDetails(String status)
			throws Exception {

		String query = " select  top 1 asd.cert,cp.[CHECK_AMT],cp.[CHECK_DATE],cp.[CHECK_NUM] ,cht.TYPE ,chs.STATUS "
				+ " from [dbo].[ALLSALES_DETAILS] asd  "
				+ " join [dbo].[UW_CONTRACT_STATUS] uw on uw.id = asd.[CONTRACT_STATUS_ID] "
				+ " join [dbo].[CANCELLATION_PARAMETERS]cp on  cp.[ALLSALES_DETAILS_ID] = asd.id "
				+ " join [dbo].[CHECKS] on checks.[CERT] = asd.cert "
				+ " join [dbo].[CHECK_STATUS] chs on chs.Id = checks.[STATUS] "
				+ " join [dbo].[CHECK_TYPE] cht on cht.id = checks.[CHECK_TYPE] "
				+ " where uw.name = 'Active' and cp.[CHECK_AMT] is not null and cp.[CHECK_DATE] "
				+ " is not null and cp.[CHECK_NUM] is not null  ";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		String date1 = dbMap.get("CHECK_DATE").toString();
		date1 = convertNewDateFormat(date1);
		String CHECK_AMT = dbMap.get("CHECK_AMT");
		int index = CHECK_AMT.indexOf('.');
		CHECK_AMT = CHECK_AMT.substring(0, index + 3);
		dbMap.put("CHECK_DATE", date1);
		dbMap.put("CHECK_AMT", CHECK_AMT);

		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> cancellation_getCancellationMouduleSearchDataForCheckComments(String contract_Id)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = " select b.CHECK_COMMENTS from [dbo].[ALLCANCEL_DETAILS] a join [dbo].[CANCELLATION_PARAMETERS] b "
				+ " on a.CANCELLATION_PARAMETER_ID= b.id where len(b.CHECK_COMMENTS) >0 and b.CHECK_COMMENTS is not null "
				+ " and a.CERT ='" + contract_Id + "';";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	public HashMap<String, String> get_ContractDetailsForTC22(String role_type) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select * from   [dbo].[ACCOUNT_WARNING] w join ACCOUNT aa on aa.id = w.ACCOUNT_ID "
				+ " where account_id in(select account.id from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on"
				+ " account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID"
				+ " where warning_text not like '' and account.ACCOUNT_STATUS_ID = 1 and account.ROLE_TYPE_ID =  "
				+ role_type + ");";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search the count of all sales details status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractCount(String allSalesId) throws Exception {
		HashMap<String, String> dbMap;
		String query = "select count (*) as count from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id = cp.ALLSALES_DETAILS_ID "
				+ "where asd.ID = '" + allSalesId + "' ";
		System.out.println("query cancellation_getContractCount: " + query);
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This function gets internal comments based on contract id.
	 * 
	 */
	public HashMap<String, String> getCancelledContract_ID(String contract_ID) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 INTERNAL_COMMENTS from [dbo].[ALLCANCEL_DETAILS] a join [dbo].[CANCELLATION_PARAMETERS] b"
				+ "on a.CANCELLATION_PARAMETER_ID= b.id where	len(b.INTERNAL_COMMENTS) >0 and"
				+ " b.INTERNAL_COMMENTS is not null" + " and a.[cert] = '" + contract_ID + "';";
		// execute query
		dbMap = getTopRowDataFromDatabase(query);
		// comment = queryResult.getNString("");
		return dbMap;
	}

	/**
	 * This gets search contract id based on status and role_name for cancellation
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnRolenameAndStatus(String roleName, String status)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();

		String query = "SELECT	 top 1 AD.ID,AD.CERT,AD.SALE_DATE,"
				+ " PR.ID AS Account_ID,PR.ROLE_IDENTIFIER,CS.NAME as Staus "
				+ " FROM   ALLSALES_DETAILS	  AS AD WITH (NOLOCK) "
				+ " JOIN [UW_CONTRACT_STATUS]  AS CS  WITH (NOLOCK) ON AD.CONTRACT_STATUS_ID = CS.ID "
				+ " LEFT JOIN [ACCOUNT] AS PR WITH (NOLOCK) ON PR.ID = AD.PRIMARY_ACCOUNT_ID "
				+ " JOIN [ACCOUNT_ROLE_TYPE] AS P1 WITH (NOLOCK) ON P1.ID = PR.ROLE_TYPE_ID " + " WHERE  CS.NAME = '"
				+ status + "' and PR.ROLE_IDENTIFIER in "
				+ " (	SELECT distinct  A.ROLE_IDENTIFIER  FROM  dbo.ACCOUNT AS A "
				+ " INNER JOIN dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID AND R.ROLE_NAME = '" + roleName
				+ "' " + " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID AND T.NAME = 'Seller' "
				+ " JOIN  ACCOUNT_CANCELLATION ac ON ac.PRIMARY_ACCOUNT_ID = A.ID "
				+ " JOIN CANCELLATION_CATEGORY_RULE rr on rr.account_Cancellation_id = ac.id "
				+ " where  A.ROLE_IDENTIFIER <>24 ) order by PR.ROLE_IDENTIFIER asc,AD.SALE_DATE desc ";
		dbMap = getTopRowDataFromDatabase(query);

		return dbMap;

	}

	/**
	 * This query is used to get account Role Id without any account Rule Builder
	 * based on ProductName And State and return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> cancellation_getAccountRoleIdBasedOnProductCode(String roleType, String ProductName)
			throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String Query = "";
		if (!(ProductName == "")) {
			Query = "and SD.PROGRAM_CODE = '" + ProductName + "'";
		}
		int preyear = year - 2;
		String query = " SELECT distinct top 200 A.ROLE_IDENTIFIER,A.Name,R.ROLE_NAME,SD.[STATE],PRA.[MSTR_PRICESHEET_NAME] as Product_Name ,"
				+ " SD.PROGRAM_CODE from [dbo].[ALLSALES_DETAILS] SD  "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION PRA ON A.ID = PRA.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5 " + " " + Query
				+ " and SD.PRICESHEET_ID is not null and R.ROLE_NAME ='" + roleType + "' "
				+ " and A.ROLE_IDENTIFIER not in " + " (select distinct a.role_identifier "
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id  "
				+ " join CANCELLATION_CATEGORY_RULE rr on rr.account_Cancellation_id = ac.id ) "
				+ " and SD.[SALE_DATE] between '" + preyear + "' and '" + year + "'  ";
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		HashMap<String, String> myData = new HashMap<String, String>();
		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				String roleId = dbMap.get(number).get("ROLE_IDENTIFIER");
				String accountName = dbMap.get(number).get("Name");
				String state = dbMap.get(number).get("STATE");
				String productName = dbMap.get(number).get("Product_Name");
				myData.put("role_identifier", roleId);
				myData.put("accName", accountName);
				myData.put("state", state);
				myData.put("productName", productName);
				return myData;
			}

		}

		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractHistoryData(String allSalesId) throws Exception {
		HashMap<String, String> dbMap;
		String query = "select top 1 asd.cert as Contract,"
				+ "cs.Name as Status,cp.CREATED_DATE as Process_Date,cp.REFUND_PERCENTAGE,cp.NET_REFUND_AMOUNT as Net_Refund "
				+ ", cp.CANCEL_MILEAGE as Cancel_Miles, cp.CANCEL_DATE, cib.NAME as INITIATED_BY,crt.NAME as Cancel_Reason "
				+ "from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id = cp.ALLSALES_DETAILS_ID join "
				+ "[dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID join [dbo].[CANCELLATION_INITIATED_BY] cib "
				+ "on cib.id = cp.INITIATED_BY_ID left join [dbo].[CANCELLATION_REASON_TYPE] crt "
				+ "on crt.id = cp.REASON_TYPE_ID " + "where asd.ID = '" + allSalesId + "' order by asd.id desc";
		System.out.println("query cancellation_getContractHistoryData: " + query);
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all details of cancelled contract based on contract id
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractDataBasedOnContractID(String contractId) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();

		String query = " Select CANCELLATION_PARAMETER_ID from [dbo].[ALLCANCEL_DETAILS] " + " where cert = '"
				+ contractId + "' ";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		String cpID = dbMap.get("CANCELLATION_PARAMETER_ID");

		String query1 = "SELECT	AD.CERT,AD.SALE_DATE,CP.CANCEL_DATE,"
				+ " CRT.NAME as CANCEL_REASON,CP.CANCEL_MILEAGE,CP.CANCEL_FEE_AMOUNT AS CANCEL_FEE,"
				+ " CM.NAME AS CANCEL_METHOD,CP.NET_REFUND_AMOUNT AS 'NET_REFUND',CP.REFUND_PERCENTAGE AS 'REFUND_PCNT',"
				+ " CP.REFUND_AMOUNT AS 'AULREFUND',CS.NAME AS 'STATUS', "
				+ " CP.PAYEE_NAME,CP.PAYEE_ADDRESS,CP.PAYEE_CITY,CP.PAYEE_STATE,	"
				+ " CP.PAYEE_ZIP_CODE AS 'PAYEE_ZIP',CP.PAYEE_COUNTRY," + " CP.DEALER_REFUND,	CP.CUSTOMER_REFUND"
				+ "   FROM   CANCELLATION_PARAMETERS	AS CP WITH (NOLOCK)"
				+ " JOIN ALLSALES_DETAILS	AS AD WITH (NOLOCK ) ON AD.ID = CP.ALLSALES_DETAILS_ID"
				+ "   JOIN PRICING_PRICESHEET ON AD.PRICESHEET_ID = PRICING_PRICESHEET.ID"
				+ "   JOIN ALLCANCEL_DETAILS	AS ACD WITH (NOLOCK ) ON CP.ID = ACD.CANCELLATION_PARAMETER_ID"
				+ "  LEFT JOIN CANCELLATION_REASON_TYPE AS CRT WITH (NOLOCK) ON CP.REASON_TYPE_ID = CRT.ID"
				+ "   JOIN CANCELLATION_STATUS  AS CS  WITH (NOLOCK) ON CP.STATUS_ID = CS.ID"
				+ "   JOIN CANCELLATION_METHOD	 AS CM  WITH (NOLOCK) ON CM.ID = CP.METHOD_ID"
				+ "  LEFT JOIN [ACCOUNT] AS PR WITH (NOLOCK) ON PR.ID = AD.PRIMARY_ACCOUNT_ID      "
				+ "  JOIN [ACCOUNT_ROLE_TYPE] AS P1 WITH (NOLOCK) ON P1.ID = PR.ROLE_TYPE_ID"
				+ "	LEFT JOIN [ACCOUNT] AS SR WITH (NOLOCK) ON SR.ID = AD.SECONDARY_ACCOUNT_ID"
				+ "   LEFT JOIN [ACCOUNT_ROLE_TYPE] AS S1 WITH (NOLOCK) ON S1.ID = SR.ROLE_TYPE_ID"
				+ "   WHERE  CP.IS_DELETED = 0 and AD.IS_DELETED = 0 and CP.ID= '" + cpID + "'   ";

		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query1);
		myData = dbMap1;
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * effectiveDate and RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnEffectiveDateAndRoleId(String roleId,
			String effectiveDate, String ProgCode) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = 0;
		String Query = "";
		if (!(ProgCode == "")) {
			Query = "and SD.PROGRAM_CODE = '" + ProgCode + "'";
		}
		if (effectiveDate == "") {
			effectiveDate = String.valueOf(currYear);
			preYear = currYear - 1;
		} else {
			effectiveDate = effectiveDate.substring(0, 4);
			preYear = Integer.parseInt(effectiveDate) - 1;
		}

		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER ='" + roleId
				+ "' and SD.PRICESHEET_ID is not null " + Query + " "
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00  and SD.SALE_DATE " + " between '"
				+ preYear + "' and '" + effectiveDate + "' and YEAR(SD.EXPIRATION_DATE) >= '" + currYear
				+ "' order by  SD.SALE_DATE desc ;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status and RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusAndRoleId(String roleId, String roleType)
			throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 2;
		String query1 = "";

		if (roleType == "") {
		} else {
			query1 = "and R.[ROLE_NAME] ='" + roleType + "' ";
		}
		String query = "SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME  from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where  SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER = '" + roleId + " '  "
				+ " and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW'"
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00 " + query1
				+ " and SD.SALE_DATE  between '" + preYear + "' and ' " + currYear + " ' order by  SD.SALE_DATE asc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status and RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusAndRoleId(String roleId) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER = '" + roleId + "' "
				+ " and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW' "
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00  and SD.SALE_DATE  between '"
				+ preYear + "' and '" + currYear + "' order by  SD.SALE_DATE asc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/*
	 * to get the State display name based on cert and primary account name.
	 */
	public String compliance_StateDisplayNamebasedOnCertAndPAccount(String cert, String pAccount) throws Exception {
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

	@SuppressWarnings({ "unused" })
	public HashMap<String, String> cancellation_getAccountRoleIdWithAccountRuleBuilder(String roleType,
			String programCode) throws Exception {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String effectiveDate = null;
		String name = null;
		HashMap<String, String> dbRuleId = new HashMap<String, String>();

		String queryPart = null;
		if (programCode == "") {
			queryPart = "";
		} else {
			queryPart = "and SD.PROGRAM_CODE = '" + programCode + "'";
		}

		String query = " select distinct aa.role_identifier,ac.EFFECTIVE_DATE,aa.Name  from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id  "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id  "
				+ " join CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id "
				+ " join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id "
				+ " join dbo.Account aa on aa.id = ac.Primary_account_id"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = aa.ROLE_TYPE_ID "
				+ " Join [dbo].[ALLSALES_DETAILS] SD on aa.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ " where SD.PRICESHEET_ID is not null and R.ROLE_NAME ='" + roleType + "' and SD.PROGRAM_CODE = '"
				+ programCode + "' " + " and c.cancellation_category_group_id in( 4 ,1)	and aa.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =1 and   SD.CONTRACT_STATUS_ID =5 and SD.PRICESHEET_ID is not null "
				+ " and SD.SALE_DATE between '" + preYear + "' and '" + currYear + "') " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		int j = dbMap.size();
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				effectiveDate = dbMap.get(number).get("EFFECTIVE_DATE");
				effectiveDate = effectiveDate.substring(0, 10);
				name = dbMap.get(number).get("Name");
				System.out.println("roleid====" + roleId);
				dbRuleId.put("roleIdentifier", roleId);
				dbRuleId.put("EFFECTIVE_DATE", effectiveDate);
				dbRuleId.put("name", name);
				return dbRuleId;
			}
		}
		return dbRuleId;
	}

	/**
	 * This query is used to get all AccountlevelsRules Associated with given
	 * primaryRoleId and return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<Integer, HashMap<String, String>> dBValidationOfAccountLevelRules(String primaryRoleId, String pCode,
			String PrimaryRoleName) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id "
				+ " INNER JOIN ALLSALES_DETAILS SD on SD.PRICESHEET_ID =ac.PRICESHEET_ID "
				+ " where a.role_identifier =" + primaryRoleId + " and a.name ='" + PrimaryRoleName
				+ "' and SD.PROGRAM_CODE = '" + pCode + "')" + " order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {
				HashMap<String, String> finalData = new HashMap<String, String>();
				myData = dbMap.get(i);
				String rulegroup = myData.get("Rule_Group");
				String ruleUsed = myData.get("Rule_Used");
				finalData.put(rulegroup, ruleUsed);
				dbData.put(i, finalData);
			}
			return dbData;
		} else {

			String query2 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
					+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
					+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
					+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
					+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
					+ " on rr.account_Cancellation_id = ac.id " + " where a.role_identifier =" + primaryRoleId
					+ ") order by 1 desc" + ";";
			HashMap<String, String> myData1 = new HashMap<String, String>();
			HashMap<Integer, HashMap<String, String>> dbMap1 = getAllDataFromDatabase(query2);

			for (int i = 1; i <= dbMap1.size(); i++) {
				HashMap<String, String> finalData = new HashMap<String, String>();
				myData = dbMap1.get(i);
				String rulegroup = myData.get("Rule_Group");
				String ruleUsed = myData.get("Rule_Used");
				finalData.put(rulegroup, ruleUsed);
				dbData.put(i, finalData);
			}
			return dbData;
		}
	}

	/**
	 * This query is used to get account Secondary Role Id with account Rule Builder
	 * and return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> cancellation_getSecondaryAccountRoleIdWithAccountRuleBuilderTC14(String roletype)
			throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String effectiveDate = null;
		HashMap<String, String> dbRuleId = new HashMap<String, String>();
		String query = " select distinct aa.role_identifier,ac.EFFECTIVE_DATE from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " join CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id "
				+ " join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id "
				+ " join dbo.Account aa on aa.id = ac.SECONDARY_ACCOUNT_ID where  "
				+ " c.cancellation_category_group_id in( 4 ,1)	and aa.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.SECONDARY_ACCOUNT_ID "
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " where A.ACCOUNT_STATUS_ID =1 and   SD.CONTRACT_STATUS_ID =5  and SD.PROGRAM_CODE <>'OLW' "
				+ " and R.ROLE_NAME ='" + roletype + "' and ac.EFFECTIVE_DATE > '" + preYear
				+ "' and SD.SALE_DATE between '" + preYear + "' and '" + currYear + "') " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				effectiveDate = dbMap.get(number).get("EFFECTIVE_DATE");
				effectiveDate = effectiveDate.substring(0, 10);
				System.out.println("roleid====" + roleId);
				dbRuleId.put("roleIdentifier", roleId);
				dbRuleId.put("EFFECTIVE_DATE", effectiveDate);
				return dbRuleId;
			}
		}
		return dbRuleId;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * effectiveDate and Secondary RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnSecondaryRoleId(String roleId) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String query = " Select distinct s.cert as CERT,s.SALE_DATE as Sale_Date,"
				+ " a.role_identifier as Primary_Seller_Id,a.name as Primary_Seller_Name, accType.role_name as Primary_Seller_Type , "
				+ " aa.role_identifier as Secondary_Seller_ID, aa.name as Secondary_Seller_Name, accType3.role_name as Secondary_Seller_Type "
				+ " from [dbo].[ALLSALES_DETAILS] s WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
				+ " ON a.ID = s.PRIMARY_ACCOUNT_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
				+ " left join [ACCOUNT] AS aa WITH (NOLOCK) ON aa.ID = s.SECONDARY_ACCOUNT_ID "
				+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id "
				+ " where s.SECONDARY_ACCOUNT_ID  is not null and aa.role_identifier = '" + roleId + "' and "
				+ " a.ACCOUNT_STATUS_ID =1 and aa.ACCOUNT_STATUS_ID =1 and s.CONTRACT_STATUS_ID =5 and s.PROGRAM_CODE <>'OLW' "
				+ " and  s.Customer_Paid is not null and s.Customer_Paid <>0.00 and a.role_identifier not in (select distinct a.role_identifier "
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id  "
				+ " join CANCELLATION_CATEGORY_RULE rr on rr.account_Cancellation_id = ac.id) "
				+ " and Sale_Date between '" + preYear + "' and '" + currYear + "' " + ";";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * RoleId/RoleType For TC-28 it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdTC_28(String roleId, String roleType) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 2;
		String query1 = "";
		if (roleType == "") {
		} else {
			query1 = "R.[ROLE_NAME] ='" + roleType + "' ";
		}
		String query = "SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME  from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " where  SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER ='" + roleId
				+ "' and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW'"
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00 and " + query1
				+ " and SD.SALE_DATE > " + " '" + preYear + "' order by  SD.SALE_DATE asc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This query is used to get account Role Id with given Classification Type and
	 * Role Type and return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> cancellation_getAccountRoleIdWithGivenParameterTC_28(String classiType,
			String roleType) throws Exception {

		String query = " SELECT APG.[ACCOUNT_ID], ART.[ID] AS 'ROLE_TYPE_ID', AP.[NAME],A.[ROLE_IDENTIFIER],A.Name as AccName ,ART.[ROLE_NAME], "
				+ " ISNULL(CONVERT(NVARCHAR,APV.[STRING_VALUE]),(ISNULL(CONVERT(NVARCHAR,APV.[NUMERIC_VALUE]),"
				+ " (ISNULL(CONVERT(NVARCHAR,APV.[DATE_VALUE]),(ISNULL(CONVERT(NVARCHAR,APV.[DATETIME_VALUE]),"
				+ " (ISNULL(APV.[RANGE_FROM_VALUE],(ISNULL(APV.[RANGE_TO_VALUE], APV.[BOOLEAN_VALUE])))))))))))  AS 'VALUE' "
				+ " FROM [ACCOUNT_PROPERTY_GROUP] AS APG WITH (NOLOCK) JOIN [ACCOUNT_PROPERTY_VALUE] AS APV WITH (NOLOCK) "
				+ " ON APV.ID = APG.PROPERTY_VALUE_ID JOIN [ACCOUNT_PROPERTY] AS AP WITH (NOLOCK) "
				+ " ON AP.ID = APV.ACCOUNT_PROPERTY_ID JOIN [ACCOUNT_ROLE_TYPE] AS ART WITH (NOLOCK) "
				+ " ON ART.[ID] = AP.[ROLE_TYPE_ID] and ap.name ='Classification' "
				+ " join Account  A on A.ID = APG.[ACCOUNT_ID] "
				+ " where ISNULL(CONVERT(NVARCHAR,APV.[STRING_VALUE]),(ISNULL(CONVERT(NVARCHAR,APV.[NUMERIC_VALUE]),"
				+ " (ISNULL(CONVERT(NVARCHAR,APV.[DATE_VALUE]),(ISNULL(CONVERT(NVARCHAR,APV.[DATETIME_VALUE]), "
				+ " (ISNULL(APV.[RANGE_FROM_VALUE],(ISNULL(APV.[RANGE_TO_VALUE], APV.[BOOLEAN_VALUE]))))))))))) = '"
				+ classiType + "' "
				+ " and APG.[ACCOUNT_ID] in ( select A.[ID] from [dbo].[ACCOUNT] A join [dbo].[ACCOUNT_ROLE_TYPE] Accrole "
				+ " on Accrole.[ACCOUNT_TYPE_ID] = A.[ACCOUNT_TYPE_ID]  "
				+ " join [dbo].[ALLSALES_DETAILS] s on s.[PRIMARY_ACCOUNT_ID] =A.ID and " + " Accrole.[ROLE_NAME] ='"
				+ roleType + "' and A.[ACCOUNT_STATUS_ID]= 1 and s.[PRICESHEET_ID] is not null and"
				+ " s.PROGRAM_CODE not in ('OLW','LWA') and s.sale_Date >'2018' and s.CONTRACT_STATUS_ID =5) "
				+ " and A.[ROLE_IDENTIFIER]<>30  order by A.[ROLE_IDENTIFIER] desc " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		for (int i = 1; i < dbMap.size(); i++) {
			HashMap<String, String> myData = new HashMap<String, String>();
			String PrimaryRoleID = dbMap.get(i).get("ROLE_IDENTIFIER");
			String AccID = dbMap.get(i).get("ACCOUNT_ID");
			myData.put("PrimaryRoleID", dbMap.get(i).get("ROLE_IDENTIFIER"));
			myData.put("AccID", AccID);
			myData.put("AccName", dbMap.get(i).get("AccName"));
			myData.put("ROLE_NAME", dbMap.get(i).get("ROLE_NAME"));

			return myData;
		}
		return null;
	}

	/**
	 * This query is used to get account Role Id without any account Rule Builder
	 * and zero contract and return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> cancellation_getAccountRoleIdWithNoContract(String roleType) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = " Select  distinct a.[ROLE_IDENTIFIER] ,a.Name ,par.[PRICESHEET_ID] ,[MSTR_PRICESHEET_NAME],"
				+ " actt.ROLE_NAME from " + " [dbo].[ACCOUNT] a join [dbo].[ACCOUNT_ROLE_TYPE] actt on "
				+ " actt.ACCOUNT_TYPE_ID = a.[ROLE_TYPE_ID] " + " join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par "
				+ " on par.[PRIMARY_SELLER_ID] = a.id "
				+ " where a.[ROLE_IDENTIFIER] not in (select a.[ROLE_IDENTIFIER]  from "
				+ " [dbo].[ACCOUNT] a inner join [dbo].[ALLSALES_DETAILS] s on "
				+ " s.PRIMARY_ACCOUNT_ID = a.ID ) and a.ACCOUNT_STATUS_ID =1 and par.[PRICESHEET_ID] is not null"
				+ " and actt.ROLE_NAME ='" + roleType + "'" + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		for (int i = 1; i < dbMap.size(); i++) {
			HashMap<String, String> myData = new HashMap<String, String>();
			String roleidentifier = dbMap.get(i).get("ROLE_IDENTIFIER");
			String Name = dbMap.get(i).get("Name");
			String ROLE_NAME = dbMap.get(i).get("ROLE_NAME");
			String CERT = dbMap.get(i).get("CERT");
			String priceSheetID = dbMap.get(i).get("PRICESHEET_ID");
			myData.put("PrimaryRoleID", roleidentifier);
			myData.put("PrimaryAccountName", Name);
			myData.put("RoleType", ROLE_NAME);
			myData.put("PRICESHEET_ID", dbMap.get(i).get("PRICESHEET_ID"));
			myData.put("MSTR_PRICESHEET_NAME", dbMap.get(i).get("MSTR_PRICESHEET_NAME"));

			String query1 = " select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on" + " rr.account_Cancellation_id = ac.id"
					+ " INNER JOIN [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par on par.[PRIMARY_SELLER_ID] =a.ID "
					+ " where a.role_identifier = " + roleidentifier + " and par.PRICESHEET_ID = " + priceSheetID + "  "
					+ ";";
			HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query1);
			if (dbMap1.size() == 0) {
				return myData;
			}
		}
		return null;
	}

	/**
	 * This query is used to get all AccountlevelsRules Associated with given
	 * primaryRoleId and return hashMap
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> dBValidationOfAddAccountLevelRules(String primaryRoleId,
			String primaryRoleName) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id " + " where a.role_identifier =" + primaryRoleId
				+ " and a.name ='" + primaryRoleName + "' ) order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		for (int i = 1; i <= dbMap.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData = dbMap.get(i);
			String rulegroup = myData.get("Rule_Group");
			String ruleUsed = myData.get("Rule_Used");
			finalData.put(rulegroup, ruleUsed);
			dbData.put(i, finalData);
		}
		return dbData;
	}

	/**
	 * This query is used to get account Role Id without any account Rule Builder
	 * and Single contract and return hashMap
	 * 
	 */
	public HashMap<String, String> cancellation_getAccountRoleIdWithSingleContract(String roleType) throws Exception {
		String query = "Select  distinct a.[ROLE_IDENTIFIER] ,a.Name ,s.PROGRAM_CODE,s.cert,s.PRICESHEET_ID, "
				+ " actt.ROLE_NAME from [dbo].[ACCOUNT] a join [dbo].[ACCOUNT_ROLE_TYPE] actt on   "
				+ " actt.ACCOUNT_TYPE_ID = a.[ROLE_TYPE_ID] INNER JOIN  ALLSALES_DETAILS  s"
				+ " ON a.ID = s.PRIMARY_ACCOUNT_ID where  actt.Role_Name ='Dealer' and s.[CONTRACT_STATUS_ID] = 5 "
				+ " and s.PRICESHEET_ID is not null and a.[ROLE_IDENTIFIER]  in ( "
				+ " Select a.[ROLE_IDENTIFIER] from [dbo].[ACCOUNT] a "
				+ " INNER JOIN  ALLSALES_DETAILS  s ON a.ID = s.PRIMARY_ACCOUNT_ID"
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] actt on  " + " actt.ACCOUNT_TYPE_ID = a.[ROLE_TYPE_ID]  "
				+ " where actt.Role_Name ='Dealer' and s.PRICESHEET_ID is not null and a.ACCOUNT_STATUS_ID =1 "
				+ " group by a.[ROLE_IDENTIFIER] Having count(s.cert) =1) " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		for (int i = 1; i < dbMap.size(); i++) {
			HashMap<String, String> myData = new HashMap<String, String>();
			String roleidentifier = dbMap.get(i).get("ROLE_IDENTIFIER");
			String Name = dbMap.get(i).get("Name");
			String ROLE_NAME = dbMap.get(i).get("ROLE_NAME");
			String priceSheetID = dbMap.get(i).get("PRICESHEET_ID");
			myData.put("PrimaryRoleID", roleidentifier);
			myData.put("PrimaryAccountName", Name);
			myData.put("RoleType", ROLE_NAME);
			myData.put("PRICESHEET_ID", dbMap.get(i).get("PRICESHEET_ID"));
			myData.put("PROGRAM_CODE", dbMap.get(i).get("PROGRAM_CODE"));

			String query1 = " select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on" + " rr.account_Cancellation_id = ac.id"
					+ " INNER JOIN [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par on par.[PRIMARY_SELLER_ID] =a.ID "
					+ " where a.role_identifier = " + roleidentifier + " and par.PRICESHEET_ID = " + priceSheetID + "  "
					+ ";";
			HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query1);
			if (dbMap1.size() == 0) {
				return myData;
			}
		}
		return null;
	}

	/**
	 * This query is used to get account Role Id without any account Rule Builder
	 * and Single contract and return hashMap
	 * 
	 */
	public HashMap<String, String> cancellation_getAccountRoleIdWithMultipleStatusAndProgCode(String roleType)
			throws Exception {

		String query = " select Role_Identifier , count(Role_Identifier) as 'Count-Role' from ("
				+ " select distinct  a.[ROLE_IDENTIFIER],a.Name ,s.PROGRAM_CODE ,s.CONTRACT_STATUS_ID "
				+ " from [dbo].[ACCOUNT] a join [dbo].[ACCOUNT_ROLE_TYPE] actt on "
				+ " actt.ACCOUNT_TYPE_ID = a.[ROLE_TYPE_ID] INNER JOIN  ALLSALES_DETAILS  s "
				+ " ON a.ID = s.PRIMARY_ACCOUNT_ID  where  actt.Role_Name = '" + roleType + "' "
				+ " and s.PRICESHEET_ID is not null and a.[ROLE_IDENTIFIER]  in ( "
				+ " select a.[ROLE_IDENTIFIER]  from [dbo].[ACCOUNT] a "
				+ " INNER JOIN  ALLSALES_DETAILS  s ON a.ID = s.PRIMARY_ACCOUNT_ID "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] actt on actt.ACCOUNT_TYPE_ID = a.[ROLE_TYPE_ID] "
				+ " where  a.ACCOUNT_STATUS_ID =1 group by a.[ROLE_IDENTIFIER] Having count(s.cert) >5) "
				+ " group by a.[ROLE_IDENTIFIER] ,s.PROGRAM_CODE ,a.Name ,s.CONTRACT_STATUS_ID "
				+ " Having count(a.[ROLE_IDENTIFIER]) >3 ) FinalView "
				+ " group by Role_Identifier having count(Role_Identifier) > =2 " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		for (int i = 1; i < dbMap.size(); i++) {
			String roleidentifier = dbMap.get(i).get("Role_Identifier");
			String query1 = "Select  distinct a.[ROLE_IDENTIFIER] ,a.Name ,s.[PRICESHEET_ID] ,actt.ROLE_NAME,s.[PROGRAM_CODE] from "
					+ " [dbo].[ACCOUNT] a join [dbo].[ACCOUNT_ROLE_TYPE] actt on "
					+ "	actt.ACCOUNT_TYPE_ID = a.[ROLE_TYPE_ID] "
					+ "	INNER JOIN  ALLSALES_DETAILS  s ON a.ID = s.PRIMARY_ACCOUNT_ID "
					+ "	where s.[PRICESHEET_ID] is not null and actt.Role_Name = '" + roleType
					+ "' and a.[ROLE_IDENTIFIER] = '" + roleidentifier + "' " + ";";

			HashMap<Integer, HashMap<String, String>> dbMap1 = getAllDataFromDatabase(query1);
			for (int j = 1; j < dbMap1.size(); j++) {
				HashMap<String, String> myData = new HashMap<String, String>();
				String priceSheetID = dbMap1.get(i).get("PRICESHEET_ID");
				String query2 = " select rr.rule_category_applied_rule_id "
						+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
						+ "join CANCELLATION_CATEGORY_RULE rr on" + " rr.account_Cancellation_id = ac.id"
						+ " INNER JOIN [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par on par.[PRIMARY_SELLER_ID] =a.ID "
						+ " where a.role_identifier = " + roleidentifier + " and par.PRICESHEET_ID = " + priceSheetID
						+ "  " + ";";
				HashMap<String, String> dbMap2 = getTopRowDataFromDatabase(query2);
				if (dbMap2.size() == 0) {
					myData.put("PrimaryRoleID", roleidentifier);
					myData.put("PrimaryAccountName", dbMap1.get(i).get("Name"));
					myData.put("RoleType", dbMap1.get(i).get("ROLE_NAME"));
					myData.put("PRICESHEET_ID", priceSheetID);
					myData.put("PROGRAM_CODE", dbMap1.get(i).get("PROGRAM_CODE"));
					return myData;
				}
			}
		}
		return null;
	}

	/**
	 * This gets Active Contract Number based on RoleID and ProgCode
	 */

	public HashMap<String, String> cancellation_getContractIdBasedOnRoleIdAndProgCode(String roleId, String ProCode,
			String sRoleId) throws Exception {
		String secRoleID = "";
		if (!sRoleId.equalsIgnoreCase("")) {
			secRoleID = " and aa.ROLE_IDENTIFIER =" + sRoleId + " ";
		}
		HashMap<String, String> myData = new HashMap<String, String>();

		String query = "SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]  left join [ACCOUNT]  aa  ON aa.ID = SD.[SECONDARY_ACCOUNT_ID] "
				+ "	where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5   "
				+ " and SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER =" + roleId + " " + " " + secRoleID
				+ "and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE ='" + ProCode + "'  "
				+ " and  SD.Customer_Paid is not null  order by  SD.SALE_DATE desc";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This query is used to get all AccountlevelsRules Associated with given
	 * primaryRoleId with zero Contract and return hashMap
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> dBValidationOfAddAccountLevelRulesWithoutContract(
			String primaryRoleId, String priceSheetId) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();
		System.out.print("Inside Datavalidation");
		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id "
				+ " INNER JOIN [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par on par.[PRIMARY_SELLER_ID] =a.ID  "
				+ " where a.role_identifier =" + primaryRoleId + " and par.PRICESHEET_ID =" + priceSheetId
				+ ") order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		for (int i = 1; i <= dbMap.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData = dbMap.get(i);
			String rulegroup = myData.get("Rule_Group");
			String ruleUsed = myData.get("Rule_Used");
			finalData.put(rulegroup, ruleUsed);
			dbData.put(i, finalData);
		}
		System.out.print(dbData);
		return dbData;

	}

	/**
	 * This query is used to get account Role Id without any account Rule Builder
	 * and return hashMap
	 * 
	 */
	public String cancellation_getAccountRoleIdPriceSheet(String priceSheetID) throws Exception {

		String query = " select pp.[INTERNAL_NAME] from [dbo].[PRICING_PRICESHEET] pp "
				+ " join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par on " + " pp.[ID] = par.[PRICESHEET_ID] "
				+ " where par.[PRICESHEET_ID] = " + priceSheetID + "" + ";";

		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		String PricesheetName = dbMap1.get("INTERNAL_NAME");
		return PricesheetName;
	}

	/**
	 * This query is used to get account Role Id with account Rule Builder and
	 * return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public String cancellation_getAccountRoleInactiveIdWithAccountRuleBuilder2(String roleType, int status)
			throws Exception {
		String roleId = null;
		String query = "select  rr.rule_category_applied_rule_id ,a.role_identifier"
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
				+ " join CANCELLATION_CATEGORY_RULE rr on "
				+ " rr.account_Cancellation_id = ac.id where a.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =" + status + " and   SD.CONTRACT_STATUS_ID =5  and "
				+ " SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null " + " and R.ROLE_NAME ='" + roleType
				+ "') " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size());
				roleId = dbMap.get(number).get("role_identifier");
				System.out.println("roleid====" + roleId);

				return roleId;
			}
		}
		return roleId;
	}

	/*
	 * to get the pricesheet group name based on cert and primary account name for
	 * Compliance Module.
	 */
	public String compliance_PricesheetIdbasedOnCert(String cert) throws Exception {
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

	/**
	 * This query is used to get account Role Id without any account Rule Builder
	 * and return hashMap
	 * 
	 */
	public HashMap<String, String> cancellation_getAccountRoleIdBasedOnRoleType(String roleType, int status)
			throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = " SELECT distinct  A.ROLE_IDENTIFIER,A.Name,R.ROLE_NAME" + " from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =" + status + " and   SD.CONTRACT_STATUS_ID =5  and "
				+ " SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null "
				+ " and R.ROLE_NAME ='Dealer' and SD.SALE_DATE like '%" + year + "%'" + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		for (int i = 1; i < dbMap.size(); i++) {
			HashMap<String, String> myData = new HashMap<String, String>();
			String roleidentifier = dbMap.get(i).get("ROLE_IDENTIFIER");
			String accountName = dbMap.get(i).get("Name");
			String ROLE_NAME = dbMap.get(i).get("ROLE_NAME");
			@SuppressWarnings("unused")
			String CERT = dbMap.get(i).get("CERT");
			myData.put("PrimaryRoleID", roleidentifier);
			myData.put("PrimaryAccountName", accountName);
			myData.put("PrimaryRoleType", ROLE_NAME);

			String query1 = " select rr.rule_category_applied_rule_id "
					+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
					+ "join CANCELLATION_CATEGORY_RULE rr on"
					+ " rr.account_Cancellation_id = ac.id where a.role_identifier = " + roleidentifier + "" + ";";
			HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query1);
			if (dbMap1.size() == 0) {
				return myData;
			} else {
			}
		}
		return null;
	}

	/**
	 * to get the contract number on the basis of Status and Substatus
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdforStatus(String status, String substatus)
			throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = df.format(Calendar.getInstance().getTime());
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 2;
		String queryPart = "";
		String queryPart1 = "";
		String queryPart2 = "";
		if (status.equals("Cancelled")) {
			queryPart2 = "and SD.ID in (select ALLSALES_DETAILS_ID from CANCELLATION_PARAMETERS group by ALLSALES_DETAILS_ID having count(*) = 1) "
					+ "and SD.ID in (select ALLSALES_DETAILS_ID from [dbo].[ALLCANCEL_TRANS_DETAILS ] group by ALLSALES_DETAILS_ID having count(*) = 1) ";
		} else if (substatus.equalsIgnoreCase("Reinstated")) {
			queryPart = "join [dbo].[UW_CONTRACT_SUBSTATUS] SSTA On SSTA.[ID] = SD.[CONTRACT_SUBSTATUS_ID] ";
			queryPart1 = "and SSTA.NAME = '" + substatus + "' ";
			queryPart2 = "and SD.ID in (select ALLSALES_DETAILS_ID from CANCELLATION_PARAMETERS group by ALLSALES_DETAILS_ID having count(*) = 1) "
					+ "and SD.ID in (select ALLSALES_DETAILS_ID from CANCELLATION_PARAMETERS where STATUS_ID = 1) ";
		} else {
			queryPart2 = "and SD.ID not in (select ALLSALES_DETAILS_ID from CANCELLATION_PARAMETERS) ";
		}
		String query = "SELECT top 1 SD.CERT as Contract,SD.SALE_DATE,SD.ID from [dbo].[ALLSALES_DETAILS] SD "
				+ "join [dbo].[UW_CONTRACT_STATUS] STA On STA.[ID] = SD.[CONTRACT_STATUS_ID] " + queryPart
				+ "where STA.NAME = '" + status + "' " + queryPart1
				+ "and SD.Customer_Paid is not null and SD.Customer_Paid <> 0.00 and SD.PROGRAM_CODE NOT LIKE '%LW' "
				+ queryPart2 + "and SD.SALE_DATE " + " between '" + preYear + "' and '" + currYear + "'"
				+ " and SD.EXPIRATION_DATE > '" + currDate + "' order by SD.SALE_DATE asc;";
		System.out.println("query cancellation_getContractIdforStatus: " + query);
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * to get the contract status on the basis contractID
	 * 
	 */
	public String cancellation_getContractStatus(String ContractID) throws Exception {
		String contractStatus = "";
		String query = "SELECT STA.NAME AS CONTRACTSTATUS from [dbo].[ALLSALES_DETAILS] SD "
				+ "join [dbo].[UW_CONTRACT_STATUS] STA On STA.[ID] = SD.[CONTRACT_STATUS_ID] " + "where SD.CERT = '"
				+ ContractID + "'";
		System.out.println("query cancellation_getContractStatus: " + query);
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contractStatus = dbMap.get("CONTRACTSTATUS");
		return contractStatus;
	}

	@SuppressWarnings("unused")
	public HashMap<String, String> cancellation_getAccountRoleIdWithAccountRuleBuilder(String roleType, int status)
			throws Exception {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String effDate = null;
		String name = null;
		String secName = null;
		String secRoleId = null;
		String priceSheetId = null;
		HashMap<String, String> dbResult = new HashMap<String, String>();
		String query = "select  distinct a.role_identifier ,a.name ,ac.EFFECTIVE_DATE, aa.role_identifier as secRoleId ,aa.name as secName,ac.PRICESHEET_ID"
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
				+ " join CANCELLATION_CATEGORY_RULE rr on "
				+ " rr.account_Cancellation_id = ac.id INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " left join dbo.[ACCOUNT] aa on aa.id = ac.[SECONDARY_ACCOUNT_ID]" + " where a.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " left join [ACCOUNT] AS aa WITH (NOLOCK) ON aa.ID = SD.[SECONDARY_ACCOUNT_ID] "
				+ " where A.ACCOUNT_STATUS_ID =" + status + " and   SD.CONTRACT_STATUS_ID =5  and "
				+ " SD.PROGRAM_CODE not in ('OLW','LWA','OCW','RLW','RAW') and SD.PRICESHEET_ID is not null"
				+ " and SD.SALE_DATE between  '" + preYear + "'  and '" + currYear + "') and R.ROLE_NAME ='" + roleType
				+ "' " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				effDate = dbMap.get(number).get("EFFECTIVE_DATE");
				name = dbMap.get(number).get("name");
				effDate = effDate.substring(0, 10);
				secName = dbMap.get(number).get("secName");
				secRoleId = dbMap.get(number).get("secRoleId");
				priceSheetId = dbMap.get(number).get("PRICESHEET_ID");
				System.out.println("roleid====" + roleId);
				dbResult.put("roleIdentifier", roleId);
				dbResult.put("effectiveDate", effDate);
				dbResult.put("name", name);
				dbResult.put("secRoleId", secRoleId);
				dbResult.put("secName", secName);
				dbResult.put("priceSheetId", priceSheetId);
				return dbResult;
			}
		}
		return dbResult;
	}

	/*
	 * /**
	 * 
	 * /** This query is used to get account Role Id with account Rule Builder and
	 * return hashMap
	 * 
	 */
	/*
	 * public HashMap<String, String>
	 * cancellation_getAccountRoleIdWithAccountRuleBuilder(String roleType) throws
	 * Exception { int year = Calendar.getInstance().get(Calendar.YEAR); String
	 * query = " SELECT distinct  A.ROLE_IDENTIFIER,A.Name,R.ROLE_NAME" +
	 * " from [dbo].[ALLSALES_DETAILS] SD " +
	 * " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]" +
	 * " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  " +
	 * " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  " +
	 * " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
	 * + " where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5  and " +
	 * " SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null " +
	 * " and R.ROLE_NAME ='Dealer' and  SD.SALE_DATE like '%"+ year +"%'" + ";";
	 * 
	 * HashMap<Integer, HashMap<String, String>> dbMap =
	 * getAllDataFromDatabase(query); for(int i =1 ;i<dbMap.size() ;i++) {
	 * HashMap<String, String> myData = new HashMap<String, String>(); String
	 * roleidentifier = dbMap.get(i).get("ROLE_IDENTIFIER"); String accountName =
	 * dbMap.get(i).get("Name"); String ROLE_NAME = dbMap.get(i).get("ROLE_NAME");
	 * myData.put("PrimaryRoleID",roleidentifier ); myData.put("PrimaryRoleName",
	 * accountName); myData.put("PrimaryRoleType",ROLE_NAME ); String query1 =
	 * " select rr.rule_category_applied_rule_id " +
	 * " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
	 * + "join CANCELLATION_CATEGORY_RULE rr on" +
	 * " rr.account_Cancellation_id = ac.id where a.role_identifier = "
	 * +roleidentifier +"" + ";"; HashMap<String, String> dbMap1 =
	 * getTopRowDataFromDatabase(query1); if(dbMap1.size()>0) { return myData; } }
	 * return null; }
	 */

	/**
	 * This query is used to get all AccountlevelsRules Associated with given
	 * primaryRoleId and return hashMap
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> dBValidationOfAddAccountLevelRules(String primaryRoleId)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id "
				+ " INNER JOIN ALLSALES_DETAILS SD on SD.PRICESHEET_ID =ac.PRICESHEET_ID "
				+ " where a.role_identifier =" + primaryRoleId + " ) order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		for (int i = 1; i <= dbMap.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData = dbMap.get(i);
			String rulegroup = myData.get("Rule_Group");
			String ruleUsed = myData.get("Rule_Used");
			finalData.put(rulegroup, ruleUsed);
			dbData.put(i, finalData);
		}
		return dbData;
	}

	/**
	 * This query is used to get account Role Id with account Rule Builder and
	 * return hashMap
	 * 
	 */
	public HashMap<String, String> cancellation_getAccountRoleInactiveIdWithAccountRuleBuilder4(String roleType,
			int status) throws Exception {
		String roleId = null;
		String name = null;
		HashMap<String, String> mapDB = new HashMap<String, String>();
		String query = "select  rr.rule_category_applied_rule_id ,a.role_identifier,a.name"
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
				+ " join CANCELLATION_CATEGORY_RULE rr on "
				+ " rr.account_Cancellation_id = ac.id where a.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =" + status + " and   SD.CONTRACT_STATUS_ID =5  and "
				+ " SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null " + " and R.ROLE_NAME ='" + roleType
				+ "') " + ";";
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				name = dbMap.get(number).get("name");
				mapDB.put("roleId", roleId);
				mapDB.put("name", name);
				System.out.println("roleid====" + roleId);
				System.out.println("name====" + name);
			}
		}
		return mapDB;
	}

	/**
	 * This query is used to get account Role Id with account Rule Builder and
	 * return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public String cancellation_getAccountRoleInactiveIdWithAccountRuleBuilder(String roleType, int status)
			throws Exception {
		String roleId = null;
		String query = "select  rr.rule_category_applied_rule_id ,a.role_identifier"
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
				+ " join CANCELLATION_CATEGORY_RULE rr on "
				+ " rr.account_Cancellation_id = ac.id where a.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =" + status + " and   SD.CONTRACT_STATUS_ID =5  and "
				+ " SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null " + " and R.ROLE_NAME ='" + roleType
				+ "') " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				System.out.println("roleid====" + roleId);

				return roleId;
			}
		}
		return roleId;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status and RoleId it will fetch contracts for Inactive
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusInactiveRoleId(String roleId)
			throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		@SuppressWarnings("unused")
		int preYear = currYear - 1;
		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER =" + roleId
				+ " and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW' "
				+ " and  SD.Customer_Paid is not null  " + " order by  SD.SALE_DATE desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	@SuppressWarnings("unused")
	public HashMap<Integer, HashMap<String, String>> dBValidationOfAccountLevelRules(String primaryRoleId, String pCode)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id "
				+ " INNER JOIN ALLSALES_DETAILS SD on SD.PRICESHEET_ID =ac.PRICESHEET_ID "
				+ " where a.role_identifier =" + primaryRoleId + " and SD.PROGRAM_CODE = '" + pCode
				+ "') order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {
				HashMap<String, String> finalData = new HashMap<String, String>();
				myData = dbMap.get(i);
				String rulegroup = myData.get("Rule_Group");
				String ruleUsed = myData.get("Rule_Used");
				finalData.put(rulegroup, ruleUsed);
				dbData.put(i, finalData);
			}
			return dbData;
		} else {

			String query2 = "select b.name as Rule_Used ,c.name as Rule_Group, from RULE_CATEGORY_APPLIED_RULE a "
					+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
					+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
					+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
					+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
					+ " on rr.account_Cancellation_id = ac.id " + " where a.role_identifier =" + primaryRoleId
					+ ") order by 1 desc" + ";";
			HashMap<String, String> myData1 = new HashMap<String, String>();
			HashMap<Integer, HashMap<String, String>> dbMap1 = getAllDataFromDatabase(query1);

			for (int i = 1; i <= dbMap1.size(); i++) {
				HashMap<String, String> finalData = new HashMap<String, String>();
				myData1 = dbMap1.get(i);
				String rulegroup = myData1.get("Rule_Group");
				String ruleUsed = myData1.get("Rule_Used");
				finalData.put(rulegroup, ruleUsed);
				dbData.put(i, finalData);
			}
			return dbData;
		}
	}

	/**
	 * This query is used to get account Role Id with account Rule Builder and
	 * return hashMap
	 * 
	 */
	@SuppressWarnings("unused")
	public String cancellation_getContractIdBasedOnStatusAndRoleId(String roleType, int status) throws Exception {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String query = "select  rr.rule_category_applied_rule_id ,a.role_identifier"
				+ " from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
				+ " join CANCELLATION_CATEGORY_RULE rr on "
				+ " rr.account_Cancellation_id = ac.id where a.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER" + " from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]"
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID  "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =" + status + " and   SD.CONTRACT_STATUS_ID =5  and "
				+ " SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null " + " and R.ROLE_NAME ='" + roleType
				+ "' and SD.SALE_DATE between  '" + preYear + "'  and '" + currYear + "') " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				System.out.println("roleid====" + roleId);

				return roleId;
			}
		}
		return query;
	}

	/**
	 * This function gets all Contract details used in TC14
	 * 
	 */
	public HashMap<String, String> getContractDetailsDB(String cert) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = "select  sales.CERT as Contract_Number, account.NAME as "
				+ " Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ " CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ " AS Customer_Name,sales.SALE_DATE as Sale_Date,(sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES]) as SaleMileage ,"
				+ " sales.VIN, sales.EXPIRATION_DATE ,((sales.[EXPIRATION_MILEAGE]-sales.[PLAN_MILES])+2214) as cancelMiles ,"
				+ " (sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ " price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ " sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ " account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ " [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ " left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ " join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where sales.CERT = '" + cert
				+ "' ";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This gets search all compliance details based on cert and primary account
	 * name
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> getComplianceRuleSearchparameter(String cert, String Name) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();

		String query = " display_name from dbo.state where name in (select state from allsales_details asd "
				+ " join [dbo].[ACCOUNT] account on account.id =  asd.PRIMARY_ACCOUNT_ID where cert = '" + cert + "' "
				+ " and name= '" + Name + "')";
		HashMap<String, String> stateName = getTopRowDataFromDatabase(query);
		String display_Name = stateName.get("display_name");
		String query1 = " Select document_version as VersionNumber ,b.name as GroupName from COMPLIANCE_DOCUMENT c"
				+ " join COMPLIANCE_DOCUMENT_GROUP a on a.compliance_document_id = c.id "
				+ " join [dbo].[STATE] s on s.id = c.document_state_id"
				+ " join COMPLIANCE_GROUP b on b.id = a.compliance_group_id where b.name in ("
				+ " select group_name from COMPLIANCE_GROUP_PRICESHEET cgp where cgp.PRICING_PRICESHEET_ID in "
				+ " (select id from dbo.pricing_pricesheet where id in(select parent_pricesheet_id "
				+ " from dbo.pricing_pricesheet where id in(select parent_pricesheet_id "
				+ " from dbo.pricing_pricesheet where id in (select pricesheet_id  "
				+ " from AllSales_Details  where cert = '" + cert + "'))))) " + " and s.Display_Name = ' "
				+ display_Name + "' order by c.id desc ";

		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query1);
		if (dbMap.size() < 1) {
			String query2 = " Select document_version as version_number ,b.name as group from COMPLIANCE_DOCUMENT c"
					+ " join COMPLIANCE_DOCUMENT_GROUP a on a.compliance_document_id = c.id "
					+ " join [dbo].[STATE] s on s.id = c.document_state_id"
					+ " join COMPLIANCE_GROUP b on b.id = a.compliance_group_id where b.name in ("
					+ " select group_name from COMPLIANCE_GROUP_PRICESHEET cgp where cgp.PRICING_PRICESHEET_ID in "
					+ " (select id from dbo.pricing_pricesheet where id in(select parent_pricesheet_id "
					+ " from dbo.pricing_pricesheet where id in(select parent_pricesheet_id "
					+ " from dbo.pricing_pricesheet where id in (select pricesheet_id  "
					+ " from AllSales_Details  where cert = '" + cert + "'))))) "
					+ " and s.Display_Name ='Multi-State' order by c.id desc ";

			HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query2);
			dbMap1.put("stateName", display_Name);
			myData = dbMap1;
		} else {
			dbMap.put("state", display_Name);
			myData = dbMap;
		}
		return myData;
	}

	/**
	 * This query is used to get the AccountlevelsRules Associated with the provided
	 * Rule Type and Rule subType and return hashMap
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> cancellation_SearchSpecificAccountRule(String ruleType,
			String ruleSubType) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String queryPart = "";
		if (ruleType.equalsIgnoreCase("RefundPercent")) {
			queryPart = queryPart + "and cancellation_category_group_id = 4";
			if (ruleSubType.equalsIgnoreCase("Refund Percent"))
				queryPart = queryPart
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			else if (ruleSubType.equalsIgnoreCase("Refund Percent, Administrator"))
				queryPart = queryPart + " and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			else if (ruleSubType.equalsIgnoreCase("Refund Percent, Lender, Trade/Sale"))
				queryPart = queryPart + " and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			else if (ruleSubType.equalsIgnoreCase("Refund Percent, Repossession"))
				queryPart = queryPart + " and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		if (ruleType.equalsIgnoreCase("CancelFee")) {
			queryPart = queryPart + "and cancellation_category_group_id = 1";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Repossession"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			else if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Repossession"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		if (ruleType.equalsIgnoreCase("NonCompliance")) {
			queryPart = queryPart + "and cancellation_category_group_id = 6";
			if (ruleSubType.contains("Refund Based On")) {
				String ruleKey = ruleSubType.substring(0, ruleSubType.indexOf("_"));
				String ruleValue = ruleSubType.substring(ruleSubType.indexOf("_") + 1);
				queryPart = queryPart + " and c.name = '" + ruleKey + "' and b.name = '" + ruleValue + "' ";
			} else if (ruleSubType.contains("Payee")) {
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' ";
			}
		}

		if (ruleType.equalsIgnoreCase("") && ruleSubType.equalsIgnoreCase("")) {
			queryPart = queryPart
					+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
		}

		String query1 = "select distinct aa.ROLE_IDENTIFIER,ac.EFFECTIVE_DATE,aa.Name from RULE_CATEGORY_APPLIED_RULE a "
				+ "join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ "join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ "join CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id "
				+ "join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id join dbo.Account aa on aa.id = ac.Primary_account_id where ac.pricesheet_id is null and ac.secondary_account_id is null "
				+ queryPart + "order by ROLE_IDENTIFIER desc;";

		HashMap<String, String> myData = new HashMap<String, String>();
		System.out.println("query1:::::::::" + query1);
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		for (int i = 1; i <= dbMap.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData = dbMap.get(i);
			String roleIdValue = myData.get("ROLE_IDENTIFIER");
			String accountRuleEffDate = myData.get("EFFECTIVE_DATE");
			String roleName = myData.get("Name");
			finalData.put(roleIdValue + "_" + roleName, accountRuleEffDate);
			dbData.put(i, finalData);
		}
		return dbData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * role type and RoleId it will fetch contracts whose expiration date is greater
	 * than system current date
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusRoleIdAccRulesClaims(String roleType,
			String roleId, String ruleSubtype, String minSaleDate) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = df.format(Calendar.getInstance().getTime());
		String queryPart = "";
		if (minSaleDate.length() > 0) {
			Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(minSaleDate);
			String minSaleDateF = df.format(date1);
			if (minSaleDateF.contains("*")) {
				minSaleDateF = minSaleDateF.replace("*", "");
				queryPart = "and SD.SALE_DATE < '" + minSaleDateF + "'";
			} else {
				queryPart = "and SD.SALE_DATE > '" + minSaleDateF + "'";
			}
		} else {
			int currYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
			int preYear = currYear - 2;
			queryPart = "and SD.SALE_DATE " + " between '" + preYear + "' and '" + currYear + "'";
		}
		String queryPart1 = "";
		String queryPart2 = "";
		if (ruleSubtype.contains("CancelFee") && ruleSubtype.contains("if Claims")) {
			queryPart1 = " and CLAIM_AMOUNT> 0 AND STATUS ='Paid'";
			queryPart2 = "join [dbo].[CLAIMS] b on SD.cert = b.cert ";
		} else {
			queryPart1 = " AND CERT NOT IN (SELECT CERT FROM [dbo].[CLAIMS])";
		}
		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,SD.PLAN_MILES,SD.PLAN_MONTHS,SD.EXPIRATION_DATE,SD.EXPIRATION_MILEAGE,SD.CUSTOMER_PAID,SD.DEALER_PAID from [dbo].[ALLSALES_DETAILS] SD "
				+ queryPart2 + "join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ "INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ "INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ "INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ "where A.ACCOUNT_STATUS_ID = 1 and SD.CONTRACT_STATUS_ID = 5 and A.ROLE_IDENTIFIER =" + roleId
				+ " and R.ROLE_NAME = '" + roleType + "' and SD.PRICESHEET_ID is not null "
				+ "and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00 " + queryPart
				+ " and SD.EXPIRATION_DATE > '" + currDate + "'" + queryPart1 + " order by  SD.SALE_DATE asc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This query is used to get the specific AccountlevelsRules (Refund Percent and
	 * Cancel Fee) Associated with given primaryRoleId and return hashMap
	 * 
	 */
	public HashMap<String, String> dBValidationOfAccountLevelRulesforRefundPCancelF(String primaryRoleId,
			String ruleType, String ruleSubType) throws Exception {
		String queryPart = "";
		if (ruleType.equalsIgnoreCase("RefundPercent")) {
			queryPart = queryPart + "and cancellation_category_group_id = 4";
			if (ruleSubType.equalsIgnoreCase("Refund Percent"))
				queryPart = queryPart
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Administrator"))
				queryPart = queryPart + " and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Lender, Trade/Sale"))
				queryPart = queryPart + " and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Repossession"))
				queryPart = queryPart + " and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		if (ruleType.equalsIgnoreCase("CancelFee")) {
			queryPart = queryPart + "and cancellation_category_group_id = 1";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Repossession"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Administrator"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Repossession"))
				queryPart = queryPart
						+ " and c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		if (ruleType.equalsIgnoreCase("NonCompliance")) {
			queryPart = queryPart + "and cancellation_category_group_id = 6";
			if (ruleSubType.contains("Refund Based On")) {
				String ruleKey = ruleSubType.substring(0, ruleSubType.indexOf("_"));
				queryPart = queryPart + " and c.name = '" + ruleKey + "' ";
			} else if (ruleSubType.contains("Payee")) {
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' ";
			}
		}

		if (ruleType.equalsIgnoreCase("") && ruleSubType.equalsIgnoreCase("")) {
			queryPart = queryPart
					+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
		}

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id where a.role_identifier =" + primaryRoleId
				+ " and pricesheet_id is null) " + queryPart + " order by 1 desc" + ";";

		System.out.println("query2:::::::::" + query1);
		HashMap<String, String> myData = getTopRowDataFromDatabase(query1);
		HashMap<String, String> finalData = new HashMap<String, String>();
		String rulegroup = myData.get("Rule_Group");
		String ruleUsed = myData.get("Rule_Used");
		finalData.put(rulegroup, ruleUsed);

		return finalData;

	}

	/**
	 * This query is used to get the AccountlevelsRules Associated with the provided
	 * Rule Type and Rule subType and return hashMap
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> cancellation_SearchSpecificAccountRuleWithPriceSheetLevelRules(
			String ruleType, String ruleSubType) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String queryPart = "";
		if (ruleType.equalsIgnoreCase("RefundPercent")) {
			queryPart = queryPart + "and cancellation_category_group_id = 4";
			if (ruleSubType.equalsIgnoreCase("Refund Percent"))
				queryPart = queryPart
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Administrator"))
				queryPart = queryPart + " and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Lender, Trade/Sale"))
				queryPart = queryPart + " and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Repossession"))
				queryPart = queryPart + " and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		if (ruleType.equalsIgnoreCase("CancelFee")) {
			queryPart = queryPart + "and cancellation_category_group_id = 1";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period,Administrator"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period,Administrator"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Repossession"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims,Administrator"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims,Administrator"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Repossession"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		String query1 = "select distinct aa.ROLE_IDENTIFIER,ac.EFFECTIVE_DATE,aa.Name, ac.PRICESHEET_ID from RULE_CATEGORY_APPLIED_RULE a "
				+ "join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ "join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ "join CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id "
				+ "join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id join dbo.Account aa on aa.id = ac.Primary_account_id where pricesheet_id is not null "
				+ queryPart + "order by ROLE_IDENTIFIER desc;";

		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		for (int i = 1; i <= dbMap.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData = dbMap.get(i);
			String roleIdValue = myData.get("ROLE_IDENTIFIER");
			String accountRuleEffDate = myData.get("EFFECTIVE_DATE");
			String roleName = myData.get("Name");
			@SuppressWarnings("unused")
			String priceSheet = myData.get("PRICESHEET_ID");
			finalData.put(roleIdValue + "_" + roleName, accountRuleEffDate);
			dbData.put(i, finalData);
		}
		return dbData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * role type , role Id and priceSheet it will fetch contracts whose expiration
	 * date is greater than system current date
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusRoleIdPriceSheetLevelRules(String roleType,
			String roleId, String ruleSubtype, String minSaleDate, String priceSheet) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currDate = df.format(Calendar.getInstance().getTime());
		String queryPart = "";
		if (minSaleDate.length() > 0) {
			Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(minSaleDate);
			String minSaleDateF = df.format(date1);
			queryPart = "and SD.SALE_DATE > '" + minSaleDateF + "'";
		} else {
			int currYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
			int preYear = currYear - 2;
			queryPart = "and SD.SALE_DATE " + " between '" + preYear + "' and '" + currYear + "'";
		}
		String queryPart1 = "";
		String queryPart2 = "";
		if (ruleSubtype.contains("CancelFee") && ruleSubtype.contains("if Claims")) {
			queryPart1 = " and CLAIM_AMOUNT> 0 AND STATUS ='Paid'";
			queryPart2 = "join [dbo].[CLAIMS] b on SD.cert = b.cert ";
		} else {
			queryPart1 = " AND CERT NOT IN (SELECT CERT FROM [dbo].[CLAIMS])";
		}
		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,SD.PLAN_MILES,SD.PLAN_MONTHS,SD.EXPIRATION_DATE,SD.EXPIRATION_MILEAGE from [dbo].[ALLSALES_DETAILS] SD "
				+ queryPart2 + "join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ "INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ "INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ "INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ "where A.ACCOUNT_STATUS_ID = 1 and SD.CONTRACT_STATUS_ID = 5 and A.ROLE_IDENTIFIER =" + roleId
				+ " and R.ROLE_NAME = '" + roleType + "' and SD.PRICESHEET_ID = ' " + priceSheet + "' "
				+ "and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00 " + queryPart
				+ " and SD.EXPIRATION_DATE > '" + currDate + "'" + queryPart1 + " order by  SD.SALE_DATE asc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This query is used to get the specific AccountlevelsRules (Refund Percent and
	 * Cancel Fee) Associated with given primaryRoleId and return hashMap
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> dBValidationOfPriceSheetLevelRulesforRefundPCancelF(
			String primaryRoleId, String ruleType, String ruleSubType, String priceSheet) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String queryPart = "";
		if (ruleType.equalsIgnoreCase("RefundPercent")) {
			queryPart = queryPart + "and cancellation_category_group_id = 4";
			if (ruleSubType.equalsIgnoreCase("Refund Percent"))
				queryPart = queryPart
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Administrator"))
				queryPart = queryPart + " and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Lender, Trade/Sale"))
				queryPart = queryPart + " and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Refund Percent, Repossession"))
				queryPart = queryPart + " and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}

		if (ruleType.equalsIgnoreCase("CancelFee")) {
			queryPart = queryPart + "and cancellation_category_group_id = 1";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period"))
				queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
						+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period,Administrator"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period,Administrator"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee Within Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Lender, Trade/Sale"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
			if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period, Repossession"))
				queryPart = queryPart
						+ " c.name = 'Cancel Fee After Flat Cancel Period' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";
		}
		if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims"))
			queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
					+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
		if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims"))
			queryPart = queryPart + " and c.name = '" + ruleSubType + "' "
					+ " and cancellation_reason_type_id is null and cancellation_initiated_by_id is null ";
		if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims,Administrator"))
			queryPart = queryPart
					+ " c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
		if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims,Administrator"))
			queryPart = queryPart
					+ " c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id is null and cancellation_initiated_by_id= 4 ";
		if (ruleSubType.equalsIgnoreCase("Cancel Fee Within Flat Cancel Period if Claims, Lender, Trade/Sale"))
			queryPart = queryPart
					+ " c.name = 'Cancel Fee Within Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
		if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Lender, Trade/Sale"))
			queryPart = queryPart
					+ " c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 1 and cancellation_initiated_by_id= 2 ";
		if (ruleSubType.equalsIgnoreCase("Cancel Fee After Flat Cancel Period if Claims, Repossession"))
			queryPart = queryPart
					+ " c.name = 'Cancel Fee After Flat Cancel Period if Claims' and cancellation_reason_type_id= 3 and cancellation_initiated_by_id is null ";

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group ,ac.pricesheet_id from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id where a.role_identifier =" + primaryRoleId
				+ " and ac.pricesheet_id ='" + priceSheet + "') " + queryPart + " order by 1 desc" + ";";

		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {
				HashMap<String, String> finalData = new HashMap<String, String>();
				myData = dbMap.get(i);
				String rulegroup = myData.get("Rule_Group");
				String ruleUsed = myData.get("Rule_Used");
				finalData.put(rulegroup, ruleUsed);
				dbData.put(i, finalData);
			}
		}
		return dbData;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * effectiveDate and RoleId it will fetch contracts
	 *
	 * @throws Exception
	 *
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnEffectiveDateAndRoleId(String roleId,
			String effectiveDate) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = 0;
		String Query = "";
		if (effectiveDate == "") {
			effectiveDate = String.valueOf(currYear);
			preYear = currYear - 1;
		} else {
			effectiveDate = effectiveDate.substring(0, 4);
			preYear = Integer.parseInt(effectiveDate) - 1;
		}

		String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER ='" + roleId
				+ "' and SD.PRICESHEET_ID is not null " + Query + " "
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00  and SD.SALE_DATE " + " between '"
				+ preYear + "' and '" + effectiveDate + "'  and YEAR(SD.EXPIRATION_DATE) >= '" + currYear
				+ "' order by  SD.SALE_DATE desc ;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This query is used to get account Primary Role Id with account Rule Builder
	 * and return hashMap
	 *
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> cancellation_getAccountRoleIdWithAccountRuleBuilderTC14(String roleType)
			throws Exception {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = currYear - 1;
		String roleId = null;
		String effectiveDate = null;
		String name = null;
		HashMap<String, String> dbRuleId = new HashMap<String, String>();
		String query = " select distinct aa.role_identifier,aa.name,ac.EFFECTIVE_DATE from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " join CANCELLATION_CATEGORY_RULE rr on rr.rule_category_applied_rule_id = a.id "
				+ " join ACCOUNT_CANCELLATION ac on rr.account_Cancellation_id = ac.id "
				+ " join dbo.Account aa on aa.id = ac.Primary_account_id where  "
				+ " c.cancellation_category_group_id in( 4 ,1)    and aa.role_identifier in ( "
				+ " SELECT distinct  A.ROLE_IDENTIFIER from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A   On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " where A.ACCOUNT_STATUS_ID =1 and   SD.CONTRACT_STATUS_ID =5  and SD.PROGRAM_CODE <>'OLW' and SD.PRICESHEET_ID is not null "
				+ " and R.ROLE_NAME ='" + roleType + "' and SD.SALE_DATE between '" + preYear + "' and '" + currYear
				+ "') " + ";";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		if (dbMap.size() > 0) {
			for (int i = 1; i < dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				roleId = dbMap.get(number).get("role_identifier");
				effectiveDate = dbMap.get(number).get("EFFECTIVE_DATE");
				effectiveDate = effectiveDate.substring(0, 10);
				name = dbMap.get(number).get("name");
				System.out.println("roleid====" + roleId);
				dbRuleId.put("roleIdentifier", roleId);
				dbRuleId.put("EFFECTIVE_DATE", effectiveDate);
				dbRuleId.put("name", name);
				return dbRuleId;
			}
		}
		return dbRuleId;
	}

	@SuppressWarnings("unused")
	public HashMap<Integer, HashMap<String, String>> dBValidationOfAccountLevelRulesforInitatorDealer(
			String primaryRoleId) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String query2 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id " + " where a.role_identifier =" + primaryRoleId
				+ ") and cancellation_reason_type_id is null and cancellation_initiated_by_id is null order by 1 desc"
				+ ";";
		HashMap<String, String> myData1 = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap1 = getAllDataFromDatabase(query2);

		for (int i = 1; i <= dbMap1.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData1 = dbMap1.get(i);
			String rulegroup = myData1.get("Rule_Group");
			String ruleUsed = myData1.get("Rule_Used");
			finalData.put(rulegroup, ruleUsed);
			dbData.put(i, finalData);
		}
		return dbData;
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
				String query2 = "select sales.CERT as CERT, sales.sale_date as SALE_DATE, account.name from [dbo].[ALLSALES_DETAILS] sales "
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
		System.out.println("version==" + versionNumber);
		return versionNumber;
	}

	/**
	 * to get the contract number which has more than one entry in
	 * ALLCANCEL_TRANS_DETAILS table
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdforPBI15863() throws Exception {
		String query = "select cert as Contract, id from [dbo].[ALLSALES_DETAILS] sale where sale.id in "
				+ "(select top 1 ALLSALES_DETAILS_ID from [dbo].[ALLCANCEL_TRANS_DETAILS ] "
				+ "group by ALLSALES_DETAILS_ID having count(*) > 1) ;";
		///// execute query
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		System.out.println("query: " + query);
		return dbMap;
	}

	/**
	 * to get ALLCANCEL_TRANS_DETAILS table associated with a contract
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getAllCancelTransHistoryData(String contractId, String allSalesID)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbData = new HashMap<Integer, HashMap<String, String>>();

		String query = "select cs.Name as cancel_status,cancel_adjtype, process_date, refund_percentage, net_refund_amount, cancel_mileage, cancel_date, cib.NAME as initiated_by, "
				+ "crt.NAME as cancel_reason from [dbo].[ALLCANCEL_TRANS_DETAILS ] ac "
				+ "join [dbo].[CANCELLATION_STATUS] cs on cs.id = ac.STATUS_ID "
				+ "join [dbo].[CANCELLATION_INITIATED_BY] cib on cib.id = ac.INITIATED_BY_ID "
				+ "join [dbo].[CANCELLATION_REASON_TYPE] crt on crt.id = ac.REASON_TYPE_ID "
				+ "where ALLSALES_DETAILS_ID = '" + allSalesID + "' ;";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);

		for (int i = 1; i <= dbMap.size(); i++) {
			HashMap<String, String> finalData = new HashMap<String, String>();
			myData = dbMap.get(i);
			finalData.put("Contract", contractId);
			finalData.put("cancelStatus", myData.get("cancel_status"));
			finalData.put("adjType", myData.get("cancel_adjtype"));
			String processDate = convertDate(myData.get("process_date"), 0);
			finalData.put("processDate", processDate);
			finalData.put("refundPercent", myData.get("refund_percentage"));
			float netRefund = Float.parseFloat(myData.get("net_refund_amount"));
			DecimalFormat df = new DecimalFormat("0.00");
			finalData.put("netRefund", df.format(netRefund));
			finalData.put("cancelMiles", myData.get("cancel_mileage"));
			String cancelDate = convertDate(myData.get("cancel_date"), 0);
			finalData.put("cancelDate", cancelDate);
			finalData.put("initiatedBy", myData.get("initiated_by"));
			finalData.put("cancelReason", myData.get("cancel_reason"));
			dbData.put(i, finalData);
		}
		return dbData;

	}

	/**
	 * 
	 * to get the cancellation parameteres Id
	 */
	public String getCancellationParameteresId(String contrcatId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String cancellationParametesId = "";
		String query = "Select cp.ID as CancellationParametersId from dbo.ALLSALES_DETAILS asd join dbo.CANCELLATION_PARAMETERS cp on asd.ID=cp.ALLSALES_DETAILS_ID "
				+ " Where asd.cert='" + contrcatId + "' ; ";
		System.out.println("query===" + query);
		dbMap = getTopRowDataFromDatabase(query);
		cancellationParametesId = dbMap.get("CancellationParametersId");
		System.out.println("cancellationParametesId==" + cancellationParametesId);
		return cancellationParametesId;
	}

	/**
	 * 
	 * to get the breakdownInfo of cancelled Contract
	 */
	public HashMap<Integer, HashMap<String, String>> getBreakdownInfo(String cancellationParametersId)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		String query = "SELECT  B.[ROLLUP_DESCR] AS Bucket ,SUM(A.DVALUE) As CancelValue FROM [ALLCANCEL_DOLLAR_DETAIL] AS A WITH ( NOLOCK ) "
				+ " JOIN [ALLSALES_DOLLAR_DESCR_ROLLUP] AS B WITH ( NOLOCK ) ON A.DID = B.DOLLAR_DESCR_ID "
				+ " where A.[CANCELLATION_PARAMETERS_ID] ='" + cancellationParametersId
				+ "' GROUP BY A.[CANCELLATION_PARAMETERS_ID] , B.[ROLLUP_DESCR] ;";
		System.out.println("query===" + query);
		dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status and RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> getContractIdForWarning(String roleId) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		String query = " ";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets SearchDataOnAccountSearchScreen and give associated PriceSheet with
	 * the Account
	 * 
	 */
	public HashMap<String, String> checkPriceSheetOnAccountForCancellationWarning(
			HashMap<String, String> searchParamater) throws Exception {
		String query = "select top 1 pp.INTERNAL_NAME as PriceSheetInternalName, pp.CODE as PriceSheet from dbo.Account account "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on account.ROLE_TYPE_ID = accountRole.id "
				+ " join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
				+ " join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ " join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
				+ " join PRICING_PRICESHEET_ACCOUNT_RELATION ppar on  ppar.ID=account.ID"
				+ " join PRICING_PRICESHEET pp on pp.id=ppar.PRICESHEET_ID where " + " account.ROLE_IDENTIFIER='"
				+ searchParamater.get("Role_Id") + "' and accountRole.ROLE_NAME='" + searchParamater.get("Role_Type")
				+ "' and accStatus.STATUS='" + searchParamater.get("Status") + "' ";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets Active Contract Number based on RoleID and ProgCode
	 */

	public HashMap<Integer, HashMap<String, String>> cancellation_getContractMultipleIdBasedOnRoleIdAndProgCode(
			String roleId, String ProCode) throws Exception {
		String query = "SELECT distinct SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER =" + roleId
				+ " and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE ='" + ProCode + "'  "
				+ " and  SD.Customer_Paid is not null  " + " order by  SD.SALE_DATE desc;";
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		return dbMap;

	}

	/**
	 * This gets Active Contract Number based on RoleID and ProgCode
	 */

	public HashMap<Integer, HashMap<String, String>> cancellation_getProgCode(String roleId, String effDate,
			String sRoleId) throws Exception {
		String secRoleID = "";
		if (!sRoleId.equalsIgnoreCase("") || !sRoleId.equalsIgnoreCase(null)) {
			secRoleID = " and aa.ROLE_IDENTIFIER =" + sRoleId + " ";
		}
		String query = "SELECT distinct  SD.PROGRAM_CODE "
				+ "	from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
				+ "	On A.[ID] = SD.[PRIMARY_ACCOUNT_ID]	left join [ACCOUNT]  aa  ON aa.ID = SD.[SECONDARY_ACCOUNT_ID] "
				+ "	where A.ACCOUNT_STATUS_ID = 1 and   SD.CONTRACT_STATUS_ID =5  and  "
				+ "	SD.PROGRAM_CODE not in ('OLW','LWA','OCW','RLW','RAW') and SD.PRICESHEET_ID is not null "
				+ "	and A.ROLE_IDENTIFIER = " + roleId + " " + secRoleID + " and SD.SALE_DATE > '" + effDate + "'";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets ProgCode based on RoleID and price sheet
	 */

	public HashMap<Integer, HashMap<String, String>> cancellation_getProgCodeWithAccRule(String roleId, String sRoleId)
			throws Exception {
		String secRoleID = "";
		if (!sRoleId.equalsIgnoreCase("") || !sRoleId.equalsIgnoreCase(null)) {
			secRoleID = " and aa.ROLE_IDENTIFIER =" + sRoleId + " ";
		}
		String query = " select  distinct pp.CODE from ACCOUNT_CANCELLATION ac join dbo.Account a on a.id = ac.Primary_account_id "
				+ " join CANCELLATION_CATEGORY_RULE rr on rr.account_Cancellation_id = ac.id INNER JOIN "
				+ " dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID left join dbo.[ACCOUNT] aa on aa.id = ac.[SECONDARY_ACCOUNT_ID] "
				+ " left join [dbo].[PRICING_PRICESHEET] pp on pp.PARENT_PRICESHEET_ID = ac.PRICESHEET_ID "
				+ " where a.role_identifier = " + roleId + " " + secRoleID + " ";

		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search account Role_Identifier with account_cancellation_warning
	 *
	 */
	public HashMap<String, String> getAccountCancellationWarning(String roletype) throws Exception {
		HashMap<String, String> finalValue = new HashMap<String, String>();
		String CERT = "";
		String SALE_DATE = "";
		String PROGRAM_CODE = "";
		String AccID = "";
		String AccName = "";
		String AccRoleIdn = "";
		String AccWarType = "";

		String query = "SELECT distinct top 1000 SD.CERT ,SD.SALE_DATE,SD.PROGRAM_CODE,A.ID,A.NAME ,A.[ROLE_IDENTIFIER],AW.WARNING_TYPE "
				+ " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN [dbo].[ACCOUNT_WARNING] AW on AW.[ACCOUNT_ID] = A.ID "
				+ " where  SD.CONTRACT_STATUS_ID =5  and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW' "
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00 and R.ROLE_NAME =' " + roletype + "' "
				+ " and AW.WARNING_TYPE = 2 and AW.WARNING_TEXT is not null and AW.WARNING_TEXT <> ''"
				+ " order by SD.SALE_DATE desc ";
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				CERT = dbMap.get(number).get("CERT");
				SALE_DATE = dbMap.get(number).get("SALE_DATE");
				PROGRAM_CODE = dbMap.get(number).get("PROGRAM_CODE");
				AccName = dbMap.get(number).get("Name");
				AccID = dbMap.get(number).get("ID");
				AccRoleIdn = dbMap.get(number).get("ROLE_IDENTIFIER");
				AccWarType = dbMap.get(number).get("WARNING_TYPE");
				finalValue.put("CERT", CERT);
				finalValue.put("SALE_DATE", SALE_DATE);
				finalValue.put("PROGRAM_CODE", PROGRAM_CODE);
				finalValue.put("AccID", AccID);
				finalValue.put("AccName", AccName);
				finalValue.put("AccRoleIdn", AccRoleIdn);
				finalValue.put("AccWarType", AccWarType);

				String query2 = "select distinct  AW.WARNING_TEXT ,AW.CREATED_DATE  from  [dbo].[ACCOUNT] A  "
						+ " INNER JOIN [dbo].[ACCOUNT_WARNING] AW on AW.[ACCOUNT_ID] = A.ID "
						+ " where AW.WARNING_TYPE = 2 " + " and A.ROLE_IDENTIFIER = '" + AccRoleIdn
						+ "'  order by AW.CREATED_DATE  desc ";

				HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query2);
				String warningText = dbMap1.get("WARNING_TEXT");
				if (warningText != "" && warningText != "Empty" && warningText != null) {
					finalValue.put("AccWarText", warningText);
					return finalValue;
				}
			}
		}
		return null;
	}

	/**
	 * This query is used to get all AccountlevelsRules Associated with given
	 * primaryRoleId and return hashMap
	 * 
	 */
	public HashMap<String, String> dBValueOfAddAccountLevelRules(String primaryRoleId, String Pcode,
			String primaryRoleName) throws Exception {
		HashMap<String, String> finalData = new HashMap<String, String>();

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id "
				+ " INNER JOIN ALLSALES_DETAILS SD on SD.PRICESHEET_ID =ac.PRICESHEET_ID "
				+ " where a.role_identifier =" + primaryRoleId + " and a.name ='" + primaryRoleName
				+ "' and SD.PROGRAM_CODE = '" + Pcode + "')" + " order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {

				myData = dbMap.get(i);
				String rulegroup = myData.get("Rule_Group");
				String ruleUsed = myData.get("Rule_Used");
				if (!ruleUsed.equalsIgnoreCase("Standard Refund Percent if Claims, Else 100%")) {
					finalData.put(rulegroup, ruleUsed);

				}
			}
			return finalData;
		} else {
			String query2 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
					+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
					+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
					+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
					+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
					+ " on rr.account_Cancellation_id = ac.id " + " where a.role_identifier =" + primaryRoleId
					+ ") order by 1 desc" + ";";
			HashMap<Integer, HashMap<String, String>> dbMap1 = getAllDataFromDatabase(query2);
			for (int i = 1; i <= dbMap1.size(); i++) {
				myData = dbMap1.get(i);
				String rulegroup = myData.get("Rule_Group");
				String ruleUsed = myData.get("Rule_Used");
				if (!ruleUsed.equalsIgnoreCase("Standard Refund Percent if Claims, Else 100%")) {
					finalData.put(rulegroup, ruleUsed);
				}
			}
			return finalData;
		}
	}

	/**
	 * This gets SearchDataOnAccountSearchScreen
	 * 
	 */
	public HashMap<String, String> accounts_getDataSetforSearchForCancellation(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "";
		String query1 = "select top " + "1" + " ";
		String query2 = " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID where ";
		String myKey = "";
		String myvalue = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String key = (String) mapElement.getKey();
			String value = (String) mapElement.getValue();
			if (value.equals("*")) {
				String value1 = "";
				if (key.equals("Account_Name")) {
					key = " account.name as Account_Name ";
					value1 = " account.name ";
				}
				if (key.equals("Role_Id")) {
					key = " account.ROLE_IDENTIFIER as Role_Id ";
					value1 = " account.ROLE_IDENTIFIER ";
				}
				if (key.equals("Role_Type")) {
					key = " accountRole.ROLE_NAME as Role_Type ";
					value1 = " accountRole.ROLE_NAME ";
				}
				if (key.equals("Address")) {
					key = " accAddress.STREET as Address ";
					value1 = " accAddress.STREET ";
				}

				if (key.equals("City")) {
					key = " accAddress.CITY ";
					value1 = " accAddress.CITY ";
				}

				if (key.equals("State")) {
					key = " accAddress.STATE ";
					value1 = " accAddress.STATE ";
				}

				if (key.equals("Zip_Code")) {
					key = " accAddress.ZIP_CODE ";
					value1 = " accAddress.ZIP_CODE ";
				}
				if (key.equals("Status")) {
					key = " accStatus.STATUS ";
					value1 = " accStatus.STATUS ";
				}
				myKey = myKey + key + ",";
				myvalue = myvalue + value1 + " is not null and ";
			} else if (value.length() < 1) {
				//// do nothing
			} else {
				String value1 = "";
				if (key.equals("Account_Name")) {
					key = " account.name as Account_Name ";
					value1 = " account.name ";
				}
				if (key.equals("Role_Id")) {
					key = " account.ROLE_IDENTIFIER as Role_Id ";
					value1 = " account.ROLE_IDENTIFIER ";
				}
				if (key.equals("Role_Type")) {
					key = " accountRole.ROLE_NAME as Role_Type ";
					value1 = " accountRole.ROLE_NAME ";
				}
				if (key.equals("Address")) {
					key = " accAddress.STREET as Address ";
					value1 = " accAddress.STREET ";
				}

				if (key.equals("City")) {
					key = " accAddress.CITY ";
					value1 = " accAddress.CITY ";
				}

				if (key.equals("State")) {
					key = " accAddress.STATE ";
					value1 = " accAddress.STATE ";
				}

				if (key.equals("Zip_Code")) {
					key = " accAddress.ZIP_CODE ";
					value1 = " accAddress.ZIP_CODE ";
				}
				if (key.equals("Status")) {
					key = " accStatus.STATUS ";
					value1 = " accStatus.STATUS ";
				}
				myKey = myKey + key + ",";
				if (key.equals(" account.ROLE_IDENTIFIER as Role_Id "))
					myvalue = myvalue + value1 + " = " + value + " and ";
				else
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
	 * This gets search all sales details and return us latest contract id based on
	 * status and RoleId it will fetch contracts
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnRoleIdAndPriceSheet(String roleId,
			String programCode) throws Exception {

		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
		int preYear = currYear - 1;
		String query = "  SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[ALLSALES_DETAILS] SD "
				+ " join [dbo].[ACCOUNT] A On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] "
				+ " INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.CONTRACT_STATUS_ID =5  and  A.ROLE_IDENTIFIER = '" + roleId
				+ "' and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE ='" + programCode + "' and "
				+ " SD.Customer_Paid is not null and SD.Customer_Paid <>0.00  and SD.SALE_DATE  between '" + preYear
				+ "' and '" + currYear + "' order by  SD.SALE_DATE asc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		myData = dbMap;
		return myData;
	}

	/**
	 * This gets search account Role_Identifier with account_cancellation_warning
	 *
	 */
	public HashMap<String, String> getRoleIdWithAccountCancellationWarning() throws Exception {
		HashMap<String, String> finalValue = new HashMap<String, String>();
		String CERT = "";
		String SALE_DATE = "";
		String PROGRAM_CODE = "";
		String AccID = "";
		String AccName = "";
		String AccRoleIdn = "";
		String AccWarType = "";

		String query = "SELECT distinct top 1000 SD.CERT ,SD.SALE_DATE,SD.PROGRAM_CODE,A.ID,A.NAME ,A.[ROLE_IDENTIFIER],AW.WARNING_TYPE "
				+ " from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID  "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " INNER JOIN [dbo].[ACCOUNT_WARNING] AW on AW.[ACCOUNT_ID] = A.ID "
				+ " where  SD.CONTRACT_STATUS_ID =5  and SD.PRICESHEET_ID is not null and SD.PROGRAM_CODE <>'OLW' "
				+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00 and R.ROLE_NAME ='Dealer' "
				+ " and AW.WARNING_TYPE = 2 and AW.WARNING_TEXT is not null and AW.WARNING_TEXT <> ''"
				+ " order by SD.SALE_DATE desc ";
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		if (dbMap.size() > 0) {
			for (int i = 1; i <= dbMap.size(); i++) {
				Random generator = new Random();
				int number = generator.nextInt(dbMap.size() - 1) + 1;
				CERT = dbMap.get(number).get("CERT");
				SALE_DATE = dbMap.get(number).get("SALE_DATE");
				PROGRAM_CODE = dbMap.get(number).get("PROGRAM_CODE");
				AccName = dbMap.get(number).get("Name");
				AccID = dbMap.get(number).get("ID");
				AccRoleIdn = dbMap.get(number).get("ROLE_IDENTIFIER");
				AccWarType = dbMap.get(number).get("WARNING_TYPE");
				finalValue.put("CERT", CERT);
				finalValue.put("SALE_DATE", SALE_DATE);
				finalValue.put("PROGRAM_CODE", PROGRAM_CODE);
				finalValue.put("AccID", AccID);
				finalValue.put("AccName", AccName);
				finalValue.put("AccRoleIdn", AccRoleIdn);
				finalValue.put("AccWarType", AccWarType);

				String query2 = "select distinct  AW.WARNING_TEXT ,AW.CREATED_DATE  from  [dbo].[ACCOUNT] A  "
						+ " INNER JOIN [dbo].[ACCOUNT_WARNING] AW on AW.[ACCOUNT_ID] = A.ID "
						+ " where AW.WARNING_TYPE = 2 " + " and A.ROLE_IDENTIFIER = '" + AccRoleIdn
						+ "'  order by AW.CREATED_DATE  desc ";

				HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query2);
				String warningText = dbMap1.get("WARNING_TEXT");
				if (warningText != "" && warningText != "Empty" && warningText != null) {
					finalValue.put("AccWarText", warningText);
					return finalValue;
				}
			}
		}
		return null;
	}

	/**
	 * This query is used to get all AccountlevelsEditedRules Associated with given
	 * primaryRoleId and return hashMap
	 * 
	 */
	public HashMap<String, String> dBValidationOfEditedAccountLevelRules(String primaryRoleId, String primaryRoleName)
			throws Exception {
		HashMap<String, String> dbData = new HashMap<String, String>();

		String query1 = "select b.name as Rule_Used ,c.name as Rule_Group from RULE_CATEGORY_APPLIED_RULE a "
				+ " join Cancellation_Rules b with (NOLOCK) on a.cancellation_rule_id = b.id "
				+ " join cancellation_rule_category c with (NOLOCK) on a.cancellation_rule_category_id = c.id "
				+ " where a.id in " + " (select rr.rule_category_applied_rule_id from ACCOUNT_CANCELLATION ac "
				+ " join dbo.Account a on a.id = ac.Primary_account_id join CANCELLATION_CATEGORY_RULE rr "
				+ " on rr.account_Cancellation_id = ac.id " + " where a.role_identifier =" + primaryRoleId
				+ " and a.name ='" + primaryRoleName + "' ) order by 1 desc" + ";";
		HashMap<String, String> myData = new HashMap<String, String>();
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query1);
		for (int i = 1; i <= dbMap.size(); i++) {
			myData = dbMap.get(i);
			String rulegroup = myData.get("Rule_Group");
			String ruleUsed = myData.get("Rule_Used");
			dbData.put(rulegroup, ruleUsed);
		}
		return dbData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public String getContractIDBasedOnContractStatus_T8(String status, String contractStatus) throws Exception {
		String query = "select sub.name, sta.NAME, cs.Name, cert as ContractId from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta on "
				+ " sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=sale.contract_substatus_id join CANCELLATION_PARAMETERS cp on "
				+ " sale.id = cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID where sale.PROGRAM_CODE NOT LIKE '%LW' and sta.NAME = '"
				+ status + "'  and cs.name='" + contractStatus + "' ;";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		String contractId = myData.get("ContractId");
		return contractId;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> getContractIDBasedOnContractStatus_T2(String status, String subStatus,
			String contractStatus) throws Exception {
		String query = "select  distinct sub.name, sta.NAME, cs.Name, cert as ContractId, sale.sale_date as sale_date from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta on "
				+ " sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=sale.contract_substatus_id join CANCELLATION_PARAMETERS cp on "
				+ " sale.id = cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID where sale.PROGRAM_CODE NOT LIKE '%LW' and sta.NAME = '"
				+ status + "' and sub.name='" + subStatus + "' " + " and cs.name='" + contractStatus + "' ;";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> getContractIDBasedOnContractStatus_T1(String status, String subStatus)
			throws Exception {
		String query = "select  top 1 sub.name, sta.NAME, cs.Name, cert as ContractId, sale.sale_date as sale_date from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta on "
				+ " sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=sale.contract_substatus_id join CANCELLATION_PARAMETERS cp on "
				+ " sale.id = cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID where sale.PROGRAM_CODE NOT LIKE '%LW' and sale.SALE_DATE like '%2020%' and "
				+ " sta.NAME = '" + status + "'  and" + " sub.name='" + subStatus + "' order by sale.sale_date desc ;";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> getContractIDBasedOnContractStatus_T5(String status) throws Exception {
		String query = "select sub.name, sta.NAME, cs.Name, cert as ContractId, sale.sale_date as sale_date from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta on "
				+ " sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=sale.contract_substatus_id join CANCELLATION_PARAMETERS cp on "
				+ " sale.id = cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID where sale.PROGRAM_CODE NOT LIKE '%LW' and  sale.SALE_DATE like '%2020%' "
				+ " and " + " sta.NAME = '" + status + "' order by sale.sale_date desc;";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromCHECKSTable_T5(String contractId) throws Exception {
		String query = "Select * from CHECKS where cert='" + contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromALLCANCEL_TRANS_DOLLAR_DETAILTable_T5(String contractId)
			throws Exception {
		String query = "Select * from ALLCANCEL_TRANS_DOLLAR_DETAIL atd join ALLCANCEL_TRANS_DETAILS act on act.id = atd.ALLCANCEL_TRANS_DETAILS_ID "
				+ " join ALLSALES_DETAILS sale on sale.id = act.ALLSALES_DETAILS_ID where cert='" + contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> getContractIDBasedOnContractStatus_T6(String status, String subStatus,
			String contractStatus) throws Exception {
		String query = "select  distinct sub.name, sta.NAME, cs.Name, cert as ContractId, sale.sale_date as sale_date from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta on "
				+ " sale.CONTRACT_STATUS_ID = sta.ID left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=sale.contract_substatus_id join CANCELLATION_PARAMETERS cp on "
				+ " sale.id = cp.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID where sale.PROGRAM_CODE NOT LIKE '%LW' and sta.NAME = '"
				+ status + "' and sub.name!='" + subStatus + "' " + " and cs.name='" + contractStatus + "' ;";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromALLCANCEL_TRANS_DETAILS_T5(String contractId) throws Exception {
		String query = "Select * from ALLCANCEL_TRANS_DETAILS act join ALLSALES_DETAILS sale on sale.id = act.ALLSALES_DETAILS_ID where cert='"
				+ contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromCANCELLATION_PARAMETER_RESULTS_T5(String contractId)
			throws Exception {
		String query = "Select * from CANCELLATION_PARAMETER_RESULTS cpr join CANCELLATION_PARAMETERS cp on cp.ID = cpr.CANCELLATION_PARAMETER_ID  join ALLSALES_DETAILS sale on sale.id = cp.ALLSALES_DETAILS_ID "
				+ " where cert='" + contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromALLCANCEL_DOLLAR_DETAIL_T5(String contractId) throws Exception {
		String query = "Select * from ALLCANCEL_DOLLAR_DETAIL acd join CANCELLATION_PARAMETERS cp on cp.ID = acd.[CANCELLATION_PARAMETERS_ID] join ALLSALES_DETAILS sale on sale.id = cp.ALLSALES_DETAILS_ID "
				+ " where cert='" + contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromCANCELLATION_CONTRACT_CATEGORY_T5(String contractId)
			throws Exception {
		String query = "Select * from CANCELLATION_CONTRACT_CATEGORY ccc join ALLCANCEL_DETAILS acd on acd.ID = ccc.ALLCANCEL_DETAILS_ID where cert='"
				+ contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromCANCELLATION_FORMS_T5(String contractId) throws Exception {
		String query = "Select * from CANCELLATION_FORMS cf join ALLCANCEL_DETAILS acd on acd.ID = cf.ALLCANCEL_DETAILS_ID where cert='"
				+ contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromALLCANCEL_DETAILS_T5(String contractId) throws Exception {
		String query = "Select * from ALLCANCEL_DETAILS  where cert='" + contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This query is used to get ContractID BasedOn status and ContractStatus
	 * 
	 */
	public HashMap<String, String> verificationFromCANCELLATION_PARAMETERS_T5(String contractId) throws Exception {
		String query = "Select * from CANCELLATION_PARAMETERS cp join ALLSALES_DETAILS sale on sale.id = cp.ALLSALES_DETAILS_ID where cert='"
				+ contractId + "';";
		HashMap<String, String> myData = getTopRowDataFromDatabase(query);
		return myData;
	}

}
