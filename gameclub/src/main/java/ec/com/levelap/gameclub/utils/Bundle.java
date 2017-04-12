package ec.com.levelap.gameclub.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Bundle {

	private static Resource resource = new ClassPathResource("systemParameters.properties");

	private static String getResurceBundle(String key, String attribute) throws IOException {
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		StringBuilder keyName = new StringBuilder();
		keyName.append(key);
		keyName.append(".");
		keyName.append(attribute);
		return props.get(keyName.toString()).toString();
	}

	public static String getName(String key) throws IOException {
		return getResurceBundle(key, "name");
	};

	public static String getCode(String key) throws IOException {
		return getResurceBundle(key, "code");
	};
}
