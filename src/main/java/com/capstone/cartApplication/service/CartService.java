package com.capstone.cartApplication.service;

import java.util.List;

import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.model.Cart;


public interface CartService {
	Cart saveOrUpdate(CartRequest cartRequest) throws Exception;

    Cart findCartByUserId(CartRequest cartRequest);

    List<Cart> findAllCarts();

}

