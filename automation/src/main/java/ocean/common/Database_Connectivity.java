package ocean.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to achieve database connectivity and stores all db queries
 * and functions this is only single class to have all db related queries
 * 
 * @author Mohit Goel
 */
public class Database_Connectivity {

	public Connection conn;
	public Statement stmt;

	/**
	 * used to create db connectivity
	 * 
	 * @throws Exception
	 * 
	 */
	public void aulDBConnect() throws Exception {
		try {
			//// class to access jdbc sql driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//// connection string to connect to aul db
			String url = "jdbc:sqlserver://AUL-DEVDB-01\\DEVDB01;databaseName=OCEAN";
			//// create connection
			conn = DriverManager.getConnection(url, "niit.mohit", "MGNthre3");
			//// attached session
			stmt = conn.createStatement();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * used to close db
	 * 
	 * @throws Exception
	 * 
	 */
	public void closeConnection() throws SQLException {
		try {
			//// check if connection is open, close the same
			if (conn != null)
				//// close connection
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	/**
	 * Convert db data to hashmap and return a hashmap This function will only top
	 * row of db data
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> returnData(ResultSet rs1) throws Exception {
		//// Hash map to store columns and value
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			///// rssult set
			ResultSet rs = rs1;
			ResultSetMetaData metaData = rs.getMetaData();
			//// column count
			int count = metaData.getColumnCount();
			/// iterate all rows
			while (rs.next()) {
				for (int i = 1; i <= count; i++) {
					//// get column mane
					String col_name = metaData.getColumnName(i);
					//// get column type, like int, string etc
					String columnReturnType = metaData.getColumnTypeName(i);
					//// convert all column type to string and apened to hashmap
					String data = convertData(columnReturnType, rs, i);
					dbMap.put(col_name, data);
				}
				break;
			}

		} catch (Exception e) {
			//// exception
			throw e;
		} finally {
			closeConnection();
		}
		/// return map
		return dbMap;

	}

	/**
	 * Convert db data to hashmap and return a hashmap This function will convert
	 * all db data
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> returnAllData(ResultSet rs1) throws Exception {
		//// Hash map to store columns and value
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> mapp = new HashMap<String, String>();
		try {
			int counter = 1;
			///// rssult set
			ResultSet rs = rs1;
			ResultSetMetaData metaData = rs.getMetaData();
			//// column count
			int count = metaData.getColumnCount();
			/// iterate all rows
			while (rs.next()) {
				for (int i = 1; i <= count; i++) {
					//// get column mane
					String col_name = metaData.getColumnName(i);
					//// get column type, like int, string etc
					String columnReturnType = metaData.getColumnTypeName(i);
					//// convert all column type to string and apened to hashmap
					String data = convertData(columnReturnType, rs, i);
					mapp.put(col_name, data);
				}
				dbMap.put(counter, mapp);
				counter++;
			}

		} catch (Exception e) {
			//// exception
			throw e;
		} finally {
			closeConnection();
		}
		/// return map
		return dbMap;

	}

	/**
	 * This function is used to convert db data like int, date etc to string
	 * 
	 */
	public String convertData(String columnReturnType, ResultSet rs, int i) throws SQLException {
		switch (columnReturnType) {
		case "int":
			return Integer.toString(rs.getInt(i));
		case "nvarchar":
			return rs.getString(i);
		case "string":
			return rs.getString(i);
		case "float":
			return Float.toString(rs.getFloat(i));
		default:
			return rs.getString(i);
		}
	}

	/**
	 * Database function to getpricesheet data based on price sheet code
	 * 
	 * @throws Exception
	 * 
	 */
	public HashMap<String, String> getPriceSheetList(String priceSheetCode) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery("select * from [dbo].[ACCOUNT_TYPE]");
			//// save data in map
			dbMap = returnData(rs);

			String annbc = dbMap.get("ID");
			System.out.println(annbc);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}

	/**
	 * This gets PendingContractwithRemittance
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getPendingContractwithRemittance() throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(
					"select top 2 r.RemittanceNumber,r.RemittanceName,d.FILE_NAME from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID where "
							+ "d.status_id = 4 and DOCUMENTTYPEID = 1 and r .IsDeleted = 0 order by d.CreateByDate desc;");
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
	 * This gets DataSetforSearch
	 * 
	 */
	public HashMap<String, String> getDataSetforSearch(HashMap<String, String> searchParamater) throws Exception {
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
	public HashMap<String, String> getSearchDataCountOnCancellationScreen(HashMap<String, String> searchParamater)
			throws Exception {
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
	public String getContractIdBasedOnStatus(String status) throws Exception {
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
	public HashMap<String, String> getContractIdBasedOnStatusAndPriceSheet(String status, String priceSheet)
			throws Exception {
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
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> getCancellationMouduleSearchData(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select sales.CERT as Contract_Number,price.INTERNAL_NAME as PriceSheet_Name,sales.PROGRAM_CODE, "
					+ " account.NAME as Primary_Account,account.ROLE_IDENTIFIER, "
					+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) AS customer_name,statuss.Name as contractStatus ,summary.COMMENTS "
					+ "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] account on account.id =  "
					+ "sales.PRIMARY_ACCOUNT_ID join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
					+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
					+ " left join [dbo].[ALLSALES_DETAILS_LOG] summary on sales.id = "
					+ "summary.ALLSALES_DETAILS_ID where sales.CERT = '" + contractId
					+ "' order by summary.LOG_SEQ desc;";

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
