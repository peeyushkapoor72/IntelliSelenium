package com.csc.ss.web.element.impl;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class IntelliSCopy implements IIntelliSWebElement {

	/**
	 * @param data
	 * @param driver
	 * @param element
	 */
	public void process(WebDriver driver, WebElement element, String data) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String strName = element.getText();

		Actions act = new Actions(driver);
		act.moveToElement(element).doubleClick().build().perform();
		// catch here is double click on the text will by default select the
		// text
		// now apply copy command

		element.sendKeys(Keys.chord(Keys.CONTROL, "c"));

		System.out.println(strName);
		System.out.println(Keys.chord(Keys.CONTROL, "c"));

		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		// element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		// element.sendKeys(Keys.chord(Keys.CONTROL, "c"));
	}
}