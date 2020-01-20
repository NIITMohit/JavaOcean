package ocean.modules.database;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * search Module
 * 
 * @author Mohit Goel
 */
public class SearchDataBase extends CommonFunctions {
	/**
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<String, String> search_Search(HashMap<String, String> searchParamater) throws Exception {
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
			waitForSomeTime(1);
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
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<String, String> searchCountFromDatabase(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String query1 = "select count(1) as count";
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
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<String, String> searchDetailsFromSearchData(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query1 = "select asd.CERT as Contract, asd.PROGRAM_CODE as Code, asd.CUSTOMER_LAST as Last_Name, asd.CUSTOMER_FIRST as First_Name, "
					+ "asd.SALE_DATE as Sale_Date, asd.TRANS_DATE as Trans_Date, " + "stat.NAME as Status, "
					+ "asd.VIN as VIN,asd.PHONE as Phone, asd.STATE as State, " + "aaa.Name as Secondary_Seller_Name, "
					+ "aaa.ROLE_IDENTIFIER as Secondary_Seller_Id, " + "accType.Role_Name as Secondary_Seller_Type "
					+ "from AllSales_Details asd join UW_CONTRACT_STATUS stat  "
					+ "on stat.Id = asd.contract_status_id left join [dbo].[ACCOUNT] aaa on aaa.id = asd.secondary_Account_id "
					+ "join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = aaa.Role_Type_Id " + "where asd.CERT = '"
					+ contractId + "';";
			String query2 = "select aaa.ROLE_IDENTIFIER as Primary_Seller_Id, aaa.NAME as Prmary_Seller_Name, "
					+ "accType.ROLE_NAME as Primary_Seller_Type, aaaaaaa.ROLE_IDENTIFIER as Primary_Payee_Id "
					+ "from AllSales_Details asd left join [dbo].[ACCOUNT] aaa on aaa.id = asd.primary_Account_id "
					+ "join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = aaa.Role_Type_Id "
					+ "join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pp on pp.PRICESHEET_ID = asd.PRICESHEET_ID "
					+ "join [dbo].[ACCOUNT] aaaaaaa on aaaaaaa.id = pp.PAYEE_ID " + "where asd.CERT = '" + contractId
					+ "';";
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query1);
			//// save data in map
			dbMap = returnData(rs);
			aulDBConnect();
			ResultSet rs1 = stmt.executeQuery(query2);
			//// save data in map
			dbMap.putAll(returnData(rs1));

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}
}
