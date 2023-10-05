package com.capstone.cartApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.capstone.cartApplication.repository.CartRepository;
import com.capstone.cartApplication.service.CartService;

@SpringBootApplication

@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
@EnableMongoRepositories(basePackageClasses = CartRepository.class)
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
		
	}

}
