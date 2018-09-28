package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class readMultipleTestCases {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> caseDataMap = new LinkedHashMap<String, Map<String, String>>();
	static LinkedHashMap<String, Map<String, Integer>> caseSeqMap = new LinkedHashMap<String, Map<String, Integer>>();

	public static void main(String[] args) throws IOException,
			IntelliSException {

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

				String filepaths = bean.getConfigMap().get(
						IntelliSConstants.TEST_FILE_PATH)
						+  file1;

				LinkedHashMap<String, Map<String, String>> map = readMultipleTCFromXL(
						filepaths, workbook, workbook.getSheetName(i));
				System.out.println(map);
			}
		}
	}

	/**
	 * @return
	 * @param filePath
	 * @param workbook
	 * @param sheetname
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public static LinkedHashMap<String, Map<String, String>> readMultipleTCFromXL(
			String filePath, HSSFWorkbook workbook, String sheetname)
			throws IOException {

		LinkedHashMap<String, String> map = null;
		LinkedHashMap<String, String> headerMap = null;
		LinkedHashMap<String, Map<String, String>> bigMap = new LinkedHashMap<String, Map<String, String>>();
		Row row = sheet.getRow(2);
		int lastcellnumber = row.getLastCellNum(); // no of Columns
		System.out.println("Last Cell Number" + lastcellnumber);
		int lastrownumber = sheet.getLastRowNum(); // no of Rows
		System.out.println("Last ROWS Number" + lastrownumber);

		// Lets get contents of the very first column
		ArrayList<String> list = readXLDesign.extractExcelContentByColumnIndex(
				filePath, 0, sheetname);

		// Reading the header and storing it in a Linked Hash Map
		// headerMap = readXLDesign.headerRowMap(1);

		int count = 0;
		int cellCount = 1;
		for (int i = 0; i < list.size() - 1; i++) {// List-Iteration
			for (int rowNumber = 2; rowNumber < 5; rowNumber++) {// Row-Iteration

				// We want to initialize this map every time so that there is no
				// reference which is being used
				map = new LinkedHashMap<String, String>();
				for (int index = 1; index < lastcellnumber; index++) {// Column-Iteration

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
					String testString = cell.getStringCellValue();
					System.out.println("\n" + testString);
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

				if (!(count > 2)) {
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

}