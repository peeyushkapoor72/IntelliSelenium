package com.csc.intellis.core;

import org.openqa.selenium.By;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import com.csc.intellis.constants.ElementType;
import org.openqa.selenium.JavascriptExecutor;
import com.csc.intellis.exception.IntelliSException;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.csc.ss.web.element.impl.WebElementFactory;
import com.csc.ss.web.element.impl.IIntelliSWebElement;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WebElementProcessor {

	private ExecutorParameterBean bean = null;
	final static Logger logger = Logger.getLogger(WebElementProcessor.class);

	/**
	 * @param bean
	 */
	public WebElementProcessor(ExecutorParameterBean bean) {
		this.bean = bean;
	}

	/**
	 * @param HtmlId
	 * @param HtmlName
	 * @param CSSPath
	 * @param labelName
	 * @param HtmlXpath
	 * @throws IntelliSException
	 */
	public void process(String labelName, String HtmlName, String CSSPath,
			String HtmlId, String HtmlXpath, Integer Sleepfactor)
			throws IntelliSException {

		WebElement seleniumElem = null;
		String data = bean.getData();

		if (labelName != "") {

			if (!bean.getType().equals(ElementType.ALERT.name())) {
				isPageLoaded(bean.getDriver(), Sleepfactor);
				logger.info(data + "                    ++" + bean.getType());
				if (bean.getType().equals(ElementType.ELEMENTNOTPRESENT.name())) {
					try {
						seleniumElem = bean.getDriver().findElement(
								By.xpath(bean.getXpath()));
						logger.info(seleniumElem + "***");
						CheckElementPresent(seleniumElem, Sleepfactor);
					} catch (IntelliSException e) {
						throw new IntelliSException("", e);
					} catch (Exception e) {
						logger.info("Element Not Found on Screen ! ");
					}
				} else {
					seleniumElem = bean.getDriver().findElement(
							By.xpath(bean.getXpath()));
					logger.info(seleniumElem + "***");
					IIntelliSWebElement element = WebElementFactory
							.getInstance().getElement(bean.getType());
					isElementVisible(bean.getDriver(), seleniumElem,
							Sleepfactor);
					element.process(bean.getDriver(), seleniumElem, data);
				}
			}
		} else if (HtmlName != "") {

			if (!bean.getType().equals(ElementType.ALERT.name())) {
				isPageLoaded(bean.getDriver(), Sleepfactor);
				logger.info(data + "                    ++");
				if (bean.getType().equals(ElementType.ELEMENTNOTPRESENT.name())) {
					try {
						seleniumElem = bean.getDriver().findElement(
								By.name(HtmlName));
						logger.info(seleniumElem + "***");
						CheckElementPresent(seleniumElem, Sleepfactor);
					} catch (IntelliSException e) {
						throw new IntelliSException("", e);
					} catch (Exception e) {
						logger.info("Element Not Found on Screen !");
					}
				} else {
					seleniumElem = bean.getDriver().findElement(
							By.name(HtmlName));
					logger.info(seleniumElem + "***");
					IIntelliSWebElement element = WebElementFactory
							.getInstance().getElement(bean.getType());
					isElementVisible(bean.getDriver(), seleniumElem,
							Sleepfactor);
					element.process(bean.getDriver(), seleniumElem, data);
				}
			}
		} else if (CSSPath != "") {

			if (!bean.getType().equals(ElementType.ALERT.name())) {
				isPageLoaded(bean.getDriver(), Sleepfactor);
				logger.info(data + "                    ++");
				if (bean.getType().equals(ElementType.ELEMENTNOTPRESENT.name())) {
					try {
						seleniumElem = bean.getDriver().findElement(
								By.cssSelector(CSSPath));
						logger.info(seleniumElem + "***");
						CheckElementPresent(seleniumElem, Sleepfactor);
					} catch (IntelliSException e) {
						throw new IntelliSException("", e);
					} catch (Exception e) {
						logger.info("Element Not Found on Screen !");
					}
				} else {
					seleniumElem = bean.getDriver().findElement(
							By.cssSelector(CSSPath));
					logger.info(seleniumElem + "***");
					IIntelliSWebElement element = WebElementFactory
							.getInstance().getElement(bean.getType());
					isElementVisible(bean.getDriver(), seleniumElem,
							Sleepfactor);
					element.process(bean.getDriver(), seleniumElem, data);
				}
			}
		} else if (HtmlId != "") {

			if (!bean.getType().equals(ElementType.ALERT.name())) {
				isPageLoaded(bean.getDriver(), Sleepfactor);
				logger.info(data + "                    ++");
				if (bean.getType().equals(ElementType.ELEMENTNOTPRESENT.name())) {
					try {
						seleniumElem = bean.getDriver().findElement(
								By.id(HtmlId));
						logger.info(seleniumElem + "***");
						CheckElementPresent(seleniumElem, Sleepfactor);
					} catch (IntelliSException e) {
						throw new IntelliSException("", e);
					} catch (Exception e) {
						logger.info("Element Not Found on Screen !");
					}
				} else {
					seleniumElem = bean.getDriver().findElement(By.id(HtmlId));
					logger.info(seleniumElem + "***");
					IIntelliSWebElement element = WebElementFactory
							.getInstance().getElement(bean.getType());
					isElementVisible(bean.getDriver(), seleniumElem,
							Sleepfactor);
					element.process(bean.getDriver(), seleniumElem, data);
				}
			}
		} else if (HtmlXpath != "") {

			if (!bean.getType().equals(ElementType.ALERT.name())) {
				isPageLoaded(bean.getDriver(), Sleepfactor);

				logger.info(data + "                    ++");
				if (bean.getType().equals(ElementType.ELEMENTNOTPRESENT.name())) {
					try {
						seleniumElem = bean.getDriver().findElement(
								By.xpath(HtmlXpath));
						logger.info(seleniumElem + "***");
						CheckElementPresent(seleniumElem, Sleepfactor);
					} catch (IntelliSException e) {
						throw new IntelliSException("", e);
					} catch (Exception e) {
						logger.info("Element Not Found on Screen !");
					}
				} else {
					seleniumElem = bean.getDriver().findElement(
							By.xpath(HtmlXpath));
					logger.info(seleniumElem + "***");
					IIntelliSWebElement element = WebElementFactory
							.getInstance().getElement(bean.getType());
					isElementVisible(bean.getDriver(), seleniumElem,
							Sleepfactor);
					element.process(bean.getDriver(), seleniumElem, data);
				}
			}
		}
	}

	/**
	 * @param element
	 * @param Sleepfactor
	 * @throws IntelliSException
	 */
	public void CheckElementPresent(WebElement element, Integer Sleepfactor)
			throws IntelliSException {
		if (bean.getType().contains("ELEMENTNOTPRESENT")) {
			logger.info("Checking Element is Present or Not !");
			WebDriverWait wait = new WebDriverWait(bean.getDriver(),
					Sleepfactor.intValue() * 60);
			try {
				wait.until(ExpectedConditions.visibilityOf(element));
				throw new IntelliSException("", "ElementFoundException");
			} catch (TimeoutException e) {
				logger.info("Element Not Found on Screen ! ");
			}
		}
	}

	/**
	 * @param driver
	 * @param Sleepfactor
	 */
	public void isPageLoaded(WebDriver driver, Integer Sleepfactor) {
		System.out.println("=============isPageLoaded==============");
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(bean.getDriver(),
				(Sleepfactor * 60));
		wait.until(expectation);
	}

	/**
	 * @param driver
	 * @param element
	 * @param Sleepfactor
	 */
	public void isElementVisible(WebDriver driver, WebElement element,
			Integer Sleepfactor) {
		System.out.println("=============isElementVisible==============");
		WebDriverWait wait = new WebDriverWait(bean.getDriver(),
				(Sleepfactor * 60));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}