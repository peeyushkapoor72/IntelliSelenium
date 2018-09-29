package com.csc.intellis.parser;

import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("unused")
public class ParserBean {
	private String xPath = "/";
	private ParserBean parent;
	private Map<String, Integer> elementNameCount = new HashMap<String, Integer>();

	public ParserBean(String xPath, ParserBean parent) {
		this.xPath = xPath;
		this.parent = parent;
	}
}