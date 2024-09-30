package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	//1. private By locators: page objects
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[normalize-space()='Forgotten Password']");
	private By logo = By.xpath("//img[@title='naveenopencart']");
	private By registerLink = By.linkText("Register");
	
	//2. public Page Constructor
	public LoginPage(WebDriver driver){
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//3. public Page Actions/Methods
	
	public String getLoginPageTitle() {
		String loginPageTitle = eleUtil.PageTitleContains(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_TITLE);
		System.out.println("Login Page Title is:" + loginPageTitle);
		return loginPageTitle;
	}
	
	public String getCurrentURL() {
		String currentURL = eleUtil.waitForURLContainsAndReturn(AppConstants.DEFAULT_SHORT_TIME_OUT, AppConstants.LOGIN_PAGE_FRACTION_URL);
		System.out.println("Login Page URL is:" + currentURL);
		return currentURL;
	}
	
	public boolean isforgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	public boolean islogoExist() {
		 return eleUtil.isElementDisplayed(logo);
	}
	
	public AcccountPage doLogin(String userName, String pwd) {
		
		eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys("avats123@gmail.com");
		eleUtil.doSendKeys(password, "Amit@123");
		eleUtil.doClick(loginBtn);
		return new AcccountPage(driver);
	}
	
	public RegisterPage navigateToRegisterPage () {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
