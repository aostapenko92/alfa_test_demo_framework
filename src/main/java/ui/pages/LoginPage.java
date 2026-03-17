package ui.pages;

import core.driver.DriverManager;
import core.element.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.entities.User;

public class LoginPage extends AbstractPage {

    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    @FindBy(id = "com.alfabank.qapp:id/tvTitle")
    private ExtendedWebElement title;

    @FindBy(id = "com.alfabank.qapp:id/etUsername")
    private ExtendedWebElement usernameField;

    @FindBy(id = "com.alfabank.qapp:id/etPassword")
    private ExtendedWebElement passField;

    @FindBy(id = "com.alfabank.qapp:id/btnConfirm")
    private ExtendedWebElement confirmButton;

    @FindBy(id = "com.alfabank.qapp:id/text_input_end_icon")
    private ExtendedWebElement showPassButton;

    @FindBy(id = "com.alfabank.qapp:id/tvError")
    private ExtendedWebElement errorMessageTextView;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return title.isElementPresent(100);
    }

    public boolean isUsernameFieldFieldPresent() {
        return usernameField.isElementPresent();
    }

    public String getUsernameFieldText() {
        return usernameField.getText();
    }

    public void typeUsername(String username) {
        usernameField.type(username);
    }

    public boolean isPassFieldPresent() {
        return passField.isElementPresent();
    }

    public String getPassFieldText() {
        return passField.getText();
    }

    public void typePass(String pass) {
        passField.type(pass);
    }

    public boolean isPassFieldHidden() {
        return passField.getElement().getAttribute("password").equals("true");
    }

    public void clickOnShowPassButton() {
        showPassButton.click();
    }

    public void clickOnConfirmButton() {
        confirmButton.click();
    }

    public String getErrorMessageText() {
        return errorMessageTextView.getText();
    }

    public boolean isErrorMessagePresent() {
        return errorMessageTextView.isElementPresent();
    }

    public SuccessfulLoginPage login(User user) {
        typeUsername(user.getUsername());
        typePass(user.getPass());
        clickOnConfirmButton();
        return new SuccessfulLoginPage(DriverManager.getDriver());
    }
}