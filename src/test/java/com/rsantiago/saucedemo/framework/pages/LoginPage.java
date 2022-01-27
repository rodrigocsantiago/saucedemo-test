package com.rsantiago.saucedemo.framework.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

/**
 * This class represents the SauceDemo login page
 * This is the page where users can log into application
 */
@Slf4j
public class LoginPage extends SeleniumBasePage<LoginPage> {

    private static final String SAUCEDEMO_URL = "https://www.saucedemo.com/";   // SauceDemo application URL
    private static final String DEFAULT_PWD = "secret_sauce";   // The default password used for all users

    // -- Page elements --

    @FindBy(css = "div.login_wrapper")
    private WebElement loginArea;

    @FindBy(id = "user-name")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "login-button")
    private WebElement login;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    // -- Page actions --

    public LoginPage loginAsLockedOutUser() {
        loginAs("locked_out_user", DEFAULT_PWD);
        return new LoginPage(driver);
    }

    public ProductsPage loginAsProblemUser() {
        loginAs("problem_user", DEFAULT_PWD);
        return new ProductsPage(driver);
    }

    public ProductsPage loginAsPerformanceGlitchUser() {
        loginAs("performance_glitch_user", DEFAULT_PWD);
        return new ProductsPage(driver);
    }

    public ProductsPage loginAsStandardUser() {
        loginAs("standard_user", DEFAULT_PWD);
        return new ProductsPage(driver);
    }

    public void loginAs(String userName, String password) {
        // Enter username and password
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);

        // Press login button
        this.login.click();
    }

    public boolean isErrorMessageDisplayed() {
        wait.until(ExpectedConditions.elementToBeClickable(errorMessage));
        return isElementAvailable(errorMessage);
    }

    public String getErrorMessage() {
        return isErrorMessageDisplayed() ? errorMessage.getText() : "";
    }

    @Override
    protected void load() {
        log.info("Loading SauceDemo login page");
        driver.get(SAUCEDEMO_URL);
    }

    @Override
    protected void isLoaded() throws Error {
        log.info("Checking if SauceDemo login page is loaded");
        // This is the only place inside the page objects where assertions are made
        // This page is loaded when:
        // Title is 'Swag Labs'
        assertThat("Unexpected page title for SauceDemo login page", driver.getTitle(), containsStringIgnoringCase("Swag Labs"));
        // Login button is displayed
        assertThat("Login button not displayed", isElementAvailable(login), is(true));
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }
}
