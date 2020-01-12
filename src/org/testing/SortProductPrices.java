package org.testing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SortProductPrices {
	public static void main(String[] args) {
		System.setProperty("webdriver.chromedriver", System.getProperty("user.dir") + "/driver/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.flipkart.com/");
		WebDriverWait w = new WebDriverWait(driver, 20);
		// popup ad
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='_2AkmmA _29YdH8']")));

		WebElement txtSearch = driver.findElement(By.className("LM6RPg"));
		WebElement btnSearch = driver.findElement(By.xpath("//button[@type='submit']"));
		WebElement btnClosePopUp = driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']"));

		btnClosePopUp.click();
		txtSearch.sendKeys("hp laptop");
		btnSearch.click();

		// products to load
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='_2cLu-l']")));

		List<WebElement> lstProducts = driver.findElements(By.xpath("//a[@class='_2cLu-l']"));

		System.out.println("List count of products: " + lstProducts.size());

		List<WebElement> lstPrices = driver.findElements(By.xpath("//div[@class='_1vC4OE']"));

		System.out.println("List count of prices: " + lstPrices.size());

		String[] prodValue = new String[lstPrices.size()];
		int n = prodValue.length;

		for (int i = 0; i < n; i++)
			prodValue[i] = lstPrices.get(i).getText();

		float[] ap = new float[n];

		int k = 0;
		for (int j = 0; j < n; j++) {
			String temp = prodValue[j].substring(1);
			if (temp.contains(",")) {
				String replace = temp.replace(",", "");
				ap[j] = Float.parseFloat(replace);
				continue;
			}
			ap[j] = Float.parseFloat(temp);
			k++;
		}
		
		float temp = 0;
		WebElement bal = null;
		for (int x = 0; x < ap.length; x++) {
			for (int y = 0; y < ap.length; y++) {
					if (ap[x] > ap[y]) {
						//sorting the prices in descending order
					temp = ap[x];
					ap[x] = ap[y];
					ap[y] = temp;
						//sorting the corresponding web elements in the same order
					bal = lstProducts.get(x);
					lstProducts.set(x, lstProducts.get(y));
					lstProducts.set(y, bal);
				}
			}
		}
		for (float f : ap) {
			System.out.print(" "+f+" ");
		}
		System.out.println("The product with highest price:");
		System.out.println(lstProducts.get(0).getText());
		System.out.println("Product Price:" + ap[0]);
		System.out.println("The Product with the lowest price:");
		System.out.println(lstProducts.get(lstProducts.size()-1).getText());
		System.out.println("Product Price: "+ap[ap.length-1]);
		driver.quit();
	}

}
