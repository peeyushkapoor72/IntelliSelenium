package com.csc.ss.web.element.impl;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSReturnKey implements IIntelliSWebElement {

	/**
	 * @param data
	 * @param driver
	 * @param element
	 */
	public void process(WebDriver driver, WebElement element, String data) {

		sleepTime(4000);
		element.click();
		sleepTime(5000);

		Robot robot;
		// sleepTime(4000);
		// element.sendKeys(Keys.ENTER);
		// element.sendKeys(Keys.RETURN);
		// sleepTime(3000);

		try {
			robot = new Robot();
			robot.setAutoDelay(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.setAutoDelay(500);
			System.out.println("Return Key Pressed");
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.setAutoDelay(500);
			System.out.println("Return Key Released");
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		sleepTime(4000);
		System.out.println("Done");
	}

	/**
	 * @param milliseconds
	 */
	public static void sleepTime(Integer milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}