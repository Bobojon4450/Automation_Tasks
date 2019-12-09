package com.cbt.tests.test_case_4;

import com.cbt.utilities.BrowserUtils;
import com.cbt.utilities.ConfigurationReader;
import com.cbt.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestCases_4 {
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        BrowserUtils.maximaze(Driver.getDriver());
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(Driver.getDriver(), 35);
        /* waits for the entire dom's elements are loaded within given time-frame,
           continues if elements are loaded quicker then given time.            */
        Driver.getDriver().findElement(By.id("prependedInput")).sendKeys(ConfigurationReader.getProperty("user_name"));
        Driver.getDriver().findElement(By.id("prependedInput2")).sendKeys(ConfigurationReader.getProperty("password"), Keys.ENTER);
        /* String xpath = "//a[@class='unclickable' and @href = '#' ]/span[@class='title title-level-1' and contains(text(),'Activities')]";  */
        /* WebElement activitiesElement = driver.findElement(By.xpath(xpath));  */
        BrowserUtils.wait(3);
        WebElement activitiesElement = Driver.getDriver().findElement(By.linkText("Activities"));
        wait.until(ExpectedConditions.visibilityOf(activitiesElement));
        wait.until(ExpectedConditions.elementToBeClickable(activitiesElement));
        activitiesElement.click();

        WebElement calendarEventsElement = Driver.getDriver().findElement(By.linkText("Calendar Events"));
        wait.until(ExpectedConditions.visibilityOf(calendarEventsElement));
        wait.until(ExpectedConditions.elementToBeClickable(calendarEventsElement));
        calendarEventsElement.click();
        WebElement spinnerElement = Driver.getDriver().findElement(By.cssSelector("div[class='loader-mask shown']"));
        wait.until(ExpectedConditions.invisibilityOf(spinnerElement));
    }

    @AfterMethod
    public void tearUp() {
//        Driver.getDriver().quit();
    }

    @Test(description = "Verify that 'view', 'edit' and 'delete' options are available")
    public void test_1(){
        Actions actions = new Actions(Driver.getDriver());
        WebElement target = Driver.getDriver().findElement(By.cssSelector(".action-cell"));
        actions.moveToElement(target).perform();
        List<WebElement> listOfOptions = Driver.getDriver().findElements(By.xpath("//ul[@class='nav nav-pills icons-holder launchers-list']/li/a"));
        List<String> expectedOptions = new ArrayList<>(Arrays.asList("View", "Edit", "Delete"));
        for(int i = 0; i < listOfOptions.size(); i++){
            Assert.assertEquals(listOfOptions.get(i).getAttribute("title"), expectedOptions.get(i));
        }
    }

    @Test(description = "Verify that “Title” column still displayed")
    public void test_2() {
        WebElement grid = Driver.getDriver().findElement(By.cssSelector("[title='Grid Settings']"));
        grid.click();
//        List<WebElement>checkBoxes = Driver.getDriver().findElements(By.cssSelector(".ui-sortable tr td:nth-of-type(3)"));
        List<WebElement> list = Driver.getDriver().findElements(By.cssSelector(".ui-sortable tr"));
        List<WebElement> checkBox = Driver.getDriver().findElements(By.cssSelector(".ui-sortable tr td:nth-of-type(3) input"));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().trim().equals("Title")) {
                continue;
            } else {
                if (checkBox.get(i).isSelected()) {
                    checkBox.get(i).click();
                }
            }
        }
        WebElement titleColumn = Driver.getDriver().findElement(By.cssSelector(".grid-header-cell__link"));
        Assert.assertTrue(titleColumn.isDisplayed());
    }


}
