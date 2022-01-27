package com.rsantiago.saucedemo.framework.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class SeleniumBasePage<T extends SeleniumBasePage<T>> {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    /**
     * If the page is not loaded yet, load the page then return the page
     * @return the page loaded
     */
    @SuppressWarnings("unchecked")
    public T get() {
        try {
            isLoaded();
            return (T) this;
        } catch (Error e) {
            load();
        }
        isLoaded();
        return (T) this;
    }

    protected abstract void load();

    protected abstract void isLoaded() throws Error;

    public boolean isElementAvailable(WebElement element) {
        try {
            return element.isDisplayed() && element.isEnabled();
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public SeleniumBasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
}
