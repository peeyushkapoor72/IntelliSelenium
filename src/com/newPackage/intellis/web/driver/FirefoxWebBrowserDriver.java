package com.csc.intellis.web.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import com.thoughtworks.selenium.SeleniumException;

public class FirefoxWebBrowserDriver implements IWebBrowserDriver {

	public FirefoxWebBrowserDriver() {
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath) {
		WebDriver driver = null;

		try {

			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile = profile.getProfile("WebAutomationUser");
			// driver = new FirefoxDriver();
			// driver.manage().window().maximize();
			driver = new FirefoxDriver(myprofile);
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising Firefox Driver!!");
		}
		return driver;
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath, String Node) {
		WebDriver driver = null;

		try {

			ProfilesIni profile = new ProfilesIni();
			FirefoxProfile myprofile = profile.getProfile("WebAutomationUser");
			// driver = new FirefoxDriver();
			// driver.manage().window().maximize();
			// driver = new RemoteWebDriver(new URL(Node), capabilities);
			driver = new FirefoxDriver(myprofile);
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising Firefox Driver!!");
		}
		return driver;
	}

}