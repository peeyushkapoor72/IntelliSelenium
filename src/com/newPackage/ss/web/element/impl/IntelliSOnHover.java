package com.csc.ss.web.element.impl;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class IntelliSOnHover implements IIntelliSWebElement {

	public void process(WebDriver driver, WebElement element, String data) {

		// Actions builder = new Actions(driver);
		sleepTime(6000);
		// builder.clickAndHold(element).moveToElement(element).build().perform();
		// builder.click();

		// builder.clickAndHold(element);
		// builder.moveToElement(element);
		// builder.build();
		// builder.perform();
		// builder.click();

		// Actions act = new Actions(driver);
		// org.openqa.selenium.Point p1 = element.getLocation();
		// int x = p1.getX();
		// int y = p1.getY();
		// System.out.println("X:" + x + " Y:" + y);
		// builder.moveByOffset(x, y).moveToElement(element).click().build()
		// .perform();

		// builder.moveToElement(element, x,
		// y).clickAndHold().build().perform();

		Actions action = new Actions(driver);
		sleepTime(3000);
		action.moveToElement(element).clickAndHold(element).build().perform();

		// Robot robot;
		// try {
		// robot = new Robot();
		// robot.mouseMove(0, 1050);
		// } catch (AWTException e1) {
		// e1.printStackTrace();
		// }

		// builder.moveToElement(element).clickAndHold().build().perform();
		// builder.click();

		sleepTime(6000);
		System.out.println("done");
		// mouseHoverJScript(element, driver);
	}

	/**
	 * @param milliseconds
	 */
	public static void sleepTime(Integer milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param driver
	 * @param HoverElement
	 */
	public static void mouseHoverJScript(WebElement HoverElement,
			WebDriver driver) {

		try {
			if (isElementPresent(HoverElement)) {
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) driver).executeScript(mouseOverScript,
						HoverElement);

			} else {
				System.out.println("Element was not visible to hover " + "\n");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with " + HoverElement
					+ "is not attached to the page document"
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element " + HoverElement
					+ " was not found in DOM" + e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occurred while hovering"
					+ e.getStackTrace());
		}
	}

	/**
	 * @return
	 * @param element
	 */
	public static boolean isElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
	}
}