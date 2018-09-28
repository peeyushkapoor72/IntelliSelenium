package com.csc.intellis.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.csc.intellis.constants.ElementType;
import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.exception.IntelliSException;
import com.csc.intellis.parser.HTMLReader;

public class IntelliSExecutor_OLD {
	final static Logger logger = Logger.getLogger(IntelliSExecutor_OLD.class);
	private ExecutorParameterBean bean = null;
	String xpath;

	/**
	 * @param bean
	 */
	public IntelliSExecutor_OLD(ExecutorParameterBean bean) {
		this.bean = bean;
	}

	/**
	 * @param dataValue
	 * @param strSheetName
	 * @throws IntelliSException
	 */
	public void execute(Map<String, Map<String, String>> dataValue,
			String strSheetName, String fileName, Integer Sleepfactor)
			throws IntelliSException {

		try {

			List<String> xpathList = parsePageSrc(generatePgSrc(false));
			bean.setXpathList(xpathList);
			String screenShotDir = bean.getConfigMap().get(
					IntelliSConstants.ScreenShot_FILE_PATH);
			logger.info(screenShotDir);
			Map<String, Map<String, Integer>> sequenceDataMap = bean
					.getSequenceMap();
			String caseId = bean.getCaseId();
			Map<String, Integer> sequenceOrder = sequenceDataMap.get(caseId);
			Set<String> seqKeyset = sequenceOrder.keySet();
			Map<String, List<String>> labelData = new HashMap<String, List<String>>();
			DataResolver_Old object = new DataResolver_Old(bean, fileName);
			labelData = object.LabelDataResolver(dataValue, strSheetName, 0);

			Iterator<String> seqKeyItr = seqKeyset.iterator();
			Set<String> labelKeyset = labelData.keySet();
			String Parent_Window = bean.getDriver().getWindowHandle();

			for (int i = 0; i < labelKeyset.size(); i++) {

				if (seqKeyItr.hasNext()) {

					String name = "";
					String type = "";
					String seq = "1";
					String data = "";
					String skip = "0";
					String HtmlId = "";
					String keyword = "";
					String refresh = "";
					String HtmlName = "";
					String sleepTime = "";
					String HtmlXpath = "";
					String keyWord = seqKeyItr.next();

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
								sleepTime = labelVIterator.next();
								data = dataValue.get(caseId).get(keyword);

								logger.info("**********" + Parent_Window);
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
							if (name != "") {
								if (!type.equals(ElementType.ALERT.name())) {
									XPathResolver resolver = new XPathResolver(
											bean);
									xpath = resolver.resolve();
									bean.setXpath(xpath);
								}

								/**
								 * Highlights the Element
								 */
								String highLight = dataValue.get(caseId).get(
										"HighLight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight(xpath, "", "");
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								processor.process(name, "", "", "", "",
										Sleepfactor);

								if (!sleepTime.equals("")) {
									Thread.sleep(Long.parseLong(sleepTime));
								}

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValue.get(caseId).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {

										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														// + bean.getName()
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

								/**
								 * Highlights the Element
								 */
								String highLight = dataValue.get(caseId).get(
										"HighLight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight("", HtmlId, "");
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								logger.info(HtmlId);
								processor.process("", "", "", HtmlId, "",
										Sleepfactor);

								if (!sleepTime.equals("")) {
									Thread.sleep(Long.parseLong(sleepTime));
								}

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValue.get(caseId).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {

										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														// + bean.getName()
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

								/**
								 * Highlights the Element
								 */
								String highLight = dataValue.get(caseId).get(
										"HighLight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight("", "", HtmlName);
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								logger.info(HtmlName);
								processor.process("", HtmlName, "", "", "",
										Sleepfactor);

								if (!sleepTime.equals("")) {
									Thread.sleep(Long.parseLong(sleepTime));
								}

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValue.get(caseId).get(
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

								/**
								 * Highlights the Element
								 */
								String highLight = dataValue.get(caseId).get(
										"HighLight");
								if (highLight.equals("Yes")) {
									logger.info("XPATH FOR HIGHLIGHT IS :-    "
											+ xpath);
									Highlight(HtmlXpath, "", "");
								} else if (highLight.equals("")) {
									break;
								}

								WebElementProcessor processor = new WebElementProcessor(
										bean);
								logger.info(HtmlXpath);
								processor.process("", "", "", "", HtmlXpath,
										Sleepfactor);

								if (!sleepTime.equals("")) {
									Thread.sleep(Long.parseLong(sleepTime));
								}

								/**
								 * Takes Screen Shot
								 */
								screenShot = dataValue.get(caseId).get(
										"ScreenShot");
								if (screenShot.equals("Yes")) {

									if (refresh
											.contains(IntelliSConstants.REFRESH)) {

										takeScreenShot(
												bean.getDriver(),
												screenShotDir
														+ bean.getCaseId()
														+ "\\"
														// + bean.getName()
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
							} else if (type.contains("SWITCHTOCHILD")) {

								logger.info("Switch 2 CHILD");

								for (String Child_Window : bean.getDriver()
										.getWindowHandles()) {
									bean.getDriver().switchTo()
											.window(Child_Window);
									logger.info(bean.getDriver().switchTo()
											.window(Child_Window));
								}

							} else if (type.contains("SWITCHTOPARENT")) {

								logger.info("Switch 2 Parent");

								// Switching back to Parent Window
								bean.getDriver().switchTo()
										.window(Parent_Window);

							} else if (type.contains("ALERT")) {

								logger.info("Handling Alert");

								boolean presentFlag = false;
								presentFlag = isAlertPresent(bean.getDriver());
								WebDriver newdriver = bean.getDriver();
								Alert alert = null;
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
										logger.info("Not able to swtich to alert");
									}
								} else {
									logger.info("Couldn't switch!");
								}

								if (!sleepTime.equals("")) {
									Thread.sleep(Long.parseLong(sleepTime));
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
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Runtime error !!");
		}
	}

	/**
	 * @param driver
	 * @param destPath
	 */
	public static void takeScreenShot(WebDriver driver, String destPath) {
		try {
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(destPath));
		} catch (IOException e1) {
			e1.printStackTrace();
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
		}
		return presentFlag;
	}

	/**
	 * @return
	 * @param winReferesh
	 */
	private String generatePgSrc(boolean winReferesh) {
		PageSourceGenerator pageSrcGenerator = new PageSourceGenerator(
				bean.getDriver());
		return pageSrcGenerator.generate(winReferesh);
	}

	/**
	 * @return
	 * @param pgSrc
	 * @throws IntelliSException
	 */
	private List<String> parsePageSrc(String pgSrc) throws IntelliSException {
		try {
			HTMLReader reader = new HTMLReader();
			return reader.readHTML(pgSrc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntelliSException("501", "Error reading HTML page source");
		}
	}

	/**
	 * @param id
	 * @param name
	 * @param xpath
	 */
	private void Highlight(String xpath, String id, String name) {

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
}