package com.csc.intellis.auotIT.functions;

import com.csc.intellis.auotIT.miscMethods.javaMethods;
import com.csc.intellis.core.bean.ExecutorParameterBean;

import autoitx4java.AutoItX;

public class autoITKeyboardFunctions {

	static AutoItX autoIT = ExecutorParameterBean.autoIT();

	public static void main(String[] args) {
		autoIT = new AutoItX();
		// pressTab(1);
	}

	public static void pressF1(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F1}", false);
		}
	}

	public static void pressF2(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F2}", false);
		}
	}

	public static void pressF3(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F3}", false);
		}
	}

	public static void pressF4(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F4}", false);
		}
	}

	public static void pressF5(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F5}", false);
		}
	}

	public static void pressF6(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F6}", false);
		}
	}

	public static void pressF7(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F7}", false);
		}
	}

	public static void pressF8(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F8}", false);
		}
	}

	public static void pressF9(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F9}", false);
		}
	}

	public static void pressF10(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F10}", false);
		}
	}

	public static void pressF11(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F11}", false);
		}
	}

	public static void pressF12(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{F12}", false);
		}
	}

	// public static void pressTab(int i) {
	public static void pressTab() {
		// for (int j = 0; j <= i - 1; j++) {
		autoIT.send("{TAB}", false);
		// }
	}

	// public static void pressEnter(int i) throws InterruptedException {
	public static void pressEnter() throws InterruptedException {
		// for (int j = 0; j <= i; j++) {
		autoIT.send("{ENTER}", false);
		Thread.sleep(6000);
		// }
	}

	public static void pressBackSpace(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{BACKSPACE}", false);
		}
	}

	public static void pressDELETE(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{DELETE}", false);
		}
	}

	public static void pressESCAPE(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{ESCAPE}", false);
		}
	}

	public static void pressINSERT(int i) {
		for (int j = 0; j <= i; j++) {
			autoIT.send("{INSERT}", false);
		}
	}

	public static void pressingEnterKey() {
		try {
			autoIT.send("{ENTER}", false);
			javaMethods.sleep(6000);
		} catch (Exception e) {
			System.out
					.println("   Throws Exception in Method == pressingEnterKey     "
							+ e);
			System.out
					.println("Executing teat down Method to close the execution.");
		}
	}

	/**
	 * @param strWinTitle
	 */
	public static void winClose(String strWinTitle) {
		autoIT.winClose(strWinTitle);
		javaMethods.sleep(8000);
	}
}