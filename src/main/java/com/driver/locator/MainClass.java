package com.driver.locator;

import com.driver.locator.writer.FileType;

public class MainClass {

	public static void main(String[] args) {
		ElementLocator locator = new ElementLocator();
		try {
			locator.writeToFile(FileType.Json,"http://automationpractice.com/index.php");
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
