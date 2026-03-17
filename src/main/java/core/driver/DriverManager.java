package core.driver;

import core.config.ConfigManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class DriverManager {

    private static AndroidDriver driver;

    public static AndroidDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    private static void createDriver() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", ConfigManager.get("platformName"));
            caps.setCapability("deviceName", ConfigManager.get("deviceName"));
            caps.setCapability("udid", ConfigManager.get("udid"));
            caps.setCapability("appPackage", ConfigManager.get("appPackage"));
            caps.setCapability("appActivity", ConfigManager.get("appActivity"));
            caps.setCapability("automationName", "UiAutomator2");
            String noReset = ConfigManager.get("noReset");
            caps.setCapability("noReset", Boolean.parseBoolean(noReset != null ? noReset : "true"));

            caps.setCapability("newCommandTimeout", 2000);
            caps.setCapability("adbExecTimeout", 120000);
            driver = new AndroidDriver(new URL(ConfigManager.get("appiumServer")), caps);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create driver: " + e.getMessage(), e);
        }
    }

    public static void quit() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
