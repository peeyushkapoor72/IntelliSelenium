package com.csc.intellis.core.bean;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import autoitx4java.AutoItX;

import org.openqa.selenium.WebDriver;

public class ExecutorParameterBean {

	private String dir;
	private String skip;
	private String type;
	private String name;
	private String data;
	private String xpath;
	private String caseId;
	private String htmlId;
	private String trigger;
	private String keyword;
	private String htmlName;
	private String position;
	private Process process;
	private WebDriver driver;
	private String htmlXpath;
	private static AutoItX autoIT;
	private List<String> xpathList;
	private Map<String, String> configMap;
	private List<List<String>> statuslist;
	private Map<String, String> patternMap;
	private Map<String, Map<String, Integer>> sequenceMap = new HashMap<String, Map<String, Integer>>();

	public static AutoItX autoIT() {
		autoIT = new AutoItX();
		return autoIT;
	}

	public String getDir() {
		return dir;
	}

	public String getTrigger() {
		return trigger;
	}

	public Process getProcess() {
		return process;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public List<List<String>> getStatuslist() {
		return statuslist;
	}

	public void setStatuslist(List<List<String>> statuslist) {
		this.statuslist = statuslist;
	}

	public Map<String, Map<String, Integer>> getSequenceMap() {
		return sequenceMap;
	}

	public void setSequenceMap(Map<String, Map<String, Integer>> sequenceMap) {
		this.sequenceMap = sequenceMap;
	}

	public String getCaseId() {
		return caseId;
	}

	// /**
	// * @return WebElement
	// */
	// public WebElement getWebElement() {
	// return saveWebElement;
	// }

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	/**
	 * @return the XPath
	 */
	public String getXpath() {
		return xpath;
	}

	/**
	 * @param xpath
	 *            the XPath to set
	 */
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * @return the keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the skip
	 */
	public String getSkip() {
		return skip;
	}

	/**
	 * @param skip
	 *            the skip to set
	 */
	public void setSkip(String skip) {
		this.skip = skip;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return HtmlId
	 */
	public String getHtmlId() {
		return htmlId;
	}

	/**
	 * @return HtmlName
	 */
	public String getHtmlName() {
		return htmlName;
	}

	/**
	 * @return HtmlXpath
	 */
	public String getHtmlXpath() {
		return htmlXpath;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public void setHtmlId(String htmlId) {
	}

	public void setHtmlName(String htmlName) {
	}

	public void setHtmlXpath(String htmlXpath) {
	}

	/**
	 * @return the xpathList
	 */
	@SuppressWarnings("rawtypes")
	public List getXpathList() {
		return xpathList;
	}

	/**
	 * @param xpathList
	 *            the xpathList to set
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setXpathList(List xpathList) {
		this.xpathList = xpathList;
	}

	/**
	 * @return the patternMap
	 */
	public Map<String, String> getPatternMap() {
		return patternMap;
	}

	/**
	 * @param patternMap
	 *            the patternMap to set
	 */
	public void setPatternMap(Map<String, String> patternMap) {
		this.patternMap = patternMap;
	}

	/**
	 * @return the configMap
	 */
	public Map<String, String> getConfigMap() {
		return configMap;
	}

	/**
	 * @param configMap
	 *            the configMap to set
	 */
	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}
}