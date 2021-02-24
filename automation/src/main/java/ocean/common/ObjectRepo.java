package ocean.common;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

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
		String locators = object[0].toLowerCase();
		locators = locators.replaceAll("[^a-zA-Z0-9]", "");
		String value = object[1].trim();
		value = value.replaceAll("\n", " ");
		value = value.replaceAll("\t", "");
		value = value.replaceAll("\r", "");
		// value = value.replace("", newChar)
		switch (locators) {
		case "id":
			return By.id(value);

		case "resourceid":
			return By.id(value);

		case "name":
			return By.name(value);

		case "classname":
			return By.className(value);

		case "tagname":
			return By.tagName(value);

		case "cssselector":
			return By.cssSelector(value);

		case "linktext":
			return By.linkText(value);

		case "partiallinktext":
			return By.partialLinkText(value);

		case "xpath":
			return By.xpath(value);

		case "automationid":
			return (MobileBy) MobileBy.AccessibilityId(value);

		case "accessibilityid":
			return (MobileBy) MobileBy.AccessibilityId(value);

		default:
			return By.xpath(value);
		}

	}

}
