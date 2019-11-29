package ocean.modules.database;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import ocean.common.CommonFunctions;

public class SearchDataBase extends CommonFunctions {
	/**
	 * This gets DataSetforSearch
	 * 
	 */
	public HashMap<String, String> search_getDataSetforSearch(HashMap<String, String> searchParamater)
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
}
