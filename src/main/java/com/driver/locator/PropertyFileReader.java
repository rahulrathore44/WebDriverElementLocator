package com.driver.locator;

import java.io.FileInputStream;
import java.util.Properties;

import com.driver.locator.utility.ResourceHelper;

public class PropertyFileReader {
	
	private Properties aProperty = null;
	private String bFileName = "";
	
	private String getFilePath(){
		return this.getClass().getResource("/").getPath() + "configfile/" + this.bFileName;
	}
	
	public PropertyFileReader() {
		this.bFileName = "config.properties";
		
		try {
				aProperty = new Properties();
				aProperty.load(ResourceHelper.getResourcePathInputStream("configfile/" + this.bFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PropertyFileReader(String cConfigPropFileName) {
		this.bFileName = cConfigPropFileName;
		
		try {
				aProperty = new Properties();
				aProperty.load(new FileInputStream(getFilePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getWebsiteNames() {
		return aProperty.getProperty("Website").split(",");
	}
}
