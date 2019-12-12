package com.cbt.pages;

import com.cbt.utilities.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/*  According to page object model design we have to create corresponded page classes for each page of application. */
/*  login page = login page class */
/*  Every page class will store webelements and methods related to that page      */

public class LoginPage extends BasePage{

    public LoginPage(){
        /* This is mandatory if you want to use @FindBy annotation */
        /* this: means LoginPage class */
        /* Driver.getDriver() return webdriver object */
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /* This line will initialize web element   */
    @FindBy(id = "prependedInput")
    public WebElement userNameInput;

    @FindBy(id = "prependedInput2") /* Without @FindBy, web element will be null */
    public WebElement passwordInput;

    @FindBy(id = "_submit")
    public WebElement loginButton;

    @FindBy(css = "[class='alert alert-error']")
    public WebElement warningMessage;

    /**
     * Reusable login method
     * just call this method to login
     * provide username and password as parameters
     * @param userName
     * @param password
     */
    public void login(String userName, String password){
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(password, Keys.ENTER);
    }

}
