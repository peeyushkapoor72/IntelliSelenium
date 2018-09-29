package com.csc.ss.web.element.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSLink implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		String newXpath = GetWebElementXpath(element);
		System.out.println("Xpath :- " + newXpath);
		element = driver.findElement(By.xpath(newXpath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
		// element.click();
	}

	/**
	 * @return
	 * @param webElement
	 * @throws AssertionError
	 */
	public static String GetWebElementXpath(WebElement webElement)
			throws AssertionError {
		if ((webElement instanceof WebElement)) {
			Object o = webElement;
			String text = o.toString();
			text = text.substring(text.indexOf("xpath: ") + 7,
					text.length() - 1);
			return text;
		} else {
			System.out
					.println("Argument is not an WebElement, his actual class is:"
							+ webElement.getClass());
		}
		return "";
	}
}