package com.capstone.cartApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.cartApplication.convert.ProductRequestToProduct;
import com.capstone.cartApplication.dto.ProductRequest;
import com.capstone.cartApplication.model.Products;
import com.capstone.cartApplication.repository.ProductRepository;

public class ProductServiceImpl implements ProductService{


	private ProductRepository productRepository;
	private ProductRequestToProduct productRequestToProduct;




	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, ProductRequestToProduct productRequestToProduct) {
		this.productRepository = productRepository;
		this.productRequestToProduct = productRequestToProduct;
	}    

	@Override
	public List<Products> findAll() {
		return productRepository.findAll();
	}

	/*
	 * @Override public Products findByTitle(String name) { return
	 * productRepository.findByTitle(name); }
	 */

	@Override
	public Products findItemById(Integer id) {
		return productRepository.findItemById(id);
	}

	@Override
	public Products saveOrUpdate(ProductRequest productRequest) {
		return productRepository.save(productRequestToProduct.convert(productRequest));
	}

	@Override
	public void delete(Integer id) {
		productRepository.deleteById(id);

	}

}
