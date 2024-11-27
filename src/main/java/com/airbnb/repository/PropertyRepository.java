package com.airbnb.repository;

import com.airbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("select p from Property p where p.propertyName=:propertyName")
    Optional<Property> findPropertyByName(String propertyName);

    @Query("select p from Property p JOIN p.city c JOIN p.country co where p.propertyName LIKE %:name% or c.cityName LIKE %:name% or co.countryName LIKE %:name%")
    List<Property> searchHotels(@Param("name") String name);


}