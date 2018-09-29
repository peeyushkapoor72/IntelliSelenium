package com.csc.intellis.core;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.csc.intellis.constants.ElementType;
import com.csc.intellis.exception.IntelliSException;
import com.csc.intellis.core.bean.ExecutorParameterBean;

public class XPathResolver {
	final static Logger logger = Logger.getLogger(XPathResolver.class);
	private ExecutorParameterBean bean = null;
	int skipCount = 0;

	/**
	 * @param bean
	 */
	public XPathResolver(ExecutorParameterBean bean) {
		this.bean = bean;
	}

	@SuppressWarnings("unchecked")
	public String resolve() throws IntelliSException {

		String xpathOut = "";
		List<String> xpathList = bean.getXpathList();
		String seq = bean.getPosition();
		// logger.info("XPathResolver ----> seq:-     " + seq);
		String name = bean.getName();

		// logger.info("XPathResolver ----> name:-    " + name);
		if (name.trim().contains("Accept")) {
			logger.info("BINGOOOOOOOOOOOO");
		}
		int inSeq = 1;
		int count = 1;
		if (!seq.equals("") && !seq.equals("0")) {
			inSeq = Integer.parseInt(seq);
		}
		String skip = bean.getSkip();
		int inSkip = 0;

		if (!skip.equals("") && !skip.equals("0")) {
			inSkip = Integer.parseInt(skip);
		}
		boolean found = false;
		int keyIndex = 0;
		Iterator<String> itr = xpathList.iterator();
		while (itr.hasNext()) {
			String xpath = (String) itr.next();
			// logger.info("XPATH:-        " + xpath);
			if (xpath.contains("'" + name + "'")) {
				// logger.info("NAME:        " + name);
				if (inSeq == count) {
					// logger.info("KEY:      " + xpath);
					found = true;
					logger.info("FOUND ");
					break;
				}
				count++;
			}
			keyIndex++;
			/*
			 * if(found){ if(match(xpath)){ if (inSkip == skipCount) {
			 * if(!xpath.contains("='" + name + "']")){ xpath =
			 * xpath.replace("='" + name + "'", ""); } xpathOut=xpath;
			 * logger.info("XPATH:"+xpath); break; } skipCount++; } }
			 */
		}
		// logger.info(found + "===");
		if (found) {
			if (!bean.getType().equals(ElementType.BUTTON.name())) {
				for (int i = keyIndex; i < xpathList.size(); i++) {
					// logger.info("XPATH:-        " + xpathList.get(i)
					// + "----");
					xpathOut = processXPath(xpathList.get(i), name, inSkip);
					// logger.info(xpathOut);

					if (!xpathOut.equals(""))
						break;
				}
			} else {
				for (int i = keyIndex; i > 0; i--) {
					xpathOut = processXPath(xpathList.get(i), name, inSkip);
					if (!xpathOut.equals(""))
						break;
				}
			}
		}
		if (xpathOut.equals("")) {
			throw new IntelliSException("500", "XPath cannot be resolved for :"
					+ name);
		} else {
			if (xpathOut.contains("@id")) {
				xpathOut = changetoRelative(xpathOut);
			}
			logger.info("xpathOut:" + xpathOut);
		}
		return xpathOut;
	}

	/**
	 * @return
	 * @param xpath
	 */
	private String changetoRelative(String xpath) {
		String str = xpath.substring(xpath.lastIndexOf("/"));
		return "/" + str;
	}

	/**
	 * @return
	 * @param xpath
	 * @param name
	 * @param inSkip
	 */
	private String processXPath(String xpath, String name, int inSkip) {
		String xpathOut = "";
		if (match(xpath)) {
			if (inSkip == skipCount) {
				if (!xpath.contains("='" + name + "']")) {
					xpath = xpath.replace("='" + name + "'", "");
				}
				xpathOut = xpath;
				// logger.info("XPATH:" + xpath);
			}
			skipCount++;
		}
		logger.info(xpathOut);
		return xpathOut;
	}

