package com.driver.locator.writer;

public class ObjectFactory {
	
	public static FileWrite getObject(FileType type) {
		switch (type) {
		case Excel:
			return new ExcelFileWriter();

		case Csv:
			return new CsvFileWriter();
		}
		throw new RuntimeException("Cannot create Object");
	}

}
