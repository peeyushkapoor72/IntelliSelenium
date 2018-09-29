package com.csc.ss.web.element.impl;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.InputMismatchException;

public class IntelliSTable implements IIntelliSWebElement {

	/**
	 * @param data
	 * @param driver
	 * @param element
	 */
	public void process(WebDriver driver, WebElement element, String data) {

		boolean bool = false;
		String strXpath = GetWebElementXpath(element);
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(data
				.split("\\r\\n|\\n|\\r")));

		for (int i = 0; i < aList.size(); i++) {

			bool = webTable(driver, strXpath, aList.get(i).trim());
			if (bool) {
				System.out.println("Sucessfully retrieved text   "
						+ aList.get(i).trim() + "   in the web table.");
				result.add("Exists");
			} else {
				System.out.println("Not able to locate text   "
						+ aList.get(i).trim() + "   in the web table.");
				result.add("Not Exists");
				throw new InputMismatchException();
			}
		}
		for (int y = 0; y < result.size(); y++) {
			if (result.get(y).trim().equals("Exists")) {
				System.out.println("Implementation Passed");
			} else {
				System.out.println("Value Not Present.");
			}
		}
	}

	/**
	 * @return
	 * @param data
	 * @param driver
	 * @param strXpath
	 */
	public boolean webTable(WebDriver driver, String strXpath, String data) {

		boolean bool = false;
		// To locate table.
		WebElement mytable = driver.findElement(By.xpath(strXpath));

		// Now get all the TR elements from the table
		List<WebElement> allRows = mytable.findElements(By.tagName("tr"));

		// And iterate over them, getting the cells
		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			// Print the contents of each cell
			for (WebElement cell : cells) {
				System.out.println(cell.getText());
				if (cell.getText().equals(data)) {
					System.out.println("got it");
					bool = true;
				}
			}
		}
		return bool;
	}

	/**
	 * @param data
	 * @param driver
	 * @param strXpath
	 */
	@SuppressWarnings("unused")
	public void Handle_Dynamic_Webtable(WebDriver driver, String strXpath,
			String data) {

		// To locate table.
		WebElement mytable = driver.findElement(By.xpath(strXpath));

		// To locate rows of table.
		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));

		// To calculate no of rows In table.
		int rows_count = rows_table.size();

		// Loop will execute till the last row of table.
		for (int row = 0; row < rows_count; row++) {

			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(
					By.tagName("td"));

			// To calculate no of columns(cells) In that specific row.
			int columns_count = Columns_row.size();
			System.out.println("Number of cells In Row " + row + " are "
					+ columns_count);

			// Loop will execute till the last cell of that specific row.
			for (int column = 0; column < columns_count; column++) {

				// To retrieve text from that specific cell.
				String celtext = Columns_row.get(column).getText();
				System.out.println("Cell Value Of row number " + row
						+ " and column number " + column + " Is " + celtext);
				if (data.equals(celtext)) {
					System.out.println("\n"
							+ "The Text found and Matches the verification "
							+ "\n");
					break;
				}
			}
			break;
		}
	}

	/**
	 * @return
	 * @param webElement
	 * @throws AssertionError
	 */
	public static String GetWebElementXpath(WebElement webElement)
			throws AssertionError {
		if ((webElement instanceof WebElement)) {
			Object o = webElement;
			String text = o.toString();
			text = text.substring(text.indexOf("xpath: ") + 7,
					text.length() - 1);
			return text;
		} else {
			System.out
					.println("Argument is not an WebElement, his actual class is:"
							+ webElement.getClass());
		}
		return "";
	}
}