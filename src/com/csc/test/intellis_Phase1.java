package com.csc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.ResultSet;
import com.csc.intellis.core.SortMapByValue;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.exception.IntelliSException;

public class intellis_Phase1 {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> caseDataMap = new LinkedHashMap<String, Map<String, String>>();
	static LinkedHashMap<String, Map<String, Integer>> caseSeqMap = new LinkedHashMap<String, Map<String, Integer>>();

	public static void main(String args[]) throws IntelliSException {

		String desc = null;
		ExecutorParameterBean bean = new ExecutorParameterBean();
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> statusReport = new ArrayList<String>();
		LinkedHashMap<String, Map<String, String>> dataValueMap = new LinkedHashMap<String, Map<String, String>>();

		try {
			HashMap<String, String> configMap = ConfigLoader.getInstance()
					.loadConfig();
			bean.setConfigMap(configMap);

			File folder = new File(bean.getConfigMap().get(
					IntelliSConstants.TEST_FILE_PATH));
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				String file1 = file.getName();
				System.out
						.println("Current File being accessed from the Folder is:-   "
								+ bean.getConfigMap().get(
										IntelliSConstants.TEST_FILE_PATH)
								+ "\\" + file1);

				fileInputS = new FileInputStream(new File(bean.getConfigMap()
						.get(IntelliSConstants.TEST_FILE_PATH) + file1));
				HSSFWorkbook workbook = new HSSFWorkbook(fileInputS);

				for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
					System.out
							.println("Total number of sheets in excel are:-   "
									+ workbook.getNumberOfSheets() + "\n"
									+ "current sheet is :-    "
									+ workbook.getSheetName(i));

					sheet = workbook.getSheetAt(i);
					dataValueMap.clear();

					dataValueMap = processData(sheet, bean, bean.getConfigMap()
							.get(IntelliSConstants.TEST_FILE_PATH)
							+ "\\"
							+ file1, 0, workbook.getSheetName(i));
					for (String key : dataValueMap.keySet()) {
						System.out.println(key + " " + dataValueMap.get(key));
					}

					List<String> keys = new ArrayList<String>(
							dataValueMap.keySet());
					Collections.sort(keys, new Comparator<String>() {

						@Override
						public int compare(String s1, String s2) {
							s1 = s1.split(" ")[1];
							s2 = s2.split(" ")[1];
							return Float.compare(Float.valueOf(s1),
									Float.valueOf(s2));
						}
					});

					for (String key : keys) {
						System.out.println(key);
					}
					int count = 0;

					for (int itr = 0; itr <= keys.size() - 1; itr++) {

						try {
							count++;
							System.out.println("itr   =  " + itr);
							String caseId = keys.get(itr);
							System.out.println("Case Id is    	     :- "
									+ caseId);
							bean.setCaseId(caseId);
							// IntelliSServer server = new IntelliSServer();
							String execute = dataValueMap.get(caseId).get(
									"Execute");
							if (execute.contains("Yes")) {
								driver = bean.getDriver();
								// IntelliSServer.spawn(dataValueMap, "", bean,
								// workbook.getSheetName(i) + "_Result",
								// driver, file1, workbook, "");
								String statusMsg = dataValueMap.get(caseId)
										.get("StatusText");
								desc = dataValueMap.get(caseId).get(
										"Description");
								System.out.println("status msg----->"
										+ statusMsg);
								Thread.sleep(2000);
								boolean pgCode = bean.getDriver()
										.getPageSource().replace(" ", "")
										.contains(statusMsg.replace(" ", ""));

								if (pgCode) {
									statusReport.add(bean.getCaseId());
									statusReport.add("PASS");
									statusReport.add("Status message found.");
									statusReport.add(desc);
								} else {
									statusReport.add(bean.getCaseId());
									statusReport.add("FAIL");
									statusReport
											.add("Status message not found.");
									statusReport.add(desc);

									/**
									 * Takes Screen Shot
									 */
									String screenShotDir = bean
											.getConfigMap()
											.get(IntelliSConstants.RESULT_FILE_PATH);
									File scrFile = ((TakesScreenshot) bean
											.getDriver())
											.getScreenshotAs(OutputType.FILE);
									FileUtils
											.copyFile(
													scrFile,
													new File(screenShotDir
															+ "\\FAILURES\\"
															+ bean.getCaseId()
															+ "\\"
															+ bean.getHtmlId()
															+ ".jpg"));
									// String executionBreak = dataValueMap.get(
									// caseId).get("Break");
									// if (executionBreak.contains("Yes")) {
									// System.out
									// .println("Breaking the entire suite execution");
									// cleanup(bean);
									// } else {
									// System.out
									// .println("Next test case is about to execute");
									// }
								}
							} else {
								System.out
										.println("Nope this Test Case is not executed as directed by the user");
								statusReport.add(bean.getCaseId());
								statusReport
										.add("Not Executing as user has selected NO");
								statusReport.add("");
								statusReport.add(desc);
							}
						} catch (Exception e) {
							statusReport.add(bean.getCaseId());
							statusReport.add("FAIL");
							statusReport.add("Runtime Exception :" + e);
							statusReport.add(desc);
						}
						result.add(statusReport);

						bean.setStatuslist(result);
						ResultSet result1 = new ResultSet(bean);
						String strSheetName = workbook.getSheetName(i)
								+ "_Result";
						System.out
								.println("Sheet Name Is:-    " + strSheetName);
						result1.writeResultsToOutExcel(strSheetName, result,
								count);

						result.clear();
						statusReport.clear();
					}
				}
			}
			cleanup(bean);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Test file not found!!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Test file not found!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Test file not found!!");
		}
	}

	/**
	 * @return
	 * @param bean
	 * @param sheet
	 */
	public static LinkedHashMap<String, Map<String, String>> processData(
			HSSFSheet sheet, ExecutorParameterBean bean, String filePath,
			int columnIndex, String sheetName) {

		try {
			int sheet2ColCount = sheet.getRow(0).getLastCellNum();

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator2 = sheet.iterator();
			List<String> keyWord = new ArrayList<String>();
			System.out.println("\n");
			if (rowIterator2.hasNext()) {

				HSSFRow rowData = (HSSFRow) rowIterator2.next();
				Iterator<Cell> cellIter = rowData.cellIterator();
				HSSFCell cellData = (HSSFCell) cellIter.next();
				String id = cellData.getStringCellValue().toString();
				System.out.println(id);

				while (cellIter.hasNext()) {
					HSSFCell cellData1 = (HSSFCell) cellIter.next();
					String cellvalue = cellData1.getStringCellValue()
							.toString();
					System.out.println(cellvalue);
					keyWord.add(cellvalue);
				}
				System.out.println("keyword: : :" + keyWord);
			}

			int rowCount = 0;
			ArrayList<String> arr = extractExcelContentByColumnIndex(filePath,
					columnIndex, sheetName);

			for (int i = 0; i <= arr.size() - 1; i++) {
				System.out.println("Current Case Id being accessed is :-"
						+ arr.get(i));

				while (rowIterator2.hasNext()) {

					rowCount++;
					System.out.println("Row Count : : : : " + rowCount);
					int cCount = 0;
					HSSFRow rowData = (HSSFRow) rowIterator2.next();
					Iterator<Cell> cellIter = rowData.cellIterator();
					Map<String, String> dataMap = new HashMap<String, String>();
					Map<String, Integer> seqMapData = new HashMap<String, Integer>();

					if (rowCount % 2 != 0) {

						HSSFCell caseData = (HSSFCell) cellIter.next();
						String caseValue = caseData.getStringCellValue();
						System.out.println(caseValue);

						while (cellIter.hasNext()) {
							if (cCount < sheet2ColCount - 1) {
								System.out.println(cCount);
								String cellvalue;
								HSSFCell cellData = (HSSFCell) cellIter.next();
								System.out.println("------" + cellData);

								if (cellData != null
										&& cellData.getCellType() != Cell.CELL_TYPE_BLANK) {

									switch (cellData.getCellType()) {

									case Cell.CELL_TYPE_NUMERIC:
										cellvalue = String.valueOf(cellData
												.getNumericCellValue());
										dataMap.put(keyWord.get(cCount),
												cellvalue);
										break;

									case Cell.CELL_TYPE_BOOLEAN:
										cellvalue = String.valueOf(cellData
												.getBooleanCellValue());
										dataMap.put(keyWord.get(cCount),
												cellvalue);
										break;

									case Cell.CELL_TYPE_STRING:
										cellvalue = cellData
												.getStringCellValue();
										dataMap.put(keyWord.get(cCount),
												cellvalue);
										break;

									case Cell.CELL_TYPE_BLANK:
										cellvalue = cellData
												.getStringCellValue();
										dataMap.put(keyWord.get(cCount),
												cellvalue);
										break;
									}
								}
							}
							cCount++;
						}
						caseDataMap.put("Case " + arr.get(i), dataMap);
					} else {
						HSSFCell caseData = (HSSFCell) cellIter.next();
						String caseSeqValue = caseData.getStringCellValue();
						System.out.println("caseSeqValue     " + caseSeqValue);

						while (cellIter.hasNext()) {

							if (cCount < sheet2ColCount - 1) {
								HSSFCell cellData = (HSSFCell) cellIter.next();
								String cellvalue = cellData
										.getStringCellValue();
								System.out.println(cellvalue);
								if (cellvalue != "") {
									int sequence = Integer.parseInt(cellvalue);
									seqMapData.put(keyWord.get(cCount),
											sequence);
								}
							}
							cCount++;
						}
						Map<String, Integer> sortedSeqMap = new HashMap<String, Integer>();
						sortedSeqMap = SortMapByValue.sortMap(seqMapData);
						System.out.println("====  " + arr.get(i) + "  ====");
						caseSeqMap.put("Case " + arr.get(i), sortedSeqMap);
						break;
					}
				}
			}

			System.out.println("Data map: : :----> " + caseSeqMap);
			fileInputS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		bean.setSequenceMap(caseSeqMap);
		return caseDataMap;
	}

	/**
	 * @param bean
	 */
	public static void cleanup(ExecutorParameterBean bean) {

		driver = bean.getDriver();
		driver.quit();
		try {
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param browserType
	 */
	public static void killBrowser(String browserType) {

		if (browserType.contains("IE")) {
			System.out.println("Killing IE Browser");
			try {
				Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
				Thread.sleep(5000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (browserType.contains("Chrome")) {
			System.out.println("Killing Chrome Browser");
			try {
				Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
				Thread.sleep(5000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (browserType.contains("firefox")) {
			System.out.println("Killing Chrome Browser");
			try {
				Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
				Thread.sleep(5000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param filePath
	 * @param sheetName
	 * @param columnIndex
	 * @return
	 */
	public static ArrayList<String> extractExcelContentByColumnIndex(
			String filePath, int columnIndex, String sheetName) {

		ArrayList<String> columndata = null;
		try {
			File f = new File(filePath);
			FileInputStream ios = new FileInputStream(f);
			HSSFWorkbook workbook = new HSSFWorkbook(ios);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();
			columndata = new ArrayList<String>();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if (row.getRowNum() > 0) { // To filter column headings
						if (cell.getColumnIndex() == columnIndex) {// To match
																	// column
																	// index
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								columndata.add(cell.getNumericCellValue() + "");
								break;
							case Cell.CELL_TYPE_STRING:
								columndata.add(cell.getStringCellValue());
								break;
							}
						}
					}
				}
			}
			ios.close();
			System.out.println(columndata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columndata;
	}
}