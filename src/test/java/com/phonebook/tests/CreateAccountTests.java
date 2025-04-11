package com.phonebook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAccountTests extends TestBase{

    @Test
    public void newUserRegistrationPositiveTest(){
        //click on login Link
        driver.findElement(By.cssSelector("[href='/login']")).click();

    //enter email to Email field
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("Aa123456$@gmail.com");//f@f.com
    //enter password to Password
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("Qwerty123@");//Aa123456$
        //click on Registration button
        driver.findElement(By.name("registration")).click();
//verify SignOut button is displayed
        Assert.assertTrue(isElementPresent(By.xpath("//button[.='Sign Out']")));

    }










}
