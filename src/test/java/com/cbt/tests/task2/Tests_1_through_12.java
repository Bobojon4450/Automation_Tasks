package com.cbt.tests.task2;

import com.cbt.utils.BrowserFactory;
import com.cbt.utils.BrowserUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

public class Tests_1_through_12 {

    private WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = BrowserFactory.getDriver("chrome");
        BrowserUtils.maximaze(driver);
        BrowserUtils.implicitWait(10, driver);
        driver.get("https://practice-cybertekschool.herokuapp.com/");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test(description = "Verify that warning message is displayed")
    public void testCase_1(){
        driver.findElement(By.linkText("Registration Form")).click();
        WebElement DOB = driver.findElement(By.cssSelector(".col-sm-5 > input[placeholder='MM/DD/YYYY']"));
        DOB.sendKeys("wrong_dob");
        WebElement errorMessage = driver.findElement(By.xpath("//div[@class='col-sm-5']//small[@class='help-block' and contains(text(),'The date of birth is not valid')]"));
        Assert.assertEquals("The date of birth is not valid", errorMessage.getText());
        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test(description = "Verifying programming languages are displayed")
    public void testCase_2(){
        String[] expectedLanguages = {"C++", "Java", "JavaScript"};
        driver.findElement(By.linkText("Registration Form")).click();
        List<WebElement>list = new ArrayList<>(); // = driver.findElements(By.cssSelector(".form-check.form-check-inline"));
        for (int i = 1; i <= 3; i++) {
            list.add(driver.findElement(By.xpath("//label[@for = \"inlineCheckbox"+ i +"\"" + "]" )));
        }

        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(list.get(i).getText(), expectedLanguages[i]);
            Assert.assertTrue(list.get(i).isDisplayed());
            Assert.assertTrue(list.get(i).isEnabled());
        }
    }

    @Test(description = "Verifying first names' error message is displayed")
    public void testCase_3(){
        String expectedMessage = "first name must be more than 2 and less than 64 characters long";
        driver.findElement(By.linkText("Registration Form")).click();
        driver.findElement(By.name("firstname")).sendKeys("a");
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-bv-result =\"INVALID\"]"));
        Assert.assertEquals(errorMessage.getText(), expectedMessage);
        Assert.assertTrue(errorMessage.isDisplayed());
    }

    @Test(description = "Verifying last names' error message is displayed")
    public void testCase_4(){
        String expectedErrorMsg = "The last name must be more than 2 and less than 64 characters long";
        driver.findElement(By.linkText("Registration Form")).click();
        driver.findElement(By.name("lastname")).sendKeys("A");
        WebElement lastNameErrorMsg = driver.findElement(By.cssSelector("[data-bv-result =\"INVALID\"]"));
        Assert.assertEquals(expectedErrorMsg, lastNameErrorMsg.getText());
        Assert.assertTrue(lastNameErrorMsg.isDisplayed());
    }

    @Test(description = "End-to-End scenario, Verifying success message is displayed")
    public void testCase_5(){
        String expectedMsg = "You've successfully completed registration!";
        driver.findElement(By.linkText("Registration Form")).click();
        driver.findElement(By.name("firstname")).sendKeys("Adam");
        driver.findElement(By.name("lastname")).sendKeys("Adamsho");
        driver.findElement(By.name("username")).sendKeys("cybertek");
        driver.findElement(By.name("email")).sendKeys("student@cybertekschool.com");
        driver.findElement(By.name("password")).sendKeys("cybertek1212");
        driver.findElement(By.name("phone")).sendKeys("804-111-1111");
        driver.findElement(By.cssSelector("[value=\"male\"]")).click();
        driver.findElement(By.name("birthday")).sendKeys("01/10/1990");
        WebElement dropDown = driver.findElement(By.name("department"));
        Select select = new Select(dropDown);
        select.selectByValue("DE");BrowserUtils.wait(1);
        Select select1 = new Select(driver.findElement(By.name("job_title")));
        List<WebElement> jobList = select1.getOptions();
        System.out.println("jobList.size(): "+jobList.size());
        for (WebElement option : jobList) {
            if(option.getText().equals("SDET")){
                option.click();
                break;
            }
        }BrowserUtils.wait(1);
        WebElement javaCheckbox = driver.findElement(By.id("inlineCheckbox2"));
        if(javaCheckbox.isEnabled() && !javaCheckbox.isSelected()){
            javaCheckbox.click();
        }
        driver.findElement(By.id("wooden_spoon")).click();
        BrowserUtils.wait(1);
        WebElement successMsg = driver.findElement(By.cssSelector("[class='alert alert-success'] > p"));
        Assert.assertEquals(expectedMsg,successMsg.getText());
        Assert.assertTrue(successMsg.isDisplayed());

       /* WebElement successMsg = driver.findElement(By.cssSelector(".alert.alert-success p"));*/
    }

    @Test(description = "Upload file, Verifying success message is displayed")
    public void testCase_7(){
        driver.findElement(By.linkText("File Upload")).click();
        driver.findElement(By.id("file-upload")).sendKeys("C:\\Users\\Vaha\\Desktop\\classNotes.txt");
        BrowserUtils.wait(1);
        driver.findElement(By.id("file-submit")).click();
        BrowserUtils.wait(2);
        String expectedMsg = "File Uploaded!";
        WebElement successMsg = driver.findElement(By.cssSelector(".example h3"));
        Assert.assertEquals(expectedMsg, successMsg.getText());
        Assert.assertTrue(successMsg.isDisplayed());
    }

    @Test(description = "Upload file, Verifying success message is displayed")
    public void testCase_8(){
        String expectedMsg = "You selected: United States of America";
        driver.findElement(By.linkText("Autocomplete")).click();
        driver.findElement(By.id("myCountry")).sendKeys("United States of America");
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
        WebElement successMsg = driver.findElement(By.id("result"));
        Assert.assertEquals(expectedMsg, successMsg.getText());
        Assert.assertTrue(successMsg.isDisplayed());
    }

    @DataProvider(name = "testData")
    public static Object[][] testData() {
        return new Object[][]{{200}, {301}, {404}, {500}};
    }

    @Test(dataProvider = "testData")
    public void testCase_9_10_11_12(int x){
        String expectedMsg = "This page returned a "+ x +" status code.";
        driver.findElement(By.linkText("Status Codes")).click();
        driver.findElement(By.xpath("//a[@href='status_codes/"+ x + "']")).click();
        WebElement successMsg = driver.findElement(By.cssSelector(".example p"));
        Assert.assertTrue(successMsg.getText().contains(expectedMsg));
        Assert.assertTrue(successMsg.isDisplayed());
    }



}
