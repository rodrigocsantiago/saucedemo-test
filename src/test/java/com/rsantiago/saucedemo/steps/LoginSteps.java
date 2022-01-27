package com.rsantiago.saucedemo.steps;

import com.rsantiago.saucedemo.framework.pages.LoginPage;
import com.rsantiago.saucedemo.framework.pages.ProductsPage;
import com.rsantiago.saucedemo.framework.webdriver.WebDriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
public class LoginSteps {
    private final WebDriver driver = WebDriverFactory.getDriver();

    LoginPage loginPage;
    ProductsPage productsPage;

    @Given("I am at the SauceDemo login page")
    public void iAmAtTheSauceDemoLoginPage() {
        driver.manage().deleteAllCookies();
        loginPage = new LoginPage(driver).get();
    }

    @When("I log into the application using a {string} user")
    public void iLogIntoTheApplicationUsingAStandardUser(String userType) {
        if (userType.equalsIgnoreCase("standard")) {
            productsPage = loginPage.loginAsStandardUser().get();
        } else if (userType.equalsIgnoreCase("locked out")) {
            // Locked out user do not go to Products Page. It continues on Login Page
            loginPage = loginPage.loginAsLockedOutUser().get();
        } else if (userType.equalsIgnoreCase("problem")) {
            productsPage = loginPage.loginAsProblemUser().get();
        } else if (userType.equalsIgnoreCase("performance glitch")) {
            productsPage = loginPage.loginAsPerformanceGlitchUser().get();
        }
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {
        assertThat(productsPage, is(notNullValue()));
        assertThat(productsPage.isProductsPageLoaded(), is(true));
    }

    @Then("I should see a message saying the user has been locked out")
    public void iShouldSeeAMessageSayingTheUserHasBeenLockedOut() {
        assertThat(loginPage, is(notNullValue()));
        assertThat(loginPage.getErrorMessage(), containsStringIgnoringCase("this user has been locked out"));
    }
}
