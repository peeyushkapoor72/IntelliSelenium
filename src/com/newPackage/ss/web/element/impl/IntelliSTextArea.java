package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSTextArea implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		element.click();
		// element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		// element.sendKeys(Keys.DELETE);
		element.sendKeys(data);
	}
}