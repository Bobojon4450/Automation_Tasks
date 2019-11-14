package com.cbt.tests.additional_tasks;

import com.cbt.utilities.BrowserFactory;
import com.cbt.utilities.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AdditionalTasks {
    WebDriver driver;
    WebElement tc4_errorMessage;
    WebElement tc5_errorMessage;
    WebElement tc6_errorMessage;
    WebElement tc7_errorMessage_1;
    WebElement tc7_errorMessage_2;
    WebElement tc8_errorMessage;
    String URL = "https://practice-cybertekschool.herokuapp.com";
    String tc1_expected = "Thank you for signing up. Click the button below to return to the home page.";
    String tc3_expectedText = "Clicked on button one!";
    int tc2_expectedExamples = 48;

    @BeforeMethod
    public void setUp(){
        driver = BrowserFactory.getDriver("chrome");
        BrowserUtils.maximaze(driver);
        driver.get(URL);
        BrowserUtils.wait(1);
    }

    @AfterMethod
    public void tearDown(){
        BrowserUtils.wait(1);
        driver.quit();
    }

    @Test
    public void testCase_1() {
        driver.findElement(By.linkText("Sign Up For Mailing List")).click();
        driver.findElement(By.cssSelector("input[name ='full_name']")).sendKeys("Adamsho");
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector("input[name ='email']")).sendKeys("adam@gmail.com");
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector(".radius")).click();
        BrowserUtils.wait(3);
        String actual = driver.findElement(By.cssSelector("h3[name='signup_message']")).getText();
        Assert.assertEquals(actual, tc1_expected);
        WebElement homeButton = driver.findElement(By.cssSelector("#wooden_spoon"));
        BrowserUtils.wait(1);
        Assert.assertTrue(homeButton.isDisplayed());
    }

    @Test
    public void testCase_2() {
        BrowserUtils.wait(1);
        List<WebElement> list = driver.findElements(By.cssSelector("ul[class='list-group'] li"));
        BrowserUtils.wait(1);
        Assert.assertTrue(tc2_expectedExamples == list.size());
    }

    @Test
    public void testCase_3() {
        driver.findElement(By.linkText("Multiple Buttons")).click();
        BrowserUtils.wait(1);
        driver.findElement(By.xpath("//div[@class='container']/button[1]")).click();
        String actualText = driver.findElement(By.cssSelector("#result")).getText();
        Assert.assertEquals(tc3_expectedText, actualText);
        BrowserUtils.wait(1);
        Assert.assertTrue(driver.findElement(By.cssSelector("#result")).isDisplayed());
    }

    @Test
    public void testCase_4() {
        driver.findElement(By.linkText("Registration Form")).click();
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector("input[name='firstname']")).sendKeys("123");
        tc4_errorMessage = driver.findElement(By.xpath("//small[contains(text(), \"first name can only consist of alphabetical letters\")]"));
        Assert.assertTrue(tc4_errorMessage.isDisplayed());
    }

    @Test
    public void testCase_5() {
        driver.findElement(By.linkText("Registration Form")).click();
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector("input[name='lastname']")).sendKeys("123");
        tc5_errorMessage = driver.findElement(By.xpath("//small[contains(text(), 'The last name can only consist of alphabetical letters and dash')]"));
        Assert.assertTrue(tc5_errorMessage.isDisplayed());
    }

    @Test
    public void testCase_6() {
        driver.findElement(By.linkText("Registration Form")).click();
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector("input[name='username']")).sendKeys("user");
        tc6_errorMessage = driver.findElement(By.xpath("//small[contains(text(), 'The username must be more than 6 and less than 30 characters long')]"));
        Assert.assertTrue(tc6_errorMessage.isDisplayed());
    }

    @Test
    public void testCase_7() {
        driver.findElement(By.linkText("Registration Form")).click();
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("testers@email");
        tc7_errorMessage_1 = driver.findElement(By.xpath("//small[contains(text(), 'email address is not a valid')]"));
        tc7_errorMessage_2 = driver.findElement(By.xpath("//small[contains(text(), 'Email format is not correct')]"));
        Assert.assertTrue(tc7_errorMessage_1.isDisplayed());
        BrowserUtils.wait(1);
        Assert.assertTrue(tc7_errorMessage_2.isDisplayed());
        BrowserUtils.wait(1);
    }

    @Test
    public void testCase_8() {
        driver.findElement(By.linkText("Registration Form")).click();
        BrowserUtils.wait(1);
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("5711234354");
        tc8_errorMessage = driver.findElement(By.xpath("//small[contains(text(), 'Phone format is not correct')]"));
        Assert.assertTrue(tc8_errorMessage.isDisplayed());
    }

}
