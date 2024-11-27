package com.airbnb.service;

import com.airbnb.entity.Country;
import com.airbnb.exception.ResourceNotFoundException;
import com.airbnb.payload.CountryDto;
import com.airbnb.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public CountryDto createCountry(CountryDto dto) {
        Country country = mapToEntity(dto);
        Country save = countryRepository.save(country);
        CountryDto countryDto = mapToDto(save);
        return countryDto;
    }

    public List<CountryDto> findAllCountries() {
        List<Country> countries = countryRepository.findAll();
        List<CountryDto> countryDtos = countries.stream().map(r->mapToDto(r)).collect(Collectors.toList());
        return countryDtos;
    }

    @Transactional
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }

    public Country update(Long id, Country country) {
        Country coun = countryRepository.findById(id).get();
        coun.setCountryName(country.getCountryName());
        Country savedCountry = countryRepository.save(coun);
        return savedCountry;
    }

    public CountryDto getCountryId(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Record Not Found")
        );
        CountryDto countryDto = mapToDto(country);
        return countryDto;
    }


    private CountryDto mapToDto(Country save) {
        CountryDto countryDto = modelMapper.map(save, CountryDto.class);
        return countryDto;
    }

    private Country mapToEntity(CountryDto dto) {
        Country country = modelMapper.map(dto, Country.class);
        return country;
    }


}
