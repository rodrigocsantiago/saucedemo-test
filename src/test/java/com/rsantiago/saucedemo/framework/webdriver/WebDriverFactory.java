package com.rsantiago.saucedemo.framework.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Slf4j
public class WebDriverFactory {

    private static final Map<DriverType, Supplier<WebDriver>> webDrivers = new HashMap<>();
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final Supplier<WebDriver> chromeDriver = () -> {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-maximized");
        return new ChromeDriver(chromeOptions);
    };

    private static final Supplier<WebDriver> edgeDriver = () -> {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    };

    private static final Supplier<WebDriver> firefoxDriver = () -> {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    };

    static {
        log.info("Initializing WebDriverFactory configurations");
        webDrivers.put(DriverType.CHROME, chromeDriver);
        webDrivers.put(DriverType.EDGE, edgeDriver);
        webDrivers.put(DriverType.FIREFOX, firefoxDriver);
    }

    public static void createDriver(DriverType driverType) {
        log.info("{} Creating WebDriver of type '{}'", Thread.currentThread().getName(), driverType.name());
        WebDriver webDriver = webDrivers.get(driverType).get();
        if (webDriver != null) {
            log.info("{} Saving WebDriver on ThreadLocal variable", Thread.currentThread().getName());
            driver.set(webDriver);
        } else {
            log.error("{} createDriver() invoked using an unrecognized driver type: '{}'", Thread.currentThread().getName(), driverType.name());
            throw new InvalidArgumentException(String.format("createDriver() invoked using an unrecognized driver type: '%s'\n", driverType.name()));
        }
    }

    public static WebDriver getDriver() {
        // if webdriver was not created before, throw an error
        if (driver.get() == null) {
            log.error("{} getDriver() invoked without calling createDriver() first", Thread.currentThread().getName());
            throw new IllegalStateException("Before calling getDriver() call createDriver() for a specific driver type");
        }
        return driver.get();
    }

    public static void quitDriver() {
        log.info("{} Closing WebDriver", Thread.currentThread().getName());
        if (driver.get() != null) {
            try {
                driver.get().quit();
                driver.remove();
            } catch (Exception ex) {
                log.error("{} Error closing WebDriver. {}", Thread.currentThread().getName(), ex);
            }
        }
    }
}
