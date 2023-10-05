package com.capstone.cartApplication.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("products")
public class Products {


	@Id
	private Integer Id;
	private Integer quantity;
	//private String title;
	//private String purchasable;
	
	public Products() {
        this.quantity = 1;
    }
	
	
	/*
	 * public String getPurchasable() { return purchasable; } public void
	 * setPurchasable(String purchasable) { this.purchasable = purchasable; } public
	 * String getTitle() { return title; } public void setTitle(String title) {
	 * this.title = title; }
	 */
	
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	


}
