package com.csc.ss.web.element.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.InputMismatchException;

public class IntelliSToast implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		String toast = element.getText();
		System.out.println("======" + toast + "======");
		System.out.println("*********" + data + "*********");
		if (data.trim().equals(toast.trim())) {
			System.out.println("Macthes the text in TOAST Message");
		} else {
			System.out
					.println("Text in TOAST Message is diffrent from the one shown ");
			throw new InputMismatchException();
		}
	}
}