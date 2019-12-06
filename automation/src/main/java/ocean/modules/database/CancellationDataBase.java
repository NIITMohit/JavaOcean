package ocean.modules.database;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * cancellation Module
 * 
 * @author Mohit Goel
 */
public class CancellationDataBase extends CommonFunctions {

	/**
	 * This gets DataSetforSearch
	 * 
	 */
	public HashMap<String, String> cancellation_Search(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
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
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}

	/**
	 * This gets SearchDataCountOnCancellationScreen
	 * 
	 */
	public HashMap<String, String> cancellation_getSearchDataCountOnCancellationScreen(
			HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
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
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatus(String status) throws Exception {
		String contract_id = "";
		try {
			aulDBConnect();
			String query = "select top 1 CERT from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
					+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' order by 1 desc;";
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap = returnData(rs);
			contract_id = dbMap.get("CERT");
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractHistoryBasedOnStatus(String status) throws Exception {
		HashMap<String, String> dbMap;
		try {
			aulDBConnect();
			String query = "select  asd.cert as Contract,asd.CUSTOMER_FIRST,"
					+ "cs.Name as Status,cp.CREATED_DATE as Process_Date,cp.REFUND_PERCENTAGE,cp.NET_REFUND_AMOUNT as Net_Refund "
					+ ", cp.CANCEL_MILEAGE as Cancel_Miles, cp.CANCEL_DATE, cib.NAME as INITIATED_BY,crt.NAME as Cancel_Reason "
					+ "from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id = cp.ALLSALES_DETAILS_ID join "
					+ "[dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID join [dbo].[CANCELLATION_INITIATED_BY] cib "
					+ "on cib.id = cp.INITIATED_BY_ID left join [dbo].[CANCELLATION_REASON_TYPE] crt "
					+ "on crt.id = cp.REASON_TYPE_ID " + "where cs.name = '" + status.toLowerCase()
					+ "' and CUSTOMER_FIRST = 'Vivek';";
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
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
		try {
			aulDBConnect();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String query = "select top 1 CERT,SALE_DATE from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
					+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' and PROGRAM_CODE = '"
					+ priceSheet + "' and sale_date like '%" + year + "%'order by 1 desc;";
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap = returnData(rs);
			myData = dbMap;
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return myData;
	}

	/**
	 * This gets search return refund percentage and cancel fees data
	 * 
	 */
	public HashMap<String, String> cancellation_getRefundPercentAndCancelFee(String contractId) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		try {
			aulDBConnect();
			String query = "select CANCEL_FEE_AMOUNT,REFUND_PERCENTAGE from [dbo].[CANCELLATION_PARAMETERS]  where ALLSALES_DETAILS_ID in "
					+ "(select ID from [dbo].[ALLSALES_DETAILS] " + "where cert = '" + contractId + "')";
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap = returnData(rs);
			myData = dbMap;
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return myData;
	}

	/**
	 * This function gets all required details used in TC 08
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC08(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		try {
			aulDBConnect();
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
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap = returnData(rs);
			myData = dbMap;
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return myData;
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09(String status) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();
		try {
			aulDBConnect();
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
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap = returnData(rs);
			myData = dbMap;
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return myData;
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> cancellation_getCancellationMouduleSearchData(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select sales.CERT as Contract_Number,price.INTERNAL_NAME as PriceSheet_Name,sales.PROGRAM_CODE, "
					+ " account.NAME as Primary_Account,account.ROLE_IDENTIFIER, "
					+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) AS customer_name,statuss.Name as contractStatus ,sales.COMMENTS "
					+ "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] account on account.id =  "
					+ "sales.PRIMARY_ACCOUNT_ID join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
					+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
					+ " where sales.CERT = '" + contractId + "';";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;
	}
}
