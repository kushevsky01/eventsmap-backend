package com.example.eventsmap.service;

import com.example.eventsmap.dto.RegionDTO;
import com.example.eventsmap.model.Region;
import com.example.eventsmap.repository.RegionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис с операциями над сущностью Region
 */
@Service
public class RegionService {

    /**
     * Репозиторий регионов
     */
    private final RegionRepository regionRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public RegionService(RegionRepository regionRepository, ModelMapper modelMapper) {
        this.regionRepository = regionRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ищет все регионы
     * @return найденные регионы
     */
    public List<RegionDTO> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        return regions
                .stream()
                .map(region -> modelMapper.map(region, RegionDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Ишет регион по id
     * @param id id региона
     * @return найденный регион
     */
    public RegionDTO getRegionById(long id) {
        Optional<Region> region = Optional.ofNullable(regionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return modelMapper.map(region, RegionDTO.class);
    }
}
