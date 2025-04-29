package com.phonebook.tests;

import com.phonebook.data.ContactData;
import com.phonebook.data.UserData;
import com.phonebook.models.Contact;
import com.phonebook.models.User;
import com.phonebook.utils.DataProviders;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

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

        WebDriver driver = app.getDriver();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }
        System.out.println("Checking if contact with name " + lastAddedContactName + " is added");

        // Wait for contact to appear on page and then assert
        Assert.assertTrue(app.getContact().waitForContactToAppear(lastAddedContactName),
                "Contact " + lastAddedContactName + " was not added successfully.");
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

        WebDriver driver = app.getDriver();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }

        Assert.assertTrue(app.getContact().waitForContactToAppear(name),
                "Contact " + name + " was not added successfully.");
    }

    @Test(dataProvider = "addNewContactWithCsv", dataProviderClass = DataProviders.class)
    public void addContactPositiveFromDataProviderWithCsvFileTest(Contact contact) {
        app.getContact().clickOnAddLink();
        app.getContact().fillContactForm(contact);
        app.getContact().clickOnSaveButton();

        WebDriver driver = app.getDriver();
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }

        Assert.assertTrue(app.getContact().waitForContactToAppear(contact.getName()),
                "Contact " + contact.getName() + " was not added successfully.");
    }

    @AfterMethod
    public void postCondition() {
        try {
            Alert alert = app.getDriver().switchTo().alert();
            alert.accept(); // Accept the alert if it appears
            System.out.println("Alert accepted successfully");
        } catch (NoAlertPresentException e) {
            System.out.println("No alert appeared.");
        }
        app.getContact().deleteContact();
    }
}
