package com.csc.intellis.auotIT.miscMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class javaMethods {

	// public static void main(String[] args) throws IOException {
	// confirmTextContent("C:\\Users\\pkapoor22\\Desktop\\CAMS2\\report.txt",
	// "00200512300835247687864");
	// }

	/**
	 * @param sleepMilliseconds
	 */
	public static void sleep(int sleepMilliseconds) {

		try {
			Thread.sleep(sleepMilliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @param strSavedFilePath
	 * @throws IOException
	 */
	public static String fileExists(String strSavedFilePath) throws IOException {

		String fileName = null;
		String absolutePath = null;
		String filePath = null;
		String strfilePath = null;
		File file = new File(strSavedFilePath);
		boolean exists = file.exists();
		if (exists) {
			System.out
					.println("looks like the file exists, chnaging the name of the file, so that the program doesnt breaks.");
			fileName = file.getName();
			absolutePath = file.getAbsolutePath();
			filePath = absolutePath.substring(0,
					absolutePath.lastIndexOf(File.separator));
			System.out.println(absolutePath);
			System.out.println("new file path is " + filePath + "\\"
					+ fileName.subSequence(0, 4) + "_"
					+ System.currentTimeMillis() + ".txt");
			strfilePath = filePath + "\\" + fileName.subSequence(0, 4) + "_"
					+ System.currentTimeMillis() + ".txt";
		} else {
			System.out
					.println("Nope the file doesnot exists, going ahead and creating one.");
			file.createNewFile();
			fileName = file.getName();
			absolutePath = file.getAbsolutePath();
			filePath = absolutePath.substring(0,
					absolutePath.lastIndexOf(File.separator));
			System.out.println(absolutePath);
			System.out.println("new file path is " + filePath + "\\"
					+ fileName.subSequence(0, 4) + "_"
					+ System.currentTimeMillis() + ".txt");
			strfilePath = filePath + "\\" + fileName.subSequence(0, 4) + "_"
					+ System.currentTimeMillis() + ".txt";
		}
		return strfilePath;
	}

	/**
	 * @param strFilePath
	 * @param string2Match
	 */
	public static void confirmTextContent(String strFilePath,
			String string2Match) {
		try {
			File file = new File(strFilePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			int lineNum = 0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				lineNum++;
				if (line.trim().contains(string2Match.trim())) {
					System.out.println("Text found on line " + lineNum);
					break;
				}
			}
			fileReader.close();
			System.out.println("Contents of file:");
			// System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Not Found");
		}
	}
}