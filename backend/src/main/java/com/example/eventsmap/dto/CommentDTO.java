package com.example.eventsmap.dto;

import lombok.Data;

import java.util.Date;

/**
 * DTO сущности Comment
 */
@Data
public class CommentDTO {

    /**
     * Id комментария
     */
    private long id;

    /**
     * Текст комментария
     */
    private String text;

    /**
     * Id пользователя, написавший комментарий
     */
    private Long userId;

    /**
     * Дата создания комментария
     */
    private Date createdTime;


    private String username;

}
