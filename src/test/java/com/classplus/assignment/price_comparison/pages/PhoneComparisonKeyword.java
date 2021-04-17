package com.classplus.assignment.price_comparison.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * 
 * @author aasthashekhar
 */
public class PhoneComparisonKeyword {

	protected WebDriver driver;

	public PhoneComparisonKeyword(WebDriver webdriver) {
		this.driver = webdriver;
	}

	/**
	 * To launch the specified url.
	 *
	 * @param url
	 */
	public void launchUrl(String url) {
		driver.get(url);
		System.out.println("[INFO]: Launched URL: '" + url + "' \n");
	}

	/**
	 * To fetch phone price from amazon.
	 *
	 * @param phoneName
	 * @return
	 */
	public int fetchPhonePriceAmazon(String phoneName) {
		int price = 0;
		phoneName = replaceSpecialChars(phoneName);
		driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys(phoneName);
		System.out.println("[INFO]: Entered '" + phoneName + "' in search box \n");
		driver.findElement(By.cssSelector("#nav-search-submit-button")).click();
		System.out.println("[INFO]: Clicked on Search button \n");
		List<WebElement> amazonSearchResult = driver
				.findElements(By.cssSelector("[cel_widget_id^='MAIN-SEARCH_RESULTS'] h2 span"));
		boolean condition = false;
		for (int resultNo = 0; resultNo < amazonSearchResult.size(); resultNo++) {
			String searchResult = amazonSearchResult.get(resultNo).getText();
			condition = compareModelToSearchResult(phoneName, searchResult);
			if (condition) {
				price = Integer.parseInt(
						driver.findElements(By.cssSelector("[cel_widget_id^='MAIN-SEARCH_RESULTS'] .a-price-whole"))
								.get(resultNo).getText().replaceAll("[^0-9]", "").trim());
				Reporter.log(
						"[INFO]: Price for Product '" + searchResult + "' is displayed as :" + price + " in Amazon\n",
						true);
				break;
			} else
				System.out.println("[INFO]: Product does not match with searched result: '" + searchResult + "' \n");
		}
		Assert.assertTrue(condition, "[TEST FAILED]: Product '" + phoneName + "' is not displayed in Amazon \n");
		return price;
	}

	/**
	 * To fetch phone price from flipkart.
	 *
	 * @param phoneName
	 * @return
	 */
	public int fetchPhonePriceFlipkart(String phoneName) {
		driver.findElement(By.xpath("//button[text()='✕']")).click();
		System.out.println("[INFO]: Closed login popup \n");
		int price = 0;
		phoneName = replaceSpecialChars(phoneName);
		driver.findElement(By.cssSelector("input[title^='Search for products']")).sendKeys(phoneName);
		System.out.println("[INFO]: Entered '" + phoneName + "' in search box \n");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		System.out.println("[INFO]: Clicked on Search button \n");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> flipkartSearchResult = driver
				.findElements(By.cssSelector("div[data-tkid*='.SEARCH'] div[class*='_4rR01T']"));
		boolean condition = false;
		for (int resultNo = 0; resultNo < flipkartSearchResult.size(); resultNo++) {
			String searchResult = flipkartSearchResult.get(resultNo).getText();
			System.out.println("searchResult: " + searchResult);
			condition = compareModelToSearchResult(phoneName, searchResult);
			try {
				if (condition && driver.findElements(By.cssSelector("div[data-tkid*='.SEARCH'] span[class*='_192laR']"))
						.get(resultNo).isDisplayed())
					condition = false;
			} catch (IndexOutOfBoundsException e) {
				Reporter.log("[INFO]: Verified that product is not temporarily unvailable \n", true);
			}
			if (condition) {
				price = Integer
						.parseInt(driver.findElements(By.cssSelector("div[data-tkid*='.SEARCH'] div[class*='WHN1']"))
								.get(resultNo).getText().replaceAll("[^0-9]", "").trim());
				Reporter.log("[INFO]: Price for Product '" + searchResult + "' is displayed as :" + price + "\n", true);
				break;
			} else
				System.out.println(
						"[INFO]: Product does not match with searched result: '" + searchResult + "' in Flipkart \n");

		}
		Assert.assertTrue(condition, "[TEST FAILED]: Product '" + phoneName + "' is not displayed \n");
		return price;
	}

	/**
	 * To replace all special characters from searched text.
	 *
	 * @param text
	 * @return
	 */
	public String replaceSpecialChars(String text) {
		text = text.replaceAll("[^a-zA-Z0-9 ]", "");
		System.out.println("[INFO]: String after replacing special characters '" + text + "' \n");
		return text;
	}

	/**
	 * To check if the searched phone matches the search result text.
	 *
	 * @param modelName
	 * @param searchResult
	 * @return
	 */
	public boolean compareModelToSearchResult(String modelName, String searchResult) {
		boolean condition = true;
		for (String word : modelName.split(" ")) {
			if (!searchResult.replaceAll(" ", "").toLowerCase().contains(word.toLowerCase())) {
				condition = false;
				System.out.println("[INFO]: '" + word + "' is not found in search result: " + searchResult + " \n");
				break;
			}
		}
		return condition;
	}

	/**
	 * To compare price of amazon and flipkart.
	 *
	 * @param amazonPrice
	 * @param flipkartPrice
	 */
	public void comparePrices(int amazonPrice, int flipkartPrice) {
		Assert.assertFalse(amazonPrice == flipkartPrice && flipkartPrice == 0,
				"[TEST FAILED]: Product is displayed neither on Amazon nor on Flipkart \n");
		if (amazonPrice == flipkartPrice)
			Reporter.log("[INFO]: Both the website show same value which is: " + flipkartPrice, true);
		else if (amazonPrice > flipkartPrice && flipkartPrice != 0)
			Reporter.log("[INFO]: Flipkart Price is less which is: " + flipkartPrice, true);
		else if (amazonPrice > flipkartPrice && flipkartPrice == 0)
			Reporter.log("[INFO]: Phone not displayed in Flipkart so, Amazon Price of Iphone is: " + flipkartPrice,
					true);
		else if (flipkartPrice > amazonPrice && amazonPrice != 0)
			Reporter.log("[INFO]: Amazon Price is less which is: " + flipkartPrice, true);
		else if (flipkartPrice > amazonPrice && amazonPrice == 0)
			Reporter.log("[INFO]: Phone not displayed in Amazon so, Flipkart Price of Iphone is: " + flipkartPrice,
					true);
	}
}
