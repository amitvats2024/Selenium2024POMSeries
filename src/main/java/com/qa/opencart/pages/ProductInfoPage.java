package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.tagName("h1");
	private By productMetaData = By.xpath("(//div[@class = 'col-sm-4']//ul)[1]/li");
	private By productPriceData = By.xpath("(//div[@class = 'col-sm-4']//ul)[2]/li");
	private By productImage = By.xpath("//ul[@class = 'thumbnails']//img");
	
	private Map <String, String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeader() {
	String productHeaderValue =	eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_LONG_TIME_OUT).getText();
	System.out.println("Product Header is " + productHeaderValue );
	return productHeaderValue;
	}
	
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElementsBy(productMetaData);
		for (WebElement meta: metaList) {
			String metaText = meta.getText();
			String metaData[] = metaText.split(":");
			String metaKey = metaData[0].trim();
			String metaValue = metaData[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	
	private void getproductPriceData () {
		List<WebElement> metaList = eleUtil.getElementsBy(productPriceData);
		String price = metaList.get(0).getText();
		String exTaxPrice = metaList.get(1).getText().split(":")[1];
		productMap.put("productPrice", price);
		productMap.put("extraPrice", exTaxPrice);
	}
	
	public Map<String, String> getProductData() {
		productMap = new HashMap<String, String>(); //no order
//		productMap = new LinkedHashMap<String, String>(); //to maintain insertion order
//		productMap = new TreeMap<String, String>(); //to maintain the alphabetical order
		productMap.put("productheader", getProductHeader());
		getProductMetaData();
		getproductPriceData();
		System.out.println("Product Full Data: " + productMap);
		return productMap;
	}
	
	public int getProductImagesCount() {
		List<WebElement> productimages= eleUtil.waitForWebElementsVisible(productImage, AppConstants.DEFAULT_LONG_TIME_OUT);
		int productimagescount = productimages.size();
		System.out.println("Product Image Count is: " + productimagescount);
		return productimagescount;
	}
} 
