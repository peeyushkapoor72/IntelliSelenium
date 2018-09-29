package com.csc.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class XMLTester {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(false);
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HtmlCleaner htmlCleaner = new HtmlCleaner();
		CleanerProperties cleanerProperties = htmlCleaner.getProperties();
		cleanerProperties.setOmitXmlDeclaration(true);
		cleanerProperties.setOmitDoctypeDeclaration(true);
		cleanerProperties.setRecognizeUnicodeChars(true);
		cleanerProperties.setTranslateSpecialEntities(false);
		cleanerProperties.setIgnoreQuestAndExclam(true);
		cleanerProperties.setUseEmptyElementTags(false);
		cleanerProperties.setPruneTags("img,br");

		FileInputStream fIn = new FileInputStream(
				"D:/WP/New folder/IntelliSelenium/src/com/csc/test/input.xml");
		InputSource in = new InputSource(fIn);
		TagNode node = htmlCleaner.clean(new File(
				"D:/WP/New folder/IntelliSelenium/src/com/csc/test/input.xml"));
		// TagNode node = htmlCleaner.clean(new
		// File("D:/workspaces/ctmServices/IntelliSelenium/src/com/csc/test/inputchrome.xml"));
		// TagNode firstnode = (TagNode)node.getAllChildren().get(0);
		TagNode bodyNode = (TagNode) node.getChildTags()[1];

		TagNode headNode = (TagNode) node.getChildTags()[0];
		node.removeChild(headNode);
		Document doc = new DomSerializer(cleanerProperties).createDOM(bodyNode);

		// System.out.println(firstnode.getName());
		// System.out.println(firstnode.findElementByName("META", false));
		// firstnode.removeChild(firstnode.findElementByName("META", false));
		// firstnode.removeChild(firstnode.findElementByName("meta", false));
		// Document doc=new
		// DomSerializer(cleanerProperties).createDOM(bodyNode);

		/*
		 * NodeList list = doc.getFirstChild().getChildNodes(); for(int i=0;
		 * i<=list.getLength();i++){
		 * 
		 * Node xmlNode = list.item(i); if(xmlNode!=null){
		 * 
		 * if(xmlNode.getNodeName().equals("META")){ doc.removeChild(xmlNode);
		 * break; } } }
		 */

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString();

		// System.out.println(output);

		String xmlDec = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		// xr.setContentHandler(new FragmentContentHandler(xr));
		xr.parse(new InputSource(new ByteArrayInputStream(output
				.getBytes("utf-8"))));
	}
}
