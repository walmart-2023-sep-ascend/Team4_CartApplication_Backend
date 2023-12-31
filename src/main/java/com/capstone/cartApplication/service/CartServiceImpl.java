package com.capstone.cartApplication.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.cartApplication.controller.CartController;
import com.capstone.cartApplication.convert.CartRequestToCart;
import com.capstone.cartApplication.dto.CartRequest;
import com.capstone.cartApplication.feign.CartInterface;
import com.capstone.cartApplication.feign.CartWishlistInterface;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.model.Products;
import com.capstone.cartApplication.model.Wishlist;
import com.capstone.cartApplication.repository.CartRepository;
import com.capstone.cartApplication.repository.ProductRepository;
import com.capstone.cartApplication.utility.ProductException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class CartServiceImpl extends Exception   implements CartService {

	private CartRepository cartRepository;
	private CartRequestToCart cartRequestToCart;
	private ProductRepository productRepository;

	@Autowired	
	CartInterface cartInterface;

	@Autowired	
	CartWishlistInterface cartWishlistInterface;


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
			c.setAmount(cartRequest.getAmount());
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
	public Cart removeProductFromCart(Cart cart,String email,Integer prodId) throws ProductException,IllegalAccessException, JsonMappingException, JsonProcessingException {
		
		logger.info("-- Inside removeProductFromCart ---");
		
		List<Products> listProduct=new ArrayList<>(cart.getProducts());
		boolean isProductinCart=false;
		Double reducedPrice=0.0;
		Double cartPrice=0.0;
		cartPrice=cart.getAmount();
		System.out.println("Inside removeProductFromCart "+cartPrice);
		logger.info("Initial cartPrice during removeProductFromCart "+cartPrice);

		for (int i = 0; i < listProduct.size(); i++) {


			if((listProduct.get(i)).getId() == prodId) {
				isProductinCart=true;
				ResponseEntity<Object> respProduct=cartInterface.get(prodId);
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> map = objectMapper.convertValue(respProduct.getBody(), new TypeReference<Map<String, Object>>() {});
				Object o=map.get("data");
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				Products p = objectMapper.readValue(objectMapper.writeValueAsString(o), Products.class);
				logger.info("ProductPrice of the item to be removed during removeProductFromCart "+p.getRetailPrice());
				reducedPrice=cartPrice-p.getRetailPrice();

				//Updating Wishlist Product before removing from cart
				Wishlist w=new Wishlist();
				List<Products> wishlistprod =new ArrayList<>();
				w.setEmail(email);
				//w.setUserId(cart.getUserId());
				wishlistprod.add(listProduct.get(i));
				w.setProducts(wishlistprod);

				logger.info("-- Removing ProductId ---"+(listProduct.get(i)).getId()+"  :  "+prodId);
				listProduct.remove((listProduct.get(i)));
				logger.info("--After Removing ---"+listProduct);
				//System.out.println("Inside removeProductFromCart after "+listProduct);

				//Calling Wishlist Add method to add the removed product to wishlist
				logger.info("Product to be added in wishlist "+w.getProducts());
				logger.info("Email for which product is added in wishlist "+w.getEmail());
				 Wishlist addedwishlist=cartWishlistInterface.insert(w);
				//Wishlist addedwishlist=cartWishlistInterface.insert(w);

			}
			logger.info("Final cartPrice during removeProductFromCart "+reducedPrice);
			cart.setAmount(reducedPrice);
			cart.setProduct(listProduct);
		}
		cart=cartRepository.save(cart);


		if(!isProductinCart)
			throw new ProductException("Product is not in Cart and cannot be moved.");
		else
		{
			return cart;
		}
	}

	public Date getDate() {
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy hh:mm:ss");
		Date date = java.sql.Date.valueOf(currentDate);
		return date;
	}
	@Override
	public Cart findCartByCartId(int cartid) throws ProductException{
		logger.info("Inside findCartByCartId"); 
		if(cartRepository.findCartByCartId(cartid)==null)
			throw new ProductException("Cart Details not available in cart table ");
		else
			return cartRepository.findCartByCartId(cartid);
	}
	@Override
	public Cart removeItemFromCart(int userId, int id) throws ProductException {
		logger.error("Inside removeItemFromCart ");
		Cart c = null;
		boolean isItemPresent =false;
		try {
			c =cartRepository.findCartByUserId(userId);
		}catch(Exception e) {
			logger.error("Exception occered inside  cart saveOrUpdate process "+e);
		}

		if(c!=null && c.get_id()!=null) { // Update Cart 
			List<Products> dbProductList = c.getProducts();


			Iterator<Products> iterator = dbProductList.iterator();
			while (iterator.hasNext()) {
				Products product = iterator.next();
				if (product.getId() == id) {
					logger.info("Remvoing product id ->"+id); 
					iterator.remove();
					double updatedPrice=updateCartPrice(id,c.getAmount(),product.getQuantity());
					logger.info("updated cart price  ->"+updatedPrice); 
					c.setAmount(updatedPrice);
				}

			}
			c.setProduct(dbProductList);

			logger.error("size of cart "+dbProductList.size());
		}


		return cartRepository.save(c);
	}

	public double updateCartPrice(int pId, double totalAmt, int qty) throws ProductException {
		logger.info("Inside updateCartPrice");
		logger.info("Product id "+pId+"  cart amt ->"+totalAmt+"  qty->"+qty);
		double updatedPrice=0;
		Products products = productRepository.findItemById(pId);
		int productPrice =products.getRetailPrice();

		updatedPrice=totalAmt -(productPrice*qty);
		logger.info(" prod priceß  "+productPrice*qty);
		logger.info(" updateCartPrice  "+updatedPrice);
		if(updatedPrice>0)
			return updatedPrice;
		else
			throw new ProductException("Update cart value exception"); 
	}



}