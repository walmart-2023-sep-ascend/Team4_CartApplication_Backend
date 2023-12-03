package com.capstone.cartApplication.service;

import java.util.List;

import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.utility.ProductException;


public interface CartService {
	Cart saveOrUpdate(CartRequest cartRequest) throws Exception;

    Cart findCartByUserId(int userid) throws ProductException;
    
    Cart findCartByCartId(int cartid) throws ProductException;
    

    List<Cart> findAllCarts();

    Cart removeProductFromCart(Cart cart,Integer prodId) throws ProductException;
    
    Cart removeItemFromCart(int userId, int prodid) throws ProductException;
    
}
