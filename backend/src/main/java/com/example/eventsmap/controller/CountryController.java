package com.example.eventsmap.controller;

import com.example.eventsmap.dto.CountryDTO;
import com.example.eventsmap.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  Rest контроллер для операций над сущностью Country
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    /**
     * Сервис с операциями над сущностью Country
     */
    private final CountryService countryService;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Запрос на поиск страны по id
     * @return найденная страна в формате JSON
     */
    @GetMapping
    public List<CountryDTO> getAllCountries() {
        return countryService.getAllCountries();
    }

    /**
     * Запрос на поиск страны по id
     * @param countryId id страны
     * @return найденную страну в формате JSON
     */
    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable(name = "countryId") long countryId){
        CountryDTO country = countryService.getCountryById(countryId);

        return new ResponseEntity<>(country, HttpStatus.OK) ;
    }
}
