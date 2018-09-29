package com.csc.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.media.sound.InvalidFormatException;

public class sampleTC {

	public static int columnIndex = 0;
	public static String sheetName = "LoginSheet_1_Results";
	public static String filePath = "C:\\T420\\C_Drive\\Share_Folder_Peeyush\\ER\\newResults\\Test.xls";

	public static void main(String[] args) throws IOException {

		// LinkedHashMap<String, String> map = new LinkedHashMap<String,
		// String>();
		// ArrayList<String> list = extractExcelContentByColumnIndex(filePath,
		// columnIndex, "NewTab");
		// for (int i = 0; i <= list.size() - 1; i++) {
		// System.out.println(list.get(i));
		// }
		// ArrayList<String> list1 = extractExcelContentByColumnIndex(filePath,
		// 1,
		// "NewTab");
		// for (int i = 0; i <= list1.size() - 1; i++) {
		// System.out.println(list1.get(i));
		// }
		//
		// for (int i = 1; i <= list.size() - 1; i++) {
		//
		// System.out.println("current iteration  is " + i);
		//
		// for (int y = 1; y <= list1.size() - 1;) {
		//
		// System.out.println("current iteration  is " + y);
		// System.out.println("Key   " + list.get(i) + "     value   "
		// + list1.get(i));
		// map.put(list.get(i), list1.get(i));
		// break;
		// }
		// }
		// System.out.println(map);
		//
		// // writeNewTabOfExcel(filePath, list, sheetName, "NewTab", filePath);

		String strString = "accountName=;socialSecurityNumber=null;creditInterest=null;largeItemMonitor=null;language=null;odLineAmount=null;serviceCharge=null;svcChgCycle=EM;svcChgCode=null;svcChgWaiveCode=null;obpCode=null;processingId=null;statement=null;statementMailCode=null;statementSequence=null;statementCycle=EQ;currency=null;summarizeTransaction=null;includePaperItems=null;product=81;demographicDetails=null;geographicCode=null;ownerCode=1;subownerCode=null;costCenter=null;relOfficer2=null;relOfficer1=null;location=null;sic=null;branch=null;naics=null;arpCode=null;taxIdNumber=null;accountType=dda;accountNumber=123345670000056;packageCode=null;companyNbr=11;withHoldCode=null;debitInterest=null;openDate=null;overdraftServiceCharge=null";
		Map<String, String> map = string2Map(strString);
		for (String name : map.keySet()) {

			String key = name.toString();
			String value = map.get(name).toString();
			System.out.println(key + " " + value);

		}
	}

	/**
	 * @return
	 * @param strString
	 */
	public static Map<String, String> string2Map(String strString) {

		HashMap<String, String> myMap = new HashMap<String, String>();
		String[] pairs = strString.split(";");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split("=");

			if (keyValue.length > 1) {

				if (keyValue[1].equals("null")) {
					continue;
				} else {
					myMap.put(keyValue[0], keyValue[1]);
				}
			} else {
				System.out
						.println("No Value, therefore moving to the next iteration.");
			}

		}
		// System.out.println(myMap);
		return myMap;
	}

	/**
	 * @param list
	 * @param filePath
	 * @param sheetName
	 * @param outFilePath
	 * @throws IOException
	 * @param strNewSheetName
	 * @throws InvalidFormatException
	 */
	public static void writeNewTabOfExcel(String filePath,
			ArrayList<String> list, String sheetName, String strNewSheetName,
			String outFilePath) throws InvalidFormatException, IOException {

		FileInputStream fis = new FileInputStream(filePath);
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.createSheet(strNewSheetName);

		for (int i = 0; i < list.size(); i++) {
			Row r = sheet.createRow(i);
			r.createCell(0).setCellValue(list.get(i));
			if (i == 0) {
				r.createCell(1).setCellValue("Reference Name");
			} else {
				r.createCell(1).setCellValue("ACT_" + i);
			}
		}
		FileOutputStream fileOut = new FileOutputStream(outFilePath);
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	/**
	 * @return
	 * @param fis
	 * @throws IOException
	 * @param excelFilePath
	 * @throws InvalidFormatException
	 */
	public static Workbook getWorkbook(InputStream fis, String excelFilePath)
			throws IOException, InvalidFormatException {
		Workbook workbook = null;

		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(excelFilePath);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new XSSFWorkbook(excelFilePath);
		} else {
			throw new IllegalArgumentException(
					"The specified file is not Excel file");
		}
		return workbook;
	}

	/**
	 * @return
	 * @param filePath
	 * @param sheetName
	 */
	public static int getRowCount(String filePath, String sheetName) {

		int rCount = 0;
		try {
			File f = new File(filePath);
			FileInputStream ios = new FileInputStream(f);
			HSSFWorkbook workbook = new HSSFWorkbook(ios);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			rCount = sheet.getLastRowNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rCount;
	}

	/**
	 * @return
	 * @param filePath
	 * @param sheetName
	 * @param columnIndex
	 * @throws IOException
	 */
	public static ArrayList<String> extractExcelContentByColumnIndex(
			String filePath, int columnIndex, String sheetName)
			throws IOException {

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
}