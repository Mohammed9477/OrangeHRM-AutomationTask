package utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ElementHelper {
    // Default waiting time for explicit waits
    static int waitingTime = 5;

    // Waits until the element is clickable and performs a click
    public static void click(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    // Overloaded method to click a WebElement directly (with validations)
    public static void click(WebElement webElement) {
        if (webElement != null && webElement.isDisplayed() && webElement.isEnabled()) {
            webElement.click();
        } else {
            throw new IllegalArgumentException("WebElement is not clickable");
        }
    }

    // Sends text to an input field after waiting for its visibility
    public static void sendText(String text, WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(text);
    }

    // Finds a WebElement by its visible text using XPath
    public static WebElement findElementByText(String text, WebDriver driver) {
        return driver.findElement(By.xpath("//*[text()='" + text + "']"));
    }

    // Waits for visibility of a locator and returns the WebElement
    public static WebElement findElementBy(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Selects an option from a <select> dropdown by visible text
    public static void selectTextFromDropDownByText(String text, WebDriver driver, By locator) {
        Select select = new Select(findElementBy(driver, locator));
        select.selectByVisibleText(text);
    }

    // Returns the current URL of the browser
    public static String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    // Checks if an element is visible on the page (handles exceptions)
    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            WebElement element = findElementBy(driver, locator);
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    // Handles typing into an auto-suggest field and selecting the first suggestion
    // Returns the selected suggestion text
    public static String selectFromAutoSuggest(WebDriver driver, By inputLocator, String inputText, By suggestionLocator) {
        sendText(inputText, driver, inputLocator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionLocator));

        List<WebElement> suggestions = driver.findElements(suggestionLocator);
        if (!suggestions.isEmpty()) {
            String selectedText = suggestions.get(0).getText().trim();
            suggestions.get(0).click();
            return selectedText;
        } else {
            throw new NoSuchElementException("No suggestions found for input: " + inputText);
        }
    }

    // Waits for a specific element to be visible (custom timeout)
    public static void waitForElementVisible(WebDriver driver, By locator, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Waits for a specific text to be present inside an element
    public static void waitForTextPresent(WebDriver driver, By locator, String text, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
}
