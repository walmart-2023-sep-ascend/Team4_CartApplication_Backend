package com.capstone.cartApplication.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.cartApplication.model.Products;




@FeignClient(name="ProductService")
//@RequestMapping("/api/products")
//@FeignClient("http://localhost:8081")
public interface CartInterface {
	
    @GetMapping(path="/api/products/getByID/{id}")
   // @CrossOrigin
   // public ResponseEntity<Object> get (@PathVariable int id);
    public Products get (@PathVariable int id);
    
    @PostMapping("/api/products/insert")
    //public ResponseEntity<Object> insert(@RequestBody Product product);
    public Products insert(@RequestBody Products product);
}
