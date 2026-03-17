package core.element;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExtendedWebElement {

    private WebDriver driver;
    private By locator;
    private final long EXPLICIT_TIMEOUT = 50;

    public ExtendedWebElement(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
    }

    public WebElement getElement() {
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void type(String value, boolean clearField) {
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(getElement()));
        if (clearField)
            el.clear();
        el.sendKeys(value);
    }

    public void type(String value) {
        type(value, true);
    }

    public void click() {
        click(EXPLICIT_TIMEOUT);
    }

    public void click(long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public boolean isElementPresent() {
        return isElementPresent(EXPLICIT_TIMEOUT);
    }

    public boolean isElementPresent(long timeOutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getText() {
        return getElement().getText();
    }

}