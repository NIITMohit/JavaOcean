package ocean.common;

import org.testng.annotations.DataProvider;

/**
 * Data provider class, common class consisting all data provider consumed in
 * test classes
 * 
 * @author Mohit Goel
 */
public class DataProviderClass {

	/**
	 * Data provider
	 */
	@DataProvider(name = "SearchContract")
	public static Object[][] SearchContractDataprovider() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Contract");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "PremiumReCalculate")
	public static Object[][] PremiumReCalculate() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "UnderWriting.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Premium");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "SearchContractonBR")
	public static Object[][] SearchContractonBRDatProvider() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Error");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "SearchContractonOverRide")
	public static Object[][] SearchContractonOverRideDatProvider() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "OverRide");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "cancelContractCalculateVerify")
	public static Object[][] cancelContractCalculateVerify() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Cancellation.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Calculate");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "fetchContractDetailsForPremiumCalculation")
	public static Object[][] fetchContractDetailsForPremiumCalculation() {
		//// current runnig directory
		String currentDir = System.getProperty("user.dir");
		//// pat of test ng test data
		String dir = currentDir + "\\Repository\\";
		//// Excel sheet file name
		String fileName = "Pricing.xlsx";
		//// Excel sheet, sheet name
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "PremiumCalculation");
		return arrayObject;
	}

}
