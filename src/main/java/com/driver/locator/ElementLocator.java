package com.driver.locator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.driver.locator.model.IgnoreAttribute;
import com.driver.locator.utility.NullRemove;
import com.driver.locator.utility.ResourceHelper;
import com.opencsv.CSVWriter;

public class ElementLocator {
	
	private PropertyFileReader rReader;
	private WebDriver dDriver;
	private Document dDocument;
	
	private static final String TAG_LINK = "a";
	private static final String TAG_BUTTON = "button";
	private static final String TAG_INPUT = "input";
	private static final String TAG_DROP_DOWN = "select";
	private static final String TAG_TEXT_AREA = "textarea";
	
	
	private static final String LOCATOR_ID = "id";
	private static final String LOCATOR_CLASS = "class";
	private static final String LOCATOR_NAME = "name";
	
	private String getXpath(WebDriver aDriver,Element bElement){
		Iterator<Attribute> attIterator = bElement.attributes().iterator();
		String locator = "";
		
		while (attIterator.hasNext()) {
			Attribute attribute = (Attribute) attIterator.next();
			if(IgnoreAttribute.ignoreAttribute.contains(attribute.getKey()) || attribute.getValue().isEmpty()) 
				continue;
			locator = "//" + bElement.nodeName() + "[@" + attribute.getKey() + "='" + attribute.getValue() + "']";
			if(isUnique(aDriver, By.xpath(locator)))
				break;
			locator = "";
		}
		
		return locator.length() == 0 ? "No Unique Locator" : "Xpath:" + locator;
	}
	
	private String getUniqueLocator(WebDriver aDriver,Element bElement) {
		Attributes aAttributes = bElement.attributes();
		
		if( !aAttributes.get(LOCATOR_ID).isEmpty() && isUnique(aDriver, By.id(aAttributes.get(LOCATOR_ID)))){
			return "Id:" + aAttributes.get(LOCATOR_ID);
		}else if( !aAttributes.get(LOCATOR_CLASS).isEmpty() && isUnique(aDriver, By.className(aAttributes.get(LOCATOR_CLASS)))){
			return "Class Name:" + aAttributes.get(LOCATOR_CLASS);
		}else if( !aAttributes.get(LOCATOR_NAME).isEmpty() && isUnique(aDriver, By.name(aAttributes.get(LOCATOR_NAME)))){
			return "Name:" + aAttributes.get(LOCATOR_NAME);
		}else if(bElement.hasText()){
			String xPath = "//" + bElement.nodeName() + "[normalize-space(text())='" + bElement.ownText().trim() + "']";
			if (isUnique(aDriver, By.xpath(xPath))){
				return "Xpath:" + xPath;
			}
		}
		return  getXpath(dDriver,bElement);
		
	}
	
	private List<String> getElementsByTag(String tagName,WebDriver aDriver){
		Elements eleList = dDocument.getElementsByTag(tagName);
		List<String> locator = new ArrayList<String>();
		for (int i = 0; i < eleList.size(); i++) {
			locator.add(getUniqueLocator(aDriver, eleList.get(i)));
		}
		return locator;
	}
	
	private boolean isUnique(WebDriver driver,By locator) {
		try {
			return driver.findElements(locator).size() == 1;
		} catch (Exception e) {
		}
		return false;
		
	}
	
	public List<String> getLocator(WebDriver aDriver){
		List<String> locatorList = new ArrayList<String>();
			locatorList.addAll(getElementsByTag(TAG_LINK, aDriver));
			locatorList.addAll(getElementsByTag(TAG_BUTTON, aDriver));
			locatorList.addAll(getElementsByTag(TAG_DROP_DOWN, aDriver));
			locatorList.addAll(getElementsByTag(TAG_INPUT, aDriver));
			locatorList.addAll(getElementsByTag(TAG_TEXT_AREA, aDriver));
			locatorList.removeIf(new NullRemove());
		return locatorList;
		
	}
	
	private void openPage(String url) {
		try {
			dDocument = Jsoup.connect(url).get();
			dDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			dDriver.get(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean writeToCsvFile(String fileName,List<String> dData){
		try (CSVWriter writer = new CSVWriter(new FileWriter(ResourceHelper.getResourcePath("locator/") + fileName + ".csv",false),',')){
				for (String string : dData) {
					String[] str = string.split(":");
					writer.writeNext(str, false);
				}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ElementLocator() {
		rReader = new PropertyFileReader();
		dDriver = new HtmlUnitDriver();
		dDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}
		
	public void generateLocator() {
		Map<String, String> urlMap = rReader.getWebsiteNames();
		for (String websiteKey : urlMap.keySet()) {
			openPage(urlMap.get(websiteKey));
			writeToCsvFile(websiteKey,getLocator(this.dDriver));
		}
		if(this.dDriver != null)
			dDriver.quit();
	}
	

}
