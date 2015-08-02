package org.hack.util;

import java.util.Properties;

public class EnvProperties {
	private static Properties prop = new Properties();
	static{
		prop.setProperty("env", "cloud");
		try {
			prop.load(EnvProperties.class.getClassLoader().getResourceAsStream("env_config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getProp(String propName){
		return prop.getProperty(propName);
	}
}
