package ocean.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
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

	public void waitForSomeTime(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//// do nothing
		}

	}

	public void rightClick(String locator) {
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				Actions actions = new Actions(windowsDriver);
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement clickElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Click the web element
				actions.contextClick(clickElement).perform();
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
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
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

	public void typeKeys(String locator, String value) {
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Click the web element
				typeElement.click();
				//// Clear the web element in case any this is already types
				typeElement.clear();
				//// type value in web element
				typeElement.sendKeys(value);
				typeElement.sendKeys(Keys.ENTER);
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
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
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
	 * getValue keyword , this function is used to get text/value of locator
	 *
	 * @param unique identifier to locate object
	 * @return the text/value of locator
	 */
	public String getValue(String locator, int position) {
		String abc = "";
		String[] object = Variables.oR.get(locator);
		String xpath = "(" + object[1] + ")[" + ++position + "]";
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				@SuppressWarnings("unchecked")
				//// Find list of web elements
				List<WebElement> listWebElement = windowsDriver.findElements(By.xpath(xpath));
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
	 * getValue keyword , this function is used to get text/value of locator
	 *
	 * @param unique identifier to locate object
	 * @return the text/value of locator
	 */
	public Set<String> getAllValuesSaveInSet(String locator) {
		HashSet<String> abc = new HashSet<String>();
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				@SuppressWarnings("unchecked")
				//// Find list of web elements
				List<WebElement> listWebElement = windowsDriver.findElements(ObjectRepo.fetchOR(locator));
				for (WebElement webElement : listWebElement) {
					//// get value and return the same
					abc.add(webElement.getAttribute("Name"));
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
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.fetchOR(locator)));
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

	/**
	 * checkEnableDisable keyword , this function is used to get state of object,
	 * and identify weather it is enable or disable
	 *
	 * @param unique identifier to locate object
	 * @return true or false based on locator state
	 */
	public String checkEnableDisableBasedOnBoundingRectangle(String locator) {
		String flag = "not able identify";
		boolean flaf = true;
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Check Enable of button
				String boundingRectangle = typeElement.getAttribute("BoundingRectangle");
				if (boundingRectangle.equals("Left:0 Top:0 Width:0 Height:0")) {
					flaf = false;
				}
				flag = Boolean.toString(flaf);
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
			}
		}
		return flag;
	}
}
