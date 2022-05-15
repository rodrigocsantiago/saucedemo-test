package com.rsantiago.saucedemo.steps;

import com.rsantiago.saucedemo.framework.TestContext;
import com.rsantiago.saucedemo.pages.LoginPage;
import com.rsantiago.saucedemo.pages.ProductsPage;
import com.rsantiago.saucedemo.framework.webdriver.WebDriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class LoginSteps {

    private final WebDriver driver = WebDriverFactory.getDriver();
    private final TestContext testContext;

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @Given("I am at the SauceDemo login page")
    public void iAmAtTheSauceDemoLoginPage() {
        driver.manage().deleteAllCookies();
        testContext.setLoginPage(new LoginPage(driver).get());
    }

    @Given("I am logged into SauceDemo as a {string} user")
    public void iAmLoggedIntoSauceDemoAsAStandardUser(String userType) {
        // This is the same step as "When I log into the application using a {string} user"
        iLogIntoTheApplicationUsingAStandardUser(userType);
    }

    @When("I log into the application using a {string} user")
    public void iLogIntoTheApplicationUsingAStandardUser(String userType) {
        // If Login page is not already opened, open it
        if (testContext.getLoginPage() == null) {
            iAmAtTheSauceDemoLoginPage();
        }

        loginPage = testContext.getLoginPage();

        if (userType.equalsIgnoreCase("standard") ||
                userType.equalsIgnoreCase("problem") ||
                userType.equalsIgnoreCase("performance glitch")) {
            productsPage = loginPage.loginAsStandardUser().get();
            testContext.setProductsPage(productsPage);
        } else if (userType.equalsIgnoreCase("locked out")) {
            // Locked out user do not go to Products Page. It continues on Login Page
            loginPage = loginPage.loginAsLockedOutUser().get();
            testContext.setLoginPage(loginPage);
        }
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        productsPage = testContext.getProductsPage();
        assertThat(productsPage, is(notNullValue()));
        assertThat(productsPage.isProductsPageLoaded(), is(true));
    }

    @Then("I should see a message saying the user has been locked out")
    public void iShouldSeeAMessageSayingTheUserHasBeenLockedOut() {
        loginPage = testContext.getLoginPage();
        assertThat(loginPage, is(notNullValue()));
        assertThat(loginPage.getErrorMessage(), containsStringIgnoringCase("this user has been locked out"));
    }

    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }
}
