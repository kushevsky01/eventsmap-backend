package com.example.eventsmap.dto;

import lombok.Data;

/**
 * DTO сущности Country
 */
@Data
public class CountryDTO {

    /**
     * Id страны
     */
    private long id;

    /**
     * Название страны
     */
    private String name;
}
