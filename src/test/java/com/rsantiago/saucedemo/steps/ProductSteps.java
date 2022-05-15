package com.rsantiago.saucedemo.steps;

import com.rsantiago.saucedemo.framework.TestContext;
import com.rsantiago.saucedemo.pages.ProductDetailsPage;
import com.rsantiago.saucedemo.pages.ProductsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductSteps {
    private final TestContext testContext;

    @And("A product with name {string} and price {string} exists")
    public void aProductWithNameSauceLabsBackpackAndPriceExists(String productName, String productPrice) {
        ProductsPage productsPage = testContext.getProductsPage();

        assertThat(String.format("Expected product name '%s' not found", productName),
                productsPage.isProductDisplayed(productName), is(true));
        assertThat(String.format("Expected product price '%s' for product '%s' not found", productPrice, productName),
                productsPage.isProductNameAndPriceDisplayed(productName, productPrice), is(true));
    }

    @When("I click on {string} product")
    public void iClickOnSauceLabsBackpackProduct(String productName) {
        ProductsPage productsPage = testContext.getProductsPage();
        ProductDetailsPage productDetailsPage = productsPage.clickProduct(productName);
        testContext.setProductDetailsPage(productDetailsPage);
    }

    @Then("I should see the product details with name {string} and price {string}")
    public void iShouldSeeTheProductDetailsWithNameAndPriceMatchingTheProductPage(String productName, String productPrice) {
        ProductDetailsPage productDetailsPage = testContext.getProductDetailsPage();
        assertThat(String.format("Expected product name '%s' not found", productName),
                productDetailsPage.getProductName(), is(productName));
        //  The product price displayed on Product Details screen has a '$' before its value
        assertThat(String.format("Expected product price '%s' for product '%s' not found", productPrice, productName),
                productDetailsPage.getProductPrice(), containsString("$" + productPrice));
    }

    public ProductSteps(TestContext testContext) {
        this.testContext = testContext;
    }
}
