package com.nikhilawasthi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Login tests for the public practice site https://www.saucedemo.com
 * Each test starts a fresh Chrome browser and closes it afterwards.
 */
class LoginTest {

    private static final String URL = "https://www.saucedemo.com/";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();            // Selenium Manager downloads the matching driver
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    private String errorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']"))).getText();
    }

    @Test
    @DisplayName("TC01 Valid login opens the Products page")
    void validLogin() {
        login("standard_user", "secret_sauce");
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        assertEquals("Products", driver.findElement(By.className("title")).getText());
    }

    @Test
    @DisplayName("TC02 Wrong password shows an error")
    void wrongPassword() {
        login("standard_user", "wrong_password");
        assertTrue(errorMessage().contains("Username and password do not match"));
    }

    @Test
    @DisplayName("TC03 Locked out user cannot log in")
    void lockedOutUser() {
        login("locked_out_user", "secret_sauce");
        assertTrue(errorMessage().contains("this user has been locked out"));
    }

    @Test
    @DisplayName("TC04 Empty username shows 'Username is required'")
    void emptyUsername() {
        login("", "secret_sauce");
        assertTrue(errorMessage().contains("Username is required"));
    }

    @Test
    @DisplayName("TC05 Empty password shows 'Password is required'")
    void emptyPassword() {
        login("standard_user", "");
        assertTrue(errorMessage().contains("Password is required"));
    }

    @Test
    @DisplayName("TC06 Logout returns to the login page")
    void logout() {
        login("standard_user", "secret_sauce");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        assertEquals(URL, driver.getCurrentUrl());
    }
}
