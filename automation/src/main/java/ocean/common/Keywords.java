package ocean.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords extends Variables {

	public void testObject(String locator) {
		for (int i = 0; i < 3; i++) {
			try {
				@SuppressWarnings("unused")
				WebDriverWait wait = new WebDriverWait(windriver, 20);
				@SuppressWarnings("unchecked")
				List<WebElement> eee = windriver.findElements(ObjectRepo.fetchOR(locator));
				for (WebElement webElement : eee) {
					// Thread.sleep(3000);
					System.out.println("fsdf");
					String abc = webElement.getText();
					System.out.println(abc);

				}
			} catch (Exception e) {
				System.out.println("fsddf");
				continue;
			}
		}

	}

	public void click(String locator) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		for (int i = 0; i < 3; i++) {
			try {
				WebDriverWait wait = new WebDriverWait(windriver, 20);
				WebElement clickElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				clickElement.click();
				System.out.println("Click Successful for " + locator);
				break;

			} catch (Exception e) {
				try {
					Thread.sleep(Variables.shortWait * 1000);
					System.out.println("Click failed for " + locator);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}

		}
		Date date1 = new Date();
		System.out.println(dateFormat.format(date1));

	}

	public void type(String locator, String value) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		for (int i = 0; i < 3; i++) {
			try {
				WebDriverWait wait = new WebDriverWait(windriver, 20);
				WebElement typeElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				typeElement.click();
				typeElement.clear();
				typeElement.sendKeys(value);
				System.out.println("send keys Successful for " + locator);
				break;

			} catch (Exception e) {
				try {
					Thread.sleep(Variables.shortWait * 1000);
					System.out.println("send keys Failed for " + locator);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				continue;
			}
		}
		Date date1 = new Date();
		System.out.println(dateFormat.format(date1));
	}

	public String getValue(String locator) {
		String abc = "";
		for (int i = 0; i < 5; i++) {
			try {
				WebDriverWait wait = new WebDriverWait(windriver, 20);
				wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				@SuppressWarnings("unchecked")
				List<WebElement> we = windriver.findElements(ObjectRepo.fetchOR(locator));
				for (WebElement webElement : we) {
					abc = webElement.getText();
					System.out.println("get value passed for " + locator + abc);
					break;
				}
				break;

			} catch (Exception e) {
				System.out.println("get value failed for " + locator);
				continue;
			}

		}

		return abc;
	}

	public Boolean compareValue(String locator, String value) {
		boolean flag = false;
		for (int i = 0; i < 5; i++) {
			try {
				WebDriverWait wait = new WebDriverWait(windriver, 20);
				WebElement typeElement = wait
						.until(ExpectedConditions.visibilityOfElementLocated(ObjectRepo.fetchOR(locator)));
				String abc = typeElement.getText();
				if (abc.toLowerCase().equalsIgnoreCase(value))
					flag = true;
				break;

			} catch (Exception e) {
				System.out.println("get value failed for " + locator);
				continue;
			}

		}

		return flag;
	}

}
