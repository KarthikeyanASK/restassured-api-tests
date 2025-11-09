package api.configurations;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;


public class RouteConfig {
	    private static Properties properties;

	    static {
	        try (InputStream input = new FileInputStream("src/test/resources/config/env.properties")) {
	            properties = new Properties();
	            properties.load(input);
	        } catch (IOException e) {
	        	System.err.println("Error loading environment configuration: " + e.getMessage());
	            throw new RuntimeException("Failed to load environment config", e);
	        }
	    }

	    public static String getBaseUrl() {
	        return properties.getProperty("base.url");
	    }

}