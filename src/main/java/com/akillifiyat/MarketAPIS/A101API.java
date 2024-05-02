package com.akillifiyat.MarketAPIS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.akillifiyat.entity.DiscountProduct;
import com.akillifiyat.entity.Product;



@Component
public class A101API {

	public List<DiscountProduct> discountProducts() {

		List<DiscountProduct> discountProducts = new ArrayList();

		try {
			String apiUrl = "https://www.a101.com.tr/_next/data/lh1Jsf0-GK7rz8lmb18NW/tr/ekstra/haftanin-yildizlari.json?promotion=haftanin-yildizlari";
			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String inputLine;
				StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				JSONObject jsonResponse = new JSONObject(response.toString());
				JSONArray mainShoppingLists = jsonResponse.getJSONObject("pageProps").getJSONObject("data")
						.getJSONArray("res");
				for (int i = 0; i < mainShoppingLists.length(); i++) {
					String isim = mainShoppingLists.getJSONObject(i).getString("name");
					String indirimsiz = mainShoppingLists.getJSONObject(i).getJSONObject("price")
							.getString("normalText");
					String indirimli = mainShoppingLists.getJSONObject(i).getJSONObject("price")
							.getString("discountedText");
					String resim = mainShoppingLists.getJSONObject(i).getJSONArray("images").getJSONObject(0)
							.getString("url");
					
					Float indirimsizFloat = Float.valueOf(indirimsiz.replace("₺", "").replace(",", "."));
					Float indirimliFloat = Float.valueOf(indirimli.replace("₺", "").replace(",", "."));
					DiscountProduct discountProduct = new DiscountProduct(isim, indirimliFloat, indirimsizFloat, resim, "A101");
					discountProducts.add(discountProduct);
				}
			} else {
				System.out.println("Failed to retrieve JSON. Response code: " + responseCode);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return discountProducts;
	}

	
	private List<String> getJSON() {

		List<String> jsons = new ArrayList<>();

		for (int i = 0; i < categories.length; i++) {
			try {
				Document document = Jsoup.connect("https://www.a101.com.tr/kapida/" + categories[i]).get();

				Element elements = document.getElementById("__NEXT_DATA__");
				String xd = elements.toString().replace("<script id=\"__NEXT_DATA__\" type=\"application/json\">",
						"");
				String yeah = xd.toString().replace("</script>", "");

				jsons.add(yeah);

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return jsons;
		
	}
	static int x = 0;
	static String[] categories = { "sut-urunleri-kahvaltilik", "meyve-sebze", "et-balik-tavuk", "firindan",
			"temel-gida", "atistirmalik", "icecek", "donuk-hazir-yemek-meze", "tatli", "dondurma",
			"temizlik-urunleri", "kisisel-bakim", "kagit-urunleri", "elektronik", "anne-bebek", "ev-yasam",
			"kitap-kirtasiye-oyuncak", "evcil-hayvan", "bayram" };

	public List<Product> a101AllProducts() {
		
		List<Product> allProductsA101 = new ArrayList();
		List<String> allJSONS = getJSON();

		for (int k = 0; k < allJSONS.size(); k++) {
			try {

				JSONObject jsonObject = new JSONObject(allJSONS.get(k));

				JSONArray allProducts = new JSONArray();

				JSONArray children = jsonObject.getJSONObject("props").getJSONObject("pageProps")
						.getJSONObject("productsByCategoryOutput").getJSONArray("children");

				for (int i = 0; i < children.length(); i++) {
					JSONObject category = children.getJSONObject(i);
					JSONArray products = category.getJSONArray("products");

					for (int j = 0; j < products.length(); j++) {
						JSONObject product = products.getJSONObject(j);
						allProducts.put(product);
					}
				}

				for (int i = 0; i < allProducts.length(); i++) {
				
					try {
					JSONObject product = allProducts.getJSONObject(i);
					JSONObject attributes = product.getJSONObject("attributes");
					String productName = attributes.getString("name");
					
					JSONObject price = product.getJSONObject("price");
					JSONArray image = product.getJSONArray("images");
					
					JSONObject getImage;
					
					
					if(image.getJSONObject(0).getString("imageType").equals("product")) 
						 getImage = image.getJSONObject(0);
					else if(image.getJSONObject(1).getString("imageType").equals("product")) {
						getImage = image.getJSONObject(1);
					}
					else
						getImage = image.getJSONObject(2);
	
				
	
					String normalPrice = price.getString("normalStr");
					String discountedPrice = price.getString("discountedStr");
					String fixedNormalPrice = normalPrice.replace("₺", "").replace(",", ".");
					
					
					float normalPriceFloat = Float.valueOf(fixedNormalPrice);
								

					String imageURL = getImage.getString("url");

					System.out.println(productName);
					Product product2 = new Product(productName, normalPriceFloat, imageURL, "A101");
					allProductsA101.add(product2);
					
					
					}
					catch(Exception e) {
						
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		

		}
		return allProductsA101;

	}

}
