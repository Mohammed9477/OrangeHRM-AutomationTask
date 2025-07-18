package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.*;

public class AdminPage {
    private WebDriver driver;

    // Locators for Admin page elements
    private final By adminTab = By.xpath("//span[text()='Admin']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleDropdown = By.xpath("(//div[@class='oxd-select-text-input'])[1]");
    private final By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    private final By statusDropdown = By.xpath("(//div[@class='oxd-select-text-input'])[2]");
    private final By usernameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private final By passwordField = By.xpath("(//input[@type='password'])[1]");
    private final By confirmPasswordField = By.xpath("(//input[@type='password'])[2]");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By searchUsernameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By recordsFoundText = By.xpath("//span[@class='oxd-text oxd-text--span']");
    private final By deleteIcon = By.xpath("//i[@class='oxd-icon bi-trash']");
    private final By confirmDeleteBtn = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By suggestionOptions = By.xpath("//div[@role='listbox']//span");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    // Clicks the Admin tab
    public void clickAdminTab() {
        ElementHelper.click(driver, adminTab);
    }

    // Returns number of records from admin user list
    public int getRecordCount() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Try to get record count from visible text
            try {
                WebElement recordCountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class,'oxd-text--span') and contains(text(),'Records Found')]")));
                String countText = recordCountElement.getText();
                String count = countText.replaceAll("[^0-9]", "");
                return Integer.parseInt(count);
            } catch (Exception e) {
                System.out.println("Alternative record count method used");
            }

            // Fallback to counting actual table rows
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".oxd-table-card")));
            return rows.size();

        } catch (Exception e) {
            System.out.println("Error getting record count: " + e.getMessage());
            return 0;
        }
    }

    // Adds a new user and returns the generated username
    public String addNewUser() {
        ElementHelper.click(driver, addButton);
        ElementHelper.click(driver, userRoleDropdown);
        ElementHelper.findElementByText("ESS", driver).click();

        // Handle employee name suggestion and selection
        String EmployeeName = ElementHelper.selectFromAutoSuggest(
                driver,
                employeeNameField,
                "a ",
                suggestionOptions
        );

        ElementHelper.click(driver, statusDropdown);
        ElementHelper.findElementByText("Enabled", driver).click();

        String username = "user" + System.currentTimeMillis();
        ElementHelper.sendText(username, driver, usernameField);
        ElementHelper.sendText("password123", driver, passwordField);
        ElementHelper.sendText("password123", driver, confirmPasswordField);

        ElementHelper.click(driver, saveButton);
        return username;
    }

    // Searches for a user by username
    public void searchUser(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for the field and enter the username only if not already filled
        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchUsernameField));
        if (!searchField.getAttribute("value").equals(username)) {
            searchField.clear();
            searchField.sendKeys(username);
        }

        // Click the search button
        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();

        // Wait for either results or 'No Records Found'
        wait.until(driver1 ->
                driver1.findElements(By.xpath("//div[@class='oxd-table-card']")).size() > 0 ||
                        driver1.findElements(By.xpath("//span[contains(text(),'No Records Found')]")).size() > 0
        );
    }

    // Checks whether the user is present in the table
    public boolean isUserPresent(String username) {
        try {
            return driver.findElements(
                    By.xpath("//div[contains(text(),'" + username + "')]")).size() > 0;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    // Verifies the Admin page is loaded
    public void verifyAdminPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[normalize-space()='System Users']")));
    }

    // Clears search field and reloads all users
    public void clearSearchAndSearchAgain() {
        WebElement searchField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(searchUsernameField));
        searchField.clear();

        WebElement searchBtn = driver.findElement(searchButton);
        searchBtn.click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText));
    }

    // Alternative method to clear and reload search
    public void clearSearchAndReload() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(searchUsernameField));
        searchField.clear();

        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText));
    }

    // Deletes a user after searching and confirming presence
    public void deleteUser(String username) {
        searchUser(username);
        if (isUserPresent(username)) {
            ElementHelper.click(driver, deleteIcon);
            ElementHelper.click(driver, confirmDeleteBtn);
        }
    }
}
