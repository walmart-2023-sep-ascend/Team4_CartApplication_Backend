package com.capstone.cartApplication.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cartApplication.controller.CartController;
import com.capstone.cartApplication.convert.CartRequestToCart;
import com.capstone.cartApplication.convert.ProductRequestToProduct;
import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.dto.CartToWishRequest;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.model.Products;
import com.capstone.cartApplication.repository.CartRepository;
import com.capstone.cartApplication.repository.ProductRepository;
import com.capstone.cartApplication.utility.ProductException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class CartServiceImpl extends Exception   implements CartService {

	private CartRepository cartRepository;
	private CartRequestToCart cartRequestToCart;
	private ProductRepository productRepository;

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired	
	private MessageService messageService;

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, CartRequestToCart cartRequestToCart,ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.cartRequestToCart = cartRequestToCart;
		this.productRepository=productRepository;

	}


	@Override
	public Cart saveOrUpdate(CartRequest cartRequest) throws Exception {

		Cart c = null;
		boolean isItemPresent =false;
		try {
			c =cartRepository.findCartByUserId(cartRequest.getUserId());
		}catch(Exception e) {
			logger.error("Exception occered inside  cart saveOrUpdate process "+e);
		}

		if(c!=null && c.get_id()!=null) { // Update Cart 
			List<Products> dbProductList = c.getProducts();
			List<Products> reqProdList = cartRequest.getProducts();

			for (Products reqProduct : reqProdList)
			{
				for (Products dbProduct : dbProductList)
				{
					boolean  isItemAvailable =isProductAvailable(reqProduct);

					if( isItemAvailable) {
						if (dbProduct.getId().equals(reqProduct.getId()))
						{
							dbProduct.setQuantity(reqProduct.getQuantity());
							isItemPresent=true;
							break;
						}
					}else {
						throw new ProductException("Product or Quantity is not available ");
					}
				}
				if(!isItemPresent) {
					Products p = new Products();
					p.setId(reqProduct.getId());
					p.setQuantity(reqProduct.getQuantity());
					dbProductList.add(p);
				}
			}
			c.setProduct(dbProductList);

			return cartRepository.save(c);
		}else { // Create Cart
			if(cartRepository.max()!=null) {
				cartRequest.setId(cartRepository.max()+1); // fetch the max count and insert into cart
				Date d =getDate();
				cartRequest.setDate(d);
			}
			else
				cartRequest.setId(1);

			return cartRepository.save(cartRequestToCart.convert(cartRequest));
		}

	}

	@Override
	public Cart findCartByUserId(int  userID) throws ProductException {
		logger.info("Inside findCartByUserId");
		if(cartRepository.findCartByUserId(userID)==null)
			throw new ProductException("User Details not available in cart table ");
		else
			return cartRepository.findCartByUserId(userID);

	}

	@Override
	public List<Cart> findAllCarts() {
		return cartRepository.findAll();
	}


	public boolean isProductAvailable(Products reqProduct) {
		boolean productAvaliablity = false;

		Products products = productRepository.findItemById(reqProduct.getId());
		if(products!=null && products.getId()!=null) {

			if( products.getAvailableQty() >=reqProduct.getQuantity()) 
			{

				productAvaliablity=true;
			}
		}
		logger.info("productAvaliablity : "+productAvaliablity);

		return productAvaliablity;
	}

	@Override
	public Cart removeProductFromCart(Cart cart,Integer prodId) {
		List<Products> listProduct=cart.getProducts();
		for(Products p:listProduct) {
			if(p.getId() == prodId) {
				listProduct.remove(p);
			}
			cart.setProduct(listProduct);
		}
		return cartRepository.save(cart);
	}

	public Date getDate() {
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy hh:mm:ss");
		Date date = java.sql.Date.valueOf(currentDate);
		return date;
	}


}