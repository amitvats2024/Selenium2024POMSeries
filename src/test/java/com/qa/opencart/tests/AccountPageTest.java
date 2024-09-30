package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountPageTest extends BaseTest{

	@BeforeClass
	public void accSetUp() {
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		String acttitle = accpage.getAccountPageTitle();
		Assert.assertEquals(acttitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void isLogoutExistTest() {
		Assert.assertTrue(accpage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		Assert.assertEquals(accpage.getTotalAccountPageHeaders(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> headersList = accpage.accountPageHeaders();
		Assert.assertEquals(headersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"MacBook", 3},
			{"iMac", 1},
			{"Samsung", 2}
		};
	}
	
	@Test (dataProvider = "getSearchKey")
	public void searchCountTest(String searchKey, int count) {
		resultsPage = accpage.doSearch(searchKey);
		Assert.assertEquals(resultsPage.getResultsCount(), count);
	}
	
	
	@DataProvider
	public Object[][] getSearchData(){
	return new Object[][]	{
		{"MacBook", "MacBook Pro"},
		{"MacBook", "MacBook Air"},
		{"iMac", "iMac"},
		{"Samsung", "Samsung SyncMaster 941BW"},
		{"Samsung", "Samsung Galaxy Tab 10.1"}
	};
}
	
	@Test (dataProvider = "getSearchData")
	public void searchTest(String searchKey, String productName) {
		resultsPage = accpage.doSearch(searchKey);
		productInfoPage =  resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
		
	}
	
	
}
