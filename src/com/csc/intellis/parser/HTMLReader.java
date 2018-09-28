package com.csc.intellis.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlparser.Node;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HTMLReader {

	/*
	 * public List<String> readHTML(String htmlSrc) throws SAXException,
	 * ParserConfigurationException, TransformerException, IOException{
	 * //System.out.println(htmlSrc); XMLReader xr = new XMLReader();
	 * HTMLParserHandler handler = new HTMLParserHandler(xr, new
	 * ArrayList<String>()); try{ Parser parser = new Parser(htmlSrc); NodeList
	 * nl = parser.parse(null); NodeIterator itr = nl.elements(); Node node
	 * =null; while (itr.hasMoreNodes()) { node = (Node) itr.nextNode();
	 * if(node.getChildren()!=null){ node = traverseNodes(node.getChildren()); }
	 * } xr.setContentHandler(handler); xr.parse(node.toHtml());
	 * 
	 * }catch(Exception e){ e.printStackTrace(); } return
	 * handler.getXpathList();
	 * 
	 * }
	 */

	@SuppressWarnings("unused")
	private Node traverseNodes(NodeList list) throws ParserException {
		Node node = null;
		NodeIterator itr = list.elements();
		while (itr.hasMoreNodes()) {
			node = (Node) itr.nextNode();
			if (node.getText().contains("body")) {
				break;
			} else {
				if (node.getChildren() != null) {
					traverseNodes(node.getChildren());
				}
			}
		}
		return node;
	}

	public List<String> readHTML(String htmlSrc)
			throws ParserConfigurationException, TransformerException,
			IOException, SAXException {
		HTMLParser handler = null;

		SAXParserFactory spf = SAXParserFactory.newInstance();

		SAXParser sp = spf.newSAXParser();
		org.xml.sax.XMLReader xr = sp.getXMLReader();

		HtmlCleaner htmlCleaner = new HtmlCleaner();
		CleanerProperties cleanerProperties = htmlCleaner.getProperties();
		cleanerProperties.setOmitXmlDeclaration(true);
		cleanerProperties.setOmitDoctypeDeclaration(true);
		cleanerProperties.setRecognizeUnicodeChars(true);
		cleanerProperties.setTranslateSpecialEntities(false);
		cleanerProperties.setIgnoreQuestAndExclam(true);
		cleanerProperties.setUseEmptyElementTags(false);
		// cleanerProperties.setPruneTags("img,br");

		TagNode node = htmlCleaner.clean(htmlSrc);
		TagNode headNode = (TagNode) node.getChildTags()[0];
		TagNode bodyNode = (TagNode) node.getChildTags()[1];
		node.removeChild(headNode);

		Document doc = new DomSerializer(cleanerProperties).createDOM(bodyNode);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();

		// String xmlDec="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		handler = new HTMLParser(xr, new ArrayList<String>());

		xr.setContentHandler(handler);
		xr.parse(new InputSource(new ByteArrayInputStream(output
				.getBytes("utf-8"))));

		return handler.getXpathList();
	}
}