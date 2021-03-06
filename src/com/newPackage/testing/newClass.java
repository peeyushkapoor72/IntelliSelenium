package com.csc.testing;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.thoughtworks.selenium.SeleniumException;

public class newClass {

	static WebDriver driver;
	static String driverPath = "C:\\T420\\C_Drive\\Share_Folder_Peeyush\\ER\\IEDriverServer_Win32_3.4.0\\IEDriverServer.exe";

	public static void main(String[] args) throws InterruptedException {

		// driver = inDriver(driverPath);

		System.setProperty(
				"webdriver.gecko.driver",
				"C:\\T420\\C_Drive\\Share_Folder_Peeyush\\ER\\geckodriver-v0.21.0-win32\\geckodriver.exe");
		driver = new FirefoxDriver();

		// Maximize the browser
		driver.manage().window().maximize();

		// Implicit wait for 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// To launch softwaretestingmaterial.com
		driver.get("https://www.softwaretestingmaterial.com");

		// Wait for 5 seconds
		Thread.sleep(30000);

		// Used tagName method to collect the list of items with tagName "a"
		//
		// findElements - to find all the elements with in the current page. It
		//
		// returns a list of all webelements or an empty list if nothing matches
		List<WebElement> links = driver.findElements(By.tagName("a"));

		// To print the total number of links
		System.out.println("Total links are " + links.size());

		// used for loop to
		for (int i = 0; i < links.size(); i++) {
			WebElement element = links.get(i);
			// By using "href" attribute, we could get the url of the requried
			// link
			String url = element.getAttribute("href");
			// calling verifyLink() method here. Passing the parameter as url
			// which we collected in the above link
			// See the detailed functionality of the verifyLink(url) method
			// below
			verifyLink(url);
		}
	}

	public static WebDriver inDriver(String driverPath) {

		WebDriver driver = null;

		try {
			System.setProperty("webdriver.ie.driver", driverPath);
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			// capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
			// capabilities.setCapability(
			// InternetExplorerDriver.INITIAL_BROWSER_URL,
			// "www.google.com");
			// capabilities.setCapability(
			// InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			// capabilities.setCapability(
			// InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new SeleniumException("Error initialising IE Driver!!");
		}
		return driver;
	}

	// The below function verifyLink(String urlLink) verifies any broken links
	// and return the server status.
	public static void verifyLink(String urlLink) {
		// Sometimes we may face exception "java.net.MalformedURLException".
		// Keep the code in try catch block to continue the broken link analysis
		try {
			// Use URL Class - Create object of the URL Class and pass the
			// urlLink as parameter
			URL link = new URL(urlLink);
			// Create a connection using URL object (i.e., link)
			HttpURLConnection httpConn = (HttpURLConnection) link
					.openConnection();
			// Set the timeout for 2 seconds
			httpConn.setConnectTimeout(2000);
			// connect using connect method
			httpConn.connect();
			// use getResponseCode() to get the response code.
			if (httpConn.getResponseCode() == 200) {
				System.out.println(urlLink + " - "
						+ httpConn.getResponseMessage());
			}
			if (httpConn.getResponseCode() == 404) {
				System.out.println(urlLink + " - "
						+ httpConn.getResponseMessage());
			}
		}
		// getResponseCode method returns = IOException - if an error occurred
		// connecting to the server.
		catch (Exception e) {
			// e.printStackTrace();
		}
	}
}