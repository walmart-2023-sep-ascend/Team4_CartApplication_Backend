package com.capstone.cartApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.cartApplication.model.Promotions;
import com.capstone.cartApplication.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/promotions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PromotionController {

    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/active")
    public List<Promotions> getActivePromotions() {
        return promotionService.getActivePromotions();
    }
}
