package com.cbt.pages;

import com.cbt.utils.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class VehiclesPage extends BasePage{

    @FindBy(css = "[title='Create Car']")
    public WebElement createACarElement;

    public void clickToCreateCar(){
        BrowserUtils.waitForVisibility(createACarElement, 15);
        BrowserUtils.waitForClickablility(createACarElement, 15);
        createACarElement.click();
    }




}
