package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	//public static String isHeadless;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		//Boolean flag = Boolean.parseBoolean(prop.getProperty("headless"));
		//if (flag) {
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println(".......Running in headless.....");
			co.addArguments("--headless");
	}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))){
			co.addArguments("--incognito");
		}
		return co;
		}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))){
			System.out.println(".......Running in headless.....");
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))){
			System.out.println(".......Running in headless.....");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))){
			eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))){
			eo.addArguments("--inPrivate");
		}
		return eo;
	}
	
}
