package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSSwitchToChildWindow implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		System.out.println(driver.getWindowHandle() + "===========");

		// Switching from parent window to child window
		for (String Child_Window : driver.getWindowHandles()) {
			driver.switchTo().window(Child_Window);
			System.out.println("******************************"
					+ driver.switchTo().window(Child_Window));
		}
	}
}