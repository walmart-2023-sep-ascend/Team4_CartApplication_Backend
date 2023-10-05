package com.capstone.cartApplication.service;

import java.util.List;

import com.capstone.cartApplication.dto.ProductRequest;
import com.capstone.cartApplication.model.Products;

public interface ProductService {
	
	List<Products> findAll();

   // Products findByTitle(String name);

    Products findItemById(Integer id);

    Products saveOrUpdate(ProductRequest itemRequest);

    void delete(Integer id);

}
