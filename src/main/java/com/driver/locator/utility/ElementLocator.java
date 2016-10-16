package com.driver.locator.utility;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.driver.locator.PropertyFileReader;
import com.opencsv.CSVWriter;

public class ElementLocator {
	
	private PropertyFileReader rReader;
	private WebDriver dDriver;
	private Document dDocument;
	
	private String getUniqueLocator(WebDriver aDriver,Element bElement) {
		Attributes aAttributes = bElement.attributes();
		
		if( !aAttributes.get("id").isEmpty() && isUnique(aDriver, By.id(aAttributes.get("id")))){
			return "Id : " + aAttributes.get("id");
		}else if( !aAttributes.get("class").isEmpty() && isUnique(aDriver, By.className(aAttributes.get("class")))){
			return "Class Name : " + aAttributes.get("class");
		}else if( !aAttributes.get("name").isEmpty() && isUnique(aDriver, By.name(aAttributes.get("name")))){
			return "Name : " + aAttributes.get("name");
		}else if(bElement.hasText()){
			String xPath = "//" + bElement.nodeName() + "[normalize-space(text())='" + bElement.ownText().trim() + "']";
			if (isUnique(aDriver, By.xpath(xPath))){
				return "Xpath : " + xPath;
			}
		}
		return "No Unique Locator";
		
	}
	
	private boolean isUnique(WebDriver driver,By locator) {
		try {
			return driver.findElements(locator).size() == 1;
		} catch (Exception e) {
			//Ignore
		}
		return false;
		
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
	
	private boolean writeToCsvFile(List<String[]> dData){
		try (CSVWriter writer = new CSVWriter(new FileWriter("users.csv",true),',')){
			writer.writeAll(dData);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ElementLocator() {
		rReader = new PropertyFileReader();
		dDriver = new HtmlUnitDriver();
	}
	
	
	
	public void generateLocator() {
		
	}
	

}
