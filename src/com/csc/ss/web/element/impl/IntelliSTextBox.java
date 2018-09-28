package com.csc.ss.web.element.impl;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSTextBox implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		Robot robot;
		element.click();
		sleepTime(4000);
		try {
			robot = new Robot();
			robot.setAutoDelay(450);
			robot.keyPress(KeyEvent.VK_CONTROL);
			System.out.println("Key CONTROL Pressed");
			robot.setAutoDelay(450);
			robot.keyPress(KeyEvent.VK_A);
			System.out.println("Key A Pressed");
			robot.setAutoDelay(450);
			robot.keyPress(KeyEvent.VK_DELETE);
			System.out.println("Key DELETE Pressed");
			sleepTime(4000);
			robot.keyRelease(KeyEvent.VK_A);
			System.out.println("Key A Released");
			robot.setAutoDelay(450);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("Key CONTROL Released");
			robot.setAutoDelay(450);
			robot.keyRelease(KeyEvent.VK_DELETE);
			System.out.println("Key DELETE Released");
			sleepTime(4000);
		} catch (AWTException e) {
			e.printStackTrace();
		}

		element.sendKeys(data);
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