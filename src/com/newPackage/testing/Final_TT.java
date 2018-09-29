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

public class Final_TT {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	// static int maxIteration = 5;
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

				// String filepaths = bean.getConfigMap().get(
				// IntelliSConstants.TEST_FILE_PATH)
				// + "\\" + file1;

				int lastRow = sheet.getLastRowNum();
				System.out.println(lastRow);
				for (int t = 0; t <= lastRow - 9; t++) {

					LinkedHashMap<String, Map<String, String>> map = null;
					map = testCaseSectionMap1(t);
					System.out.println(map);
				}
			}
		}
	}

	public static LinkedHashMap<String, Map<String, String>> testCaseSectionMap1(
			int index) {

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		LinkedHashMap<String, Map<String, String>> bigMap = new LinkedHashMap<String, Map<String, String>>();
		int min = 9 * index + 0; // 0:1,1:10,2:19
		int max = 9 * index + 9; // 0:9,1:18,2:27
		System.out.println("min:-" + "\t" + min + "\t" + "max:" + "\t" + max);

		// int count = 0;
		for (int i = min; i <= max - 1; i++) {
			Row row;
			if ((i == 0)) {
				row = sheet.getRow(i);
			} else if ((i == 1)) {
				row = sheet.getRow(i + 1);
			} else {
				row = sheet.getRow(i + 2);
			}
			// System.out.println("Value of i is:-" + "\t" + i);

			for (int z = 0; z <= row.getLastCellNum() - 1; z++) {
				// System.out.println("Value of z is:-" + "\t" + z);

				Cell cell = row.getCell(z);
				String stringValue = cell.getStringCellValue();
				// System.out.println(stringValue);

				Row row1 = sheet.getRow(row.getRowNum() + 1);
				Cell cell1 = row1.getCell(z);

				if (cell1 != null
						&& cell1.getCellType() != Cell.CELL_TYPE_BLANK) {
					switch (cell1.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						map.put(stringValue,
								String.valueOf(cell1.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						map.put(stringValue,
								String.valueOf(cell1.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						map.put(stringValue, cell1.getStringCellValue());

						System.out.println(stringValue + "\t"
								+ cell1.getStringCellValue());
						break;
					case Cell.CELL_TYPE_BLANK:
						map.put(stringValue, cell1.getStringCellValue());
						break;
					}
				} else {
					map.put(stringValue, "");
				}
			}
		}
		return bigMap;
	}

	public static Map<Integer, List<String>> ReadExcel() throws IOException {

		String cellobj = "";
		int rowCnt = 0;
		Map<Integer, List<String>> data = new HashMap<Integer, List<String>>();
		Iterator<Row> iterator = sheet.iterator();

		while (iterator.hasNext()) {

			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			List<String> obj = new ArrayList<String>();

			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();
				cellobj = cell.getStringCellValue();

				if ("".equals(cell.getStringCellValue())) {
					obj.add("Missing");

				} else if (cellobj.equals(null)) {
					obj.add("");

				} else {
					obj.add(cell.getStringCellValue());
				}
			}
			data.put(rowCnt, obj);
			rowCnt++;
		}
		return data;
	}

}