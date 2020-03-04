package ocean.modules.database;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	 * This gets search billing address info data based on roleId, roleType,
	 * roleStatus
	 *
	 */
	public HashMap<Integer, HashMap<String, String>> account_getPropertyInfoOnAccountDetails(String roleId,
			String roleType, String roleStatus) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			String query = "select p.name as Name,val.STRING_VALUE as Message from [dbo].[ACCOUNT_PROPERTY_GROUP] g join [dbo].[ACCOUNT_PROPERTY] p "
					+ "on g.PROPERTY_VALUE_ID = p.id " + "join [dbo].[ACCOUNT] aa on aa.id = g.ACCOUNT_ID "
					+ "join [dbo].[ACCOUNT_PROPERTY_VALUE] val on val.ACCOUNT_PROPERTY_ID = p.id "
					+ "join  [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID "
					+ "join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on aa.ROLE_TYPE_ID = accountRole.id "
					+ "where p.is_visible = 1 and p.Name in('Corp ID','Corp Name','Group ID','Group Name') "
					+ "and val.IS_DELETED = 0 " + "and aa.ROLE_IDENTIFIER = '" + roleId + "'"
					+ " and accStatus.STATUS = '" + roleStatus + "' " + " and  accountRole.ROLE_NAME = '" + roleType
					+ "';";

			aulDBConnect();
			///// execute query
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
	 * This gets search billing address info data based on roleId, roleType,
	 * roleStatus
	 *
	 */
	public HashMap<Integer, HashMap<String, String>> account_getCorpAndGroupOnAccountDetails(String roleId,
			String roleType, String roleStatus) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			String query = "select p.name as Name,val.STRING_VALUE as Message from [dbo].[ACCOUNT_PROPERTY_GROUP] g join [dbo].[ACCOUNT_PROPERTY] p "
					+ "on g.PROPERTY_VALUE_ID = p.id " + "join [dbo].[ACCOUNT] aa on aa.id = g.ACCOUNT_ID "
					+ "join [dbo].[ACCOUNT_PROPERTY_VALUE] val on val.ACCOUNT_PROPERTY_ID = p.id "
					+ "join  [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID "
					+ "join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on aa.ROLE_TYPE_ID = accountRole.id "
					+ "where p.is_visible = 1 and p.Name in('Corp ID','Corp Name','Group ID','Group Name') "
					+ "and val.IS_DELETED = 0 " + "and aa.ROLE_IDENTIFIER = '" + roleId + "'"
					+ " and accStatus.STATUS = '" + roleStatus + "' " + " and  accountRole.ROLE_NAME = '" + roleType
					+ "';";

			aulDBConnect();
			///// execute query
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

	/**
	 * This gets exceptions which we have added in pricsheet based on internal name
	 *
	 */
	public HashMap<Integer, HashMap<String, String>> validateExceptionDataOnTheBasisOfInternalName(String internalName)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {

			String query = "select ap.INTERNAL_NAME as InternalName, q.CATEGORY_VALUE as ExceptionType ,"
					+ " q.CLASS as Class,q.COVERAGE as Coverage, q.TERM as Term "
					+ " from dbo.PRICING_PRICESHEET_ACCOUNT_RELATION as p"
					+ " join dbo.pricing_pricesheet as ap on p.pricesheet_id= ap.id  "
					+ " join dbo.account ac on p.payee_id = ac.id "
					+ "join PRICESHEET_PRODUCT_TIER q on  ap.id=q.[PRICESHEET_ID] "
					+ "join PRICESHEET_PRODUCT_TIER_TARGET r on q.id=r.TIER_ID"
					+ " join [PRICESHEET_TIER_TARGET_PROPERTY] s on r.TIER_TARGET_PROPERTY_ID =s.ID "
					+ "where ap.internal_name like '" + internalName + "' ";
			aulDBConnect();
			// /// execute query
			ResultSet rs = stmt.executeQuery(query);
			// // save data in map
			dbMap = returnAllData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			// // close connection
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * This function correct all data needed PriceSheetVisibility
	 * 
	 */
	public HashMap<String, String> setAllDataForPriceSheetVisibility(HashMap<String, String> map) throws Exception {
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
			String query = "select top 1 p.id as pricesheetId "
					+ ",a.role_identifier as dealerid, p.CODE as pcode , pac.EFFECTIVEDATE as MainPSeffDate, "
					+ "tp.EFFECTIVE_DATE as EffDate2,tt.EFFECTIVE_DATE as effDate3 "
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
					+ "%' and a.account_status_id = 1 and a.role_type_id = " + map.get("ROLETYPE") + " order by 1 desc";
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
			dbMap.put("PRICESHEETINTERNALCODE", dbMap1.get("pcode"));
			dbMap.put("PRICESHEETMAINEFFECTIVEDATE", dbMap1.get("MainPSeffDate"));
			dbMap.put("PRICESHEETMAINEFFECTIVEDATEEXCEPTION1", dbMap1.get("EffDate2"));
			dbMap.put("PRICESHEETMAINEFFECTIVEDATEEXCEPTION2", dbMap1.get("effDate3"));
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;
	}

}
