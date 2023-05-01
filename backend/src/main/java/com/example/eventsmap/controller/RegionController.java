package com.example.eventsmap.controller;

import com.example.eventsmap.dto.RegionDTO;
import com.example.eventsmap.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest контроллер для операций над сущностью Region
 */
@RestController
@RequestMapping("/regions")
public class RegionController {

    /**
     * Сервис с операциями над сущностью Region
     */
    private final RegionService regionService;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * Запрос на поиск всех регионов
     * @return все регионы
     */
    @GetMapping
    public List<RegionDTO> getAllRegions() {

        return regionService.getAllRegions();
    }

    /**
     * Запрос на поиск региона по id
     * @param regionId id региона
     * @return найденный регион в формате JSON
     */
    @GetMapping("/{regionId}")
    public ResponseEntity<RegionDTO> getRegionById(@PathVariable(name = "regionId") long regionId){
        RegionDTO region = regionService.getRegionById(regionId);

        return new ResponseEntity<>(region, HttpStatus.OK) ;
    }

}
