package tests;

import core.config.TestDataProperties;
import core.utils.RandomGenerator;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ui.entities.User;
import ui.pages.LoginPage;
import ui.pages.SuccessfulLoginPage;

@Feature("Authorization")
public class AlfaTest extends BaseTest {

    private final String DEFAULT_USERNAME_FIELD_VALUE = TestDataProperties.get("login.placeholder.username");
    private final String DEFAULT_PASS_FIELD_VALUE = TestDataProperties.get("login.placeholder.password");
    private final String VALID_USERNAME = TestDataProperties.get("login.valid.username");
    private final String VALID_PASS = TestDataProperties.get("login.valid.password");
    private final String ERROR_MESSAGE = TestDataProperties.get("expected.error.message");
    private final String INVALID_CHARACTERS_USERNAME = RandomGenerator.generateUsername(false, 10);
    private final String INVALID_LENGTH_USERNAME = RandomGenerator.generateUsername(true, 60);
    private final String INVALID_PASS = RandomGenerator.generatePass(60);

    @Owner("aostapenko")
    @Test
    @Story("Username field")
    @Description("Validate username field display and text input")
    public void loginFieldUITest() {
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(driver);
        Allure.step("Check that Login page is open", () -> {
            Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        });
        Allure.step("Check that Login field is present", () -> {
            Assert.assertTrue(loginPage.isUsernameFieldFieldPresent(), "Login field is not present");
        });
        Allure.step("Validate placeholder", () -> {
            softAssert.assertEquals(loginPage.getUsernameFieldText(), DEFAULT_USERNAME_FIELD_VALUE, "Placeholder text is not correct");
        });
        Allure.step("Type valid username", () -> {
            loginPage.typeUsername(VALID_USERNAME);
        });
        Allure.step("Check username in the field", () -> {
            softAssert.assertEquals(loginPage.getUsernameFieldText(), VALID_USERNAME, "Login field text is not correct after changing");
        });
        softAssert.assertAll();
    }

    @DataProvider(name = "usernameData")
    public Object[][] provideUsernameData() {
        return new Object[][]{
                {"INVALID CHARACTERS USERNAME", INVALID_CHARACTERS_USERNAME},
                {"INVALID LENGTH USERNAME", INVALID_LENGTH_USERNAME},
                {"VALID USERNAME", VALID_USERNAME}
        };
    }

    @Owner("aostapenko")
    @Test(dataProvider = "usernameData")
    @Story("Username field")
    @Description("Validate username field input validation rules and error message")
    public void loginFieldValidationsTest(String usernameDescription, String usernameValue) {
        String regex = "^[A-Za-z.,/'_\\- ]{1,50}$";
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.typeUsername(usernameValue);
        loginPage.clickOnConfirmButton();
        if (usernameValue.matches(regex)) {
            Assert.assertFalse(loginPage.isErrorMessagePresent(), "[" + usernameDescription + "] Error username message present");
        } else {
            Assert.assertTrue(loginPage.isErrorMessagePresent(), "[" + usernameDescription + "] Error username message is not present");
            Assert.assertEquals(loginPage.getErrorMessageText(), ERROR_MESSAGE, "[" + usernameDescription + "] Incorrect username error message");
        }
    }

    @DataProvider(name = "passeData")
    public Object[][] providePassData() {
        return new Object[][]{
                {"INVALID LENGTH PASS", INVALID_PASS},
                {"VALID PASS", VALID_PASS}
        };
    }

    @Owner("aostapenko")
    @Test(dataProvider = "passeData")
    @Story("Pass field")
    @Description("Validate pass field display and text input")
    public void passFieldValidationsTest(String passDescription, String passValue) {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.typePass(passValue);
        loginPage.clickOnConfirmButton();
        if (passValue.length() <= 50) {
            Assert.assertFalse(loginPage.isErrorMessagePresent(), "[" + passDescription + "] Error pass message present");
        } else {
            Assert.assertTrue(loginPage.isErrorMessagePresent(), "[" + passDescription + "] Error pass message is not present");
            Assert.assertEquals(loginPage.getErrorMessageText(), ERROR_MESSAGE, "[" + passDescription + "] Incorrect pass error message");
        }
    }

    @Owner("aostapenko")
    @Test
    @Story("Pass field")
    @Description("Validate pass field input validation rules and error message")
    public void passFieldUITest() {
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        Assert.assertTrue(loginPage.isPassFieldPresent(), "Pass field is not present");
        softAssert.assertEquals(loginPage.getPassFieldText(), DEFAULT_PASS_FIELD_VALUE, "Default pass field text is not correct");
        loginPage.typePass(VALID_PASS);
        softAssert.assertTrue(loginPage.isPassFieldHidden(), "Pass field is not hidden by default");
        loginPage.clickOnShowPassButton();
        softAssert.assertFalse(loginPage.isPassFieldHidden(), "Pass field hidden after click 'Show pass' button");
        softAssert.assertEquals(loginPage.getPassFieldText(), VALID_PASS, "Pass field text is not correct after changing");
        softAssert.assertAll();
    }

    @Owner("aostapenko")
    @Test
    @Story("Login")
    @Description("Verify successful login")
    public void successfulLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        SuccessfulLoginPage successfulLoginPage =loginPage.login(new User(VALID_USERNAME, VALID_PASS));
        Assert.assertTrue(successfulLoginPage.isPageOpened(), "Successful login page is not opened");
        Assert.assertEquals(successfulLoginPage.isPageOpened(), "Successful login page is not opened");
    }

}