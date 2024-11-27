package com.airbnb.repository;

import com.airbnb.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("select c from Country c where c.countryName=:countryName")
    Optional<Country> findByCountryByName(String countryName);
}