package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.IntelliSServer;
import com.csc.intellis.core.ResultSet;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.exception.IntelliSException;

public class readXLDesign {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> navigation = null;
	static LinkedHashMap<String, Map<String, String>> verification = null;
	static LinkedHashMap<String, Map<String, String>> TestCaseExecution = null;

	public static void main(String[] args) throws IntelliSException,
			IOException {

		String desc = null;
		boolean bool = false;
		List<String> statusReport = new ArrayList<String>();
		ExecutorParameterBean bean = new ExecutorParameterBean();
		List<List<String>> result = new ArrayList<List<String>>();
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
								 + file1);

				fileInputS = new FileInputStream(new File(bean.getConfigMap()
						.get(IntelliSConstants.TEST_FILE_PATH)  + file1));
				HSSFWorkbook workbook = new HSSFWorkbook(fileInputS);

				for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
					System.out
							.println("Total number of sheets in excel are:-   "
									+ workbook.getNumberOfSheets() + "\n"
									+ "current sheet is :-    "
									+ workbook.getSheetName(i));

					sheet = workbook.getSheetAt(i);

					String filepaths = bean.getConfigMap().get(
							IntelliSConstants.TEST_FILE_PATH)
							 + file1;
					dataValueMap = testCaseSectionMap(filepaths, workbook,
							workbook.getSheetName(i));

					for (Entry<String, Map<String, String>> entry : dataValueMap
							.entrySet()) {
						System.out.println(entry.getKey());
						Map<String, String> internalMap = entry.getValue();
						for (Entry<String, String> internalEntry : internalMap
								.entrySet()) {
							System.out.println(internalEntry.getKey());
							// System.out.println(internalEntry.getValue());
						}
					}

