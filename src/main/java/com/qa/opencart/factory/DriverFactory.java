package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameWorkException;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	public static String isHighlight;
	public static String isHeadless;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * This method is used to initialize the driver on the basis of given browser name
	 * @param prop.getProperty(null)
	 * @return it returns driver
	 */
	public WebDriver initDriver(Properties prop) {
		String browsername = prop.getProperty("browser");
		String URL = prop.getProperty("url");
		System.out.println("browser name is : " + browsername);
		
		isHighlight = prop.getProperty("highlight");
		
		System.out.println("isHighlight Value is" + isHighlight);
		
		OptionsManager optionsManager = new OptionsManager(prop);
		
		
		switch (browsername.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
			
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println(AppError.INVALID_BROWSER_MESG + browsername);
			throw new BrowserException(AppError.INVALID_BROWSER_MESG + browsername);
		}
		
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(URL);
		return getDriver();
	}
	
	/**
	 * This method is returning driver with thread local
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	//mvn clean install -Denv="qa"
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		
		String envName = System.getProperty("env");
		System.out.println("Running Test on env: " + envName);
		try {
		if (envName == null) {
			System.out.println("env is null ------hence running test on QA env");
			ip = new FileInputStream(".\\src\\test\\resource\\config\\qa.config.properties");
			} 
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
					ip = new FileInputStream(".\\src\\test\\resource\\config\\qa.config.properties");
				break;
			case "dev":
					ip = new FileInputStream(".\\src\\test\\resource\\config\\dev.config.properties");
				break;
			case "uat":
					ip = new FileInputStream(".src\\resources\\config\\uat.config.properties");
				break;
			case "stage":
					ip = new FileInputStream(".src\\resources\\config\\stage.config.properties");
				break;
			case "prod":
					ip = new FileInputStream(".src\\resources\\config\\config.properties");
				break;
			default:
				System.out.println("Please pass the right env name..." + envName);
				throw new FrameWorkException("INVALID ENV NAME");
			}
		}
		prop.load(ip);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);//temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}
