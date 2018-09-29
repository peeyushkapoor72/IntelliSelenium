package com.csc.intellis.auotIT.mianFrame;

import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.csc.intellis.auotIT.functions.autoITKeyboardFunctions;
import com.csc.intellis.auotIT.functions.autoItFunctions;
import com.csc.intellis.auotIT.miscMethods.javaMethods;

import autoitx4java.AutoItX;

public class MainFrameMethods {

	public static Process p;
	static String strCopiedText;
	public static AutoItX autoIT = null;

	/**
	 * @throws IOException
	 * @param strdriverPath
	 */
	public static Process is3270WindowDisplayed(String strdriverPath)
			throws IOException {

		try {
			File file = new File(strdriverPath);
			if (!file.exists()) {
				throw new IllegalArgumentException("The file " + strdriverPath
						+ "  does not exist");
			}
			p = Runtime.getRuntime().exec(file.getAbsolutePath());
			if (p.isAlive()) {
				System.out.println("  Yes the process exists ... !!!!");
			} else {
				System.out
						.println("  Nahhh the process DOSE NOT EXISTS ... !!!!");
			}
		} catch (Exception e) {
			System.out
					.println("  Throws Exception in Method == is3270WindowDisplayed     "
							+ e);
			System.out
					.println("Executing teat down Method to close the execution.");
			autoItFunctions.tearDown(strdriverPath, p);
		}
		return p;
	}

	public static void clickYesOnPopUp() {

		try {
			autoIT = new AutoItX();
			javaMethods.sleep(4000);
			autoIT.controlClick("Quick3270", "", "[CLASS:Button; INSTANCE:1]");
			javaMethods.sleep(4000);
		} catch (Exception e) {
			System.out
					.println("  Throws Exception in Method == clickYesOnPopUp     "
							+ e);
			System.out
					.println("Executing teat down Method to close the execution.");
		}
	}

	/**
	 * @param strMainFrameIP
	 * @throws InterruptedException
	 */
	public static void setSessionSettings(String strMainFrameIP) {
		// ,String strSavedFilePath) {

		try {
			javaMethods.sleep(2000);
			autoItFunctions.setMaximiseWindow("Quick3270");
			javaMethods.sleep(4000);
			autoIT.send("!+{s}+{s}", false);
			javaMethods.sleep(4000);
			autoIT.controlSend("Session settings", "",
					"[CLASS:Edit; INSTANCE:1]", strMainFrameIP);
			javaMethods.sleep(2000);
			System.out.println(" Entered " + strMainFrameIP
					+ "  nvac into mainFrame screen  ");
			autoIT.controlClick("Session settings", "",
					"[CLASS:Button; INSTANCE:1]");
			javaMethods.sleep(2000);
			autoIT.send("!+{s}+{c}", false);
			javaMethods.sleep(8000);

			// // verification section
			// try {
			// autoItFunctions
			// .verificationProcess(strSavedFilePath,
			// "Enter the code for the application you wish to access");
			// } catch (Exception e) {
			// System.out
			// .println("Looks like verification threw an error in setSessionSettings method "
			// + e);
			// // System.exit(0);
			// }

		} catch (Exception e) {
			System.out
					.println("  Throws Exception in Method == setSessionSettings     "
							+ e);
			System.out
					.println("Executing tear down Method to close the execution.");
			// autoItFunctions.tearDown();
			// System.exit(0);
		}
	}

