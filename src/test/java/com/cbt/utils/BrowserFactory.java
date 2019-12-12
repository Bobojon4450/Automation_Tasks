package com.cbt.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory {

    public static WebDriver getDriver(String browser){
        switch (browser.toLowerCase()){
            case "chrome":  WebDriverManager.chromedriver().setup();
                            return new ChromeDriver();
            case "firefox": WebDriverManager.firefoxdriver().setup();
                            return new  FirefoxDriver();
            case "ie":      WebDriverManager.iedriver().setup();
                            return new InternetExplorerDriver();
            default:
                return null;
        }
    }



}





    /*    if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }else {
            return null;
        }*/
