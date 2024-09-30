package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AcccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

import io.qameta.allure.Step;

public class BaseTest {
	
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	
	protected LoginPage loginpage;
	protected AcccountPage accpage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	protected SoftAssert softAssert;
	
	@Step("Setup with browser : {0} and init the propeties")
	@Parameters({"browser"})
	@BeforeTest
	public void setup(@Optional("chrome") String browserName) {
		df = new DriverFactory();
		prop = df.initProp();		

			//check if browser param is coming from testng.xml
			if(browserName!=null) {
				prop.setProperty("browser", browserName);
			}
		
		
		driver = df.initDriver(prop);
		loginpage = new LoginPage(driver);
		//softAssert = new SoftAssert();
	}
	
	@Step("close the browser")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}	
}
