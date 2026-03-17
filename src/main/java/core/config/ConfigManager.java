package core.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load config");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
