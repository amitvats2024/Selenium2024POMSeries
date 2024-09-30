package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.ExtentReportListener;

//@Listeners({ExtentReportListener.class, AnnotationTransformer.class })
public class LoginPageTest extends BaseTest{
	
	@Test
	public void loginPageTitleTest() {
		String acttitle = loginpage.getLoginPageTitle();
		Assert.assertEquals(acttitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Test
	public void loginPageURLTest() {
		String actURL = loginpage.getCurrentURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginpage.isforgotPwdLinkExist());
	}
	
	@Test
	public void logoExistTest() {
		Assert.assertTrue(loginpage.islogoExist());
	}

	@Test (priority = Integer.MAX_VALUE)
	public void loginTest() { 
		accpage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accpage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
}
