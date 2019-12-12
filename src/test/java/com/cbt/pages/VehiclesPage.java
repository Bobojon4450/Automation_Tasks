package com.cbt.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.BrowserUtils;

public class VehiclesPage extends BasePage{

    @FindBy(css = "[title='Create Car']")
    public WebElement createACarElement;

    public void clickToCreateCar(){
        BrowserUtils.waitForVisibility(createACarElement, 15);
        BrowserUtils.waitForClickablility(createACarElement, 15);
        createACarElement.click();
    }




}
