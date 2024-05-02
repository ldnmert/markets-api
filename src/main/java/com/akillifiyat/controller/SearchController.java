package com.akillifiyat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akillifiyat.entity.Product;
import com.akillifiyat.repository.ProductRepository;

@RestController
@RequestMapping("/search")
@CrossOrigin("*")
public class SearchController {

	private final ProductRepository productRepository;

	@Autowired
	public SearchController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
//
//	@CrossOrigin("http://localhost:4200")
	@GetMapping
	public ResponseEntity<List<Product>> keyword(@RequestParam String searchTerm, @RequestParam(required = false) String sort) {
		
		    List<Product> productsPage = null;

		    if (sort == null) {
		        productsPage = productRepository.findProducts(searchTerm);
		    } else if (sort.equals("DESC")) {
		        productsPage = productRepository.findProductsDesc(searchTerm);
		    } else if (sort.equals("ASC")) {
		        productsPage = productRepository.findProductsAsc(searchTerm);
		    }

		    return new ResponseEntity<>(productsPage, HttpStatus.OK);
		


	}

}
