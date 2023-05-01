package com.example.eventsmap.repository;

import com.example.eventsmap.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий городов
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Ищет города по названию
     * @param name название города
     * @return найденный город
     */
    Optional<City> findByName(String name);
}
