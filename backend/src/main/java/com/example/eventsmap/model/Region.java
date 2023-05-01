package com.example.eventsmap.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Сущноть регионов страны
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "region")
public class Region {

    /**
     * id региона в БД
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Назание региона
     */
    @Column(name = "name", length = 128)
    private String name;

    /**
     * Дата создания
     */
    @Temporal(TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * Страна в которой находится данный регион
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    @ToString.Exclude
    private Country country;

    /**
     * Спиок городов в данном регионе
     */
    @OneToMany(mappedBy = "region")
    @ToString.Exclude
    private List<City> cities = new ArrayList<>();
}
