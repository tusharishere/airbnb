package com.airbnb.payload;

import com.airbnb.entity.Property;
import com.airbnb.entity.User;
import lombok.Data;

@Data
public class ReviewDto {
    private Integer rating;
    private String description;
    private Property property;
    private User user;
}
