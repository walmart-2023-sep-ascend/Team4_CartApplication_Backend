package com.capstone.cartApplication.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.capstone.cartApplication.dto.ProductRequest;
import com.capstone.cartApplication.model.Products;
@Component
public class ProductRequestToProduct implements Converter<ProductRequest, Products> {

    @Override
    public Products convert(ProductRequest productRequest) {
        Products prodcuts = new Products();
        
        if (!StringUtils.isEmpty(productRequest.getId())) {
        	System.out.println("productRequest-> Id :"+productRequest.getId());
        	prodcuts.setId(productRequest.getId());
        }
		/*
		 * prodcuts.setTitle(productRequest.getTitle());
		 * prodcuts.setPurchasable(productRequest.getPurchasable());
		 */
        prodcuts.setQuantity(productRequest.getQuantity());
        return prodcuts;
    }
}
