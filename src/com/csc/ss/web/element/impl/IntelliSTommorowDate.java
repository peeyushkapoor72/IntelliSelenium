package com.csc.ss.web.element.impl;

import java.util.Date;
import java.util.Calendar;
import org.openqa.selenium.Keys;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSTommorowDate implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		// Getting the date we want to enter
		data = getTommorowsDate();

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
	 * @return tomorrowsDate
	 */
	public static String getTommorowsDate() {

		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.DATE, 1);
		dt = calendar.getTime();
		String tomorrowsDate = new SimpleDateFormat("MM/dd/yyyy").format(dt);
		System.out.println(tomorrowsDate);

		return tomorrowsDate;
	}
}