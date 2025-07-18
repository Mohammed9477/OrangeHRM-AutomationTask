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

}
