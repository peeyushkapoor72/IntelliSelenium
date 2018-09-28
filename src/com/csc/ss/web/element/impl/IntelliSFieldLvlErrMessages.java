package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.InputMismatchException;

public class IntelliSFieldLvlErrMessages implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		String getText = element.getText();
		if (data == getText) {
			System.out
					.println("Yes the error meesage is the same as mentioned in the test data sheet");
		} else {
			System.out
					.println("Nope the error message seems to be diffrent from the one mentioned in the test data sheet");
			throw new InputMismatchException();
		}
	}
}