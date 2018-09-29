package com.csc.intellis.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

class HTMLParser extends DefaultHandler {

	private String xPath = "/";
	private List<String> xPathList = new ArrayList<String>();
	private XMLReader xmlReader;
	private HTMLParser parent;
	private StringBuilder characters = new StringBuilder();
	private Map<String, Integer> elementNameCount = new HashMap<String, Integer>();

	public HTMLParser(XMLReader xmlReader, List<String> inList) {
		this.xmlReader = xmlReader;
		this.xPathList = inList;
	}

	public HTMLParser(String xPath, XMLReader xmlReader, HTMLParser parent,
			List<String> inList) {
		this(xmlReader, inList);
		this.xPath = xPath;
		this.parent = parent;
		this.xPathList = inList;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		Integer count = elementNameCount.get(qName);
		if (null == count) {
			count = 1;
		} else {
			count++;
		}
		elementNameCount.put(qName, count);
		String childXPath = xPath + "/" + qName + "[" + count + "]";

		int attsLength = atts.getLength();
		for (int x = 0; x < attsLength; x++) {
			// System.out.println(childXPath + "[@" + atts.getQName(x) + "='" +
			// atts.getValue(x) + ']');
			xPathList.add(childXPath + "[@" + atts.getQName(x) + "='"
					+ atts.getValue(x) + "']");
		}

		HTMLParser child = new HTMLParser(childXPath, xmlReader, this,
				xPathList);
		xmlReader.setContentHandler(child);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String value = characters.toString().trim();
		if (value.length() > 0) {
			// System.out.println(xPath + "='" + characters.toString() + "'");
			xPathList.add(xPath + "='" + characters.toString().trim() + "'");
		}
		xmlReader.setContentHandler(parent);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		characters.append(ch, start, length);
	}

	public List<String> getXpathList() {
		return xPathList;
	}
}