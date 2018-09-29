package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

public class newTest {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static int maxIteration = 5;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> caseDataMap = new LinkedHashMap<String, Map<String, String>>();
	static LinkedHashMap<String, Map<String, Integer>> caseSeqMap = new LinkedHashMap<String, Map<String, Integer>>();

	public static void main(String[] args) throws IntelliSException,
			IOException, Exception {

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

				String filepaths = bean.getConfigMap().get(
						IntelliSConstants.TEST_FILE_PATH)
						+ "\\" + file1;
				String currentSheetName = workbook.getSheetName(i);

				int lastRow = sheet.getLastRowNum();
				System.out.println(lastRow);
				for (int t = 0; t <= lastRow; t++) {
					readexcel(filepaths, currentSheetName, t);
				}
			}
		}
	}

	static void readexcel(String filePath, String currentSheetName, int index)
			throws IOException {

		FileInputStream file = new FileInputStream(new File(filePath));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheet(currentSheetName);
		List<String> list = null;

		Iterator<Row> rowIterator = sheet.iterator();
		int min = 9 * index + 0; // 0:1,1:10,2:19
		int max = 9 * index + 9; // 0:9,1:18,2:27
		for (int i = min; i <= max - 1; i++) {
			while (rowIterator.hasNext()) {

				list = new ArrayList<String>();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					if (cell != null
							&& cell.getCellType() != Cell.CELL_TYPE_BLANK) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							System.out.print(cell.getNumericCellValue() + "\t");
							list.add(String.valueOf(cell.getNumericCellValue()));
							break;
						case Cell.CELL_TYPE_STRING:
							System.out.print(cell.getStringCellValue() + "\t");
							list.add(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							System.out.print(cell.getBooleanCellValue() + "\t");
							list.add(String.valueOf(cell.getBooleanCellValue()));
							break;
						case Cell.CELL_TYPE_BLANK:
							System.out.print(cell.getStringCellValue() + "\t");
							list.add(cell.getStringCellValue());
							break;
						}
					} else {
						System.out.print("NULL" + "\t");
					}
				}
				System.out.println("");
				break;
			}
		}
	}
}