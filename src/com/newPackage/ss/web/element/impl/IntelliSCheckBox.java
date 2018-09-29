package com.csc.ss.web.element.impl;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSCheckBox implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		// element.click();
	}
}