	/**
	 * @param strDivisionName
	 */
	public static void enteringInGreenScreen(String strDivisionName) {

		try {
			// Setting NVAC
			autoIT.send("{TAB}+{TAB}" + strDivisionName, false);
			javaMethods.sleep(6000);
			System.out.println("   Entered nvac into mainFrame screen   ");
			autoITKeyboardFunctions.pressingEnterKey();
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == enteringInGreenScreen     "
							+ e);
			System.out
					.println("Executing teat down Method to close the execution.");
			// autoItFunctions.tearDown();
		}
	}

	/**
	 * @param strUN
	 * @param strPWD
	 * @throws AWTException
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	// public static void settingUNPWD(String strSavedFilePath, String strUN,
	public static void settingUNPWD(String strUN, String strPWD)
			throws FileNotFoundException, AWTException, IOException {

		// // verification
		// autoItFunctions.verificationProcess(strSavedFilePath,
		// "WELCOME TO NVAC SUPERSESSION");

		try {
			// Setting User Name
			autoIT.send(strUN, false);
			javaMethods.sleep(3000);
			System.out.println("  User Name   " + strUN + "    entered.  ");
			// autoITKeyboardFunctions.pressTab(1);
			autoITKeyboardFunctions.pressTab();
			// Setting Password
			autoIT.send(strPWD, false);
			javaMethods.sleep(3000);
			System.out.println("  Password   " + strPWD + "    entered.  ");
			autoITKeyboardFunctions.pressingEnterKey();
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == settingUNPWD     "
							+ e);
			System.out
					.println("Executing teat down Method to close the execution.");
			// autoItFunctions.tearDown();
		}
	}

	/**
	 * @param strRegion
	 * @throws InterruptedException
	 */
	public static void enteringRegion(String strRegion) {

		try {
			autoIT.send(strRegion, false);
			javaMethods.sleep(3000);
			System.out.println("  Entering TSO Screen  ");
			autoITKeyboardFunctions.pressingEnterKey();
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == enteringRegion     "
							+ e);
			System.out
					.println("Executing teat down Method to close the execution.");
			// autoItFunctions.tearDown();
		}
	}

	/**
	 * @param strDataset
	 * @throws InterruptedException
	 */
	public static void veiwingDataSet(String strDataset) {

		try {
			autoIT.send(strDataset, false);
			javaMethods.sleep(3000);
			System.out.println("  Entering  " + strDataset + "  Screen  ");
			autoITKeyboardFunctions.pressingEnterKey();
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == veiwingDataSet     "
							+ e);
		}
	}

	/**
	 * @param strLibName
	 */
	public static void enterLibrary(String strLibName) {

		try {
			autoIT.send(strLibName, false);
			javaMethods.sleep(3000);
			System.out.println("  Entering  " + strLibName + "  Screen  ");
			autoITKeyboardFunctions.pressingEnterKey();
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == enterLibrary     "
							+ e);
		}
	}

	/**
	 * @param nodeName
	 */
	public static void setValueInMF(String valueText) {
		try {
			autoIT.send(valueText, false);
			javaMethods.sleep(3000);
			System.out.println("  Entering  " + valueText
					+ "  in Node on MainFrame Panel  ");
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == setValueInMF     "
							+ e);
		}
	}

	// /**
	// * @param poolName
	// */
	// public static void setPoolInMF(String poolName) {
	// try {
	// autoIT.send(poolName, false);
	// javaMethods.sleep(3000);
	// System.out.println("  Entering  " + poolName
	// + "  in Node on MainFrame Panel  ");
	// } catch (Exception e) {
	// System.out
	// .println("   Throws Exception in Method == setPoolInMF     "
	// + e);
	// }
	// }
	//
	// /**
	// * @param batchName
	// */
	// public static void setBatchCycleInMF(String batchName) {
	// try {
	// autoIT.send(batchName, false);
	// javaMethods.sleep(3000);
	// System.out.println("  Entering  " + batchName
	// + "  in Node on MainFrame Panel  ");
	// } catch (Exception e) {
	// System.out
	// .println("   Throws Exception in Method == setBatchCycleInMF     "
	// + e);
	// }
	// }
	//
	// /**
	// * @param bakupDataSetName
	// */
	// public static void setBackUpDataSetInMF(String bakupDataSetName) {
	// try {
	// autoIT.send(bakupDataSetName, false);
	// javaMethods.sleep(3000);
	// System.out.println("  Entering  " + bakupDataSetName
	// + "  in Node on MainFrame Panel  ");
	// } catch (Exception e) {
	// System.out
	// .println("   Throws Exception in Method == setBackUpDataSetInMF     "
	// + e);
	// }
	// }
	//
	// /**
	// * @param dataSetName
	// */
	// public static void setDataSetInMF(String dataSetName) {
	// try {
	// autoIT.send(dataSetName, false);
	// javaMethods.sleep(3000);
	// System.out.println("  Entering  " + dataSetName
	// + "  in Node on MainFrame Panel  ");
	// } catch (Exception e) {
	// System.out
	// .println("   Throws Exception in Method == setDataSetInMF     "
	// + e);
	// }
	// }
	//
	// /**
	// * @param emailName
	// */
	// public static void setEmailMF(String emailName) {
	// try {
	// autoIT.send(emailName, false);
	// javaMethods.sleep(3000);
	// System.out.println("  Entering  " + emailName
	// + "  in Node on MainFrame Panel  ");
	// } catch (Exception e) {
	// System.out
	// .println("   Throws Exception in Method == setEmailMF     "
	// + e);
	// }
	// }

	/**
	 * @param p
	 * @throws IOException
	 * @param strdriverPath
	 */
	public static void tearDown(Process p) throws IOException {

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