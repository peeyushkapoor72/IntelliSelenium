package com.csc.ss.web.element.impl;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSEscapeKey implements IIntelliSWebElement {

	/**
	 * @param data
	 * @param driver
	 * @param element
	 */
	public void process(WebDriver driver, WebElement element, String data) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		element.sendKeys(Keys.ESCAPE);
		System.out.println("adsas");
	}
}