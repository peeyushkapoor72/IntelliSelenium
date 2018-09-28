package com.csc.intellis.core;

import java.io.File;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import autoitx4java.AutoItX;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import java.awt.AWTException;
import org.openqa.selenium.By;
import java.awt.event.KeyEvent;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import com.jacob.com.LibraryLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import com.csc.intellis.parser.HTMLReader;
import org.openqa.selenium.TakesScreenshot;
import java.awt.datatransfer.StringSelection;
import com.csc.intellis.constants.ElementType;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.exception.IntelliSException;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IntelliSExecutor {

	public String xpath;
	public ExecutorParameterBean bean = null;
	final static Logger logger = Logger.getLogger(IntelliSExecutor.class);

	/**
	 * @param bean
	 */
	public IntelliSExecutor(ExecutorParameterBean bean) {
		this.bean = bean;
	}

	/**
	 * @return
	 * @param str
	 */
	public static char lastCharOfString(String str) {
		System.out.println("last char = " + str.charAt(str.length() - 1));
		return str.charAt(str.length() - 1);
	}

	/**
	 * @return
	 * @param rownNum
	 * @param fileName
	 * @param workbook
	 * @param dataValue
	 * @param Sleepfactor
	 * @param dataValueMap
	 * @throws IOException
	 * @param strSheetName
	 * @throws IntelliSException
	 */
	public boolean execute(Map<String, Map<String, String>> dataValue,
			Map<String, Map<String, String>> dataValueMap, String strSheetName,
			String fileName, HSSFWorkbook workbook, int rownNum,
			Integer Sleepfactor) throws IntelliSException, IOException {

		boolean bool = false;
		try {
			logger.info("Checking InteliExecuter Class !");
			List<String> xpathList = parsePageSrc(generatePgSrc(false));
			bean.setXpathList(xpathList);

			String screenShotDir = bean.getConfigMap().get(
					IntelliSConstants.ScreenShot_FILE_PATH);
			logger.info("Screen Shots would be saved at location:- " + "\t"
					+ screenShotDir);

			char ch = lastCharOfString(bean.getCaseId());
			String caseId = Character.toString(ch);
			System.out.println(caseId);

			List<String> keys = new ArrayList<String>(dataValueMap.keySet());
			for (String key : keys) {
				System.out.println(key);
			}

			String header = "";
			for (int itr = 3; itr < keys.size(); itr++) {
				header = keys.get(itr);
				logger.info("Case Id is :- " + header);
			}

			Map<String, List<String>> labelData = new HashMap<String, List<String>>();
			DataResolver_Old object = new DataResolver_Old(bean, fileName);
			labelData = object.LabelDataResolver(dataValue, strSheetName,
					rownNum);

			Set<String> labelKeyset = labelData.keySet();
			// String Parent_Window = bean.getDriver().getWindowHandle();

			for (String mapKey : dataValue.keySet()) {

				for (Entry<String, String> entry : dataValue.get(mapKey)
						.entrySet()) {

					String keyWord = entry.getKey();
					String keyValue = entry.getValue();
					logger.info("=================" + keyWord
							+ "=================" + keyValue
							+ "=================");

					String name = "";
					String keyword = "";
					String type = "";
					String refresh = "";
					String seq = "1";
					String skip = "0";
					String sleepTime = "";
					String data = "";
					String HtmlId = "";
					String HtmlName = "";
					String HtmlXpath = "";
					String test = "";
					for (String key : labelKeyset) {

						if (keyWord.equals(key)) {

							List<String> labelValue = new ArrayList<String>();
							labelValue = labelData.get(keyWord);
							Iterator<String> labelVIterator = labelValue
									.iterator();

							if (labelVIterator.hasNext()) {
								name = labelVIterator.next();
								HtmlId = labelVIterator.next();
								HtmlName = labelVIterator.next();
								HtmlXpath = labelVIterator.next();
								type = labelVIterator.next();
								keyword = labelVIterator.next();
								refresh = labelVIterator.next();
								seq = labelVIterator.next();
								skip = labelVIterator.next();
								test = labelVIterator.next();

								if ((test != null) && (test != "")) {
									int x = Integer.parseInt(test);
									Integer Sleep = (x * Sleepfactor);
									sleepTime = Sleep.toString();
								} else {
									sleepTime = "";
								}
								data = entry.getValue();

								// logger.info("**********" + Parent_Window
								// + "**********");
								bean.getDriver();
								logger.info("input-->" + name + ":" + HtmlId
										+ ":" + HtmlName + ":" + HtmlXpath
										+ ":" + type + ":" + keyword + ":"
										+ refresh + ":" + seq + ":" + skip);
							}

							bean.setName(name);
							bean.setHtmlId(HtmlId);
							bean.setHtmlName(HtmlName);
							bean.setHtmlXpath(HtmlXpath);
							bean.setType(type);
							bean.setKeyword(keyword);
							bean.setPosition(seq);
							bean.setSkip(skip);
							bean.setData(data);

							String screenShot = null;
							if (!sleepTime.equals("")) {
								Thread.sleep(Long.parseLong(sleepTime));
							}
							if (name != "") {

								// Debugging Purposes
								if (name.equals("")) {
									System.out.println("dsadas");
								}

								if (!type.equals(ElementType.ALERT.name())) {
									XPathResolver resolver = new XPathResolver(
											bean);
									xpath = resolver.resolve();
									bean.setXpath(xpath);
								}

								/**
								 * Highlights the Element
								 */
								String highLight = dataValueMap.get(header)
										.get("Highlight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight(xpath, "", "", "");
								} else if (highLight.equals("")) {
									break;
								}
								WebElementProcessor processor = new WebElementProcessor(
										bean);
								processor.process(name, "", "", "", "",
										Sleepfactor);

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValueMap.get(header).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {

										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														+ bean.getName()
																.substring(
																		0,
																		bean.getName()
																				.length() - 1)
														+ "\\" + bean.getName()
														+ ".png");
									}
								} else if (screenShot.equals("")) {
									break;
								}

								if (refresh.contains(IntelliSConstants.REFRESH)) {
									if (refresh
											.equals(IntelliSConstants.WIN_REFRESH)) {
										bean.setXpathList(parsePageSrc(generatePgSrc(true)));
									} else {
										bean.setXpathList(parsePageSrc(generatePgSrc(false)));
									}
								}
							} else if (HtmlId != "") {

								// Debugging Purposes
								if (HtmlId.equals("")) {
									System.out.println("dsadas");
								}

								/**
								 * Highlights the Element
								 */
								String highLight = dataValueMap.get(header)
										.get("Highlight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight("", HtmlId, "", "");
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								logger.info(HtmlId);
								processor.process("", "", "", HtmlId, "",
										Sleepfactor);

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValueMap.get(header).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {

										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														+ bean.getName()
																.substring(
																		0,
																		bean.getName()
																				.length() - 1)
														+ "\\" + bean.getName()
														+ ".png");
									}
								} else if (screenShot.equals("")) {
									break;
								}

								if (refresh.contains(IntelliSConstants.REFRESH)) {
									if (refresh
											.equals(IntelliSConstants.WIN_REFRESH)) {
										bean.setXpathList(parsePageSrc(generatePgSrc(true)));
									} else {
										bean.setXpathList(parsePageSrc(generatePgSrc(false)));
									}
								}
							} else if (HtmlName != "") {

								// Debugging Purposes
								if (HtmlName.equals("")) {
									System.out.println("dsadas");
								}

								/**
								 * Highlights the Element
								 */
								String highLight = dataValueMap.get(header)
										.get("Highlight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight("", "", HtmlName, "");
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								logger.info(HtmlName);
								processor.process("", HtmlName, "", "", "",
										Sleepfactor);

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValueMap.get(header).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {
										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														+ bean.getCaseId()
														+ "\\"
														+ bean.getName()
																.substring(
																		0,
																		bean.getName()
																				.length() - 1)
														+ ".png");
									}
								} else if (screenShot.equals("")) {
									break;
								}

								if (refresh.contains(IntelliSConstants.REFRESH)) {
									if (refresh
											.equals(IntelliSConstants.WIN_REFRESH)) {
										bean.setXpathList(parsePageSrc(generatePgSrc(true)));
									} else {
										bean.setXpathList(parsePageSrc(generatePgSrc(false)));
									}
								}
							} else if (HtmlXpath != "") {

								// Debugging Purposes
								if (HtmlXpath.equals("")) {
									System.out.println("dsadas");
								}
								/**
								 * Highlights the Element
								 */
								String highLight = dataValueMap.get(header)
										.get("Highlight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight(HtmlXpath, "", "", "");
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								logger.info(HtmlXpath);
								processor.process("", "", "", "", HtmlXpath,
										Sleepfactor);

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValueMap.get(header).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {

										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														+ bean.getCaseId()
														+ "\\"
														+ bean.getName()
																.substring(
																		0,
																		bean.getName()
																				.length() - 1)
														+ ".png");
									}
								} else if (screenShot.equals("")) {
									break;
								}

								if (refresh.contains(IntelliSConstants.REFRESH)) {
									if (refresh
											.equals(IntelliSConstants.WIN_REFRESH)) {
										bean.setXpathList(parsePageSrc(generatePgSrc(true)));
									} else {
										bean.setXpathList(parsePageSrc(generatePgSrc(false)));
									}
								}
							} else if (type.contains("CLOSECHILDWINDOW")) {

								logger.info("Closing Child Window");
								System.out.println("Closing Child Window");
								bean.getDriver().close();

							} else if (type.contains("SWITCHTOCHILD")) {

								logger.info("Switch 2 CHILD");
								for (String Child_Window : bean.getDriver()
										.getWindowHandles()) {
									bean.getDriver().switchTo()
											.window(Child_Window);
									System.out
											.println("Switching to Child Window");
									logger.info("Switching to Child Window");
									logger.info(bean.getDriver().switchTo()
											.window(Child_Window));
								}
								bean.getDriver().manage().window().maximize();
								System.out.println("done");

							} else if (type.contains("SAVE")) {

								sleepTime(5000);
								clickAndSaveFile();
								sleepTime(3000);

							} else if (type.contains("SAVECANCEL")) {

								sleepTime(5000);
								try {
									Runtime.getRuntime()
											.exec("..\\autoITFiles\\File_Download_Cancel.exe");
									sleepTime(10000);
									System.out
											.println("File NOT Saved as the user clicked on Cancel in SAVE popup.");
								} catch (IOException e) {
									e.printStackTrace();
								}

							} else if (type.contains("UPLOAD")) {

								System.out.println("the path is  :-   "
										+ bean.getData());
								StringSelection ss = new StringSelection(
										bean.getData());
								Toolkit.getDefaultToolkit()
										.getSystemClipboard()
										.setContents(ss, null);

								// native key strokes for CTRL, V and ENTER
								// keys
								Robot robot = new Robot();
								sleepTime(5000);
								System.out
										.println("Waiting for 5 seconds BEFORE typing the path in the window. ");
								logger.info("Waiting for 5 seconds BEFORE typing the path in the window. ");
								robot.keyPress(KeyEvent.VK_CONTROL);
								robot.keyPress(KeyEvent.VK_V);
								robot.keyRelease(KeyEvent.VK_V);
								robot.keyRelease(KeyEvent.VK_CONTROL);
								robot.keyPress(KeyEvent.VK_ENTER);
								robot.keyRelease(KeyEvent.VK_ENTER);
								sleepTime(5000);
								System.out
										.println("Waiting for 5 seconds AFTER typing the path in the window. ");
								logger.info("Waiting for 5 seconds AFTER typing the path in the window. ");

							} else if (type.contains("ESCAPEKEY")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.keyPress(KeyEvent.VK_ESCAPE);
								System.out.println("Escape Key Pressed");

							} else if (type.contains("ALT0")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_0);
								System.out.println("ALT & 0 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_0);
								System.out.println("ALT & 0 Key Released");

							} else if (type.contains("ALT1")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_1);
								System.out.println("ALT & 1 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_1);
								System.out.println("ALT & 1 Key Released");

							} else if (type.contains("ALT2")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_2);
								System.out.println("ALT & 2 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_2);
								System.out.println("ALT & 2 Key Released");

							} else if (type.contains("ALT3")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_3);
								System.out.println("ALT & 3 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_3);
								System.out.println("ALT & 3 Key Released");

							} else if (type.contains("ALT4")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_4);
								System.out.println("ALT & 4 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_4);
								System.out.println("ALT & 4 Key Released");

							} else if (type.contains("ALT5")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_5);
								System.out.println("ALT & 5 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_5);
								System.out.println("ALT & 5 Key Released");

							} else if (type.contains("ALT6")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.setAutoDelay(250);
								robot.keyPress(KeyEvent.VK_ALT);
								sleepTime(3000);
								robot.keyPress(KeyEvent.VK_6);
								System.out.println("ALT & 6 Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_ALT);
								robot.keyRelease(KeyEvent.VK_6);
								System.out.println("ALT & 6 Key Released");

							} else if (type.contains("DELETEKEY")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.keyPress(KeyEvent.VK_DELETE);
								System.out.println("Delete Key Pressed");
								sleepTime(2000);
								robot.keyRelease(KeyEvent.VK_DELETE);
								System.out.println("Delete Key Released");

							} else if (type.contains("HOMEKEY")) {

								sleepTime(3000);
								Robot robot = new Robot();
								robot.keyPress(KeyEvent.VK_HOME);
								System.out.println("Home Key Pressed");

							} else if (type.contains("SWITCHTOPARENT")) {

								logger.info("Switch 2 Parent");
								for (String current_Window : bean.getDriver()
										.getWindowHandles()) {
									bean.getDriver().switchTo()
											.window(current_Window);
								}

							} else if (type.contains("SWITCHURL")) {

								System.out.println("Switching URL");
								bean.getDriver().get(data);
								bean.getDriver().manage().timeouts()
										.implicitlyWait(20, TimeUnit.SECONDS);

							} else if (type.contains("SWITCHTOFRAME")) {

								logger.info("Switching To Frame"
										+ bean.getData());
								int FrameNumber = Integer.parseInt(bean
										.getData());
								WebDriver newFDriver = bean.getDriver()
										.switchTo().frame(FrameNumber);
								bean.setDriver(newFDriver);

							} else if (type.contains("SWITCHTOPARENRTFRAME")) {

								logger.info("Switching To ParentFrame"
										+ bean.getData());
								WebDriver newFDriver = bean.getDriver()
										.switchTo().defaultContent();
								bean.setDriver(newFDriver);

							} else if (type.contains("ALERT")) {

								logger.info("Handling Alert");
								Alert alert = null;
								boolean presentFlag = false;
								// bean.getDriver().navigate().refresh();
								presentFlag = isAlertPresent(bean.getDriver());
								WebDriver newdriver = bean.getDriver();
								if (presentFlag) {
									try {
										alert = newdriver.switchTo().alert();
										if (data.equals("OK")) {
											logger.info("Got the alert, Clicking OK Button in alert");
											logger.info(alert.getText());
											alert.accept();
										} else {
											logger.info("Could not locate OK Button, So dismissing the alert");
											WebDriverWait wait = new WebDriverWait(
													bean.getDriver(), 15, 100);
											wait.until(ExpectedConditions
													.alertIsPresent());
											alert.dismiss();
										}
									} catch (NoAlertPresentException e) {
										logger.error("Not able to swtich to alert");
										FileWriter fw = new FileWriter(
												bean.getConfigMap()
														.get(IntelliSConstants.RESULT_FILE_PATH)
														+ "exception"
														+ "_TCID_"
														+ bean.getCaseId()
														+ "_"
														+ strSheetName
														+ ".txt", true);
										PrintWriter pw = new PrintWriter(fw);
										e.printStackTrace(pw);
										pw.close();
									}
								} else {
									logger.info("Alert NOT Present !!!!");
									FileWriter fw = new FileWriter(
											bean.getConfigMap()
													.get(IntelliSConstants.RESULT_FILE_PATH)
													+ "exception"
													+ "_TCID_"
													+ bean.getCaseId()
													+ "_"
													+ strSheetName + ".txt",
											true);
									PrintWriter pw = new PrintWriter(fw);
									pw.write("Alert Not Present......in Test case ID    :-"
											+ bean.getCaseId());
									pw.close();
								}
								if (refresh.contains(IntelliSConstants.REFRESH)) {
									if (refresh
											.equals(IntelliSConstants.WIN_REFRESH)) {
										bean.setXpathList(parsePageSrc(generatePgSrc(true)));
									} else {
										bean.setXpathList(parsePageSrc(generatePgSrc(false)));
									}
								}
							}
							// else {
							// throw new IntelliSException(
							// "No Locator Assigned for the type   "
							// + type
							// + "   With Keyword Assigned   "
							// + " : "
							// + keyword
							// +
							// "   Therefore Failing the test case having test case id :-   "
							// + bean.getCaseId());
							// }
						}
					}
				}
			}
			bool = true;
		} catch (Exception e) {
			// e.printStackTrace();
			// throw new IntelliSException("500", "Runtime error !!");
			FileWriter fw = new FileWriter(bean.getConfigMap().get(
					IntelliSConstants.RESULT_FILE_PATH)
					+ "exception"
					+ "_TCID_"
					+ bean.getCaseId()
					+ "_"
					+ strSheetName + ".txt", true);
			PrintWriter pw = new PrintWriter(fw);
			e.printStackTrace(pw);
			pw.close();
		}
		return bool;
	}

	/**
	 * @param driver
	 * @param destPath
	 */
	public void takeScreenShot(WebDriver driver, String destPath) {
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(destPath));
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error(e1);
		}
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
	 * @return
	 * @param driver
	 */
	public boolean isAlertPresent(WebDriver driver) {

		boolean presentFlag = false;
		try {
			driver.switchTo().alert();
			presentFlag = true;
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return presentFlag;
	}

	/**
	 * @return
	 * @param winReferesh
	 */
	public String generatePgSrc(boolean winReferesh) {
		PageSourceGenerator pageSrcGenerator = new PageSourceGenerator(
				bean.getDriver());
		return pageSrcGenerator.generate(winReferesh);
	}

	/**
	 * @return
	 * @param pgSrc
	 * @throws IntelliSException
	 */
	public List<String> parsePageSrc(String pgSrc) throws IntelliSException {
		try {
			HTMLReader reader = new HTMLReader();
			return reader.readHTML(pgSrc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new IntelliSException("501", "Error reading HTML page source");
		}
	}

	/**
	 * @return
	 * @param newMap
	 * @param strKey
	 */
	public static String accessInternalMap(
			Map<String, Map<String, String>> newMap, String strKey) {

		String returnValue = "";
		for (Entry<String, Map<String, String>> entry : newMap.entrySet()) {
			Map<String, String> internalMap = entry.getValue();
			returnValue = internalMap.get(strKey);
		}
		return returnValue;
	}

	/**
	 * @param id
	 * @param name
	 * @param xpath
	 */
	public void Highlight(String xpath, String id, String name, String CSSPath) {

		for (int i = 0; i < 2; i++) {

			JavascriptExecutor js = (JavascriptExecutor) bean.getDriver();
			if (xpath != "") {
				WebElement element = bean.getDriver().findElement(
						By.xpath(xpath));
				js.executeScript(
						"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						element);
				js.executeScript(
						"arguments[0].setAttribute('style','border: solid 2px white')",
						element);
			} else if (name != "") {
				WebElement element = bean.getDriver()
						.findElement(By.name(name));
				js.executeScript(
						"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						element);
				js.executeScript(
						"arguments[0].setAttribute('style','border: solid 2px white')",
						element);
			} else if (CSSPath != "") {
				WebElement element = bean.getDriver().findElement(
						By.cssSelector(CSSPath));
				js.executeScript(
						"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						element);
				js.executeScript(
						"arguments[0].setAttribute('style','border: solid 2px white')",
						element);
			} else if (id != "") {
				WebElement element = bean.getDriver().findElement(By.id(id));
				js.executeScript(
						"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
						element);
				js.executeScript(
						"arguments[0].setAttribute('style','border: solid 2px white')",
						element);
			}
		}
	}

	/**
	 * @param inputDate
	 */
	public static void setDate(String inputDate) {

		Calendar cal = Calendar.getInstance();
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(inputDate
				.split(",")));
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;

		for (int i = 0; i < aList.size(); i++) {

			char first = 0;
			try {
				first = aList.get(i).charAt(0);
			} catch (StringIndexOutOfBoundsException siobe) {
				System.out
						.println("Either of Year, Month or Day is missing in the req.");
			}
			String firstChar = String.valueOf(first);
			System.out.println(aList.get(i));

			if (i == 0) {

				if (firstChar.contains("+")) {
					System.out.println("Add Year");
					cal.add(Calendar.YEAR, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Subtarct Year");
					cal.add(Calendar.YEAR, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				}
			} else if (i == 1) {

				if (firstChar.contains("+")) {
					System.out.println("Add MONTH");
					cal.add(Calendar.MONTH, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Subtarct MONTH");
					cal.add(Calendar.MONTH, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				}
			} else if (i == 2) {

				if (firstChar.contains("+")) {
					System.out.println("Add DATE");
					cal.add(Calendar.DATE, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Subtarct DATE");
					cal.add(Calendar.DATE, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();
				}
			}
		}
		String newDateAfterChange = null;
		newDateAfterChange = format1.format(date);
		System.out.println(newDateAfterChange);
	}

	public static String jvmBitVersion() {
		return System.getProperty("sun.arch.data.model");
	}

	/**
	 * @param filepath
	 */
	public static void uploadUsingJacob(String filepath) {

		String jacobDllVersionToUse;
		if (jvmBitVersion().contains("32")) {
			jacobDllVersionToUse = "jacob-1.18-x86.dll";
		} else {
			jacobDllVersionToUse = "jacob-1.18-x64.dll";
		}
		File file = new File("lib", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());

		AutoItX uploadWindow = new AutoItX();

		if (uploadWindow.winWaitActive("Choose File to Upload", "", 20)) {
			if (uploadWindow.winExists("Choose File to Upload")) {
				uploadWindow.sleep(200);
				uploadWindow.send(filepath);
				uploadWindow.sleep(800);
				uploadWindow.controlClick("Choose File to Upload", "", "&Open");
				uploadWindow.sleep(200);
			}
		}
		System.out.println("Uploaded File .. !!!!");
	}

	/**
	 * @throws InterruptedException
	 */
	public static void clickAndSaveFile() {
		try {
			Robot robot = new Robot();
			sleepTime(2000);
			// press s key to save
			robot.keyPress(KeyEvent.VK_ALT);
			sleepTime(5000);
			// press enter to save the file with default name and in default
			// location
			robot.keyPress(KeyEvent.VK_S);
			sleepTime(2000);
			robot.keyRelease(KeyEvent.VK_ALT);
			sleepTime(2000);
			robot.keyRelease(KeyEvent.VK_S);
			sleepTime(5000);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}