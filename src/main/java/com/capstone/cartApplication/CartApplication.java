package com.capstone.cartApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.capstone.cartApplication.repository.CartRepository;
import com.capstone.cartApplication.repository.ProductRepository;

import jakarta.annotation.Resource;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories(basePackageClasses = {CartRepository.class,ProductRepository.class})
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);

	}

	@Resource
	private CartRepository cartRepository;

	@Resource
	private ProductRepository productRepository;

	

}