					List<String> keys = new ArrayList<String>(dataValueMap.keySet());
					for (String key : keys) {
						System.out.println(key);
					}
					System.out.println(">>>>>>>>=======" + keys.size());
					int count = 0;
					for (int itr = 3; itr < keys.size(); itr++) {

						try {
							count++;
							System.out.println("itr   =  " + itr);
							String header = keys.get(itr);
							System.out.println("Case Id is    	     :- "
									+ header);
							bean.setCaseId(header);
							String execute = dataValueMap.get(header).get(
									"Execute");

							// initializing the maps Every-Time
							navigation = new LinkedHashMap<String, Map<String, String>>();
							verification = new LinkedHashMap<String, Map<String, String>>();
							TestCaseExecution = new LinkedHashMap<String, Map<String, String>>();
							driver = bean.getDriver();

							if (execute.equals("Yes")) {

								TestCaseExecution.put(keys.get(itr - 3),
										dataValueMap.get(keys.get(itr - 3)));
								navigation.put(keys.get(itr - 2),
										dataValueMap.get(keys.get(itr - 2)));
								verification.put(keys.get(itr - 1),
										dataValueMap.get(keys.get(itr - 1)));
								System.out.println(TestCaseExecution);
								System.out.println(navigation);
								System.out.println(verification);

								System.out.println("Testcase=" + (itr - 3));
								System.out.println("Navigation=" + (itr - 2));
								System.out.println("Verification=" + (itr - 1));

								try {
									bool = IntelliSServer.spawn(
											TestCaseExecution, dataValueMap,
											bean, workbook.getSheetName(i),
											driver, file1, workbook, (itr - 3));
									if (bool == true) {
										WebDriver driver1 = bean.getDriver();
										bool = IntelliSServer.spawn(navigation,
												dataValueMap, bean,
												workbook.getSheetName(i),
												driver1, file1, workbook,
												(itr - 2));
										if (bool == true) {
											bool = IntelliSServer.spawn(
													verification, dataValueMap,
													bean,
													workbook.getSheetName(i),
													driver1, file1, workbook,
													(itr - 1));
											if (bool == true) {
												statusReport.add(bean
														.getCaseId());
												statusReport.add("PASS");
												statusReport
														.add("Status message found.");
												statusReport.add(desc);
											} else {// If Verification Fails
												statusReport.add(bean
														.getCaseId());
												statusReport.add("FAIL");
												statusReport
														.add("Verification failed.");
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
																new File(
																		screenShotDir
																				+ "\\FAILURES\\"
																				+ bean.getCaseId()
																				+ "\\"
																				+ bean.getHtmlId()
																				+ ".jpg"));
											}
										} else {// If Navigation Fails
											statusReport.add(bean.getCaseId());
											statusReport.add("FAIL");
											statusReport
													.add("Navigation failed.");
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
											FileUtils.copyFile(
													scrFile,
													new File(screenShotDir
															+ "\\FAILURES\\"
															+ bean.getCaseId()
															+ "\\"
															+ bean.getHtmlId()
															+ ".jpg"));
										}
									} else {// If Test Execution fails
										statusReport.add(bean.getCaseId());
										statusReport.add("FAIL");
										statusReport
												.add("Test Case Execution Failed.");
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
										FileUtils.copyFile(
												scrFile,
												new File(screenShotDir
														+ "\\FAILURES\\"
														+ bean.getCaseId()
														+ "\\"
														+ bean.getHtmlId()
														+ ".jpg"));
									}
								} catch (Exception ex) {
									statusReport.add(bean.getCaseId());
									statusReport.add("FAIL");
									statusReport.add("Test Case Failed.");
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
							itr = itr + 3;
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
	 * @param lastrownumber
	 */
	public static LinkedHashMap<String, String> headerRowMap(int lastrownumber,
			int rowNum) {

		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		Row row = sheet.getRow(0);// To read only the Properties
		int lastcellnumber = row.getLastCellNum(); // no of Columns

		for (int rowNumber = rowNum; rowNumber < lastrownumber; rowNumber++) {
			for (int index = 0; index <= lastcellnumber - 1; index++) {

				Cell cell = row.getCell(index);
				String cellContent = cell.getStringCellValue();
				System.out.println(cellContent);
				Row row1 = sheet.getRow(rowNumber + 1);
				Cell cell1 = row1.getCell(index);

				if (cell1 != null
						&& cell1.getCellType() != Cell.CELL_TYPE_BLANK) {

					switch (cell1.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						headerMap.put(cellContent,
								String.valueOf(cell1.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						headerMap.put(cellContent,
								String.valueOf(cell1.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						headerMap.put(cellContent, cell1.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						headerMap.put(cellContent, cell1.getStringCellValue());
						break;
					}
				}
			}
		}
		System.out.println(headerMap);
		return headerMap;
	}

	/**
	 * @return
	 * @param filePath
	 * @param workbook
	 * @param sheetname
	 * @param coulmnIndex
	 * @param rowNumber
	 * @throws IOException
	 */
	public static LinkedHashMap<String, Map<String, String>> testCaseSectionMap(
			String filePath, HSSFWorkbook workbook, String sheetname)
			throws IOException {

		LinkedHashMap<String, String> map = null;
		LinkedHashMap<String, String> headerMap = null;
		LinkedHashMap<String, Map<String, String>> bigMap = new LinkedHashMap<String, Map<String, String>>();

		// Lets get contents of the very first column
		ArrayList<String> list = extractExcelContentByColumnIndex(filePath, 0,
				sheetname);
		System.out.println(">>>>>>>>>>>>>>>>" + "\t" + (list.size() / 5));
		int count = 2;
		int countdiff = 1;
		int min = 2;
		int max = 4;
		for (int i = 1; i <= (list.size() / 5); i++) {// List-Iteration

			if (countdiff > 1) {
				min = min + 9;
				max = max + 9;
				count = count + 2;
			}

			int cellCount = 1;
			// Reading the header and storing it in a Linked Hash Map
			headerMap = headerRowMap(min - 1, min - 2);
			Row row = sheet.getRow(min);
			int lastcellnumber = row.getLastCellNum(); // no of Columns
			System.out.println("Last Cell Number:-" + "\t" + lastcellnumber);
			int rowCount = 0;
			for (int rowNumber = min; rowNumber <= max; rowNumber++) {// Row-Iteration

				System.out.println("Current Row is :-" + "\t" + rowNumber);

				// We want to initialize this map every time so that there is no
				// reference which is being used
				map = new LinkedHashMap<String, String>();
				for (int index = 1; index < sheet.getRow(rowNumber + rowCount)
						.getLastCellNum(); index++) {// Column-Iteration

					Row currentRow = null;

					if (cellCount == 1) {
						currentRow = sheet.getRow(rowNumber);
					} else if (cellCount == 2) {
						currentRow = sheet.getRow(rowNumber + 1);
					} else {
						currentRow = sheet.getRow(rowNumber + 2);
					}

					Cell cell = currentRow.getCell(index);
					String testString = cell.getStringCellValue();
					System.out.println("\n>>>>>" + testString);
					Row row1 = sheet.getRow(currentRow.getRowNum() + 1);
					Cell cell1 = row1.getCell(index);

					// Adding values to map as in accordance to its
					// nature(i.e Numeric, Boolean, String, Blank, etc.)
					if (cell1 != null
							&& cell1.getCellType() != Cell.CELL_TYPE_BLANK) {

						switch (cell1.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							map.put(testString,
									String.valueOf(cell1.getNumericCellValue()));
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							map.put(testString,
									String.valueOf(cell1.getBooleanCellValue()));
							break;
						case Cell.CELL_TYPE_STRING:
							map.put(testString, cell1.getStringCellValue());
							break;
						case Cell.CELL_TYPE_BLANK:
							map.put(testString, cell1.getStringCellValue());
							break;
						}
					} else {
						// Adding empty value to map
						map.put(testString, "");
					}

				}// Column-Iteration
				System.out.println(map);

				System.out.println(">>>>>>>><<<<<<<<<<" + "\t" + count);
				bigMap.put(list.get(count++) + "" + i, map);
				System.out.println(bigMap);
				cellCount++;
				rowCount++;
			}// Row-Iteration

			// Adding the header to the existing map
			bigMap.put("Header_" + headerMap.get("CaseId"), headerMap);
			System.out.println(bigMap);
			countdiff++;

		}// List-Iteration

		// Return bigMap
		return bigMap;
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

					if (cell.getColumnIndex() == columnIndex) {// Column-Index
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
			ios.close();
			System.out.println(columndata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columndata;
	}

	/**
	 * @param workbook
	 * @param rowIndex
	 * @param cellIndex
	 * @param sheetName
	 */
	public static Cell specificCellValue(int cellIndex, int rowIndex,
			String sheetName, HSSFWorkbook workbook) {

		Cell cell = null;
		CellReference ref = new CellReference("A" + cellIndex);
		Row row = sheet.getRow(ref.getRow());
		if (row != null) {
			cell = row.getCell(ref.getCol());
		}
		return cell;
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

}