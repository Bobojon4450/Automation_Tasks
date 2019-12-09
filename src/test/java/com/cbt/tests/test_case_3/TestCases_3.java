package com.cbt.tests.test_case_3;

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

public class TestCases_3 {

    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        BrowserUtils.maximaze(Driver.getDriver());
//        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(Driver.getDriver(), 35);
        /* waits for the entire dom's elements are loaded within given timeframe,
          continues if elements are loaded quicker then given time    */
        Driver.getDriver().findElement(By.id("prependedInput")).sendKeys(ConfigurationReader.getProperty("user_name"));
        Driver.getDriver().findElement(By.id("prependedInput2")).sendKeys(ConfigurationReader.getProperty("password"), Keys.ENTER);
        //String xpath = "//a[@class='unclickable' and @href = '#' ]/span[@class='title title-level-1' and contains(text(),'Activities')]";
        //WebElement activitiesElement = driver.findElement(By.xpath(xpath));
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

    @Test(description = "Verify that page subtitle 'Options' is displayed")
    public void test_1() {
        WebElement optionButton = Driver.getDriver().findElement(By.cssSelector("[class = 'btn btn-link dropdown-toggle']"));
        Assert.assertTrue(optionButton.isDisplayed());
        Assert.assertTrue(optionButton.isEnabled());
    }

    @Test(description = "Verify that page number is equals to 1 ")
    public void test_2() {
        WebElement pageCount = Driver.getDriver().findElement(By.cssSelector("[type = 'number']"));
        String value = pageCount.getAttribute("value");
        Assert.assertTrue(pageCount.isDisplayed());
        Assert.assertTrue(pageCount.isEnabled());
        Assert.assertEquals(value, "1");
    }

    @Test(description = "Verify that view per page number is equals to 25")
    public void test_3() {
        WebElement pageNumber = Driver.getDriver().findElement(By.cssSelector("[class = 'btn-group'] button"));
        Assert.assertEquals(pageNumber.getText(), "25");
    }

    @Test(description = "Verify that number of calendar events (rows in the table) is equals to number of records")
    public void test_4() {
        WebElement numberOfEvents = Driver.getDriver().findElement(By.cssSelector("[class='dib']:nth-of-type(3)"));
        String[] arr = numberOfEvents.getText().split(" ");
        int eventCount = Integer.parseInt(arr[2]);
        List<WebElement> eventList = Driver.getDriver().findElements(By.cssSelector(".grid tbody tr"));
        Assert.assertEquals(eventCount, eventList.size());
    }

    @Test(description = "Verify that all calendar events were selected")
    public void test_5() {
        WebElement checkBox = Driver.getDriver().findElement(By.cssSelector(".btn.btn-default input"));
        checkBox.click();
        List<WebElement> eventList = Driver.getDriver().findElements(By.cssSelector(".row-selected"));
        for (WebElement each : eventList) {
            if (each.isEnabled() && each.isSelected()) {
                Assert.assertTrue(each.isSelected());
            }
        }
    }

    @Test(description = "Verify that following data is displayed:")
    public void test_6() {
        String expectedDescription = "This is a a weekly testers meeting";//==
        String expectedTitle = "Testers Meeting";//1
        String organizer = "Stephan Haley";//5
        String stDate = "Nov 27, 2019, 9:30 PM";//2
        String eDate = "Nov 27, 2019, 10:30 PM";//3
        String allDayEvent = "No";//4
        String expectedGuest = "Tom Smith";//==
        String expectedReccurrence = "Weekly every 1 week on Wednesday";
        String expectedHangOutCall = "No";
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 35);
        Actions action = new Actions(Driver.getDriver());
        List<String> expectedData = new ArrayList<>(Arrays.asList(expectedTitle, stDate, eDate, allDayEvent, organizer));
        List<WebElement> testersList = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[2]"));
        for (int j = 0; j < testersList.size(); j++) {
            if (testersList.get(j).getText().equals(expectedTitle)) {
                wait.until(ExpectedConditions.elementToBeClickable(testersList.get(j)));
                wait.until(ExpectedConditions.visibilityOf(testersList.get(j)));
                BrowserUtils.wait(1);
                action.moveToElement(testersList.get(j)).doubleClick(testersList.get(j));
//                testersList.get(j).click();
                BrowserUtils.wait(1);
                WebElement tomSmith = Driver.getDriver().findElement(By.linkText("Tom Smith"));
                wait.until(ExpectedConditions.visibilityOf(tomSmith));
                wait.until(ExpectedConditions.elementToBeClickable(tomSmith));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Tom Smith")));
                for (int i = 1; i <= expectedData.size(); i++) {
                    BrowserUtils.wait(2);
                    Assert.assertEquals(expectedData.get(i - 1), extractText(i));
                }
            }
            WebElement description = Driver.getDriver().findElement(By.cssSelector(".control-label.html-property p"));
            WebElement guest = Driver.getDriver().findElement(By.xpath("//a[@href='/user/view/18']"));
            WebElement reccurence = Driver.getDriver().findElement(By.xpath("(//div[@class ='control-label'])[7]"));
            WebElement hangOut = Driver.getDriver().findElement(By.xpath("(//div[@class ='control-label'])[8]"));
            Assert.assertEquals(expectedDescription, description.getText());
            Assert.assertEquals(expectedGuest, guest.getText());
            Assert.assertEquals(expectedReccurrence, reccurence.getText());
            break;
        }
    }

    @Test (description = "Verify that the data for testers meeting is displayed correct")
    public void test_7(){
        //find testers meeting element
        WebElement testersMeeting = Driver.getDriver().findElement(By.cssSelector(".grid-row.row-click-action:nth-of-type(13)"));
        testersMeeting.click();
        WebElement spinner = Driver.getDriver().findElement(By.cssSelector("div[class='loader-mask shown']"));
        wait.until(ExpectedConditions.invisibilityOf(spinner));
        List<String> meetingInfo = new ArrayList<>(Arrays.asList(
                "Testers Meeting",
                "This is a a weekly testers meeting",
                "Nov 27, 2019, 9:30 PM",
                "Nov 27, 2019, 10:30 PM",
                "No",
                "Stephan Haley",
                "Tom Smith",
                "Weekly every 1 week on Wednesday",
                "No"));
        for (int i = 0; i < meetingInfo.size(); i++) {
            String actualMeetingInfo = Driver.getDriver().findElements(By.cssSelector("[class='responsive-block']>div>div>div")).get(i).getText();
            if (i == 6) {
                //for Guests - required should be removed
                Assert.assertEquals(actualMeetingInfo.replace(" - Required", ""), meetingInfo.get(i));
            } else {
                Assert.assertEquals(actualMeetingInfo, meetingInfo.get(i));
            }
        }
    }









    public static String extractText(int x) {
        String xpath = Driver.getDriver().findElement(By.xpath("(//div[@class = 'control-label'])[" + x + "]")).getText();
        return xpath;
    }


}


