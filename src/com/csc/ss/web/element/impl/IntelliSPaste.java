package com.csc.ss.web.element.impl;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSPaste implements IIntelliSWebElement {

	/**
	 * @param data
	 * @param driver
	 * @param element
	 */
	public void process(WebDriver driver, WebElement element, String data) {

		sleepTime(4000);
		element.click();
		sleepTime(4000);

		Robot robot;

		try {
			robot = new Robot();
			robot.setAutoDelay(500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			System.out.println("Key CONTROL Pressed");
			// robot.keyPress(KeyEvent.VK_SHIFT);
			// System.out.println("Key SHIFT Pressed");
			robot.setAutoDelay(500);
			robot.keyPress(KeyEvent.VK_V);
			System.out.println("Key V Pressed");
			// robot.keyPress(KeyEvent.VK_INSERT);
			// System.out.println("Key INSERT Pressed");
			sleepTime(4000);
			System.out
					.println("================================================");
			System.out
					.println("================================================");
			System.out
					.println("================================================");
			System.out
					.println("================================================");
			robot.setAutoDelay(500);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("Key CONTROL Released");
			// robot.keyRelease(KeyEvent.VK_SHIFT);
			// System.out.println("Key SHIFT Released");
			robot.setAutoDelay(500);
			robot.keyRelease(KeyEvent.VK_V);
			System.out.println("Key V Released");
			// robot.keyRelease(KeyEvent.VK_INSERT);
			// System.out.println("Key INSERT Released");
			sleepTime(4000);
		} catch (AWTException e) {
			e.printStackTrace();
		}

		System.out.println("done");
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