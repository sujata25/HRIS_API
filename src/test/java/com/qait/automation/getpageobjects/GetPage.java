package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;



import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import static org.testng.Assert.*;

import org.testng.Assert;
import org.testng.*;

public class GetPage extends BaseUi {

	public WebDriver webdriver;
	public String pageName;
	String windowHandle;
	LogMessages log;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		log = new LogMessages();
	}

	///////////////////////////////////////////////////////////
	////////////////////// VERIFICATIONS //////////////////////
	///////////////////////////////////////////////////////////

	private boolean _isElementDisplayed(String locator) {
		WebElement elem = element(locator);
		wait.waitForElementToBeVisible(elem);
		return element(locator).isDisplayed();
	}

	private boolean _isElementDisplayed(String locator, String replacement) {
		WebElement elem = element(locator, replacement);
		wait.waitForElementToBeVisible(elem);
		return element(locator, replacement).isDisplayed();
	}

	private boolean _isElementDisplayed(String locator, String replacement1, String replacement2) {
		WebElement elem = element(locator, replacement1, replacement2);
		wait.waitForElementToBeVisible(elem);
		return element(locator, replacement1, replacement2).isDisplayed();
	}

	protected void verifyElementDisplayed(String locator) {
		assertTrue(_isElementDisplayed(locator), log.getVerificationFailedMessage(Verification.DISPLAYED,
                "",locator, "", ""));
		log.sendVerificationPassedMessage(Verification.DISPLAYED, "",
                locator, "", "");
	}

	protected void verifyElementDisplayed(String locator, String replacement) {
		assertTrue(_isElementDisplayed(locator, replacement), log.getVerificationFailedMessage(
		        Verification.DISPLAYED, "",locator, replacement, ""));
		log.sendVerificationPassedMessage(Verification.DISPLAYED, "",locator,
                replacement, "");
	}

	protected void verifyElementDisplayed(String locator, String replacement1, String replacement2) {
		assertTrue(_isElementDisplayed(locator, replacement1, replacement2), log.getVerificationFailedMessage(
                Verification.DISPLAYED, "", locator, replacement1, replacement2));
		log.sendVerificationPassedMessage(Verification.DISPLAYED, "",
                locator, replacement1, replacement2);
	}

	protected void verifyElementNotDisplayed(String locator) {
		assertFalse(_isElementDisplayed(locator), log.getVerificationFailedMessage(Verification.NOT_DISPLAYED,
                "",locator, "", ""));
		log.sendVerificationPassedMessage(Verification.NOT_DISPLAYED, "",
                locator, "", "");
	}

	protected void verifyElementNotDisplayed(String locator, String replacement) {
		assertFalse(_isElementDisplayed(locator, replacement), log.getVerificationFailedMessage(
                Verification.NOT_DISPLAYED, "", locator, replacement, ""));
		log.sendVerificationPassedMessage(Verification.NOT_DISPLAYED, "",
                locator, replacement, "");
	}

	///////////// Verify Element Contains Specified Text ////////////
	protected void verifyElementContainsText(String locator, String expectedText) {
		verifyElementDisplayed(locator);
		assertTrue(element(locator).getText().trim().contains(expectedText),
				log.getVerificationFailedMessage(Verification.PARTIAL_TEXT, expectedText,
                        locator, "", ""));
		log.sendVerificationPassedMessage(Verification.PARTIAL_TEXT, expectedText,
                locator, "", "");
	}
	protected void verifyElementContainsText(String locator, String replacement, String expectedText) {
		verifyElementDisplayed(locator, replacement);
		assertTrue(element(locator, replacement).getText().trim().contains(expectedText),
				log.getVerificationFailedMessage(Verification.PARTIAL_TEXT, expectedText,
                        locator, replacement, ""));
		log.sendVerificationPassedMessage(Verification.PARTIAL_TEXT, expectedText,
                locator, replacement, "");
	}
	protected void verifyElementContainsText(String locator, String replacement1, String replacement2, String expectedText) {
		verifyElementDisplayed(locator, replacement1, replacement2);
		assertTrue(element(locator, replacement1, replacement2).getText().trim().contains(expectedText),
				log.getVerificationFailedMessage(Verification.PARTIAL_TEXT, expectedText,
                        locator, replacement1, replacement2));
		log.sendVerificationPassedMessage(Verification.PARTIAL_TEXT, expectedText, locator,
                replacement1, replacement2);
	}

	//////////////////// Verify Element Text ///////////////////////
	protected void verifyElementText(String locator, String expectedText) {
		verifyElementDisplayed(locator);
		assertEquals(element(locator).getText().trim(), expectedText,
				log.getVerificationFailedMessage(Verification.EXACT_TEXT, expectedText,
                        locator, "", ""));
		log.sendVerificationPassedMessage(Verification.EXACT_TEXT, expectedText,
                locator, "", "");
	}

	protected void verifyElementText(String locator, String replacement, String expectedText) {
		verifyElementDisplayed(locator, replacement);
		assertEquals(element(locator, replacement).getText().trim(), expectedText,
				log.getVerificationFailedMessage(Verification.EXACT_TEXT, expectedText,
                        locator, replacement, ""));
		log.sendVerificationPassedMessage(Verification.EXACT_TEXT, expectedText,
                locator, replacement, "");
	}

	protected void verifyElementText(String locator, String replacement1, String replacement2, String expectedText) {
		verifyElementDisplayed(locator, replacement1, replacement2);
		assertEquals(element(locator, replacement1, replacement2).getText().trim(), expectedText,
				log.getVerificationFailedMessage(Verification.EXACT_TEXT, expectedText,
                        locator, replacement1, replacement2));
		log.sendVerificationPassedMessage(Verification.EXACT_TEXT, expectedText,
                locator, replacement1, replacement2);
	}

	////////////////////////////////////////////////////////////////////////////
	///////////////////// BROWSER WINDOW & FRAME OPERATIONS ////////////////////
	////////////////////////////////////////////////////////////////////////////

	protected void reloadPage() {
		driver.navigate().refresh();
		logMessage("[INFO]: PAGE GOT REFRESHED...");
	}

	/**
	 * Switches focus of WebDriver to the next found window handle (that's your
	 * newly opened window)
	 */
	protected void switchToNewWindow() {
		windowHandle = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			logMessage("Info", "Switching Driver to new Window: " + winHandle);
		}
	}

	protected void switchToMainWindow() {
		driver.close();
		driver.switchTo().window(windowHandle);
		logMessage("Info", "Switching Driver back to Main Window");
	}

	protected void switchToFrame(String locator) {
		verifyElementDisplayed(locator);
		driver.switchTo().frame(element(locator));
		logMessage("Info", "Switching Driver to Frame with locator '" + locator + "'");
	}

	protected void switchToFrame(String locator, String replacement) {
		verifyElementDisplayed(locator, replacement);
		driver.switchTo().frame(element(locator, replacement));
		logMessage("Info",
				"Switching Driver to Frame with locator '" + locator + "' and replacement '" + replacement + "'");
	}

	protected void switchToNestedFrames(String... frameLocators) {
		switchToDefaultContent();
		for (int i = 0; i < frameLocators.length; i++) {
			switchToFrame(frameLocators[i]);
		}
	}

	protected void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		logMessage("Info", "Switching Driver back to Default Content");
	}

	///////////////////////////////////////////////////////////////
	/////////////////// ALERT, CONFIRMATION & POPUPS //////////////
	///////////////////////////////////////////////////////////////

	/**
	 * Accepts alert window.
	 */
	protected void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			logMessage("Action", "Accepting Alert");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			logMessage("Error", e.getMessage());
		}
	}

	protected void dismissAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			logMessage("Action", "Dismissing Alert");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			logMessage("Error", e.getMessage());
		}
	}

	////////////////////////////////////////////////////////////////////////////
	////////////////////////// JAVASCRIPT OPERATIONS ///////////////////////////
	////////////////////////////////////////////////////////////////////////////

	protected List<WebElement> executeJavascriptWithReturnElement(String script) {
		return (List<WebElement>) ((JavascriptExecutor) driver).executeScript(script);
	}

	protected String executeJavascriptWithReturnString(String script) {
		return (String) ((JavascriptExecutor) driver).executeScript(script);
	}

	protected String getValueInsideTextInput(String css) {
		return executeJavascriptWithReturnString("$('" + css + "').val()").trim();
	}

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void clickElementUsingJavaScript(String locator) {
		verifyElementDisplayed(locator);
		WebElement elem = element(locator);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", elem);
		logMessage("Action", "Clicked element with locator '" + locator + "' using Javascript");
	}

	public void clickElementUsingJavaScript(String locator, String replacement) {
		verifyElementDisplayed(locator, replacement);
		WebElement elem = element(locator, replacement);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", elem);
		logMessage("Action", "Clicked element with locator '" + locator + "' and replacement '" + replacement
				+ "' using Javascript");
	}

	public void clickElementUsingJavaScript(String locator, String replacement1, String replacement2) {
		verifyElementDisplayed(locator, replacement1, replacement2);
		WebElement elem = element(locator, replacement1, replacement2);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", elem);
		logMessage("Action", "Clicked element with locator '" + locator + "' and replacement '" + replacement1
				+ "' and replacement '" + replacement2 + "' using Javascript");
	}
	///////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// GET LIST OF WEB ELEMENTS ///////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////

	protected List<WebElement> elements(String elementToken) {
		return webdriver.findElements(getLocator(elementToken));
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return webdriver.findElements(getLocator(elementToken, replacement));
	}

	protected List<WebElement> elements(String elementToken, String replacement1, String replacement2) {
		return webdriver.findElements(getLocator(elementToken, replacement1, replacement2));
	}

	/////////////////////////////////////////////////////////////////////////////
	////////////////////////////// DROPDOWN OPERATIONS //////////////////////////
	/////////////////////////////////////////////////////////////////////////////

	protected void selectDropdownByIndex(String locator, int index) {
		verifyElementDisplayed(locator);
		Select dropdown = new Select(element(locator));
		dropdown.selectByIndex(index);
		logMessage("Action", "Selected index '" + index + "' from dropdown with locator '" + locator + "'");
	}

	protected void selectDropdownByIndex(String locator, String replacement, int index) {
		verifyElementDisplayed(locator, replacement);
		Select dropdown = new Select(element(locator, replacement));
		dropdown.selectByIndex(index);
		logMessage("Action", "Selected index '" + index + "' from dropdown with locator '" + locator
				+ "' and replacement '" + replacement + "'");
	}

	protected void selectDropdownByValue(String locator, String value) {
		verifyElementDisplayed(locator);
		Select dropdown = new Select(element(locator));
		dropdown.selectByValue(value);
		logMessage("Action", "Selected value '" + value + "' from dropdown with locator '" + locator + "'");
	}

	protected void selectDropdownByValue(String locator, String replacement, String value) {
		verifyElementDisplayed(locator, replacement);
		Select dropdown = new Select(element(locator, replacement));
		dropdown.selectByValue(value);
		logMessage("Action", "Selected value '" + value + "' from dropdown with locator '" + locator
				+ "' and replacement '" + replacement + "'");
	}

	protected void selectDropdownByVisibleText(String locator, String text) {
		verifyElementDisplayed(locator);
		Select dropdown = new Select(element(locator));
		dropdown.selectByVisibleText(text);
		logMessage("Action", "Selected visible text '" + text + "' from dropdown with locator '" + locator + "'");
	}

	protected void selectDropdownByVisibleText(String locator, String replacement, String text) {
		verifyElementDisplayed(locator, replacement);
		Select dropdown = new Select(element(locator, replacement));
		dropdown.selectByVisibleText(text);
		logMessage("Action", "Selected visible text '" + text + "' from dropdown with locator '" + locator
				+ "' and replacement '" + replacement + "'");
	}

	protected void selectDropdownByVisibleText(String locator, String replacement1, String replacement2, String text) {
		verifyElementDisplayed(locator, replacement1, replacement2);
		Select dropdown = new Select(element(locator, replacement1, replacement2));
		dropdown.selectByVisibleText(text);
		logMessage("Action", "Selected visible text '" + text + "' from dropdown with locator '" + locator
				+ "', replacement1 '" + replacement1 + "' and replacement2 '" + replacement2 + "'");
	}

	//////////////////////////////////////////////////////////////////////
	////////////////////// FILL TEXT INPUT/TEXT AREA /////////////////////
	//////////////////////////////////////////////////////////////////////

	private void _fillText(WebElement elem, String value) {
		elem.click();
		elem.clear();
		elem.sendKeys(value);
	}

	protected void fillTextField(String locator, String value) {
		verifyElementDisplayed(locator);
		WebElement elem = element(locator);
		_fillText(elem, value);
		logMessage("Action", "Filling TextField having locator '" + locator + "' with value '" + value + "'");
	}

	protected void fillTextField(String locator, String replacement, String value) {
		verifyElementDisplayed(locator, replacement);
		WebElement elem = element(locator, replacement);
		_fillText(elem, value);
		logMessage("Action", "Filling TextField having locator '" + locator + "', replacement '" + replacement
				+ "', with value '" + value + "'");
	}

	protected void fillTextField(String locator, String replacement1, String replacement2, String value) {
		verifyElementDisplayed(locator, replacement1, replacement2);
		WebElement elem = element(locator, replacement1, replacement2);
		_fillText(elem, value);
		logMessage("Action", "Filling TextField having locator '" + locator + "', replacement1 '" + replacement1
				+ "', replacement2 '" + replacement2 + "' with value '" + value + "'");
	}

	//////////////////////////////////////////////////////////
	///////////////////////// HOVER //////////////////////////
	//////////////////////////////////////////////////////////

	protected void hover(String locator) {
		verifyElementDisplayed(locator);
		Actions hoverAction = new Actions(driver);
		hoverAction.moveToElement(element(locator)).build().perform();
		logMessage("Action", "Hover action performed on element with locator '" + locator + "'");
	}

	protected void hover(String locator, String replacement) {
		verifyElementDisplayed(locator, replacement);
		Actions hoverAction = new Actions(driver);
		hoverAction.moveToElement(element(locator, replacement)).build().perform();
		logMessage("Action", "Hover action performed on element with locator '" + locator + "' and replacement '"
				+ replacement + "'");
	}

	protected void hoverUsingJavaScript(String locator) {
		verifyElementDisplayed(locator);
		WebElement element = element(locator);
		String javaScript = "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
				+ "arguments[0].dispatchEvent(evObj);";
		((JavascriptExecutor) driver).executeScript(javaScript, element);
	}

	//////////////////////////////////////////////////////////
	///////////////////////// CLICK //////////////////////////
	//////////////////////////////////////////////////////////

	protected void click(String locator) {
		verifyElementDisplayed(locator);
		element(locator).click();
		logMessage("Action", "Click action performed on element with locator '" + locator + "'");
	}

	protected void click(String locator, String replacement) {
		verifyElementDisplayed(locator, replacement);

		element(locator, replacement).click();
		logMessage("Action", "Click action performed on element with locator '" + locator + "' and replacement '"
				+ replacement + "'");
	}

	protected void click(String locator, String replacement1, String replacement2) {
		verifyElementDisplayed(locator, replacement1, replacement2);
		element(locator, replacement1, replacement2).click();
		logMessage("Action", "Click action performed on element with locator '" + locator + "', replacement1 '"
				+ replacement1 + "' and replacement2 '" + replacement2 + "'");
	}

	//////////////////////////// END ///////////////////////////


	protected boolean checkIfElementIsThere(String eleString) {
		boolean flag = false;
		try {
			if (driver.findElements(getLocator(eleString)).size() > 0) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString, String replacement) {
		boolean flag = false;
		try {
			if (driver.findElements(getLocator(eleString, replacement)).size() > 0) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	/*
	 * protected By getLocator(String elementToken, String replacement1, String
	 * replacement2) { String[] locator = getELementFromFile(this.pageName,
	 * elementToken); locator[2] = StringUtils.replace(locator[2], "${value}",
	 * replacement1); locator[2] = StringUtils.replace(locator[2], "%{text}",
	 * replacement2); return getBy(locator[1].trim(), locator[2].trim()); }
	 */

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

	public String verifyElementAttribute(String ele, String AttributeType) {
		String attributeValue = element(ele).getAttribute(AttributeType);
		return attributeValue;
	}

	public void clickOn(String ele, String message) {
		verifyElementDisplayed(ele);
		element(ele).click();
		logMessage("[INFO]: " + message);
		wait.waitForPageToLoadCompletely();
	}

	public int getNoOfOpenedWindows() {
		Set<String> winHandles = driver.getWindowHandles();
		return winHandles.size();
	}

	public void selectDropDownText(WebElement e, String value) {
		Select dropdown = new Select(e);
		dropdown.selectByVisibleText(value);
		Reporter.log(value + " is selected");
		waitTOSync();
	}

	protected void resetImplicitTimeout(int newTimeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
	}

	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}

	protected WebElement element(String elementToken, String replacement) throws NoSuchElementException {
		WebElement elem = null;
		try {

			elem = wait.waitForElementToBeVisible(driver.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail(logMessage(
					"[ASSERT FAILED]: Element " + elementToken + " not found on the " + this.pageName + " !!!"));
		} catch (NullPointerException npe) {

		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2) {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(
					webdriver.findElement(getLocator(elementToken, replacement1, replacement2)));
		} catch (NoSuchElementException excp) {
			ReportMsg.fail("No such Element Found Exception caught!!!!");
			ReportMsg.fail("Element " + elementToken + " not found on the " + this.pageName + " !!!");
		} catch (Exception npe) {
			ReportMsg.fail(npe.getLocalizedMessage());
			ReportMsg.fail("General Exception cought Exception caught!!!!");
			ReportMsg.fail("Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	public void selectCheckbox(String elementToken) {
		verifyElementDisplayed(elementToken, "", "");
		if (!element(elementToken).isSelected()) {
			element(elementToken).click();
		}
	}

	public void selectCheckbox(String elementToken, String replacement1) {
		verifyElementDisplayed(elementToken, replacement1, "");
		if (!element(elementToken, replacement1).isSelected()) {
			element(elementToken, replacement1).click();
		}
	}

	public void selectCheckbox(String elementToken, String replacement1, String replacement2) {
		verifyElementDisplayed(elementToken, replacement1, replacement2);
		if (!element(elementToken, replacement1, replacement2).isSelected()) {
			element(elementToken, replacement1, replacement2).click();
		}
	}

	public void deselectCheckbox(String elementToken) {
		verifyElementDisplayed(elementToken, "", "");
		if (element(elementToken).isSelected()) {
			element(elementToken).click();
		}
	}

	public void deselectCheckbox(String elementToken, String replacement1) {
		verifyElementDisplayed(elementToken, replacement1, "");
		if (element(elementToken, replacement1).isSelected()) {
			element(elementToken, replacement1).click();
		}
	}

	public void deselectCheckbox(String elementToken, String replacement1, String replacement2) {
		verifyElementDisplayed(elementToken, replacement1, replacement2);
		if (element(elementToken, replacement1, replacement2).isSelected()) {
			element(elementToken, replacement1, replacement2).click();
		}
	}

}
