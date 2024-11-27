package com.airbnb.controller;

import com.airbnb.entity.City;
import com.airbnb.entity.Country;
import com.airbnb.entity.Property;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.CityRepository;
import com.airbnb.repository.CountryRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @PostMapping("/addProperty")
    public ResponseEntity<?> createProperty(
            @RequestBody PropertyDto propertyDto,
            @RequestParam long cityId,
            @RequestParam long countryId
    ){
        City city = cityRepository.findById(cityId).get();
        Country country = countryRepository.findById(countryId).get();
        propertyDto.setCity(city);
        propertyDto.setCountry(country);
        Optional<Property> propertyByName = propertyRepository.findPropertyByName(propertyDto.getPropertyName());
        if(propertyByName.isPresent()){
            return new ResponseEntity<>("Property already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PropertyDto dto = propertyService.createProperty(propertyDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties(){
        List<PropertyDto> allProperties = propertyService.findAllProperties();
        return new ResponseEntity<>(allProperties,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam Long id
    ) {
        propertyService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable Long id,
            @RequestBody Property property
    ) {
        Property prop = propertyService.update(id, property);
        return new ResponseEntity<>(prop, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDto> getPropertyById(
            @PathVariable Long id
    ) {
        PropertyDto propertyDto = propertyService.getPropertyId(id);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping("/search-hotels")
    public List<Property> searchHotels(
            @RequestParam String name
    ){
        List<Property> properties = propertyService.searchAllHotels(name);
        return new ResponseEntity<>(properties,HttpStatus.OK).getBody();
    }

}
