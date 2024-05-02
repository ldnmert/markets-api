package com.akillifiyat.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.akillifiyat.entity.DiscountProduct;

import com.akillifiyat.repository.DiscountProductRepository;

@RestController
@RequestMapping("/discount")
public class HomeController {


	
	DiscountProductRepository discountProductRepository;


	@Autowired
	public HomeController(DiscountProductRepository discountProductRepository) {

		this.discountProductRepository = discountProductRepository;
	}
	
	

	@GetMapping("/get-discount-products")
	ResponseEntity<List<DiscountProduct>> migrosDiscount() {

		List<DiscountProduct> discountProducts = discountProductRepository.findAll();
		return new ResponseEntity<>(discountProducts, HttpStatus.OK);
	}

//	@GetMapping("/discount-products-carrefour")
//	public ResponseEntity<List<CarrefourDiscount>> carrefourDiscount() {
//		List<CarrefourDiscount> carrefourDiscountList = carrefourDiscountRepository.findAll();
//		int i = 0;
//		i += carrefourDiscountList.size();
//		System.out.println(i + "carrefoyr");
//		return new ResponseEntity<>(carrefourDiscountList, HttpStatus.OK);
//	}
//
//	@GetMapping("/discount-products-bim")
//	public ResponseEntity<List<BimDiscount>> bimDiscount() {
//		List<BimDiscount> bimDiscountList = bimDiscountRepository.findAll();
//		int i = 0;
//		i += bimDiscountList.size();
//		System.out.println(i + "bim");
//		System.out.println();
//		return new ResponseEntity<>(bimDiscountList, HttpStatus.OK);
//	}
//
//	@GetMapping("/discount-products-a101")
//	public ResponseEntity<List<A101Discount>> a101Discount() {
//		List<A101Discount> a101DiscountList = a101DiscountRepository.findAll();
//		int i = 0;
//		i += a101DiscountList.size();
//		System.out.println(i + "a101");
//	
//		return new ResponseEntity<>(a101DiscountList, HttpStatus.OK);
//	}
	

}
