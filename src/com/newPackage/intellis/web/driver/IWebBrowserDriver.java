package com.csc.intellis.web.driver;

import org.openqa.selenium.WebDriver;

public interface IWebBrowserDriver {

	public WebDriver initDriver(String driverPath);

	public WebDriver initDriver(String driverPath, String Node);
}