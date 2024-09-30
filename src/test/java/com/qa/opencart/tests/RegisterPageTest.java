package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void registerSetUp() {
		registerPage = loginpage.navigateToRegisterPage();
	}
	
	public String getRandomEmail() {
		return "uiautomation"+System.currentTimeMillis()+"@open.com";
	}
	
	
	@Test
	public void userRegistrationTest() {
		Assert.assertTrue(registerPage.userRegisteration("Veena", "automation", getRandomEmail(), "123456789", "Veena@123", "No"));
	}

}
