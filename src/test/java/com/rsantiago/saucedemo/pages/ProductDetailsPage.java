package com.rsantiago.saucedemo.pages;

import com.rsantiago.saucedemo.framework.pages.SeleniumBasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

@Slf4j
public class ProductDetailsPage extends SeleniumBasePage<ProductDetailsPage> {

    // -- Page elements --

    @FindBy(id = "back-to-products")
    private WebElement backToProducts;

    @FindBy(css = "div.inventory_details_name")
    private WebElement productName;

    @FindBy(css = "div.inventory_details_price")
    private WebElement productPrice;

    // -- Page actions --


    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    @Override
    protected void load() {}

    @Override
    protected void isLoaded() throws Error {
        log.info("Checking if SauceDemo Product Details page is loaded");
        // This is the only place inside the page objects where assertions are made
        // This page is loaded when:
        // Title is 'Swag Labs'
        assertThat("Unexpected page title for SauceDemo Product Details page", driver.getTitle(), containsStringIgnoringCase("Swag Labs"));
        // There is a button titled 'BACK TO PRODUCTS'
        assertThat(isElementAvailable(backToProducts), is(true));
    }

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }
}
