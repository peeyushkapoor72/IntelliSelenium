package com.csc.ss.web.element.impl;

import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSVerifyEnabled implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		boolean bool = element.isEnabled();
		if (bool) {
			System.out.print("\nElement is  still enabled.");
		} else {
			System.out.print("\nElement is  Disabled. throwing error.");
			throw new NoSuchElementException();
		}
	}
}