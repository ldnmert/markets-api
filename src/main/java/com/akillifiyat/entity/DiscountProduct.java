package com.akillifiyat.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class DiscountProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String nameproduct;

	Float price;

	Float priceWithoutDiscount;

	String image;
	
	String marketName;

	public DiscountProduct(String nameproduct, Float price, Float priceWithoutDiscount, String image, String marketName) {

		this.nameproduct = nameproduct;
		this.price = price;
		this.priceWithoutDiscount = priceWithoutDiscount;
		this.image = image;
		this.marketName = marketName;
	}

}