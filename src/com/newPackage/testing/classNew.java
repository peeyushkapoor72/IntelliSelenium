package com.csc.testing;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class classNew {

	public static void main(String[] args) {

		TreeMap<String, String> arr = new TreeMap<String, String>();
		arr.put("Peeyush", "Kapoor");
		arr.put("Neeraj", "Lohani");

		Set<String> set = arr.keySet();
		Iterator<String> itr = set.iterator();

		while (itr.hasNext()) {
			String key = itr.next();
			System.out.println("Key   " + key + "   Value   " + arr.get(key));
		}
	}
}