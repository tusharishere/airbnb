package com.airbnb.repository;

import com.airbnb.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select c from City c where c.cityName=:cityName")
    Optional<City> findByCityName(@RequestParam("cityName") String cityName);
}