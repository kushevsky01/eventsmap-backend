package com.example.eventsmap.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * Сущноть городов региона
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "city")
public class City {

    /**
     * id города в БД
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Назание города
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
     * Регион, содержащий этот город
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    @ToString.Exclude
    private Region region;

    /**
     * Спиок мероприятий, принадлежащих этому городу
     */
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Events> eventsList = new ArrayList<>();
}
