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

}
