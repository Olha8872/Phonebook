package com.phonebook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTests extends TestBase {
    @BeforeMethod
    public void ensureprecondition() {
        if (!app.getHome().isHomeComponentPresent()) {
            app.getHome().clickOnHomeLink();
        }
    }

    @Test
    public void isHomeComponentPresentTest() {
        // Проверка наличия главного компонента на домашней странице
        Assert.assertTrue(app.getHome().isHomeComponentPresent());
    }
}

