package com.rsantiago.saucedemo.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:/features",
                 glue = "com.rsantiago.saucedemo.steps",
                 plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json"})
public class RunTest {
}
