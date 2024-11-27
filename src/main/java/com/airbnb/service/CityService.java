package com.airbnb.service;

import com.airbnb.entity.City;
import com.airbnb.exception.ResourceNotFoundException;
import com.airbnb.payload.CityDto;
import com.airbnb.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityDto createCity(CityDto dto) {
        City city = mapToEntity(dto);
        City save = cityRepository.save(city);
        CityDto cityDto = mapToDto(save);
        return cityDto;
    }

    public List<CityDto> findAllCities() {
        List<City> cities = cityRepository.findAll();
        List<CityDto> cityDtos = cities.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return cityDtos;
    }

    @Transactional
    public void delete(Long id) {
        cityRepository.deleteById(id);
    }

    public City update(Long id, City city) {
        City citi = cityRepository.findById(id).get();
        citi.setCityName(city.getCityName());
        City savedCity = cityRepository.save(citi);
        return savedCity;
    }

    public CityDto getCityId(Long id) {
        City city = cityRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record Not Found")
        );
        CityDto cityDto = mapToDto(city);
        return cityDto;
    }

    private CityDto mapToDto(City save) {
        CityDto cityDto = modelMapper.map(save, CityDto.class);
        return cityDto;
    }

    private City mapToEntity(CityDto dto) {
        City city = modelMapper.map(dto, City.class);
        return city;
    }
}
