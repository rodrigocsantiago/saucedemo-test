package com.rsantiago.saucedemo.steps;

import com.rsantiago.saucedemo.framework.webdriver.DriverType;
import com.rsantiago.saucedemo.framework.webdriver.WebDriverFactory;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {

    @BeforeAll
    public static void beforeAll() {
        log.info("Running beforeAll");
        WebDriverFactory.createDriver(DriverType.CHROME);
    }

    @AfterAll
    public static void afterAll() {
        log.info("Running afterAll");
        WebDriverFactory.quitDriver();
    }
}
