package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By searchHeader = By.cssSelector("div#content h2");
	private By results = By.cssSelector("div.product-thumb");
	
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String searchHeader() {
		String searchHeaderValue= eleUtil.waitForElementVisible(searchHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		return searchHeaderValue;
	}

	public int getResultsCount() {
		int resultCount= eleUtil.waitForWebElementsVisible(results, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Search Results Count =" + resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Selecting Product " + productName);
		eleUtil.waitForElementAndClick(By.linkText(productName), AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		return new ProductInfoPage(driver);
	}
}
