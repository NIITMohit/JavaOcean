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
	 * status
	 * 
	 */
	public String deleteRemittanceStatus(String remittance) throws Exception {
		String contract_id = "";
		try {
			aulDBConnect();
			String query = "select isDeleted from  dbo.remittance where remittanceName = '" + remittance
					+ "'  order by 1 desc;";
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			HashMap<String, String> dbMap = returnData(rs);
			contract_id = dbMap.get("isDeleted");
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
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public String getPremiumCalculation(HashMap<String, String> map) throws Exception {
		String sum = "0";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select sum(numeric_value) as ddd from PRICESHEET_PRODUCT_TIER tt join PRICESHEET_PRODUCT_TIER_TARGET "
					+ "ttt on tt.id  = ttt. tier_id "
					+ "join [dbo].[PRICESHEET_TIER_TARGET_PROPERTY] pp on pp.id = ttt.tier_target_property_id "
					+ "where tt.pricesheet_id in( " + "select id from dbo.pricing_pricesheet where code = '"
					+ map.get("parentpricesheetcode") + "' and parent_pricesheet_id is null) " + "and tt.TERM = '"
					+ map.get("TERM") + "' and tt.class = '" + map.get("CLASS") + "' and tt.coverage ='"
					+ map.get("COVERAGE") + "' " + "and tt.MILEAGE_FROM<'" + map.get("MILEAGE")
					+ "' and tt.MILEAGE_TO>'" + map.get("MILEAGE") + "' and pricesheet_category_id = 2 ;";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			sum = dbMap.get("ddd");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return sum;
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
			String query = "select top 1 p.id as pricesheetId,a.role_identifier as dealerid, p.CODE as pcode "
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
			dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
			String finalQuery = "";
			if (map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase(map.get("AGENTPLANTYPE").toUpperCase())
					&& map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase("ALLPLANS")
					&& (map.get("DEALEREXCEPTION").equalsIgnoreCase("y")
							|| map.get("AGENTEXCEPTION").equalsIgnoreCase("y"))) {
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
				float sumOfPremium = 0;
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

	/**
	 * This gets PendingContractwithRemittanceName
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> pendingContractsFromRemittanceName(String remitName)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			String query = "select  r.RemittanceNumber,r.RemittanceName,d.FILE_NAME from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d "
					+ "on r.REMITTANCEID = d.REMITTANCEID where d.status_id = 4 and DOCUMENTTYPEID = 1 and r .IsDeleted = 0 "
					+ "and r.RemittanceName = '" + remitName + "' order by d.CreateByDate desc;";
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnAllData(rs);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}

	/**
	 * This gets getRemitCreationdata
	 * 
	 */
	public HashMap<String, String> getRemitCreationdata(String remitName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			waitForSomeTime(1);
			//// connect to aul db
			aulDBConnect();
			///// execute query
			String query = "select r.RemittanceName, s.Source_Type, st.Subtype_Name, t.name,r.corecount,r.lwacount,r.comments , "
					+ "c.checknumber,c.checkamount "
					+ "from REMITTANCE r join UW_REMITTANCE_SOURCE s on s.id = r.remit_source_id "
					+ "left join UW_REMITTANCE_SUBTYPE st on st.id = remit_subtype_id "
					+ "join UW_REMITTANCE_TYPE t on t.id = r.remit_type_id "
					+ "left join [dbo].[UW_CHECK] c on c.remittanceId = r.remittanceNumber "
					+ "where r.RemittanceName = '" + remitName + "' order by r.[RemittanceID] desc;";
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
	 * This gets getRemitDataForSearch
	 * 
	 */
	public HashMap<String, String> getRemitDataForFilters() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			String query = "select top 1 r.RemittanceName as Remittance_Name,"
					+ " r.PostPeriod as Post_Period,r.remittanceNumber as Remittance_Number "
					+ ", s.Source_Type as Source , st.Subtype_Name as Sub_Type, t.name as Type"
					+ " ,r.corecount as Core,r.lwacount as LWA ,r.UnderwritingCount as UnderW, "
					+ "r.comments as Comment, "
					+ "r.CreateByUser as Created_By,r.locked_by as Locked_By,r.CreateByDate as Created_Date "
					+ "from REMITTANCE r join UW_REMITTANCE_SOURCE s on s.id = r.remit_source_id "
					+ "join UW_REMITTANCE_SUBTYPE st on st.id = remit_subtype_id "
					+ "join UW_REMITTANCE_TYPE t on t.id = r.remit_type_id "
					+ "where r.isDeleted = 0 and comments like '% %' "
					+ " and locked_by is not null order by r.[RemittanceID] desc;";
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
	 * This gets all data fields for remittance information under remittance list
	 * 
	 */
	public HashMap<String,String> getAllDataFieldsUnderRemittanceList(String remitName) throws Exception
	{
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			waitForSomeTime(1);
			//// connect to aul db
			aulDBConnect();
			///// execute query
			String query = "select r.PostPeriod,r.RemittanceNumber,r.RemittanceName,r.UnderwritingCount,"
					+ " s.Source_Type, st.Subtype_Name, t.name,r.corecount,r.lwacount,"
					+ "r.comments ,r.CreateByDate, r.Locked_by,r.CreateByUser "
					+ "from REMITTANCE r join UW_REMITTANCE_SOURCE s on s.id = r.remit_source_id "
					+ " left join UW_REMITTANCE_SUBTYPE st on st.id = remit_subtype_id "
					+ "join UW_REMITTANCE_TYPE t on t.id = r.remit_type_id  "
					+ "+left join [dbo].[UW_CHECK] c on c.remittanceId = r.remittanceNumber "
					+ "where r.RemittanceName = '" + remitName + "' order by r.[RemittanceID] desc;";
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
