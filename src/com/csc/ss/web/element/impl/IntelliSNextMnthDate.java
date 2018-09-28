package com.csc.ss.web.element.impl;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSNextMnthDate implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		element.click();
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.sendKeys(Keys.DELETE);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		calendar.set(Calendar.DATE,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		String nextMonthFirstDay = df.format(calendar.getTime());
		// System.out.println(nextMonthFirstDay);
		element.sendKeys(nextMonthFirstDay);
	}
}