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

import io.appium.java_client.windows.WindowsDriver;

/**
 * Keyword class which have basic keywords like click, type, etc
 * 
 * @author Mohit Goel
 */
public class Keywords extends Variables {
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
	 * dragAndDrop keyword , this function is used to perform dragAndDrop on any
	 * object
	 * 
	 * @param sourceLocator      : unique identifier to locate object
	 * @param destinationLocator : position of click element
	 * @throws Exception
	 */
	public void dragAndDrop(String sourceLocator, String destinationLocator) throws Exception {
		@SuppressWarnings("rawtypes")
		WindowsDriver wd = windowsDriver;
		for (int i = 0; i < 4; i++) {
			try {
				Actions action = new Actions(wd);
				WebDriverWait wait = new WebDriverWait(wd, mediumWait);
				WebElement sourceElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(sourceLocator)));
				WebElement destinationElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(destinationLocator)));
				//// perform drag and drop
				action.clickAndHold(sourceElement).moveToElement(destinationElement).release().build().perform();
				break;
			} catch (Exception e) {
				if (i < 3) {
					continue;
				} else
					throw e;
			}
		}
	}

	/**
	 * click keyword , this function is used to perform click on any object
	 * 
	 * @param locator  : unique identifier to locate object
	 * @param position : position of click element
	 */
	public void click(String locator, int position) {
		String[] object = Variables.oR.get(locator);
		String xpath = "(" + object[1] + ")[" + ++position + "]";
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement clickElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
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
	 * waitForSomeTime keyword , this function is used to indulge some delay in
	 * script
	 * 
	 * @param time : wait time in seconds
	 */
	public void waitForSomeTime(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//// do nothing
		}
	}

	/**
	 * waitForSomeTime keyword , this function is used to indulge some delay in
	 * script
	 * 
	 * @param time : wait time in seconds
	 */
	public void clickComboBox(String locator) {
		@SuppressWarnings("rawtypes")
		WindowsDriver windowsDriver1 = windowsDriver;
		Actions action = new Actions(windowsDriver1);
		for (int i = 0; i < 4; i++) {
			try {
				String attribute = getAttributeValue(locator, "BoundingRectangle");
				String[] coordinates = attribute.split(" ");
				String x = coordinates[2].substring(coordinates[2].indexOf(":") + 1, coordinates[2].length());
				String y = coordinates[3].substring(coordinates[3].indexOf(":") + 1, coordinates[3].length());
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver1, mediumWait);
				WebElement comboBox = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				action.moveToElement(comboBox, Integer.parseInt(x) - 10, Integer.parseInt(y) / 2).click().build()
						.perform();
				waitForSomeTime(5);
				//// Click the web element
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			} finally {
				action = null;
				windowsDriver1 = null;
			}
		}
	}

	/**
	 * waitForSomeTime keyword , this function is used to indulge some delay in
	 * script
	 * 
	 * @param time : wait time in seconds
	 */
	public void specialclickComboBox(String locator) {
		@SuppressWarnings("rawtypes")
		WindowsDriver windowsDriver1 = windowsDriver;
		Actions action = new Actions(windowsDriver1);
		for (int i = 0; i < 4; i++) {
			try {
				String attribute = getAttributeValue(locator, "BoundingRectangle");
				String[] coordinates = attribute.split(" ");
				String x = coordinates[2].substring(coordinates[2].indexOf(":") + 1, coordinates[2].length());
				String y = coordinates[3].substring(coordinates[3].indexOf(":") + 1, coordinates[3].length());
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver1, mediumWait);
				WebElement comboBox = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				action.moveToElement(comboBox, Integer.parseInt(x) - 10, Integer.parseInt(y) / 2).doubleClick().build()
						.perform();
				waitForSomeTime(5);
				//// Click the web element
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			} finally {
				action = null;
				windowsDriver1 = null;
			}
		}
	}

	/**
	 * rightClick keyword , this function is used to perform right click on any
	 * object
	 * 
	 * @param unique identifier to locate object
	 */
	public void rightClick(String locator) {
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				Actions actions = new Actions(windowsDriver);
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement clickElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Click the web element
				actions.contextClick(clickElement).build().perform();
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

	/**
	 * typeKeys keyword , this function is used to keyboard keys events and press
	 * enter
	 * 
	 * @param locator : unique identifier to locate object
	 * @value value : to be entered in locator
	 */
	public void doubleClick(String locator) {
		for (int i = 0; i < 4; i++) {
			try {
				Actions action = new Actions(windowsDriver);
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// doubleClick the web element
				action.doubleClick(typeElement).perform();
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
	 * typeKeys keyword , this function is used to keyboard keys events and press
	 * enter
	 * 
	 * @param locator : unique identifier to locate object
	 * @value value : to be entered in locator
	 */
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
				waitForSomeTime(1);
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, shortWait);
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				} catch (Exception e) {
					wait.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.fetchOR(locator)));
				}
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
	public Set<String> specialGetAllValuesSaveInSet(String locator) {
		HashSet<String> abc = new HashSet<String>();
		for (int i = 0; i < 4; i++) {
			try {
				waitForSomeTime(5);
				//// Wait till web element is located
				@SuppressWarnings("unchecked")
				//// Find list of web elements
				List<WebElement> listWebElement = windowsDriver.findElements(ObjectRepo.fetchOR(locator));
				if (listWebElement.size() < 1) {
					if (i < 3)
						continue;
					else
						break;
				}
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
	 * getValue keyword , this function is used to get text/value of locator
	 *
	 * @param unique identifier to locate object
	 * @return the text/value of locator
	 */
	public Set<String> getAllValuesSaveInSet(String locator) {
		HashSet<String> abc = new HashSet<String>();
		for (int i = 0; i < 4; i++) {
			try {
				waitForSomeTime(5);
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
	public String getAttributeValue(String locator, String attribute) {
		String value = "not able identify";
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				WebElement typeElement = wait
						.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.fetchOR(locator)));
				//// Check Enable of button
				value = typeElement.getAttribute(attribute);
				break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
		return value;
	}

	/**
	 * listOfElements keyword , this function is used to get list of all elements
	 * based on unique identifier
	 *
	 * @param locator : unique identifier to locate object
	 * @return List of web elements
	 */
	@SuppressWarnings("unchecked")
	public List<WebElement> listOfElements(String locator) {
		List<WebElement> we = null;
		for (int i = 0; i < 4; i++) {
			try {
				//// Wait till web element is located
				WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
				wait.until(ExpectedConditions.presenceOfElementLocated(ObjectRepo.fetchOR(locator)));
				we = windowsDriver.findElements(ObjectRepo.fetchOR(locator));
				if (we.size() < 1) {
					if (i < 3)
						continue;
					else
						break;
				} else
					break;
			} catch (Exception e) {
				if (i < 3)
					continue;
				else
					throw e;
			}
		}
		return we;
	}

	/**
	 * getTextOfElement keyword , this function is used to get text/value of locator
	 *
	 * @param unique identifier to locate object
	 * @return the text/value of locator
	 */
	public String getTextOfElement(String locator) {
		String abc = "";
		try {
			// // Wait till web element is located
			WebDriverWait wait = new WebDriverWait(windowsDriver, mediumWait);
			wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
			abc = windowsDriver.findElement(ObjectRepo.fetchOR(locator)).getText();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return abc;
	}
}
