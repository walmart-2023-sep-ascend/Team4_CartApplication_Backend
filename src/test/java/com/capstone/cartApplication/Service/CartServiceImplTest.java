package com.capstone.cartApplication.Service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import com.capstone.cartApplication.model.Cart;
import com.capstone.cartApplication.repository.CartRepository;
import com.capstone.cartApplication.service.CartServiceImpl;
import com.capstone.cartApplication.utility.ProductException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes= {CartServiceImplTest.class})
public class CartServiceImplTest {
	
	 @Mock
	 private CartRepository cartRepository;
	  
	 @InjectMocks
	 CartServiceImpl cartServiceImpl;
	
	
	 @Test
	 @Order(1)
	 public void findCartByUserIdTest() throws ProductException {
		 
		 Cart cart = new Cart();
		 cart.setCartId(4);
		 cart.setUserId(4);
		 cart.setAmount(200.0);
	     
		 when(cartRepository.findCartByUserId(4)).thenReturn(cart);
		 
	     Cart cartResponse = cartServiceImpl.findCartByUserId(4);
	     //cartResponse.
	     assertEquals(4,(cartResponse.getCartId()).intValue());
	     System.out.println("Tests : "+ cartResponse.getCartId());
	 }
	 
	 
	 @Test
	 @Order(2)
	 public void findCartByCartIdTest() throws Exception{
		 
		 Cart cart = new Cart();
		 cart.setCartId(5);
		 cart.setUserId(6);
		 cart.setAmount(300.0);
		 	 
		 when(cartRepository.findCartByCartId(5)).thenReturn(cart);
		 
		 Cart cartResponse = cartServiceImpl.findCartByCartId(5);
		  assertEquals(300.0,(cartResponse.getAmount()));
	 }

	 	
	@Test
	@Order(3)
	public void  getAllCarts() {
		List<Cart> carts =new ArrayList<Cart>();
		Cart cart1 = new Cart();
		cart1.setCartId(2);
		cart1.setUserId(4);
		cart1.setAmount(300.0);
		 
		Cart cart2 = new Cart();
		cart2.setCartId(3);
		cart2.setUserId(9);
		cart2.setAmount(800.0);
		carts.add(cart1);
		carts.add(cart2);
		
		when(cartRepository.findAll()).thenReturn(carts);
		assertEquals(2,cartServiceImpl.findAllCarts().size());
	}
}
