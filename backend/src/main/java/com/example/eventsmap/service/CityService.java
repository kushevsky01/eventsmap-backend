package com.example.eventsmap.service;

import com.example.eventsmap.dto.CityDTO;
import com.example.eventsmap.model.City;
import com.example.eventsmap.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис с операциями над сущностью City
 */
@Transactional(readOnly = true)
@Service
public class CityService {

    /**
     * Репозиторий городов
     */
    private final CityRepository cityRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ищет все города
     * @return найденные города
     */
    public List<CityDTO> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities
                .stream()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Ищет город по id
     * @param id id города
     * @return найденный город
     */
    public CityDTO getCityById(long id) {
        Optional<City> city = Optional.ofNullable(cityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

         return modelMapper.map(city, CityDTO.class);
    }
}
