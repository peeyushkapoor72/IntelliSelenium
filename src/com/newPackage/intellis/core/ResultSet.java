package com.csc.intellis.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;

public class ResultSet {
	final static Logger logger = Logger.getLogger(ResultSet.class);
	private ExecutorParameterBean bean = null;

	public ResultSet(ExecutorParameterBean bean) {
		this.bean = bean;
	}

	/**
	 * @param result
	 * @param rouNumber
	 * @param sheetName
	 */
	public void writeResultsToOutExcel(String sheetName, List<List<String>> result, int rouNumber) {

		for (List<String> l1 : result) {
			for (String n : l1) {
				System.out.print(n + " ");
			}
		}

		try {
			String dir = bean.getConfigMap().get(IntelliSConstants.RESULT_FILE_PATH) + "Result.xls";
			File file = new File(dir);
			FileInputStream fsIP = new FileInputStream(file);

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("No sheet exists with name " + sheetName);
			}

			Iterator<List<String>> i = result.iterator();
			int rownum = rouNumber;
			int cellnum = 0;
			while (i.hasNext()) {

				List<String> templist = (List<String>) i.next();
				Iterator<String> tempIterator = templist.iterator();
				logger.info(templist.size());
				HSSFRow row = sheet.createRow(rownum++);
				cellnum = 0;

				// for (int y = 0; y <= templist.size() - 1; y++) {
				for (int y = 0; y < 4; y++) {
					if (y > 3) {
						row = sheet.createRow(rownum++);
						String temp = (String) tempIterator.next();
						logger.info("String value is:-    " + temp);
						Cell cell = row.createCell(cellnum++);
						cell.setCellValue(temp);
					} else {
						String temp = (String) tempIterator.next();
						logger.info("String value is:-    " + temp);
						Cell cell = row.createCell(cellnum++);
						cell.setCellValue(temp);
					}
				}
			}
			fsIP.close();
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error in writeresult class!!!");
		}
	}

	/**
	 * @param result
	 * @param sheetName
	 */
	public void WriteResult(String sheetName, List<List<String>> result, String desc) {

		for (List<String> l1 : result) {
			for (String n : l1) {
				System.out.print(n + " ");
			}
		}
		try {
			String dir = bean.getConfigMap().get(IntelliSConstants.RESULT_FILE_PATH) + "Result.xls";
			File file = new File(dir);
			FileInputStream fsIP = new FileInputStream(file);

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("No sheet exists with name " + sheetName);
			}
			int rownum = 1;
			for (List<String> status : result) {
				int colNum = 0;
				logger.info("2...........In for loop : : ; " + status);

				HSSFRow dataRow = sheet.createRow(rownum++);
				// logger.info("checkpoint 4............" + dataRow);
				Iterator<String> itr = status.iterator();

				while (itr.hasNext()) {
					String temp = (String) itr.next();
					logger.info(temp);
					HSSFCell cell = dataRow.createCell(colNum++);
					logger.info("Column Number:-" + colNum++);
					// cell.setCellValue(itr.next());
					cell.setCellValue(temp);
					// logger.info(itr.next());
				}
			}
			fsIP.close();
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();

		} catch (Exception e) {
			logger.info(e);
			logger.info("error in writeresult class!!!");
		}
	}
}