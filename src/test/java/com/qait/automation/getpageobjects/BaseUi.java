/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static com.qait.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static org.testng.Assert.fail;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;


import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import com.qait.automation.utils.RestAPITester;
import com.qait.automation.utils.SeleniumWait;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;

/**
 * @author QAIT
 *
 */
public class BaseUi {

	protected WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;
	
	protected final int AJAX_WAIT = 5;
	

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));

	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected String logMessage(String message) {
		Reporter.log(message, true);
		return message;
	}

	protected String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	protected void executeJavascript(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}

//	protected void selectValueTextFromDropDown(WebElement el, String value) {
//		wait.waitForElementToBeVisible(el);
//		Select sel = new Select(el);
//		sel.selectByVisibleText(value);
//	}

	public void logMessage(String msgType, String message) {
		ReportMsg.log(msgType, message);
	}

	public void waitTOSync() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	protected void waitForSpinnerToDisappear() {
		int i = 0;
		wait.resetImplicitTimeout(5);
		try {
			List<WebElement> spinnerGifs = driver
					.findElements(By.xpath("//img[contains(@src, '/nb/ui/images/savingAnimation_')]"));
			if (spinnerGifs.size() > 0) {
				for (WebElement spinnerGif : spinnerGifs) {
					while (spinnerGif.isDisplayed() && i <= AJAX_WAIT) {
						wait.hardWait(5);
						i++;
					}
				}
			}
		} catch (Exception e) {
		}
		wait.resetImplicitTimeout(AJAX_WAIT);
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	public By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = StringUtils.replaceOnce(locator[2], "\\$\\{.+\\}", replacement1);
		locator[2] = StringUtils.replaceOnce(locator[2], "\\$\\{.+\\}", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	public static String getStringWithTimestamp(String name) {
		Long timeStamp = System.currentTimeMillis();
		return name + "_" + timeStamp;
	}

	public static long generateRandom(int length) {
		Random random = new Random();
		char[] digits = new char[length];
		digits[0] = (char) (random.nextInt(9) + '1');
		for (int i = 1; i < length; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return Long.parseLong(new String(digits));
	}

}
