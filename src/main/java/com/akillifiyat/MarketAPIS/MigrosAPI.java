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
import org.springframework.stereotype.Component;

import com.akillifiyat.entity.DiscountProduct;
import com.akillifiyat.entity.Product;

@Component
public class MigrosAPI {

	public List<DiscountProduct> discountProducts() {

		List<DiscountProduct> discountProducts = new ArrayList<DiscountProduct>();

		try {

			String apiUrl = "https://www.migros.com.tr/rest/home/screens";

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

				JSONArray mainShoppingLists = jsonResponse.getJSONObject("data").getJSONArray("tabShoppingLists")
						.getJSONObject(0).getJSONArray("itemInfos");

				for (int i = 0; i < mainShoppingLists.length(); i++) {

					JSONObject item = mainShoppingLists.getJSONObject(i);
					String photo = item.getJSONArray("images").getJSONObject(0).getJSONObject("urls")
							.getString("PRODUCT_DETAIL");

					DiscountProduct p = new DiscountProduct(item.getString("name"),
							Float.valueOf(item.getInt("regularPrice") / 100),
							Float.valueOf(item.getInt("shownPrice") / 100), photo

							, "MIGROS");

					discountProducts.add(p);

				}
			} else {
				System.out.println("Failed to retrieve JSON. Response code: " + responseCode);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return discountProducts;
	}

	String[] categories22 = { "meyve-sebze-c-2", "et-tavuk-balik-c-3", "sut-kahvaltilik-c-4", "temel-gida-c-5",
			"meze-hazir-yemek-donuk-c-7d", "icecek-c-6", "dondurma-c-41b", "atistirmalik-c-113fb", "firin-pastane-c-7e",
			"deterjan-temizlik-c-7", "kagit-islak-mendil-c-8d", "kisisel-bakim-kozmetik-saglik-c-8", "bebek-c-9",
			"ev-yasam-c-a", "kitap-kirtasiye-oyuncak-c-118ec", "cicek-c-502", "pet-shop-c-a0", "elektronik-c-a6" };

	public List<Product> getAllMigrosProducts() {

		List<Product> allMigrosProducts = new ArrayList();

		for (int i = 0; i < 18; i++) {
			for (int i2 = 1; i2 < 120; i2++) {
				String apiUrl;
				if (i2 == 1)
					apiUrl = "https://www.migros.com.tr/rest/search/screens/" + categories22[i];
				else
					apiUrl = "https://www.migros.com.tr/rest/search/screens/" + categories22[i] + "?sayfa=" + i2
							+ "&sirala=onerilenler";
				try {

					URL url = new URL(apiUrl);

					HttpURLConnection connection = (HttpURLConnection) url.openConnection();

					connection.setRequestMethod("GET");

					int responseCode = connection.getResponseCode();

					if (responseCode == HttpURLConnection.HTTP_OK) {

						BufferedReader in = new BufferedReader(
								new InputStreamReader(connection.getInputStream(), "UTF-8"));
						String inputLine;
						StringBuilder response = new StringBuilder();
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();

						JSONObject jsonResponse = new JSONObject(response.toString());

						if (jsonResponse.getJSONObject("data").getJSONObject("searchInfo").getInt("pageCount") == 0)
							break;

						JSONArray productsArray = jsonResponse.getJSONObject("data").getJSONObject("searchInfo")
								.getJSONArray("storeProductInfos");
						System.out.println(productsArray.length());

						for (int j = 0; j < productsArray.length(); j++) {

							JSONObject iteration = productsArray.getJSONObject(j);

							String productName = iteration.getString("name");
						Float productPrice = iteration.getFloat("regularPrice")/100;
					
						String productPhoto = iteration.getJSONArray("images").getJSONObject(0).getJSONObject("urls")
								.getString("PRODUCT_DETAIL");
						
						allMigrosProducts.add(new Product(productName, productPrice, productPhoto, "MIGROS"));
					
						}

					} else {
						System.out.println("Failed to retrieve JSON. Response code: " + responseCode);

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			
			}
			System.out.println(i + 1);
		}

		return allMigrosProducts;
	}

}
