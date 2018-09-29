package com.csc.ss.web.element.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class IntelliSVerifyToolTipText implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		// Use action class to mouse hover on Text box field
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
		WebElement toolTipElement = driver.findElement(By
				.className("tooltip-inner"));
		String toolTipText = toolTipElement.getText().trim();
		System.out.println("Tooltip message is 		" + toolTipText);
		System.out.println("Data    message is 		" + data);

		ArrayList<String> dataList = new ArrayList<String>(Arrays.asList(data
				.split("\\s+")));
		ArrayList<String> toolTipList = new ArrayList<String>(
				Arrays.asList(toolTipText.split("\\s+")));

		ArrayList<String> al3 = new ArrayList<String>();
		for (String temp : dataList)
			al3.add(toolTipList.contains(temp) ? "Yes" : "No");
		System.out.println(al3);

		if (data.trim().equals(toolTipText.trim())) {
			System.out.println("Text Macthes");
		} else {
			System.out.println("Text Not Present");
			throw new InputMismatchException();
		}
		System.out.println("done");
	}
}