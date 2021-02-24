package ocean.modules.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ocean.common.CommonFunctions;

/**
 * Data Base class, common class consisting all data base queries consumed in
 * search Module
 * 
 * @author Mohit Goel
 */
public class SearchDataBase extends CommonFunctions {
	/**
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */

	public HashMap<Integer, HashMap<String, String>> searchCancelDetailsFromSearchData(String contractId)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			String query = "select asd.cert as Contract,asd.CUSTOMER_FIRST as First_Name, asd.customer_last as Last_Name, "
					+ " acd.Process_Date, cp.NET_REFUND_AMOUNT as Net_Refund, cs.name as Status, cp.CUSTOMER_REFUND as Customer_Refund from dbo.allsales_details asd "
					+ "join allcancel_details acd on acd.cert=asd.cert join dbo.cancellation_parameters cp on "
					+ "asd.id=cp.allsales_details_id join account account on account.id=asd.PRIMARY_ACCOUNT_ID join "
					+ "dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = account.role_type_ID join [dbo].[CANCELLATION_STATUS] cs on cs.ID = cp.[STATUS_ID] "
					+ " where asd.cert='" + contractId + "';";
			System.out.println("dbquery====" + query);
			//// save data in map
			dbMap = getAllDataFromDatabase(query);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}

	public HashMap<String, String> search_Search(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String query1 = "select top " + "1" + " ";
			String query2 = " from dbo.allsales_details asd WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS "
					+ "account WITH (NOLOCK) ON account.ID = asd.PRIMARY_ACCOUNT_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on "
					+ "accType.id = account.Role_Type_Id left join [ACCOUNT] AS account2 WITH (NOLOCK) ON account2.ID = asd.SECONDARY_ACCOUNT_ID"
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = account2.Role_Type_Id join UW_CONTRACT_STATUS stat on "
					+ "stat.Id =  asd.contract_status_id left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=asd.contract_substatus_id where ";
			String myKey = "";
			String myvalue = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				String beforeKey = convertKeys(key);
				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					myKey = myKey + beforeKey + " as " + key + ",";
					myvalue = myvalue + beforeKey + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing }
				} else if (key.equals("From_Sale_Date") || key.equals("To_Sale_Date")) {
					String fromSaleDate = "";
					String toSaleDate = "";
					if (key.equals("From_Sale_Date")) {
						fromSaleDate = searchParamater.get("From_Sale_Date");
					} else {
						toSaleDate = searchParamater.get("To_Sale_Date");
					}
					if (fromSaleDate.length() > 0) {
						myvalue = myvalue + "'" + fromSaleDate + "' <=" + beforeKey + " and ";
					}
					if (toSaleDate.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toSaleDate + "' and ";
					}
				} else if (key.equals("From_Trans_Date") || key.equals("To_Trans_Date")) {
					String fromTransDate = "";
					String toTransDate = "";
					if (key.equals("From_Trans_Date")) {
						fromTransDate = searchParamater.get("From_Trans_Date");
					} else {
						toTransDate = searchParamater.get("To_Trans_Date");
					}
					if (fromTransDate.length() > 0) {
						myvalue = myvalue + "'" + fromTransDate + "' <=" + beforeKey + " and ";
					}
					if (toTransDate.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toTransDate + "' and ";
					}
				} else if (key.equals("From_Post_Period") || key.equals("To_Post_Period")) {
					String fromPostPeriod = "";
					String toPostPeriod = "";
					if (key.equals("From_Post_Period")) {
						fromPostPeriod = searchParamater.get("From_Post_Period");
					} else {
						toPostPeriod = searchParamater.get("To_Post_Period");
					}
					if (fromPostPeriod.length() > 0) {
						myvalue = myvalue + "'" + fromPostPeriod + "' <=" + beforeKey + " and ";
					}
					if (toPostPeriod.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toPostPeriod + "' and ";
					}
				} else {
					myvalue = myvalue + beforeKey + " = '" + value + "' and ";
				}
			}
			query = query1 + myKey + query2 + myvalue;
			query = query.substring(0, query.lastIndexOf("and"));
			query = query.substring(0, query.lastIndexOf(","))
					+ query.substring(query.lastIndexOf(",") + 1, query.length()) + " order by asd.cert desc;";
			aulDBConnect();
			waitForSomeTime(1);
			// execute query
			ResultSet rs = stmt.executeQuery(query);
			// save data in map
			dbMap = returnData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			// close connection
			closeConnection();
		}
		return dbMap;
	}

	/*
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<String, String> searchCountFromDatabase(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String query1 = "select asd.cert as cert, " + " ";
			String query2 = " from dbo.allsales_details asd WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS "
					+ "account WITH (NOLOCK) ON account.ID = asd.PRIMARY_ACCOUNT_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on "
					+ "accType.id = account.Role_Type_Id left join [ACCOUNT] AS account2 WITH (NOLOCK) ON account2.ID = asd.SECONDARY_ACCOUNT_ID"
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = account2.Role_Type_Id join UW_CONTRACT_STATUS stat on "
					+ "stat.Id =  asd.contract_status_id left join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=asd.contract_substatus_id where ";
			String myvalue = "";
			String beforeKey = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				beforeKey = convertKeys(key);
				String value = (String) mapElement.getValue();
				if (value.length() < 1) {
					// do nothing }
				} else if (key.equals("From_Sale_Date") || key.equals("To_Sale_Date")) {
					String fromSaleDate = "";
					String toSaleDate = "";
					if (key.equals("From_Sale_Date")) {
						fromSaleDate = searchParamater.get("From_Sale_Date");
					} else {
						toSaleDate = searchParamater.get("To_Sale_Date");
					}
					if (fromSaleDate.length() > 0) {
						myvalue = myvalue + "'" + fromSaleDate + "' <=" + beforeKey + " and ";
					}
					if (toSaleDate.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toSaleDate + "' and ";
					}
				} else if (key.equals("From_Trans_Date") || key.equals("To_Trans_Date")) {
					String fromTransDate = "";
					String toTransDate = "";
					if (key.equals("From_Trans_Date")) {
						fromTransDate = searchParamater.get("From_Trans_Date");
					} else {
						toTransDate = searchParamater.get("To_Trans_Date");
					}
					if (fromTransDate.length() > 0) {
						myvalue = myvalue + "'" + fromTransDate + "' <=" + beforeKey + " and ";
					}
					if (toTransDate.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toTransDate + "' and ";
					}
				} else if (key.equals("From_Post_Period") || key.equals("To_Post_Period")) {
					String fromPostPeriod = "";
					String toPostPeriod = "";
					if (key.equals("From_Post_Period")) {
						fromPostPeriod = searchParamater.get("From_Post_Period");
					} else {
						toPostPeriod = searchParamater.get("To_Post_Period");
					}
					if (fromPostPeriod.length() > 0) {
						myvalue = myvalue + "'" + fromPostPeriod + "' <=" + beforeKey + " and ";
					}
					if (toPostPeriod.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toPostPeriod + "' and ";
					}
				} else {
					myvalue = myvalue + beforeKey + " = '" + value + "' and ";
				}

			}
			String order = "group by asd.cert";
			query = query1 + query2 + myvalue;
			query = query.substring(0, query.lastIndexOf("and")) + order + ";";
			query = query.substring(0, query.lastIndexOf(","))
					+ query.substring(query.lastIndexOf(",") + 1, query.length());
			System.out.println("DBCountquery====" + query);
			aulDBConnect();
			waitForSomeTime(1);
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			// save data in map
			dbMap = returnData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			// close connection
			closeConnection();
		}

		return dbMap;
	}

	/**
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<String, String> searchDetailsFromSearchData(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = " Select top 1 asd.cert as Contract, asd.program_code as Code, asd.Phone as Phone, asd.customer_last as Last_Name, stat.name as Status, "
					+ " sub.name as SubStatus, asd.Customer_first as First_Name, asd.VIN as VIN,"
					+ " asd.STATE as State, account.ROLE_IDENTIFIER as Primary_Seller_ID, "
					+ " account.NAME as Primary_Seller_Name, accType.ROLE_NAME as Primary_Seller_Type, asd.sale_date as Sale_Date, "
					+ " asd.TRANS_DATE as Trans_Date, asd.POST_PERIOD as Post_Period, accType3.ROLE_NAME as Secondary_Seller_Type, account2.ROLE_IDENTIFIER as "
					+ " Secondary_Seller_ID, account2.Name as Secondary_Seller_Name from dbo.allsales_details asd WITH (NOLOCK) "
					+ " LEFT JOIN [ACCOUNT] AS account WITH (NOLOCK) ON account.ID = asd.PRIMARY_ACCOUNT_ID join"
					+ " dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = account.Role_Type_Id left join [ACCOUNT] AS"
					+ " account2 WITH (NOLOCK) ON account2.ID = asd.SECONDARY_ACCOUNT_ID join dbo.[ACCOUNT_ROLE_TYPE] accType3 on "
					+ " accType3.id = account2.Role_Type_Id join UW_CONTRACT_STATUS stat on stat.Id =  asd.contract_status_id left join"
					+ " [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id=asd.contract_substatus_id where asd.cert='"
					+ contractId + "' ;";
			System.out.println("dbdetailquery=====" + query);
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
	 * This function Search contract first name having count more than 1.
	 * 
	 */
	public HashMap<String, String> searchFirstNameByCount() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select count(*), asd.CUSTOMER_FIRST as First_Name from dbo.allsales_details as asd where asd.CUSTOMER_FIRST"
					+ " is not null group by asd.CUSTOMER_FIRST having count(*)=10;";
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
	 * This function Search Cancel contract first name having count more than 1.
	 * 
	 */
	public HashMap<String, String> searchFirstNameOfCancelByCount() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select count(*), asd.CUSTOMER_FIRST as First_Name from dbo.allsales_details as asd "
					+ "join [dbo].[UW_CONTRACT_STATUS] sta on asd.CONTRACT_STATUS_ID = sta.ID where asd.CUSTOMER_FIRST is not null "
					+ "and sta.NAME = 'Cancelled' group by asd.CUSTOMER_FIRST having count(*)=10;";
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
	 * This gets SearchDataCountOnSearchScreen for web contract
	 * 
	 */
	public HashMap<String, String> search_SearchWeb(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			@SuppressWarnings("unused")
			String key1 = "";
			String query = "";
			String myKey = "";
			String myvalue = "";
			String value = "";
			String fSaleDate = searchParamater.get("From_Sale_Date");
			String tSaleDate = searchParamater.get("To_Sale_Date");
			String fCreateDate = searchParamater.get("From_Create_Date");
			String tCreateDate = searchParamater.get("To_Create_Date");
			String fRemitDate = searchParamater.get("From_Remit_Date");
			String tRemitDate = searchParamater.get("To_Remit_Date");
			String cFlag = searchParamater.get("CFlag");
			searchParamater.remove("CFlag");
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();

				value = (String) mapElement.getValue();
				if (key.equals("Primary_Seller_ID")) {
					key = "a.role_identifier  ";
				}
				if (key.equals("Payee_ID")) {
					key = "PAR.Payee_ID ";
				}
				if (key.equals("Primary_Seller_Name")) {
					key = "a.name  ";
				}
				if (key.equals("State")) {
					if (cFlag.equalsIgnoreCase("true")) {
						key = "st.DISPLAY_NAME ";
					} else {
						key = "stp.DISPLAY_NAME ";
					}
				}
				if (key.equalsIgnoreCase("CITY")) {
					if (cFlag.equalsIgnoreCase("true")) {
						key = "s.City ";
					} else {
						key = "aadd.City ";
					}
				}
				if (key.equals("Primary_Seller_Type")) {
					key = "accType.role_name  ";
					key1 = "accType.role_name as Primary_Seller_Type ";
				}
				if (key.equals("Secondary_Seller_Name")) {
					key = "aa.name   ";
				}
				if (key.equals("Secondary_Seller_Type")) {
					key = "accType3.role_name";
				}
				if (key.equals("Secondary_Seller_ID")) {
					key = "aa.role_identifier  ";
				}

				if (value.equals("*") && !(key == "From_Sale_Date" || key == "To_Sale_Date")
						&& !(key == "From_Create_Date" || key == "To_Create_Date")
						&& !(key == "From_Remit_Date" || key == "To_Remit_Date")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else if ((value.length() > 1) && !(key == "From_Sale_Date" || key == "To_Sale_Date")
						&& !(key == "From_Create_Date" || key == "To_Create_Date")
						&& !(key == "From_Remit_Date" || key == "To_Remit_Date")) {

					myKey = myKey + key + ",";
					myvalue = myvalue + key + " like '%" + value + "%' and ";
				} else if (key == "From_Sale_Date" || key == "To_Sale_Date") {
					if (value.length() > 1) {
						if (!myKey.contains("s.PURCHASE_DATE")) {
							myKey = myKey + "s.PURCHASE_DATE " + ",";

							fSaleDate = ConverDtFormat(fSaleDate);
							tSaleDate = ConverDtFormat(tSaleDate);

							myvalue = myvalue + "  s.PURCHASE_DATE between '" + fSaleDate + "'  and '" + tSaleDate
									+ "'  and " + " ";
						}
					}
				}

				else if (key == "From_Create_Date" || key == "To_Create_Date") {
					if (value.length() > 1) {
						if (!myKey.contains("s.DATE_Created")) {
							myKey = myKey + "s.DATE_Created " + ",";

							fCreateDate = ConverDtFormat(fCreateDate);
							tCreateDate = ConverDtFormat(tCreateDate);

							myvalue = myvalue + "  s.DATE_Created between '" + fCreateDate + "'  and '" + tCreateDate
									+ "'  and " + " ";
						}
					}
				}

				else if (key == "From_Remit_Date" || key == "To_Remit_Date") {
					if (value.length() > 1) {
						if (!myKey.contains("s.Remit_DATE")) {
							myKey = myKey + "s.Remit_DATE " + ",";

							fRemitDate = ConverDtFormat(fRemitDate);
							tRemitDate = ConverDtFormat(tRemitDate);

							myvalue = myvalue + "  s.Remit_DATE between '" + fRemitDate + "'  and '" + tRemitDate
									+ "'  and " + " ";
						}
					}
				}
			}

			if (myKey.contains("a.role_identifier")) {
				myKey = myKey.replaceAll("a.role_identifier", "a.role_identifier as Primary_Seller_ID");
			}
			if (myKey.contains("a.name")) {
				myKey = myKey.replaceAll("a.name", "a.name as Primary_Seller_Name");
			}
			if (myKey.contains("accType.role_name")) {
				myKey = myKey.replace("accType.role_name", "accType.role_name as Primary_Seller_Type");
			}
			if (myKey.contains("aa.role_identifier")) {
				myKey = myKey.replace("aa.role_identifier", "aa.role_identifier as Secondary_Seller_ID");
			}
			if (myKey.contains("aa.name")) {
				myKey = myKey.replace("aa.name", "aa.name as Secondary_Seller_Name");
			}
			if (myKey.contains("accType3.role_name")) {
				myKey = myKey.replaceAll("accType3.role_name", "accType3.role_name as Secondary_Seller_Type");
			}

			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));
			query = " select top 1 " + myKey + " " + " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) "
					+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) " + " ON a.ID = s.PRIMARY_SELLER_ID "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id"
					+ " left JOIN dbo.ALLSALES_DETAILS SD on SD.PRIMARY_ACCOUNT_ID = a.ID   "
					+ " join [dbo].[ACCOUNT_ADDRESS] aadd on aadd.[ACCOUNT_ID]= a.Id "
					+ " join [dbo].[STATE] st on st.[NAME]= s.[STATE] "
					+ " join [dbo].[STATE] stp on stp.[NAME]= aadd.[STATE] "
					+ " Join  dbo.PRICING_PRICESHEET_ACCOUNT_RELATION PAR ON a.ID = PAR.PRIMARY_SELLER_ID " + " where "
					+ myvalue + " ";
			dbMap = getTopRowDataFromDatabase(query);
			if (dbMap.containsKey("PURCHASE_DATE")) {
				dbMap.put("From_Sale_Date", fSaleDate);
				dbMap.put("To_Sale_Date", tSaleDate);
			}
			if (dbMap.containsKey("DATE_Created")) {
				String DATE_Created = dbMap.get("DATE_Created");
				String[] cDate = DATE_Created.split(" ");
				dbMap.put("DATE_Created", cDate[0]);
				dbMap.put("From_Created_Date", fCreateDate);
				dbMap.put("To_Created_Date", tCreateDate);
			}
			if (dbMap.containsKey("REMIT_DATE")) {
				dbMap.put("From_Remit_Date", fRemitDate);
				dbMap.put("To_Remit_Date", tRemitDate);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * This gets SearchDataCountOnSearchScreen web contract
	 * 
	 */
	public HashMap<String, String> searchDetailsFromSearchDataWebContract(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query1 = "select distinct  s.cert as Contract,s.First_Name, "
					+ " s.Last_Name ,s.VIN, s.Status,s.STATE as Customer_State,s.CITY as Customer_City,s.Phone ,"
					+ " s.PROGRAM_CODE ,format(s.DATE_CREATED,'MM/dd/yyyy') as Created_Date,format(s.PURCHASE_DATE,'MM/dd/yyyy') as Sale_Date,"
					+ " format(s.REMIT_DATE,'MM/dd/yyyy') as Remit_Date ,CAST(s.REMIT_NO AS CHAR)REMIT_NO,"
					+ " a.name as Primary_Account_Name, accType.role_name as Primary_Account_Type ,"
					+ " aadd.CITY as Primary_Account_City ,aadd.[STATE] as Primary_Account_State,"
					+ " aa.role_identifier as Secondary_Account_ID,"
					+ " aa.name as Secondary_Account_Name, accType3.role_name as Secondary_Account_Type ,"
					+ " aadd1.CITY as Secondary_Account_City ,aadd1.[STATE] as Secondary_Account_State "
					+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) "
					+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) ON a.ID = s.PRIMARY_SELLER_ID "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " join [dbo].[ACCOUNT_ADDRESS] aadd on aadd.[ACCOUNT_ID]= a.Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK) ON aa.ID = s.SECONDARY_SELLER_ID "
					+ " left join [dbo].[ACCOUNT_ADDRESS] aadd1 on aadd1.[ACCOUNT_ID]= aa.Id "
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id "
					+ "	where aadd1.ADDRESS_TYPE_ID = 1 and aadd.ADDRESS_TYPE_ID = 1 and  s.cert ='" + contractId
					+ "';";
			dbMap = getTopRowDataFromDatabase(query1);
			String Remit_Date = dbMap.get("Remit_Date");
			if (Remit_Date.equalsIgnoreCase("0")) {

			}
			String query2 = "SELECT TOP 1  PAR.PAYEE_ID as Primary_Payee_ID " + " FROM dbo.WEB_CONTRACTS SD JOIN "
					+ " dbo.ACCOUNT ACC ON SD.PRIMARY_SELLER_ID = ACC.ID  JOIN "
					+ "	dbo.[ACCOUNT_ROLE_TYPE] accType on acctype.id = ACC.Role_Type_Id JOIN "
					+ " dbo.PRICING_PRICESHEET_ACCOUNT_RELATION PAR ON ACC.ID = PAR.PRIMARY_SELLER_ID "
					+ " where SD.CERT ='" + contractId + "';";
			dbMap.putAll(getTopRowDataFromDatabase(query2));
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.isEmpty()) {
				dbMap.replace(key, "***");

			}
		}
		return dbMap;
	}

	/**
	 * This gets SearchDataCountOnSearchScreen web contract
	 * 
	 */
	public HashMap<String, String> searchDetailsFromCompareWebContract(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		try {
			String query1 = "select s.First_Name, "
					+ " s.Last_Name ,s.VIN, s.STATUS,s.STATE,s.CITY,s.COVERAGE,s.AUL_PREMIUM,s.MILEAGE,s.TERM,"
					+ " s.PROGRAM_CODE, a.name as Primary_Seller_Name, accType.role_name as Primary_Seller_Type, s.PURCHASE_PRICE as PRICE, "
					+ " s.DEDUCTIBLE, s.CLASS, a.ROLE_IDENTIFIER as Primary_Seller_ID, accStatus.STATUS as Primary_Seller_Status, s.OPTION_TOTAL "
					+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
					+ " ON a.ID = s.PRIMARY_SELLER_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = a.ACCOUNT_STATUS_ID "
					+ " where s.cert ='" + contractId + "';";
			System.out.println("searchDetailsFromCompareWebContract query1: " + query1);
			dbMap = getTopRowDataFromDatabase(query1);
			String query2 = "select aa.ROLE_IDENTIFIER as Secondary_Seller_ID,accStatus.STATUS as Secondary_Seller_Status "
					+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS aa WITH (NOLOCK) ON aa.ID = s.SECONDARY_SELLER_ID "
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id "
					+ " join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = aa.ACCOUNT_STATUS_ID "
					+ " where s.cert ='" + contractId + "';";
			System.out.println("searchDetailsFromCompareWebContract query2: " + query2);
			dbMap1 = getTopRowDataFromDatabase(query2);
			if (dbMap1.size() == 0) {
				dbMap1.put("Secondary_Seller_ID", "");
				dbMap1.put("Secondary_Seller_Status", "");
			}
			dbMap.putAll(dbMap1);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("")) {
				dbMap.replace(key, "***");

			}
		}
		return dbMap;
	}

