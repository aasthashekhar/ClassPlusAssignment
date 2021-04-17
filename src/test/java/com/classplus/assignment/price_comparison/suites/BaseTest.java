package com.classplus.assignment.price_comparison.suites;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.classplus.assignment.price_comparison.pages.PhoneComparisonKeyword;

/**
 * 
 * @author aasthashekhar
 */
public class BaseTest {

	public WebDriver driver;
	protected PhoneComparisonKeyword keywordObj;

	@BeforeTest
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		keywordObj = new PhoneComparisonKeyword(driver);
	}

	@AfterTest
	public void closeSession() {
		driver.quit();
	}
}
