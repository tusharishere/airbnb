package com.airbnb.controller;

import com.airbnb.entity.Country;
import com.airbnb.payload.CountryDto;
import com.airbnb.repository.CountryRepository;
import com.airbnb.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
    private CountryService countryService;
    private CountryRepository countryRepository;

    public CountryController(CountryService countryService, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> createCountry(
            @RequestBody CountryDto dto
    ){
        Optional<Country> opCountryName = countryRepository.findByCountryByName(dto.getCountryName());
        if(opCountryName.isPresent()){
            return new ResponseEntity<>("Country already present", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CountryDto countryDto = countryService.createCountry(dto);
        return new ResponseEntity<>(countryDto, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        List<CountryDto> countryDtos = countryService.findAllCountries();
        return new ResponseEntity<>(countryDtos,HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam Long id
    ){
        countryService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(
            @PathVariable Long id,
            @RequestBody Country country
    ){
        Country coun = countryService.update(id,country);
        return new ResponseEntity<>(coun,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(
            @PathVariable Long id
    ){
        CountryDto countryDto = countryService.getCountryId(id);
        return new ResponseEntity<>(countryDto,HttpStatus.OK);
    }
}
