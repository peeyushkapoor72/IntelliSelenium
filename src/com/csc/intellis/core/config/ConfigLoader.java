package com.csc.intellis.core.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Enumeration;
import com.csc.intellis.helper.PropertyFileReader;
import com.csc.intellis.exception.IntelliSException;

public class ConfigLoader {

	private static volatile ConfigLoader instance = null;
	private static String FILE_PATH = "../config/config.properties";

	private ConfigLoader() {
	}

	/**
	 * Method to get an instance of this class
	 * 
	 * @return
	 */
	public static ConfigLoader getInstance() {
		if (instance == null) {
			synchronized (ConfigLoader.class) {
				if (instance == null)
					instance = new ConfigLoader();
			}
		}
		return instance;
	}

	public HashMap<String, String> loadConfig() throws IntelliSException,
			IOException {

		System.out.println(new File(FILE_PATH).getCanonicalPath() + "---");
		Properties prop = PropertyFileReader.getInstance().readFile(
				new File(FILE_PATH).getCanonicalPath());
		Enumeration<Object> enu = prop.keys();
		HashMap<String, String> configMap = new HashMap<String, String>();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			configMap.put(key, prop.getProperty(key));
		}
		return configMap;
	}
}