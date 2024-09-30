package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage  {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName = By.xpath("//input[@id='input-firstname']");
	private By lastName = By.xpath("//input[@id='input-lastname']");
	private By email = By.xpath("//input[@id='input-email']");
	private By telephone = By.xpath("//input[@id='input-telephone']");
	private By password = By.xpath("//input[@id='input-password']");
	private By passwordConfirm = By.xpath("//input[@id='input-confirm']");
	private By subscribeNo = By.xpath("//input[@value='0']");
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']//input[@name='newsletter']");
	private By privacypolicy = By.xpath("//input[@name='agree']");
	private By continueBtn = By.xpath("//input[@value='Continue']");
	private By successMessage = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	public RegisterPage (WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public boolean userRegisteration(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_SHORT_TIME_OUT).sendKeys(firstName);
		
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.passwordConfirm, password);
		
		if (subscribe.equalsIgnoreCase("Yes")) {
		eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(privacypolicy);
		eleUtil.doClick(continueBtn);
		
		String successMessageText = eleUtil.waitForElementVisible(successMessage, AppConstants.DEFAULT_LONG_TIME_OUT).getText();
		
		if (successMessageText.contains(AppConstants.USER_REGISTER_SUCCESS_MESSAGE)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		else {
			return false;
		}
		
}
}