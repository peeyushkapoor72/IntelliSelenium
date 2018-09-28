package com.csc.testing;

import java.io.File;

public class filesAnddFolders {

	public static String path = "C:\\T420\\C_Drive\\Share_Folder_Peeyush\\ER_Dev\\Results\\";

	public static void main(String[] args) {

		File f = new File(path);
		File[] files = f.listFiles();
		for (File file : files) {
			System.out.println(file);
		}

	}
}