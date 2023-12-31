package com.capstone.cartApplication.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capstone.cartApplication.model.Cart;

@Repository
public interface CartRepository extends  MongoRepository<Cart, ObjectId> {
	
	Cart findCartByUserId(Integer userId);
	
	@Aggregation(pipeline = { "{$group: { _id: '', total: {$max: $cartId }}}" })
	public Integer max();

	@Aggregation(pipeline = { "{$group: { _id: '', total: {$min: $cartId }}}" })
	public Integer min();

	//Cart findById(Integer cartID);
    Cart findCartByCartId(Integer cartID);
	
	//Cart findTopByCartByCart();

}