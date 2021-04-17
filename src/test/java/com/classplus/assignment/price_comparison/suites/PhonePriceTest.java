package com.classplus.assignment.price_comparison.suites;

import org.testng.annotations.Test;

/**
 * 
 * @author aasthashekhar
 */
public class PhonePriceTest extends BaseTest {
	int amazonPrice = 0;
	int flipkartPrice = 0;
	// iphone 11 pro max Gold 256 gb
	// iPhone XR (64GB) - Yellow
	String phoneName = "iPhone XR (64GB) - Yellow";

	@Test
	public void TC01_fetchPhonePriceAmazon() {
		keywordObj.launchUrl("https://www.amazon.in");
		amazonPrice = keywordObj.fetchPhonePriceAmazon(phoneName);
	}

	@Test
	public void TC02_fetchPhonePriceFlipkart() {
		keywordObj.launchUrl("https://www.flipkart.com");
		flipkartPrice = keywordObj.fetchPhonePriceFlipkart(phoneName);
	}

	@Test
	public void TC03_comparePrices() {
		keywordObj.comparePrices(amazonPrice, flipkartPrice);
	}
}
