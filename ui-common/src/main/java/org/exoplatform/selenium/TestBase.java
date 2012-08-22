package org.exoplatform.selenium;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class TestBase {
	protected static WebDriver driver;
	protected String baseUrl;
	public static Actions actions;
	protected StringBuffer verificationErrors = new StringBuffer();
	public static String browser;
	protected static boolean ieFlag;	 
	protected static boolean chromeFlag;

	private static int seconds = 0;
	protected static int timeoutSecInt=30;
	protected static String timeout="30000";
	public static int loopCount = 0;	

	public static final String ELEMENT_SIGN_IN_LINK = "//b[contains(text(),'Sign in')]";
	public static final String ELEMENT_INPUT_USERNAME = "//input[@name='username']";
	public static final String ELEMENT_INPUT_PASSWORD = "//input[@name='password']";
	public static final String ELEMENT_SIGN_IN_CONFIRM_BUTTON = "//form[@id='UIPortalComponentLogin']//div[@class='UIAction']/*";
	public static final String ELEMENT_SEARCH_ICON_REGISTER = "//img[@class='SearchIcon']";
	public static final String ELEMENT_SAVE_BUTTON = "//a[text()='Save']";
	public static final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON = "//div[contains(@class, 'UIPopupWindow') and contains(@style, 'visibility: visible')]//span[text()='Messages']/..//a[@class='CloseButton']";

	public static final String ELEMENT_INPUT_CONFIRM_PASSWORD = "//input[@id='Confirmpassword']";
	public static final String ELEMENT_INPUT_NEW_PASSWORD = "//input[@id='newPassword']";
	public static final String ELEMENT_INPUT_NEW_CONFIRM_PASSWORD = "//input[@id='confirmPassword']";
	public static final String ELEMENT_INPUT_FIRSTNAME = "//input[@id='firstName']";
	public static final String ELEMENT_INPUT_LASTNAME = "//input[@id='lastName']";
	public static final String ELEMENT_INPUT_EMAIL = "//input[@id='email']";   
	public static final String ELEMENT_LINK_SetUp ="//img[@alt='Setup']";
	public static final String ELEMENT_LINK_Users ="//a[text()='Users']";
	public static final String ELEMENT_LINK_AddUsers="//a[text()='Add Users']";

	public static final String ELEMENT_PAGINATOR_PAGE_LINK = "//a[contains(@class, 'Number') and text()='${number}']";
	public static final String ELEMENT_PAGINATOR_TOTAL_NUMBER = "//a[@class='PagesTotalNumber']";
	public static final String ELEMENT_PAGINATOR_NEXT_ICON = "//a[@class='Icon NextPageIcon']";
	public static final String ELEMENT_PAGINATOR_SELECTED_PAGE = "//a[@class='Number PageSelected' and text()='${number}']";

	public static final String ELEMENT_LINK_PORTAL_TOP_CONTAINER = "//ul[contains (@id, 'PortalNavigationContainer')]/..";
	public static final String ELEMENT_SIGN_OUT_LINK = "//a[@class='LogoutIcon']";
	public static final String ELEMENT_MESSAGE_TEXT = "//li[@class='MessageContainer']/span[contains(@class, 'PopupIcon')]";
	public static final String ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE = ELEMENT_MESSAGE_TEXT + "/../../../../../..//a";

	public static void setup(){
		ieFlag = "true".equals(System.getProperty("selenium.browser"));
		chromeFlag = "true".equals(System.getProperty("selenium.browser"));
	}

	public static void log(String msg) {
		StackTraceElement callerClass = Thread.currentThread().getStackTrace()[2];
		System.out.println(String.format("%-100s%s", "[" + callerClass.getClassName() + "." + callerClass.getMethodName() + "]", msg).replaceAll("  ", ".."));
	}

	public static void checkCycling(Exception e, int loopCountAllowed) {
		System.err.println(e.getClass());
		if (loopCount > loopCountAllowed) {
			Assert.fail("Cycled: " + e.getMessage());
		}
		loopCount++;
	}

	public static void click(String xpath) {
		try {
			WebElement element = waitForAndGetElement(xpath);
			actions.click(element).perform();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			click(xpath);
		} finally {
			loopCount = 0;
		}
	}

	public static void copy(String xpath1, String xpath2){
		try {
			for (int second=0;;second++){
				if (second>=30){
					Assert.fail("time out at:"+ xpath1);
				} 
				WebElement element1 = waitForAndGetElement(xpath1);
				String value2 = element1.getAttribute("value");
				WebElement element2 = waitForAndGetElement(xpath2);
				element2.clear();
				element2.click();
				element2.sendKeys(value2);
				pause(2000);
				if (value2.equals(element1.getAttribute("value"))){
					break;
				}
			}
		} catch (StaleElementReferenceException e) {
			//copy(xpath1, xpath2);
			e.printStackTrace();
		}

	}

	public static void copyPaste(String Source, String value, String Target){ 	
		WebElement element = waitForAndGetElement(Source);
		element.sendKeys(value);
		actions.doubleClick(element).perform();
		element.sendKeys(Keys.LEFT_CONTROL + "a");
		element.sendKeys(Keys.LEFT_CONTROL + "c");
		pause(3000);
		WebElement b = waitForAndGetElement(Target);
		b.sendKeys(Keys.LEFT_CONTROL + "v");
		/*WebElement element=waitForAndGetElement(Source);
  	  actions.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
  	  WebElement element1=waitForAndGetElement(Target);
  	  actions.contextClick(element1).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
		 */

	}

	public static void closeMessageDialog() {
		System.out.println("--Closing message dialog--");
		setup();
		if (ieFlag) {
			click(ELEMENT_MESSAGE_DIALOG_CLOSE_ICON_IE);
		} else {
			click(ELEMENT_MESSAGE_DIALOG_CLOSE_ICON);
		}
	}

	public static void clearCache(){
		Actions actionObject = new Actions(driver);	 
		actionObject.sendKeys(Keys.CONTROL).sendKeys(Keys.F5).build().perform();
	}

	public static void check(String xpath) {
        try {
            WebElement element = waitForAndGetElement(xpath);

            if (!element.isSelected()) {
                actions.click(element).perform();
            } else {
                Assert.fail("Element " + xpath + " is already checked.");
            }
        } catch (StaleElementReferenceException e) {
            checkCycling(e, 5);
            pause(1000);
            check(xpath);
        } finally {
            loopCount = 0;
        }
    }
	
	public static String getText(String xpath) {
		WebElement element = null;
		try {
			element = waitForAndGetElement(xpath);
			return element.getText();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			return getText(xpath);
		} finally {
			loopCount = 0;
		}
	}

	public static String getValue(String xpath) {
		try {
			return waitForAndGetElement(xpath).getAttribute("value");
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			return getValue(xpath);
		} finally {
			loopCount = 0;
		}
	}

	public static void goToNewStaff() {
		System.out.println("--Go to New Staff--");
		goToPage(ELEMENT_SEARCH_ICON_REGISTER, ELEMENT_LINK_SetUp, ELEMENT_LINK_Users, ELEMENT_LINK_AddUsers);
	}

	public static void goToPage(String verification, String... navigation) {
		String page = makeLink(navigation[navigation.length - 1]);
		boolean needToBeVerified = true;

		List<String> navigationList = new ArrayList<String>();

		for (int i = 0; i < (navigation.length - 1); i++) {
			String node = navigation[i];
			node = makeLink(node);
			navigationList.add(node);
		}

		try {
			for (String node : navigationList) {
				if (ieFlag) {
					actions.moveToElement(getElement(node));
				} else {
					mouseOver(node, false);
				}
			}
			mouseOverAndClick(page);
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 10);
			goToPage(verification, navigation);
			needToBeVerified = false;
		} finally {
			loopCount = 0;
		}

		if (verification != null && needToBeVerified) {
			pause(500);
			verifyLocation(verification, navigationList, page);
		}
	}

	public static String getTextFromAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			return alert.getText();
		} catch (NoAlertPresentException e) {
			return "";
		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isTextPresent(String text) {
		pause(500);
		String allVisibleTexts = getText("//body");

		return allVisibleTexts.contains(text);
	}

	public static boolean isTextNotPresent(String text) {
		return !isTextPresent(text);
	}

	public static boolean isElementPresent(String xpath) {
		setup();
		if (ieFlag) {
			pause(1000);
		} else {
			pause(500);
		}
		try {
			driver.findElement(By.xpath(xpath));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementNotPresent(String xpath) {
		return !isElementPresent(xpath);
	}

	private static String makeLink(String node) {
		if (!node.contains("//")) {
			String label = node;
			node = "//a[text()='" + label + "']";
		}
		return node;
	}

	public static void mouseOver(String xpath, boolean safeToSERE) {
		if (safeToSERE) {
			try {
				WebElement element = waitForAndGetElement(xpath);
				actions.moveToElement(element).perform();
			} catch (StaleElementReferenceException e) {
				checkCycling(e, 5);
				pause(1000);
				mouseOver(xpath, safeToSERE);
			} finally {
				loopCount = 0;
			}
		} else {
			WebElement element = waitForAndGetElement(xpath);
			actions.moveToElement(element).perform();
		}
	}

	public static void mouseOverAndClick(String xpath) {
		WebElement element;
		setup();
		if (ieFlag) {
			element = getElement(xpath);
		} else {
			element = waitForAndGetElement(xpath);
		}
		actions.moveToElement(element).click(element).build().perform();
	}

	public static void pause(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForElementPresent(By by) throws Exception {
		for (int second = 0;; second++) {
			if (second >= 60) Assert.fail("timeout");
			try { if (isElementPresent(by)) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}
	}

	public static WebElement getElement(String xpath) {
		pause(500);
		return driver.findElement(By.xpath(xpath));
	}

	public static WebElement waitForAndGetElement(String xpath) {
		WebElement element = null;
		for (int second = 0;; second++) {
			if (second >= timeoutSecInt) {
				Assert.fail("Timeout at waitForElementPresent: " + xpath);
			}
			try {
				element = driver.findElement(By.xpath(xpath));
				boolean isLoadingDisplayed = false;
				try {
					WebElement loading = driver.findElement(By.xpath("//div[@id='AjaxLoadingMask']"));
					isLoadingDisplayed = loading.isDisplayed();
				} catch (Exception e) {
				}
				if (element.isDisplayed() && !isLoadingDisplayed) {
					break;
				}
			} catch (Exception e) {
			}
			pause(1000);
		}
		return element;
	}

	public static void waitForElementNotPresent(String xpath) {
		for (int second = 0;; second++) {
			if (second >= timeoutSecInt) {
				Assert.fail("Timeout at waitForElementNotPresent: " + xpath);
			}
			try {
				driver.findElement(By.xpath(xpath));
			} catch (NoSuchElementException e) {
				break;
			} catch (Exception e) {
			}
			pause(1000);
		}
	}

	public static void waitForTextPresent(String text) {
		for (int second = 0;; second++) {
			if (second >= timeoutSecInt) {
				Assert.fail("Timeout at waitForTextPresent: " + text);
			}
			if (isTextPresent(text)) {
				break;
			}
			pause(500);
		}
	}

	public static void waitForTextNotPresent(String text) {
		for (int second = 0;; second++) {
			if (second >= timeoutSecInt) {
				Assert.fail("Timeout at waitForTextNotPresent: " + text);
			}
			if (isTextNotPresent(text)) {
				break;
			}
			pause(500);
		}
	}

	public static void waitForMessage(String message) {
		System.out.println("--Verify message: " + message);
		pause(500);
		waitForTextPresent(message);
	}

	public static void type(String xpath, String value, boolean validate) {
		try {
			for (int second = 0;; second++) {
				if (second >= timeoutSecInt) {
					Assert.fail("Timeout at type: " + value + " into " + xpath);
				}
				WebElement element = waitForAndGetElement(xpath);
				element.clear();
				element.click();
				element.sendKeys(value);
				if (!validate || value.equals(getValue(xpath))) {
					break;
				}
				pause(1000);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 5);
			pause(1000);
			type(xpath, value, validate);
		} finally {
			loopCount = 0;
		}
	}

	public static void select(String xpath, String option) {
		try {
			for (int second = 0;; second++) {
				if (second >= timeoutSecInt) {
					Assert.fail("Timeout at select: " + option + " into " + xpath);
				}
				Select select = new Select(waitForAndGetElement(xpath));
				select.selectByVisibleText(option);
				if (option.equals(select.getFirstSelectedOption().getText())) {
					break;
				}
				pause(1000);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, 7);
			pause(1000);
			select(xpath, option);
		} finally {
			loopCount = 0;
		}
	}

	public static void save() {
		waitForAndGetElement(ELEMENT_SAVE_BUTTON);
		click(ELEMENT_SAVE_BUTTON);
	}

	public static void signInAsRoot() {
		signIn("root", "gtn");
	}

	public static void signIn(String username, String password) {
		System.out.println("--Sign in as " + username + "--");
		click(ELEMENT_SIGN_IN_LINK);
		type(ELEMENT_INPUT_USERNAME, username, true);
		type(ELEMENT_INPUT_PASSWORD, password, true);
		click(ELEMENT_SIGN_IN_CONFIRM_BUTTON);
		waitForElementNotPresent(ELEMENT_SIGN_IN_CONFIRM_BUTTON);
	}

	public static void signOut(){
		Actions action_logout = new Actions(driver);
		WebElement UI = driver.findElement(By.id("UserNavigationTabsContainer"));
		action_logout.moveToElement(UI).build().perform();
		driver.findElement(By.linkText("Logout")).click();	
	}

	private static void verifyLocation(String xpath, List<String> navigation, String page) {
		System.out.println("verifyLocation, element: " + xpath);
		setup();
		if (isElementNotPresent(xpath)) {
			pause(5000);
		}
		for (; isElementNotPresent(xpath); seconds++) {
			if (seconds >= timeoutSecInt) {
				Assert.fail("Timeout at goToPage");
			}
			pause(500);
			try {
				for (String node : navigation) {
					if (ieFlag) {
						actions.moveToElement(getElement(xpath));
					} else {
						mouseOver(node, false);
					}
				}
				mouseOverAndClick(page);
			} catch (StaleElementReferenceException e) {
				checkCycling(e, 10);
				verifyLocation(xpath, navigation, page);
				break;
			} finally {
				loopCount = 0;
			}
		}
		seconds = 0;
	}

	public static void usePaginator(String xpath, String exceptionMessage) {
		String page1 = ELEMENT_PAGINATOR_PAGE_LINK.replace("${number}", "1");

		click(page1);
		pause(500);
		int totalPages = isElementPresent(ELEMENT_PAGINATOR_TOTAL_NUMBER) ? Integer.valueOf(getText(ELEMENT_PAGINATOR_TOTAL_NUMBER)) : 1;
		int i = 1;
		while (isElementNotPresent(xpath)) {
			if (i == totalPages) {
				Assert.fail(exceptionMessage);
			}
			click(ELEMENT_PAGINATOR_NEXT_ICON);
			waitForAndGetElement(ELEMENT_PAGINATOR_SELECTED_PAGE.replace("${number}", String.valueOf((++i))));
			pause(500);
		}
	}

	public static void waitForConfirmation(String confirmationText) {
		String message = getTextFromAlert();

		System.out.println("confirmation: " + message);

		if (message.isEmpty()) {
			if (loopCount > 5) {
				Assert.fail("Message is empty");
			}
			pause(500);
			loopCount++;
			waitForConfirmation(confirmationText);
			return;
		}

		for (int second = 0;; second++) {
			if (second >= timeoutSecInt) {
				Assert.fail("Timeout at waitForConfirmation: " + confirmationText);
			}
			if (message.equals(confirmationText)) {
				break;
			}
			pause(1000);
		}
		Alert alert = driver.switchTo().alert();
		alert.accept();
		pause(500);
		//driver.findElement(By.linkText("OK")).click();
	}


}
