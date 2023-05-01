package com.example.eventsmap.dto;

import lombok.Data;

/**
 * DTO сущности Region
 */
@Data
public class RegionDTO {

    /**
     * Id региона
     */
    private long id;

    /**
     * Название региона
     */
    private String name;
}
