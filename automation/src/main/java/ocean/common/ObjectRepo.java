package ocean.common;

import org.openqa.selenium.By;

/**
 * Object Repo class, to return By element bases of locator id.
 *
 * @author Mohit Goel
 */
public class ObjectRepo {
	/**
	 * Function to return respective locator type bases in input parameter id
	 *
	 * @param id the id
	 * @return the by
	 */
	public static By fetchOR(String id) {

		String[] object = Variables.oR.get(id);

		switch (object[0].toLowerCase()) {
		case "id":
			return By.id(object[1]);

		case "name":
			return By.name(object[1]);

		case "classname":
			return By.className(object[1]);

		case "tagname":
			return By.tagName(object[1]);

		case "cssselector":
			return By.cssSelector(object[1]);

		case "linktext":
			return By.linkText(object[1]);

		case "partiallinktext":
			return By.partialLinkText(object[1]);

		case "xpath":
			return By.xpath(object[1]);

		default:
			return By.xpath(object[1]);

		}

	}

}
