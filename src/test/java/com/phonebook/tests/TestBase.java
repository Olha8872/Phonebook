package com.phonebook.tests;

import com.phonebook.fw.ApplicationManager;
import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    // Инициализация ApplicationManager с параметром browser
    protected static ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName()));

    @BeforeSuite
    public void setUp() {
        // Устанавливаем драйвер в зависимости от браузера
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Tools\\chromedriver.exe");
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\Tools\\geckodriver.exe");
        } // Добавьте другие браузеры по необходимости
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void startTest(Method method, Object[] p) {
        logger.info("Start test: " + method.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod
    public void stopTest(ITestResult result) {
        if (result.isSuccess()) {
            logger.info("PASSED: " + result.getMethod().getMethodName());
        } else {
            // Логирование при неудачном тесте с путем до скриншота
            logger.error("FAILED: " + result.getMethod().getMethodName() + " Screenshot path: " + app.getUser().takeScreenshot());
        }
        logger.info("Stop test");
        logger.info("==========================================");
    }
}
