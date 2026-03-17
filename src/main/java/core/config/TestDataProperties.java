package core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataProperties {

    private static Properties properties;

    private static Properties getProperties() {
        if (properties == null) {
            loadProperties();
        }
        return properties;
    }

    private static void loadProperties() {
        properties = new Properties();
        String fileName = System.getProperty("test.data.file", "testdata.properties");

        try (InputStream input = TestDataProperties.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + fileName, e);
        }
    }

    public static String get(String key) {
        String value = getProperties().getProperty(key);
        if (value == null) {
            throw new RuntimeException("Test data property not found: " + key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    public static void reload() {
        properties = null;
        loadProperties();
    }
}
