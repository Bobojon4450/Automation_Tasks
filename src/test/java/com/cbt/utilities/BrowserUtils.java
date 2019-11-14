package com.cbt.utilities;

import org.openqa.selenium.WebDriver;

public class BrowserUtils {

    public static void wait(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void maximaze(WebDriver driver){
        driver.manage().window().maximize();
    }


}
