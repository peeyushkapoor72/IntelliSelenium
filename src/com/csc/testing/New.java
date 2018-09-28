package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;

public class New {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static int maxIteration = 5;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> caseDataMap = new LinkedHashMap<String, Map<String, String>>();
	static LinkedHashMap<String, Map<String, Integer>> caseSeqMap = new LinkedHashMap<String, Map<String, Integer>>();

	public static void main(String[] args) throws Exception {

		ExecutorParameterBean bean = new ExecutorParameterBean();
		HashMap<String, String> configMap = ConfigLoader.getInstance().loadConfig();
		bean.setConfigMap(configMap);

		File folder = new File(bean.getConfigMap().get(IntelliSConstants.TEST_FILE_PATH));
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			String file1 = file.getName();
			System.out.println("Current File being accessed from the Folder is:-   "
					+ bean.getConfigMap().get(IntelliSConstants.TEST_FILE_PATH) + "\\" + file1);

			fileInputS = new FileInputStream(
					new File(bean.getConfigMap().get(IntelliSConstants.TEST_FILE_PATH) + file1));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputS);
			for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
				System.out.println("Total number of sheets in excel are:-   " + workbook.getNumberOfSheets() + "\n"
						+ "current sheet is :-    " + workbook.getSheetName(i));

				sheet = workbook.getSheetAt(i);

				int lastRow = sheet.getLastRowNum();

				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				LinkedList<String> keyList = new LinkedList<String>();
				LinkedList<String> valueList = new LinkedList<String>();

				for (int t = 0; t <= lastRow; t++) {

					System.out.println("current row being accessed" + "\t" + t);

					if (keyList.size() == 0) {
						keyList = readXL(t);
						System.out.println(keyList);
					} else {
						valueList = readXL(t);
						System.out.println(valueList);
						for (int p = 0; p <= keyList.size() - 1; p++) {
							System.out.println(
									"Key:-" + "\t" + keyList.get(p) + "\t" + "Value:-" + "\t" + valueList.get(p));
							map.put(keyList.get(p), valueList.get(p));
						}
						System.out.println(map);
						keyList.clear();
						valueList.clear();
					}
				}
				// System.out.println("***" + "\n");
				// for (int p = 0; p <= keyList.size() - 1; p++) {
				// System.out.println("Key:-" + "\t" + keyList.get(p) + "\t"
				// + "Value:-" + "\t" + valueList.get(p));
				// map.put(keyList.get(p), valueList.get(p));
				// }
			}
		}
	}

	public static LinkedList<String> readXL(int index) throws Exception, FileNotFoundException, IOException {

		String filename = "C:\\Users\\pkapoor22\\Desktop\\ER_Automation\\excel\\KKK\\TC\\Validation_1.xls";
		LinkedList<String> dataList = null;
		Workbook workbook = WorkbookFactory.create(new FileInputStream(filename));
		Sheet sheet = workbook.getSheet("Sheet2");
		Row row = sheet.getRow(index);

		if (row != null) {
			// for (row : sheet) {// Row Iteration

			int crrRowNum = row.getRowNum();
			System.out.println(crrRowNum + "^^^^^^^^^^^^^^^^^^^^^");
			if ((crrRowNum % 2) == 0) {// Checking if row is Even or Odd

				// evenDataList
				dataList = new LinkedList<String>();
				for (Cell cell : row) { // Cell-Wise Iteration

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						System.out.println(cell.getRichStringCellValue().getString());
						// evenDataList.add(cell.getRichStringCellValue().toString());
						dataList.add(cell.getRichStringCellValue().toString());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							System.out.println(cell.getDateCellValue());
							// evenDataList
							// .add(String.valueOf(cell.getDateCellValue()));
							dataList.add(String.valueOf(cell.getDateCellValue()));
						} else {
							System.out.println(cell.getNumericCellValue());
							// evenDataList.add(String.valueOf(cell
							// .getNumericCellValue()));
							dataList.add(String.valueOf(cell.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue());
						// evenDataList
						// .add(String.valueOf(cell.getBooleanCellValue()));
						dataList.add(String.valueOf(cell.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_FORMULA:
						System.out.println(cell.getCellFormula());
						// evenDataList.add(String.valueOf(cell.getCellFormula()));
						dataList.add(String.valueOf(cell.getCellFormula()));
						break;
					default:
						System.out.println();
					}
				}

			} else {

				dataList = new LinkedList<String>();
				for (Cell cell : row) { // Cell-Wise Iteration

					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						System.out.println(cell.getRichStringCellValue().getString());
						dataList.add(cell.getRichStringCellValue().toString());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							System.out.println(cell.getDateCellValue());
							dataList.add(String.valueOf(cell.getDateCellValue()));
						} else {
							System.out.println(cell.getNumericCellValue());
							dataList.add(String.valueOf(cell.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue());
						dataList.add(String.valueOf(cell.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_FORMULA:
						System.out.println(cell.getCellFormula());
						dataList.add(String.valueOf(cell.getCellFormula()));
						break;
					default:
						System.out.println("NULL");
						dataList.add("Null");
					}
				}
				// break;
			}
		}
		// for (int p = 0; p <= evenDataList.size(); p++) {
		// for (int d = 0; d <= dataList.size(); d++) {
		// System.out.println("Key:-" + "\t" + evenDataList.get(p)
		// + "\t" + "Value:-" + "\t" + dataList.get(d));
		// map.put(evenDataList.get(p), dataList.get(d));
		// }
		// }

		// }
		// for (int p = 0; p <= evenDataList.size() - 1; p++) {
		// System.out.println("Key:-" + "\t" + evenDataList.get(p) + "\t"
		// + "Value:-" + "\t" + dataList.get(p));
		// map.put(evenDataList.get(p), dataList.get(p));
		// }
		return dataList;
	}
}