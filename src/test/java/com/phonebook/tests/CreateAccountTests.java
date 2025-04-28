package com.phonebook.tests;

import com.phonebook.data.UserData;
import com.phonebook.models.User;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateAccountTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        if (!app.getUser().isLoginLinkPresent()) {
            app.getUser().clickOnSignOutButton();

        }
    }

    SoftAssert softAssert = new SoftAssert();

    @Test(enabled = false)
    public void newUserRegistrationPositiveTest() {
        // 1) Открыть форму логина/регистрации
        app.getUser().clickOnLoginLink();

        // 2) Заполнить поля логина (email + password)
        app.getUser().fillRegisterLoginForm(new User()
                .setEmail(UserData.Email)
                .setPassword(UserData.Password));

        // 3) Нажать кнопку Registration
        app.getUser().clickOnRegistrationButton();

        // 4) Убедиться, что появилась кнопка Sign Out
        Assert.assertTrue(app.getUser().isSignOutButtonPresent());
    }

    @Test
    public void existedUserRegistrationNegativeTest() {
        // 1) Открыть форму логина/регистрации
        app.getUser().clickOnLoginLink();

        // 2) Заполнить только password (без email)
        app.getUser().fillRegisterLoginForm(new User()
                .setPassword(UserData.Email)
                .setPassword(UserData.Password));

        // 3) Нажать Registration
        app.getUser().clickOnRegistrationButton();

        // 4) Проверки (SoftAssert):
        //   — появился alert?
        softAssert.assertTrue(app.getUser().isAlertDisplayed());
        softAssert.assertTrue(app.getUser().isErrorMessagePresent());
        softAssert.assertAll();
    }
}




