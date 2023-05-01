package com.example.eventsmap.model;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Сущноть мероприятий
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Events {

    /**
     * id мероприятия в БД
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Координаты, где будет проходить мероприятие(широта)
     */
    @Column(name = "latitude")
    private double latitude;

    /**
     * Координаты, где будет проходить мероприятие(долгота)
     */
    @Column(name = "longitude")
    private double longitude;

    @Column(name = "adress")
    private String adress;

    /**
     * Назание мероприятия
     */
    @NotEmpty(message = "Введите название мероприятия")
    @Column(name = "title")
    private String title;

    /**
     * Дата мероприятия
     */
    @Temporal(TIMESTAMP)
    @Column(name = "date")
    private Date date;

    /**
     * Дата создания мероприятия
     */
    @Temporal(TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * Дата изменения мероприятия
     */
    @Temporal(TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * Оценка мероприятия
     */
    @Column(name = "rating")
    private double rating;

    /**
     * Описание мероприятия
     */
    @Column(name = "description")
    private String description;


    /**
     * Пользователь, создаший мероприятие
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    /**
     * Город, в котором проходит мероприятие
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ToString.Exclude
    private City city;

    /**
     * Список комментариев для мероприятие
     */
    @OneToMany(mappedBy = "events", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "userEvents", cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @ToString.Exclude
    private List<User> users = new ArrayList<>();


    public void removeUsers() {
        for(User user: this.getUsers()){
            user.removeEvent(this);
        }
    }

    @OneToMany(mappedBy = "events")
    @ToString.Exclude
    private List<EventsRating> ratingList = new ArrayList<>();



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Events other = (Events) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
