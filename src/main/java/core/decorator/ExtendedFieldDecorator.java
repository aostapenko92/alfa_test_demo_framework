package core.decorator;

import core.element.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;

public class ExtendedFieldDecorator implements FieldDecorator {

    private WebDriver driver;

    public ExtendedFieldDecorator(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        FindBy findBy = field.getAnnotation(FindBy.class);
        if (findBy == null)
            return null;
        By locator = getLocator(findBy);
        if (locator == null)
            return null;
        else return new ExtendedWebElement(driver, locator);
    }

    private By getLocator(FindBy findBy) {
        if (!findBy.id().isEmpty()) return By.id(findBy.id());
        if (!findBy.xpath().isEmpty()) return By.xpath(findBy.xpath());
        if (!findBy.className().isEmpty()) return By.className(findBy.className());
        if (!findBy.name().isEmpty()) return By.name(findBy.name());
        return null;
    }

}