package com.csc.testing;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.thoughtworks.selenium.SeleniumException;

public class tab {

	static WebDriver driver;
	static String driverPath = "C:\\T420\\C_Drive\\Share_Folder_Peeyush\\ER\\IEDriverServer_Win32_3.4.0\\IEDriverServer.exe";

	public static void main(String[] args) {

		driver = initDriver(driverPath);
		// set implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("https://www.stackoverflow.com");
		// set implicit wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		openNewTab();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// Quitting Driver
		driver.quit();
	}

	public static void openNewTab() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		// driver.get("https://www.google.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// ArrayList<String> tabs = new ArrayList<String>(
		// driver.getWindowHandles());
		// driver.switchTo().window(tabs.get(1));
	}

	public static WebDriver initDriver(String driverPath) {
		WebDriver driver = null;

		try {
			System.setProperty("webdriver.ie.driver", driverPath);
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			capabilities.setCapability(
					InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS,
					false);
			capabilities.setCapability(
					InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities
					.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability(
					CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
					UnexpectedAlertBehaviour.ACCEPT);
			capabilities.setCapability("browserstack.ie.enablePopups", "true");
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			driver = new InternetExplorerDriver(capabilities);
			driver.navigate().refresh();
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising IE Driver!!");
		}
		return driver;
	}

}