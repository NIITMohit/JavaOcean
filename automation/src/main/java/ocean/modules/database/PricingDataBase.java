package ocean.modules.database;

import java.util.Calendar;
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
	public HashMap<String, String> getClassificationInfomation(String CLASS_GROUP_NAME, String turbo, String diesel)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select top 1 CLASS_GROUP_NAME,turbo,diesel from" + "  CLASS_GROUP where Turbo= " + turbo
				+ " and DIESEL =" + diesel + " and CLASS_GROUP_NAME like '%" + CLASS_GROUP_NAME + "%'; ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/**
	 * This function is used to get the Form Type value
	 * 
	 */
	public HashMap<String, String> fetchFormTypeValueDB(String code) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = " SELECT PPPV.[PRICESHEET_ID],PPPV.[PRICING_PROPERTY_VALUE_ID], PRP.[NAME],"
				+ " ISNULL(CONVERT(NVARCHAR,PPV.[STRING_VALUE]),(ISNULL(CONVERT(NVARCHAR,PPV.[NUMERIC_VALUE]), "
				+ " (ISNULL(CONVERT(NVARCHAR,PPV.[DATE_VALUE]),(ISNULL(CONVERT(NVARCHAR,PPV.[DATETIME_VALUE]),"
				+ " (ISNULL(PPV.[RANGE_FROM_VALUE],(ISNULL(PPV.[RANGE_TO_VALUE], PPV.[BOOLEAN_VALUE])))))))))))  AS 'VALUE' "
				+ " FROM DBO.[PRICING_PRICESHEET_PROPERTY_VALUE] AS  PPPV  WITH (NOLOCK) "
				+ " JOIN DBO.[PRICING_PROPERTY_VALUE]  AS PPV WITH (NOLOCK)  "
				+ "  ON PPV.ID = PPPV.PRICING_PROPERTY_VALUE_ID "
				+ " JOIN PRD_OCEAN.DBO.[PRICING_PROPERTY] AS PRP WITH (NOLOCK) "
				+ " ON PRP.ID = PPV.PRICING_PROPERTY_ID "
				+ " where PPPV.[PRICESHEET_ID] in (select ID from [dbo].[PRICING_PRICESHEET] where  [PRICESHEET_TYPE_ID] = 1 and code ='"
				+ code + "')" + " and  PRP.[NAME] like'%Form Type%' ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

	/*
	 * to get the ActiveContractValue based on webcontract/contract.
	 */
	public HashMap<String, String> fetchActiveContractValueDB(String progCode, String contractType) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = 0;

		String effectiveDate = String.valueOf(currYear + 1);
		preYear = currYear - 1;

		if (contractType.equalsIgnoreCase("WebContract")) {
			String query = " SELECT top 1 SD.CERT ,SD.[PURCHASE_DATE] ,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[WEB_CONTRACTS] SD join [dbo].[ACCOUNT] A  "
					+ " On A.[ID] = SD.[PRIMARY_SELLER_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
					+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
					+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
					+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
					+ " and   SD.[STATUS] = 'Processed'  and  SD.PROGRAM_CODE='" + progCode + "' "
					+ " and SD.PRICESHEET_ID is not null "
					+ " and SD.[PURCHASE_PRICE] is not null and SD.[PURCHASE_PRICE] <>0.00   and SD.[PURCHASE_DATE] "
					+ " between '" + preYear + "' and '" + effectiveDate + "' " + " order by SD.[PURCHASE_DATE] desc ;";
			dbMap = getTopRowDataFromDatabase(query);
		} else {
			String query = " SELECT top 1 SD.CERT ,SD.SALE_DATE,SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME from [dbo].[ALLSALES_DETAILS] SD join [dbo].[ACCOUNT] A  "
					+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
					+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
					+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
					+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
					+ " and   SD.CONTRACT_STATUS_ID =5  and  SD.PROGRAM_CODE='" + progCode + "' "
					+ " and SD.PRICESHEET_ID is not null "
					+ " and  SD.Customer_Paid is not null and SD.Customer_Paid <>0.00  and SD.SALE_DATE " + " between '"
					+ preYear + "' and '" + effectiveDate + "' and YEAR(SD.EXPIRATION_DATE) >= '" + currYear + "' "
					+ " order by  SD.SALE_DATE desc ;";
			dbMap = getTopRowDataFromDatabase(query);

		}

		String query1 = " SELECT  PRP.[NAME],"
				+ " ISNULL(CONVERT(NVARCHAR,PPV.[STRING_VALUE]),(ISNULL(CONVERT(NVARCHAR,PPV.[NUMERIC_VALUE]), "
				+ " (ISNULL(CONVERT(NVARCHAR,PPV.[DATE_VALUE]),(ISNULL(CONVERT(NVARCHAR,PPV.[DATETIME_VALUE]),"
				+ " (ISNULL(PPV.[RANGE_FROM_VALUE],(ISNULL(PPV.[RANGE_TO_VALUE], PPV.[BOOLEAN_VALUE])))))))))))  AS 'VALUE' "
				+ " FROM DBO.[PRICING_PRICESHEET_PROPERTY_VALUE] AS  PPPV  WITH (NOLOCK) "
				+ " JOIN DBO.[PRICING_PROPERTY_VALUE]  AS PPV WITH (NOLOCK)  "
				+ "  ON PPV.ID = PPPV.PRICING_PROPERTY_VALUE_ID "
				+ " JOIN PRD_OCEAN.DBO.[PRICING_PROPERTY] AS PRP WITH (NOLOCK) "
				+ " ON PRP.ID = PPV.PRICING_PROPERTY_ID "
				+ " where PPPV.[PRICESHEET_ID] in (select ID from [dbo].[PRICING_PRICESHEET] where  [PRICESHEET_TYPE_ID] = 1 and code ='"
				+ progCode + "')" + " and  PRP.[NAME] like'%Form Type%' ";

		HashMap<String, String> dbMap1 = getTopRowDataFromDatabase(query1);

		myData.putAll(dbMap);
		myData.putAll(dbMap1);

		return myData;
	}

	/*
	 * to get the version number of PriceSheet group
	 */
	public String getPriceSheetGroupVersion(String groupName, String state) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String versionNumber = "";
		String query = "select top 1 document_version as VersionNumber from COMPLIANCE_DOCUMENT c join COMPLIANCE_DOCUMENT_GROUP a "
				+ " on a.compliance_document_id = c.id join [dbo].[STATE] s on s.id = c.document_state_id join "
				+ " COMPLIANCE_GROUP b on b.id = a.compliance_group_id where b.name ='" + groupName + "' "
				+ " and Display_Name = '" + state + "' order by c.id desc;";
		System.out.println("query===" + query);
		dbMap = getTopRowDataFromDatabase(query);
		versionNumber = dbMap.get("VersionNumber");
		System.out.println("version==" + versionNumber);
		return versionNumber;
	}

	/*
	 * This used to get Seller State Dealer Detail
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> dBGetSellerStateDealerDetail() throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = 0;

		String effectiveDate = String.valueOf(currYear + 1);
		preYear = currYear - 1;

		String query = "SELECT top 1 SD.CERT ,SD.[SALE_DATE],SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME ,a.ROLE_IDENTIFIER SD.STATE as Cust_State ,addr.STATE as Dealer_State"
				+ " from [dbo].[ALLSALES_DETAILS]  SD join [dbo].[ACCOUNT] A "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
				+ " join [dbo].[ACCOUNT_ADDRESS] addr on addr.[ACCOUNT_ID]  = a.id "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.[CONTRACT_STATUS_ID]=5 and  SD.PROGRAM_CODE IN (SELECT CODE from [dbo].[PRICING_PRICESHEET]"
				+ " where [ID] in (SELECT PPPV.[PRICESHEET_ID] FROM DBO.[PRICING_PRICESHEET_PROPERTY_VALUE] AS  PPPV  WITH (NOLOCK) --APG "
				+ " JOIN DBO.[PRICING_PROPERTY_VALUE]  AS PPV WITH (NOLOCK) --APV [ACCOUNT_PROPERTY_VALUE] "
				+ " ON PPV.ID = PPPV.PRICING_PROPERTY_VALUE_ID JOIN PRD_OCEAN.DBO.[PRICING_PROPERTY] AS PRP WITH (NOLOCK)  --AP PRP "
				+ "  ON PRP.ID = PPV.PRICING_PROPERTY_ID where PPPV.[PRICESHEET_ID] in (select ID from [dbo].[PRICING_PRICESHEET] "
				+ " where  [PRICESHEET_TYPE_ID] = 1 ) and  PRP.[NAME] like'%Form Type%' and PPPV.[PRICING_PROPERTY_VALUE_ID] = 1281) and Active =1 ) "
				+ " and SD.PRICESHEET_ID is not null and  SD.[CUSTOMER_PAID] is not null and SD.[CUSTOMER_PAID] <>0.00  and SD.SALE_DATE "
				+ " between ' " + preYear + "' and '" + effectiveDate
				+ "' and SD.STATE <> addr.STATE  order by  SD.SALE_DATE desc ;";

		dbMap = getTopRowDataFromDatabase(query);

		return dbMap;
	}

	/*
	 * This used to get customer State DealerDetail
	 */
	@SuppressWarnings("unused")
	public HashMap<String, String> dBGetCustomerStateDealerDetail() throws Exception {

		HashMap<String, String> dbMap = new HashMap<String, String>();
		HashMap<String, String> myData = new HashMap<String, String>();
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int preYear = 0;

		String effectiveDate = String.valueOf(currYear + 1);
		preYear = currYear - 1;

		String query = "SELECT top 1 SD.CERT ,SD.[SALE_DATE],SD.PRICESHEET_ID,SD.PROGRAM_CODE,A.NAME ,a.ROLE_IDENTIFIER SD.STATE ,addr.STATE "
				+ " from [dbo].[ALLSALES_DETAILS]  SD join [dbo].[ACCOUNT] A "
				+ " On A.[ID] = SD.[PRIMARY_ACCOUNT_ID] INNER JOIN  dbo.ACCOUNT_ROLE_TYPE AS R ON R.ID = A.ROLE_TYPE_ID "
				+ " INNER JOIN  dbo.ACCOUNT_TYPE AS T ON T.ID = A.ACCOUNT_TYPE_ID "
				+ " Left join ACCOUNT_CANCELLATION ac on ac.Primary_account_id  = A.id "
				+ " join [dbo].[ACCOUNT_ADDRESS] addr on addr.[ACCOUNT_ID]  = a.id "
				+ " INNER JOIN PRICING_PRICESHEET_ACCOUNT_RELATION ON A.ID = PRICING_PRICESHEET_ACCOUNT_RELATION.PRIMARY_SELLER_ID "
				+ " and   SD.[CONTRACT_STATUS_ID]=5 and  SD.PROGRAM_CODE IN (SELECT CODE from [dbo].[PRICING_PRICESHEET]"
				+ " where [ID] in (SELECT PPPV.[PRICESHEET_ID] FROM DBO.[PRICING_PRICESHEET_PROPERTY_VALUE] AS  PPPV  WITH (NOLOCK) --APG "
				+ " JOIN DBO.[PRICING_PROPERTY_VALUE]  AS PPV WITH (NOLOCK) --APV [ACCOUNT_PROPERTY_VALUE] "
				+ " ON PPV.ID = PPPV.PRICING_PROPERTY_VALUE_ID JOIN PRD_OCEAN.DBO.[PRICING_PROPERTY] AS PRP WITH (NOLOCK)  --AP PRP "
				+ "  ON PRP.ID = PPV.PRICING_PROPERTY_ID where PPPV.[PRICESHEET_ID] in (select ID from [dbo].[PRICING_PRICESHEET] "
				+ " where  [PRICESHEET_TYPE_ID] = 1 ) and  PRP.[NAME] like'%Form Type%' and PPPV.[PRICING_PROPERTY_VALUE_ID] = 1282) and Active =1 ) "
				+ " and SD.PRICESHEET_ID is not null and  SD.[CUSTOMER_PAID] is not null and SD.[CUSTOMER_PAID] <>0.00  and SD.SALE_DATE "
				+ " between ' " + preYear + "' and '" + effectiveDate
				+ "' and SD.STATE <> addr.STATE  order by  SD.SALE_DATE desc ;";

		dbMap = getTopRowDataFromDatabase(query);

		return dbMap;
	}

	/*
	 * to get the pricesheet group name based on cert and primary account name for
	 * Compliance Module.
	 */
	public String compliance_PricesheetIdbasedOnCert(String cert) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String code = "";
		String query = "select group_name from COMPLIANCE_GROUP_PRICESHEET cgp where cgp.PRICING_PRICESHEET_ID in"
				+ "(select id from dbo.pricing_pricesheet where id in(select parent_pricesheet_id from dbo.pricing_pricesheet"
				+ " where id in(select parent_pricesheet_id from dbo.pricing_pricesheet where id in (select pricesheet_id "
				+ "from AllSales_Details  where cert = '" + cert + "'))));";
		System.out.println("pricesheetquery====" + query);
		dbMap = getTopRowDataFromDatabase(query);
		code = dbMap.get("group_name");
		System.out.println("dbMap====" + dbMap);
		return code;
	}

	/*
	 * to get the State display name based on cert and primary account name.
	 */
	public String compliance_StateDisplayNamebasedOnCertAndPAccount(String cert, String pAccount) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String state = "";
		String query = "select display_name from dbo.state where name in (select state from allsales_details asd "
				+ " join [dbo].[ACCOUNT] account on account.id =  asd.PRIMARY_ACCOUNT_ID where cert = '" + cert + "'"
				+ " and name='" + pAccount + "') ;";
		System.out.println("statequery====" + query);
		dbMap = getTopRowDataFromDatabase(query);
		state = dbMap.get("display_name");
		System.out.println("dbMap====" + dbMap);
		return state;
	}

	/*
	 * to get the State display name based on cert and primary account name.
	 */
	public String compliance_CustomerStateDisplayNamebasedOnCertAndPAccount(String cert) throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String state = "";
		String query = "select display_name from dbo.state where name in (select state from allsales_details asd "
				+ " where cert = '" + cert + "'  ;";
		System.out.println("statequery====" + query);
		dbMap = getTopRowDataFromDatabase(query);
		state = dbMap.get("display_name");
		System.out.println("dbMap====" + dbMap);
		return state;
	}

	/**
	 * This function is used to get the Diesel value before editing and after
	 * editing
	 * 
	 */
	public HashMap<String, String> getClassficationListForDieselBeforeAndAfterEditing(String pricesheet, int value)
			throws Exception {
		HashMap<String, String> dbMap = new HashMap<String, String>();
		String query = "Select * from  CLASS_GROUP " + " where CLASS_GROUP_NAME like '%" + pricesheet
				+ "%' and Diesel='" + value + "' ";
		dbMap = getTopRowDataFromDatabase(query);
		return dbMap;
	}

}
