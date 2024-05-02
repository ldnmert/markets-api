package com.akillifiyat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AkilliFiyatApplication {

	public static void main(String[] args) {	
		SpringApplication.run(AkilliFiyatApplication.class, args);
	}

}
