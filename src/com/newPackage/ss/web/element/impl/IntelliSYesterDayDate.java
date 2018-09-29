package com.csc.ss.web.element.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSYesterDayDate implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		// Getting the date we want to enter
		data = getYesterdayDate();

		// Clicking the element and clearing it before we enter any value
		element.click();
		element.clear();

		// Waiting for 30 seconds
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);

		// Entering the required value in the specified element
		element.sendKeys(String.valueOf(data));

		// Waiting for 30 seconds
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);

		// Clicking on TAB after entering value
		element.sendKeys(Keys.TAB);

	}

	/**
	 * @return yesterdaysDate
	 */
	public static String getYesterdayDate() {

		Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L
				* 60L * 24L);
		String yesterdaysDate = new SimpleDateFormat("MM/dd/yyyy")
				.format(yesterday);
		System.out.println(yesterdaysDate);

		return yesterdaysDate;
	}

}