package com.csc.ss.web.element.impl;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSScroll implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		element.sendKeys(Keys.PAGE_DOWN);
	}
}