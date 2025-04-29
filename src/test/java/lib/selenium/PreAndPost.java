package lib.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.restassured.RestAssured;
import lib.utils.DataInputProvider;
import lib.utils.HTMLReporter;
import lib.utils.OsUtils;

public class PreAndPost extends WebDriverServiceImpl {

	public String dataSheetName;

	@BeforeSuite
	public void beforeSuite() {
		startReport();
	}

	@BeforeClass
	public void beforeClass() {
		startTestCase(testCaseName, testDescription);
	}

	@BeforeMethod
	public void beforeMethod() throws FileNotFoundException, IOException {

		// Load the properties file
		Properties prop = new Properties();
		prop.load(Files.newInputStream(new File("./src/test/resources/config.properties").toPath()));

		// for reports
		startTestModule(nodes);
		test.assignAuthor(authors);
		test.assignCategory(category);
		HTMLReporter.svcTest = test;

		// settings for launching browser
		System.setProperty("webdriver.chrome.driver", "./drivers/" + OsUtils.getDriverName());
		System.setProperty("webdriver.chrome.silentOutput", "true");

		// Start browser
		webdriver = new ChromeDriver();
		driver = new EventFiringWebDriver(webdriver);
		driver.register(this);
		driver.manage().window().maximize();
		driver.get("https://" + prop.getProperty("server"));
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

	@AfterMethod
	public void afterMethod() {
		closeActiveBrowser();
	}

	@AfterSuite
	public void afterSuite() {
		endResult();
	}

	@DataProvider(name = "fetchData", indices = { 0 })
	public Object[][] getData() {
		return DataInputProvider.getSheet(dataSheetName);
	}

}
