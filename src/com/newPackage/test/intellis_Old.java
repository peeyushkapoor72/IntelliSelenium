package com.csc.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.csc.intellis.core.DataResolver_Old;
import com.csc.intellis.core.bean.ExecutorParameterBean;

public class intellis_Old {

	static WebDriver driver;

	public static void main(String args[]) throws Exception {

		ExecutorParameterBean bean = new ExecutorParameterBean();
		List<List<String>> result = new ArrayList<List<String>>();
		Map<String, Map<String, String>> dataValueMap = new HashMap<String, Map<String, String>>();
		String fileName = null;
		DataResolver_Old object = new DataResolver_Old(bean, fileName);
		dataValueMap = object.processData();

		for (String key : dataValueMap.keySet()) {
			System.out.println(key + ":---- " + dataValueMap.get(key));
		}

		ArrayList<String> keys = new ArrayList<String>(dataValueMap.keySet());

		for (int itr = keys.size() - 1; itr >= 0; itr--) {

			List<String> statusReport = new ArrayList<String>();
			try {

				String caseId = keys.get(itr);
				bean.setCaseId(caseId);
				// IntelliSServer server = new IntelliSServer();
				// server.spawn(dataValueMap, bean);
				String statusMsg = dataValueMap.get(caseId).get("StatusText");
				System.out.println("status msg----->" + statusMsg);
				Thread.sleep(2000);
				boolean pgCode = bean.getDriver().getPageSource()
						.replace(" ", "").contains(statusMsg.replace(" ", ""));

				if (pgCode) {
					statusReport.add(bean.getCaseId());
					statusReport.add("PASS");
					statusReport.add("Status message found.");
				} else {
					statusReport.add(bean.getCaseId());
					statusReport.add("FAIL");
					statusReport.add("Status message not found.");
				}
				cleanup(bean);
			} catch (Exception e) {
				statusReport.add(bean.getCaseId());
				statusReport.add("FAIL");
				statusReport.add("Runtime Exception :" + e);
			}
			result.add(statusReport);
		}
		bean.setStatuslist(result);
		// ResultSet result1 = new ResultSet(bean);
		// result1.WriteResult();
		cleanup(bean);
	}

	/**
	 * @param bean
	 */
	private static void cleanup(ExecutorParameterBean bean) {

		driver = bean.getDriver();
		driver.quit();
		try {
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}