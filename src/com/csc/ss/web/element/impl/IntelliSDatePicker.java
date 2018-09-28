package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSDatePicker implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		System.out.println("Date being sended in date picker is :- " + data);
		element.sendKeys(data);
	}
}