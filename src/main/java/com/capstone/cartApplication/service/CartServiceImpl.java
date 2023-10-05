package com.capstone.cartApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cartApplication.convert.CartRequestToCart;
import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.repository.CartRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	private CartRepository cartRepository;
	private CartRequestToCart cartRequestToCart;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, CartRequestToCart cartRequestToCart) {
		this.cartRepository = cartRepository;
		this.cartRequestToCart = cartRequestToCart;
	}


	@Override
	public Cart saveOrUpdate(CartRequest cartRequest) {
		System.out.println(" -Inside saveOrUpdate - ");
		System.out.println("cartRequest"+cartRequest.getProducts().size());
		return cartRepository.save(cartRequestToCart.convert(cartRequest));
	}

	@Override
	public Cart findCartByUserId(CartRequest cartRequest) {
		System.out.println("-- Inside findCartByUserId ---");
		return cartRepository.findCartByUserId(cartRequest.getUserId());
	}

	@Override
	public List<Cart> findAllCarts() {
		return cartRepository.findAll();
	}





}
