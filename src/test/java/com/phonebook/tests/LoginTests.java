package com.phonebook.tests;

import com.phonebook.data.UserData;
import com.phonebook.models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        if (!app.getUser().isLoginLinkPresent()) {
            app.getUser().clickOnSignOutButton();
        }
    }

    @Test
    public void loginPositiveTest() {
        logger.info("Login with data-->"+UserData.Email+"**********************"+UserData.Password);
        // Открыть форму логина
        app.getUser().clickOnLoginLink();

        // Ввести email и пароль
        app.getUser().fillRegisterLoginForm(
                new User().setEmail(UserData.Email).setPassword(UserData.Password)
        );

        // Нажать Login
        app.getUser().clickOnLoginButton();

        // Проверить наличие кнопки Sign Out
        Assert.assertTrue(app.getUser().isSignOutButtonPresent());
    }

    @Test
    public void loginNegativeWithoutEmailTest() {
        // Открыть форму логина
        app.getUser().clickOnLoginLink();

        // Ввести только пароль
        app.getUser().fillRegisterLoginForm(
                new User().setPassword(UserData.Password)
        );

        // Нажать Login
        app.getUser().clickOnLoginButton();

        // Проверить наличие алерта
        Assert.assertTrue(app.getUser().isAlertDisplayed());
    }

}
