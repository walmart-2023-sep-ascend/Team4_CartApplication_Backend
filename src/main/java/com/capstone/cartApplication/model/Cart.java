package com.capstone.cartApplication.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("cart")
public class Cart {


	@Id
	private ObjectId databaseId;
	private Integer cartId;
	private Integer userId;
	private Date date;
	private String promoCode;
	private Double amount;

	//@DBRef
	private @NonNull List<Products> products;

	public Integer getCartId() {
		System.out.println("-- get cart id-- "+cartId);
		return cartId;
	}

	public void setCartId(Integer cartId) {
		System.out.println("-- set cart id-- "+cartId);
		this.cartId = cartId;
	}

	public Integer getUserId() {
		System.out.println("-- get user id-- "+cartId);
		return userId;
	}

	public void setUserId(Integer userId) {
		System.out.println("-- set user id-- "+cartId);
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public List<Products> getProducts() {
		System.out.println("------ get product ----");
		System.out.println(" --"+products.get(0).getId());
		return products;
	}

	public void setProduct(List<Products> products) {
		this.products = products;
	}


	

}
