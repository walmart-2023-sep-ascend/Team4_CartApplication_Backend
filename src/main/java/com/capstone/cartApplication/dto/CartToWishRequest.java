package com.capstone.cartApplication.dto;

public class CartToWishRequest {
	
	private Integer cartId;
	private Integer prodId;
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