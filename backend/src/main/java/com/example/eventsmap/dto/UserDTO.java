package com.example.eventsmap.dto;

import lombok.Data;

/**
 * DTO сущности User
 */
@Data
public class UserDTO {

    /**
     * Id пользователя
     */
    private long id;

    /**
     * Имя пользователя
     */
    private String name;

    /**
     * Логин мероприятия
     */
    private String username;

    /**
     * Пароль пользователя
     */
    private String password;

    /**
     * Email пользователя
     */
    private String email;

    /**
     * Роль пользователя
     */
    private String role;

    /**
     * Активность пользователя
     */
    private boolean active;

}
