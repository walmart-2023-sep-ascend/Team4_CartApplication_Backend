package com.capstone.cartApplication.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.model.Cart;


@Component
public class CartRequestToCart implements Converter<CartRequest, Cart> {

	@Override
	public Cart convert(CartRequest cartRequest) {
		System.out.println("--Inside cartRequestTocart--");
		Cart cart = new Cart();


		
		  if (!StringUtils.isEmpty(cartRequest.getId())) {
		  System.out.println("--Inside cartRequestTocart-- "+cartRequest.getId());
		  cart.setCartId(cartRequest.getId()); }
		 

		cart.setUserId(cartRequest.getUserId());
		cart.setPromoCode(cartRequest.getPromoCode());
		cart.setDate(cartRequest.getDate());
		cart.setAmount(cartRequest.getAmount());
		cart.setProduct(cartRequest.getProducts());

		//System.out.println("cartRequest.getProducts()-->"+cartRequest.getProducts() );
		return cart;
	}
}