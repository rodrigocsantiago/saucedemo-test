package com.rsantiago.saucedemo.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginSteps {
    @Given("I am at the SauceDemo login page")
    public void iAmAtTheSauceDemoLoginPage() {
        log.info("I am at the SauceDemo login page");
    }

    @When("I log into the application using a {string} user")
    public void iLogIntoTheApplicationUsingAStandardUser(String userType) {
        log.info("I log into the application using a {} user", userType);
    }

    @Then("I should see the products page")
    public void iShouldSeeTheProductsPage() {

    }

    @Then("I should see a message saying the user has been locked out")
    public void iShouldSeeAMessageSayingTheUserHasBeenLockedOut() {

    }
}
