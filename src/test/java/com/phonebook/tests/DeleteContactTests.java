package com.phonebook.tests;

import com.phonebook.data.ContactData;
import com.phonebook.data.UserData;
import com.phonebook.models.Contact;
import com.phonebook.models.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeleteContactTests extends TestBase {

    @BeforeMethod
    public void precondition() {
        if (!app.getUser().isLoginLinkPresent()) {
            app.getUser().clickOnSignOutButton();
        }
        app.getUser().clickOnLoginLink();
        app.getUser().fillRegisterLoginForm(new User().setEmail(UserData.Email).setPassword(UserData.Password));
        app.getUser().clickOnLoginButton();

        app.getContact().clickOnAddLink();
        app.getContact().fillContactForm(new Contact()
                .setName(ContactData.Name)
                .setLastName(ContactData.Last_Name)
                .setPhone(ContactData.Phone)
                .setEmail(ContactData.Email)
                .setAddress(ContactData.Address)
                .setDescription(ContactData.Description));
        app.getContact().clickOnSaveButton();
    }

    @Test
    public void deleteContactTest() {
        int sizeBefore = app.getContact().sizeOfContacts();

        // Удаляем контакт
        app.getContact().deleteContact();

        // Ожидаем появления алерта перед тем, как с ним взаимодействовать
        WebDriver driver = app.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Увеличено время ожидания

        try {
            // Попробуем обработать алерт, если он появляется
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert appeared: " + alert.getText()); // Логируем текст алерта
            alert.accept(); // Принять алерт
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }

        // Пауза на всякий случай, если нужно время для обновления данных
        app.getContact().pause(1000);

        int sizeAfter = app.getContact().sizeOfContacts();
        Assert.assertEquals(sizeAfter, sizeBefore - 1);
    }
}
