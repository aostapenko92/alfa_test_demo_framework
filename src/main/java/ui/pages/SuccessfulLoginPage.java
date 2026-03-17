package ui.pages;

import core.element.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class SuccessfulLoginPage extends AbstractPage {

    @FindBy(xpath = "//android.widget.TextView[@text='Вход в Alfa-Test выполнен']")
    private ExtendedWebElement successfulLoginTextView;

    public SuccessfulLoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return successfulLoginTextView.isElementPresent();
    }
}
