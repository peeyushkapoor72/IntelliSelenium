package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.csc.intellis.exception.IntelliSException;

public class IntelliSVerifyDisabled implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		boolean bool = element.isEnabled();
		if (bool) {
			System.out.print("\nElement is  still enabled. throwing error.");
			try {
				throw new IntelliSException(
						"Element is  still enabled. throwing error");
			} catch (IntelliSException e) {
				e.printStackTrace();
			}
		} else {
			System.out
					.print("\nElement is  Disabled. Pls Proceed and take your action.");
		}
	}
}