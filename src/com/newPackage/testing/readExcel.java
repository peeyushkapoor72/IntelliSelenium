package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

@SuppressWarnings("rawtypes")
public class readExcel {

	public static void main(String[] args) {
		HashMap<String, LinkedHashMap<Integer, List>> map = loadExcelLines(new File(
				"C:\\Users\\pkapoor22\\Desktop\\ER_Automation\\excel\\KKK\\TC\\Validation_1.xls"));
		System.out.println(map);
		for (Entry<String, LinkedHashMap<Integer, List>> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}

	public static HashMap<String, LinkedHashMap<Integer, List>> loadExcelLines(
			File fileName) {

		// Used the LinkedHashMap and LikedList to maintain the order
		HashMap<String, LinkedHashMap<Integer, List>> outerMap = new LinkedHashMap<String, LinkedHashMap<Integer, List>>();
		LinkedHashMap<Integer, List> hashMap = new LinkedHashMap<Integer, List>();
		String sheetName = null;

		// Create an ArrayList to store the data read from excel sheet.
		// List<List<HSSFCell>> sheetData = new ArrayList<List<HSSFCell>>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);

			// Create an excel workbook from the file system
			HSSFWorkbook workBook = new HSSFWorkbook(fis);

			// Get the first sheet on the workbook.
			for (int i = 1; i < workBook.getNumberOfSheets(); i++) {
				HSSFSheet sheet = workBook.getSheet("Sheet2");
				sheetName = workBook.getSheetName(i);

				Iterator<?> rows = sheet.rowIterator();
				while (rows.hasNext()) {
					HSSFRow row = (HSSFRow) rows.next();
					Iterator<?> cells = row.cellIterator();

					List<HSSFCell> data = new LinkedList<HSSFCell>();
					while (cells.hasNext()) {
						HSSFCell cell = (HSSFCell) cells.next();
						if (cell != null
								&& cell.getCellType() != Cell.CELL_TYPE_BLANK) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								data.add(cell);
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								data.add(cell);
								break;
							case Cell.CELL_TYPE_STRING:
								data.add(cell);
								break;
							case Cell.CELL_TYPE_BLANK:
								data.add(cell);
								break;
							}
						} else {
							data.add(cell);
						}
					}
					hashMap.put(row.getRowNum(), data);

					// sheetData.add(data);
				}
				outerMap.put(sheetName, hashMap);
				hashMap = new LinkedHashMap<Integer, List>();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return outerMap;
	}
}