package com.example.eventsmap.dto;

import lombok.Data;

/**
 * DTO сущности City
 */
@Data
public class CityDTO {

    /**
     * Id города
     */
    private long id;

    /**
     * Название города
     */
    private String name;
}
