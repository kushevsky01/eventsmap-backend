package com.example.eventsmap.repository;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий мероприятий
 */
@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {

    /**
     * Ищет мероприятия по названию
     * @param title мероприятия
     * @return найденное мероприятие
     */
    Optional<Events> findByTitle(String title);

    List<Events> findByLatitudeAndLongitude(double latitude, double longitude);

    List<Events> findByUser(User user);

    List<Events> findByTitleContaining(String infix);

    List<Events> findByDateLessThan(Date date);

    void deleteAllByDateIsLessThan(Date date);

}
