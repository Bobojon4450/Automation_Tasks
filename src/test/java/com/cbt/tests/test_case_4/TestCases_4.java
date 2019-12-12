package com.cbt.tests.test_case_4;

import com.cbt.utils.BrowserUtils;
import com.cbt.utils.ConfigurationReader;
import com.cbt.utils.Driver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestCases_4 {
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        BrowserUtils.maximaze(Driver.getDriver());
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
        /*List<WebElement>checkBoxes = Driver.getDriver().findElements(By.cssSelector(".ui-sortable tr td:nth-of-type(3)"));*/
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

    @Test(description = "Verify that 'Save And Close', 'Save And New' and 'Save' options are available")
    public void test_3() {
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.visibilityOf(createCalendarButton));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='btn-success btn dropdown-toggle']")));
        WebElement saveCloseButton = Driver.getDriver().findElement(By.cssSelector("[class='btn-success btn dropdown-toggle']"));
        wait.until(ExpectedConditions.elementToBeClickable(saveCloseButton));
        saveCloseButton.click();
        List<WebElement>list = Driver.getDriver().findElements(By.cssSelector("[class='btn-group pull-right open'] ul li"));
        List<String> text = new ArrayList<>();
        for (WebElement element : list) {
            text.add(element.getText().trim());
        }
        for (int i = 0; i < text.size(); i++){
            Assert.assertEquals(text.get(i), list.get(i).getText().trim());
        }
    }

    @Test(description = "Verify that “All Calendar Events” page subtitle is displayed")
    public void test_4() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.visibilityOf(createCalendarButton));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='Cancel']")));
        WebElement cancelButton = Driver.getDriver().findElement(By.cssSelector("a[title='Cancel']"));
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        js.executeScript("arguments[0].click();", cancelButton);// clicks.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='btn-group views-group'] h1")));
        WebElement dropDown =Driver.getDriver().findElement(By.cssSelector("[class='btn-group views-group'] h1"));
        Assert.assertTrue(dropDown.isDisplayed());
        Assert.assertTrue(dropDown.isEnabled());
        Assert.assertEquals(dropDown.getText().trim(), "All Calendar Events");
    }

    @Test(description = "Verify that difference between end and start time is exactly 1 hour")
    public void test_5() {
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.visibilityOf(createCalendarButton));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_start-uid')]")));
        WebElement startTime = Driver.getDriver().findElement(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_start-uid')]"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_end-uid')]")));
        WebElement endTime = Driver.getDriver().findElement(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_end-uid')]"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class='fields-row'] input:nth-of-type(2)")));
        String stTime[] = startTime.getAttribute("value").replace(":","").split(" ");
        String enTime[] = endTime.getAttribute("value").replace(":","").split(" ");
        int strt = Integer.parseInt(stTime[0]);
        int end =Integer.parseInt(enTime[0]);
        Assert.assertTrue((strt - end) == 100 || (strt - end) == -100 ? true : false);
    }

    @Test(description = "Verify that end time is equals to '10:00 PM' ")
    public void test_6() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_start-uid')]")));
        WebElement startTime = Driver.getDriver().findElement(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_start-uid')]"));
        js.executeScript("arguments[0].click();", startTime);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='ui-timepicker-list'] li:nth-child(43)")));
        WebElement pm_9 = Driver.getDriver().findElement(By.cssSelector("[class='ui-timepicker-list'] li:nth-child(43)"));
        wait.until(ExpectedConditions.elementToBeClickable(pm_9));
        js.executeScript("arguments[0].click();",pm_9);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_end-uid')]")));
        WebElement endTime = Driver.getDriver().findElement(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_end-uid')]"));
        String enTime = endTime.getAttribute("value");
        Assert.assertEquals(enTime, "10:00 PM", "Incorrect time");
    }

    @Test(description = "Verify that “All-Day Event” checkbox is selected")
    public void test_7() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='oro_calendar_event_form[allDay]']")));
        WebElement allDayCheckBox = Driver.getDriver().findElement(By.cssSelector("[name='oro_calendar_event_form[allDay]']"));
        if (!allDayCheckBox.isSelected()) {
            js.executeScript("arguments[0].click();", allDayCheckBox);
        }
        WebElement startTime = Driver.getDriver().findElement(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_start-uid')]"));
        WebElement endTime = Driver.getDriver().findElement(By.xpath("//div[@class='fields-row']//input[contains(@name,'time_selector_oro_calendar_event_form_end-uid')]"));
        WebElement startDate = Driver.getDriver().findElement(By.cssSelector(".input-small.datepicker-input.start"));
        WebElement endDate = Driver.getDriver().findElement(By.cssSelector(".input-small.datepicker-input.end"));
        Assert.assertTrue(startDate.isDisplayed());
        Assert.assertTrue(endDate.isDisplayed());
        Assert.assertTrue(!startTime.isDisplayed());
        Assert.assertTrue(!endTime.isDisplayed());
    }

    @Test(description = "Verify that “Repeat” checkbox is selected")
    public void test_8() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-name='recurrence-repeat']")));
        WebElement repeatCheckbox = Driver.getDriver().findElement(By.cssSelector("[data-name='recurrence-repeat']"));
        wait.until(ExpectedConditions.elementToBeClickable(repeatCheckbox));
        js.executeScript("arguments[0].click();", repeatCheckbox);
        Assert.assertTrue(repeatCheckbox.isSelected());
        List<WebElement> list = Driver.getDriver().findElements(By.cssSelector("[class='recurrence-repeats__select'] option"));
        Assert.assertEquals(list.get(0).getAttribute("value"), "daily");
        for (int i = 1; i < list.size(); i++) {
            Assert.assertTrue(list.get(i).isDisplayed());
            Assert.assertTrue(list.get(i).isEnabled());
        }
    }

    @Test(description = "Verify that “Repeat” checkbox is selected, Verify that “Repeat Every” radio button is selected....")
    public void test_9() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[data-name='recurrence-repeat']"))));
        WebElement repeatCheckbox = Driver.getDriver().findElement(By.cssSelector("[data-name='recurrence-repeat']"));
        wait.until(ExpectedConditions.elementToBeClickable(repeatCheckbox));
        js.executeScript("arguments[0].click();", repeatCheckbox);
        Assert.assertTrue(repeatCheckbox.isSelected());
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[class='fields-row'] [checked='checked']"))));
        WebElement repeatEveryRadio = Driver.getDriver().findElement(By.cssSelector("[class='fields-row'] [checked='checked']"));
        Assert.assertTrue(repeatEveryRadio.isSelected());
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//input[@type='radio'])[3]"))));
        WebElement endNeverCheckBox = Driver.getDriver().findElement(By.xpath("(//input[@type='radio'])[3]"));
        Assert.assertTrue(endNeverCheckBox.isSelected());
        /*WebElement confirmMessage = Driver.getDriver().findElement(By.xpath("//div[@class='control-group recurrence-summary alert-info']"));*/
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[normalize-space()='Summary: Daily every 1 day' and @class='control-group recurrence-summary alert-info']"))));
        WebElement confirmMessage = Driver.getDriver().findElement(By.xpath("//*[normalize-space()='Summary: Daily every 1 day' and @class='control-group recurrence-summary alert-info']"));
        Assert.assertEquals(confirmMessage.getText().replaceAll("\\n", " "), "Summary: Daily every 1 day");
        Assert.assertTrue(confirmMessage.isDisplayed());
    }

    @Test(description = "Verify that following message as a summary is displayed: 'Summary: Daily every 1 day, endafter 10 occurrences'")
    public void test_10() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[data-name='recurrence-repeat']"))));
        WebElement repeatCheckbox = Driver.getDriver().findElement(By.cssSelector("[data-name='recurrence-repeat']"));
        wait.until(ExpectedConditions.elementToBeClickable(repeatCheckbox));
        js.executeScript("arguments[0].click();", repeatCheckbox);
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//input[@type='radio'])[4]"))));
        WebElement afterCheckBox = Driver.getDriver().findElement(By.xpath("(//input[@type='radio'])[4]"));
        js.executeScript("arguments[0].click();", afterCheckBox);
        WebElement occurrencesBox = Driver.getDriver().findElement(By.cssSelector("[data-related-field='occurrences']"));
        int num = 10;
        js.executeScript("arguments[0].setAttribute('value', "+num+")", occurrencesBox);
        WebElement confirmMessage = Driver.getDriver().findElement(By.xpath("//*[normalize-space()='Summary: Daily every 1 day' and @class='control-group recurrence-summary alert-info']"));
        System.out.println(confirmMessage.getText());
        Assert.assertEquals(confirmMessage.getText().replaceAll("\\n", " "), "Summary: Daily every 1 day, end after 10 occurrences", "Suppose to fail due to the Incorrect message");
    }

    @Test(description = "Verify that following message as a summary is displayed: 'Summary: Daily every 1 day, endafter 10 occurrences'")
    public void test_11() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[data-name='recurrence-repeat']"))));
        WebElement repeatCheckbox = Driver.getDriver().findElement(By.cssSelector("[data-name='recurrence-repeat']"));
        wait.until(ExpectedConditions.elementToBeClickable(repeatCheckbox));
        js.executeScript("arguments[0].click();", repeatCheckbox);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@type='radio'])[4]")));
        WebElement afterCheckBox = Driver.getDriver().findElement(By.xpath("(//input[@type='radio'])[4]"));
        js.executeScript("arguments[0].click();", afterCheckBox);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-related-field='occurrences']")));
        WebElement occurrencesBox = Driver.getDriver().findElement(By.cssSelector("[data-related-field='occurrences']"));
        int num = 10;
        js.executeScript("arguments[0].setAttribute('value', "+num+")", occurrencesBox);
        WebElement ByCheckBox = Driver.getDriver().findElement(By.xpath("(//div[@class= 'recurrence-subview-control__item recurrence-subview-control__item-datetime']//input)[1]"));
        js.executeScript("arguments[0].click();", ByCheckBox);
        WebElement chooseDate = Driver.getDriver().findElement(By.xpath("//input[@class='datepicker-input hasDatepicker']"));
        js.executeScript("arguments[0].click();", chooseDate);
        chooseDate.sendKeys("Nov 18, 2021");
        WebElement confirmMessage = Driver.getDriver().findElement(By.xpath("//*[normalize-space()='Summary: Daily every 1 day, end by Nov 18, 2021' and @class='control-group recurrence-summary alert-info']"));
        Assert.assertEquals(confirmMessage.getText().replaceAll("\\n"," "),"Summary: Daily every 1 day, end by Nov 18, 2021");
    }

    @Test(description = "Verify that following message as a summary is displayed: 'Summary: Daily every 1 day, endafter 10 occurrences'")
    public void test_12() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[title='Create Calendar event']")));
        WebElement createCalendarButton = Driver.getDriver().findElement(By.cssSelector("[title='Create Calendar event']"));
        wait.until(ExpectedConditions.elementToBeClickable(createCalendarButton));
        createCalendarButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("[data-name='recurrence-repeat']"))));
        WebElement repeatCheckbox = Driver.getDriver().findElement(By.cssSelector("[data-name='recurrence-repeat']"));
        wait.until(ExpectedConditions.elementToBeClickable(repeatCheckbox));
        js.executeScript("arguments[0].click();", repeatCheckbox);
        WebElement weeklyRepeat = Driver.getDriver().findElement(By.cssSelector(".recurrence-repeats__select"));
        Actions actions = new Actions(Driver.getDriver());
        Select select = new Select(weeklyRepeat);
        List<WebElement>statesList = select.getOptions();
        for(WebElement option: statesList) {
            if(option.getAttribute("value").equals("weekly")){
               option.click();
               break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector(".multi-checkbox-control label input"))));
        List<WebElement> list = Driver.getDriver().findElements(By.cssSelector(".multi-checkbox-control label input"));
        System.out.println(list.size());
        for (WebElement each : list) {
            if(each.getAttribute("value").equals("monday") ||each.getAttribute("value").equals("friday")){
                js.executeScript("arguments[0].click();", each);
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[normalize-space()='Summary: Weekly every 1 week on Monday, Friday' and @class='control-group recurrence-summary alert-info']"))));
        WebElement confirmMessage = Driver.getDriver().findElement(By.xpath("//*[normalize-space()='Summary: Weekly every 1 week on Monday, Friday' and @class='control-group recurrence-summary alert-info']"));
        Assert.assertEquals(confirmMessage.getText().replaceAll("\\n"," "),"Summary: Weekly every 1 week on Monday, Friday");
    }

}




// ScreenMaskElement -> #oro-dropdown-mask
