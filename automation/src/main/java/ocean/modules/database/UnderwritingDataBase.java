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
 * underwriting Module
 * 
 * @author Mohit Goel
 */
public class UnderwritingDataBase extends CommonFunctions {
	/**
	 * This gets SearchDataCountOnCancellationScreen
	 * 
	 */
	public HashMap<String, String> cancellation_getSearchDataCountOnCancellationScreen(
			HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "";
		String query1 = "select count(1) as count";
		String query2 = " from [dbo].[ALLSALES_DETAILS] where ";
		String myvalue = "";
		for (@SuppressWarnings("rawtypes")
		Map.Entry mapElement : searchParamater.entrySet()) {
			String key = (String) mapElement.getKey();
			String value = (String) mapElement.getValue();
			if (value.equals("*")) {
				//// do noting
			} else if (value.length() < 1) {
				//// do nothing
			} else {

				myvalue = myvalue + key + " = '" + value + "' and ";
			}

		}
		query = query1 + query2 + myvalue;
		query = query.substring(0, query.lastIndexOf("and")) + ";";
		dbMap = getTopRowDataFromDatabase(query1);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String cancellation_getContractIdTrasferred(String contract, String fee) throws Exception {
		String query = "select transferred from [dbo].[ALLSALES_DETAILS]  asd join allsales_dollar_Detail asdd on asd.id = asdd.allsales_details_id "
				+ "where cert = '" + contract + "' and DValue = '" + fee + "'";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("transferred");
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> contractIdRemittanceNameFromStatusAndSubStatus(String status, String subStatus)
			throws Exception {
		String query = "select  r.remittanceName, sale.cert from [dbo].[ALLSALES_DETAILS] sale join [dbo].[REMITTANCE] r "
				+ "on sale.remittance_id = r.remittanceid join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id = sale.contract_substatus_id "
				+ "where r.remittance_status_id = 1 and sta.NAME = '" + status + "' and sub.name = '" + subStatus
				+ "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> contractIdPostedRemittanceNameFromStatusAndSubStatus(String status, String subStatus)
			throws Exception {
		String query = "select  r.remittanceName, sale.cert from [dbo].[ALLSALES_DETAILS] sale join [dbo].[REMITTANCE] r "
				+ "on sale.remittance_id = r.remittanceid join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id = sale.contract_substatus_id "
				+ "where r.remittance_status_id = 2 and sta.NAME = '" + status + "' and sub.name = '" + subStatus
				+ "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets ContractFileStatusAfterRemittanceUNderwritten
	 * 
	 */
	public String checkForFileStatusAFterUNderwritten(String filenmae) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = "select top 1 d.status_id from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID where "
				+ "d.ORIGINALFILENAME = '" + filenmae + "'";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("status_id");
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String getroleId(String status) throws Exception {
		String query = "select ROLE_IDENTIFIER from dbo.ACCOUNT a join  dbo.ACCOUNT_ROLE_TYPE b on a.ACCOUNT_TYPE_ID = b.id where b.ROLE_NAME = '"
				+ status + "' and a.ACCOUNT_STATUS_ID = 1 order by a.id desc ;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("ROLE_IDENTIFIER");
	}

	/**
	 * This gets ContractFileStatusAfterRemittanceUNderwritten
	 * 
	 */
	public String checkVINELIGIBILITY(String filenmae) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = "select field,ELIGIBILITY,CLASS_FIELD_VALUE,code  FROM dbo.CLASS_TUPLE AS CT "
				+ "JOIN dbo.CLASS_CONDITION  CD ON CD.ID = CT.CLASS_CONDITION_ID JOIN CLASS_FIELD AS CF ON CF.ID = CT.CLASS_FIELD_ID "
				+ "JOIN dbo.CLASS_FIELD_VALUE AS CFV ON CFV.ID = CT.CLASS_FIELD_VALUE_ID JOIN dbo.CLASS_OPERATOR AS CO ON CO.ID = CT.CLASS_OPERATOR_ID "
				+ "JOIN dbo.CLASS_TUPLE_GROUP AS CTG ON CTG.ID = CT.CLASS_TUPLE_GROUP_ID JOIN CLASS_GROUP AS CG ON CG.ID = CTG.CLASS_GROUP_ID "
				+ "JOIN dbo.CLASS_TUPLE_RESULT AS CTR ON CTR.CLASS_TUPLE_GROUP_ID = CTG.ID JOIN dbo.PRICING_PRICESHEET AS PP ON PP.CLASS_GROUP_ID = CG.ID "
				+ "where field = 'VIN' and code = 'SNE' and ELIGIBILITY = 'inEligible'";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("status_id");
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> contractIdPostedRemittanceNameFromStatusAndSubStatusExtAdjustment(String status,
			String subStatus) throws Exception {
		String query = "select  r.remittanceName, sale.cert, tt.check_amt from [dbo].[ALLSALES_DETAILS] sale join [dbo].[REMITTANCE] r "
				+ "on sale.remittance_id = r.remittanceid join [dbo].[ALLTRANS_DETAILS]  tt on tt.cert = sale.[CERT] join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID join [dbo].[UW_CONTRACT_SUBSTATUS] sub on sub.id = sale.contract_substatus_id "
				+ "where r.remittance_status_id = 2 and tt.adjtype = 'EXTADJ' and sta.NAME = '" + status
				+ "' and sub.name = '" + subStatus + "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatusNotTransferred(String status) throws Exception {
		String contract_id = "";
		String query = "select top 1 CERT from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status
				+ "' and sale.transferred = 0 order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("CERT");
		return contract_id;
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
		System.out.println("getPricesheetDetails query: " + query);
		return getTopRowDataFromDatabase(query);
	}

	/**
	 * This function correct all data needed to calculate premium for limited
	 * PriceSheet
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationLimitedPriceSheet(HashMap<String, String> map,
			String roleId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, -2);
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
		String dealerId = "";
		try {
			dealerId = map.get("DEALERID");
			if (dealerId == null) {
				dealerId = "";
			}
		} catch (Exception e) {
		}
		String partQuery = "";
		if (roleId.length() > 0)
			partQuery = "and a.role_identifier ='" + roleId + "' ";
		String query = "select top 1  a.account_status_id,p.id as pricesheetId,a.role_identifier as dealerid, p.CODE as pcode ,"
				+ " convert(date,pac.EFFECTIVEDATE) as MainPSeffDate,"
				+ " convert(date,tt.EFFECTIVE_DATE) as effDate3 , p.INTERNAL_NAME as internalName,term, mileage_from, mileage_to, mileage_band, coverage,age_to  "
				+ " from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ " join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ " left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ " where p.code like '%" + progCode + "%' " + partQuery
				+ "and  a.account_status_id = 1  and a.role_type_id = 1   ;";
		//// save data in map
		System.out.println("setAllDataForPremiumCalculationLimitedPriceSheet query: " + query);
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
				+ date + "' and t.EFFECTIVE_DATE < '" + date + "'"
				+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
		System.out.println("setAllDataForPremiumCalculationLimitedPriceSheet finalQuery: " + finalQuery);
		HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
		float sumOfPremium = 0;
		String psDate = data.get(1).get("PSDATE");
		String expDate = data.get(1).get("expdate");
		for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
			String psDate1 = maps.getValue().get("PSDATE");
			String expDate1 = maps.getValue().get("expdate");
			if (psDate.equals(psDate1) && expDate.equals(expDate1)) {
				sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
			}

		}
		dbMap.put("Premium", String.valueOf(sumOfPremium));
		return dbMap;
	}

	/**
	 * This gets role id for dealer with LW
	 * 
	 * 
	 */
	public HashMap<String, String> getRoleIdWithLWProgCode(String ProgCode) throws Exception {
		String query = " select distinct a.[ROLE_IDENTIFIER] ,art.ROLE_NAME ,a.name  from  ACCOUNT a "
				+ " join [dbo].[ALLSALES_DETAILS] asd on asd.[PRIMARY_ACCOUNT_ID] = a.ID "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] art on art.[ACCOUNT_TYPE_ID] = a.ACCOUNT_TYPE_ID "
				+ " and [PROGRAM_CODE] ='" + ProgCode
				+ "' and a.ACCOUNT_STATUS_ID =1  and asd.SALE_DATE >'2018' and art.ROLE_NAME = 'Dealer' "
				+ " and asd.CONTRACT_STATUS_ID =5  ";

		System.out.println("getPricesheetDetails query: " + query);
		return getTopRowDataFromDatabase(query);
	}

	/**
	 * This function correct all data needed to calculate premium for limited
	 * PriceSheet
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationLimitedPriceSheet_old(HashMap<String, String> map)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, -2);
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
		String dealerId = "";
		try {
			dealerId = map.get("DEALERID");
			if (dealerId == null) {
				dealerId = "";
			}
		} catch (Exception e) {
		}
		String query = "select top 1  a.account_status_id,p.id as pricesheetId,a.role_identifier as dealerid, p.CODE as pcode ,"
				+ " convert(date,pac.EFFECTIVEDATE) as MainPSeffDate,"
				+ " convert(date,tt.EFFECTIVE_DATE) as effDate3 , p.INTERNAL_NAME as internalName "
				+ " from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ " join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ " left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ " where p.code like '%" + progCode + "%'  and  a.account_status_id = 1  and a.role_type_id = 1   ;";
		//// save data in map
		System.out.println("setAllDataForPremiumCalculationLimitedPriceSheet query: " + query);
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("DEALERID", dbMap1.get("dealerid"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		dbMap.put("internalName", dbMap1.get("internalName"));
		String finalQuery = "";

		finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
				+ "from PRICESHEET_PRODUCT_TIER tt " + "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
				+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where t.pricesheet_id in (" + dbMap1.get("pricesheetId")
				+ ", (select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
				+ dbMap1.get("pricesheetId") + ")) " + "and xtype not like '%STD-XCP%' " + " and tt.EFFECTIVE_DATE < '"
				+ date + "' and t.EFFECTIVE_DATE < '" + date + "'"
				+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
		System.out.println("setAllDataForPremiumCalculationLimitedPriceSheet finalQuery: " + finalQuery);
		HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
		float sumOfPremium = 0;
		String psDate = data.get(1).get("PSDATE");
		String expDate = data.get(1).get("expdate");
		for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
			String psDate1 = maps.getValue().get("PSDATE");
			String expDate1 = maps.getValue().get("expdate");
			if (psDate.equals(psDate1) && expDate.equals(expDate1)) {
				sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
			}

		}
		dbMap.put("Premium", String.valueOf(sumOfPremium));
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String primaryIdForAdjustContract(String status) throws Exception {
		String contract_id = "";
		String query = "select role_identifier from [dbo].[ALLSALES_DETAILS] asd join account a on a.id = asd.primary_account_id "
				+ "where cert = '" + status + "'";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("role_identifier");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getContractStatus(String remitName) throws Exception {
		String query = "select d.FILE_NAME as file_name,s.name as status, ddd.DOCUMENTTYPENAME as documenttype  "
				+ "from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on d.remittanceid = r.remittanceid "
				+ "join [dbo].[UW_DOCUMENTTYPE] ddd on ddd.documenttypeid = d.documenttypeid "
				+ "left join [dbo].[UW_CONTRACT_STATUS] s on s.id = d.status_id where RemittanceNumber = " + remitName
				+ " ;";
		return getAllDataFromDatabase(query);
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String deleteRemittanceStatus(String remittance) throws Exception {
		String contract_id = "";
		String query = "select isDeleted from  dbo.remittance where remittanceName = '" + remittance
				+ "'  order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("isDeleted");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status it will fetch contracts for current year only
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusAndPriceSheet(String status,
			String priceSheet) throws Exception {
		HashMap<String, String> myData = new HashMap<String, String>();

		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = "select top 1 CERT,SALE_DATE from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' and PROGRAM_CODE = '"
				+ priceSheet + "' and sale_date like '%" + year + "%'order by 1 desc;";
		myData = getTopRowDataFromDatabase(query);
		return myData;
	}

	/**
	 * This function gets all required details used in TC 08
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC08(String status) throws Exception {
		String query = "select top 1 sales.CERT as Contract_Number, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.START_MILEAGE as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ "account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where statuss.name = '"
				+ status + "' order by sales.id desc;";
		return getTopRowDataFromDatabase(query);
	}

	/**
	 * This function gets all required details used in TC 09
	 * 
	 */
	public HashMap<String, String> cancellation_getDetailsForTC09(String status) throws Exception {
		String query = "select top 1 sales.CERT as Contract_Number, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.START_MILEAGE as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ "account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where statuss.name = '"
				+ status + "' order by sales.id desc;";
		return getTopRowDataFromDatabase(query);
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> cancellation_getCancellationMouduleSearchData(String contractId) throws Exception {
		String query = "select sales.CERT as Contract_Number,price.INTERNAL_NAME as PriceSheet_Name,sales.PROGRAM_CODE, "
				+ " account.NAME as Primary_Account,account.ROLE_IDENTIFIER, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) AS customer_name,statuss.Name as contractStatus ,sales.COMMENTS "
				+ "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] account on account.id =  "
				+ "sales.PRIMARY_ACCOUNT_ID join [dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ " where sales.CERT = '" + contractId + "';";
		return getTopRowDataFromDatabase(query);
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String UW_getCancelContractBasedOnCancelreason(String status, String reason) throws Exception {
		String contract_id = "";

		String query = "select top 1 sale.id,  CERT, crt.name from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta on sale.CONTRACT_STATUS_ID = sta.ID join "
				+ " CANCELLATION_PARAMETERS cp on sale.id = cp.ALLSALES_DETAILS_ID left join [dbo].[CANCELLATION_REASON_TYPE] crt on crt.id = cp.REASON_TYPE_ID "
				+ " where sta.NAME = '" + status + "'  and crt.name = '" + reason + "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("CERT");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String uw_GetPostedContractIdBasedOnStatus(String status) throws Exception {
		String contract_id = "";
		String query = "select  r.remittanceName, sale.cert, par.STATUS_ID from [dbo].[ALLSALES_DETAILS] sale join [dbo].[REMITTANCE] r on sale.remittance_id = r.remittanceid "
				+ " join [dbo].[UW_CONTRACT_STATUS] sta on sale.CONTRACT_STATUS_ID = sta.ID join	dbo.ACCOUNT ACC ON sale.PRIMARY_ACCOUNT_ID = ACC.ID	join "
				+ " dbo.PRICING_PRICESHEET_ACCOUNT_RELATION PAR ON ACC.ID = PAR.PRIMARY_SELLER_ID where par.status_id=1 and r.remittance_status_id = 2 and sale.sale_date like '%2021%' "
				+ " and sta.NAME = '" + status + "' order by 1 desc;";
		System.out.println("query===" + query);
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("cert");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public String getPremiumCalculation(HashMap<String, String> map) throws Exception {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int age = year - Integer.parseInt(map.get("YEAR"));
		String strAgeofVechile = Integer.toString(age);
		String sum = "0";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select sum(numeric_value) as ddd from PRICESHEET_PRODUCT_TIER tt join PRICESHEET_PRODUCT_TIER_TARGET "
				+ "ttt on tt.id  = ttt. tier_id "
				+ "join [dbo].[PRICESHEET_TIER_TARGET_PROPERTY] pp on pp.id = ttt.tier_target_property_id "
				+ "where tt.pricesheet_id in( " + "select id from dbo.pricing_pricesheet where code = '"
				+ map.get("parentpricesheetcode") + "' and parent_pricesheet_id is null) " + "and tt.TERM = '"
				+ map.get("TERM") + "' and tt.class = '" + map.get("CLASS") + "' and tt.coverage ='"
				+ map.get("COVERAGE") + "' " + "and tt.MILEAGE_FROM<='" + map.get("MILEAGE") + "' and tt.MILEAGE_TO>='"
				+ map.get("MILEAGE") + "'and tt.AGE_FROM <= '" + strAgeofVechile + "' AND  tt.AGE_TO> '"
				+ strAgeofVechile + "' and pricesheet_category_id = 2 ;";
		dbMap = getTopRowDataFromDatabase(query);
		sum = dbMap.get("ddd");
		return sum;
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public HashMap<String, String> getLenderPrimaryDealerSecondary() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "with cte as " + "( " + "SELECT     PRICESHEET_ID " + " ,PRIMARY_SELLER_ID "
				+ " ,SECONDARY_SELLER_ID"
				+ ",row_number() OVER(PARTITION BY PRIMARY_SELLER_ID order by SECONDARY_SELLER_ID) RN "
				+ "FROM  dbo.PRICING_PRICESHEET_ACCOUNT_RELATION where PRIMARY_SELLER_ID in "
				+ "(select id from account where ROLE_TYPE_ID = 4) and SECONDARY_SELLER_ID is not null) "
				+ "select top 1 asd.cert, r.remittancename ,asd.primary_Account_id, asd.[SECONDARY_ACCOUNT_ID] from cte "
				+ "left join [dbo].[ALLSALES_DETAILS] asd WITH (NOLOCK) on cte.PRIMARY_SELLER_ID = asd.[PRIMARY_ACCOUNT_ID]"
				+ "join dbo.account a WITH (NOLOCK) on a.id = asd.[PRIMARY_ACCOUNT_ID] and a.role_type_id = 4"
				+ " and a.[ACCOUNT_STATUS_ID] = 1 join dbo.account b WITH (NOLOCK) on b.id "
				+ "= asd.[SECONDARY_ACCOUNT_ID] and b.role_type_id = 1 and b.[ACCOUNT_STATUS_ID] = 1 "
				+ "join remittance r on r.remittanceid = asd.remittance_id and [REMITTANCE_STATUS_ID] = 1 "
				+ "and asd.[CONTRACT_STATUS_ID] = 1 where rn>1 order by remittance_id desc;";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public String getSecondryDealer(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1  a.NAME, a.ROLE_IDENTIFIER from dbo.PRICING_PRICESHEET_ACCOUNT_RELATION ar join account a on ar.secondary_Seller_id = a.id "
				+ "where PRIMARY_SELLER_ID = " + map.get("primary_Account_id") + "and secondary_seller_id not in('"
				+ map.get("SECONDARY_ACCOUNT_ID") + "') and secondary_seller_id is not null;";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("ROLE_IDENTIFIER");
	}

	/**
	 * This gets search all sales details and return us latest contract id
	 * 
	 */
	public String getPremiumCalculation_Edited(HashMap<String, String> map) throws Exception {
		String sum = "0";
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String miegae = map.get("MIELAGEBAND");
		String from = miegae.substring(miegae.lastIndexOf("From:") + 5, miegae.lastIndexOf("To:")).trim()
				.replaceAll("[^A-Za-z0-9]", "");
		String to = miegae.substring(miegae.lastIndexOf("To:") + 3, miegae.length()).trim().replaceAll("[^A-Za-z0-9]",
				"");
		String query = "select sum(numeric_value) as ddd from PRICESHEET_PRODUCT_TIER tt join PRICESHEET_PRODUCT_TIER_TARGET "
				+ "ttt on tt.id  = ttt. tier_id "
				+ "join [dbo].[PRICESHEET_TIER_TARGET_PROPERTY] pp on pp.id = ttt.tier_target_property_id "
				+ "where tt.pricesheet_id in( " + "select id from dbo.pricing_pricesheet where code = '"
				+ map.get("parentpricesheetcode") + "' and parent_pricesheet_id is null) " + "and tt.TERM = '"
				+ map.get("TERM") + "' and tt.class = '" + map.get("CLASS") + "' and tt.coverage ='"
				+ map.get("COVERAGE") + "' " + "and tt.MILEAGE_FROM<='" + from + "' and tt.MILEAGE_TO>='" + to
				+ "' and pricesheet_category_id = 2 ;";
		dbMap = getTopRowDataFromDatabase(query);
		sum = dbMap.get("ddd");
		return sum;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationNotIn(HashMap<String, String> map) throws Exception {
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
		String query = "select top 1 p.id as pricesheetId,a.role_identifier as dealerid, p.CODE as pcode "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + dealerId
				+ "%' and a.account_status_id = 1 and a.role_type_id = 1 and p.id not in (" + map.get("PRICESHEETID")
				+ ") order by 1 desc";
		//// save data in map
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("DEALERID", dbMap1.get("dealerid"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		return dbMap;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationLender(HashMap<String, String> map) throws Exception {
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
		String query = "select top 1 p.id as pricesheetId,a.role_identifier as lenderId, b.role_identifier as dealerid, p.CODE as pcode "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac with (NOLOCK) on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a with (NOLOCK) on a.id = pac.PRIMARY_SELLER_ID join dbo.account b (NOLOCK) on b.id = pac.secondary_SELLER_ID  left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + dealerId
				+ "%' and a.account_status_id = 1 and a.role_type_id = 4 order by 1 desc";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("SecondaryAccountId", dbMap1.get("lenderId"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		dbMap.put("DealerId", dbMap1.get("dealerid"));
		return dbMap;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> primaryDealerSecndaryLender(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select p.id as pricesheetId,  b.role_identifier as lenderId, a.role_identifier as dealerid, "
				+ " p.CODE as pcode from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac "
				+ "  with (NOLOCK) on pac.PRICESHEET_ID = p.id join dbo.account a with (NOLOCK) on a.id = pac.PRIMARY_SELLER_ID "
				+ "  join dbo.account b (NOLOCK) on b.id = pac.secondary_SELLER_ID  left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = "
				+ "   t.pricesheet_id  left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID left join PRICESHEET_TIER_TARGET_PROPERTY "
				+ "     tp on t.tier_target_property_id  = tp.id where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p "
				+ "	  left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "	  left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id where Parent_PriceSheet_id in(select id from "
				+ "	  [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and t.id is null) and t.id is null "
				+ " and p.code like '%%' and a.role_identifier like '%%' and a.account_status_id = 1 and b.account_status_id = 1 and b.role_type_id = 4 order by 1 desc; ";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("SecondaryAccountId", dbMap1.get("lenderId"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		dbMap.put("DEALERID", dbMap1.get("dealerid"));
		return dbMap;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationLenderSecondaryNotNull(HashMap<String, String> map)
			throws Exception {
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
		String query = "select top 1 p.id as pricesheetId,b.role_identifier as lenderId, a.role_identifier as dealerid, p.CODE as pcode "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac with (NOLOCK) on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a with (NOLOCK) on a.id = pac.PRIMARY_SELLER_ID join dbo.account b (NOLOCK) on b.id = pac.secondary_SELLER_ID  left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + dealerId
				+ "%' and a.account_status_id = 1 and a.role_type_id = 1 and b.role_type_id = 4 and b.[ACCOUNT_STATUS_ID] = 1 order by 1 desc";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("SecondaryAccountId", dbMap1.get("lenderId"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		dbMap.put("DealerId", dbMap1.get("dealerid"));
		return dbMap;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationLenderNotIn(HashMap<String, String> map)
			throws Exception {
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
		String query = "select top 1 p.id as pricesheetId,a.role_identifier as lenderId, p.CODE as pcode "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + agentException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + dealerId
				+ "%' and a.account_status_id = 1 and a.role_type_id = 4 and  p.id not in (" + map.get("PRICESHEETID")
				+ ")  order by 1 desc";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("SecondaryAccountId", dbMap1.get("lenderId"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		return dbMap;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> getDealerFromLender(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select a.role_identifier as dealerId from  [dbo].[PRICING_PRICESHEET] p "
				+ "join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.SECONDARY_SELLER_ID " + "where p.id = " + map.get("PRICESHEETID");
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("DEALERID", dbMap1.get("dealerId"));
		return dbMap;
	}

	/**
	 * This function return dealerId
	 * 
	 */
	public String dealerIdforfetchContractId(String contractId) throws Exception {

		String query = "select a.[ROLE_IDENTIFIER], sd.[CERT] from account a "
				+ " join [dbo].[ALLSALES_DETAILS] sd on a.id = sd.[PRIMARY_ACCOUNT_ID] " + " where sd.cert = '"
				+ contractId + "' ";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return null;
		}
		String DEALERID = dbMap1.get("ROLE_IDENTIFIER");
		return DEALERID;
	}

	/**
	 * This gets PendingContractwithRemittanceName
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> pendingContractsFromRemittanceName(String remitName)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		//// connect to aul db
		///// execute query
		String query = "select  r.RemittanceNumber,r.RemittanceName,d.FILE_NAME from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d "
				+ "on r.REMITTANCEID = d.REMITTANCEID where d.status_id = 4 and DOCUMENTTYPEID = 1 and r .IsDeleted = 0 "
				+ "and r.RemittanceName = '" + remitName + "' order by d.CreateByDate desc;";
		System.out.println("pendingContractsFromRemittanceName query: " + query);
		dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This searches most recent remittance which contains contract added via 'Add
	 * External WebContracts'
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getExternalWebContractsFromRemittanceName(String remitName)
			throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		//// connect to aul db
		///// execute query
		String query = "select  r.RemittanceNumber,r.RemittanceName,d.FILE_NAME from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d "
				+ "on r.REMITTANCEID = d.REMITTANCEID where d.status_id = 1 and DOCUMENTTYPEID = 5 and r .IsDeleted = 0 "
				+ "and r.RemittanceName = '" + remitName + "' order by d.CreateByDate desc;";
		System.out.println("getExternalWebContractsFromRemittanceName query: " + query);
		dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets PendingContractwithRemittanceName
	 * 
	 */
	public String postedRemittanceStatus(String remitName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = "select rs.Status from  [dbo].[REMITTANCE]  r join [dbo].[UW_REMITTANCE_STATUS] rs "
				+ "on r.remittance_status_id = rs.id " + "where r.remittanceName = '" + remitName + "'";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("Status");
	}

	/**
	 * This gets PendingContractwithRemittanceName
	 * 
	 */
	public String statusidforCOntract(String remitName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = "select d.status_id  from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d  "
				+ "on r.REMITTANCEID = d.REMITTANCEID where DOCUMENTTYPEID = 1 and r .IsDeleted = 0 "
				+ "and r.RemittanceName = '" + remitName + "' order by d.CreateByDate desc;";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("status_id");
	}

	/**
	 * This function correct all data needed to calculate premium for lender
	 * 
	 */
	public HashMap<String, String> setAllDataForPremiumCalculationForLender(HashMap<String, String> map)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DATE, -2);
		String date = dateFormat.format(c.getTime());
		//// get data for query
		String lenderException = "";
		if (map.get("LENDEREXCEPTION").toLowerCase().equals("y"))
			lenderException = "not null and t.EFFECTIVE_DATE < '" + date + "' and tt.EFFECTIVE_DATE < '" + date
					+ "' and tt.CATEGORY_VALUE = '" + map.get("LENDERPLANTYPE").toUpperCase() + "'";

		else
			lenderException = "null";
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
		String lenderId = "";
		try {
			lenderId = map.get("LENDERID");
			if (lenderId == null) {
				lenderId = "";
			}
		} catch (Exception e) {
		}
		String query = "select top 1 p.id as pricesheetId,a.role_identifier as lenderId, p.CODE as pcode "
				+ "from [dbo].[PRICING_PRICESHEET] p join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.PRIMARY_SELLER_ID left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ " left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
				+ "where p.parent_PriceSheet_id in(select p.id from [dbo].[PRICING_PRICESHEET] p left join PRICESHEET_PRODUCT_TIER_TARGET t on p.id = t.pricesheet_id "
				+ "left join PRICESHEET_PRODUCT_TIER tt on tt.id = t.TIER_ID "
				+ "left join PRICESHEET_TIER_TARGET_PROPERTY tp on t.tier_target_property_id  = tp.id "
				+ "where Parent_PriceSheet_id in(select id from [dbo].[PRICING_PRICESHEET] where Parent_PriceSheet_id is null) and "
				+ "t.id is " + lenderException + ") and t.id is " + dealerException + " and p.code like '%" + progCode
				+ "%' and a.role_identifier like '%" + lenderId
				+ "%' and a.account_status_id = 1 and a.role_type_id = 4 order by 1 desc";
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("PRICESHEETID", dbMap1.get("pricesheetId"));
		dbMap.put("LENDERID", dbMap1.get("lenderId"));
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		String finalQuery = "";
		if (map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase(map.get("LENDERPLANTYPE").toUpperCase())
				&& map.get("DEALERPLANTYPE").toUpperCase().equalsIgnoreCase("ALLPLANS")
				&& (map.get("DEALEREXCEPTION").equalsIgnoreCase("y")
						|| map.get("LENDEREXCEPTION").equalsIgnoreCase("y"))) {
			finalQuery = "select tt.EFFECTIVE_DATE as PSDATE,t.EFFECTIVE_DATE as expdate,t.NUMERIC_VALUE,tp.NAME "
					+ "from PRICESHEET_PRODUCT_TIER tt "
					+ "join PRICESHEET_PRODUCT_TIER_TARGET t  on tt.id = t.TIER_ID "
					+ "join PRICESHEET_TIER_TARGET_PROPERTY  tp on t.tier_target_property_id  = tp.id "
					+ "where t.pricesheet_id in (" + dbMap1.get("pricesheetId")
					+ ", (select parent_pricesheet_id from [dbo].[PRICING_PRICESHEET] where id = "
					+ dbMap1.get("pricesheetId") + ")) " + "and tt.CATEGORY_VALUE = 'ALLPLANS' "
					+ "and xtype not like '%STD-XCP%' " + " and tt.EFFECTIVE_DATE < '" + date
					+ "' and t.EFFECTIVE_DATE < '" + date + "'"
					+ "order by tt.EFFECTIVE_DATE desc,t.EFFECTIVE_DATE desc;";
			HashMap<Integer, HashMap<String, String>> data = getAllDataFromDatabase(finalQuery);
			float sumOfPremium = 0;
			String psDate = data.get(1).get("PSDATE");
			String expDate = data.get(1).get("expdate");
			for (Entry<Integer, HashMap<String, String>> maps : data.entrySet()) {
				String psDate1 = maps.getValue().get("PSDATE");
				String expDate1 = maps.getValue().get("expdate");
				if (psDate.equals(psDate1) && expDate.equals(expDate1)) {
					sumOfPremium = sumOfPremium + Float.parseFloat(maps.getValue().get("NUMERIC_VALUE"));
				}
			}
			dbMap.put("ExceptionPremium", String.valueOf(sumOfPremium));
		}
		return dbMap;
	}

	/**
	 * This gets getRemitCreationdata
	 * 
	 */
	public HashMap<String, String> getRemitCreationdata(String remitName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select r.RemittanceName, s.Source_Type, st.Subtype_Name, t.name,r.corecount,r.lwacount,r.comments, "
				+ "c.CHECK_NO as checknumber,c.AMOUNT as checkamount "
				+ "from REMITTANCE r join UW_REMITTANCE_SOURCE s on s.id = r.remit_source_id "
				+ "left join UW_REMITTANCE_SUBTYPE st on st.id = remit_subtype_id "
				+ "join UW_REMITTANCE_TYPE t on t.id = r.remit_type_id "
				+ "left join [dbo].[UW_BUSINESS_PROCESSOR_CHECK] c on c.remittance_number = r.remittanceNumber "
				+ "where r.RemittanceName = '" + remitName + "' order by r.[RemittanceID] desc;";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets getRemitDataForSearch
	 * 
	 */
	public HashMap<String, String> getRemitDataForFilters() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();

		String query = "select top 1 r.RemittanceName as Remittance_Name,"
				+ " r.PostPeriod as Post_Period,r.remittanceNumber as Remittance_Number "
				+ ", s.Source_Type as Source , st.Subtype_Name as Sub_Type, t.name as Type"
				+ " ,r.corecount as Core,r.lwacount as LWA ,r.UnderwritingCount as UnderW, " + "r.comments as Comment, "
				+ "r.locked_by as Locked_By,r.CreateByDate as Created_Date "
				+ "from REMITTANCE r join UW_REMITTANCE_SOURCE s on s.id = r.remit_source_id "
				+ "join UW_REMITTANCE_SUBTYPE st on st.id = remit_subtype_id "
				+ "join UW_REMITTANCE_TYPE t on t.id = r.remit_type_id "
				+ "where r.isDeleted = 0 and comments like '% %' "
				+ " and locked_by is not null order by r.[RemittanceID] desc;";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets all data fields for remittance information under remittance list
	 * 
	 */
	public HashMap<String, String> getAllDataFieldsUnderRemittanceList(String remitName) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();

		String query = "select r.PostPeriod,r.RemittanceNumber,r.RemittanceName,r.UnderwritingCount,"
				+ " s.Source_Type, st.Subtype_Name, t.name,r.corecount,r.lwacount,"
				+ "r.comments ,r.CreateByDate, r.Locked_by "
				+ "from REMITTANCE r join UW_REMITTANCE_SOURCE s on s.id = r.remit_source_id "
				+ " left join UW_REMITTANCE_SUBTYPE st on st.id = remit_subtype_id "
				+ "join UW_REMITTANCE_TYPE t on t.id = r.remit_type_id  "
				+ " left join [dbo].[UW_CHECK] c on c.remittanceId = r.remittanceNumber " + "where r.RemittanceName = '"
				+ remitName + "' order by r.[RemittanceID] desc;";

		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets PendingContractwithRemittance
	 * 
	 */
	public HashMap<String, String> underWDocument() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = "select top 1 r.RemittanceNumber,r.RemittanceName,d.FILE_NAME from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID where "
				+ " d.status_id = 1 and DOCUMENTTYPEID = 1 and r.IsDeleted = 0 order by d.CreateByDate desc;";

		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	///// Prepare test data needed for test case for lender as primary account
	///// validatePremiumCalculationForMasterAndSubMasterPriceSheet
	public HashMap<String, String> prepareDataForLenderAsPrimaryAccount(String[] inputData) {
		HashMap<String, String> premiumData = new HashMap<String, String>();
		//// Fetch PRICESHEETCODE, if exist
		if (inputData[0].length() > 0)
			premiumData.put("PRICESHEETCODE", inputData[0]);
		else
			premiumData.put("PRICESHEETCODE", "");
		//// Fetch ACTUALPRICESHEETCODE, if exist
		if (inputData[1].length() > 0)
			premiumData.put("LENDERID", inputData[1]);
		//// Fetch VIN, in case of Blank, use any hard code value
		if (inputData[9].length() > 0)
			premiumData.put("VIN", inputData[9]);
		else
			premiumData.put("VIN", "WBANE52010CK67774");
		//// Fetch MAKE, in case of Blank, use any hard code value
		if (inputData[10].length() > 0)
			premiumData.put("MAKE", inputData[10]);
		else
			premiumData.put("MAKE", "HONDA");
		//// Fetch MODEL, in case of Blank, use any hard code value
		if (inputData[11].length() > 0)
			premiumData.put("MODEL", inputData[11]);
		else
			premiumData.put("MODEL", "Accord");
		//// Fetch YEAR, in case of Blank, use any hard code value
		if (inputData[12].length() > 0)
			premiumData.put("YEAR", inputData[12]);
		else
			premiumData.put("YEAR", "2013");
		//// Fetch MILEAGE, in case of Blank, use any hard code value
		if (inputData[13].length() > 0)
			premiumData.put("MILEAGE", inputData[13]);
		else
			premiumData.put("MILEAGE", "1000");
		//// Fetch VEHICLEPRICE, in case of Blank, use any hard code value
		if (inputData[14].length() > 0)
			premiumData.put("VEHICLEPRICE", inputData[14]);
		else
			premiumData.put("VEHICLEPRICE", "1000");
		//// Fetch TERM, if exist
		if (inputData[15].length() > 0)
			premiumData.put("TERM", inputData[15]);
		//// Fetch COVERAGE, if exist
		if (inputData[16].length() > 0)
			premiumData.put("COVERAGE", inputData[16]);
		//// Fetch MIELAGEBAND, if exist
		if (inputData[17].length() > 0)
			premiumData.put("MIELAGEBAND", inputData[17]);
		//// Fetch CLASS, if exist
		if (inputData[18].length() > 0)
			premiumData.put("CLASS", inputData[18]);
		//// Fetch SERVICEDATE, if exist
		if (inputData[19].length() > 0)
			premiumData.put("SERVICEDATE", inputData[19]);
		//// Fetch SURCHARGES, in case of Blank, use N
		if (inputData[4].length() > 0)
			premiumData.put("SURCHARGES", inputData[4]);
		else
			premiumData.put("SURCHARGES", "N");
		//// Fetch DEDUCTIBLE, in case of Blank, use N
		if (inputData[6].length() > 0)
			premiumData.put("DEDUCTIBLE", inputData[6]);
		else
			premiumData.put("DEDUCTIBLE", "N");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[7].length() > 0)
			premiumData.put("LENDERPLANTYPE", inputData[7]);
		else
			premiumData.put("LENDERPLANTYPE", "ALLPLANS");
		//// Fetch AGENTPLANTYPE, in case of Blank, use All_Plans
		if (inputData[8].length() > 0)
			premiumData.put("DEALERPLANTYPE", inputData[8]);
		else
			premiumData.put("DEALERPLANTYPE", "ALLPLANS");
		//// Fetch OPTIONS, in case of Blank, use N
		if (inputData[5].length() > 0)
			premiumData.put("OPTIONS", inputData[5]);
		else
			premiumData.put("OPTIONS", "N");
		//// Fetch DEALEREXCEPTION, in case of Blank, use N
		if (inputData[2].length() > 0)
			premiumData.put("DEALEREXCEPTION", inputData[2]);
		else
			premiumData.put("DEALEREXCEPTION", "N");
		//// Fetch AGENTEXCEPTION, in case of Blank, use N
		if (inputData[3].length() > 0)
			premiumData.put("LENDEREXCEPTION", inputData[3]);
		else
			premiumData.put("LENDEREXCEPTION", "N");

		return premiumData;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> getDealerForLender(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select a.role_identifier as dealerId from  [dbo].[PRICING_PRICESHEET] p "
				+ "join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] pac on pac.PRICESHEET_ID = p.id "
				+ "join dbo.account a on a.id = pac.SECONDARY_SELLER_ID " + "where p.id = " + map.get("PRICESHEETID");
		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query);
		if (dbMap1.size() == 0) {
			return dbMap = null;
		}
		dbMap.put("DEALERID", dbMap1.get("dealerId"));
		return dbMap;
	}

	/**
	 * This function correct all data needed PriceSheetVisibility
	 * 
	 */
	public HashMap<String, String> setAllDataForSaleDateLessThanExceptionDate(HashMap<String, String> map)
			throws Exception {
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
				+ "t.id is " + agentException + ") and pac.EFFECTIVEDATE < tp.EFFECTIVE_DATE and t.id is "
				+ dealerException + " and p.code like '%" + progCode + "%' and a.role_identifier like '%" + dealerId
				+ "%' and a.account_status_id = 1 and a.role_type_id = " + map.get("ROLETYPE") + " order by 1 desc";
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
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
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
		dbMap.put("parentpricesheetcode", dbMap1.get("pcode"));
		return dbMap;
	}

	/*
	 * This gets search all dealer_detail based on role_id
	 * 
	 * 
	 */
	public HashMap<String, String> getfullAddressDetail(int Roleid) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = " SELECT top 1 dbo.ACCOUNT.ID, dbo.ACCOUNT.ROLE_IDENTIFIER, dbo.ACCOUNT.ACCOUNT_STATUS_ID,dbo.ACCOUNT.NAME, "
				+ " dbo.ACCOUNT_ROLE_TYPE.ROLE_NAME ,dbo.ACCOUNT_ADDRESS.STREET, dbo.ACCOUNT_ADDRESS.CITY, dbo.ACCOUNT_ADDRESS.STATE,"
				+ " dbo.ACCOUNT_ADDRESS.ZIP_CODE, dbo.ACCOUNT_ADDRESS.COUNTRY " + " FROM dbo.ACCOUNT INNER JOIN "
				+ " dbo.ACCOUNT_ADDRESS ON dbo.ACCOUNT.ID = dbo.ACCOUNT_ADDRESS.ACCOUNT_ID INNER JOIN "
				+ " dbo.ACCOUNT_ROLE_TYPE ON dbo.ACCOUNT.ROLE_TYPE_ID = dbo.ACCOUNT_ROLE_TYPE.ID "
				+ "	where [dbo].[ACCOUNT_ROLE_TYPE].ID = '+ Roleid +' and dbo.ACCOUNT.ACCOUNT_STATUS_ID = 3; ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/*
	 * This gets search dealer details and return us latest dealer record based on
	 * status
	 * 
	 */

	public HashMap<String, String> getDelearLenderDetail(int i) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = " select  top 1 * from(select  top 1 dbo.ACCOUNT.ID, dbo.ACCOUNT.NAME,dbo.ACCOUNT_ROLE_TYPE.ROLE_NAME,"
				+ " dbo.ACCOUNT_ADDRESS.STREET, dbo.ACCOUNT_ADDRESS.CITY,"
				+ " dbo.ACCOUNT_ADDRESS.STATE, dbo.ACCOUNT_ADDRESS.ZIP_CODE, dbo.ACCOUNT_ADDRESS.COUNTRY"
				+ " FROM dbo.ACCOUNT INNER JOIN "
				+ " dbo.ACCOUNT_ADDRESS ON dbo.ACCOUNT.ID = dbo.ACCOUNT_ADDRESS.ACCOUNT_ID"
				+ " INNER JOIN dbo.ACCOUNT_ROLE_TYPE ON dbo.ACCOUNT.ROLE_TYPE_ID = dbo.ACCOUNT_ROLE_TYPE.ID"
				+ " where [dbo].[ACCOUNT_ROLE_TYPE].ID = +'i '+ and dbo.ACCOUNT.ACCOUNT_STATUS_ID = 3"
				+ " order by  dbo.ACCOUNT.ID desc )as FTable order by id desc; ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets OnHoldContractwithRemittance
	 * 
	 */
	public String checkForFileStatus(String filenmae) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		///// execute query
		String query = "select top 1 d.status_id from [dbo].[REMITTANCE] r join [dbo].[UW_DOCUMENT] d on r.REMITTANCEID = d.REMITTANCEID where "
				+ "d.ORIGINALFILENAME = '" + filenmae + "'";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("status_id");
	}

	/**
	 * This gets remittance physical address detail based on roleId and type
	 * 
	 */
	public HashMap<String, String> getDBRemittancePhysicalAddressDetail(String roleType, String roleId)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		// execute query
		String query = "select top 1 account.name,address.STREET,address.CITY,address.STATE, address.ZIP_CODE"
				+ " from dbo.Account account join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on account.ROLE_TYPE_ID = accountRole.id"
				+ " join [dbo].[ACCOUNT_STATUS] accStatus on accStatus.id  = account.ACCOUNT_STATUS_ID"
				+ " join dbo.[ACCOUNT_TYPE] accType on accType.id = account.ACCOUNT_TYPE_ID"
				+ " join ACCOUNT_ADDRESS address on address.ACCOUNT_ID = account.id"
				+ " join [dbo].[ACCOUNT_PHONE_FAX] phone on phone.ACCOUNT_ID = account.id"
				+ " where accountRole.ROLE_NAME = '" + roleType + "' " + " and account.ROLE_IDENTIFIER = '" + roleId
				+ "' and address.ADDRESS_TYPE_ID = 1;";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets roleID based on roleType, Status having Pricesheet
	 * 
	 */
	public String getRoleIdForContractDataEntry(String roleType) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		// execute query
		String query = "select top 10 *  FROM dbo.Account account join [dbo].[ACCOUNT_STATUS] accStatus "
				+ " on accStatus.id  = account.ACCOUNT_STATUS_ID "
				+ " join [dbo].[ACCOUNT_ROLE_TYPE] accountRole on account.ROLE_TYPE_ID = accountRole.id "
				+ " join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] accrel on account.id = accrel.PRIMARY_SELLER_ID "
				+ " Where  accStatus.STATUS = 'Active' and accountRole.ROLE_NAME ='" + roleType
				+ "' order by account.role_identifier desc;";
		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap.get("ROLE_IDENTIFIER");
	}

	/**
	 * This gets co-OwnerAdress Detail by ContractID
	 * 
	 */
	public HashMap<String, String> getAddressDetailsOfCoOwner(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select CO_OWNER_FIRST as coOwnerFNAME, CO_OWNER_LAST as coOwnerLNAME, CO_OWNER_EMAIL as coOwnerEmail, CO_OWNER_PHONE as coOwnerPhone, CO_OWNER_ADDRESS as coOwnerADD,"
				+ " CO_OWNER_ZIP as coOwnerZip FROM dbo.ALLSALES_DETAILS where cert='" + contractId + "';";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets ownerAdress Detail by ContractID
	 * 
	 */
	public HashMap<String, String> getAddressDetailsOfOwner(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = " select CUSTOMER_FIRST as ownerFNAME, CUSTOMER_LAST as ownerLNAME, CUST_EMAIL as ownerEmail, "
				+ " PHONE as ownerPhone,ADDRESS as ownerADD ,Zip as ownerZip "
				+ " FROM dbo.ALLSALES_DETAILS where cert='" + contractId + "';";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets PricesheetName name
	 * 
	 */
	public String getPriceshhetId(String SHEETID) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();

		String query = " select pp.[INTERNAL_NAME] from [dbo].[PRICING_PRICESHEET] pp "
				+ "	join [dbo].[PRICING_PRICESHEET_ACCOUNT_RELATION] par on " + "	pp.[ID] = par.[PRICESHEET_ID]  "
				+ "	where par.[PRICESHEET_ID] = '" + SHEETID + "' ;";
		dbMap = getTopRowDataFromDatabase(query);
		String priceSheet = dbMap.get("INTERNAL_NAME");
		return priceSheet;
	}

	/**
	 * This gets search relative remittance number when contract status is on hold
	 * 
	 * 
	 */
	public HashMap<String, String> getRemittanceNumber(String contractNumber) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select dbo.ALLSALES_DETAILS.CERT as Contract_Number, dbo.REMITTANCE.RemittanceNumber"
				+ " from dbo.ALLSALES_DETAILS INNER JOIN "
				+ " dbo.UW_CONTRACT_STATUS ON dbo.ALLSALES_DETAILS.CONTRACT_STATUS_ID = dbo.UW_CONTRACT_STATUS.ID INNER JOIN "
				+ " dbo.UW_DOCUMENT ON dbo.ALLSALES_DETAILS.ID = dbo.UW_DOCUMENT.ALLSALES_DETAILS_ID"
				+ " AND dbo.UW_CONTRACT_STATUS.ID = dbo.UW_DOCUMENT.STATUS_ID INNER JOIN "
				+ " dbo.REMITTANCE ON dbo.UW_DOCUMENT.REMITTANCEID = dbo.REMITTANCE.RemittanceID "
				+ "	where dbo.UW_CONTRACT_STATUS.ID =2 and dbo.ALLSALES_DETAILS.CERT = '" + contractNumber + "';";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets business processor search all sales details and return us latest
	 * contract id
	 * 
	 */
	public HashMap<String, String> busineerProcessor_getbusineerProcessorSearchData(
			HashMap<String, String> searchParamaters) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String fromdate = "";
		String todate = "";

		if (searchParamaters.get("From_Date") != null) {
			Date fromdate1 = new SimpleDateFormat("MM/dd/yyyy").parse(searchParamaters.get("From_Date"));
			fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fromdate1);
		} else {
			fromdate = null;
		}

		if (searchParamaters.get("To_Date") != null) {
			Date todate1 = new SimpleDateFormat("MM/dd/yyyy").parse(searchParamaters.get("To_Date"));
			Calendar c = Calendar.getInstance();
			c.setTime(todate1);
			c.add(Calendar.HOUR_OF_DAY, +23);
			c.add(Calendar.MINUTE, +59);
			todate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.getTime());
		} else {
			todate = null;
		}
		String query = "";
		if (searchParamaters.get("Created_By") != null && fromdate != null && todate != null) {
			query = "select check_no, amount, remittance_number, created_Date, creator "
					+ "from UW_BUSINESS_PROCESSOR_CHECK " + " where '" + fromdate
					+ "' < =Created_Date  and Created_Date <= '" + todate + "' and remittance_number = '"
					+ searchParamaters.get("remittance_number") + "'and creator= '" + searchParamaters.get("Created_By")
					+ "' order by 1 desc ;";
		}

		if (searchParamaters.get("Created_By") != null && fromdate == null && todate == null) {
			query = "select check_no, amount, remittance_number, created_Date, creator "
					+ "from UW_BUSINESS_PROCESSOR_CHECK " + " where remittance_number = '"
					+ searchParamaters.get("remittance_number") + "' and creator= '"
					+ searchParamaters.get("Created_By") + "' order by 1 desc ;";
		}

		if ((searchParamaters.get("Created_By") == null || searchParamaters.get("Created_By") == "") && fromdate != null
				&& todate == null) {
			query = "select check_no, amount, remittance_number, created_Date, creator "
					+ "from UW_BUSINESS_PROCESSOR_CHECK " + " where '" + fromdate
					+ "' < =Created_Date and remittance_number = '" + searchParamaters.get("remittance_number")
					+ "' order by 1 desc ;";
		}

		if ((searchParamaters.get("Created_By") == null || searchParamaters.get("Created_By") == "") && fromdate == null
				&& todate != null) {
			query = "select check_no, amount, remittance_number, created_Date, creator "
					+ "from UW_BUSINESS_PROCESSOR_CHECK " + " where Created_Date <= '" + todate
					+ "' and remittance_number = '" + searchParamaters.get("remittance_number") + "' order by 1 desc ;";
		}

		if (searchParamaters.get("Created_By") != null && fromdate != null && todate == null) {
			query = "select check_no, amount, remittance_number, created_Date, creator "
					+ "from UW_BUSINESS_PROCESSOR_CHECK " + " where '" + fromdate
					+ "' < =Created_Date and remittance_number = '" + searchParamaters.get("remittance_number")
					+ "' and creator= '" + searchParamaters.get("Created_By") + "' order by 1 desc ;";
		}
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This is used to search on business processor
	 * 
	 */
	public HashMap<String, String> businessProcessor_Search(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		//// get data for query
		String fromdate = "";
		if (searchParamater.get("From_Date").equals("*")) {
			c.add(Calendar.DATE, -60);
			fromdate = dateFormat.format(c.getTime());
		} else
			try {
				fromdate = searchParamater.get("From_Date");
				if (fromdate == null) {
					fromdate = "";
				} else {
					//// parse string to date
					Date fromdate1 = new SimpleDateFormat("MM/dd/yyyy").parse(fromdate);
					fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fromdate1);
				}
			} catch (Exception e) {
			}

		String todate = "";
		if (searchParamater.get("To_Date").equals("*")) {
			c.setTime(currentDate);
			c.add(Calendar.DATE, -2);
			todate = dateFormat.format(c.getTime());
		} else
			try {
				todate = searchParamater.get("To_Date");
				if (todate == null || todate == "") {
					todate = "";
				} else {
					//// parse string to date
					Date todate1 = new SimpleDateFormat("MM/dd/yyyy").parse(todate);
					todate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(todate1);
				}
			} catch (Exception e) {
			}

		String createdby;
		if (searchParamater.get("CreatedBy").equals(null))
			createdby = "";
		else
			createdby = searchParamater.get("CreatedBy");

		String query = "";
		if (!searchParamater.get("From_Date").equals("") && !searchParamater.get("To_Date").equals("")) {
			query = "select top 1 * from [dbo].[UW_BUSINESS_PROCESSOR_CHECK] where " + " '" + fromdate
					+ "' < =Created_Date  and Created_Date <= '" + todate + "' ; ";
		}

		else {
			query = "select top 1 * from [dbo].[UW_BUSINESS_PROCESSOR_CHECK] order by created_Date desc ;";

		}
		dbMap = getTopRowDataFromDatabase(query);
		dbMap.put("From_Date", fromdate);
		dbMap.put("To_Date", todate);
		dbMap.put("CreatedBy", createdby);
		if (dbMap.get("CreatedBy").equals("*")) {
			dbMap.replace("CreatedBy", dbMap.get("CREATOR"));
		}

		return dbMap;
	}

	/**
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> getroleId(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 name as Account_Name, role_identifier as RoleId, role_name as Role_Type,"
				+ "status as Status  from account acc "
				+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id "
				+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id  " + " where act.role_name ='"
				+ map.get("Role_Type") + "' and acc.role_identifier like '%" + map.get("RoleId") + "%' "
				+ " and sts.status='" + map.get("Status") + "' order by NEWID(); ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets roleID based on roleType, Status having Pricesheet
	 * 
	 */
	public HashMap<String, String> getCheckDetailsFromDB(String remitNumber) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		dbMap = getTopRowDataFromDatabase(
				"select top 1 check_no,amount,account_id as roleid, name as accountname from UW_BUSINESS_PROCESSOR_CHECK ubw "
						+ "join dbo.account acc on ubw.account_id=acc.role_identifier where remittance_number='"
						+ remitNumber + "' order by 1 desc");
		return dbMap;
	}

	/**
	 * This gets SearchDataCountOnSearchScreen for web contract
	 * 
	 */
	public HashMap<String, String> search_SearchWeb(HashMap<String, String> searchParamater) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
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

			for (@SuppressWarnings("rawtypes")
			Map.Entry mapElement : searchParamater.entrySet()) {
				String key = (String) mapElement.getKey();

				value = (String) mapElement.getValue();

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

			myKey = myKey.substring(0, myKey.lastIndexOf(","));
			myvalue = myvalue.substring(0, myvalue.lastIndexOf("and"));
			query = " select top 1 " + myKey + " " + " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) "
					+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) " + " ON a.ID = s.PRIMARY_SELLER_ID "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id" + " where "
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
			String query1 = "select  s.cert as Contract,s.First_Name, "
					+ " s.Last_Name ,s.VIN, s.Status,s.STATE,s.CITY,s.Phone,"
					+ " s.PROGRAM_CODE as Code ,format(s.PURCHASE_DATE,'dd-MM-yyyy') as Sale_Date,"
					+ " a.role_identifier as Primary_Seller_Id,"
					+ " a.name as Primary_Seller_Name, accType.role_name as Primary_Seller_Type ,"
					+ " aa.role_identifier as Secondary_Seller_ID,"
					+ " aa.name as Secondary_Seller_Name, accType3.role_name as Secondary_Seller_Type"
					+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
					+ " ON a.ID = s.PRIMARY_SELLER_ID join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK) ON aa.ID = s.SECONDARY_SELLER_ID "
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id "
					+ " where s.cert ='" + contractId + "';";
			dbMap = getTopRowDataFromDatabase(query1);
			String query2 = "SELECT TOP 1  PAR.PAYEE_ID as Primary_Payee_ID " + " FROM dbo.ALLSALES_DETAILS SD JOIN "
					+ " dbo.ACCOUNT ACC ON SD.PRIMARY_ACCOUNT_ID = ACC.ID  JOIN "
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

			if (value == ("NULL") || value == ("None") || value == ("")) {
				dbMap.replace(key, "***");

			}
		}
		return dbMap;
	}

	//// This function count no of rows from database for given condition on web
	//// contract

	public HashMap<String, String> searchCountFromDatabaseWebContract(HashMap<String, String> searchParamater)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
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

				if (key.equals("Primary_Seller_ID") || key.equals("Primary_Seller_Name")) {
					String table = "a.";
					key = table + key;
				}
				if (key.equals("Secondary_Seller_Name") || key.equals("Secondary_Seller_Type")) {
					String table = "aa.";
					key = table + key;
				}
				if (key.equals("Primary_Seller_Type")) {
					String table = "accTyp.";
					key = table + key;
				}

				if (key.equals("Secondary_Seller_Type")) {
					String table = "accTyp3.";
					key = table + key;
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
			query = " SELECT SUM(Kcount) as count from  (select count(1) as Kcount , " + myKey + " "
					+ " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK) " + " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) "
					+ " ON a.ID = s.Primary_Seller_Id "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK)" + " ON aa.ID = s.SECONDARY_SELLER_ID"
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id" + " where "
					+ myvalue + "Group by " + myKey + ") as test ;";
			dbMap = getTopRowDataFromDatabase(query);
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
	public HashMap<String, String> searchCompareDataWebContract(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query1 = "select s.[AUL_PREMIUM],s.[COVERAGE],s.[MILEAGE],s.[PROGRAM_CODE],s.[TERM],s.[PRICE],"
					+ " s.[FIRST_NAME],s.[LAST_NAME],s.VIN,s.STATE,s.CITY,s.STATUS," + " a.name as Primary_Seller_Name,"
					+ " accType.role_name Primary_Seller_Type   " + " from [dbo].[WEB_CONTRACTS] s WITH (NOLOCK)"
					+ " LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK)" + " ON a.ID = s.PRIMARY_SELLER_ID "
					+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id "
					+ " left join [ACCOUNT] AS aa WITH (NOLOCK) " + " ON aa.ID = s.SECONDARY_SELLER_ID "
					+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id "
					+ " where s.cert ='" + contractId + "';";
			dbMap = getTopRowDataFromDatabase(query1);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * This function get the agent role id
	 * 
	 */
	public HashMap<String, String> getAgentRoleId(String agentId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 role_identifier as RoleId, role_name as Role_Type,"
					+ " status as Status  from account acc   "
					+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id "
					+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id  "
					+ " where act.role_name ='Agent' and acc.role_identifier like '%" + agentId + "%' "
					+ "  and sts.status='Active' order by NEWID(); ";

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
	 * This function gives the contract id with associated roleid
	 * 
	 */
	public HashMap<String, String> getContractIdWithRoleID(String roleId, String roleType) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select top 1 cert as ContractId from dbo.allsales_details asd "
					+ " join dbo.account acc on  asd.primary_account_id=acc.id "
					+ " join dbo.account_role_type  art on acc.account_type_id=art.ACCOUNT_TYPE_ID "
					+ " where acc.role_identifier='" + roleId + "' and art.role_name='" + roleType
					+ "' order by 1 desc ";

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
	 * This function get all ACR added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedDataOfACRScreenFromDB() throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select top 1 Post_Period as PostPeriod,Cert as ContractID,Primary_Account_Id as RoleID,"
					+ " Check_no as CheckNo, Check_amt as CheckAmount, AdjType as AdjustmentType, "
					+ " Adjtype_cat as AdjustmentCategpry, Trans_Date as Trans_Date, Created_by as CreatedBy "
					+ "  from ALLTRANS_DETAILS order by 1 desc  ";

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
	 * This function get all addtional Payments added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedDataOfAdditinalPaymentsFromDB(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 Post_Period as PostPeriod,Cert as ContractId, "
					+ " check_no as CheckNo, check_amt as CheckAmount, "
					+ " reason as Comments from UW_REMITTANCE_CHECK_DETAILs " + " where cert= '" + contractId
					+ "' order by 1 desc ";

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
	 * This function correct all data needed to calculate premium
	 * 
	 */
	public HashMap<String, String> getContractIdOnTheBasisOfActiveRoleOId(HashMap<String, String> map)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = null;
		if (map.get("RoleId") == null || map.get("RoleId") == "") {
			query = "select top 1 name as Account_Name, role_identifier as RoleId, role_name as Role_Type,status as Status, cert as ContractId  from account acc  "
					+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id  "
					+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id join dbo.allsales_details asd on asd.primary_account_id=acc.id "
					+ " where act.role_name ='" + map.get("Role_Type") + "' and acc.role_identifier like '%%'  "
					+ " and sts.status='" + map.get("Status") + "' order by NEWID(); ";
		} else {
			query = "select top 1 name as Account_Name, role_identifier as RoleId, role_name as Role_Type,status as Status, cert as ContractId  from account acc  "
					+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id  "
					+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id join dbo.allsales_details asd on asd.primary_account_id=acc.id "
					+ " where act.role_name ='" + map.get("Role_Type") + "' and acc.role_identifier ='"
					+ map.get("RoleId") + "'  " + " and sts.status='" + map.get("Status") + "' order by NEWID(); ";
		}
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This function get the agent role id
	 * 
	 */
	public HashMap<String, String> getAgentRoleId(HashMap<String, String> map) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = null;
		try {
			if (map.get("AgentRoleId") == null || map.get("AgentRoleId") == "") {

				query = "  select top 1 role_identifier as AgentRoleId, role_name as Role_Type,status as Status  from account acc  "
						+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id  "
						+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id   "
						+ " where act.role_name ='Agent' and acc.role_identifier like '%" + map.get("AgentRoleId")
						+ "%'  and sts.status='Active' order by NEWID();";

			} else {
				query = "  select top 1 role_identifier as AgentRoleId, role_name as Role_Type,status as Status  from account acc  "
						+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id  "
						+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id   "
						+ " where act.role_name ='Agent' and acc.role_identifier ='" + map.get("AgentRoleId")
						+ "'  and sts.status='Active' order by NEWID();";

			}
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
	 * This function get all ACR added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedDataOfACRScreenFromDB(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select top 1 Post_Period as PostPeriod,Cert as ContractId,Primary_Account_Id as RoleId,"
					+ " Check_no as CheckNo, Check_amt as CheckAmount, AdjType as AdjustmentType, "
					+ " Adjtype_cat as AdjustmentCategpry, Trans_Date as Trans_Date, Created_by as CreatedBy "
					+ "  from ALLTRANS_DETAILS where cert= '" + contractId + "'order by 1 desc  ";

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
		if (dbMap.containsKey("Trans_Date")) {
			String Trans_Date = convertDate(dbMap.get("Trans_Date"), 0);
			dbMap.replace("Trans_Date", Trans_Date);
		}

		return dbMap;
	}

	/**
	 * This gets business processor search grid all the data
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> businessProcessor_getAllSearchGridData(
			HashMap<String, String> searchParamaters) throws Exception {
		String fromdate = "";
		String todate = "";

		if (searchParamaters.get("From_Date") != null) {
			Date fromdate1 = new SimpleDateFormat("MM/dd/yyyy").parse(searchParamaters.get("From_Date"));
			fromdate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fromdate1);
		} else {
			fromdate = null;
		}

		if (searchParamaters.get("To_Date") != null) {
			Date todate1 = new SimpleDateFormat("MM/dd/yyyy").parse(searchParamaters.get("To_Date"));
			Calendar c = Calendar.getInstance();
			c.setTime(todate1);
			c.add(Calendar.HOUR_OF_DAY, +23);
			c.add(Calendar.MINUTE, +59);
			todate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.getTime());
		} else {
			todate = null;
		}
		String query = "";
		if (searchParamaters.get("Created_By") != null && fromdate != null && todate != null) {
			query = "select uw.check_no as CheckNo, uw.amount as CheckAmount, uw.remittance_number as RemittanceNumber, rem.remittancename as RemittanceName, uw.created_Date as DateDeposited, "
					+ " uw.creator as Creator, acc.role_identifier as RoleIdentifier, acc.name as AccountName , uw.account_id as AccountId "
					+ " from UW_BUSINESS_PROCESSOR_CHECK uw left join account acc on acc.id=uw.ACCOUNT_ID "
					+ " left join [dbo].[REMITTANCE] rem on rem.remittancenumber=uw.remittance_number " + " where '"
					+ fromdate + "' <=Created_Date  and Created_Date <= '" + todate + "' and creator= '"
					+ searchParamaters.get("Created_By") + "' " + " and account_id is not null order by 1 desc ;";
		}

		if (searchParamaters.get("Created_By") != null && fromdate == null && todate == null) {
			query = "select uw.check_no as CheckNo, uw.amount as CheckAmount, uw.remittance_number as RemittanceNumber, rem.remittancename as RemittanceName, uw.created_Date as DateDeposited, "
					+ " uw.creator as Creator, acc.role_identifier as RoleIdentifier, acc.name as AccountName , uw.account_id as AccountId "
					+ " from UW_BUSINESS_PROCESSOR_CHECK uw left join account acc on acc.id=uw.ACCOUNT_ID  "
					+ " left join [dbo].[REMITTANCE] rem on rem.remittancenumber=uw.remittance_number "
					+ " where creator= '" + searchParamaters.get("Created_By")
					+ "' and account_id is not null order by 1 desc ;";
		}

		if ((searchParamaters.get("Created_By") == null || searchParamaters.get("Created_By") == "") && fromdate != null
				&& todate == null) {
			query = "select uw.check_no as CheckNo, uw.amount as CheckAmount, uw.remittance_number as RemittanceNumber, rem.remittancename as RemittanceName, uw.created_Date as DateDeposited, "
					+ " uw.creator as Creator, acc.role_identifier as RoleIdentifier, acc.name as AccountName , uw.account_id as AccountId"
					+ " from UW_BUSINESS_PROCESSOR_CHECK uw left join account acc on acc.id=uw.ACCOUNT_ID  "
					+ " left join [dbo].[REMITTANCE] rem on rem.remittancenumber=uw.remittance_number " + " where '"
					+ fromdate + "' < =Created_Date  and account_id is not order by 1 desc ;";
		}

		if ((searchParamaters.get("Created_By") == null || searchParamaters.get("Created_By") == "") && fromdate == null
				&& todate != null) {
			query = "select uw.check_no as CheckNo, uw.amount as CheckAmount, uw.remittance_number as RemittanceNumber, rem.remittancename as RemittanceName, uw.created_Date as DateDeposited, "
					+ " uw.creator as Creator, acc.role_identifier as RoleIdentifier, acc.name as AccountName , uw.account_id as AccountId "
					+ " from UW_BUSINESS_PROCESSOR_CHECK uw left join account acc on acc.id=uw.ACCOUNT_ID  "
					+ " left join [dbo].[REMITTANCE] rem on rem.remittancenumber=uw.remittance_number "
					+ " where Created_Date <= '" + todate + "' and account_id is not null order by 1 desc ;";
		}

		if (searchParamaters.get("Created_By") != null && fromdate != null && todate == null) {
			query = "select uw.check_no as CheckNo, uw.amount as CheckAmount, uw.remittance_number as RemittanceNumber, rem.remittancename as RemittanceName, uw.created_Date as DateDeposited, "
					+ " uw.creator as Creator, acc.role_identifier as RoleIdentifier, acc.name as AccountName , uw.account_id as AccountId "
					+ " from UW_BUSINESS_PROCESSOR_CHECK uw left join account acc on acc.id=uw.ACCOUNT_ID "
					+ " left join [dbo].[REMITTANCE] rem on rem.remittancenumber=uw.remittance_number " + " where '"
					+ fromdate + "' < =Created_Date  and creator= '" + searchParamaters.get("Created_By")
					+ "' and account_id is not null order by 1 desc ;";
		}
		return getAllDataFromDatabase(query);
	}

	/**
	 * This function used to get the remittance information
	 * 
	 */
	public HashMap<String, String> getRemittanceInformationFromDBForRemittanceSummary(String remittance)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select  rem.RemittanceNumber as RemittanceNumber,rem.remittanceName as RemittanceName,rem.remit_Subtype_id as RemitType, "
					+ " rem.PostPeriod as PostPeriod,rem.createByUser as CreatedDate,rem.CoreCount as CoreCount,rem.LWACount as LWACount from remittance rem "
					+ " join UW_DOCUMENT uw on rem.RemittanceID = uw.REMITTANCEID"
					+ " join UW_DOCUMENTTYPE  documentType on uw.DOCUMENTTYPEID= documentType.DOCUMENTTYPEID "
					+ " join  UW_CONTRACT_STATUS  ContractStatus on uw.STATUS_ID = ContractStatus.ID "
					+ " where rem.RemittanceNumber ='" + remittance
					+ "' and documentType.DOCUMENTTYPENAME='Contract' order by 1 desc";

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
	 * This function used to get the Contract information
	 * 
	 */
	public HashMap<String, String> getContractInformationFromDBForRemittanceSummary(String Contract) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select Cert as Contract,CUSTOMER_FIRST as FirstName, CUSTOMER_LAST as LastName,DEALER_PAID as DealerPaid,DBCR_AMT as DbCrAmount,PROGRAM_CODE as PrograCode,SALE_Date As SaleDate,PRIMARY_ACCOUNT_ID as PrimaryRoleId, SECONDARY_ACCOUNT_ID as SecondaryRoleId,COMMENTS as Comments "
					+ " from [ALLSALES_DETAILS] where cert='" + Contract + "' order by 1 desc ";

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
	 * This function get all addtional Payments added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedDataOfAdditinalPaymentsFromDBForRemittanceSummary(String contractId)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "select top 1 check_no as CheckNo, check_amt as CheckAmount,PRIMARY_SELLER_ID as AccountNumber,CREATED_BY as CreatedBy  from UW_REMITTANCE_CHECK_DETAILs  where cert= '"
					+ contractId + "' order by 1 desc ";

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
	 * This gets onhold contract
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> searchOnHoldContractWithremittance() throws Exception {
		HashMap<Integer, HashMap<String, String>> dbMap = new HashMap<Integer, HashMap<String, String>>();
		try {
			String query1 = "select dbo.REMITTANCE.RemittanceNumber, dbo.UW_CONTRACT_STATUS.name, dbo.ALLSALES_DETAILS.CERT as Contract_Number, dbo.REMITTANCE.CoreCount"
					+ " from dbo.ALLSALES_DETAILS INNER JOIN dbo.UW_CONTRACT_STATUS ON dbo.ALLSALES_DETAILS.CONTRACT_STATUS_ID = dbo.UW_CONTRACT_STATUS.ID INNER JOIN "
					+ " dbo.UW_DOCUMENT ON dbo.ALLSALES_DETAILS.ID = dbo.UW_DOCUMENT.ALLSALES_DETAILS_ID AND dbo.UW_CONTRACT_STATUS.ID = dbo.UW_DOCUMENT.STATUS_ID INNER JOIN"
					+ " dbo.REMITTANCE ON dbo.UW_DOCUMENT.REMITTANCEID = dbo.REMITTANCE.RemittanceID where dbo.UW_CONTRACT_STATUS.ID =2 and dbo.REMITTANCE.CoreCount=1";
			dbMap = getAllDataFromDatabase(query1);
		} catch (Exception e) {
			throw e;
		} finally {
			//// close connection
			closeConnection();
		}
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public String cancellation_getContractIdBasedOnStatus(String status) throws Exception {
		String contract_id = "";
		String query = "select top 1 sale.id, CERT from [dbo].[ALLSALES_DETAILS] sale join [dbo].[UW_CONTRACT_STATUS] sta "
				+ "on sale.CONTRACT_STATUS_ID = sta.ID where sta.NAME = '" + status + "' order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		contract_id = dbMap.get("CERT");
		return contract_id;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractIdBasedOnStatusBList(String status) throws Exception {
		String query = "select r.RemittanceName,asd.CERT,asd.SECONDARY_ACCOUNT_ID from ALLSALES_DETAILS asd join REMITTANCE r on asd.REMITTANCE_ID = r.RemittanceID "
				+ " join [dbo].[UW_CONTRACT_STATUS] sta on sta.ID = asd.CONTRACT_STATUS_ID "
				+ " where r.REMITTANCE_STATUS_ID = 2\r\n" + "and sta.name = '" + status + "' "
				+ "and asd.SECONDARY_ACCOUNT_ID in (select ac.id from [dbo].[ACCOUNT_PROPERTY] p join [dbo].[ACCOUNT_PROPERTY_VALUE] v on p.id = v.account_property_id "
				+ "join [dbo].[ACCOUNT_PROPERTY_GROUP] g on g.property_value_id = v.id "
				+ "join  [dbo].[ACCOUNT] ac on ac.id = g.account_id " + "where STRING_VALUE in ('B-List')) "
				+ " order by 1 desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> Underwriting_getCheckDetail(String remittanceNumber)
			throws Exception {

		String query = "select Check_no, Amount from dbo.UW_BUSINESS_PROCESSOR_CHECK where remittance_number = '"
				+ remittanceNumber + "'";
		HashMap<Integer, HashMap<String, String>> dbMap = getAllDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This gets search firstname and last name of customer based on contract id
	 * 
	 * 
	 */
	public HashMap<String, String> dBValidation(String cert) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select CUSTOMER_FIRST as firstName , CUSTOMER_LAST as lastName from [dbo].[ALLSALES_DETAILS] where cert = '"
				+ cert + "';";
		dbMap = getTopRowDataFromDatabase(query);

		return dbMap;
	}

	/**
	 *
	 * / * This gets search contract on the basis of ford f-150/ non ford f-150
	 * vehicle type status
	 * 
	 */

	public String searchContractOnBasisVehicleType(String vehicleType) throws Exception {
		String queryPart = "";
		if (vehicleType.equals("Ford F150"))
			queryPart = "model = 'f150' ";
		else
			queryPart = "model <> 'f150' ";

		String query = "select top 1 CERT from [dbo].[WEB_CONTRACTS] where Status in ('Entered', 'Remitted') and "
				+ queryPart + "order by date_created desc;";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		String contract_id = dbMap.get("CERT");
		return contract_id;
	}

	/**
	 * This gets the value of current post period
	 * 
	 * 
	 */
	public String getCurrentPostPeriod() throws Exception {
		String query = "select Top 1 Post_Period_Date from UW_POSTPERIOD_CURRENT where Type = 'AccountsAndCredits' AND Is_Closed = 0";
		HashMap<String, String> dbMap = getTopRowDataFromDatabase(query);
		String postPeriod = dbMap.get("Post_Period_Date");
		postPeriod = postPeriod.substring(0, postPeriod.indexOf('-') + 3).replace("-", "");
		return postPeriod;
	}

	/**
	 * This gets the value of value of Account Statement Grid on the basis of
	 * givenPostPeriod, roleType and Credit/Debit/Zerobalance
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getAccountStatementGridData(String givenPostPeriod,
			String roleType, String functionPerform) throws Exception {

		String query = "";
		String roleTypePart = "";
		String queryPart = "";
		String queryZeroBalPart = "";
		String priorPostPeriod;

		if (givenPostPeriod.substring(4, 6).equals("01") || givenPostPeriod.substring(4, 6).equals("02")) {
			priorPostPeriod = String.valueOf(Integer.parseInt(givenPostPeriod) - 90);
		} else {
			priorPostPeriod = String.valueOf(Integer.parseInt(givenPostPeriod) - 2);
		}

		if (roleType.equalsIgnoreCase("Dealer") || roleType.equalsIgnoreCase("Lender"))
			roleTypePart = "('" + roleType + "') ";
		else
			roleTypePart = "('Dealer', 'Lender') ";

		if (functionPerform.contains("Credit")) {
			queryPart = "AND ROUND([A].[EOM_POSTED_DBCR], 2) > 0 ";
		} else if (functionPerform.contains("Debit")) {
			queryPart = "AND ROUND([A].[EOM_POSTED_DBCR], 2) < 0 ";
		} else {
			queryPart = "AND ROUND([A].[EOM_POSTED_DBCR], 2) = 0 ";
			queryZeroBalPart = "AND [A].[ACCOUNT_ID] in (SELECT DISTINCT [PRIMARY_ACCOUNT_ID] from [ocean].[ALLTRANS] WHERE [ADJTYPE] = 'NEWBUS' "
					+ "AND [POST_PERIOD] >= '" + priorPostPeriod + "' " + "AND [POST_PERIOD] <= '" + givenPostPeriod
					+ "' ) ";
		}

		query = "USE AULDATAMART; SELECT [ACC].[MAIN_AGENT_ROLE_IDENTIFIER] AS [Agent ID],[ACC].[COMPANY_STATUS] AS [Status],[A].[ROLE_NAME] AS [Role Type], "
				+ "[A].[ROLE_IDENTIFIER] AS [Role ID], [ACC].[ACCOUNT_NAME] AS [Account Name], ROUND([A].[EOM_POSTED_DBCR], 2) [EOM Posted DBCR], "
				// + "ROUND([A].[EOM_POSTED_DBCR], 2) [Prior1 EOM], ROUND([A].[EOM_POSTED_DBCR],
				// 2) [Prior2 EOM], "
				+ "ROUND([A].[POSTED_DBCR], 2) [Posted DBCR], ROUND([A].[ONHOLD_DBCR], 2) [Onhold DBCR] "
				+ "FROM [eom_stmt].[ALLTRANS_EOM_ACCOUNT] AS [A] "
				+ "JOIN [ocean].[ACCOUNTS] AS [ACC] ON [A].[ACCOUNT_ID] = [ACC].[ACCOUNT_ID] where ROLE_NAME in "
				+ roleTypePart + "AND POST_PERIOD = '" + givenPostPeriod + "' " + queryPart + queryZeroBalPart
				+ "order by [A].[EOM_POSTED_DBCR] desc";

		// System.out.println("query: " + query);
		HashMap<Integer, HashMap<String, String>> dbData = getAllDataFromDatabase(query);
		for (int i = 1; i < dbData.size(); i++) {
			HashMap<String, String> dbMap = dbData.get(i);

			String postedDBCR = dbMap.get("Posted DBCR");
			String eomPostedDBCR = dbMap.get("EOM Posted DBCR");
			String roleID = dbMap.get("Role ID");
			DecimalFormat df = new DecimalFormat("0.00");
			double prior1EOM = Double.parseDouble(eomPostedDBCR) - Double.parseDouble(postedDBCR);
			dbMap.put("Prior1 EOM", df.format(prior1EOM));

			String lastPostPeriod = "";
			if (givenPostPeriod.substring(4, 6).equals("01"))
				lastPostPeriod = String.valueOf(Integer.parseInt(givenPostPeriod) - 89);
			else
				lastPostPeriod = String.valueOf(Integer.parseInt(givenPostPeriod) - 1);

			String query1 = "USE AULDATAMART; SELECT  ROUND([A].[POSTED_DBCR], 2) [Posted DBCR] FROM [eom_stmt].[ALLTRANS_EOM_ACCOUNT] AS [A] "
					+ "JOIN [ocean].[ACCOUNTS] AS [ACC] ON [A].[ACCOUNT_ID] = [ACC].[ACCOUNT_ID] where ROLE_NAME in "
					+ roleTypePart + "AND POST_PERIOD = '" + lastPostPeriod + "' " + queryPart + queryZeroBalPart
					+ "AND [A].[ROLE_IDENTIFIER] = '" + roleID + "'";

			HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query1);
			// System.out.println("query1: " + query1);
			String lastPostedDBCR = "0.00";
			if (dbMap1.size() > 0)
				lastPostedDBCR = dbMap1.get("Posted DBCR");
			double prior2EOM = prior1EOM - Double.parseDouble(lastPostedDBCR);
			dbMap.put("Prior2 EOM", df.format(prior2EOM));

		}
		return dbData;
	}

	/**
	 * This function get all Returns added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedReturnsContractDataFromDB(String contractId, String roleType)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		HashMap<String, String> dbMap1 = new HashMap<String, String>();
		String secondaryRoleId = "";
		String primaryRoleId = "";

		String queryPart = "";
		String queryPart1 = "";
		if (roleType.equalsIgnoreCase("Dealer")) {
			queryPart = " and a.role_type_id = 1 ";
			queryPart1 = " and a.role_type_id = 4 ";
			primaryRoleId = "DealerID";
			secondaryRoleId = "LenderID";

		} else {
			queryPart = " and a.role_type_id = 4 ";
			queryPart1 = " and a.role_type_id = 1 ";
			primaryRoleId = "LenderID";
			secondaryRoleId = "DealerID";
		}
		String query = "Select top 1 Post_Period as PostPeriod, Cert as ContractId, Check_no as CheckNo, Check_amt as CheckAmount, Trans_Date as Trans_Date,"
				+ " DBCR_AMT as DBCR, DEALER_PAID as DealerPaid, STD_COMMENTS as Comments, CREATED_BY as UserName, PROGRAM_CODE as Admin, "
				+ " RemittanceNumber as RemitNumber, ad.Secondary_Account_Id as SecAccountID, ad.Primary_Account_Id as PrimAccountID, a.role_identifier as "
				+ primaryRoleId
				+ " from ALLTRANS_DETAILS ad left join [dbo].[ACCOUNT] a on ad.Primary_Account_Id= a.id "
				+ " join REMITTANCE r on ad.REMITTANCE_ID = r.RemittanceID  " + " where cert= '" + contractId
				+ "' and adjtype='RETURN' " + queryPart + " order by 1 desc ";

		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);
		String secondaryAccId = dbMap.get("SecAccountID");

		String query1 = "select a.role_identifier from [dbo].[ACCOUNT] a  where a.id = '" + secondaryAccId + "' "
				+ queryPart1;
		dbMap1 = getTopRowDataFromDatabase(query1);

		if (dbMap.containsKey("Trans_Date")) {
			String Trans_Date = convertDate(dbMap.get("Trans_Date"), 0);
			dbMap.replace("Trans_Date", Trans_Date);
		}

		dbMap.put("PrimaryRoleName", roleType);

		String secRoleId = dbMap1.get("role_identifier");
		dbMap.put(secondaryRoleId, secRoleId);
		return dbMap;
	}

	/**
	 * This function get all Returns added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedReturnsRowDataFromDB(HashMap<String, String> dbdataForExpander,
			String premium) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select count (*) as ContractCount from ALLTRANS_DETAILS ad where POST_PERIOD= '"
				+ dbdataForExpander.get("PostPeriod") + "' and adjtype='RETURN' and Primary_Account_Id = '"
				+ dbdataForExpander.get("PrimAccountID") + "' order by 1 desc ";

		//// save data in map
		dbMap = getTopRowDataFromDatabase(query);

		dbMap.put("Premium", premium);
		dbMap.put("DBCR", dbdataForExpander.get("DBCR"));
		dbMap.put("DealerPaid", dbdataForExpander.get("DealerPaid"));
		dbMap.put("Balance", dbdataForExpander.get("DBCR"));
		return dbMap;
	}

	/**
	 * This function is used to get the contract id on the basis of Role Id
	 * 
	 */
	public HashMap<String, String> getContractIdOnTheBasisOfActiveRoleOIdForRefundExpander(HashMap<String, String> map)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = null;
		if (map.get("RoleId") == null || map.get("RoleId") == "") {
			query = "select top 1 name as Account_Name, role_identifier as RoleId, role_name as Role_Type,status as Status, cert as ContractId  from account acc  "
					+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id  "
					+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id join dbo.Alltrans_details atd on atd.primary_account_id=acc.id "
					+ " where act.role_name ='" + map.get("Role_Type") + "' and acc.role_identifier like '%%'  "
					+ " and sts.status='" + map.get("Status") + "' and atd.ADJTYPE='NEWBUS'  order by NEWID(); ";
		} else {
			query = "select top 1 name as Account_Name, role_identifier as RoleId, role_name as Role_Type,status as Status, cert as ContractId  from account acc  "
					+ " join ACCOUNT_ROLE_TYPE act on acc.role_type_id = act.account_type_id  "
					+ " join ACCOUNT_STATUS sts on acc.account_status_id= sts.id join dbo.Alltrans_details atd on atd.primary_account_id=acc.id "
					+ " where act.role_name ='" + map.get("Role_Type") + "' and acc.role_identifier ='"
					+ map.get("RoleId") + "'  " + " and sts.status='" + map.get("Status")
					+ "' and atd.ADJTYPE='NEWBUS'  order by NEWID(); ";
		}
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This function get all ACR added data from DB
	 * 
	 */
	public HashMap<String, String> getAddedRefundDataForContractFromDB(String contractId, String roletype)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select top 1 atd.Post_Period as PostPeriod,atd.Cert as ContractId,acc.ROLE_IDENTIFIER as RoleID, atd.Check_no as CheckNo, "
					+ " atd.Check_amt as CheckAmount, atd.Trans_Date as Trans_Date, atd.DBCR_AMT as DBCR, atd.DEALER_PAID as DealerPaid, atd.STD_COMMENTS as Comments"
					+ " from ALLTRANS_DETAILS atd join account acc on atd.Primary_Account_Id=acc.ID " + " where cert= '"
					+ contractId + "' and adjtype='EXTAdJ'order by 1 desc ";

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
		if (dbMap.containsKey("Trans_Date")) {
			String Trans_Date = convertDate(dbMap.get("Trans_Date"), 0);
			dbMap.replace("Trans_Date", Trans_Date);
		}

		dbMap.put("PrimaryRoleName", roletype);
		return dbMap;
	}

	/**
	 * This gets search all sales details and return us latest contract id based on
	 * status
	 * 
	 */
	public HashMap<String, String> cancellation_getContractHistoryBasedOnStatus(String status) throws Exception {
		HashMap<String, String> dbMap;
		String allStatus = "('Authorized','Calculated','Check Assigned','Denied','OnHold','Posted','Processed','Quote','Reinstated')";
		String subQuery = "";
		if (status.equalsIgnoreCase("Authorized")) {
			subQuery = allStatus.replace("'" + status + "',", "");
		} else {
			subQuery = allStatus.replace(",'" + status + "'", "");
		}
		String query = "select top 1 asd.cert as Contract,asd.CUSTOMER_FIRST,"
				+ "cs.Name as Status,cp.CREATED_DATE as Process_Date,cp.REFUND_PERCENTAGE,cp.NET_REFUND_AMOUNT as Net_Refund "
				+ ", cp.CANCEL_MILEAGE as Cancel_Miles, cp.CANCEL_DATE, cib.NAME as INITIATED_BY,crt.NAME as Cancel_Reason "
				+ "from ALLSALES_DETAILS asd join CANCELLATION_PARAMETERS cp on asd.id = cp.ALLSALES_DETAILS_ID join "
				+ "[dbo].[CANCELLATION_STATUS] cs on cs.id = cp.STATUS_ID join [dbo].[CANCELLATION_INITIATED_BY] cib "
				+ "on cib.id = cp.INITIATED_BY_ID left join [dbo].[CANCELLATION_REASON_TYPE] crt "
				+ "on crt.id = cp.REASON_TYPE_ID " + "where cs.name = '" + status.toLowerCase()
				+ "' and cert not in (select asd1.cert from ALLSALES_DETAILS asd1 "
				+ "join CANCELLATION_PARAMETERS cp1 on asd1.id = cp1.ALLSALES_DETAILS_ID join [dbo].[CANCELLATION_STATUS] cs1 on cs1.id = cp1.STATUS_ID where "
				+ "cs1.name in " + subQuery + ") order by asd.id desc";
		System.out.println("query: " + query);
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This function get all new contract details from DB
	 * 
	 */
	public HashMap<String, String> getAddedNewContractDataForContractFromDB(String contractId) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select top 1 atd.Post_Period as PostPeriod,atd.Cert as ContractId,atd.Primary_Account_Id as RoleId,atd.Check_no as CheckNo, atd.Check_amt as CheckAmount, "
					+ " atd.Trans_Date as Trans_Date,atd.DBCR_AMT as DBCR, atd.DEALER_PAID as DealerPaid,atd.STD_COMMENTS as Comments, atd.CREATED_BY as UserId, "
					+ " rem.RemittanceNumber as RemitNum,atd.PROGRAM_CODE as AdminCode, atd.ADJTYPE as AdjType,asd.CUSTOMER_FIRST as FirstName,asd.CUSTOMER_LAST as LastName "
					+ " from ALLTRANS_DETAILS atdjoin REMITTANCE rem on atd.REMITTANCE_ID=rem.RemittanceIDjoin ALLSALES_DETAILS asd on atd.CERT=asd.CERT "
					+ " where atd.cert='" + contractId + "' and adjtype='NEWBUS'order by 1 desc ";

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
		if (dbMap.containsKey("Trans_Date")) {
			String Trans_Date = convertDate(dbMap.get("Trans_Date"), 0);
			dbMap.replace("Trans_Date", Trans_Date);
		}
		return dbMap;
	}

	/**
	 * This function get sepcheck added data from DB
	 * 
	 */
	public HashMap<String, String> getSepChkAddedDataForContractFromDB(String contractId, String roletype)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		try {
			String query = "Select top 1 atd.Post_Period as PostPeriod,atd.Cert as ContractId,acc.ROLE_IDENTIFIER as RoleId, atd.Check_no as CheckNo, "
					+ " atd.Check_amt as CheckAmount, atd.Trans_Date as Trans_Date, atd.DBCR_AMT as DBCR, atd.DEALER_PAID as DealerPaid, "
					+ " atd.STD_COMMENTS as Comments,atd.ADJTYPE_CAT as DetailsDescription from ALLTRANS_DETAILS atdjoin account acc on atd.Primary_Account_Id=acc.ID "
					+ " where cert= '" + contractId + "' and adjtype='SEPCHECK'order by 1 desc";

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
		if (dbMap.containsKey("Trans_Date")) {
			String Trans_Date = convertDate(dbMap.get("Trans_Date"), 0);
			dbMap.replace("Trans_Date", Trans_Date);
		}

		dbMap.put("PrimaryRoleName", roletype);
		return dbMap;
	}

	/**
	 *
	 * / * This gets search on hold contract on the basis of Role Id , Role Type and
	 * Post period for on hold/short pay section
	 * 
	 */
	public HashMap<String, String> fetchOnHoldGivenRoleIDPostPeriodDB(String roleID, String roleType, String postPeriod)
			throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select [CERT],format(TRANS_DATE,'MM/dd/yyyy') as TRANS_DATE ,[DEALER_PAID],"
				+ " [DBCR_AMT] ,[STD_COMMENTS] "
				+ " from [dbo].[ALLTRANS_DETAILS] atd  join ACCOUNT a on a.id = atd.PRIMARY_ACCOUNT_ID "
				+ " join ACCOUNT_ROLE_TYPE art on art.ACCOUNT_TYPE_ID = a.ACCOUNT_TYPE_ID "
				+ " where a.ROLE_IDENTIFIER ='" + roleID + "' and [POST_PERIOD] ='" + postPeriod
				+ "' and [ADJTYPE]= ONHOLD  and art.ROLE_NAME=' " + roleType + "' ";
		dbMap = getTopRowDataFromDatabase(query);
		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.isEmpty()) {
				dbMap.replace(key, "***");

			}
		}

		int DealerPaid = Integer.parseInt(dbMap.get("DEALER_PAID"));
		int dbcrAmt = Integer.parseInt(dbMap.get("DBCR_AMT"));
		String PRE = String.valueOf((DealerPaid - (dbcrAmt)));
		dbMap.put("PREMIUM", PRE);
		return dbMap;

	}

	/**
	 *
	 * / * This gets search all records value on hold pop up window on the basis of
	 * Role Id , Role Type and Post period
	 * 
	 */
	public HashMap<String, String> dbValueOnHoldPopupScreen(String roleID, String AdjType, String postPeriod)
			throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select distinct atd.[CERT],atd.[DEALER_PAID],atd.[DBCR_AMT] ,atd.[CHECK_AMT],a.ROLE_IDENTIFIER as DEALER_ID"
				+ "atd.[CHECK_NO],atd.[POST_PERIOD],atd.[PROGRAM_CODE],atd.[ADJTYPE],atd.STD_COMMENTS "
				+ " from [dbo].[ALLTRANS_DETAILS] atd  join ACCOUNT a on a.id = atd.PRIMARY_ACCOUNT_ID "
				+ " join ACCOUNT_ROLE_TYPE art on art.ACCOUNT_TYPE_ID = a.ACCOUNT_TYPE_ID "
				+ " join [dbo].[ALLSALES_DETAILS] asd on asd.ID = atd.[ALLSALES_DETAILS_ID] "
				+ " where a.ROLE_IDENTIFIER ='" + roleID + "' and asd.CONTRACT_STATUS_ID = 2  and [POST_PERIOD] ='"
				+ postPeriod + "' and [ADJTYPE]=' " + AdjType + "' ";
		dbMap = getTopRowDataFromDatabase(query);
		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.isEmpty()) {
				dbMap.replace(key, "***");
			}
		}
		int DealerPaid = Integer.parseInt(dbMap.get("DEALER_PAID"));
		int dbcrAmt = Integer.parseInt(dbMap.get("DBCR_AMT"));
		String PRE = String.valueOf((DealerPaid - (dbcrAmt)));
		dbMap.put("PREMIUM", PRE);
		return dbMap;

	}

	/**
	 *
	 * / * This gets search on hold contract on the basis of Role Id , Role Type and
	 * Post period
	 * 
	 */
	public HashMap<String, String> dbValueOnHoldcontract(String roleID, String AdjType, String postPeriod)
			throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select [CERT] from [dbo].[ALLTRANS_DETAILS] atd  join ACCOUNT a on a.id = atd.PRIMARY_ACCOUNT_ID "
				+ " join ACCOUNT_ROLE_TYPE art on art.ACCOUNT_TYPE_ID = a.ACCOUNT_TYPE_ID"
				+ " join [dbo].[ALLSALES_DETAILS] asd on asd.ID = atd.[ALLSALES_DETAILS_ID]"
				+ " left join [dbo].[ALLTRANS_FINCO_DATA_BASE] atdf on atdf.[ALLSALES_DETAILS_ID] = asd.ID"
				+ " where a.ROLE_IDENTIFIER ='" + roleID + "' and asd.CONTRACT_STATUS_ID = 2  and [POST_PERIOD] ='"
				+ postPeriod + "' and [ADJTYPE]= ' " + AdjType + "' ";
		dbMap = getTopRowDataFromDatabase(query);
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
	 *
	 * / * This gets search on contract on the basis of Role Id , Role Type and Post
	 * period
	 */
	public HashMap<String, String> dbSearchAdjTypeDetChange(int postPeriod, String ADJTYPE_DET, String roleName)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select distinct atd.[CERT],atd.[DEALER_PAID],atd.[DBCR_AMT] ,atd.[CHECK_AMT],atd.[CHECK_NO],"
				+ " atd.[POST_PERIOD],atd.[PROGRAM_CODE],atd.[ADJTYPE],atd.[ADJTYPE_CAT],"
				+ " a.ROLE_IDENTIFIER as Dealer,atd.[ADJTYPE_DET],atd.STD_COMMENTS "
				+ " from [dbo].[ALLTRANS_DETAILS] atd  join ACCOUNT a on a.id = atd.PRIMARY_ACCOUNT_ID "
				+ " join ACCOUNT_ROLE_TYPE art on art.ACCOUNT_TYPE_ID = a.ACCOUNT_TYPE_ID "
				+ " join [dbo].[ALLSALES_DETAILS] asd on asd.ID = atd.[ALLSALES_DETAILS_ID] "
				+ " where atd.[ADJTYPE] = 'UWADJ' and atd.[ADJTYPE_DET] =' " + ADJTYPE_DET + "' AL.ROLE_NAME =' "
				+ roleName + "'and atd.[POST_PERIOD] =" + postPeriod + " ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	public HashMap<String, String> dBmileageChangeData(int postPeriod, int adjID, int DealerId, String roleType)
			throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select BALANCE ,DBCR,DLR_PAID,ITEM_COUNT,TOTAL from eom_stmt.ALLSTMTS AL "
				+ " where AL.ROLE_IDENTIFIER = " + DealerId + " and AL.POST_PERIOD =" + postPeriod
				+ " and AL.ROLE_NAME = " + roleType + " " + " and al.STMT_ACCOUNT = " + adjID + " ";
		dbMap = getTopRowDataFromDatabase(query);
		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				dbMap.replace(key, "***");
			}
		}
		return dbMap;
	}

	public HashMap<String, String> dBSearchActiveRoleIDAndActiveContract(String roleType) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = " Select distinct top 1  a.[ROLE_IDENTIFIER] as DealerID ,s.CERT ,s.SALE_DATE "
				+ " from [dbo].[ALLSALES_DETAILS] s WITH (NOLOCK) "
				+ "	LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) ON a.ID = s.[PRIMARY_ACCOUNT_ID]  "
				+ "	join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id  "
				+ "	where  a.[ACCOUNT_STATUS_ID] =  1 and accType.[ROLE_NAME] ='" + roleType
				+ "' and s.CONTRACT_STATUS_ID = 5 " + "	order by s.SALE_DATE desc ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	public HashMap<String, String> accStatdBmileageChangeData(int postPeriod, int adjID, int DealerId, String roleType)
			throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select BALANCE ,DBCR,DLR_PAID,ITEM_COUNT,TOTAL from eom_stmt.ALLSTMTS AL "
				+ " where AL.ROLE_IDENTIFIER = " + DealerId + " and AL.POST_PERIOD =" + postPeriod
				+ " and AL.ROLE_NAME = '" + roleType + "' " + " and al.STMT_ACCOUNT = " + adjID + " ";
		dbMap = getTopRowDataFromDatabase(query);
		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();

			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				dbMap.replace(key, "***");
			}
		}
		return dbMap;
	}

	public HashMap<String, String> dBSearchRoleIdWithNoAdjCount(int postPeriod, int adjID, String roleType)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "select top 1 AL.ROLE_IDENTIFIER,AL.ITEM_COUNT  from eom_stmt.ALLSTMTS AL "
				+ " where AL.ITEM_COUNT =0 and AL.POST_PERIOD =" + postPeriod + " and AL.ROLE_NAME ='" + roleType + "' "
				+ " and al.STMT_ACCOUNT =" + adjID + " ";
		dbMap = getTopRowDataFromDatabase(query);
		for (Map.Entry<String, String> entry : dbMap.entrySet()) {
			String value = entry.getValue();
			value = value.trim();
			value = value.replaceAll("\\s+", "");
			String key = entry.getKey();
			if (value.equals("NULL") || value.equals("None") || value.equals("") || value.equals(" ")) {
				dbMap.replace(key, "***");
			}
		}
		String roleIdentifier = dbMap.get("ROLE_IDENTIFIER");
		String query1 = " select distinct top 1 s.cert as Contract,format(s.[SALE_DATE],'MM/dd/yyyy') as Created_Date,"
				+ " a.name as Primary_Account_Name, accType.role_name as Primary_Account_Type ,a.[ROLE_IDENTIFIER] as Primary_Account_ID,"
				+ " aa.role_identifier as Secondary_Account_ID,accType3.role_name as Secondary_Account_Type "
				+ " from [dbo].[ALLSALES_DETAILS]s WITH (NOLOCK) LEFT JOIN [ACCOUNT] AS a WITH (NOLOCK) ON a.ID = s.[PRIMARY_ACCOUNT_ID] "
				+ " join dbo.[ACCOUNT_ROLE_TYPE] accType on accType.id = a.Role_Type_Id  join [dbo].[ACCOUNT_ADDRESS] aadd on aadd.[ACCOUNT_ID]= a.Id "
				+ " left join [ACCOUNT] AS aa WITH (NOLOCK) ON aa.ID = s.[SECONDARY_ACCOUNT_ID] "
				+ " left join [dbo].[ACCOUNT_ADDRESS] aadd1 on aadd1.[ACCOUNT_ID]= aa.Id "
				+ " left join dbo.[ACCOUNT_ROLE_TYPE] accType3 on accType3.id = aa.Role_Type_Id "
				+ " where a.role_identifier ='" + roleIdentifier
				+ "' and s.CONTRACT_STATUS_ID = 5 order by Created_Date desc ";
		dbMap = getTopRowDataFromDatabase(query1);
		return dbMap;
	}

	/**
	 * This function get all the order of categories details from DB
	 * 
	 */
	public HashMap<Integer, HashMap<String, String>> getOrderOfCategoriesFromDB() throws Exception {
		String query = "select ACCOUNT_DESCR from eom_stmt.ALLSTMTS_STMT_ACCOUNT order by DISPLAY_SEQ";
		return getAllDataFromDatabase(query);
	}

	/**
	 * This function gets all required details used in TC 88
	 * 
	 */
	public HashMap<String, String> getDetailsOfCancelledContract(String status) throws Exception {
		String query = "select top 1 sales.CERT as CONTRACT_NUMBER, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.START_MILEAGE as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments,cp.CANCEL_MILEAGE as Cancel_Miles, cp.CANCEL_DATE,cp.CREATED_DATE as PROCESS_DATE,cp.NET_REFUND_AMOUNT as NET_REFUND, "
				+ "cp.REFUND_PERCENTAGE from [dbo].[ALLSALES_DETAILS] sales "
				+ "join CANCELLATION_PARAMETERS cp on sales.id = cp.ALLSALES_DETAILS_ID"
				+ "join [dbo].[ACCOUNT] account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where statuss.name = '"
				+ status + "' order by sales.id desc;";
		return getTopRowDataFromDatabase(query);
	}

	/**
	 * This function gets all required details for cancelled contract for new cancel
	 * page under Account Statement
	 * 
	 */
	public HashMap<String, String> getNewCancellationDetailsforCanceledContract(String contract) throws Exception {
		String query = "select top 1 sales.CERT as Contract_Number, account.NAME as "
				+ "Primary_Account,account.ROLE_IDENTIFIER as Primart_Acct_Id, accS.Status as Primary_Acct_Status, "
				+ "CONCAT(sales.CUSTOMER_FIRST, ' ', sales.CUSTOMER_LAST) "
				+ "AS Customer_Name,sales.SALE_DATE as Sale_Date,sales.START_MILEAGE as Sale_Mileage,sales.VIN, "
				+ "(sales.DEALER_PAID - sales.DBCR_AMT )as Premium, sales.CUSTOMER_PAID as Customer_Paid, "
				+ "price.INTERNAL_NAME as " + "Pricesheet,statuss.NAME as Contract_Status, "
				+ "sales.COMMENTS as Comments " + "from [dbo].[ALLSALES_DETAILS] sales join [dbo].[ACCOUNT] "
				+ "account on account.id =  sales.PRIMARY_ACCOUNT_ID join "
				+ "[dbo].[UW_CONTRACT_STATUS] statuss on statuss.id = sales.CONTRACT_STATUS_ID "
				+ "left join [dbo].[PRICING_PRICESHEET] price on price.id = sales.PRICESHEET_ID "
				+ "join  ACCOUNT_STATUS accS on " + "accS.id =account.ACCOUNT_STATUS_ID  where sales.CERT = '"
				+ contract + "' order by sales.id desc;";
		return getTopRowDataFromDatabase(query);
	}

}
