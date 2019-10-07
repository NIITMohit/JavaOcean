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
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Contract");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "PremiumReCalculate")
	public static Object[][] PremiumReCalculate() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "UnderWriting.xlsx";
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Premium");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "SearchContractonBR")
	public static Object[][] SearchContractonBRDatProvider() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Error");
		return arrayObject;
	}

	/**
	 * Data provider
	 */
	@DataProvider(name = "SearchContractonOverRide")
	public static Object[][] SearchContractonOverRideDatProvider() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "OverRide");
		return arrayObject;
	}
	
	/**
	 * Data provider
	 */
	@DataProvider(name = "cancelContractCalculateVerify")
	public static Object[][] cancelContractCalculateVerify() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadData.getExcelData(dir + fileName, "Calculate");
		return arrayObject;
	}


}
