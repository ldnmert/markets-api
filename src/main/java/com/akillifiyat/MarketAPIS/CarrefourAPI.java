package com.akillifiyat.MarketAPIS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import com.akillifiyat.entity.DiscountProduct;
import com.akillifiyat.entity.Product;

@Component
public class CarrefourAPI {
	private static String removePartFromUrl(String url, String partToRemove) {
		return url.replace(partToRemove, "");
	}

//	public List<DiscountProduct> discountProducts() {
//		List<DiscountProduct> DiscountProductProducts = new ArrayList();
//		try {
//			Document document = Jsoup.connect("https://www.carrefoursa.com/").get();
//			Elements elements = document.getElementsByClass("product_click");
//
//			for (Element element : elements) {
//				try {
//					String html = element.html();
//					Document test = Jsoup.parse(html);
//					Element itemNameElement;
//					Element itemPriceElement;
//					Element imgElement;
//					Element ayrintiLink;
//					Element itemEskiFiyat;
//					if (test.select("img") != null && test.select("img").first() != null
//							&& test.select(".item-name") != null && test.select(".item-name").first() != null
//							&& test.select(".item-price") != null) {
//						itemNameElement = test.select(".item-name").first();
//						itemPriceElement = test.select(".item-price").first();
//						imgElement = test.select("img").first();
//						String itemName = itemNameElement.text();
//
//						if (itemPriceElement.attr("content") != null) {
//
//							String itemPrice = itemPriceElement.attr("content");
//
//							DecimalFormat decimalFormat = new DecimalFormat("#.##");
//
//							// Belirtilen formata göre sayıyı biçimlendir
//							itemPrice = decimalFormat.format(Double.parseDouble(itemPrice));
//
//							String dataSrc = imgElement.attr("data-src");
//
//							ayrintiLink = test.selectFirst("a");
//							String ayrintLinkString = "https://www.carrefoursa.com" + ayrintiLink.attr("href");
//
//							ayrintLinkString = removePartFromUrl(ayrintLinkString, "/quickView");
//
//							itemEskiFiyat = test.select("span.priceLineThrough.js-variant-price").first();
//							if (itemEskiFiyat != null) {
//								String[] fiyatParcalari = itemEskiFiyat.text().split(" TL");
//
//								// İlk parçayı seçme (519,90 TL'yi almak)
//								String eskiFiyatString = fiyatParcalari[0];
//
//								// System.out.println(itemName);
//								
//								DiscountProduct DiscountProduct = new DiscountProduct(itemName, itemPrice, eskiFiyatString, dataSrc, "CARREFOUR");
//								DiscountProductProducts.add(DiscountProduct);
//
//							}
//
//						}
//
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return DiscountProductProducts;
//	}
	
	
	
		String[] numbers = {
            "1015", "1025", "1045", "1098", "1065", "1070", "1102", "1061", "1174", "1234", 
            "1239", "1111", "1121", "1275", "1223", "1159", "1186", "1209", "1493", "1009", 
            "1939", "1948", "2538", "1940", "1963", "1454", "1484", "1412", "1418", "1411", 
            "1481", "1040", "1311", "1318", "1356", "1390", "1389", "1349", "1348", "1385", 
            "1038", "1962", "1875", "1873", "1899", "1858", "1847", "1915", "1613", "1591", 
            "1602", "1627", "1557", "1658", "1652", "1598", "1675", "1710", "1736", "1757", 
            "1772", "1785", "1805", "1831", "1838", "1729", "1821", "1820", "2305", "2313", 
            "2341", "2287", "2330", "2202", "2234", "2195", "2076", "2239", "1964", "2025", 
            "2149", "2127", "2054", "2342", "2115", "2190", "2105", "2186", "2537", "2138", 
            "1261", "1270", "1266"
        };

		public List<Product> getAllProducts() {
			int qwes = 0;
			List<Product> list = new ArrayList();
			

			WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
			for(int w = 0 ; w < numbers.length;w++) {
			driver.get("https://www.carrefoursa.com/unlu-mamuller-ve-tatlilar/c/"+ numbers[w] +"?q=%3AbestSeller&show=All");

			List<WebElement> elements = driver.findElements(By.cssSelector(".product_click"));

			for (WebElement element : elements) {
				try {

					WebElement itemNameElement = element.findElement(By.cssSelector(".item-name"));
					WebElement itemPriceElement = element.findElement(By.cssSelector(".item-price"));
					WebElement imgElement = element.findElement(By.tagName("img"));
//					WebElement ayrintiLink = element.findElement(By.tagName("a"));

					String itemName = itemNameElement.getText();
					String itemPriceSalt = (itemPriceElement.getAttribute("content"));
					
					   BigDecimal number = new BigDecimal(itemPriceSalt);
				        
				        BigDecimal formatted = number.setScale(2, RoundingMode.HALF_UP);
					
				    String itemPrice = formatted.toString();
				    
				    float floatPrice = Float.valueOf(itemPrice);
					
					String dataSrc = imgElement.getAttribute("data-src");
				
					
					Product c = new Product(itemName, floatPrice, dataSrc, "CARREFOUR");
					list.add(c);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
		}
			driver.quit();
			return list;
		}

}
