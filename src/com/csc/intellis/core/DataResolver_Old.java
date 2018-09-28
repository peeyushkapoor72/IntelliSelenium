package com.csc.intellis.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;
import com.csc.intellis.core.config.ConfigLoader;
import com.csc.intellis.exception.IntelliSException;

public class DataResolver_Old {

	HSSFSheet sheet1 = null;
	HSSFSheet sheet2 = null;
	FileInputStream file = null;
	ExecutorParameterBean bean = null;
	Map<String, List<String>> labelDataMap = new HashMap<String, List<String>>();
	Map<String, Map<String, String>> caseDataMap = new HashMap<String, Map<String, String>>();
	Map<String, Map<String, Integer>> caseSeqMap = new HashMap<String, Map<String, Integer>>();
	final static Logger logger = Logger.getLogger(DataResolver_Old.class);

	// public static void main(String[] args) throws IOException {
	//
	// String FilePath =
	// "C:\\Share_Folder_Peeyush\\ER\\TestCases\\Validation.xls";
	// FileInputStream fileInputS = new FileInputStream(new File(FilePath));
	// HSSFWorkbook workbook = new HSSFWorkbook(fileInputS);
	//
	// HSSFSheet sheet_2 = workbook.getSheetAt(1);
	// readRow(sheet_2, 2);
	// }

