package tests.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import lib.utils.OsUtils;

public class SeleniumTest {

    @Test
    public void openGoogleTest() {
        System.setProperty("webdriver.chrome.driver", "./drivers/"+OsUtils.getDriverName());
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        assert title.contains("Google") : "Title does not contain 'Google'";
//        driver.quit();
    }
}
