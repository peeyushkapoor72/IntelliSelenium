package com.csc.ss.web.element.impl;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class IntelliSDrag implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		// Configure the action
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.CONTROL).click(element);
	}
}