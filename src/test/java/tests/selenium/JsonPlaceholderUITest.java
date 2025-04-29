package tests.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.*;

public class JsonPlaceholderUITest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // 🔁 Replace with your path
        driver = new ChromeDriver();
    }

    @Test
    public void checkPageTitle() {
        driver.get("https://jsonplaceholder.typicode.com/");
        assertEquals(driver.getTitle(), "JSONPlaceholder - Fake online REST API");
    }

    @Test
    public void clickPostsLink() {
        driver.get("https://jsonplaceholder.typicode.com/");
        WebElement postsLink = driver.findElement(By.linkText("/posts"));
        postsLink.click();

        assertTrue(driver.getPageSource().contains("\"userId\""));
        assertTrue(driver.getCurrentUrl().contains("/posts"));
    }

    @Test
    public void validateJsonListItems() {
        driver.get("https://jsonplaceholder.typicode.com/posts");
        List<WebElement> preElements = driver.findElements(By.tagName("pre"));
        assertTrue(preElements.size() > 0 || driver.getPageSource().contains("{"), "Expected JSON content");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
