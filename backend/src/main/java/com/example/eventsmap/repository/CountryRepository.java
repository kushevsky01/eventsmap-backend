package com.example.eventsmap.repository;

import com.example.eventsmap.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий стран
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    /**
     * Ищет страны по названию
     * @param name название страны
     * @return найденный город
     */
    Optional<Country> findByName(String name);
}
