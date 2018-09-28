package com.csc.intellis.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HTMLParserHandler extends DefaultHandler {

	private String xPath = "/";
	private List<String> xPathList = new ArrayList<String>();
	private XMLReader xmlReader;
	private HTMLParserHandler parent;
	private StringBuilder characters = new StringBuilder();
	private Map<String, Integer> elementNameCount = new HashMap<String, Integer>();

	public HTMLParserHandler(XMLReader xmlReader, List<String> inList) {
		this.xmlReader = xmlReader;
		this.xPathList = inList;
	}

	private HTMLParserHandler(String xPath, XMLReader xmlReader,
			HTMLParserHandler parent, List<String> inList) {
		this.xmlReader = xmlReader;
		this.xPath = xPath;
		this.parent = parent;
		this.xPathList = inList;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		// System.out.println("qName:"+qName+"--localName:"+localName);
		Integer count = elementNameCount.get(localName);
		System.out.println("localName:" + localName + "=count:" + count);
		if (null == count) {
			count = 1;
		} else {
			count++;
		}

		elementNameCount.put(localName, count);
		String childXPath = xPath + "/" + localName + "[" + count + "]";

		int attsLength = atts.getLength();

		for (int x = 0; x < attsLength; x++) {
			// if(!atts.getLocalName(x).contains("#text")){
			// System.out.println(atts.getLocalName(x));
			System.out.println(childXPath + "[@" + atts.getLocalName(x) + "='"
					+ atts.getValue(x) + ']');
			xPathList.add(childXPath + "[@" + atts.getLocalName(x) + "='"
					+ atts.getValue(x) + "']");
			// }
		}

		HTMLParserHandler child = new HTMLParserHandler(childXPath, xmlReader,
				this, xPathList);
		xmlReader.setContentHandler(child);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		String value = characters.toString().trim();
		if (value.length() > 0) {
			System.out.println(xPath + "='" + characters.toString() + "'");
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