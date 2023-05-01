package com.example.eventsmap.model;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Сущность стран
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "country")
public class Country {

    /**
     * id страны в БД
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Назание страны
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
     * Спиосок регионов в стране
     */
    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    private List<Region> regions = new ArrayList<>();
}
