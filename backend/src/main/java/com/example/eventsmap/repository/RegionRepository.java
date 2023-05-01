package com.example.eventsmap.repository;

import com.example.eventsmap.model.City;
import com.example.eventsmap.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий регионов
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    /**
     * Ищет региона по названию
     * @param name название региона
     * @return найденный регион
     */
    Optional<Region> findByName(String name);
}
