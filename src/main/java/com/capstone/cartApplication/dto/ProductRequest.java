package com.capstone.cartApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
	
    private Integer id;
   
	/*
	 * private String title; private String purchasable;
	 */
    private Integer quantity;
	
    
    
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
    
	/*
	 * public String getTitle() { return title; } public void setTitle(String title)
	 * { this.title = title; } public String getPurchasable() { return purchasable;
	 * } public void setPurchasable(String purchasable) { this.purchasable =
	 * purchasable; }
	 */
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
    
    
}
