package ocean.modules.database;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * accounts Module
 * 
 * @author Mohit Goel
 */
public class AccountsDataBase extends CommonFunctions {
	/**
	 * This gets SearchDataOnAccountSearchScreen
	 * 
	 */
	public HashMap<String, String> accounts_getDataSetforSearch(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String query1 = "select top " + "1" + " ";
			String query2 = " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID where ";
			String myKey = "";
			String myvalue = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					String value1 = "";
					if (key.equals("Account_Name")) {
						key = " account.name as Account_Name ";
						value1 = " account.name ";
					}
					if (key.equals("Role_Id")) {
						key = " account.ROLE_IDENTIFIER as Role_Id ";
						value1 = " account.ROLE_IDENTIFIER ";
					}
					if (key.equals("Role_Type")) {
						key = " accountRole.ROLE_NAME as Role_Type ";
						value1 = " accountRole.ROLE_NAME ";
					}
					if (key.equals("Address")) {
						key = " accAddress.STREET as Address ";
						value1 = " accAddress.STREET ";
					}

					if (key.equals("City")) {
						key = " accAddress.CITY ";
						value1 = " accAddress.CITY ";
					}

					if (key.equals("State")) {
						key = " accAddress.STATE ";
						value1 = " accAddress.STATE ";
					}

					if (key.equals("Zip_Code")) {
						key = " accAddress.ZIP_CODE ";
						value1 = " accAddress.ZIP_CODE ";
					}
					if (key.equals("Status")) {
						key = " accStatus.STATUS ";
						value1 = " accStatus.STATUS ";
					}
					myKey = myKey + key + ",";
					myvalue = myvalue + value1 + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else {
					String value1 = "";
					if (key.equals("Account_Name")) {
						key = " account.name as Account_Name ";
						value1 = " account.name ";
					}
					if (key.equals("Role_Id")) {
						key = " account.ROLE_IDENTIFIER as Role_Id ";
						value1 = " account.ROLE_IDENTIFIER ";
					}
					if (key.equals("Role_Type")) {
						key = " accountRole.ROLE_NAME as Role_Type ";
						value1 = " accountRole.ROLE_NAME ";
					}
					if (key.equals("Address")) {
						key = " accAddress.STREET as Address ";
						value1 = " accAddress.STREET ";
					}

					if (key.equals("City")) {
						key = " accAddress.CITY ";
						value1 = " accAddress.CITY ";
					}

					if (key.equals("State")) {
						key = " accAddress.STATE ";
						value1 = " accAddress.STATE ";
					}

					if (key.equals("Zip_Code")) {
						key = " accAddress.ZIP_CODE ";
						value1 = " accAddress.ZIP_CODE ";
					}
					if (key.equals("Status")) {
						key = " accStatus.STATUS ";
						value1 = " accStatus.STATUS ";
					}
					myKey = myKey + key + ",";
					if (key.equals(" account.ROLE_IDENTIFIER as Role_Id "))
						myvalue = myvalue + value1 + " = " + value + " and ";
					else
						myvalue = myvalue + value1 + " = '" + value + "' and ";
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
	 * This gets SearchDataCountOnAccountSearchScreen
	 * 
	 */
	public HashMap<String, String> account_getSearchDataCountOnAccountsScreen(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String query1 = "select count(1) as count";
			String query2 = " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID where ";
			String myKey = "";
			String myvalue = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					String value1 = "";
					if (key.equals("Account_Name")) {
						key = " account.name as Account_Name ";
						value1 = " account.name ";
					}
					if (key.equals("Role_Id")) {
						key = " account.ROLE_IDENTIFIER as Role_Id ";
						value1 = " account.ROLE_IDENTIFIER ";
					}
					if (key.equals("Role_Type")) {
						key = " accountRole.ROLE_NAME as Role_Type ";
						value1 = " accountRole.ROLE_NAME ";
					}
					if (key.equals("Address")) {
						key = " accAddress.STREET as Address ";
						value1 = " accAddress.STREET ";
					}

					if (key.equals("City")) {
						key = " accAddress.CITY ";
						value1 = " accAddress.CITY ";
					}

					if (key.equals("State")) {
						key = " accAddress.STATE ";
						value1 = " accAddress.STATE ";
					}

					if (key.equals("Zip_Code")) {
						key = " accAddress.ZIP_CODE ";
						value1 = " accAddress.ZIP_CODE ";
					}
					if (key.equals("Status")) {
						key = " accStatus.STATUS ";
						value1 = " accStatus.STATUS ";
					}
					myKey = myKey + key + ",";
					myvalue = myvalue + value1 + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing f
				} else {
					String value1 = "";
					if (key.equals("Account_Name")) {
						key = " account.name as Account_Name ";
						value1 = " account.name ";
					}
					if (key.equals("Role_Id")) {
						key = " account.ROLE_IDENTIFIER as Role_Id ";
						value1 = " account.ROLE_IDENTIFIER ";
					}
					if (key.equals("Role_Type")) {
						key = " accountRole.ROLE_NAME as Role_Type ";
						value1 = " accountRole.ROLE_NAME ";
					}
					if (key.equals("Address")) {
						key = " accAddress.STREET as Address ";
						value1 = " accAddress.STREET ";
					}

					if (key.equals("City")) {
						key = " accAddress.CITY ";
						value1 = " accAddress.CITY ";
					}

					if (key.equals("State")) {
						key = " accAddress.STATE ";
						value1 = " accAddress.STATE ";
					}

					if (key.equals("Zip_Code")) {
						key = " accAddress.ZIP_CODE ";
						value1 = " accAddress.ZIP_CODE ";
					}
					if (key.equals("Status")) {
						key = " accStatus.STATUS ";
						value1 = " accStatus.STATUS ";
					}
					myKey = myKey + key + ",";
					if (key.equals(" account.ROLE_IDENTIFIER as Role_Id "))
						myvalue = myvalue + value1 + " = " + value + " and ";
					else if (key.equals(" account.name as Account_Name ")) {
						myvalue = myvalue + value1 + " like '%" + value + "%' and ";
					} else
						myvalue = myvalue + value1 + " = '" + value + "' and ";

				}
			}
			query =  query1 + query2 + myvalue;
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
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> account_getAccountMouduleSearchData(String roleId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select account.name as Account_Name, account.ROLE_IDENTIFIER as Role_Id , accountRole.ROLE_NAME as Role_Type ,"
					+ " accAddress.STREET as Address,  accAddress.CITY , accAddress.STATE, accAddress.ZIP_CODE,accStatus.STATUS , "
					+ " accType.name as Account_Type "
					+ " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID"
					+ " where account.ROLE_IDENTIFIER = " + roleId + ";";

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