////This function count no of rows from database for given condition on web
	//// contract

	//// This function count no of rows from database for given condition on web
	//// contract

	public HashMap<String, String> searchCountFromDatabaseWebContract(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String myKey = "";
			String myvalue = "";
			String cFlag = searchParamater.get("cFlag");
			searchParamater.remove("cFlag");
			searchParamater.remove("CFlag");
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				if (key.equals("FIRST_NAME") || key.equals("LAST_NAME") || key.equals("CERT") || key.equals("VIN")
						|| key.equals("PHONE") || key.equals("Program_Code") || key.equals("REMIT_DATE")
						|| key.equals("PURCHASE_DATE") || key.equals("DATE_Created") || key.equals("Remit_no")) {
					String table = "s.";
					key = table + key;
				}

				if (key.equals("Primary_Seller_ID")) {
					key = "a.role_identifier";
				}
				if (key.equalsIgnoreCase("role_name")) {
					key = "accType.role_name";
				}
				if (key.equals("Primary_Seller_Name")) {
					key = "a.name";
				}
				if (key.equals("DISPLAY_NAME") || key.equals("State")) {
					if (cFlag.equalsIgnoreCase("true")) {
						key = "st.DISPLAY_NAME ";
					} else {
						key = "stp.DISPLAY_NAME ";
					}
				}
				if (key.equalsIgnoreCase("CITY")) {
					if (cFlag.equalsIgnoreCase("true")) {
						key = "s.CITY ";
					} else {
						key = "aadd.CITY ";
					}
				}
				if (key.equals("Primary_Seller_Type")) {
					key = "accType.role_name";
				}
				if (key.equals("Payee_ID")) {
					key = "PAR.Payee_ID";
				}
				if (key.equals("Secondary_Seller_Name")) {
					key = "aa.name";
				}
				if (key.equals("Secondary_Seller_Type")) {
					key = "accType3.role_name";
				}
				if (key.equals("Secondary_Seller_ID")) {
					key = "aa.role_identifier";
				}
				if (key.equals("STATUS")) {
					String table = "s.";
					key = table + key;
				}

				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else if ((value.length() > 1)
						&& !(key.equals("From_Sale_Date") || key.equals("To_Sale_Date")
								|| key.equals("s.PURCHASE_DATE"))
						&& !(key.equals("From_Created_Date") || key.equals("To_Created_Date")
								|| key.equals("s.DATE_Created"))
						&& !(key.equals("From_Remit_Date") || key.equals("To_Remit_Date")
								|| key.equals("s.REMIT_DATE"))) {

					myKey = myKey + key + ",";
					myvalue = myvalue + key + " like '%" + value + "%' and  ";
				} else if (key.equals("s.PURCHASE_DATE")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";

						myvalue = myvalue + key + " =  '" + value + "'  and ";

					}
				}

				else if (key.equals("s.DATE_Created")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";

						myvalue = myvalue + key + " =  '" + value + "'  and ";
					}
				}

				else if (key.equals("s.REMIT_DATE")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";
						myvalue = myvalue + key + " =  '" + value + "'  and ";
					}
				}
			}

			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			if (myKey.contains("CFlag")) {
				myKey = myKey.replaceAll(",CFlag", "");
			}

			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));
			if (myvalue.contains("CFlag")) {
				myvalue = myvalue.replaceAll(",CFlag", "");
			}
			String myKey1 = myKey;
			if (myKey.contains("accType3.role_name")) {
				myKey1 = myKey.replaceAll("accType3.role_name", "accType3.role_name as Secondary_Seller_Type");
			}

			if (myKey.contains("Payee_ID")) {
				query = "select count(*) as count  from" + "(select  distinct " + myKey
						+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) " + " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
						+ " ON a.ID = s.Primary_Seller_Id "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
						+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id"
						+ " Join  dbo.PRICING_PRICESHEET_ACCOUNT_RELATION PAR ON a.ID = PAR.PRIMARY_SELLER_ID "
						+ " join [dbo].[STATE] st on st.[NAME]= s.[STATE]" + " where " + myvalue + ") as test ;";
			}
			if (cFlag.equalsIgnoreCase("true")) {
				query = " SELECT SUM(Kcount) as Rcount from  (select count(1) as Kcount , " + myKey1 + " "
						+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) " + " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
						+ " ON a.ID = s.Primary_Seller_Id "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
						+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id"
						+ " join [dbo].[STATE] st on st.[NAME]= s.[STATE] " + " where " + myvalue + "Group by " + myKey
						+ ") as test ";
			} else {
				query = " select count(*) as Rcount from (select count(1) as Kcount , " + myKey1 + " "
						+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK)" + " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
						+ " ON a.ID = s.PRIMARY_SELLER_ID "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
						+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id"
						+ " join [dbo].[ACCOUNT_ADDRESS] aadd on aadd.[ACCOUNT_ID]= a.Id "
						+ " join [dbo].[STATE] st on st.[NAME]= s.[STATE] "
						+ " join [dbo].[STATE] stp on stp.[NAME]= aadd.[STATE] " + "  where " + myvalue + "Group by "
						+ myKey + " ,s.cert ) as test ";
			}
			@SuppressWarnings("unused")
			String searhQuery = " select  " + myKey + " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) "
					+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) " + " ON a.ID = s.Primary_Seller_Id "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id" + " where "
					+ myvalue + "Group by " + myKey + " ;";

			dbMap = getTopRowDataFromDatabase(query);

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		System.out.println(dbMap);
		return dbMap;

	}

