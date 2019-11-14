package com.cbt.tests.basicNavigation;

import com.cbt.utilities.BrowserFactory;
import com.cbt.utilities.BrowserUtils;
import com.cbt.utilities.StringUtility;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BasicNavigation {

    WebDriver driver;
    static String ie_driver = "webdriver.ie.driver";
    static String driverPath = "C:/Users/Vaha/Documents/libraries/IEDriverServer.exe";
    String googleURL = "https://google.com";
    String etsyURL = "https://etsy.com";

    @BeforeMethod
    public void setUp(){
        driver = BrowserFactory.getDriver("chrome");
    }

    @AfterMethod
    public void tearDown(){
        BrowserUtils.wait(1);
        driver.quit();
    }

    @Test
    public void testChrome(){
        driver.get(googleURL); //2
        String googleTitle = driver.getTitle();
        driver.get(etsyURL);
        String etsyTitle = driver.getTitle();
        driver.navigate().back();
        StringUtility.verifyEquals(googleTitle, driver.getTitle());
        driver.navigate().forward();
        StringUtility.verifyEquals(etsyTitle, driver.getTitle());
    }

    @Test
    public void testIE(){
        System.setProperty(ie_driver, driverPath);
        driver.get(googleURL); //2
        String googleTitle = driver.getTitle();
        driver.get(etsyURL);
        String etsyTitle = driver.getTitle();
        driver.navigate().back();
        StringUtility.verifyEquals(googleTitle, driver.getTitle());
        driver.navigate().forward();
        StringUtility.verifyEquals(etsyTitle, driver.getTitle());
    }

    @Test
    public void testFirefox(){
        WebDriver driver = BrowserFactory.getDriver("firefox");
        driver.get(googleURL); //2
        String googleTitle = driver.getTitle();
        driver.get(etsyURL);
        String etsyTitle = driver.getTitle();
        driver.navigate().back();
        StringUtility.verifyEquals(googleTitle, driver.getTitle());
        driver.navigate().forward();
        StringUtility.verifyEquals(etsyTitle, driver.getTitle());
    }
}
