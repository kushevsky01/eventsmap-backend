package com.example.eventsmap.dto;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.EventsRating;
import lombok.*;

import java.util.Date;
import java.util.List;

import com.example.eventsmap.model.User;

/**
 * DTO сущности Events
 */
@Data
public class EventsDTO {

    /**
     * Id мероприятия
     */
    private long id;

    /**
     * Название мероприятия
     */
    private String title;

    /**
     * Координаты проведения мероприятия (широта)
     */
    private double latitude;

    /**
    * Координаты проведения мероприятия (долгота)
    */
    private double longitude;

    /**
     * Дата проведения мероприятия
     */
    private Date date;

    /**
     * Дата создания мероприятия
     */
    private Date createdTime;

    /**
     * Дата изменения мероприятия
     */
    private Date updateTime;

    /**
     * Оценка мероприятия
     */
    private double rating;

    /**
     * Описание мероприятия
     */
    private String description;

    /**
     * Название города
     */
    private String cityName;

    /**
     * Пользователь создавший мероприятие
     */
     private String userName;

    /**
     * адресс в городе
     */
    private String adress;
//
//    private List<EventsRating> ratingList;


}