	/**
	 * @param bean
	 * @throws IntelliSException
	 */
	public DataResolver_Old(ExecutorParameterBean bean, String fileName)
			throws IntelliSException {
		super();
		this.bean = bean;

		try {
			HashMap<String, String> configMap = ConfigLoader.getInstance()
					.loadConfig();
			bean.setConfigMap(configMap);

			file = new FileInputStream(new File(bean.getConfigMap().get(
					IntelliSConstants.TEST_FILE_PATH)
					+ fileName));

			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			System.out.println(workbook.getNumberOfSheets());
			logger.info(workbook.getNumberOfSheets()
			// + "current sheet is :- " + workbook.getSheetName(0));
					+ "current sheet is :- " + workbook.getSheetName(1));

			// Get first sheet from the workbook
			// sheet1 = workbook.getSheetAt(0);
			sheet1 = workbook.getSheetAt(1);
			// Get first sheet from the workbook
			// sheet2 = workbook.getSheetAt(1);
			sheet2 = workbook.getSheetAt(2);
			System.out.println(sheet2);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
			throw new IntelliSException("500", "Test file not found!!");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			throw new IntelliSException("500", "Test file not found!!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new IntelliSException("500", "Test file not found!!");
		}
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	public Map<String, Map<String, String>> processData() {

		try {

			int sheet2ColCount = sheet2.getRow(0).getLastCellNum();

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator2 = sheet2.iterator();

			List<String> keyWord = new ArrayList<String>();

			if (rowIterator2.hasNext()) {

				HSSFRow rowData = (HSSFRow) rowIterator2.next();
				Iterator<Cell> cellIter = rowData.cellIterator();
				HSSFCell cellData = (HSSFCell) cellIter.next();
				String id = cellData.getStringCellValue().toString();

				while (cellIter.hasNext()) {
					HSSFCell cellData1 = (HSSFCell) cellIter.next();
					String cellvalue = cellData1.getStringCellValue()
							.toString();
					keyWord.add(cellvalue);
				}
				logger.info("keyword: : :" + keyWord);
			}

			int rowCount = 0;
			int i = 0;

			while (rowIterator2.hasNext()) {

				rowCount++;

				// logger.info("Row Count : : : : " + rowCount);
				HSSFRow rowData = (HSSFRow) rowIterator2.next();
				Iterator<Cell> cellIter = rowData.cellIterator();
				int cCount = 0;
				Map<String, String> dataMap = new HashMap<String, String>();
				Map<String, Integer> seqMapData = new HashMap<String, Integer>();

				if (rowCount % 2 != 0) {

					HSSFCell caseData = (HSSFCell) cellIter.next();
					String caseValue = caseData.getStringCellValue();

					while (cellIter.hasNext()) {

						if (cCount < sheet2ColCount - 1) {
							HSSFCell cellData = (HSSFCell) cellIter.next();
							String cellvalue = cellData.getStringCellValue();
							dataMap.put(keyWord.get(cCount), cellvalue);
						}
						cCount++;
					}
					caseDataMap.put("Case " + ++i, dataMap);

				} else {

					HSSFCell caseData = (HSSFCell) cellIter.next();
					String caseSeqValue = caseData.getStringCellValue();

					while (cellIter.hasNext()) {

						if (cCount < sheet2ColCount - 1) {
							HSSFCell cellData = (HSSFCell) cellIter.next();
							String cellvalue = cellData.getStringCellValue();

							if (cellvalue != "") {
								int sequence = Integer.parseInt(cellvalue);
								seqMapData.put(keyWord.get(cCount), sequence);
							}
						}
						cCount++;
					}
					Map<String, Integer> sortedSeqMap = new HashMap<String, Integer>();
					sortedSeqMap = SortMapByValue.sortMap(seqMapData);
					caseSeqMap.put("Case " + i, sortedSeqMap);
				}
			}
			// logger.info("Data map: : :----> " + caseSeqMap);
			file.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		bean.setSequenceMap(caseSeqMap);
		// CreateOutputExcel();
		return caseDataMap;
	}

	/**
	 * @return
	 */
	public Map<String, List<String>> LabelDataResolver(
			Map<String, Map<String, String>> dataValue, String strSheetName,
			int rowNum) {

		try {

			/*
			 * List<String> keyWord = new ArrayList<String>(); keyWord =
			 * readRow(sheet2, rowNum);
			 * 
			 * logger.info("\n"); int totalListSize = keyWord.size();
			 * logger.info(totalListSize); int lastrowNum =
			 * sheet1.getLastRowNum(); logger.info(lastrowNum); List<String>
			 * label = new ArrayList<String>(); logger.info("\n");
			 * logger.info("\n"); logger.info("\n"); logger.info("\n"); for (int
			 * i = 1; i <= lastrowNum; i++) { logger.info(
			 * "current iteration is:-" + "\t" + i); label = readRow(sheet1, i);
			 * //label = readRow(sheet1); if (keyWord.size() > i) {
			 * labelDataMap.put(keyWord.get(i), label); } else { break; } }
			 */

			Map<String, String> internalMap = null;
			List<String> rowValue = new ArrayList<String>();
			for (Entry<String, Map<String, String>> entry : dataValue
					.entrySet()) {
				logger.info(entry.getKey());
				internalMap = entry.getValue();

				for (Entry<String, String> internalEntry : internalMap
						.entrySet()) {
					rowValue.add(internalEntry.getKey());
					logger.info(internalEntry.getKey());
				}
			}
			List<String> label = null;
			labelDataMap.clear();
			int lastrowNum = sheet1.getLastRowNum();

			for (int i = 0; i < rowValue.size(); i++) {

				logger.info(rowValue.get(i));
				for (int j = 1; j <= lastrowNum; j++) {
					logger.info("current iteration is:-" + "\t" + j);
					label = new ArrayList<String>();
					label = readRow(sheet1, j);

					System.out.println(label.get(4));
					System.out.println(label.get(5));
					System.out.println(label.get(6));
					// if (label.get(5).equals(rowValue.get(i))) {
					if (label.get(6).equals(rowValue.get(i))) {
						labelDataMap.put(rowValue.get(i), label);
						break;
					}
				}
			}

			logger.info("\n" + "Done" + "\n");
			logger.info(labelDataMap);

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		// Create Output Excel
		CreateOutputExcel(strSheetName + "_Results");

		// Return Map
		return labelDataMap;
	}

	/**
	 * @param sheet
	 * @param rowIndex
	 */
	public static List<String> readRow(HSSFSheet sheet, int rowIndex) {

		List<String> keyword = new ArrayList<String>();

		Row row = sheet.getRow(rowIndex);
		int lastRowNum = sheet.getLastRowNum();
		logger.info(lastRowNum);
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {

			HSSFCell cellData = (HSSFCell) cellIterator.next();
			String id = cellData.getStringCellValue().toString();
			System.out.println(id);
			keyword.add(id);
		}
		return keyword;
	}

	/**
	 * @param strSheetName
	 */
	public void CreateOutputExcel(String strSheetName) {
		logger.info("Inside CreateOutputExcel()!!!!");
		try {
			File file = new File(bean.getConfigMap().get(
					IntelliSConstants.RESULT_FILE_PATH)
					+ "Result.xls");
			if (!file.exists()) {
				FileOutputStream out;
				HSSFSheet sheet;
				logger.info("Creating a new workbook '" + file + "'");
				HSSFWorkbook workbook = new HSSFWorkbook();
				sheet = workbook.createSheet(strSheetName);
				String[] headings = { "CaseID", "Result", "Reason",
						"Description" };

				int rowCount = 0;
				HSSFRow row = sheet.createRow(rowCount);
				int cellCount = 0;

				for (String head : headings) {
					HSSFCell cell = row.createCell(cellCount++);
					cell.setCellValue(head);
				}
				out = new FileOutputStream(file);
				workbook.write(out);
				out.close();
			} else {
				HSSFWorkbook workbook1 = new HSSFWorkbook();
				HSSFSheet sheet1;
				FileOutputStream out1;
				logger.info("deleted the existing workbook and creating a new excel file at path  :-  '"
						+ file + "'");
				final InputStream is = new FileInputStream(file);
				try {
					workbook1 = new HSSFWorkbook(is);

					sheet1 = workbook1.createSheet(strSheetName);
					String[] headings = { "CaseID", "Result", "Reason",
							"Description" };

					int rowCount1 = 0;
					HSSFRow row1 = sheet1.createRow(rowCount1);
					int cellCount1 = 0;

					for (String head : headings) {
						HSSFCell cell1 = row1.createCell(cellCount1++);
						cell1.setCellValue(head);
					}
				} finally {
					is.close();
					out1 = new FileOutputStream(file);
					workbook1.write(out1);
					out1.close();
				}
			}
		} catch (Exception e) {
			logger.error("File not found" + e);
		}
	}

}