package ocean.common;

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
	 * Convert db data to hashmap and return a hashmap
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

	public HashMap<Integer, HashMap<String, String>> getDataSetforSearch(HashMap<String, String> searchParamater) throws Exception {
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

}
