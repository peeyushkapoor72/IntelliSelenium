package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSCheckUncheckCheckBox implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		boolean isChecked = element.isSelected();
		if (isChecked) {
			System.out.println("The Check Box in question is Checked.");
		} else {
			System.out.println("The Check Box in question is unchecked.");
		}
	}
}