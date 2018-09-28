package com.csc.ss.web.element.impl;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IntelliSJSAlert implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		boolean presentFlag = false;
		Alert jAlert = null;
		// Actions action = new Actions(driver);
		System.out
				.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			jAlert = wait.until(ExpectedConditions.alertIsPresent());
			System.out
					.println("============================================================================");
			jAlert = driver.switchTo().alert();
			presentFlag = true;
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
		}
		if (presentFlag) {
			if (data.equals("OK")) {
				System.out
						.println("Got the alert, Clicking OK Button in alert");
				jAlert.accept();
				// action.sendKeys(Keys.ESCAPE);
			} else {
				System.out
						.println("Could not locate OK Button, So dismissing the alert");
				jAlert.dismiss();
			}
		} else {
			System.out.println("Couldn't switch!");
		}
	}

	// public void process(WebDriver driver, WebElement element, String data) {
	//
	// WebDriverWait wait = new WebDriverWait(driver, 2);
	// wait.until(ExpectedConditions.alertIsPresent());
	// Alert jAlert = driver.switchTo().alert();
	// System.out.println("text present in the alert is :-  "
	// + driver.switchTo().alert().getText());
	//
	// if (data.equals("OK")) {
	// System.out.println("Got the alert, Clicking OK Button in alert");
	// jAlert.accept();
	// } else {
	// System.out
	// .println("Could not locate OK Button, So dismissing the alert");
	// jAlert.dismiss();
	// }
	// }

	// public boolean isAlertPresent(WebDriver driver) {
	//
	// boolean presentFlag = false;
	//
	// try {
	// // Check the presence of alert
	// Alert alert = driver.switchTo().alert();
	// // Alert present; set the flag
	// presentFlag = true;
	// // if present consume the alert
	// alert.accept();
	//
	// } catch (NoAlertPresentException ex) {
	// // Alert not present
	// ex.printStackTrace();
	// }
	// return presentFlag;
	// }

	//
	// public void process(WebDriver driver, WebElement element, String data) {
	//
	// // WebDriverWait wait = new WebDriverWait(driver, 2);
	// // wait.until(ExpectedConditions.alertIsPresent());
	// Alert jAlert = driver.switchTo().alert();
	// System.out.println("text present in the alert is :-  "
	// + driver.switchTo().alert().getText());
	//
	// if (data.equals("OK")) {
	// System.out.println("Got the alert, Clicking OK Button in alert");
	// jAlert.accept();
	// } else {
	// System.out
	// .println("Could not locate OK Button, So dismissing the alert");
	// jAlert.dismiss();
	// }
	//
	// // waitForAlertToBeAccepted(driver, data);
	// }
	//
	// public void waitForAlertToBeAccepted(WebDriver driver, final String data)
	// {
	// new WebDriverWait(driver, 5) {
	// }.until(new ExpectedCondition<Boolean>() {
	// @Override
	// public Boolean apply(WebDriver driver) {
	// Boolean switched = false;
	// try {
	// String popupMsg = driver.switchTo().alert().getText();
	// System.out.println("text in Pop up is :-  " + popupMsg);
	// // driver.switchTo().alert().accept();
	// if (data.equals("OK")) {
	// System.out
	// .println("Got the alert, Clicking OK Button in alert");
	// driver.switchTo().alert().accept();
	// } else {
	// System.out
	// .println("Could not locate OK Button, So dismissing the alert");
	// driver.switchTo().alert().dismiss();
	// }
	// switched = true;
	// } catch (Exception Ex) {
	// System.out.println("Couldn't switch!");
	// }
	// return switched;
	// }
	// });
	// }
	// }
}