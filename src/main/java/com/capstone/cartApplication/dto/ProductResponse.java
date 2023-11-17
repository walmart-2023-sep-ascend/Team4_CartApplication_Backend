package com.capstone.cartApplication.dto;

import org.bson.types.ObjectId;

public class ProductResponse {
	
	private Integer id;
	private Integer quantity;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
