package com.akillifiyat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name = "nameproduct")
	String nameproduct;

	Float price;

	String image;
	
	
	String marketName;

	public Product(String nameproduct, Float price, String image, String marketName) {
		this.marketName = marketName;
		this.nameproduct = nameproduct;
		this.price = price;
		this.image = image;
	}
	
}
