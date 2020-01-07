package ocean.modules.database;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * underwriting Module
 * 
 * @author Mohit Goel
 */
public class UnderwritingDataBase extends CommonFunctions {
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
			String query = "select CANCEL_FEE_AMOUNT,REFUND_PERCENTAGE from [dbo].[CANCELLATION_PARAMETERS]  where ALLSALES_DETAILS_ID in \r\n"
					+ "(select ID from [dbo].[ALLSALES_DETAILS]\r\n" + "where cert = '" + contractId + "')";
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

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculation(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			c.add(Calendar.DATE, -2);
			String date = dateFormat.format(c.getTime());
			//// get data for query
			String agentException = "";
			if (map.get("AGENTEXCEPTION").toLowerCase().equals("y"))
				agentException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
						+ "' and tt.CATEGORY_VALUE = '" + map.get("AGENTPLANTYPE").toUpperCase() + "'";

			else
				agentException = "null";
			String dealerException = "";
			if (map.get("DEALEREXCEPTION").toLowerCase().equals("y"))
				dealerException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
						+ "' and tt.CATEGORY_VALUE = '" + map.get("DEALERPLANTYPE").toUpperCase() + "'";
			else
				dealerException = "null";
			String progCode = "";
			try {
				progCode = map.get("PRICESHEETCODE");
				if (progCode == null) {
					progCode = "";
				}
			} catch (Exception e) {
			}
			String dealerId = "";
			try {
				dealerId = map.get("DEALERID");
				if (dealerId == null) {
					dealerId = "";
				}
			} catch (Exception e) {
			}
			String query = "select top 1 p.id as pricesheetId,a.role_identifier as dealerid "
					+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
					+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
					+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
					+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
					+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
					+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
					+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
					+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
					+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%"
					+ progCode + "%' and a.role_identifier like '%" + dealerId
					+ "%' and a.account_status_id = 1 and a.account_type_id = 1 order by 1 desc";
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap1 = returnData(rs);
			if (dbMap1.size() == 0) {
				return dbMap = null;
			}
			dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
			dbMap.put("DEALERID", dbMap1.get("dealerid"));

			String finalQuery = "";
			if (map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase(map.get("AGENTPLANTYPE").toUpperCase())
					&& map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase("ALLPLANS")) {
				finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
						+ "from PRICESHEET_PRODUCT_TIER tt "
						+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
						+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
						+ "where t.pricesheet_id in (" + dbMap1.get("pricesheetId")
						+ ", (select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
						+ dbMap1.get("pricesheetId") + ")) " + "and tt.CATEGORY_VALUE = 'ALLPLANS' "
						+ "and xtype not like '%STD-XCP%' " + " and tt.EFFECTIVE_DATE < '" + date
						+ "' and t.EFFECTIVE_DATE < '" + date + "'"
						+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
				aulDBConnect();
				ResultSet rs1 = stmt.executeQuery(finalQuery);
				HashMap<Integer, HashMap<String, String>> data = returnAllData(rs1);
				float sumOfPremium = 1;
				String psDate = data.get(1).get("PSDATE");
				String expDate = data.get(1).get("expdate");
				for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
					String psDate1 = maps.getValue().get("PSDATE");
					String expDate1 = maps.getValue().get("expdate");
					if (psDate.equals(psDate1) && expDate.equals(expDate1)) {
						sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
					}

				}
				dbMap.put("ExceptionPremium", String.valueOf(sumOfPremium));
				return dbMap;
			} else {

			}

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;
	}
}
