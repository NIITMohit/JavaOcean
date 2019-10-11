package ocean.common;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Keyword class which have basic keywords like click, type, etc
 * 
 * @author Mohit Goel
 */
public class Keywords extends Variables {

	/**
	 * test object keyword , this function is reserved to debug objects
	 */
	public void testObject(String locator) {
		for (int i = 0; i < 4; i++) {
			try {
				@SuppressWarnings("unused")
				WebDriverWait wait = new WebDriverWait(windowsDriver, 20);
				@SuppressWarnings("unchecked")
				List<WebElement> eee = windowsDriver.findElements(ObjectRepo.fetchOR(locator));
				for (WebElement webElement : eee) {
					// Thread.sleep(3000);
					String abc = webElement.getText();
					System.out.println(abc);
					try {
						webElement.sendKeys("2121");
					} catch (Exception e) {
						//// do nothing
					}

				}
			} catch (Exception e) {
				System.out.println("fsddf");
				if (i < 3)
					continue;
				else
					throw e;
			}
		}

	}

	/**
	 * click keyword , this function is used to perform click on any object
	 * 
	 * @param unique identifier to locate object
	 */
	public void click(String locator) {
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, extraLongWait);
				WebElement clickElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Click the web element
				clickElement.click();
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			}
		}

	}

	/**
	 * type keyword , this function is used to perform type on any object
	 * 
	 * @param unique identifier to locate object
	 * @value value to be entered in locator
	 */
	public void type(String locator, String value) {
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, extraLongWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Click the web element
				typeElement.click();
				//// Clear the web element in case any this is already types
				typeElement.clear();
				//// type value in web element
				typeElement.sendKeys(value);
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
	}

	/**
	 * getValue keyword , this function is used to get text/value of locator
	 *
	 * @param unique identifier to locate object
	 * @return the text/value of locator
	 */
	public String getValue(String locator) {
		String abc = "";
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, extraLongWait);
				wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				@SuppressWarnings("unchecked")
				//// Find list of web elements
				List<WebElement> listWebElement = windowsDriver.findElements(ObjectRepo.fetchOR(locator));
				for (WebElement webElement : listWebElement) {
					//// get value and return the same
					abc = webElement.getText();
					break;
				}
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
		return abc;
	}

	/**
	 * checkEnableDisable keyword , this function is used to get state of object,
	 * and identify weather it is enable or disable
	 *
	 * @param unique identifier to locate object
	 * @return true or false based on locator state
	 */
	public String checkEnableDisable(String locator) {
		String flag = "not able identify";
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, extraLongWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));

				//// Check Enable of button
				Boolean flaf = typeElement.isEnabled();
				flag = Boolean.toString(flaf);
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
		return flag;
	}
}
