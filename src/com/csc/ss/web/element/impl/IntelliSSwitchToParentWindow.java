package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSSwitchToParentWindow implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		// Storing parent window reference into a String Variable
		String Parent_Window = driver.getWindowHandle();

		// Switching back to Parent Window
		driver.switchTo().window(Parent_Window);
	}
}