package com.csc.ss.web.element.impl;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSDeleteKey implements IIntelliSWebElement {

	/**
	 * @param data
	 * @param driver
	 * @param element
	 */
	public void process(WebDriver driver, WebElement element, String data) {
		element.sendKeys(Keys.DELETE);
	}
}