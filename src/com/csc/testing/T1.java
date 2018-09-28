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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.openqa.selenium.WebDriver;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.exception.IntelliSException;

public class T1 {

	static WebDriver driver;
	static HSSFSheet sheet = null;
	static FileInputStream fileInputS = null;
	static ExecutorParameterBean bean = null;
	static LinkedHashMap<String, Map<String, String>> caseDataMap = new LinkedHashMap<String, Map<String, String>>();
	static LinkedHashMap<String, Map<String, String>> first2RowDataMap = new LinkedHashMap<String, Map<String, String>>();

	public static void main(String[] args) throws IntelliSException,
			IOException {

		ExecutorParameterBean bean = new ExecutorParameterBean();
		LinkedHashMap<String, Map<String, String>> dataValueMap = new LinkedHashMap<String, Map<String, String>>();
		// LinkedHashMap<String, String> First2RowMap = new
		// LinkedHashMap<String, String>();

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
				sheet = workbook.getSheetAt(i);
				dataValueMap.clear();

				// dataValueMap = readOnlyFirst2Rows(sheet, bean, bean
				// .getConfigMap().get(IntelliSConstants.TEST_FILE_PATH)
				// + "\\" + file1, 0, workbook.getSheetName(i), workbook);

				dataValueMap = processData(sheet, bean, bean.getConfigMap()
						.get(IntelliSConstants.TEST_FILE_PATH) + "\\" + file1,
						0, workbook.getSheetName(i), workbook);
				for (String key : dataValueMap.keySet()) {
					System.out.println(key + " " + dataValueMap.get(key));
				}
			}
		}
	}

	/**
	 * @return
	 * @param bean
	 * @param sheet
	 */
	public static LinkedHashMap<String, Map<String, String>> processData(
			HSSFSheet sheet, ExecutorParameterBean bean, String filePath,
			int columnIndex, String sheetName, HSSFWorkbook workbook) {

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

				String id = null;
				if (cellData != null
						&& cellData.getCellType() != Cell.CELL_TYPE_BLANK) {

					switch (cellData.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						id = String.valueOf(cellData.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						id = String.valueOf(cellData.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						id = cellData.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						id = cellData.getStringCellValue();
						break;
					}
				}
				System.out.println(id);

				while (cellIter.hasNext()) {
					HSSFCell cellData1 = (HSSFCell) cellIter.next();

					String cellvalue = null;
					if (cellData1 != null
							&& cellData1.getCellType() != Cell.CELL_TYPE_BLANK) {

						switch (cellData1.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							cellvalue = String.valueOf(cellData1
									.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							cellvalue = String.valueOf(cellData1
									.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							cellvalue = cellData1.getStringCellValue();
							break;
						case Cell.CELL_TYPE_BLANK:
							cellvalue = cellData1.getStringCellValue();
							break;
						}
					}
					System.out.println(cellvalue);
					keyWord.add(cellvalue);
				}
				System.out.println("keyword: : :" + keyWord);
			}

			int rowCount = 0;
			ArrayList<String> arr = extractExcelContentByColumnIndex(filePath,
					columnIndex, sheetName);

			Cell cv = specificCellValue(2, 2, sheetName, workbook);
			String tcValue = String.valueOf(cv.getStringCellValue());
			System.out.println(tcValue);

			for (int i = 0; i <= arr.size() - 1; i++) {
				System.out.println("Current section being accessed is :-"
						+ arr.get(i));

				while (rowIterator2.hasNext()) {

					rowCount++;
					System.out.println("Row Count : : : : " + rowCount);
					int cCount = 0;
					HSSFRow rowData = (HSSFRow) rowIterator2.next();
					Iterator<Cell> cellIter = rowData.cellIterator();
					Map<String, String> dataMap = new HashMap<String, String>();
					// Map<String, Integer> seqMapData = new HashMap<String,
					// Integer>();

					if (rowCount % 2 != 0) {

						HSSFCell caseData = (HSSFCell) cellIter.next();
						String caseValue = caseData.getStringCellValue();
						System.out.println(caseValue);

						while (cellIter.hasNext()) {
							// int colCount = sheet.getRow(rowCount)
							// .getLastCellNum();
							if (cCount < sheet2ColCount - 1) {
								// if (cCount < colCount - 1) {
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
						// caseDataMap.put("Case " + arr.get(i), dataMap);
						caseDataMap.put("Case " + tcValue, dataMap);
					} else {
						// HSSFCell caseData = (HSSFCell) cellIter.next();
						// String caseSeqValue = caseData.getStringCellValue();
						// System.out.println("caseSeqValue     " +
						// caseSeqValue);
						//
						// while (cellIter.hasNext()) {
						//
						// if (cCount < sheet2ColCount - 1) {
						// HSSFCell cellData = (HSSFCell) cellIter.next();
						// String cellvalue = cellData
						// .getStringCellValue();
						// System.out.println(cellvalue);
						// if (cellvalue != "") {
						// int sequence = Integer.parseInt(cellvalue);
						// seqMapData.put(keyWord.get(cCount),
						// sequence);
						// }
						// }
						// cCount++;
						// }
						// Map<String, Integer> sortedSeqMap = new
						// HashMap<String, Integer>();
						// sortedSeqMap = SortMapByValue.sortMap(seqMapData);
						// System.out.println("====  " + arr.get(i) + "  ====");
						// caseSeqMap.put("Case " + arr.get(i), sortedSeqMap);
						// break;
					}
				}
			}

			// System.out.println("Data map: : :----> " + caseSeqMap);
			fileInputS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// bean.setSequenceMap(caseSeqMap);
		return caseDataMap;
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

	/**
	 * @param workbook
	 * @param rowIndex
	 * @param cellIndex
	 * @param sheetName
	 */
	public static Cell specificCellValue(int cellIndex, int rowIndex,
			String sheetName, HSSFWorkbook workbook) {
		// HSSFCell strValue = workbook.getSheet(sheetName).getRow(rowIndex)
		// .getCell(cellIndex);

		Cell c = null;
		CellReference ref = new CellReference("A" + cellIndex);
		Row r = sheet.getRow(ref.getRow());
		if (r != null) {
			c = r.getCell(ref.getCol());
		}
		return c;
	}
}