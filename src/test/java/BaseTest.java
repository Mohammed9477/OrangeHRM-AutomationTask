import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utilities.DriverManager;
import utilities.ExtentManager;
import utilities.LoadProperties;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeClass
    public void setupDriver() {
        driver = DriverManager.getDriver();
        driver.get(LoadProperties.getProperty("url"));
    }

    @AfterClass
    public void quitDriver() {
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush(); // write everything to the report
        }
    }
}
