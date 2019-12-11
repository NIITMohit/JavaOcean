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

	/**
	 * This gets search all account info data based on roleId, roleType, roleStatus
	 *
	 */
	public HashMap<String, String> account_getAccountInfoOnAccountDetails(String roleId, String roleType,
			String roleStatus) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select account.Name as Account_Name,accType.Name as Account_Type,account.ROLE_IDENTIFIER as Role_Id, "
					+ "accountRole.ROLE_NAME as Role_Type, account.REF_IDENTIFIER as Reference_Id,accStatus.STATUS as Status, "
					+ "account.DATE_SIGNED as Date_Signed "
					+ "from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
					+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
					+ "' and account.ROLE_IDENTIFIER = '" + roleId + "';";

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
	 * This gets search physical address info data based on roleId, roleType,
	 * roleStatus
	 *
	 */
	public String account_getPhysicalAddressOnAccountDetails(String roleId, String roleType, String roleStatus)
			throws Exception {
		String physicalAddress = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select CONCAT(address.STREET,address.CITY,address.STATE,address.COUNTRY, "
					+ "address.ZIP_CODE,'Phone:',phone.PHONE,'Fax:',phone.Fax) as physicalAddress "
					+ "from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
					+ "join ACCOUNT_ADDRESS address on address.ACCOUNT_ID = account.id "
					+ "join [dbo].[ACCOUNT_PHONE_FAX] phone on phone.ACCOUNT_ID = account.id "
					+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
					+ "' and account.ROLE_IDENTIFIER = '" + roleId + "' and address.ADDRESS_TYPE_ID = 1;";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			physicalAddress = dbMap.get("physicalAddress");
			physicalAddress.replace(",", "");
			physicalAddress.replace(" ", "");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return physicalAddress;
	}

	/**
	 * This gets search billing address info data based on roleId, roleType,
	 * roleStatus
	 *
	 */
	public String account_getBillingAddressOnAccountDetails(String roleId, String roleType, String roleStatus)
			throws Exception {
		String physicalAddress = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select CONCAT(address.STREET,address.CITY,address.STATE,address.COUNTRY, "
					+ "address.ZIP_CODE,'Phone:',phone.PHONE,'Fax:',phone.Fax) as physicalAddress "
					+ "from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
					+ "join ACCOUNT_ADDRESS address on address.ACCOUNT_ID = account.id "
					+ "join [dbo].[ACCOUNT_PHONE_FAX] phone on phone.ACCOUNT_ID = account.id "
					+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
					+ "' and account.ROLE_IDENTIFIER = '" + roleId + "' and address.ADDRESS_TYPE_ID = 3;";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			physicalAddress = dbMap.get("physicalAddress");
			physicalAddress.replace(",", "");
			physicalAddress.replace(" ", "");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return physicalAddress;
	}

	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getAccountUnderwritingWarningAccountDetails(String roleId, String roleType, String roleStatus)
			throws Exception {
		String physicalAddress = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 warning_text from [dbo].[ACCOUNT_WARNING] where account_id in("
					+ "select account.id from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
					+ "' and account.ROLE_IDENTIFIER = '" + roleId + "') and warning_type = 1 order by 1 desc;";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			physicalAddress = dbMap.get("physicalAddress");
			physicalAddress.replace(",", "");
			physicalAddress.replace(" ", "");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return physicalAddress;
	}

	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getAccountCancellationWarningAccountDetails(String roleId, String roleType, String roleStatus)
			throws Exception {
		String physicalAddress = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 warning_text from [dbo].[ACCOUNT_WARNING] where account_id in("
					+ "select account.id from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
					+ "account.ROLE_TYPE_ID = accountRole.id "
					+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
					+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
					+ "' and account.ROLE_IDENTIFIER = '" + roleId + "') and warning_type = 2 order by 1 desc;";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			physicalAddress = dbMap.get("physicalAddress");
			physicalAddress.replace(",", "");
			physicalAddress.replace(" ", "");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return physicalAddress;
	}

	
	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getPriceSheetUnderwritingWarningAccountDetails(String internalName) throws Exception {
		String physicalAddress = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 WARNING_TEXT from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pa "
					+ "on p.id = pa.pricesheet_id "
					+ "join [dbo].[PRICESHEET_WARNING] w on pa.id = w.PRICESHEET_RELATION_ID "
					+ "where INTERNAL_NAME = '" + internalName + "' and WARNING_TYPE = 3 order by w.id desc";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			physicalAddress = dbMap.get("physicalAddress");
			physicalAddress.replace(",", "");
			physicalAddress.replace(" ", "");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return physicalAddress;
	}
	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getPriceSheetCancellationWarningAccountDetails(String internalName) throws Exception {
		String physicalAddress = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 WARNING_TEXT from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pa "
					+ "on p.id = pa.pricesheet_id "
					+ "join [dbo].[PRICESHEET_WARNING] w on pa.id = w.PRICESHEET_RELATION_ID "
					+ "where INTERNAL_NAME = '" + internalName + "' and WARNING_TYPE = 4 order by w.id desc";

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			//// save data in map
			dbMap = returnData(rs);
			physicalAddress = dbMap.get("physicalAddress");
			physicalAddress.replace(",", "");
			physicalAddress.replace(" ", "");

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return physicalAddress;
	}
}
