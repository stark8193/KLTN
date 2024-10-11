package com.apec.pos.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
public class RestaurantEntity extends BaseEntity  {

    private String restaurantName;

    private String address;

    private Integer quantitySold;

    private Double distance;

    private Double star;

    private String timeStart;

    private String timeClose;

    private String detail;

    private String phoneNumber;

    private String imgRes;

    @OneToMany(mappedBy = "restaurantEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonManagedReference(value = "food-res")
    private List<FoodEntity> foodEntities;

    public RestaurantEntity(String createBy, String modifiedBy, String restaurantName, String address,
                            Integer quantitySold, Double distance, String phoneNumber, String imgRes,
                            List<FoodEntity> foodEntities) {
        super(createBy, modifiedBy);
        this.restaurantName = restaurantName;
        this.address = address;
        this.quantitySold = quantitySold;
        this.distance = distance;
        this.phoneNumber = phoneNumber;
        this.imgRes = imgRes;
        this.foodEntities = foodEntities;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeClose() {
        return timeClose;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public List<FoodEntity> getFoodEntities() {
        return foodEntities;
    }

    public void setFoodEntities(List<FoodEntity> foodEntities) {
        this.foodEntities = foodEntities;
    }

    public String getImgRes() {
        return imgRes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public RestaurantEntity() {

    }

    public RestaurantEntity(String createBy, String modifiedBy) {
        super(createBy, modifiedBy);
    }

    public Integer getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(Integer quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
