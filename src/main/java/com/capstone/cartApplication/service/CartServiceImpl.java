package com.capstone.cartApplication.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cartApplication.convert.CartRequestToCart;
import com.capstone.cartApplication.convert.ProductRequestToProduct;
import com.capstone.cartApplication.dto.CartRequest;
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

	@Autowired
	public CartServiceImpl(CartRepository cartRepository, CartRequestToCart cartRequestToCart,ProductRepository productRepository) {
		this.cartRepository = cartRepository;
		this.cartRequestToCart = cartRequestToCart;
		this.productRepository=productRepository;

	}


	@Override
	public Cart saveOrUpdate(CartRequest cartRequest) throws Exception {
		Cart c = null;
		try {
			c =cartRepository.findCartByUserId(cartRequest.getUserId());
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}

		if(c!=null && c.get_id()!=null) { // Update Cart 
			List<Products> newProductList = new ArrayList<Products>();
			List<Products> dbProductList = c.getProducts();
			List<Products> reqProdList = cartRequest.getProducts();

			for (Products reqProduct : reqProdList)
			{
				for (Products dbProduct : dbProductList)
				{
					boolean  isItemAvailable =isProductAvailable(reqProduct);	
					System.out.println("Is Product is available "+ isItemAvailable);
					if( isItemAvailable) {
						if (dbProduct.getId().equals(reqProduct.getId()))
						{
							dbProduct.setQuantity(reqProduct.getQuantity());
							newProductList.add(dbProduct);
							break;
						}else {
							System.out.println("creating new product  ---->"+dbProduct.getId());
							Products p = new Products();
							p.setId(reqProduct.getId());
							p.setQuantity(reqProduct.getQuantity());
							newProductList.add(p);
							break;
						}
					}else {
						throw new ProductException("Product or Quantity is not available ");
					}
				}
			}
			c.setProduct(newProductList);

			return cartRepository.save(c);
		}else { // Create Cart
			cartRequest.setId(cartRepository.max()+1); // fetch the max count and insert into cart
			return cartRepository.save(cartRequestToCart.convert(cartRequest));
		}

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


	public boolean isProductAvailable(Products reqProduct) {
		boolean productAvaliablity = false;

		Products products = productRepository.findItemById(reqProduct.getId());
		System.out.println("req id : "+reqProduct.getId());
		System.out.println("req qty ->: "+reqProduct.getQuantity());
		if(products.getId()!=null) {

			System.out.println("db qty "+ products.getAvailableQty());
			if( products.getAvailableQty() >=reqProduct.getQuantity()) 
			{

				productAvaliablity=true;
			}
		}
		System.out.println("productAvaliablity : "+productAvaliablity);

		return productAvaliablity;
	}






}
