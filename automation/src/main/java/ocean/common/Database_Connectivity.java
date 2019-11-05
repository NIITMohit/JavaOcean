package ocean.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * This class is used to achieve database connectivity and stores all db queries
 * and functions this is only single class to have all db related queries
 * 
 * @author Mohit Goel
 */
public class Database_Connectivity {

	/**
	 * used to create db connectivity
	 * @throws Exception 
	 * 
	 */
	public static void aulDBConnect() throws Exception {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://AUL-DEVDB-01\\DEVDB01;databaseName=OCEAN";
			Connection con = DriverManager.getConnection(url, "niit.mohit", "MGNthre3");
			Statement stmt = con.createStatement();
			// ResultSet rs = stmt.executeQuery("select * from [dbo].[ACCOUNT_TYPE]");
			// while (rs.next())
			// System.out.println(rs.getInt(1) + " " + rs.getString(2));
		} catch (Exception e) {
			throw e;
		}
	}

}
