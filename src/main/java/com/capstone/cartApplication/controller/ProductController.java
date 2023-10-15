package com.capstone.cartApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.dto.ProductRequest;
import com.capstone.cartApplication.model.Products;
import com.capstone.cartApplication.service.ProductService;

@RestController
@RequestMapping("/item")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	
	   @GetMapping("/fetchItemId")
	    private ResponseEntity fetchItem(@RequestBody ProductRequest productRequest) {
		   
	        try {
	            Products products = productService.findItemById(productRequest);
	            return new ResponseEntity<>(products, HttpStatus.OK);
	        } catch (Exception e) {
	            System.out.println("Find Items method error {}"+ e.getMessage());
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
