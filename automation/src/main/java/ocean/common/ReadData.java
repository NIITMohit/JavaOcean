package ocean.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ReadData, to read xml/excel file, acts as OR reader.
 *
 * @author Mohit Goel
 */
public class ReadData extends Keywords {

	/**
	 * Read XML, and save whole xml file in static variable Variables.oR
	 *
	 * @param filePath the path of xml file
	 * @throws Exception
	 */
	public void readXML(String filePath) throws Exception {
		try {
			//// read or
			File ORXmlFile = new File(filePath);
			//// parse xml
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(ORXmlFile);
			NodeList nList = doc.getElementsByTagName("keyword");
			//// parse xml file to extract, and save in hash map based on keyword node
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					/*
					 * String ids = eElement.getAttribute("id"); if (Variables.oR.get(ids) != null)
					 * { System.out.println(ids); }
					 */
					//// get data from xml and appened in hashmap
					Variables.oR.put(eElement.getAttribute("id"),
							new String[] { eElement.getElementsByTagName("locator").item(0).getTextContent().trim(),
									eElement.getElementsByTagName("value").item(0).getTextContent().trim() });
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Read excel, used as test data file
	 *
	 * @param filePath  the path of excel file
	 * @param sheetName sheet which need to get data
	 * @throws Exception
	 */
	@SuppressWarnings({ "resource" })
	public static Object[][] getExcelData(String fileName, String sheetName) {
		//// read data from data provider excel and appened in string array
		Object[][] excelData = null;
		try {
			//// read file
			FileInputStream fs = new FileInputStream(fileName);
			//// get workbook based on sheeta
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			//// get excel sheet
			Sheet sh = wb.getSheet(sheetName);
			//// no of rows
			int totalNoOfRows = sh.getLastRowNum() - sh.getFirstRowNum();
			excelData = new Object[totalNoOfRows][1];
			//// no of columns
			int totalNoOfColumn = sh.getRow(0).getLastCellNum();
			//// iterate through rows and columns
			for (int i = 1; i <= totalNoOfRows; i++) {
				String[] data = new String[totalNoOfColumn];
				Row row = sh.getRow(i);
				for (int j = 0; j < totalNoOfColumn; j++) {
					String abc = "";
					try {
						CellType cellType = row.getCell(j).getCellType();
						//// Switch case to convert excel data to excel
						switch (cellType.toString().toLowerCase()) {
						case "string":
							abc = row.getCell(j).getStringCellValue();
							break;
						case "blank":
							abc = row.getCell(j).getStringCellValue();
							break;
						case "numeric":
							abc = Double.toString(row.getCell(j).getNumericCellValue());
							break;
						default:
							abc = row.getCell(j).getStringCellValue();
						}
					} catch (Exception e) {
						abc = "";
					}
					//// appened data in string array
					data[j] = abc;
				}
				excelData[i - 1][0] = data;
			}
		} catch (Exception e) {
			//// do nothing

		}
		return excelData;
	}

	public static Object[][] getRoughData(String[][] myData) {
		//// read data from data provider excel and appened in string array
		Object[][] excelData = null;
		try {
			int totalNoOfRows = myData.length;
			excelData = new Object[totalNoOfRows][1];
			//// no of columns
			//// iterate through rows and columns
			for (int i = 1; i <= totalNoOfRows; i++) {
				int totalNoOfColumn = myData[i - 1].length;
				String[] data = new String[totalNoOfColumn];
				for (int j = 0; j < totalNoOfColumn; j++) {
					String abc = myData[i - 1][j];
					//// appened data in string array
					data[j] = abc;
				}
				excelData[i - 1][0] = data;
			}
		} catch (Exception e) {
			//// do nothing

		}
		return excelData;
	}

	public static StringBuffer getHTMLData() throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			String currentDir = System.getProperty("user.dir");
			//// pat of test ng test data
			String dir = currentDir + "\\Repository\\";
			File myObj = new File(dir + "MasterReport.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				sb.append(myReader.nextLine());
			}
			myReader.close();
		} catch (Exception e) {
			throw e;
		}
		return sb;
	}

	public static StringBuffer readReport() throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			File myObj = new File(reportPath + "\\" + "Report" + ".html");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				sb.append(myReader.nextLine());
			}
			myReader.close();
		} catch (Exception e) {
			throw e;
		}
		return sb;
	}

	public static StringBuffer readReportPath(String path) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				sb.append(myReader.nextLine());
			}
			myReader.close();
		} catch (Exception e) {
			throw e;
		}
		return sb;
	}

	public int getDownloadedExcelRow(String fileName, String sheetName) throws IOException {
		//// read data from data provider excel and appened in string array read file
		FileInputStream fs = new FileInputStream(fileName);
		//// get workbook based on sheeta
		@SuppressWarnings("resource")
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		//// get excel sheet
		Sheet sh = wb.getSheet(sheetName);
		//// no of rows
		int totalNoOfRows = sh.getLastRowNum() - sh.getFirstRowNum();
		int NoOfRows = totalNoOfRows - 2;
		return NoOfRows;
	}	
}