	/**
	 * @return
	 * @param xpath
	 */
	private boolean match(String xpath) {
		String type = bean.getType();
		// logger.info("type:" + type);
		type.toUpperCase();
		String pattern = (String) bean.getPatternMap().get(type);
		// logger.info("pattern:-      " + pattern);
		boolean match = false;
		StringTokenizer commaTok = new StringTokenizer(pattern, ",");
		if (!commaTok.hasMoreElements()) {
			match = true;
		}
		while (commaTok.hasMoreElements()) {
			String commatoken = commaTok.nextToken();
			StringTokenizer colonTok = new StringTokenizer(commatoken, ":");
			while (colonTok.hasMoreElements()) {
				String token = colonTok.nextToken();
				// logger.info("token-->" + token);
				if (xpath.contains(token)) {
					match = true;
				} else {
					match = false;
					break;
				}
			}
			if (match) {
				break;
			}
		}
		// logger.info("match:" + match);
		return match;
	}

	@SuppressWarnings("unchecked")
	public String resolve1() throws IntelliSException {

		String key = "";
		String xpathOut = "";
		List<String> xpathList = bean.getXpathList();
		Map<String, String> patternMap = bean.getPatternMap();
		String seq = bean.getPosition();
		String skip = bean.getSkip();
		String type = bean.getType();
		String name = bean.getName();

		Iterator<String> itr = xpathList.iterator();
		String pattern = (String) patternMap.get(type);
		int inSeq = 1;
		if (!seq.equals("") && !seq.equals("0")) {
			inSeq = Integer.parseInt(seq);
		}
		int inSkip = 0;
		if (!skip.equals("") && !skip.equals("0")) {
			inSkip = Integer.parseInt(skip);
		}
		int count = 1;
		int skipCount = 0;
		if (!type.equals("Button")) {
			while (itr.hasNext()) {
				String xpath = (String) itr.next();
				if (xpath.contains("'" + name + "'")) {
					// logger.info(key);
					// logger.info(xpath);
					if (inSeq == count) {
						key = name;
					}
					count++;
				}
				if (!key.equals("")) {
					boolean match = false;
					StringTokenizer commaTok = new StringTokenizer(pattern, ",");
					if (!commaTok.hasMoreElements()) {
						match = true;
					}
					while (commaTok.hasMoreElements()) {
						String commatoken = commaTok.nextToken();
						StringTokenizer colonTok = new StringTokenizer(
								commatoken, ":");
						while (colonTok.hasMoreElements()) {
							String token = colonTok.nextToken();
							if (xpath.contains(token)) {
								match = true;
							} else {
								match = false;
								break;
							}
						}
						if (match) {
							break;
						}
					}
					if (match) {
						if (inSkip == skipCount) {
							if (!xpath.contains("='" + name + "']")) {
								xpath = xpath.replace("='" + name + "'", "");
							}
							xpathOut = xpath;
							// xpMap.put(key, value);
							// logger.info(key + ":" + xpathOut);
							break;
						}
						skipCount++;
					}
				}
			}
		} else {
			for (int i = xpathList.size() - 1; i > 0; i--) {
				String xpath = (String) xpathList.get(i);
				if (xpath.contains("'" + name + "'")) {
					// logger.info(key);
					// logger.info(xpath);
					if (inSeq == count) {
						key = name;
					}
					count++;
				}
				if (!key.equals("")) {
					boolean match = false;
					StringTokenizer commaTok = new StringTokenizer(pattern, ",");
					if (!commaTok.hasMoreElements()) {
						match = true;
					}
					while (commaTok.hasMoreElements()) {
						String commatoken = commaTok.nextToken();
						// logger.info("commatoken:" + commatoken);
						StringTokenizer colonTok = new StringTokenizer(
								commatoken, ":");
						while (colonTok.hasMoreElements()) {
							String token = colonTok.nextToken();
							// logger.info("token-->" + token);
							if (xpath.contains(token)) {
								match = true;
							} else {
								match = false;
								break;
							}
						}
						if (match) {
							break;
						}
					}
					if (match) {
						if (inSkip == skipCount) {
							if (!xpath.contains("@value")) {
								xpath = xpath.replace("='" + name + "'", "");
							}
							xpathOut = xpath;
							logger.info(key + ":" + xpathOut);
							break;
						}
						skipCount++;
					}
				}
			}
		}
		if (xpathOut.equals("")) {
			throw new IntelliSException("500", "XPath cannot be resolved for :"
					+ name);
		}
		return xpathOut;
	}
}