package com.csc.ss.web.element.impl;

import java.util.Arrays;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.InputMismatchException;

public class IntelliSVerifyTextPresent implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		String verifyText = element.getText();
		System.out.println(verifyText);
		if (verifyText.isEmpty()) {
			verifyText = element.getAttribute("value");
		}
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(data
				.split("\\r\\n|\\n|\\r")));
		if (aList.size() < 2) {
			if (data.trim().equals(verifyText.trim())) {
				System.out.println("Text Matches");
				// System.out.println("About to ASSERT");
				// Assert.assertEquals(verifyText.trim(), data.trim());
			} else {
				System.out.println("Text Does Not Match");
				throw new InputMismatchException();
			}
		} else {
			System.out.println("For Multi-Check !");
			// ArrayList<String> ElementList = new ArrayList<String>(
			// Arrays.asList(verifyText.trim().split("\\s+")));
			ArrayList<String> ElementList = new ArrayList<String>(
					Arrays.asList(verifyText.trim().split("\\r?\\n")));
			// Arrays.asList(verifyText.trim()));
			for (String p : aList) {
				boolean flag = false;
				for (String k : ElementList) {
					System.out.println(k);
					System.out.println(p);
					if (k.equals(p)) {
						System.out.println(p + "found in List");
						flag = true;
					}
				}
				if (flag) {
					System.out.println(p + "\t" + "found on Screen");
				} else {
					System.err.println(p + "\t" + "Not Found");
					throw new InputMismatchException();
				}
			}
		}
	}
}