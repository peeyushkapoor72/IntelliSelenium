package com.csc.intellis.web.driver;

import com.csc.intellis.exception.IntelliSException;

/**
 * Factory class for producing executor objects based on service types
 *
 */
public class WebBrowserDriverFactory {

	public static String DRV_IE = "IE";
	public static String DRV_CHROME = "Chrome";
	public static String DRV_Firefox = "Firefox";
	public static String DRV_headLess = "HtmlUnit";
	private static volatile WebBrowserDriverFactory instance = null;

	private WebBrowserDriverFactory() {
	}

	/**
	 * Method to get an instance of this class
	 * 
	 * @return
	 */
	public static WebBrowserDriverFactory getInstance() {
		if (instance == null) {
			synchronized (WebBrowserDriverFactory.class) {
				if (instance == null)
					instance = new WebBrowserDriverFactory();
			}
		}
		return instance;
	}

	/**
	 * Method to generate driver objects
	 * 
	 * @param srvType
	 * @return
	 * @throws MockSrvException
	 */
	public IWebBrowserDriver getDriver(String driverType)
			throws IntelliSException {
		if (driverType.equals(DRV_CHROME)) {
			return new ChromeWebBrowserDriver();
		} else if (driverType.equals(DRV_headLess)) {
			return new HtmlUnitWebBrowserDriver();
		} else if (driverType.equals(DRV_IE)) {
			return new IEWebBrowserDriver();
		} else if (driverType.equals(DRV_Firefox)) {
			return new FirefoxWebBrowserDriver();
		} else {
			throw new IntelliSException("500", "Error initialising driver !!");
		}
	}
}