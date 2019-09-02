package ocean.common;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadData extends Keywords {

	public void readXML(String filePath) {
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
			System.out.print("xml read exception, message :" + e.toString());
		}
	}

}
