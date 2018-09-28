package com.csc.testing;

import java.io.File;
import java.io.IOException;

public class pk_test {

	public static void main(String[] args) throws IOException {

		// String filePath =
		// "C:\\T420\\C_Drive\\Share_Folder_Peeyush\\ER\\Results\\";
		// File file = new File(filePath);
		// deleteDirectory(file);
		// createDirectory(file);
		String creditCardNumber = "1234567891234567";
		// driver.findElement(By.id("label")).sendKeys(
		String abc = maskCardNumber(creditCardNumber, "xxxxxxxxxxxxxxx");
		System.out.println(abc);
	}

	public static String maskCardNumber(String cardNumber, String maskedCard) {
		// format the number
		int index = 0;
		StringBuilder maskedCardNumber = new StringBuilder();
		for (int i = 0; i < maskedCard.length(); i++) {
			char c = maskedCard.charAt(i);
			if (c == '#') {
				maskedCardNumber.append(cardNumber.charAt(index));
				index++;
			} else if (c == 'x') {
				maskedCardNumber.append(c);
				index++;
			} else {
				maskedCardNumber.append(c);
			}
		}
		// return the masked number
		return maskedCardNumber.toString();
	}

	/**
	 * @param file
	 */
	public static void createDirectory(File file) {
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	/**
	 * @param folder
	 */
	public static void deleteDirectory(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteDirectory(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
}