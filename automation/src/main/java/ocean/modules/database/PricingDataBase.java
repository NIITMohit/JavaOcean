package ocean.modules.database;

import java.sql.ResultSet;
import java.util.HashMap;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * pricing Module
 * 
 * @author Mohit Goel
 */
public class PricingDataBase extends CommonFunctions {

	/**
	 * This function is used to get the turbo value before editing and after editing
	 * 
	 */
	public HashMap<String, String> getClassficationListForTurboBeforAndAfterEditing(String pricesheet, int value)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select * from  CLASS_GROUP " + "where CLASS_GROUP_NAME like '%" + pricesheet
					+ "%' and Turbo='" + value + "' ";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			// // save data in map
			dbMap = returnData(rs);

		} catch (Exception e) {
			throw e;
		} finally {
			// // close connection
			closeConnection();
		}

		return dbMap;
	}

	/**
	 * This function is used to get the Diesel value before editing and after
	 * editing
	 * 
	 */
	public HashMap<String, String> getClassficationListForDieselBeforeAndAfterEditing(String pricesheet, int value)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select * from  CLASS_GROUP " + " where CLASS_GROUP_NAME like '%" + pricesheet
					+ "%' and Diesel='" + value + "' ";
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
