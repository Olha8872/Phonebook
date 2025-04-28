package com.phonebook.fw;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseHelper {

    protected WebDriver driver;

    // Конструктор для инициализации драйвера
    public BaseHelper(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для проверки наличия элемента на странице
    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    // Метод для ввода текста в поле
    public void type(By locator, String text) {
        if (text != null) {
            click(locator);
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        }
    }

    // Метод для клика по элементу
    public void click(By locator) {
        driver.findElement(locator).click();
    }

    // Метод для ожидания появления alert и его закрытия
    public boolean isAlertDisplayed() {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.alertIsPresent());
            if (alert != null) {
                alert.accept();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    // Метод для паузы (использовать с осторожностью)
    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error during sleep", e);
        }
    }
    public String takeScreenshot() {
        File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File screenshot = new File("screenshots/screen-" + System.currentTimeMillis() + ".png");
        try {
            Files.copy(tmp, screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return screenshot.getAbsolutePath();
    }

}
