package com.rsantiago.saucedemo.framework;

import com.rsantiago.saucedemo.pages.LoginPage;
import com.rsantiago.saucedemo.pages.ProductDetailsPage;
import com.rsantiago.saucedemo.pages.ProductsPage;
import lombok.Data;

@Data
public class TestContext {
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private ProductDetailsPage productDetailsPage;
}
