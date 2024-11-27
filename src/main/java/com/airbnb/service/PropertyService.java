package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.exception.ResourceNotFoundException;
import com.airbnb.payload.PropertyDto;
import com.airbnb.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public PropertyService(PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public PropertyDto createProperty(PropertyDto propertyDto) {
        Property property = mapToEntity(propertyDto);
        Property savedProperty = propertyRepository.save(property);
        return mapToDto(savedProperty);
    }

    public List<PropertyDto> findAllProperties() {
        List<Property> properties = propertyRepository.findAll();
        List<PropertyDto> propertyDtos = properties.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return propertyDtos;
    }

    public void delete(Long id) {
        propertyRepository.deleteById(id);
    }

    public Property update(Long id, Property property) {
        Property prop = propertyRepository.findById(id).get();
        prop.setPropertyName(property.getPropertyName());
        Property savedProperty = propertyRepository.save(prop);
        return savedProperty;
    }

    public PropertyDto getPropertyId(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record Not Found")
        );
        PropertyDto propertyDto = mapToDto(property);
        return propertyDto;
    }

    public List<Property> searchAllHotels(String name) {
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }


    private PropertyDto mapToDto(Property save) {
        PropertyDto propertyDto = modelMapper.map(save, PropertyDto.class);
        return propertyDto;
    }

    private Property mapToEntity(PropertyDto dto) {
        Property property = modelMapper.map(dto, Property.class);
        return property;
    }
}
