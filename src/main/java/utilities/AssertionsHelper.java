package utilities;

import org.testng.Assert;

public class AssertionsHelper {

    public static void assertEqual(int actual, int expected) {
        Assert.assertEquals(actual, expected, "Values are not equal");
    }

    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }
}
