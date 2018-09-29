package com.csc.testing;

import java.io.File;
import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;

public class DataValidation {

	static String sheetName = "Keywords";
	static String filePath = "C:\\Users\\pkapoor22\\Desktop\\ER_Automation\\excel\\KKK\\TC\\Validation_1.xls";
	static String outFilePath = "C:\\Users\\pkapoor22\\Desktop\\ER_Automation\\excel\\KKK\\TC\\Validation_71.xls";

	public static void main(String[] args) throws IOException {

		// Read Keywords
		ArrayList<String> columndata = extractExcelContentByColumnIndex(5,
				filePath, sheetName);
		for (int i = 0; i <= columndata.size() - 1; i++) {
			System.out.println(columndata.get(i));
		}

		// Apply data validation to excel
		dataValidation(filePath, outFilePath, sheetName);

	}

	/**
	 * @param filePath
	 * @param sheetName
	 * @param outFilePath
	 * @throws IOException
	 */
	public static void dataValidation(String filePath, String outFilePath,
			String sheetName) throws IOException {

		File f = new File(outFilePath);
		FileOutputStream ios = new FileOutputStream(f);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Data Validation");
		CellRangeAddressList addressList = new CellRangeAddressList(1, 20, 1, 2);

		ArrayList<String> columndata = extractExcelContentByColumnIndex(5,
				filePath, sheetName);
		String[] stringArr = new String[columndata.size()];
		stringArr = columndata.toArray(stringArr);

		DVConstraint dvConstraint = DVConstraint
				.createExplicitListConstraint(stringArr);
		HSSFDataValidation dataValidation = new HSSFDataValidation(addressList,
				dvConstraint);
		dataValidation.setSuppressDropDownArrow(false);
		sheet.addValidationData(dataValidation);
		workbook.write(ios);
	}

	/**
	 * @return
	 * @param columnIndex
	 * @param strSheetPath
	 * @param strSheetName
	 * 
	 *            We are reading the Keywords sheet and saving all the keywords
	 *            in array list which would be later used to perform data
	 *            validation to the sheet excel
	 */
	public static ArrayList<String> extractExcelContentByColumnIndex(
			int columnIndex, String strSheetPath, String strSheetName) {
		ArrayList<String> columndata = null;
		try {
			File f = new File(strSheetPath);
			FileInputStream ios = new FileInputStream(f);
			HSSFWorkbook workbook = new HSSFWorkbook(ios);
			HSSFSheet sheet = workbook.getSheet(strSheetName);
			Iterator<Row> rowIterator = sheet.iterator();
			columndata = new ArrayList<String>();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					if (row.getRowNum() > 0) {
						if (cell.getColumnIndex() == columnIndex) {
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
}