package com.capstone.cartApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.cartApplication.model.Promotions;
import com.capstone.cartApplication.repository.PromotionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public List<Promotions> getActivePromotions() {
    	Date currentDate = new Date();
        List<Promotions> activePromotions = promotionRepository.findByStatus("Active");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Promotions> validPromotions = new ArrayList<>();
        for (Promotions promotion : activePromotions) {
            try {
            	Date startDate = sdf.parse(promotion.getStartDate());
                Date endDate = sdf.parse(promotion.getEndDate());
                if (startDate.before(currentDate) && endDate.after(currentDate)) {
                    validPromotions.add(promotion);
                }
            } catch (ParseException e) {
                // Handle parsing errors if needed
            }
        }
        return validPromotions;
    }
 }



