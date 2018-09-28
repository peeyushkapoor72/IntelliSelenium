package com.csc.ss.web.element.impl;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSCurrntMnthDate implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		element.click();
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element.sendKeys(Keys.DELETE);

		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_MONTH, 1);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String startDate = df.format(date.getTime());
		System.out.println("this is start date" + startDate);
		element.sendKeys(startDate);
	}
}