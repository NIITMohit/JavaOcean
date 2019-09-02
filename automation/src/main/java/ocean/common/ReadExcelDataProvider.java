package ocean.common;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelDataProvider {

	@SuppressWarnings({ "resource" })
	public static String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfRows = sh.getLastRowNum() - sh.getFirstRowNum();
			int totalNoOfColumn = sh.getRow(0).getLastCellNum();
			arrayExcelData = new String[totalNoOfRows][totalNoOfColumn];
			for (int i = 1; i <= totalNoOfRows; i++) {
				Row row = sh.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					String abc = row.getCell(j).getStringCellValue();
					arrayExcelData[i-1][j] = abc;
					System.out.println(abc);
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();

		}
		return arrayExcelData;
	}
}