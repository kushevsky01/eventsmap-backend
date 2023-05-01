package com.example.eventsmap.controller;

import com.example.eventsmap.dto.CityDTO;
import com.example.eventsmap.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest контроллер для операций над сущностью City
 */
@RestController
@RequestMapping("/cities")
public class CityController {

    /**
     * Сервис с операциями над сущностью City
     */
    private final CityService cityService;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Запрос на поиск городов
     * @return найденные города
     */
    @GetMapping
    public List<CityDTO> getAllCities() {

        return cityService.getAllCities();
    }

    /**
     * Запрос на поиск города по id
     * @param cityId id комментария
     * @return найденный город в формате JSON
     */
    @GetMapping("/{cityId}")
    public ResponseEntity<CityDTO> getCityById(@PathVariable(name = "cityId") long cityId){
        CityDTO city = cityService.getCityById(cityId);

        return new ResponseEntity<>(city, HttpStatus.OK) ;
    }
}
