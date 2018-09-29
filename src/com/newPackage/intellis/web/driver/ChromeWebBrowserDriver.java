package com.csc.intellis.web.driver;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.SeleniumException;

public class ChromeWebBrowserDriver implements IWebBrowserDriver {

	public ChromeWebBrowserDriver() {
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath) {
		WebDriver driver = null;
		try {
			System.setProperty("webdriver.chrome.driver", driverPath);
			DesiredCapabilities Capability = DesiredCapabilities.chrome();
			Capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising Chrome Driver!!");
		}
		return driver;
	}

	public WebDriver initDriver(String driverPath, String Node) {
		WebDriver driver = null;
		try {
			System.setProperty("webdriver.chrome.driver", driverPath);
			DesiredCapabilities Capability = DesiredCapabilities.chrome();
			Capability.setPlatform(org.openqa.selenium.Platform.ANY);
			// driver = new ChromeDriver();
			driver = new RemoteWebDriver(new URL(Node), Capability);
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising Chrome Driver!!");
		}
		return driver;
	}
}