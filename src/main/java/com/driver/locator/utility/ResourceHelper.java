/**
 * @author rahul.rathore
 *	
 *	20-Jul-2016
 */
package com.driver.locator.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author rahul.rathore
 *	
 *	20-Jul-2016
 *
 */
public class ResourceHelper {

	public static String getResourcePath(String resource) {
		String path = getBaseResourcePath() + resource;
		//System.out.println(path);
		return path;
	}
	
	public static String getBaseResourcePath() {
		String path = ResourceHelper.class.getResource("/").getPath();
		//System.out.println(path);
		return path;
	}
	
	public static InputStream getResourcePathInputStream(String resource) throws FileNotFoundException {
		return new FileInputStream(ResourceHelper.getResourcePath(resource));
	}
	
}
