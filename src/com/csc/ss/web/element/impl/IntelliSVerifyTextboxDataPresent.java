package com.csc.ss.web.element.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSVerifyTextboxDataPresent implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		String strXpath = GetWebElementXpath(element);
		WebElement webEle = driver.findElement(By.xpath(strXpath));
		String strGetData = webEle.getText().trim();
		System.out.println(strGetData.trim());

		if (data.trim().equals(strGetData.trim())) {
			System.out.println("Text Matches");
		} else {
			System.out.println("Text Does Not Match");
		}
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