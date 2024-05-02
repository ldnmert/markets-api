package com.akillifiyat.MarketAPIS;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import com.akillifiyat.entity.Product;

@Component
public class SokAPI {

	private  String[] ewq = { "meyve-ve-sebze-c-20", "et-ve-tavuk-ve-sarkuteri-c-160", "anne-bebek-ve-cocuk-c-20634",
			"atistirmaliklar-c-20376", "sut-ve-sut-urunleri-c-460", "kahvaltilik-c-890", "ekmek-ve-pastane-c-1250",
			"dondurma-c-31102", "dondurulmus-urunler-c-1550", "yemeklik-malzemeler-c-1770", "icecek-c-20505",
			"kisisel-bakim-ve-kozmetik-c-20395", "temizlik-c-20647", "kagit-urunler-c-20875", "evcil-dostlar-c-20880",
			"elektronik-c-22769", "giyim-ayakkabi-ve-aksesuar-c-20886", "ev-ve-yasam-c-20898" };

	public List<Product> getAllProducts() {

		List<Product> allSokProducts = new ArrayList();

		int jx = 0;
		String a = "?page=";
		WebDriver driver = new ChromeDriver();
		for (int s = 0; s < ewq.length; s++) {
			for (int i = 1; i < 25; i++) {
				if (i == 1)
					driver.get("https://www.sokmarket.com.tr/" + ewq[s]);
				else
					driver.get("https://www.sokmarket.com.tr/" + ewq[s] + a + i);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.cssSelector(".CProductCard-module_containerTop__35Ma3")));
				} catch (TimeoutException te) {
					System.out.println("bitecek aninda");
					break;
				}
				List<WebElement> elements = driver
						.findElements(By.cssSelector(".CProductCard-module_containerTop__35Ma3"));

				for (WebElement element : elements) {
					try {

						WebElement itemNameElement = element
								.findElement(By.cssSelector(".CProductCard-module_title__u8bMW"));

						WebElement itemPriceElement = element
								.findElement(By.className("CPriceBox-module_price__bYk-c"));

						WebElement itemPhoto = element
								.findElement(By.className("CProductCard-module_imageContainer__aTMdz"));
						WebElement itemPhotoSrc = itemPhoto.findElement(By.tagName("img"));

						String itemName = itemNameElement.getText();
						String itemPriceSalt = itemPriceElement.getText();
						String itemPrice = itemPriceSalt.replace("₺", "").replace(",", "."); 

						float itemPriceFloat = Float.valueOf(itemPrice);
						
						String itemPhotoURL = itemPhotoSrc.getAttribute("src");
						System.out.println(itemName + ++jx);
						Product sokProduct = new Product(itemName, itemPriceFloat, itemPhotoURL, "SOK");

						allSokProducts.add(sokProduct);

					} catch (Exception e) {
						System.out.println("bir urun yok --------------------------------------------------------------------------------------------");
					}
				}

			}
		}

		driver.quit();

		return allSokProducts;
	}

}
