package ocean.common;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

	@DataProvider(name = "SearchContract")
	public static Object[][] SearchContractDataprovider() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadExcelDataProvider.getExcelData(dir + fileName, "Contract");
		return arrayObject;
	}

	
	@DataProvider(name = "SearchContractonBR")
	public static Object[][] SearchContractonBRDatProvider() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadExcelDataProvider.getExcelData(dir + fileName, "Error");
		return arrayObject;
	}
	
	@DataProvider(name = "SearchContractonOverRide")
	public static Object[][] SearchContractonOverRideDatProvider() {
		String currentDir = System.getProperty("user.dir");
		String dir = currentDir + "\\Repository\\";
		String fileName = "Cancellation.xlsx";
		Object[][] arrayObject = ReadExcelDataProvider.getExcelData(dir + fileName, "OverRide");
		return arrayObject;
	}

}
