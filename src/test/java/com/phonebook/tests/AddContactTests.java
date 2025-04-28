package com.phonebook.tests;

import com.phonebook.data.ContactData;
import com.phonebook.data.UserData;
import com.phonebook.models.Contact;
import com.phonebook.models.User;
import com.phonebook.utils.DataProviders;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddContactTests extends TestBase {
    private String lastAddedContactName;

    @BeforeMethod
    public void precondition() {
        if (!app.getUser().isLoginLinkPresent()) {
            app.getUser().clickOnSignOutButton();
        }
        app.getUser().clickOnLoginLink();
        app.getUser().fillRegisterLoginForm(new User().setEmail(UserData.Email).setPassword(UserData.Password));
        app.getUser().clickOnLoginButton();
    }

    @Test
    public void addContactPositiveTest() {
        app.getContact().clickOnAddLink();
        lastAddedContactName = ContactData.Name;
        app.getContact().fillContactForm(new Contact()
                .setName(lastAddedContactName)
                .setLastName(ContactData.Last_Name)
                .setPhone(ContactData.Phone)
                .setEmail(ContactData.Email)
                .setAddress(ContactData.Address)
                .setDescription(ContactData.Description));

        // Click Save and handle potential alert
        app.getContact().clickOnSaveButton();

        WebDriver driver = app.getDriver();  // Use WebDriver from ApplicationManager
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }
        System.out.println("Checking if contact with name " + lastAddedContactName + " is added");

        Assert.assertTrue(app.getContact().isContactAdded(lastAddedContactName));
    }

    @Test(dataProvider = "addNewContact", dataProviderClass = DataProviders.class)
    public void addContactPositiveFromDataProviderTest(String name, String lastName,
                                                       String phone, String email,
                                                       String address, String description) {
        app.getContact().clickOnAddLink();
        lastAddedContactName = name;
        app.getContact().fillContactForm(new Contact()
                .setName(name)
                .setLastName(lastName)
                .setPhone(phone)
                .setEmail(email)
                .setAddress(address)
                .setDescription(description));
        app.getContact().clickOnSaveButton();

        WebDriver driver = app.getDriver();  // Use WebDriver from ApplicationManager
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }

        Assert.assertTrue(app.getContact().isContactAdded(name));
    }

    @Test(dataProvider = "addNewContactWithCsv", dataProviderClass = DataProviders.class)
    public void addContactPositiveFromDataProviderWithCsvFileTest(Contact contact) {
        app.getContact().clickOnAddLink();
        app.getContact().fillContactForm(contact);
        app.getContact().clickOnSaveButton();

        WebDriver driver = app.getDriver();  // Use WebDriver from ApplicationManager
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }

        Assert.assertTrue(app.getContact().isContactAdded(contact.getName()));
        Assert.assertTrue(app.getContact().isContactAdded(lastAddedContactName),
                "Contact " + lastAddedContactName + " not found after adding!");

    }

    @AfterMethod
    public void postCondition() {
        try {
            Alert alert = app.getDriver().switchTo().alert();  // Заменили driver на app.getDriver()
            alert.accept(); // Принять алерт, если он есть
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            // Игнорируем, если алерт не появился
            System.out.println("No alert appeared.");
        }
        app.getContact().deleteContact();
    }
}

