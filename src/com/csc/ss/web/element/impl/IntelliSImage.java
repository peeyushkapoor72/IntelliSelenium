package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSImage implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		System.out.println("Entering IMAGE");
		// ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
		// element);
		// System.out.println("Entering IMAGE");
		element.click();
		System.out.println("Clicked Image");
	}
}