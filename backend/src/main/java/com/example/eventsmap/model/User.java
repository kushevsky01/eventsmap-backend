package com.example.eventsmap.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.validation.constraints.*;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Сущноть пользователя
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * id пользователя в БД
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Имя пользователя
     */
    @Column(name = "name", length = 128)
    private String name;

    /**
     * Логин пользователя
     */
    @Column(name = "username", length = 128)
    private String username;

    /**
     * Пароль пользователя
     */
    @Column(name = "password")
    private String password;

    /**
     * Email пользователя
     */
    @Email(message = "Неправильно введён email")
    @Column(name = "email", length = 50)
    private String email;

    /**
     * Роль пользователя (Admin, User, Moderator, Guest)
     */
    @Column(name = "role", length = 50)
    private String role;

    /**
     * Активность пользователя active(true)/banned(false)
     */
    @Column(name = "active")
    private boolean active;

    /**
     * Список мероприятий, принадлежащих пользователю
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Events> eventsList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "user_events",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "events_id")}
    )
    @ToString.Exclude
    private List<Events> userEvents = new ArrayList<>();

    public void addEvents(Events event){
//        System.out.println('h');
        userEvents.add(event);
    }

    public void removeEvent(Events event){
        userEvents.remove(event);
    }

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<EventsRating> ratingList = new ArrayList<>();

    public void addRating(EventsRating eventRating){
        ratingList.add(eventRating);
    }

    public void removeRating(EventsRating eventRating){
        ratingList.remove(eventRating);
    }
}
