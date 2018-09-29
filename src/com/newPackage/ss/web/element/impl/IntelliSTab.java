package com.csc.ss.web.element.impl;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSTab implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		sleepTime(3000);
		Robot robot;
		try {
			robot = new Robot();
			robot.setAutoDelay(500);
			robot.keyPress(KeyEvent.VK_TAB);
			sleepTime(3000);
			System.out.println("TAB Key Pressed");
			sleepTime(3000);
			robot.setAutoDelay(500);
			robot.keyRelease(KeyEvent.VK_TAB);
			System.out.println("TAB Key Released");
		} catch (AWTException e) {
			e.printStackTrace();
		}
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