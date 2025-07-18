package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverManager {
    // Static WebDriver instance shared across the framework
    private static WebDriver driver;

    // Method to initialize and return the WebDriver instance
    public static WebDriver getDriver() {
        if (driver == null) {
            // Get browser type from configuration properties
            String browser = LoadProperties.getProperty("browser");

            // Determine browser type and initialize the appropriate driver
            switch (browser.toLowerCase()) {
                case "chrome":
                    // Automatically setup ChromeDriver using WebDriverManager
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                    
                default:
                    // Throw error if unsupported browser is specified
                    throw new RuntimeException("Unsupported browser");
            }

            // Maximize browser window after launch
            driver.manage().window().maximize();

            // Set default implicit wait time
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    // Method to quit and nullify the WebDriver instance
    public static void quitDriver() {
        if (driver != null) {
            driver.quit(); // Close all browser windows and end session
            driver = null; // Reset driver reference
        }
    }
}
