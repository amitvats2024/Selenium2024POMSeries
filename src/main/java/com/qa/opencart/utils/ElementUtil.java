package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.FrameWorkException;
import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	
	
	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public void printElementList(By locator) {
		List<String> eleTextList = getElementsTextList(locator);
		for (String e: eleTextList) {
			System.out.println(e);
		}
	}
	
	public List<String> getElementsTextList(By locator) {
		
		List<WebElement> eleList = getElementsBy(locator);
		List<String> eleListText = new ArrayList<String>();
		for (WebElement e: eleList) {
				String eleText = e.getText();
				if (eleText.length()!=0) {
					eleListText.add(eleText);
			}
			
		}
		return eleListText;
		
	}
	
	public int getElementCounts(By locator) {
		return getElementsBy(locator).size();
	}
	public List<WebElement> getElementsBy(By locator) {
		return driver.findElements(locator);
	}
	
	public String doElementGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	
	public void doSendKeys (By locator, String value) {
		 getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys (WebElement element, String value) {
		 element.clear();
		 element.sendKeys(value);
	}
	
	public void doSendKeys (By locator, CharSequence... value) { 
		getElement(locator).sendKeys(value);
	}
	
	
	public void doSendKeys (By locator, int timeout, String value) {
		 waitForElementVisible (locator, timeout).sendKeys(value);
	}
	
	private void checkElementHighlight(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.isHighlight)) {
			jsUtil.flash(element);
		}
	}
	
	public WebElement getElement (By locator) {
		WebElement element =  driver.findElement(locator);
		checkElementHighlight(element);
		return element;
	}
	
	public void doClick (By locator) {
		getElement(locator).click();
	}
	
	public void doClick (By locator, int timeout) {
		 waitForElementVisible (locator, timeout).click();
	}
	
	public String doGetElementText(By locator) {
		String eleText = getElement(locator).getText();
		if (eleText!=null) {
			return eleText;
		}
		
		else {
			System.out.println("Element text is null" + eleText);
			return null;
		}
	}
	
	public boolean isElementDisplayed(By locator) {
					
		try {
			return getElement(locator).isDisplayed();
			//System.out.println("Element is displayed");
		}
		catch(NoSuchElementException e) {
			System.out.println("Element is not displayed" + locator);
			return false;
		}

	}
	
	public boolean doSearch(By searchfield, By sugglist, String searchkey, String searchmatchvalue) throws InterruptedException  {
		boolean flag = false;
		doSendKeys(searchfield, searchkey);;
		
		Thread.sleep(3000);
		
		List<WebElement> suggEleList = getElementsBy(sugglist);
		
		int suggsize = suggEleList.size();
		
		System.out.println ("The size of suggestion List is:" + suggsize);
		
		if (suggsize==0) {
			System.out.println("No Suggestion Found...");
			throw new FrameWorkException("No Suggestion Found---");
		}
		
		for (WebElement e : suggEleList) {
			String text = e.getText();
			if (text.contains(searchmatchvalue)) {
				e.click();
				flag = true;
				break;
			}
		}
		
		if (flag) {
			System.out.println(searchmatchvalue+ "is found");
			return true;
		}
		
		else {
			System.out.println(searchmatchvalue+ "is not found");
			return false;
		}
	}
	
	// **************Select DropDown Utils**************
	
	public Select getSelect(By locator) {
		return new Select (getElement(locator));
	}
	public int getDropDownCount(By locator) {
		List<WebElement> countrylist = getSelect(locator).getOptions();
		return countrylist.size();
	}
	
	public void selectDropDownValueByVisibleText(By locator, String visibletext) {
		getSelect(locator).selectByVisibleText(visibletext);
	}

	public void selectDropDownValueByValue(By locator, String value) {
		getSelect(locator).selectByValue(value);
	}
	
	public void selectDropDownValueByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
	}
	
	public List<String> getDropDownOptionsList (By locator) {
		List <WebElement> optionlist = getSelect(locator).getOptions();
		System.out.println("Total Number of Options:" + optionlist.size());
		List <String> options = new ArrayList<String>();
		for (WebElement e: optionlist) {
			String text = e.getText();
			options.add(text);
		}
		
		return options;
	}
	
	//Select drop down value using Select Class
	public void getDropDownOptionsListUsingSelect (By locator, String value) {
		List <WebElement> optionlist = getSelect(locator).getOptions();
		SelectDropDown(optionlist, value);
	}
	
	//Select drop down value without using Select Class
	public void SelectDropDownValue(By locator, String value) {
		List <WebElement> optionlist = getElementsBy(locator);
		SelectDropDown(optionlist, value);
	}
	
	private void SelectDropDown(List <WebElement> optionlist, String value) {
		System.out.println("Total Number of Options:" + optionlist.size());
		for (WebElement e : optionlist) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}
	
	//**** wait utils
	
	
	/*
	 * presenceOfElementLocated method definition
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 */
	
	public WebElement waitForElementPresence (By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible on the page as well.
	 * Visibility means that the element is not only displayed but also has a height and width that is greater than 0.
	 * default interval time/ internal time is 500 ms
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public WebElement waitForElementVisible (By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	public WebElement waitForElementVisible (By locator, int timeout, int pollinginterval) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(pollinginterval));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 */
	public void waitForElementAndClick(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public List <WebElement> waitForWebElementsVisible (By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public List <WebElement> waitForWebElementsVisible (By locator, int timeout, int interval) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(interval));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * wait for element visible on the page with fluent page feature
	 * @param locator
	 * @param timeout
	 * @param poolingTime
	 * @return
	 */
	public WebElement waitForElementVisibleWithFluentFeatures (By locator, int timeout, int poolingTime) {
		Wait <WebDriver> wait = new FluentWait<WebDriver>(driver)
									.withTimeout(Duration.ofSeconds(timeout))
									.pollingEvery(Duration.ofSeconds(poolingTime))
									.ignoring(NoSuchElementException.class)
									.ignoring(StaleElementReferenceException.class)
									.ignoring(ElementNotInteractableException.class)
									.withMessage("====Element is not found====" + locator);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));							
	}
	
	
	public List <WebElement> waitForWebElementsPresence (By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		//An expectation for checking that there is at least one element present on a web page.
	}
	
	public String PageTitleIs(int timeout, String expectedTitle) {
		if(waitForTitleIs(timeout, expectedTitle)) {
			return driver.getTitle();
		}
		else {
			return "-1"; 
		}
	}
		
	
	public boolean waitForTitleIs(int timeout, String expectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		
		try {
		return wait.until(ExpectedConditions.titleIs(expectedTitle));
		}
		catch (TimeoutException e){
		System.out.println("Title is incorrect");
		return false;
		}
	}
	
	public String PageTitleContains(int timeout, String expectedTitle) {
		if(waitForTitleContains(timeout, expectedTitle)) {
			return driver.getTitle();
		}
		else {
			return "-1"; 
		}
	}
	
	public boolean waitForTitleContains(int timeout, String expectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		
		try {
		return wait.until(ExpectedConditions.titleContains(expectedTitle));
		}
		catch (TimeoutException e){
		System.out.println("Title is incorrect");
		return false;
		}
	}
	
	public String PageURLContains(int timeout, String expectedTitle) {
		if(waitForURLContains(timeout, expectedTitle)) {
			return driver.getTitle();
		}
		else {
			return "-1"; 
		}
	}
		
	
	public boolean waitForURLContains(int timeout, String expectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		
		try {
		return wait.until(ExpectedConditions.urlContains(expectedTitle));
		}
		catch (TimeoutException e){
		System.out.println("Title is incorrect");
		return false;
		}
	}
	
	public String waitForURLContainsAndReturn(int timeout, String expectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		wait.until(ExpectedConditions.urlContains(expectedTitle));
		return driver.getCurrentUrl();
		}
		catch (TimeoutException e){
		System.out.println("Title is incorrect");
		return "-1";
		}	
	}
	
	//**** wait utils for alerts
	public Alert waitForAlertAndSwitch(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForAlertUsingFluentWaitAndSwitch(int timeout, int poolingTime, By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(poolingTime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("====JS Alert is not present====" + locator);
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public String getAlertText (int timeout) {
		return waitForAlertAndSwitch(timeout).getText();
	}
	
	public void acceptAlert(int timeout) {
		waitForAlertAndSwitch(timeout).accept();
	}

	public void dismissAlert (int timeout) {
		waitForAlertAndSwitch(timeout).dismiss();
	}
	
	public void enterValueOnAlert(int timeout, String value) {
		waitForAlertAndSwitch(timeout).sendKeys(value);
	}
	
	//wait for frame:
	public WebDriver waitForFrameUsingLocatorAndSwitchToIt(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	
	public WebDriver waitForFrameUsingLocatorAndSwitchToIt(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public WebDriver waitForFrameUsingLocatorAndSwitchToIt(String idOrName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public WebDriver waitForFrameUsingLocatorAndSwitchToIt(WebElement frameElement, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	public Boolean waitForNewWindowOrTab(int expectedNumberOfWindows, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		 try {if (wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows))) {
			 return true;
		 }
		 }
		 catch (TimeoutException e) {
			System.out.println("number of windows are not matched....."); 
		 }
		 return false;
}
}
