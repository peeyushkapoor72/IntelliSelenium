package com.csc.intellis.core.pattern;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Enumeration;

import com.csc.intellis.helper.PropertyFileReader;
import com.csc.intellis.exception.IntelliSException;

public class PatternLoader {

	private static volatile PatternLoader instance = null;
	private static String FILE_PATH = "../config/pattern.properties";

	private PatternLoader() {
	}

	/**
	 * Method to get an instance of this class
	 * 
	 * @return
	 */
	public static PatternLoader getInstance() {
		if (instance == null) {
			synchronized (PatternLoader.class) {
				if (instance == null)
					instance = new PatternLoader();
			}
		}
		return instance;
	}

	public HashMap<String, String> loadPattern() throws IntelliSException,
			IOException {

		System.out.println(new File(FILE_PATH).getCanonicalPath() + "---");
		Properties prop = PropertyFileReader.getInstance().readFile(
				new File(FILE_PATH).getCanonicalPath());
		Enumeration<Object> enu = prop.keys();
		HashMap<String, String> patternMap = new HashMap<String, String>();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			System.out.println("key:" + key);
			patternMap.put(key, prop.getProperty(key));
		}
		return patternMap;
	}
}