package com.driver.locator;

public class MainClass {

	public static void main(String[] args) {
		ElementLocator locator = new ElementLocator();
		try {
			locator.generateLocator();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
