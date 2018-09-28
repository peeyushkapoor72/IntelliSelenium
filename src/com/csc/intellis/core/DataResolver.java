package com.csc.intellis.core;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.csc.intellis.core.config.ConfigLoader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.csc.intellis.exception.IntelliSException;
import com.csc.intellis.constants.IntelliSConstants;
import com.csc.intellis.core.bean.ExecutorParameterBean;

public class DataResolver {

	public static Map<String, Map<String, String>> caseDataMap = new HashMap<String, Map<String, String>>();
	public static Map<String, Map<String, Integer>> caseSeqMap = new HashMap<String, Map<String, Integer>>();
	public static Map<String, List<String>> labelDataMap = new HashMap<String, List<String>>();
	final static Logger logger = Logger.getLogger(DataResolver.class);
	HSSFSheet sheet = null;
	FileInputStream file = null;
	ExecutorParameterBean bean = null;

	public DataResolver(ExecutorParameterBean bean) throws IntelliSException {

		this.bean = bean;
		try {
			HashMap<String, String> configMap = ConfigLoader.getInstance()
					.loadConfig();
			bean.setConfigMap(configMap);
			file = new FileInputStream(new File(bean.getConfigMap().get(
					IntelliSConstants.TEST_FILE_PATH)));
			HSSFWorkbook workbook = new HSSFWorkbook(file);

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				logger.info(workbook.getNumberOfSheets());
				logger.info(workbook.getSheetName(i));
				sheet = workbook.getSheetAt(i);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Test file not found!!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Test file not found!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntelliSException("500", "Test file not found!!");
		}
		return;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	public Map<String, Map<String, String>> processData() {

		try {

			int sheet2ColCount = sheet.getRow(0).getLastCellNum();

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator2 = sheet.iterator();

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
							logger.info(cellvalue);
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
}