////This function return contract numbers from database for given condition on web
	//// contract

	public HashSet<String> searchContractFromDatabaseWebContract(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		HashSet<String> dbSet = new HashSet<String>();
		try {
			String query = "";
			String myKey = "";
			String myvalue = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				if (key.equals("FIRST_NAME") || key.equals("LAST_NAME") || key.equals("CERT") || key.equals("VIN")
						|| key.equals("STATE") || key.equals("CITY") || key.equals("PHONE")
						|| key.equals("Program_Code") || key.equals("REMIT_DATE") || key.equals("PURCHASE_DATE")
						|| key.equals("DATE_Created")) {
					String table = "s.";
					key = table + key;
				}

				if (key.equals("Primary_Seller_ID")) {
					key = "a.role_identifier";
				}
				if (key.equals("Primary_Seller_Name")) {
					key = "a.name";
				}
				if (key.equals("Primary_Seller_Type")) {
					key = "accTyp.role_name";
				}
				if (key.equals("Payee_ID")) {
					key = "PAR.Payee_ID";
				}
				if (key.equals("Secondary_Seller_Name")) {
					key = "aa.name";
				}
				if (key.equals("Secondary_Seller_Type")) {
					key = "accTyp3.role_name";
				}
				if (key.equals("Secondary_Seller_ID")) {
					key = "aa.role_identifier";
				}
				if (key.equals("STATUS")) {
					String table = "s.";
					key = table + key;
				}

				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else if ((value.length() > 1)
						&& !(key.equals("From_Sale_Date") || key.equals("To_Sale_Date")
								|| key.equals("s.PURCHASE_DATE"))
						&& !(key.equals("From_Created_Date") || key.equals("To_Created_Date")
								|| key.equals("s.DATE_Created"))
						&& !(key.equals("From_Remit_Date") || key.equals("To_Remit_Date")
								|| key.equals("s.REMIT_DATE"))) {

					myKey = myKey + key + ",";
					myvalue = myvalue + key + " like '%" + value + "%' and  ";
				} else if (key.equals("s.PURCHASE_DATE")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";

						myvalue = myvalue + key + " =  '" + value + "'  and ";

					}
				}

				else if (key.equals("s.DATE_Created")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";

						myvalue = myvalue + key + " =  '" + value + "'  and ";
					}
				}

				else if (key.equals("s.REMIT_DATE")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";
						myvalue = myvalue + key + " =  '" + value + "'  and ";
					}
				}
			}
			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));
			if (myKey.contains("Payee_ID")) {
				query = " (select  distinct s.cert " + " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) "
						+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) " + " ON a.ID = s.Primary_Seller_Id "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
						+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id"
						+ " Join  dbo.PRICING_PRICESHEET_ACCOUNT_RELATION PAR ON a.ID = PAR.PRIMARY_SELLER_ID "
						+ " where " + myvalue + " ;";
			}

			else {
				query = " SELECT distinct s.cert " + " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) "
						+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) " + " ON a.ID = s.Primary_Seller_Id "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
						+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id" + " where "
						+ myvalue + ";";

			}

			dbMap = getAllDataFromDatabase(query);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		for (int i = 1; i <= dbMap.size(); i++) {
			String contractId = dbMap.get(i).get("cert");
			dbSet.add(contractId);
		}
		return dbSet;
	}

	/**
	 * This gets SearchDataOnSearchScreen for Transaction_History
	 * 
	 */
	public HashMap<String, String> search_TransactionHistory(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String myKey = "";
			String myvalue = "";
			String value = "";
			String fTransDate = searchParamater.get("From_Trans_Date");
			String tTransDate = searchParamater.get("To_Trans_Date");

			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();

				value = (String) mapElement.getValue();

				if (value.equals("*") && !(key == "From_Trans_Date" || key == "To_Trans_Date")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else if ((value.length() > 1) && !(key == "From_Trans_Date" || key == "To_Trans_Date")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " like '%" + value + "%' and ";
				} else if (key == "From_Trans_Date" || key == "To_Trans_Date") {
					if (value.length() > 1) {
						if (!myKey.contains("atd.TRANS_DATE")) {
							myKey = myKey + "atd.TRANS_DATE " + ",";

							fTransDate = ConverDtFormat(fTransDate);
							tTransDate = ConverDtFormat(tTransDate);

							myvalue = myvalue + "  atd.TRANS_DATE between '" + fTransDate + "'  and '" + tTransDate
									+ "'  and " + " ";
						}
					}
				}
			}

			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));
			if (myvalue.contains("atd.DBCR_AMT is not null")) {
				myvalue = myvalue.replaceAll("atd.DBCR_AMT is not null", "atd.DBCR_AMT >0.00");
			}
			if (myvalue.contains("atd.Dealer_Paid is not null")) {
				myvalue = myvalue.replaceAll("atd.Dealer_Paid is not null", "atd.DBCR_AMT >0.00");
			}
			query = " select top 1 " + myKey + " " + " from dbo.ALLTRANS_DETAILS atd   "
					+ " JOIN [ACCOUNT] AS a WITH (NOLOCK)  ON a.ID = atd.[PRIMARY_ACCOUNT_ID] "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [dbo].[ALLSALES_DETAILS] asd on asd.id = atd.ALLSALES_DETAILS_ID " + " where "
					+ myvalue + " ";
			if (myvalue.contains("AFDB.Agent1_ID")) {
				query = " select top 1 " + myKey + " " + " from dbo.ALLTRANS_DETAILS atd   inner join "
						+ " ALLTRANS_FINCO_DATA_BASE AFDB ON atd.ALLSALES_DETAILS_ID = AFDB.ALLSALES_DETAILS_ID "
						+ " JOIN [ACCOUNT] AS a WITH (NOLOCK)  ON a.ID = atd.[PRIMARY_ACCOUNT_ID] "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [dbo].[ALLSALES_DETAILS] asd on asd.id = atd.ALLSALES_DETAILS_ID " + " where "
						+ myvalue + " order by 1 desc ;";
			}
			if (myvalue.contains("a.Role_Identifier")) {
				query = query.replace("inner join", "left join");

			}
			dbMap = getTopRowDataFromDatabase(query);
			if (dbMap.containsKey("TRANS_DATE")) {
				String DATE_Created = dbMap.get("TRANS_DATE");
				String[] cDate = DATE_Created.split(" ");
				dbMap.put("TRANS_DATE", cDate[0]);
				dbMap.put("From_Trans_Date", fTransDate);
				dbMap.put("To_Trans_Date", tTransDate);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;
	}
