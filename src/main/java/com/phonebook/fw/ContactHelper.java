package com.phonebook.fw;

import com.phonebook.models.Contact;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ContactHelper extends BaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(ContactHelper.class);

    public ContactHelper(WebDriver driver) {
        super(driver);
    }

    public void clickOnSaveButton() {
        click(By.cssSelector(".add_form__2rsm2 button"));
    }

    public void fillContactForm(Contact contact) {
        type(By.cssSelector("input:nth-child(1)"), contact.getName());
        type(By.cssSelector("input:nth-child(2)"), contact.getLastName());
        type(By.cssSelector("input:nth-child(3)"), contact.getPhone());
        type(By.cssSelector("input:nth-child(4)"), contact.getEmail());
        type(By.cssSelector("input:nth-child(5)"), contact.getAddress());
        type(By.cssSelector("input:nth-child(6)"), contact.getDescription());
    }

    public void clickOnAddLink() {
        click(By.cssSelector("[href='/add']"));
    }

    public boolean waitForContactToAppear(String name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Увеличен тайм-аут
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'" + name + "')]")
            ));
            logger.info("Контакт " + name + " появился успешно.");
            return true;
        } catch (TimeoutException e) {
            logger.error("Контакт " + name + " не появился за отведенное время.");
            return false;
        } catch (Exception e) {
            logger.error("Произошла неожиданная ошибка при ожидании контакта: " + e.getMessage());
            return false;
        }
    }



    public void deleteContact() {
        if (isElementPresent(By.xpath("(//div[contains(@class,'contact-item-detailed_card')]//button[text()='Remove'])[1]"))) {
            pause(1000);
            click(By.xpath("(//div[contains(@class,'contact-item-detailed_card')]//button[text()='Remove'])[1]"));
            logger.info("Contact removed.");
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                logger.info("Alert accepted after contact deletion.");
            } catch (NoAlertPresentException e) {
                logger.info("No alert appeared after contact deletion.");
            }
        } else {
            logger.warn("No contact found to delete.");
        }
    }

    public int sizeOfContacts() {
        if (isElementPresent(By.cssSelector(".contact-item_card__2SOIM"))) {
            return driver.findElements(By.cssSelector(".contact-item_card__2SOIM")).size();
        }
        return 0;
    }

    public boolean isContactAddedByName(String name) {
        WebElement wd = driver.findElement(By.tagName("body"));
        return wd.findElements(By.xpath("//h2[text()='" + name + "']")).size() > 0;
    }
}
