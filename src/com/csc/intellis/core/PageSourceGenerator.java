package com.csc.intellis.core;

import org.openqa.selenium.WebDriver;

public class PageSourceGenerator {

	WebDriver driver = null;

	public PageSourceGenerator(WebDriver driver) {
		this.driver = driver;
	}

	public String generate(boolean winReferesh) {
		System.out.println("Refresh");

		if (winReferesh) {
			driver.navigate().refresh();
		}
		return driver.getPageSource();
	}
}