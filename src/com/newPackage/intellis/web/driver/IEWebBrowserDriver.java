package com.csc.intellis.web.driver;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.exception.IntelliSException;
import com.thoughtworks.selenium.SeleniumException;

public class IEWebBrowserDriver implements IWebBrowserDriver {

	public IEWebBrowserDriver() {
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath) {

		WebDriver driver = null;

		try {
			HashMap<String, String> configMap = ConfigLoader.getInstance()
					.loadConfig();
			System.out.println(configMap.get("TraceFilePath") + "\\"
					+ "browserTrace.txt");
			System.setProperty("webdriver.ie.driver.loglevel", "TRACE");
			System.setProperty("webdriver.ie.driver.logfile",
					configMap.get("TraceFilePath") + "\\" + "browserTrace.txt");
			System.setProperty("webdriver.ie.driver", driverPath);
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			// capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
			capabilities.setCapability(
					InternetExplorerDriver.INITIAL_BROWSER_URL,
					"www.google.com");
			capabilities.setCapability(
					InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			capabilities.setCapability(
					InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS,
					false);
			capabilities.setCapability(
					InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, false);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities
					.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
			capabilities.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability("browserstack.ie.enablePopups", "true");
			capabilities.setCapability(
					InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			driver = new InternetExplorerDriver(capabilities);
			// driver.manage().deleteAllCookies();
			driver.navigate().refresh();
			driver.manage().window().maximize();
		} catch (IntelliSException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising IE Driver!!");
		}
		return driver;
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath, String Node) {
		WebDriver driver = null;

		try {
			System.setProperty("webdriver.ie.driver", driverPath);
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			// capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
			// "www.google.com");
			// capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,
			// true);
			capabilities.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			// capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,
			// true);
			// capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS,
			// false);
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
			// driver = new InternetExplorerDriver(capabilities);
			driver = new RemoteWebDriver(new URL(Node), capabilities);
			driver.navigate().refresh();
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising IE Driver!!");
		}
		return driver;
	}
}