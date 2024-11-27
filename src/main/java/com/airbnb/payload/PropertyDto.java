package com.airbnb.payload;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import lombok.Data;

@Data
public class PropertyDto {
    private String propertyName;
    private Integer no_of_guest;
    private Integer no_of_bedrooms;
    private Integer no_of_bathrooms;
    private Integer no_of_beds;
    private Country country;
    private City city;
}
