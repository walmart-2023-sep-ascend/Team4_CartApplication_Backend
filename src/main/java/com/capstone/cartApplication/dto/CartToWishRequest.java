package com.capstone.cartApplication.dto;

public class CartToWishRequest {
	
	private Integer cartId;
	private Integer prodId;
	private Integer userId;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCartID() {
		return cartId;
	}
	public void setCartID(Integer cartID) {
		this.cartId = cartID;
	}
	public Integer getProdId() {
		return prodId;
	}
	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
}