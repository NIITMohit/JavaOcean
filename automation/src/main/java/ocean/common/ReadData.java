package ocean.common;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nList = doc.getElementsByTagName("keyword");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
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
					arrayExcelData[i - 1][j] = abc;
				}
			}
		} catch (Exception e) {
			//// Do nothing

		}
		return arrayExcelData;
	}

}