/*
        List<WebElement> testersList = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[2]"));
        List<WebElement> OrginizerNameList = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[3]"));
        List<WebElement> startDate = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[4]"));
        List<WebElement> endDate = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[5]"));
        List<WebElement> listOfRows = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]"));

        for(int i = 0; i < listOfRows.size(); i++){
            String[] arr = new String[listOfRows.size()];
            List<String[]> listOfData = new ArrayList<>();
            arr[i] = listOfRows.get(i).getText();
        }

       for (int i = 0; i < testersList.size(); i++) {
            if (testersList.get(i).getText().equals("Testers Meeting")) {
                Assert.assertEquals(expectedTitle, testersList.get(i).getText());
                if (OrginizerNameList.get(i).getText().equals("Stephan Haley")) {
                    Assert.assertEquals("Stephan Haley", OrginizerNameList.get(i).getText());
                    if (startDate.get(i).getText().equals("Nov 27, 2019, 9:30 PM")) {
                        System.out.println(startDate.get(i).getText());
                        Assert.assertEquals(startDate.get(i).getText(), "Nov 27, 2019, 9:30 PM");
                        if (endDate.get(i).getText().equals("Nov 27, 2019, 10:30 PM")) {
                            Assert.assertEquals(endDate.get(i).getText(), "Nov 27, 2019, 10:30 PM");
                            endDate.get(i).click();
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//div[@class = 'control-label' and text()= 'No']")).getText(), "No");
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("(//a[@class=' '])[2]")).getText().trim(), "Tom Smith");
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//div[text()='Weekly every 1 week on Wednesday']"))
                                    .getText(), "Weekly every 1 week on Wednesday");
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("(//div[@class='responsive-block'])[3]//div[@class='control-label']")).getText().trim(),
                                    "No");
                            break;
                        }
                    }
                }
            } else {
                testersList.remove(i);
            }
        }
*/

/*    public static boolean assertData(String title, String organizer, String stDate, String eDate, String event, String guest, String recurre, String hangOut) {
        List<WebElement> testersList = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[2]"));
        List<WebElement> OrginizerNameList = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[3]"));
        List<WebElement> startDate = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[4]"));
        List<WebElement> endDate = Driver.getDriver().findElements(By.xpath("//tr[@class='grid-row row-click-action' ]//td[5]"));
       boolean sentinel = false;
        for (int i = 0; i < testersList.size(); i++) {
            if (testersList.get(i).getText().equals("Testers Meeting")) {
                Assert.assertEquals("Testers Meeting", testersList.get(i).getText());
                if (OrginizerNameList.get(i).getText().equals("Stephan Haley")) {
                    Assert.assertEquals("Stephan Haley", OrginizerNameList.get(i).getText());
                    if (startDate.get(i).getText().equals("Nov 27, 2019, 9:30 PM")) {
                        System.out.println(startDate.get(i).getText());
                        Assert.assertEquals(startDate.get(i).getText(), "Nov 27, 2019, 9:30 PM");
                        if (endDate.get(i).getText().equals("Nov 27, 2019, 10:30 PM")) {
                            Assert.assertEquals(endDate.get(i).getText(), "Nov 27, 2019, 10:30 PM");
                            endDate.get(i).click();
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//div[@class = 'control-label' and text()= 'No']")).getText(), "No");
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("(//a[@class=' '])[2]")).getText().trim(), "Tom Smith");
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//div[text()='Weekly every 1 week on Wednesday']"))
                                    .getText(), "Weekly every 1 week on Wednesday");
                            Assert.assertEquals(Driver.getDriver().findElement(By.xpath("(//div[@class='responsive-block'])[3]//div[@class='control-label']")).getText().trim(),
                                    "No");
                            sentinel = true;
                            return sentinel;
                        }
                    }
                }
            } else {
                testersList.remove(i);
                OrginizerNameList.remove(i);
                startDate.remove(i);
            }
        }
  */