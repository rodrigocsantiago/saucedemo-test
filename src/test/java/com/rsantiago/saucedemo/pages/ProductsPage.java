package com.rsantiago.saucedemo.pages;

import com.rsantiago.saucedemo.framework.pages.SeleniumBasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

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

    @FindBy(className = "inventory_item_description")
    private List<WebElement> inventoryItems;

    // Locator relative to inventoryItems above - single item name
    private final By itemName = By.className("inventory_item_name");

    // Locator relative to inventoryItems above - single item description
    private final By itemDescription = By.className("inventory_item_desc");

    // Locator relative to inventoryItems above - single item price
    private final By itemPrice = By.className("inventory_item_price");

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
     * Checks if the given product name and price is displayed on Products page
     * @param productName The product name to be searched for
     * @param productPrice The product price to be searched for
     * @return true if the product name and price combination is found or false otherwise
     */
    public boolean isProductNameAndPriceDisplayed(String productName, String productPrice) {
        return inventoryItems.stream()
                .filter(item -> item.findElement(itemName).getText().contains(productName))
                .anyMatch(item -> item.findElement(itemPrice).getText().contains(productPrice));
    }

    /**
     * Returns true if the product name provided is displayed on Products page or false otherwise
     * @param productName The product name to be searched for
     * @return true if product name is displayed on Products page or false otherwise
     */
    public boolean isProductDisplayed(String productName) {
        return inventoryItems.stream()
                .anyMatch(item -> item.findElement(itemName).getText().contains(productName));
    }

    /**
     * Click on a product link displayed on Products page. NOTE: this is NOT the 'Add to Cart' option
     */
    public ProductDetailsPage clickProduct(String productName) {
        inventoryItems.stream()
                .filter(item -> item.findElement(itemName).getText().contains(productName))
                .findAny()
                .ifPresent(item -> item.findElement(itemName).click());
                //.ifPresent(WebElement::click);
        return new ProductDetailsPage(driver);
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

    public void setInventoryItems(List<WebElement> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
