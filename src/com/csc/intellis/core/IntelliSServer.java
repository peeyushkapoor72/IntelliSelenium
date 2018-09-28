package com.csc.intellis.core;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.core.pattern.PatternLoader;
import com.csc.intellis.exception.IntelliSException;
import com.csc.intellis.web.driver.IWebBrowserDriver;
import com.csc.intellis.web.driver.WebBrowserDriverFactory;

public class IntelliSServer {
	final static Logger logger = Logger.getLogger(IntelliSServer.class);

	public IntelliSServer() {
	}

	/**
	 * @param bean
	 * @param rowNum
	 * @param dataValue
	 */
	public static boolean spawn(Map<String, Map<String, String>> dataValue,
			Map<String, Map<String, String>> dataValueMap,
			ExecutorParameterBean bean, String strSheetName, WebDriver driver,
			String fileName, HSSFWorkbook workbook, int rowNum) {

		boolean bool = false;
		try {
			HashMap<String, String> configMap = ConfigLoader.getInstance()
					.loadConfig();
			String drvType = configMap.get(IntelliSConstants.DRV_TYPE);
			String url = configMap.get(IntelliSConstants.TEST_URL);
			String sleepfactor = configMap.get("SleepFactor");
			Integer SleepFac;
			if (NumberUtils.isNumber(sleepfactor)) {
				SleepFac = Integer.parseInt(sleepfactor);
				logger.info(SleepFac);
			} else {
				SleepFac = 1;
			}

			boolean ie = false;
			boolean chrome = false;
			boolean firefox = false;
			boolean HTMLUnit = false;
			IWebBrowserDriver webDriver = null;

			HashMap<String, String> patternMap = PatternLoader.getInstance()
					.loadPattern();
			// IntelliSExecutor executor_Verification = new
			// IntelliSExecutor(bean);
			IntelliSExecutor_New executor_Verification = new IntelliSExecutor_New(
					bean);
			bean.setConfigMap(configMap);
			bean.setPatternMap(patternMap);

			String Node = "http://"
					+ InetAddress.getLocalHost().getHostAddress() + ":"
					+ configMap.get(IntelliSConstants.HUBPORT) + "/wd/hub";

			if (isAlive(driver)) {
				logger.info("Browser:-  " + drvType
						+ "   already up and running ");
			} else {
				if (drvType != null
						&& drvType.equals(WebBrowserDriverFactory.DRV_CHROME)) {
					webDriver = WebBrowserDriverFactory.getInstance()
							.getDriver(WebBrowserDriverFactory.DRV_CHROME);
					chrome = true;
				} else if (drvType != null
						&& drvType.equals(WebBrowserDriverFactory.DRV_IE)) {
					webDriver = WebBrowserDriverFactory.getInstance()
							.getDriver(WebBrowserDriverFactory.DRV_IE);
					ie = true;
				} else if (drvType != null
						&& drvType.equals(WebBrowserDriverFactory.DRV_Firefox)) {
					webDriver = WebBrowserDriverFactory.getInstance()
							.getDriver(WebBrowserDriverFactory.DRV_Firefox);
					firefox = true;
				} else if (drvType != null
						&& drvType.equals(WebBrowserDriverFactory.DRV_headLess)) {
					webDriver = WebBrowserDriverFactory.getInstance()
							.getDriver(WebBrowserDriverFactory.DRV_headLess);
					HTMLUnit = true;

				} else {
					throw new IntelliSException("500",
							"Error Intialising driver for Driver type :"
									+ drvType);
				}
				// Checking Remote Connection
				if (!(bean.getConfigMap().get(IntelliSConstants.REMOTECONNECT)
						.equals("Yes"))) {
					// Initialize Browser for execution
					if (chrome) {
						driver = webDriver.initDriver(configMap
								.get("ChromeDriver"));
						Thread.sleep(2000);
						driver.get(url);
						Thread.sleep(4000);
						logger.info("Opening Chrome For Execution");
					} else if (ie) {
						driver = webDriver
								.initDriver(configMap.get("IEDriver"));
						logger.info("Opening IE For Execution");
						Thread.sleep(2000);
						driver.get(url);
						Thread.sleep(4000);
					} else if (firefox) {
						driver = webDriver.initDriver(configMap
								.get("FirefoxDriver"));
						logger.info("Opening Firefox For Execution");
						Thread.sleep(2000);
						driver.get(url);
						Thread.sleep(4000);
					} else if (HTMLUnit) {
						driver = new HtmlUnitDriver(true);
						logger.info("HeadLess Execution Requiered.");
						Thread.sleep(2000);
						driver.get(url);
						Thread.sleep(4000);
						System.out.println("URL Got Loaded");
					}
				} else {
					if (chrome) {
						driver = webDriver.initDriver(
								configMap.get("ChromeDriver"), Node);
						driver.get(url);
						Thread.sleep(4000);
						logger.info("Opening Chrome For Execution");
					} else if (ie) {
						driver = webDriver.initDriver(
								configMap.get("IEDriver"), Node);
						logger.info("Opening IE For Execution");
						driver.get(url);
						Thread.sleep(4000);
					} else if (firefox) {
						driver = webDriver.initDriver(
								configMap.get("FirefoxDriver"), Node);
						logger.info("Opening Firefox For Execution");
						driver.get(url);
						Thread.sleep(4000);
					} else if (HTMLUnit) {
						driver = new HtmlUnitDriver(true);
						logger.info("HeadLess Execution Requiered.");
						driver.get(url);
						Thread.sleep(4000);
						System.out.println("URL Got Loaded");
					}
				}
			}

			bean.setDriver(driver);
			logger.info(bean.getData());
			bool = executor_Verification.execute(dataValue, dataValueMap,
					strSheetName, fileName, workbook, rowNum, SleepFac);

		} catch (IntelliSException is) {
			is.printStackTrace();
			logger.error(is);
		} catch (Exception e) {
			logger.info("Exception thrown :" + "\n" + e);
			logger.error(e);
		}
		return bool;
	}

	/**
	 * @return
	 * @param driver
	 */
	public static Boolean isAlive(WebDriver driver) {
		try {
			driver.getCurrentUrl();
			return true;
		} catch (Exception ex) {
			// logger.error(ex);
			return false;
		}
	}
}