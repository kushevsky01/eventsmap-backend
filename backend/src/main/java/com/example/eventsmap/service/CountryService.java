package com.example.eventsmap.service;

import com.example.eventsmap.dto.CountryDTO;
import com.example.eventsmap.model.Country;
import com.example.eventsmap.repository.CountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис с операциями над сущностью Country
 */
@Service
public class CountryService {

    /**
     * Репозиторий стран
     */
    private final CountryRepository countryRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ищет все страны
     * @return
     */
    public List<CountryDTO> getAllCountries() {
        List<Country> countries = countryRepository.findAll();

        return countries
                .stream()
                .map(country -> modelMapper.map(country, CountryDTO.class))
                .collect(Collectors.toList());
}

    /**
     * Ищет страну по id
     * @param id id траны
     * @return найденную страну
     */
    public CountryDTO getCountryById(long id) {
        Optional<Country> country= Optional.ofNullable(countryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return modelMapper.map(country, CountryDTO.class);
    }
}
