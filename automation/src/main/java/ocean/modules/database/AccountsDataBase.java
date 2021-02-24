package ocean.modules.database;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		query = query.substring(0, query.lastIndexOf("and")) + " order by 1 desc ;";
		query = query.substring(0, query.lastIndexOf(","))
				+ query.substring(query.lastIndexOf(",") + 1, query.length());
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets SearchDataCountOnAccountSearchScreen
	 * 
	 */
	public HashMap<String, String> account_getSearchDataCountOnAccountsScreen(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
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
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	public HashMap<Integer, HashMap<String, String>> account_getSearchDataCountOnAccountsScreen2(
			HashMap<String, String> searchParamater) throws Exception {
		String query = "";
		String query1 = "select account.ROLE_IDENTIFIER as Role_Id";
		String query2 = " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "left join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID where address_type_id = 1 and ";
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
		return getAllDataFromDatabase(query);
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> account_getAccountMouduleSearchData(String roleId, String role_type, String Status)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select account.name as Account_Name, account.ROLE_IDENTIFIER as Role_Id , accountRole.ROLE_NAME as Role_Type ,"
				+ " accAddress.STREET as Address,  accAddress.CITY , accAddress.STATE, accAddress.ZIP_CODE,accStatus.STATUS , "
				+ " accType.name as Account_Type "
				+ " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID"
				+ " where address_type_id = 1 and account.ROLE_IDENTIFIER = " + roleId + " and accStatus.STATUS = '"
				+ Status + "' and accountRole.ROLE_NAME = '" + role_type + "';";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all account info data based on roleId, roleType, roleStatus
	 *
	 */
	public HashMap<String, String> account_getAccountInfoOnAccountDetails(String roleId, String roleType,
			String roleStatus) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select account.Name as Account_Name,accType.Name as Account_Type,account.ROLE_IDENTIFIER as Role_Id, "
				+ "accountRole.ROLE_NAME as Role_Type, account.REF_IDENTIFIER as Reference_Id,accStatus.STATUS as Status "
				+ " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
				+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
				+ "' and account.ROLE_IDENTIFIER = '" + roleId + "';";
		dbMap = getTopRowDataFromDatabase(query);
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
		String query = "select CONCAT(address.STREET,address.CITY,address.STATE,address.COUNTRY, "
				+ "address.ZIP_CODE,'Phone:',phone.PHONE,'Fax:',phone.Fax) as physicalAddress "
				+ "from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
				+ "left join ACCOUNT_ADDRESS address on address.ACCOUNT_ID = account.id "
				+ "left join [dbo].[ACCOUNT_PHONE_FAX] phone on phone.ACCOUNT_ID = account.id "
				+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
				+ "' and account.ROLE_IDENTIFIER = '" + roleId + "' and address.ADDRESS_TYPE_ID = 1;";
		dbMap = getTopRowDataFromDatabase(query);
		physicalAddress = dbMap.get("physicalAddress");
		physicalAddress = physicalAddress.replaceAll(",", "");
		physicalAddress = physicalAddress.replaceAll(" ", "");
		physicalAddress = physicalAddress.replaceAll("N/A", "");
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

		String query = "select CONCAT(address.STREET,address.CITY,address.STATE,address.COUNTRY, "
				+ "address.ZIP_CODE,'Phone:',phone.PHONE,'Fax:',phone.Fax) as physicalAddress "
				+ "from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
				+ "left join ACCOUNT_ADDRESS address on address.ACCOUNT_ID = account.id "
				+ "left join [dbo].[ACCOUNT_PHONE_FAX] phone on phone.ACCOUNT_ID = account.id "
				+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
				+ "' and account.ROLE_IDENTIFIER = '" + roleId + "' and address.ADDRESS_TYPE_ID = 3;";
		dbMap = getTopRowDataFromDatabase(query);
		physicalAddress = dbMap.get("physicalAddress");
		physicalAddress = physicalAddress.replace(",", "");
		physicalAddress = physicalAddress.replace(" ", "");
		physicalAddress = physicalAddress.replaceAll("N/A", "");
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
		String query = "select p.name as Name,val.STRING_VALUE as Message from [dbo].[ACCOUNT_PROPERTY_GROUP] g join [dbo].[ACCOUNT_PROPERTY] p "
				+ "on g.PROPERTY_VALUE_ID = p.id " + "join [dbo].[ACCOUNT] aa on aa.id = g.ACCOUNT_ID "
				+ "join [dbo].[ACCOUNT_PROPERTY_VALUE] val on val.ACCOUNT_PROPERTY_ID = p.id "
				+ "join  [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID "
				+ "join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on aa.ROLE_TYPE_ID = accountRole.id "
				+ "where p.is_visible = 1 and p.Name in('Corp ID','Corp Name','Group ID','Group Name') "
				+ "and val.IS_DELETED = 0 " + "and aa.ROLE_IDENTIFIER = '" + roleId + "'" + " and accStatus.STATUS = '"
				+ roleStatus + "' " + " and  accountRole.ROLE_NAME = '" + roleType + "';";
		dbMap = getAllDataFromDatabase(query);
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
		String query = "select p.name as Name,val.STRING_VALUE as Message from [dbo].[ACCOUNT_PROPERTY_GROUP] g join [dbo].[ACCOUNT_PROPERTY] p "
				+ "on g.PROPERTY_VALUE_ID = p.id " + "join [dbo].[ACCOUNT] aa on aa.id = g.ACCOUNT_ID "
				+ "join [dbo].[ACCOUNT_PROPERTY_VALUE] val on val.ACCOUNT_PROPERTY_ID = p.id "
				+ "join  [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID "
				+ "join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on aa.ROLE_TYPE_ID = accountRole.id "
				+ "where p.is_visible = 1 and p.Name in('Corp ID','Corp Name','Group ID','Group Name') "
				+ "and val.IS_DELETED = 0 " + "and aa.ROLE_IDENTIFIER = '" + roleId + "'" + " and accStatus.STATUS = '"
				+ roleStatus + "' " + " and  accountRole.ROLE_NAME = '" + roleType + "';";
		dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getAccountUnderwritingWarningAccountDetails(String roleId, String roleType, String roleStatus)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 warning_text from [dbo].[ACCOUNT_WARNING] where account_id in("
				+ "select account.id from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
				+ "' and account.ROLE_IDENTIFIER = '" + roleId + "') and warning_type = 1 order by 1 desc;";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("warning_text");
	}

	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getAccountCancellationWarningAccountDetails(String roleId, String roleType, String roleStatus)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 warning_text from [dbo].[ACCOUNT_WARNING] where account_id in("
				+ "select account.id from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on "
				+ "account.ROLE_TYPE_ID = accountRole.id "
				+ "join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ "where accountRole.ROLE_NAME = '" + roleType + "' and accStatus.STATUS = '" + roleStatus
				+ "' and account.ROLE_IDENTIFIER = '" + roleId + "') and warning_type = 2 order by 1 desc;";

		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("warning_text");
	}

	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getPriceSheetUnderwritingWarningAccountDetails(String internalName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 WARNING_TEXT from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pa on p.id = pa.pricesheet_id "
				+ " join [dbo].[PRICESHEET_WARNING] w on pa.id = w.PRICESHEET_RELATION_ID where INTERNAL_NAME = '"
				+ internalName + "' and WARNING_TYPE = 3 order by w.id desc";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("WARNING_TEXT");
	}

	/**
	 * This gets account_getAccountUnderwritingWarningAccountDetails based on
	 * roleId, roleType, roleStatus
	 *
	 */
	public String account_getPriceSheetCancellationWarningAccountDetails(String internalName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 WARNING_TEXT from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pa "
				+ "on p.id = pa.pricesheet_id "
				+ "join [dbo].[PRICESHEET_WARNING] w on pa.id = w.PRICESHEET_RELATION_ID where INTERNAL_NAME = '"
				+ internalName + "' and WARNING_TYPE = 4 order by w.id desc";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("WARNING_TEXT");
	}

	/**
	 * This gets exceptions which we have added in pricsheet based on internal name
	 *
	 */
	public HashMap<Integer, HashMap<String, String>> validateExceptionDataOnTheBasisOfInternalName(String internalName)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		String query = "select ap.INTERNAL_NAME as internalName, q.CATEGORY_VALUE as ExceptionType ,"
				+ "q.CLASS as Class,q.COVERAGE as Coverage, q.TERM as Term , NUMERIC_VALUE as CommissionsAmount , s.name ,p.effectivedate as MainEffectiveDate ,q.effective_date  as EffectiveDate "
				+ " from dbo.PRICING_PRICESHEET_ACCOUNT_RELATION as p"
				+ " join dbo.pricing_pricesheet as ap on p.pricesheet_id= ap.id  "
				+ " left join dbo.account ac on p.payee_id = ac.id "
				+ " left join PRICESHEET_PRODUCT_TIER q on  ap.id=q.[PRICESHEET_ID] "
				+ " left join PRICESHEET_PRODUCT_TIER_TARGET r on q.id=r.TIER_ID"
				+ " left join [PRICESHEET_TIER_TARGET_PROPERTY] s on r.TIER_TARGET_PROPERTY_ID =s.ID "
				+ "where ap.internal_name like '" + internalName + "'  and s.DTYPE not in ('TYPE','ID'); ";
		dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This function correct all data needed PriceSheetVisibility
	 * 
	 */
	public HashMap<String, String> setAllDataForPriceSheetVisibility(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();

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
				+ "tp.EFFECTIVE_DATE as EffDate2,tt.EFFECTIVE_DATE as effDate3 ," + "p.INTERNAL_NAME as intName "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + dealerId + "%' and a.account_status_id = 1 and a.role_type_id = "
				+ map.get("ROLETYPE") + " order by 1 desc";
		//// save data in map
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("DEALERID", dbMap1.get("dealerid"));
		dbMap.put("PRICESHEETINTERNALCODE", dbMap1.get("pricesheetId"));
		dbMap.put("PRICESHEETMAINEFFECTIVEDATE", dbMap1.get("MainPSeffDate"));
		dbMap.put("PRICESHEETMAINEFFECTIVEDATEEXCEPTION1", dbMap1.get("EffDate2"));
		dbMap.put("PRICESHEETMAINEFFECTIVEDATEEXCEPTION2", dbMap1.get("effDate3"));
		dbMap.put("PRICESHEETINTERNALNAME", dbMap1.get("intName"));
		return dbMap;
	}

	/**
	 * This method is used to get the premium
	 *
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationLimitedPriceSheet(HashMap<String, String> map,
			String roleId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		// c.add(Calendar.DATE, -2);
		String date = dateFormat.format(c.getTime());
		//// get data for query

		String progCode = "";
		try {
			progCode = map.get("PRICESHEETCODE");
			if (progCode == null) {
				progCode = "";
			}
		} catch (Exception e) {
		}
		String partQuery = "";
		if (roleId.length() > 0)
			partQuery = "and a.role_identifier ='" + roleId + "' ";
		String query = "select top 1 a.account_status_id,p.id as pricesheetId,a.role_identifier as dealerid, p.CODE as pcode ,"
				+ " convert(date,pac.EFFECTIVEDATE) as MainPSeffDate,"
				+ " convert(date,tt.EFFECTIVE_DATE) as effDate3 , p.INTERNAL_NAME as internalName, term, mileage_from, mileage_to, mileage_band, coverage,age_to "
				+ " from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ " join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ " left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ " where p.code like '%" + progCode + "%' " + partQuery
				+ "and  a.account_status_id = 1  and a.role_type_id = 1 order by p.id desc, p.created_date desc;";
		//// save data in map
		System.out.println("query : " + query);
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("DEALERID", dbMap1.get("dealerid"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		dbMap.put("internalName", dbMap1.get("internalName"));
		dbMap.put("TERM", dbMap1.get("term"));
		String mileageBand = "From: " + dbMap1.get("mileage_from") + " - To: " + dbMap1.get("mileage_to");
		dbMap.put("MILEAGE_TO", dbMap1.get("mileage_to"));
		dbMap.put("MIELAGEBAND", mileageBand);
		dbMap.put("COVERAGE", dbMap1.get("coverage"));
		// for age calculation
		String calcultedYEAR = getVehicleAge(dbMap1.get("age_to"));
		int calYear = Integer.parseInt(calcultedYEAR) + 1;
		System.out.println("YEAR :" + String.valueOf(calYear));
		dbMap.put("YEAR", String.valueOf(calYear));
		String mileage = dbMap1.get("mileage_to");
		int miles = Integer.parseInt(mileage);
		dbMap.put("MILEAGE", String.valueOf(miles));

		String finalQuery = "";

		finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
				+ "from PRICESHEET_PRODUCT_TIER tt " + "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
				+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where t.pricesheet_id in (" + dbMap1.get("pricesheetId")
				+ ", (select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
				+ dbMap1.get("pricesheetId") + ")) " + "and xtype not like '%STD-XCP%' " + " and tt.EFFECTIVE_DATE < '"
				+ date + "' and t.EFFECTIVE_DATE < '" + date + "'" + "and tt.TERM = '" + dbMap1.get("term")
				+ "' and mileage_band = '" + dbMap1.get("mileage_band") + "'and tt.coverage ='" + dbMap1.get("coverage")
				+ "' order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
		System.out.println("final query : " + finalQuery);
		HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
		if (data.size() > 0) {
			float sumOfPremium = 0;
			String deductibleNumericValue = "";
			String deductibleType = "";
			String psDate = data.get(1).get("PSDATE");
			String expDate = data.get(1).get("expdate");
			for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
				String psDate1 = maps.getValue().get("PSDATE");
				String expDate1 = maps.getValue().get("expdate");
				String bucketName = maps.getValue().get("NAME");
				if (psDate.equals(psDate1) && expDate.equals(expDate1)) {
					sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
				}
				if (bucketName.contains("RES_DEDUCTIBLE")) {
					float deductibleValueInInt = Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
					deductibleNumericValue = Float.toString(deductibleValueInInt);
					deductibleType = bucketName;

				}

			}
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
			String numberAsString = decimalFormat.format(sumOfPremium);
			String finallyd = "$" + numberAsString;
			dbMap.put("Premium", finallyd);
			dbMap.put("DeductibleNumericValue", deductibleNumericValue);
			dbMap.put("DeductibleType", deductibleType);
		}
		return dbMap;
	}

	/**
	 * This gets selectPricesheetProgramCode based on roleId, roleType, roleStatus
	 *
	 */
	public HashMap<String, String> validatePriceSheetForRoleType(String internalName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 INTERNAL_NAME as InternalName, CODE as PriceSheetCode ,Role_Identifier as RoleTypeId "
					+ "from dbo.PRICING_PRICESHEET_ACCOUNT_RELATION as p "
					+ "join dbo.pricing_pricesheet as ap on p.pricesheet_id= ap.id "
					+ "left join dbo.account ac on p.payee_id = ac.id where ap.internal_name like '" + internalName
					+ "' order by 1 desc";

			aulDBConnect();
			// /// execute query
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
	 * This gets SearchDataOnAccountSearchScreen and give associated PriceSheet with
	 * the Account
	 * 
	 */
	public HashMap<String, String> checkPriceSheetOnAccount(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 pp.INTERNAL_NAME as PriceSheetInternalName, pp.CODE as PriceSheet from dbo.Account account "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on account.ROLE_TYPE_ID = accountRole.id "
				+ " join [dbo].[ACCOUNT_ADDRESS] accAddress on accAddress.ACCOUNT_ID = account.id "
				+ " join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ " join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID "
				+ " join PRICING_PRICESHEET_ACCOUNT_RELATION ppar on  ppar.ID=account.ID"
				+ " join PRICING_PRICESHEET pp on pp.id=ppar.PRICESHEET_ID where " + " account.ROLE_IDENTIFIER='"
				+ searchParamater.get("Role_Id") + "' and accountRole.ROLE_NAME='" + searchParamater.get("Role_Type")
				+ "' and accStatus.STATUS='" + searchParamater.get("Status") + "' ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This method is used to get to property info of cancellation refund method
	 * string message
	 *
	 */
	public String account_getCancellationRefundMethod(HashMap<String, String> map) throws Exception {
		String cancellation_Refund_Method = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select apv.string_value as method_value from ACCOUNT_PROPERTY ap join [ACCOUNT_PROPERTY_Value] apv on  ap.id= apv.ACCOUNT_PROPERTY_ID "
				+ " where apv.Id in (select PROPERTY_VALUE_ID from [dbo].[ACCOUNT_PROPERTY_GROUP] where account_id = (Select aa.id as account_id from account aa join  [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on aa.ROLE_TYPE_ID = accountRole.id where aa.ROLE_IDENTIFIER = '"
				+ map.get("Role_Id") + "' and accStatus.STATUS = '" + map.get("Status")
				+ "' and  accountRole.ROLE_NAME = '" + map.get("Role_Type")
				+ "') ) and ap.Name like '%Cancellation Refund Method%' order by 1 desc ";
		dbMap = getTopRowDataFromDatabase(query);
		cancellation_Refund_Method = dbMap.get("method_value");
		return cancellation_Refund_Method;
	}

	/**
	 * This method is used to get account id
	 *
	 */
	public HashMap<String, String> account_getaccountId(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select aa.id as account_id from account aa join  [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on aa.ROLE_TYPE_ID = accountRole.id "
				+ " where aa.ROLE_IDENTIFIER = '" + map.get("roleId") + "' and accStatus.STATUS = '"
				+ map.get("roleStatus") + "' and  accountRole.ROLE_NAME = '" + map.get("roleType+") + "'; ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets Agent account ID on which the given Limited Warranty plan code is
	 * not applied
	 */
	public String account_getActiveAgent() throws Exception {
		String roleId = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 role_identifier from dbo.account a where a.account_status_id = 1  and a.role_type_id = 11 "
				+ "and sugar_account_id is not null order by role_identifier desc;";
		System.out.println("account_getActiveAgent query : " + query);
		dbMap = getTopRowDataFromDatabase(query);
		roleId = dbMap.get("role_identifier");
		return roleId;
	}

	/**
	 * This gets search all the details defined for given plancode which is required
	 * to set LW Pricing
	 *
	 */
	public HashMap<String, String> account_getDBValueDefinedForLWSetUp(String progCode) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String partQuery = "and term in ('3/3', '6/6', '12/12') ";

		String query = "select top 1 p.id as pricesheetId, p.CODE as pcode, category_value, coverage_type, coverage, age_from, age_to, term, mileage_band, mileage_from, mileage_to, class,term, term_months, term_mileage"
				+ " from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID " + " where p.code like '%" + progCode
				+ "%' " + "and category_value is not null and coverage_type like '%Limited%' " + partQuery
				+ "order by tt.term;";
		//// save data in map
		System.out.println("account_getDBValueDefinedForLWSetUp : " + query);
		dbMap = getTopRowDataFromDatabase(query);
		if (dbMap.size() == 0) {
			partQuery = "and term <> '0/0' ";

			query = "select top 1 p.id as pricesheetId, p.CODE as pcode, category_value, coverage_type, coverage, age_from, age_to, term, mileage_band, mileage_from, mileage_to, class,term, term_months, term_mileage"
					+ " from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
					+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID " + " where p.code like '%" + progCode
					+ "%' " + "and category_value is not null and coverage_type like '%Limited%' " + partQuery
					+ "order by  mileage_to desc, term_months asc;";
			System.out.println("account_getDBValueDefinedForLWSetUp : " + query);
			dbMap = getTopRowDataFromDatabase(query);
		}
		if (dbMap.size() > 0) {
			dbMap.put("age_from", dbMap.get("age_from"));
			dbMap.put("age_to", dbMap.get("age_to"));
			dbMap.put("mileage_from", dbMap.get("mileage_from"));
			dbMap.put("mileage_to", dbMap.get("mileage_to"));
			dbMap.put("term_months", dbMap.get("term_months"));
			dbMap.put("term_mileage", dbMap.get("term_mileage"));
		}
		return dbMap;
	}

	public String getDealerDetailsForRecentLWSetup(String lWPlanCode) throws Exception {
		String roleId = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 a.role_identifier as dealerid from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ " join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ " left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ " where p.code like '%" + lWPlanCode
				+ "%'  and  a.account_status_id = 1  and a.role_type_id = 1 order by p.id desc, p.created_date desc;";
		//// save data in map
		System.out.println("getDealerDetailsForRecentLWSetup query : " + query);
		dbMap = getTopRowDataFromDatabase(query);
		roleId = dbMap.get("dealerid");
		return roleId;
	}

	/**
	 * This gets search PriceSheet Detail For LW
	 * 
	 * 
	 */
	public HashMap<String, String> getPricesheetDetails(String internalName) throws Exception {
		String query = " select q.PRICESHEET_ID,ap.INTERNAL_NAME as internalName, q.CATEGORY_VALUE as ExceptionType ,"
				+ " q.AGE_FROM,q.AGE_TO,q.MILEAGE_BAND,"
				+ " q.CLASS as Class,q.COVERAGE as Coverage, q.TERM as Term , NUMERIC_VALUE as CommissionsAmount , s.name as Name "
				+ "  from dbo.PRICING_PRICESHEET_ACCOUNT_RELATION as p join dbo.pricing_pricesheet as ap "
				+ " on p.pricesheet_id= ap.id  left join dbo.account ac on p.payee_id = ac.id "
				+ " left join PRICESHEET_PRODUCT_TIER q on  ap.id=q.[PRICESHEET_ID] "
				+ " left join PRICESHEET_PRODUCT_TIER_TARGET r on q.id=r.TIER_ID "
				+ " left join [PRICESHEET_TIER_TARGET_PROPERTY] s on r.TIER_TARGET_PROPERTY_ID =s.ID "
				+ " where ap.internal_name like '" + internalName + "%';";
		return getTopRowDataFromDatabase(query);
	}

	public HashMap<Integer, HashMap<String, String>> getProgCodeWithLW() throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		String query = " select distinct  p.CODE as pcode , tp.[IS_LIMITED_WARRANTY] "
				+ " from [dbo].[PRICING_PRICESHEET] p "
				+ " join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ " left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ " left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ " where  tp.[IS_LIMITED_WARRANTY] = 1 and pac.[ON_WEB]=1 and pac.[ON_WS] = 1 "
				+ " and p.[EXTERNAL_NAME] like '%Warranty%' and active =1 " + " order by  pcode ;";
		//// save data in map
		System.out.println("getProgCodeWithLW : " + query);
		dbMap = getAllDataFromDatabase(query);
		for (Entry<Integer, HashMap<String, String>> entry : dbMap.entrySet()) {
			HashMap<String, String> value = entry.getValue();
			Integer key = entry.getKey();

			if (value.equals("ATE")) {
				dbMap.remove(key, value);

			}
		}

		return dbMap;
	}

	/**
	 * This gets account ID on which the given Limited Warranty plan code is not
	 * applied
	 */
	public String account_getRoleIdToSetupLWPricing(String planCode) throws Exception {
		String roleId = "";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select  top 1 a.role_identifier from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID where a.account_status_id = 1  and a.role_type_id = 1 "
				+ "and a.role_identifier not in (select a.role_identifier from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ " join dbo.account a on a.id = pac.PRIMARY_SELLER_ID where p.code = '" + planCode + "');";
		System.out.println("account_getRoleIdToSetupLWPricing query : " + query);
		dbMap = getTopRowDataFromDatabase(query);
		roleId = dbMap.get("role_identifier");
		return roleId;
	}

	public HashMap<String, String> dBValue() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = " select ac.ROLE_IDENTIFIER,art.ROLE_NAME ,p.NAME from [dbo].[ACCOUNT_PROPERTY] p join [dbo].[ACCOUNT_PROPERTY_VALUE] v on p.id = v.account_property_id "
				+ " join [dbo].[ACCOUNT_PROPERTY_GROUP] g on g.property_value_id = v.id "
				+ " join  [dbo].[ACCOUNT] ac on ac.id = g.account_id "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] art on art.ACCOUNT_TYPE_ID = ac.ACCOUNT_TYPE_ID "
				+ " where art.ROLE_NAME ='DEALER' and p.NAME like 'Westlake Partner Code' OR p.NAME like 'Western Funding Code' ;";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}
}
