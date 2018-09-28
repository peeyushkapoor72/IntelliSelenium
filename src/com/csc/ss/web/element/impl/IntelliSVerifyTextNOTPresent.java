package com.csc.ss.web.element.impl;

import java.util.InputMismatchException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IntelliSVerifyTextNOTPresent implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {
		System.out.println("Value Entered in data section is :-" + "\t" + data);
		String verifyText = element.getText();
		if (verifyText.isEmpty()) {
			verifyText = element.getAttribute("value");
			if (verifyText == null) {
				System.out.println("value turns out to be null here.........");
				verifyText = "";
			}
		}
		if (data.trim().equals(verifyText.trim())) {
			System.out.println("Text Macthes");
			throw new InputMismatchException();
		} else {
			System.out.println("Text Not Present");
		}
	}
}