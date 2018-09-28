package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.WebDriver;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.exception.IntelliSException;

public class NewStart {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> caseDataMap = new LinkedHashMap<String, Map<String, String>>();
	static LinkedHashMap<String, Map<String, Integer>> caseSeqMap = new LinkedHashMap<String, Map<String, Integer>>();

	public static void main(String[] args) throws IntelliSException,
			IOException {

		ExecutorParameterBean bean = new ExecutorParameterBean();
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
							+ "\\"
							+ file1);

			fileInputS = new FileInputStream(new File(bean.getConfigMap().get(
					IntelliSConstants.TEST_FILE_PATH)
					+ "\\" + file1));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputS);

			for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
				System.out.println("Total number of sheets in excel are:-   "
						+ workbook.getNumberOfSheets() + "\n"
						+ "current sheet is :-    " + workbook.getSheetName(i));

				sheet = workbook.getSheetAt(i);

				// String filepaths = bean.getConfigMap().get(
				// IntelliSConstants.TEST_FILE_PATH)
				// + "\\" + file1;

				// LinkedHashMap<String, Map<String, String>> map = null;

				// map = testCaseSectionMap(filepaths, workbook,
				// workbook.getSheetName(i), 1, 0);
				// System.out.println(map);
			}
		}
	}

	public static LinkedHashMap<String, Map<String, String>> testCase(
			String filePath, HSSFWorkbook workbook, String sheetname,
			int lastrowNumber, int rowNum, int index) {

		// LinkedHashMap<String, String> testCaseMap = null;
		// LinkedHashMap<String, String> navigationMap = null;
		// LinkedHashMap<String, String> verificationMap = null;
		LinkedHashMap<String, Map<String, String>> masterMap = new LinkedHashMap<String, Map<String, String>>();

		int min = 9 * index + 0; // 0:1,1:10,2:19
		int max = 9 * index + 9; // 0:9,1:18,2:27
		System.out.println("min:-" + "\t" + min + "\t" + "max:" + "\t" + max);

		// int count = 0;
		// Lets get contents of the very first column
		ArrayList<String> list = extractExcelContentByColumnIndex(filePath, 0,
				sheetname);
		for (int k = 0; k < list.size() - 1; k++) {// List-Iteration
			for (int i = min; i <= max - 1; i++) {
				new LinkedHashMap<String, String>();

				headerMap(lastrowNumber, rowNum);
			}
		}
		return masterMap;
	}

	/**
	 * @return
	 * @param filePath
	 * @param sheetName
	 * @param columnIndex
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
			}
			ios.close();
			System.out.println(columndata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columndata;
	}

	/**
	 * @return
	 * @param lastRow
	 */
	public static LinkedHashMap<String, String> readTestCase(int lastRow,
			int rowNum) {

		LinkedHashMap<String, String> testCaseMap = new LinkedHashMap<String, String>();
		Row row = sheet.getRow(0);// To read only the Properties
		int lastcellnumber = row.getLastCellNum(); // no of Columns

		for (int rowNumber = rowNum; rowNumber < lastRow; rowNumber++) {
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
						testCaseMap.put(cellContent,
								String.valueOf(cell1.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						testCaseMap.put(cellContent,
								String.valueOf(cell1.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						testCaseMap
								.put(cellContent, cell1.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						testCaseMap
								.put(cellContent, cell1.getStringCellValue());
						break;
					}
				}
			}
		}
		return testCaseMap;
	}

	/**
	 * @return
	 * @param lastRow
	 */
	public static LinkedHashMap<String, String> headerMap(int lastRow,
			int rowNum) {

		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		Row row = sheet.getRow(0);// To read only the Properties
		int lastcellnumber = row.getLastCellNum(); // no of Columns

		for (int rowNumber = rowNum; rowNumber < lastRow; rowNumber++) {
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
		return headerMap;
	}

}