////This function count no of rows from database for given condition on Transaction_History

	public HashMap<String, String> searchCountFromDatabaseTransactionHistory(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String myKey = "";
			String myvalue = "";
			String fTransDate = searchParamater.get("From_Trans_Date");
			String tTransDate = searchParamater.get("To_Trans_Date");
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				if (key.equals("CERT") || key.equals("ADJTYPE") || key.equals("ADJTYPE_DET")
						|| key.equals("ADJTYPE_CAT") || key.equals("Batch_No") || key.equals("Post_Period")
						|| key.equals("Check_No") || key.equals("Check_AMT") || key.equals("DBCR_AMT")
						|| (key.equals("Dealer_Paid") || key.equals("TRANS_DATE"))) {
					String table = "atd.";
					key = table + key;
				}

				if (key.equals("Agent1_ID")) {
					String table = "AFDB.";
					key = table + key;
				}
				if (key.equals("Program_Code")) {
					String table = "asd.";
					key = table + key;
				}
				if (key.equals("ROLE_NAME")) {
					String table = "accType.";
					key = table + key;
				}

				if (key.equals("Name") || key.equals("Role_Identifier")) {
					String table = "a.";
					key = table + key;
				}

				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else if (key == "From_Trans_Date" || key == "To_Trans_Date") {
					if (value.length() > 1) {
						if (!myKey.contains("atd.TRANS_DATE")) {
							myKey = myKey + "atd.TRANS_DATE " + ",";

							fTransDate = ConverDtFormat(fTransDate);
							tTransDate = ConverDtFormat(tTransDate);

							myvalue = myvalue + "  atd.TRANS_DATE between '" + fTransDate + "'  and '" + tTransDate
									+ "'  and " + " ";
						}
					}
				}
				myKey = myKey + key + ",";
				myvalue = myvalue + key + " =  '" + value + "'  and ";

			}

			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));
			query = " SELECT SUM(Kcount) as count from  (select count(1) as Kcount , " + myKey + " "
					+ " from dbo.ALLTRANS_DETAILS atd   "
					+ " JOIN [ACCOUNT] AS a WITH (NOLOCK)  ON a.ID = atd.[PRIMARY_ACCOUNT_ID] "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [dbo].[ALLSALES_DETAILS] asd on asd.id = atd.ALLSALES_DETAILS_ID " + " where "
					+ myvalue + "Group by " + myKey + ") as test ";
			if (myvalue.contains("a.Role_Identifier")) {
				query = query.replace("inner join", "left join");
			}
			if (myvalue.contains("AFDB.Agent1_ID")) {
				query = " SELECT SUM(Kcount) as count from  (select count(1) as Kcount , " + myKey + " "
						+ " from dbo.ALLTRANS_DETAILS atd  inner join "
						+ " ALLTRANS_FINCO_DATA_BASE AFDB ON atd.ALLSALES_DETAILS_ID = AFDB.ALLSALES_DETAILS_ID "
						+ " JOIN [ACCOUNT] AS a WITH (NOLOCK)  ON a.ID = atd.[PRIMARY_ACCOUNT_ID] "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [dbo].[ALLSALES_DETAILS] asd on asd.id = atd.ALLSALES_DETAILS_ID " + " where "
						+ myvalue + "Group by " + myKey + ") as test ";
			}
			query = query + ";";
			dbMap = getTopRowDataFromDatabase(query);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

		return dbMap;

	}

	/// This get database result displayed in datagrid of Transacion History Page
	/// based on
	// given Parameters

	public List<Map<String, String>> searchFromDatabaseTransactionHistory(HashMap<String, String> searchParamater)
			throws Exception {

		try {
			String query = "";
			String myKey = "";
			String myvalue = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				if (key.equals("CERT") || key.equals("ADJTYPE") || key.equals("ADJTYPE_DET")
						|| key.equals("ADJTYPE_CAT") || key.equals("Batch_No") || key.equals("Post_Period")
						|| key.equals("Check_No") || key.equals("Check_AMT") || key.equals("DBCR_AMT")
						|| (key.equals("Dealer_Paid") || key.equals("TRANS_DATE"))) {
					String table = "atd.";
					key = table + key;
				}

				if (key.equals("Agent1_ID")) {
					String table = "AFDB.";
					key = table + key;
				}
				if (key.equals("Program_Code")) {
					String table = "asd.";
					key = table + key;
				}
				if (key.equals("ROLE_NAME")) {
					String table = "accType.";
					key = table + key;
				}

				if (key.equals("Name") || key.equals("Role_Identifier")) {
					String table = "a.";
					key = table + key;
				}

				String value = (String) mapElement.getValue();
				if (value.equals("*")) {
					myKey = myKey + key + ",";
					myvalue = myvalue + key + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing
				} else if ((value.length() > 1) && !(key.equals("From_Sale_Date") || key.equals("To_Sale_Date")
						|| key.equals("s.PURCHASE_DATE"))) {

					myKey = myKey + key + ",";
					myvalue = myvalue + key + " like '%" + value + "%' and  ";
				} else if (key.equals("atd.TRANS_DATE")) {
					if (value.length() > 1) {

						myKey = myKey + key + ",";

						myvalue = myvalue + key + " =  '" + value + "'  and ";

					}
				}
				myKey = myKey + key + ",";
				myvalue = myvalue + key + " =  '" + value + "'  and ";
			}

			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));

			query = " SELECT  atd.CERT , atd.[ADJTYPE]  ,atd.[ADJTYPE_DET] ,atd.[ADJTYPE_CAT] ,"
					+ " atd.BATCH_NO ,atd.CHECK_NO,atd.CHECK_AMT ,atd.POST_PERIOD ,"
					+ " atd.DEALER_PAID  ,atd.[DBCR_AMT] as Debit_Credit, atd.STD_COMMENTS,atd.ADD_COMMENTS  "
					+ " from dbo.ALLTRANS_DETAILS atd   "
					+ " JOIN [ACCOUNT] AS a WITH (NOLOCK)  ON a.ID = atd.[PRIMARY_ACCOUNT_ID] "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [dbo].[ALLSALES_DETAILS] asd on asd.id = atd.ALLSALES_DETAILS_ID " + " where   "
					+ myvalue + " ";

			if (myvalue.contains("AFDB.Agent1_ID")) {

				query = " SELECT  atd.CERT , atd.[ADJTYPE]  ,atd.[ADJTYPE_DET] ,atd.[ADJTYPE_CAT] ,"
						+ " atd.BATCH_NO ,atd.CHECK_NO,atd.CHECK_AMT ,atd.POST_PERIOD ,"
						+ " atd.DEALER_PAID  ,atd.[DBCR_AMT] as Debit_Credit, atd.STD_COMMENTS,atd.ADD_COMMENTS  "
						+ " from dbo.ALLTRANS_DETAILS atd   inner join "
						+ " ALLTRANS_FINCO_DATA_BASE AFDB ON atd.ALLSALES_DETAILS_ID = AFDB.ALLSALES_DETAILS_ID "
						+ " JOIN [ACCOUNT] AS a WITH (NOLOCK)  ON a.ID = atd.[PRIMARY_ACCOUNT_ID] "
						+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
						+ " left join [dbo].[ALLSALES_DETAILS] asd on asd.id = atd.ALLSALES_DETAILS_ID " + " where  "
						+ myvalue + " ";
			}
			query = query + ";";

			if (myvalue.contains("a.Role_Identifier")) {
				query = query.replace("inner join", "left join");

			}

			aulDBConnect();
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			ResultSetMetaData meta = rs.getMetaData();
			int columns = meta.getColumnCount();
			while (rs.next()) {
				HashMap<String, String> dbMap = new HashMap<String, String>();
				for (int i = 1; i <= columns; i++) {
					String key = meta.getColumnName(i);
					String value = rs.getString(key);
					dbMap.put(key, value);
				}
				for (Map.Entry<String, String> entry : dbMap.entrySet()) {
					String value1 = entry.getValue();
					String key1 = entry.getKey();

					if (value1 == null || value1.isEmpty() || value1 == "NONE" || value1 == "") {
						dbMap.replace(key1, "***");
					}
				}
				list.add(dbMap);
				// return list;
			}
			return list;
			//// save data in map
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}

	}

	/**
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<String, String> cancel_Search(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "";
			String query1 = "select top " + "1" + " ";
			String query2 = " from dbo.allsales_details asd join allcancel_details acd on acd.cert=asd.cert join "
					+ "dbo.cancellation_parameters cp on asd.id=cp.allsales_details_id join account account on "
					+ "account.id=asd.PRIMARY_ACCOUNT_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = "
					+ "account.role_type_ID join [dbo].[CANCELLATION_STATUS] cs on cs.ID = cp.[STATUS_ID] where ";
			String myKey = "";
			String myvalue = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				String beforeKey = convertKeysforCancelSearch(key);
				System.out.println("key===" + key);
				String value = (String) mapElement.getValue();
				System.out.println("value====" + value);
				if (value.equals("*")) {
					myKey = myKey + beforeKey + " as " + key + ",";
					myvalue = myvalue + beforeKey + " is not null and ";
				} else if (value.length() < 1) {
					//// do nothing }
				} else {
					String fromCancelDate = "";
					String toCancelDate = "";
					String fromDateProcessed = "";
					String toDateProcessed = "";
					if (key.equals("From_Cancel_Date") || key.equals("To_Cancel_Date")) {
						if (key.equals("From_Cancel_Date")) {
							fromCancelDate = searchParamater.get("From_Cancel_Date");
						} else {
							toCancelDate = searchParamater.get("To_Cancel_Date");
						}
						if (fromCancelDate.length() > 0) {
							myvalue = myvalue + "'" + fromCancelDate + "' <=" + beforeKey + " and ";
						}
						if (toCancelDate.length() > 0) {
							myvalue = myvalue + beforeKey + "<= '" + toCancelDate + "' and ";
						}
					} else if (key.equals("From_Date_Processed") || key.equals("To_Date_Processed")) {
						if (key.equals("From_Date_Processed")) {
							fromDateProcessed = searchParamater.get("From_Date_Processed");
						} else {
							toDateProcessed = searchParamater.get("To_Date_Processed");
						}
						if (fromDateProcessed.length() > 0) {
							myvalue = myvalue + "'" + fromDateProcessed + "' <=" + beforeKey + " and ";
						}
						if (toDateProcessed.length() > 0) {
							myvalue = myvalue + beforeKey + "<= '" + toDateProcessed + "' and ";
						}
					} else {
						myvalue = myvalue + beforeKey + " = '" + value + "' and ";
					}
				}
			}
			query = query1 + myKey + query2 + myvalue;
			System.out.println("query1 ===>" + query);
			query = query.substring(0, query.lastIndexOf("and"));
			System.out.println("query2 ===>" + query);
			query = query.substring(0, query.lastIndexOf(","))
					+ query.substring(query.lastIndexOf(",") + 1, query.length()) + " order by 1 desc;";
			System.out.println("query3 ===>" + query);
			aulDBConnect();
			waitForSomeTime(1);
			// execute query
			ResultSet rs = stmt.executeQuery(query);
			// save data in map
			dbMap = returnData(rs);
			System.out.println(dbMap);
		} catch (Exception e) {
			throw e;
		} finally {
			// close connection
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * This function is used to navigate to perform search based on search parameter
	 * given. It accepts a hashmap with input parameters
	 * 
	 */
	public void searchCancelContractForInputParamaters(HashMap<String, String> searchParamaters) throws Exception {
		waitForSomeTime(10);
		click("clickClearInCancellationSearch");
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamaters.entrySet()) {
			String searchParamater = (String) mapElement.getKey();
			String valueToInput = (String) mapElement.getValue();
			switch (searchParamater) {
			case "Contract":
				type("TypeContractInCancellationSearch", valueToInput);
				break;
			case "First_Name":
				type("TypeFNameInCancellationSearch", valueToInput);
				break;
			case "Last_Name":
				type("TypeLNameInCancellationSearch", valueToInput);
				break;
			case "VIN":
				type("TypeVINInCancellationSearch", valueToInput);
				break;
			case "Role_Type":
				type("TypeRole_TypeInCancellationSearch", valueToInput);
				break;
			case "Role_ID":
				type("TypeRoleIDInCancellationSearch", valueToInput);
				break;
			case "Payee":
				type("TypePayeeInCancellationSearch", valueToInput);
				break;
			case "Code":
				type("TypePCodeInCancellationSearch", valueToInput);
				break;
			case "From_Cancel_Date":
				type("TypeFCancelDateInCancellationSearch", valueToInput);
				break;
			case "To_Cancel_Date":
				type("TypeTCancelDateInCancellationSearch", valueToInput);
				break;
			case "From_Date_Processed":
				type("TypeFDateProcessedInCancellationSearch", valueToInput);
				break;
			case "To_Date_Processed":
				type("TypeTDateProcessedInCancellationSearch", valueToInput);
				break;
			case "Status":
				type("TypeStatusInCancellationSearch", valueToInput);
				break;
			default:
				//// do nothing
			}
		}
		///// click search button
		click("clickSearchInCancellationSearch");
	}

	/*
	 * This gets SearchDataCountOnSearchScreen
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> searchCancelCountFromDatabase(
			HashMap<String, String> searchParamater) throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			String query = "";
			String query1 = "select asd.cert, " + " ";
			String query2 = " from dbo.allsales_details asd join allcancel_details acd on acd.cert=asd.cert join "
					+ " dbo.cancellation_parameters cp on asd.id=cp.allsales_details_id join account account on "
					+ " account.id=asd.PRIMARY_ACCOUNT_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id =  "
					+ " account.role_type_ID join [dbo].[CANCELLATION_STATUS] cs on cs.ID = cp.[STATUS_ID] where ";
			String myvalue = "";
			String beforeKey = "";
			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();
				beforeKey = convertKeysforCancelSearch(key);
				String value = (String) mapElement.getValue();
				if (value.length() < 1) {
					// do nothing }
				} else if (key.equals("From_Date_Processed") || key.equals("To_Date_Processed")) {
					String fromProcessDate = "";
					String toProcessDate = "";
					if (key.equals("From_Sale_Date")) {
						fromProcessDate = searchParamater.get("From_Date_Processed");
					} else {
						toProcessDate = searchParamater.get("To_Date_Processed");
					}
					if (fromProcessDate.length() > 0) {
						myvalue = myvalue + "'" + fromProcessDate + "' <=" + beforeKey + " and ";
					}
					if (toProcessDate.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toProcessDate + "' and ";
					}
				} else if (key.equals("From_Cancel_Date") || key.equals("To_Cancel_Date")) {
					String fromCancelDate = "";
					String toCancelDate = "";
					if (key.equals("From_Cancel_Date")) {
						fromCancelDate = searchParamater.get("From_Cancel_Date");
					} else {
						toCancelDate = searchParamater.get("To_Cancel_Date");
					}
					if (fromCancelDate.length() > 0) {
						myvalue = myvalue + "'" + fromCancelDate + "' <=" + beforeKey + " and ";
					}
					if (toCancelDate.length() > 0) {
						myvalue = myvalue + beforeKey + "<= '" + toCancelDate + "' and ";
					}
				} else {
					myvalue = myvalue + beforeKey + " = '" + value + "' and ";
				}

			}
			query = query1 + query2 + myvalue;
			query = query.substring(0, query.lastIndexOf("and")) + ";";
			query = query.substring(0, query.lastIndexOf(","))
					+ query.substring(query.lastIndexOf(",") + 1, query.length());
			System.out.println("count query===" + query);
			aulDBConnect();
			waitForSomeTime(1);
			///// execute query
			ResultSet rs = stmt.executeQuery(query);
			// save data in map
			dbMap = returnAllData(rs);
		} catch (Exception e) {
			throw e;
		} finally {
			// close connection
			closeConnection();
		}

		return dbMap;
	}

}
