package com.csc.ss.web.element.impl;

import java.util.Date;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSTodayDate implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		Robot robot;
		element.click();
		// element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		// element.sendKeys(Keys.DELETE);
		try {
			robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_A);
			sleepTime(2000);
			robot.keyRelease(KeyEvent.VK_A);
			System.out.println("Key A Released");
			robot.setAutoDelay(250);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			System.out.println("TAB Key Released");
		} catch (AWTException e) {
			e.printStackTrace();
		}
		sleepTime(2000);
		Date date = new Date();
		String DATE_FORMAT = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		System.out.println("Today is " + sdf.format(date));
		// System.out.println("this is start date" + startDate);
		element.sendKeys(sdf.format(date));
		sleepTime(2000);
		System.out.println("todays date enetered.");
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