package com.capstone.cartApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.dto.ProductRequest;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.model.Products;
import com.capstone.cartApplication.service.*;


@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	  @Autowired
	  public CartController(CartService cartService)
	  { this.cartService=cartService;
	  
	  }
	 
	


	@GetMapping(value = "/fetchCartDetails")
	private ResponseEntity getCartByCustomerId(@RequestBody CartRequest cartRequest) {
		System.out.println(" Step 1: Inside fetchCartDetails ");
		try {
			Cart cart = cartService.findCartByUserId(cartRequest);
			return new ResponseEntity<>(cart, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Find Cart by Customer id method error {}" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	  @PostMapping(value = "/addToCart")
	    private ResponseEntity addToCart(@RequestBody CartRequest cartRequest) {
	        try {
	        	
	        	System.out.println(" Inside addToCart");
	        	System.out.println(" incoming Request for User Id> "+cartRequest.getUserId());
	        	
	            Cart cart = cartService.saveOrUpdate(cartRequest);
	            return new ResponseEntity<>(cart, HttpStatus.CREATED);
	        } catch (Exception e) {
	        	System.out.println("Exception :: Create Cart method error {}"+ e.getMessage());
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

		/*
		 * @GetMapping(value = "/fetchItem") private ResponseEntity
		 * getItemById(@RequestBody ProductRequest productRequest) {
		 * System.out.println("Inside fetchCartDetails "); try { Products product =
		 * productService.findItemById(productRequest.getId());
		 * System.out.println("outside "+ productRequest.getId()); return new
		 * ResponseEntity<>(product, HttpStatus.OK); } catch (Exception e) {
		 * System.out.println("Find item by item id method error {}" + e.getMessage());
		 * return new ResponseEntity<>(e.getMessage(),
		 * HttpStatus.INTERNAL_SERVER_ERROR); } }
		 */

}
