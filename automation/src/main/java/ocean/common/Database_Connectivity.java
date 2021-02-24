package ocean.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * This class is used to achieve database connectivity and stores all db queries
 * and functions this is only single class to have all db related queries
 * 
 * @author Mohit Goel
 */
public class Database_Connectivity {

	public static Connection conn;
	public static Statement stmt;

	/**
	 * used to create db connectivity
	 * 
	 * @throws Exception
	 * 
	 */
	public static void aulDBConnect() throws Exception {
		try {
			//// class to access jdbc sql driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//// connection string to connect to aul db
			String url = "jdbc:sqlserver://AUL-DEVDB-01;databaseName=OCEAN";
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
	public static void closeConnection() throws SQLException {
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
	public static HashMap<String, String> returnData(ResultSet rs1) throws Exception {
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
					if (data == null)
						dbMap.put(col_name.trim(), "");
					else
						dbMap.put(col_name.trim(), data.trim());

				}
				break;
			}

		} catch (Exception e) {
			//// exception
			System.out.println(e.toString());
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
	public static HashMap<Integer, HashMap<String, String>> returnAllData(ResultSet rs1) throws Exception {
		//// Hash map to store columns and value
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			int counter = 1;
			///// rssult set
			ResultSet rs = rs1;
			ResultSetMetaData metaData = rs.getMetaData();
			//// column count
			int count = metaData.getColumnCount();
			/// iterate all rows
			while (rs.next()) {
				HashMap<String, String> mapp = new HashMap<String, String>();
				for (int i = 1; i <= count; i++) {
					//// get column mane
					String col_name = metaData.getColumnName(i);
					//// get column type, like int, string etc
					String columnReturnType = metaData.getColumnTypeName(i);
					//// convert all column type to string and apened to hashmap
					String data = convertData(columnReturnType, rs, i);
					if (data == null)
						mapp.put(col_name.trim(), "");
					else
						mapp.put(col_name.trim(), data.trim());
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
	public static String convertData(String columnReturnType, ResultSet rs, int i) throws SQLException {
		switch (columnReturnType) {
		case "int":
			return Integer.toString(rs.getInt(i));
		case "nvarchar":
			return rs.getString(i);
		case "string":
			return rs.getString(i);
		case "float":
			return Float.toString(rs.getFloat(i));
		case "bigint":
			return rs.getString(i);
		case "decimal":
			BigDecimal decimalValue = rs.getBigDecimal(i);
			if (decimalValue == null)
				return null;
			return rs.getBigDecimal(i).toString();
		// return df.format(rs.get(i));
		default:
			return rs.getString(i);
		}
	}

	/**
	 * This gets PendingContractwithRemittance
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> pricing_underwriting_getPendingContractwithRemittance()
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(
					"select * from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID where "
							+ "d.status_id = 4 and DOCUMENTTYPEID = 1 and r.IsDeleted = 0 "
							+ "and r.RemittanceID in (select r.RemittanceID from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID "
							+ "join  [dbo].[UW_BUSINESS_PROCESSOR_CHECK] ccc on r.RemittanceNumber = ccc.REMITTANCE_NUMBER "
							+ " where DOCUMENTTYPEID = 2) " + "order by d.CreateByDate desc");
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
	 * This gets PendingContractwithRemittance
	 * 
	 */
	public HashMap<String, String> getUnderWContractForBListLender() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(
					"select r.RemittanceName,asd.CERT,asd.SECONDARY_ACCOUNT_ID from ALLSALES_DETAILS asd join REMITTANCE r on asd.REMITTANCE_ID = r.RemittanceID "
							+ " where r.REMITTANCE_STATUS_ID = 1 " + "and asd.CONTRACT_STATUS_ID = 1 "
							+ "and asd.SECONDARY_ACCOUNT_ID in (select ac.id from [dbo].[ACCOUNT_PROPERTY] p join [dbo].[ACCOUNT_PROPERTY_VALUE] v on p.id = v.account_property_id "
							+ "join [dbo].[ACCOUNT_PROPERTY_GROUP] g on g.property_value_id = v.id "
							+ "join  [dbo].[ACCOUNT] ac on ac.id = g.account_id " + "where STRING_VALUE in ('B-List')) "
							+ " order by 1 desc;");
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
	 * This gets PendingContractwithRemittance
	 * 
	 */
	public String roleIdentfier(String roldid) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(
					"select  ac.ROLE_IDENTIFIER from [dbo].[ACCOUNT_PROPERTY] p join [dbo].[ACCOUNT_PROPERTY_VALUE] v on p.id = v.account_property_id "
							+ "join [dbo].[ACCOUNT_PROPERTY_GROUP] g on g.property_value_id = v.id "
							+ "join  [dbo].[ACCOUNT] ac on ac.id = g.account_id "
							+ "where STRING_VALUE in ('B-List') and ac.id not in ('" + roldid
							+ "') order by ROLE_IDENTIFIER;");
			//// save data in map
			dbMap = returnData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap.get("ROLE_IDENTIFIER");

	}

	/**
	 * This gets OnHoldContractwithRemittance
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> pricing_underwriting_getOnHoldContractwithRemittance()
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query

			ResultSet rs = stmt.executeQuery(
					"select top 1 r.RemittanceNumber,r.RemittanceName,d.FILE_NAME  from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID where "
							+ " d.status_id in(2) and remittance_status_id not in(3,2) and DOCUMENTTYPEID = 1 and r.IsDeleted = 0 order by d.CreateByDate desc;");
			//// save
			//// data //// in
			//// map
			dbMap = returnAllData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;

	}

	public HashMap<Integer, HashMap<String, String>> pricing_underwriting_getOnHoldContract(String cert)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			//// connect to aul db
			aulDBConnect();
			///// execute query

			ResultSet rs = stmt.executeQuery(
					"select top 1 r.RemittanceNumber,r.RemittanceName,d.FILE_NAME ,s.cert,d.ORIGINALFILENAME from [dbo].[REMITTANCE] r join"
							+ " [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID "
							+ " join [ALLSALES_DETAILS] s  on s.id = d.[ALLSALES_DETAILS_ID] where "
							+ " d.status_id in(2) and remittance_status_id not in(3) and DOCUMENTTYPEID = 1 "
							+ " and r.IsDeleted = 0 and s.cert = '" + cert + "' order by d.CreateByDate desc;");
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
}
