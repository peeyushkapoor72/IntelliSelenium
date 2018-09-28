package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSJLink implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		element.click();
		System.out.println("Done");
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