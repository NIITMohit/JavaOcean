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
	 * Data provider for search contract used in cancellation module
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
	 * Data provider for premium calculation used in underwriting module
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
	 * Data provider for business rules used in cancellation module
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
	 * Data provider for override rules used in cancellation module
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
	 * Data provider for cancel contract used in cancellation module
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
	 * Data provider for fetchContractDetailsForPremiumCalculation used in pricing
	 * module
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
