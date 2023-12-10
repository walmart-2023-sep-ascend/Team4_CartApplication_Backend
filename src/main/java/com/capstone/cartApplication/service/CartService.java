package com.capstone.cartApplication.service;

import java.util.List;

import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.utility.ProductException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface CartService {
	Cart saveOrUpdate(CartRequest cartRequest) throws Exception;

    Cart findCartByUserId(int userid) throws ProductException;
    
    Cart findCartByCartId(int cartid) throws ProductException;
    

    List<Cart> findAllCarts();

    Cart removeProductFromCart(Cart cart,String email,Integer prodId) throws ProductException,IllegalAccessException, JsonMappingException, JsonProcessingException;
    
    Cart removeItemFromCart(int userId, int prodid) throws ProductException;
    
}
