package com.csc.intellis.auotIT.functions;

import java.io.File;
import java.io.IOException;
import autoitx4java.AutoItX;
import java.awt.AWTException;
import com.jacob.com.LibraryLoader;
import java.io.FileNotFoundException;
import com.csc.intellis.auotIT.miscMethods.javaMethods;
import com.csc.intellis.auotIT.mianFrame.MainFrameMethods;

public class autoItFunctions {

	public static Process p;
	public static String strCopiedText;
	public static String strLibName = "FNBN.JCLLIB";
	public static AutoItX autoIT = new AutoItX();;
	public static String strUN = "Y00938";
	public static String strRegion = "TSO";
	public static String strDataset = "3.4";
	public final static int SW_MAXIMIZE = 3;
	public static String strPWD = "1qaz2wsx";
	public static String strMainframeIP = "20.17.189.72";
	public static String strSavedFilePath = "C:\\T420\\C_Drive\\Testing\\abc2.txt";
	public static String driverPath = "C:\\T420\\Quick3270\\Quick3270\\Quick3270.exe";

	public static void main(String[] args) throws IOException, AWTException {

		setDriver();
		p = MainFrameMethods.is3270WindowDisplayed(driverPath);
		MainFrameMethods.clickYesOnPopUp();
		// MainFrameMethods.setSessionSettings(strMainframeIP,
		// strSavedFilePath);
		MainFrameMethods.setSessionSettings(strMainframeIP);
		MainFrameMethods.enteringInGreenScreen("nvac");
		// MainFrameMethods.settingUNPWD(strSavedFilePath, strUN, strPWD);
		MainFrameMethods.settingUNPWD(strUN, strPWD);
		MainFrameMethods.enteringRegion(strRegion);
		MainFrameMethods.veiwingDataSet(strDataset);
		autoITKeyboardFunctions.pressingEnterKey();
		// autoITKeyboardFunctions.pressTab(1);
		autoITKeyboardFunctions.pressTab();
		MainFrameMethods.enterLibrary(strLibName);
		tearDown(driverPath, p);

	}

	/**
	 * This function initializes the driver
	 */
	public static void setDriver() {

		try {
			String jacobDllVersionToUse;
			jacobDllVersionToUse = "jacob-1.18-x64.dll";
			File file1 = new File("lib", jacobDllVersionToUse);
			System.setProperty(LibraryLoader.JACOB_DLL_PATH,
					file1.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("  Throws Exception in Method == setDriver     "
					+ e);
		}
	}

	/**
	 * @param string2Match
	 * @throws IOException
	 * @throws AWTException
	 * @param strSavedFilePath
	 * @throws FileNotFoundException
	 */
	public static void verificationProcess(String strSavedFilePath,
			String string2Match) throws FileNotFoundException, AWTException,
			IOException {

		// performing CTRL+A and then performing CTRL+C
		selectAndCopy();
		javaMethods.sleep(2000);
		String newFilePath = javaMethods.fileExists(strSavedFilePath);
		System.out.println("Path where notepad would be saved is  "
				+ newFilePath);
		// Pasting the selected values to a Notepad
		pasteSelected(newFilePath);
		File fileName = new File(newFilePath);
		System.out.println(fileName.getName());
		// Closing the Notepad process
		autoITKeyboardFunctions.winClose(fileName.getName());

		javaMethods.confirmTextContent(newFilePath, string2Match);
	}

	/**
	 * This Function performs Ctrl+A(Select All) and then Ctrl+C(Copy)
	 */
	public static void selectAndCopy() {
		autoIT.send("^a", false);
		javaMethods.sleep(8000);
		autoIT.send("^c", false);
		javaMethods.sleep(8000);
	}

	/**
	 * @param strFilePath
	 * @throws AWTException
	 * @throws FileNotFoundException
	 */
	public static void pasteSelected(String strFilePath) throws AWTException,
			FileNotFoundException {
		autoIT.run("notepad.exe", "", AutoItX.SW_SHOW);
		autoIT.winActivate("Untitled - Notepad");
		autoIT.winWaitActive("Untitled - Notepad");
		javaMethods.sleep(3000);
		autoIT.send("^v", false);
		javaMethods.sleep(8000);

		// saving the file
		autoIT.send("^s", false);
		System.out.println(strFilePath + "==========");
		autoIT.controlFocus("Save As", "", "Edit1");
		javaMethods.sleep(2000);
		autoIT.controlSend("Save As", "", "Edit1", strFilePath);
		javaMethods.sleep(8000);
		autoITKeyboardFunctions.pressingEnterKey();
	}

	/**
	 * This function Maximize the window
	 */
	public static void setMaximiseWindow(String strWindowState) {
		javaMethods.sleep(2000);
		autoIT.winSetState(strWindowState, "", SW_MAXIMIZE);
		javaMethods.sleep(3000);
	}

	/**
	 * @param p
	 * @throws IOException
	 * @param strdriverPath
	 */
	public static void tearDown(String strdriverPath, Process p)
			throws IOException {

		if (p.isAlive()) {
			try {
				p.destroyForcibly();
				System.out.println("Destroying the process ");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			System.out.println("  Nahhh the process DID NOT EXISTS ... !!!!");
		}
	}
}