package com.csc.testing;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.csc.intellis.core.config.ConfigLoader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.csc.intellis.exception.IntelliSException;
import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;

public class TT {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static int maxIteration = 5;
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
					 + file1));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputS);

			for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
				System.out.println("Total number of sheets in excel are:-   "
						+ workbook.getNumberOfSheets() + "\n"
						+ "current sheet is :-    " + workbook.getSheetName(i));

				sheet = workbook.getSheetAt(i);

				bean.getConfigMap().get(IntelliSConstants.TEST_FILE_PATH);

				int lastRow = sheet.getLastRowNum();
				for (int t = 0; t <= lastRow; t++) {
				}
			}
		}
	}

	/**
	 * @return
	 * @param lastrownumber
	 */
	public static LinkedHashMap<String, String> headerRowMap(int lastrownumber,
			int rowNum) {

		LinkedHashMap<String, String> headerMap = new LinkedHashMap<String, String>();
		// Row row = sheet.getRow(0);// To read only the Properties
		Row row = sheet.getRow(rowNum);// To read only the Properties
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
		return headerMap;
	}

	// public static LinkedHashMap<String, Map<String, String>>
	// testCaseSectionMap1(
	// String filePath, HSSFWorkbook workbook, String sheetname,
	// int index) throws IOException {
	//
	// LinkedHashMap<String, String> map = null;
	// LinkedHashMap<String, String> headerMap = null;
	// LinkedHashMap<String, Map<String, String>> bigMap = new
	// LinkedHashMap<String, Map<String, String>>();
	// int min = 9*index+1; //0:1,1:10,2:19
	// int max = 9*index+9; //0:9,1:18,2:27
	// int j=0;
	// for (int i = min; i <= max; i++) {
	// //j==0 : 1st row
	//
	//
	// }
	// Row row = sheet.getRow(lastrowNumber);
	// int lastcellnumber = row.getLastCellNum(); // no of Columns
	// System.out.println("Last Cell Number" + lastcellnumber);
	// int lastrownumber = sheet.getLastRowNum(); // no of Rows
	// System.out.println("Last ROWS Number" + lastrownumber);
	//
	// // Lets get contents of the very first column
	// ArrayList<String> list = extractExcelContentByColumnIndex(filePath,
	// coulmnIndex, sheetname);
	// System.out.println(list);
	//
	// // Reading the header and storing it in a Linked Hash Map
	// if (lastrowNumber == 2) {
	// headerMap = headerRowMap(lastrowNumber - 1, rowNum);
	// } else {
	// headerMap = headerRowMap(lastrowNumber, rowNum);
	// }
	// System.out.println(headerMap);
	//
	// int count = 1;
	// int cellCount = 1;
	// for (int i = 0; i < list.size() - 1; i++) {// List-Iteration
	// for (int rowNumber = lastrowNumber; rowNumber < maxIteration;
	// rowNumber++) {// Row-Iteration
	//
	// System.out.println("==========================" + rowNumber);
	// // We want to initialize this map every time so that there is no
	// // reference which is being used
	// map = new LinkedHashMap<String, String>();
	// for (int index = 1; index < lastcellnumber; index++) {// Column-Iteration
	//
	// Cell cell1 = null;
	// String testString = null;
	// Row currentRow = null;
	//
	// if (cellCount == 1) {
	// currentRow = sheet.getRow(rowNumber);
	// } else if (cellCount == 2) {
	// currentRow = sheet.getRow(rowNumber + 1);
	// } else {
	// currentRow = sheet.getRow(rowNumber + 2);
	// }
	//
	// System.out.println(currentRow.getLastCellNum() + "===");
	// Cell cell = currentRow.getCell(index);
	// testString = cell.getStringCellValue();
	// System.out.println("\n" + testString);
	// Row row1 = sheet.getRow(currentRow.getRowNum() + 1);
	// if (row1 == null) {
	// System.out.println(currentRow.getLastCellNum() + "===");
	// row1 = sheet.getRow(currentRow.getRowNum() + 1 + 4);
	// System.out.println("row1 seems to be null   "
	// + row1.getRowNum());
	// cell1 = row1.getCell(index);
	// System.out.println(cell1.toString());
	// testString = cell1.getStringCellValue();
	// System.out.println(testString);
	// } else {
	// cell1 = row1.getCell(index);
	// System.out.println(cell1.toString());
	// }
	//
	// // Adding values to map as in accordance to its
	// // nature(i.e Numeric, Boolean, String, Blank, etc.)
	// if (cell1 != null
	// && cell1.getCellType() != Cell.CELL_TYPE_BLANK) {
	//
	// switch (cell1.getCellType()) {
	// case Cell.CELL_TYPE_NUMERIC:
	// map.put(testString,
	// String.valueOf(cell1.getNumericCellValue()));
	// break;
	// case Cell.CELL_TYPE_BOOLEAN:
	// map.put(testString,
	// String.valueOf(cell1.getBooleanCellValue()));
	// break;
	// case Cell.CELL_TYPE_STRING:
	// map.put(testString, cell1.getStringCellValue());
	// break;
	// case Cell.CELL_TYPE_BLANK:
	// map.put(testString, cell1.getStringCellValue());
	// break;
	// }
	// } else {
	// // Adding empty value to map
	// map.put(testString, "");
	// }
	// }// Column-Iteration
	//
	// // if (!(count > 2)) {
	// if (!(count > rowNumber)) {
	// bigMap.put(list.get(count + 1), map);
	// }
	// count++;
	// cellCount++;
	//
	// }// Row-Iteration
	//
	// }// List-Iteration
	//
	// // Adding the header to the existing map
	// bigMap.put("Header_" + headerMap.get("CaseId"), headerMap);
	//
	// // Return bigMap
	// return bigMap;
	// }

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
			String filePath, HSSFWorkbook workbook, String sheetname,
			int coulmnIndex, int lastrowNumber, int rowNum) throws IOException {

		LinkedHashMap<String, String> map = null;
		LinkedHashMap<String, String> headerMap = null;
		LinkedHashMap<String, Map<String, String>> bigMap = new LinkedHashMap<String, Map<String, String>>();
		Row row = sheet.getRow(lastrowNumber);
		int lastcellnumber = row.getLastCellNum(); // no of Columns
		System.out.println("Last Cell Number" + lastcellnumber);
		int lastrownumber = sheet.getLastRowNum(); // no of Rows
		System.out.println("Last ROWS Number" + lastrownumber);

		// Lets get contents of the very first column
		ArrayList<String> list = extractExcelContentByColumnIndex(filePath,
				coulmnIndex, sheetname);
		System.out.println(list);

		// Reading the header and storing it in a Linked Hash Map
		if (lastrowNumber == 2) {
			headerMap = headerRowMap(lastrowNumber - 1, rowNum);
		} else {
			headerMap = headerRowMap(lastrowNumber, rowNum);
		}
		System.out.println(headerMap);

		int count = 1;
		int cellCount = 1;
		for (int i = 0; i < list.size() - 1; i++) {// List-Iteration
			for (int rowNumber = lastrowNumber; rowNumber < maxIteration; rowNumber++) {// Row-Iteration

				System.out.println("==========================" + rowNumber);
				// We want to initialize this map every time so that there is no
				// reference which is being used
				map = new LinkedHashMap<String, String>();
				for (int index = 1; index < lastcellnumber; index++) {// Column-Iteration

					Cell cell1 = null;
					String testString = null;
					Row currentRow = null;

					if (cellCount == 1) {
						currentRow = sheet.getRow(rowNumber);
					} else if (cellCount == 2) {
						currentRow = sheet.getRow(rowNumber + 1);
					} else {
						currentRow = sheet.getRow(rowNumber + 2);
					}

					System.out.println(currentRow.getLastCellNum() + "===");
					Cell cell = currentRow.getCell(index);
					testString = cell.getStringCellValue();
					System.out.println("\n" + testString);
					Row row1 = sheet.getRow(currentRow.getRowNum() + 1);
					if (row1 == null) {
						System.out.println(currentRow.getLastCellNum() + "===");
						row1 = sheet.getRow(currentRow.getRowNum() + 1 + 4);
						System.out.println("row1 seems to be null   "
								+ row1.getRowNum());
						cell1 = row1.getCell(index);
						System.out.println(cell1.toString());
						testString = cell1.getStringCellValue();
						System.out.println(testString);
					} else {
						cell1 = row1.getCell(index);
						System.out.println(cell1.toString());
					}

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

				// if (!(count > 2)) {
				if (!(count > rowNumber)) {
					bigMap.put(list.get(count + 1), map);
				}
				count++;
				cellCount++;

			}// Row-Iteration

		}// List-Iteration

		// Adding the header to the existing map
		bigMap.put("Header_" + headerMap.get("CaseId"), headerMap);

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

					if (row.getRowNum() >= 0) { // To filter column headings
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
}