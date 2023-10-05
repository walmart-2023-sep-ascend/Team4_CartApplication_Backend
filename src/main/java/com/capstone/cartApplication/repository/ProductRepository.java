package com.capstone.cartApplication.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capstone.cartApplication.model.Products;

@Repository
public interface ProductRepository extends MongoRepository<Products, Integer> {
    List<Products> findAll();

   // Products findByTitle(String title);

    Products findItemById(Integer id);
    
}
