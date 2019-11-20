package com.cbt.utilities;

import org.openqa.selenium.WebDriver;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class BrowserUtils {

    public static void wait(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void implicitWait(int seconds, WebDriver driver){
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void maximaze(WebDriver driver){
        driver.manage().window().maximize();
    }


}
