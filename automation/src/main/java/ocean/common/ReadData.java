package ocean.common;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Cell;
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
					//// get data from xml and appened in hashmap
					Variables.oR.put(eElement.getAttribute("id"),
							new String[] { eElement.getElementsByTagName("locator").item(0).getTextContent(),
									eElement.getElementsByTagName("value").item(0).getTextContent() });
				}
			}
		}

		catch (Exception e) {
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
	public static String[][] getExcelData(String fileName, String sheetName) {
		//// read data from data provider excel and appened in string array
		String[][] excelData = null;
		try {
			//// read file
			FileInputStream fs = new FileInputStream(fileName);
			//// get workbook based on sheeta
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			//// get excel sheet
			Sheet sh = wb.getSheet(sheetName);
			//// no of rows
			int totalNoOfRows = sh.getLastRowNum() - sh.getFirstRowNum();
			//// no of columns
			int totalNoOfColumn = sh.getRow(0).getLastCellNum();
			//// iterate through rows and columns
			excelData = new String[totalNoOfRows][totalNoOfColumn];
			for (int i = 1; i <= totalNoOfRows; i++) {
				Row row = sh.getRow(i);
				for (int j = 0; j < totalNoOfColumn; j++) {
					System.out.println(i + "," + j);
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
					excelData[i - 1][j] = abc.trim();
				}

			}
		} catch (Exception e) {
			//// do nothing

		}
		return excelData;
	}

}
