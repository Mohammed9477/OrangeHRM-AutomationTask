package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ElementHelper;

public class LoginPage {
    private WebDriver driver;

    // Locators for the login page elements
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");

    // Constructor that receives the WebDriver instance
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to perform login using provided username and password
    public void login(String username, String password) {
        ElementHelper.sendText(username, driver, usernameField); // Enter username
        ElementHelper.sendText(password, driver, passwordField); // Enter password
        ElementHelper.click(driver, loginButton);                // Click on login button
    }
}
