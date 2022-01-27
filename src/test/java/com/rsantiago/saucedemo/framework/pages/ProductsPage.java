package com.rsantiago.saucedemo.framework.pages;

import lombok.extern.slf4j.Slf4j;
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
 * This is the SauceDemo Products page
 * This is the first page displayed after a successful login is performed
 */
@Slf4j
public class ProductsPage extends SeleniumBasePage<ProductsPage> {

    // -- Page elements --

    @FindBy(css = "span.title")
    private WebElement productsPageTitle;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logout;

    // -- Page actions --

    public LoginPage logout() {
        log.info("Logging out of Products page");
        menuButton.click();
        wait.until(ExpectedConditions.attributeContains(logout, "tabindex", "0"));
        wait.until(ExpectedConditions.elementToBeClickable(logout));
        logout.click();
        return new LoginPage(driver);
    }

    /**
     * Checks if the Products page is loaded of not
     * @return true if the page is loaded or false otherwise
     */
    public boolean isProductsPageLoaded() {
        // This page is loaded when:
        // Title is 'Swag Labs'
        // There is a bar with name 'Products' in it
        return driver.getTitle().contains("Swag Labs") && productsPageTitle.isDisplayed();
    }

    @Override
    protected void load() {}

    @Override
    protected void isLoaded() throws Error {
        log.info("Checking if SauceDemo Products page is loaded");
        // This is the only place inside the page objects where assertions are made
        // This page is loaded when:
        // Title is 'Swag Labs'
        assertThat("Unexpected page title for SauceDemo Products page", driver.getTitle(), containsStringIgnoringCase("Swag Labs"));
        // There is a bar with name 'Products' in it
        assertThat(productsPageTitle.isDisplayed(), is(true));
    }

    public ProductsPage(WebDriver driver) {
        super(driver);
    }
}
