package com.capstone.cartApplication.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("promotions")
public class Promotions {
    private int promotionId;
    private String promotionDescription;
    private String promotionType;
    private String startDate;
    private String endDate;
    private String status;
    private int valueChange;
    private int noOfPromo;

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getValueChange() {
        return valueChange;
    }

    public void setValueChange(int valueChange) {
        this.valueChange = valueChange;
    }

    public int getNoOfPromo() {
        return noOfPromo;
    }

    public void setNoOfPromo(int noOfPromo) {
        this.noOfPromo = noOfPromo;
    }
}

