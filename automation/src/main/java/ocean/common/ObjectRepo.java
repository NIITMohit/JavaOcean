package ocean.common;

import org.openqa.selenium.By;

public class ObjectRepo {

	public static By fetchOR(String id) {

		String[] abc = Variables.oR.get(id);

		switch (abc[0].toLowerCase()) {
		case "id":
			return By.id(abc[1]);

		case "name":
			return By.name(abc[1]);

		case "classname":
			return By.className(abc[1]);

		case "tagname":
			return By.tagName(abc[1]);

		case "cssselector":
			return By.cssSelector(abc[1]);

		case "linktext":
			return By.linkText(abc[1]);

		case "partiallinktext":
			return By.partialLinkText(abc[1]);

		case "xpath":
			return By.xpath(abc[1]);

		default:
			return By.xpath(abc[1]);

		}

	}

}
