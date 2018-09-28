package com.csc.ss.web.element.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author pkapoor22
 *
 *         SETDATE(+/-/*Month,+/-/*Date,+/-/*year)
 * 
 */

public class IntelliSSetDate implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		String newDate = SETDATE(data);
		driver.manage().timeouts().implicitlyWait(400, TimeUnit.MILLISECONDS);
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(newDate
				.split("/")));
		System.out.println(aList.get(0) + "/" + aList.get(1) + "/"
				+ aList.get(2));
		element.clear();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		element.sendKeys(aList.get(0) + "/" + aList.get(1) + "/" + aList.get(2));
		element.sendKeys(Keys.TAB);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param data
	 */
	public static String SETDATE(String data) {

		Calendar cal = Calendar.getInstance();
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(data
				.split(",")));
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;

		for (int i = 0; i < aList.size(); i++) {

			char first = 0;
			try {
				first = aList.get(i).charAt(0);
			} catch (StringIndexOutOfBoundsException siobe) {
				System.out
						.println("Either of Year, Month or Day is missing in the req.");
			}
			String firstChar = String.valueOf(first);
			System.out.println(aList.get(i));

			if (i == 2) {

				if (firstChar.contains("+")) {
					System.out.println("Add Year");
					cal.add(Calendar.YEAR, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Sub Year");
					cal.add(Calendar.YEAR, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("*")) {
					System.out
							.println("User Wants to set specific Year" + "\n");
					int setYear = Integer.parseInt(aList.get(i).substring(1));
					System.out.println(setYear);
					cal.set(Calendar.YEAR, setYear);
					date = cal.getTime();

				}
			} else if (i == 0) {

				if (firstChar.contains("+")) {
					System.out.println("Add MONTH");
					cal.add(Calendar.MONTH, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Sub MONTH");
					cal.add(Calendar.MONTH, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("*")) {
					System.out.println("User Wants to set specific Month"
							+ "\n");
					cal.set(Calendar.MONTH,
							(Integer.parseInt(aList.get(i).substring(1)) - 1));
					date = cal.getTime();

				}
			} else if (i == 1) {

				if (firstChar.contains("+")) {
					System.out.println("Add DATE");
					cal.add(Calendar.DATE, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Sub DATE");
					cal.add(Calendar.DATE, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();
					System.out.println(date);

				} else if (firstChar.contains("*")) {
					System.out
							.println("User Wants to set specific Date" + "\n");
					int setDate = Integer.parseInt(aList.get(i).substring(1));
					System.out.println(setDate);
					cal.set(Calendar.DATE, setDate);
					date = cal.getTime();
				}
			}
		}
		String newDateAfterChange = null;
		newDateAfterChange = format1.format(date);
		System.out.println(newDateAfterChange);
		return newDateAfterChange;
	}
}