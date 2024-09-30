package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class ProductInfoPageTest extends BaseTest{

	@BeforeClass
	public void productInfoSetUp() {
	accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void productHeaderTest() {
		resultsPage = accpage.doSearch("Mac Book");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeader(),"MacBook Pro");
	}
	
	@Test
	public void productInfoTest() {
		softAssert = new SoftAssert();
		resultsPage = accpage.doSearch("Mac Book");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> actualProductDataMap = productInfoPage.getProductData();
		
		softAssert.assertEquals(actualProductDataMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actualProductDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(actualProductDataMap.get("Availability"), "In Stock");
		softAssert.assertEquals(actualProductDataMap.get("productPrice"), "$2,000.00");
		softAssert.assertEquals(actualProductDataMap.get("extraPrice"), " $2,000.00");
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] getProductImagesCountData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7},
			{"canon", "Canon EOS 5D", 3}
		};
	}
	@Test (dataProvider = "getProductImagesCountData")
	public void productCountTest(String searchKey, String productName, int imagesCount) {
		resultsPage = accpage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		int actualProductCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actualProductCount, imagesCount);
	}
}
