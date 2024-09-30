package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AcccountPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By headers = By.xpath("//div[@id='content']/h2");
	private By search = By.xpath("//div[@id = 'search']/input[@name = 'search']");
	private By searchIcon = By.cssSelector("div#search button");
	
	public AcccountPage (WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccountPageTitle() {
		String title = eleUtil.PageTitleContains(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.ACCOUNTS_PAGE_TITLE);
		System.out.println("Account Page Title is:" + title);
		return title;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.isElementDisplayed(logoutLink);
	}

	public int getTotalAccountPageHeaders() {
		return eleUtil.waitForWebElementsVisible(headers, AppConstants.DEFAULT_LONG_TIME_OUT).size();
	}
	public List<String> accountPageHeaders() {
		List <WebElement> headerlist = eleUtil.waitForWebElementsVisible(headers, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> headersValueList = new ArrayList<String>();
		for (WebElement e: headerlist) {
			String header = e.getText();
			headersValueList.add(header);
		}
		return headersValueList;
	}
	
	public ResultsPage doSearch(String searchKey) {
		WebElement searchEle = eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_LONG_TIME_OUT);
		eleUtil.doSendKeys(searchEle, searchKey);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}
}
