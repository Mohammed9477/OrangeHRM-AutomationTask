import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.AdminPage;
import Pages.LoginPage;
import utilities.AssertionsHelper;

public class userManageTest extends BaseTest {

    LoginPage loginPage;
    AdminPage adminPage;
    AssertionsHelper assertHelper = new AssertionsHelper();

    @Test
    public void userManagementFlow() {
        test = extent.createTest("User Management Flow");

        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);

        test.log(Status.INFO, "Logging in with valid credentials");
        loginPage.login("Admin", "admin123");

        test.log(Status.INFO, "Navigating to Admin tab");
        adminPage.clickAdminTab();
        adminPage.verifyAdminPageLoaded();

        int oldRecordCount = adminPage.getRecordCount();
        test.log(Status.INFO, "Initial user count: " + oldRecordCount);

        String newUsername = adminPage.addNewUser();
        test.log(Status.PASS, "New user created: " + newUsername);

        Assert.assertTrue(adminPage.isUserPresent(newUsername), "User was not created successfully");
        test.log(Status.PASS, "User is displayed in the user list");

        int newRecordCount = adminPage.getRecordCount();
        AssertionsHelper.assertEqual(newRecordCount, oldRecordCount + 1);
        test.log(Status.PASS, "User count increased by 1");

        int RecordCountBeforeDelete = adminPage.getRecordCount();
        adminPage.deleteUser(newUsername);
        test.log(Status.INFO, "User deleted: " + newUsername);

        driver.navigate().refresh();
        adminPage.verifyAdminPageLoaded();

        int finalRecordCount = adminPage.getRecordCount();
        AssertionsHelper.assertEqual(finalRecordCount, RecordCountBeforeDelete - 1);
        test.log(Status.PASS, "User count decreased by 1 after deletion");
    }
}
