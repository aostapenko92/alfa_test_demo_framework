package tests;

import core.driver.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.nio.file.Files;

public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected AndroidDriver driver;

    @BeforeMethod
    public void setup() {
        logger.info("Initializing driver...");
        driver = DriverManager.getDriver();
    }

    @AfterMethod
    public void quitDriver(ITestResult result) {
        takeScreenshot(result.getName());
        DriverManager.quit();
        logger.info("Driver session closed successfully!");
    }

    private void takeScreenshot(String testName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotDir = new File("target/screenshots/");
            screenshotDir.mkdirs();
            String fileName = testName + "_" + System.currentTimeMillis() + ".png";
            File dest = new File(screenshotDir, fileName);
            FileUtils.copyFile(screenshot, dest);
            Allure.addAttachment("Screenshot: " + testName, Files.newInputStream(dest.toPath()));
            logger.info("Screenshot saved: {}", fileName);
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
        }
    }
}