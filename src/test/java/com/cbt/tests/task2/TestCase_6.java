package com.cbt.tests.task2;


import com.cbt.utils.BrowserFactory;
import com.cbt.utils.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestCase_6 {

    private WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = BrowserFactory.getDriver("chrome");
        BrowserUtils.maximaze(driver);driver.get("https://www.tempmailaddress.com/");
        BrowserUtils.implicitWait(10, driver);
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void test_6(){
        String expectedSuccessMsg = "Thank you for signing up. Click the button below to return to the home page.";
        String email = driver.findElement(By.cssSelector(".animace")).getText();
        driver.navigate().to("https://practice-cybertekschool.herokuapp.com/");
        BrowserUtils.wait(1);
        driver.findElement(By.linkText("Sign Up For Mailing List")).click();
        driver.findElement(By.name("full_name")).sendKeys("Adamsho");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("wooden_spoon")).click();
        WebElement successMsg = driver.findElement(By.name("signup_message"));
        Assert.assertEquals(expectedSuccessMsg, successMsg.getText());
        Assert.assertTrue(successMsg.isDisplayed());
        driver.navigate().back();driver.navigate().back();driver.navigate().back();
        WebElement emailReceived = driver.findElement(By.xpath("(//table[@class='table table-hover']/tbody/tr/td)[1]"));
        WebDriverWait wait = new WebDriverWait(driver, 35);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//table[@class='table table-hover']/tbody/tr/td)[1]")));
        wait.until(ExpectedConditions.visibilityOf(emailReceived));
        wait.until(ExpectedConditions.elementToBeClickable(emailReceived));
        String expectedEmailTitle = "do-not-reply@practice.cybertekschool.com";
        Assert.assertEquals(expectedEmailTitle, emailReceived.getText().trim());
        emailReceived.click();BrowserUtils.wait(1);
        String verifyEmailSender = driver.findElement(By.id("odesilatel")).getText();
        Assert.assertEquals(verifyEmailSender,expectedEmailTitle);
        Assert.assertTrue(driver.findElement(By.id("odesilatel")).isDisplayed());
        WebElement subject = driver.findElement(By.id("predmet"));
        String expectedSubjectText = "Thanks for subscribing to practice.cybertekschool.com!";
        Assert.assertEquals(expectedSubjectText, subject.getText());
        Assert.assertTrue(subject.isDisplayed());
    }

}
