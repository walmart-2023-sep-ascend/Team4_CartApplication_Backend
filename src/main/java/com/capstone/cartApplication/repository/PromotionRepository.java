package com.capstone.cartApplication.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.capstone.cartApplication.model.Promotions;

import java.util.List;

public interface PromotionRepository extends MongoRepository<Promotions, String> {
    List<Promotions> findByStatus(String status);

}
