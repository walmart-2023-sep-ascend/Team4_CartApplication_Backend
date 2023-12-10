package com.capstone.cartApplication.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.capstone.cartApplication.model.Wishlist;

@FeignClient(name="WhishlistService",url="http://52.142.30.237:9004")
public interface CartWishlistInterface {
	
	@GetMapping(path="/wishlist/fetch/user")
	   // @CrossOrigin
	   // public ResponseEntity<Object> get (@PathVariable int id);
	    public Wishlist get (@PathVariable int id);
	    
	    @PostMapping("/wishlist/add")
	    //public ResponseEntity<Object> insert(@RequestBody Product product);
	    public Wishlist insert(@RequestBody Wishlist wislist);

}
