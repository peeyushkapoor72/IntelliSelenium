package com.csc.intellis.web.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HtmlUnitWebBrowserDriver implements IWebBrowserDriver {

	public HtmlUnitWebBrowserDriver() {
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath) {
		WebDriver driver = new HtmlUnitDriver();
		return driver;
	}

	/**
	 * @param driverPath
	 */
	public WebDriver initDriver(String driverPath, String Node) {
		WebDriver driver = new HtmlUnitDriver();
		return driver;
	}
}