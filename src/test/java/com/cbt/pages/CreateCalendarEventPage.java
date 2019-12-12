package com.cbt.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateCalendarEventPage extends BasePage {
    /* Here the constructor of the BasePage is inherited along with methods. */

    @FindBy(css = "[class='select2-chosen']")
    public WebElement owner;



}
