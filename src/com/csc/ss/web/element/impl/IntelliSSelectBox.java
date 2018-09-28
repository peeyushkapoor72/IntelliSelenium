package com.csc.ss.web.element.impl;

import java.util.ArrayList;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public abstract class IntelliSSelectBox implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		WebElement newWebElement;
		String newXpath = GetWebElementXpath(element);
		System.out.println("NEW XPATH     :-" + newXpath + "*****");
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(data.split("\\r\\n|\\n|\\r")));
		for (int i = 0; i < aList.size(); i++) {

			if (newXpath.contains("id:")) {
				newWebElement = driver.findElement(By.id(newXpath.substring(96)));
				Select se = new Select(newWebElement);
				System.out.println("data after trim is :- " + data);
				// se.selectByVisibleText(data.trim());
				se.selectByVisibleText(aList.get(i).trim());
			} else {
				newWebElement = driver.findElement(By.xpath(newXpath));
				Select se = new Select(newWebElement);
				data = data.trim();
				System.out.println("data after trim is :- " + data);
				// se.selectByVisibleText(data.trim());
				se.selectByVisibleText(aList.get(i).trim());
			}
		} // For Loop Finished

		System.out.println("finished");
	}

	/**
	 * Method to get Xpath from WebElement
	 * 
	 * @return
	 * @param webElement
	 * @throws AssertionError
	 */
	public static String GetWebElementXpath(WebElement webElement) throws AssertionError {
		if ((webElement instanceof WebElement)) {
			Object o = webElement;
			String text = o.toString();
			text = text.substring(text.indexOf("xpath: ") + 7, text.length() - 1);
			System.out.println(text);
			return text;
		} else {
			System.out.println("Argument is not an WebElement, his actual class is:" + webElement.getClass());
		}
		return "";
	}
}