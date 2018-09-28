package com.csc.intellis.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SortMapByValue {

	public static <Key, Value extends Comparable<? super Value>> Map<Key, Value> sortMap(
			final Map<Key, Value> mapToSort) {

		List<Map.Entry<Key, Value>> entries = new ArrayList<Map.Entry<Key, Value>>(
				mapToSort.size());

		entries.addAll(mapToSort.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<Key, Value>>() {
			@Override
			public int compare(final Map.Entry<Key, Value> entry1,
					final Map.Entry<Key, Value> entry2) {
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});

		Map<Key, Value> sortedMap = new LinkedHashMap<Key, Value>();
		for (Map.Entry<Key, Value> entry : entries) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}