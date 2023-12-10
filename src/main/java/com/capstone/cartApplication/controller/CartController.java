package com.capstone.cartApplication.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cartApplication.dto.CartItemDelRequest;
import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.dto.CartResponse;
import com.capstone.cartApplication.dto.CartToWishRequest;
import com.capstone.cartApplication.dto.ProductResponse;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.model.Products;
import com.capstone.cartApplication.service.CartService;
import com.capstone.cartApplication.service.MessageService;
import com.capstone.cartApplication.utility.ProductException;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartService cartService;


	@Autowired
	private MessageService messageService;


	@Autowired
	public CartController(CartService cartService)
	{ this.cartService=cartService;

	}

	@GetMapping(value = "/fetchCartDetails/user/{id}")
	private ResponseEntity getCartByCustomerId(@PathVariable(value = "id")int userId) {
		try {
			Cart cart = cartService.findCartByUserId(userId );
			CartResponse cartResponse=buildFetchCartResponse(cart);
			return new ResponseEntity<>(cartResponse, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Find Cart by Customer id method error {}" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping(value = "/addToCart")
	private ResponseEntity addToCart(@RequestBody CartRequest cartRequest) {
		try {

			System.out.println(" Inside addToCart");
			logger.info("Inside addToCart for User Id :"+cartRequest.getUserId());
			Cart cart = cartService.saveOrUpdate(cartRequest);
			System.out.println(" Response came "+cart.getUserId());
			CartResponse cartResponse=buildFetchCartResponse(cart);

			return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println("Exception :: Create Cart method error {}"+ e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	public CartResponse buildFetchCartResponse(Cart cart) {

		CartResponse cartResponse = new CartResponse();
		cartResponse.setCartId(cart.getCartId());
		cartResponse.setUserId(cart.getUserId());
		cartResponse.setAmount(cart.getAmount());
		List<ProductResponse> responseProd = new ArrayList<ProductResponse>();
		List<Products> responseProdlist = cart.getProducts();
		for (Products prod : responseProdlist) {
			ProductResponse p = new ProductResponse();
			p.setId(prod.getId());
			p.setQuantity(prod.getQuantity());
			responseProd.add(p);

		}
		cartResponse.setProducts(responseProd);

		return cartResponse;


	}

	 @PostMapping(value= "/moveFromCartToWish")
      private ResponseEntity moveFromCartToWish(@RequestBody CartToWishRequest cartToWishRequest) {
                 
            try {
               System.out.println("Inside moveFromCartToWish");
               
               //Cart cart = cartService.findCartByCartId(cartToWishRequest.getCartID()); 
               Cart cart = cartService.findCartByUserId(cartToWishRequest.getUserId());
               cart=cartService.removeProductFromCart(cart,cartToWishRequest.getEmail(),cartToWishRequest.getProdId());
               
               CartResponse cartResponse=buildFetchCartResponse(cart);
               return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
            }catch (ProductException e) {
            	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
             }
            catch (Exception e) {
            	String stacktrace = ExceptionUtils.getStackTrace(e);
            	System.out.println("stacktrace "+ stacktrace);
             return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
             
             }
                 
	  }
	 
	 
	 	@DeleteMapping("/remove")
	    public ResponseEntity removeItemFromCart(@RequestBody CartItemDelRequest cartRequest) {
			try {
				logger.info("---Inside removeItemFromCart"+cartRequest.getUserId()+"cartRequest.getProdId()"+cartRequest.getProdId());
				Cart cart =cartService.removeItemFromCart(cartRequest.getUserId(), cartRequest.getProdId());
				CartResponse cartResponse=buildFetchCartResponse(cart);
				return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
			} catch (ProductException e) {
				logger.error("Error While deleting "+e.getMessage());
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	    }

}